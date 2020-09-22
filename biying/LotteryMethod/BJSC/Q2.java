package com.lottery.biying.LotteryMethod.BJSC;

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
 * Created by Administrator on 2018/4/18.
 */
public class Q2 extends BaseAnalysisClass {

    private final String GYH="q2_q2_gyh";//冠亚和
    private final String Q2FS="q2_q2_fs";//前二复式
    private final String CQ2="q2_q2_cq2";//猜前二

    public static Q2 q2;
    public static Q2 getInstance()
    {
        if(q2==null)
            q2=new Q2();
        return q2;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        boolean isselect=false;
        boolean record=true;

        for (int i = 0; i <Numbers.size() ; i++) {
            ArrayList<LotteryButton> nums=Numbers.get(i);
            ArrayList<LotteryButton> select_nums = new ArrayList<>();
            isselect=false;
            for (int j = 0; j < nums.size(); j++) {
                if (nums.get(j).getChecked()) {
                    select_nums.add(nums.get(j));
                    isselect=true;
                }
            }
            if(record && !isselect)
            {
                record=false;
            }
            if(select_nums.size()>0)
                SelectNumbers.put(i, select_nums);
        }

        if(!record)
            return 0;

        if(GameCode.contains(GYH))
        {
            return GYH();
        }
        if(GameCode.contains(Q2FS) || GameCode.contains(CQ2))
        {
            return Fushi();
        }
        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        if(GameCode.contains(GYH))
        {
             Random_Two_numbers(Numbers,true,17,1);
        }
        if(GameCode.contains(Q2FS) || GameCode.contains(CQ2))
        {
            Random_Two_numbers(Numbers,true,10,1,1);
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {

        return Formatnumber(SelectNumbers);
    }

    private int GYH()
    {
        int count=0;
        for (int i = 0; i < SelectNumbers.valueAt(0).size(); i++) {
            int num=Integer.valueOf( SelectNumbers.valueAt(0).get(i).getText());
            if(num<11)
            {
                count=count+((num+1)/2-1)*2;
            }
            else if(num>11)
            {
                int arg=Math.abs(num-22);
                count=count+((arg+1)/2-1)*2;
            }
            else
            {
                count=count+10;
            }
        }
        return count;
    }

    private int Fushi()
    {
        //相同的注数 第一行多少个 第二行多少个
        int x=0,y=SelectNumbers.valueAt(0).size(),z=SelectNumbers.valueAt(1).size();
        for (int i = 0; i <SelectNumbers.valueAt(0).size(); i++) {
            String index=SelectNumbers.valueAt(0).get(i).getText();
            for (int j = 0; j <SelectNumbers.get(1).size() ; j++) {
                String index2=SelectNumbers.valueAt(1).get(j).getText();
                if(index.equalsIgnoreCase(index2))
                {
                    x++;
                }
            }
        }
        return x*(x-1)+(y-x)*x+(z-x)*y;
    }

    private void Random_Two_numbers(ArrayList<ArrayList<LotteryButton>> Numbers,boolean CannotbeSame,int nums,int ...args)
    {
        SelectNumbers.clear();
        Random random = new Random();
        SparseArray<String> hashmap=new SparseArray<>();
        ArrayList<Integer> temp=new ArrayList<>();
        for (int i = 0; i <args.length ; i++) {
            temp.clear();
            for (int j = 0; j <nums ; j++) {
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
}
