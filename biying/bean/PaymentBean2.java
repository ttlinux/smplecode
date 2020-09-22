package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/1. 提现记录
 */
public class PaymentBean2 {

    private String checkTime;
    private String createTime;
    private String remark;
    private String userOrder;
    private String withdrawStatus;
    private String withdrawTypeStr;
    private String statusDes;
    private String checkStatusDes;
    private String withdrawTypeDes;
    private String userWithdrawRealMoney;

    private int check_status;

    public String getUserWithdrawRealMoney() {
        return userWithdrawRealMoney;
    }

    public void setUserWithdrawRealMoney(String userWithdrawRealMoney) {
        this.userWithdrawRealMoney = userWithdrawRealMoney;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public String getCheckStatusDes() {
        return checkStatusDes;
    }

    public void setCheckStatusDes(String checkStatusDes) {
        this.checkStatusDes = checkStatusDes;
    }

    public String getWithdrawTypeDes() {
        return withdrawTypeDes;
    }

    public void setWithdrawTypeDes(String withdrawTypeDes) {
        this.withdrawTypeDes = withdrawTypeDes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
    public int getCheck_status() {
        return check_status;
    }

    public void setCheck_status(int check_status) {
        this.check_status = check_status;
    }



    public double getUserWithdrawMoney() {
        return userWithdrawMoney;
    }

    public void setUserWithdrawMoney(double userWithdrawMoney) {
        this.userWithdrawMoney = userWithdrawMoney;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(String userOrder) {
        this.userOrder = userOrder;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getWithdrawTypeStr() {
        return withdrawTypeStr;
    }

    public void setWithdrawTypeStr(String withdrawTypeStr) {
        this.withdrawTypeStr = withdrawTypeStr;
    }

    private double userWithdrawMoney;

    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }

    boolean itemstate=false;

    public static PaymentBean2 Analysis(JSONObject jsobj)
    {
        PaymentBean2 bean = new PaymentBean2();
        bean.setCheckTime(jsobj.optString("checkTime", ""));
        bean.setCreateTime(jsobj.optString("createTime", ""));
        bean.setRemark(jsobj.optString("remark", ""));
        bean.setUserOrder(jsobj.optString("userOrder", ""));
        bean.setUserWithdrawMoney(jsobj.optDouble("userWithdrawMoney", 0.0));
        bean.setWithdrawTypeStr(jsobj.optString("withdrawTypeStr", ""));
        bean.setWithdrawStatus(jsobj.optString("withdrawStatus", ""));
        bean.setCheck_status(jsobj.optInt("checkStatus", 0));
        bean.setStatus(jsobj.optInt("status", 0));
        bean.setCheckStatusDes(jsobj.optString("checkStatusDes", ""));
        bean.setStatusDes(jsobj.optString("statusDes", ""));
        bean.setWithdrawTypeDes(jsobj.optString("withdrawTypeDes", ""));
        bean.setUserWithdrawRealMoney(jsobj.optString("userWithdrawRealMoney",""));
        return bean;
    }

    public static PaymentBean2 Analysis_local(JSONObject jsobj)
    {
        PaymentBean2 bean = new PaymentBean2();
        bean.setCheckTime(jsobj.optString("checkTime", ""));
        bean.setCreateTime(jsobj.optString("createTime", ""));
        bean.setRemark(jsobj.optString("remark", ""));
        bean.setUserOrder(jsobj.optString("userOrder", ""));
        bean.setUserWithdrawMoney(jsobj.optDouble("userWithdrawMoney", 0.0));
        bean.setWithdrawTypeStr(jsobj.optString("withdrawTypeStr", ""));
        bean.setWithdrawStatus(jsobj.optString("withdrawStatus", ""));
        bean.setCheck_status(jsobj.optInt("checkStatus", 0));
        bean.setStatus(jsobj.optInt("status", 0));
        bean.setCheckStatusDes(jsobj.optString("checkStatusDes", ""));
        bean.setStatusDes(jsobj.optString("statusDes", ""));
        bean.setWithdrawTypeDes(jsobj.optString("withdrawTypeDes", ""));
        bean.setUserWithdrawRealMoney(jsobj.optString("userWithdrawRealMoney",""));
        return bean;
    }
}
