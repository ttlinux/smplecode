package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/9.
 */
public class WithDrawBean {


    /**
     * bankAddress : jskdjskajsj
     * bankCard : 02939*******9283
     * bankCnName : 农业银行
     * bankType : abc
     * bigPicUrl : http://res.6820168.com/m/site/e0/bankicon/abc_big.png
     * id : 10
     * maxPay : 0
     * minMaxDes : 最低金额1.0元
     * minPay : 1
     * smallPicUrl : http://res.6820168.com/m/site/e0/bankicon/abc_small.png
     * userName : nihaoma
     * userRealName : **丰
     */

    private String bankAddress;
    private String bankCard;
    private String bankCnName;
    private String bankType;
    private String bigPicUrl;
    private String  id;
    private double maxPay;
    private String minMaxDes;
    private double minPay;
    private String smallPicUrl;
    private String userName;
    private String userRealName;

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCnName() {
        return bankCnName;
    }

    public void setBankCnName(String bankCnName) {
        this.bankCnName = bankCnName;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBigPicUrl() {
        return bigPicUrl;
    }

    public void setBigPicUrl(String bigPicUrl) {
        this.bigPicUrl = bigPicUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(double maxPay) {
        this.maxPay = maxPay;
    }

    public String getMinMaxDes() {
        return minMaxDes;
    }

    public void setMinMaxDes(String minMaxDes) {
        this.minMaxDes = minMaxDes;
    }

    public double getMinPay() {
        return minPay;
    }

    public void setMinPay(double minPay) {
        this.minPay = minPay;
    }

    public String getSmallPicUrl() {
        return smallPicUrl;
    }

    public void setSmallPicUrl(String smallPicUrl) {
        this.smallPicUrl = smallPicUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public static WithDrawBean Analysis(JSONObject jsonObject)
    {
        WithDrawBean bean=new WithDrawBean();
        bean.setBankAddress(jsonObject.optString("bankAddress",""));
        bean.setBankCard(jsonObject.optString("bankCard", ""));
        bean.setBankCnName(jsonObject.optString("bankCnName", ""));
        bean.setBankType(jsonObject.optString("bankType", ""));
        bean.setBigPicUrl(jsonObject.optString("bigPicUrl", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setMaxPay(jsonObject.optDouble("maxPay", 0));
        bean.setMinMaxDes(jsonObject.optString("minMaxDes", ""));
        bean.setMinPay(jsonObject.optDouble("minPay", 0));
        bean.setSmallPicUrl(jsonObject.optString("smallPicUrl", ""));
        bean.setUserName(jsonObject.optString("userName",""));
        bean.setUserRealName(jsonObject.optString("userRealName",""));
        return  bean;
    }

    public static WithDrawBean Analysis_local(JSONObject jsonObject)
    {
        WithDrawBean bean=new WithDrawBean();
        bean.setBankAddress(jsonObject.optString("bankAddress",""));
        bean.setBankCard(jsonObject.optString("bankCard", ""));
        bean.setBankCnName(jsonObject.optString("bankCnName", ""));
        bean.setBankType(jsonObject.optString("bankType", ""));
        bean.setBigPicUrl(jsonObject.optString("bigPicUrl", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setMaxPay(jsonObject.optDouble("maxPay", 0));
        bean.setMinMaxDes(jsonObject.optString("minMaxDes", ""));
        bean.setMinPay(jsonObject.optDouble("minPay", 0));
        bean.setSmallPicUrl(jsonObject.optString("smallPicUrl", ""));
        bean.setUserName(jsonObject.optString("userName",""));
        bean.setUserRealName(jsonObject.optString("userRealName",""));
        return  bean;
    }
}
