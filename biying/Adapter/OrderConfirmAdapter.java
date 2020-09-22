package com.lottery.biying.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.util.HandleLotteryOrder;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.view.AppAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/11.
 */
public class OrderConfirmAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Itemvalue> ivs;
    OnDeleteListener onDeleteListener;
    ViewHolder viewHolder;
    SparseArray<View> views=new SparseArray<>();

    public OrderConfirmAdapter(Activity activity,ArrayList<Itemvalue> ivs)
    {
        this.activity=activity;
        this.ivs=ivs;
    }

    public ArrayList<Itemvalue> getIvs() {
        return ivs;
    }

    public void setIvs(ArrayList<Itemvalue> ivs) {
        this.ivs = ivs;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public void Notify(ArrayList<Itemvalue> ivs)
    {
        this.ivs=ivs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ivs.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=View.inflate(activity, R.layout.item_order_confirm,null);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView v1 = (TextView) v.findViewById(R.id.value1);
                Layout l = v1.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
                    if (lines > 0)
                        if (l.getEllipsisCount(lines - 1) > 0) {
                            AlertDialog4(ivs.get(position).getItemvalueV1());
                        }
                }
            }
        });
        views.put(position,convertView);


        viewHolder.delete=(ImageView)convertView.findViewById(R.id.delete);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteListener!=null)
                    onDeleteListener.Ondelete(position);
            }
        });
        viewHolder.value1=(TextView)convertView.findViewById(R.id.value1);
        viewHolder.value2=(TextView)convertView.findViewById(R.id.value2);
        viewHolder.value1.setText(ivs.get(position).getItemvalueV1());
        viewHolder.value2.setText(ivs.get(position).getItemvalueO1() + " " + ivs.get(position).getItemvalueO2());


        if(position==ivs.size()-1)
            convertView.setBackground(activity.getResources().getDrawable(R.drawable.bottom_pic));
        else
            convertView.setBackground(activity.getResources().getDrawable(R.drawable.item_pic));

        return convertView;
    }

    public static class ViewHolder{
        ImageView delete;
        TextView value1,value2;
    }
    public static class Itemvalue
    {
        String itemvalueV1;
        String itemvalueO1;
        String itemvalueO2;
        String playtype_str;//三级code
        String titlecode;//二级code
        JSONObject info;
        int Times;//倍数
        double unit;
        int Bettimes;//注数
        String lotterytype;//一级code
        int playtype;
        int BounsType;

        public int getBounsType() {
            return BounsType;
        }

        public void setBounsType(int bounsType) {
            BounsType = bounsType;
        }

        public double getUnit() {
            return unit;
        }

        public void setUnit(double unit) {
            this.unit = unit;
        }

        public int getBettimes() {
            return Bettimes;
        }

        public void setBettimes(int bettimes) {
            Bettimes = bettimes;
        }

        public String getTitlecode() {
            return titlecode;
        }

        public void setTitlecode(String titlecode) {
            this.titlecode = titlecode;
        }

        public JSONObject getInfo() {
            return info;
        }

        public void setInfo(JSONObject info) {
            this.info = info;
        }

        public String getPlaytype_str() {
            return playtype_str;
        }

        public void setPlaytype_str(String playtype_str) {
            this.playtype_str = playtype_str;
        }

        public int getPlaytype() {
            return playtype;
        }

        public void setPlaytype(int playtype) {
            this.playtype = playtype;
        }

        public String getLotterytype() {
            return lotterytype;
        }

        public void setLotterytype(String lotterytype) {
            this.lotterytype = lotterytype;
        }

        public int getTimes() {
            return Times;
        }

        public void setTimes(int times) {
            Times = times;
        }

        public String getItemvalueO1() {
            return itemvalueO1;
        }

        public void setItemvalueO1(String itemvalueO1) {
            this.itemvalueO1 = itemvalueO1;
        }

        public String getItemvalueV1() {
            return itemvalueV1;
        }

        public void setItemvalueV1(String itemvalueV1) {
            this.itemvalueV1 = itemvalueV1;
        }

        public String getItemvalueO2() {
            return itemvalueO2;
        }

        public void setItemvalueO2(String itemvalueO2) {
            this.itemvalueO2 = itemvalueO2;
        }

        public static Itemvalue Itemvalue_analysis(JSONObject jsonObject)
        {
            Itemvalue iv=new Itemvalue();
            iv.setItemvalueV1(jsonObject.optString("itemvalueV1",""));
            iv.setItemvalueO2(jsonObject.optString("itemvalueO1",""));
            iv.setItemvalueO1(jsonObject.optString("itemvalueO2",""));
            return iv;
        }
    }

    public interface OnDeleteListener
    {
        public void Ondelete(int index);
    }

    private void AlertDialog4(String string) {
        AppAlertDialog dialog = new AppAlertDialog(activity);
        dialog.setTitle("号码");
        dialog.setConetnt(string);
        dialog.setStyle();
        dialog.setConfirmstr("确定");
        dialog.show();
        dialog.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
            @Override
            public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                dialog.hide();
            }
        });
    }
}
