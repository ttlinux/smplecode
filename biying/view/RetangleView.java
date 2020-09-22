package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.R;
import com.lottery.biying.util.LotteryButtonOnCheckListener;
import com.lottery.biying.util.ScreenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class RetangleView extends LotteryButton{

    Context context;
    Boolean isCheck=false;
    LotteryButtonOnCheckListener onCheckListener;
    String value;
    ArrayList<TextView> textViews=new ArrayList<>();
    String GameCode;
    Object objects[];
    JSONObject jsonObject;

    public RetangleView(Context context,String GameCode) {
        super(context);
        initView(context,GameCode);
    }

    public RetangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,GameCode);
    }

    public RetangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,GameCode);
    }

    @Override
    public ButtonTypes getType() {
        return ButtonTypes.Rectangle_HK6;
    }

    @Override
    public Boolean getChecked() {
        return isCheck;
    }

    @Override
    public void setOnCheckListener(LotteryButtonOnCheckListener onCheckListener) {
        this.onCheckListener=onCheckListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if(onCheckListener!=null) {
                    boolean need=onCheckListener.OnChecking(this, getChecked());
                    if(need)
                    {
                        setCheckedOpposite();
                        onCheckListener.OnChecked(this, getChecked());
                    }
                }
                else {
                    setCheckedOpposite();
                }
                break;

        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setChecked(Boolean checked) {
        this.isCheck=checked;
        if(checked)
        {
            setBackground(context.getResources().getDrawable(R.drawable.dice_radiu_background));
        }
        else
        {
            setBackground(null);
        }
    }

    @Override
    public void setCheckedOpposite() {
        setChecked(!isCheck);
    }

    @Override
    public void fillUpData(Object... obj) {
        objects=obj;


        if(obj.length>2 && jsonObject!=null)
        {
            try {
                JSONObject json=jsonObject.getJSONObject((String)obj[2]);
                String numbers=json.optString("formatstr");
                if(TextUtils.isEmpty(numbers))
                {
                    textViews.get(1).setPadding(0,0,0,ScreenUtils.getDIP2PX(context,5));
                    textViews.get(2).setVisibility(GONE);
                }
                else
                {
                    textViews.get(1).setPadding(0,0,0,0);
                    textViews.get(2).setText(numbers);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                textViews.get(1).setPadding(0,0,0,ScreenUtils.getDIP2PX(context,5));
                textViews.get(2).setVisibility(GONE);
            }
        }
        else
        {
            textViews.get(1).setPadding(0,0,0,ScreenUtils.getDIP2PX(context,5));
            textViews.get(2).setVisibility(GONE);
        }
        textViews.get(0).setText((String)obj[0]);
        textViews.get(1).setText((String)obj[1]);

    }

    public void readRaw(){
        try {
            //获取文件中的内容
            InputStream inputStream=getResources().openRawResource(R.raw.hk6_numberlist);
            //将文件中的字节转换为字符
            InputStreamReader isReader=new InputStreamReader(inputStream,"UTF-8");
            //使用bufferReader去读取字符
            BufferedReader reader=new BufferedReader(isReader);
            String out="";
            StringBuilder sb=new StringBuilder();
            try {
                while((out=reader.readLine())!=null){
                    sb.append(out);
                }
                jsonObject=new JSONObject(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getText() {
        return value;
    }

    @Override
    public void setText(String value) {
        this.value=value;
    }

    @Override
    public Object[] getData() {
        return objects;
    }

    @Override
    public View getCurrentView() {
        return this;
    }


    private void initView(Context context,String GameCode)
    {
        this.context=context;
        this.GameCode=GameCode;
        RelativeLayout view=(RelativeLayout)View.inflate(context, R.layout.item_retangle_btn,null);
        LinearLayout linearLayout=(LinearLayout)view.getChildAt(0);
        for (int i = 0; i <linearLayout.getChildCount() ; i++) {
            textViews.add((TextView) linearLayout.getChildAt(i));
        }

        readRaw();
        LotteryButton.LayoutParams ll=new LotteryButton.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view,ll);
    }
}
