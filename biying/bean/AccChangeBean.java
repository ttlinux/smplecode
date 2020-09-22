package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/2.
 */
public class AccChangeBean {


    /**
     * changeMoney : 100.0
     * changeTime : 2018-05-01 05:29:40
     * changeType : 奖金派送
     * changeTypeValue : 4
     * gameName : 大小单双
     * id : 116
     * lotteryName : 幸运28
     * model : 元
     * modelValue : 2.0
     * qihao : 885529
     * test : 收入
     * testValue : 1
     * userBalance : 999990.0
     * userName : dunaifen
     */

    private String changeMoney;
    private String changeTime;
    private String changeType;
    private String changeTypeValue;
    private String gameName;
    private String id;
    private String lotteryName;
    private String model;
    private String modelValue;
    private String qihao;
    private String test;
    private String testValue;
    private String userBalance;
    private String userName;
    private String fanganOrder;
    private String appendOrder;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFanganOrder() {
        return fanganOrder;
    }

    public void setFanganOrder(String fanganOrder) {
        this.fanganOrder = fanganOrder;
    }

    public String getAppendOrder() {
        return appendOrder;
    }

    public void setAppendOrder(String appendOrder) {
        this.appendOrder = appendOrder;
    }

    public String getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(String changeMoney) {
        this.changeMoney = changeMoney;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangeTypeValue() {
        return changeTypeValue;
    }

    public void setChangeTypeValue(String changeTypeValue) {
        this.changeTypeValue = changeTypeValue;
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

    public String getModelValue() {
        return modelValue;
    }

    public void setModelValue(String modelValue) {
        this.modelValue = modelValue;
    }

    public String getQihao() {
        return qihao;
    }

    public void setQihao(String qihao) {
        this.qihao = qihao;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static AccChangeBean AnalusisData(JSONObject jsonObject)
    {
        AccChangeBean accChangeBean=new AccChangeBean();
        accChangeBean.setChangeMoney(jsonObject.optString("changeMoney", ""));
        accChangeBean.setChangeTime(jsonObject.optString("changeTime", ""));
        accChangeBean.setChangeType(jsonObject.optString("changeType", ""));
        accChangeBean.setChangeTypeValue(jsonObject.optString("changeTypeValue", ""));
        accChangeBean.setGameName(jsonObject.optString("gameName", ""));
        accChangeBean.setId(jsonObject.optString("id", ""));
        accChangeBean.setLotteryName(jsonObject.optString("lotteryName", ""));
        accChangeBean.setModel(jsonObject.optString("model", ""));
        accChangeBean.setModelValue(jsonObject.optString("modelValue", ""));
        accChangeBean.setQihao(jsonObject.optString("qihao", ""));
        accChangeBean.setTest(jsonObject.optString("test", ""));
        accChangeBean.setTestValue(jsonObject.optString("testValue", ""));
        accChangeBean.setUserBalance(jsonObject.optString("userBalance", ""));
        accChangeBean.setUserName(jsonObject.optString("userName", ""));
        accChangeBean.setAppendOrder(jsonObject.optString("appendOrder", ""));
        accChangeBean.setFanganOrder(jsonObject.optString("fanganOrder", ""));
        accChangeBean.setRemark(jsonObject.optString("remark",""));
        return accChangeBean;
    }


    public static AccChangeBean AnalusisData_local(JSONObject jsonObject)
    {
        AccChangeBean accChangeBean=new AccChangeBean();
        accChangeBean.setChangeMoney(jsonObject.optString("changeMoney", ""));
        accChangeBean.setChangeTime(jsonObject.optString("changeTime",""));
        accChangeBean.setChangeType(jsonObject.optString("changeType",""));
        accChangeBean.setChangeTypeValue(jsonObject.optString("changeTypeValue",""));
        accChangeBean.setGameName(jsonObject.optString("gameName",""));
        accChangeBean.setId(jsonObject.optString("id",""));
        accChangeBean.setLotteryName(jsonObject.optString("lotteryName",""));
        accChangeBean.setModel(jsonObject.optString("model",""));
        accChangeBean.setModelValue(jsonObject.optString("modelValue",""));
        accChangeBean.setQihao(jsonObject.optString("qihao",""));
        accChangeBean.setTest(jsonObject.optString("test",""));
        accChangeBean.setTestValue(jsonObject.optString("testValue",""));
        accChangeBean.setUserBalance(jsonObject.optString("userBalance",""));
        accChangeBean.setUserName(jsonObject.optString("userName",""));
        accChangeBean.setAppendOrder(jsonObject.optString("appendOrder", ""));
        accChangeBean.setFanganOrder(jsonObject.optString("fanganOrder", ""));
        accChangeBean.setRemark(jsonObject.optString("remark",""));
        return accChangeBean;
    }
}
