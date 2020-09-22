package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/4/30.
 */
public class OrderHistoryDialog extends RelativeLayout {

    String[] titles;
    BubbleView bubbleView;
    WindowManager windowManager;
    Context context;
    OnDiaClickitemListener onDiaClickitemListener;
    OnDismissListener Ondismiss;

    public OnDismissListener getOndismiss() {
        return Ondismiss;
    }

    public void setOndismiss(OnDismissListener ondismiss) {
        Ondismiss = ondismiss;
    }

    public OrderHistoryDialog(Context context) {
        super(context);
    }

    public OrderHistoryDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderHistoryDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setOnDiaClickitemListener(OnDiaClickitemListener onDiaClickitemListener) {
        this.onDiaClickitemListener = onDiaClickitemListener;
    }
    public OrderHistoryDialog(Context context, String[] titles) {
        super(context);
        this.titles = titles;
        this.context = context;
    }
    private void MakeView() {

        bubbleView = new BubbleView(context);
        bubbleView.setOrientation(BubbleView.VERTICAL);

//        bubbleView.SetLayoutPosition(BubbleView.Side_Top, BubbleView.Style_Right);
        bubbleView.SetLayoutPosition(BubbleView.Side_Top, BubbleView.Style_Right);
        bubbleView.setBotWidth(0);
        bubbleView.setBotHeight(0);

        bubbleView.SetViewStyle(true);
        bubbleView.SetViewColor(0xFF272C2F);

        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                TextView textview = new TextView(context);
                textview.setText(titles[i]);
                textview.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textview.setTextColor(0xFFFFFFFF);
                textview.setGravity(Gravity.CENTER);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textview.setPadding(ScreenUtils.getDIP2PX(context, 15), ScreenUtils.getDIP2PX(context, 13), ScreenUtils.getDIP2PX(context, 15), ScreenUtils.getDIP2PX(context, 13));
                textview.setSingleLine(true);
                textview.setTag(i);
                textview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onDiaClickitemListener != null)
                            onDiaClickitemListener.Onclickitem((int) v.getTag());
                    }
                });
                bubbleView.addView(textview);
                ImageView imagview = new ImageView(context);
                imagview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                imagview.setBackgroundColor(0xFF6D777E);
                bubbleView.addView(imagview);
            }
            if (bubbleView.getChildCount() > 0)
                bubbleView.removeViewAt(bubbleView.getChildCount() - 1);
        }
    }

    public void showDropdown(View view)
    {
        MakeView();
        if(getChildCount()<1)
        {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ScreenUtils.getDIP2PX(context, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
//        params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.topMargin=view.getTop() +view.getHeight();
            params.leftMargin=view.getWidth()-ScreenUtils.getDIP2PX(context, 100);
            LogTools.e("showDropdown", view.getTop() + view.getHeight() + "  " + view.getLeft());
            bubbleView.setLayoutParams(params);
            addView(bubbleView);
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        windowManager=((Activity)context).getWindowManager();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity= Gravity.CENTER;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明
        windowManager.addView(this, lp);
    }

    public void hide()
    {
        windowManager.removeView(this);
        if(Ondismiss!=null)
            Ondismiss.Ondismiss();
    }
    public interface OnDiaClickitemListener
    {
        public void Onclickitem(int index);
    }

    public interface OnDismissListener
    {
        public void Ondismiss();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            hide();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
