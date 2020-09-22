package com.lottery.biying.Activity.RunChart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.RunChartFragment1Adapter;
import com.lottery.biying.Adapter.RunChartFragment2Adapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.TrendBean;
import com.lottery.biying.bean.ZoushiBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnChangeSequenceListener;
import com.lottery.biying.view.NumberTextview;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/1.
 */
public class Fragment2 extends BaseFragment implements OnChangeSequenceListener {

    ListView listview;
    String Resequence;
    ArrayList<ZoushiBean> zoushiBeans=new ArrayList<>();
    RunChartFragment2Adapter runChartFragment2Adapter;
    LinearLayout hor,hor2;
    int appear[]=new int[10];
    int max[]=new int[10];

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment2_runchart, null);
    }

    public void Initview()
    {
        listview=FindView(R.id.listview);
//        android:dividerHeight="1px"
//        android:divider="@color/line"
        LinearLayout ll=(LinearLayout)getView();
        LinearLayout hor=(LinearLayout)View.inflate(getActivity(), R.layout.fragment_runchart2, null);
        LinearLayout numberlist=(LinearLayout)hor.findViewById(R.id.numberlist);
        for (int i = 0; i <10 ; i++) {
            NumberTextview textview=(NumberTextview)numberlist.getChildAt(i*2);
            textview.setArgs(i + "", NumberTextview.None);
        }
        ll.addView(hor, 0);

        ImageView imageView=new ImageView(getActivity());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        imageView.setBackgroundColor(getResources().getColor(R.color.line));
        ll.addView(imageView, 1);

        RequestData();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(!state && listview!=null)
        {
            RequestData();
        }
    }

    public void setAdapterAndFooterView(boolean refresh)
    {
        String sequence=((RunChartActivity)getActivity()).getSequence();

        if(zoushiBeans.size()==0 || refresh)
        {
            listview.removeFooterView(hor);
            listview.removeFooterView(hor2);
             hor=(LinearLayout)View.inflate(getActivity(), R.layout.fragment_runchart2, null);
            LinearLayout numberlist=(LinearLayout)hor.findViewById(R.id.numberlist);
            TextView indexview1=(TextView)hor.getChildAt(0);
            indexview1.setText("出现次数");
            indexview1.setTextColor(0xFFC639BF);
            for (int i = 0; i <10 ; i++) {
                NumberTextview textview=(NumberTextview)numberlist.getChildAt(i*2);
                textview.setArgs(appear[i] + "", NumberTextview.None);
                textview.setTextColor(0xFFC639BF);
            }
            listview.addFooterView(hor);

             hor2=(LinearLayout)View.inflate(getActivity(), R.layout.fragment_runchart2, null);
            LinearLayout numberlist2=(LinearLayout)hor2.findViewById(R.id.numberlist);
            TextView indexview2=(TextView)hor2.getChildAt(0);
            indexview2.setText("最大连出");
            indexview2.setTextColor(0xFF3B7DCC);
            for (int i = 0; i <10 ; i++) {
                NumberTextview textview=(NumberTextview)numberlist2.getChildAt(i*2);
                textview.setArgs(max[i] + "",NumberTextview.None);
                textview.setTextColor(0xFF3B7DCC);
            }
            listview.addFooterView(hor2);
        }

        setAdapter(sequence);
    }

    public void setAdapter(String sequence)
    {
        if(runChartFragment2Adapter==null)
        {
            runChartFragment2Adapter=new RunChartFragment2Adapter(getActivity(),zoushiBeans,sequence);
            listview.setAdapter(runChartFragment2Adapter);
        }
        else
        {
            runChartFragment2Adapter.NotifyData(zoushiBeans,sequence);
        }
    }

    public void RequestData()
    {
        zoushiBeans.clear();
        setAdapterAndFooterView(true);
        RequestParams requestParams=new RequestParams();
        requestParams.put("lotteryCode",getArguments().getString(BundleTag.id,""));
        requestParams.put("row",50+"");
        Httputils.PostWithBaseUrl(Httputils.Trend,requestParams,new MyJsonHttpResponseHandler(getActivity(),true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))return;
                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray appear_arr=datas.optJSONArray("appear");
                JSONArray max_arr=datas.optJSONArray("max");
                for (int i = 0; i <appear_arr.length(); i++) {
                    appear[i]=appear_arr.optInt(i);
                }
                for (int i = 0; i <max_arr.length(); i++) {
                    max[i]=max_arr.optInt(i);
                }
                JSONArray trends=datas.optJSONArray("trend");
                zoushiBeans.clear();
                for (int i = 0; i <trends.length() ; i++) {
                    JSONObject jsonobj=trends.optJSONObject(i);
                    zoushiBeans.add(TrendBean.Analysis_ZoushiBean(jsonobj));
                }
                setAdapterAndFooterView(true);
            }
        });
    }

    @Override
    public void OnChange() {
        RequestData();
    }
}
