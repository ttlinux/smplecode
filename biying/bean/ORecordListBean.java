package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/12.
 */
public class ORecordListBean {


    /**
     * betMoney : 50.0
     * betTime : 2018-03-27 00:33:46
     * flat : bbin
     * game_type :
     * status : 已结算
     * statusValue : 1
     * userName : py880920
     * winMoney : 0.0
     */

    private String betMoney;
    private String betTime;
    private String flat;
    private String game_type;
    private String status;
    private String statusValue;
    private String userName;
    private String winMoney;

    public String getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(String betMoney) {
        this.betMoney = betMoney;
    }

    public String getBetTime() {
        return betTime;
    }

    public void setBetTime(String betTime) {
        this.betTime = betTime;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(String winMoney) {
        this.winMoney = winMoney;
    }

    public static ORecordListBean Analysis(JSONObject jsonObject)
    {
        ORecordListBean bean=new ORecordListBean();
        bean.setBetMoney(jsonObject.optString("betMoney",""));
        bean.setBetTime(jsonObject.optString("betTime",""));
        bean.setFlat(jsonObject.optString("flat",""));
        bean.setGame_type(jsonObject.optString("game_type",""));
        bean.setStatus(jsonObject.optString("status",""));
        bean.setStatusValue(jsonObject.optString("statusValue",""));
        bean.setUserName(jsonObject.optString("userName",""));
        bean.setWinMoney(jsonObject.optString("winMoney",""));
        return bean;
    }

    public static ORecordListBean Analysis_local(JSONObject jsonObject)
    {
        ORecordListBean bean=new ORecordListBean();
        bean.setBetMoney(jsonObject.optString("betMoney",""));
        bean.setBetTime(jsonObject.optString("betTime",""));
        bean.setFlat(jsonObject.optString("flat",""));
        bean.setGame_type(jsonObject.optString("game_type",""));
        bean.setStatus(jsonObject.optString("status",""));
        bean.setStatusValue(jsonObject.optString("statusValue",""));
        bean.setUserName(jsonObject.optString("userName",""));
        bean.setWinMoney(jsonObject.optString("winMoney",""));
        return bean;
    }
}
