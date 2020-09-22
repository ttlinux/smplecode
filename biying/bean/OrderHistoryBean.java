package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/4/28.
 */
public class OrderHistoryBean {


    /**
     * betMoney : 1.8
     * betQishu : 20180428059
     * betTime : 2018-04-28 15:40:27
     * content : 0123456789,0123456789
     * gameName : 五星组选5
     * id : 142
     * isTrace : 否
     * lotteryName : 重庆时时彩
     * model : 分
     * multipe : 1
     * status : 待开奖
     * userName : okmokm
     * winMoney : 0.0
     * winNumber :
     */

    private String stopOrderFalg;
    private String betMoney;
    private String betQishu;
    private String betTime;
    private String content;
    private String gameName;
    private String id;
    private String isTrace;
    private String lotteryName;
    private String model;
    private String multipe;
    private String status;
    private String userName;
    private String winMoney;
    private String winNumber;
    private String statusValue;

    public String getStopOrderFalg() {
        return stopOrderFalg;
    }

    public void setStopOrderFalg(String stopOrderFalg) {
        this.stopOrderFalg = stopOrderFalg;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(String betMoney) {
        this.betMoney = betMoney;
    }

    public String getBetQishu() {
        return betQishu;
    }

    public void setBetQishu(String betQishu) {
        this.betQishu = betQishu;
    }

    public String getBetTime() {
        return betTime;
    }

    public void setBetTime(String betTime) {
        this.betTime = betTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsTrace() {
        return isTrace;
    }

    public void setIsTrace(String isTrace) {
        this.isTrace = isTrace;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMultipe() {
        return multipe;
    }

    public void setMultipe(String multipe) {
        this.multipe = multipe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(String winNumber) {
        this.winNumber = winNumber;
    }


    public static OrderHistoryBean AnalysisData(JSONObject jsonObject)
    {
        OrderHistoryBean bean=new OrderHistoryBean();
        bean.setBetMoney(jsonObject.optString("betMoney", ""));
        bean.setBetQishu(jsonObject.optString("betQishu", ""));
        bean.setBetTime(jsonObject.optString("betTime", ""));
        bean.setContent(jsonObject.optString("content", ""));
        bean.setGameName(jsonObject.optString("gameName", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setIsTrace(jsonObject.optString("isTrace", ""));
        bean.setLotteryName(jsonObject.optString("lotteryName", ""));
        bean.setModel(jsonObject.optString("model", ""));
        bean.setMultipe(jsonObject.optString("multipe", ""));
        bean.setStatus(jsonObject.optString("status", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setWinMoney(jsonObject.optString("winMoney", ""));
        bean.setWinNumber(jsonObject.optString("winNumber", ""));
        bean.setStatusValue(jsonObject.optString("statusValue", ""));
        bean.setStopOrderFalg(jsonObject.optString("stopOrderFalg",""));
        return bean;
    }
}
