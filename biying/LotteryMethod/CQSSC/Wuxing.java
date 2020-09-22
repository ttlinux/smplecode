package com.lottery.biying.LotteryMethod.CQSSC;

import android.util.SparseArray;

import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/6.
 */
public class Wuxing extends BaseAnalysisClass {

    private final String ZX_FS = "5x_zx_fs"; // 五星直选复式
    private final String ZX_ZX5 = "5x_zux_zux5"; // 五星组选组选5
    private final String ZX_ZX10 = "5x_zux_zux10"; // 五星组选组选10
    private final String ZX_ZX20 = "5x_zux_zux20"; // 五星组选组选20
    private final String ZX_ZX30 = "5x_zux_zux30"; // 五星组选组选30
    private final String ZX_ZX60 = "5x_zux_zux60"; // 五星组选组选30
    private final String ZX_ZX120 = "5x_zux_zux120"; // 五星组选组选120
    private final String QW_YFFS = "5x_qw_yffs"; // 五星趣味一帆风顺
    private final String QW_HSCS = "5x_qw_hscs"; // 五星趣味好事成双
    private final String QW_SXBX = "5x_qw_sxbx"; // 五星趣味三星报喜
    private final String QW_SJFC = "5x_qw_sjfc"; // 五星趣味四季发财
    private final String BDW_YMBDW = "5x_bdw_ymbdw"; // 五星不定位一码不定位
    private final String BDW_EMBDW = "5x_bdw_ermbdw"; // 五星不定位二码不定位
    private final String BDW_SMBDW = "5x_bdw_smbdw"; // 五星不定位三码不定位

    private String WuXing[] = {ZX_FS, QW_YFFS
            , QW_HSCS, QW_SXBX, QW_SJFC, BDW_YMBDW,ZX_ZX5, ZX_ZX10, ZX_ZX20, ZX_ZX30, ZX_ZX60, ZX_ZX120,BDW_EMBDW, BDW_SMBDW };

    private int Wuxing_random[][]={{1,1},{1,1},{1,2},{2,1},{1,3},{5},{2},{3}};

