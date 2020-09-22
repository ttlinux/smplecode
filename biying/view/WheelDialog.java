package com.lottery.biying.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import com.lottery.biying.R;

import org.json.JSONArray;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2017/4/10.
 */
public class WheelDialog extends Dialog{

    Context context;

    public void setListener(OnChangeListener listener) {
        this.listener = listener;
    }

    OnChangeListener listener;

    public String[] getStrs() {
        return strs;
    }

    public void setStrs(String[] strs) {
        this.strs = strs;
    }

    public void setStrs(JSONArray strs) {
        this.strs=new String[strs.length()];
        for (int i = 0; i <strs.length(); i++) {
            this.strs[i]=strs.optJSONObject(i).optString("name","");
        }
    }

    String strs[];

    public WheelDialog(Context context) {
        super(context);
        InitView(context);
    }

    protected WheelDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        InitView(context);
    }

    public WheelDialog(Context context, int theme) {
        super(context, theme);
        InitView(context);
    }

    private void InitView(Context context)
    {
        this.context=context;
        getContext().setTheme(R.style.loading_dialog);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        RelativeLayout layout=new RelativeLayout(context);
        layout.setBackground(new ColorDrawable(0x99b8b8b8));
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        WheelView wheelView=new WheelView(context);
        RelativeLayout.LayoutParams layoutParams1= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        wheelView.setLayoutParams(layoutParams1);
        wheelView.setVisibleItems(4);
        wheelView.SetNoBackgound();
//        wheelView.setCyclic(true);
        wheelView.setViewAdapter(new ArrayWheelAdapter<String>(context, strs));
//        wheelView.addScrollingListener(new OnWheelScrollListener() {
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//
//            }
//        });
        wheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                LogTools.e("eeee",oldValue+" "+newValue);
                if(listener!=null)
                {
                    listener.onChanged(wheel,oldValue,newValue);
                }
            }
        });
        layout.addView(wheelView);

        setContentView(layout);
    }

    public interface OnChangeListener
    {
        public void onChanged(WheelView wheel, int oldValue, int newValue);
    }
}
