package com.lottery.biying.Activity.User;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.AccountManage.PersonalData.BandPhoneActivity;
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
import com.lottery.biying.util.LoginRecord;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.GestureLockViewGroup;
import com.lottery.biying.view.PublicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/3/30.登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText password, name, code;
    TextView register,gesturepassword,forgetpassward;
    Button comit;
    CheckBox remeberpassword;
    ImageView back;
    String staterecord;
    public static long autime = 0;
    public PublicDialog publicDialog;
    TextView notice;
    String loginType = "1";
    SharedPreferences usernamelist;
    String arr[] = null;
    PopupWindow popwindow;
    private String Record[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.login);
        initview();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==BundleTag.LoginSuccess)
        {
            setResult(BundleTag.LoginSuccess, data);
            finish();
        }
    }

    private void initview() {
        remeberpassword=FindView(R.id.remeberpassword);
        register=FindView(R.id.register);
        gesturepassword=FindView(R.id.gesturepassword);
        forgetpassward=FindView(R.id.forgetpassward);
        register.setOnClickListener(this);
        gesturepassword.setOnClickListener(this);
        forgetpassward.setOnClickListener(this);
        usernamelist = RepluginMethod.getHostApplication(this).getSharedPreferences(BundleTag.UserNameList, Activity.MODE_PRIVATE);
        back = (ImageView) FindView(R.id.back);
        back.setOnClickListener(this);
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.userlogin));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        name = FindView(R.id.name);
//        name.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN && arr != null) {
//                    openPopView((TextView) v, arr);
//                }
//                return false;
//            }
//        });

        String namelist = usernamelist.getString(BundleTag.UserNameList, "");
        LogTools.e("namelist", namelist);
        if (!namelist.equalsIgnoreCase("")) {
            String[] temp = namelist.split("\\|");
            arr = new String[temp.length + 1];
            for (int q = 0; q < arr.length - 1; q++) {
                arr[q] = temp[q];
            }
            arr[arr.length - 1] = "清除历史记录";
        }
        password = (EditText) FindView(R.id.password);
        code = (EditText) FindView(R.id.code);
        comit = (Button) FindView(R.id.comit);
        comit.setOnClickListener(this);
        notice = FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice,HttpforNoticeinbottom.Tag.login.name,this);
        if (autime == 0) {
            autime = System.currentTimeMillis() / 1000;
        }

        Record= LoginRecord.getReocrd();
        LogTools.e("Record",Record+" "+" nnnsadsad");
        if(Record!=null)
        {
            name.setText(Record[0]);
            password.setText(getString(R.string.placehold));
            remeberpassword.setChecked(true);
        }
    }

    private void openPopView(final TextView view, final String strs[]) {
        LogTools.e("strs length", strs.length + " " + view.getWidth());
        if (popwindow == null) {
            int width = view.getWidth();
            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams laypa = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(laypa);
            ll.setBackground(getResources().getDrawable(R.drawable.rangle_noradiu_backgroud));

            for (int i = 0; i < strs.length; i++) {
                ImageView imageview = new ImageView(this);
                LinearLayout.LayoutParams imagell = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                imageview.setLayoutParams(imagell);
                imageview.setBackgroundColor(getResources().getColor(R.color.line));

                TextView textview = new TextView(this);
                LinearLayout.LayoutParams tvll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textview.setText(strs[i]);
                textview.setLayoutParams(tvll);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textview.setPadding(ScreenUtils.getDIP2PX(this, 10), ScreenUtils.getDIP2PX(this, 4), 0, ScreenUtils.getDIP2PX(this, 4));
                textview.setGravity(Gravity.LEFT);
                textview.setTag(i);
                textview.setFocusable(true);
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closePopView();
                        int index = Integer.valueOf(v.getTag() + "");
                        LogTools.e("tvvvvv", index + "");
                        if (index < arr.length - 1) {
                            TextView tv = (TextView) v;
                            name.setText(tv.getText().toString());
                        } else {
                            usernamelist.edit().clear().commit();
                            arr = null;
                            popwindow = null;
                        }

                    }
                });

                ll.addView(imageview);
                ll.addView(textview);
            }
            popwindow = new PopupWindow(ll, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popwindow.setWidth(width);
            popwindow.setFocusable(false);
            popwindow.setOutsideTouchable(true);
            popwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            popwindow.setContentView(ll);
        }
        popwindow.update();
        popwindow.showAsDropDown(view);
