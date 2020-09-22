package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/5/7.
 */
public class ModifyRemarkDialog extends RelativeLayout implements View.OnClickListener{

    WindowManager windowManager;
    Context context;
    TextView confirm,cancel;
    OnEditListener onEditListener;
    EditText username;

    public void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public ModifyRemarkDialog(Context context) {
        super(context);
        this.context=context;
    }

    public ModifyRemarkDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public ModifyRemarkDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public View MakeView()
    {
        View view=View.inflate(context, R.layout.dialog_modify_remark,null);
        confirm=(TextView)view.findViewById(R.id.confirm);
        cancel=(TextView)view.findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        username=(EditText)view.findViewById(R.id.username);
        return view;
    }

    public void show()
    {
        MakeView();
        if(getChildCount()<1)
        {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin= ScreenUtils.getDIP2PX(context, 30);
            params.rightMargin=ScreenUtils.getDIP2PX(context,30);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            addView(MakeView(),params);
            setBackgroundColor(0x88333333);
        }
        setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
        {
            hide();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cancel:
                hide();
                break;
            case R.id.confirm:
                hide();
                if(onEditListener!=null && username.getText().toString().length()>0)
                    onEditListener.OnEdit(username.getText().toString());
                break;
        }
    }

    public interface OnEditListener
    {
        public void OnEdit(String name);
    }

}
