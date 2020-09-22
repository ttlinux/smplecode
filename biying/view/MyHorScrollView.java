package com.lottery.biying.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.lottery.biying.bean.TrendChartBean;
import com.lottery.biying.util.LogTools;

/**
 * Created by Administrator on 2018/5/14.
 */
public class MyHorScrollView extends HorizontalScrollView {

    onScrollChanged onScrollChanged;
    boolean isTouch=false;

    public MyHorScrollView.onScrollChanged getOnScrollChanged() {
        return onScrollChanged;
    }

    public void setOnScrollChanged(MyHorScrollView.onScrollChanged onScrollChanged) {
        this.onScrollChanged = onScrollChanged;
    }

    public MyHorScrollView(Context context) {
        super(context);
        Initview(context);
    }

    public MyHorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initview(context);
    }

    public MyHorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Initview(context);
    }

    private void Initview(Context context)
    {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        LogTools.e("test","touch");
        isTouch=true;
        return super.onTouchEvent(ev);
    }

    public void MoveToX(final int x)
    {
//            post(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
            scrollTo(x, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        LogTools.e("test",l+" "+t+" "+oldl+" "+oldt+"  "+getScrollX());
        if(onScrollChanged!=null && isTouch){
            onScrollChanged.OnScroll(this,l);
            isTouch=false;
        }
    }


    public interface onScrollChanged
    {
        public void OnScroll(MyHorScrollView view, int newX);
    }
    @Override
    public void fling(int velocity) {
        super.fling(velocity / 1000);
    }

}
