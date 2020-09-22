package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Message.MessageDatailActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.MessageBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.RepluginMethod;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/9.
 */
public class MessageListAdapter extends BaseAdapter{

    ArrayList<MessageBean> messageBeans;
    Context context;
    String username;
    int mode=0;
    public MessageListAdapter(ArrayList<MessageBean> messageBeans,Context context)
    {
        this.messageBeans=messageBeans;
        this.context=context;
        this.username= RepluginMethod.getApplication(context).getBaseapplicationUsername();
    }

    public void Notify(ArrayList<MessageBean> messageBeans)
    {
        this.messageBeans=messageBeans;
        notifyDataSetChanged();
    }

    public ArrayList<MessageBean> getMessageBeans() {
        return messageBeans;
    }

    public void setMode(int mode)
    {
        this.mode=mode;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messageBeans.size();
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
            convertView=View.inflate(context, R.layout.item_message,null);
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
                intent.setClass(context, MessageDatailActivity.class);
                intent.putExtra(BundleTag.id, messageBeans.get(position).getId());
                intent.putExtra(BundleTag.GroupId, messageBeans.get(position).getGroupKey());
                ((Activity) context).startActivityForResult(intent, BundleTag.ResultCode);
            }
        });
        viewHold.image=(ImageView)convertView.findViewById(R.id.image);
        viewHold.checkbox=(ImageView)convertView.findViewById(R.id.checkbox);
        viewHold.checkbox.setTag(position);
        if(mode==1)
        {
            viewHold.checkbox.setVisibility(View.VISIBLE);
            Drawable drawable=messageBeans.get(position).ischeck()?context.getResources().getDrawable(R.drawable.check_selected):
                    context.getResources().getDrawable(R.drawable.check_nomal);
            viewHold.checkbox.setImageDrawable(drawable);
            viewHold.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imageView=(ImageView)v;
                    int index = (int) v.getTag();
                    messageBeans.get(index).setIscheck(!messageBeans.get(index).ischeck());
                    if (messageBeans.get(index).ischeck()) {
                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.check_selected));
                    } else {
                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.check_nomal));
                    }
                }
            });
        }
        else
        {
            viewHold.checkbox.setVisibility(View.GONE);
        }
        viewHold.readstatus=(ImageView)convertView.findViewById(R.id.readstatus);

        viewHold.linearlayout=(LinearLayout)convertView.findViewById(R.id.linearlayout);
        MessageBean bean=messageBeans.get(position);
        if(bean.getType().equalsIgnoreCase("1"))
        {
            //green
            viewHold.image.setImageDrawable(context.getResources().getDrawable(R.drawable.self_message));
        }
        else
        {
            //red
            viewHold.image.setImageDrawable(context.getResources().getDrawable(R.drawable.other_message));
        }
        if(bean.getStatus().equalsIgnoreCase("0"))
        {
            viewHold.readstatus.setVisibility(View.GONE);
        }
        else
        {
            viewHold.readstatus.setVisibility(View.VISIBLE);
        }

        TextView content=(TextView)viewHold.linearlayout.getChildAt(0);
        TextView time=(TextView)viewHold.linearlayout.getChildAt(1);
        content.setText(bean.getTitle());
        time.setText(bean.getMessageTime());

        return convertView;
    }

    class ViewHold
    {
        ImageView image,readstatus,checkbox;
        LinearLayout linearlayout;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
