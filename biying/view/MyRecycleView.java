package com.lottery.biying.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.lottery.biying.util.LogTools;

/**
 * Created by Administrator on 2018/5/14.
 */
public class MyRecycleView extends RecyclerView {
    public MyRecycleView(Context context) {
        super(context);
        Initview(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initview(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initview(context);
    }

    private void Initview(Context context)
    {

    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

    }
}
