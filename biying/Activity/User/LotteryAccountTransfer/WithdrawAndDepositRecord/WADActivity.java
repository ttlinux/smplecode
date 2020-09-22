package com.lottery.biying.Activity.User.LotteryAccountTransfer.WithdrawAndDepositRecord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.Activity.User.Proxy.SalaryManage.NewSubordinateSalaryFragment;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.SubordinateSalaryFragment;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.BaseParent.BaseFragmentActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class WADActivity extends BaseFragmentActivity{

    RadioGroup radiogroup_main;
    int Screenwidth, mCurrentCheckedRadioLeft=0;
    ImageView moveline;
    RelativeLayout ScrollerParentView;
    ArrayList<BaseFragment> baseFragments=new ArrayList<>();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wad);
        Initeview();
    }

    private void Initeview()
    {
        String title=getIntent().getStringExtra("title");
        setBackTitleClickFinish();
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.wadrecord):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        baseFragments.add(new DepositFragment());
        baseFragments.add(new WithdrawFragment());
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout,baseFragments.get(0));
        fragmentTransaction.add(R.id.framelayout,baseFragments.get(1));
        if(baseFragments.isEmpty())return;
        fragmentTransaction.hide(baseFragments.get(0));
        fragmentTransaction.commitAllowingStateLoss();


        Screenwidth = ScreenUtils.getScreenWH(this)[0];
        radiogroup_main=FindView(R.id.radiogroup_main);
        ScrollerParentView=FindView(R.id.ScrollerParentView);
        radiogroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.depositrecord:
                        setMoveline(0);
                        break;
                    case R.id.withdrawrecord:
                        setMoveline(1);
                        break;
                }
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

        setFragment(index);
    }


    public  void setFragment(int index) {
        for (int i = 0; i < baseFragments.size(); i++) {
            Fragment fragment = baseFragments.get(i);

            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (index == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
    }
}
