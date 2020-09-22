package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/15.
 */
public class CustomPopwindow {

    private PopupWindow popupWindow;
    private TextView RecordTextview;
    private TextView backtitle;
    Drawable normal;
    private OnSelectListener onselectListenr;

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setOnselectListenr(OnSelectListener onselectListenr) {
        this.onselectListenr = onselectListenr;
    }

    public CustomPopwindow(Context context,TextView backtitle,String[] titles)
    {
        normal = context.getResources().getDrawable(R.drawable.triangle);

        InitGridLayout(context,backtitle,titles);
    }

    private void InitGridLayout(final Context context,final TextView backtitle, final String[] titles)
    {
        final LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null)
                {
                    popupWindow.dismiss();
                    backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                }


            }
        });


        int padding=ScreenUtils.getDIP2PX(context,15);
        for (int i = 0; i <titles.length ; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(titles[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textView.setTextColor(0xff666666);
            textView.setPadding(0, padding, 0, padding);
            textView.setBackground(new ColorDrawable(0xFFFFFFFF));
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (RecordTextview != null) {
                        RecordTextview.setTextColor(0xff666666);
                    }
                    ((TextView) v).setTextColor(context.getResources().getColor(R.color.loess4));
                    RecordTextview = (TextView) v;
                    int tag = (int) RecordTextview.getTag();
                    backtitle.setText(titles[tag]);
                    backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);


                    if (onselectListenr != null)
                        onselectListenr.OnSelect(tag);
                    popupWindow.dismiss();
                }
            });
            linearLayout.addView(textView);

            ImageView imageView=new ImageView(context);
            imageView.setBackgroundColor(context.getResources().getColor(R.color.line));
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
            linearLayout.addView(imageView);
        }

        if(RecordTextview==null)
        {
            RecordTextview=(TextView)linearLayout.getChildAt(0);
        }
        RecordTextview.setTextColor(context.getResources().getColor(R.color.loess4));
        popupWindow = new PopupWindow(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, false);
    }

    public interface OnSelectListener
    {
        public void OnSelect(int index);
    }

}
