package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.ThirdParty.Bubble.BubbleRelativeLayout;
import com.lottery.biying.ThirdParty.Bubble.BubbleStyle;
import com.lottery.biying.ThirdParty.Bubble.BubbleTextView;
import com.lottery.biying.bean.MessageDetailBean;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/9.
 */
public class MessageDetailAdapter extends BaseAdapter{

    ArrayList<MessageDetailBean> beans;
    Context context;
    int padding;
    int bubblewidth;
    String username;
    public MessageDetailAdapter(ArrayList<MessageDetailBean> beans,Context context)
    {
        this.beans=beans;
        this.context=context;
        padding=ScreenUtils.getDIP2PX(context, 10);
        bubblewidth=ScreenUtils.getScreenWH((Activity)context)[0]/2+ScreenUtils.getDIP2PX(context, 30);
        username= RepluginMethod.getApplication(context).getBaseapplicationUsername();
    }

    public void Notify(ArrayList<MessageDetailBean> beans)
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

        ViewHold viewHold=null;
        if(convertView==null)
        {
            convertView=View.inflate(context, R.layout.item_message_detail,null);
            viewHold=new ViewHold();
            convertView.setTag(viewHold);
        }
        else {
            viewHold=(ViewHold)convertView.getTag();
        }

        MessageDetailBean bean=beans.get(position);
        viewHold.left_layout=(LinearLayout)convertView.findViewById(R.id.left_layout);
        viewHold.right_layout=(RelativeLayout)convertView.findViewById(R.id.right_layout);

        if(bean.getSender().equalsIgnoreCase(username))
        {
            viewHold.left_layout.setVisibility(View.GONE);
            viewHold.right_layout.setVisibility(View.VISIBLE);
            viewHold.name_right=(TextView)convertView.findViewById(R.id.name_right);
            viewHold.time_right=(TextView)convertView.findViewById(R.id.time_right);

            viewHold.content_right=(TextView)convertView.findViewById(R.id.content_right);
            viewHold.name_right.setText(bean.getSender());
            viewHold.bubbleview_right=(BubbleRelativeLayout)convertView.findViewById(R.id.bubbleview_right);
            viewHold.bubbleview_right.getLayoutParams().width=bubblewidth;
            viewHold.bubbleview_right.setCornerRadius(20, 20, 20, 20);
            viewHold.bubbleview_right.setArrowDirection(BubbleStyle.ArrowDirection.Right);
            viewHold.bubbleview_right.setArrowPosPolicy(BubbleStyle.ArrowPosPolicy.SelfBegin);
            viewHold.bubbleview_right.setArrowPosDelta(10f);
            viewHold.bubbleview_right.setFillColor(0xFFFFFFFF);
            viewHold.content_right.setText(bean.getMessage());
            viewHold.time_right.setText(bean.getMessageTimeLong());
        }
        else
        {
            viewHold.left_layout.setVisibility(View.VISIBLE);
            viewHold.right_layout.setVisibility(View.GONE);
            viewHold.name_left=(TextView)convertView.findViewById(R.id.name_left);
            viewHold.time_left=(TextView)convertView.findViewById(R.id.time_left);
            viewHold.content_left=(TextView)convertView.findViewById(R.id.content_left);
            viewHold.name_left.setText(bean.getSender());
            viewHold.bubbleview_left=(BubbleRelativeLayout)convertView.findViewById(R.id.bubbleview_left);
            viewHold.bubbleview_left.getLayoutParams().width=bubblewidth;
            viewHold.bubbleview_left.setCornerRadius(20, 20, 20, 20);
            viewHold.bubbleview_left.setArrowDirection(BubbleStyle.ArrowDirection.Left);
            viewHold.bubbleview_left.setArrowPosPolicy(BubbleStyle.ArrowPosPolicy.SelfBegin);
            viewHold.bubbleview_left.setArrowPosDelta(10f);
            viewHold.bubbleview_left.setFillColor(0xFFFFFFFF);
            viewHold.content_left.setText(bean.getMessage());
            viewHold.time_left.setText(bean.getMessageTimeLong());
        }



        return convertView;
    }

    class ViewHold
    {
        LinearLayout left_layout;
        RelativeLayout right_layout;
        BubbleRelativeLayout bubbleview_left,bubbleview_right;
        TextView name_left,name_right,content_right,content_left,time_right,time_left;
    }
}
