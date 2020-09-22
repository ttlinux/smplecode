package com.lottery.biying.LotteryMethod.CQSSC;

import android.util.SparseArray;

import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.LotteryMethod.BaseAnalysisClass;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.view.LotteryButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/11.
 */
public class DingweiDan extends BaseAnalysisClass {



    public static DingweiDan dingweiDan;
    public static DingweiDan getInstance()
    {
        if(dingweiDan==null)
            dingweiDan=new DingweiDan();
        return dingweiDan;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int betOrders = 0;

        for (int i = 0; i <Numbers.size() ; i++) {
            ArrayList<LotteryButton> nums=Numbers.get(i);
            ArrayList<LotteryButton> select_nums = new ArrayList<>();
            for (int j = 0; j < nums.size(); j++) {
                if (nums.get(j).getChecked()) {
                    select_nums.add(nums.get(j));
                    betOrders++;
                }
            }
            if(select_nums.size()>0)
            SelectNumbers.put(i, select_nums);
        }
        return betOrders;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode) {
        random(Numbers);
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        return Formatnumber(SelectNumbers);
    }


    private JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers)
    {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonarry = new JSONArray();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < AnalysisType.getCurrentbuttons().size(); i++) {
            ArrayList<LotteryButton> btns=SelectNumbers.get(i);
            if(btns!=null)
            {
                JSONObject listindexobj=new JSONObject();
                JSONArray temp=new JSONArray();
                for (int j = 0; j <btns.size() ; j++) {
                    JSONObject tempobj=new JSONObject();
                    try {
                        tempobj.put(BundleTag.Number,btns.get(j).getText().toString());
                        if(btns.get(j).getTag()!=null)
                            tempobj.put(BundleTag.Index,((IndexRecordBean)btns.get(j).getTag()).getIndex2());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    temp.put(tempobj);
                    sb.append(btns.get(j).getText().toString());
                }
                if(AnalysisType.getCurrentbuttons().size()>1)
                    sb.append(",");
                try {
                    listindexobj.put(BundleTag.List,temp);
                    listindexobj.put(BundleTag.Index,i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarry.put(listindexobj);
            }
            else
            {
                sb.append("-,");
            }
        }
        try {
            sb.deleteCharAt(sb.length()-1);
            jsonObject.put(BundleTag.Data,jsonarry);
            jsonObject.put(BundleTag.FormatNumber,sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void random(ArrayList<ArrayList<LotteryButton>> Numbers)
    {
        SelectNumbers.clear();
        Random random = new Random();
        int index=random.nextInt(50);
        LotteryButton bn=Numbers.get(index/10).get(index%10);
        bn.setChecked(true);
        ArrayList<LotteryButton> sNum=new ArrayList<>();
        sNum.add(bn);
        SelectNumbers.put(index/10,sNum);
    }
}
