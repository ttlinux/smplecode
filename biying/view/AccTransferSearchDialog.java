package com.lottery.biying.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer.LotteryAccountTracsferListActivity2;
import com.lottery.biying.R;
import com.lottery.biying.ThirdParty.cn.aigestudio.datepicker.cons.DPMode;
import com.lottery.biying.ThirdParty.cn.aigestudio.datepicker.views.DatePicker;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/5/2.
 */
public class AccTransferSearchDialog extends RelativeLayout implements View.OnClickListener{

    Context context;
    WindowManager windowManager;
    TextView RecordTextview,RecordTextview2,starttime,endtime;
    RelativeLayout relayout,includes;
    DatePicker datepicker;
    String currentdate="";
    ImageView image;
    EditText username_ed;
    RelativeLayout searchlayout;
    int selectedtype;

    public AccTransferSearchDialog(Context context,int selectedtype) {
        super(context);
        this.context=context;
        this.selectedtype=selectedtype;

    }

    public AccTransferSearchDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public AccTransferSearchDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public View MakeView()
    {
        View view=View.inflate(context, R.layout.dialog_account_transfer_search,null);
        relayout=(RelativeLayout)view.findViewById(R.id.relayout);
        includes=(RelativeLayout)view.findViewById(R.id.includes);
        includes.setTag(false);
        image=(ImageView)view.findViewById(R.id.image);

        searchlayout=(RelativeLayout)view.findViewById(R.id.searchlayout);
        if(RepluginMethod.getApplication(context).isgent().equalsIgnoreCase("1"))
        {
            searchlayout.setVisibility(VISIBLE);
        }
        else
        {
            searchlayout.setVisibility(GONE);
        }
        username_ed=(EditText)view.findViewById(R.id.username_ed);
        username_ed.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int dstart, int dend) {
                        if(charSequence.length()==0)return null;
                        String regex = "^[\u4E00-\u9FA5]+$";
                        boolean isChinese = Pattern.matches(regex, charSequence.toString());
                        if (!Character.isLetterOrDigit(charSequence.charAt(start)) || isChinese) {
                            return "";
                        }
                        return null;
                    }
                }, new InputFilter.LengthFilter(16)
        });
        includes.setOnClickListener(this);
        relayout.setOnClickListener(this);
        relayout.setBackgroundColor(0x99999999);
        TextView confirm=(TextView)view.findViewById(R.id.confirm);
        starttime=(TextView)view.findViewById(R.id.starttime);
        endtime=(TextView)view.findViewById(R.id.endtime);
        starttime.setOnClickListener(this);
        endtime.setOnClickListener(this);
        String timestr= TimeUtils.Gettime();
        starttime.setText(timestr);
        endtime.setText(timestr);

        confirm.setOnClickListener(this);
        GridLayout gl=(GridLayout)view.findViewById(R.id.gridview);
        gl.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(context, 10));
        gl.setColumnCount(3);
        gl.setOrientation(GridLayout.HORIZONTAL);

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
        for (int i = 0; i < menuBeans.size(); i++) {
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
            if(menuBeans.get(i).getMenuCode()!=null)
            textView.setTag(menuBeans.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textView.setBackgroundResource(R.drawable.item_title);
            textView.setTextColor(context.getResources().getColor(R.color.gray20));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuBean bean=(MenuBean) v.getTag();
                    v.setBackgroundResource(R.drawable.item_title_selected);
                    ((TextView) v).setTextColor(context.getResources().getColor(R.color.loess4));

                    if (RecordTextview != null) {
                        RecordTextview.setBackgroundResource(R.drawable.item_title);
                        RecordTextview.setTextColor(context.getResources().getColor(R.color.gray20));
                    }
                    RecordTextview=(TextView)v;

                }
            });
            gl.addView(textView);
        }
