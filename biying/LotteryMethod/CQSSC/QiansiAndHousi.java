package com.lottery.biying.LotteryMethod.CQSSC;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.LotteryMethod.BaseAnalysisClass;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HandleLotteryOrder;
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
public class QiansiAndHousi extends BaseAnalysisClass {


    public final String ZX_FS = "q4_zx_fs"; // 前四直选复式
    public final String ZX_ZX4 = "q4_zux_zux4"; // 前四组选组选4
    public final String ZX_ZX6 = "q4_zux_zux6"; // 前四组选组选6
    public final String ZX_ZX12 = "q4_zux_zux12"; // 前四组选组选12
    public final String ZX_ZX24 = "q4_zux_zux24"; // 前四组选组选24
    public final String BDW_YMBDW = "q4_bdw_1mbdw"; // 前四不定位一码不定位
    public final String BDW_EMBDW = "q4_bdw_2mbdw"; // 前四不定位二码不定位

    public final String HS_ZX_FS = "h4_zx_fs"; // 后四直选复式
    public final String HS_ZX_ZX4 = "h4_zux_zux4"; // 后四组选组选4
    public final String HS_ZX_ZX6 = "h4_zux_zux6"; // 后四组选组选6
    public final String HS_ZX_ZX12 = "h4_zux_zux12"; // 后四组选组选12
    public final String HS_ZX_ZX24 = "h4_zux_zux24"; // 后四组选组选24
    public final String HS_BDW_YMBDW = "h4_bdw_1mbdw"; // 后四不定位一码不定位
    public final String HS_BDW_EMBDW = "h4_bdw_2mbdw"; // 后四不定位二码不定位

    private String SI[] = {ZX_FS, BDW_YMBDW, ZX_ZX4, ZX_ZX6, ZX_ZX12, ZX_ZX24, BDW_EMBDW};
    private String HSI[] = {HS_ZX_FS, HS_BDW_YMBDW, HS_ZX_ZX4, HS_ZX_ZX6, HS_ZX_ZX12, HS_ZX_ZX24, HS_BDW_EMBDW};
    private int args[][] = {{1, 1}, {2}, {1, 2}, {4}, {2}};

    public static QiansiAndHousi qiansiAndHousi;

    public static QiansiAndHousi getInstance() {
        if (qiansiAndHousi == null)
            qiansiAndHousi = new QiansiAndHousi();
        return qiansiAndHousi;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int betOrders = 1;
        boolean isselect = false;
        for (int i = 0; i < SI.length; i++) {
            boolean equal=GameCode.contains(SI[i]) || GameCode.contains(HSI[i]);
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
            int list[] = new int[SelectNumbers.get(y).size()];
            for (int e = 0; e < SelectNumbers.get(y).size(); e++) {
                list[e] = Integer.valueOf(SelectNumbers.get(y).get(e).getText());
            }
            lists.add(list);
        }

        if (GameCode.contains(ZX_ZX4) || GameCode.contains(HS_ZX_ZX4)) {
            int temp[] = caculate(lists.get(0), lists.get(1));
            return Zuxuan4(temp[0], temp[1], temp[2]);
        }
        if (GameCode.contains(ZX_ZX6) || GameCode.contains(HS_ZX_ZX6)) {
            return Zuxuan6(lists.get(0).length);
        }
        if (GameCode.contains(ZX_ZX12) || GameCode.contains(HS_ZX_ZX12)) {
            return Zuxuan12(lists.get(0), lists.get(1));
        }
        if (GameCode.contains(ZX_ZX24) || GameCode.contains(HS_ZX_ZX24)) {
            return Zuxuan24(lists.get(0).length);
        }
        if (GameCode.contains(BDW_EMBDW) || GameCode.contains(HS_BDW_EMBDW)) {

            return Two_Budingwei(lists.get(0));
        }


        return betOrders;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        if (eq(GameCode, SI[0], SI[1], HSI[0], HSI[1])) {
            random_noremal(Numbers);
            return;
        }
        for (int i = 2; i < SI.length; i++) {
            if (GameCode.contains(SI[i]) || GameCode.contains(HSI[i])) {
                Zuhe(Numbers, true, args[i - 2]);
                break;
            }
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        if(eq(GameCode,HS_ZX_FS,ZX_FS))
        {
            if(eq(GameCode,HS_ZX_FS))
                return Formatnumber_si(SelectNumbers,"-,",0);
            else
                return Formatnumber_si(SelectNumbers,",-",1);
        }
        else
        {
            return Formatnumber_si(SelectNumbers,"",2);
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
                    jsonObject.put(BundleTag.FormatNumber,sb.toString()+appendtr);
                    break;
                case 2:
                    jsonObject.put(BundleTag.FormatNumber,sb.toString());
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private boolean eq(String gamecode, String... strs) {
        boolean temp = false;
        for (int i = 0; i < strs.length; i++) {
            temp = gamecode.contains(strs[i]);
            if (temp) return temp;
        }
        return temp;
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

    private int Zuxuan4(int x, int y, int z) {
        int chonghao = y * (y - 1);
        int x_value = y * (x - y);
        int z_value = x * (z - y);
        return chonghao + x_value + z_value;
    }

    private int Zuxuan6(int size) {
        if (size < 2) return 0;
        //(size-1)!
        return size * (size - 1) / 2;
    }

    private int Zuxuan12(int ch[], int dh[]) {
        int sum = 0;
        ArrayList<int[]> lists = new ArrayList<>();
        for (int i = 0; i < dh.length; i++) {
            int index = dh[i];
            for (int j = 0; j < dh.length; j++) {
                int index2 = dh[j];
                if (index == index2)
                    continue;
                for (int k = 0; k < ch.length; k++) {
                    if (ch[k] != index && ch[k] != index2) {
                        boolean has = false;
                        for (int l = 0; l < lists.size(); l++) {
                            int temp[] = lists.get(l);
                            if (temp[0] == index2 && temp[1] == index
                                    && temp[2] == ch[k])
                                has = true;
                        }
                        if (has) continue;
                        int save[] = {index, index2, ch[k]};
                        lists.add(save);
                        sum++;
                        System.err.println(index + " " + index2 + " " + ch[k]);
                    }
                }
            }
        }
        return sum;
    }

    private int Zuxuan24(int size) {
        if (size < 4) return 0;
        int temp[] = {1, 5, 15, 35, 70, 126, 210};
        return temp[size - 4];
    }

    private int Two_Budingwei(int ch[]) {
        if (ch.length < 2) return 0;
        return ch.length * (ch.length - 1) / 2;
    }

    private int[] caculate(int ch[], int dh[]) {
        int result[] = new int[3];
        int count = 0;
        for (int i = 0; i < ch.length; i++) {
            int index = ch[i];
            for (int j = 0; j < dh.length; j++) {
                int index2 = dh[j];
                if (index == index2)
                    count++;
            }
        }
        result[0] = dh.length;
        result[1] = count;
        result[2] = ch.length;
        return result;
    }


}
