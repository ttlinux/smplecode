package com.lottery.biying.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.loopj.android.http.AsyncHttpClient;
import com.lottery.biying.BaseParent.BaseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Administrator on 2017/4/3.
 */
public class MyAsyncHttpClient extends AsyncHttpClient {

    public MyAsyncHttpClient(Context context)
    {
        StringBuilder sb=new StringBuilder();
        if(context==null)
        {
            LogTools.e("错误提示","Context是空");
            return;
        }
        JSONObject header=new JSONObject();
        SharedPreferences sharedPreferences = RepluginMethod.getHosttSharedPreferences(context);
        String web_flag = sharedPreferences.getString(BundleTag.siteFlag, "d5");//e0
        String Access_token=sharedPreferences.getString(BundleTag.Access_token,"");
        this.addHeader("web-flag", web_flag);

        this.addHeader("device-type","android");
        this.addHeader("platform-type","android");
        String SERIAL=android.os.Build.SERIAL;
        this.addHeader("device-id",SERIAL);
        this.addHeader("web-domain", sharedPreferences.getString(BundleTag.siteDomain, ""));
//        this.addHeader("client_ip","127.0.0.1");
        String Username=RepluginMethod.getApplication(context).getBaseapplicationUsername();
        if(Access_token!=null && !Access_token.equalsIgnoreCase(""))
        {
            this.addHeader("access-token", Access_token);
        }

        String authsign="";
        if(Username!=null && !Username.equalsIgnoreCase(""))
        {
            this.addHeader("user-name", Username);
            sb.append(web_flag).append("|");
            sb.append(Username).append("|");
            sb.append("android").append("|");
            sb.append(SERIAL).append("|");
            sb.append(UUID.randomUUID().toString().replaceAll("-", "")).append("|");
            sb.append((int)(Math.random()*500)+100);
            try {
                authsign=AESOperator.getInstance().encrypt(sb.toString());
                this.addHeader("auth-sign",authsign );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        if(BaseApplication.packagename!=null && !BaseApplication.packagename.equalsIgnoreCase(""))
        {
            this.addHeader("update-package", BaseApplication.packagename);
        }


        try {
            header.put("web-flag", web_flag);
            header.put("device-type","android");
            header.put("platform-type", "android");
            header.put("device-id", SERIAL);
            header.put("web-domain", sharedPreferences.getString(BundleTag.siteDomain, ""));
            header.put("access-token", Access_token);
            header.put("user-name",Username);
            header.put("client_ip","127.0.0.1");
            header.put("update-package",BaseApplication.packagename);
            header.put("auth-sign",authsign);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogTools.e("请求头",header.toString());
        header=null;
    }
}
