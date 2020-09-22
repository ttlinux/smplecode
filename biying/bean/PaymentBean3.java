package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/1. 转换记录
 */
public class PaymentBean3 {

    private String eduOrder;
    private String eduForwardRemark;
    private String createTime;
    boolean itemstate = false;
    int eduStatus, eduType;
    private String flatName;
    private double eduPoints;
    private String remark;
    private String eduStatusDes;

    public String getEduStatusDes() {
        return eduStatusDes;
    }

    public void setEduStatusDes(String eduStatusDes) {
        this.eduStatusDes = eduStatusDes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlatName() {
        return flatName;
    }

    public void setFlatName(String flatName) {
        this.flatName = flatName;
    }

    public int getEduType() {
        return eduType;
    }

    public void setEduType(int eduType) {
        this.eduType = eduType;
    }

    public int getEduStatus() {
        return eduStatus;
    }

    public void setEduStatus(int eduStatus) {
        this.eduStatus = eduStatus;
    }

    public double getEduPoints() {
        return eduPoints;
    }

    public void setEduPoints(double eduPoints) {
        this.eduPoints = eduPoints;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEduForwardRemark() {
        return eduForwardRemark;
    }

    public void setEduForwardRemark(String eduForwardRemark) {
        this.eduForwardRemark = eduForwardRemark;
    }

    public String getEduOrder() {
        return eduOrder;
    }

    public void setEduOrder(String eduOrder) {
        this.eduOrder = eduOrder;
    }

//    public String getEduStatusStr() {
//        return eduStatusStr;
//    }
//
//    public void setEduStatusStr(String eduStatusStr) {
//        this.eduStatusStr = eduStatusStr;
//    }

//    public String getEduTypeStr() {
//        return eduTypeStr;
//    }
//
//    public void setEduTypeStr(String eduTypeStr) {
//        this.eduTypeStr = eduTypeStr;
//    }

    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }

    public static PaymentBean3 Analysis(JSONObject jsobj) {
        PaymentBean3 bean = new PaymentBean3();
        bean.setCreateTime(jsobj.optString("createTime", ""));
        bean.setEduForwardRemark(jsobj.optString("eduForwardRemark", ""));
        bean.setEduOrder(jsobj.optString("eduOrder", ""));
        bean.setEduPoints(jsobj.optDouble("eduPoints", 0.0));
        bean.setEduType(jsobj.optInt("eduType", 1));
        bean.setEduStatus(jsobj.optInt("eduStatus", 1));
        bean.setFlatName(jsobj.optString("flatName", ""));
        bean.setRemark(jsobj.optString("remark", ""));
        bean.setEduStatusDes(jsobj.optString("eduStatusDes", ""));
        return bean;
    }

    public static PaymentBean3 Analysis_local(JSONObject jsobj) {
        PaymentBean3 bean = new PaymentBean3();
        bean.setCreateTime(jsobj.optString("createTime", ""));
        bean.setEduForwardRemark(jsobj.optString("eduForwardRemark", ""));
        bean.setEduOrder(jsobj.optString("eduOrder", ""));
        bean.setEduPoints(jsobj.optDouble("eduPoints", 0.0));
        bean.setEduType(jsobj.optInt("eduType", 1));
        bean.setEduStatus(jsobj.optInt("eduStatus", 1));
        bean.setFlatName(jsobj.optString("flatName", ""));
        bean.setRemark(jsobj.optString("remark", ""));
        bean.setEduStatusDes(jsobj.optString("eduStatusDes", ""));
        return bean;
    }
}
