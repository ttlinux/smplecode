package com.lottery.biying.LotteryMethod.K3;

import android.util.SparseArray;

import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.view.DiceView;
import com.lottery.biying.view.LotteryButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/23.
 */
public class K3 {

    private static final String HZDXDS = "dxds_dxds_hzdxds";
    private static final String HWDXDS = "dxds_dxds_hwdxds";
    private static final String HeZhi = "hz_hz_hz";
    private static final String DTYS = "ds_ds_dtys";
    private static final String EBT = "es_es_ebt";
    private static final String ETH = "es_es_eth";
    private static final String SLH = "ss_ss_slh";
    private static final String SBT = "ss_ss_sbt";
    private static final String STH = "ss_ss_sth";
    public  SparseArray<ArrayList<DiceView>> SelectNumbers=new SparseArray<>();
    private  ArrayList<ArrayList<DiceView>> m_Numbers;
    private static K3 k3;
    public static K3 getInstance()
    {
        if(k3==null)
        {
            k3=new K3();
        }
        return k3;
    }

    public  ArrayList<ArrayList<DiceView>> getCorrent_Numbers() {
        return m_Numbers;
    }

    public  SparseArray<ArrayList<DiceView>> getSelectNumbers() {
        return SelectNumbers;
    }

    public  void setSelectNumbers(SparseArray<ArrayList<DiceView>> selectNumbers) {
        SelectNumbers = selectNumbers;
    }

    public  int CaculateOrderAmount(ArrayList<ArrayList<DiceView>> Numbers, String GameCode) {
        m_Numbers=Numbers;
        SelectNumbers.clear();
        int betOrders = 0;

        for (int i = 0; i < Numbers.size(); i++) {
            ArrayList<DiceView> nums = Numbers.get(i);
            ArrayList<DiceView> select_nums = new ArrayList<>();
            for (int j = 0; j < nums.size(); j++) {
                if (nums.get(j).getCheck()) {
                    select_nums.add(nums.get(j));
                    betOrders++;
                }
            }
            if (select_nums.size() > 0)
                SelectNumbers.put(i, select_nums);
        }
        return betOrders;
    }

    public boolean NumberSelectRule(ArrayList<ArrayList<DiceView>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    public  void RandomNumbers(ArrayList<ArrayList<DiceView>> Numbers, String GameCode) {
        m_Numbers=Numbers;
        int count=Numbers.size()*Numbers.get(0).size();
        SelectNumbers.clear();
        Random random = new Random();
        int index=random.nextInt(count);
        DiceView bn=Numbers.get(index/Numbers.get(0).size()).get(index%Numbers.get(0).size());
        bn.setCheck(true);
        ArrayList<DiceView> sNum=new ArrayList<>();
        sNum.add(bn);
        SelectNumbers.put(index/Numbers.get(0).size(),sNum);
    }

    public  JSONObject Formatnumber() {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonarry = new JSONArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getCorrent_Numbers().size(); i++) {
            ArrayList<DiceView> btns = SelectNumbers.get(i);
            if (btns != null) {
                JSONObject listindexobj = new JSONObject();
                JSONArray temp = new JSONArray();
                for (int j = 0; j < btns.size(); j++) {
                    JSONObject tempobj = new JSONObject();
                    try {
                        tempobj.put(BundleTag.Number, btns.get(j).getValue());
                        if (btns.get(j).getTag() != null)
                            tempobj.put(BundleTag.Index, ((IndexRecordBean) btns.get(j).getTag()).getIndex2());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    temp.put(tempobj);
                    sb.append(btns.get(j).getValue());
                        sb.append(",");
                }
                try {
                    listindexobj.put(BundleTag.List, temp);
                    listindexobj.put(BundleTag.Index, i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonarry.put(listindexobj);
            }
        }
        try {
            sb.deleteCharAt(sb.length() - 1);
            jsonObject.put(BundleTag.Data, jsonarry);
            jsonObject.put(BundleTag.FormatNumber, sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static String[] getArgs(String gamecode, String hm) {
        String args[] = null;
        if (gamecode.contains(HZDXDS) || gamecode.contains(HWDXDS) || gamecode.contains(HeZhi) || gamecode.contains(DTYS)) {
            args = new String[1];
            args[0] = hm;
        }
        if (gamecode.contains(EBT) ) {
            args = new String[2];
            args[0] = String.valueOf(hm.toCharArray()[0]);
            args[1] = String.valueOf(hm.toCharArray()[1]);
        }

        if (gamecode.contains(SLH) || gamecode.contains(STH) || gamecode.contains(SBT) || gamecode.contains(ETH) ){
            args = new String[3];
            args[0] = String.valueOf(hm.toCharArray()[0]);
            args[1] = String.valueOf(hm.toCharArray()[1]);
            args[2] = String.valueOf(hm.toCharArray()[2]);
        }
        return args;
    }
}
