package com.lottery.biying.LotteryMethod.CQSSC;

import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

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
public class RenXuan extends BaseAnalysisClass {

    // ssc玩法 - 任选
    public final String RX_RX2_ZXFS = "rx_rx2_zxfs"; // 任选任选二直选复式
    public static final String RX_RX2_ZUXFS = "rx_rx2_zuxfs"; // 任选任选二组选复式
    public final String RX_RX3_ZXFS = "rx_rx3_zxfs"; // 任选任选三直选复式
    public static final String RX_RX3_ZS = "rx_rx3_zu3"; // 任选任选三组三
    public static final String RX_RX3_ZL = "rx_rx3_zu6"; // 任选任选三组六
    public final String RX_RX4_ZXFS = "rx_rx4_zxfs"; // 任选任选四直选复式


    public static RenXuan renXuan;
    public static RenXuan getInstance()
    {
        if(renXuan==null)
            renXuan=new RenXuan();
        return renXuan;
    }

    @Override
    public int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers, String GameCode) {
        SelectNumbers.clear();
        int betOrders = 1;
        boolean isselect=false;
        boolean record=true;
        for (int i = 0; i <Numbers.size() ; i++) {
            ArrayList<LotteryButton> nums=Numbers.get(i);
            ArrayList<LotteryButton> select_nums = new ArrayList<>();
            isselect=false;
            for (int j = 0; j < nums.size(); j++) {
                if (nums.get(j).getChecked()) {
                    select_nums.add(nums.get(j));
                    betOrders++;
                    isselect=true;
                }
            }
            if(!isselect && record)
            {
                record=false;
            }
            if(select_nums.size()>0)
                  SelectNumbers.put(i,select_nums);
        }

//        if(!record)
//            return 0;

        if(GameCode.contains("|"))
        {
            String datas[]=GameCode.split("\\|");
            String ws=datas[0];
            String Real_GameCode=datas[1];
            if(Real_GameCode.contains(RX_RX2_ZUXFS))
            {
                return RenXuan2_Zuxian(ws);
            }
            if(Real_GameCode.contains(RX_RX3_ZS))
            {
                LogTools.e("Real_GameCode",Real_GameCode);
                return ZuXuan3(ws);
            }
            if(Real_GameCode.contains(RX_RX3_ZL))
            {
                return ZuXuan6(ws);
            }
        }
        else
        {
            if(GameCode.contains(RX_RX2_ZXFS))
            {
                return ZhiXuan2_fushi();
            }
            if(GameCode.contains(RX_RX3_ZXFS))
            {
                return ZhiXuan3_fushi();
            }
            if(GameCode.contains(RX_RX4_ZXFS))
            {
                return ZhiXuan4_fushi();
            }
        }

        return 0;
    }

    @Override
    public boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers, LotteryButton buttonNumber, String GameCode) {
        return true;
    }

    @Override
    public void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode) {
        if(GameCode.contains(RX_RX2_ZXFS))
        {
            random_noremal(Numbers);
        }
        if(GameCode.contains(RenXuan.RX_RX3_ZS) )
        {
            Zuhe(Numbers, true, 2);
        }
        if(GameCode.contains(RenXuan.RX_RX3_ZL))
        {
            Zuhe(Numbers, true, 3);
        }
        if(GameCode.contains(RenXuan.RX_RX2_ZUXFS))
        {
            Zuhe(Numbers,true,2);
        }
        if(GameCode.contains(RX_RX3_ZXFS))
        {
            Zuhe(Numbers,3);
        }
        if(GameCode.contains(RX_RX4_ZXFS))
        {
            Zuhe(Numbers,4);
        }
    }

    @Override
    public JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers, String GameCode) {
        if(GameCode.contains("|"))
        {
            String datas[]=GameCode.split("\\|");
            String ws=datas[0];
            return  Formatnumber_special(SelectNumbers,ws);
        }
        else
        {
            return  Formatnumber(SelectNumbers);
        }
    }


    private JSONObject Formatnumber_special(SparseArray<ArrayList<LotteryButton>> SelectNumbers,String ws)
    {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonarry=new JSONArray();
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
                    sb.append(",");
                }

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
            String weishu[]={"万","千","百","十","个"};
            StringBuilder wsstr=new StringBuilder();
            for (int z = 0; z <ws.length() ; z++) {
                wsstr.append(weishu[ws.charAt(z)-'0']);
            }
            jsonObject.put(BundleTag.FormatNumber,wsstr.toString()+"|"+sb.toString());
            jsonObject.put(BundleTag.Weishu,ws);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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

    private  void Zuhe(ArrayList<ArrayList<LotteryButton>> Numbers,int arg)
    {
        SelectNumbers.clear();
        Random random = new Random();
        ArrayList<Integer> temp=new ArrayList<>();
            for (int j = 0; j <5 ; j++) {
                temp.add(j);
            }
            for (int j = 0; j <arg; j++) {
                int index= random.nextInt(temp.size());
                LotteryButton bn=Numbers.get(temp.get(index)).get(random.nextInt(10));
                bn.setChecked(true);
                ArrayList<LotteryButton> sNum=new ArrayList<>();
                sNum.add(bn);
                SelectNumbers.put(j,sNum);
                temp.remove(index);
            }
    }

    private void random_noremal(ArrayList<ArrayList<LotteryButton>> Numbers)
    {
        SelectNumbers.clear();
        Random random = new Random();
        ArrayList<Integer> temp=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            temp.add(i);
        }
        int radoms[]=new int[2];
        for (int i = 0; i <2 ; i++) {
            int r_temp=random.nextInt(temp.size());
            radoms[i]=temp.get(r_temp);
            temp.remove(temp.get(r_temp));
        }

        for (int i = 0; i <radoms.length ; i++) {
            ArrayList<LotteryButton> buttonnumbers=Numbers.get(radoms[i]);
            ArrayList<LotteryButton> sNum=new ArrayList<>();
            int index=random.nextInt(10);
            buttonnumbers.get(index).setChecked(true);
            sNum.add(buttonnumbers.get(index));
            SelectNumbers.put(radoms[i],sNum);
        }
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

    private int ZhiXuan2_fushi()
    {
        int sum = 0;
        for (int i = 0; i < SelectNumbers.size(); i++) {
            int temp = SelectNumbers.valueAt(i).size();
            for (int j = i + 1; j < SelectNumbers.size(); j++) {
                int compare =SelectNumbers.valueAt(j).size() ;
                sum = sum + compare * temp;
            }
        }
        return sum;
    }

    private int ZhiXuan3_fushi()
    {
        int sum = 0;
        for (int i = 0; i < SelectNumbers.size(); i++) {
            int temp = SelectNumbers.valueAt(i).size();
            for (int j = i + 1; j < SelectNumbers.size(); j++) {
                int temp2 = SelectNumbers.valueAt(j).size();
                for (int k = j+1; k < SelectNumbers.size(); k++) {
                    int temp3 = SelectNumbers.valueAt(k).size();
                    sum=sum+temp*temp2*temp3;
                }
            }
        }
        return sum;
    }

    private int ZhiXuan4_fushi()
    {
        int sum = 0;
        for (int i = 0; i < SelectNumbers.size(); i++) {
            int temp = SelectNumbers.valueAt(i).size();
            for (int j = i + 1; j < SelectNumbers.size(); j++) {
                int temp2 = SelectNumbers.valueAt(j).size();
                for (int k = j+1; k < SelectNumbers.size(); k++) {
                    int temp3 = SelectNumbers.valueAt(k).size();
                    for (int l = k+1; l < SelectNumbers.size(); l++) {
                        int temp4 = SelectNumbers.valueAt(l).size();
                        sum=sum+temp*temp2*temp3*temp4;
                    }
                }
            }
        }
        return sum;
    }


    private int RenXuan2_Zuxian(String ws)
    {
        if(SelectNumbers.size()<1)return 0;
        if(ws.length()<2)return 0;
        int count=0;
        for (int i = 0; i <SelectNumbers.get(0).size() ; i++) {
            if(SelectNumbers.get(0).get(i).getChecked())
            {
                count++;
            }
        }
        if(count<2)return 0;
        return count*(count-1)/2*ws.length()*(ws.length()-1)/2;
    }

    private int ZuXuan3(String ws)
    {
        if(SelectNumbers.size()<1)return 0;
        if(ws.length()<2)return 0;
        int count=0;
        for (int i = 0; i <SelectNumbers.get(0).size() ; i++) {
            if(SelectNumbers.get(0).get(i).getChecked())
            {
                count++;
            }
        }
        if(count<2)return 0;
        int n=count;
        int cha[]={2,8,20};
        n=n-1;
        return (n-1)*cha[ws.length()-3]*2+(n-1)*(n-2)/2*cha[ws.length()-3]+cha[ws.length()-3];
    }

    private int ZuXuan6(String ws)
    {
        if(SelectNumbers.size()<1)return 0;
        if(ws.length()<3)return 0;
        int count=0;
        for (int i = 0; i <SelectNumbers.get(0).size() ; i++) {
            if(SelectNumbers.get(0).get(i).getChecked())
            {
                count++;
            }
        }
        if(count<3)return 0;
        int temp[]={1,4,10};
        int sum=0;
        for (int i = 2; i < count-1; i++) {
            sum=sum+(i+1)*i/2*temp[ws.length()-3];
        }
       return sum+temp[ws.length()-3];
    }

    public static void isNeedSpecialView(String gamecode,LinearLayout weishu_layout)
    {
        boolean value=false;
        if(gamecode.contains(RenXuan.RX_RX2_ZUXFS))
        {
            value=true;
            setChecked(weishu_layout,2);
        }
        if(gamecode.contains(RenXuan.RX_RX3_ZS) || gamecode.contains(RenXuan.RX_RX3_ZL))
        {
            value=true;
            setChecked(weishu_layout,3);
        }
        if(value)
            weishu_layout.setVisibility(View.VISIBLE);
        else
            weishu_layout.setVisibility(View.GONE);
    }

    private static void setChecked(LinearLayout weishu_layout,int count)
    {
        for (int i = 0; i < weishu_layout.getChildCount(); i++) {
            CheckBox checkbox=(CheckBox)weishu_layout.getChildAt(i);
            if(i<count)
            {
                checkbox.setChecked(true);
            }
            else
            {
                checkbox.setChecked(false);
            }
        }
    }
}
