package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.DailySalaryManageActivity;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.DailySalaryitemBean;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.view.InComeDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/4.
 */
public class DailySalaryPayoffAdapter extends BaseAdapter {

    ArrayList<DailySalaryitemBean> dailySalaryitemBeans;
    Context context;
    BaseFragment fragment;

    public DailySalaryPayoffAdapter(ArrayList<DailySalaryitemBean> dailySalaryitemBeans, Context context,BaseFragment fragment) {
        this.dailySalaryitemBeans = dailySalaryitemBeans;
        this.context = context;
        this.fragment=fragment;
    }

    public void Notidfy(ArrayList<DailySalaryitemBean> dailySalaryitemBeans) {
        this.dailySalaryitemBeans = dailySalaryitemBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dailySalaryitemBeans.size();
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
            convertView = View.inflate(context, R.layout.item_acc_transfer, null);
            viewHold = new ViewHold();
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.setPosition(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = ((ViewHold) v.getTag()).getPosition();
                Intent intent=new Intent();
                intent.setClass(context,DailySalaryManageActivity.class);
                intent.putExtra(BundleTag.Data, new Gson().toJson(dailySalaryitemBeans.get(position)));
                fragment.startActivityForResult(intent, BundleTag.RequestCode);
            }
        });

        viewHold.changeinfo = (TextView) convertView.findViewById(R.id.changeinfo);
        viewHold.username = (TextView) convertView.findViewById(R.id.username);
        viewHold.lottryname = (TextView) convertView.findViewById(R.id.lottryname);
        viewHold.changemoney = (TextView) convertView.findViewById(R.id.changemoney);
        viewHold.midtitle = (TextView) convertView.findViewById(R.id.midtitle);
        viewHold.midvalue = (TextView) convertView.findViewById(R.id.midvalue);

        DailySalaryitemBean bean = dailySalaryitemBeans.get(position);
        viewHold.username.setText("用户");
        viewHold.lottryname.setText(bean.getUserName());
        viewHold.midtitle.setText("日薪金额");
        viewHold.midvalue.setText(bean.getSalaryMoney());
        viewHold.changemoney.setText("状态");
        viewHold.changeinfo.setText(bean.getStatus());


        viewHold.changeinfo.setTextColor(context.getResources().getColor(R.color.red2));
        return convertView;
    }

    class ViewHold {
        TextView username, lottryname, changemoney, changeinfo, midtitle, midvalue;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
