package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.Activity.User.Record.OtherGame.GameRecordDetailActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.ORecordListBean;
import com.lottery.biying.bean.QueryBean;
import com.lottery.biying.util.BundleTag;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class OtherGameRecordListAdapter extends BaseAdapter {

    ArrayList<QueryBean> oRecordListBeans;
    Context context;

    public OtherGameRecordListAdapter(ArrayList<QueryBean> oRecordListBeans,Context context)
    {
        this.oRecordListBeans=oRecordListBeans;
        this.context=context;
    }

    public void Notidfy(ArrayList<QueryBean> oRecordListBeans)
    {
        this.oRecordListBeans=oRecordListBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return oRecordListBeans.size();
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
                intent.putExtra(BundleTag.Data, new Gson().toJson(oRecordListBeans.get(position)));
                intent.setClass(context, GameRecordDetailActivity.class);
                context.startActivity(intent);
            }
        });

        viewHold.changeinfo=(TextView)convertView.findViewById(R.id.changeinfo);
        viewHold.username=(TextView)convertView.findViewById(R.id.username);
        viewHold.lottryname=(TextView)convertView.findViewById(R.id.lottryname);
        viewHold.changemoney=(TextView)convertView.findViewById(R.id.changemoney);

        QueryBean bean=oRecordListBeans.get(position);

        viewHold.username.setText(bean.getBetGameContent());
        viewHold.lottryname.setText(bean.getBetTime());
        viewHold.changemoney.setText(bean.getBetUsrWin()+"");
        viewHold.changeinfo.setText(bean.getSettleStatus());
        try {
            double money=Double.valueOf(bean.getBetUsrWin());
            if(money>-1)
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
