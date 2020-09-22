package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Record.TraceDetailActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.OrderHistoryBean;
import com.lottery.biying.bean.TraceOrderBean;
import com.lottery.biying.util.BundleTag;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/30.
 */
public class TraceHistoryListAdapter extends BaseAdapter {

    ArrayList<TraceOrderBean> traceOrderBeans;
    Activity activity;
    public TraceHistoryListAdapter(Activity activity, ArrayList<TraceOrderBean> traceOrderBeans)
    {
        this.activity=activity;
        this.traceOrderBeans=traceOrderBeans;
    }
    public void Notidfy(ArrayList<TraceOrderBean> traceOrderBeans)
    {
        this.traceOrderBeans=traceOrderBeans;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return traceOrderBeans.size();
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
        ViewHold viewHold;
        if(convertView==null)
        {
            convertView=View.inflate(activity, R.layout.item_trace_history,null);
            viewHold=new ViewHold();
            convertView.setTag(viewHold);
        }
        else
        {
            viewHold=(ViewHold)convertView.getTag();
        }
        viewHold.setPosition(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHold viewHold=(ViewHold)v.getTag();
                TraceOrderBean traceOrderBean=traceOrderBeans.get(viewHold.getPosition());
                Intent intent=new Intent();
                intent.setClass(activity, TraceDetailActivity.class);
                intent.putExtra(BundleTag.OrderId,traceOrderBean.getOrdreId());
                intent.putExtra(BundleTag.OrderNumber,traceOrderBean.getOrderNumber());
                activity.startActivityForResult(intent, BundleTag.RequestCode);
            }
        });

        viewHold.lotteryname=(TextView)convertView.findViewById(R.id.lotteryname);
        viewHold.trace_money=(TextView)convertView.findViewById(R.id.trace_money);
        viewHold.totalwin=(TextView)convertView.findViewById(R.id.totalwin);
        viewHold.used=(TextView)convertView.findViewById(R.id.used);
        viewHold.status=(TextView)convertView.findViewById(R.id.status);

        TraceOrderBean traceOrderBean=traceOrderBeans.get(position);
        viewHold.status.setText(traceOrderBean.getStatus());
        viewHold.used.setText(traceOrderBean.getTraceCountRate());
        viewHold.lotteryname.setText(traceOrderBean.getLotteryName()+" ("+traceOrderBean.getGameName()+")");
        viewHold.trace_money.setText(traceOrderBean.getTraceMoney());
        viewHold.totalwin.setText(traceOrderBean.getBetWinMoney());

        if(traceOrderBean.getStatusValue().equalsIgnoreCase("0"))
        {
            viewHold.status.setBackground(activity.getResources().getDrawable(R.drawable.unjiantou));
        }
        else
        {
            viewHold.status.setBackground(activity.getResources().getDrawable(R.drawable.seled_jiantou));
        }

        return convertView;
    }

    public class ViewHold
    {
        TextView lotteryname,trace_money,totalwin,used,status;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
