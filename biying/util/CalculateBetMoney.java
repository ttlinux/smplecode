package com.lottery.biying.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.lottery.biying.bean.CalculateBet;
import com.lottery.biying.bean.CaseBean;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/15.
 */
public class CalculateBetMoney {

    public static double[] Calculate(String playtype, double money, CalculateBet bean) {
        // 判断选择的是那几位
        // int res = (a*b>d*e)?(a*b):(d*e)
        // res = res * 20;
        // 最小值 = res * c;
        // 最大值 = (a*b*c)*20 + (a*b)*200 + (c*d*e)*20 + (d*e)*200 + 20000

        if (playtype.equalsIgnoreCase(LotteryTypeName.SSC)) {
            return SSC_Method.ssc_CalculateMoney(money, bean);
        }
        if(playtype.equalsIgnoreCase(LotteryTypeName.PL3))
            return PL3_Method.pl3_CalculateMoney(money,bean);
        if(playtype.equalsIgnoreCase(LotteryTypeName.PL5))
            return PL5_Method.pl5_CalculateMoney(money, bean);
        if(playtype.equalsIgnoreCase(LotteryTypeName.QiXingCai))
            return QiXingCai_Method.qxc_CalculateMoney(money, bean);
        if(playtype.equalsIgnoreCase(LotteryTypeName.FuCai3D))
            return FuCai3D_Method.fc3d_CalculateMoney(money, bean);
        return null;
    }


    //智能选号 时时彩独有
    public static ArrayList<CaseBean> Calculate(int min, int max, int starMultiply, int count, double price, int minPercent, int winmin,int MaxPay) {
        int betMultiply = starMultiply;
        int index = 0;
        ArrayList<CaseBean> beans = new ArrayList<>();
        if(min<1 || max<1)
            return beans;
        int money = 0;
        while (index < count ) {
            CaseBean bean = new CaseBean();
            bean.setMultiply(betMultiply);
            bean.setMoney((int) (price * bean.getMultiply()) + money);

            bean.setWinmax(max* bean.getMultiply() - bean.getMoney());
            bean.setWinmaxPercent((int) (bean.getWinmax() * 0.01d / bean.getMoney() * 10000));
            bean.setWinmin(min* bean.getMultiply() - bean.getMoney());
            bean.setWinminPercent((int) (bean.getWinmin() * 0.01d / bean.getMoney() * 10000));

            if (minPercent > 0) {
                if(!HandlerPercent(bean, minPercent, money, price, min, max,MaxPay))return null;
            }
            if (winmin > 0) {
                if(!HandlerWinmin(bean, winmin, money, price, min, max,MaxPay))return null;
            }

            betMultiply = bean.getMultiply();
            money = bean.getMoney();
            if(MaxPay<=money)
                return null;
            index++;
            beans.add(bean);
        }
        return beans;
    }

    //智能选号 时时彩独有
    public static ArrayList<CaseBean> Calculate(int min, int max, int starMultiply, int count, double price, int minPercent, int winmin,int MaxPay,JSONArray jsonArray) {
        int betMultiply = starMultiply;
        int index = 0;
        ArrayList<CaseBean> beans = new ArrayList<>();
        if(min<1 || max<1)
            return beans;
        int money = 0;
        while (index < count ) {
            CaseBean bean = new CaseBean();
            bean.setMultiply(betMultiply);
            bean.setMoney((int) (price * bean.getMultiply()) + money);
            bean.setQishu(jsonArray.optJSONObject(index).optString("qsnumber",""));
            bean.setWinmax(max* bean.getMultiply() - bean.getMoney());
            bean.setWinmaxPercent((int) (bean.getWinmax() * 0.01d / bean.getMoney() * 10000));
            bean.setWinmin(min* bean.getMultiply() - bean.getMoney());
            bean.setWinminPercent((int) (bean.getWinmin() * 0.01d / bean.getMoney() * 10000));

            if (minPercent > 0) {
                if(!HandlerPercent(bean, minPercent, money, price, min, max,MaxPay))return null;
            }
            if (winmin > 0) {
                if(!HandlerWinmin(bean, winmin, money, price, min, max,MaxPay))return null;
            }

            betMultiply = bean.getMultiply();
            money = bean.getMoney();
            if(MaxPay<=money)
                return null;
            index++;
            beans.add(bean);
        }
        return beans;
    }

    private static boolean HandlerPercent(CaseBean bean, int minPercent, int money, double price, int min, int max,int MaxPay) {
        while (bean.getWinminPercent() < minPercent) {
            bean.setMultiply(bean.getMultiply() + 1);
            bean.setMoney((int) (price * bean.getMultiply()) + money);
            bean.setWinmin(min * bean.getMultiply() - bean.getMoney());
            bean.setWinminPercent((int) (bean.getWinmin() * 0.01d / bean.getMoney() * 10000));
            if( bean.getMoney()>MaxPay)
            {
                return false;
            }
        }

        bean.setWinmax(max * bean.getMultiply() - bean.getMoney());
        bean.setWinmaxPercent((int) (bean.getWinmax() * 0.01d / bean.getMoney() * 10000));
        return true;
    }

    private static boolean HandlerWinmin(CaseBean bean, int winmin, int money, double price, int min, int max,int MaxPay) {
        while (bean.getWinmin() < winmin) {
            bean.setMultiply(bean.getMultiply() + 1);
            bean.setMoney((int) (price * bean.getMultiply()) + money);
            bean.setWinmin(min * bean.getMultiply() - bean.getMoney());
            if( bean.getMoney()>MaxPay)
            {
                return false;
            }
        }
        bean.setWinminPercent((int) (bean.getWinmin() * 0.01d / bean.getMoney() * 10000));
        bean.setWinmax(max * bean.getMultiply() - bean.getMoney());
        bean.setWinmaxPercent((int) (bean.getWinmax() * 0.01d / bean.getMoney() * 10000));
        return true;
    }

    public static  int Onchange(ArrayList<CaseBean> casebeans,int index,int min,int max)
    {
        int lowwinPercent=casebeans.get(0).getWinminPercent();
        int money=index>0?casebeans.get(index>0?index-1:0).getMoney():0;
        for (int i = index; i < casebeans.size(); i++) {
            CaseBean bean=casebeans.get(i);
            bean.setMoney(bean.getMultiply() * 2 + money);
            bean.setWinmin(min * bean.getMultiply() - bean.getMoney());
            bean.setWinmax(max * bean.getMultiply() - bean.getMoney());
            bean.setWinminPercent((int) (bean.getWinmin() * 0.01d / bean.getMoney() * 10000));
            bean.setWinmaxPercent((int) (bean.getWinmax() * 0.01d / bean.getMoney() * 10000));
            lowwinPercent=lowwinPercent>bean.getWinminPercent()?bean.getWinminPercent():lowwinPercent;
            casebeans.set(i,bean);
            money=bean.getMoney();
        }
        return lowwinPercent;
    }


}
