package com.lottery.biying.Activity.User.AccountManage.PersonalData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/5/16.
 */
public class BandPhoneActivity extends BaseActivity {

    TextView getauth, confirm,phonetag,notice;
    EditText phonenumber, authnumber,forget_phone;
    RelativeLayout forgetpassward_layout;
    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    getauth.setEnabled(false);
                    getauth.setBackground(getResources().getDrawable(R.drawable.btn_conner_noradiu_gray20));
                    getauth.setText(TimeCount - count + "秒后重新获取");
                    count++;
                    if (count == 61) {
                        getauth.setEnabled(true);
                        getauth.setBackground(getResources().getDrawable(R.drawable.btn_conner_noradiu_loss));
                        getauth.setText("获取验证码");
                        count=1;
                    } else {
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
            }
        }
    };
    static int count = 1;
    static int TimeCount = 60;

    int type = 0;// 0 个人信息入口  1 忘记密码入口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_phone);
        forgetpassward_layout=FindView(R.id.forgetpassward_layout);
        forget_phone=FindView(R.id.forget_phone);
        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        confirm = FindView(R.id.confirm);
        getauth = FindView(R.id.getauth);
        notice=FindView(R.id.notice);
        phonenumber = FindView(R.id.phonenumber);
        authnumber = FindView(R.id.authnumber);
        phonetag=FindView(R.id.phonetag);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    Bandphone();
                } else {
                    VerifyCode();
                }
            }
        });
        getauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendAuthCode();
            }
        });
        type = getIntent().getIntExtra(BundleTag.Type, 0);
        switch (type) {
            case 0:
                backtitle.setText(getString(R.string.bandphone));
                confirm.setText("确认绑定");
                phonenumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                HttpforNoticeinbottom.GetMainPageData(notice, HttpforNoticeinbottom.Tag.bdsj.name, this);
                break;
            case 1:
                forgetpassward_layout.setVisibility(View.VISIBLE);
                HttpforNoticeinbottom.GetMainPageData(notice,HttpforNoticeinbottom.Tag.forgetpwd.name,this);
                backtitle.setText(getString(R.string.forgetpassword));
                confirm.setText("下一步");
                phonetag.setText("用户名");
                phonenumber.setHint("请输入用户名");
                phonenumber.setInputType(InputType.TYPE_CLASS_TEXT);
                phonenumber.setFilters(new InputFilter[]{
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int dstart, int dend) {
                                if (charSequence.length() == 0) return null;
                                String regex = "^[\u4E00-\u9FA5]+$";
                                boolean isChinese = Pattern.matches(regex, charSequence.toString());
                                if (!Character.isLetterOrDigit(charSequence.charAt(start)) || isChinese) {
                                    return "";
                                }
                                return null;
                            }
                        }, new InputFilter.LengthFilter(16)
                });
                break;
        }

    }


    private void Bandphone() {
        if (phonenumber.getText().toString().length() == 0) {
            ToastUtil.showMessage(this, "请输入正确的手机号码");
            return;
        }
        if (authnumber.getText().toString().length() == 0) {
            ToastUtil.showMessage(this, "请输入正确的验证码");
            return;
        }
        confirm.setEnabled(false);
        RequestParams requestParams = new RequestParams();
        requestParams.put("mobile", phonenumber.getText().toString());
        requestParams.put("code", authnumber.getText().toString());
        Httputils.PostWithBaseUrl(Httputils.bindmobile, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirm.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirm.setEnabled(true);
                ToastUtil.showMessage(BandPhoneActivity.this, jsonObject.optString("msg", ""));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                setResult(BundleTag.BandPhone);
                count=1;
                finish();

            }
        });
    }

    private void SendAuthCode() {
        if(type==0)
        {
            if(phonenumber.getText().toString().length() <11)
            {
                ToastUtil.showMessage(this, "请输入正确的手机号码");
                return;
            }

        }
        else
        {
            //忘记密码入口
            if(phonenumber.getText().toString().length() <4)
            {
                ToastUtil.showMessage(this, "请输入4-16位数字/字母或组合的帐号");
                return;
            }
        }
        getauth.setEnabled(false);
        RequestParams requestParams = new RequestParams();
        if(type==0)
        {
            requestParams.put("phone", phonenumber.getText().toString());
        }
        else
        {
            //忘记密码入口
            requestParams.put("account",phonenumber.getText().toString());
            requestParams.put("phone", forget_phone.getText().toString());
        }

        requestParams.put("businessType", type == 0 ? "3" : "2");
        Httputils.PostWithBaseUrl(Httputils.Sendsms, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                getauth.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jjjj", jsonObject.toString());
                getauth.setEnabled(true);
                ToastUtil.showMessage(BandPhoneActivity.this, jsonObject.optString("msg", ""));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                getauth.setEnabled(false);
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
    }

    private void VerifyCode()
    {
        if (phonenumber.getText().toString().length() <4) {
            ToastUtil.showMessage(this, "请输入4-16位数字/字母或组合作为帐号");
            return;
        }
        if (authnumber.getText().toString().length() == 0) {
            ToastUtil.showMessage(this, "请输入正确的验证码");
            return;
        }
        if(forget_phone.getText().toString().length()<11)
        {
            ToastUtil.showMessage(this, "请输入正确的手机号码");
            return;
        }

        confirm.setEnabled(false);
        RequestParams requestParams=new RequestParams();
        requestParams.put("code",authnumber.getText().toString());
        requestParams.put("account",phonenumber.getText().toString());
        requestParams.put("phone", forget_phone.getText().toString());
        Httputils.PostWithBaseUrl(Httputils.VerifyPassword,requestParams,new MyJsonHttpResponseHandler(this,true)
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
                LogTools.e("VerifyCode",jsonObject.toString());
                ToastUtil.showMessage(BandPhoneActivity.this, jsonObject.optString("msg", ""));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(BandPhoneActivity.this, ForgetPasswordActivity.class);
                intent.putExtra(BundleTag.AuthCode, jsonObject.optJSONObject("datas").optString("key",""));
                intent.putExtra(BundleTag.Username, phonenumber.getText().toString());
                startActivity(intent);
                count=1;
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(count>1)
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }


}
