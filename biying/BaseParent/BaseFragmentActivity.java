package com.lottery.biying.BaseParent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.OnMultiClickListener;
import com.lottery.biying.util.TimeMethod;


/**
 * Created by Administrator on 2017/3/28.
 */
public class BaseFragmentActivity extends FragmentActivity {

    private boolean isInFront=true;
    public boolean isneedback() {
        return isneedback;
    }

    public void setIsneedback(boolean isneedback) {
        this.isneedback = isneedback;
    }

    boolean isneedback=true;

    public boolean isInFront() {
        return isInFront;
    }

    public void setIsInFront(boolean isInFront) {
        this.isInFront = isInFront;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
            getWindow().setStatusBarColor(0xFF000000);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInFront=false;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        View view=FindView(R.id.back);

        if(view!=null && isneedback)
        {
            view.setVisibility(View.VISIBLE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public <T extends View> T FindView(int id)
    {
        return (T)findViewById(id);
    }
}
