package com.lottery.biying.Activity.User.Change;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.Change_Adapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PlatformBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30. 额度转换
 */
public class ChangeActivity extends BaseActivity implements View.OnClickListener {
    TextView backtitle;
    ImageView back;
    ListviewWithBackTitle listView;
    Change_Adapter adapter;
    ArrayList<PlatformBean> platformBeans = new ArrayList<PlatformBean>();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
        initview();

    }

    private void initview() {
        sharedPreferences=RepluginMethod.getHosttSharedPreferences(ChangeActivity.this);

        String title=getIntent().getStringExtra("title");
        setBackTitleClickFinish();
        backtitle = FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.moneytransfer):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        listView =  FindView(R.id.listview);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                GetPlatform(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.gray1)));
        listView.setDividerHeight(2);
        GetPlatform(false);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (platformBeans.size() > 0)
            adapter.notifyData(platformBeans);
    }

    private void GetPlatform(final boolean clear) {
        if (clear) {
            platformBeans.clear();
            sharedPreferences.edit().putString(BundleTag.ChangeTempData, "").commit();
            if (adapter == null) {
                adapter = new Change_Adapter(ChangeActivity.this, platformBeans);
                listView.setAdapter(adapter);
            } else {
                adapter.notifyData(platformBeans);
            }
        }
        String json=sharedPreferences.getString(BundleTag.ChangeTempData,"");
        if(json.length()>0)
        {
            try {
                Handledata(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listView.onRefreshComplete();
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.Platform, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listView.onRefreshComplete();
                if(platformBeans.size()==0)
                {
                    listView.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                listView.onRefreshComplete();
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                LogTools.e("ddddsfafadsfas", jsonObject.toString());
                Handledata(jsonObject);
                sharedPreferences.edit().putString(BundleTag.ChangeTempData,jsonObject.toString()).commit();
            }
        });
    }

    private void Handledata(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.optJSONArray("datas");
        if (jsonArray != null && jsonArray.length() > 0) {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsobject = jsonArray.optJSONObject(i);
                PlatformBean bean = new PlatformBean();
                bean.setFlat(jsobject.optString("flat", ""));
//                        bean.setFlatDes(jsobject.optString("flatDes", ""));
                bean.setFlatName(jsobject.optString("flatName", ""));
//                        bean.setFlatUrl(jsobject.optString("flatUrl", ""));
                bean.setBigPic(jsobject.optString("bigPic", ""));
                bean.setSmallPic(jsobject.optString("smallPic", ""));
                bean.setState(false);
                bean.setJine("余额");
                platformBeans.add(bean);
            }
            if (adapter == null) {
                adapter = new Change_Adapter(ChangeActivity.this, platformBeans);
                listView.setAdapter(adapter);
            } else {
                adapter.notifyData(platformBeans);
            }

            if(platformBeans.size()==0)
            {
                listView.ShowTextview();
            }
            else
            {
                listView.ShowListview();
            }
        }
    }
}
