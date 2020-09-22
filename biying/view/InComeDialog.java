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
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.PersonalIncomBean;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/5/3.
 */
public class InComeDialog extends RelativeLayout{

    WindowManager windowManager;
    Context context;
    TextView confirm;
    GridLayout gridview;

    public InComeDialog(Context context) {
        super(context);
        this.context=context;
    }

    public InComeDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public InComeDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public View MakeView(TeamIncomeBean bean)
    {
        View view=View.inflate(context, R.layout.dialog_team_income,null);
        confirm=(TextView)view.findViewById(R.id.confirm);
        gridview=(GridLayout)view.findViewById(R.id.gridview);
        String values[]={bean.getBetAmount(),bean.getWinAmount(),bean.getDepositAmount(),bean.getDrawAmount(),bean.getHuoDongAmount(),bean.getBetBack()};

        int itemwidth=(ScreenUtils.getScreenWH((Activity)context)[0]-ScreenUtils.getDIP2PX(context,70))/2;
        for (int i = 0; i <gridview.getChildCount() ; i++) {
            LinearLayout ll=(LinearLayout)gridview.getChildAt(i);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width=itemwidth;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 10);
            if(i%2==1)
            layoutParams.leftMargin = ScreenUtils.getDIP2PX(context, 10);
            ll.setLayoutParams(layoutParams);
            TextView textview=(TextView)ll.getChildAt(1);
            textview.setText(values[i]);
        }
        TextView real_income=(TextView)view.findViewById(R.id.real_income);
        real_income.setText(bean.getTotalProfit());
        if(bean.getTestValue().equalsIgnoreCase("0"))
        {
            real_income.setTextColor(context.getResources().getColor(R.color.green));
        }
        else
        {
            real_income.setTextColor(context.getResources().getColor(R.color.red2));
        }
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        return view;
    }

    public View MakeView(PersonalIncomBean bean)
    {
        View view=View.inflate(context, R.layout.dialog_team_income,null);
        confirm=(TextView)view.findViewById(R.id.confirm);
        gridview=(GridLayout)view.findViewById(R.id.gridview);
//        String values[]={bean.getBetAmount(),bean.getWinAmount(),bean.getDepositAmount(),bean.getDrawAmount(),bean.getHuoDongAmount(),bean.getBetBack()};
        String values[]={bean.getBetAmount(),bean.getWinAmount(),bean.getBetBack()};

        int itemwidth=(ScreenUtils.getScreenWH((Activity)context)[0]-ScreenUtils.getDIP2PX(context,70))/2;
        //去掉 充值总额 提款总额 活动总额
        gridview.removeViewAt(2);
        gridview.removeViewAt(2);
        gridview.removeViewAt(2);
        for (int i = 0; i <gridview.getChildCount() ; i++) {
            LinearLayout ll=(LinearLayout)gridview.getChildAt(i);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width=itemwidth;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 10);
            if(i%2==1)
                layoutParams.leftMargin = ScreenUtils.getDIP2PX(context, 10);
            ll.setLayoutParams(layoutParams);
            TextView textview=(TextView)ll.getChildAt(1);
            textview.setText(values[i]);
        }
        TextView real_income=(TextView)view.findViewById(R.id.real_income);
        real_income.setText(bean.getTotalProfit());
        if(bean.getTestValue().equalsIgnoreCase("0"))
        {
            real_income.setTextColor(context.getResources().getColor(R.color.green));
        }
        else
        {
            real_income.setTextColor(context.getResources().getColor(R.color.red2));
        }
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        return view;
    }

    public View MakeView2(PersonalIncomBean bean)
    {
        View view=View.inflate(context, R.layout.dialog_team_income2,null);
        confirm=(TextView)view.findViewById(R.id.confirm);
        gridview=(GridLayout)view.findViewById(R.id.gridview);
//        String values[]={bean.getBetAmount(),bean.getBetInAmout(),bean.getBetPayout(),bean.getBetBack(),
//                bean.getDepositAmount(),bean.getWithdrawAmount(),bean.getHuoDongAmount()};
        String values[]={bean.getBetAmount(),bean.getBetInAmout(),bean.getBetPayout(),bean.getBetBack(),
                bean.getDepositAmount(),bean.getWithdrawAmount()};

        int itemwidth=(ScreenUtils.getScreenWH((Activity)context)[0]-ScreenUtils.getDIP2PX(context,70))/2;

        //去掉  活动总额
        gridview.removeViewAt(6);

        for (int i = 0; i <gridview.getChildCount() ; i++) {
            LinearLayout ll=(LinearLayout)gridview.getChildAt(i);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width=itemwidth;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 10);
            if(i%2==1)
                layoutParams.leftMargin = ScreenUtils.getDIP2PX(context, 10);
            ll.setLayoutParams(layoutParams);
            TextView textview=(TextView)ll.getChildAt(1);
            textview.setText(values[i]);
        }
        TextView real_income=(TextView)view.findViewById(R.id.real_income);
        real_income.setText(bean.getTotalProfit());
        if(bean.getTestValue().equalsIgnoreCase("0"))
        {
            real_income.setTextColor(context.getResources().getColor(R.color.green));
        }
        else
        {
            real_income.setTextColor(context.getResources().getColor(R.color.red2));
        }
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        return view;
    }

    public View MakeView2(TeamIncomeBean bean)
    {
        View view=View.inflate(context, R.layout.dialog_team_income2,null);
        confirm=(TextView)view.findViewById(R.id.confirm);
        gridview=(GridLayout)view.findViewById(R.id.gridview);
        String values[]={bean.getBetAmount(),bean.getBetInAmout(),bean.getWinAmount(),bean.getBetBack(),bean.getDepositAmount(),bean.getDrawAmount(),bean.getHuoDongAmount()};

        int itemwidth=(ScreenUtils.getScreenWH((Activity)context)[0]-ScreenUtils.getDIP2PX(context,70))/2;
        for (int i = 0; i <gridview.getChildCount() ; i++) {
            LinearLayout ll=(LinearLayout)gridview.getChildAt(i);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width=itemwidth;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 10);
            if(i%2==1)
                layoutParams.leftMargin = ScreenUtils.getDIP2PX(context, 10);
            ll.setLayoutParams(layoutParams);
            TextView textview=(TextView)ll.getChildAt(1);
            textview.setText(values[i]);
        }
        TextView real_income=(TextView)view.findViewById(R.id.real_income);
        real_income.setText(bean.getTotalProfit());
        if(bean.getTestValue().equalsIgnoreCase("0"))
        {
            real_income.setTextColor(context.getResources().getColor(R.color.green));
        }
        else
        {
            real_income.setTextColor(context.getResources().getColor(R.color.red2));
        }
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        return view;
    }

    public void show(TeamIncomeBean bean)
    {
        if(getChildCount()<1)
        {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
        params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            addView(MakeView(bean), params);
            setBackgroundColor(0x88333333);
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

    public void show_other(TeamIncomeBean bean)
    {
        if(getChildCount()<1)
        {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
            params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            addView(MakeView2(bean), params);
            setBackgroundColor(0x88333333);
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
    public void show_other(PersonalIncomBean bean)
    {
        if(getChildCount()<1)
        {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
            params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            addView(MakeView2(bean), params);
            setBackgroundColor(0x88333333);
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

    public void show(PersonalIncomBean bean)
    {
        if(getChildCount()<1)
        {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
            params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            addView(MakeView(bean), params);
            setBackgroundColor(0x88333333);
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
}
