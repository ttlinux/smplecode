package com.lottery.biying.LotteryMethod.XY28;

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
 * Created by Administrator on 2018/7/7.
 */
public class ErXing extends BaseAnalysisClass {

    private final String Q2ZUX = "ex_q2_q2zux"; // 二星前二组选复式
    private final String Q2FS = "ex_q2_q2fs"; // 二星前二直选复式
    private final String H2ZUX = "ex_h2_h2zux"; // 二星后二组选复式
    private final String H2FS = "ex_h2_h2fs"; // 二星后二直选复式

    public static ErXing erxing;
    public static ErXing getInstance()
    {
        if(erxing==null)
            erxing=new ErXing();
        return erxing;
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

        if(GameCode.contains(Q2ZUX) || GameCode.contains(H2ZUX))
        {
            if(SelectNumbers.size()<1||SelectNumbers.get(0).size()<2)
                return 0;
            int size=SelectNumbers.get(0).size();
            return size*(size-1)/2;
        }
        if(GameCode.contains(Q2FS) || GameCode.contains(H2FS))
        {
            if(SelectNumbers.size()<2)
                return 0;
            betOrders=1;
            for (int i = 0; i < SelectNumbers.size(); i++) {
                betOrders=betOrders*SelectNumbers.get(i).size();
            }
            return betOrders;
        }
        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        if(GameCode.contains(Q2ZUX) || GameCode.contains(H2ZUX))
        {
            Zuhe(Numbers,true,2);
        }
        if(GameCode.contains(Q2FS) || GameCode.contains(H2FS))
        {
            Zuhe(Numbers,false,1,1);
        }
    }


    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        if(GameCode.contains(Q2ZUX) || GameCode.contains(H2ZUX))
        {
            return Formatnumber(SelectNumbers);
        }
        if(GameCode.contains(Q2FS) || GameCode.contains(H2FS))
        {
            return Formatnumber_special(SelectNumbers,GameCode);
        }
        return new JSONObject();
    }

    private void Zuhe(ArrayList<ArrayList<LotteryButton>> Numbers,boolean CannotbeSame,int ...args)
    {
        SelectNumbers.clear();
        int count=Numbers.get(0).size();
        Random random = new Random();
        SparseArray<String> hashmap=new SparseArray<>();
        ArrayList<Integer> temp=new ArrayList<>();
        for (int i = 0; i <args.length ; i++) {
            temp.clear();
            for (int j = 0; j <count ; j++) {
                if(CannotbeSame && hashmap.get(j)!=null)continue;
                temp.add(j);
            }
            ArrayList<LotteryButton> sNum=new ArrayList<>();
            for (int j = 0; j <args[i] ; j++) {
                int index= random.nextInt(temp.size());
                if(CannotbeSame)hashmap.put(temp.get(index), "");
                LotteryButton bn=Numbers.get(i).get(temp.get(index));
                bn.setChecked(true);
                sNum.add(bn);
                temp.remove(index);
            }
            SelectNumbers.put(i, sNum);
        }
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
                    if(AnalysisType.getCurrentbuttons().size()==1)
                        sb.append(",");
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

    private JSONObject Formatnumber_special(SparseArray<ArrayList<LotteryButton>> SelectNumbers,String Gamecode)
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
            sb.deleteCharAt(sb.length() - 1);
            jsonObject.put(BundleTag.Data,jsonarry);
            if(Gamecode.contains(Q2FS) )
            {
                jsonObject.put(BundleTag.FormatNumber,sb.toString()+",-");
            }
            if(Gamecode.contains(H2FS))
            {
                jsonObject.put(BundleTag.FormatNumber,"-,"+sb.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
