package com.lottery.biying.Activity.User.WithDraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.WithDrawBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MD5Util;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/9.
 */
public class WithDrawDetailActivity extends BaseActivity implements View.OnClickListener{

    TextView bank, bankname, banknumber, bankaddress, title;
    TextView comit;
    ImageView back;
    EditText jine, mima;
    Intent intent;
    TextView notice;
    double minPay;
    WithDrawBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_detail);
        initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initview() {
        intent = getIntent();
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.withdraw));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        back = (ImageView) FindView(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        notice = FindView(R.id.notice);
        jine = (EditText) FindView(R.id.jine);
        mima = (EditText) FindView(R.id.mima);
        HttpforNoticeinbottom.GetMainPageData(notice, HttpforNoticeinbottom.Tag.withdraw.name, this);

        try {
            bean=WithDrawBean.Analysis_local(new JSONObject(intent.getStringExtra(BundleTag.Data)));
            bank = (TextView) FindView(R.id.bank);
            bank.setText(bean.getBankCnName());

            bankname = (TextView) FindView(R.id.bankname);
            bankname.setText(bean.getUserRealName());

            banknumber = (TextView) FindView(R.id.banknumber);
            banknumber.setText(bean.getBankCard());

            bankaddress = (TextView) FindView(R.id.bankaddress);
            bankaddress.setText(bean.getBankAddress());

            minPay=bean.getMinPay() ;

            jine.setHint(bean.getMinMaxDes());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        comit = (TextView) FindView(R.id.comit);
        comit.setOnClickListener(this);


        mima.setHint("请输入资金密码");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.comit:
                if (jine.getText().toString().length() < 1) {
                    ToastUtil.showMessage(this, "请输入正确的金额");
                    return;
                }
                if (Integer.valueOf(jine.getText().toString()) < minPay) {
                    ToastUtil.showMessage(this, jine.getHint().toString());
                    return;
                }
                if (mima.getText().toString().length() < 1) {
                    ToastUtil.showMessage(this, "请输入正确的资金密码");
                    return;
                }

                comit.setEnabled(false);
                comitwith();
                break;
        }
    }

    private void comitwith() {
        String UserName = RepluginMethod.getApplication(this).getBaseapplicationUsername();
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", UserName);
        requestParams.put("balance", jine.getText().toString().trim());
        requestParams.put("withdrawPwd", mima.getText().toString().trim());
        requestParams.put("bankCode", bean.getId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UserName);
        stringBuilder.append("|");
        stringBuilder.append(jine.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(mima.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(bean.getId());
        requestParams.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));
        Httputils.PostWithBaseUrl(Httputils.Withdraw, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                ToastUtil.showMessage(WithDrawDetailActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    finish();
                }
            }
        });
    }
}
