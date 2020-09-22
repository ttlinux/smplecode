package com.lottery.biying.Activity.User.Proxy.SalaryManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/3. 新增下级日薪
 */
public class NewSubordinateSalaryFragment extends BaseFragment{

    EditText username,dailysalary,startfund,betamount,limitedfund;
    TextView confirm,notice;
    RadioGroup group_spend;
    int mode=-1;
    RadioButton salarymode;


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
        return View.inflate(getActivity(), R.layout.fragment_new_subordinate_salary,null);
    }

    private void InitView()
    {
        notice=FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice,HttpforNoticeinbottom.Tag.xjrx.name,getActivity());
        salarymode=FindView(R.id.salarymode);
        confirm=FindView(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commit();
            }
        });
        username=FindView(R.id.username);
        dailysalary=FindView(R.id.dailysalary);
        startfund=FindView(R.id.startfund);
        betamount=FindView(R.id.betamount);
        limitedfund=FindView(R.id.limitedfund);
        group_spend=FindView(R.id.group_spend);
        group_spend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.require:
                        mode=1;
                        break;
                    case R.id.none:
                        mode=0;
                        break;
                }
            }
        });
        ((RadioButton)group_spend.getChildAt(2)).setChecked(true);
        salarymode.setChecked(true);
    }

    private void Commit()
    {
        RequestParams requestParams=auth();
        if(requestParams==null)return;
        confirm.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.NewSubordinateSalary, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirm.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jjj", jsonObject.toString());
                confirm.setEnabled(true);
                ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                ResetView();
                ((SalaryManageActivity) getActivity()).SetClick(2);
            }
        });

    }
    private RequestParams auth()
    {
        RequestParams requestParams;

        if(username.getText().toString().length()<4)
        {
            ToastUtil.showMessage(getActivity(),"请输入4-16位数字/字母或组合的帐号");
            return null;
        }
        if(!salarymode.isChecked())
        {
            ToastUtil.showMessage(getActivity(),"请选择发放模式");
            return null;
        }
        if(mode<0)
        {
            ToastUtil.showMessage(getActivity(),"请选择亏损要求");
            return null;
        }

        try {
            double vaule=Double.valueOf(dailysalary.getText().toString());
            if(vaule==0)
            {
                ToastUtil.showMessage(getActivity(), "日薪金额需大于0");
                return null;
            }
        } catch (NumberFormatException ex) {
            ToastUtil.showMessage(getActivity(), "请输入日薪金额");
            return null;
        }

        try {
            Double.valueOf(startfund.getText().toString());
        } catch (NumberFormatException ex) {
            ToastUtil.showMessage(getActivity(), "请输入起始金额");
            return null;
        }

        if(betamount.getText().toString().length()>0) {
            try {
                Integer.valueOf(betamount.getText().toString());
            } catch (NumberFormatException ex) {
                ToastUtil.showMessage(getActivity(), "请正确输入投注人数");
                return null;
            }
        }

        if(limitedfund.getText().toString().length()>0)
        {
            try {
                Double.valueOf(limitedfund.getText().toString());
            } catch (NumberFormatException ex) {
                ToastUtil.showMessage(getActivity(), "请正确输入封顶金额");
                return null;
            }
        }

        requestParams=new RequestParams();

        int personCount=0;
        double maxMoney=0;
        try
        {
             personCount=betamount.getText().toString().length()==0?0:Integer.valueOf(betamount.getText().toString());
             maxMoney=limitedfund.getText().toString().length()==0?0:Double.valueOf(limitedfund.getText().toString());
        }
        catch (NumberFormatException ex)
        {
            ToastUtil.showMessage(getActivity(),"请正确填写");
        }

        requestParams.put("startMoney",startfund.getText().toString());
        requestParams.put("account",username.getText().toString());
        requestParams.put("salaryMoney",dailysalary.getText().toString());
        requestParams.put("fangshi","0");
        requestParams.put("personCount",personCount+"");
        requestParams.put("maxMoney",String.format("%.2f",maxMoney));
        requestParams.put("loss",mode+"");
        return  requestParams;
    }

    private void ResetView()
    {
        username.setText("");
        dailysalary.setText("");
        startfund.setText("");
        limitedfund.setText("");
        betamount.setText("");
        ((RadioButton)group_spend.getChildAt(2)).setChecked(true);
        mode=1;
        salarymode.setChecked(true);
    }
}
