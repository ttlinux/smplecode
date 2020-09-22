package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;


/**
 * Created by Administrator on 2017/12/16.
 */
public class AppAlertDialog extends RelativeLayout implements View.OnClickListener{

    public String titles,cancelstr,confirmstr;
    public CharSequence conetents;
    private TextView title,content,cancel,confirm;
    ImageView line;
    WindowManager windowManager;
    Context context;
    OnChooseListener onChooseListener;
    Handler handler=new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what)
            {
                case 0:
                    hide();
                    break;
            }
        }
    };
    boolean Exits=false;

    public void setStyle()
    {
        line.setVisibility(GONE);
        cancel.setVisibility(GONE);
    }

    public void NoContent()
    {
        content.setVisibility(GONE);
    }

    public void Notitle()
    {
        title.setVisibility(GONE);
    }

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

    public void setConfirmstr(String confirmstr) {
        this.confirmstr = confirmstr;
        confirm.setText(confirmstr);
    }

    public void setCancelstr(String cancelstr) {
        this.cancelstr = cancelstr;
        cancel.setText(cancelstr);
    }

    public void setTitle(String titles) {
        this.titles = titles;
        title.setText(titles);
        findViewById(R.id.titleline).setVisibility(GONE);
    }

    public void setTitleWithLine(String titles) {
        this.titles = titles;
        title.setText(titles);
    }

    public void setConetnt(CharSequence conetnts) {
        this.conetents = conetnts;
        content.setText(conetents);
    }

    public void setConetntTextSize(int Type ,float size) {
        content.setTextSize(Type,size);
    }

    public void setConetntgravity(int gravity) {
        content.setGravity(gravity);
    }

    public AppAlertDialog(Context context) {
        super(context);
        InitView(context);
    }

    public AppAlertDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }

    public AppAlertDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView(context);
    }

    public void setSingleLine(boolean boo)
    {
        if(content!=null)
            content.setSingleLine(boo);
    }
    public void InitView(Context context)
    {
        this.context=context;
        View view =View.inflate(context, R.layout.dialog_appalert,null);
        title=(TextView)view.findViewById(R.id.title);
        content=(TextView)view.findViewById(R.id.content);
        content.setEnabled(false);
        content.setFocusable(false);
        content.setSingleLine(false);
        content.setFilters(new InputFilter[]{});
        content.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        cancel=(TextView)view.findViewById(R.id.cancel);
        confirm=(TextView)view.findViewById(R.id.confirm);
        line=(ImageView)view.findViewById(R.id.line);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        LayoutParams rl=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        rl.leftMargin= ScreenUtils.getDIP2PX(context, 15);
        rl.rightMargin=ScreenUtils.getDIP2PX(context,15);
        addView(view, rl);

        setBackgroundColor(0xaa000000);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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
        Exits=true;
    }

    public void hide()
    {
        handler.removeMessages(0);

        if(context!=null && Exits)
        {
            if(context instanceof Activity)
            {
                Activity activity=(Activity)context;
                if(!activity.isFinishing() && !activity.isDestroyed())
                {
                    windowManager.removeView(this);
                }
            }
            else
            {
                windowManager.removeView(this);
            }
            Exits=false;

        }


    }
    public void setAutoClose()
    {
        handler.sendEmptyMessageDelayed(0,5*1000);
    }

    @Override
    public void onClick(View v) {
        if(onChooseListener==null)return;
        switch (v.getId())
        {
            case R.id.cancel:
                onChooseListener.Onchoose(false,this);
                break;
            case R.id.confirm:
                onChooseListener.Onchoose(true,this);
                break;
        }
    }

    public interface OnChooseListener
    {
        public void Onchoose(boolean confirm, AppAlertDialog dialog);
    }
}
