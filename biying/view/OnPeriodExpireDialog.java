package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/3/21.
 */
public class OnPeriodExpireDialog extends RelativeLayout{

    WindowManager windowManager;
    Context context;
    int originalIndex;
    int checkindex;
    TextView content,confirm;
    boolean isshowing=false;


    public OnPeriodExpireDialog(Context context) {
        super(context);
        InitView(context);
    }

    public OnPeriodExpireDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }

    public OnPeriodExpireDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView(context);
    }

    public void setContentText(String text)
    {
        content.setText(text);
    }

    public void InitView(Context context)
    {
        this.context=context;
        View view =View.inflate(context, R.layout.dialog_period_expire,null);

        content=(TextView)view.findViewById(R.id.content);
        confirm=(TextView)view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        rl.leftMargin= ScreenUtils.getDIP2PX(context, 15);
        rl.rightMargin=ScreenUtils.getDIP2PX(context,15);
        addView(view, rl);

        setBackgroundColor(0xaa000000);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

    public void show()
    {
        windowManager=((Activity)context).getWindowManager();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity= Gravity.CENTER;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明

        windowManager.addView(this, lp);
        originalIndex=checkindex;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                hide();
            }
        },5000);//5秒后自动关闭弹窗
        isshowing=true;
    }

    public void hide()
    {
        if(!isshowing)return;
        windowManager.removeView(this);
        isshowing=false;
    }
}
