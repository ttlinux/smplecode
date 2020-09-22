package com.lottery.biying.Activity.User.WithDraw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.util.verifyEditext;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/9. 绑定银行卡
 */
public class BandBankCardActivity extends BaseActivity {

    TextView bankusername, banktype, confirm,notice;
    EditText bankcard, bankaddress, fundpassword;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_bankcard);


        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.bandbankcard));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        banktype = FindView(R.id.banktype);
        banktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                startActivityForResult(new Intent(BandBankCardActivity.this, WithDrawBankListActivty.class), BundleTag.RequestCode);
                v.setEnabled(true);
            }
        });
        bankcard = FindView(R.id.bankcard);
        bankaddress = FindView(R.id.bankaddress);
        fundpassword = FindView(R.id.fundpassword);
        bankusername = FindView(R.id.bankusername);
        notice=FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice,HttpforNoticeinbottom.Tag.bank.name,this);
        confirm = FindView(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindBank(v);
            }
        });
         sharedPreferences = RepluginMethod.getHostApplication(BandBankCardActivity.this).getSharedPreferences(BundleTag.Lottery, MODE_PRIVATE);

        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(sharedPreferences.getString(BundleTag.UserInfo,""));
            UserInfoBean bean=UserInfoBean.Analysis_local(jsonObject);
            if(bean==null){
                getuserinfo();
                return;
            }
            bankusername.setText(bean.getUserRealName());
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    /*{
"bankCnName": "京东",
"bankCode": "jdpay",
"bigPicUrl": "http:\/\/res.6820168.com\/share\/bankicon\/1521830572751.jpg",
"isBank": 1,
"smallPicUrl": "http:\/\/res.6820168.com\/share\/bankicon\/1521830572751.jpg"
}*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BundleTag.ResultCode) {
            try {
                JSONObject jsonObject=new JSONObject(data.getStringExtra(BundleTag.Data));
                banktype.setText(jsonObject.optString("bankCnName",""));
                banktype.setTag(jsonObject.optString("bankCode",""));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void BindBank(final View v) {
        if (!verifyEditext.getInstance().BankCard(this, banktype.getText().toString(),
                bankcard.getText().toString(),
                bankaddress.getText().toString(),
                fundpassword.getText().toString())) {
            return;
        }

        RequestParams params = new RequestParams();

        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());

        params.put("bankType", String.valueOf(banktype.getTag()));
        params.put("bankCard", bankcard.getText().toString());
        params.put("bankAddress", bankaddress.getText().toString());
        params.put("withdrawPwd", fundpassword.getText().toString());
        v.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.BindBank, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                v.setEnabled(true);
                ToastUtil.showMessage(BandBankCardActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    setResult(BundleTag.ResultCode);
                    finish();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                v.setEnabled(true);
            }
        });
    }

    private void getuserinfo() {
        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.UserInfo, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONObject datas = jsonObject.optJSONObject("datas").optJSONObject("userInfo");
                    sharedPreferences.edit()
                            .putString(BundleTag.UserInfo, datas.toString())
                            .commit();
                    UserInfoBean bean=UserInfoBean.Analysis_local(datas);
                    bankusername.setText(bean.getUserRealName());
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });

    }
}
