package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.Change.ChangeintoActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PlatformBean;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Change_Adapter extends BaseAdapter {
    private Context mContext;
    public static   ArrayList<PlatformBean> platformBeans;
    ImageLoader imageDownloader;
    public Change_Adapter(Context context, ArrayList<PlatformBean> platformBeans) {
        this.mContext = context;

        this.platformBeans = platformBeans;
        imageDownloader = RepluginMethod.getApplication(context)
                .getImageLoader();
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return platformBeans.size();
    }
   public void   notifyData(ArrayList<PlatformBean> platformBeans){
       if(platformBeans==null)
           this.platformBeans.clear();
       else {
           if (this.platformBeans != null && this.platformBeans.size() > 0) {
               for (int i = 0; i < platformBeans.size(); i++) {
                   ArrayList<PlatformBean> beans = platformBeans;
                   beans.get(i).setState(this.platformBeans.get(i).isState());
                   if(this.platformBeans.get(i).isState()){
                       GetPlatform(platformBeans.get(i).getFlat(),i);
                   }
               }


           } else {

           }
       }
           this.platformBeans = platformBeans;
            notifyDataSetChanged();
   }
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return platformBeans.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final int index =position;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.change_item_layout, null);

//            holder.change_name = (TextView) convertView.findViewById(R.id.platformchange);
//            holder.change_into = (Button) convertView.findViewById(R.id.changeinto);
//            holder.change_out = (Button) convertView.findViewById(R.id.changeout);
//            holder.change_balance = (TextView) convertView.findViewById(R.id.balance);
//            holder.change_balance.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            convertView.setTag(holder);
//            holder.change_balance.setTag(position);
//            holder.change_into.setTag(position);
//            holder.change_out.setTag(position);
        } else {
            holder = (ViewHolder) convertView.getTag();
//            holder.change_balance.setTag(position);
//            holder.change_into.setTag(position);
//            holder.change_out.setTag(position);
        }
        holder.change_name = (TextView) convertView.findViewById(R.id.platformchange);
        holder.change_into = (Button) convertView.findViewById(R.id.changeinto);
        holder.change_out = (Button) convertView.findViewById(R.id.changeout);
        holder.change_balance = (TextView) convertView.findViewById(R.id.balance);
//        holder.change_balance.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.change_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             GetPlatform(platformBeans.get(index).getFlat(),index);
            }
        });
        holder.change_balance.setText(platformBeans.get(index).getJine());

        holder.change_name.setText(platformBeans.get(position).getFlatName());
        holder.change_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChangeintoActivity.class);
                intent.putExtra("title", platformBeans.get(index).getFlatName());
                intent.putExtra("type", "1");
                intent.putExtra("flat", platformBeans.get(index).getFlat());
                intent.putExtra("index",index+"");
                ((Activity) mContext).startActivityForResult(intent, 888);
            }
        });
        holder.change_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChangeintoActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("title", platformBeans.get(index).getFlatName());
                intent.putExtra("flat", platformBeans.get(index).getFlat());
                intent.putExtra("index",index+"");
                ((Activity) mContext).startActivityForResult(intent, 888);
            }
        });

        holder.title_img=(ImageView)convertView.findViewById(R.id.title_img);
        imageDownloader.displayImage(platformBeans.get(position).getSmallPic(), holder.title_img);
        return convertView;
    }

    class ViewHolder {
        TextView change_name, change_balance;
        Button change_into, change_out;
        ImageView title_img;
    }
      String yue="0";
    private  void GetPlatform(String flat,final int indexd) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", RepluginMethod.getApplication(mContext).getBaseapplicationUsername());
        requestParams.put("flat", flat);
        Httputils.PostWithBaseUrl(Httputils.balancemList, requestParams, new MyJsonHttpResponseHandler((Activity) mContext, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(mContext,jsonObject.optString("msg",""));
                    return;
                }

                LogTools.e("ddddsfafadsfas", jsonObject.toString());
                yue = jsonObject.optString("datas");
                platformBeans.get(indexd).setState(true);
                platformBeans.get(indexd).setJine(Httputils.setjiage(yue));
                notifyDataSetChanged();
            }
        });
    }
}