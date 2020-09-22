package com.lottery.biying.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/1.
 */
public class TraceOrderDetailBean {


    /**
     * appendLottery : {"allCount":"1","allTraceMoney":"￥0.4元","allTraceMoneyValue":"0.4","currentGameName":"前四直选单式","jingDu":"已开0期/总1期","logo":"http://res.6820168.com/share/lottery/ssc_small.png","lotteryName":"重庆时时彩","openCount":"0","orderNumber":"CQSSC201804305457278464","orderStatus":"1","startBetQishu":"20180501027","stopCondition":"中奖即停","stopTraceValue":"1","traceMoney":"￥0.0","traceMoneyValue":"0.0","traceNumber":"BE201804305457278467","traceTime":"2018-05-01 10:28:54","winMoney":"￥0.0","winMoneyValue":"0.0"}
     * appendScheme : {"content":"3456,7891","gameName":"前四直选单式","model":"角","modelValue":"0.2","multipe":"1","noteNuber":"2"}
     * resultList : [{"betQishu":"20180501027","betmoney":"0.4","id":"1385","multipe":"1","status":"待开奖","statusValue":"0","winMoney":"0.0","winNumber":""}]
     */

    private AppendLotteryBean appendLottery;
    private AppendSchemeBean appendScheme;
    private List<ResultListBean> resultList;

    public AppendLotteryBean getAppendLottery() {
        return appendLottery;
    }

    public void setAppendLottery(AppendLotteryBean appendLottery) {
        this.appendLottery = appendLottery;
    }

    public AppendSchemeBean getAppendScheme() {
        return appendScheme;
    }

