package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/3.
 */
public class PersonalIncomBean {


    /**
     * betAmount : 1082.0
     * betBack : 0.0
     * date : 2018-05-02
     * depositAmount : 0.0
     * drawAmount : 0.0
     * huoDongAmount : 0.0
     * testValue : 1
     * totalProfit : -1082.0
     * userName : dunaifen
     * winAmount : 0.0
     */

    private String betAmount;
    private String betBack;
    private String date;
    private String depositAmount;
    private String drawAmount;
    private String huoDongAmount;
    private String testValue;
    private String totalProfit;
    private String userName;
    private String winAmount;
    private String betFlat;
    private String betInAmout;
    private String betPayout;
    private String withdrawAmount;

    public String getBetFlat() {
        return betFlat;
    }

    public void setBetFlat(String betFlat) {
        this.betFlat = betFlat;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getBetPayout() {
        return betPayout;
    }

    public void setBetPayout(String betPayout) {
        this.betPayout = betPayout;
    }

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

    public static PersonalIncomBean Analysis(JSONObject jsonObject)
    {
        PersonalIncomBean bean=new PersonalIncomBean();
        bean.setBetAmount(jsonObject.optString("betAmount",""));
        bean.setBetBack(jsonObject.optString("betBack", ""));
        bean.setDate(jsonObject.optString("date", ""));
        bean.setDepositAmount(jsonObject.optString("depositAmount", ""));
        bean.setDrawAmount(jsonObject.optString("drawAmount", ""));
        bean.setHuoDongAmount(jsonObject.optString("huoDongAmount", ""));
        bean.setTestValue(jsonObject.optString("testValue", ""));
        bean.setTotalProfit(jsonObject.optString("totalProfit", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setWinAmount(jsonObject.optString("winAmount", ""));

        bean.setBetFlat(jsonObject.optString("betFlat", ""));
        bean.setBetInAmout(jsonObject.optString("betInAmout", ""));
        bean.setBetPayout(jsonObject.optString("betPayout", ""));
        bean.setWithdrawAmount(jsonObject.optString("withdrawAmount", ""));

        return bean;

    }
}
