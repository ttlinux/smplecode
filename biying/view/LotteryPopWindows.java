package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryPlaytypeBean;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.BitmapHandler;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

/**
 * Created by Administrator on 2018/4/4.
 */
public class LotteryPopWindows{


    private MenuBean menuBean;

    private Context context;

    private TextView backtitle;

    private PopupWindow popupWindow;

    private OnSelectListener onselectListenr;

    private TextView RecordTextview;

    Drawable normal, upsidedown;

    private  LinearLayout linearLayout;

    private int DefaultSelect=-1;

    public void setOnselectListenr(OnSelectListener onselectListenr) {
        this.onselectListenr = onselectListenr;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public  LotteryPopWindows(MenuBean menuBean,Context context,TextView backtitle,OnSelectListener onselectListenr)
    {
        this.menuBean=menuBean;
        this.context=context;
        this.backtitle=backtitle;
        this.onselectListenr=onselectListenr;
        normal = context.getResources().getDrawable(R.drawable.triangle);
        upsidedown = new BitmapDrawable(BitmapHandler.upSideDown(context, R.drawable.triangle));
        Initview();
    }

    public void Initview()
    {
        int maxCount=menuBean.getGameList().size()>11?11:menuBean.getGameList().size();
         linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setBackgroundColor(0x996c6c6c);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.triangle), null);
            }
        });
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        ScrollView scrollLayout_right=new ScrollView(context);
        LinearLayout.LayoutParams sr = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        sr.weight=1;
        scrollLayout_right.setLayoutParams(sr);
        scrollLayout_right.setFillViewport(true);
        final GridLayout gl = new GridLayout(context);
        gl.setLayoutParams(new LinearLayout.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));
        gl.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(context, 10));
        gl.setColumnCount(2);
        gl.setOrientation(GridLayout.HORIZONTAL);
        gl.setBackgroundColor(Color.rgb(243, 243, 243));
        scrollLayout_right.addView(gl);

        ScrollView scrollLayout_left=new ScrollView(context);
        scrollLayout_left.setFillViewport(true);
        if(menuBean.getGameList().size()>11)
            scrollLayout_left.setScrollbarFadingEnabled(false);
        LinearLayout.LayoutParams sl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sl.weight=2;
        scrollLayout_left.setLayoutParams(sl);
        RadioGroup linearLayout_left=new RadioGroup(context);
        linearLayout_left.setOrientation(LinearLayout.VERTICAL);
        linearLayout_left.setBackgroundColor(0xFFFFFFFF);
        linearLayout_left.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrollLayout_left.addView(linearLayout_left);

        int maxitemIndex=0;
        int maxsize=0;
        for (int i = 0; i < menuBean.getGameList().size(); i++) {
            MenuBean.GameListBean lbean = menuBean.getGameList().get(i);

            if(maxsize<lbean.getList().size())
            {
                maxsize=lbean.getList().size();
                maxitemIndex=i;
            }
            /*左边的列表*/
            RadioButton title=new RadioButton(context);
            title.setButtonDrawable(context.getResources().getDrawable(android.R.color.transparent));
            title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            title.setGravity(Gravity.CENTER);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            title.setBackgroundColor(Color.rgb(219, 219, 219));
            title.setTextColor(context.getResources().getColor(R.color.gray));
            title.setText(lbean.getTitle());
            title.setPadding(0, ScreenUtils.getDIP2PX(context, 10), 0, ScreenUtils.getDIP2PX(context, 10));
            title.setTag(i);
            title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setBackgroundColor(Color.rgb(243, 243, 243));
                        DefaultSelect=-1;
                        InitGridLayout(gl, (int) buttonView.getTag());
                    } else {
                        buttonView.setBackgroundColor(Color.rgb(219, 219, 219));
                        DefaultSelect=0;
                    }
                }
            });

            ImageView line=new ImageView(context);
            line.setBackgroundColor(context.getResources().getColor(R.color.line));
            line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
            linearLayout_left.addView(title);
            linearLayout_left.addView(line);
             /*左边的列表*/
        }

        linearLayout.addView(scrollLayout_left);
        linearLayout.addView(scrollLayout_right);
        RadioButton radioButton=(RadioButton) linearLayout_left.getChildAt(0);
        radioButton.setChecked(true);
        radioButton.measure(0,0);
        int MeasureHeight= radioButton.getMeasuredHeight();
        int height=MeasureHeight*maxCount+2*maxCount;

        View Rightitem=gl.getChildAt(0);
        Rightitem.measure(0,0);
        MenuBean.GameListBean gamebean=menuBean.getGameList().get(maxitemIndex);
        int rightHeight=(Rightitem.getMeasuredHeight()+ScreenUtils.getDIP2PX(context,10))*(gamebean.getList().size()+1)/2+20;
        LogTools.e("ccc",rightHeight+" "+height);
        height=rightHeight>height?rightHeight:height;
        height=height>MeasureHeight*11+2*11?MeasureHeight*11+2*11:height;

        scrollLayout_left.getLayoutParams().height=height;
        linearLayout_left.getLayoutParams().height=height;

        gl.getLayoutParams().height=height;
        scrollLayout_right.getLayoutParams().height=height;


        popupWindow = new PopupWindow(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, false);
    }

    public void dismiss()
    {
        if(popupWindow!=null)
            popupWindow.dismiss();
    }
    private void InitGridLayout(GridLayout gl,int index)
    {
        gl.removeAllViews();
        RecordTextview=null;
        MenuBean.GameListBean gamebean=menuBean.getGameList().get(index);
        for (int j = 0; j < gamebean.getList().size(); j++) {
            MenuBean.GameListBean.ListBean lbean = gamebean.getList().get(j);
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
            textView.setText(lbean.getGameName());
            textView.setTag(lbean);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setBackgroundResource(R.drawable.item_title);
            textView.setTextColor(0xff666666);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuBean.GameListBean.ListBean bean=(MenuBean.GameListBean.ListBean) v.getTag();
                    v.setBackgroundResource(R.drawable.item_title_selected);
                    ((TextView) v).setTextColor(context.getResources().getColor(R.color.loess4));

                    if (RecordTextview != null) {
                        RecordTextview.setBackgroundResource(R.drawable.item_title);
                         RecordTextview.setTextColor(0xff666666);
                    }
                    RecordTextview=(TextView)v;
                    backtitle.setText(bean.getCurrentGameName());
                    backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);


                    if (onselectListenr != null )
                        onselectListenr.OnSelect(bean);
                    if(DefaultSelect>-1)
                        dismiss();

                }
            });
            gl.addView(textView);
        }

            TextView text = (TextView)gl.getChildAt(DefaultSelect<0?0:DefaultSelect);
            text.performClick();

    }

    public void setItemSelected(MenuBean.GameListBean.ListBean bean)
    {
        LogTools.e("index",bean.getGameListIndex()+" "+bean.getIndex());
        DefaultSelect=bean.getIndex();

        LinearLayout linearLayout_left= (LinearLayout)((ScrollView)linearLayout.getChildAt(0)).getChildAt(0);
        RadioButton radioButton=(RadioButton)linearLayout_left.getChildAt(bean.getGameListIndex()*2);
        if(radioButton.isChecked())
        {
            RecordTextview=null;
            GridLayout gridLayout= (GridLayout)((ScrollView)linearLayout.getChildAt(1)).getChildAt(0);
            TextView text = (TextView)gridLayout.getChildAt(DefaultSelect<0?0:DefaultSelect);
            text.performClick();
        }
        else
        {
            radioButton.setChecked(true);
            RecordTextview=null;
        }


//        GridLayout gridLayout= (GridLayout)linearLayout.getChildAt(1);
//        TextView textView=(TextView)gridLayout.getChildAt(bean.getIndex());
//        textView.performClick();
    }

    public interface OnSelectListener
    {
        public void OnSelect(MenuBean.GameListBean.ListBean bean);
    }
}
