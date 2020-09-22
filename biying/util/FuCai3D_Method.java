package com.lottery.biying.util;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.Activity.LotteryActivity;
import com.lottery.biying.Adapter.OrderConfirmAdapter;
import com.lottery.biying.R;
import com.lottery.biying.bean.CalculateBet;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.NumberPosition;
import com.lottery.biying.view.LotteryButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 2018/1/27.
 */
public class FuCai3D_Method {

    public static void ModifyTitleItem(RelativeLayout layout)
    {
        LinearLayout mainview=(LinearLayout)layout.findViewById(R.id.mainview);
        TextView textView3=(TextView)mainview.getChildAt(4);
        TextView textView4=(TextView)mainview.getChildAt(6);
        textView3.setText("形态");
        textView4.setText("试机号");
    }

    public static RelativeLayout Inititem(LotteryResultList lrl,int color,Context context,View view)
    {
        RelativeLayout rl=null;
        if(view==null)
            rl=(RelativeLayout) View.inflate(context, R.layout.item_lottery_history, null);
        else
            rl=(RelativeLayout)view;
        rl.setBackgroundColor(color);
//        LinearLayout mainview=(LinearLayout)rl.findViewById(R.id.mainview);
//        TextView textView1=(TextView)mainview.getChildAt(0);
//        TextView textView2=(TextView)mainview.getChildAt(2);
//        TextView textView3=(TextView)mainview.getChildAt(4);
//        TextView textView4=(TextView)mainview.getChildAt(6);
//        textView1.setText(lrl.getQsFormat());
//        StringBuilder sb=new StringBuilder();
//        for (int i = 0; i <lrl.getResult().size(); i++) {
//            sb.append(lrl.getResult().get(i));
//            sb.append("  ");
//        }
//        if(sb.length()>2)
//            sb.deleteCharAt(sb.length()-2);
//        if(sb.toString().length()==0)
//            textView2.setText(context.getString(R.string.waiteforOpen));
//        else
//            textView2.setText(sb.toString());
//        textView2.setTextColor(0xFFEE1F3B);
//        textView3.setText(lrl.getXt());
//        textView4.setText("");
//        for (int i = 0; i <lrl.getSJHS().size() ; i++) {
//            LotteryResultList.SJH sjh=lrl.getSJHS().get(i);
//            textView4.append(Html.fromHtml(Html(sjh.getIsRed()>0?"#EE1F3B":"#615E5F",sjh.getNum()+" ")));
//        }
//        LogTools.e("ssss",new Gson().toJson(lrl.getSJHS()));
        return rl;
    }

    //加---的 时时彩才要
    public static String fc3d_result(int playtype,String value)
    {
        return value;
    }

    //排列组合的用html做颜色
    public static void fc3d_result_style(int playtype,String value,TextView textView)
    {
        //0 1 (01 | 01)
        //2 3 4 5 6(01 01 )
        if(playtype<2)
            textView.setText(Html.fromHtml(value));
        else
            textView.setText(value);
    }

