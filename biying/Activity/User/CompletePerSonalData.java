package com.lottery.biying.Activity.User;

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
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/17.
 */
public class CompletePerSonalData extends BaseActivity {

    EditText fundpassword,confirmpassword,bankusername;
    TextView confirm,notice;
    String Classname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_personal_data);

        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.complete));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        confirm=FindView(R.id.confirm);
        notice=FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice,HttpforNoticeinbottom.Tag.wszl.name,this);
        fundpassword=FindView(R.id.fundpassword);
        confirmpassword=FindView(R.id.confirmpassword);
        bankusername=FindView(R.id.bankusername);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commit();
            }
        });
        Classname=getIntent().getStringExtra(BundleTag.Classname);
    }

    private void Commit()
    {
        if(fundpassword.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this,"请输入4位数字作为资金密码");
            return;
        }
        if(confirmpassword.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this,"请输入4位数字的确认密码");
            return;
        }
        if(!fundpassword.getText().toString().equalsIgnoreCase(confirmpassword.getText().toString()))
        {
            ToastUtil.showMessage(this,"两次输入的资金密码不一致");
            return;
        }
        if(bankusername.getText().toString().length()==0)
        {
            ToastUtil.showMessage(this,"请输入提款银行卡姓名");
            return;
        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("zjPwd",fundpassword.getText().toString());
        requestParams.put("realName",bankusername.getText().toString());

        confirm.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.CompleteData,requestParams,new MyJsonHttpResponseHandler(this,true)
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
                ToastUtil.showMessage(CompletePerSonalData.this,jsonObject.optString("msg"));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    return;
                }
                JSONObject UserInfo= null;
                try {
                    SharedPreferences sharedPreferences=RepluginMethod.getHosttSharedPreferences(CompletePerSonalData.this);
                    UserInfo = new JSONObject(sharedPreferences.getString(BundleTag.UserInfo, ""));
                    UserInfo.put("hasRealName",true);
                    UserInfo.put("hasWithdrawPwd",true);
                    sharedPreferences.edit().putString(BundleTag.UserInfo,UserInfo.toString()).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(Classname!=null && Classname.length()>0)
                {
                    Intent intent=new Intent();
                    try {
                        intent.setClass(CompletePerSonalData.this,Class.forName(Classname));
                        intent.putExtra(BundleTag.Username,getIntent().getStringExtra(BundleTag.Username));
                        startActivityForResult(intent, BundleTag.RequestCode);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                setResult(BundleTag.CompleteData);
                finish();
            }
        });
    }
}
