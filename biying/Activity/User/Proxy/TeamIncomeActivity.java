package com.lottery.biying.Activity.User.Proxy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.AccTransferAdapter;
import com.lottery.biying.Adapter.TeamIncomAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.AccChangeBean;
import com.lottery.biying.bean.TeamIncomeBean;
import com.lottery.biying.util.BitmapHandler;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AccTransferSearchDialog;
import com.lottery.biying.view.CustomPopwindow;
import com.lottery.biying.view.InComeDialog;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;
import com.lottery.biying.view.UserNameSearchDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3.团队盈亏
 */
public class TeamIncomeActivity extends BaseActivity implements View.OnClickListener{

    ImageView moveline;
    RelativeLayout ScrollerParentView;
    int Screenwidth, mCurrentCheckedRadioLeft=0;
    int mtotalItemCount,mfirstVisibleItem;
    RadioGroup radiogroup,radiogroup_main;
    String timestr[];
    private int currentPage=1;
    private final int RefreshCount=20;
    String Username="";
    int Mode=0;
    TextView backtitle;
    ImageView assistant_icon;
    TeamIncomAdapter teamIncomAdapter;
    ArrayList<TeamIncomeBean> beans=new ArrayList<>();
    ListviewWithBackTitle listview;
    boolean hasdata=true;
    Drawable normal, upsidedown;
    TextView title;
    String lottery_records_titles[];
    CustomPopwindow customPopwindow;
    PopupWindow popupWindow;
    public int selectedtype=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_income);
        InitView();
    }

    private void InitView()
    {

        normal = getResources().getDrawable(R.drawable.triangle);
        upsidedown = new BitmapDrawable(BitmapHandler.upSideDown(this, R.drawable.triangle));
        title=FindView(R.id.title);
        title.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
        lottery_records_titles=getResources().getStringArray(R.array.lottery_records_titles);
        title.setText(lottery_records_titles[0]);
        title.setVisibility(View.VISIBLE);
        title.setCompoundDrawablePadding(ScreenUtils.getDIP2PX(this, 10));
        title.setOnClickListener(this);
        customPopwindow=new CustomPopwindow(this,title,lottery_records_titles);
        popupWindow=customPopwindow.getPopupWindow();
        customPopwindow.setOnselectListenr(new CustomPopwindow.OnSelectListener() {
            @Override
            public void OnSelect(int index) {
                selectedtype=index;
                GetData(true);
                if(selectedtype>0)
                {
                    assistant_icon.setVisibility(View.GONE);
                }
                else
                {
                    assistant_icon.setVisibility(View.VISIBLE);
                }
            }
        });

        Username = getIntent().getStringExtra(BundleTag.Username);
        assistant_icon=FindView(R.id.assistant_icon);
        assistant_icon.setVisibility(View.VISIBLE);
        assistant_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserNameSearchDialog dialog = new UserNameSearchDialog(TeamIncomeActivity.this);
                dialog.setOnEditListener(new UserNameSearchDialog.OnEditListener() {
                    @Override
                    public void OnEdit(String name) {
//                        Intent intent=new Intent();
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.setClass(TeamIncomeActivity.this, TeamIncomeActivity.class);
//                        intent.putExtra(BundleTag.Username,name);
//                        startActivity(intent);
                        Username = name;
                        GetData(true);
                    }
                });
                dialog.show();
            }
        });

        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.teamincome):title);
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
                        timestr = TimeUtils.GetTimestr(1);
                        break;
                    case R.id.radiobutton2:
                        timestr = TimeUtils.GetTimestr(7);
                        break;
                    case R.id.radiobutton3:
                        timestr = TimeUtils.GetTimestr(30);
                        break;
                }

                currentPage = 1;
                GetData(true);
            }
        });
        radiogroup_main=FindView(R.id.radiogroup_main);

        ((RadioButton)radiogroup_main.getChildAt(0)).setChecked(true);
        setMoveline(0);
        radiogroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.team:
                        setMoveline(0);
                        Mode=0;
                        assistant_icon.setVisibility(View.VISIBLE);
                        break;
                    case R.id.daily:
                        setMoveline(1);
                        Mode=1;
                        assistant_icon.setVisibility(View.GONE);
                        break;
                }

                GetData(true);
            }
        });
        ((RadioButton)radiogroup.getChildAt(0)).setChecked(true);

    }
    private void setMoveline(int index) {

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

    public void GetData(final boolean isinit)
    {

        if(isinit)
        {
            currentPage=1;
            hasdata=true;
            beans.clear();
            SetAdapter();
        }

        RequestParams requestParams=new RequestParams();
        requestParams.put("startTime", timestr[0]);
        requestParams.put("finishTime", timestr[1]);
        requestParams.put("currentPage", currentPage + "");
        requestParams.put("pageLimit", RefreshCount + "");
        if(Username!=null && Username.length()>0)
        requestParams.put("account",Username);
        requestParams.put("flag", Mode + "");
        if(selectedtype>0)
        {
            switch (selectedtype)
            {
                case 1:
                    requestParams.put("flatType","live");
                    break;
                case 2:
                    requestParams.put("flatType","electronic");
                    break;
                case 3:
                    requestParams.put("flatType","sport");
                    break;
                case 4:
                    requestParams.put("flatType","fish");
                    break;
                case 5:
                    requestParams.put("flatType","card");
                    break;
            }
        }



        Httputils.PostWithBaseUrl(selectedtype>0?Httputils.TeamIncome_other:Httputils.TeamIncome,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata=false;
                if(beans.size()==0)
                {
                    listview.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("TTTTT",jsonObject.toString());
                listview.onRefreshComplete();
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(TeamIncomeActivity.this,jsonObject.optString("msg",""));
                    hasdata=false;
                    return;
                }
                if(isinit)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata=true;
                    beans.clear();
                }

                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    beans.add(TeamIncomeBean.Analysis(resultList.optJSONObject(i)));
                }
                if(resultList.length()<RefreshCount)
                    hasdata=false;
                if(  resultList.length()<RefreshCount)
                {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(beans.size()>=RefreshCount)
                    ToastUtil.showMessage(TeamIncomeActivity.this, getString(R.string.completetoast));
                }
                SetAdapter();
                if(beans.size()==0)
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
        if(teamIncomAdapter==null)
        {
            teamIncomAdapter=new TeamIncomAdapter(beans,this,Mode);
            listview.setAdapter(teamIncomAdapter);
        }
        else
        {
            teamIncomAdapter.Notidfy(beans,Mode);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.title:
                if (popupWindow != null) {
                    if (!popupWindow.isShowing()) {
                        upsidedown.setBounds(0, 0, normal.getMinimumWidth(), normal.getMinimumHeight());
                        title.setCompoundDrawables(null, null, upsidedown, null);
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
                        title.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                    }
                }
                break;
        }
    }
}
