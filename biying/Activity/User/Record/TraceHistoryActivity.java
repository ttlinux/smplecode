package com.lottery.biying.Activity.User.Record;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.OrderHistoryAdapter;
import com.lottery.biying.Adapter.TraceHistoryListAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.OrderHistoryBean;
import com.lottery.biying.bean.TraceOrderBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/30.
 */
public class TraceHistoryActivity extends BaseActivity {

    ImageView moveline;
    RelativeLayout ScrollerParentView;
    TextView search;
    int Screenwidth, mCurrentCheckedRadioLeft;
    ListviewWithBackTitle listview;
    RadioGroup radiogroup;
    int mtotalItemCount,mfirstVisibleItem;
    private int currentPage=1;
    private final int RefreshCount=20;
    ArrayList<TraceOrderBean> traceOrderBeans=new ArrayList<>();
    String status="";
    boolean hasdata=true;
    TraceHistoryListAdapter traceHistoryListAdapter;
    EditText username_val;
    String username="";
    RelativeLayout searchlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_trace_list);
        InitView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==BundleTag.ResultCode)
        {
            currentPage = 1;
            Getdata(true);
        }
    }

    public void InitView() {
        username=getIntent().getStringExtra(BundleTag.Username);
        Screenwidth = ScreenUtils.getScreenWH(this)[0];
        ScrollerParentView = FindView(R.id.ScrollerParentView);
        searchlayout=FindView(R.id.searchlayout);
        if(RepluginMethod.getApplication(this).isgent().equalsIgnoreCase("1"))
            searchlayout.setVisibility(View.VISIBLE);
        listview = FindView(R.id.listview);
        listview.setDividerHeight(1);
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.line)));
        radiogroup = FindView(R.id.radiogroup);

        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.tracehistory):title);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        search=FindView(R.id.search);
        username_val=FindView(R.id.username_val);
        if(username!=null && username.length()>0)
        username_val.setText(username);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_val.getText().toString().length() < 4) {
                    ToastUtil.showMessage(TraceHistoryActivity.this, "请正确输入用户名(4-16位英文字母以及数字组合)");
                    return;
                }
                Getdata(true);
            }
        });
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (username_val.getText().toString().length() > 0 && username_val.getText().toString().length() < 4) {
                    listview.onRefreshComplete();
                    ToastUtil.showMessage(TraceHistoryActivity.this, "请正确输入用户名(4-16位英文字母以及数字组合)");
                    currentPage = 1;
                    hasdata = true;
                    traceOrderBeans.clear();
                    SetAdapter();
                    return;
                }

                Getdata(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                Getdata(false);
            }
        });
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton1:
                        setMoveline(0);
                        status = "";
                        break;
                    case R.id.radiobutton2:
                        setMoveline(1);
                        status = "1";
                        break;
                    case R.id.radiobutton3:
                        setMoveline(2);
                        status = "0";
                        break;
                }
                Getdata(true);
            }
        });
        ((RadioButton)radiogroup.getChildAt(1)).setChecked(true);

    }

    private void setMoveline(int index) {
        if (moveline == null) {
            moveline = new ImageView(this);
            moveline.setLayoutParams(new RelativeLayout.LayoutParams(Screenwidth / 3 / 2, ScreenUtils.getDIP2PX(this, 5)));
            moveline.setBackgroundColor(getResources().getColor(R.color.loess4));
            ScrollerParentView.addView(moveline);
        }

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;
        translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, Screenwidth / 12 + Screenwidth / 3 * index, 0f, 0f);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(100);
        moveline.startAnimation(animationSet);//开始上面红色横条图片的动画切换
        mCurrentCheckedRadioLeft=Screenwidth / 12 + Screenwidth / 3 * index;
    }

    public void Getdata(final boolean isinit) {
        RequestParams requestParams = new RequestParams();
        if(status.length()>0)
        requestParams.put("status",status);


        if(username_val.getText().toString().length() >= 4)
        {
            requestParams.put("account",username_val.getText().toString());
        }
        else
        {

        }


        if(isinit)
        {
            hasdata=true;
            traceOrderBeans.clear();
            SetAdapter();
        }

        Httputils.PostWithBaseUrl(Httputils.Tracehistory, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                hasdata=false;
                listview.onRefreshComplete();
                if(traceOrderBeans.size()==0)
                {
                    listview.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                listview.onRefreshComplete();
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(TraceHistoryActivity.this, jsonObject.optString("msg"));
                    hasdata=false;
                    return;
                }
                if(isinit)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata=true;
                    traceOrderBeans.clear();
                }
                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    traceOrderBeans.add(TraceOrderBean.AnalysisData(resultList.optJSONObject(i)));
                }
                if(resultList.length()<RefreshCount)
                    hasdata=false;
                if(  resultList.length()<RefreshCount) {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(traceOrderBeans.size()>=RefreshCount)
                    ToastUtil.showMessage(TraceHistoryActivity.this, getString(R.string.completetoast));
                }
                SetAdapter();
                if(traceOrderBeans.size()==0)
                {
                    listview.ShowTextview();
                }
                else
                {
                    listview.ShowListview();
                }
            }
        });
    }

    private void SetAdapter()
    {
        if(traceHistoryListAdapter==null)
        {
            traceHistoryListAdapter=new TraceHistoryListAdapter(this,traceOrderBeans);
            listview.setAdapter(traceHistoryListAdapter);
        }
        else
        {
            traceHistoryListAdapter.Notidfy(traceOrderBeans);
        }
    }
}
