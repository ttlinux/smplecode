package com.lottery.biying.Activity.RunChart;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.RunChartFragment2Adapter;
import com.lottery.biying.Adapter.RunChartFragment3Adapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.FooterBean;
import com.lottery.biying.bean.HeaderBean;
import com.lottery.biying.bean.Hou3ZoushiBean;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.TrendBean;
import com.lottery.biying.bean.TrendChartBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnChangeSequenceListener;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.view.MyHorScrollView;
import com.lottery.biying.view.MyRecycleView;
import com.lottery.biying.view.NumberTextview;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */
public class Fragment3 extends BaseFragment implements OnChangeSequenceListener {

    RadioGroup radiogroup;
    View titleview;
    MyRecycleView listview;
    RunChartFragment3Adapter runChartFragment3Adapter;
    RadioButton Record_button;
    String hou3titles[],hou3titles_color[];
    int pageindex=-1;
    FooterBean beans[];
    ArrayList<HeaderBean> hbeans=new ArrayList<>();
    ArrayList<TrendChartBean> trendcharbeans=new ArrayList<>();
    MyHorScrollView myscrollview;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment3_runchart, null);
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(!state)
        {
            RequestData();
        }
        if(getActivity()!=null && state)
        {
            if (runChartFragment3Adapter != null)
                runChartFragment3Adapter.ClearData();
        }
    }

    private void Initview()
    {
        radiogroup=FindView(R.id.radiogroup);
        listview=FindView(R.id.listview);
        listview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listview.setLayoutManager(mLayoutManager);

        hou3titles=getActivity().getResources().getStringArray(R.array.hou3titles);
        hou3titles_color=getActivity().getResources().getStringArray(R.array.hou3titles_color);


    }

    public void RequestData()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("lotteryCode",getArguments().getString(BundleTag.id,""));
        Httputils.PostWithBaseUrl(Httputils.TrendChart, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("kkkk", jsonObject.toString());

//                if (runChartFragment3Adapter != null)
//                    runChartFragment3Adapter.ClearData();

                hbeans.clear();
                beans = null;
                trendcharbeans.clear();


                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                JSONObject datas = jsonObject.optJSONObject("datas");

                JSONArray head = datas.optJSONArray("head");
                for (int i = 0; i < head.length(); i++) {
                    JSONObject jsobj = head.optJSONObject(i);
                    hbeans.add(HeaderBean.Analysis(jsobj));
                }

                JSONArray total = datas.optJSONArray("total");
                beans = new FooterBean[4];
                for (int i = 0; i < beans.length; i++) {
                    JSONObject jsobj = total.optJSONObject(i);
                    beans[i] = FooterBean.Analysis(jsobj);
                }

                JSONArray list = datas.optJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    trendcharbeans.add(TrendChartBean.Analysis(list.optJSONObject(i)));
                }

                InitRadioGroup();
            }
        });
    }

    public void InitRadioGroup()
    {
        radiogroup.removeAllViews();
        int padding=ScreenUtils.getDIP2PX(getActivity(),6);
        int width=(ScreenUtils.getScreenWH(getActivity())[0]-ScreenUtils.getDIP2PX(getActivity(),70)-hbeans.size())/hbeans.size();
        for (int i = 0; i <hbeans.size() ; i++) {
            RadioButton radiobtn=new RadioButton(getActivity());
            LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            radiobtn.setLayoutParams(ll);
            radiobtn.setPadding(padding, padding, padding, padding);
            radiobtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            radiobtn.setButtonDrawable(new ColorDrawable(0));
            radiobtn.setGravity(Gravity.CENTER);
            radiobtn.setTextColor(getResources().getColor(R.color.text2));
            radiobtn.setTag(i);
            radiobtn.setText(hbeans.get(i).getTitle());
            radiobtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        pageindex = (int) buttonView.getTag();
                        AddHeadView(hbeans.get(pageindex).getBall());
                        setAdapter();
                        buttonView.setTextColor(getResources().getColor(R.color.loess4));
                    } else {
                        buttonView.setTextColor(getResources().getColor(R.color.text2));
                    }
                }
            });

            radiogroup.addView(radiobtn);

            ImageView image=new ImageView(getActivity());
            image.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            image.setBackgroundColor(getResources().getColor(R.color.line));
            radiogroup.addView(image);
        }

        ((RadioButton)radiogroup.getChildAt(0)).setChecked(true);
    }

    public void AddHeadView(List<String> nums)
    {
        LinearLayout ll=(LinearLayout)getView();
        if(ll.getChildCount()>3)
        {
            ll.removeViewAt(2);//去掉位数
            ll.removeViewAt(2);//去掉位数下面的 线
        }

        View view=View.inflate(getActivity(),R.layout.fragment_runchart2,null);
        myscrollview=(MyHorScrollView)view.findViewById(R.id.myscrollview);
        myscrollview.setOnScrollChanged(new MyHorScrollView.onScrollChanged() {
            @Override
            public void OnScroll(MyHorScrollView view, int newX) {
                if(runChartFragment3Adapter!=null)
                {
                    ArrayList<MyHorScrollView> myHorScrollViewArrayList=runChartFragment3Adapter.getMyscrolls();
                    for (int i = 0; i < myHorScrollViewArrayList.size(); i++) {
                        MyHorScrollView myhorscrollview = myHorScrollViewArrayList.get(i);
                            myhorscrollview.MoveToX(newX);
                    }
                }
            }
        });
        LinearLayout hor=(LinearLayout)view.findViewById(R.id.numberlist);

        int padding=ScreenUtils.getDIP2PX(getActivity(),3);
        int leftwidth=ScreenUtils.getScreenWH(getActivity())[0]-ScreenUtils.getDIP2PX(getActivity(),70)-nums.size();
        leftwidth=leftwidth/nums.size()>=ScreenUtils.getDIP2PX(getActivity(),30)?leftwidth/nums.size()
                :ScreenUtils.getDIP2PX(getActivity(),30);

        for (int i = 0; i <nums.size() ; i++) {
            ImageView imageView=new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundColor(getResources().getColor(R.color.line));
            hor.addView(imageView);

            TextView textview=new TextView(getActivity());
            textview.setLayoutParams(new ViewGroup.LayoutParams(leftwidth, ScreenUtils.getDIP2PX(getActivity(), 30)));
            textview.setText(nums.get(i));
            textview.setTextColor(getResources().getColor(R.color.text2));
            textview.setGravity(Gravity.CENTER);

            hor.addView(textview);


        }
        ll.addView(view, 2);
        ImageView imageView=new ImageView(getActivity());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        imageView.setBackgroundColor(getResources().getColor(R.color.line));
        ll.addView(imageView, 3);
    }


    public void setAdapter()
    {


        if(runChartFragment3Adapter==null)
        {
            for (int i = 0; i <beans.length ; i++) {
                TrendChartBean bean=new TrendChartBean();
                bean.setQsFormat(beans[i].getTitle());
                bean.setYilou(beans[i].getArrayLists());
                bean.setIsOpen(1);
                trendcharbeans.add(bean);
            }
            runChartFragment3Adapter=new RunChartFragment3Adapter(getActivity(),trendcharbeans,hbeans,pageindex);
            listview.setAdapter(runChartFragment3Adapter);
            runChartFragment3Adapter.setOnmovelistener(new RunChartFragment3Adapter.OnMoveItemListener() {
                @Override
                public void Onmove(int X) {
                    myscrollview.MoveToX(X);
                }
            });
        }
        else
        {
            for (int i = 0; i <4 ; i++) {
                trendcharbeans.remove(trendcharbeans.size()-1);
            }
            for (int i = 0; i <beans.length ; i++) {
                TrendChartBean bean=new TrendChartBean();
                bean.setQsFormat(beans[i].getTitle());
                bean.setYilou(beans[i].getArrayLists());
                bean.setIsOpen(1);
                trendcharbeans.add(bean);
            }
            runChartFragment3Adapter.NotifyData(trendcharbeans,hbeans,pageindex);
        }

    }



    @Override
    public void OnChange() {

    }

}
