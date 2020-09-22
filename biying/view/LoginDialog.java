package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.AccountManage.PersonalData.BandPhoneActivity;
import com.lottery.biying.Activity.User.GestureLoginActivity;
import com.lottery.biying.Activity.User.RegActivity;
import com.lottery.biying.Activity.WelcomeActivity;
import com.lottery.biying.MainActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Administrator on 2018/5/28.
 */
public class LoginDialog extends RelativeLayout implements View.OnClickListener {

    OnLoginListener onLoginListener;
    String args[];
    Context context,plugin_context;
    WindowManager windowManager;
    View view;
    private EditText password, name, code;
    TextView register, gesturepassword, forgetpassward;
    Button comit;
    ImageView back;
    String staterecord;
    public static long autime = 0;
    public Special_Dialog publicDialog;
    TextView notice;
    String loginType = "1";
    SharedPreferences usernamelist;
    String arr[] = null;
    PopupWindow popwindow;


    public LoginDialog(Context context) {
        super(context);

    }

    public LoginDialog(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public LoginDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public void setContext(Context context) {
        this.context = context;
    }

    public void setContext_plugin(Context plugin_context) {
        this.plugin_context = plugin_context;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    public void show() {
        if (context != null)
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

//        view = RePlugin.fetchViewByLayoutName(BundleTag.PluginName, "login", null);
//        if(view==null)
//        {
//            LogTools.e("yyyy","错误1");
//            view= View.inflate(plugin_context,RePlugin.fetchResourceIdByName(BundleTag.PluginName, "login"),null);
//            if(view==null)LogTools.e("yyyy","错误2");
//        }

        Makeview();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明
        windowManager.addView(view, lp);
    }

    public void hide() {
        windowManager.removeView(view);
    }

    private void Makeview() {
        register = (TextView) view.findViewById(R.id.register);
        gesturepassword = (TextView) view.findViewById(R.id.gesturepassword);
        forgetpassward = (TextView) view.findViewById(R.id.forgetpassward);
        register.setOnClickListener(this);
        gesturepassword.setOnClickListener(this);
        forgetpassward.setOnClickListener(this);
        usernamelist = RepluginMethod.getHostApplication(plugin_context).getSharedPreferences(BundleTag.UserNameList, Activity.MODE_PRIVATE);
        back = (ImageView) view.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        TextView backtitle = (TextView) view.findViewById(R.id.backtitle);
        backtitle.setText(plugin_context.getString(R.string.userlogin));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        backtitle.setOnClickListener(this);
        name = (EditText) view.findViewById(R.id.name);
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && arr != null) {
                    openPopView((TextView) v, arr);
                }
                return false;
            }
        });

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
        password = (EditText) view.findViewById(R.id.password);
        code = (EditText) view.findViewById(R.id.code);
        comit = (Button) view.findViewById(R.id.comit);
        comit.setOnClickListener(this);
        notice = (TextView) view.findViewById(R.id.notice);

        if (autime == 0) {
            autime = System.currentTimeMillis() / 1000;
        }


        comit = (Button) view.findViewById(R.id.comit);
        comit.setOnClickListener(this);
    }

    private void openPopView(final TextView view, final String strs[]) {
        LogTools.e("strs length", strs.length + " " + view.getWidth());
        if (popwindow == null) {
            int width = view.getWidth();
            LinearLayout ll = new LinearLayout(context);
            LinearLayout.LayoutParams laypa = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(laypa);
            ll.setBackground(plugin_context.getResources().getDrawable(R.drawable.rangle_noradiu_backgroud));

            for (int i = 0; i < strs.length; i++) {
                ImageView imageview = new ImageView(context);
                LinearLayout.LayoutParams imagell = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                imageview.setLayoutParams(imagell);
                imageview.setBackgroundColor(plugin_context.getResources().getColor(R.color.line));

                TextView textview = new TextView(context);
                LinearLayout.LayoutParams tvll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textview.setText(strs[i]);
                textview.setLayoutParams(tvll);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textview.setPadding(ScreenUtils.getDIP2PX(context, 10), ScreenUtils.getDIP2PX(context, 4), 0, ScreenUtils.getDIP2PX(context, 4));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comit:
                if (name.getText().toString().length() < 4) {
                    ToastUtil.showMessage(context,plugin_context, "请输入4-16位数字/字母或组合的帐号");
                    return;
                }
                if (password.getText().toString().length() < 6) {
                    ToastUtil.showMessage(context,plugin_context, "请输入6-16位数字/字母或组合的密码");
                    return;
                }
                authorize();
                break;
            case R.id.backtitle:
            case R.id.back:
                if (MainActivity.isexit != 2) {
                } else {
                    if (onLoginListener != null)
                        onLoginListener.onlogin("",args);
                    hide();
                }
                break;
            case R.id.register:
                hide();
                context.startActivity(new Intent(context, RegActivity.class));
                break;
            case R.id.forgetpassward:
                hide();
                Intent intent = new Intent();
                intent.setClass(context, BandPhoneActivity.class);
                intent.putExtra(BundleTag.Type, 1);
                context.startActivity(intent);
                break;
            case R.id.gesturepassword:
                hide();
                Intent intent2 = new Intent();
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.setClass(context, GestureLoginActivity.class);
                context.startActivity(intent2);
                break;
        }
    }

    public interface OnLoginListener {
        public void onlogin(String Username,String args[]);
    }


    private void authorize() {
        if (publicDialog == null) {
            publicDialog = new Special_Dialog(context,plugin_context);
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
        Httputils.PostWithBaseUrl(Httputils.authorize, params, new MyJsonHttpResponseHandler((Activity) context, false) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if (publicDialog != null) {
                    publicDialog.dismiss();
                }
                LogTools.e("Throwable", s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (publicDialog != null) {
                    publicDialog.dismiss();
                }
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject tempjsobject = jsonObject.optJSONObject("datas");
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000") && staterecord.equalsIgnoreCase(tempjsobject.optString("state", ""))) {
                    autime = Long.valueOf(tempjsobject.optString("expires_in", ""));
                    String access_token = tempjsobject.optString("access_token", "");
                    SharedPreferences sharedPreferences = context.getSharedPreferences(BundleTag.Lottery, Activity.MODE_PRIVATE);
                    sharedPreferences.edit().putString(BundleTag.Access_token, access_token).commit();
                    Login(access_token);
                } else {
                    ToastUtil.showMessage(context,plugin_context, jsonObject.optString("msg", ""));
                    comit.setEnabled(true);
                }
            }
        });
    }

    private void Login(String access_token) {
        if (name.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(context,plugin_context, plugin_context.getString(R.string.plzfillinname));
            return;
        }
        if (password.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(context,plugin_context, plugin_context.getString(R.string.plzfillinpassword));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("accessToken", access_token);
        params.put("password", password.getText().toString().trim());
        params.put("userName", name.getText().toString().trim());
        params.put("loginType", loginType);
        Httputils.PostWithBaseUrl(Httputils.login, params, new MyJsonHttpResponseHandler((Activity) context, false) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if (publicDialog != null) {
                    publicDialog.dismiss();
                }
                LogTools.e("onFailureOfMe", "fail");
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject datas = jsonObject.optJSONObject("datas").optJSONObject("userInfo");
                if (publicDialog != null) {
                    publicDialog.dismiss();
                }
                ToastUtil.showMessage(context,plugin_context, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(BundleTag.Lottery, Activity.MODE_PRIVATE);
                    RepluginMethod.getApplication(plugin_context).setUsername(datas.optString("userName", ""));
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
                    hide();
                    if (onLoginListener != null)
                        onLoginListener.onlogin(datas.optString("userName", ""),args);
                } else {
                    if (publicDialog != null) {
                        publicDialog.dismiss();
                    }
                }
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            hide();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
