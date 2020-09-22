package com.lottery.biying.util;

import android.app.Activity;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/9/23.
 */
public class verifyEditext {

    public static verifyEditext ve;
    public static verifyEditext getInstance()
    {
        if(ve==null)
            ve=new verifyEditext();
        return ve;
    }

    public boolean Companypay_Bank(Activity activity,String...args)//公司入款
    {
        //请输入充值金额
        //请选择汇款时间
        //请输入账号
        //请输入姓名
        String string[]={"请输入充值金额","请选择汇款时间","请选择汇款方式","请输入汇款人姓名"};
        for (int i = 0; i < args.length; i++) {
            if(TextUtils.isEmpty(args[i]))
            {
                ToastUtil.showMessage(activity,string[i]);
                return false;
            }
        }
        return true;
    }
    public boolean Companypay_Type(Activity activity,String...args)//微信 支付宝之类的
    {
        String string[]={"请输入充值金额","请选择汇款时间","请输入账号","请输入姓名"};
        for (int i = 0; i < args.length; i++) {
           if(TextUtils.isEmpty(args[i]))
           {
               ToastUtil.showMessage(activity,string[i]);
               return false;
           }
        }
        //请输入充值金额
        //请选择汇款时间
        //请输入账号
        //请输入姓名


        return true;
    }

    public boolean QRCode_pay(Activity activity,String...args)//二维码的
    {
        //请输入充值金额
        //请输入姓名
        String string[]={"请输入充值金额","请输入姓名"};
        for (int i = 0; i < args.length; i++) {
            if(TextUtils.isEmpty(args[i]))
            {
                ToastUtil.showMessage(activity,string[i]);
                return false;
            }
        }
        return true;
    }

    public boolean BankCard(Activity activity,String...args)//银行卡绑定
    {
        //请选择汇款时间
        //请输入银行卡号
        //请输入开户行地址
        //请输入正确的4位密码

        String string[]={"请输入收款银行","请输入银行卡号","请输入开户行地址","请输入正确的4位密码"};
        for (int i = 0; i < args.length; i++) {
            if(TextUtils.isEmpty(args[i]))
            {
                ToastUtil.showMessage(activity,string[i]);
                return false;
            }
            if(i==3 && args[i].length()<4)
            {
                ToastUtil.showMessage(activity,string[i]);
                return false;
            }
        }
        return true;
    }
}
