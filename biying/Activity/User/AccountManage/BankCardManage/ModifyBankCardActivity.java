package com.lottery.biying.Activity.User.AccountManage.BankCardManage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.WithDrawBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/5/13.
 */
public class ModifyBankCardActivity extends BaseActivity implements View.OnClickListener{

    TextView unband,modify,bankname,banknumber,bankaddress,password;
    WithDrawBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_bankcard);

        bankname=FindView(R.id.bankname);
        banknumber=FindView(R.id.banknumber);
        bankaddress=FindView(R.id.bankaddress);
        password=FindView(R.id.password);
        unband=FindView(R.id.unband);
        modify=FindView(R.id.modify);
        unband.setOnClickListener(this);
        modify.setOnClickListener(this);
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.bankcardinfo));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        try {
            bean= WithDrawBean.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            bankname.setText(bean.getBankCnName());
            banknumber.setText(bean.getBankCard());
            bankaddress.setText(bean.getBankAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private  void bankremove(){

        if(password.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this,"请输入正确的资金密码");
            return;
        }
        RequestParams params=new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        params.put("id", bean.getId());
        params.put("withdrawPwd", password.getText().toString().trim());
        unband.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.bankremove, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                unband.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                unband.setEnabled(true);
                setResult(BundleTag.ResultCode);
                LogTools.e("jsonObject", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(ModifyBankCardActivity.this, "解绑成功");
                    finish();
                } else {
                    ToastUtil.showMessage(ModifyBankCardActivity.this, jsonObject.optString("msg"));
                }
            }
        });
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private  void bankreupdate(){
        boolean commit=true;
        if(bean.getBankCard().equalsIgnoreCase(banknumber.getText().toString()))
        {
            commit=false;
        }
        else
        {
            if(banknumber.getText().toString().length()<1 || !isInteger(banknumber.getText().toString()))
            {
                ToastUtil.showMessage(this,"请输入正确的银行卡号");
                return;
            }
        }

        if(bankaddress.getText().toString().length()<1)
        {
            ToastUtil.showMessage(this,"请输入开户地址");
            return;
        }

        if(password.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this,"请输入正确的资金密码");
            return;
        }
        RequestParams params=new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        params.put("bankType",bean.getBankType());
        if(commit)
            params.put("bankCard", banknumber.getText().toString().trim());
        else
            params.put("bankCard", "".trim());
        params.put("bankAddress", bankaddress.getText().toString().trim());
        params.put("withdrawPwd", password.getText().toString().trim());
        params.put("id", bean.getId());
        modify.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.bankreupdate, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                modify.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                modify.setEnabled(true);
                setResult(BundleTag.ResultCode);
                LogTools.e("jsonObject", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(ModifyBankCardActivity.this, "修改成功");
                    finish();
                } else
                    ToastUtil.showMessage(ModifyBankCardActivity.this, jsonObject.optString("msg"));

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.modify:
                bankreupdate();
                break;
            case R.id.unband:
                bankremove();
                break;
        }
    }
}
