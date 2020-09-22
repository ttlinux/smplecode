package com.lottery.biying.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.Activity.LotteryActivity;
import com.lottery.biying.Adapter.OrderConfirmAdapter;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.R;
import com.lottery.biying.bean.CalculateBet;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.bean.LotteryPlaytypeBean;
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
 * Created by Administrator on 2018/1/25.
 */
public class SSC_Method {

    public static JSONArray HandleOrderCommit(String gamecode,String args[])
    {
        JSONArray jsonarr=new JSONArray();
        if(args.length<1)
            return jsonarr;
        if(gamecode.equalsIgnoreCase(LotteryTypeName.dxds))
        {
            jsonarr.put("-");
            jsonarr.put("-");
            jsonarr.put("-");
            jsonarr.put(args[0]);
            jsonarr.put(args[1]);
        }
        else
        {
            for (int i = 0; i <args.length; i++) {
                if(args[i].equalsIgnoreCase("-"))
                {
                    jsonarr.put("-");
                }
                else
                {
                    LogTools.e("args[i]",args[i]);
                    StringBuilder sb=new StringBuilder();
                    for (int j = 0; j < args[i].length(); j++) {
                        if(args[i].trim().length()==0)continue;
                        sb.append(args[i].charAt(j));
                        sb.append(",");
                    }

                    LogTools.e("sbbb",sb.toString());
                    if(sb.length()>0)
                    sb.deleteCharAt(sb.length()-1);
                    jsonarr.put(sb.toString());
                }
            }
        }
        return jsonarr;
    }

    public static void ModifyTitleItem(RelativeLayout layout)
    {
        LinearLayout mainview=(LinearLayout)layout.findViewById(R.id.mainview);
        mainview.getChildAt(mainview.getChildCount()-1).setVisibility(View.GONE);
        mainview.getChildAt(mainview.getChildCount()-2).setVisibility(View.GONE);
        mainview.getChildAt(mainview.getChildCount()-3).setVisibility(View.GONE);
    }

