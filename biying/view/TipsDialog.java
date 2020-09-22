package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/4/5.
 */
public class TipsDialog extends RelativeLayout{


    public String titles,conetents,cancelstr,confirmstr;
    private TextView content,confirm;
    ImageView line;
    WindowManager windowManager;
    Context context;

    public TipsDialog(Context context) {
        super(context);
        InitView(context);
    }

    public TipsDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }

    public TipsDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView(context);
    }

    public void setConetent(String content_str,String example)
    {
        if(content!=null)
        {
            content.setText("");
            content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            content.append("玩法:\n");
            content.append(Html.fromHtml("<small>" + content_str + "</small>"));
            content.append("\n\n");
            content.append("示例:\n");
            content.append(Html.fromHtml("<small>" + example + "</small>"));
        }
    }
    public void InitView(Context context)
    {
        this.context=context;
        View view =View.inflate(context, R.layout.dialog_play_tips,null);
        content=(TextView)view.findViewById(R.id.content);
        confirm=(TextView)view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        line=(ImageView)view.findViewById(R.id.line);

        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        rl.leftMargin= ScreenUtils.getDIP2PX(context, 15);
        rl.rightMargin=ScreenUtils.getDIP2PX(context,15);
        addView(view, rl);

        setBackgroundColor(0xaa000000);
        setOnClickListener(new View.OnClickListener() {
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
    }

    public void hide()
    {
        windowManager.removeView(this);
    }

}
