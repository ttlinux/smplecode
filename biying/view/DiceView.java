package com.lottery.biying.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/4/21.
 */
public class DiceView extends RelativeLayout{

    public static final int Char=0;
    public static final int Dice=1;
    private int mode=Char;
    private String arg[];//显示的字或者骰子
    private Context mcontext;
    boolean checked=false;
    TextView contentview;
    String value;
    OnCheckedListener onCheckedListener;

    private int Bigdice_pic[]={R.drawable.big1,R.drawable.big2,R.drawable.big3,R.drawable.big4,R.drawable.big5,R.drawable.big6};
    private int Smalldice_pic[]={R.drawable.small1,R.drawable.small2,R.drawable.small3,R.drawable.small4,R.drawable.small5,R.drawable.small6};

    public boolean getCheck()
    {
        return checked;
    }

    public void setModeAndArgs(int mode,String...arg) {
        this.mode = mode;
        this.arg = arg;
        AddChildview();
    }

    public OnCheckedListener getOnCheckedListener() {
        return onCheckedListener;
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setContent(String text)
    {
        if(contentview!=null)
        {
            contentview.setText(text);
        }
    }

    public DiceView(Context context) {
        super(context);
        InitView(context);
    }

    public DiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }

    public DiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView(context);
    }

    public void InitView(Context context)
    {
        int padding= ScreenUtils.getDIP2PX(context,5);
        setPadding(padding, padding, padding, padding);


        mcontext=context;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checked=!checked;
                setCheck(checked);
                if(onCheckedListener!=null)
                    onCheckedListener.onChecked(checked,DiceView.this);
            }
        });
    }

    public void setCheck(boolean checked)
    {
        this.checked=checked;
        if(checked)
        {
            setBackground(mcontext.getResources().getDrawable(R.drawable.dice_radiu_background));
        }
        else
        {
            setBackground(null);
        }
    }


    private void AddChildview()
    {

        if(arg==null || arg.length==0)return;
        LinearLayout linearLayout=new LinearLayout(mcontext);
        linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        switch (mode)
        {
            case 0:
                if(arg.length>1 )return;
                boolean isnum=Character.isDigit(arg[0].charAt(0));
                if(isnum)
                {
                    View view=View.inflate(mcontext,R.layout.k3_button_item_layout2,null);
                    linearLayout.addView(view);
                    TextView title=(TextView)view.findViewById(R.id.title);
                    title.setText(arg[0]);
                }
                else
                {
                    View view=View.inflate(mcontext,R.layout.k3_button_item_layout,null);
                    linearLayout.addView(view);
                    contentview=(TextView)view.findViewById(R.id.content);
                    TextView title=(TextView)view.findViewById(R.id.title);
                    title.setText(arg[0]);
                }
                break;
            case 1:
                if(arg.length>1)
                {
                    //多个骰子 小图
                    for (int i = 0; i <arg.length ; i++) {
                        int index=Integer.valueOf(arg[i])-1;
                        ImageView imageView=new ImageView(mcontext);
                        imageView.setAdjustViewBounds(true);
                        imageView.setImageDrawable(mcontext.getResources().getDrawable(Smalldice_pic[index]));
                        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        ll.leftMargin=ScreenUtils.getDIP2PX(mcontext,5);
                        imageView.setLayoutParams(ll);
                        linearLayout.addView(imageView);
                    }
                }
                else
                {
                    //一个骰子 大图
                    int index=Integer.valueOf(arg[0])-1;
                    ImageView imageView=new ImageView(mcontext);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageDrawable(mcontext.getResources().getDrawable(Bigdice_pic[index]));
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayout.addView(imageView);
                }
                break;
        }

        addView(linearLayout);
    }

    public interface OnCheckedListener
    {
        public void onChecked(boolean checked,DiceView view);
    }
}
