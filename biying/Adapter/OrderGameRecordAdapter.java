package com.lottery.biying.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.Activity.User.Record.OtherGame.OtherGameRecordListActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PlatformBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.RepluginMethod;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class OrderGameRecordAdapter extends BaseAdapter {

    ArrayList<PlatformBean> platformBeans;
    Context context;
    ImageLoader imageDownloader;
    public  OrderGameRecordAdapter(ArrayList<PlatformBean> platformBeans,Context context)
    {
        this.platformBeans=platformBeans;
        this.context=context;
        imageDownloader = RepluginMethod.getApplication(context)
                .getImageLoader();
    }


    @Override
    public int getCount() {
        return platformBeans.size();
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
            convertView=View.inflate(context, R.layout.item_othergame_platform,null);
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
                ViewHold viewHold = (ViewHold) v.getTag();
                Intent intent=new Intent();
                intent.setClass(context, OtherGameRecordListActivity.class);
                intent.putExtra(BundleTag.Data,new Gson().toJson(platformBeans.get(viewHold.getPosition())));
                context.startActivity(intent);
            }
        });

        viewHold.image=(ImageView)convertView.findViewById(R.id.image);
        viewHold.name=(TextView)convertView.findViewById(R.id.name);

        PlatformBean bean=platformBeans.get(position);
        imageDownloader.displayImage(bean.getSmallPic(),viewHold.image);
        viewHold.name.setText(bean.getFlatName());


        return convertView;
    }

    class ViewHold
    {
        TextView name;
        ImageView image;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
