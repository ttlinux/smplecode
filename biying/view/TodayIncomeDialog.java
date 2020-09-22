package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.OnMultiClickListener;
import com.lottery.biying.util.ScreenUtils;

import java.util.ArrayList;

public class TodayIncomeDialog extends RelativeLayout {

    Activity context;
    View view;
    WindowManager windowManager;

    public TodayIncomeDialog(Activity context) {
        super(context);
        InitView(context);
    }

    public TodayIncomeDialog(Activity context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }

    public TodayIncomeDialog(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView(context);
    }

    private void InitView(Activity context)
    {
        setBackground(new ColorDrawable(0x88817F80));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dimiss();
            }
        });

        this.context=context;
         view=View.inflate(context, R.layout.dailog_today_income,null);
        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        view.setLayoutParams(rl);
        addView(view);
        findViewById(R.id.cancel).setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                dimiss();
            }
        });
    }

    public void fillUpData(ArrayList<ArrayList<String>> arrayLists)
    {
        int padding= ScreenUtils.getDIP2PX(context,5);
        TableLayout tablelayout=findViewById(R.id.tablelayout);
        for (int i = 0; i <arrayLists.size() ; i++) {
            TableRow tableRow=new TableRow(context);
            tableRow.setPadding(0,1,0,0);
            for (int j = 0; j <5 ; j++) {
                TextView textView=new TextView(context);
                TableRow.LayoutParams tl=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                tl.weight=1;
                tl.leftMargin=j>0?1:0;
                textView.setLayoutParams(tl);
                if(j==0)
                {
                    textView.setPadding(padding*3,padding,padding*3,padding);
                }
                else
                {
                    textView.setPadding(padding,padding,padding,padding);
                }
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.WHITE);
                textView.setText(arrayLists.get(i).get(j));
                tableRow.addView(textView);
            }
            tablelayout.addView(tableRow);
        }
    }

    public void show() {
        if (context != null && !context.isDestroyed() && !context.isFinishing())
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        lp.gravity = Gravity.CENTER;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明
        windowManager.addView(this, lp);
    }

    public void dimiss()
    {
        if (context != null && !context.isDestroyed() && !context.isFinishing())
        windowManager.removeView(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
        {
            dimiss();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
