package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/4/30.
 */
public class TraceOrderBean {


    /**
     * allCount : 5
     * allTraceMoney : 0.1
     * betMoneyRate : 0.0/0.1
     * betWinMoney : 0.0
     * gameName : 五星组选5
     * lotteryName : 重庆时时彩
     * openCount : 0
     * orderNumber : CQSSC201804304288869632
     * ordreId : 509
     * startTraceQishu : 20180430080
     * startTraceTime : 2018-04-30 19:17:42
     * status : 未完成
     * statusValue : 1
     * stopTraceValue : 1
     * traceCountRate : 0/5
     * traceMoney : 0.0
     * traceWinStop : 是
     * userName : okmokm
     */

    private String allCount;
    private String allTraceMoney;
    private String betMoneyRate;
    private String betWinMoney;
    private String gameName;
    private String lotteryName;
    private String openCount;
    private String orderNumber;
    private String ordreId;
    private String startTraceQishu;
    private String startTraceTime;
    private String status;
    private String statusValue;
    private String stopTraceValue;
    private String traceCountRate;
    private String traceMoney;
    private String traceWinStop;
    private String userName;

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getAllTraceMoney() {
        return allTraceMoney;
    }

    public void setAllTraceMoney(String allTraceMoney) {
        this.allTraceMoney = allTraceMoney;
    }

    public String getBetMoneyRate() {
        return betMoneyRate;
    }

    public void setBetMoneyRate(String betMoneyRate) {
        this.betMoneyRate = betMoneyRate;
    }

    public String getBetWinMoney() {
        return betWinMoney;
    }

    public void setBetWinMoney(String betWinMoney) {
        this.betWinMoney = betWinMoney;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getOpenCount() {
        return openCount;
    }

    public void setOpenCount(String openCount) {
        this.openCount = openCount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrdreId() {
        return ordreId;
    }

    public void setOrdreId(String ordreId) {
        this.ordreId = ordreId;
    }

    public String getStartTraceQishu() {
        return startTraceQishu;
    }

    public void setStartTraceQishu(String startTraceQishu) {
        this.startTraceQishu = startTraceQishu;
    }

    public String getStartTraceTime() {
        return startTraceTime;
    }

    public void setStartTraceTime(String startTraceTime) {
        this.startTraceTime = startTraceTime;
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

    public String getStopTraceValue() {
        return stopTraceValue;
    }

    public void setStopTraceValue(String stopTraceValue) {
        this.stopTraceValue = stopTraceValue;
    }

    public String getTraceCountRate() {
        return traceCountRate;
    }

    public void setTraceCountRate(String traceCountRate) {
        this.traceCountRate = traceCountRate;
    }

    public String getTraceMoney() {
        return traceMoney;
    }

    public void setTraceMoney(String traceMoney) {
        this.traceMoney = traceMoney;
    }

    public String getTraceWinStop() {
        return traceWinStop;
    }

    public void setTraceWinStop(String traceWinStop) {
        this.traceWinStop = traceWinStop;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static TraceOrderBean AnalysisData(JSONObject jsonObject)
    {
        TraceOrderBean traceOrderBean=new TraceOrderBean();
        traceOrderBean.setAllCount(jsonObject.optString("allCount",""));
        traceOrderBean.setAllTraceMoney(jsonObject.optString("allTraceMoney",""));
        traceOrderBean.setBetMoneyRate(jsonObject.optString("betMoneyRate",""));
        traceOrderBean.setBetWinMoney(jsonObject.optString("betWinMoney",""));
        traceOrderBean.setGameName(jsonObject.optString("gameName",""));
        traceOrderBean.setLotteryName(jsonObject.optString("lotteryName",""));
        traceOrderBean.setOpenCount(jsonObject.optString("openCount",""));
        traceOrderBean.setOrderNumber(jsonObject.optString("orderNumber",""));
        traceOrderBean.setOrdreId(jsonObject.optString("ordreId",""));
        traceOrderBean.setStartTraceQishu(jsonObject.optString("startTraceQishu",""));
        traceOrderBean.setStartTraceTime(jsonObject.optString("startTraceTime",""));
        traceOrderBean.setStatus(jsonObject.optString("status",""));
        traceOrderBean.setStatusValue(jsonObject.optString("statusValue",""));
        traceOrderBean.setTraceCountRate(jsonObject.optString("traceCountRate",""));
        traceOrderBean.setTraceMoney(jsonObject.optString("traceMoney",""));
        traceOrderBean.setTraceWinStop(jsonObject.optString("traceWinStop",""));
        traceOrderBean.setUserName(jsonObject.optString("userName",""));
        return traceOrderBean;
    }
}
