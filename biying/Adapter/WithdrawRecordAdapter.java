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
import com.lottery.biying.bean.PaymentBean2;
import com.lottery.biying.util.BundleTag;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class WithdrawRecordAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PaymentBean2> payments;
    private int imageresoures[] = {R.drawable.success, R.drawable.fail, R.drawable.authing};

    public WithdrawRecordAdapter(Context context, ArrayList<PaymentBean2> payments) {
        this.context = context;
        this.payments = payments;
    }

    public void Notify(ArrayList<PaymentBean2> payments) {
        this.payments = payments;
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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_withdraw_record, null);
            viewHold = new ViewHold();
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.setPosition(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHold viewHold = (ViewHold) v.getTag();
                Intent intent = new Intent();
                intent.setClass(context, WithdrawOrDepositDeatilActivity.class);
                intent.putExtra(BundleTag.Type, 1);
                intent.putExtra(BundleTag.Title, "提现详情");
                intent.putExtra(BundleTag.Data, new Gson().toJson(payments.get(viewHold.getPosition())));
                context.startActivity(intent);
            }
        });
        viewHold.statusimg = (ImageView) convertView.findViewById(R.id.statusimg);
        viewHold.tag = (TextView) convertView.findViewById(R.id.tag);
        viewHold.time = (TextView) convertView.findViewById(R.id.time);
        viewHold.money = (TextView) convertView.findViewById(R.id.money);
        viewHold.status = (TextView) convertView.findViewById(R.id.status);
        viewHold.setPosition(position);

        PaymentBean2 paymentBean = payments.get(position);
        int Check_status = paymentBean.getCheck_status();
        int status = paymentBean.getStatus();

        if (status == 1) {
            if (Check_status == 1) {
                viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[0]));
            } else {
                viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[1]));
            }
        } else {
            viewHold.statusimg.setImageDrawable(context.getResources().getDrawable(imageresoures[2]));
        }

        viewHold.status.setText(paymentBean.getCheckStatusDes()+"("+paymentBean.getStatusDes()+")");
        viewHold.tag.setText(paymentBean.getWithdrawTypeDes());
        viewHold.time.setText(paymentBean.getCreateTime());
        viewHold.money.setText(paymentBean.getUserWithdrawMoney() + "");
        return convertView;
    }

    class ViewHold {
        ImageView statusimg;
        TextView tag, time, money, status;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
