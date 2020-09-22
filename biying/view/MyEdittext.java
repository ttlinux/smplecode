package com.lottery.biying.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/18.
 */
public class MyEdittext extends EditText{

    private OnafterTextChangedListener onafterTextChangedListener;
    public boolean hasFocus=false;

    public OnafterTextChangedListener getOnafterTextChangedListener() {
        return onafterTextChangedListener;
    }

    public void setOnafterTextChangedListener(OnafterTextChangedListener onafterTextChangedListener) {
        this.onafterTextChangedListener = onafterTextChangedListener;
    }

    public MyEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    public MyEdittext(Context context) {
        super(context);
        initview();
    }

    public MyEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
    }

    private void initview()
    {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                MyEdittext.this.hasFocus=hasFocus;
            }
        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isFocused() && onafterTextChangedListener!=null)
                {
                    onafterTextChangedListener.OnAfterTextChanged(s.toString(), MyEdittext.this);
                }
            }
        });
    }

    public interface OnafterTextChangedListener
    {
        public void OnAfterTextChanged(String text, MyEdittext myEdittext);
    }
}
