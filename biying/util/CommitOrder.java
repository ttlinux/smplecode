package com.lottery.biying.util;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.OrderConfirmAdapter;
import com.lottery.biying.bean.SmartTraceBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */
public class CommitOrder {

    // lotteryCode, amount, counts,order,traceOrder
    public static void commit(final OnFinishListener listener,final boolean isTrace, final boolean traceUntilwin,Activity context,String ...args)
    {
        if(traceUntilwin && args.length<5 )
        {
            return;
        }

        RequestParams requestParams=new RequestParams();
        requestParams.put("lotteryCode",args[0]);
        requestParams.put("amount",args[1]);
        requestParams.put("counts",args[2]);
        requestParams.put("traceWinStop",traceUntilwin?"1":"0");
        requestParams.put("isTrace",isTrace?"1":"0");
        requestParams.put("order",args[3]);
        requestParams.put("traceOrder",args[4]);

        Httputils.PostWithBaseUrl(Httputils.BetOrder,requestParams,new MyJsonHttpResponseHandler(context,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if(listener!=null)listener.OnDone(false);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("commit",jsonObject.toString());
                if(!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(context,jsonObject.optString("msg",""));
                    if(listener!=null)listener.OnDone(false);
                    return;
                }
                ToastUtil.showMessage(context,jsonObject.optString("msg",""));
                if(listener!=null)listener.OnDone(true);
            }
        });
    }

    public interface OnFinishListener
    {
        public void OnDone(boolean succesful);
    }

    public static JSONArray orderMaker_Usually(ArrayList<OrderConfirmAdapter.Itemvalue> ivs)
    {
        JSONArray jsonArray=new JSONArray();
        if(ivs!=null)
        {

            for (int i = 0; i < ivs.size(); i++) {
                JSONObject jsonObject=new JSONObject();
                OrderConfirmAdapter.Itemvalue iv=ivs.get(i);
                try {
                    jsonObject.put("lotteryGame",iv.getPlaytype_str());
                    jsonObject.put("content",iv.getItemvalueV1());
                    jsonObject.put("counts",iv.getBettimes());
                    jsonObject.put("unit",iv.getUnit());
                    jsonObject.put("bounsType",iv.getBounsType());
                    jsonObject.put("multiple",iv.getTimes());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray;
    }

    public static JSONArray traceOrderMaker_Usually(ArrayList<OrderConfirmAdapter.Itemvalue> ivs ,String qs)
    {
        JSONArray jsonArray=new JSONArray();
        if(ivs!=null)
        {

                JSONObject jsonObject=new JSONObject();
                OrderConfirmAdapter.Itemvalue iv=ivs.get(0);
                try {
                    jsonObject.put("qs",qs);
                    jsonObject.put("betMultiple","1");
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        return jsonArray;
    }

    public static JSONArray orderMaker_Trace(ArrayList<SmartTraceBean> stbs)
    {
        JSONArray jsonArray=new JSONArray();
        if(stbs!=null)
        {

                JSONObject jsonObject=new JSONObject();
                SmartTraceBean smartTraceBean=stbs.get(0);
                try {
                    jsonObject.put("lotteryGame",smartTraceBean.getGameCode());
                    jsonObject.put("content",smartTraceBean.getContent());
                    jsonObject.put("counts",smartTraceBean.getBettimes());
                    jsonObject.put("unit",smartTraceBean.getUnit());
                    jsonObject.put("bounsType",smartTraceBean.getBounsType());
                    jsonObject.put("multiple",smartTraceBean.getTimes());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        return jsonArray;
    }

    public static JSONArray traceOrderMaker_Trace(ArrayList<SmartTraceBean> stbs ,String qs)
    {
        JSONArray jsonArray=new JSONArray();
        if(stbs!=null)
        {
            for (int i = 0; i < stbs.size(); i++) {
                JSONObject jsonObject=new JSONObject();
                SmartTraceBean smartTraceBean=stbs.get(i);
                if(smartTraceBean.isSelect())
                {
                    try {
                        jsonObject.put("qs",stbs.get(i).getQs());
                        jsonObject.put("betMultiple",smartTraceBean.getTimes());
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonArray;
    }

}
