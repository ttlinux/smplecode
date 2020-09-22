package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.Activity.User.TopUp.Pay_formActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PayBankCardBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pay_Adapter extends BaseAdapter {
    private Context mContext;
    ArrayList<PayBankCardBean.PayBankCardBean1> arrayList;
    SparseArray<CheckBox> checkBoxSparseArray=new SparseArray<CheckBox>();
    ImageLoader imageLoader;
    int index=-1;
    public Pay_Adapter(Context context, ArrayList arrayList) {
        this.mContext = context;
        this.arrayList=arrayList;
        imageLoader= RepluginMethod.getApplication(context).getImageLoader();
    }

    public void NotifyAdapter(ArrayList arrayList)
    {
        this.arrayList=arrayList;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.into_pay_item_layout, null);
            convertView.setTag(holder);
        }   else{
        holder= (ViewHolder) convertView.getTag();
    }

        holder.textView = (TextView) convertView.findViewById(R.id.textView);//1
        holder.account = (TextView) convertView.findViewById(R.id.account);//2
        holder.name= (TextView) convertView.findViewById(R.id.name);//2
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type= 0;
                    type = Integer.valueOf(arrayList.get(position).getPayType());
                switch (arrayList.get(position).getPayType()) {
                    case "1"://compayWx
                        HttpforNoticeinbottom.hashMap2.put("compayWx", arrayList.get(position).getRemark());
                        break;
                    case "2":
                        HttpforNoticeinbottom.hashMap2.put("compayAlipay", arrayList.get(position).getRemark());
                        break;
                    case "3":
                        HttpforNoticeinbottom.hashMap2.put("compayTenpay", arrayList.get(position).getRemark());
                        break;
                    case "4":
                        HttpforNoticeinbottom.hashMap2.put("compayBank", arrayList.get(position).getRemark());
                        break;
                    case "5":
                        HttpforNoticeinbottom.hashMap2.put("compayWeb", arrayList.get(position).getRemark());
                        break;
                }
//                    if(type==5)
//                    {
//                        Intent intent=  new Intent(mContext, webActivity.class);
//                        intent.putExtra(BundleTag.title,"网页支付");
//                        intent.putExtra(BundleTag.IntentTag,true);
//                        intent.putExtra(BundleTag.URL,arrayList.optJSONObject(position).optString("payLink",""));
//                        mContext.startActivity(intent);
//                    }
//                    else
//                    {
                        Intent intent=  new Intent(mContext, Pay_formActivity.class);
                        intent.putExtra(BundleTag.Data,new Gson().toJson(arrayList.get(position)));
                        intent.putExtra(BundleTag.Platform,type+"");
                        mContext.startActivity(intent);
//                    }


            }
        });
//        holder.changebox = (TextView) convertView.findViewById(R.id.changebox);
//        holder.changebox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    for (int i = 0; i <checkBoxSparseArray.size(); i++) {
//                        checkBoxSparseArray.get(i).setChecked(false);
//                    }
//                    buttonView.setChecked(true);
//                    index=position;
//                }
//                else
//                {
//                    index=-1;
//                }
//            }
//        });
//            checkBoxSparseArray.put(position,holder.changebox);
        holder.img= (ImageView) convertView.findViewById(R.id.img);//1
        imageLoader.displayImage(arrayList.get(position).getBigPic(), holder.img, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                int width=bitmap.getWidth()>70?bitmap.getWidth():70;
//                int height=bitmap.getHeight()>70?bitmap.getHeight():70;
                if(bitmap==null)return;
                Bitmap resizeBmp = bitmap;
                    Matrix matrix = new Matrix();
                    float ma= ScreenUtils.getDIP2PX(mContext, 40)*0.01f/bitmap.getWidth() *100;
                    matrix.postScale(ma, ma); //长和宽放大缩小的比例
                    resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


                ImageView imageView = (ImageView) view;
//                Drawable drawable=new BitmapDrawable(bitmap);
//                drawable.setBounds(0,0,200,200);
                imageView.setImageBitmap(resizeBmp);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        holder.textView.setText(arrayList.get(position).getBankType());
        int PayType=Integer.valueOf(arrayList.get(position).getPayType());
        String prefix="";
        switch (PayType)
        {
            case 1:
            case 2:
            case 3:
                prefix=arrayList.get(position).getBankUser();
                break;
            case 4:
                prefix=arrayList.get(position).getBankUser();
                break;
            case 5:
                prefix="";
                break;
        }
        holder.account.setText(arrayList.get(position).getBankCard());
        holder.name.setText(prefix);
        return convertView;
    }
        class ViewHolder {
        TextView textView, account,name;
            ImageView img;
    }

    public int getselectIndex()
    {
        return index;
    }
}
