package com.lottery.biying.util;

import com.lottery.biying.Activity.LotteryActivity;
import com.lottery.biying.Adapter.OrderConfirmAdapter;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.view.LotteryButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 2017/12/16.
 */
public class HandleRandomNumber {

    public static OrderConfirmAdapter.Itemvalue RanDomNumber_result(String lotterytype,int typeindex,String lotteryname)
    {
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.SSC))
        {
            return SSC_Method.ssc_comfirm(typeindex, lotteryname);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL3))
        {
            return PL3_Method.pl3_comfirm(typeindex, lotteryname);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL5))
        {
            return PL5_Method.pl5_comfirm(typeindex, lotteryname);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai))
        {
            return QiXingCai_Method.qxc_comfirm(typeindex, lotteryname);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D))
        {
            return FuCai3D_Method.fc3d_comfirm(typeindex, lotteryname);
        }
        return null;
    }

    public static void RandomNumber_Status(String lotterytype,int index,ArrayList<ArrayList<LotteryButton>> buttonnumbers,HashMap<String, LotteryButton> Selectednumbers)
    {
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.SSC))
        {
            SSC_Method.SSC_Random(index, buttonnumbers, Selectednumbers);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL3))
        {
            PL3_Method.pl3_Random(index, buttonnumbers, Selectednumbers);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL5))
        {
            PL5_Method.pl5_Random(index, buttonnumbers, Selectednumbers);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai))
        {
            QiXingCai_Method.qxc_Random(index, buttonnumbers, Selectednumbers);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D))
        {
            FuCai3D_Method.fc3d_Random(index, buttonnumbers, Selectednumbers);
        }
    }

}
