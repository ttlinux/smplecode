package com.lottery.biying.Activity.User.Record;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
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
 * Created by Administrator on 2018/4/30.
 */
public class OrderDetailActivity extends BaseActivity{

    ImageLoader imageLoader;
    ImageView lotteryticon,spaceline;
    TextView lotteryname,ordermoney,winmoney,rebatesum,status,numbers,detail,detailvalue,remark;
    TextView qs,confirm;
    SwipeHeader swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setText(getString(R.string.betdetail));
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                GetDetail(getIntent().getStringExtra(BundleTag.id));
            }
        });
        confirm=FindView(R.id.confirm);
        lotteryticon=FindView(R.id.lotteryticon);
        qs=FindView(R.id.qs);
        lotteryname=FindView(R.id.lotteryname);
        ordermoney=FindView(R.id.ordermoney);
        winmoney=FindView(R.id.winmoney);
        rebatesum=FindView(R.id.rebatesum);
        status=FindView(R.id.status);
        numbers=FindView(R.id.numbers);
        detail=FindView(R.id.detail);
        detailvalue=FindView(R.id.detailvalue);
        remark=FindView(R.id.remark);
        imageLoader= RepluginMethod.getApplication(this).getImageLoader();
        spaceline=FindView(R.id.spaceline);
        spaceline.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        spaceline.setBackground(getResources().getDrawable(R.drawable.spaceline));
        GetDetail(getIntent().getStringExtra(BundleTag.id));
        String IsShowDeleteBtn=getIntent().getStringExtra(BundleTag.Type);
        if(IsShowDeleteBtn!=null && IsShowDeleteBtn.equalsIgnoreCase("1"))
        {
            confirm.setVisibility(View.VISIBLE);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RemoveOrder(getIntent().getStringExtra(BundleTag.id));
                }
            });
        }
        else
        {
            confirm.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void GetDetail(String id)
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("id",id);
        Httputils.PostWithBaseUrl(Httputils.OrderDetail, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                swipe.setRefreshing(false);
                LogTools.e("GetDetail", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(OrderDetailActivity.this, jsonObject.optString("msg"));
                    setResult(BundleTag.ResultCode);
                    finish();
                    return;
                }
                JSONObject datas = jsonObject.optJSONObject("datas");
                lotteryticon.setScaleType(ImageView.ScaleType.FIT_XY);
                imageLoader.displayImage(datas.optString("logo", ""), lotteryticon);
                lotteryname.setText(datas.optString("lotteryName", ""));
                qs.setText("第" + datas.optString("betQishu", "") + "期");
                ordermoney.setText(datas.optString("betMoney", "") );
                rebatesum.setText(datas.optString("betBackStr", ""));
                status.setText(datas.optString("betStatus", ""));
                winmoney.setText(datas.optString("status", "") );
                numbers.setText(datas.optString("winNumber", ""));
                detail.setText("选号详情: " + datas.optString("noteNuber", "") + "注" + datas.optString("multipe", "") + "倍");
                detailvalue.setText("玩法: " + datas.optString("gameName", "") + "\r\n" + datas.optString("content", ""));
                remark.setText("投注时间: " + datas.optString("betTime", "")
                        + "\r\n" + "方案编号: " + datas.optString("schemeNumbe", "")
                                + "\r\n" + "模式: " + datas.optString("remarks", "")
                        + "\r\n" + "金额类型: " + datas.optString("model", "")
                        );
            }
        });
    }

    public void RemoveOrder(String id)
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("id",id);
        Httputils.PostWithBaseUrl(Httputils.RemoveOrder,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                ToastUtil.showMessage(OrderDetailActivity.this, jsonObject.optString("msg"));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                setResult(BundleTag.ResultCode);
                finish();
            }
        });
    }
}
