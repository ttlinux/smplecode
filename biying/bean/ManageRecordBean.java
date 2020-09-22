package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/5.
 */
public class ManageRecordBean {


    /**
     * betBack : 1.20
     * createTime : 2018-05-04 14:39:48
     * dailySalary :
     * id : 19
     * lastLoginTime :
     * onLineStatus :
     * parentBack : 12.50
     * remark :
     * teamCount : 1
     * teamMoney : 0.00
     * userByName :
     * userMoney : 0.00
     * userName : haha123
     * userType : 代理
     * userTypeValue : 1
     */

    private String betBack;
    private String createTime;
    private String dailySalary;
    private String id;
    private String lastLoginTime;
    private String onLineStatus;
    private String operateFlag;
    private String parentBack;
    private String remark;
    private String teamCount;
    private String teamMoney;
    private String userByName;
    private String userMoney;
    private String userName;
    private String userType;
    private String userTypeValue;
    private String salaryMoney;
    private String salaryFlag;
    private String salaryInfo;
    private String clickFlag;


    public String getClickFlag() {
        return clickFlag;
    }

    public void setClickFlag(String clickFlag) {
        this.clickFlag = clickFlag;
    }

    public String getSalaryInfo() {
        return salaryInfo;
    }

    public void setSalaryInfo(String salaryInfo) {
        this.salaryInfo = salaryInfo;
    }

    public String getSalaryFlag() {
        return salaryFlag;
    }

    public void setSalaryFlag(String salaryFlag) {
        this.salaryFlag = salaryFlag;
    }

    public String getSalaryMoney() {
        return salaryMoney;
    }

    public void setSalaryMoney(String salaryMoney) {
        this.salaryMoney = salaryMoney;
    }

    public String getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(String operateFlag) {
        this.operateFlag = operateFlag;
    }

    public String getBetBack() {
        return betBack;
    }

    public void setBetBack(String betBack) {
        this.betBack = betBack;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDailySalary() {
        return dailySalary;
    }

    public void setDailySalary(String dailySalary) {
        this.dailySalary = dailySalary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getOnLineStatus() {
        return onLineStatus;
    }

    public void setOnLineStatus(String onLineStatus) {
        this.onLineStatus = onLineStatus;
    }

    public String getParentBack() {
        return parentBack;
    }

    public void setParentBack(String parentBack) {
        this.parentBack = parentBack;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(String teamCount) {
        this.teamCount = teamCount;
    }

    public String getTeamMoney() {
        return teamMoney;
    }

    public void setTeamMoney(String teamMoney) {
        this.teamMoney = teamMoney;
    }

    public String getUserByName() {
        return userByName;
    }

    public void setUserByName(String userByName) {
        this.userByName = userByName;
    }

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeValue() {
        return userTypeValue;
    }

    public void setUserTypeValue(String userTypeValue) {
        this.userTypeValue = userTypeValue;
    }

    public static ManageRecordBean Analysis(JSONObject jsonObject)
    {
        ManageRecordBean bean=new ManageRecordBean();
        bean.setBetBack(jsonObject.optString("betBack", ""));
        bean.setCreateTime(jsonObject.optString("createTime", ""));
        bean.setDailySalary(jsonObject.optString("dailySalary", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setLastLoginTime(jsonObject.optString("lastLoginTime", ""));
        bean.setOnLineStatus(jsonObject.optString("onLineStatus", ""));
        bean.setParentBack(jsonObject.optString("parentBack", ""));
        bean.setRemark(jsonObject.optString("remark", ""));
        bean.setTeamCount(jsonObject.optString("teamCount", ""));
        bean.setTeamMoney(jsonObject.optString("teamMoney", ""));
        bean.setUserByName(jsonObject.optString("userByName", ""));
        bean.setUserMoney(jsonObject.optString("userMoney", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setUserType(jsonObject.optString("userType", ""));
        bean.setUserTypeValue(jsonObject.optString("userTypeValue", ""));
        bean.setOperateFlag(jsonObject.optString("operateFlag", ""));
        bean.setSalaryFlag(jsonObject.optString("salaryFlag", ""));
        bean.setSalaryMoney(jsonObject.optString("salaryMoney",""));
        bean.setSalaryInfo(jsonObject.optString("salaryInfo",""));
        bean.setClickFlag(jsonObject.optString("clickFlag",""));
        return  bean;
    }

    public static ManageRecordBean Analysis_local(JSONObject jsonObject)
    {
        ManageRecordBean bean=new ManageRecordBean();
        bean.setBetBack(jsonObject.optString("betBack", ""));
        bean.setCreateTime(jsonObject.optString("createTime", ""));
        bean.setDailySalary(jsonObject.optString("dailySalary", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setLastLoginTime(jsonObject.optString("lastLoginTime", ""));
        bean.setOnLineStatus(jsonObject.optString("onLineStatus", ""));
        bean.setParentBack(jsonObject.optString("parentBack", ""));
        bean.setRemark(jsonObject.optString("remark", ""));
        bean.setTeamCount(jsonObject.optString("teamCount", ""));
        bean.setTeamMoney(jsonObject.optString("teamMoney", ""));
        bean.setUserByName(jsonObject.optString("userByName", ""));
        bean.setUserMoney(jsonObject.optString("userMoney", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setUserType(jsonObject.optString("userType", ""));
        bean.setUserTypeValue(jsonObject.optString("userTypeValue", ""));
        bean.setOperateFlag(jsonObject.optString("operateFlag", ""));
        bean.setSalaryFlag(jsonObject.optString("salaryFlag", ""));
        bean.setSalaryMoney(jsonObject.optString("salaryMoney", ""));
        bean.setSalaryInfo(jsonObject.optString("salaryInfo",""));
        bean.setClickFlag(jsonObject.optString("clickFlag",""));
        return  bean;
    }
}
