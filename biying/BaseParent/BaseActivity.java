package com.lottery.biying.BaseParent;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.OnMultiClickListener;
import com.lottery.biying.util.TimeMethod;
import com.lottery.biying.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2017/3/28.
 */
public class BaseActivity extends Activity{


    public boolean isneedback() {
        return isneedback;
    }

    public void setIsneedback(boolean isneedback) {
        this.isneedback = isneedback;
    }

    boolean isneedback=true;

    View.OnClickListener clickListener;

    private boolean isInFront=true;

    public void setBackButtonListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
            getWindow().setStatusBarColor(0xFF000000);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public boolean isInFront() {
        return isInFront;
    }

    public void setIsInFront(boolean isInFront) {
        this.isInFront = isInFront;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInFront=false;
        TimeMethod.End();
    }

    public void setActivityTitle(String title)
    {
        TextView textView=FindView(R.id.title);
        if(textView!=null)
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText(title);
        }
    }

    public void setBackTitleClickFinish()
    {
        TextView textView=FindView(R.id.backtitle);
        if(textView!=null)
        {
            textView.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    finish();
                }
            });
        }
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        View view=FindView(R.id.back);

        if(view!=null && isneedback)
        {
            view.setVisibility(View.VISIBLE);
            if(clickListener!=null)
                view.setOnClickListener(clickListener);
            else
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        }
//        View backtitle=FindView(R.id.backtitle);
//        if(backtitle!=null)
//        {
//            backtitle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        }
        isInFront=true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public <T> T FindView(int id)
    {
        return (T)findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isInFront=false;
    }

    public void Notification(Object obj)
    {

    }
}
