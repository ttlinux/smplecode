package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Proxy.SalaryManage.SubordinateSalaryDetailActivity;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.Salarybean;
import com.lottery.biying.util.BundleTag;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/4.
 */
public class SubordinateSalaryAdapter extends BaseAdapter {

    ArrayList<Salarybean> salarybeans;
    Context context;
    BaseFragment fragment;

    public SubordinateSalaryAdapter(ArrayList<Salarybean> salarybeans,Context context,BaseFragment fragment)
    {
        this.salarybeans=salarybeans;
        this.context=context;
        this.fragment=fragment;
    }

    public void Notidfy(ArrayList<Salarybean> salarybeans)
    {
        this.salarybeans=salarybeans;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return salarybeans.size();
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
                int position = ((ViewHold) v.getTag()).getPosition();
                Intent intent = new Intent();
                intent.putExtra(BundleTag.Data, new Gson().toJson(salarybeans.get(position)));
                intent.setClass(context, SubordinateSalaryDetailActivity.class);
                fragment.startActivityForResult(intent, BundleTag.RequestCode);
            }
        });


        viewHold.username=(TextView)convertView.findViewById(R.id.username);
        viewHold.lottryname=(TextView)convertView.findViewById(R.id.lottryname);
        viewHold.changemoney=(TextView)convertView.findViewById(R.id.changemoney);
        viewHold.changeinfo=(TextView)convertView.findViewById(R.id.changeinfo);


        Salarybean bean=salarybeans.get(position);
        viewHold.changeinfo.setText(bean.getSalaryMoney());
        viewHold.username.setText("用户");
        viewHold.lottryname.setText(bean.getUserName());
        viewHold.changemoney.setText("日薪金额");

        viewHold.changemoney.setTextColor(context.getResources().getColor(R.color.gray25));
        viewHold.lottryname.setTextColor(context.getResources().getColor(R.color.gray25));
        viewHold.changeinfo.setTextColor(context.getResources().getColor(R.color.red2));
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
