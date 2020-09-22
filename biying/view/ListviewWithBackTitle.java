package com.lottery.biying.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;

/**
 * Created by Administrator on 2018/10/18.
 */
public class ListviewWithBackTitle extends RelativeLayout {

    PullToRefreshListView listView;
    TextView textView;
    String text;

    public void setText(String text) {
        this.text = text;
        if(textView!=null)
        {
            textView.setText(text);
        }
    }


    public ListviewWithBackTitle(Context context) {
        super(context);
        OnIntitview(context);
    }

    public ListviewWithBackTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        OnIntitview(context);
    }

    public ListviewWithBackTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        OnIntitview(context);
    }

    public void OnIntitview(Context context)
    {
         textView=new TextView(context);
        if(text==null || text.length()==0)
        text=context.getResources().getString(R.string.nodataclickonewmore);
        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(rl);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getResources().getColor(R.color.black2));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setVisibility(GONE);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getOnRefreshListener2() != null)
                    listView.getOnRefreshListener2().onPullDownToRefresh(listView);
            }
        });
        addView(textView);

         listView=new PullToRefreshListView(context);
        setFooterDividersEnabled(true,context);
        listView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.getRefreshableView().setSelector(new ColorDrawable(0));
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setDividerPadding(2);
        listView.setDividerDrawable(new ColorDrawable(context.getResources().getColor(R.color.line)));

        addView(listView);
    }

    public void setAdapter(final ListAdapter listAdapter)
    {
        listView.setAdapter(listAdapter);
    }

    public void setDivider( Drawable divider) {
        listView.getRefreshableView().setDivider(divider);
    }

    public void setDividerHeight(int height) {
        listView.getRefreshableView().setDividerHeight(height);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener listener)
    {
        listView.setOnScrollListener(listener);

    }

    public interface OnClickBacktitleListener
    {
        public void Onclick();
    }

    public void ShowTextview()
    {
        textView.setVisibility(VISIBLE);
        listView.setVisibility(GONE);
    }
    public void ShowListview()
    {
        textView.setVisibility(GONE);
        listView.setVisibility(VISIBLE);
    }

    public void setOnRefreshListener(PullToRefreshBase.OnRefreshListener2<ListView> listener)
    {
        listView.setOnRefreshListener(listener);
    }

    public void onRefreshComplete()
    {
        listView.onRefreshComplete();
    }

    public void setFooterDividersEnabled(boolean state,Context context)
    {
        ImageView imageView=new ImageView(context);
        imageView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        imageView.setBackground(new ColorDrawable(context.getResources().getColor(R.color.line)));
//        listView.getRefreshableView().addFooterView(imageView);
//        listView.getRefreshableView().setFooterDividersEnabled(state);
        listView.getRefreshableView().setOverscrollFooter(null);
    }

    public void setMode(PullToRefreshBase.Mode mode )
    {
        listView.setMode(mode);
    }
}
