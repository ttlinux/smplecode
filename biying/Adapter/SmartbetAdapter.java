package com.lottery.biying.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.CaseBean;
import com.lottery.biying.bean.SmartTraceBean;
import com.lottery.biying.util.CalculateBetMoney;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.view.Keyboard;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/19.
 */
public class SmartbetAdapter extends BaseAdapter {

    Context context;
    ArrayList<SmartTraceBean> casebeans;
    OnChangeListener onchangelistener;
    Handler handler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what)
            {
                case 0:
                    TextView textView=(TextView)msg.obj;
                    textView.setText(msg.arg1+"");
                    break;
            }
        }
    };

    public OnChangeListener getOnchangelistener() {
        return onchangelistener;
    }

    public void setOnchangelistener(OnChangeListener onchangelistener) {
        this.onchangelistener = onchangelistener;
    }

    public ArrayList<SmartTraceBean> getCasebeans() {
        return casebeans;
    }

    public SmartbetAdapter(Context context, ArrayList<SmartTraceBean> casebeans) {
        this.casebeans = casebeans;
        this.context = context;
    }

    public void NotifysetChange(ArrayList<SmartTraceBean> casebeans) {
        this.casebeans = casebeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return casebeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold holder;
        if (convertView == null) {
            holder = new ViewHold();
            convertView = View.inflate(context, R.layout.item_smart_bet2, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHold) convertView.getTag();
        }

        LinearLayout linearLayout = (LinearLayout) convertView;
        holder.imageView = (ImageView) linearLayout.getChildAt(0);
        holder.xuhao = (TextView) linearLayout.getChildAt(2);
        holder.qihao = (TextView) linearLayout.getChildAt(4);
        final EditText beishu = (EditText) linearLayout.getChildAt(6);
        holder.ljtz = (TextView) linearLayout.getChildAt(8);
        holder.zjyl = (TextView) linearLayout.getChildAt(10);
        holder.zyll = (TextView) linearLayout.getChildAt(12);

        if (casebeans.get(position).isSelect()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_checked));
        } else {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_normal));
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casebeans.get(position).setSelect(!casebeans.get(position).isSelect());
                if (onchangelistener != null) onchangelistener.onChange(position);
            }
        });
        beishu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                handler.removeMessages(0);
                Message message=new Message();
                message.what=0;
                message.obj=beishu;
                if (s.toString().length() < 1) {

                    message.arg1=1;
                    handler.sendMessageAtTime(message,800);
                    casebeans.get(position).setTimes(1);
                } else {
                    beishu.setHint(s.toString());
                    message.arg1=Integer.valueOf(s.toString());
                    handler.sendMessageAtTime(message, 800);
                    casebeans.get(position).setTimes(Integer.valueOf(beishu.getHint().toString()));
                }
                if (onchangelistener != null) onchangelistener.onChange(position);
            }
        });
        holder.xuhao.setText(casebeans.get(position).getIndex() + "");
        holder.qihao.setText(casebeans.get(position).getQihao());
        beishu.setHint(casebeans.get(position).getTimes() + "");
        holder.ljtz.setText(casebeans.get(position).getBetsmoney());
        holder.zjyl.setText(casebeans.get(position).getWinmoney());
        holder.zyll.setText(casebeans.get(position).getPercentofwinmoney());


//        int red=context.getResources().getColor(R.color.red2);
//        int green=context.getResources().getColor(R.color.green2);
//        if(casebeans.get(position).getWinmax()==casebeans.get(position).getWinmin())
//        {
//            holder.textView5.setText(casebeans.get(position).getWinmin()+"");
//            holder.textView5.setTextColor(casebeans.get(position).getWinmin()>0?red:green);
//            holder.textView6.setText(casebeans.get(position).getWinminPercent() + "%");
//            holder.textView6.setTextColor(casebeans.get(position).getWinmin()>0?red:green);
//        }
//        else
//        {
//            String text1=casebeans.get(position).getWinmin()+"\n至\n"+casebeans.get(position).getWinmax();
//            String text2=casebeans.get(position).getWinminPercent()+"%"+"\n至\n"+(casebeans.get(position).getWinmaxPercent())+"%";
//
//            SpannableStringBuilder builder = new SpannableStringBuilder(text1);
//            builder.setSpan(new ForegroundColorSpan(casebeans.get(position).getWinmin()>0?red:green), 0,text1.indexOf("\n") , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            builder.setSpan(new ForegroundColorSpan(casebeans.get(position).getWinmax()>0?red:green), text1.lastIndexOf("\n")+1,text1.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.textView5.setText(builder);
//
//
//            SpannableStringBuilder builder2 = new SpannableStringBuilder(text2);
//            builder2.setSpan(new ForegroundColorSpan(casebeans.get(position).getWinminPercent()>0?red:green), 0,text2.indexOf("\n") , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            builder2.setSpan(new ForegroundColorSpan(casebeans.get(position).getWinmaxPercent()>0?red:green), text2.lastIndexOf("\n") + 1, text2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.textView6.setText(builder2);
//        }

        return convertView;
    }

    class ViewHold {
        TextView xuhao, qihao, ljtz, zjyl, zyll;
        EditText beishu;
        ImageView imageView;
    }


    public interface OnChangeListener {
        public void onChange(int position);
    }
}