    public static Wuxing wuxing;
    public static Wuxing getInstance()
    {
        if(wuxing==null)
            wuxing=new Wuxing();
        return wuxing;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int betOrders = 1;
        boolean isselect = false;
        for (int i = 0; i < WuXing.length; i++) {
            if (GameCode.contains(WuXing[i]) && i<6) {
                for (int j = 0; j < Numbers.size(); j++) {
                    isselect = false;
                    ArrayList<LotteryButton> nums = Numbers.get(j);
                    int count = 0;
                    ArrayList<LotteryButton> select_nums =new ArrayList<>();
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

        if (!record)
            return 0;//没选全
        //转成数组
        ArrayList<int[]> lists = new ArrayList<>();
        for (int y = 0; y < SelectNumbers.size(); y++) {
            int list[] = new int[SelectNumbers.get(y).size()];
            for (int e = 0; e < SelectNumbers.get(y).size(); e++) {
                list[e] = Integer.valueOf(SelectNumbers.get(y).get(e).getText());
            }
            lists.add(list);
        }

        if (GameCode.contains(ZX_ZX5) || GameCode.contains(ZX_ZX10)) {
            int xyz[] = caculate(lists.get(0), lists.get(1));
            return Zuxuan5And10(xyz[0], xyz[1], xyz[2]);
        }
        if (GameCode.contains(ZX_ZX20)) {
            int xyz[] = caculate(lists.get(0), lists.get(1));
            return Zuxuan20(xyz[0], xyz[1], xyz[2]);
        }
        if (GameCode.contains(ZX_ZX30)) {
            return Zuxuan30(lists.get(0), lists.get(1));
        }
        if (GameCode.contains(ZX_ZX60)) {
            return Zuxuan60(lists.get(0), lists.get(1));
        }
        if (GameCode.contains(ZX_ZX120)) {
            return Zuxuan120(lists.get(0));
        }

        if (GameCode.contains(BDW_EMBDW)) {
            return Two_Budingwei(lists.get(0));
        }

        if (GameCode.contains(BDW_SMBDW)) {
            return Three_Budingwei(lists.get(0));
        }

        return betOrders;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode) {
        if(eq(GameCode,ZX_FS,QW_YFFS,QW_HSCS,QW_SXBX,QW_SJFC,BDW_YMBDW))
        {
            random_noremal(Numbers);
            return;
        }
        for (int i = 6; i < WuXing.length; i++) {
            if(GameCode.contains(WuXing[i]))
            {
                Zuhe(Numbers, true, Wuxing_random[i-6]);
                break;
            }
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


    private boolean eq(String gamecode,String ...strs)
    {
        boolean temp=false;
        for (int i = 0; i <strs.length ; i++) {
            temp=gamecode.contains(strs[i]);
            if(temp)return temp;
        }
        return temp;
    }

    private void random_noremal(ArrayList<ArrayList<LotteryButton>> Numbers)
    {
        SelectNumbers.clear();
        Random random = new Random();
        for (int i = 0; i <Numbers.size() ; i++) {
            ArrayList<LotteryButton> buttonnumbers=Numbers.get(i);
            ArrayList<LotteryButton> sNum=new ArrayList<>();
            int index=random.nextInt(10);
            buttonnumbers.get(index).setChecked(true);
            sNum.add(buttonnumbers.get(index));
            SelectNumbers.put(i, sNum);
        }
    }

    private void Zuhe(ArrayList<ArrayList<LotteryButton>> Numbers,boolean CannotbeSame,int ...args)
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

    private int Zuxuan5And10(int x, int y, int z) {
        return y * (y - 1) + (z - y) * y + (x - y) * z;
    }
    private int Two_Budingwei(int ch[])
    {
        if(ch.length<2)return 0;
        return ch.length*(ch.length-1)/2;
    }

    private int Three_Budingwei(int ch[])
    {
        if(ch.length<3)return 0;
        int sum = 0;
        for (int i = 2; i < ch.length-1; i++) {
            sum=sum+(i+1)*i/2;
        }
        return sum+1;
    }

    private int Zuxuan20(int x, int y, int z) {
        //x单号 y 相同的号 z 重号
        int chonghao = y * (y - 1) * (y - 2) / 2;
        int z_value;//x*(x-1)/2*(z-y)
        int together;//z*(y+x-3)*(x-y)/2+(z-y)*(x-y)
        if (x - y > 0 || z - y > 0) {
            //(y+1)*(x-y)
            int temp = z;
            z = y;
            chonghao = y * (y - 1) * (y - 2) / 2;
            z_value = x * (x - 1) / 2 * (z - y);
            together = z * (y + x - 3) * (x - y) / 2 + (z - y) * (x - y);//xxx
            z = temp;
            int temp_value = together + chonghao + z_value;
            z_value = x * (x - 1) / 2 * (z - y);
            return temp_value + z_value;
        } else {
            return chonghao;
        }
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

    private int Zuxuan30(int ch[], int dh[]) {
        ArrayList<Integer[]> list = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < ch.length; i++) {
            int index1 = ch[i];
            for (int j = 0; j < ch.length; j++) {
                int index2 = ch[j];
                if (index1 == index2)
                    continue;
                for (int k = 0; k < dh.length; k++) {
                    if (dh[k] != index1 && dh[k] != index2) {
                        boolean has = false;
                        for (int l = 0; l < list.size(); l++) {
                            Integer temp[] = (Integer[]) list.get(l);
                            if (temp[1] == index1 && temp[0] == index2
                                    && dh[k] == temp[2])
                                has = true;
                        }
                        if (has)
                            continue;

                        Integer values[] = {index1, index2, dh[k]};
                        list.add(values);
                        sum++;
                    }
                }

            }
        }
        return sum;
    }

    private int Zuxuan60(int ch[], int dh[]) {
        ArrayList<Integer[]> list = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < dh.length; i++) {
            int index1 = dh[i];
            for (int j = 0; j < dh.length; j++) {
                int index2 = dh[j];
                if (index1 == index2) continue;
                for (int k = 0; k < dh.length; k++) {
                    int index3 = dh[k];
                    if (index3 == index2 || index3 == index1) continue;
                    for (int g = 0; g < ch.length; g++) {
                        if (ch[g] != index1 && ch[g] != index2 && ch[g] != index3) {
                            boolean has = false;
                            int multiply = 0;
                            if (index1 == 0) {
                                multiply = index2 * index3;
                            } else if (index2 == 0) {
                                multiply = index1 * index3;
                            } else if (index3 == 0) {
                                multiply = index2 * index1;
                            } else
                                multiply = index2 * index1 * index3;
                            int plus = index1 + index2 + index3;
                            for (int l = 0; l < list.size(); l++) {
                                Integer[] temp = list.get(l);
                                if (multiply == temp[0] && temp[1] == plus && ch[g] == temp[2]) {
                                    has = true;
                                }
                            }
                            if (has) continue;

                            list.add(new Integer[]{multiply, plus, ch[g]});
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }

    private int Zuxuan120(int ch[]) {
        if (ch.length < 5)
            return 0;
        int zhu_temp[] = {1, 6, 21, 56, 126, 252};
        return zhu_temp[ch.length - 5];
    }
}
