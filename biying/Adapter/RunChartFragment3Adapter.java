package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.HeaderBean;
import com.lottery.biying.bean.Hou3ZoushiBean;
import com.lottery.biying.bean.TrendBean;
import com.lottery.biying.bean.TrendChartBean;
import com.lottery.biying.bean.ZoushiBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.view.MyHorScrollView;
import com.lottery.biying.view.MyRedTextView;
import com.lottery.biying.view.NumberTextview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */
public class RunChartFragment3Adapter extends RecyclerView.Adapter<RunChartFragment3Adapter.MyHolder> {

    Context context;
    ArrayList<TrendChartBean> trendcharbeans;
    ArrayList<HeaderBean> hbeans;
    int index;
    ArrayList<MyHorScrollView> myscrolls = new ArrayList<>();
    int X = 0;
    OnMoveItemListener onmovelistener;
    int numwidth = 0;


    public ArrayList<MyHorScrollView> getMyscrolls() {
        return myscrolls;
    }

    public void setMyscrolls(ArrayList<MyHorScrollView> myscrolls) {
        this.myscrolls = myscrolls;
    }

    public void setOnmovelistener(OnMoveItemListener onmovelistener) {
        this.onmovelistener = onmovelistener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_runchart2, parent, false);

        final MyHolder hold = new MyHolder(view);
        myscrolls.add(hold.myscrollview);
        hold.myscrollview.post(new Runnable() {
            @Override
            public void run() {
                hold.myscrollview.MoveToX(X);
            }
        });
        return hold;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        TrendChartBean bean = trendcharbeans.get(position);
        holder.qici.setText(bean.getQsFormat());
        holder.itemView.setBackgroundColor(position % 2 > 0 ? context.getResources().getColor(R.color.gray18) : 0xFFFFFFFF);
        holder.myscrollview.setTag(position);
        holder.myscrollview.setOnScrollChanged(new MyHorScrollView.onScrollChanged() {
            @Override
            public void OnScroll(MyHorScrollView view, int newX) {
                int index = (int) view.getTag();
                X = newX;
                if (onmovelistener != null)
                    onmovelistener.Onmove(X);
                for (int i = 0; i < myscrolls.size(); i++) {
                    MyHorScrollView myhorscrollview = myscrolls.get(i);
                    int tag = (int) myhorscrollview.getTag();
                    if (tag != index) {
                        myhorscrollview.MoveToX(X);
                    }
                }
            }
        });
        holder.myscrollview.MoveToX(X);

