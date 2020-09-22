package com.lottery.biying.Activity.User.Proxy.SalaryManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.DailySalaryPayoffAdapter;
import com.lottery.biying.Adapter.TeamIncomAdapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.DailySalaryitemBean;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3. 日薪发放
 */
public class DailySalaryPayOffFragment extends BaseFragment {

    String timestr[];
    RadioGroup radiogroup;
    ArrayList<DailySalaryitemBean> arrayList = new ArrayList<>();
    DailySalaryPayoffAdapter dailySalaryPayoffAdapter;
    ListviewWithBackTitle listview;
    int mtotalItemCount, mfirstVisibleItem;
    private int currentPage = 1;
    boolean hasdata = true;
    private final int RefreshCount = 20;
    private TextView notice;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_daily_salary_payoff, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BundleTag.ResultCode) {
            currentPage = 1;
            GetData(true);
        }
    }

    private void Initview() {
        notice=FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice,HttpforNoticeinbottom.Tag.rxff.name,getActivity());
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
        radiogroup = FindView(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton1:
                        timestr = TimeUtils.GetTimestr(1);
                        break;
                    case R.id.radiobutton2:
                        timestr = TimeUtils.GetTimestr(7);
                        break;
                    case R.id.radiobutton3:
                        timestr = TimeUtils.GetTimestr(30);
                        break;
                }
                currentPage = 1;
                GetData(true);
            }
        });
        ((RadioButton) radiogroup.getChildAt(0)).setChecked(true);
    }

    //PersonalSalaryList
    public void GetData(final boolean isinit) {
        if (isinit) {
            currentPage = 1;
            hasdata = true;
            arrayList.clear();
            SetAdapter();
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("startTime", timestr[0]);
        requestParams.put("finishTime", timestr[1]);
        requestParams.put("currentPage", currentPage + "");
        requestParams.put("pageLimit", RefreshCount + "");


        Httputils.PostWithBaseUrl(Httputils.PersonalSalaryList, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata = false;
                if(arrayList.size()==0)
                {
                    listview.ShowTextview();
                }

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                listview.onRefreshComplete();
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                    hasdata = false;
                    return;
                }
                if (isinit == true)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata = true;
                    arrayList.clear();
                }

                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    arrayList.add(DailySalaryitemBean.Analysis(resultList.optJSONObject(i)));
                }
                if (resultList.length() < RefreshCount)
                    hasdata = false;

                if (  resultList.length() < RefreshCount) {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(arrayList.size() >= RefreshCount)
                    ToastUtil.showMessage(getActivity(), getString(R.string.completetoast));
                }
                SetAdapter();
                if(arrayList.size()==0)
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
        if (dailySalaryPayoffAdapter == null) {
            dailySalaryPayoffAdapter = new DailySalaryPayoffAdapter(arrayList, getActivity(), this);
            listview.setAdapter(dailySalaryPayoffAdapter);
        } else {
            dailySalaryPayoffAdapter.Notidfy(arrayList);
        }
    }

}
