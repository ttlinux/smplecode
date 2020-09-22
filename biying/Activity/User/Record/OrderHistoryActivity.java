package com.lottery.biying.Activity.User.Record;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.Change_Adapter;
import com.lottery.biying.Adapter.OrderHistoryAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.bean.OrderHistoryBean;
import com.lottery.biying.bean.PlatformBean;
import com.lottery.biying.util.BitmapHandler;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.OrderHistoryDialog;
import com.lottery.biying.view.OrderHistoryPopWindow;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/28.
 */
public class OrderHistoryActivity extends BaseActivity implements View.OnClickListener{

    ListviewWithBackTitle listview;
    OrderHistoryAdapter orderHistoryAdapter;
    ArrayList<OrderHistoryBean> orderHistoryBeans=new ArrayList<>();
    private final int RefreshCount=10;
    Drawable normal, upsidedown;
    private int currentPage=1;
    OrderHistoryPopWindow window;
    PopupWindow popupWindow;
    TextView backtitle,assistant;
    int times[]={1,7,30};
    String timestr[],assitant_time[];
    int mtotalItemCount,mfirstVisibleItem;
    boolean hasdata=true;
    String name;
    RelativeLayout proxylayout;
    TextView search;
    EditText username_val;
    String LotteryCode,LotteryName;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_layout);
        Initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==BundleTag.ResultCode)
        {
            Getdata(true);
        }
    }


    private void Initview()
    {

        name=getIntent().getStringExtra(BundleTag.Username);
        LotteryCode=getIntent().getStringExtra(BundleTag.LotteryCode);
        LotteryName=getIntent().getStringExtra(BundleTag.LotteryName);
        assitant_time=getResources().getStringArray(R.array.assitant_time);
        timestr=TimeUtils.GetTimestr(1);
        normal = getResources().getDrawable(R.drawable.triangle);
        upsidedown = new BitmapDrawable(BitmapHandler.upSideDown(this, R.drawable.triangle));
        backtitle=FindView(R.id.backtitle);
        assistant=FindView(R.id.assistant);
        proxylayout=FindView(R.id.proxylayout);
        username_val=FindView(R.id.username_val);
        search=FindView(R.id.search);
        search.setOnClickListener(this);
        if(RepluginMethod.getApplication(this).isgent().equalsIgnoreCase("1"))
            proxylayout.setVisibility(View.VISIBLE);
        if(name!=null && name.length()>0)
        {
            username_val.setText(name);
        }
        assistant.setCompoundDrawablePadding(ScreenUtils.getDIP2PX(this, 5));
        assistant.setText(assitant_time[0]);
        assistant.setVisibility(View.VISIBLE);
        assistant.setOnClickListener(this);
        assistant.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);

        setBackTitleClickFinish();
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setText(LotteryName != null && LotteryName.length() > 0 ? LotteryName : getString(R.string.alllotterytype));
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
        backtitle.setOnClickListener(this);
        listview=FindView(R.id.listview);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (username_val.getText().toString().length() > 0 && username_val.getText().toString().length() < 4) {
                    listview.onRefreshComplete();
                    ToastUtil.showMessage(OrderHistoryActivity.this, "请正确输入用户名(4-16位英文字母以及数字组合)");
                    currentPage = 1;
                    hasdata = true;
                    orderHistoryBeans.clear();
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

        window=new OrderHistoryPopWindow(this,backtitle,LotteryCode);
        window.setOnselectListenr(new OrderHistoryPopWindow.OnSelectListener() {
            @Override
            public void OnSelect(MenuBean bean) {
                LotteryCode=bean.getMenuCode();
                currentPage=1;
                Getdata(true);
                popupWindow.dismiss();
            }
        });
        popupWindow=window.getPopupWindow();
        GetBannerdata();
        Getdata(true);
    }

    private void Getdata(final boolean isinit)
    {

        if(isinit)
        {
            currentPage=1;
            hasdata=true;
            orderHistoryBeans.clear();
            SetAdapter();
        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("currentPage",currentPage+"");
        requestParams.put("pageLimit", RefreshCount + "");
        if(LotteryCode!=null && LotteryCode.length()>0)
        {
            requestParams.put("lotteryCode",LotteryCode);
        }
        if(username_val.getText().toString().length() >= 4)
        {
            requestParams.put("account",username_val.getText().toString());
        }
        else
        {

        }
        if (timestr != null && timestr.length>1)
        {
            requestParams.put("startTime", timestr[0]);
            requestParams.put("finishTime", timestr[1]);
        }

        Httputils.PostWithBaseUrl(Httputils.OrderHistory, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata=false;
                if(orderHistoryBeans.size()==0)
                {
                    listview.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
//                LogTools.e("OrderHistory", jsonObject.toString());
                listview.onRefreshComplete();
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(OrderHistoryActivity.this, jsonObject.optString("msg"));
                    hasdata=false;
                    return;
                }
                if(isinit)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata=true;
                    orderHistoryBeans.clear();
                }

                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    orderHistoryBeans.add(OrderHistoryBean.AnalysisData(resultList.optJSONObject(i)));
                }
                if(resultList.length()<RefreshCount)
                    hasdata=false;
                if(  resultList.length()<RefreshCount)
                {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(orderHistoryBeans.size()>=RefreshCount)
                    ToastUtil.showMessage(OrderHistoryActivity.this, getString(R.string.completetoast));
                }
                SetAdapter();
                if(orderHistoryBeans.size()==0)
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
        if(orderHistoryAdapter==null)
        {
            orderHistoryAdapter=new OrderHistoryAdapter(this,orderHistoryBeans);
            listview.setAdapter(orderHistoryAdapter);
        }
        else
        {
            orderHistoryAdapter.Notidfy(orderHistoryBeans);
        }
    }

    private void GetBannerdata() {
        if(RepluginMethod.getApplication(this).getMenuBeans().size()>0)
        {
            return;
        }
        Httputils.PostWithBaseUrl(Httputils.mainmethod, new RequestParams(), new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("onSuccessOfMe", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(OrderHistoryActivity.this, jsonObject.optString("msg"));
                    return;
                }
                JSONObject datas = jsonObject.optJSONObject("datas");


                //内容
                JSONArray menus = datas.optJSONArray("menu");

                for (int i = 0; i < menus.length(); i++) {
                    MenuBean mbean = MenuBean.Handlder_Json(menus.optJSONObject(i));
                    mbean.setIndex(i);
                    RepluginMethod.getApplication(context).getMenuBeans().put(mbean.getMenuCode(), mbean);
                }

                //内容
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backtitle:
                if (popupWindow != null) {
                    if (!popupWindow.isShowing()) {
                        upsidedown.setBounds(0, 0, normal.getMinimumWidth(), normal.getMinimumHeight());
                        backtitle.setCompoundDrawables(null, null, upsidedown, null);
                        View view=FindView(R.id.toplayout);
                        if (Build.VERSION.SDK_INT < 24) {
                            popupWindow.showAsDropDown(view);
                        } else {
                            Rect visibleFrame = new Rect();
                            view.getGlobalVisibleRect(visibleFrame);
                            int height = view.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                            popupWindow.setHeight(height);
                            popupWindow.showAsDropDown(view);
                        }
                    } else {
                        popupWindow.dismiss();
                        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                    }
                }
                break;
            case R.id.assistant:
                final OrderHistoryDialog dialog = new OrderHistoryDialog(this, getResources().getStringArray(R.array.assitant_time));
                dialog.setOnDiaClickitemListener(new OrderHistoryDialog.OnDiaClickitemListener() {
                    @Override
                    public void Onclickitem(int index) {
                        timestr = TimeUtils.GetTimestr(times[index]);
                        currentPage = 1;
                        Getdata(true);
                        dialog.hide();
                        assistant.setText(assitant_time[index]);
                    }
                });
                dialog.setOndismiss(new OrderHistoryDialog.OnDismissListener() {
                    @Override
                    public void Ondismiss() {
                        assistant.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                    }
                });
                dialog.showDropdown((View) FindView(R.id.topmainview));
                upsidedown.setBounds(0, 0, normal.getMinimumWidth(), normal.getMinimumHeight());
                assistant.setCompoundDrawables(null, null, upsidedown, null);
                break;
            case R.id.search:
                if(username_val.getText().toString().length()<4)
                {
                    ToastUtil.showMessage(this, "请正确输入用户名(4-16位英文字母以及数字组合)");
                    return;
                }
                Getdata(true);
                break;
        }
    }


}
