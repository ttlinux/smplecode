package com.lottery.biying.LotteryMethod.LHC;

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
 * Created by Administrator on 2018/4/23.
 */
public class LHC extends BaseAnalysisClass {

    public static LHC lhc;
    public static LHC getInstance()
    {
        if(lhc==null)
            lhc=new LHC();
        return lhc;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int betOrders = 1;
        boolean isselect = false;
        for (int j = 0; j < Numbers.size(); j++) {
            ArrayList<LotteryButton> nums = Numbers.get(j);
            int count = 0;
            ArrayList<LotteryButton> select_nums = new ArrayList<>();
            for (int k = 0; k < nums.size(); k++) {
                if (nums.get(k).getChecked()) {
                    count++;
                    isselect = true;
                    select_nums.add(nums.get(k));
                }
            }
            if(select_nums.size()>0)
                SelectNumbers.put(j,select_nums);
            betOrders = betOrders * count;
        }
        return betOrders;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode) {
        random_noremal(Numbers);
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        return Formatnumber(SelectNumbers);
    }


    private JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers)
    {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonarry=new JSONArray();
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
                    sb.append(",");
                }

                try {
                    listindexobj.put(BundleTag.List,temp);
                    listindexobj.put(BundleTag.Index,i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarry.put(listindexobj);
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

    private void random_noremal(ArrayList<ArrayList<LotteryButton>> Numbers)
    {
        SelectNumbers.clear();
        Random random = new Random();
        for (int i = 0; i <Numbers.size() ; i++) {
            ArrayList<LotteryButton> buttonnumbers=Numbers.get(i);
            ArrayList<LotteryButton> sNum=new ArrayList<>();
            int index=random.nextInt(Numbers.get(i).size());
            buttonnumbers.get(index).setChecked(true);
            sNum.add(buttonnumbers.get(index));
            SelectNumbers.put(i,sNum);
        }
    }
}
