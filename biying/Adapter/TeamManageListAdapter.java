package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Proxy.SalaryManage.SubordinateSalaryDetailActivity;
import com.lottery.biying.Activity.User.Proxy.TeamManage.TeamManageActivity;
import com.lottery.biying.Activity.User.Proxy.TeamManage.TeamManageDetailActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.ManageRecordBean;
import com.lottery.biying.bean.Salarybean;
import com.lottery.biying.util.BundleTag;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/5.
 */
public class TeamManageListAdapter extends BaseAdapter{

    ArrayList<ManageRecordBean> manageRecordBeans;
    Context context;
    int mode;//1是代理 0是普通会员
    public TeamManageListAdapter(ArrayList<ManageRecordBean> manageRecordBeans,Context context,int mode)
    {
        this.manageRecordBeans=manageRecordBeans;
        this.context=context;
        this.mode=mode;
    }

    public void Notidfy(ArrayList<ManageRecordBean> manageRecordBeans,int mode)
    {
        this.manageRecordBeans=manageRecordBeans;
        this.mode=mode;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return manageRecordBeans.size();
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
                intent.putExtra(BundleTag.Data, new Gson().toJson(manageRecordBeans.get(position)));
                intent.setClass(context, TeamManageDetailActivity.class);
                ((Activity) context).startActivityForResult(intent, BundleTag.RequestCode);
            }
        });


        viewHold.username=(TextView)convertView.findViewById(R.id.username);
        viewHold.lottryname=(TextView)convertView.findViewById(R.id.lottryname);
        viewHold.changemoney=(TextView)convertView.findViewById(R.id.changemoney);
        viewHold.changeinfo=(TextView)convertView.findViewById(R.id.changeinfo);
        viewHold.userlayout=(LinearLayout)convertView.findViewById(R.id.userlayout);




        ManageRecordBean bean=manageRecordBeans.get(position);
        viewHold.userlayout.setTag(bean);
        viewHold.changeinfo.setText(bean.getTeamMoney());
        viewHold.username.setText("用户");
        viewHold.username.setTextColor(context.getResources().getColor(R.color.gray20));
        viewHold.lottryname.setText(bean.getUserName());
        viewHold.lottryname.setTextColor(context.getResources().getColor(R.color.black2));

        if(bean.getClickFlag().equalsIgnoreCase("1") && mode==1)
        {
            viewHold.lottryname.setTextColor(context.getResources().getColor(R.color.blue6));
            viewHold.lottryname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            viewHold.userlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManageRecordBean bean = (ManageRecordBean) v.getTag();
                    Intent intent = new Intent();
                    intent.putExtra(BundleTag.Username, bean.getUserName());
                    intent.setClass(context, TeamManageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }


        viewHold.changemoney.setText("团队总余额");
        viewHold.changemoney.setTextColor(context.getResources().getColor(R.color.gray20));

        viewHold.changeinfo.setTextColor(context.getResources().getColor(R.color.red2));
        return convertView;
    }

    class ViewHold
    {
        TextView username,lottryname,changemoney,changeinfo;
        LinearLayout userlayout;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
