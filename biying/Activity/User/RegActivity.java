package com.lottery.biying.Activity.User;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.WelcomeActivity;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.MainActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.ModuleBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.ImageDownLoader;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.PublicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/3/30.
 */
public class RegActivity extends BaseActivity implements View.OnClickListener{
    TextView regtext,name,password,confirmpassword,notice,invitecode;
    String staterecord;
    public static long autime=0;
    Button comit;
    ImageView back;
    LinearLayout mainview;
    ArrayList< ArrayList<ModuleBean>> gameList=new ArrayList< ArrayList<ModuleBean>>();
    CheckBox checkBox;
    PublicDialog publicDialog;
    SharedPreferences usernamelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        setIsneedback(true);
        initview();
    }

    private void initview(){
        usernamelist=RepluginMethod.getHostApplication(this).getSharedPreferences(BundleTag.UserNameList, Activity.MODE_PRIVATE);
        name=FindView(R.id.name);
        password=FindView(R.id.password);
        invitecode=FindView(R.id.invitecode);
        confirmpassword=FindView(R.id.confirmpassword);
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.reg_name));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        comit=FindView(R.id.comit);
        comit.setOnClickListener(this);
        checkBox=FindView(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    comit.setEnabled(false);
                    comit.setBackground(getResources().getDrawable(R.drawable.btn_conner_noradiu_gray20));
                }
                else
                {
                    comit.setEnabled(true);
                    comit.setBackground(getResources().getDrawable(R.drawable.radius_imageview));
                }
            }
        });
        regtext=FindView(R.id.regtext);
        regtext.setOnClickListener(this);

        notice=FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice, HttpforNoticeinbottom.Tag.register.name, this);
        mainview=FindView(R.id.mainview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comit:
                Register();
                break;
            case R.id.regtext:
                regtext.setEnabled(false);
                startActivity(new Intent(this, regwebActivity.class));
                regtext.setEnabled(true);
                break;
            case R.id.back:
                if (MainActivity.isexit != 2) {
                    Intent intent2 = new Intent(this, WelcomeActivity.class);
                    intent2.setAction(Intent.ACTION_MAIN);
                    intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    finish();
                }
                else {
                    finish();
                }
                break;
        }

    }

    public void Register()
    {
        if(!checkBox.isChecked())
        {
            ToastUtil.showMessage(this, getString(R.string.confirmdetail));
            return;
        }
        if(name.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this, "请输入4-16位数字/字母或组合作为帐号");
            return;
        }
        if(password.getText().toString().length()<6)
        {
            ToastUtil.showMessage(this, "请输入6-16位数字/字母或组合作为密码");
            return;
        }
        if(confirmpassword.getText().toString().length()<6)
        {
            ToastUtil.showMessage(this, "两次密码输入不一致");
            return;
        }
        if(!confirmpassword.getText().toString().equalsIgnoreCase(password.getText().toString()))
        {
            ToastUtil.showMessage(this, "两次密码输入不一致");
            return;
        }

        comit.setEnabled(false);
