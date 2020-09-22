package com.lottery.biying.Activity.User.Proxy.TeamManage;

import android.content.Intent;
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
import com.lottery.biying.Adapter.TeamIncomAdapter;
import com.lottery.biying.Adapter.TeamManageListAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.ManageRecordBean;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;
import com.lottery.biying.view.UserNameSearchDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/5.
 */
public class TeamManageActivity extends BaseActivity{

    TextView backtitle;
    ImageView assistant_icon;
    ArrayList<ManageRecordBean> manageRecordBeans=new ArrayList<>();
    TeamManageListAdapter teamManageListAdapter;
    RadioGroup radiogroup_main;
    int Mode;
    ImageView moveline;
    RelativeLayout ScrollerParentView;
    int Screenwidth, mCurrentCheckedRadioLeft=0;
    String Username="";
    private int currentPage=1;
    private final int RefreshCount=20;
    boolean hasdata=true;
    ListviewWithBackTitle listview;
    int mtotalItemCount,mfirstVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manage);
        Initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== BundleTag.ResultCode)
        {
            GetData(true);
        }
    }

    private void Initview()
    {
        Username=getIntent().getStringExtra(BundleTag.Username);
        Screenwidth = ScreenUtils.getScreenWH(this)[0];

        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.teammanage):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        assistant_icon=FindView(R.id.assistant_icon);
        assistant_icon.setVisibility(View.VISIBLE);
        assistant_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserNameSearchDialog dialog = new UserNameSearchDialog(TeamManageActivity.this);
                dialog.setOnEditListener(new UserNameSearchDialog.OnEditListener() {
                    @Override
                    public void OnEdit(String name) {
                        Username = name;
                        GetData(true);
                    }
                });
                dialog.show();
            }
        });
        radiogroup_main=FindView(R.id.radiogroup_main);
        ScrollerParentView=FindView(R.id.ScrollerParentView);
        radiogroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.proxymem:
                        setMoveline(0);
                        Mode=1;
                        break;
                    case R.id.normalmem:
                        setMoveline(1);
                        Mode=0;
                        break;
                }
                Username = getIntent().getStringExtra(BundleTag.Username);
                GetData(true);
            }
        });


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
        ((RadioButton)radiogroup_main.getChildAt(0)).setChecked(true);
    }

    private void setMoveline(int index) {
        if (moveline == null) {
            moveline = new ImageView(this);
            moveline.setLayoutParams(new RelativeLayout.LayoutParams(Screenwidth / 4, ScreenUtils.getDIP2PX(this, 5)));
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

    private void GetData(final boolean isinit)
    {
        if(isinit)
        {
            currentPage=1;
            hasdata=true;
            manageRecordBeans.clear();
            SetAdapter();
        }

        RequestParams requestParams=new RequestParams();
        requestParams.put("isAgent",Mode+"");

        requestParams.put("currentPage", currentPage + "");
        requestParams.put("pageLimit", RefreshCount + "");
        if(Username!=null && Username.length()>0)
            requestParams.put("account",Username);
        requestParams.put("flag", "0");



        Httputils.PostWithBaseUrl(Httputils.TeamManageList,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata=false;
                if(manageRecordBeans.size()==0)
                {
                    listview.ShowTextview();
                }

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("TeamManageList", jsonObject.toString());
                listview.onRefreshComplete();
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(TeamManageActivity.this, jsonObject.optString("msg", ""));
                    hasdata=false;
                    return;
                }
                if(isinit)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata=true;
                    manageRecordBeans.clear();
                }

                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    manageRecordBeans.add(ManageRecordBean.Analysis(resultList.optJSONObject(i)));
                }
                if(resultList.length()<RefreshCount)
                    hasdata=false;
                if(  resultList.length()<RefreshCount)
                {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(manageRecordBeans.size()>=RefreshCount)
                    ToastUtil.showMessage(TeamManageActivity.this, getString(R.string.completetoast));
                }
                SetAdapter();
                if(manageRecordBeans.size()==0)
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
        if(teamManageListAdapter==null)
        {
            teamManageListAdapter=new TeamManageListAdapter(manageRecordBeans,this,Mode);
            listview.setAdapter(teamManageListAdapter);
        }
        else
        {
            teamManageListAdapter.Notidfy(manageRecordBeans,Mode);
        }
    }
}
