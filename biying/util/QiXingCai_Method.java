package com.lottery.biying.util;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class QiXingCai_Method {

    public static void ModifyTitleItem(RelativeLayout layout)
    {
        LinearLayout mainview=(LinearLayout)layout.findViewById(R.id.mainview);
        int size=mainview.getChildCount();
        for (int i = 1; i <5 ; i++) {
            mainview.getChildAt(size-i).setVisibility(View.GONE);
        }

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
//        int size=mainview.getChildCount();
//        for (int i = 1; i <5 ; i++) {
//            mainview.getChildAt(size-i).setVisibility(View.GONE);
//        }
//        TextView textView1=(TextView)mainview.getChildAt(0);
//        TextView textView2=(TextView)mainview.getChildAt(2);
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
        return rl;
    }

    //加---的 时时彩才要
    public static String qxc_result(int playtype,String value)
    {
        return value;
    }

    //排列组合的用html做颜色
    public static void qxc_result_style(int playtype,String value,TextView textView)
    {
        textView.setText(Html.fromHtml(value));
    }

    public static int qxc(ArrayList<ArrayList<LotteryButton>> arrayLists, double price, int playtype,
                          StringBuilder sb,ArrayList<Integer> listcount,ArrayList<NumberPosition> numlist) {
        if(sb.length()>0)
            sb.delete(0, sb.length());
        int bets = 1;//
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
                    sb.append(Html("#EE1F3B",text));
                    sb.append("  ");
                    isnull = true;
                    texts.add(bn.getText());
                }
            }
            np.setText(texts);
            numlist.add(np);
            bets = count * bets;
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


    public static int qxc_order(ArrayList<ArrayList<LotteryButton>> arrayLists, int playtype) {
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
            if(count<arg)
                return 1;
        }
        return 2;
    }

    //确认订单的随机
    public static OrderConfirmAdapter.Itemvalue qxc_comfirm(int typeindex,String lotteryname)
    {
        OrderConfirmAdapter.Itemvalue value=new OrderConfirmAdapter.Itemvalue();
        value.setItemvalueO1(lotteryname);
        Random random = new Random();
        JSONArray arrayList=new JSONArray();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < LotteryActivity.buttonnumbers.size(); i++) {
            ArrayList<LotteryButton> bns = LotteryActivity.buttonnumbers.get(i);
            if (bns.size() < 1) return null;
            int num=random.nextInt(bns.size());
            if(sb.length()>0)
                sb.append(Html("#999999", "  |  "));
            sb.append(Html("#EE1F3B", "0" + num));
            JSONArray array=new JSONArray();
            array.put(num);
            arrayList.put(array);
        }
        value.setItemvalueO2("1注 2元");
        value.setTimes(1);
        value.setLotterytype(LotteryTypeName.QiXingCai);
        value.setPlaytype(typeindex);
        value.setItemvalueV1(QiXingCai_Method.qxc_result(typeindex, sb.toString()));
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

    public static double[] qxc_CalculateMoney(double money, CalculateBet bean) {
        double[] args = new double[4];
        int arg = bean.getWin3() > 0 ? bean.getWin3() : bean.getWin1();
        args[0] = arg;

        args[1] = arg- bean.getZhushu() * money;
        args[2] = args[0] - bean.getZhushu() * money;
        args[3] = args[1] - bean.getZhushu() * money;
        return args;
    }
    public static void qxc_Random(int index,ArrayList<ArrayList<LotteryButton>> buttonnumbers,HashMap<String, LotteryButton> Selectednumbers)
    {
        int radomnumber[];
        Random random = new Random();
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
                    if(HandleLotteryOrder.getSb().length()>0)
                        HandleLotteryOrder.getSb().append(Html("#999999", "  |  "));
                    HandleLotteryOrder.getSb().append(Html("#EE1F3B", "0" + num));
                    bn.setChecked(true);
                }
                else
                    bn.setChecked(false);
            }
        }
    }

    public static String qxc_calculateReword(int bettype,int zhu,CalculateBet cbet, int...winmoney)
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

    public static boolean qxc_Btn(int playtype, IndexRecordBean bean, ArrayList<ArrayList<LotteryButton>> arrayLists) {

        return true;
    }

    private static String Html(String color,String text)
    {
        String template="<font color='%s' size='20'>%s</font>";
        return String.format(template,color,text);
    }
}
