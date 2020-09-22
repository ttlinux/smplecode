package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.RepluginMethod;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/9.
 */
public class WithDrawBankListAdapter extends BaseAdapter {

    public JSONArray jsonObjects;
    public Context context;
    ImageLoader imageLoader;

    public WithDrawBankListAdapter(JSONArray jsonObjects, Context context) {
        this.context = context;
        this.jsonObjects = jsonObjects;
        imageLoader = RepluginMethod.getApplication(context).getImageLoader();
    }

    @Override
    public int getCount() {
        return jsonObjects.length();
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
        final Viewhold viewhold;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_withdraw_banklist, null);
            viewhold = new Viewhold();
            convertView.setTag(viewhold);
        } else {
            viewhold = (Viewhold) convertView.getTag();
        }
        viewhold.setPosition(position);

        LinearLayout ll = (LinearLayout) convertView;
        viewhold.imageView = (ImageView) ll.getChildAt(0);
        viewhold.textView = (TextView) ll.getChildAt(1);
        imageLoader.displayImage(jsonObjects.optJSONObject(position).optString("bigPicUrl", ""), viewhold.imageView);
        viewhold.textView.setText(jsonObjects.optJSONObject(position).optString("bankCnName", ""));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = ((Viewhold) v.getTag()).getPosition();
                Intent intent = new Intent();
                intent.putExtra(BundleTag.Data, jsonObjects.optJSONObject(position).toString());
                ((Activity) context).setResult(BundleTag.ResultCode, intent);
                ((Activity) context).finish();

            }
        });
        return convertView;
    }

    public class Viewhold {
        ImageView imageView;
        TextView textView;
        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