    public static int fc3d(ArrayList<ArrayList<LotteryButton>> arrayLists, double price, int playtype,
                          StringBuilder sb,ArrayList<Integer> listcount,ArrayList<NumberPosition> numlist) {

        //0 1 (09 | 09)
        //2 3 4 5 6(09 09 09)
        if(sb.length()>0)
            sb.delete(0, sb.length());
        int bets = playtype>3?0:1;//4 的时候是加法 其他是乘法
        listcount.clear();
        numlist.clear();
        for (int i = 0; i < arrayLists.size(); i++) {
            ArrayList<LotteryButton> aars = arrayLists.get(i);
            boolean isnull = false;
            int count = 0;
            NumberPosition np=new NumberPosition();
            np.setPosition(i);
            ArrayList<String> texts=new ArrayList<>();
            if(sb.length()>0 && playtype<2)
                sb.append(Html("#999999", "|  "));
            for (int k = 0; k < aars.size(); k++) {
                LotteryButton bn = aars.get(k);
                if (bn.getChecked()) {
                    count++;
                    String text=bn.getText();
                    if(bn.getText().length()==1)
                    {
                        text="0"+bn.getText();
                    }
                    switch (playtype) {
                        case 0:
                        case 1:
                            sb.append(Html("#EE1F3B",text));
                            sb.append(" ");
                            break;
                        case 2:
                        case 3:
                            sb.append(text);
                            sb.append("  ");
                            break;
                        case 4:
                            bets=bets+CalculateHezhi(Integer.valueOf(bn.getText()));
                            sb.append(text);
                            sb.append("  ");
                            break;
                        case 5:
                            bets=bets+CalculateZhu3(Integer.valueOf(bn.getText()));
                            sb.append(text);
                            sb.append("  ");
                            break;
                        case 6:
                            bets=bets+CalculateZhu6(Integer.valueOf(bn.getText()));
                            sb.append(text);
                            sb.append("  ");
                            break;

                    }
                    isnull = true;
                    texts.add(bn.getText());
                }
            }
            np.setText(texts);
            numlist.add(np);
            if(playtype==2)
            {
                bets = count*(count-1);
            }
            else if(playtype==3)
            {
                bets = count*(count-1)*(count-2)/6 * bets;
            }
            else if(playtype==4 || playtype==5 || playtype==6)//算和值
            {
                //上面已经写了 这里不能删除 删除就被下面的else覆盖了
            }
            else
            {
                bets = count * bets;
            }

            listcount.add(count);
            if (!isnull) {
                sb.delete(0, sb.length());
                listcount.clear();
                numlist.clear();
                return -1;
            }
        }
        return bets;
    }

    public static int fc3d_order(ArrayList<ArrayList<LotteryButton>> arrayLists, int playtype) {
        int temp=0;//0 一个号码都没选 1 没选齐号码 2 直接跳转
        for (int i = 0; i <arrayLists.size() ; i++) {
            ArrayList<LotteryButton> btns=arrayLists.get(i);
            int count=0;
            for (int k = 0; k < btns.size(); k++) {
                if(btns.get(k).getChecked())
                {
                    count++;
                }
            }
            int arg=1;//规则 每行最少选多少个
            if(playtype==3)arg=3;
            if(playtype==2)arg=2;
            if(count<arg)
                return 1;
        }
        return 2;
    }

