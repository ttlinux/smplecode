package com.lottery.biying.Activity.User.AccountManage.Password;

import android.content.Intent;
import android.os.Bundle;
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

import com.lottery.biying.BaseParent.BaseFragmentActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.ScreenUtils;


/**
 * Created by Administrator on 2017/3/29. 修改密码
 */
public class ModifyPassword extends BaseFragmentActivity {

    private FrameLayout framelayout;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    MoneyPasswordFragment moneyPasswordFragment;
    NormalPasswordFragment normalPasswordFragment;
    RelativeLayout ScrollerParentView;
    int Screenwidth, mCurrentCheckedRadioLeft=0;
    ImageView moveline;
    RadioGroup radiogroup_main;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        String title=getIntent().getStringExtra("title");
        setBackTitleClickFinish();
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.modifypassword):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        Screenwidth = ScreenUtils.getScreenWH(this)[0];
        ScrollerParentView = FindView(R.id.ScrollerParentView);
        framelayout=FindView(R.id.framelayout);
        radiogroup_main=FindView(R.id.radiogroup_main);
        radiogroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction();
                switch (checkedId) {
                    case R.id.accpassword:
                        setMoveline(0);
                        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
                        fragmentTransaction.hide(moneyPasswordFragment);
                        fragmentTransaction.show(normalPasswordFragment);
                        break;
                    case R.id.fundpassword:
                        setMoveline(1);
                        fragmentTransaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
                        fragmentTransaction.hide(normalPasswordFragment);
                        fragmentTransaction.show(moneyPasswordFragment);
                        break;
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_left_in,R.anim.slide_right_out);
         moneyPasswordFragment =new MoneyPasswordFragment();
         normalPasswordFragment=new NormalPasswordFragment();
        fragmentTransaction.add(R.id.framelayout,normalPasswordFragment);
        fragmentTransaction.add(R.id.framelayout, moneyPasswordFragment);
//        fragmentTransaction.hide(moneyPasswordFragment);
//        fragmentTransaction.show(normalPasswordFragment);
        fragmentTransaction.commitAllowingStateLoss();
        ((RadioButton)radiogroup_main.getChildAt(0)).setChecked(true);
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
}
