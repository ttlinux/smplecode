package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer.LotteryAccTransferDetailActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer.LotteryAccountTracsferListActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.AccChangeBean;
import com.lottery.biying.util.BundleTag;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/2.
 */
public class AccTransferAdapter extends BaseAdapter{

    ArrayList<AccChangeBean> accs=new ArrayList<>();
    Context context;
    int selecttype;
    public AccTransferAdapter(ArrayList<AccChangeBean> accs, Context context,int selecttype)
    {
        this.accs=accs;
        this.context=context;
        this.selecttype=selecttype;
    }

    public void Notidfy(ArrayList<AccChangeBean> accs,int selecttype)
    {
        this.accs=accs;
        this.selecttype=selecttype;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return accs.size();
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
                Intent intent=new Intent();
                intent.setClass(context, LotteryAccTransferDetailActivity.class);
                intent.putExtra(BundleTag.Data, new Gson().toJson(accs.get(position)));
                intent.putExtra(BundleTag.Type,selecttype);
                context.startActivity(intent);
            }
        });

        viewHold.changeinfo=(TextView)convertView.findViewById(R.id.changeinfo);
        viewHold.username=(TextView)convertView.findViewById(R.id.username);
        viewHold.lottryname=(TextView)convertView.findViewById(R.id.lottryname);
        viewHold.changemoney=(TextView)convertView.findViewById(R.id.changemoney);

        AccChangeBean bean=accs.get(position);
        viewHold.changeinfo.setText(bean.getChangeType());
        viewHold.username.setText(bean.getUserName());
        viewHold.lottryname.setText(bean.getLotteryName()+" ("+bean.getGameName()+")");
        viewHold.changemoney.setText(bean.getChangeMoney());

        try {
            double money=Double.valueOf(bean.getChangeMoney());
            if(money>0)
            {
                viewHold.changemoney.setTextColor(context.getResources().getColor(R.color.green));
            }
            else
            {
                viewHold.changemoney.setTextColor(context.getResources().getColor(R.color.red2));
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
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
