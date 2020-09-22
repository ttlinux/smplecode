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
 * Created by Administrator on 2018/4/23.
 */
public class TEMA extends BaseAnalysisClass{

    public static enum XY28
    {
        ZX("tm_tm_zx",0),BS("tm_tm_bs",1),
        DXDS("tm_tm_dxds",2),ZHDXDS("tm_tm_zhdxds",3),
        JZ("tm_tm_jz",4),BOSE("tm_tm_bose",5),BZ("tm_tm_bz",6);
        public String name;
        public int index;
        // 构造方法
        private XY28(String name,int index) {
            this.name = name;
            this.index=index;
        }
        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }

    }
    public static TEMA usuallyMethod;
    public static TEMA getInstance()
    {
        if(usuallyMethod==null)
            usuallyMethod=new TEMA();
        return usuallyMethod;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int count = 0;
        for (int j = 0; j < Numbers.size(); j++) {
            ArrayList<LotteryButton> nums = Numbers.get(j);
            ArrayList<LotteryButton> select_nums = new ArrayList<>();
            for (int k = 0; k < nums.size(); k++) {
                if (nums.get(k).getChecked()) {
                    count++;
                    select_nums.add(nums.get(k));
                }
            }
            if(select_nums.size()>0)
                SelectNumbers.put(j,select_nums);
        }

//        if(contains(GameCode,XY28.ZX.name,XY28.DXDS.name,XY28.ZHDXDS.name,XY28.JZ.name,XY28.BOSE.name,XY28.BZ.name))
//        {
//
//        }
        if(contains(GameCode,XY28.BS.name))
        {
            if(count<3)
            {
                count=0;
            }
            else
            {
                count=1;
            }
        }
        return count;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        if(contains(GameCode,XY28.BS.name))
        {
            IndexRecordBean irb=(IndexRecordBean)buttonNumber.getTag();
            ArrayList<IndexRecordBean> irbs=new ArrayList<>();
            for (int i = 0; i <Numbers.size() ; i++) {
                ArrayList<LotteryButton> btns=Numbers.get(i);
                for (int j = 0; j < btns.size(); j++) {
                    LotteryButton bn=btns.get(j);
                    if(bn.getChecked())
                    {
                        irbs.add((IndexRecordBean)bn.getTag());
                    }

                }
            }
            if(irbs.size()<3)return true;
            for (int i = 0; i <irbs.size() ; i++) {
                if(irbs.get(i).getIndex1()==irb.getIndex1() && irbs.get(i).getIndex2()==irb.getIndex2())
                {
                    return true;
                }
            }
        }
        else {
            return true;
        }
        return false;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode) {
        if(contains(GameCode,XY28.BS.name))
        {
            RandomNumbers(Numbers,true,3);
        }
        else
            random_noremal(Numbers);
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

    private static boolean contains(String gamecode,String ...strs)
    {
        boolean temp=false;
        for (int i = 0; i <strs.length ; i++) {
            temp=gamecode.contains(strs[i]);
            if(temp)return temp;
        }
        return temp;
    }

    private void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,boolean CannotbeSame,int ...args)
    {
        SelectNumbers.clear();
        Random random = new Random();
        SparseArray<String> hashmap=new SparseArray<>();
        ArrayList<Integer> temp=new ArrayList<>();
        for (int i = 0; i <args.length ; i++) {
            temp.clear();
            for (int j = 0; j <10 ; j++) {
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
}
