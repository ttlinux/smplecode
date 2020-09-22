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
 * Created by Administrator on 2018/4/17.
 */
public class X1 extends BaseAnalysisClass {

    private final String X1_RENXUAN_FS="rx1z1_fs";//任选一任选复式
    private final String X1_DWD="dwd_dwd";//定位胆
    private final String X1_QSBDW="bdw_bdw";//前三不定位


    public static X1 x1;
    public static X1 getInstance()
    {
        if(x1==null)
            x1=new X1();
        return x1;
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
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        if(GameCode.contains(X1_RENXUAN_FS) || GameCode.contains(X1_QSBDW))
        {
            random(Numbers,11);
        }
        else
        {
            random(Numbers,55);
        }
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


    private void random(ArrayList<ArrayList<LotteryButton>> Numbers,int ramdons)
    {
        SelectNumbers.clear();
        Random random = new Random();
        int index=random.nextInt(ramdons);
        LotteryButton bn=Numbers.get(index/11).get(index%11);
        bn.setChecked(true);
        ArrayList<LotteryButton> sNum=new ArrayList<>();
        sNum.add(bn);
        SelectNumbers.put(index/11,sNum);
    }
}
