package com.lottery.biying.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
public class TrendBean {


    /**
     * isOpen : 1
     * nums : [{"count":1,"num":"0","yl":"-"},{"count":1,"num":"1","yl":"-"},{"count":0,"num":"2","yl":"-"},{"count":0,"num":"3","yl":"-"},{"count":0,"num":"4","yl":"-"},{"count":1,"num":"5","yl":"-"},{"count":1,"num":"6","yl":"-"},{"count":0,"num":"7","yl":"-"},{"count":0,"num":"8","yl":"-"},{"count":1,"num":"9","yl":"-"}]
     * openMessage :
     * openResult : 9,5,1,0,6
     * qs : 20180312054
     * qsFormat : 054期
     */

    private String isOpen;
    private String openMessage;
    private String openResult;
    private String qs;
    private String qsFormat;
    private List<NumsBean> nums;

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getOpenMessage() {
        return openMessage;
    }

    public void setOpenMessage(String openMessage) {
        this.openMessage = openMessage;
    }

    public String getOpenResult() {
        return openResult;
    }

    public void setOpenResult(String openResult) {
        this.openResult = openResult;
    }

    public String getQs() {
        return qs;
    }

    public void setQs(String qs) {
        this.qs = qs;
    }

    public String getQsFormat() {
        return qsFormat;
    }

    public void setQsFormat(String qsFormat) {
        this.qsFormat = qsFormat;
    }

    public List<NumsBean> getNums() {
        return nums;
    }

    public void setNums(List<NumsBean> nums) {
        this.nums = nums;
    }

    public static class NumsBean {
        /**
         * count : 1
         * num : 0
         * yl : -
         */

        private int count;
        private String num;
        private String yl;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getYl() {
            return yl;
        }

        public void setYl(String yl) {
            this.yl = yl;
        }
    }

    public static TrendBean Analysis_TrendBean(JSONObject jsonObject)
    {
        TrendBean trendBean=new TrendBean();
        trendBean.setIsOpen(jsonObject.optString("isOpen",""));
        JSONArray jsonArray= jsonObject.optJSONArray("nums");
        List<NumsBean> nums = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobj=jsonArray.optJSONObject(i);
            NumsBean numbean=new NumsBean();
            numbean.setCount(jsonobj.optInt("count"));
            numbean.setNum(jsonobj.optString("num", ""));
            numbean.setYl(jsonobj.optString("yl", ""));
            nums.add(numbean);
        }
        trendBean.setNums(nums);
        trendBean.setOpenMessage(jsonObject.optString("openMessage",""));
        trendBean.setOpenResult(jsonObject.optString("openResult",""));
        trendBean.setQs(jsonObject.optString("qs",""));
        trendBean.setQsFormat(jsonObject.optString("qsFormat",""));
        return trendBean;
    }

    public static ZoushiBean Analysis_ZoushiBean(JSONObject jsonObject)
    {
        ZoushiBean zousibean=new ZoushiBean();
        TrendBean trendBean=new TrendBean();
        trendBean.setIsOpen(jsonObject.optString("isOpen",""));
        JSONArray jsonArray= jsonObject.optJSONArray("nums");
        List<NumsBean> nums = new ArrayList();
        ArrayList<Integer> indexs=new ArrayList<>();
        ArrayList<Integer> snAmount=new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobj=jsonArray.optJSONObject(i);
            NumsBean numbean=new NumsBean();
            numbean.setCount(jsonobj.optInt("count"));
            numbean.setNum(jsonobj.optString("num", ""));
            numbean.setYl(jsonobj.optString("yl", ""));
            nums.add(numbean);
            if(numbean.getCount()>0)
            {
                indexs.add(Integer.valueOf(numbean.getNum()));
                snAmount.add(numbean.getCount());
            }
        }
        zousibean.setSnAmount(snAmount);
        zousibean.setIndexs(indexs);
        zousibean.setStatus(Integer.valueOf(trendBean.getIsOpen()));
        trendBean.setNums(nums);
        trendBean.setOpenMessage(jsonObject.optString("openMessage", ""));
        trendBean.setOpenResult(jsonObject.optString("openResult", ""));
        trendBean.setQs(jsonObject.optString("qs", ""));
        trendBean.setQsFormat(jsonObject.optString("qsFormat", ""));
        zousibean.setQishu(trendBean.getQsFormat());
        zousibean.setOpenMessage(trendBean.getOpenMessage());
        return zousibean;
    }
}
