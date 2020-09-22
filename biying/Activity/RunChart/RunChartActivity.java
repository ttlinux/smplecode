package com.lottery.biying.Activity.RunChart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.RunChartFragmentAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.BaseParent.BaseFragmentActivity;
import com.lottery.biying.Fragment.CategoryFragemnt;
import com.lottery.biying.Fragment.MainFragment;
import com.lottery.biying.Fragment.SettingFragment;
import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.LotteryTimebean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeMethod;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AppAlertDialog;
import com.lottery.biying.view.ZoushiSequenceDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */
public class RunChartActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    ViewPager viewpager;
    FragmentManager fragmentManager;
    ArrayList<BaseFragment> bases = new ArrayList<BaseFragment>();
    RadioGroup titles_btn;
    RelativeLayout ScrollerParentView;
    private int pagerindex = 0;
    RadioButton jqkj;
    ImageView moveline;
    String LotteryType;
    String sequence = BundleTag.Desc;
    TextView assistant;
    int Screenwidth, mCurrentCheckedRadioLeft=0;
    public ArrayList<LotteryResultList> lotteryResultLists=new ArrayList<>();
    TextView backtitle;


    public ArrayList<LotteryResultList> getLotteryResultLists() {
        return lotteryResultLists;
    }

    public String getSequence() {
        return sequence;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runchart);
        Init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void Init() {
         backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        assistant=FindView(R.id.assistant);
        assistant.setTextColor(0xffffffff);
        assistant.setVisibility(View.VISIBLE);
        Screenwidth = ScreenUtils.getScreenWH(this)[0];
        LotteryType = getIntent().getStringExtra(BundleTag.id);
        fragmentManager = getSupportFragmentManager();
        titles_btn = FindView(R.id.titles_btn);
        ScrollerParentView = FindView(R.id.ScrollerParentView);
        viewpager = FindView(R.id.viewpager);
        viewpager.setOffscreenPageLimit(3);
        jqkj = FindView(R.id.jqkj);

        bases.add(new Fragment1());
//        bases.add(new Fragment2());
        LogTools.e("AnalysisType.getLottertCategory()",AnalysisType.getLottertCategory()+" "+AnalysisType.MainLotteryCode.HK.getIndex());
        if(AnalysisType.getLottertCategory()==AnalysisType.MainLotteryCode.HK.getIndex())
        {
            FindView(R.id.zst).setVisibility(View.GONE);
        }
        else
        {
            bases.add(new Fragment3());
        }

        Bundle bundle = new Bundle();
        bundle.putString(BundleTag.id, LotteryType);
        for (int i = 0; i < bases.size(); i++) {
            bases.get(i).setArguments(bundle);
        }

        if (bases.isEmpty()) return;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton=(RadioButton)group.findViewById(checkedId);
        if (checkedId == R.id.jqkj) {
            setMoveline(0);
            viewpager.setCurrentItem(0);
        }
        if (checkedId == R.id.zst) {
            setMoveline(1);
            viewpager.setCurrentItem(1);
        }
        backtitle.setText(radioButton.getText().toString());
    }


    private void setMoveline(int index) {
        if (moveline == null) {
            moveline = new ImageView(this);
            RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(Screenwidth / 4, ScreenUtils.getDIP2PX(this, 5));
            if(AnalysisType.getLottertCategory()==AnalysisType.MainLotteryCode.HK.getIndex())
            {
                rl.leftMargin=Screenwidth*3/8;
            }
            moveline.setLayoutParams(rl);
            moveline.setBackgroundColor(getResources().getColor(R.color.loess4));
            ScrollerParentView.addView(moveline);
        }
        if(AnalysisType.getLottertCategory()!=AnalysisType.MainLotteryCode.HK.getIndex())
        {
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

    }

    public void InitViewPagerAndSelected() {

        titles_btn.setOnCheckedChangeListener(this);
        viewpager.setAdapter(new RunChartFragmentAdapter(fragmentManager, bases));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) titles_btn.getChildAt(position*2);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetTime();
    }
    private void GetTime() {
        assistant.setText(getString(R.string.requestingqihao));
        TimeMethod.End();
        RequestParams requestParams = new RequestParams();
        requestParams.put("lotteryCode", getIntent().getStringExtra(BundleTag.id));
        Httputils.PostWithBaseUrl(Httputils.DTime, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("timeeee", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;

                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONObject jsonobj = datas.optJSONObject("paiqiResult");
                JSONObject yl = datas.optJSONObject("yl");
                if(jsonobj.optString("isClose", "").equalsIgnoreCase("1"))
                {
                    assistant.setText(getString(R.string.RoundEnd));
                    TimeMethod.End();
                    return;
                }
                LotteryTimebean ltbean = new LotteryTimebean();
                ltbean.setTimes(jsonobj.optInt("closeTime", 0));
                ltbean.setQs(jsonobj.optString("qs", ""));
                ltbean.setQsFormat(jsonobj.optString("qsFormat", ""));

                GetData();
                /*倒计时*/
                TimeMethod.setOnTimeChangeListener(new TimeMethod.OnTimeChangeListener() {
                    @Override
                    public void OnChange(int CloseLimitime, int LotteryStatus, String qsstr) {

                        assistant.setText("距离" + qsstr + "期截止: " + TimeUtils.ConverTime(CloseLimitime));
                        if (CloseLimitime == 0 && isInFront()) {
                            AlertDialog2("第" + qsstr + "期已截止");
                        }
                        if (CloseLimitime == -1) {
                            assistant.setText(getString(R.string.requestingqihao));
                            GetTime();
                        }
                    }
                }, RunChartActivity.this, ltbean.getQsFormat());
                TimeMethod.Start(Integer.valueOf(ltbean.getTimes()));
                 /*倒计时*/
            }
        });
    }


    private void GetData()
    {
        RequestParams requestparams = new RequestParams();
        requestparams.put("lotteryCode", getIntent().getStringExtra(BundleTag.id));
        requestparams.put("count", 50 + "");

        Httputils.PostWithBaseUrl(Httputils.lotteryresult, requestparams, new MyJsonHttpResponseHandler(RunChartActivity.this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(RunChartActivity.this, jsonObject.optString("msg"));
                    return;
                }
                lotteryResultLists.clear();
                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONArray resultList = datas.optJSONArray("openResult");
                for (int i = 0; i < resultList.length(); i++) {
                    JSONObject jsonobj = resultList.optJSONObject(i);
                    LotteryResultList bean = new LotteryResultList();
                    bean.setQs(jsonobj.optString("qs", ""));
                    bean.setQsFormat(jsonobj.optString("qsFormat", ""));
                    bean.setIsOpen(jsonobj.optInt("isOpen", 0));
                    JSONArray openResult = jsonobj.optJSONArray("openResult");
                    if (openResult.length() > 0) {
                        String results[] = new String[openResult.length()];
                        for (int j = 0; j < openResult.length(); j++) {
                            results[j] = openResult.optString(j);
                        }
                        bean.setOpenResult(results);
                    }
                    lotteryResultLists.add(bean);
                }
                if(viewpager.getAdapter()==null)
                {
                    InitViewPagerAndSelected();
                    RadioButton radioButton = (RadioButton) titles_btn.getChildAt(0);
                    radioButton.setChecked(true);
                }

                Fragment1 fragment1=(Fragment1)bases.get(0);
                fragment1.OnChange();

                if(bases.size()>1)
                {
                    Fragment3 fragment3=(Fragment3)bases.get(1);
                    fragment3.OnChange();
                }

            }
        });
    }

    private void AlertDialog2(String Content) {
        final AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.setTitle(getResources().getString(R.string.warmtips));
        dialog.setConetnt(Content);
        dialog.setStyle();
        dialog.setConfirmstr("确定");
        dialog.show();
        dialog.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
            @Override
            public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                dialog.hide();
            }
        });
        dialog.setAutoClose();
    }
}
