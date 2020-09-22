package com.lottery.biying.LotteryMethod.FC3D;

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
 * Created by Administrator on 2018/4/19.
 */
public class FC3D_Sanxing extends BaseAnalysisClass{

    private final String SX_ZX_FS="3x_zx_fs";//三星直选复式
    private final String SX_ZX_HZ="3x_zx_hz";//三星直选和值
    private final String SX_ZX_KD="3x_zx_kd";//三星直选跨度
    private final String SX_ZUX_Z3="3x_zux_z3";//三星组三
    private final String SX_ZUX_Z6="3x_zux_z6";//三星组六

    private final String SX_ZUX_HZ="3x_zux_hz";//三星组三和值
    private final String SX_ZUX_BD="3x_zux_bd";//三星组选包胆
    private final String SX_YMBDW="3x_bdw_ymbdw";//三星一码不定位
    private final String SX_EMBDW="3x_bdw_embdw";//三星二码不定位

    private  int Zuxuan_hezhi_Array[];

    public static FC3D_Sanxing fsx;
    public static FC3D_Sanxing getInstance()
    {
        if(fsx==null)
            fsx=new FC3D_Sanxing();
        return fsx;
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

        if(GameCode.contains(SX_ZX_FS) || GameCode.contains(SX_YMBDW))
        {
            int count=1;
            for (int i = 0; i <SelectNumbers.size() ; i++) {
                count=count*SelectNumbers.valueAt(i).size();
            }
            return count;
        }

        if(GameCode.contains(SX_ZX_HZ))
        {
            return Hezhi();
        }

        if(GameCode.contains(SX_ZX_KD))
        {
            return Kuadu();
        }
        if(GameCode.contains(SX_ZUX_Z3))
        {
            int size=SelectNumbers.valueAt(0).size();
            return size*(size-1);
        }
        if(GameCode.contains(SX_ZUX_Z6))
        {
            return Zu6();
        }
        if(GameCode.contains(SX_ZUX_HZ))
        {
            return Zuxuan_Hezhi();
        }
        if(GameCode.contains(SX_ZUX_BD))
        {
            return Baodan();
        }
        if(GameCode.contains(SX_EMBDW))
        {
            return Two_Budingwei();
        }
        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        if (GameCode.contains(SX_ZUX_BD)) {
            if (buttonNumber.getChecked())
                return false;
            else {
                for (int i = 0; i < Numbers.size(); i++) {
                    ArrayList<LotteryButton> btns = Numbers.get(i);
                    for (int j = 0; j < btns.size(); j++) {
                        btns.get(j).setChecked(false);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        if (GameCode.contains(SX_ZUX_Z3) || GameCode.contains(SX_EMBDW)) {
            Zuhe(Numbers, true, 2);
        } else if (GameCode.contains(SX_ZUX_Z6)) {
            Zuhe(Numbers, true, 3);
        } else {
            random_noremal(Numbers);
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
            return Formatnumber_si(SelectNumbers);
    }

    private int Two_Budingwei() {
        int size=SelectNumbers.valueAt(0).size();
        if (size < 2) return 0;
        return size * (size - 1) / 2;
    }

    private int Baodan() {
        return 54;
    }

    private int Zuxuan_Hezhi() {
        int ch[]=getNumbers().get(0);
        if (Zuxuan_hezhi_Array == null) {
            Zuxuan_hezhi_Array = new int[26];
            for (int i = 0; i < 26; i++) {
                Zuxuan_hezhi_Array[i] = Zuxuan_Hezhi_order(i + 1);
            }
        }

        int sum = 0;
        for (int i = 0; i < ch.length; i++) {
            sum = sum + Zuxuan_hezhi_Array[ch[i] - 1];
        }
        return sum;
    }

    private int Zuxuan_Hezhi_order(int limit) {
        int ch[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<String> list = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < ch.length; i++) {
            int index = ch[i];
            for (int j = 0; j < ch.length; j++) {
                int index2 = ch[j];
                for (int k = 0; k < ch.length; k++) {
                    int index3 = ch[k];
                    if (index2 == index && index3 == index2)
                        continue;
                    boolean has = false;
                    for (int l = 0; l < list.size(); l++) {
                        String temp = list.get(l);
                        if (temp.contains(index + "") && temp.contains(index2 + "") && temp.contains(index3 + "")) {
                            has = true;
                            break;
                        }

                    }
                    if (has) continue;
                    if (index + index2 + index3 == limit) {
                        list.add("" + index + index2 + index3);
                        sum++;
                    }


                }
            }
        }
        return sum;
    }


    private int Kuadu() {

        int ch[]=getNumbers().get(0);

        int temp[] = {10, 54, 96, 126, 144, 150, 144, 126, 96, 54};
        int sum = 0;
        for (int i = 0; i < ch.length; i++) {
            sum = sum + temp[ch[i]];
        }
        return sum;
    }

    private ArrayList<int[]> getNumbers()
    {
        ArrayList<int[]> lists = new ArrayList<>();
        for (int y = 0; y < SelectNumbers.size(); y++) {
            int list[] = new int[SelectNumbers.valueAt(y).size()];
            for (int e = 0; e < SelectNumbers.valueAt(y).size(); e++) {
                list[e] = Integer.valueOf(SelectNumbers.valueAt(y).get(e).getText());
            }
            lists.add(list);
        }
        return lists;
    }

    private int Zu6() {
        int ch[]=getNumbers().get(0);
        if (ch.length < 3) return 0;
        int temp[] = {1, 4, 10, 20, 35, 56, 84, 120};
        return temp[ch.length - 3];
    }


    private int Hezhi() {
        int ch[]=getNumbers().get(0);
        int sum = 0;
        int together = 0;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] < 10 || ch[i] > 17) {
                int temp = 0;
                if (ch[i] < 10) {
                    temp = ch[i] + 1;
                } else {
                    temp = Math.abs(ch[i] - 28);
                }
                sum = temp * (temp + 1) / 2;
            } else {

                if (ch[i] < 14) {
                    int args[] = {63, 69, 73, 75};
                    sum = args[ch[i] - 10];
                } else {
                    int args[] = {75, 73, 69, 63};
                    sum = args[ch[i] - 14];
                }
            }
            together = together + sum;
        }
        return together;
    }

    private void Zuhe(ArrayList<ArrayList<LotteryButton>> Numbers, boolean CanbeSame, int... args) {
        SelectNumbers.clear();
        Random random = new Random();
        SparseArray<String> hashmap = new SparseArray<>();
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            temp.clear();
            for (int j = 0; j < 10; j++) {
                if (CanbeSame && hashmap.get(j) != null) continue;
                temp.add(j);
            }
            ArrayList<LotteryButton> sNum = new ArrayList<>();
            for (int j = 0; j < args[i]; j++) {
                int index = random.nextInt(temp.size());
                if (CanbeSame) hashmap.put(temp.get(index), "");
                LotteryButton bn = Numbers.get(i).get(temp.get(index));
                bn.setChecked(true);
                sNum.add(bn);
                temp.remove(index);
            }
            SelectNumbers.put(i, sNum);
        }
    }

    private void random_noremal(ArrayList<ArrayList<LotteryButton>> Numbers) {
        SelectNumbers.clear();
        Random random = new Random();
        for (int i = 0; i < Numbers.size(); i++) {
            ArrayList<LotteryButton> buttonnumbers = Numbers.get(i);
            ArrayList<LotteryButton> sNum = new ArrayList<>();
            int index = random.nextInt(10);
            buttonnumbers.get(index).setChecked(true);
            sNum.add(buttonnumbers.get(index));
            SelectNumbers.put(i, sNum);
        }
    }

    private JSONObject Formatnumber_si(SparseArray<ArrayList<LotteryButton>> SelectNumbers)
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
            sb.deleteCharAt(sb.length() - 1);
            jsonObject.put(BundleTag.Data, jsonarry);
            jsonObject.put(BundleTag.FormatNumber,sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
