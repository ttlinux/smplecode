package com.lottery.biying.Activity.User.Proxy.SalaryManage;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/4. 日薪调配
 */
public class RuleofPayoffActivity extends BaseActivity {


    String username;
    RadioGroup radiogroup;
    EditText dailysalary, startfund, betamount, limitedfund;
    TextView confirm, textview_usernanme;
    int Mode=-1;
    RadioButton samode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_payoff);
        Initview();
    }

    private void Initview() {
        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setText("日薪修改");
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        samode=FindView(R.id.samode);
        samode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        textview_usernanme = FindView(R.id.textview_usernanme);
        textview_usernanme.setText(getIntent().getStringExtra(BundleTag.Username));

        username = getIntent().getStringExtra(BundleTag.Username);
        radiogroup = FindView(R.id.radiogroup);

        dailysalary = FindView(R.id.dailysalary);
        dailysalary.setText(getIntent().getStringExtra(BundleTag.DailySalary));

        startfund = FindView(R.id.startfund);
        startfund.setText(getIntent().getStringExtra(BundleTag.StartSalary));

        betamount = FindView(R.id.betamount);
        if(getIntent().getStringExtra(BundleTag.BetAmount)!=null)
        {
            try {
                int m=Integer.valueOf(getIntent().getStringExtra(BundleTag.BetAmount));
                if(m>0)
                    betamount.setText(m+"");
            }
            catch (NumberFormatException ex)
            {
                ex.printStackTrace();
            }
        }


        limitedfund = FindView(R.id.limitedfund);
        if(getIntent().getStringExtra(BundleTag.Limitedfund)!=null)
        {
            try {
                Double m=Double.valueOf(getIntent().getStringExtra(BundleTag.Limitedfund));
                if(m>0)
                    limitedfund.setText(m+"");
            }
            catch (NumberFormatException ex)
            {
                ex.printStackTrace();
            }
        }

        confirm = FindView(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra(BundleTag.id);
                if (id == null || id.length() < 1) {
                    ///没有就调用创建的接口
                    Commit2();
                } else {
                    Commit();
                }
            }
        });
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.require:
                        Mode = 1;
                        break;
                    case R.id.none:
                        Mode = 0;
                        break;
                }
            }
        });
        ((RadioButton)radiogroup.getChildAt(2)).setChecked(true);
        samode.setChecked(true);
    }

    private void Commit() {
        RequestParams requestParams = auth();
        if(requestParams==null)return;
        confirm.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.Salaryupdate, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirm.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirm.setEnabled(true);
                ToastUtil.showMessage(RuleofPayoffActivity.this, jsonObject.optString("msg", ""));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                } else {
                    setResult(BundleTag.ResultCode);
                    finish();
                }

            }
        });
    }



    private RequestParams auth() {
        RequestParams requestParams;

        if(!samode.isChecked())
        {
            ToastUtil.showMessage(this, "请选择发放模式");
            return null;
        }
        if(Mode<0)
        {
            ToastUtil.showMessage(this, "请选择亏损要求");
            return null;
        }
        try {
            double vaule=Double.valueOf(dailysalary.getText().toString());
            if(vaule==0)
            {
                ToastUtil.showMessage(this, "日薪金额需大于0");
                return null;
            }
        } catch (NumberFormatException ex) {
            ToastUtil.showMessage(this, "请输入日薪金额");
            return null;
        }

        try {
            Double.valueOf(startfund.getText().toString());
        } catch (NumberFormatException ex) {
            ToastUtil.showMessage(this, "请输入起始金额");
            return null;
        }
        if(betamount.getText().toString().length()>0) {
            try {
                Integer.valueOf(betamount.getText().toString());
            } catch (NumberFormatException ex) {
                ToastUtil.showMessage(this, "请正确输入投注人数");
                return null;
            }
        }

        if(limitedfund.getText().toString().length()>0)
        {
            try {
                Double.valueOf(limitedfund.getText().toString());
            } catch (NumberFormatException ex) {
                ToastUtil.showMessage(this, "请正确输入封顶金额");
                return null;
            }
        }



        requestParams = new RequestParams();

        int personCount = 0;
        double maxMoney = 0;
        try {
            personCount = betamount.getText().toString().length() == 0 ? 0 : Integer.valueOf(betamount.getText().toString());
            maxMoney = limitedfund.getText().toString().length() == 0 ? 0 : Double.valueOf(limitedfund.getText().toString());
            Double.valueOf(startfund.getText().toString());
            Double.valueOf(dailysalary.getText().toString());
        } catch (NumberFormatException ex) {
            ToastUtil.showMessage(this, "请正确填写");
        }

        if (username != null && username.length() > 0) {
            requestParams.put("account", username);
        }
        requestParams.put("fangshi", "0");
        requestParams.put("loss", Mode + "");
        requestParams.put("startMoney", startfund.getText().toString());
        requestParams.put("salaryMoney", dailysalary.getText().toString());
        requestParams.put("personCount", personCount + "");
        requestParams.put("maxMoney", String.format("%.2f", maxMoney));
        requestParams.put("id", getIntent().getStringExtra(BundleTag.id));
        return requestParams;
    }


    private RequestParams auth2()
    {
        RequestParams requestParams;

        if(!samode.isChecked())
        {
            ToastUtil.showMessage(this, "请选择发放模式");
            return null;
        }
        if(Mode<0)
        {
            ToastUtil.showMessage(this, "请选择亏损要求");
            return null;
        }

        try {
            double vaule=Double.valueOf(dailysalary.getText().toString());
            if(vaule==0)
            {
                ToastUtil.showMessage(this, "日薪金额需大于0");
                return null;
            }
        } catch (NumberFormatException ex) {
            ToastUtil.showMessage(this, "请输入日薪金额");
            return null;
        }

        try {
            Double.valueOf(startfund.getText().toString());
        } catch (NumberFormatException ex) {
            ToastUtil.showMessage(this, "请输入起始金额");
            return null;
        }

        if(betamount.getText().toString().length()>0) {
            try {
                Integer.valueOf(betamount.getText().toString());
            } catch (NumberFormatException ex) {
                ToastUtil.showMessage(this, "请正确输入投注人数");
                return null;
            }
        }

        if(limitedfund.getText().toString().length()>0)
        {
            try {
                Double.valueOf(limitedfund.getText().toString());
            } catch (NumberFormatException ex) {
                ToastUtil.showMessage(this, "请正确输入封顶金额");
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
            ToastUtil.showMessage(this,"请正确填写");
        }

        requestParams.put("startMoney",startfund.getText().toString());
        requestParams.put("account",username);
        requestParams.put("salaryMoney",dailysalary.getText().toString());
        requestParams.put("fangshi","0");
        requestParams.put("personCount",personCount+"");
        requestParams.put("maxMoney",String.format("%.2f",maxMoney));
        requestParams.put("loss",Mode + "");
        return  requestParams;
    }

    private void Commit2()
    {
        RequestParams requestParams=auth2();
        if(requestParams==null)return;
        confirm.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.NewSubordinateSalary,requestParams,new MyJsonHttpResponseHandler(this,true)
        {

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
                ToastUtil.showMessage(RuleofPayoffActivity.this, jsonObject.optString("msg", ""));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                setResult(BundleTag.ResultCode);
                finish();
            }
        });

    }
}
