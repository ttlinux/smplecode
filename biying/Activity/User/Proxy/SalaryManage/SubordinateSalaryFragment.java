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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.SubordinateSalaryAdapter;
import com.lottery.biying.Adapter.TeamIncomAdapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.Salarybean;
import com.lottery.biying.bean.TeamIncomeBean;
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
 * Created by Administrator on 2018/5/3. 下级日薪列表
 */
public class SubordinateSalaryFragment extends BaseFragment {

    ArrayList<Salarybean> salarybeans = new ArrayList<>();
    ListviewWithBackTitle listview;
    private int currentPage = 1;
    private final int RefreshCount = 20;
    boolean hasdata = true;
    int mtotalItemCount, mfirstVisibleItem;
    SubordinateSalaryAdapter subordinateSalaryAdapter;
    String username;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitView();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(getActivity()!=null && !state && subordinateSalaryAdapter!=null)
        {
            GetData(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_subordinate_salary, null);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BundleTag.ResultCode) {
            GetData(true);
        }
    }

    private void InitView() {
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
        GetData(true);
    }

    private void GetData(final boolean isinit) {

        if (isinit) {
            currentPage = 1;
            hasdata = true;
            salarybeans.clear();
            SetAdapter();
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("flag", "0");
        requestParams.put("currentPage", currentPage + "");
        requestParams.put("pageLimit", RefreshCount + "");
        if (username != null && username.length() > 0)
            requestParams.put("account", username);


        Httputils.PostWithBaseUrl(Httputils.PersonalSalaryDetail, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata = false;
                username = "";
                if(salarybeans.size()==0)
                {
                    listview.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                username = "";
                listview.onRefreshComplete();
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                    return;
                }
                if (isinit)
                {
                    hasdata = true;
                    salarybeans.clear();
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                }

                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    salarybeans.add(Salarybean.Analysis(resultList.optJSONObject(i)));
                }
                if (resultList.length() < RefreshCount)
                    hasdata = false;
                if (  resultList.length() < RefreshCount) {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(salarybeans.size() >= RefreshCount)
                    ToastUtil.showMessage(getActivity(), getString(R.string.completetoast));
                }
                SetAdapter();
                if(salarybeans.size()==0)
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
        if (subordinateSalaryAdapter == null) {
            subordinateSalaryAdapter = new SubordinateSalaryAdapter(salarybeans, getActivity(), this);
            listview.setAdapter(subordinateSalaryAdapter);
        } else {
            subordinateSalaryAdapter.Notidfy(salarybeans);
        }
    }

    @Override
    public void Notification(Object... obj) {
        super.Notification(obj);

        if (obj != null && obj.length > 0) {
            username = String.valueOf(obj[0]);
            GetData(true);
        }
    }
}
