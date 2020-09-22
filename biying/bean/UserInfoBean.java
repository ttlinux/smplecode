package com.lottery.biying.bean;

import android.text.TextUtils;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/5/18.
 */
public class UserInfoBean {


    /**
     * agentDesc : 代理会员
     * betBackWater : 13
     * hasRealName : true
     * hasWithdrawPwd : true
     * isAgent : 1
     * lastLoginIp : 192.168.0.124
     * lastLoginTime : 2018-05-17 03:54:43
     * mail : **1122.com
     * mobile : 188****8888
     * nickName : wwwwwwwwwwwwwwww
     * qq : ****456
     * teamMoney : -5355980.041
     * typeDetail : {"autoUpdateTime":"2016-05-17 15:54:52","contentStatus":0,"msgContent":"","typeLevel":"代理会员分组","typeName":"代理分组","typePicName":"http://res.6820168.com/m/site/e0/member/1497666045601.png"}
     * userMoney : 9513005.04
     * userName : nihaoma
     * userRealName : *******改
     * ylxx : 汤姆克鲁斯
     */

    private String agentDesc;
    private String betBackWater;
    private boolean hasRealName;
    private boolean hasWithdrawPwd;
    private int isAgent;
    private String lastLoginIp;
    private String lastLoginTime;
    private String mail;
    private String mobile;
    private String nickName;
    private String qq;
    private String teamCount;
    private String teamMoney;
    private TypeDetailBean typeDetail;
    private String userMoney;
    private String userName;
    private String userRealName;
    private String ylxx;
    private BackWater backWater;

    public BackWater getBackWater() {
        return backWater;
    }

    public void setBackWater(BackWater backWater) {
        this.backWater = backWater;
    }

    public String getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(String teamCount) {
        this.teamCount = teamCount;
    }

    public String getAgentDesc() {
        return agentDesc;
    }

    public void setAgentDesc(String agentDesc) {
        this.agentDesc = agentDesc;
    }

    public String getBetBackWater() {
        return betBackWater;
    }

    public void setBetBackWater(String betBackWater) {
        this.betBackWater = betBackWater;
    }

    public boolean isHasRealName() {
        return hasRealName;
    }

    public void setHasRealName(boolean hasRealName) {
        this.hasRealName = hasRealName;
    }

    public boolean isHasWithdrawPwd() {
        return hasWithdrawPwd;
    }

    public void setHasWithdrawPwd(boolean hasWithdrawPwd) {
        this.hasWithdrawPwd = hasWithdrawPwd;
    }

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTeamMoney() {
        return teamMoney;
    }

    public void setTeamMoney(String teamMoney) {
        this.teamMoney = teamMoney;
    }