//        popwindow.showAsDropDown(view,0,0);
    }

    private void closePopView() {
        if (popwindow != null) {
            popwindow.dismiss();
        }
    }

    public static void KeyBoard(final EditText txtSearchKey, final boolean status, int second) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (m.isActive()) {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, second);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comit:
                if (name.getText().toString().length() < 4) {
                    ToastUtil.showMessage(this, "请输入4-16位数字/字母或组合的帐号");
                    return;
                }
                if (password.getText().toString().length() < 6) {
                    ToastUtil.showMessage(this, "请输入6-16位数字/字母或组合的密码");
                    return;
                }
                authorize();
                break;
            case R.id.back:
                if (MainActivity.isexit != 2) {
                    Intent intent2 = new Intent(LoginActivity.this, WelcomeActivity.class);
                    intent2.setAction(Intent.ACTION_MAIN);
                    intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    finish();
                } else {
                    setResult(112);
                    finish();
                }
                break;
            case R.id.register:
                startActivityForResult(new Intent(this, RegActivity.class), BundleTag.RequestCode);
                break;
            case R.id.forgetpassward:
                Intent intent=new Intent();
                intent.setClass(this, BandPhoneActivity.class);
                intent.putExtra(BundleTag.Type,1);
                startActivity(intent);
                break;
            case R.id.gesturepassword:
                Intent intent2=new Intent();
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.setClass(this, GestureLoginActivity.class);
                startActivityForResult(intent2, BundleTag.RequestCode);
                break;
        }
    }

    private void authorize() {
        if (publicDialog == null) {
            publicDialog = new PublicDialog(this);
        }
        publicDialog.show();
        staterecord = UUID.randomUUID().toString().trim();
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
        comit.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.authorize, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("Throwable", s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject tempjsobject = jsonObject.optJSONObject("datas");
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000") ) {
                    autime = Long.valueOf(tempjsobject.optString("expires_in", ""));
                    String access_token = tempjsobject.optString("access_token", "");
                    SharedPreferences sharedPreferences = RepluginMethod.getHosttSharedPreferences(LoginActivity.this);
                    sharedPreferences.edit().putString(BundleTag.Access_token, access_token).commit();
                    Login(access_token);
                } else {
                    ToastUtil.showMessage(LoginActivity.this, jsonObject.optString("msg", ""));
                    comit.setEnabled(true);
                }
            }
        });
    }

    //    "datas": {
//        "userInfo": {
//                     "unReadMsg": 0,
//                    "userLastLoginTime": "2017-04-04 19:42:31",
//                    "userMobile": "18707508894",
//                    "userQq": "877149698",
//                    "userName": "4318471pk",
//                    "realName": "",
//                    "userMoney": 0,
//                    "userLastLoginIp": "127.0.0.1",
//                    "userMail": ""
//        },
//        "bankList": [
//
//        ],
//        "level": {
//            "levelName": "普通会员",
//                    "levelUrl": "\/m\/site\/e0\/member\/vip.png"
//        }
//    },
    private void Login(String access_token) {
        if (name.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, getString(R.string.plzfillinname));
            return;
        }
        if (password.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, getString(R.string.plzfillinpassword));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("accessToken", access_token);
        String passwordstr=password.getText().toString().trim();
        if(password.getText().toString().equalsIgnoreCase(getString(R.string.placehold)))
        {
            passwordstr=Record[1];
        }
        params.put("password", passwordstr);
        params.put("userName", name.getText().toString().trim());
        params.put("loginType", loginType);
        Httputils.PostWithBaseUrl(Httputils.login, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("onFailureOfMe", "fail");
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject datas = jsonObject.optJSONObject("datas").optJSONObject("userInfo");
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                ToastUtil.showMessage(LoginActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    SharedPreferences sharedPreferences = RepluginMethod.getHostApplication(LoginActivity.this).getSharedPreferences(BundleTag.Lottery, MODE_PRIVATE);
                    RepluginMethod.getApplication(LoginActivity.this).setUsername(datas.optString("userName", ""));
                    RepluginMethod.getApplication(LoginActivity.this).setisgent(datas.optString("isAgent", ""));

                    //保存记录
                    if(remeberpassword.isChecked())
                    {
                        String passwordstr=password.getText().toString().trim();
                        if(password.getText().toString().equalsIgnoreCase(getString(R.string.placehold)))
                        {
                            passwordstr=Record[1];
                        }
                        LoginRecord.SaveReord(name.getText().toString().trim(),passwordstr);
                    }
                    else
                    {
                        LoginRecord.DeleteFile();
                    }


                    Application app = RepluginMethod.getHostApplication(LoginActivity.this);
                    Field f = null;
                    try {
                        f = app.getClass().getDeclaredField("Username");
                        f.setAccessible(true);
                        f.set(app, datas.optString("userName", ""));
                        LogTools.e("Username", "修改成功");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    sharedPreferences.edit()
                            .putString(BundleTag.UserInfo, datas.toString())
                            .commit();
                    String namelist = usernamelist.getString(BundleTag.UserNameList, "");
                    if (!namelist.contains(name.getText().toString().trim())) {
                        String middle = namelist.equalsIgnoreCase("") ? "" : "|";
                        String username = namelist + middle + name.getText().toString().trim();
                        if (username.split("\\|").length > 4) {
                            username = username.substring(username.indexOf("|", 2) + 1, username.length());
                        }
                        usernamelist.edit().putString(BundleTag.UserNameList, username).commit();
                    }
                    Intent intent = new Intent();
                    intent.putExtra(BundleTag.Username, datas.optString("userName", ""));
                    setResult(BundleTag.ResultCode, intent);
                    finish();
                } else {
                    comit.setEnabled(true);
                    if (publicDialog != null && !isDestroyed()) {
                        publicDialog.dismiss();
                    }
                }
            }
        });
    }


}
