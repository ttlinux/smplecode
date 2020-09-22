package com.lottery.biying.util;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.bean.CaseBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/10.
 */
public class SmartFollowUntils {

    OnReceiveListener onReceiveListener;

    public OnReceiveListener getOnReceiveListener() {
        return onReceiveListener;
    }

    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    public static void RequestQiShuData(String LotteryName,String gamecode,Activity context,int snoteNumber,int appendCount,final OnReceiveListener onReceiveListener)
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("lotteryCode",LotteryName);
        requestParams.put("gameCode",gamecode);
        requestParams.put("snoteNumber",snoteNumber+"");
        requestParams.put("appendCount",appendCount+"");
        Httputils.PostWithBaseUrl(Httputils.SmartFollow,requestParams,new MyJsonHttpResponseHandler(context,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))return;;
                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray appendList=datas.optJSONArray("appendList");
                ArrayList<CaseBean> caseBeans=new ArrayList<CaseBean>();
                for (int i = 0; i <appendList.length() ; i++) {
                    CaseBean caseBean=new CaseBean();
                    JSONObject append=appendList.optJSONObject(i);
                    caseBean.setMoney(append.optInt("betMoney",2));
                    caseBean.setQishu(append.optString("qsFormat",""));
                    caseBean.setMultiply(append.optInt("betMultiple",1));
                    caseBean.setProfit(append.optString("profit",""));
                    caseBean.setWinProfit(append.optString("winProfit",""));
                    caseBeans.add(caseBean);
                }
                if(onReceiveListener!=null)
                    onReceiveListener.OnReceive(caseBeans);
            }
        });
    }


    public interface OnReceiveListener
    {
        public void OnReceive(ArrayList<CaseBean> caseBeans);// "openTime": 26255,   "qs": "024",       "qsFormat": "距024期截止",
    }
}