    public TypeDetailBean getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(TypeDetailBean typeDetail) {
        this.typeDetail = typeDetail;
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

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getYlxx() {
        return ylxx;
    }

    public void setYlxx(String ylxx) {
        this.ylxx = ylxx;
    }

    public static class TypeDetailBean {
        /**
         * autoUpdateTime : 2016-05-17 15:54:52
         * contentStatus : 0
         * msgContent :
         * typeLevel : 代理会员分组
         * typeName : 代理分组
         * typePicName : http://res.6820168.com/m/site/e0/member/1497666045601.png
         */

        private String autoUpdateTime;
        private int contentStatus;
        private String msgContent;
        private String typeLevel;
        private String typeName;
        private String typePicName;

        public String getAutoUpdateTime() {
            return autoUpdateTime;
        }

        public void setAutoUpdateTime(String autoUpdateTime) {
            this.autoUpdateTime = autoUpdateTime;
        }

        public int getContentStatus() {
            return contentStatus;
        }

        public void setContentStatus(int contentStatus) {
            this.contentStatus = contentStatus;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public String getTypeLevel() {
            return typeLevel;
        }

        public void setTypeLevel(String typeLevel) {
            this.typeLevel = typeLevel;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypePicName() {
            return typePicName;
        }

        public void setTypePicName(String typePicName) {
            this.typePicName = typePicName;
        }
    }

    public static class BackWater
    {

        /**
         * sport : 8.8
         * lottery : 5
         * electronic : 8.8
         * live : 8.8
         */

        private String sport;
        private String lottery;
        private String electronic;
        private String live;
        private String fish;

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        private String card;


        public String getFish() {
            return fish;
        }

        public void setFish(String fish) {
            this.fish = fish;
        }

        public String getSport() {
            return sport;
        }

        public void setSport(String sport) {
            this.sport = sport;
        }

        public String getLottery() {
            return lottery;
        }

        public void setLottery(String lottery) {
            this.lottery = lottery;
        }

        public String getElectronic() {
            return electronic;
        }

        public void setElectronic(String electronic) {
            this.electronic = electronic;
        }

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }
    }

    public static UserInfoBean Analysis(JSONObject jsonObject)
    {
        UserInfoBean bean=new UserInfoBean();
        bean.setAgentDesc(jsonObject.optString("agentDesc", ""));
        bean.setBetBackWater(jsonObject.optString("betBackWater", ""));
        bean.setHasRealName(jsonObject.optBoolean("hasRealName", false));
        bean.setHasWithdrawPwd(jsonObject.optBoolean("hasWithdrawPwd", false));
        bean.setIsAgent(jsonObject.optInt("isAgent", 0));
        bean.setLastLoginIp(jsonObject.optString("lastLoginIp", ""));
        bean.setLastLoginTime(jsonObject.optString("lastLoginTime", ""));
        bean.setMail(jsonObject.optString("mail", ""));
        bean.setMobile(jsonObject.optString("mobile", ""));
        bean.setNickName(jsonObject.optString("nickName", ""));
        bean.setQq(jsonObject.optString("qq", ""));
        bean.setTeamMoney(jsonObject.optString("teamMoney", ""));
        bean.setTeamCount(jsonObject.optString("teamCount", ""));
        JSONObject typeDetail=jsonObject.optJSONObject("typeDetail");
        UserInfoBean.TypeDetailBean tbean=new TypeDetailBean();
        tbean.setAutoUpdateTime(typeDetail.optString("autoUpdateTime", ""));
        tbean.setContentStatus(typeDetail.optInt("contentStatus", 0));
        tbean.setMsgContent(typeDetail.optString("msgContent", ""));
        tbean.setTypeLevel(typeDetail.optString("typeLevel", ""));
        tbean.setTypeName(typeDetail.optString("typeName", ""));
        tbean.setTypePicName(typeDetail.optString("typePicName", ""));
        bean.setTypeDetail(tbean);
        BackWater backWater=new BackWater();
        JSONObject backWater_json=jsonObject.optJSONObject("backWater");

        backWater.setSport(format(backWater_json.optString("sport", "")));
        backWater.setLottery(format(backWater_json.optString("lottery", "")));
        backWater.setElectronic(format(backWater_json.optString("electronic", "")));
        backWater.setLive(format(backWater_json.optString("live", "")));
        backWater.setFish(format(backWater_json.optString("fish", "")));
        bean.setBackWater(backWater);

        bean.setUserMoney(jsonObject.optString("userMoney", ""));
        bean.setUserRealName(jsonObject.optString("userRealName", ""));
        bean.setUserName(jsonObject.optString("userName",""));
        bean.setYlxx(jsonObject.optString("ylxx",""));
        return bean;
    }

    public static UserInfoBean Analysis_local(JSONObject jsonObject)
    {
        UserInfoBean bean=new UserInfoBean();
        bean.setAgentDesc(jsonObject.optString("agentDesc", ""));
        bean.setBetBackWater(jsonObject.optString("betBackWater", ""));
        bean.setHasRealName(jsonObject.optBoolean("hasRealName", false));
        bean.setHasWithdrawPwd(jsonObject.optBoolean("hasWithdrawPwd", false));
        bean.setIsAgent(jsonObject.optInt("isAgent", 0));
        bean.setLastLoginIp(jsonObject.optString("lastLoginIp", ""));
        bean.setLastLoginTime(jsonObject.optString("lastLoginTime", ""));
        bean.setMail(jsonObject.optString("mail", ""));
        bean.setMobile(jsonObject.optString("mobile", ""));
        bean.setNickName(jsonObject.optString("nickName", ""));
        bean.setQq(jsonObject.optString("qq", ""));
        bean.setTeamMoney(jsonObject.optString("teamMoney", ""));
        bean.setTeamCount(jsonObject.optString("teamCount", ""));
        JSONObject typeDetail=jsonObject.optJSONObject("typeDetail");
        UserInfoBean.TypeDetailBean tbean=new TypeDetailBean();
        tbean.setAutoUpdateTime(typeDetail.optString("autoUpdateTime", ""));
        tbean.setContentStatus(typeDetail.optInt("contentStatus", 0));
        tbean.setMsgContent(typeDetail.optString("msgContent", ""));
        tbean.setTypeLevel(typeDetail.optString("typeLevel", ""));
        tbean.setTypeName(typeDetail.optString("typeName", ""));
        tbean.setTypePicName(typeDetail.optString("typePicName", ""));
        bean.setTypeDetail(tbean);
        BackWater backWater=new BackWater();
        JSONObject backWater_json=jsonObject.optJSONObject("backWater");
        backWater.setSport(format(backWater_json.optString("sport", "")));
        backWater.setLottery(format(backWater_json.optString("lottery", "")));
        backWater.setElectronic(format(backWater_json.optString("electronic", "")));
        backWater.setLive(format(backWater_json.optString("live", "")));
        backWater.setFish(format(backWater_json.optString("fish", "")));
        backWater.setCard(format(backWater_json.optString("card","")));
        bean.setBackWater(backWater);
        bean.setUserMoney(jsonObject.optString("userMoney", ""));
        bean.setUserRealName(jsonObject.optString("userRealName", ""));
        bean.setUserName(jsonObject.optString("userName",""));
        bean.setYlxx(jsonObject.optString("ylxx",""));
        return bean;
    }

    /*/保留2位小数***/
    public static String format(String data) {
        if(TextUtils.isEmpty(data))return "0.0";
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(new BigDecimal(Double.valueOf(data.toString())));
    }
}
