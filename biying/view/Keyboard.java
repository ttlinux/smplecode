package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/20.
 */
public class Keyboard extends RelativeLayout implements View.OnClickListener{


    WindowManager windowManager;
    Context context;
    TextView title,back;
    ImageView triangle;
    String FormatText;
    RelativeLayout confirm,delete;
    TextView multiply_Record;
    ArrayList<TextView> Multiplys=new ArrayList<>();
    ArrayList<TextView> numbers=new ArrayList<>();
    int multiply_nums[]={10,50,100,250,500};
    int mode;//单色：1倍 双色：1注1倍1期 共2元
    double unit;
    int zhu;//注数
    private int MaxLimint=99999,times;
    OnDismissListener ondismiss;
    int realTimes=0;


    public void setzhu(int zhu) {
        this.zhu = zhu;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Keyboard(Context context,int mode) {
        super(context);
        this.mode=mode;
        Initview(context);
    }

    public Keyboard(Context context) {
        super(context);
        Initview(context);
    }

    public Keyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initview(context);
    }

    public Keyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Initview(context);
    }

    public void setTitle(String formatText,double unit,int times)
    {
        this.unit=unit;
        this.FormatText=formatText;
        this.times=times;
        OnAction();
    }

    private void Initview(final Context context)
    {
        this.context=context;
        LinearLayout linearLayout=(LinearLayout)View.inflate(context, R.layout.view_keyboard,null);
        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        this.addView(linearLayout, rl);
        setBackgroundColor(0x99414247);

        title=(TextView)findViewById(R.id.title);
        triangle=(ImageView)findViewById(R.id.triangle);
        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(this);
        triangle.setOnClickListener(this);

        confirm=(RelativeLayout)findViewById(R.id.confirm);
        confirm.setEnabled(false);
        delete=(RelativeLayout)findViewById(R.id.delete);
        confirm.setOnClickListener(this);
        delete.setOnClickListener(this);

        LinearLayout times_view=(LinearLayout)findViewById(R.id.times_view);
        for (int i = 0; i <times_view.getChildCount()-1; i++) {
            TextView multiply=(TextView)times_view.getChildAt(i);
            multiply.setTag(i);
            Multiplys.add(multiply);
            multiply.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //倍数按钮
                    ((TextView)v).setTextColor(0xffffffff);
                    v.setBackground(context.getResources().getDrawable(R.drawable.radiu_loss4_rangle_nostoke3));
                    if(multiply_Record!=null)
                    {
                        multiply_Record.setTextColor(0xFF000000);
                        multiply_Record.setBackground(context.getResources().getDrawable(R.drawable.radiu_white_rangle_nostoke3));
                    }
                    multiply_Record=(TextView)v;
                    times=multiply_nums[(int)v.getTag()];
                    realTimes=times;
                    OnAction();
                }
            });
        }

        LinearLayout keys=(LinearLayout)findViewById(R.id.keys);
        for (int i = 0; i <keys.getChildCount() ; i++) {
            if(i%2!=0)continue;
            LinearLayout ll=(LinearLayout)keys.getChildAt(i);
            for (int j = 0; j < ll.getChildCount(); j++) {
                if(ll.getChildAt(j) instanceof TextView)
                {
                    if(ll.getChildAt(j).getId()!=R.id.back)
                    {
                        TextView number=(TextView)ll.getChildAt(j);
                        numbers.add(number);
                        number.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //数字按钮
                                if(multiply_Record!=null)
                                {
                                    multiply_Record.setTextColor(0xFF000000);
                                    multiply_Record.setBackground(context.getResources().getDrawable(R.drawable.radiu_white_rangle_nostoke3));
                                }
                                int number=Integer.valueOf(((TextView)v).getText().toString());
                                if(realTimes==0  && number>1)
                                {
                                    realTimes=number;
                                    times=number;
                                    OnAction();
                                    return;
                                }
                                if(realTimes==0 && number<1)
                                {
                                    realTimes=0;
                                    times=1;
                                    return;
                                }
                                if(realTimes==0 && number==1)
                                {
                                    realTimes=1;
                                    times=1;
                                }
                                else
                                {
                                    int expect=times*10+number;
                                    if(expect<=MaxLimint)
                                    {
                                        realTimes=expect;
                                        times=expect;
                                        OnAction();
                                    }
                                }

                            }
                        });
                    }

                }

            }
        }
    }

    public void show()
    {
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.triangle:
                hide();
                break;
            case R.id.back:
                hide();
                break;
            case R.id.confirm:
                hide();
                if(ondismiss!=null)
                    ondismiss.Ondismiss(times,unit);
                break;
            case R.id.delete:
                if(multiply_Record!=null)
                {
                    multiply_Record.setTextColor(0xFF000000);
                    multiply_Record.setBackground(context.getResources().getDrawable(R.drawable.radiu_white_rangle_nostoke3));
                }

                if(times/10>0)
                {
                    times=times/10;
                    realTimes=times;
                }
                else
                {
                    times=1;
                    realTimes=0;
                }
                OnAction();
                break;
        }
    }

    private void OnAction()
    {

       String values;
        switch (mode)
        {
            case 0:
                values= String.format(FormatText, times);
                title.setText(values);
                break;
            case 1:
                values= String.format(FormatText, times,getPrettyNumber(times*unit*zhu));
                SpannableStringBuilder builder = new SpannableStringBuilder(values);
                builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.loess4)), values.indexOf("共"),values.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                title.setText(builder);
                break;
        }
        if(realTimes>0)
        {
            //88FF7701
            confirm.setEnabled(true);
            confirm.setBackgroundColor(0xffFF7701);
        }
        else
        {
            confirm.setEnabled(false);
            confirm.setBackgroundColor(0x88FF7701);
        }
    }

    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

    public static String getPrettyNumber(double number) {
        return String.format("%.2f", number);
    }

    public OnDismissListener getOndismiss() {
        return ondismiss;
    }

    public void setOndismiss(OnDismissListener ondismiss) {
        this.ondismiss = ondismiss;
    }

    public interface OnDismissListener
    {
        public void Ondismiss(int value,double unit);
    }
}
