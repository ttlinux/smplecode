package com.lottery.biying.Activity.User.Proxy.GeneralizeLink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Proxy.SalaryManage.DailySalaryPayOffFragment;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.MyDailySalaryFragment;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.NewSubordinateSalaryFragment;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.SubordinateSalaryFragment;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.BaseParent.BaseFragmentActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/4.
 */
public class GeneralizeLinkActivity  extends BaseFragmentActivity {

    RadioGroup radiogroup_main;
    TextView backtitle;
    FrameLayout framelayout;
    ArrayList<BaseFragment> baseFragments=new ArrayList<>();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    RelativeLayout ScrollerParentView;
    int Screenwidth,mCurrentCheckedRadioLeft;
    ImageView moveline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalize_link);
        Initview();
    }

    private void Initview()
    {

        Screenwidth = ScreenUtils.getScreenWH(this)[0];

        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.generalizelink):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        ScrollerParentView = FindView(R.id.ScrollerParentView);
        radiogroup_main = FindView(R.id.radiogroup_main);
        radiogroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.team:
                        setMoveline(0);
                        setFragment(0);
                        break;
                    case R.id.daily:
                        setMoveline(1);
                        setFragment(1);
                        break;
                }
            }
        });


        baseFragments.add(new ExistLinkFragment());
        baseFragments.add(new MakeANewLinkFragment());

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.framelayout,baseFragments.get(0));
        fragmentTransaction.add(R.id.framelayout,baseFragments.get(1));
        if(baseFragments.isEmpty())return;
        fragmentTransaction.hide(baseFragments.get(0));
        fragmentTransaction.commitAllowingStateLoss();

        ((RadioButton) radiogroup_main.getChildAt(0)).setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== BundleTag.ResultCode)
        {
            SetFragment();
        }
    }

    public void SetFragment()
    {
        ((RadioButton) radiogroup_main.getChildAt(0)).setChecked(true);
        ((ExistLinkFragment)baseFragments.get(0)).GetData(true);
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

    public  void setFragment(int index) {
        for (int i = 0; i < baseFragments.size(); i++) {
            Fragment fragment = baseFragments.get(i);

            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (index == i) {
                LogTools.e("showstateshowstate", i + "");
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
    }
}
