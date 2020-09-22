package com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.AccTransferAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.AccChangeBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/2.
 */
public class LotteryAccountTracsferListActivity2 extends BaseActivity {


    RadioGroup radiogroup;
    ListviewWithBackTitle listview;
    TextView backtitle;
    boolean hasdata = true;
    private int currentPage = 1;
    private final int RefreshCount = 10;
    int mtotalItemCount, mfirstVisibleItem,selectedtype;
    ArrayList<AccChangeBean> accs = new ArrayList<>();
    AccTransferAdapter accadapter;
    ImageView assistant_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_change);
        Initview();
    }

    private void Initview() {
        selectedtype=getIntent().getIntExtra(BundleTag.Type,0);
        radiogroup = FindView(R.id.radiogroup);
        radiogroup.setVisibility(View.GONE);
        listview = FindView(R.id.listview);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;

                GetData(true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                GetData(false);
            }
        });
        backtitle = FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.lotterytransfer));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        GetData(true);
    }

    public void GetData(final boolean isinit) {
        if (isinit) {
            currentPage=1;
            hasdata=true;
            accs.clear();
            SetAdapter();
        }

        Intent intent = getIntent();
        RequestParams requestparams = new RequestParams();
        requestparams.put("currentPage", currentPage + "");
        requestparams.put("pageLimit", RefreshCount + "");
        requestparams.put("startTime", intent.getStringExtra(BundleTag.StartTime));
        requestparams.put("finishTime", intent.getStringExtra(BundleTag.EndTime));
        if (intent.getStringExtra(BundleTag.AccType) != null)
            requestparams.put("changeType", intent.getStringExtra(BundleTag.AccType));
        if (intent.getStringExtra(BundleTag.LotteryCode) != null)
            requestparams.put("lotteryCode", intent.getStringExtra(BundleTag.LotteryCode));
        requestparams.put("flag", intent.getIntExtra(BundleTag.Incluesubordinate, 0) + "");
        String username = intent.getStringExtra(BundleTag.Username);
        if (username != null && username.length() > 0) {
            requestparams.put("account", intent.getStringExtra(BundleTag.Username));
        }


        Httputils.PostWithBaseUrl(selectedtype>0?Httputils.AccountChangeListv2:Httputils.AccountChangeList, requestparams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata = false;
                if(accs.size()==0)
                {
                    listview.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("LLLLLL",jsonObject.toString());
                listview.onRefreshComplete();
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(LotteryAccountTracsferListActivity2.this,jsonObject.optString("msg"));
                    hasdata = false;
                    return;
                }
                if(isinit)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata=true;
                    accs.clear();
                }

                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    accs.add(AccChangeBean.AnalusisData(resultList.optJSONObject(i)));
                }
                if (resultList.length() < RefreshCount)
                    hasdata = false;
                if (  resultList.length() < RefreshCount) {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(accs.size() >= RefreshCount)
                    ToastUtil.showMessage(LotteryAccountTracsferListActivity2.this, getString(R.string.completetoast));
                }
                SetAdapter();
                if(accs.size()==0)
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

    private void SetAdapter() {
        if (accadapter == null) {
            accadapter = new AccTransferAdapter(accs, this,selectedtype);
            listview.setAdapter(accadapter);
        } else {
            accadapter.Notidfy(accs,selectedtype);
        }
    }


}
