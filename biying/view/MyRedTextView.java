package com.lottery.biying.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lottery.biying.util.LogTools;

/**
 * Created by Administrator on 2018/5/18.
 */
public class MyRedTextView extends TextView {

    int originalColor;
    public Boolean isred=false;

    public Boolean getIsred() {
        return isred;
    }

    public void setIsred(Boolean isred) {
        this.isred = isred;
    }

    public int getOriginalColor() {
        return originalColor;
    }

    public void setOriginalColor(int originalColor) {
        this.originalColor = originalColor;
    }

    public MyRedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyRedTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(isred)
        {
            Paint paint=new Paint();
            paint.setAntiAlias(true);
            paint.setColor(0xFFEE1F3B);
            paint.setStyle(Paint.Style.FILL);
            int radiu=getWidth()>getHeight()?(getHeight()/2-10):(getWidth()/2-10);
            canvas.drawCircle(getWidth()/2,getHeight()/2,radiu,paint);
            setTextColor(0xFFFFFFFF);
        }
        else
        {
            setTextColor(originalColor);
        }
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
