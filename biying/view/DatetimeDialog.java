package com.lottery.biying.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;


import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/1.
 */
public class DatetimeDialog extends Dialog {


    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    OnSelectDialogListener dialog;
    OnSelectDialogListener2 dialog2;
    TextView textView;
    DatePicker datePicker;
    TimePicker timePicker;

    boolean ShowtimePicker = false;

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    String arg;

    public DatetimeDialog(Context context, OnSelectDialogListener dialog) {
        super(context);
        this.dialog = dialog;
        InitView(context);
    }

    public DatetimeDialog(Context context, OnSelectDialogListener2 dialog, boolean ShowtimePicker) {
        super(context);
        this.dialog2 = dialog;
        this.ShowtimePicker = ShowtimePicker;
        InitView(context);
    }

    protected DatetimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        InitView(context);
    }

    public DatetimeDialog(Context context, int theme, OnSelectDialogListener dialog) {
        super(context, theme);
        this.dialog = dialog;
        InitView(context);
    }


    private void InitView(Context context) {
//        Window window = getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setGravity(Gravity.CENTER);
//        window.setAttributes(lp);

        getContext().setTheme(R.style.loading_dialog);
//        View view = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_loading_view);// 加载布局.

        LinearLayout layout = new LinearLayout(context);
        layout.setBackground(new ColorDrawable(0x99b8b8b8));
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 200);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        textView.setTextColor(0xFF000000);
        layout.addView(textView);

        datePicker = new DatePicker(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.gravity = Gravity.CENTER;
        layoutParams2.topMargin = ScreenUtils.getDIP2PX(context, 20);
        datePicker.setLayoutParams(layoutParams2);
        layout.addView(datePicker);

        if (ShowtimePicker) {
            timePicker = new TimePicker(context);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams3.gravity = Gravity.CENTER;
            layoutParams3.topMargin = ScreenUtils.getDIP2PX(context, 20);
            timePicker.setLayoutParams(layoutParams2);
            layout.addView(timePicker);
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        // 获取当前的年、月、日、小时、分钟
        if (arg != null && !arg.equalsIgnoreCase("") && arg.contains("-")) {
//            String args[]=arg.split("-");
//            year = Integer.valueOf(args[0]);
//            month = Integer.valueOf(args[1])-1;
//            day = Integer.valueOf(args[2]);
            Date date = ShowtimePicker?getStringtime(arg):getStringtime2(arg);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            second = c.get(Calendar.SECOND);
        } else {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            second = c.get(Calendar.SECOND);
        }

        if (ShowtimePicker)
            textView.setText(year + "年" + (month + 1) + "月" + day + "日" + hour + "时" + minute + "分");
        else
            textView.setText(year + "年" + (month + 1) + "月" + day + "日");
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {


            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (ShowtimePicker)
                    textView.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日" + hour + "时" + minute + "分");
                else
                    textView.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                if (dialog != null) {
                    dialog.OnselectDate(year, monthOfYear, dayOfMonth);
                }
            }
        });

        if (timePicker != null) {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
            timePicker.setIs24HourView(true);
        }

//        setTitle("日期为：" + year + "年" + month + "月" + day + "日");
        setCancelable(true); // 是否可以按“返回键”消失
        setCanceledOnTouchOutside(true); // 点击加载框以外的区域
        setContentView(layout);// 设置布局


    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        if (arg != null && !arg.equalsIgnoreCase("") && arg.contains("-")) {
            //            String args[]=arg.split("-");
//            year = Integer.valueOf(args[0]);
//            month = Integer.valueOf(args[1])-1;
//            day = Integer.valueOf(args[2]);

            Date date = ShowtimePicker?getStringtime(arg):getStringtime2(arg);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            second = c.get(Calendar.SECOND);
        } else {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            second = c.get(Calendar.SECOND);
        }
        if (ShowtimePicker)
            textView.setText(year + "年" + (month + 1) + "月" + day + "日" + hour + "时" + minute + "分");
        else
            textView.setText(year + "年" + (month + 1) + "月" + day + "日");
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                textView.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                if (ShowtimePicker)
                    textView.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日" + hour + "时" + minute + "分");
                else
                    textView.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                DatetimeDialog.this.year = year;
                DatetimeDialog.this.month = monthOfYear;
                DatetimeDialog.this.day = dayOfMonth;
                if (dialog != null) {
                    dialog.OnselectDate(year, monthOfYear, dayOfMonth);
                }
                if (dialog2 != null) {
                    dialog2.OnselectDate(DatetimeDialog.this.year, DatetimeDialog.this.month, DatetimeDialog.this.day,
                            DatetimeDialog.this.hour, DatetimeDialog.this.minute);
                }
            }
        });
        if (timePicker != null) {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
            timePicker.setIs24HourView(true);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    DatetimeDialog.this.hour = hourOfDay;
                    DatetimeDialog.this.minute = minute;
                    textView.setText(year + "年" + (DatetimeDialog.this.month + 1) + "月" + DatetimeDialog.this.day + "日" + DatetimeDialog.this.hour + "时" + minute + "分");
                    if (dialog2 != null) {
                        dialog2.OnselectDate(DatetimeDialog.this.year, DatetimeDialog.this.month, DatetimeDialog.this.day,
                                DatetimeDialog.this.hour, DatetimeDialog.this.minute);
                    }
                }
            });
        }
    }

    public interface OnSelectDialogListener {
        public void OnselectDate(int year, int mouth, int date);
    }

    public interface OnSelectDialogListener2 {
        public void OnselectDate(int year, int mouth, int date, int hour, int min);
    }

    public void setShowtimePicker(boolean showtimePicker) {
        ShowtimePicker = showtimePicker;
    }

    /***
     * 年月日 时分秒
     ***/
    public Date getStringtime(String dstr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date date = null;
        try {
            date = sdf.parse(dstr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getStringtime2(String dstr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date date = null;
        try {
            date = sdf.parse(dstr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
