package com.lottery.biying.bean;

/**
 * Created by Administrator on 2018/4/14.
 */
public class SmartTraceBean {

    boolean select;
    int index;
    String qihao;
    int times;//投注倍数
    String betsmoney;//累计投注
    String winmoney;
    String percentofwinmoney;
    String GameCode;
    double unit;
    int bettimes;
    int BounsType;
    String Content;
    String qs;

    public String getQs() {
        return qs;
    }

    public void setQs(String qs) {
        this.qs = qs;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getBounsType() {
        return BounsType;
    }

    public void setBounsType(int bounsType) {
        BounsType = bounsType;
    }

    public int getBettimes() {
        return bettimes;
    }

    public void setBettimes(int bettimes) {
        this.bettimes = bettimes;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public String getGameCode() {
        return GameCode;
    }

    public void setGameCode(String gameCode) {
        GameCode = gameCode;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getQihao() {
        return qihao;
    }

    public void setQihao(String qihao) {
        this.qihao = qihao;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getBetsmoney() {
        return betsmoney;
    }

    public void setBetsmoney(String betsmoney) {
        this.betsmoney = betsmoney;
    }

    public String getWinmoney() {
        return winmoney;
    }

    public void setWinmoney(String winmoney) {
        this.winmoney = winmoney;
    }

    public String getPercentofwinmoney() {
        return percentofwinmoney;
    }

    public void setPercentofwinmoney(String percentofwinmoney) {
        this.percentofwinmoney = percentofwinmoney;
    }
}
