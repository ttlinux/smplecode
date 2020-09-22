package com.lottery.biying.Activity.User.LotteryAccountTransfer;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.PersonalIncomeAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PersonalIncomBean;
import com.lottery.biying.util.BitmapHandler;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.CustomPopwindow;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3. 个人盈亏
 */
public class PersonalIncomeActivity extends BaseActivity implements View.OnClickListener{

    RadioGroup radiogroup;
    ListviewWithBackTitle listview;
    TextView backtitle;
    String timestr[];
    boolean hasdata=true;
    private int currentPage=1;
    private final int RefreshCount=20;
    int mtotalItemCount,mfirstVisibleItem;
    ArrayList<PersonalIncomBean> personalIncomBeans=new ArrayList<>();
    PersonalIncomeAdapter accadapter;
    ImageView assistant_icon;
    Drawable normal, upsidedown;
    TextView title;
    String lottery_records_titles[];
    CustomPopwindow customPopwindow;
    PopupWindow popupWindow;
    public int selectedtype=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_change);
        Initview();
    }

    private void Initview()
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
                selectedtype = index;
                GetData(true);
            }
        });

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

        String title=getIntent().getStringExtra("title");
        setBackTitleClickFinish();
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.personalincome):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radiobutton1:
                        timestr= TimeUtils.GetTimestr(1);
                        break;
                    case R.id.radiobutton2:
                        timestr=TimeUtils.GetTimestr(7);
                        break;
                    case R.id.radiobutton3:
                        timestr=TimeUtils.GetTimestr(30);
                        break;
                }
                currentPage=1;
                GetData(true);
            }
        });

        int index=getIntent().getIntExtra(BundleTag.Index,0);
        ((RadioButton)radiogroup.getChildAt(index)).setChecked(true);
    }



    public void GetData(final boolean isinit)
    {

        if(isinit)
        {
            hasdata=true;
            personalIncomBeans.clear();
            currentPage=1;
            SetAdapter();
        }
        RequestParams requestparams=new RequestParams();
        requestparams.put("currentPage", currentPage + "");
        requestparams.put("pageLimit", RefreshCount + "");
        requestparams.put("flag","1");
        if(timestr != null && timestr.length>1)
        {
            requestparams.put("startTime", timestr[0]);
            requestparams.put("finishTime", timestr[1]);
        }
        if(selectedtype>0)
        {
            switch (selectedtype)
            {
                case 1:
                    requestparams.put("flatType","live");
                    break;
                case 2:
                    requestparams.put("flatType","electronic");
                    break;
                case 3:
                    requestparams.put("flatType","sport");
                    break;
                case 4:
                    requestparams.put("flatType","fish");
                    break;
                case 5:
                    requestparams.put("flatType","card");
                    break;
            }

        }

        Httputils.PostWithBaseUrl(selectedtype==0?Httputils.PersonalIncome:Httputils.PersonalIncome_other, requestparams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata = false;
                if(personalIncomBeans.size()== 0)
                {
                    listview.ShowTextview();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                listview.onRefreshComplete();
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(PersonalIncomeActivity.this,jsonObject.optString("msg"));
                    hasdata = false;
                    return;
                }
                if(isinit)
                {
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    hasdata=true;
                    personalIncomBeans.clear();
                }
                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("resultList");
                for (int i = 0; i < resultList.length(); i++) {
                    personalIncomBeans.add(PersonalIncomBean.Analysis(resultList.optJSONObject(i)));
                }
                if (resultList.length() < RefreshCount)
                    hasdata = false;
                if (  resultList.length() < RefreshCount) {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(personalIncomBeans.size() >= RefreshCount)
                    ToastUtil.showMessage(PersonalIncomeActivity.this, getString(R.string.completetoast));
                }
                SetAdapter();
                if(personalIncomBeans.size()==0)
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
        if(accadapter==null)
        {
            accadapter=new PersonalIncomeAdapter(personalIncomBeans,this);
            listview.setAdapter(accadapter);
        }
        else
        {
            accadapter.Notidfy(personalIncomBeans);
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
