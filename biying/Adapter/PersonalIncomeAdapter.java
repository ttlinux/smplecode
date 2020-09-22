package com.lottery.biying.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.biying.Activity.User.LotteryAccountTransfer.PersonalIncomeActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PersonalIncomBean;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.view.InComeDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3.
 */
public class PersonalIncomeAdapter extends BaseAdapter {

    ArrayList<PersonalIncomBean> beans;
    Context context;
    public PersonalIncomeAdapter(ArrayList<PersonalIncomBean> beans,Context context)
    {
        this.context=context;
        this.beans=beans;
    }

    public void Notidfy(ArrayList<PersonalIncomBean> beans)
    {
        this.beans=beans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans.size();
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
            convertView=View.inflate(context, R.layout.item_acc_transfer,null);
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
                int position=((ViewHold)v.getTag()).getPosition();
                PersonalIncomBean bean=beans.get(position);
                InComeDialog dialog=new InComeDialog(context);
                if(context instanceof PersonalIncomeActivity)
                {
                    PersonalIncomeActivity personalIncomeActivity=(PersonalIncomeActivity)context;
                    if(personalIncomeActivity.selectedtype>0)
                    {
                        dialog.show_other(bean);
                    }
                    else
                    {
                        dialog.show(bean);
                    }
                }

            }
        });

        viewHold.changeinfo=(TextView)convertView.findViewById(R.id.changeinfo);
        viewHold.username=(TextView)convertView.findViewById(R.id.username);
        viewHold.lottryname=(TextView)convertView.findViewById(R.id.lottryname);
        viewHold.changemoney=(TextView)convertView.findViewById(R.id.changemoney);

        PersonalIncomBean bean=beans.get(position);
        viewHold.changeinfo.setText(bean.getTotalProfit());
        viewHold.username.setText("日期");
        viewHold.lottryname.setText(bean.getDate());
        viewHold.changemoney.setText("盈亏金额");


        if(bean.getTestValue().equalsIgnoreCase("0"))
        {
            viewHold.changeinfo.setTextColor(context.getResources().getColor(R.color.green));
        }
        else
        {
            viewHold.changeinfo.setTextColor(context.getResources().getColor(R.color.red2));
        }
        return convertView;
    }

    class ViewHold
    {
        TextView username,lottryname,changemoney,changeinfo;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
