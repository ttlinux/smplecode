package com.lottery.biying.util;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/20.
 */
public class GetLotteryJson {

    public static JSONObject get(String gamecode,String lotterycode,Activity activity)
    {
        JSONObject jsonObject=null;
        InputStream is = null;
        try {
            is = activity.getAssets().open(lotterycode+"/"+gamecode+".json");
            int lenght = is.available();
            byte[]  buffer = new byte[lenght];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            jsonObject=new JSONObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
