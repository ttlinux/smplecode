package com.lottery.biying.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/5.
 */
public class TimeUtils {

    public static String ConverTime(long time) {
        long hour = time / 3600;
        long min = time%3600 / 60;
        long sec = time % 60;
        StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append(hour);
            sb.append("时");
        }
        if (min > 0) {
            if (min < 10)
                sb.append("0" + min);
            else
                sb.append(min);
            sb.append("分");
        }
        if (sec < 10) {
            sb.append("0" + sec);
        } else {
            sb.append(sec);
        }
        sb.append("秒");
        return sb.toString();
    }

    //
    public static String[] MMDDHHMM(String timestr) {
        String time[] = new String[2];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new Date(df.parse(timestr).getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        time[0] = (calendar.get(Calendar.MONTH)+1) + "/" + getstr(calendar.get(Calendar.DATE));
        time[1] = getstr(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + getstr(calendar.get(Calendar.MINUTE));
        return time;
    }

    public static String getstr(int str)
    {
        if(str<10)
            return "0"+str;
        else
            return str+"";
    }

    public static String Gettime()
    {
        //yyyy-MM-dd
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

    public static String Gettime2()
    {
        //yyyy-MM-dd
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(calendar.getTime());
    }

    public static String Gettime3()
    {
        //yyyy-MM-dd
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(calendar.getTime());
    }

    public static String Analysistime(long time)
    {
        //yyyy-MM-dd
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(time));
    }

    public static String[] GetTimestr(int days)//几天内的时间
    {
        String timestr[]=new String[2];
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        switch (days)
        {
            case 1:

                timestr[0]=df.format(calendar.getTime());
                calendar.add(Calendar.DATE,1);
                timestr[1]=df.format(calendar.getTime());
                break;
            case 7:
                calendar.add(Calendar.DATE, -7);
                timestr[0]=df.format(calendar.getTime());
                calendar.add(Calendar.DATE,8);
                timestr[1]=df.format(calendar.getTime());
                break;
            case 30:
                calendar.add(Calendar.DATE,-30);
                timestr[0]=df.format(calendar.getTime());
                calendar.add(Calendar.DATE,31);
                timestr[1]=df.format(calendar.getTime());
                break;
        }

        return timestr;
    }

    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
