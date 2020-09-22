package com.lottery.biying.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */
public class TimeMethod {

    public static int CloseTime;
    private static final int Start = 0;
    private static final int End = 1;
    private static int TimeerStatus=0;//0 停止 1运行
    private static OnTimeChangeListener m_onTimeChangeListener;
    private static Activity mcontext;
    private static String mlotteryType;
    private int Lotterystatus=0;//0 等待开奖中 1 新一轮开奖，更新数据 -1 请求期号中
    public final static int Wating=0;
    public final static int NewRound=1;
    public final static int Requesting=-1;
    private static int failTryCount=0;
    private static String qsstr;


    public OnTimeChangeListener getOnTimeChangeListener() {
        return m_onTimeChangeListener;
    }

    public static void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener,Activity context,String qsformat) {
        m_onTimeChangeListener=null;
        m_onTimeChangeListener = onTimeChangeListener;
        mcontext=context;
        qsstr=qsformat;
    }


    public static String getQsstr() {
        return qsstr;
    }

    public static Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case Start:
                    CloseTime--;
                        if (CloseTime <0) {
                            if (m_onTimeChangeListener != null) {
                                m_onTimeChangeListener.OnChange(CloseTime,Requesting,"");
                            }
                            removeMessages(Start);
                            TimeerStatus=0;
//                            if(mcontext instanceof BaseActivity)
//                            {
//                                if(((BaseActivity) mcontext).isInFront())
//                                    ShowExpiredialog();
//                            }
                        } else {
                            sendEmptyMessageDelayed(Start, 1000);
                            if (m_onTimeChangeListener != null) {
                                m_onTimeChangeListener.OnChange(CloseTime,Wating,qsstr);
                            }
                        }

                    break;
                case End:
                    removeMessages(Start);
                    break;
            }
        }
    };

    public static void ShowExpiredialog()
    {
//        if(lrljsonobj==null)return;
//        OnPeriodExpireDialog dialog=new OnPeriodExpireDialog(mcontext);
//        String qs=lrljsonobj.optJSONObject("datas").optJSONObject("next").optString("qs","");
//        dialog.setContentText("第"+qs+"期已结束");
//        dialog.show();
    }

    public static void Start(int closetime) {
        if(TimeerStatus>0)return;
        handler.removeMessages(Start);
        CloseTime = closetime;
        handler.sendEmptyMessageDelayed(Start,1000);
        TimeerStatus=1;
        LogTools.e("TimeMethod", "Start  " + closetime);
    }

    public static void End() {
        handler.removeMessages(Start);
        TimeerStatus=0;
        m_onTimeChangeListener=null;
        LogTools.e("TimeMethod", "End  ");
    }

    public interface OnTimeChangeListener {
        public void OnChange( int CloseLimitime,int LotteryStatus,String qsstr);
    }



}
