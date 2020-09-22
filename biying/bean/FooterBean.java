package com.lottery.biying.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */
public class FooterBean {

    String  title;
    List<List<String>> arrayLists;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<String>> getArrayLists() {
        return arrayLists;
    }

    public void setArrayLists(List<List<String>> arrayLists) {
        this.arrayLists = arrayLists;
    }


    public static FooterBean Analysis(JSONObject jsobj)
    {
        FooterBean bean=new FooterBean();
        bean.setTitle(jsobj.optString("title", ""));
        JSONArray jsonl=jsobj.optJSONArray("data");
        List<List<String>> strs=new ArrayList<>();
        for (int j = 0; j <jsonl.length() ; j++) {
            JSONArray temp=jsonl.optJSONArray(j);
            ArrayList<String> nums=new ArrayList<String>();
            for (int k = 0; k <temp.length() ; k++) {
                nums.add(temp.optString(k));
            }
            strs.add(nums);
        }
        bean.setArrayLists(strs);
        return bean;
    }
}
