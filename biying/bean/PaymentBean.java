package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/30. 充值记录
 */
public class PaymentBean {

    String hkTime;
    String hkType;
    String remark;
    String hkOrder;
    String statusDes;
    String createTime;
    int hkStatus;
    String hkCheckTime;
    int hkCheckStatus;
    int hkModel;
    String hkMoney;
    String status;

    public String getHkCheckTime() {
        return hkCheckTime;
    }

    public void setHkCheckTime(String hkCheckTime) {
        this.hkCheckTime = hkCheckTime;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public int getHkCheckStatus() {
        return hkCheckStatus;
    }

    public void setHkCheckStatus(int hkCheckStatus) {
        this.hkCheckStatus = hkCheckStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }

    boolean itemstate=false;

    public int getHkModel() {
        return hkModel;
    }

    public void setHkModel(int hkModel) {
        this.hkModel = hkModel;
    }

    public String getHkMoney() {
        return hkMoney;
    }

    public void setHkMoney(String hkMoney) {
        this.hkMoney = hkMoney;
    }

    public String getHkOrder() {
        return hkOrder;
    }

    public void setHkOrder(String hkOrder) {
        this.hkOrder = hkOrder;
    }

    public int getHkStatus() {
        return hkStatus;
    }

    public void setHkStatus(int hkStatus) {
        this.hkStatus = hkStatus;
    }

    public String getHkTime() {
        return hkTime;
    }

    public void setHkTime(String hkTime) {
        this.hkTime = hkTime;
    }

    public String getHkType() {
        return hkType;
    }

    public void setHkType(String hkType) {
        this.hkType = hkType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public static PaymentBean Analysis(JSONObject jsobj)
    {
        PaymentBean bean = new PaymentBean();
        bean.setHkModel(jsobj.optInt("hkModel", 5));
        bean.setHkMoney(jsobj.optString("hkMoney", ""));
        bean.setHkOrder(jsobj.optString("hkOrder", ""));
        bean.setHkStatus(jsobj.optInt("hkStatus", -1));
        bean.setHkTime(jsobj.optString("hkTime", ""));
        bean.setHkType(jsobj.optString("hkType", ""));
        bean.setRemark(jsobj.optString("remark", ""));
        bean.setCreateTime(jsobj.optString("createTime", ""));
        bean.setStatusDes(jsobj.optString("statusDes", ""));
//                        bean.setStatus(jsobj.optString("status", ""));
        bean.setHkCheckStatus(jsobj.optInt("hkCheckStatus", -1));
        bean.setHkCheckTime(jsobj.optString("hkCheckTime", ""));
        return bean;
    }

    public static PaymentBean Analysis_local(JSONObject jsobj)
    {
        PaymentBean bean = new PaymentBean();
        bean.setHkModel(jsobj.optInt("hkModel", 5));
        bean.setHkMoney(jsobj.optString("hkMoney", ""));
        bean.setHkOrder(jsobj.optString("hkOrder", ""));
        bean.setHkStatus(jsobj.optInt("hkStatus", -1));
        bean.setHkTime(jsobj.optString("hkTime", ""));
        bean.setHkType(jsobj.optString("hkType", ""));
        bean.setRemark(jsobj.optString("remark", ""));
        bean.setCreateTime(jsobj.optString("createTime", ""));
        bean.setStatusDes(jsobj.optString("statusDes", ""));
//                        bean.setStatus(jsobj.optString("status", ""));
        bean.setHkCheckStatus(jsobj.optInt("hkCheckStatus", -1));
        bean.setHkCheckTime(jsobj.optString("hkCheckTime", ""));
        return bean;
    }
}
