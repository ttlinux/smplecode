package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.WithdrawAndDepositRecord.WithdrawOrDepositDeatilActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PaymentBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class DepositRecordAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PaymentBean> payments;
    private int imageresoures[]={R.drawable.success,R.drawable.fail,R.drawable.authing};

    public DepositRecordAdapter(Context context,ArrayList<PaymentBean> payments)
    {
        this.context=context;
        this.payments=payments;
    }

    public void Notify(ArrayList<PaymentBean> payments)
    {
        this.payments=payments;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return payments.size();
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
            convertView=View.inflate(context,R.layout.item_deposit_record,null);
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
                ViewHold viewHold=(ViewHold)v.getTag();
                Intent intent=new Intent();
                intent.setClass(context,WithdrawOrDepositDeatilActivity.class);
                intent.putExtra(BundleTag.Type, 0);
                intent.putExtra(BundleTag.Title,"充值详情");
                intent.putExtra(BundleTag.Data,new Gson().toJson(payments.get(viewHold.getPosition())));
                context.startActivity(intent);
            }
        });

        viewHold.statusimg=(ImageView)convertView.findViewById(R.id.statusimg);
        viewHold.tag=(TextView)convertView.findViewById(R.id.tag);
        viewHold.time=(TextView)convertView.findViewById(R.id.time);
        viewHold.money=(TextView)convertView.findViewById(R.id.money);
        viewHold.status=(TextView)convertView.findViewById(R.id.status);
        viewHold.setPosition(position);

        PaymentBean paymentBean=payments.get(position);
        int hkcheckstatus=paymentBean.getHkCheckStatus();
        int kStatus=paymentBean.getHkStatus();


        if(kStatus==1)
        {
            if(hkcheckstatus==1)
            {
                viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[0]));
            }
            else
            {
                viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[1]));
            }
        } else {
            viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[2]));
        }

        viewHold.tag.setText(paymentBean.getHkType());
        viewHold.time.setText(paymentBean.getCreateTime());
        viewHold.status.setText(paymentBean.getStatusDes());
        viewHold.money.setText(paymentBean.getHkMoney()+"");
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
