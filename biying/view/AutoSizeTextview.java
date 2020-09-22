package com.lottery.biying.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;


/**
 * Created by gd on 2016/12/2.
 */
public class AutoSizeTextview extends TextView {

    private Paint testPaint;
    private float cTextSize = -1;
    private boolean autosize = false;
    private int textwidth = 0;
    String LasttimeString="";
    float Inittextsize=0;

    public AutoSizeTextview(Context context) {
        super(context);
        init(context, null);
    }

    public AutoSizeTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoSizeTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoSizeTextview);
//        Height= ScreenUtils.getDIP2PX(context,typedArray.getDimension(R.styleable.TriangleView_ShapeHeight, 100f));
//        Width=ScreenUtils.getDIP2PX(context, typedArray.getDimension(R.styleable.TriangleView_ShapeHeight, 50f));
        autosize = typedArray.getBoolean(R.styleable.AutoSizeTextview_autofix, false);
        LogTools.e("autosize", autosize ? "YYYY" : "NNNN");
        typedArray.recycle();
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     * 在此方法中学习到：getTextSize返回值是以像素(px)为单位的，而setTextSize()是以sp为单位的，
     * 因此要这样设置setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
     */
    private void refitText(String text,
                           int textWidth) {
//        if(cTextSize>-1)
//        {
//            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, cTextSize);//这里制定传入的单位是px
//            return;
//        }
        if (textWidth > 0) {
            testPaint =
                    new Paint();
            cTextSize =
                    textwidth > 0 ? textwidth : this.getTextSize();//这个返回的单位为px
//            testPaint.set(this.getPaint());
            //获得当前TextView的有效宽度
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
//            Rect rect =
//                    new Rect();
//            testPaint.getTextBounds(text,
//                    0, text.length(), rect);
            //所有字符串所占像素宽度
            int textWidths = getwidth(testPaint, cTextSize, text);
            if(textWidths <=availableWidth){
                return;
            }
            LogTools.e("cTextSize",cTextSize+"");
            while (textWidths > availableWidth) {
                if (cTextSize < 12) break;
                cTextSize = cTextSize - 1;
//                testPaint.setTextSize(cTextSize);//这里传入的单位是px
                textWidths = getwidth(testPaint, cTextSize, text);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, cTextSize);//这里制定传入的单位是px
        }
    }


    private int getwidth(Paint paint, float textsize, String text) {
        float[] widths =
                new float[text.length()];
        int textWidths = 0;

        paint.setTextSize(textsize);
        paint.getTextWidths(text, widths);
        for (int k = 0; k < widths.length; k++) {
            textWidths += widths[k];
        }

        return textWidths;
    }

    public void setcTextSizeWithOutDraw(int width) {
        textwidth = width;
    }

    public void setAutoSize() {
        autosize = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (autosize)
        {
            if(Inittextsize==0)Inittextsize=getTextSize();
            if(LasttimeString.length()<1)
            {
                LasttimeString = getText().toString();
                refitText(getText().toString(),
                        this.getWidth());
            }
            else
            {
                int last=LasttimeString.length();
                int current=getText().toString().length();
                if(last!=current) {
                    if(current<last)
                    {
                        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, Inittextsize);//这里制定传入的单位是px
                    }
                    refitText(getText().toString(),
                            this.getWidth());
                }

                LasttimeString = getText().toString();
            }

        }

    }



    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

    }
}
