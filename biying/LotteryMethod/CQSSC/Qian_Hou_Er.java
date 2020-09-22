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
 * Created by Administrator on 2018/4/11.
 */
public class Qian_Hou_Er extends BaseAnalysisClass{

    // ssc玩法 - 前二
    public final String QE_ZX_FS = "q2_zx_fs"; // 前二直选复式
    public final String QE_ZX_HZ = "q2_zx_hz"; // 前二直选和值
    public final String QE_ZX_KD = "q2_zx_kd";// 前二直选跨度
    public final String QE_ZUX_FS = "q2_zux_fs"; // 前二组选复式
    public final String QE_ZUX_HZ = "q2_zux_hz"; // 前二组选和值
    public final String QE_ZUX_BD = "q2_zux_bd"; // 前二组选包胆

    // ssc玩法 - 后二
    public final String HE_ZX_FS = "h2_zx_fs"; // 后二直选复式
    public final String HE_ZX_HZ = "h2_zx_hz"; // 后二直选和值
    public final String HE_ZX_KD = "h2_zx_kd";// 后二直选跨度
    public final String HE_ZUX_FS = "h2_zux_fs"; // 后二组选复式
    public final String HE_ZUX_HZ = "h2_zux_hz"; // 后二组选和值
    public final String HE_ZUX_BD = "h2_zux_bd"; // 后二组选包胆

    private String QER[]={QE_ZX_FS,QE_ZX_HZ,QE_ZX_KD,QE_ZUX_FS,QE_ZUX_HZ,QE_ZUX_BD};
    private String HER[]={HE_ZX_FS,HE_ZX_HZ,HE_ZX_KD,HE_ZUX_FS,HE_ZUX_HZ,HE_ZUX_BD};


    public static Qian_Hou_Er qian_hou_er;
    public static Qian_Hou_Er getInstance()
    {
        if(qian_hou_er==null)
            qian_hou_er=new Qian_Hou_Er();
        return qian_hou_er;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int betOrders = 1;

        for (int i = 0; i <QER.length ; i++) {
            boolean isselect = false;
            boolean equal=GameCode.contains(QER[i]) || GameCode.contains(HER[i]);
            if(equal  && i<1)
            {
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
                    SelectNumbers.put(j,select_nums);
                        betOrders = betOrders * count;
                }
            }
            else
            {
                boolean record=true;
                for (int j = 0; j < Numbers.size(); j++) {
                    ArrayList<LotteryButton> nums = Numbers.get(j);
                    ArrayList<LotteryButton> select_nums = new ArrayList<>();
                    isselect = false;
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
                {
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

                if(eq(GameCode,HE_ZX_HZ,QE_ZX_HZ))//直选和值
                {
                    return ZhiXuan_Hezhi(lists.get(0));
                }

                if(eq(GameCode,HE_ZX_KD,QE_ZX_KD))//直选跨度
                {
                    return ZhiXuan_Kuadu(lists.get(0));
                }

                if(eq(GameCode,HE_ZUX_FS,QE_ZUX_FS))//组选复式
                {
                    return ZuXuanFushi(lists.get(0));
                }

                if(eq(GameCode,HE_ZUX_HZ,QE_ZUX_HZ))//组选和值
                {
                    return ZuXuan_HeZhi(lists.get(0));
                }

                if(eq(GameCode,HE_ZUX_BD,QE_ZUX_BD))//组选包胆
                {
                    return Baodan();
                }
            }
        }

        return betOrders;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        if(eq(GameCode,QE_ZUX_BD,HE_ZUX_BD) )//包胆
        {
            if(buttonNumber.getChecked())
                return false;
            else
            {
                for (int i = 0; i < Numbers.size(); i++) {
                    ArrayList<LotteryButton> btns=Numbers.get(i);
                    for (int j = 0; j <btns.size() ; j++) {
                        btns.get(j).setChecked(false);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode) {
        if(eq(GameCode,HE_ZUX_FS,QE_ZUX_FS))
        {
            Zuhe(Numbers,true,2);
        }
        else
        {
            random_noremal(Numbers);
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        if(GameCode.contains(QE_ZX_FS))
        {
            return Formatnumber_si(SelectNumbers,",-,-,-",0);
        }
        else if(GameCode.contains(HE_ZX_FS))
        {
            return Formatnumber_si(SelectNumbers,"-,-,-,",1);
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
                    jsonObject.put(BundleTag.FormatNumber,sb.toString()+appendtr);
                    break;
                case 1:
                    jsonObject.put(BundleTag.FormatNumber,appendtr+sb.toString());
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


    private void Zuhe(ArrayList<ArrayList<LotteryButton>> Numbers,boolean CanbeSame,int ...args)
    {
        SelectNumbers.clear();
        Random random = new Random();
        SparseArray<String> hashmap=new SparseArray<>();
        ArrayList<Integer> temp=new ArrayList<>();
        for (int i = 0; i <args.length ; i++) {
            temp.clear();
            for (int j = 0; j <10 ; j++) {
                if(CanbeSame && hashmap.get(j)!=null)continue;
                temp.add(j);
            }
            ArrayList<LotteryButton> sNum=new ArrayList<>();
            for (int j = 0; j <args[i] ; j++) {
                int index= random.nextInt(temp.size());
                if(CanbeSame)hashmap.put(temp.get(index), "");
                LotteryButton bn=Numbers.get(i).get(temp.get(index));
                bn.setChecked(true);
                sNum.add(bn);
                temp.remove(index);
            }
            SelectNumbers.put(i,sNum);
        }
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
            SelectNumbers.put(i,sNum);
        }
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

    private int ZhiXuan_Hezhi(int ch[])
    {
        int sum=0;
        for (int i = 0; i < ch.length; i++) {
            if(ch[i]<10)
            {
                sum=sum+ch[i]+1;
            }
            else if(ch[i]>9 && ch[i]<19)
            {
                sum=sum+Math.abs(ch[i]-19);
            }
        }
        return sum;
    }

    private int ZhiXuan_Kuadu(int ch[])
    {
        int sum=0;
        for (int i = 0; i <ch.length ; i++) {
            if(ch[i]==0)
            {
                sum=sum+10;
            }
            else
            {
                sum=sum+18-(ch[i]-1)*2;
            }
        }
        return sum;
    }

    private int ZuXuanFushi(int ch[])
    {
        if(ch.length<2)return 0;
        return ch.length*(ch.length-1)/2;
    }

    private int ZuXuan_HeZhi(int ch[])
    {
        int sum=0;
        for (int i = 0; i < ch.length; i++) {
            if(ch[i]==9)
            {
                sum=sum+5;
            }
            else if(ch[i]<9 || (ch[i]>9 && ch[i]<18))
            {
                int temp=ch[i];
                int index = 0;
                if(ch[i]>9)
                {
                    temp=1+ch[i]%10;
                    temp=temp%2==0?temp/2:temp/2+1;
                    index=4-temp+1;
                }
                else
                {
                    index=temp%2==0?temp/2:temp/2+1;
                }

                sum=sum+index;
            }
        }
        return sum;
    }

    private int Baodan()
    {
        return 9;
    }

}
