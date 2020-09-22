package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Record.OrderDetailActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.OrderHistoryBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/28.
 */
public class OrderHistoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderHistoryBean> orderHistoryBeans;
    public OrderHistoryAdapter(Context context,ArrayList<OrderHistoryBean> orderHistoryBeans)
    {
        this.orderHistoryBeans=orderHistoryBeans;
        this.context=context;
    }


    public void Notidfy(ArrayList<OrderHistoryBean> orderHistoryBeans)
    {
        this.orderHistoryBeans=orderHistoryBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderHistoryBeans.size();
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
        ViewHold hold;
        if(convertView==null)
        {
            convertView=View.inflate(context, R.layout.item_order_history_list,null);
            hold=new ViewHold();
            convertView.setTag(hold);
        }
        else
        {
            hold=(ViewHold)convertView.getTag();
        }
        hold.setPosition(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderHistoryBean bean = orderHistoryBeans.get(((ViewHold) v.getTag()).getPosition());
                Intent intent = new Intent();
                intent.setClass(context, OrderDetailActivity.class);
                intent.putExtra(BundleTag.id, bean.getId());
                intent.putExtra(BundleTag.Type, bean.getStopOrderFalg());
                ((Activity) context).startActivityForResult(intent, BundleTag.RequestCode);
            }
        });

        hold.qishu=(TextView)convertView.findViewById(R.id.qishu);
        hold.timeview1=(TextView)convertView.findViewById(R.id.timeview1);
        hold.timeview2=(TextView)convertView.findViewById(R.id.timeview2);
        hold.lotteryname=(TextView)convertView.findViewById(R.id.lotteryname);
        hold.betmoney=(TextView)convertView.findViewById(R.id.betmoney);
        hold.status=(TextView)convertView.findViewById(R.id.status);
        hold.ganmename=(TextView)convertView.findViewById(R.id.ganmename);
        hold.winmoney=(TextView)convertView.findViewById(R.id.winmoney);

        OrderHistoryBean bean=orderHistoryBeans.get(position);
        hold.lotteryname.setText(bean.getLotteryName());
        String time[]= TimeUtils.MMDDHHMM(bean.getBetTime());
        hold.timeview1.setText(time[0]);
        hold.timeview2.setText(time[1]);
        hold.betmoney.setText(bean.getBetMoney());
        hold.qishu.setText(bean.getBetQishu());
        hold.ganmename.setText(bean.getGameName());
        if(bean.getStatusValue().equalsIgnoreCase("2"))
        {
            //中奖
            hold.winmoney.setText(bean.getWinMoney()+"");
        }
        else
        {
            hold.winmoney.setText("");
        }


        hold.status.setText(bean.getStatus());
        if(bean.getStatusValue().equalsIgnoreCase("0"))
        {
            hold.status.setTextColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            hold.status.setTextColor(context.getResources().getColor(R.color.gray24));
        }
        return convertView;
    }

    public class ViewHold
    {
        TextView timeview1,timeview2,lotteryname,betmoney,qishu,status,winmoney,ganmename;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
