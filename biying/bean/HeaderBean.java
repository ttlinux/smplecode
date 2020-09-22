package com.lottery.biying.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */
public class HeaderBean {


    /**
     * ball : ["0","1","2","3","4","5","6","7","8","9"]
     * title : 万位
     * code : ww
     */

    private String title;
    private String code;
    private List<String> ball;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getBall() {
        return ball;
    }

    public void setBall(List<String> ball) {
        this.ball = ball;
    }

    public static HeaderBean Analysis(JSONObject jsonObject)
    {
        HeaderBean bean=new HeaderBean();
        bean.setTitle(jsonObject.optString("title",""));
        bean.setCode(jsonObject.optString("code", ""));
        JSONArray jsonArray=jsonObject.optJSONArray("ball");
        List<String> strs=new ArrayList<>();
        for (int i = 0; i <jsonArray.length() ; i++) {
            strs.add(jsonArray.optString(i));
        }
        bean.setBall(strs);
        return bean;
    }
}
