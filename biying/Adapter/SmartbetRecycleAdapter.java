package com.lottery.biying.Adapter;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.SmartTraceBean;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.view.MyEdittext;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */
public class SmartbetRecycleAdapter extends RecyclerView.Adapter<SmartbetRecycleAdapter.Viewhold>{

    Context context;
    ArrayList<SmartTraceBean> casebeans;
    OnChangeListener onchangelistener;
    LayoutInflater inflater;

    public void setOnchangelistener(OnChangeListener onchangelistener) {
        this.onchangelistener = onchangelistener;
    }

    public ArrayList<SmartTraceBean> getCasebeans() {
        return casebeans;
    }

    public SmartbetRecycleAdapter(Context context, ArrayList<SmartTraceBean> casebeans)
    {
        this.casebeans = casebeans;
        this.context = context;
         inflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    public void NotifysetChange(ArrayList<SmartTraceBean> casebeans,int from) {
        this.casebeans = casebeans;
        for (int i = from; i <casebeans.size() ; i++) {
            notifyItemChanged(i);
        }
    }

    public void clearData(ArrayList<SmartTraceBean> casebeans)
    {
        this.casebeans = casebeans;
        notifyDataSetChanged();
    }

    @Override
    public Viewhold onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_smart_bet2, parent,false);
        return new Viewhold(view);
    }

    @Override
    public void onBindViewHolder(final Viewhold holder, final int position) {
        LogTools.e("position", position + "");
        if (casebeans.get(position).isSelect()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_checked));
        } else {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_normal));
        }
        holder.imageView.setTag(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int m_position = (int) v.getTag();
                casebeans.get(position).setSelect(!casebeans.get(m_position).isSelect());
                if (onchangelistener != null) {
                    final ArrayList<SmartTraceBean> m_casebeans = onchangelistener.onChange(m_position);
                    SmartbetRecycleAdapter.this.casebeans = m_casebeans;
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemRangeChanged(m_position, m_casebeans.size() - m_position);
                        }
                    });
                }
            }
        });
        holder.beishu.setTag(position);
        holder.beishu.setOnafterTextChangedListener(new MyEdittext.OnafterTextChangedListener() {
            @Override
            public void OnAfterTextChanged(String text, MyEdittext myEdittext) {
                final int m_position = (int) myEdittext.getTag();
                if (text.length() < 1) {
                    casebeans.get(m_position).setTimes(Integer.valueOf(myEdittext.getHint().toString()));
                } else {
                    if (Integer.valueOf(text) < 2) {
                        myEdittext.setText("");
                        casebeans.get(m_position).setTimes(Integer.valueOf(myEdittext.getHint().toString()));
                    } else
                        casebeans.get(m_position).setTimes(Integer.valueOf(myEdittext.getText().toString()));
                }
                if (onchangelistener != null) {
                    final ArrayList<SmartTraceBean> m_casebeans = onchangelistener.onChange(m_position);
                    LinearLayout linearLayout = (LinearLayout) myEdittext.getParent();
                    TextView ljtz = (TextView) linearLayout.getChildAt(8);
                    TextView zjyl = (TextView) linearLayout.getChildAt(10);
                    TextView zyll = (TextView) linearLayout.getChildAt(12);
                    ljtz.setText(m_casebeans.get(m_position).getBetsmoney());
                    zjyl.setText(m_casebeans.get(m_position).getWinmoney());
                    zyll.setText(m_casebeans.get(m_position).getPercentofwinmoney());
                    SmartbetRecycleAdapter.this.casebeans = m_casebeans;
                    myEdittext.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemRangeChanged(m_position + 1, m_casebeans.size() - m_position - 1);
                        }
                    });

                }
            }
        });
        holder.xuhao.setText(casebeans.get(position).getIndex() + "");
        holder.qihao.setText(casebeans.get(position).getQihao());
        if(Integer.valueOf(casebeans.get(position).getTimes())>1)
            holder.beishu.setText(casebeans.get(position).getTimes() + "");
        else
            holder.beishu.setText("");
        holder.ljtz.setText(casebeans.get(position).getBetsmoney());
        holder.zjyl.setText(casebeans.get(position).getWinmoney());
        holder.zyll.setText(casebeans.get(position).getPercentofwinmoney());
    }

    @Override
    public int getItemCount() {
        return casebeans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class Viewhold extends RecyclerView.ViewHolder
    {
        TextView xuhao, qihao, ljtz, zjyl, zyll;
        MyEdittext beishu;
        ImageView imageView;
        public Viewhold(View itemView) {
            super(itemView);
            LinearLayout linearLayout = (LinearLayout) itemView;
            imageView = (ImageView) linearLayout.getChildAt(0);
            xuhao = (TextView) linearLayout.getChildAt(2);
            qihao = (TextView) linearLayout.getChildAt(4);
             beishu = (MyEdittext) linearLayout.getChildAt(6);
            ljtz = (TextView) linearLayout.getChildAt(8);
            zjyl = (TextView) linearLayout.getChildAt(10);
            zyll = (TextView) linearLayout.getChildAt(12);
        }
    }

    public interface OnChangeListener {
        public ArrayList<SmartTraceBean> onChange(int position);
    }
}
