package com.lottery.biying.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/6.
 */
public class NumberTextview extends TextView {

    String number;
    int count=-1;

    public static final int Zoushitu=0;
    public static final int HousanZoushi=1;
    public static final int None=-1;
    int mode=Zoushitu;//默认是0 走势图模式 1是后三走势


    public NumberTextview(Context context) {
        super(context);
    }

    public NumberTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setArgs(String number,int count)
    {
        this.number = number;
        this.count = count;
        postInvalidate();
    }
    public void setArgs(String number,int count,int Mode)
    {
        this.number = number;
        this.count = count;
        mode=Mode;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mode==Zoushitu)
        {
            zoushitu(canvas);
        }
        else
        {
            housanzoushi(canvas);
        }

        super.onDraw(canvas);

    }

    private void zoushitu(Canvas canvas)
    {
        if( count>-1)
        {
            setText(number);
            setTextColor(0xffffffff);
            Paint paint = new Paint();
            paint.setStrokeWidth(2);
            paint.setColor(0xFFEE1F3B);
            paint.setStyle(Paint.Style.FILL);//设置填满
            paint.setAntiAlias(true);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 10, paint);

            if(count>1)
            {
                paint.setColor(0xff0093DD);
                canvas.drawCircle(getWidth() / 4 * 3, 18, 18, paint);
                paint.setColor(0xffffffff);
                paint.setTextSize(30);
                canvas.drawText(count + "", getWidth() / 4 * 3 -8,28,paint);
            }

        }
        else
        {
            setText(number);
        }
    }

    private void housanzoushi(Canvas canvas)
    {
        setText(number);
        if( count>0)
        {
            setTextColor(0xffffffff);
            Paint paint = new Paint();
            paint.setStrokeWidth(2);
            paint.setColor(0xFFEE1F3B);
            paint.setStyle(Paint.Style.FILL);//设置填满
            paint.setAntiAlias(true);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 10, paint);

        }
        else
        {
            setText(number);
        }
    }
}
