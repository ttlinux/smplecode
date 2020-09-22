package com.lottery.biying.Activity.User.LotteryAccountTransfer.WithdrawAndDepositRecord;

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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.DepositRecordAdapter;
import com.lottery.biying.Adapter.WithdrawRecordAdapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.PaymentBean;
import com.lottery.biying.bean.PaymentBean2;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class WithdrawFragment extends BaseFragment {


    RadioGroup radiogroup;
    ListviewWithBackTitle listview;
    boolean hasdata=true;
    private int currentPage=1;
    private final int RefreshCount=20;
    int mtotalItemCount,mfirstVisibleItem;
    int SelectType=0;
    ArrayList<PaymentBean2> paymentBean2s=new ArrayList<>();
    WithdrawRecordAdapter withdrawRecordAdapter;

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
        return View.inflate(getActivity(), R.layout.fragment_withdraw_record, null);
    }

    private void Initview()
    {
        radiogroup=FindView(R.id.radiogroup);
        radiogroup=FindView(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton1:
                        SelectType = 0;
                        break;
                    case R.id.radiobutton2:
                        SelectType = 1;
                        break;
                    case R.id.radiobutton3:
                        SelectType = 2;
                        break;
                }
                currentPage = 1;
                GetData(true);
            }
        });
        listview=FindView(R.id.listview);
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
        ((RadioButton)radiogroup.getChildAt(0)).setChecked(true);
    }

    public void GetData(final boolean init)
    {
        if(init)
        {
            currentPage=1;
            hasdata=true;
            paymentBean2s.clear();
            SetAdapter();
        }
        hasdata = false;//控制有没有数据和是否运行中
        RequestParams params=new RequestParams();
        params.put("userName", RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername());
//        params.put("beginTime",startdate);//YYYY-MM-DD 2016-09-01
//        params.put("endTime", enddate);//YYYY-MM-DD 2016-10-10
        String tags[]={"today","oneweek","onemonth"};
        params.put("time",tags[SelectType]+"");
        params.put("pageNo",currentPage+"");
        params.put("pageSize", RefreshCount + "");
        Httputils.PostWithBaseUrl(Httputils.WithdrawHistory, params, new MyJsonHttpResponseHandler(getActivity(), false) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("response", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg"));
                    hasdata = false;
                    return;
                }
                listview.onRefreshComplete();
                JSONArray jsonArray = jsonObject.optJSONArray("datas");

                if(init)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata = true;
                    paymentBean2s.clear();
                }

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobj = jsonArray.optJSONObject(i);
                        paymentBean2s.add(PaymentBean2.Analysis(jsobj));
                    }

                    if(jsonArray.length()<RefreshCount)
                    {
                        hasdata=false;
                    }

                    if(paymentBean2s.size()==0)
                    {
                        listview.ShowTextview();
                    }
                    if(  jsonArray.length()<RefreshCount)
                    {
                        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        if(paymentBean2s.size()>=RefreshCount)
                        ToastUtil.showMessage(getActivity(), getString(R.string.completetoast));
                    }
                    SetAdapter();
                    if(paymentBean2s.size()==0)
                    {
                        listview.ShowTextview();
                    }
                    else
                    {
                        listview.ShowListview();
                    }
                }

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String jsonObject) {
                super.onFailureOfMe(throwable, jsonObject);
                hasdata = false;
                listview.onRefreshComplete();
                if(paymentBean2s.size()==0)
                {
                    listview.ShowTextview();
                }
            }

        });
    }

    private void SetAdapter()
    {
        if(withdrawRecordAdapter==null)
        {
            withdrawRecordAdapter=new WithdrawRecordAdapter(getActivity(),paymentBean2s);
            listview.setAdapter(withdrawRecordAdapter);
        }
        else
        {
            withdrawRecordAdapter.Notify(paymentBean2s);
        }

    }
}
