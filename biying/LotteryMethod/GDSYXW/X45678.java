package com.lottery.biying.LotteryMethod.GDSYXW;

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
public class X45678 extends BaseAnalysisClass{

    private String X4_FS="x4_rx4z4_fs";//任选四任选复式
    private String X4_DT="x4_rx4z4_dt";//任选四任选胆拖

    private String X5_FS="x5_rx5z5_fs";//任选五任选复式
    private String X5_DT="x5_rx5z5_dt";//任选五任选胆拖

    private String X6_FS="x6_rx6z5_fs";//任选六任选复式
    private String X6_DT="x6_rx6z5_dt";//任选六任选复式

    private String X7_FS="x7_rx7z5_fs";//任选七任选复式
    private String X7_DT="x7_rx7z5_dt";//任选七任选复式

    private String X8_FS="x8_rx8z5_fs";//任选八任选复式
    private String X8_DT="x8_rx8z5_dt";//任选八任选胆拖


    private String FSS[]={X4_FS,X5_FS,X6_FS,X7_FS,X8_FS};
    private String DTS[]={X4_DT,X5_DT,X6_DT,X7_DT,X8_DT};
    private int FSarg[]={4,5,6,7,8};
    private int DTarg[][]={{1,3},{1,4},{1,5},{1,6},{1,7}};


    public static X45678 xx;
    public static X45678 getInstance()
    {
        if(xx==null)
            xx=new X45678();
        return xx;
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

        if(GameCode.contains(X4_FS))
        {
            int count=0;
            for (int j= 3; j < SelectNumbers.valueAt(0).size(); j++) {
                int mcount=0;
                for (int i = 2; i < j; i++) {
                    mcount=mcount+(i-1)*i/2;
                }
                count=count+mcount;
            }
            return count;
        }

        if(GameCode.contains(X5_FS))
        {
            int r_count=0;
            for (int k = 4; k < SelectNumbers.valueAt(0).size(); k++) {
                int count = 0;
                for (int j = 3; j < k; j++) {
                    int mcount = 0;
                    for (int i = 2; i < j; i++) {
                        mcount = mcount + (i - 1) * i / 2;
                    }
                    count = count + mcount;
                }
                r_count=r_count+count;
            }
            return r_count;
        }

        if(GameCode.contains(X6_FS))
        {
            if(SelectNumbers.valueAt(0).size()<6)return 0;
            int temp[]={1,7,28,84,210,462};
            return temp[SelectNumbers.valueAt(0).size()-6];
        }

        if(GameCode.contains(X7_FS))
        {
            if(SelectNumbers.valueAt(0).size()<7)return 0;
            int temp[]={1,8,36,120,330};
            return temp[SelectNumbers.valueAt(0).size()-7];
        }

        if(GameCode.contains(X8_FS))
        {
            if(SelectNumbers.valueAt(0).size()<8)return 0;
            int temp[]={1,9,45,165};
            return temp[SelectNumbers.valueAt(0).size()-8];
        }

        if(GameCode.contains(X4_DT))
        {
            int temp=SelectNumbers.valueAt(1).size()-Dantuo_getSameNumbers_count();
            if(SelectNumbers.valueAt(1).size()<3)return 0;
            int mcount=0;
            for (int i = 2; i < temp; i++) {
                mcount=mcount+(i-1)*i/2;
            }
            return mcount;
        }
        if(GameCode.contains(X5_DT))
        {
            int temp=SelectNumbers.valueAt(1).size()-Dantuo_getSameNumbers_count();
            if(SelectNumbers.valueAt(1).size()<4)return 0;
            int count=0;
            for (int j= 3; j < temp; j++) {
                int mcount=0;
                for (int i = 2; i < j; i++) {
                    mcount=mcount+(i-1)*i/2;
                }
                count=count+mcount;
            }
            return count;
        }

        if(GameCode.contains(X6_DT))
        {
            int temp=SelectNumbers.valueAt(1).size()-Dantuo_getSameNumbers_count();
            if(SelectNumbers.valueAt(1).size()<5)return 0;
            int r_count=0;
            for (int k = 4; k < temp; k++) {
                int count = 0;
                for (int j = 3; j < k; j++) {
                    int mcount = 0;
                    for (int i = 2; i < j; i++) {
                        mcount = mcount + (i - 1) * i / 2;
                    }
                    count = count + mcount;
                }
                r_count=r_count+count;
            }
            return r_count;
        }

        if(GameCode.contains(X7_DT))
        {
            if(SelectNumbers.valueAt(1).size()<6)return 0;
            int temp=SelectNumbers.valueAt(1).size()-Dantuo_getSameNumbers_count();
            if(temp-6<0)return 0;
            int tempconut[]={1,7,28,84,210};
            return tempconut[temp-6];
        }

        if(GameCode.contains(X8_DT))
        {
            if(SelectNumbers.valueAt(1).size()<7)return 0;
            int temp=SelectNumbers.valueAt(1).size()-Dantuo_getSameNumbers_count();
            if(temp-7<0)return 0;
            int tempconut[]={1,8,36,120};
            return tempconut[temp-7];
        }


        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {

        if(E(GameCode,DTS))
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
        int fs=eq(GameCode,FSS);
        if(fs>-1)
        {
            Random_Two_numbers(Numbers,true,FSarg[fs]);
            return;
        }
        int dt=eq(GameCode,DTS);
        if(dt>-1)
        {
            Random_Two_numbers(Numbers,true,DTarg[dt]);
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        if(E(GameCode,FSS))
        {
            return Formatnumber_method(SelectNumbers, "");
        }
        if(E(GameCode,DTS))
        {
            return Formatnumber_method_2(SelectNumbers);
        }

        return new JSONObject();
    }

    private int Dantuo_getSameNumbers_count()
    {
        //胆拖
        String index=SelectNumbers.valueAt(0).get(0).getText();

        for (int i = 0; i <SelectNumbers.valueAt(1).size() ; i++) {
            if(index.equalsIgnoreCase(SelectNumbers.valueAt(1).get(i).getText()))
            {
               return 1;
            }
        }
        return 0;
    }

    private boolean E(String Gamecode,String ...strs)
    {
        for (int i = 0; i <strs.length ; i++) {
            if(Gamecode.contains(strs[i]))return true;
        }
        return false;
    }

    private int eq(String Gamecode,String ...strs)
    {
        for (int i = 0; i <strs.length ; i++) {
            if(Gamecode.contains(strs[i]))return i;
        }
        return -1;
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
