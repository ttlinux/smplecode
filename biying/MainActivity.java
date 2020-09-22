package com.lottery.biying;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.lottery.biying.Activity.LotteryActivity;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.BaseParent.BaseFragmentActivity;
import com.lottery.biying.Fragment.CategoryFragemnt;
import com.lottery.biying.Fragment.MainFragment;
import com.lottery.biying.Fragment.SettingFragment;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.Permission;
import com.lottery.biying.util.ToastUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener{


    FrameLayout layout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayList<BaseFragment> bases=new ArrayList<BaseFragment>();
    RadioGroup bottomview;
    public static int isexit=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permission.getPermission(this);
        setIsneedback(false);
        InitView();
    }

    public void InitView()
    {
        layout=FindView(R.id.framelayout);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        //获取com.a8android888.bocforandroid.Fragment下面的所有fragment
//        List<String > list=getClassName(getPackageName()+".Fragment");
//        for (String name:list) {
//            try {
//                BaseFragment basefragment=(BaseFragment)Class.forName(name).newInstance() ;
//                bases.add(basefragment);
////                fragmentTransaction.add(R.id.framelayout,basefragment);
//                fragmentTransaction.hide(basefragment);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        MainFragment act = new MainFragment();
//        Bundle bunle = new Bundle();
//        bunle.putString("msg", msg);
//        bunle.putString("type", type);
//        act.setArguments(bunle);
//        listFragment.add(act);
        bases.add(new MainFragment());
        fragmentTransaction.add(R.id.framelayout,bases.get(0));
        bases.add(new CategoryFragemnt());
        fragmentTransaction.add(R.id.framelayout,bases.get(1));
        bases.add(new SettingFragment());
        fragmentTransaction.add(R.id.framelayout, bases.get(2));

        if(bases.isEmpty())return;
        fragmentTransaction.hide(bases.get(0));
        fragmentTransaction.commitAllowingStateLoss();
        bottomview=(RadioGroup)findViewById(R.id.bottomview);
        bottomview.setOnCheckedChangeListener(this);
        ((RadioButton)bottomview.getChildAt(0)).performClick();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.home){
            setFragment(0);
        }
        if(checkedId==R.id.game){
            setFragment(1);
        }
        if(checkedId==R.id.mine){
            setFragment(2);
        }
    }

    public  void setFragment(int index) {
        for (int i = 0; i < bases.size(); i++) {
            Fragment fragment = bases.get(i);

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

    private long exitTime = 0;
    /**
     * 两次按返回键退出程序
     */
    private boolean mBackKeyPressed = false; // 记录是否有首次按键
    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtil.showMessage(this, "再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                // 延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {
            // 退出程序
            finish();
            if(getApplication() instanceof BaseApplication)
            {
                System.exit(0);
            }
        }
    }
}