//        RecordTextview=(TextView)gl.getChildAt(0);

        String array[]=context.getResources().getStringArray(R.array.accchange_types);
        GridLayout gl2=(GridLayout)view.findViewById(R.id.gridview2);
        gl2.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(context, 10));
        gl2.setColumnCount(3);
        gl2.setOrientation(GridLayout.HORIZONTAL);

        for (int i = 0; i <array.length ; i++) {
            TextView textView = new TextView(context);
            //使用Spec定义子控件的位置和比重
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = (ScreenUtils.getScreenWH((Activity)context)[0] - ScreenUtils.getDIP2PX(context, 10) * 4) / 3;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 10);
            layoutParams.leftMargin = ScreenUtils.getDIP2PX(context, 10);

            textView.setTag(i);
            textView.setLayoutParams(layoutParams);
            textView.setText(array[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textView.setBackgroundResource(R.drawable.item_title);
            textView.setTextColor(context.getResources().getColor(R.color.gray20));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundResource(R.drawable.item_title_selected);
                    ((TextView) v).setTextColor(context.getResources().getColor(R.color.loess4));

                    if (RecordTextview2 != null) {
                        RecordTextview2.setBackgroundResource(R.drawable.item_title);
                        RecordTextview2.setTextColor(context.getResources().getColor(R.color.gray20));
                    }
                    RecordTextview2=(TextView)v;

                }
            });
            gl2.addView(textView);
        }
