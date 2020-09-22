package com.lottery.biying.Activity.User.Proxy.SalaryManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/3. 我的日薪
 */
public class MyDailySalaryFragment extends BaseFragment{

    TextView dailysalary,startsalary,salarymode,salaryperiod,betrequires,loserequire,maxfund,opentime;
    RelativeLayout nosalary;
    SwipeHeader swipe;
    ScrollView scrollView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitView();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_my_daily_salary,null);
    }


    private void InitView()
    {
        scrollView=FindView(R.id.scrollView);
        scrollView.setVisibility(View.GONE);
        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                GetData();
            }
        });

        maxfund=FindView(R.id.maxfund);
        opentime=FindView(R.id.opentime);
        dailysalary=FindView(R.id.dailysalary);
        startsalary=FindView(R.id.startsalary);
        salarymode=FindView(R.id.salarymode);
        salaryperiod=FindView(R.id.salaryperiod);
        betrequires=FindView(R.id.betrequires);
        loserequire=FindView(R.id.loserequire);
        nosalary=FindView(R.id.nosalary);
        nosalary.setVisibility(View.VISIBLE);
        GetData();
    }

    private void GetData()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("flag","1");

        Httputils.PostWithBaseUrl(Httputils.PersonalSalaryDetail,requestParams,new MyJsonHttpResponseHandler(getActivity(),true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("PersonalSalaryDetail",jsonObject.toString());
                swipe.setRefreshing(false);
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                    return;
                }
//                if(isinit)
//                    arrayList.clear();
                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                scrollView.setVisibility(View.VISIBLE);
                if(resultList.length()>0)
                {
                    nosalary.setVisibility(View.GONE);
                    JSONObject jsonobj=resultList.optJSONObject(0);
                    dailysalary.setText(jsonobj.optString("salaryMoney",""));
                    startsalary.setText(jsonobj.optString("startMoney",""));
                    salarymode.setText(jsonobj.optString("privodeFangshi",""));
                    salaryperiod.setText(jsonobj.optString("privodezhouqi",""));
                    betrequires.setText(jsonobj.optString("personCountValue",""));
                    loserequire.setText(jsonobj.optString("lossCount",""));
                    maxfund.setText(jsonobj.optString("moneyCount",""));
                    opentime.setText(jsonobj.optString("createTime",""));
                }
                else
                {
                    nosalary.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
