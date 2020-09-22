package com.lottery.biying.LotteryMethod.GDSYXW;

import android.util.SparseArray;

import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.LotteryMethod.BaseAnalysisClass;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.view.LotteryButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/18.
 */
public class X3 extends BaseAnalysisClass{

    private String X3_RenXuan_FS="x3_rx3z3_fs";//任选三任选复式
    private String X3_RenXuan_DT="x3_rx3z3_dt";//任选三任选胆拖
    private String X3_ZhiXuan_FS="x3_zx_fs";//前三直选复式
    private String X3_ZuXuan_FS="x3_zux_fs";//前三组选复式
    private String X3_ZuXuan_DT="x3_zux_dt";//前三组选胆拖

    public static X3 x3;
    public static X3 getInstance()
    {
        if(x3==null)
            x3=new X3();
        return x3;
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

        if(GameCode.contains(X3_RenXuan_FS) || GameCode.contains(X3_ZuXuan_FS))
        {
            return SumCount();
        }
        if(GameCode.contains(X3_RenXuan_DT) || GameCode.contains(X3_ZuXuan_DT))
        {
            return Dantuo();
        }
        if(GameCode.contains(X3_ZhiXuan_FS))
        {
            return Zhixuan_Fushi();
        }
        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        if(GameCode.contains(X3_RenXuan_DT) || GameCode.contains(X3_ZuXuan_DT))
        {
            if(buttonNumber.getTag()!=null)
            {
                IndexRecordBean indexbean=(IndexRecordBean)buttonNumber.getTag();
                if(indexbean.getIndex1()==0)
                {
                    if( buttonNumber.getChecked())
                    {
                        return false;
                    }
                    else
                    {
                        for (int i = 0; i <Numbers.get(0).size() ; i++) {
                            Numbers.get(0).get(i).setChecked(false);
                        }
                        return true;
                    }
                }
//                if(indexbean.getIndex1()==1 && Numbers.get(0).get(indexbean.getIndex2()).getChecked())
//                {
//                    return false;
//                }
            }
        }
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {

        if(GameCode.contains(X3_RenXuan_FS) || GameCode.contains(X3_ZuXuan_FS))
        {
            Random_Two_numbers(Numbers, true, 3);
        }
        if(GameCode.contains(X3_ZhiXuan_FS))
        {
            Random_Two_numbers(Numbers,true,1,1,1);
        }
        if(GameCode.contains(X3_RenXuan_DT) || GameCode.contains(X3_ZuXuan_DT))
        {
            Random_Two_numbers(Numbers,true,1,2);
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        if(GameCode.contains(X3_RenXuan_FS) || GameCode.contains(X3_ZuXuan_FS))
        {
            return Formatnumber_method(SelectNumbers,"");
        }
        if(GameCode.contains(X3_ZhiXuan_FS))
        {
            return Formatnumber_method(SelectNumbers,",-,-");
        }
        if(GameCode.contains(X3_RenXuan_DT) || GameCode.contains(X3_ZuXuan_DT))
        {
           return Formatnumber_method_2(SelectNumbers);
        }
        return new JSONObject();
    }

    private int SumCount()
    {
        //累加 只有一组号码的情况
        if(SelectNumbers.size()==0 ||  SelectNumbers.valueAt(0).size()<3)return 0;
        ArrayList<LotteryButton> btns=SelectNumbers.valueAt(0);
        int count=0;
        for (int i = 2; i < btns.size(); i++) {
            count=count+(i-1)*i/2;
        }
        return count;
    }

    private int Dantuo()
    {
        //胆拖
        String index=SelectNumbers.valueAt(0).get(0).getText();

        int count=0;
        for (int i = 0; i <SelectNumbers.valueAt(1).size() ; i++) {
            if(!index.equalsIgnoreCase(SelectNumbers.valueAt(1).get(i).getText()))
            {
                count++;
            }
        }
        if(count<2)return 0;
        return count*(count-1)/2;
    }




    private int Zhixuan_Fushi()
    {

        int count=0;
        for (int i = 0; i < SelectNumbers.valueAt(0).size(); i++) {
            String index=SelectNumbers.valueAt(0).get(i).getText();
            for (int j = 0; j < SelectNumbers.valueAt(1).size(); j++) {
                String index2=SelectNumbers.valueAt(1).get(j).getText();
                if(index.equalsIgnoreCase(index2))continue;
                for (int k = 0; k < SelectNumbers.valueAt(2).size(); k++) {
                    String index3=SelectNumbers.valueAt(2).get(k).getText();
                    if(index3.equalsIgnoreCase(index2) || index3.equalsIgnoreCase(index))continue;
                    count++;
                }
            }
        }
        return count;
    }

    private void Random_Two_numbers(ArrayList<ArrayList<LotteryButton>> Numbers,boolean CannotbeSame,int ...args)
    {
        SelectNumbers.clear();
        Random random = new Random();
        SparseArray<String> hashmap=new SparseArray<>();
        ArrayList<Integer> temp=new ArrayList<>();
        for (int i = 0; i <args.length ; i++) {
            temp.clear();
            for (int j = 0; j <11 ; j++) {
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

    private JSONObject Formatnumber_method(SparseArray<ArrayList<LotteryButton>> SelectNumbers,String appendstr)
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
            jsonObject.put(BundleTag.FormatNumber,sb.toString()+appendstr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONObject Formatnumber_method_2(SparseArray<ArrayList<LotteryButton>> SelectNumbers)
    {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonarry = new JSONArray();
        StringBuilder sb=new StringBuilder();
        String dan="";
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
                    boolean handledata=false;
                    if(dan.length()==0)
                    {
                        dan=btns.get(j).getText().toString();
                        handledata=true;
                    }
                    else
                    {
                        if(!btns.get(j).getText().toString().equalsIgnoreCase(dan))
                        {
                            handledata=true;
                        }
                    }
                    if(handledata)
                    {
                        temp.put(tempobj);
                        sb.append(btns.get(j).getText().toString());
                        sb.append(",");
                    }

                }
                sb.deleteCharAt(sb.length()-1);
                sb.append(";");
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
            jsonObject.put(BundleTag.FormatNumber,"胆"+sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
