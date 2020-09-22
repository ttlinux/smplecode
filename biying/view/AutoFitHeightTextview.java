package com.lottery.biying.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lottery.biying.util.LogTools;

/**
 * Created by Administrator on 2018/1/20.
 */
public class AutoFitHeightTextview extends TextView{

    public AutoFitHeightTextview(Context context) {
        super(context);
    }

    public AutoFitHeightTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFitHeightTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(getText().toString().contains("\n"))
        {
            ViewGroup.LayoutParams layoutParams=getLayoutParams();
            layoutParams.height=(int)getTextWH(getPaint(),getText().toString())[1]*3;
            setLayoutParams(layoutParams);
        }
    }

    public static float[] getTextWH(Paint mTextPaint,String text){
        float[] values=new float[2];
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        float textheight = fm.bottom - fm.top;
        values[0]=mTextPaint.measureText(text);
        values[1]=textheight;
        return  values;
    }
}