    //确认订单的随机
    public static OrderConfirmAdapter.Itemvalue fc3d_comfirm(int typeindex,String lotteryname)
    {
        int bets=0;//只对和值起作用
        OrderConfirmAdapter.Itemvalue value=new OrderConfirmAdapter.Itemvalue();
        value.setItemvalueO1(lotteryname);
        Random random = new Random();
        JSONArray arrayList=new JSONArray();
        if(typeindex==3)
        {
            int ii1[]={0,1,2};
            int ii2[]={3,4,5};
            int ii3[]={6,7,8,9};
            int index1=ii1[random.nextInt(ii1.length)];
            int index2=ii2[random.nextInt(ii2.length)];
            int index3=ii3[random.nextInt(ii3.length)];
            JSONArray array=new JSONArray();
            array.put(index1);
            array.put(index2);
            array.put(index3);
            arrayList.put(array);
            StringBuilder sb=new StringBuilder();
            sb.append("0"+index1);
            sb.append("  ");
            sb.append("0"+index2);
            sb.append("  ");
            sb.append("0"+index3);
            value.setItemvalueV1(fc3d_result(typeindex, sb.toString()));
        }
        else if(typeindex==2)
        {
            int ii1[]={0,1,2,3,4};
            int ii2[]={5,6,7,8,9};
            int index1=ii1[random.nextInt(ii1.length)];
            int index2=ii2[random.nextInt(ii2.length)];
            JSONArray array=new JSONArray();
            array.put(index1);
            array.put(index2);
            arrayList.put(array);
            StringBuilder sb=new StringBuilder();
            sb.append("0"+index1);
            sb.append("  ");
            sb.append("0"+index2);
            value.setItemvalueV1(fc3d_result(typeindex, sb.toString()));
        }
        else if(typeindex==1)
        {
            StringBuilder sb = new StringBuilder();
            ArrayList<Integer> Randomnums = new ArrayList<>();
            int[] radomnumber = new int[LotteryActivity.buttonnumbers.size()];
            for (int i = 0; i < LotteryActivity.buttonnumbers.size(); i++) {
                ArrayList<LotteryButton> bns = LotteryActivity.buttonnumbers.get(i);
                if (bns.size() < 1) return null;
                if (Randomnums.size() > 0) {
                    Randomnums.remove(radomnumber[i - 1]);
                    radomnumber[i] = Randomnums.get(random.nextInt(Randomnums.size()));
                } else {
                    radomnumber[i] = random.nextInt(bns.size());
                }
                if(sb.length()>0)
                    sb.append(Html("#999999", "  |  "));
                sb.append(Html("#EE1F3B", "0" + radomnumber[i]));
                JSONArray array=new JSONArray();
                array.put(radomnumber[i]);
                arrayList.put(array);
                for (int k = 0; k < bns.size(); k++) {
                    if (i == 0) {
                        Randomnums.add(k);
                    }
                }
            }
            value.setItemvalueV1(fc3d_result(typeindex, sb.toString()));
        }
        else//0 4 5 6
        {
            StringBuilder sb=new StringBuilder();
            for (int i = 0; i < LotteryActivity.buttonnumbers.size(); i++) {
                ArrayList<LotteryButton> bns = LotteryActivity.buttonnumbers.get(i);
                if (bns.size() < 1) return null;
                int num=random.nextInt(bns.size());
                if(typeindex>3)
                {
                    switch (typeindex)
                    {
                        case 4:
                            bets=bets+CalculateHezhi(num);
                            break;
                        case 5:
                            bets=bets+CalculateZhu3(num);
                            break;
                        case 6:
                            bets=bets+CalculateZhu6(num);
                            break;
                    }
                    sb.append(num>9?(num+""):("0"+num));
                    sb.append("  ");
                }
                else
                {
                    if(sb.length()>0)
                        sb.append(Html("#999999", "  |  "));
                    sb.append(Html("#EE1F3B", "0" + num));
                }
                JSONArray array=new JSONArray();
                array.put(num);
                arrayList.put(array);
            }
            value.setItemvalueV1(fc3d_result(typeindex,sb.toString()));
        }
        if(typeindex==2)
        {
            value.setItemvalueO2("2注 4元");
            value.setTimes(2);
        }
        else if(typeindex>3)
        {
            String template="%s注 %s元";
            value.setItemvalueO2(String.format(template,bets,bets*2));
            value.setTimes(bets);
        }
        else
        {
            value.setItemvalueO2("1注 2元");
            value.setTimes(1);
        }

        value.setLotterytype(LotteryTypeName.FuCai3D);
        value.setPlaytype(typeindex);
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        for (int i = 0; i <arrayList.length() ; i++) {
            try {
                jsonObject.put("Position",i);
                jsonObject.put("text",arrayList.optJSONArray(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        return value;
    }

    public static double[] fc3d_CalculateMoney(double money, CalculateBet bean) {
        double[] args = new double[4];
        int arg = bean.getWin3() > 0 ? bean.getWin3() : bean.getWin1();
        args[0] = arg;

        args[1] = arg- bean.getZhushu() * money;
        args[2] = args[0] - bean.getZhushu() * money;
        args[3] = args[1] - bean.getZhushu() * money;
        return args;
    }

    public static void fc3d_Random(int index,ArrayList<ArrayList<LotteryButton>> buttonnumbers,HashMap<String, LotteryButton> Selectednumbers)
    {
        int radomnumber[];
        Random random = new Random();
        if(index==3)
        {
            int ii1[]={0,1,2};
            int ii2[]={3,4,5};
            int ii3[]={6,7,8,9};
            int index1=ii1[random.nextInt(ii1.length)];
            int index2=ii2[random.nextInt(ii2.length)];
            int index3=ii3[random.nextInt(ii3.length)];
            int temp[]={index1,index2,index3};
            for (int i = 0; i < buttonnumbers.get(0).size(); i++) {
                buttonnumbers.get(0).get(i).setChecked(false);
            }
            for (int i = 0; i < temp.length ; i++) {
                LotteryButton bn= buttonnumbers.get(0).get(temp[i]);
                bn.setChecked(true);
                IndexRecordBean IRB = (IndexRecordBean) bn.getTag();
                Selectednumbers.put(IRB.getIndex1() + "" + IRB.getIndex2(), bn);
                HandleLotteryOrder.getSb().append("0"+bn.getText().toString());
                HandleLotteryOrder.getSb().append("  ");
            }

        }
        else if(index==2)
        {
            int ii1[]={0,1,2,3,4};
            int ii2[]={5,6,7,8,9};
            int index1=ii1[random.nextInt(ii1.length)];
            int index2=ii2[random.nextInt(ii2.length)];
            int temp[]={index1,index2};
            for (int i = 0; i < buttonnumbers.get(0).size(); i++) {
                buttonnumbers.get(0).get(i).setChecked(false);
            }
            for (int i = 0; i < temp.length ; i++) {
                LotteryButton bn= buttonnumbers.get(0).get(temp[i]);
                bn.setChecked(true);
                IndexRecordBean IRB = (IndexRecordBean) bn.getTag();
                Selectednumbers.put(IRB.getIndex1() + "" + IRB.getIndex2(), bn);
                HandleLotteryOrder.getSb().append("0"+bn.getText());
                HandleLotteryOrder.getSb().append("  ");
            }
        }
        else if(index==1)
        {
            ArrayList<Integer> Randomnums = new ArrayList<>();
            radomnumber = new int[buttonnumbers.size()];
            for (int i = 0; i < buttonnumbers.size(); i++) {
                ArrayList<LotteryButton> bns = buttonnumbers.get(i);
                if (bns.size() < 1) return;
                if (Randomnums.size() > 0) {
                    Randomnums.remove(radomnumber[i - 1]);
                    radomnumber[i] = Randomnums.get(random.nextInt(Randomnums.size()));
                } else {
                    radomnumber[i] = random.nextInt(bns.size());
                }
                for (int k = 0; k < buttonnumbers.get(i).size(); k++) {
                    if (i == 0) {
                        Randomnums.add(k);
                    }
                    LotteryButton bn = bns.get(k);
                    IndexRecordBean IRB = (IndexRecordBean) bn.getTag();
                    if (radomnumber[i] == k) {
                        Selectednumbers.put(IRB.getIndex1() + "" + IRB.getIndex2(), bn);
                        int num=Integer.valueOf(bn.getText().toString());
                        if(HandleLotteryOrder.getSb().length()>0)
                            HandleLotteryOrder.getSb().append(Html("#999999", "  |  "));
                        HandleLotteryOrder.getSb().append(Html("#EE1F3B", "0" + num));
                        bn.setChecked(true);
                    } else
                        bn.setChecked(false);
                }
            }
        }
        else //0 4
        {
            radomnumber = new int[buttonnumbers.size()];
            for (int i = 0; i < buttonnumbers.size(); i++) {
                ArrayList<LotteryButton> bns = buttonnumbers.get(i);
                if (bns.size() < 1) return;
                radomnumber[i] = random.nextInt(bns.size());
                for (int k = 0; k < buttonnumbers.get(i).size(); k++) {
                    LotteryButton bn = bns.get(k);
                    IndexRecordBean IRB = (IndexRecordBean) bn.getTag();
                    if (radomnumber[i] == k) {
                        Selectednumbers.put(IRB.getIndex1() + "" + IRB.getIndex2(), bn);
                        int num=Integer.valueOf(bn.getText().toString());
                        if(index>3)
                        {
                            HandleLotteryOrder.getSb().append(num > 9 ? (num + "") : ("0" + num));
                            HandleLotteryOrder.getSb().append("  ");
                        }
                        else
                        {
                            if(HandleLotteryOrder.getSb().length()>0)
                                HandleLotteryOrder.getSb().append(Html("#999999", "  |  "));
                            HandleLotteryOrder.getSb().append(Html("#EE1F3B", "0" + num));
                        }
                        bn.setChecked(true);
                    }
                    else
                        bn.setChecked(false);
                }
            }
        }
    }

    public static String fc3d_calculateReword(int bettype,int zhu,CalculateBet cbet, int...winmoney)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("奖金"+winmoney[0]+"元,");
        int money=winmoney[0]-zhu*2;
//        sb.append("奖金"+winmoney[0]+"元,");
        if(money<0)
        {
            sb.append("亏损"+Math.abs(money)+"元");
        }
        else
        {
            sb.append("盈利"+money+"元");
        }
        return sb.toString();
    }

    public static boolean fc3d_Btn(int playtype, IndexRecordBean bean, ArrayList<ArrayList<LotteryButton>> arrayLists) {
        ArrayList<LotteryButton> btns = arrayLists.get(bean.getIndex1());

        switch (playtype) {
//            case 0:
//            case 1:
//            case 2:
//            case 3:
//            case 6:
//            case 7:
//                break;
            case 1:
                int index1 = bean.getIndex1() > 0 ? 0 : 1;
                LotteryButton btn = arrayLists.get(index1).get(bean.getIndex2());
                if (btn.getChecked())
                    btn.setCheckedOpposite();
                for (int k = 0; k < btns.size(); k++) {
                    if (btns.get(k).getChecked() && k == bean.getIndex2())
                        return false;
                    else {
                        btns.get(k).setChecked(false);
                    }
                }
                break;
        }

        return true;
    }

    private static String Html(String color,String text)
    {
        String template="<font color='%s' size='20'>%s</font>";
        return String.format(template,color,text);
    }

    private static int  CalculateHezhi(int w)//计算3个数和值的倍数 数字任意搭配
    {
        int k[]={0,1,2,3,4,5,6,7,8,9};
        int x,y,z;
        int count=0;
        for (int i = 0; i < k.length; i++) {
            x=k[i];
            for (int j = 0; j < k.length; j++) {
                y=k[j];
                for (int q = 0; q < k.length; q++) {
                    z=k[q];
                    if(z+y+x==w)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static int CalculateZhu3(int w)//福彩3d 组3和值
    {
        int count=0;
        int k[]={0,1,2,3,4,5,6,7,8,9};
        int x,y,z;
        for (int i = 0; i < k.length; i++) {
            x=k[i];
            for (int q = 0; q < k.length; q++) {
                z=k[q];
                if(z+x+x==w && x!=z)
                {
                    count++;
                }
            }
        }
        return count;
    }

    private static int CalculateZhu6(int w)//福彩3d 组6和值
    {
        int count=0;
        int k[]={0,1,2,3,4,5,6,7,8,9};
        int x,y,z;
        ArrayList<Integer> records=new ArrayList<>();
        for (int i = 0; i < k.length; i++) {
            x=k[i];
            for (int j = 0; j < k.length; j++) {
                y=k[j];
                if(x==y)
                {
                    continue;
                }
                for (int q = 0; q < k.length; q++) {
                    z=k[q];
                    if( y==z || z==x)
                    {
                        continue;
                    }
                    boolean handle=true;
                    for (int l = 0; l < records.size(); l++) {
                        if((x+1)*(y+1)*(z+1)==records.get(l))
                        {
                            handle=false;
                            break;
                        }

                    }
                    if(z+y+x==w && handle)
                    {
                        records.add((x+1)*(y+1)*(z+1));
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
