package com.lottery.biying.Activity.User.Message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.Proxy.TeamManage.SiteMessageActivity;
import com.lottery.biying.Adapter.MessageListAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.MessageBean;
import com.lottery.biying.bean.PersonalIncomBean;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/9.
 */
public class MessageListActivity extends BaseActivity implements View.OnClickListener {

    SwipeHeader swipe;
    ListView listview;
    int mtotalItemCount, mfirstVisibleItem;
    boolean hasdata = true;
    private int currentPage = 1;
    ArrayList<MessageBean> messageBeans = new ArrayList<>();
    MessageListAdapter messageListAdapter;
    private final int RefreshCount = 20;
    TextView contactsuperior, contactsubordinate;
    int Mode = 0;//1 删除状态
    TextView assistant;
    ImageView assistant_icon;
    View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagelist);
        Initview();

    }


    @Override
    protected void onStart() {
        super.onStart();
        LogTools.e("onStart","onStart");
        GetData(true);
    }

    private void Initview() {
        setIsneedback(false);

        back = FindView(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        assistant = FindView(R.id.assistant);
        assistant.setVisibility(View.GONE);
        assistant.setText("删除");
        assistant.setOnClickListener(this);

        assistant_icon = FindView(R.id.assistant_icon);
        assistant_icon.setImageDrawable(getResources().getDrawable(R.drawable.delete_message));
        assistant_icon.setVisibility(View.VISIBLE);
        assistant_icon.setOnClickListener(this);

        contactsuperior = FindView(R.id.contactsuperior);
        contactsubordinate = FindView(R.id.contactsubordinate);
        if(RepluginMethod.getApplication(this).isgent().equalsIgnoreCase("1"))
        {
            contactsubordinate.setVisibility(View.GONE);
        }
        contactsuperior.setOnClickListener(this);
        contactsubordinate.setOnClickListener(this);

        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.message));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        swipe = FindView(R.id.swipe);
        listview = FindView(R.id.listview);
        listview.setHeaderDividersEnabled(false);
        listview.setFooterDividersEnabled(false);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                currentPage = 1;
                GetData(true);
            }
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogTools.e("mtotalItemCount", mtotalItemCount + " " + mfirstVisibleItem + " " + hasdata);
                if (mtotalItemCount - mfirstVisibleItem < RefreshCount && scrollState == 0 && hasdata) {
                    LogTools.e("runing", "runing");
                    currentPage++;
                    GetData(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mfirstVisibleItem = firstVisibleItem;
                mtotalItemCount = totalItemCount;
            }
        });
        View view = new View(this);
        view.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getDIP2PX(this, 62)));
        listview.addFooterView(view);
    }

    private void GetData(final boolean init) {

        if (init) {
            messageBeans.clear();
            currentPage = 1;
            Setadapter();
            hasdata = true;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("pageLimit", RefreshCount + "");
        requestParams.put("currentPage", currentPage + "");


        Httputils.PostWithBaseUrl(Httputils.MessageList, requestParams, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
                hasdata = false;
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                swipe.setRefreshing(false);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(MessageListActivity.this, jsonObject.optString("msg"));
                    hasdata = false;
                    return;
                }
                if (init) {
                    messageBeans.clear();
                    currentPage = 1;
                    Setadapter();
                    hasdata = true;
                }

                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    messageBeans.add(MessageBean.AnalySis(resultList.optJSONObject(i)));
                }

                if (resultList.length() < RefreshCount)
                    hasdata = false;
                if (messageBeans.size() >= RefreshCount && resultList.length() < RefreshCount) {
                    ToastUtil.showMessage(MessageListActivity.this, getString(R.string.completetoast));
                }
                Setadapter();
            }
        });
    }

    private void Setadapter() {
        if (messageListAdapter == null) {
            messageListAdapter = new MessageListAdapter(messageBeans, this);
            listview.setAdapter(messageListAdapter);
        } else {
            messageListAdapter.Notify(messageBeans);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BundleTag.ResultCode) {
            GetData(true);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.contactsuperior:
                Intent intent = new Intent();
                intent.putExtra(BundleTag.Type, "0");
                intent.putExtra(BundleTag.Username, "上级");
                intent.setClass(MessageListActivity.this, SiteMessageActivity.class);
                startActivityForResult(intent, BundleTag.RequestCode);
                break;
            case R.id.contactsubordinate:
                Intent intent2 = new Intent();
                intent2.putExtra(BundleTag.Type, "1");
                intent2.setClass(MessageListActivity.this, SiteMessageActivity.class);
                startActivityForResult(intent2, BundleTag.RequestCode);
                break;
            case R.id.assistant:
                if (Mode == 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < messageListAdapter.getMessageBeans().size(); i++) {
                        if (messageListAdapter.getMessageBeans().get(i).ischeck()) {
                            sb.append(messageListAdapter.getMessageBeans().get(i).getId());
                            sb.append(";");
                        }
                    }
                    if(sb.length()>1)
                    {
                        sb.deleteCharAt(sb.length() - 1);
                        DelateMessage(sb.toString());
                    }
                    else
                    {
                        Mode = 0;
                        messageListAdapter.setMode(Mode);
                        assistant.setVisibility(View.GONE);
                        assistant_icon.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.assistant_icon:
                if (Mode == 0) {
                    if (messageListAdapter != null) {
                        Mode = 1;
                        messageListAdapter.setMode(Mode);
                        assistant.setVisibility(View.VISIBLE);
                        assistant_icon.setVisibility(View.GONE);
                    }
                }

                break;
            case R.id.back:
                if (Mode == 0) {
                    finish();
                } else {
                    Mode = 0;
                    messageListAdapter.setMode(0);
                    assistant.setVisibility(View.GONE);
                    assistant_icon.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
//        {
//            if(Mode==0)
//            {
//                finish();
//            }
//            else
//            {
//                Mode=0;
//                messageListAdapter.setMode(0);
//                assistant.setVisibility(View.VISIBLE);
//                assistant_icon.setVisibility(View.GONE);
//            }
//            return true;
//        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Mode == 0) {
                finish();
            } else {
                Mode = 0;
                messageListAdapter.setMode(0);
                assistant.setVisibility(View.VISIBLE);
                assistant_icon.setVisibility(View.GONE);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void DelateMessage(String ids) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", ids);
        Httputils.PostWithBaseUrl(Httputils.DeleteMessage, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                ToastUtil.showMessage(MessageListActivity.this, jsonObject.optString("msg"));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                Mode = 0;
                messageListAdapter.setMode(Mode);
                assistant.setVisibility(View.GONE);
                assistant_icon.setVisibility(View.VISIBLE);
                GetData(true);
            }
        });
    }
}
