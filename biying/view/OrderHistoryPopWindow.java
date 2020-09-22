package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/28.
 */
public class OrderHistoryPopWindow {

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

    public OrderHistoryPopWindow(Context context,TextView backtitle,String LotteryCode)
    {
        normal = context.getResources().getDrawable(R.drawable.triangle);

        InitGridLayout(context,backtitle,LotteryCode);
    }

    private void InitGridLayout(final Context context,final TextView backtitle,String LotteryCode)
    {
        final GridLayout gl = new GridLayout(context);
        gl.setLayoutParams(new LinearLayout.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));
        gl.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(context, 10));
        gl.setColumnCount(3);
        gl.setOrientation(GridLayout.HORIZONTAL);
        gl.setBackgroundColor(Color.rgb(243, 243, 243));

        gl.removeAllViews();


        SparseArray<MenuBean> menuBeans=new SparseArray<>();
        MenuBean nmbean=new MenuBean();
        nmbean.setMenuName("所有彩种");
        menuBeans.put(0,nmbean);
        HashMap<String, MenuBean> hashMaps=RepluginMethod.getApplication(context).getMenuBeans();
        Iterator<Map.Entry<String,MenuBean>> its=hashMaps.entrySet().iterator();
        while (its.hasNext())
        {
            MenuBean mbean=(MenuBean)its.next().getValue();
            menuBeans.put(mbean.getIndex()+1,mbean);
        }


        for (int i = 0; i <menuBeans.size() ; i++) {
            TextView textView = new TextView(context);
            //使用Spec定义子控件的位置和比重
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = (ScreenUtils.getScreenWH((Activity)context)[0] - ScreenUtils.getDIP2PX(context, 10) * 4) / 3;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 10);
            layoutParams.leftMargin = ScreenUtils.getDIP2PX(context, 10);

            textView.setLayoutParams(layoutParams);
            textView.setText(menuBeans.get(i).getMenuName());
            textView.setTag(menuBeans.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setBackgroundResource(R.drawable.item_title);
            textView.setTextColor(0xff666666);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuBean bean = (MenuBean) v.getTag();
                    v.setBackgroundResource(R.drawable.item_title_selected);
                    ((TextView) v).setTextColor(context.getResources().getColor(R.color.loess4));

                    if (RecordTextview != null) {
                        RecordTextview.setBackgroundResource(R.drawable.item_title);
                        RecordTextview.setTextColor(0xff666666);
                    }
                    RecordTextview = (TextView) v;
                    backtitle.setText(bean.getMenuName());
                    backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);


                    if (onselectListenr != null)
                        onselectListenr.OnSelect(bean);
                }
            });
            if(LotteryCode!=null && LotteryCode.length()>0 && LotteryCode.equalsIgnoreCase(menuBeans.get(i).getMenuCode()))
            {
                RecordTextview=textView;
            }
            gl.addView(textView);
        }

        if(RecordTextview==null)
        {
            RecordTextview=(TextView)gl.getChildAt(0);

        }
        RecordTextview.setBackgroundResource(R.drawable.item_title_selected);
        RecordTextview.setTextColor(context.getResources().getColor(R.color.loess4));
        popupWindow = new PopupWindow(gl, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, false);
    }

    public interface OnSelectListener
    {
        public void OnSelect(MenuBean bean);
    }



}
