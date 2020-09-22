package com.lottery.biying.Activity.User.Record.OtherGame;

import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.AccTransferAdapter;
import com.lottery.biying.Adapter.OtherGameRecordListAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.AccChangeBean;
import com.lottery.biying.bean.ORecordListBean;
import com.lottery.biying.bean.PlatformBean;
import com.lottery.biying.bean.QueryBean;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class OtherGameRecordListActivity extends BaseActivity {


    RadioGroup radiogroup;
    ListviewWithBackTitle listview;
    TextView backtitle,assistant;
    boolean hasdata=true;
    private int currentPage=1;
    private final int RefreshCount=20;
    int mtotalItemCount,mfirstVisibleItem;
    String timestr[];
    ArrayList<QueryBean> oRecordListBeans=new ArrayList<>();
    ArrayList<PlatformBean> platformBeans=new ArrayList<>();
    OtherGameRecordListAdapter otherGameRecordListAdapter;
    int index=0;
    Drawable normal, upsidedown;
    SharedPreferences sharedPreferences;
    String flat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_game_list);
        Initview();
    }

    private void Initview()
    {
        sharedPreferences=RepluginMethod.getHosttSharedPreferences(OtherGameRecordListActivity.this);



        radiogroup=FindView(R.id.radiogroup);
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
        setBackTitleClickFinish();
        backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton1:
                        timestr = TimeUtils.GetTimestr(1);
                        index = 0;
                        break;
                    case R.id.radiobutton2:
                        timestr = TimeUtils.GetTimestr(7);
                        index = 1;
                        break;
                    case R.id.radiobutton3:
                        timestr = TimeUtils.GetTimestr(30);
                        index = 2;
                        break;
                }
                currentPage = 1;
                GetData(true);
            }
        });

        GetPlatform();
    }

    private void GetData(final boolean isinit)
    {
        if(isinit)
        {
            currentPage=1;
            hasdata=true;
            oRecordListBeans.clear();
            SetAdapter();
        }

        RequestParams requestParams=new RequestParams();
//        requestParams.put("flat", bean.getFlat());
//        requestParams.put("startTime",timestr[0]);
//        requestParams.put("finishTime",timestr[1]);
//        requestParams.put("currentPage",currentPage+"");
//        requestParams.put("pageLimit",RefreshCount+"");

        requestParams.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        requestParams.put("flat",flat);
//        params.put("beginTime",startdate);//YYYY-MM-DD 2016-09-01
//        params.put("endTime",enddate);//YYYY-MM-DD 2016-10-10
        String tags[]={"today","oneweek","onemonth","threemonth"};
        requestParams.put("time",tags[index]+"");
        requestParams.put("pageNo",currentPage+"");
        requestParams.put("pageSize",RefreshCount+"");

        Httputils.PostWithBaseUrl(Httputils.Record, requestParams, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata = false;
                if(oRecordListBeans.size()==0)
                {
                    listview.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jssss",jsonObject.toString());
                listview.onRefreshComplete();
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(OtherGameRecordListActivity.this, jsonObject.optString("msg"));
                    hasdata = false;
                    return;
                }
                if (isinit)
                {
                    hasdata=true;
                    oRecordListBeans.clear();
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                }

                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("list");
                if(resultList==null)return;
                for (int i = 0; i < resultList.length(); i++) {
                    oRecordListBeans.add(QueryBean.Analysis(resultList.optJSONObject(i)));
                }
                if (resultList.length() < RefreshCount)
                    hasdata = false;
                if (  resultList.length() < RefreshCount) {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(oRecordListBeans.size() >= RefreshCount)
                    ToastUtil.showMessage(OtherGameRecordListActivity.this, getString(R.string.completetoast));
                }
                SetAdapter();
                if(oRecordListBeans.size()==0)
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
        if(otherGameRecordListAdapter==null)
        {
            otherGameRecordListAdapter=new OtherGameRecordListAdapter(oRecordListBeans,this);
            listview.setAdapter(otherGameRecordListAdapter);
        }
        else
        {
            otherGameRecordListAdapter.Notidfy(oRecordListBeans);
        }
    }

    private void GetPlatform() {
        String json=sharedPreferences.getString(BundleTag.ChangeTempData,"");
        if(json.length()>0)
        {
            try {
                Handledata(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.Platform, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                LogTools.e("ddddsfafadsfas", jsonObject.toString());
                Handledata(jsonObject);
                sharedPreferences.edit().putString(BundleTag.ChangeTempData,jsonObject.toString()).commit();
            }
        });
    }

    private void Handledata(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.optJSONArray("datas");
        if (jsonArray != null && jsonArray.length() > 0) {

            final String[] flatNames=new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsobject = jsonArray.optJSONObject(i);
                PlatformBean bean = new PlatformBean();
                bean.setFlat(jsobject.optString("flat", ""));
//                        bean.setFlatDes(jsobject.optString("flatDes", ""));
                bean.setFlatName(jsobject.optString("flatName", ""));
//                        bean.setFlatUrl(jsobject.optString("flatUrl", ""));
                bean.setBigPic(jsobject.optString("bigPic", ""));
                bean.setSmallPic(jsobject.optString("smallPic", ""));
                bean.setState(false);
                bean.setJine("余额");
                platformBeans.add(bean);
                flatNames[i]=bean.getFlatName();
            }

            assistant=FindView(R.id.assistant);
            assistant.setCompoundDrawablePadding(ScreenUtils.getDIP2PX(this, 5));
            assistant.setVisibility(View.VISIBLE);
            assistant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final OrderHistoryDialog dialog = new OrderHistoryDialog(OtherGameRecordListActivity.this, flatNames);
                    dialog.setOnDiaClickitemListener(new OrderHistoryDialog.OnDiaClickitemListener() {
                        @Override
                        public void Onclickitem(int index) {
                            flat = platformBeans.get(index).getFlat();
                            GetData(true);
                            assistant.setText(platformBeans.get(index).getFlatName());
                            backtitle.setText(platformBeans.get(index).getFlatName()+"游戏记录");
                            dialog.hide();
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
                }
            });
            normal = getResources().getDrawable(R.drawable.triangle);
            upsidedown = new BitmapDrawable(BitmapHandler.upSideDown(this, R.drawable.triangle));
            assistant.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
            assistant.setText(flatNames[0]);
            backtitle.setText(flatNames[0]+"游戏记录");
            flat=platformBeans.get(0).getFlat();
            ((RadioButton)radiogroup.getChildAt(0)).setChecked(true);
        }
    }
}
