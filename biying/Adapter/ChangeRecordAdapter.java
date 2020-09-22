package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.WithdrawAndDepositRecord.WithdrawOrDepositDeatilActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PaymentBean;
import com.lottery.biying.bean.PaymentBean3;
import com.lottery.biying.util.BundleTag;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/13.
 */
public class ChangeRecordAdapter extends BaseAdapter {

    ArrayList<PaymentBean3> paymentBean3s;
    Context context;
    private int imageresoures[]={R.drawable.success,R.drawable.fail,R.drawable.authing,R.drawable.exception};

    public ChangeRecordAdapter(ArrayList<PaymentBean3> paymentBean3s,Context context)
    {
        this.paymentBean3s=paymentBean3s;
        this.context=context;
    }

    public void Notify(ArrayList<PaymentBean3> paymentBean3s)
    {
        this.paymentBean3s=paymentBean3s;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return paymentBean3s.size();
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
            convertView=View.inflate(context, R.layout.item_deposit_record,null);
            viewHold=new ViewHold();
            convertView.setTag(viewHold);
        }
        else {
            viewHold=(ViewHold)convertView.getTag();
        }
        viewHold.setPosition(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        viewHold.statusimg=(ImageView)convertView.findViewById(R.id.statusimg);
        viewHold.tag=(TextView)convertView.findViewById(R.id.tag);
        viewHold.time=(TextView)convertView.findViewById(R.id.time);
        viewHold.money=(TextView)convertView.findViewById(R.id.money);
        viewHold.status=(TextView)convertView.findViewById(R.id.status);
        viewHold.setPosition(position);

        PaymentBean3 paymentBean=paymentBean3s.get(position);

        if(paymentBean.getEduStatus()==1)
        {
            viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[0]));
        }
        else if(paymentBean.getEduStatus()==0)
        {
            viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[1]));
        }
        else if(paymentBean.getEduStatus()==-1)
        {
            viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[3]));
        }
//        else
//        {
//            //待审核
//            ptitle4.setText("审核状态:");
//            holder.state.setTextColor(context.getResources().getColor(R.color.blue3));
//            holder.state.setText("待审核");
//            holder.state_image.setImageDrawable(context.getResources().getDrawable(imageresoures[2]));
//        }

        viewHold.tag.setText(paymentBean.getEduForwardRemark());
        viewHold.time.setText(paymentBean.getCreateTime());
        viewHold.status.setText(paymentBean.getEduStatusDes());
        viewHold.money.setText(paymentBean.getEduPoints()+"");
        return convertView;
    }

    class ViewHold
    {
        ImageView statusimg;
        TextView tag,time,money,status;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