//        comit.setBackground(getResources().getDrawable(R.drawable.btn_conner_noradiu_gray20));
        RequestParams params=new RequestParams();
        params.put("account",name.getText().toString());
        params.put("password",password.getText().toString());
        params.put("code",invitecode.getText().toString());

        Httputils.PostWithBaseUrl(Httputils.register, params, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                comit.setBackground(getResources().getDrawable(R.drawable.radius_imageview));

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                ToastUtil.showMessage(RegActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    authorize(name.getText().toString(),password.getText().toString());
                }
                else
                {
                    comit.setEnabled(true);
                    comit.setBackground(getResources().getDrawable(R.drawable.radius_imageview));
                }
            }
        });
    }

    private void authorize(final String username,final String password)
    {
        ToastUtil.showMessage(this,"正在登陆");
        if(publicDialog==null)
        {
            publicDialog=new PublicDialog(this);
        }
        publicDialog.show();
        staterecord= UUID.randomUUID().toString();
        LogTools.e("staterecord", staterecord);
        RequestParams params = new RequestParams();
        params.put("userName", name.getText().toString().trim());
        params.put("clientKey", Httputils.androidkey);//keys[0] /*unchange(keys[0])*/
        params.put("clientSecret", Httputils.androidsecret);//unchange(keys[1])  Httputils.androidsecret
        params.put("deviceId", android.os.Build.SERIAL);
        try {
            params.put("state", staterecord);//des加密就用这个 DesUtil.encrypt(staterecord,keys[2])
        } catch (Exception e) {
            e.printStackTrace();
        }

        Httputils.PostWithBaseUrl(Httputils.authorize, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                comit.setBackground(getResources().getDrawable(R.drawable.radius_imageview));
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("Throwable", s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject tempjsobject = jsonObject.optJSONObject("datas");
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000") && staterecord.equalsIgnoreCase(tempjsobject.optString("state", ""))) {
                    autime = Long.valueOf(tempjsobject.optString("expires_in", ""));
                    String access_token = tempjsobject.optString("access_token", "");
                    SharedPreferences sharedPreferences = RepluginMethod.getHosttSharedPreferences(RegActivity.this);
                    sharedPreferences.edit().putString(BundleTag.Access_token, access_token).commit();
                    Login(username, password, access_token);
                } else {
                    ToastUtil.showMessage(RegActivity.this, jsonObject.optString("msg", ""));
                    if (publicDialog != null && !isDestroyed()) {
                        publicDialog.dismiss();
                    }
                    comit.setEnabled(true);
                    comit.setBackground(getResources().getDrawable(R.drawable.radius_imageview));
                }
            }
        });
    }


    private void Login(final String username,String password,String access_token)
    {
        RequestParams params=new RequestParams();
        params.put("accessToken",access_token);
        params.put("password",password);
        params.put("userName",username);
        params.put("loginType", "1");
        Httputils.PostWithBaseUrl(Httputils.login,params,new MyJsonHttpResponseHandler(this,true){
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                comit.setBackground(getResources().getDrawable(R.drawable.radius_imageview));
                if(publicDialog!=null && !isDestroyed())
                {
                    publicDialog.dismiss();
                }
                LogTools.e("onFailureOfMe","fail");
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject datas=jsonObject.optJSONObject("datas").optJSONObject("userInfo");
                if(publicDialog!=null && !isDestroyed())
                {
                    publicDialog.dismiss();
                }
                ToastUtil.showMessage(RegActivity.this,jsonObject.optString("msg",""));
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    SharedPreferences sharedPreferences=RepluginMethod.getHostApplication(RegActivity.this).getSharedPreferences(BundleTag.Lottery, MODE_PRIVATE);
                    RepluginMethod.getApplication(RegActivity.this).setUsername(datas.optString("userName", ""));
                    RepluginMethod.getApplication(RegActivity.this).setisgent(datas.optString("isAgent", ""));
                    sharedPreferences.edit()
//                            .putString(BundleTag.level, datas.optJSONObject("typeDetail").toString())
                            .putString(BundleTag.UserInfo, datas.toString())
//                    .putString(BundleTag.BankList,datas.optJSONArray("bankList").toString())
                            .commit();

                    Application app=RepluginMethod.getHostApplication(RegActivity.this);
                    Field f = null;
                    try {
                        f = app.getClass().getDeclaredField("Username");
                        f.setAccessible(true);
                        f.set(app, datas.optString("userName", ""));
                        LogTools.e("Username","修改成功");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    String namelist=usernamelist.getString(BundleTag.UserNameList,"");
                    if(!namelist.contains(username))
                    {
                        String middle=namelist.equalsIgnoreCase("")?"":"|";
                        String username=namelist+middle+name.getText().toString().trim();
                        if(username.split("\\|").length>4)
                        {
                            username=username.substring(username.indexOf("|", 2)+1,username.length());
                        }
                        usernamelist.edit().putString(BundleTag.UserNameList,username).commit();
                    }

                    Intent intent=new Intent();
                    intent.putExtra(BundleTag.Username,datas.optString("userName", ""));
                    setResult(BundleTag.LoginSuccess,intent);

//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.setClass(context, RepluginMethod.getHostMainclass(RegActivity.this));
//                    startActivity(intent);
                    finish();
                }
                else
                {
                    if (publicDialog != null && !isDestroyed()) {
                        publicDialog.dismiss();
                    }
                    comit.setEnabled(true);
                    comit.setBackground(getResources().getDrawable(R.drawable.radius_imageview));
//                    authorize();
//                    ToastUtil.showMessage(LoginActivity.this, jsonObject.optString("msg", ""));
                }
            }
        });
    }

}
