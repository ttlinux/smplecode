package com.lottery.biying.Activity.RunChart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.RunChartFragment1Adapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.ZoushiBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnChangeSequenceListener;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.Lotteryitem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */
public class Fragment1 extends BaseFragment implements OnChangeSequenceListener{

    ListView listview;
    RunChartFragment1Adapter runChartFragment1Adapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment1_runchart, null);
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(!state && listview!=null)
        {
            setAdapter();
        }
    }

    private void Initview()
    {
        listview=FindView(R.id.listview);
        LinearLayout ll=(LinearLayout)getView();
        RelativeLayout relayout=(RelativeLayout)View.inflate(getActivity(), R.layout.item_lottery_history, null);
        LinearLayout view=(LinearLayout)relayout.findViewById(R.id.mainview);
        view.setBackgroundColor(getResources().getColor(R.color.gray18));
        while (view.getChildCount()>3)
        {
            view.removeViewAt(3);
        }
        ll.addView(relayout, 0);
        ImageView imageView=new ImageView(getActivity());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        imageView.setBackgroundColor(getResources().getColor(R.color.line));
        ll.addView(imageView, 1);
        setAdapter();
    }


    private void setAdapter()
    {
        String sequence=((RunChartActivity)getActivity()).getSequence();

        if(runChartFragment1Adapter==null)
        {
            runChartFragment1Adapter=new RunChartFragment1Adapter(getActivity(),((RunChartActivity)getActivity()).getLotteryResultLists(),sequence);
            listview.setAdapter(runChartFragment1Adapter);
        }
        else
        {
            runChartFragment1Adapter.NotifyData(((RunChartActivity)getActivity()).getLotteryResultLists(),sequence);
        }
    }

    @Override
    public void OnChange() {
        if(runChartFragment1Adapter!=null)
        {
            runChartFragment1Adapter.ClearData();
            setAdapter();
        }
    }


}
