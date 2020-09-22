package com.lottery.biying.view;

import android.content.Context;
import android.gesture.Gesture;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.lottery.biying.util.LogTools;

/**
 * Created by Administrator on 2017/11/23.
 */
public class FloatingView extends RelativeLayout {

    float EndScrollY;
    float StartScrollY;
    float EndScrollX;
    float StartScrollX;
     float wrongdistance = 0;
    float ScrollYLimit=0;
    float ScrollBackLimit=0;
    private Scroller scroller;
    final int RollBack=111;
    int itemheight=0;

    /**
     * 速度追踪对象
     */
    private VelocityTracker velocityTracker;

    Handler handler=new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what)
            {
                case RollBack:
                    float limit=(ScrollYLimit-ScrollBackLimit)*-1;
                    setScrollY((int)limit);
                    break;
            }
        }
    };

    public FloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }

    public FloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context);
    }

    public FloatingView(Context context) {
        super(context);
        Init(context);
    }


    private void Init(Context context) {
        wrongdistance= ViewConfiguration.get(getContext()).getScaledTouchSlop();
        LogTools.e("wrongdistance"," "+wrongdistance);
        scroller = new Scroller(context);
    }

    public void SetScrollYLimit(float ScrollYLimit) {
        this.ScrollYLimit = ScrollYLimit;
    }

    public void SetScrollBackLimit(float ScrollBackLimit) {
        this.ScrollBackLimit = ScrollBackLimit;
    }

    public void Setitemheight(int itemheight)
    {
        this.itemheight=itemheight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LogTools.e("onLayout", "onLayout");
        getChildAt(0).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int ScrollY = getScrollY();

        addVelocityTracker(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                StartScrollY = ev.getY();
                StartScrollX = ev.getX();

                break;
            case MotionEvent.ACTION_MOVE:


                LogTools.e("MotionEvent.ACTION_MOVE", "MotionEvent.ACTION_MOVE");
                float distanceY = Math.abs(Math.abs(ev.getY()) - Math.abs(EndScrollY > 0 ? EndScrollY : StartScrollY));
                float distanceX = Math.abs(Math.abs(ev.getX()) - Math.abs(EndScrollX > 0 ? EndScrollX : StartScrollX));
                if (distanceY < wrongdistance || distanceX > distanceY)
                    return super.onInterceptTouchEvent(ev);

                if (EndScrollY > 0) {
                    if (ev.getY() > EndScrollY) {
                        //向下

                        if(ScrollYLimit<=Math.abs(ScrollY) && ScrollYLimit>0)
                        {
                            LogTools.e("distance1", "--->" + ScrollY + "--->" + ev.getY() + "--->" + getY());
                            setScrollY((int) ScrollYLimit * -1);
                        }
                            else
                        {
                            LogTools.e("distance5", "--->" + ScrollY + "--->" + ev.getY() + "--->" + getY());
                            scrollBy(0, ((int) distanceY * -1) / 2);
                        }

                    } else {
                        //向上
                        LogTools.e("distance2", "--->" + ScrollY + "--->" + ev.getY() + "--->" + getY());
                        if (ScrollY < 0) {
                            if (ScrollY + (int) distanceY > 0) {
                                setScrollY(0);
                            }
                            else
                            {
                                scrollBy(0, ((int) distanceY)/2);
                            }
                        }
                    }
                } else {
                    if (ev.getY() > StartScrollY) {
                        //向下

                        if(ScrollYLimit<=Math.abs(ScrollY) && ScrollYLimit > 0) {
                            LogTools.e("distance3", "--->" + ScrollY + "--->" + ev.getY() + "--->" + getY());
                            setScrollY((int) ScrollYLimit * -1);
                        } else {
                            LogTools.e("distance6", "--->" + ScrollY + "--->" + ev.getY() + "--->" + getY());
                            scrollBy(0, ((int) distanceY * -1) / 2);
                        }

                    } else {
                        //向上
                        LogTools.e("distance4", "--->" + ScrollY + "--->" + ev.getY() + "--->" + getY());
                        if (ScrollY < 0) {
                            if (ScrollY + (int) distanceY > 0) {
                                setScrollY(0);
                            } else
                            {
                                scrollBy(0, ((int) distanceY)/2);
                            }
                        }
                    }
                }


                EndScrollY = ev.getY();
                EndScrollX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                LogTools.e("MotionEvent.ACTION_UP", "MotionEvent.ACTION_UP"+ev.getY()+"  "+StartScrollY+"  "+EndScrollY);
                float distanceY2 = Math.abs(Math.abs(ev.getY()) - Math.abs(EndScrollY > 0 ? EndScrollY : StartScrollY));//减缓滑动距离 /2
                LogTools.e("sccc3",getScrollVelocity()+"   ");
                if (getScrollVelocity()>0)
                {
                    boolean isLimit=distanceY2+Math.abs(ScrollY)>=ScrollYLimit-ScrollBackLimit;
                    if(isLimit)
                    {
                        distanceY2=ScrollYLimit-ScrollBackLimit-Math.abs(ScrollY);
                    }
                    //向下
                    LogTools.e("sccc1", "sccc1  " + distanceY2);
                    int realdistan=(int) GetSideHeight((int)distanceY2, ScrollY);
                    scroller.startScroll(0, ScrollY, 0, -(int)realdistan,
                            Math.abs((int)realdistan));
                    postInvalidate(); // 刷新itemView
                    if(isLimit)
                        handler.sendEmptyMessageDelayed(RollBack,500);
                }
                else
                {
                    //向上
                    if(ScrollY<=0 &&  distanceY2+ScrollY>=0)
                    {
                        distanceY2=-1*ScrollY;
                    }
                    LogTools.e("sccc2", "sccc2  "+distanceY2);
                    int realdistan=(int) GetSideHeight(-(int)distanceY2, ScrollY);
                    scroller.startScroll(0, ScrollY, 0, (int)realdistan,
                            Math.abs((int)realdistan));
                    postInvalidate(); // 刷新itemView
                }
                EndScrollY = 0;
                EndScrollX = 0;
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                LogTools.e("MotionEvent.ACTION_CANCEL", "MotionEvent.ACTION_CANCEL");
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 添加用户的速度跟踪器
     *
     * @param event
     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    /**
     * 移除用户速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.clear();
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    /**
     * 获取Y方向的滑动速度,大于0向上滑动，反之向下
     *
     * @return
     */
    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getYVelocity();
        return velocity;
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (scroller.computeScrollOffset()) {
            // 让ListView item根据当前的滚动偏移量进行滚动
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
//
            postInvalidate();

            // 滚动动画结束的时候调用回调接口
            if (scroller.isFinished()) {
//                if (mRemoveListener == null) {
//                    throw new NullPointerException("RemoveListener is null, we should called setRemoveListener()");
//                }

//                scrollTo(0, 0);

            }
        }
    }

    public float GetSideHeight(int y,int scrolly)
    {
        int realY=0;
        int exscrolly=Math.abs(scrolly)+y;
        if(y==0) return realY;
        if(itemheight>0)
        {
            if(y>0)
            {
                realY=(exscrolly%itemheight==0?(exscrolly/itemheight):((exscrolly/itemheight)+1))*itemheight;
                realY=realY-Math.abs(scrolly);
            }
            else
            {
                realY=exscrolly/itemheight*itemheight;
                realY=Math.abs(scrolly)-realY;
            }

            LogTools.e("itemheight11",itemheight+" "+realY+"  "+scrolly+" "+exscrolly+" "+y);


        }
        else
            throw new NullPointerException("设置itemheight再说");
        LogTools.e("itemheight",itemheight+" "+realY+"  "+scrolly+" "+exscrolly);
        return realY;
    }
}
