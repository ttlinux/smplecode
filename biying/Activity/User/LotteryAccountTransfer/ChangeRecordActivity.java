package com.lottery.biying.Activity.User.LotteryAccountTransfer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.ChangeRecordAdapter;
import com.lottery.biying.Adapter.TeamIncomAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PaymentBean;
import com.lottery.biying.bean.PaymentBean3;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/13.
 */
public class ChangeRecordActivity extends BaseActivity {

    ImageView moveline;
    RelativeLayout ScrollerParentView;
    int Screenwidth, mCurrentCheckedRadioLeft=0;
    int mtotalItemCount,mfirstVisibleItem;
    RadioGroup radiogroup,radiogroup_main;
    int index;
    private int currentPage=1;
    private final int RefreshCount=20;
    String Username="";
    int Mode=0;
    TextView backtitle;
    ListviewWithBackTitle listview;
    boolean hasdata=true;
    ArrayList<PaymentBean3> paymentBean3s=new ArrayList<>();
    ChangeRecordAdapter changeRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_record);
        InitView();
    }


    private void InitView()
    {
        String title=getIntent().getStringExtra("title");
        setBackTitleClickFinish();;
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.changetitle):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        Screenwidth = ScreenUtils.getScreenWH(this)[0];
        ScrollerParentView = FindView(R.id.ScrollerParentView);
        listview=FindView(R.id.listview);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                GetData(true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                GetData(false);
            }
        });
        radiogroup=FindView(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton1:
                        index = 0;
                        break;
                    case R.id.radiobutton2:
                        index = 1;
                        break;
                    case R.id.radiobutton3:
                        index = 2;
                        break;
                }
                Username="";
                currentPage = 1;
                GetData(true);
            }
        });
        radiogroup_main=FindView(R.id.radiogroup_main);
        setMoveline(0);
        ((RadioButton)radiogroup_main.getChildAt(0)).setChecked(true);
        radiogroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.in:
                        setMoveline(0);
                        break;
                    case R.id.out:
                        setMoveline(1);
                        break;
                }
                Username = "";
                GetData(true);
            }
        });
        ((RadioButton)radiogroup.getChildAt(0)).setChecked(true);

    }
    private void setMoveline(int index) {
        Mode=index;
        if (moveline == null) {
            moveline = new ImageView(this);
            RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(Screenwidth / 4, ScreenUtils.getDIP2PX(this, 5));
            moveline.setLayoutParams(rl);
            moveline.setBackgroundColor(getResources().getColor(R.color.loess4));
            ScrollerParentView.addView(moveline);
        }

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;
        translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, Screenwidth / 8 + Screenwidth / 2 * index, 0f, 0f);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(100);
        moveline.startAnimation(animationSet);//开始上面红色横条图片的动画切换
        mCurrentCheckedRadioLeft=Screenwidth / 8 + Screenwidth / 2 * index;
    }

    public void GetData(final boolean init) {
        if (init) {
            hasdata=true;
            paymentBean3s.clear();
            currentPage=1;
            SetAdapter();
        }
        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
//        params.put("beginTime",startdate);//YYYY-MM-DD 2016-09-01
//        params.put("endTime", enddate);//YYYY-MM-DD 2016-10-10
        String tags[] = {"today", "oneweek", "onemonth"};
        params.put("time", tags[index] + "");
        params.put("pageNo", currentPage + "");
        params.put("pageSize", RefreshCount+"");
        params.put("type",(Mode==0?2:1)+"");
        Httputils.PostWithBaseUrl(Httputils.EduHistory, params, new MyJsonHttpResponseHandler(this, false) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("response", jsonObject.toString());
                listview.onRefreshComplete();
                if (init) {
                    hasdata=true;
                    paymentBean3s.clear();
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                }

                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobj = jsonArray.optJSONObject(i);
                        paymentBean3s.add(PaymentBean3.Analysis(jsobj));
                    }

                    if (jsonArray.length() < RefreshCount)
                        hasdata = false;
                    if (  jsonArray.length() < RefreshCount) {
                        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        if(paymentBean3s.size() >= RefreshCount)
                        ToastUtil.showMessage(ChangeRecordActivity.this, getString(R.string.completetoast));
                    }
                    SetAdapter();
                    if(paymentBean3s.size()==0)
                    {
                        listview.ShowTextview();
                    }
                    else
                    {
                        listview.ShowListview();
                    }
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String jsonObject) {
                super.onFailureOfMe(throwable, jsonObject);
                listview.onRefreshComplete();
                hasdata=false;
                if(paymentBean3s.size()==0) {
                    listview.ShowTextview();
                }
            }

        });
    }

    private void SetAdapter()
    {
        if(changeRecordAdapter==null)
        {
            changeRecordAdapter=new ChangeRecordAdapter(paymentBean3s,this);
            listview.setAdapter(changeRecordAdapter);
        }
        else
        {
            changeRecordAdapter.Notify(paymentBean3s);
        }
    }
}
