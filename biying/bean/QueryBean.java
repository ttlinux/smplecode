package com.lottery.biying.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */
public class QueryBean {

    String backWaterMoney;
    String betTime;
    String betGameContent;
    String betWagersId,betIncome;
    String settleStatusValue,settleStatus;

    double betIn,betUsrWin;
    boolean itemstate=false;
    ArrayList<detail> details;

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    public String getSettleStatusValue() {
        return settleStatusValue;
    }

    public void setSettleStatusValue(String settleStatusValue) {
        this.settleStatusValue = settleStatusValue;
    }

    public String getBackWaterMoney() {
        return backWaterMoney;
    }

    public void setBackWaterMoney(String backWaterMoney) {
        this.backWaterMoney = backWaterMoney;
    }

    public ArrayList<detail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<detail> details) {
        this.details = details;
    }

    public String getBetIncome() {
        return betIncome;
    }

    public void setBetIncome(String betIncome) {
        this.betIncome = betIncome;
    }

    public String getBetGameContent() {
        return betGameContent;
    }

    public void setBetGameContent(String betGameContent) {
        this.betGameContent = betGameContent;
    }

    public String getBetWagersId() {
        return betWagersId;
    }

    public void setBetWagersId(String betWagersId) {
        this.betWagersId = betWagersId;
    }


    public double getBetIn() {
        return betIn;
    }

    public void setBetIn(double betIn) {
        this.betIn = betIn;
    }

    public String getBetTime() {
        return betTime;
    }

    public void setBetTime(String betTime) {
        this.betTime = betTime;
    }

    public double getBetUsrWin() {
        return betUsrWin;
    }

    public void setBetUsrWin(double betUsrWin) {
        this.betUsrWin = betUsrWin;
    }



    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }


    public static class detail
    {
        String color,key,value;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static QueryBean Analysis(JSONObject jsobj)
    {
        QueryBean bean = new QueryBean();
        bean.setBetTime(jsobj.optString("betTime", ""));
        try {
            bean.setBetIn(Double.valueOf(jsobj.optString("betIn", "")));
            bean.setBetUsrWin(Double.valueOf(jsobj.optString("betUsrWin", "")));
        } catch (java.lang.NumberFormatException ex) {
            ex.printStackTrace();
        }
        bean.setBetGameContent(jsobj.optString("betContent", ""));
        bean.setBetWagersId(jsobj.optString("betWagersId", ""));
        bean.setBetIncome(jsobj.optString("betIncome", ""));
        bean.setBackWaterMoney(jsobj.optString("backWaterMoney",""));
        bean.setSettleStatus(jsobj.optString("settleStatus", ""));
        bean.setSettleStatusValue(jsobj.optString("settleStatusValue", ""));
        try {

            JSONArray details=jsobj.getJSONArray("detail");
            ArrayList<QueryBean.detail> detaillist=new ArrayList<QueryBean.detail>();
            for (int j = 0; j <details.length(); j++) {
                JSONObject detailobj=details.optJSONObject(j);
                QueryBean.detail dbean=new QueryBean.detail();
                dbean.setColor(detailobj.optString("color",""));
                dbean.setKey(detailobj.optString("key",""));
                dbean.setValue(detailobj.optString("value",""));
                detaillist.add(dbean);
            }
            bean.setDetails(detaillist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static QueryBean Analysis_local(JSONObject jsobj)
    {
        QueryBean bean = new QueryBean();
        bean.setBetTime(jsobj.optString("betTime", ""));
        try {
            bean.setBetIn(Double.valueOf(jsobj.optString("betIn", "")));
            bean.setBetUsrWin(Double.valueOf(jsobj.optString("betUsrWin", "")));
        } catch (java.lang.NumberFormatException ex) {
            ex.printStackTrace();
        }
        bean.setBetGameContent(jsobj.optString("betGameContent", ""));
        bean.setBetWagersId(jsobj.optString("betWagersId", ""));
        bean.setBetIncome(jsobj.optString("betIncome", ""));
        bean.setBackWaterMoney(jsobj.optString("backWaterMoney", ""));
        bean.setSettleStatus(jsobj.optString("settleStatus", ""));
        bean.setSettleStatusValue(jsobj.optString("settleStatusValue", ""));
        try {

            JSONArray details=jsobj.getJSONArray("detail");
            ArrayList<QueryBean.detail> detaillist=new ArrayList<QueryBean.detail>();
            for (int j = 0; j <details.length(); j++) {
                JSONObject detailobj=details.optJSONObject(j);
                QueryBean.detail dbean=new QueryBean.detail();
                dbean.setColor(detailobj.optString("color",""));
                dbean.setKey(detailobj.optString("key",""));
                dbean.setValue(detailobj.optString("value",""));
                detaillist.add(dbean);
            }
            bean.setDetails(detaillist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
