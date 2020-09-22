package com.lottery.biying.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */
public class TrendChartBean {


    /**
     * isOpen : 1
     * openCode : ["6","9","4","5","9"]
     * openResult : 6,9,4,5,9
     * qs : 20180514055
     * qsFormat : 055
     * yilou : [[16,1,4,5,9,11,0,13,2,6],[6,2,9,8,1,7,3,4,16,0],[6,5,3,2,0,12,9,20,4,1],[3,6,5,14,2,11,1,10,17,0],[8,4,9,29,37,0,1,2,7,10]]
     */

    private int isOpen;
    private String openResult;
    private String qs;
    private String qsFormat;
    private List<String> openCode;
    private List<List<String>> yilou;

    public List<List<String>> getYilou() {
        return yilou;
    }

    public void setYilou(List<List<String>> yilou) {
        this.yilou = yilou;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
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

    public List<String> getOpenCode() {
        return openCode;
    }

    public void setOpenCode(List<String> openCode) {
        this.openCode = openCode;
    }

    public static TrendChartBean Analysis(JSONObject jsonObject)
    {
        TrendChartBean trendChartBean=new TrendChartBean();
        trendChartBean.setIsOpen(jsonObject.optInt("isOpen", 0));
        trendChartBean.setOpenResult(jsonObject.optString("openResult", ""));
        trendChartBean.setQs(jsonObject.optString("qs", ""));
        trendChartBean.setQsFormat(jsonObject.optString("qsFormat", ""));

        JSONArray openCode=jsonObject.optJSONArray("openCode");
        ArrayList openCodes=new ArrayList();
        for (int i = 0; i < openCode.length(); i++) {
            openCodes.add(openCode.optString(i));
        }
        trendChartBean.setOpenCode(openCodes);

        List<List<String>> yilous=new ArrayList<>();
        JSONArray yilou=jsonObject.optJSONArray("yilou");
        for (int i = 0; i <yilou.length() ; i++) {
            JSONArray temp=yilou.optJSONArray(i);
            List<String> ltemp=new ArrayList<>();
            for (int j = 0; j <temp.length() ; j++) {
                ltemp.add(temp.optString(j));
            }
            yilous.add(ltemp);
        }
        trendChartBean.setYilou(yilous);

        return trendChartBean;
    }
}
