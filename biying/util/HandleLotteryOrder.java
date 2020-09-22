package com.lottery.biying.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.lottery.biying.bean.CalculateBet;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.bean.NumberPosition;
import com.lottery.biying.view.LotteryButton;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/11.
 */
public class HandleLotteryOrder {

    private static StringBuilder sb = new StringBuilder();//调整投注确认显示的样式
    private static ArrayList<Integer> listcount=new ArrayList<>();//每一行有几个选中 算奖金用的
    private static ArrayList<NumberPosition> numlist=new ArrayList<>();//记录哪个被选中
    private static int Times=-1;

    public static ArrayList<NumberPosition> getNumlist() {
        return numlist;
    }

    public static int getTimes() {
        return Times;
    }

    public static void setTimes(int times) {
        Times = times;
    }

    public static ArrayList<Integer> getListcount() {
        return listcount;
    }

    public static void setListcount(ArrayList<Integer> listcount) {
        HandleLotteryOrder.listcount = listcount;
    }

    public static StringBuilder getSb() {
        return sb;
    }
    public static void Deletesb() {
        if(sb.length()>0)
         sb.delete(0,sb.length());
    }
    public static String getItemValue() {
        if (sb.length() < 1)
            return "";
        else
            return sb.toString();
    }

    //格式化显示结果
    public static String HandleResult(String lotterytype, int playtype,String value)
    {
        LogTools.e("value",value);
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.SSC)) {
            return SSC_Method.ssc_result(playtype, value);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.PL3)) {
            return PL3_Method.pl3_result(playtype, value);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.PL5)) {
            return PL5_Method.pl5_result(playtype, value);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai)) {
            return QiXingCai_Method.qxc_result(playtype, value);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D)) {
            return FuCai3D_Method.fc3d_result(playtype, value);
        }
        return value;
    }

    //格式化显示结果 看显示的是原文本 还是html
    public static void HandleResult_style(String lotterytype, int playtype,String value,TextView textView)
    {
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.SSC)) {
            SSC_Method.ssc_result_style(playtype, value, textView);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.PL3)) {
           PL3_Method.pl3_result_style(playtype, value, textView);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.PL5)) {
            PL5_Method.pl5_result_style(playtype, value, textView);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai)) {
            QiXingCai_Method.qxc_result_style(playtype, value, textView);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D)) {
            FuCai3D_Method.fc3d_result_style(playtype, value, textView);
        }
    }

    //计算多少注
    public static int HandleLottery(String lotterytype, int playtype, ArrayList<ArrayList<LotteryButton>> arrayLists, double price) {

        if (lotterytype.equalsIgnoreCase(LotteryTypeName.SSC)) {
            Times=SSC_Method.ssc(arrayLists, price, playtype, sb, listcount,numlist);
            return Times;
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL3))
        {
            Times=PL3_Method.pl3(arrayLists, price, playtype, sb, listcount, numlist);
            return Times;
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL5))
        {
            Times=PL5_Method.pl5(arrayLists, price, playtype, sb, listcount, numlist);
            return Times;
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai))
        {
            Times=QiXingCai_Method.qxc(arrayLists, price, playtype, sb, listcount, numlist);
            return Times;
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D))
        {
            Times=FuCai3D_Method.fc3d(arrayLists, price, playtype, sb, listcount, numlist);
            return Times;
        }
        return -1;
    }

    //选号界面 看选了几个号码，什么时候可以跳转到确认注单
    public static int HandleLotteryOrder(String lotterytype, int playtype, ArrayList<ArrayList<LotteryButton>> arrayLists,boolean ischoose) {
        if(!ischoose)
            return 0;
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.SSC)) {
            return SSC_Method.ssc_order(arrayLists, playtype);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL3))
        {
            return PL3_Method.pl3_order(arrayLists, playtype);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL5))
        {
            return PL5_Method.pl5_order(arrayLists, playtype);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai))
        {
            return QiXingCai_Method.qxc_order(arrayLists, playtype);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D))
        {
            return FuCai3D_Method.fc3d_order(arrayLists, playtype);
        }
        return 0;
    }

    public static boolean HandleButton(String lotterytype, int playtype, IndexRecordBean bean, ArrayList<ArrayList<LotteryButton>> arrayLists) {
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.SSC)) {
            return SSC_Method.ssc_Btn(playtype, bean, arrayLists);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.PL3)) {
            return PL3_Method.pl3_Btn(playtype, bean, arrayLists);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.PL5)) {
            return PL5_Method.pl5_Btn(playtype, bean, arrayLists);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai)) {
            return QiXingCai_Method.qxc_Btn(playtype, bean, arrayLists);
        }
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D)) {
            return FuCai3D_Method.fc3d_Btn(playtype, bean, arrayLists);
        }
        return true;
    }

    public static SpannableStringBuilder GetStr(int bets, double price) {
        String modeltemp = "共%s注 %s元";
        modeltemp = String.format(modeltemp, bets, String.format("%.2f", price));
        //FF7701
        SpannableStringBuilder style = new SpannableStringBuilder(modeltemp);
        int index = modeltemp.lastIndexOf("注");
        style.setSpan(new ForegroundColorSpan(0xFFFFFFFF), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(0xffFF7701), index + 1, modeltemp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    public static SpannableStringBuilder GetStr(String modeltemp) {
        //FF7701
        SpannableStringBuilder style = new SpannableStringBuilder(modeltemp);
        int index = modeltemp.lastIndexOf("注");
        style.setSpan(new ForegroundColorSpan(0xFFFFFFFF), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(0xffFF7701), index + 1, modeltemp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    public static String CalculateReword(int bettype,String typename,int zhu,int...winmoney)
    {
        ArrayList<Integer> list=HandleLotteryOrder.getListcount();
        CalculateBet cbet=new CalculateBet();
        cbet.setWin1(winmoney[0]);
        cbet.setWin2(winmoney[1]);
        cbet.setWin3(winmoney[2]);
        if(list.size()>0)
            cbet.setG(list.get(list.size() - 1));
        if(list.size()>1)
            cbet.setS(list.get(list.size() - 2));
        if(list.size()>2)
            cbet.setB(list.get(list.size() - 3));
        if(list.size()>3)
            cbet.setQ(list.get(list.size() - 4));
        if(list.size()>4)
            cbet.setW(list.get(list.size() -5));

        cbet.setZhushu(zhu);

        StringBuilder sb=new StringBuilder();
        if(typename.equalsIgnoreCase(LotteryTypeName.SSC))
        {
           return SSC_Method.ssc_calculateReword(bettype,zhu,cbet,winmoney);
        }
        if(typename.equalsIgnoreCase(LotteryTypeName.PL3))
        {
            return PL3_Method.pl3_calculateReword(bettype, zhu, cbet, winmoney);
        }
        if(typename.equalsIgnoreCase(LotteryTypeName.PL5))
        {
            return PL5_Method.pl5_calculateReword(bettype, zhu, cbet, winmoney);
        }
        if(typename.equalsIgnoreCase(LotteryTypeName.QiXingCai))
        {
            return QiXingCai_Method.qxc_calculateReword(bettype, zhu, cbet, winmoney);
        }
        if(typename.equalsIgnoreCase(LotteryTypeName.FuCai3D))
        {
            return FuCai3D_Method.fc3d_calculateReword(bettype, zhu, cbet, winmoney);
        }
        return sb.toString();
    }
}
