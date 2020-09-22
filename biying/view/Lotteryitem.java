package com.lottery.biying.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.util.FuCai3D_Method;
import com.lottery.biying.util.LotteryTypeName;
import com.lottery.biying.util.PL3_Method;
import com.lottery.biying.util.PL5_Method;
import com.lottery.biying.util.QiXingCai_Method;
import com.lottery.biying.util.SSC_Method;

/**
 * Created by Administrator on 2017/12/5.
 */
public class Lotteryitem {

    public static void ModifyTitleItem(String lotterytype,RelativeLayout layout)
    {
        if (lotterytype.equalsIgnoreCase(LotteryTypeName.SSC)) {
             SSC_Method.ModifyTitleItem(layout);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL3))
        {
             PL3_Method.ModifyTitleItem(layout);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL5))
        {
             PL5_Method.ModifyTitleItem(layout);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai))
        {
             QiXingCai_Method.ModifyTitleItem(layout);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D))
        {
             FuCai3D_Method.ModifyTitleItem(layout);
        }
    }
    public static RelativeLayout Inititem(String lotterytype,LotteryResultList lrl,int color,Context context,View view)
    {

        if (lotterytype.equalsIgnoreCase(LotteryTypeName.SSC)) {
            return SSC_Method.Inititem(lrl, color,context,view);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL3))
        {
            return PL3_Method.Inititem(lrl, color, context,view);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.PL5))
        {
            return PL5_Method.Inititem(lrl, color, context,view);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.QiXingCai))
        {
            return QiXingCai_Method.Inititem(lrl, color, context,view);
        }
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.FuCai3D))
        {
            return FuCai3D_Method.Inititem(lrl, color, context,view);
        }

        return null;
    }


}
