package com.lottery.biying.bean;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/27.
 */
public class LotteryPlaytypeBean {
    public String menuCode,remark,gameGroupCode,maxWinnerMoney,minSelectNum,minWinnerMoney,showCount,titleCode,title;
    public int pxh,id,isEnable;

    public int index1,index2;

    private String winMoneyDes;
    private String winMoney1;
    private String winMoney2;
    private String winMoney3;
    private String numSelectDes;
    private String singleBetMoney;
    private String showWinMoney;

    public String getWinMoneyDes() {
        return winMoneyDes;
    }

    public void setWinMoneyDes(String winMoneyDes) {
        this.winMoneyDes = winMoneyDes;
    }

    public String getWinMoney1() {
        return winMoney1;
    }

    public void setWinMoney1(String winMoney1) {
        this.winMoney1 = winMoney1;
    }

    public String getWinMoney2() {
        return winMoney2;
    }

    public void setWinMoney2(String winMoney2) {
        this.winMoney2 = winMoney2;
    }

    public String getWinMoney3() {
        return winMoney3;
    }

    public void setWinMoney3(String winMoney3) {
        this.winMoney3 = winMoney3;
    }

    public String getNumSelectDes() {
        return numSelectDes;
    }

    public void setNumSelectDes(String numSelectDes) {
        this.numSelectDes = numSelectDes;
    }

    public String getSingleBetMoney() {
        return singleBetMoney;
    }

    public void setSingleBetMoney(String singleBetMoney) {
        this.singleBetMoney = singleBetMoney;
    }

    public String getShowWinMoney() {
        return showWinMoney;
    }

    public void setShowWinMoney(String showWinMoney) {
        this.showWinMoney = showWinMoney;
    }

    public String getGameGroupCode() {
        return gameGroupCode;
    }

    public void setGameGroupCode(String gameGroupCode) {
        this.gameGroupCode = gameGroupCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String getMaxWinnerMoney() {
        return maxWinnerMoney;
    }

    public void setMaxWinnerMoney(String maxWinnerMoney) {
        this.maxWinnerMoney = maxWinnerMoney;
    }

    public String getMinSelectNum() {
        return minSelectNum;
    }

    public void setMinSelectNum(String minSelectNum) {
        this.minSelectNum = minSelectNum;
    }

    public String getMinWinnerMoney() {
        return minWinnerMoney;
    }

    public void setMinWinnerMoney(String minWinnerMoney) {
        this.minWinnerMoney = minWinnerMoney;
    }

    public String getShowCount() {
        return showCount;
    }

    public void setShowCount(String showCount) {
        this.showCount = showCount;
    }

    public int getIndex1() {
        return index1;
    }

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public int getIndex2() {
        return index2;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public int getPxh() {
        return pxh;
    }

    public void setPxh(int pxh) {
        this.pxh = pxh;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public static LotteryPlaytypeBean HandlerJson(JSONObject jsonObject)
    {
        LotteryPlaytypeBean bean=new LotteryPlaytypeBean();
        bean.setRemark(jsonObject.optString("remark", ""));
        bean.setPxh(jsonObject.optInt("pxh", 100));
        bean.setMenuCode(jsonObject.optString("menuCode", ""));
        bean.setGameGroupCode(jsonObject.optString("gameGroupCode", ""));
        bean.setId(jsonObject.optInt("id", 9));
        bean.setIsEnable(jsonObject.optInt("isEnable", 0));
        bean.setMaxWinnerMoney(jsonObject.optString("maxWinnerMoney", ""));
        bean.setMinSelectNum(jsonObject.optString("minSelectNum", ""));
        bean.setMinWinnerMoney(jsonObject.optString("minWinnerMoney", ""));
        bean.setShowCount(jsonObject.optString("showCount", ""));
        return bean;
    }
}
