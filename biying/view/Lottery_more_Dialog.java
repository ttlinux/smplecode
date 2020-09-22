package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/30.
 */
public class Lottery_more_Dialog extends RelativeLayout {

    BubbleView bubbleView;
    WindowManager windowManager;
    Context context;
    OnDiaClickitemListener onDiaClickitemListener;
    ArrayList<MenuBean.GameListBean.ListBean.Helper> switchs;

    public void setSwitchs( ArrayList<MenuBean.GameListBean.ListBean.Helper> switchs) {
        this.switchs = switchs;
    }

    public OnDiaClickitemListener getOnDiaClickitemListener() {
        return onDiaClickitemListener;
    }

    public void setOnDiaClickitemListener(OnDiaClickitemListener onDiaClickitemListener) {
        this.onDiaClickitemListener = onDiaClickitemListener;
    }

    public Lottery_more_Dialog(Context context) {
        super(context);
        this.context = context;
    }



    public Lottery_more_Dialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public Lottery_more_Dialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void MakeView() {

        bubbleView = new BubbleView(context);
        bubbleView.setOrientation(BubbleView.VERTICAL);

//        bubbleView.SetLayoutPosition(BubbleView.Side_Top, BubbleView.Style_Right);
        bubbleView.SetLayoutPosition(BubbleView.Side_Top, BubbleView.Style_Right);
        bubbleView.setBotWidth(50);
        bubbleView.setBotHeight(30);

        bubbleView.SetViewStyle(true);
        bubbleView.SetViewColor(0xFF272C2F);

        if (switchs != null) {
            for (int i = 0; i < switchs.size(); i++) {

                TextView textview = new TextView(context);
                textview.setText(switchs.get(i).getValue());
                textview.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textview.setTextColor(0xFFFFFFFF);
                textview.setGravity(Gravity.CENTER);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textview.setPadding(ScreenUtils.getDIP2PX(context, 15), ScreenUtils.getDIP2PX(context, 13), ScreenUtils.getDIP2PX(context, 15), ScreenUtils.getDIP2PX(context, 13));
                textview.setSingleLine(true);
                textview.setTag(switchs.get(i));
                textview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onDiaClickitemListener != null)
                            onDiaClickitemListener.Onclickitem((MenuBean.GameListBean.ListBean.Helper) v.getTag());
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

    public void showDropdown(View view) {
        MakeView();
        if (getChildCount() < 1) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtils.getDIP2PX(context, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
//        params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.topMargin = view.getTop() + view.getHeight() ;
            params.leftMargin = view.getRight()-ScreenUtils.getDIP2PX(context, 100);
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
        windowManager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明
        windowManager.addView(this, lp);
    }

    public void hide() {
        windowManager.removeView(this);
    }

    public interface OnDiaClickitemListener {
        public void Onclickitem(MenuBean.GameListBean.ListBean.Helper helper);
    }
}
