package com.lottery.biying.bean;

import android.os.Handler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */
public class LotteryResultList {

    /**
     * openTime : 600
     * qs : 距054期截止:
     * resultList : [{"gw":"小双","qs":"1期","remark":"","result":["5","8","7","2","2"],"sw":"小双"},{"gw":"大单","qs":"2期","remark":"","result":["8","1","9","9","7"],"sw":"大单"},{"gw":"小单","qs":"3期","remark":"","result":["9","1","2","5","1"],"sw":"大单"},{"gw":"小单","qs":"4期","remark":"","result":["8","7","6","4","3"],"sw":"小双"},{"gw":"大双","qs":"5期","remark":"","result":["9","9","3","8","8"],"sw":"大双"},{"gw":"大单","qs":"6期","remark":"","result":["2","2","2","8","7"],"sw":"大双"},{"gw":"小双","qs":"7期","remark":"","result":["3","9","4","1","4"],"sw":"小单"},{"gw":"大单","qs":"8期","remark":"","result":["9","1","9","5","5"],"sw":"大单"},{"gw":"大双","qs":"9期","remark":"","result":["3","3","7","2","6"],"sw":"小双"},{"gw":"大单","qs":"10期","remark":"","result":["1","7","1","3","7"],"sw":"小单"},{"gw":"-","qs":"11期","remark":"等待开奖","result":[],"sw":"-"}]
     */

//    private List<List<YiLou>> yiLous;

//    public List<List<YiLou>> getYiLous() {
//        return yiLous;
//    }
//
//    public void setYiLous(List<List<YiLou>> yiLous) {
//        this.yiLous = yiLous;
//    }
    private String qs;
    private String qsFormat;
    private String openResult[];
    private int isOpen;
    private int type;
    private String GameCode;

    public String getGameCode() {
        return GameCode;
    }

    public void setGameCode(String gameCode) {
        GameCode = gameCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getOpenResult() {
        return openResult;
    }

    public void setOpenResult(String[] openResult) {
        this.openResult = openResult;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
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


    public static class YiLou
    {
        String num;
        int ylMax;
        int yl;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getYlMax() {
            return ylMax;
        }

        public void setYlMax(int ylMax) {
            this.ylMax = ylMax;
        }

        public int getYl() {
            return yl;
        }

        public void setYl(int yl) {
            this.yl = yl;
        }

        public static YiLou AnalysisData(JSONObject jsonObject)
        {
            YiLou yiLou=new YiLou();
            yiLou.setNum(jsonObject.optString("num", ""));
            yiLou.setYl(jsonObject.optInt("yl", 1));
            yiLou.setYlMax(jsonObject.optInt("ylMax",1));
            return yiLou;
        }
    }

    public static class SJH {
        String num;
        int isRed;

        public int getIsRed() {
            return isRed;
        }

        public void setIsRed(int isRed) {
            this.isRed = isRed;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public static SJH HandlerJson(JSONObject jsonObject) {
            SJH sjh = new SJH();
            sjh.setIsRed(jsonObject.optInt("isRed", 0));
            sjh.setNum(jsonObject.optString("num", ""));
            return sjh;
        }
    }
}