    public void setAppendScheme(AppendSchemeBean appendScheme) {
        this.appendScheme = appendScheme;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class AppendLotteryBean {
        /**
         * allCount : 1
         * allTraceMoney : ￥0.4元
         * allTraceMoneyValue : 0.4
         * currentGameName : 前四直选单式
         * jingDu : 已开0期/总1期
         * logo : http://res.6820168.com/share/lottery/ssc_small.png
         * lotteryName : 重庆时时彩
         * openCount : 0
         * orderNumber : CQSSC201804305457278464
         * orderStatus : 1
         * startBetQishu : 20180501027
         * stopCondition : 中奖即停
         * stopTraceValue : 1
         * traceMoney : ￥0.0
         * traceMoneyValue : 0.0
         * traceNumber : BE201804305457278467
         * traceTime : 2018-05-01 10:28:54
         * winMoney : ￥0.0
         * winMoneyValue : 0.0
         */

        private String bonusType;
        private String bonusTypeValue;
        private String allCount;
        private String allTraceMoney;
        private String allTraceMoneyValue;
        private String currentGameName;
        private String jingDu;
        private String logo;
        private String lotteryName;
        private String openCount;
        private String orderNumber;
        private String orderStatus;
        private String startBetQishu;
        private String stopCondition;
        private String stopTraceValue;
        private String stopFlag;
        private String traceMoney;
        private String traceMoneyValue;
        private String traceNumber;
        private String traceTime;
        private String winMoney;
        private String winMoneyValue;


        public String getStopFlag() {
            return stopFlag;
        }

        public void setStopFlag(String stopFlag) {
            this.stopFlag = stopFlag;
        }

        public String getBonusType() {
            return bonusType;
        }

        public void setBonusType(String bonusType) {
            this.bonusType = bonusType;
        }

        public String getBonusTypeValue() {
            return bonusTypeValue;
        }

        public void setBonusTypeValue(String bonusTypeValue) {
            this.bonusTypeValue = bonusTypeValue;
        }

        public String getAllCount() {
            return allCount;
        }

        public void setAllCount(String allCount) {
            this.allCount = allCount;
        }

        public String getAllTraceMoney() {
            return allTraceMoney;
        }

        public void setAllTraceMoney(String allTraceMoney) {
            this.allTraceMoney = allTraceMoney;
        }

        public String getAllTraceMoneyValue() {
            return allTraceMoneyValue;
        }

        public void setAllTraceMoneyValue(String allTraceMoneyValue) {
            this.allTraceMoneyValue = allTraceMoneyValue;
        }

        public String getCurrentGameName() {
            return currentGameName;
        }

        public void setCurrentGameName(String currentGameName) {
            this.currentGameName = currentGameName;
        }

        public String getJingDu() {
            return jingDu;
        }

        public void setJingDu(String jingDu) {
            this.jingDu = jingDu;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLotteryName() {
            return lotteryName;
        }

        public void setLotteryName(String lotteryName) {
            this.lotteryName = lotteryName;
        }

        public String getOpenCount() {
            return openCount;
        }

        public void setOpenCount(String openCount) {
            this.openCount = openCount;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getStartBetQishu() {
            return startBetQishu;
        }

        public void setStartBetQishu(String startBetQishu) {
            this.startBetQishu = startBetQishu;
        }

        public String getStopCondition() {
            return stopCondition;
        }

        public void setStopCondition(String stopCondition) {
            this.stopCondition = stopCondition;
        }

        public String getStopTraceValue() {
            return stopTraceValue;
        }

        public void setStopTraceValue(String stopTraceValue) {
            this.stopTraceValue = stopTraceValue;
        }

        public String getTraceMoney() {
            return traceMoney;
        }

        public void setTraceMoney(String traceMoney) {
            this.traceMoney = traceMoney;
        }

        public String getTraceMoneyValue() {
            return traceMoneyValue;
        }

        public void setTraceMoneyValue(String traceMoneyValue) {
            this.traceMoneyValue = traceMoneyValue;
        }

        public String getTraceNumber() {
            return traceNumber;
        }

        public void setTraceNumber(String traceNumber) {
            this.traceNumber = traceNumber;
        }

        public String getTraceTime() {
            return traceTime;
        }

        public void setTraceTime(String traceTime) {
            this.traceTime = traceTime;
        }

        public String getWinMoney() {
            return winMoney;
        }

        public void setWinMoney(String winMoney) {
            this.winMoney = winMoney;
        }

        public String getWinMoneyValue() {
            return winMoneyValue;
        }

        public void setWinMoneyValue(String winMoneyValue) {
            this.winMoneyValue = winMoneyValue;
        }
    }

    public static class AppendSchemeBean {
        /**
         * content : 3456,7891
         * gameName : 前四直选单式
         * model : 角
         * modelValue : 0.2
         * multipe : 1
         * noteNuber : 2
         */

        private String content;
        private String gameName;
        private String model;
        private String modelValue;
        private String multipe;
        private String noteNuber;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getModelValue() {
            return modelValue;
        }

        public void setModelValue(String modelValue) {
            this.modelValue = modelValue;
        }

        public String getMultipe() {
            return multipe;
        }

        public void setMultipe(String multipe) {
            this.multipe = multipe;
        }

        public String getNoteNuber() {
            return noteNuber;
        }

        public void setNoteNuber(String noteNuber) {
            this.noteNuber = noteNuber;
        }
    }

    public static class ResultListBean {
        /**
         * betQishu : 20180501027
         * betmoney : 0.4
         * id : 1385
         * multipe : 1
         * status : 待开奖
         * statusValue : 0
         * winMoney : 0.0
         * winNumber :
         */
        private String betQishuFormat;
        private String betQishu;
        private String betmoney;
        private String id;
        private String multipe;
        private String status;
        private String statusValue;
        private String winMoney;
        private String winNumber;
        private String stopOrderFlag;

        public String getBetQishuFormat() {
            return betQishuFormat;
        }

        public void setBetQishuFormat(String betQishuFormat) {
            this.betQishuFormat = betQishuFormat;
        }

        public String getStopOrderFlag() {
            return stopOrderFlag;
        }

        public void setStopOrderFlag(String stopOrderFlag) {
            this.stopOrderFlag = stopOrderFlag;
        }

        public String getBetQishu() {
            return betQishu;
        }

        public void setBetQishu(String betQishu) {
            this.betQishu = betQishu;
        }

        public String getBetmoney() {
            return betmoney;
        }

        public void setBetmoney(String betmoney) {
            this.betmoney = betmoney;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMultipe() {
            return multipe;
        }

        public void setMultipe(String multipe) {
            this.multipe = multipe;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusValue() {
            return statusValue;
        }

        public void setStatusValue(String statusValue) {
            this.statusValue = statusValue;
        }

        public String getWinMoney() {
            return winMoney;
        }

        public void setWinMoney(String winMoney) {
            this.winMoney = winMoney;
        }

        public String getWinNumber() {
            return winNumber;
        }

        public void setWinNumber(String winNumber) {
            this.winNumber = winNumber;
        }
    }

    public static TraceOrderDetailBean AnalysisData(JSONObject jsonObject)
    {
        TraceOrderDetailBean tbean=new TraceOrderDetailBean();
        JSONObject appendLottery=jsonObject.optJSONObject("appendLottery");
        JSONObject appendScheme=jsonObject.optJSONObject("appendScheme");
        JSONArray jsonArray=jsonObject.optJSONArray("resultList");


        AppendLotteryBean appendLotteryBean=new AppendLotteryBean();
        appendLotteryBean.setAllCount(appendLottery.optString("allCount", ""));
        appendLotteryBean.setAllTraceMoney(appendLottery.optString("allTraceMoney", ""));
        appendLotteryBean.setAllTraceMoneyValue(appendLottery.optString("allTraceMoneyValue", ""));
        appendLotteryBean.setCurrentGameName(appendLottery.optString("currentGameName", ""));
        appendLotteryBean.setJingDu(appendLottery.optString("jingDu", ""));
        appendLotteryBean.setLogo(appendLottery.optString("logo", ""));
        appendLotteryBean.setLotteryName(appendLottery.optString("lotteryName", ""));
        appendLotteryBean.setOpenCount(appendLottery.optString("openCount", ""));
        appendLotteryBean.setOrderNumber(appendLottery.optString("orderNumber", ""));
        appendLotteryBean.setOrderStatus(appendLottery.optString("orderStatus", ""));
        appendLotteryBean.setStartBetQishu(appendLottery.optString("startBetQishu", ""));
        appendLotteryBean.setStopCondition(appendLottery.optString("stopCondition", ""));
        appendLotteryBean.setStopTraceValue(appendLottery.optString("stopTraceValue", ""));
        appendLotteryBean.setTraceMoney(appendLottery.optString("traceMoney", ""));
        appendLotteryBean.setTraceMoneyValue(appendLottery.optString("traceMoneyValue", ""));
        appendLotteryBean.setTraceNumber(appendLottery.optString("traceNumber", ""));
        appendLotteryBean.setTraceTime(appendLottery.optString("traceTime", ""));
        appendLotteryBean.setWinMoney(appendLottery.optString("winMoney", ""));
        appendLotteryBean.setWinMoneyValue(appendLottery.optString("winMoneyValue", ""));
        appendLotteryBean.setBonusType(appendLottery.optString("bonusType", ""));
        appendLotteryBean.setBonusTypeValue(appendLottery.optString("bonusTypeValue", ""));
        appendLotteryBean.setStopFlag(appendLottery.optString("stopFlag",""));
        tbean.setAppendLottery(appendLotteryBean);

        AppendSchemeBean appendSchemeBean=new AppendSchemeBean();
        appendSchemeBean.setContent(appendScheme.optString("content", ""));
        appendSchemeBean.setGameName(appendScheme.optString("gameName", ""));
        appendSchemeBean.setModel(appendScheme.optString("model", ""));
        appendSchemeBean.setModelValue(appendScheme.optString("modelValue", ""));
        appendSchemeBean.setMultipe(appendScheme.optString("multipe", ""));
        appendSchemeBean.setNoteNuber(appendScheme.optString("noteNuber", ""));
        tbean.setAppendScheme(appendSchemeBean);

        ArrayList<ResultListBean> resultbeans=new ArrayList<>();
        for (int i = 0; i <jsonArray.length() ; i++) {
            JSONObject obj=jsonArray.optJSONObject(i);
            ResultListBean resultListBean=new ResultListBean();
            resultListBean.setBetQishuFormat(obj.optString("betQishuFormat",""));
            resultListBean.setMultipe(obj.optString("multipe",""));
            resultListBean.setBetmoney(obj.optString("betmoney", ""));
            resultListBean.setBetQishu(obj.optString("betQishu", ""));
            resultListBean.setId(obj.optString("id", ""));
            resultListBean.setStatus(obj.optString("status", ""));
            resultListBean.setStatusValue(obj.optString("statusValue", ""));
            resultListBean.setWinMoney(obj.optString("winMoney", ""));
            resultListBean.setWinNumber(obj.optString("winNumber", ""));
            resultListBean.setStopOrderFlag(obj.optString("stopOrderFlag",""));
            resultbeans.add(resultListBean);
        }
        tbean.setResultList(resultbeans);
        return tbean;
    }
}