    public static RelativeLayout Inititem(LotteryResultList lrl,int color,Context context,View view)
    {
        RelativeLayout rl=null;
        if(view==null)
            rl=(RelativeLayout) View.inflate(context, R.layout.item_lottery_history, null);
        else
            rl=(RelativeLayout)view;
        rl.setBackgroundColor(color);
        LinearLayout mainview=(LinearLayout)rl.findViewById(R.id.mainview);
        TextView textView1=(TextView)mainview.getChildAt(0);
        TextView textView2=(TextView)mainview.getChildAt(2);
        TextView textView3=(TextView)mainview.getChildAt(4);
        TextView textView4=(TextView)mainview.getChildAt(6);
        textView1.setText(lrl.getQsFormat());
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <lrl.getOpenResult().length; i++) {
            sb.append(lrl.getOpenResult()[i]);
            sb.append("  ");
        }
        if(sb.length()>2)
            sb.deleteCharAt(sb.length()-2);
        if(lrl.getIsOpen()<1)
            textView2.setText(context.getString(R.string.waiteforOpen));
        else
            textView2.setText(sb.toString());
        textView2.setTextColor(0xFFEE1F3B);
        textView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        return rl;
    }

    public static void ssc_result_style(int playtype, String value, TextView textView) {
        textView.setText(value);
    }

    public static String ssc_result(int playtype, String value) {
        switch (playtype) {
            case 0:
                return "- - - -  " + value;
            case 1:
                return "- - -  " + value;
            case 3:
                return "- - " + value;
            case 4:
                return "- -  " + value;
            case 2:
            case 5:
            case 6:
            case 7:
            case 8:
                return value;
        }
        return value;
    }

    public static int ssc(ArrayList<ArrayList<LotteryButton>> arrayLists, double price, int playtype,
                          StringBuilder sb, ArrayList<Integer> listcount, ArrayList<NumberPosition> numlist) {
        if (sb.length() > 0)
            sb.delete(0, sb.length());
        int bets = 1;
        listcount.clear();
        numlist.clear();
        switch (playtype) {
            case 0:
            case 1:
            case 3:
            case 6:
            case 7:
            case 8:
            case 4:
                for (int i = 0; i < arrayLists.size(); i++) {
                    ArrayList<LotteryButton> aars = arrayLists.get(i);
                    boolean isnull = false;
                    int count = 0;
                    NumberPosition np = new NumberPosition();
                    np.setPosition(i);
                    ArrayList<String> texts = new ArrayList<>();
                    for (int k = 0; k < aars.size(); k++) {
                        LotteryButton bn = aars.get(k);
                        if (bn.getChecked()) {
                            count++;
                            sb.append(bn.getText());
                            texts.add(bn.getText());
                            isnull = true;
                        }
                    }
                    np.setText(texts);
                    numlist.add(np);
                    bets = count * bets;
                    listcount.add(count);
                    sb.append("  ");
                    if (!isnull) {
                        sb.delete(0, sb.length());
                        listcount.clear();
                        numlist.clear();
                        return -1;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < arrayLists.size(); i++) {
                    ArrayList<LotteryButton> aars = arrayLists.get(i);
                    int count = 0;
                    NumberPosition np = new NumberPosition();
                    np.setPosition(i);
                    ArrayList<String> texts = new ArrayList<>();
                    for (int k = 0; k < aars.size(); k++) {
                        LotteryButton bn = aars.get(k);
                        if (bn.getChecked()) {
                            count++;
                            sb.append(bn.getText());
                            sb.append("  ");
                            texts.add(bn.getText());
                        }
                    }
                    np.setText(texts);
                    numlist.add(np);
                    bets = count * (count - 1) / 2 * bets;
                    listcount.add(count);
                    if (count < 2) {
                        sb.delete(0, sb.length());
                        listcount.clear();
                        numlist.clear();
                        return -1;
                    }
                }
                break;
            case 5:
                for (int i = 0; i < arrayLists.size(); i++) {
                    ArrayList<LotteryButton> aars = arrayLists.get(i);
                    int count = 0;
                    NumberPosition np = new NumberPosition();
                    np.setPosition(i);
                    ArrayList<String> texts = new ArrayList<>();
                    for (int k = 0; k < aars.size(); k++) {
                        LotteryButton bn = aars.get(k);
                        if (bn.getChecked()) {
                            count++;
                            sb.append(bn.getText());
                            sb.append("  ");
                            texts.add(bn.getText());
                        }
                    }
                    np.setText(texts);
                    numlist.add(np);
                    bets = 1;
                    listcount.add(count);
                    if (count < 3) {
                        sb.delete(0, sb.length());
                        listcount.clear();
                        numlist.clear();
                        return -1;
                    }
                }
                break;
        }
        LogTools.e("sbsbsbsb", sb.toString());
        return bets;
    }

    public static int ssc_order(ArrayList<ArrayList<LotteryButton>> arrayLists, int playtype) {
        int temp = 0;//0 一个号码都没选 1 没选齐号码 2 直接跳转
        for (int i = 0; i < arrayLists.size(); i++) {
            ArrayList<LotteryButton> btns = arrayLists.get(i);
            int count = 0;
            for (int k = 0; k < btns.size(); k++) {
                if (btns.get(k).getChecked()) {
                    count++;
                }
            }
            int arg = 1;//规则
            if (playtype == 5) arg = 3;
            if (playtype == 2) arg = 2;
            if (count < arg)
                return 1;
        }
        return 2;
    }

    //确认订单的随机
    public static OrderConfirmAdapter.Itemvalue ssc_comfirm(int typeindex, String lotteryname) {
        OrderConfirmAdapter.Itemvalue value = new OrderConfirmAdapter.Itemvalue();
        value.setItemvalueO1(lotteryname);
        value.setItemvalueO2("1注 2元");
        Random random = new Random();
        JSONArray arrayList=new JSONArray();
        if (typeindex == 5) {
            int ii1[] = {0, 1, 2};
            int ii2[] = {3, 4, 5};
            int ii3[] = {6, 7, 8, 9};
            int index1 = ii1[random.nextInt(ii1.length)];
            int index2 = ii2[random.nextInt(ii2.length )];
            int index3 = ii3[random.nextInt(ii3.length )];
            JSONArray array=new JSONArray();
            array.put(index1 + "");
            array.put(index2 + "");
            array.put(index3 + "");
            arrayList.put(array);
            StringBuilder sb = new StringBuilder();
            sb.append(index1);
            sb.append("  ");
            sb.append(index2);
            sb.append("  ");
            sb.append(index3);
            sb.append("  ");
            value.setItemvalueV1(SSC_Method.ssc_result(typeindex, sb.toString()));

        } else if (typeindex == 4) {
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
                sb.append(radomnumber[i]);
                JSONArray array=new JSONArray();
                array.put(radomnumber[i] + "");
                arrayList.put(array);
                sb.append("  ");
                for (int k = 0; k < bns.size(); k++) {
                    if (i == 0) {
                        Randomnums.add(k);
                    }
                }
            }
            value.setItemvalueV1(SSC_Method.ssc_result(typeindex, sb.toString()));

        } else if (typeindex == 2) {
            int ii1[] = {0, 1, 2, 3, 4};
            int ii2[] = {5, 6, 7, 8, 9};
            int index1 = ii1[random.nextInt(ii1.length)];
            int index2 = ii2[random.nextInt(ii2.length)];
            JSONArray array=new JSONArray();
            array.put(index1 + "");
            array.put(index2 + "");
            arrayList.put(array);
            StringBuilder sb = new StringBuilder();
            sb.append(index1);
            sb.append("  ");
            sb.append(index2);
            sb.append("  ");
            value.setItemvalueV1(SSC_Method.ssc_result(typeindex, sb.toString()));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < LotteryActivity.buttonnumbers.size(); i++) {
                ArrayList<LotteryButton> bns = LotteryActivity.buttonnumbers.get(i);
                if (bns.size() < 1) return null;
                String rnumber=bns.get(random.nextInt(bns.size())).getText().toString();
                sb.append(rnumber);
                JSONArray array=new JSONArray();
                array.put(rnumber);
                arrayList.put(array);
                sb.append("  ");
            }
            value.setItemvalueV1(SSC_Method.ssc_result(typeindex, sb.toString()));
        }
        value.setTimes(1);
        value.setLotterytype(LotteryTypeName.SSC);
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

    public static double[] ssc_CalculateMoney(double money, CalculateBet bean) {
        double[] args = new double[4];
        int arg = bean.getWin3() > 0 ? bean.getWin3() : bean.getWin1();
        args[0] = ((bean.getW() * bean.getQ() < bean.getS() * bean.getG()) ? (bean.getW() * bean.getQ()) : (bean.getS() * bean.getG())) * arg * bean.getB();
        if(bean.getB()==10)
        {
            args[0]=args[0]*2;
        }
        args[1] = (bean.getW() * bean.getQ() * bean.getB() + bean.getB() * bean.getS() * bean.getG()) * bean.getWin3() +
                (bean.getW() * bean.getQ() + bean.getS() * bean.getG()) * bean.getWin2() + bean.getWin1();
        args[2] = args[0] - bean.getZhushu() * money;
        args[3] = args[1] - bean.getZhushu() * money;
        return args;
    }

    public static void SSC_Random(int index, ArrayList<ArrayList<LotteryButton>> buttonnumbers, HashMap<String, LotteryButton> Selectednumbers) {
        int radomnumber[];
        Random random = new Random();
            if (index == 5) {
                int ii1[] = {0, 1, 2};
                int ii2[] = {3, 4, 5};
                int ii3[] = {6, 7, 8, 9};
            int index1 = ii1[random.nextInt(ii1.length )];
            int index2 = ii2[random.nextInt(ii2.length )];
            int index3 = ii3[random.nextInt(ii3.length )];
            int temp[] = {index1, index2, index3};
            for (int i = 0; i < buttonnumbers.get(0).size(); i++) {
                buttonnumbers.get(0).get(i).setChecked(false);
            }
            for (int i = 0; i < temp.length; i++) {
                LotteryButton bn = buttonnumbers.get(0).get(temp[i]);
                bn.setChecked(true);
                IndexRecordBean IRB = (IndexRecordBean) bn.getTag();
                Selectednumbers.put(IRB.getIndex1() + "" + IRB.getIndex2(), bn);
                HandleLotteryOrder.getSb().append(bn.getText());
                HandleLotteryOrder.getSb().append("  ");
            }

        } else if (index == 2) {
            int ii1[] = {0, 1, 2, 3, 4};
            int ii2[] = {5, 6, 7, 8, 9};
            int index1 = ii1[random.nextInt(ii1.length )];
            int index2 = ii2[random.nextInt(ii2.length )];
            int temp[] = {index1, index2};
            for (int i = 0; i < buttonnumbers.get(0).size(); i++) {
                buttonnumbers.get(0).get(i).setChecked(false);
            }
            for (int i = 0; i < temp.length; i++) {
                LotteryButton bn = buttonnumbers.get(0).get(temp[i]);
                bn.setChecked(true);
                IndexRecordBean IRB = (IndexRecordBean) bn.getTag();
                Selectednumbers.put(IRB.getIndex1() + "" + IRB.getIndex2(), bn);
                HandleLotteryOrder.getSb().append(bn.getText());
                HandleLotteryOrder.getSb().append("  ");
            }
        } else if (index == 4) {
            ArrayList<Integer> Randomnums = new ArrayList<>();
            radomnumber = new int[buttonnumbers.size()];
            for (int i = 0; i < buttonnumbers.size(); i++) {
                ArrayList<LotteryButton> bns = buttonnumbers.get(i);
                if (bns.size() < 1) return;
                if (Randomnums.size() > 0) {
                    Randomnums.remove(radomnumber[i - 1]);
                    radomnumber[i] = Randomnums.get(random.nextInt(Randomnums.size() ));
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
                        HandleLotteryOrder.getSb().append(bn.getText());
                        HandleLotteryOrder.getSb().append("  ");
                        bn.setChecked(true);
                    } else
                        bn.setChecked(false);
                }
            }
        } else {
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
                        HandleLotteryOrder.getSb().append(bn.getText());
                        HandleLotteryOrder.getSb().append("  ");
                        bn.setChecked(true);
                    } else
                        bn.setChecked(false);
                }
            }
        }
    }

    public static boolean ssc_Btn(int playtype, IndexRecordBean bean, ArrayList<ArrayList<LotteryButton>> arrayLists) {
        ArrayList<LotteryButton> btns = arrayLists.get(bean.getIndex1());

        switch (playtype) {
//            case 0:
//            case 1:
//            case 2:
//            case 3:
//            case 6:
//            case 7:
//                break;
            case 4:
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
            case 8:
                for (int k = 0; k < btns.size(); k++) {
                    if (btns.get(k).getChecked() && k == bean.getIndex2())
                        return false;
                    else
                        btns.get(k).setChecked(false);
                }
                break;
            case 5:
                ArrayList<LotteryButton> check_btns = new ArrayList<>();
                for (int k = 0; k < btns.size(); k++) {
                    if (btns.get(k).getChecked()) {
                        check_btns.add(btns.get(k));
                    }
                }
                if (check_btns.size() > 2) {
                    for (int i = 0; i < check_btns.size(); i++) {
                        if (bean.getIndex2() == ((IndexRecordBean) check_btns.get(i).getTag()).getIndex2())
                            return true;
                    }
                    return false;
                }
                break;
        }

        return true;
    }

    public static String ssc_calculateReword(int bettype,int zhu,CalculateBet cbet, int...winmoney)
    {
        StringBuilder sb=new StringBuilder();
            if(bettype==7)
            {
                double[] result=CalculateBetMoney.Calculate(LotteryTypeName.SSC, 2d, cbet);
                sb.append("奖金"+(int)result[0]+"至"+(int)result[1]+"元,");
                sb.append("盈利"+(int)result[2]+"至"+(int)result[3]+"元");
            }
            else
            {
                int money=winmoney[0]-zhu*2;
                sb.append("奖金"+winmoney[0]+"元,");
                if(money<0)
                {
                    sb.append("亏损"+Math.abs(money)+"元");
                }
                else
                {
                    sb.append("盈利"+money+"元");
                }

            }
        return sb.toString();
    }
}
