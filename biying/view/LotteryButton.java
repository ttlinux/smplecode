package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lottery.biying.Activity.LotteryActivity;
import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.R;
import com.lottery.biying.util.LotteryButtonOnCheckListener;
import com.lottery.biying.util.ScreenUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;


public abstract class LotteryButton extends RelativeLayout {

    public static String RetangleShapeViews[];
    public static enum ButtonTypes {
        //彩票类型(1:时时彩 2:赛车|飞艇 3:快3 4:11选5 5:排列3|福彩 6:幸运28 7:香港)
        Circle(1),
        Rectangle_HK6(2);
        public final int index;

        // 构造方法
        private ButtonTypes(int index) {
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.index + "";
        }

        public int getIndex() {
            return this.index;
        }
    }


    public LotteryButton(Context context ) {
        super(context);
    }

    public LotteryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    public abstract ButtonTypes getType();

    public abstract Boolean getChecked();

    public abstract void setOnCheckListener(LotteryButtonOnCheckListener onCheckListener);

    public abstract void  setChecked(Boolean checked);

    public abstract void setCheckedOpposite();

    public abstract void fillUpData(Object...obj);

    public abstract String getText();

    public abstract void setText(String value);

    public abstract Object[] getData();

    public abstract View getCurrentView();


    public static LinearLayout makeView(Activity activity,String GameCode,int leftmargin)
    {
        if(RetangleShapeViews==null)
        {
            RetangleShapeViews=activity.getResources().getStringArray(R.array.RetangleShapeViews);
        }
        if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SSC.getIndex()) {
            return makeViewForButtonNumber(activity);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SYXW.getIndex()) {
            return makeViewForButtonNumber(activity);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.Saiche.getIndex()) {
            return makeViewForButtonNumber(activity);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.PL3.getIndex()) {
            return makeViewForButtonNumber(activity);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.XY28.getIndex()) {
            return makeViewForButtonNumber(activity);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.HK.getIndex()) {
            for (int i = 0; i <RetangleShapeViews.length ; i++) {
                if(GameCode.equalsIgnoreCase(RetangleShapeViews[i]))
                {
                    return makeViewForRetangleView(activity,GameCode,leftmargin);
                }
            }
            return makeViewForButtonNumberHK6(activity,GameCode);

        }
        return makeViewForButtonNumber(activity);
    }

    public static LinearLayout makeViewForButtonNumber(Activity activity)
    {
        LinearLayout numberlist = (LinearLayout) View.inflate(activity, R.layout.item_numberlist_number, null);
        ButtonNumber buttonNumber=new ButtonNumber(activity);
        buttonNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(activity,39),ScreenUtils.getDIP2PX(activity,39));
        ll.leftMargin=ScreenUtils.getDIP2PX(activity,7);
        ll.bottomMargin=ScreenUtils.getDIP2PX(activity,5);
        buttonNumber.setLayoutParams(ll);
        numberlist.addView(buttonNumber,0);
        return numberlist;
    }

    public static LinearLayout makeViewForButtonNumberHK6(Activity activity,String GameCode)
    {
        LinearLayout numberlist = (LinearLayout) View.inflate(activity, R.layout.item_numberlist_number, null);
        ButtonNumberHK6 buttonNumber=new ButtonNumberHK6(activity,GameCode);
        buttonNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(activity,39),ScreenUtils.getDIP2PX(activity,39));
        ll.leftMargin=ScreenUtils.getDIP2PX(activity,7);
        ll.bottomMargin=ScreenUtils.getDIP2PX(activity,5);
        buttonNumber.setLayoutParams(ll);
        numberlist.addView(buttonNumber,0);
        return numberlist;
    }


    public static LinearLayout makeViewForRetangleView(Activity activity,String GameCode,int leftmargin)
    {
        LinearLayout numberlist = (LinearLayout) View.inflate(activity, R.layout.item_numberlist_number, null);
        RetangleView buttonNumber=new RetangleView(activity,GameCode);
        int width=(ScreenUtils.getScreenWH(activity)[0]-ScreenUtils.getDIP2PX(activity,35))/4;
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.leftMargin=leftmargin>0?leftmargin:ScreenUtils.getDIP2PX(activity,5);
        ll.bottomMargin=ScreenUtils.getDIP2PX(activity,5);
        numberlist.setLayoutParams(ll);
        numberlist.addView(buttonNumber,0);
        return numberlist;
    }

}
