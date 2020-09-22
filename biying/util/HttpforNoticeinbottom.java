package com.lottery.biying.util;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/6/28.
 */
public class HttpforNoticeinbottom {

    public static final String noticeurl = "commons/getMobileInformation";
    private static boolean isget = false;
    public static HashMap<String, Noticebean> hashMap = new HashMap<>();
    public static HashMap<String, String> hashMap2 = new HashMap<>();

    public static enum Tag
    {
        login("login",0),register("register",1),
        rxff("rxff",2),withdraw("withdraw",3),
        wszl("wszl",4),xjrx("xjrx",5),
        zcxj("zcxj",6),applyAgent("applyAgent",7),bank("bank",8),
        bdsj("bdsj",9),compay("compay",10),fastpay("fastpay",11),forgetpwd("forgetpwd",12),eduMinPay("eduMinPay",13);
        public String name;
        public int index;
        // 构造方法
        private Tag(String name,int index) {
            this.name = name;
            this.index=index;
        }
        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }
    }


    public static String GetMainPageData(final TextView view, final String tag, Activity act) {
        if (hashMap2 == null || hashMap2.size() == 0) {
            String json = RepluginMethod.getHosttSharedPreferences(act).getString(BundleTag.Information, "");
            if (json.length() > 0) {
                JSONObject information = null;
                try {
                    information = new JSONObject(json);
                    Iterator<String> keys = information.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String value = information.optString(key, "");
                        hashMap2.put(key, value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                ((View)view.getParent()).setVisibility(View.INVISIBLE);
                return "";
            }
        }
        return CurrentMethod(view, tag, act, ((ViewGroup)view.getParent()));
//        AbandonMethod(view, tag, act);
    }


    public static String CurrentMethod(final TextView view, final String tag, Activity act, ViewGroup parent) {


        if (hashMap2 != null && hashMap2.size() > 0 && view != null) {
            if (hashMap2.get(tag) != null && !hashMap2.get(tag).equalsIgnoreCase("")) {
                view.setText(Html.fromHtml(hashMap2.get(tag)));
                if (parent != null) {
                    parent.setVisibility(View.VISIBLE);
                }
            } else {
                if (parent != null) {
                    parent.setVisibility(View.INVISIBLE);
                }
            }
        }
        if (hashMap2 != null) {
            return hashMap2.get(tag);
        }
        return null;
    }


    public interface OnreceiveListener {
        public void hasdata(HashMap<String, Noticebean> hashMap);

        public void fail();
    }

    public static class Noticebean {
        public String getModuleType() {
            return moduleType;
        }

        public void setModuleType(String moduleType) {
            this.moduleType = moduleType;
        }

        public String getModuleDesc() {
            return moduleDesc;
        }

        public void setModuleDesc(String moduleDesc) {
            this.moduleDesc = moduleDesc;
        }

        String moduleType;
        String moduleDesc;
    }

    //        "datas": [
//        {
//            "moduleType": "register",
//                "moduleDesc": "注册信息提示"
//        },
//        {
//            "moduleType": "withdraw",
//                "moduleDesc": "申请提现提示"
//        },
//        {
//            "moduleType": "login",
//                "moduleDesc": "登录信息提示"
//        },
//        {
//            "moduleType": "compay",
//                "moduleDesc": "公司入款提示"
//        },
//        {
//            "moduleType": "applyAgent",
//                "moduleDesc": "申请代理提示"
//        },
//        {
//            "moduleType": "fastpay",
//                "moduleDesc": "快捷支付提示"
//        }
//        ],
//        "errorCode": "000000",
//            "msg": "操作成功"
//    }

    public  static String geteduMinPay(Activity activity)
    {
        if(hashMap2.get(Tag.eduMinPay.name)!=null)
        {
            return hashMap2.get(Tag.eduMinPay.name);
        }
        else
        {
            RequestParams requestParams = new RequestParams();
            Httputils.PostWithBaseUrl(Httputils.UserFragment_Model, requestParams, new MyJsonHttpResponseHandler(activity, true) {
                @Override
                public void onSuccessOfMe(JSONObject jsonObject) {
                    super.onSuccessOfMe(jsonObject);
                    if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                        return;
                    }

                    JSONObject datas = jsonObject.optJSONObject("datas");
                    hashMap2.put("eduMinPay",datas.optString("eduMinPay", ""));
                }

                @Override
                public void onFailureOfMe(Throwable throwable, String s) {
                    super.onFailureOfMe(throwable, s);
                }
            });
            return null;
        }
    }
    public static String Gettext(final String strmodel, final TextView textView, Activity activity) {

        String minpay=hashMap2.get(Tag.eduMinPay.name);
        if(minpay!=null)
        {
            textView.setText(String.format(strmodel, minpay));
            return minpay;
        }
        RequestParams requestParams = new RequestParams();
        Httputils.PostWithBaseUrl(Httputils.UserFragment_Model, requestParams, new MyJsonHttpResponseHandler(activity, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }

                JSONObject datas = jsonObject.optJSONObject("datas");
                Double pay = Double.valueOf(datas.optString("eduMinPay", "")) * 0.01 * 100;
                String patstr = Httputils.Limit2(pay);
                textView.setText(String.format(strmodel, patstr));
                hashMap2.put("eduMinPay",datas.optString("eduMinPay", ""));
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
        return null;
    }
}
