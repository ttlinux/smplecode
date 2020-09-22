package com.lottery.biying.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.lottery.biying.R;
import com.lottery.biying.view.PublicDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/9/23.
 */
public class MyJsonHttpResponseHandler2 extends JsonHttpResponseHandler {

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context context;
    boolean state = false;
    private String textcode = "";
    //    public MyJsonHttpResponseHandler(){}
    PublicDialog publicDialog;

    public MyJsonHttpResponseHandler2(Context context, boolean state) {
        if (state) {
            if (publicDialog != null && context != null) {
                publicDialog.dismiss();
                publicDialog = null;
            }
            publicDialog = new PublicDialog(context);
            publicDialog.show();
        }
        this.context = context;
        this.state = state;
    }

//    public MyJsonHttpResponseHandler(Activity context,String textcode){//测试用的
//        this.context=context;
//        this.textcode=textcode;
//    }


    @Override
    public void onFailure(Throwable throwable, String s) {
        super.onFailure(throwable, s);
        onFailureOfMe(throwable, s);
        if (context != null ) {
            if(context instanceof Activity)
            {
                Activity act=(Activity)context;
                if(!act.isFinishing() && !act.isDestroyed())
                {
                    if (publicDialog != null) {
                        publicDialog.dismiss();
                    }
                }
            }
            else
            {
                if (state) {
                    if (publicDialog != null) {
                        publicDialog.dismiss();
                    }
                }
            }
            ToastUtil.showMessage(context, context.getString(R.string.connecterr));
        }
        LogTools.e("错误",throwable.toString() + s);
    }

    @Override
    protected Object parseResponse(String s) throws JSONException {
        if (context != null ) {
            if (state) {
                if (publicDialog != null) {
                    publicDialog.dismiss();
                }
            }
        }
        return super.parseResponse(s);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        super.onSuccess(jsonObject);

        if (context != null ) {
            if(context instanceof Activity)
            {
                Activity act=(Activity)context;
                if(!act.isFinishing() && !act.isDestroyed())
                {
                    if (publicDialog != null) {
                        publicDialog.dismiss();
                    }
                }
            }
            else
            {
                if (state) {
                    if (publicDialog != null) {
                        publicDialog.dismiss();
                    }
                }
            }
            //掉线的
            if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000005")) {
                OnError(jsonObject);
                return;
            }
            //登录后被顶下来的
            if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000007")) {
                OnError(jsonObject);
                return;
            }

            if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000008")) {
                Onmaintained(jsonObject.optString("msg", ""));
                return;
            }
//            if(!textcode.equalsIgnoreCase(""))
//            {
//                Onmaintained();
//                return;
//            }
            onSuccessOfMe(jsonObject);
        }

    }

    public void onSuccessOfMe(JSONObject jsonObject) {
    }

    public void onFailureOfMe(Throwable throwable, String s) {
        throwable.printStackTrace();
        LogTools.e("错误",throwable.toString() + s);
    }


    protected void OnError(JSONObject jsonObject) {
        onFailureOfMe(new Throwable(), "登录超时");
        if (context == null) return;
        if(context instanceof Activity)
        {
            if(((Activity)context).isDestroyed() || ((Activity)context).isFinishing())
            {
                return;
            }
            if(publicDialog!=null)
            {
                publicDialog.dismiss();
                publicDialog = null;
            }
        }

        Application app=RepluginMethod.getHostApplication(context);
        Field f = null;
        String MainHostUsername="";
        try {
            f = app.getClass().getDeclaredField("Username");
            f.setAccessible(true);
            MainHostUsername=(String)f.get(app);
            f.set(app, "");
            LogTools.e("主程序Username","修改成功");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = RepluginMethod.getHosttSharedPreferences(context);
        sharedPreferences.edit().putString(BundleTag.Access_token, "").commit();
        RepluginMethod.getApplication(context).ClearTiYuCache();
        boolean isClear=RepluginMethod.getApplication(context).getBaseapplicationUsername().length()==0;
        RepluginMethod.getApplication(context).setUsername("");

        if(MainHostUsername!=null && MainHostUsername.length()>0)
        {
            ToastUtil.showMessage(context, jsonObject.optString("msg", ""));
            Intent intent = RepluginMethod.HostMainclass(context);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(BundleTag.Username, "");
            intent.putExtra(BundleTag.IntentTag, 0);
            context.startActivity(intent);
        }

    }

    private void Onmaintained(String ss) {
        onFailureOfMe(new Throwable(), ss);
        if (context == null) return;
        ToastUtil.showMessage(context, ss);
    }

}
