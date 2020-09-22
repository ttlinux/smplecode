package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/4.
 */
public class GeneralizeLinkBean {


    /**
     * code :
     * createTime : 2018-05-03 15:56:09
     * extAddress : lao tie mei mao bing
     * id : 18
     * rebateRatio : 12.5
     * registAddress : http://www.lottery.com
     * registNum : 0
     * userName : dunaifen
     * userType : 会员
     * userTypeValue : 0
     * validTime : 4天
     * wxAddress : http://www.lottery.com
     * xuhao : 1
     */

    private String code;
    private String createTime;
    private String extAddress;
    private String id;
    private String rebateRatio;
    private String registAddress;
    private String registNum;
    private String userName;
    private String userType;
    private String userTypeValue;
    private String validTime;
    private String wxAddress;
    private String xuhao;
    private String qq;
    private String wxFlag;
    private String skype;
    private String wx;

    private String electronicRatio;
    private String sportRatio;
    private String twoSidesRatio;
    private String liveRatio;
    private String fishRatio;

    public String getFishRatio() {
        return fishRatio;
    }

    public void setFishRatio(String fishRatio) {
        this.fishRatio = fishRatio;
    }

    public String getElectronicRatio() {
        return electronicRatio;
    }

    public void setElectronicRatio(String electronicRatio) {
        this.electronicRatio = electronicRatio;
    }

    public String getSportRatio() {
        return sportRatio;
    }

    public void setSportRatio(String sportRatio) {
        this.sportRatio = sportRatio;
    }

    public String getTwoSidesRatio() {
        return twoSidesRatio;
    }

    public void setTwoSidesRatio(String twoSidesRatio) {
        this.twoSidesRatio = twoSidesRatio;
    }

    public String getLiveRatio() {
        return liveRatio;
    }

    public void setLiveRatio(String liveRatio) {
        this.liveRatio = liveRatio;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getWxFlag() {
        return wxFlag;
    }

    public void setWxFlag(String wxFlag) {
        this.wxFlag = wxFlag;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExtAddress() {
        return extAddress;
    }

    public void setExtAddress(String extAddress) {
        this.extAddress = extAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRebateRatio() {
        return rebateRatio;
    }

    public void setRebateRatio(String rebateRatio) {
        this.rebateRatio = rebateRatio;
    }

    public String getRegistAddress() {
        return registAddress;
    }

    public void setRegistAddress(String registAddress) {
        this.registAddress = registAddress;
    }

    public String getRegistNum() {
        return registNum;
    }

    public void setRegistNum(String registNum) {
        this.registNum = registNum;
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

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getWxAddress() {
        return wxAddress;
    }

    public void setWxAddress(String wxAddress) {
        this.wxAddress = wxAddress;
    }

    public String getXuhao() {
        return xuhao;
    }

    public void setXuhao(String xuhao) {
        this.xuhao = xuhao;
    }

    public static GeneralizeLinkBean AnaLysis(JSONObject jsonObject)
    {
        GeneralizeLinkBean bean=new GeneralizeLinkBean();
        bean.setCode(jsonObject.optString("code", ""));
        bean.setCreateTime(jsonObject.optString("createTime", ""));
        bean.setExtAddress(jsonObject.optString("extAddress", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setRebateRatio(jsonObject.optString("rebateRatio", ""));
        bean.setRegistAddress(jsonObject.optString("registAddress", ""));
        bean.setRegistNum(jsonObject.optString("registNum", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setUserType(jsonObject.optString("userType", ""));
        bean.setUserTypeValue(jsonObject.optString("userTypeValue", ""));
        bean.setValidTime(jsonObject.optString("validTime", ""));
        bean.setWxAddress(jsonObject.optString("wxAddress", ""));
        bean.setXuhao(jsonObject.optString("xuhao", ""));
        bean.setQq(jsonObject.optString("qq", ""));
        bean.setWxFlag(jsonObject.optString("wxFlag", ""));
        bean.setWx(jsonObject.optString("wx", ""));
        bean.setSkype(jsonObject.optString("skype", ""));

        bean.setElectronicRatio(jsonObject.optString("electronicRatio", ""));
        bean.setSportRatio(jsonObject.optString("sportRatio", ""));
        bean.setTwoSidesRatio(jsonObject.optString("twoSidesRatio", ""));
        bean.setLiveRatio(jsonObject.optString("liveRatio", ""));
        bean.setFishRatio(jsonObject.optString("fishRatio", ""));

        return  bean;
    }

    public static GeneralizeLinkBean AnaLysis_local(JSONObject jsonObject)
    {
        GeneralizeLinkBean bean=new GeneralizeLinkBean();
        bean.setCode(jsonObject.optString("code", ""));
        bean.setCreateTime(jsonObject.optString("createTime", ""));
        bean.setExtAddress(jsonObject.optString("extAddress", ""));
        bean.setId(jsonObject.optString("id", ""));
        bean.setRebateRatio(jsonObject.optString("rebateRatio", ""));
        bean.setRegistAddress(jsonObject.optString("registAddress", ""));
        bean.setRegistNum(jsonObject.optString("registNum", ""));
        bean.setUserName(jsonObject.optString("userName", ""));
        bean.setUserType(jsonObject.optString("userType", ""));
        bean.setUserTypeValue(jsonObject.optString("userTypeValue", ""));
        bean.setValidTime(jsonObject.optString("validTime", ""));
        bean.setWxAddress(jsonObject.optString("wxAddress", ""));
        bean.setXuhao(jsonObject.optString("xuhao",""));
        bean.setQq(jsonObject.optString("qq",""));
        bean.setWxFlag(jsonObject.optString("wxFlag",""));
        bean.setWx(jsonObject.optString("wx", ""));
        bean.setSkype(jsonObject.optString("skype", ""));
        bean.setElectronicRatio(jsonObject.optString("electronicRatio", ""));
        bean.setSportRatio(jsonObject.optString("sportRatio", ""));
        bean.setTwoSidesRatio(jsonObject.optString("twoSidesRatio", ""));
        bean.setLiveRatio(jsonObject.optString("liveRatio", ""));
        bean.setFishRatio(jsonObject.optString("fishRatio", ""));
        return  bean;
    }
}
