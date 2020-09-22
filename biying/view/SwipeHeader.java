package com.lottery.biying.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.Rotate3dAnimation;
import com.lottery.biying.util.TimeUtils;

/**
 * Created by Administrator on 2018/5/22.不要开启上拉加载功能，此控件有坑
 */
public class SwipeHeader extends SuperSwipeRefreshLayout {





    ImageView up_image,bot_image;
    TextView up_tips,up_time,bot_tips,bot_time;
    long up_timer,bot_timer;
    OnRefreshListener onRefreshListener;
    OnPullListener onPullListener;
    int textcolor;
    boolean EnableUp=false,EnableDown=false;

    public void setOnPullListener(OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public SwipeHeader(Context context) {
        super(context);
        Initview(context);
    }

    public SwipeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initview(context);
    }

    public void enablePullUp(Context context ,boolean EnablePullUp) {
        if (EnablePullUp)
        {
            if(EnableUp)return;
            EnableUp=true;
            View botview=getView(context);
            bot_tips=(TextView)botview.findViewById(R.id.tips);
            bot_time=(TextView)botview.findViewById(R.id.time);



            bot_image=(ImageView)botview.findViewById(R.id.image);
            bot_image.setBackground(null);
            bot_image.setImageResource(R.drawable.coin_ani);
            Animatable animationDrawable2 = (Animatable) bot_image.getDrawable();
            animationDrawable2.start();
            setFooterView(botview);

            setOnPushLoadMoreListener(new OnPushLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    bot_tips.setText("正在加载数据...");
                    if (onPullListener != null) onPullListener.OnRefresh();
                }

                @Override
                public void onPushDistance(int distance) {

                }

                @Override
                public void onPushEnable(boolean enable) {
                    if(textcolor<0)
                    {
                        bot_time.setTextColor(textcolor);
                        bot_tips.setTextColor(textcolor);
                    }
                    if (enable) {
                        bot_tips.setText("松开立即加载");
                    } else {
                        bot_tips.setText("上拉加载数据");
                    }
                    if (bot_timer > 0) {
                        bot_time.setText("最近上拉： " + TimeUtils.Analysistime(bot_timer));
                    } else {
                        bot_time.setText(TimeUtils.Gettime3());
                    }
                }
            });
        }
        else
        {
            if(!EnableUp)return;
            EnableUp=false;
            removeFooterView();
            setOnPushLoadMoreListener(null);
        }
    }

    public void enablePullDown(Context context ,boolean EnablePulldown)
    {
        if(!EnablePulldown)
        {
            if(!EnableDown)return;
            EnableDown=false;
            removeHeaderView();
            setOnPullRefreshListener(new OnPullRefreshListener() {
                @Override
                public void onRefresh() {
                    CompleteRefresh();
                }

                @Override
                public void onPullDistance(int distance) {

                }

                @Override
                public void onPullEnable(boolean enable) {
                    CompleteRefresh();
                }
            });
        }
        else
        {
            if(EnableDown)return;
            EnableDown=true;

            View upview=getView(context);
            up_tips=(TextView)upview.findViewById(R.id.tips);
            up_time=(TextView)upview.findViewById(R.id.time);


            up_image=(ImageView)upview.findViewById(R.id.image);
            up_image.setBackground(null);
            up_image.setImageResource(R.drawable.coin_ani);
            Animatable animationDrawable = (Animatable) up_image.getDrawable();
            animationDrawable.start();
            setHeaderView(upview);
            setOnPullRefreshListener(new OnPullRefreshListener() {
                @Override
                public void onRefresh() {
                    up_tips.setText("正在刷新数据中...");
                    if (onRefreshListener != null) onRefreshListener.OnRefresh();
                }

                @Override
                public void onPullDistance(int distance) {


                }

                @Override
                public void onPullEnable(boolean enable) {
                    if(textcolor<0)
                    {
                        up_time.setTextColor(textcolor);
                        up_tips.setTextColor(textcolor);
                    }
                    LogTools.e("textcolor",SwipeHeader.this.textcolor+" ");
                    if (enable) {
                        up_tips.setText("松开立即刷新");
                    } else {
                        up_tips.setText("下拉可以刷新");
                    }
                    if (up_timer > 0) {
                        up_time.setText("最近刷新： " + TimeUtils.Analysistime(up_timer));
                    } else {
                        up_time.setText(TimeUtils.Gettime3());
                    }
                }
            });
        }

    }

    public void setTipTextColor(int mtextcolor)
    {
        this.textcolor=mtextcolor;
    }

    public void Initview(Context context)
    {


        setTargetScrollWithLayout(true);
        enablePullDown(context,true);


    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        if(refreshing==false)
        {
            up_timer=System.currentTimeMillis();
            if(up_image!=null)
                up_image.setBackground(null);
        }
    }

    @Override
    public void setLoadMore(boolean loadMore) {
        super.setLoadMore(loadMore);
        if(loadMore==false)
        {
            bot_timer=System.currentTimeMillis();
            if(bot_image!=null)
                bot_image.setBackground(null);
        }
    }

    private View getView(Context context)
    {
        View view=View.inflate(context, R.layout.refresh_header,null);
        setHeadeViewAniDur(500);
        return  view;
    }
    public void CompleteRefresh()
    {
        this.setRefreshing(false);
        this.setLoadMore(false);
    }



    public interface OnRefreshListener
    {
        public void OnRefresh();
    }

    public interface OnPullListener {
        public void OnRefresh();
    }

}
