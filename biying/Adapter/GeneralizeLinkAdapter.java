package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Proxy.GeneralizeLink.GeneralizeLinkDetailActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.GeneralizeLinkBean;
import com.lottery.biying.util.BundleTag;

import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/5/4.
 */
public class GeneralizeLinkAdapter extends BaseAdapter {

    ArrayList<GeneralizeLinkBean> arrayList;
    Context context;
    public GeneralizeLinkAdapter(ArrayList<GeneralizeLinkBean> arrayList,Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    public void Notidfy(ArrayList<GeneralizeLinkBean> arrayList)
    {
        this.arrayList=arrayList;
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
        ViewHold hold;
        if(convertView==null)
        {
            convertView=View.inflate(context, R.layout.item_generalize_link,null);
            hold=new ViewHold();
            convertView.setTag(hold);
        }
        else
        {
            hold=(ViewHold)convertView.getTag();
        }
        hold.setPosition(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHold viewHold=(ViewHold)v.getTag();
                GeneralizeLinkBean bean=arrayList.get(viewHold.getPosition());
                Intent intent=new Intent();
                intent.setClass(context, GeneralizeLinkDetailActivity.class);
                intent.putExtra(BundleTag.Data,new Gson().toJson(bean));
                ((Activity)context).startActivityForResult(intent, BundleTag.RequestCode);
            }
        });

        hold.index=(TextView)convertView.findViewById(R.id.index);
        hold.time=(TextView)convertView.findViewById(R.id.time);
        hold.type=(TextView)convertView.findViewById(R.id.type);

        GeneralizeLinkBean bean=arrayList.get(position);
        hold.index.setText(bean.getId());
        hold.time.setText(bean.getCreateTime());
        hold.type.setText(bean.getUserType());


        return convertView;
    }

    class ViewHold
    {
        TextView time,index,type;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
