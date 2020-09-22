package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lottery.biying.Activity.User.AccountManage.BankCardManage.ModifyBankCardActivity;
import com.lottery.biying.Activity.User.WithDraw.WithDrawDetailActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.WithDrawBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.RepluginMethod;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */
public class withdrawListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<WithDrawBean> jsonObjects;
    String type;//1是提现2是绑定银行卡
    private ImageLoader mImageDownLoader;

    public withdrawListAdapter(Context context, ArrayList<WithDrawBean> jsonObjects, String type) {
        this.context = context;
        this.jsonObjects = jsonObjects;
        this.type = type;
        mImageDownLoader = RepluginMethod.getApplication(context)
                .getImageLoader();
    }

    public void Notify(ArrayList<WithDrawBean> jsonObjects, String type) {
        this.jsonObjects = jsonObjects;
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jsonObjects.size();
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
        final ViewHolder holder;
        final WithDrawBean bean;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_listwithdraw_layout, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bankname = (TextView) convertView.findViewById(R.id.bankname);
        holder.banknumber = (TextView) convertView.findViewById(R.id.banknumber);
        holder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
        holder.img = (ImageView) convertView.findViewById(R.id.img);

        bean = jsonObjects.get(position);
        holder.bankname.setText(bean.getBankCnName());
//        holder.textView3.setText(bean.getUserRealName());
        holder.banknumber.setText(bean.getBankCard());
        mImageDownLoader.displayImage(bean.getBigPicUrl(), holder.img);
        holder.setPosition(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=((ViewHolder)v.getTag()).getPosition();
                if (type.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(context, WithDrawDetailActivity.class);
                    intent.putExtra(BundleTag.Data,new Gson().toJson(jsonObjects.get(position)));
                    context.startActivity(intent);
                } else {
                   Intent intent = new Intent(context, ModifyBankCardActivity.class);
                    intent.putExtra(BundleTag.Data,new Gson().toJson(jsonObjects.get(position)));
                    ((Activity)context).startActivityForResult(intent, BundleTag.RequestCode);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView bankname, banknumber, textView3;
        ImageView img;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
