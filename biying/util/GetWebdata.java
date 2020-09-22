package com.lottery.biying.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.gamewebActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.view.WebAPPChooserDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/22.
 */
public class GetWebdata {
//跳转电子网页时取的数据
    static WebAPPChooserDialog webAPPChooserDialog;
    static boolean isruning=false;
    public static void GetData(Context context,final String flat,final String gameCode,final String title,final String pull) {
        LogTools.e("GetWebdata222","GetWebdata222");
        if(isruning)return;
        isruning=true;

        Activity activity = null;
        if (!(context instanceof Activity)) {
            activity = RepluginMethod.getApplication(context).getActivity();
            if (activity == null)
                return;
        } else {
            activity = (Activity) context;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("gameCode", gameCode);
        requestParams.put("userName", RepluginMethod.getApplication(context).getBaseapplicationUsername());
        requestParams.put("flat", flat);


        Httputils.PostWithBaseUrl("", requestParams, new MyJsonHttpResponseHandler(activity, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                isruning=false;
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                isruning=false;
                String datas;
                LogTools.e("需要打开的网页", jsonObject.toString());
                if (jsonObject.optString("datas") == null || jsonObject.optString("datas").equalsIgnoreCase("null") || jsonObject.optString("datas").equalsIgnoreCase("") || jsonObject.optString("datas").length() < 5) {
                    ToastUtil.showMessage(context, jsonObject.optString("msg", ""));
                    return;
                }

                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000")) {
                    return;
                }
                datas = jsonObject.optString("datas", "");
                if (flat.equalsIgnoreCase("sa")) {
                    Intent intent = new Intent(context, gamewebActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("flat", flat);
                    intent.putExtra("gamecode",gameCode);
                    intent.putExtra("url",datas);
                    context.startActivity(intent);
//                    iscilick = true;
//                    String[] all = datas.split("\\^");
//                    LogTools.e("ddafdfadfAA" + all, "dddd");
//                    RequestParams requestParams = new RequestParams();
//                    requestParams.put("username", all[1]);
//                    requestParams.put("token", all[2]);
//                    requestParams.put("lobby", all[3]);
//                    requestParams.put("lang", all[4]);
//                    requestParams.put("returnurl", Httputils.BaseUrl);
//                    requestParams.put("mobile", "true");
//                    String postDate = "username=" + all[1] + "&token=" + all[2] + "&lobby=" + all[3] + "&lang=" + all[4] + "&returnurl=" + Httputils.BaseUrl + "&mobile=" + "true";
////                    mWebView.postUrl(all[0] + "?", EncodingUtils.getBytes(postDate, "utf-8"));
//                    LogTools.ee("ddafdfadfSS" + EncodingUtils.getBytes(postDate, "utf-8"), all[0] + "?" + requestParams + "");
                } else {
                Openurl(context,datas, flat, title,  gameCode,pull);
                LogTools.e("kkkxzcxzsad", datas);
                }
            }
        });
    }
    private static void Openurl( final Context context,final String Url,final String flat,final String titleont,final String gameCode,final String pull) {
        SharedPreferences sharedPreferences = RepluginMethod.getApplication(context).getSharedPreferences();
        String title = sharedPreferences.getString(BundleTag.chooseBrowser, "");

        Activity activity = null;
        if (!(context instanceof Activity)) {
            activity = RepluginMethod.getApplication(context).getActivity();
            if (activity == null)
                return;
        } else {
            activity = (Activity) context;
        }
            webAPPChooserDialog = new WebAPPChooserDialog(activity, Url);
            webAPPChooserDialog.setOnSelectDefalutWebListener(new WebAPPChooserDialog.OnSelectDefalutWebListener() {
                @Override
                public void OnDefalutselect() {
                    Intent intent = new Intent(context, gamewebActivity.class);
                    if (pull.equalsIgnoreCase("pull")) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    intent.putExtra("title", titleont);
                    intent.putExtra("flat", flat);
                    intent.putExtra("gamecode", gameCode);
                    intent.putExtra("url", Url);
                    context.startActivity(intent);
//                  mWebView.loadUrl(Url);
                    webAPPChooserDialog.dismiss();
                    webAPPChooserDialog=null;
//                ToastUtil.showMessage(gamewebActivity.this,"自己");
                }
            });

            webAPPChooserDialog.show();

            webAPPChooserDialog.setTitle(title);

        }
}
