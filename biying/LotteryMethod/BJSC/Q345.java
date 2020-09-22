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
public class Q345 extends BaseAnalysisClass {

    private final String Q3_FS="q3_q3_fs";//前三复式
    private final String Q3_CQ3="q3_q3_cq3";//猜前三

    private final String Q4_FS="q4_q4_fs";//前四复式
    private final String Q4_CQ4="q4_q4_cq4";//猜前四

    private final String Q5_FS="q5_q5_fs";//前五复式
    private final String Q5_CQ5="q5_q5_cq5";//猜前五


    public static Q345 q345;
    public static Q345 getInstance()
    {
        if(q345==null)
            q345=new Q345();
        return q345;
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

        if(GameCode.contains(Q3_FS) || GameCode.contains(Q3_CQ3))
        {
            return Q3();
        }

        if(GameCode.contains(Q4_FS) || GameCode.contains(Q4_CQ4))
        {
            return Q4();
        }

        if(GameCode.contains(Q5_FS) || GameCode.contains(Q5_CQ5))
        {
            return Q5();
        }

        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        if(GameCode.contains(Q3_FS) || GameCode.contains(Q3_CQ3))
        {
            Random_Two_numbers(Numbers,true,1,1,1);
        }

        if(GameCode.contains(Q4_FS) || GameCode.contains(Q4_CQ4))
        {
            Random_Two_numbers(Numbers,true,1,1,1,1);
        }

        if(GameCode.contains(Q5_FS) || GameCode.contains(Q5_CQ5))
        {
            Random_Two_numbers(Numbers,true,1,1,1,1,1);
        }

    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        return Formatnumber2(SelectNumbers);
    }

    private int Q3()
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

    private int Q4()
    {
        int count=0;
        for (int i = 0; i < SelectNumbers.valueAt(0).size(); i++) {
            String index=SelectNumbers.valueAt(0).get(i).getText();

            for (int j = 0; j < SelectNumbers.valueAt(1).size(); j++) {
                String index2=SelectNumbers.valueAt(1).get(j).getText();

                for (int k = 0; k < SelectNumbers.valueAt(2).size(); k++) {
                    String index3=SelectNumbers.valueAt(2).get(k).getText();

                    for (int l = 0; l < SelectNumbers.valueAt(3).size(); l++) {
                        String index4=SelectNumbers.valueAt(3).get(l).getText();

                       if(!(index.equalsIgnoreCase(index2))
                               &&!(index.equalsIgnoreCase(index3))
                               &&!(index.equalsIgnoreCase(index4))
                               &&!(index2.equalsIgnoreCase(index3))
                               &&!(index2.equalsIgnoreCase(index4))
                               &&!(index3.equalsIgnoreCase(index4)))
                       {
                           count++;
                       }
                    }

                }
            }
        }
        return count;
    }

    private int Q5()
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

                    for (int l = 0; l < SelectNumbers.valueAt(3).size(); l++) {
                        String index4=SelectNumbers.valueAt(3).get(l).getText();
                        if(index4.equalsIgnoreCase(index2) || index4.equalsIgnoreCase(index) || index4.equalsIgnoreCase(index3))continue;

                        for (int m = 0; m <SelectNumbers.valueAt(4).size() ; m++) {
                            String index5=SelectNumbers.valueAt(4).get(m).getText();
                            if(index5.equalsIgnoreCase(index2) || index5.equalsIgnoreCase(index) || index5.equalsIgnoreCase(index3)
                                    || index5.equalsIgnoreCase(index4))continue;
                            count++;
                        }

                    }

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

    private JSONObject Formatnumber2(SparseArray<ArrayList<LotteryButton>> SelectNumbers)
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
}
