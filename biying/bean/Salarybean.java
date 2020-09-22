package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/4.
 */
public class Salarybean {


    /**
     * createTime : 2018-04-23 04:37:22
     * id : 5
     * lossCount : 无
     * lossCountValue : 0
     * modifyTime : 2018-04-23 04:37:22
     * moneyCount : 11.0
     * moneyCountValue : 11.0
     * personCount : 1
     * personCountValue : 1
     * privodeFangshi : 阶梯模式
     * privodeFangshiValue : 0
     * privodezhouqi : 按日发放
     * privodezhouqiValue : 0
     * salaryMoney : 10.0
     * startMoney : 24.0
     * userName : dunaifen
     */

    private String createTime;
    private String id;
    private String lossCount;
    private String lossCountValue;
    private String modifyTime;
    private String moneyCount;
    private String moneyCountValue;
    private String personCount;
    private String personCountValue;
    private String privodeFangshi;
    private String privodeFangshiValue;
    private String privodezhouqi;
    private String privodezhouqiValue;
    private String salaryMoney;
    private String startMoney;
    private String userName;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLossCount() {
        return lossCount;
    }

    public void setLossCount(String lossCount) {
        this.lossCount = lossCount;
    }

    public String getLossCountValue() {
        return lossCountValue;
    }

    public void setLossCountValue(String lossCountValue) {
        this.lossCountValue = lossCountValue;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getMoneyCountValue() {
        return moneyCountValue;
    }

    public void setMoneyCountValue(String moneyCountValue) {
        this.moneyCountValue = moneyCountValue;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getPersonCountValue() {
        return personCountValue;
    }

    public void setPersonCountValue(String personCountValue) {
        this.personCountValue = personCountValue;
    }

    public String getPrivodeFangshi() {
        return privodeFangshi;
    }

    public void setPrivodeFangshi(String privodeFangshi) {
        this.privodeFangshi = privodeFangshi;
    }

    public String getPrivodeFangshiValue() {
        return privodeFangshiValue;
    }

    public void setPrivodeFangshiValue(String privodeFangshiValue) {
        this.privodeFangshiValue = privodeFangshiValue;
    }

    public String getPrivodezhouqi() {
        return privodezhouqi;
    }

    public void setPrivodezhouqi(String privodezhouqi) {
        this.privodezhouqi = privodezhouqi;
    }

    public String getPrivodezhouqiValue() {
        return privodezhouqiValue;
    }

    public void setPrivodezhouqiValue(String privodezhouqiValue) {
        this.privodezhouqiValue = privodezhouqiValue;
    }

    public String getSalaryMoney() {
        return salaryMoney;
    }

    public void setSalaryMoney(String salaryMoney) {
        this.salaryMoney = salaryMoney;
    }

    public String getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(String startMoney) {
        this.startMoney = startMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static Salarybean Analysis(JSONObject jsonObject)
    {
        Salarybean bean=new Salarybean();
        bean.setCreateTime(jsonObject.optString("createTime", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setLossCountValue(jsonObject.optString("lossCountValue",""));
        bean.setLossCount(jsonObject.optString("lossCount",""));
        bean.setModifyTime(jsonObject.optString("modifyTime",""));
        bean.setMoneyCount(jsonObject.optString("moneyCount",""));
        bean.setMoneyCountValue(jsonObject.optString("moneyCountValue",""));
        bean.setPersonCount(jsonObject.optString("personCount",""));
        bean.setPersonCountValue(jsonObject.optString("personCountValue",""));
        bean.setPrivodeFangshi(jsonObject.optString("privodeFangshi",""));
        bean.setPrivodeFangshiValue(jsonObject.optString("privodeFangshiValue",""));
        bean.setPrivodezhouqi(jsonObject.optString("privodezhouqi",""));
        bean.setPrivodezhouqiValue(jsonObject.optString("privodezhouqiValue",""));
        bean.setSalaryMoney(jsonObject.optString("salaryMoney",""));
        bean.setStartMoney(jsonObject.optString("startMoney",""));
        bean.setUserName(jsonObject.optString("userName",""));
        return bean;

    }

    public static Salarybean Analysis_local(JSONObject jsonObject)
    {
        Salarybean bean=new Salarybean();
        bean.setCreateTime(jsonObject.optString("createTime", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setLossCountValue(jsonObject.optString("lossCountValue",""));
        bean.setLossCount(jsonObject.optString("lossCount",""));
        bean.setModifyTime(jsonObject.optString("modifyTime",""));
        bean.setMoneyCount(jsonObject.optString("moneyCount",""));
        bean.setMoneyCountValue(jsonObject.optString("moneyCountValue",""));
        bean.setPersonCount(jsonObject.optString("personCount",""));
        bean.setPersonCountValue(jsonObject.optString("personCountValue",""));
        bean.setPrivodeFangshi(jsonObject.optString("privodeFangshi",""));
        bean.setPrivodeFangshiValue(jsonObject.optString("privodeFangshiValue",""));
        bean.setPrivodezhouqi(jsonObject.optString("privodezhouqi",""));
        bean.setPrivodezhouqiValue(jsonObject.optString("privodezhouqiValue",""));
        bean.setSalaryMoney(jsonObject.optString("salaryMoney",""));
        bean.setStartMoney(jsonObject.optString("startMoney",""));
        bean.setUserName(jsonObject.optString("userName",""));
        return bean;

    }
}
