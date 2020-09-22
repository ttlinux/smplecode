package com.lottery.biying.Activity.User.WithDraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.withdrawListAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.WithDrawBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/9.
 */
public class WithDrawActivity extends BaseActivity{

    TextView addnewcard;
    ListviewWithBackTitle listview;
    withdrawListAdapter withdrawlistAdapter;
    ArrayList<WithDrawBean> jsonObjects=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        Initview();
    }

    private void Initview()
    {
        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.withdraw):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        addnewcard=FindView(R.id.addnewcard);
        addnewcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WithDrawActivity.this, BandBankCardActivity.class), BundleTag.RequestCode);
            }
        });

        listview=FindView(R.id.listview);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                GetBankCardList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        GetBankCardList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==BundleTag.ResultCode)
        {
            jsonObjects.clear();
            SetAdapter("1");
            GetBankCardList();
        }
    }

    private  void GetBankCardList(){
        String   UserName = RepluginMethod.getApplication(this).getBaseapplicationUsername();
        RequestParams requestParams=new RequestParams();
        requestParams.put("userName",UserName);
        Httputils.PostWithBaseUrl(Httputils.SelectBankList, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                if(jsonObjects.size()==0)
                {
                    listview.ShowTextview();
                }

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                listview.onRefreshComplete();
                LogTools.e("jsonsjosn", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                JSONObject temp = jsonObject.optJSONObject("datas");
                if (temp.optString("addFlag", "").equalsIgnoreCase("true")) {
                    addnewcard.setVisibility(View.VISIBLE);
                } else {
                    addnewcard.setVisibility(View.GONE);
                }
                jsonObjects.clear();
                JSONArray jsonArray = temp.optJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObjects.add(WithDrawBean.Analysis(jsonArray.optJSONObject(i)));
                }
                SetAdapter("1");
                if(jsonObjects.size()==0)
                {
                    listview.ShowTextview();
                }
                else
                {
                    listview.ShowListview();
                }
            }
        });
    }

    private void SetAdapter(String type)
    {
        if(withdrawlistAdapter==null)
        {
            withdrawlistAdapter=new withdrawListAdapter(this,jsonObjects,type);
            listview.setAdapter(withdrawlistAdapter);
        }
        else
        {
            withdrawlistAdapter.Notify(jsonObjects,type);
        }
    }
}