//        RecordTextview2=(TextView)gl2.getChildAt(0);

        return view;
    }

    public void showDropdown(View view)
    {
        if(RepluginMethod.getApplication(context).getMenuBeans().size()<1)
        {
            ToastUtil.showMessage(context,"获取彩种失败");
            return;
        }

        if(getChildCount()<1)
        {
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
//        params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            params.topMargin=view.getTop() +view.getHeight();
            LogTools.e("showDropdown", view.getTop() + view.getHeight() + "  " + view.getLeft());
            addView(MakeView(), params);

            TranslateAnimation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, -1f,
                    Animation.RELATIVE_TO_SELF, 0f);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(animation);
            animationSet.setFillBefore(true);
            animationSet.setFillAfter(true);
            animationSet.setDuration(200);
            getChildAt(0).startAnimation(animationSet);
        }
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
            case R.id.includes:
                if(includes.getTag()==null)
                {
                    includes.setTag(false);
                    image.setBackground(context.getResources().getDrawable(R.drawable.gou_black_normal));
                }
                else
                {
                    boolean ischeck=(boolean)includes.getTag();
                    includes.setTag(!ischeck);
                    if(!ischeck)
                    {
                        image.setBackground(context.getResources().getDrawable(R.drawable.gou_black_selected));
                    }
                    else
                    {
                        image.setBackground(context.getResources().getDrawable(R.drawable.gou_black_normal));
                    }
                }
                break;
            case R.id.confirm:
                if(RepluginMethod.getApplication(context).isgent().equalsIgnoreCase("1"))
                {
                    if(username_ed.getText().toString().length()>0 &&
                            username_ed.getText().toString().length()<4)
                    {
                        ToastUtil.showMessage(context,"请输入4-16位数字/字母或组合的帐号");
                        return;
                    }
                }
                else
                {
                    username_ed.setText(RepluginMethod.getApplication(context).getBaseapplicationUsername());//普通用户的话赋值 号传
                }

                hide();
                Intent intent=new Intent();
                intent.setClass(context, LotteryAccountTracsferListActivity2.class);
                intent.putExtra(BundleTag.Incluesubordinate, (boolean) includes.getTag() ? 1 : 0);
                intent.putExtra(BundleTag.Username,username_ed.getText().toString());
                intent.putExtra(BundleTag.Type,selectedtype);
                if(RecordTextview!=null && RecordTextview.getTag()!=null)
                intent.putExtra(BundleTag.LotteryCode,((MenuBean)RecordTextview.getTag()).getMenuCode());
                if(RecordTextview2!=null)
                intent.putExtra(BundleTag.AccType,RecordTextview2.getTag()+"");
                intent.putExtra(BundleTag.StartTime,starttime.getText().toString()+" 00:00:00");
                intent.putExtra(BundleTag.EndTime,endtime.getText().toString()+" "+TimeUtils.Gettime2());
                context.startActivity(intent);
                break;
            case R.id.starttime:
                relayout.setVisibility(VISIBLE);
                ShowDatePicker((TextView)v);
                break;
            case R.id.endtime:
                relayout.setVisibility(VISIBLE);
                ShowDatePicker((TextView)v);
                break;
            case R.id.relayout:
                relayout.setVisibility(GONE);
                relayout.removeAllViews();
                break;
        }
    }

    public void ShowDatePicker(final TextView mtextview)
    {
//        List<String> tmpTL = new ArrayList<>();
//        tmpTL.add("2015-10-5");
//        tmpTL.add("2015-10-6");
//        tmpTL.add("2015-10-7");
//        tmpTL.add("2015-10-8");
//        tmpTL.add("2015-10-9");
//        tmpTL.add("2015-10-10");
//        tmpTL.add("2015-10-11");
//        DPCManager.getInstance().setDecorTL(tmpTL);
//
//        List<String> tmpTR = new ArrayList<>();
//        tmpTR.add("2015-10-10");
//        tmpTR.add("2015-10-11");
//        tmpTR.add("2015-10-12");
//        tmpTR.add("2015-10-13");
//        tmpTR.add("2015-10-14");
//        tmpTR.add("2015-10-15");
//        tmpTR.add("2015-10-16");
//        DPCManager.getInstance().setDecorTR(tmpTR);
        datepicker=new DatePicker(context);
        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        rl.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        datepicker.setLayoutParams(rl);
        datepicker.setId(0x12341234);
        relayout.addView(datepicker);

        TextView textview=new TextView(context);
        RelativeLayout.LayoutParams rl2=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl2.addRule(RelativeLayout.BELOW, 0x12341234);
        textview.setLayoutParams(rl2);
        textview.setText(context.getString(R.string.confirm));
        textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textview.setTextColor(0xFFFFFFFF);
        textview.setBackgroundColor(0xFFF37B7A);
        int padding=ScreenUtils.getDIP2PX(context,15);
        textview.setPadding(padding, padding, padding, padding);
        textview.setGravity(Gravity.CENTER);
        textview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mtextview.getId())
                {
                    case R.id.starttime:
                        int state=TimeUtils.compare_date(currentdate,endtime.getText().toString());
                        if(state==1)
                        {
                            ToastUtil.showMessage(context,"起始时间必须早于结束时间");
                            return;
                        }
                        break;
                    case R.id.endtime:
                        int state2=TimeUtils.compare_date(starttime.getText().toString(),currentdate);
                        if(state2==1)
                        {
                            ToastUtil.showMessage(context,"起始时间必须早于结束时间");
                            return;
                        }
                        break;
                }
                mtextview.setText(currentdate);
                relayout.setVisibility(GONE);
                relayout.removeAllViews();
            }
        });
        relayout.addView(textview);

        Calendar cal=Calendar.getInstance();
        currentdate=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.DATE);
        datepicker.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
        datepicker.setFestivalDisplay(true);
        datepicker.setTodayDisplay(true);
        datepicker.setHolidayDisplay(true);
        datepicker.setDeferredDisplay(false);
        datepicker.setMode(DPMode.SINGLE);
        datepicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String time,String year,String month,String date) {
                currentdate=time;
            }
        });
//        TranslateAnimation animation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, -1f,
//                Animation.RELATIVE_TO_SELF, 0f);
//
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(animation);
//        animationSet.setFillBefore(true);
//        animationSet.setFillAfter(true);
//        animationSet.setDuration(500);
//        relayout.startAnimation(animation);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            hide();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