        if (holder.ll.getChildCount() == 0) {
            makeView(holder.ll, bean);
        } else {
            RelativeLayout relativeLayout = (RelativeLayout) holder.ll.getChildAt(0);
            if (bean.getIsOpen() == 0) {
                relativeLayout.getChildAt(0).setVisibility(View.VISIBLE);
                relativeLayout.getChildAt(1).setVisibility(View.GONE);
            } else {
                relativeLayout.getChildAt(0).setVisibility(View.GONE);
                relativeLayout.getChildAt(1).setVisibility(View.VISIBLE);
                LinearLayout mainll = (LinearLayout) relativeLayout.getChildAt(1);
                if (mainll.getChildCount() == 0) {
                    Additem(mainll, bean);
                }
                int iter = 0;
                for (int i = 0; i < mainll.getChildCount(); i++) {
                    if (i % 2 > 0) {
                        MyRedTextView text = (MyRedTextView) mainll.getChildAt(i);
                        text.setText(bean.getYilou().get(index).get(iter));
                        text.setOriginalColor(context.getResources().getColor(R.color.text2));
                        if (bean.getOpenCode() != null && hbeans.get(index).getBall().get(iter).equalsIgnoreCase(bean.getOpenCode().get(index))) {
                            text.setIsred(true);
                        } else {
                            text.setIsred(false);
                        }
                        iter++;
                    }
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        return trendcharbeans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public RunChartFragment3Adapter(Context context, ArrayList<TrendChartBean> trendcharbeans, ArrayList<HeaderBean> hbeans, int index) {
        this.context = context;
        this.trendcharbeans = trendcharbeans;
        this.index = index;
        this.hbeans = hbeans;
        setHasStableIds(true);
    }

    public void NotifyData(ArrayList<TrendChartBean> trendcharbeans, ArrayList<HeaderBean> hbeans, int index) {
        if (trendcharbeans == null) {
            trendcharbeans.clear();
        } else
            this.trendcharbeans = trendcharbeans;

        if (hbeans == null)
            hbeans.clear();
        else
            this.hbeans = hbeans;
        this.index = index;
        for (int i = 0; i < getItemCount(); i++) {
            notifyItemChanged(i);
        }
    }

    public void ClearData() {
        notifyItemRangeRemoved(0, getItemCount());
        hbeans.clear();
        trendcharbeans.clear();
    }

//    @Override
//    public int getCount() {
//        return trendcharbeans.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        Holder holder;
//        if (convertView == null) {
//            convertView = View.inflate(context, R.layout.fragment_runchart2, null);
//            holder = new Holder();
//            convertView.setTag(holder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }
//
//        holder.ll = (LinearLayout) convertView;
//        int color = position % 2 == 0 ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.gray18);
//        convertView.setBackgroundColor(color);
//        int bindex = sequence == BundleTag.Desc ? position : getCount() - 1 - position;
//        TextView textview = (TextView) holder.ll.getChildAt(0);
//        textview.setText(hou3ZoushiBeans.getArrayList().get(bindex).getQsFormat());
//
//
//        LinearLayout numberlist = (LinearLayout) holder.ll.findViewById(R.id.numberlist);
//        TextView view = (TextView)holder.ll.findViewById(R.id.placehold);
//        if (!hou3ZoushiBeans.getArrayList().get(bindex).getIsOpen().equalsIgnoreCase("1")) {
//            numberlist.setVisibility(View.GONE);
//            view.setVisibility(View.VISIBLE);
//            view.setText(hou3ZoushiBeans.getArrayList().get(bindex).getOpenMessage());
//        } else {
//            numberlist.setVisibility(View.VISIBLE);
//            view.setVisibility(View.GONE);
//            for (int i = 0; i < 10; i++) {
//                NumberTextview ntextview = (NumberTextview) numberlist.getChildAt(2 * i);
//                ntextview.setTextColor(0xFFBFB6A2);
//                List<TrendBean.NumsBean> numsbean = hou3ZoushiBeans.getArrayList().get(bindex).getNums();
//                    ntextview.setArgs(numsbean.get(i).getYl(), numsbean.get(i).getCount(), NumberTextview.HousanZoushi);
////                    ntextview.setArgs("", -1, NumberTextview.HousanZoushi);
//            }
//        }
//
//
//        return convertView;
//    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout ll;
        MyHorScrollView myscrollview;
        TextView qici;

        public MyHolder(View itemView) {
            super(itemView);
            myscrollview = (MyHorScrollView) itemView.findViewById(R.id.myscrollview);
            ll = (LinearLayout) itemView.findViewById(R.id.numberlist);
            qici = (TextView) itemView.findViewById(R.id.qici);
        }
    }

    public void makeView(LinearLayout ll, TrendChartBean bean) {
        int padding = ScreenUtils.getDIP2PX(context, 3);
        int rednum = 0;
        if (bean.getOpenCode() != null && bean.getOpenCode().size() > 0) {
            for (int i = 0; i <hbeans.get(index).getBall().size() ; i++) {
                if(hbeans.get(index).getBall().get(i).equalsIgnoreCase(bean.getOpenCode().get(index)))
                {
                    rednum=i;
                    break;
                }
            }
        }
        numwidth = ScreenUtils.getScreenWH((Activity) context)[0] - ScreenUtils.getDIP2PX(context, 70) - bean.getYilou().get(index).size();
        if (bean.getIsOpen() == 1)
            numwidth = numwidth / bean.getYilou().get(index).size() >= ScreenUtils.getDIP2PX(context, 30) ? numwidth / bean.getYilou().get(index).size()
                    : ScreenUtils.getDIP2PX(context, 30);

//            long color = Integer.parseInt(hou3titles_color[j], 16) + 0xFFFFFFFFFF000000l;

        LinearLayout templayout = new LinearLayout(context);
        templayout.setOrientation(LinearLayout.HORIZONTAL);
        templayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        templayout.setVisibility(View.GONE);

        ImageView line = new ImageView(context);
        line.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        line.setBackgroundColor(context.getResources().getColor(R.color.line));
        templayout.addView(line);

        TextView waitforopen = new TextView(context);
        waitforopen.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getScreenWH((Activity) context)[0] - ScreenUtils.getDIP2PX(context, 70), ViewGroup.LayoutParams.MATCH_PARENT));
        waitforopen.setGravity(Gravity.CENTER);
        waitforopen.setText("等待开奖");
        waitforopen.setTextColor(0xFFEE1F3B);
        templayout.addView(waitforopen);


        LinearLayout mainlayout = new LinearLayout(context);
        mainlayout.setOrientation(LinearLayout.HORIZONTAL);
        mainlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainlayout.setVisibility(View.GONE);

        for (int i = 0; i < bean.getYilou().get(index).size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundColor(context.getResources().getColor(R.color.line));
            mainlayout.addView(imageView);

            MyRedTextView textview = new MyRedTextView(context);
            textview.setPadding(padding, padding, padding, padding);
            textview.setLayoutParams(new ViewGroup.LayoutParams(numwidth, ScreenUtils.getDIP2PX(context, 30)));
            textview.setText(bean.getYilou().get(index).get(i));
            textview.setTextColor(context.getResources().getColor(R.color.text2));
            textview.setGravity(Gravity.CENTER);
            textview.setOriginalColor(context.getResources().getColor(R.color.text2));
            if (rednum == i) {
                textview.setIsred(true);
            } else {
                textview.setIsred(false);
            }

            mainlayout.addView(textview);
        }

        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(templayout);
        layout.addView(mainlayout);
        ll.addView(layout);
        if (bean.getIsOpen() == 0) {
            templayout.setVisibility(View.VISIBLE);
            mainlayout.setVisibility(View.GONE);
        } else {
            templayout.setVisibility(View.GONE);
            mainlayout.setVisibility(View.VISIBLE);
        }
    }

