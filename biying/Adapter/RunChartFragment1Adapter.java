package com.lottery.biying.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.util.BundleTag;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/5.
 */
public class RunChartFragment1Adapter extends BaseAdapter{

    Context context;
    ArrayList<LotteryResultList> lotteryResultLists;
    String sequence;
    int count;

    public RunChartFragment1Adapter(Context context,ArrayList<LotteryResultList> lotteryResultLists,String sequence)
    {
        this.context=context;
        this.lotteryResultLists=lotteryResultLists;
        this.sequence=sequence;
    }

    public void NotifyData(ArrayList<LotteryResultList> lotteryResultLists,String sequence)
    {
        this.lotteryResultLists=lotteryResultLists;
        this.sequence=sequence;
        notifyDataSetChanged();
    }

    public void ClearData()
    {
        count=0;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lotteryResultLists.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null)
        {
            convertView=View.inflate(context, R.layout.item_lottery_history,null);
            holder=new Holder();
            convertView.setTag(holder);
        }
        else
        {
            holder=(Holder)convertView.getTag();
        }


        int color = position % 2 == 0 ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.gray18);
        convertView.setBackgroundColor(color);
        LinearLayout view=(LinearLayout)convertView.findViewById(R.id.mainview);
        holder.textView1=(TextView)view.getChildAt(0);
        holder.textView2=(TextView)view.getChildAt(2);
        while (view.getChildCount()>3)
        {
            view.removeViewAt(3);
        }

        int index=sequence.equalsIgnoreCase(BundleTag.Desc)?position:(getCount()-position-1);
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <lotteryResultLists.get(index).getOpenResult().length; i++) {
            sb.append(lotteryResultLists.get(index).getOpenResult()[i]);
            sb.append("  ");
        }
        if(sb.length()>2)
            sb.deleteCharAt(sb.length() - 2);
        holder.textView1.setText(lotteryResultLists.get(index).getQsFormat());
        if(lotteryResultLists.get(index).getIsOpen()==0)
            holder.textView2.setText("等待开奖");
            else
            holder.textView2.setText(sb.toString());
        holder.textView2.setTextColor(0xFFEE1F3B);

        return convertView;
    }

    class Holder
    {
        TextView textView1,textView2,textView3,textView4;
    }
}
