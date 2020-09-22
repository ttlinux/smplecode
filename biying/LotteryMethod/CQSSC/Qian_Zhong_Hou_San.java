package com.lottery.biying.LotteryMethod.CQSSC;

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
 * Created by Administrator on 2018/4/10.
 */
public class Qian_Zhong_Hou_San extends BaseAnalysisClass {

    // ssc玩法 - 前三
    public final String QSAN_ZX_FS = "q3_zx_fs"; // 前三直选单式
    public final String QSAN_ZX_HZ = "q3_zx_hz"; // 前三直选和值
    public final String QSAN_ZX_KD = "q3_zx_kd"; // 前三直选跨度
    public final String QSAN_ZX_ZS = "q3_zux_zu3"; // 前三组选组三
    public final String QSAN_ZX_ZL = "q3_zux_zu6"; // 前三组选组六
    public final String QSAN_ZUX_HZ = "q3_zux_hz"; // 前三组选和值
    public final String QSAN_ZUX_BD = "q3_zux_bd"; // 前三组选包胆
    public final String QSAN_BDW_YMBDW = "q3_bdw_1mbdw"; // 前三不定位一码不定位
    public final String QSAN_BDW_EMBDW = "q3_bdw_2mbdw"; // 前三不定位二码不定位

    // ssc玩法 - 中三
    public final String ZSAN_ZX_FS = "z3_zx_fs"; // 中三直选单式
    public final String ZSAN_ZX_HZ = "z3_zx_hz"; // 中三直选和值
    public final String ZSAN_ZX_KD = "z3_zx_kd"; // 中三直选跨度
    public final String ZSAN_ZX_ZS = "z3_zux_zu3"; // 中三组选组三
    public final String ZSAN_ZX_ZL = "z3_zux_zu6"; // 中三组选组六
    public final String ZSAN_ZUX_HZ = "z3_zux_hz"; // 中三组选和值
    public final String ZSAN_ZUX_BD = "z3_zux_bd"; // 中三组选包胆
    public final String ZSAN_BDW_YMBDW = "z3_bdw_1mbdw"; // 中三不定位一码不定位
    public final String ZSAN_BDW_EMBDW = "z3_bdw_2mbdw"; // 中三不定位二码不定位

    // ssc玩法 - 后三
    public final String HSAN_ZX_FS = "h3_zx_fs"; // 后三直选单式
    public final String HSAN_ZX_HZ = "h3_zx_hz"; // 后三直选和值
    public final String HSAN_ZX_KD = "h3_zx_kd"; // 后三直选跨度
    public final String HSAN_ZX_ZS = "h3_zux_zu3"; // 后三组选组三
    public final String HSAN_ZX_ZL = "h3_zux_zu6"; // 后三组选组六
    public final String HSAN_ZUX_HZ = "h3_zux_hz"; // 后三组选和值
    public final String HSAN_ZUX_BD = "h3_zux_bd"; // 后三组选包胆
    public final String HSAN_BDW_YMBDW = "h3_bdw_1mbdw"; // 后三不定位一码不定位
    public final String HSAN_BDW_EMBDW = "h3_bdw_2mbdw"; // 后三不定位二码不定位


    private String QSan[] = {QSAN_ZX_FS, QSAN_BDW_YMBDW, QSAN_ZX_HZ, QSAN_ZX_KD, QSAN_ZX_ZS, QSAN_ZX_ZL, QSAN_ZUX_HZ, QSAN_ZUX_BD, QSAN_BDW_EMBDW};
    private String ZSan[] = {ZSAN_ZX_FS, ZSAN_BDW_YMBDW, ZSAN_ZX_HZ, ZSAN_ZX_KD, ZSAN_ZX_ZS, ZSAN_ZX_ZL, ZSAN_ZUX_HZ, ZSAN_ZUX_BD, ZSAN_BDW_EMBDW};
    private String HSan[] = {HSAN_ZX_FS, HSAN_BDW_YMBDW, HSAN_ZX_HZ, HSAN_ZX_KD, HSAN_ZX_ZS, HSAN_ZX_ZL, HSAN_ZUX_HZ, HSAN_ZUX_BD, HSAN_BDW_EMBDW};

    private  int Zuxuan_hezhi_Array[];

    public static Qian_Zhong_Hou_San qian_zhong_hou_san;

