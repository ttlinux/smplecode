package com.lottery.biying.bean;

/**
 * Created by Administrator on 2018/4/16.
 */
public class LotteryTimebean {


    /**
     * gameCode : xjssc
     * gameName : 新疆时时彩
     * qs : 20180407043
     * qsFormat : 043
     * times : 403
     */

    private String qs;
    private String qsFormat;
    private int times;
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

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
