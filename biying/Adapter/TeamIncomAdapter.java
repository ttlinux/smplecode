package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Proxy.TeamIncomeActivity;
import com.lottery.biying.Activity.User.Proxy.TeamManage.TeamManageActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.view.InComeDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3.
 */
public class TeamIncomAdapter extends BaseAdapter {

    ArrayList<TeamIncomeBean> arrayList=new ArrayList<>();
    Context context;
    int Mode;

    public TeamIncomAdapter(ArrayList<TeamIncomeBean> arrayList,Context context,int Mode)
    {
        this.arrayList=arrayList;
        this.context=context;
        this.Mode=Mode;
    }

    public void Notidfy(ArrayList<TeamIncomeBean> arrayList,int Mode)
    {
        this.arrayList=arrayList;
        this.Mode=Mode;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return arrayList.size();
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
                TeamIncomeBean bean = arrayList.get(position);
                InComeDialog dialog = new InComeDialog(context);
                if(context instanceof TeamIncomeActivity)
                {
                    TeamIncomeActivity teamIncomeActivity=(TeamIncomeActivity)context;
                    if(teamIncomeActivity.selectedtype>0)
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
        viewHold.userlayout=(LinearLayout)convertView.findViewById(R.id.userlayout);


        TeamIncomeBean bean=arrayList.get(position);
        viewHold.userlayout.setTag(bean);
        switch (Mode)
        {
            case 0://团队盈亏
                viewHold.changeinfo.setText(bean.getTotalProfit());
                viewHold.username.setText("用户");
                viewHold.lottryname.setText(bean.getUserName());
                viewHold.changemoney.setText("盈亏金额");
                if(bean.getClickFlag().equalsIgnoreCase("1"))
                {
                    viewHold.lottryname.setTextColor(context.getResources().getColor(R.color.blue6));
                    viewHold.lottryname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    viewHold.userlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Mode == 1) return;
                            TeamIncomeBean name = (TeamIncomeBean) v.getTag();
                            if (name.getClickFlag().equalsIgnoreCase("0")) {
                                return;
                            }
                            Intent intent = new Intent();
                            intent.putExtra(BundleTag.Username, name.getUserName());
                            intent.setClass(context, TeamIncomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                }
                else
                {
                    viewHold.lottryname.setTextColor(context.getResources().getColor(R.color.gray20));
                    viewHold.lottryname.getPaint().setFlags(0);
                    viewHold.userlayout.setOnClickListener(null);
                }

                break;
            case 1: //每日盈亏
                viewHold.changeinfo.setText(bean.getTotalProfit());
                viewHold.username.setText("日期");
                viewHold.lottryname.setText(bean.getDate());
                viewHold.changemoney.setText("团队总盈亏");
                viewHold.lottryname.setTextColor(context.getResources().getColor(R.color.gray20));
                viewHold.lottryname.getPaint().setFlags(0);
                break;
        }

        try {

            if(bean.getTestValue().equalsIgnoreCase("0"))
            {
                viewHold.changeinfo.setTextColor(context.getResources().getColor(R.color.green));
            }
            else
            {
                viewHold.changeinfo.setTextColor(context.getResources().getColor(R.color.red2));
            }
        }
        catch (NumberFormatException ex)
        {

        }

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
