package com.lottery.biying.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/3/7.
 */
public class ZoushiSequenceDialog extends RelativeLayout implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    WindowManager windowManager;
    Context context;
    TextView cancel,confirm;;
    RadioButton Desc,Asc;
    OnChooseListener onChooseListener;
    int originalIndex;
    int checkindex;
    boolean ischange=false;

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

    public ZoushiSequenceDialog(Context context) {
        super(context);
        InitView(context);
    }

    public ZoushiSequenceDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }

    public ZoushiSequenceDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView(context);
    }

    public void setCheck(int index)
    {
        checkindex=index;
        switch (index)
        {
            case 0:
                Desc.setChecked(true);
                break;
            case 1:
                Asc.setChecked(true);
                break;
        }
    }

    public void InitView(Context context)
    {
        this.context=context;
        View view =View.inflate(context, R.layout.zoushi_dialog,null);
        Desc=(RadioButton)view.findViewById(R.id.Desc);
        Desc.setOnCheckedChangeListener(this);
        Asc=(RadioButton)view.findViewById(R.id.Asc);
        Asc.setOnCheckedChangeListener(this);
        cancel=(TextView)view.findViewById(R.id.cancel);
        confirm=(TextView)view.findViewById(R.id.confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(onChooseListener==null)return;
        switch (v.getId())
        {
            case R.id.cancel:
                hide();
                onChooseListener.Onchoose(false,0,this);
                break;
            case R.id.confirm:
                hide();
                onChooseListener.Onchoose(ischange,checkindex,this);
                break;
        }
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
    }

    public void hide()
    {
        windowManager.removeView(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            switch (buttonView.getId())
            {
                case R.id.Desc:
                    checkindex=0;
                    Desc.setTextColor(context.getResources().getColor(R.color.loess4));
                    break;
                case R.id.Asc:
                    checkindex=1;
                    Asc.setTextColor(context.getResources().getColor(R.color.loess4));
                    break;
            }
            ischange=originalIndex!=checkindex;
        }
        else
        {
            buttonView.setTextColor(context.getResources().getColor(R.color.gray19));
        }
    }

    public interface OnChooseListener
    {
        public void Onchoose(boolean ischange,int index,ZoushiSequenceDialog dialog);
    }
}
