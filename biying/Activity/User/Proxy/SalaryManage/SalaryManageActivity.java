package com.lottery.biying.Activity.User.Proxy.SalaryManage;

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

import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.BaseParent.BaseFragmentActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.view.AccTransferSearchDialog;
import com.lottery.biying.view.UserNameSearchDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3. 日薪管理主页
 */
public class SalaryManageActivity extends BaseFragmentActivity {

    RelativeLayout relayout;
    ImageView moveline;
    int Screenwidth, mCurrentCheckedRadioLeft;
    RadioGroup radiogroup_main;
    TextView backtitle;
    FrameLayout framelayout;
    ArrayList<BaseFragment> baseFragments=new ArrayList<>();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView assistant_icon;
    int currentpage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_manage);
        InitView();
    }

    public void InitView() {

        assistant_icon=FindView(R.id.assistant_icon);
//        assistant_icon.setVisibility(View.VISIBLE);
        assistant_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserNameSearchDialog dialog = new UserNameSearchDialog(SalaryManageActivity.this);
                dialog.setOnEditListener(new UserNameSearchDialog.OnEditListener() {
                    @Override
                    public void OnEdit(String name) {
                        baseFragments.get(2).Notification(name);
                    }
                });
                dialog.show();
            }
        });

        framelayout=FindView(R.id.framelayout);

        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.dailysalarymanage):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        Screenwidth = ScreenUtils.getScreenWH(this)[0];
        moveline = FindView(R.id.Moveline);
        relayout = FindView(R.id.relayout);
        radiogroup_main = FindView(R.id.radiogroup_main);
        radiogroup_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobutton1:
                        setMoveline(0);
                        setFragment(0);
                        break;
                    case R.id.radiobutton2:
                        setMoveline(1);
                        setFragment(1);
                        break;
                    case R.id.radiobutton3:
                        setMoveline(2);
                        setFragment(2);
                        break;
                    case R.id.radiobutton4:
                        setMoveline(3);
                        setFragment(3);
                        break;
                }
            }
        });


        baseFragments.add(new MyDailySalaryFragment());
        baseFragments.add(new DailySalaryPayOffFragment());
        baseFragments.add(new SubordinateSalaryFragment());
        baseFragments.add(new NewSubordinateSalaryFragment());


        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.framelayout,baseFragments.get(0));
        fragmentTransaction.add(R.id.framelayout,baseFragments.get(1));
        fragmentTransaction.add(R.id.framelayout,baseFragments.get(2));
        fragmentTransaction.add(R.id.framelayout,baseFragments.get(3));
        if(baseFragments.isEmpty())return;
        fragmentTransaction.hide(baseFragments.get(0));
        fragmentTransaction.commitAllowingStateLoss();

        ((RadioButton) radiogroup_main.getChildAt(0)).setChecked(true);
    }

    public void SetClick(int index)
    {
        ((RadioButton) radiogroup_main.getChildAt(index*2)).setChecked(true);
    }

    private void setMoveline(int index) {
        currentpage=index;
        moveline.setLayoutParams(new RelativeLayout.LayoutParams(Screenwidth / 4, ScreenUtils.getDIP2PX(this, 5)));
        moveline.setBackgroundColor(getResources().getColor(R.color.loess4));

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;
        translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, Screenwidth / 4 * index, 0f, 0f);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(100);
        moveline.startAnimation(animationSet);//开始上面红色横条图片的动画切换
        mCurrentCheckedRadioLeft = Screenwidth / 4 * index;
    }

    public  void setFragment(int index) {
        if(index==2)
        {
            assistant_icon.setVisibility(View.VISIBLE);
        }
        else {
            assistant_icon.setVisibility(View.GONE);
        }
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