    public static Qian_Zhong_Hou_San getInstance() {
        if (qian_zhong_hou_san == null)
            qian_zhong_hou_san = new Qian_Zhong_Hou_San();
        return qian_zhong_hou_san;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int betOrders = 1;
        boolean isselect = false;
        for (int i = 0; i < QSan.length; i++) {
            boolean equal=GameCode.contains(QSan[i]) || GameCode.contains(ZSan[i]) || GameCode.contains(HSan[i]);
            if (equal && i < 2) {
                for (int j = 0; j < Numbers.size(); j++) {
                    ArrayList<LotteryButton> nums = Numbers.get(j);
                    int count = 0;
                    ArrayList<LotteryButton> select_nums = new ArrayList<>();
                    for (int k = 0; k < nums.size(); k++) {
                        if (nums.get(k).getChecked()) {
                            count++;
                            isselect = true;
                            select_nums.add(nums.get(k));
                        }
                    }
                    if(select_nums.size()>0)
                    SelectNumbers.put(j, select_nums);
                    betOrders = betOrders * count;
                }
                return betOrders;
            }
        }

        boolean record=true;
        for (int j = 0; j < Numbers.size(); j++) {
            isselect = false;
            ArrayList<LotteryButton> nums = Numbers.get(j);
            ArrayList<LotteryButton> select_nums = new ArrayList<>();
            for (int k = 0; k < nums.size(); k++) {
                if (nums.get(k).getChecked()) {
                    isselect = true;
                    select_nums.add(nums.get(k));
                }
            }
            if(!isselect && record)
            {
                record=false;
            }
            if(select_nums.size()>0)
            SelectNumbers.put(j, select_nums);

        }
        if (!record) {
            return 0;//没选全
        }
        ArrayList<int[]> lists = new ArrayList<>();
        for (int y = 0; y < SelectNumbers.size(); y++) {
            int list[] = new int[SelectNumbers.valueAt(y).size()];
            for (int e = 0; e < SelectNumbers.valueAt(y).size(); e++) {
                list[e] = Integer.valueOf(SelectNumbers.valueAt(y).get(e).getText());
            }
            lists.add(list);
        }

        LogTools.e("gamecode222",GameCode);
        if (eq(GameCode, HSAN_ZX_HZ, ZSAN_ZX_HZ, QSAN_ZX_HZ))//直选和值
        {
            return Hezhi(lists.get(0));
        }
        if (eq(GameCode, HSAN_ZX_KD, ZSAN_ZX_KD, QSAN_ZX_KD))//跨度
        {
            return Kuadu(lists.get(0));
        }

        if (eq(GameCode, HSAN_ZX_ZS, ZSAN_ZX_ZS, QSAN_ZX_ZS))//组三
        {
            return Zu3(lists.get(0));
        }
        if (eq(GameCode, HSAN_ZX_ZL, ZSAN_ZX_ZL, QSAN_ZX_ZL))//组六
        {
            return Zu6(lists.get(0));
        }
        if (eq(GameCode, HSAN_ZUX_HZ, ZSAN_ZUX_HZ, QSAN_ZUX_HZ))//组选和值
        {
            return Zuxuan_Hezhi(lists.get(0));
        }
        if (eq(GameCode, HSAN_ZUX_BD, ZSAN_ZUX_BD, QSAN_ZUX_BD))//包胆
        {
            return Baodan();
        }
        if (eq(GameCode, HSAN_BDW_EMBDW, ZSAN_BDW_EMBDW, QSAN_BDW_EMBDW))//二码不定位
        {
            return Two_Budingwei(lists.get(0));
        }


        return betOrders;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        if (eq(GameCode, HSAN_ZUX_BD, ZSAN_ZUX_BD, QSAN_ZUX_BD)) {
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
        if (eq(GameCode, QSan[4], ZSan[4], HSan[4])) {
            Zuhe(Numbers, true, 2);
        } else if (eq(GameCode, QSan[5], ZSan[5], HSan[5])) {
            Zuhe(Numbers, true, 3);
        } else if (eq(GameCode, QSan[8], ZSan[8], HSan[8])) {
            Zuhe(Numbers, true, 2);
        } else {
            random_noremal(Numbers);
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {

        if(GameCode.contains(HSAN_ZX_FS))
        {
            return Formatnumber_si(SelectNumbers,"-,-,",0);
        }
        else if(GameCode.contains(ZSAN_ZX_FS))
        {
            return Formatnumber_si(SelectNumbers,"-",1);
        }
        else if(GameCode.contains(QSAN_ZX_FS))
        {
            return Formatnumber_si(SelectNumbers,",-,-",2);
        }
        else
        {
            return Formatnumber_si(SelectNumbers,"",3);
        }
    }

    private JSONObject Formatnumber_si(SparseArray<ArrayList<LotteryButton>> SelectNumbers,String appendtr,int type)
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
            jsonObject.put(BundleTag.Data,jsonarry);
            switch (type)
            {
                case 0:
                    jsonObject.put(BundleTag.FormatNumber,appendtr+sb.toString());
                    break;
                case 1:
                    jsonObject.put(BundleTag.FormatNumber,appendtr+","+sb.toString()+","+appendtr);
                    break;
                case 2:
                    jsonObject.put(BundleTag.FormatNumber,sb.toString()+appendtr);
                    break;
                case 3:
                    jsonObject.put(BundleTag.FormatNumber,sb.toString());
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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

    private boolean eq(String gamecode, String... strs) {
        boolean temp = false;
        for (int i = 0; i < strs.length; i++) {
            temp = gamecode.contains(strs[i]);
            if (temp) return temp;
        }
        return temp;
    }

    private int Hezhi(int ch[]) {
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

    private int Kuadu(int ch[]) {
        int temp[] = {10, 54, 96, 126, 144, 150, 144, 126, 96, 54};
        int sum = 0;
        for (int i = 0; i < ch.length; i++) {
            sum = sum + temp[ch[i]];
        }
        return sum;
    }

    private int Zu3(int ch[]) {
        if (ch.length < 2) return 0;
        return ch.length * (ch.length - 1);
    }

    private int Zu6(int ch[]) {
        if (ch.length < 3) return 0;
        int temp[] = {1, 4, 10, 20, 35, 56, 84, 120};
        return temp[ch.length - 3];
    }


    private int Zuxuan_Hezhi(int ch[]) {
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

    private int Baodan() {
        return 54;
    }

    private int Two_Budingwei(int ch[]) {
        if (ch.length < 2) return 0;
        return ch.length * (ch.length - 1) / 2;
    }
}
