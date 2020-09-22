package com.lottery.biying.Activity.User.AccountManage.LotteryInstrution;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/12.
 */
public class LotteryInstrutionActivity extends BaseActivity{

    GridLayout gridlayout;
    private TextView RecordTextview;
    SwipeHeader swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrution_activity);

        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                GetBannerdata();
            }
        });
        gridlayout=FindView(R.id.gridlayout);

        String title=getIntent().getStringExtra("title");
        setBackTitleClickFinish();
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.lotteryinfo):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        GetBannerdata();

    }

    private void GetBannerdata() {
        if(RepluginMethod.getApplication(this).getMenuBeans().size()>0)
        {
            Initview(this);
            return;
        }
        Httputils.PostWithBaseUrl(Httputils.mainmethod, new RequestParams(), new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(LotteryInstrutionActivity.this, jsonObject.optString("msg"));
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
                Initview(LotteryInstrutionActivity.this);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }


    private void Initview(final Context context)
    {
        gridlayout.removeAllViews();
        RecordTextview=null;
        swipe.setRefreshing(false);
        SparseArray<MenuBean> menuBeans=new SparseArray<>();
//        MenuBean nmbean=new MenuBean();
//        nmbean.setMenuName("全部彩种");
//        menuBeans.add(nmbean);
        HashMap<String, MenuBean> hashMaps=RepluginMethod.getApplication(context).getMenuBeans();
        Iterator<Map.Entry<String,MenuBean>> its=hashMaps.entrySet().iterator();
        while (its.hasNext())
        {
            MenuBean mbean=(MenuBean)its.next().getValue();
            menuBeans.put(mbean.getIndex(),mbean);
        }

        for (int i = 0; i <menuBeans.size() ; i++) {
            TextView textView = new TextView(context);
            //使用Spec定义子控件的位置和比重
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = (ScreenUtils.getScreenWH((Activity) context)[0] - ScreenUtils.getDIP2PX(context, 10) * 4) / 3;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.topMargin = ScreenUtils.getDIP2PX(context, 10);
            layoutParams.leftMargin = ScreenUtils.getDIP2PX(context, 10);

            textView.setLayoutParams(layoutParams);
            textView.setText(menuBeans.get(i).getMenuName());
            textView.setTag(menuBeans.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setBackgroundResource(R.drawable.item_title);
            textView.setTextColor(0xff666666);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuBean bean=(MenuBean) v.getTag();
                    v.setBackgroundResource(R.drawable.item_title_selected);
                    ((TextView) v).setTextColor(context.getResources().getColor(R.color.loess4));

                    if (RecordTextview != null) {
                        RecordTextview.setBackgroundResource(R.drawable.item_title);
                        RecordTextview.setTextColor(0xff666666);
                    }
                    RecordTextview=(TextView)v;

                    Intent intent=new Intent();
                    intent.setClass(LotteryInstrutionActivity.this,LotteryInstrutionDetailActivity.class);
                    intent.putExtra(BundleTag.LotteryCode, bean.getMenuCode());
                    intent.putExtra(BundleTag.LotteryName,bean.getMenuName());
                    startActivity(intent);

                }
            });
            gridlayout.addView(textView);
        }
    }
}
