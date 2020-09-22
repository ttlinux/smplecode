package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/3.
 */
public class TeamIncomeBean {


    /**
     * betAmount : 14291.0
     * betBack : 0.0
     * clickFlag : 0
     * date : ----
     * depositAmount : 0.0
     * drawAmount : 0.0
     * huoDongAmount : 0.0
     * testValue : 1
     * totalProfit : -14091.0
     * userName : dunaifen
     * winAmount : 200.0
     */

    private String betAmount;
    private String betBack;
    private String clickFlag;
    private String date;
    private String depositAmount;
    private String drawAmount;
    private String huoDongAmount;
    private String testValue;
    private String totalProfit;
    private String userName;
    private String winAmount;
    private String betInAmout;

    public String getBetInAmout() {
        return betInAmout;
    }

    public void setBetInAmout(String betInAmout) {
        this.betInAmout = betInAmout;
    }

    public String getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(String betAmount) {
        this.betAmount = betAmount;
    }

    public String getBetBack() {
        return betBack;
    }

    public void setBetBack(String betBack) {
        this.betBack = betBack;
    }

    public String getClickFlag() {
        return clickFlag;
    }

    public void setClickFlag(String clickFlag) {
        this.clickFlag = clickFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(String drawAmount) {
        this.drawAmount = drawAmount;
    }

    public String getHuoDongAmount() {
        return huoDongAmount;
    }

    public void setHuoDongAmount(String huoDongAmount) {
        this.huoDongAmount = huoDongAmount;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(String winAmount) {
        this.winAmount = winAmount;
    }

    public static TeamIncomeBean Analysis(JSONObject jsonObject)
    {
        TeamIncomeBean bean=new TeamIncomeBean();
        bean.setBetAmount(jsonObject.optString("betAmount", ""));
        bean.setBetBack(jsonObject.optString("betBack", ""));
        bean.setClickFlag(jsonObject.optString("clickFlag", ""));
        bean.setDate(jsonObject.optString("date", ""));
        bean.setDepositAmount(jsonObject.optString("depositAmount", ""));
        bean.setDrawAmount(jsonObject.optString("drawAmount", ""));
        bean.setHuoDongAmount(jsonObject.optString("huoDongAmount", ""));
        bean.setTestValue(jsonObject.optString("testValue", ""));
        bean.setTotalProfit(jsonObject.optString("totalProfit", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setWinAmount(jsonObject.optString("winAmount",""));
        bean.setBetInAmout(jsonObject.optString("betInAmout", ""));
        return bean;

    }
}
