package com.lottery.biying.bean;

/**
 * Created by Administrator on 2018/1/19.
 */
public class CaseBean {

    private int multiply;
    private int money;
    private int winmin,winmax;
    private int winminPercent,winmaxPercent;
    private String qishu;
    private String profit,winProfit;

    public String getWinProfit() {
        return winProfit;
    }

    public void setWinProfit(String winProfit) {
        this.winProfit = winProfit;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMultiply() {
        return multiply;
    }

    public void setMultiply(int multiply) {
        this.multiply = multiply;
    }

    public int getWinmax() {
        return winmax;
    }

    public void setWinmax(int winmax) {
        this.winmax = winmax;
    }

    public int getWinmaxPercent() {
        return winmaxPercent;
    }

    public void setWinmaxPercent(int winmaxPercent) {
        this.winmaxPercent = winmaxPercent;
    }

    public int getWinmin() {
        return winmin;
    }

    public void setWinmin(int winmin) {
        this.winmin = winmin;
    }

    public int getWinminPercent() {
        return winminPercent;
    }

    public void setWinminPercent(int winminPercent) {
        this.winminPercent = winminPercent;
    }
}
