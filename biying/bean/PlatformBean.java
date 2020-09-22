package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/3.
 */
public class PlatformBean {

    private String flat;
    private String flatDes;
    private String jine;
    private boolean state;
    private String bigPic;

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    private String smallPic;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getJine() {
        return jine;
    }

    public void setJine(String jine) {
        this.jine = jine;
    }

    public String getFlatName() {
        return flatName;
    }

    public void setFlatName(String flatName) {
        this.flatName = flatName;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getFlatDes() {
        return flatDes;
    }

    public void setFlatDes(String flatDes) {
        this.flatDes = flatDes;
    }

    public String getFlatUrl() {
        return flatUrl;
    }

    public void setFlatUrl(String flatUrl) {
        this.flatUrl = flatUrl;
    }

    private String flatName;
    private String flatUrl;

    public static PlatformBean Analysis(JSONObject jsobject)
    {
        PlatformBean bean = new PlatformBean();
        bean.setFlat(jsobject.optString("flat", ""));
//                        bean.setFlatDes(jsobject.optString("flatDes", ""));
        bean.setFlatName(jsobject.optString("flatName", ""));
//                        bean.setFlatUrl(jsobject.optString("flatUrl", ""));
        bean.setBigPic(jsobject.optString("bigPic", ""));
        bean.setSmallPic(jsobject.optString("smallPic", ""));
        bean.setState(false);
        bean.setJine("余额");
        return bean;
    }

    public static PlatformBean Analysis_local(JSONObject jsobject)
    {
        PlatformBean bean = new PlatformBean();
        bean.setFlat(jsobject.optString("flat", ""));
//                        bean.setFlatDes(jsobject.optString("flatDes", ""));
        bean.setFlatName(jsobject.optString("flatName", ""));
//                        bean.setFlatUrl(jsobject.optString("flatUrl", ""));
        bean.setBigPic(jsobject.optString("bigPic", ""));
        bean.setSmallPic(jsobject.optString("smallPic", ""));
        bean.setState(false);
        bean.setJine("余额");
        return bean;
    }
}
