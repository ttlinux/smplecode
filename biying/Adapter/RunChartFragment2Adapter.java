package com.lottery.biying.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.ZoushiBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.view.NumberTextview;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/5.
 */
public class RunChartFragment2Adapter extends BaseAdapter
{
    Context context;
    ArrayList<ZoushiBean> zoushiBeans;
    String sequence;

    public RunChartFragment2Adapter(Context context,ArrayList<ZoushiBean> zoushiBeans,String sequence)
    {
        this.context=context;
        this.zoushiBeans=zoushiBeans;
        this.sequence=sequence;
    }

    public void NotifyData(ArrayList<ZoushiBean> zoushiBeans,String sequence)
    {
        this.zoushiBeans=zoushiBeans;
        this.sequence=sequence;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return zoushiBeans.size();
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
//        Holder holder;
//        if(convertView==null)
//        {
//            convertView=View.inflate(context, R.layout.fragment_runchart2,null);
//            holder=new Holder();
//            convertView.setTag(holder);
//        }
//        else
//        {
//            holder=(Holder)convertView.getTag();
//        }
//
//        holder.ll=(LinearLayout)convertView;
//        int color = position % 2 == 0 ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.gray18);
//        convertView.setBackgroundColor(color);
//        int bindex=sequence== BundleTag.Desc?position:getCount()-1-position;
//        TextView textview=(TextView)holder.ll.getChildAt(0);
//        textview.setText(zoushiBeans.get(bindex).getQishu());
//
//
//
//        LinearLayout numberlist=(LinearLayout)holder.ll.findViewById(R.id.numberlist);
//        TextView view=(TextView)holder.ll.findViewById(R.id.placehold);
//        if(zoushiBeans.get(bindex).getStatus()<1)
//        {
//            numberlist.setVisibility(View.GONE);
//            view.setVisibility(View.VISIBLE);
//            view.setText(zoushiBeans.get(bindex).getOpenMessage());
//        }
//        else
//        {
//            numberlist.setVisibility(View.VISIBLE);
//            view.setVisibility(View.GONE);
//            for (int i = 0; i <10 ; i++) {
//                NumberTextview ntextview=(NumberTextview)numberlist.getChildAt(2*i);
//                ntextview.setArgs("",-1);
//            }
//            for (int i = 0; i < zoushiBeans.get(bindex).getIndexs().size(); i++) {
//                int index=zoushiBeans.get(bindex).getIndexs().get(i);
//                int count=zoushiBeans.get(bindex).getSnAmount().get(i);
//                NumberTextview ntextview=(NumberTextview)numberlist.getChildAt(2*(index));
//                ntextview.setArgs(index+"",count);
//            }
//        }
//

        return convertView;
    }

    class Holder
    {
        NumberTextview numberTextview0,
                numberTextview1,
                numberTextview2,
                numberTextview3,
                numberTextview4,
                numberTextview5,
                numberTextview6,
                numberTextview7,
                numberTextview8,
                numberTextview9;
        LinearLayout ll;
    }
}