    public interface OnMoveItemListener {
        public void Onmove(int X);
    }

    private void Additem(LinearLayout mainlayout, TrendChartBean bean) {
        numwidth = ScreenUtils.getScreenWH((Activity) context)[0] - ScreenUtils.getDIP2PX(context, 70) - bean.getYilou().get(index).size();
        if(bean.getIsOpen()==1)
            numwidth = numwidth / bean.getYilou().get(index).size() >= ScreenUtils.getDIP2PX(context, 30) ? numwidth / bean.getYilou().get(index).size()
                    : ScreenUtils.getDIP2PX(context, 30);
        int rednum = 0;
        if (bean.getOpenCode() != null && bean.getOpenCode().size() > 0) {
            for (int i = 0; i <hbeans.get(index).getBall().size() ; i++) {
                if(hbeans.get(index).getBall().get(i).equalsIgnoreCase(bean.getOpenCode().get(index)))
                {
                    LogTools.e("qqqqqq",bean.getOpenCode().get(index)+"  "+i);
                    rednum=i;
                    break;
                }
            }
//            rednum = Integer.valueOf(bean.getOpenCode().get(index));
        }
        int padding = ScreenUtils.getDIP2PX(context, 3);
        for (int i = 0; i < bean.getYilou().get(index).size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundColor(context.getResources().getColor(R.color.line));
            mainlayout.addView(imageView);

            MyRedTextView textview = new MyRedTextView(context);
            textview.setPadding(padding, padding, padding, padding);
            textview.setLayoutParams(new ViewGroup.LayoutParams(numwidth, ScreenUtils.getDIP2PX(context, 30)));
            textview.setText(bean.getYilou().get(index).get(i));
            textview.setTextColor(context.getResources().getColor(R.color.text2));
            textview.setGravity(Gravity.CENTER);
            textview.setOriginalColor(context.getResources().getColor(R.color.text2));
            if (rednum == i) {
                textview.setIsred(true);
            } else {
                textview.setIsred(false);
            }
            mainlayout.addView(textview);
        }
    }
}
