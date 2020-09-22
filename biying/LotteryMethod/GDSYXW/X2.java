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
 * Created by Administrator on 2018/4/17.
 */
public class X2 extends BaseAnalysisClass{

    private final String X2_RENXUAN_FS="x2_rx2z2_fs";//任选二任选复式
    private final String X2_RENXUAN_DT="x2_rx2z2_dt";//任选二任选胆拖
    private final String X2_ZX_FS="x2_zx_fs";//前二直选复式
    private final String X2_ZUX_FS="x2_zux_fs";//前二组选复式
    private final String X2_ZUX_DT="x2_zux_dt";//前二组选胆拖

    public static X2 x2;
    public static X2 getInstance()
    {
        if(x2==null)
            x2=new X2();
        return x2;
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

        if(GameCode.contains(X2_ZUX_FS) || GameCode.contains(X2_RENXUAN_FS))
        {
            return SumCount();
        }
        if(GameCode.contains(X2_RENXUAN_DT) || GameCode.contains(X2_ZUX_DT))
        {
            return Dantuo();
        }
        if(GameCode.contains(X2_ZX_FS))
        {
            return Zhixuan_Fushi();
        }
        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        if(GameCode.contains(X2_RENXUAN_DT) || GameCode.contains(X2_ZUX_DT))
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

        if(GameCode.contains(X2_ZUX_FS) || GameCode.contains(X2_RENXUAN_FS))
        {
            Random_Two_numbers(Numbers, true, 2);
        }
        if(GameCode.contains(X2_ZX_FS) || GameCode.contains(X2_RENXUAN_DT) || GameCode.contains(X2_ZUX_DT))
        {
            Random_Two_numbers(Numbers,true,1,1);
        }

    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {

        if(GameCode.contains(X2_ZUX_FS) || GameCode.contains(X2_RENXUAN_FS))
        {
            return Formatnumber_method(SelectNumbers,"");
        }
        if(GameCode.contains(X2_ZX_FS))
        {
            return Formatnumber_method(SelectNumbers,",-,-,-");
        }
        if(GameCode.contains(X2_RENXUAN_DT) || GameCode.contains(X2_ZUX_DT))
        {
            return Formatnumber_method_2(SelectNumbers);
        }

        return new JSONObject();
    }

    private int SumCount()
    {
        //累加 只有一组号码的情况
        if(SelectNumbers.size()==0 ||  SelectNumbers.valueAt(0).size()<2)return 0;
            ArrayList<LotteryButton> btns=SelectNumbers.valueAt(0);
            return (btns.size()-1)*btns.size()/2;
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
        return count;
    }

    private int Zhixuan_Fushi()
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
