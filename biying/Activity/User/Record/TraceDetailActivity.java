package com.lottery.biying.Activity.User.Record;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.TraceOrderDetailBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.SwipeHeader;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/1.
 */
public class TraceDetailActivity extends BaseActivity {


    ImageView logo;
    TextView lotteryname,lotterymethod,trace_money,winmoney,content,zhushu,Mode,unit,stoptrace;
    LinearLayout list,info;
    ImageLoader imageLoader;
    String info_titles[];
    String orderNumber,orderId;
    String TraceNumber;
    SwipeHeader swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_detail);
        Initview();
    }

    public void Initview()
    {
        //item_trace_detail
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setText(getString(R.string.tracedetail));
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                Getdata();
            }
        });
        imageLoader= RepluginMethod.getApplication(this).getImageLoader();
        info_titles=getResources().getStringArray(R.array.info_titles);
        logo=FindView(R.id.logo);
        lotteryname=FindView(R.id.lotteryname);
        lotterymethod=FindView(R.id.lotterymethod);
        trace_money=FindView(R.id.trace_money);
        winmoney=FindView(R.id.winmoney);
        info=FindView(R.id.info);
        zhushu=FindView(R.id.zhushu);
        Mode=FindView(R.id.Mode);
        unit=FindView(R.id.unit);
        stoptrace=FindView(R.id.stoptrace);
        content=FindView(R.id.content);
        list=FindView(R.id.list);
        orderNumber=getIntent().getStringExtra(BundleTag.OrderNumber);
        orderId=getIntent().getStringExtra(BundleTag.OrderId);

        stoptrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopTrace();
            }
        });
        Getdata();
    }

    private void Getdata()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("orderNumber",orderNumber);
        requestParams.put("orderId",orderId);
        Httputils.PostWithBaseUrl(Httputils.Trace_Order_detail,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                swipe.setRefreshing(false);
                LogTools.e("orderNumber", jsonObject.toString());
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(TraceDetailActivity.this, jsonObject.optString("msg"));
                    return;
                }
                TraceOrderDetailBean bean=TraceOrderDetailBean.AnalysisData(jsonObject.optJSONObject("datas"));
                imageLoader.displayImage(bean.getAppendLottery().getLogo(), logo);
                lotteryname.setText(bean.getAppendLottery().getLotteryName());
                lotterymethod.setText(bean.getAppendLottery().getCurrentGameName());
                trace_money.setText(bean.getAppendLottery().getTraceMoney());
                winmoney.setText(bean.getAppendLottery().getWinMoney());
                if(bean.getAppendLottery().getStopFlag().equalsIgnoreCase("0"))
                {
                    stoptrace.setVisibility(View.GONE);
                }

                String values[]={bean.getAppendLottery().getStartBetQishu(),
                        bean.getAppendLottery().getJingDu(),
                        bean.getAppendLottery().getStopCondition(),
                        bean.getAppendLottery().getTraceTime(),
                        bean.getAppendLottery().getTraceNumber()};

                TraceNumber=bean.getAppendLottery().getTraceNumber();
                info.removeAllViews();
                for (int i = 0; i <5 ; i++) {
                    RelativeLayout relatilayout=new RelativeLayout(TraceDetailActivity.this);
                    relatilayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    TextView name=new TextView(TraceDetailActivity.this);
                    RelativeLayout.LayoutParams layoutparams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    name.setLayoutParams(layoutparams);
                    name.setTextColor(getResources().getColor(R.color.gray20));
                    name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    name.setText(info_titles[i]);

                    TextView value=new TextView(TraceDetailActivity.this);
                    RelativeLayout.LayoutParams layoutparams2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutparams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    value.setLayoutParams(layoutparams2);
                    value.setText(values[i]);

                    relatilayout.addView(name);
                    relatilayout.addView(value);

                    info.addView(relatilayout);
                }

                content.setText(bean.getAppendScheme().getContent());
                zhushu.setText(bean.getAppendScheme().getNoteNuber());
                Mode.setText(bean.getAppendLottery().getBonusType());
                unit.setText(bean.getAppendScheme().getModel());

                list.removeAllViews();
                for (int j = 0; j <bean.getResultList().size() ; j++) {
                    TraceOrderDetailBean.ResultListBean rbean= bean.getResultList().get(j);
                    LinearLayout view=(LinearLayout)View.inflate(TraceDetailActivity.this, R.layout.item_trace_detail, null);
                    TextView text1=(TextView)view.getChildAt(0);
                    TextView text2=(TextView)view.getChildAt(1);
                    TextView text3=(TextView)view.getChildAt(2);
                    TextView text4=(TextView)view.getChildAt(3);
                    TextView text5=(TextView)view.getChildAt(4);
                    TextView text6=(TextView)view.getChildAt(5);
                    TextView text7=(TextView)view.getChildAt(6);
                    text1.setText(rbean.getBetQishuFormat());
                    text2.setText(rbean.getMultipe());
                    text3.setText(rbean.getBetmoney());
                    text4.setText(rbean.getWinMoney());
                    text5.setText(rbean.getStatus());
                    text6.setTag(rbean.getId());
                    text7.setTag(rbean);
                    if(rbean.getStopOrderFlag().equalsIgnoreCase("0"))
                        text6.setVisibility(View.INVISIBLE);
                    text6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Remove_Order((String)v.getTag());
                        }
                    });
                    text7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TraceOrderDetailBean.ResultListBean rbean=(TraceOrderDetailBean.ResultListBean) v.getTag();
                            Intent intent=new Intent();
                            intent.setClass(TraceDetailActivity.this,OrderDetailActivity.class);
                            intent.putExtra(BundleTag.id, rbean.getId());
                            intent.putExtra(BundleTag.Type,rbean.getStopOrderFlag());
                            startActivity(intent);
                        }
                    });
                    list.addView(view);
                }


            }
        });
    }

    public void StopTrace()
    {
        if(TraceNumber==null || TraceNumber.length()==0)
        {
            ToastUtil.showMessage(this,"TraceNumber 为空");
            return;
        }
        RequestParams requestparams=new RequestParams();
        requestparams.put("orderNumber",TraceNumber);
        Httputils.PostWithBaseUrl(Httputils.StopTrace,requestparams,new MyJsonHttpResponseHandler(this,true){

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                ToastUtil.showMessage(TraceDetailActivity.this, jsonObject.optString("msg", ""));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    return;
                }
                setResult(BundleTag.ResultCode);
                Getdata();
            }
        });
    }

    private void Remove_Order(String id)
    {
        RequestParams requestparams=new RequestParams();
        requestparams.put("id",id);
        Httputils.PostWithBaseUrl(Httputils.RemoveOrder,requestparams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                ToastUtil.showMessage(TraceDetailActivity.this, jsonObject.optString("msg", ""));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    return;
                }
                setResult(BundleTag.ResultCode);
                Getdata();
            }
        });
    }
}
