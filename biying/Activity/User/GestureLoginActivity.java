package com.lottery.biying.Activity.User;

import android.app.Activity;
import android.app.Application;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.GestureLockViewGroup;
import com.lottery.biying.view.PublicDialog;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by Administrator on 2018/5/17.
 */
public class GestureLoginActivity extends BaseActivity {

    GestureLockViewGroup mGestureLockViewGroup;
    private EditText name;
    String passd;
    StringBuilder pass = new StringBuilder();
    SharedPreferences usernamelist;
    String arr[] = null;
    PopupWindow popwindow;
    String staterecord;
    public static long autime = 0;
    public PublicDialog publicDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_gesture_login);
        Initview();
    }

    private void Initview()
    {
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.userlogin));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);


        name =  FindView(R.id.name);
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && arr != null) {
                    openPopView((TextView) v, arr);
                }
                return false;
            }
        });
        usernamelist = RepluginMethod.getHostApplication(this).getSharedPreferences(BundleTag.UserNameList, Activity.MODE_PRIVATE);
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

        initGesture();
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
                        if (popwindow != null) {
                            popwindow.dismiss();
                        }
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

    private void initGesture() {
//        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gesturelock);
//        gestureEventListener();
//        gesturePasswordSettingListener();
//        gestureRetryLimitListener();

        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
        int width=ScreenUtils.getScreenWH(this)[0]-ScreenUtils.getDIP2PX(this,20)*2;
        int margin=ScreenUtils.getDIP2PX(this,20);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(width,width);
        ll.setMargins(margin,margin,margin,margin);
        mGestureLockViewGroup.setLayoutParams(ll);
//        mGestureLockViewGroup.setAnswer(new int[]{2, 4, 8, 6, 2, 5, 8, 4, 5, 6});
        mGestureLockViewGroup.setUnMatchExceedBoundary(4);
        mGestureLockViewGroup.setLimitListener(4,9, new GestureLockViewGroup.onLimitListener() {
            @Override
            public void onLimit() {
                ToastUtil.showMessage(GestureLoginActivity.this, "请连接4-9个点的密码");
            }
        });
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {

                    @Override
                    public void onTouchEvent(MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN)
                            pass.delete(0, pass.length());
                    }

                    @Override
                    public void oncheck(int event) {
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {

                        finish();
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
                        passd = pass.toString();
                        mGestureLockViewGroup.resetAndRefresh();
                        if (name.getText().toString().equalsIgnoreCase("")) {
                            ToastUtil.showMessage(GestureLoginActivity.this, "请输入4-16位数字/字母或组合的帐号");
                            return;
                        }
                        LogTools.e("dddddddddd11111" + pass.toString(), "passd = " + passd);
                        pass.delete(0, pass.length());
                        authorize();
                    }

                    @Override
                    public void onBlockSelected(int cId) {
                        pass.append(String.valueOf(cId));
                    }
                });
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
        Httputils.PostWithBaseUrl(Httputils.authorize, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
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
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000") && staterecord.equalsIgnoreCase(tempjsobject.optString("state", ""))) {
                    autime = Long.valueOf(tempjsobject.optString("expires_in", ""));
                    String access_token = tempjsobject.optString("access_token", "");
                    SharedPreferences sharedPreferences = RepluginMethod.getHosttSharedPreferences(GestureLoginActivity.this);
                    sharedPreferences.edit().putString(BundleTag.Access_token, access_token).commit();
                    Login(access_token);
                } else {
                    ToastUtil.showMessage(GestureLoginActivity.this, jsonObject.optString("msg", ""));
                }
            }
        });
    }

    private void Login(String access_token) {
        if (name.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, getString(R.string.plzfillinname));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("accessToken", access_token);
        params.put("password", passd);
        params.put("userName", name.getText().toString().trim());
        params.put("loginType", "2");
        Httputils.PostWithBaseUrl(Httputils.login, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
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
                ToastUtil.showMessage(GestureLoginActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    SharedPreferences sharedPreferences = RepluginMethod.getHostApplication(GestureLoginActivity.this).getSharedPreferences(BundleTag.Lottery, MODE_PRIVATE);
                    RepluginMethod.getApplication(GestureLoginActivity.this).setUsername(datas.optString("userName", ""));
                    RepluginMethod.getApplication(GestureLoginActivity.this).setisgent(datas.optString("isAgent", ""));
                    sharedPreferences.edit()
                            .putString(BundleTag.UserInfo, datas.toString())
                            .commit();

                    Application app=RepluginMethod.getHostApplication(GestureLoginActivity.this);
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

                    String namelist = usernamelist.getString(BundleTag.UserNameList, "");
                    if (!namelist.contains(name.getText().toString().trim())) {
                        String middle = namelist.equalsIgnoreCase("") ? "" : "|";
                        String username = namelist + middle + name.getText().toString().trim();
                        if (username.split("\\|").length > 4) {
                            username = username.substring(username.indexOf("|", 2) + 1, username.length());
                        }
                        usernamelist.edit().putString(BundleTag.UserNameList, username).commit();
                    }
                    Intent intent=new Intent();
                    intent.putExtra(BundleTag.Username,datas.optString("userName", ""));
                    setResult(BundleTag.LoginSuccess, intent);
                    finish();
                } else {
                    if (publicDialog != null && !isDestroyed()) {
                        publicDialog.dismiss();
                    }
                }
            }
        });
    }
}
