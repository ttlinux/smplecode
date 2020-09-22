package com.lottery.biying.Activity.User.Message;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.MessageDetailAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.MessageBean;
import com.lottery.biying.bean.MessageDetailBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/9.
 */
public class MessageDatailActivity extends BaseActivity {

    String id, groupKey;
    TextView commit;
    EditText content;
    MessageDetailAdapter messageDetailAdapter;
    SwipeRefreshLayout swipe;
    ListView listview;
    ArrayList<MessageDetailBean> messageDetailBeans=new ArrayList<>();
    int countSec;
    Handler handler=new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what)
            {
                case 0:
                    countSec++;
                    Rect rect = new Rect();
                    getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    if(getWindow().getDecorView().getHeight() - rect.bottom - getSoftButtonsBarHeight()!= 0 )
                        countSec=0;
                    else
                    {
                        if(countSec==5)
                        {
                            GetData();
                        }

                    }

                    sendEmptyMessageDelayed(0,1000);
            }
        }
    };


    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_message_detail);
        Initview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler=null;
    }

    private void Initview() {
        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.message));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        swipe = FindView(R.id.swipe);
        listview = FindView(R.id.listview);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetData();
            }
        });
        content=FindView(R.id.content);
        commit=FindView(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getText().toString().length()>0)
                SendMessage(content.getText().toString());
                else
                    ToastUtil.showMessage(MessageDatailActivity.this,"请输入内容");
            }
        });
        id = getIntent().getStringExtra(BundleTag.id);
        groupKey = getIntent().getStringExtra(BundleTag.GroupId);
        GetData();
        handler.sendEmptyMessageDelayed(0,1000);
    }

    private void GetData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", id);
        Httputils.PostWithBaseUrl(Httputils.MessageContent, requestParams, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(MessageDatailActivity.this, jsonObject.optString("msg"));
                    return;
                }
                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("resultList");
                messageDetailBeans.clear();
                for (int i = 0; i < resultList.length(); i++) {
                    messageDetailBeans.add(MessageDetailBean.Analysis(resultList.optJSONObject(i)));
                }
                SetAdapter();
                countSec=0;
            }
        });
    }

    private void SendMessage(final String content) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("groupKey", groupKey);
        requestParams.put("content", content);
        commit.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.SendMessage, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                commit.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                commit.setEnabled(true);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(MessageDatailActivity.this, jsonObject.optString("msg"));
                    return;
                }
                MessageDatailActivity.this.content.setText("");
                GetData();
            }
        });
    }

    private void SetAdapter()
    {
        if(messageDetailAdapter==null)
        {
            messageDetailAdapter=new MessageDetailAdapter(messageDetailBeans,this);
            listview.setAdapter(messageDetailAdapter);
        }
        else
        {
            messageDetailAdapter.Notify(messageDetailBeans);
        }
        listview.setSelection(messageDetailAdapter.getCount()-1);
    }


}
