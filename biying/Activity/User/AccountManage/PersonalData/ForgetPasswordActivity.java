package com.lottery.biying.Activity.User.AccountManage.PersonalData;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/17.
 */
public class ForgetPasswordActivity extends BaseActivity {

    TextView confirm;
    EditText newpassword,confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setText(getString(R.string.forgetpassword));
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        confirm=FindView(R.id.confirm);
        newpassword=FindView(R.id.newpassword);
        confirmpassword=FindView(R.id.confirmpassword);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commit();
            }
        });
    }

    private void Commit()
    {
        if(newpassword.getText().toString().length()<6)
        {
            ToastUtil.showMessage(this,"请输入6-16位数字/字母或组合的新密码");
            return;
        }
        if(confirmpassword.getText().toString().length()<6)
        {
            ToastUtil.showMessage(this,"请输入6-16位数字/字母或组合的确认新密码");
            return;
        }
        if(!confirmpassword.getText().toString().equalsIgnoreCase(newpassword.getText().toString()))
        {
            ToastUtil.showMessage(this,"两次密码输入不一致");
            return;
        }
        confirm.setEnabled(false);
        RequestParams requestParams=new RequestParams();
        requestParams.put("pwd",confirmpassword.getText().toString());
        requestParams.put("key",getIntent().getStringExtra(BundleTag.AuthCode));
        requestParams.put("account",getIntent().getStringExtra(BundleTag.Username));
        Httputils.PostWithBaseUrl(Httputils.ResetPassword,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirm.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirm.setEnabled(true);
                ToastUtil.showMessage(ForgetPasswordActivity.this, jsonObject.optString("msg", ""));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                finish();
            }
        });
    }
}
