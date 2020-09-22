package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/4.
 */
public class DailySalaryitemBean {


    /**
     * betMoney : 2.0
     * date : 2018-04-18
     * id : 2
     * lossCount : 盈利
     * lossCountValue : 1
     * personCount : 2
     * salaryAmount : 4.0
     * salaryMoney : 2.0
     * userName : dunaifen
     */

    private String betMoney;
    private String date;
    private String id;
    private String lossCount;
    private String lossCountValue;
    private String personCount;
    private String salaryAmount;
    private String salaryMoney;
    private String userName;
    private String status;
    private String statusValue;

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

    public String getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(String betMoney) {
        this.betMoney = betMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(String salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public String getSalaryMoney() {
        return salaryMoney;
    }

    public void setSalaryMoney(String salaryMoney) {
        this.salaryMoney = salaryMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static DailySalaryitemBean Analysis(JSONObject jsonObject) {
        DailySalaryitemBean bean = new DailySalaryitemBean();
        bean.setBetMoney(jsonObject.optString("betMoney", ""));
        bean.setDate(jsonObject.optString("date", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setLossCount(jsonObject.optString("lossCount", ""));
        bean.setLossCountValue(jsonObject.optString("lossCountValue", ""));
        bean.setPersonCount(jsonObject.optString("personCount", ""));
        bean.setSalaryAmount(jsonObject.optString("salaryAmount", ""));
        bean.setSalaryMoney(jsonObject.optString("salaryMoney", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setStatusValue(jsonObject.optString("statusValue", ""));
        bean.setStatus(jsonObject.optString("status", ""));
        return bean;
    }

    public static DailySalaryitemBean Analysis_local(JSONObject jsonObject) {
        DailySalaryitemBean bean = new DailySalaryitemBean();
        bean.setBetMoney(jsonObject.optString("betMoney", ""));
        bean.setDate(jsonObject.optString("date", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setLossCount(jsonObject.optString("lossCount", ""));
        bean.setLossCountValue(jsonObject.optString("lossCountValue", ""));
        bean.setPersonCount(jsonObject.optString("personCount", ""));
        bean.setSalaryAmount(jsonObject.optString("salaryAmount", ""));
        bean.setSalaryMoney(jsonObject.optString("salaryMoney", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setStatusValue(jsonObject.optString("statusValue", ""));
        bean.setStatus(jsonObject.optString("status", ""));
        return bean;
    }
}
