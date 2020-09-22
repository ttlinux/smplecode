package com.lottery.biying.Adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.lottery.biying.BaseParent.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/5.
 */
public class RunChartFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    ArrayList<BaseFragment> baseFragments;

    public RunChartFragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> baseFragments) {
        super(fm);
        mFragmentManager = fm;
        this.baseFragments = baseFragments;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = baseFragments.get(position);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        if (mCurTransaction == null) {
//            // 创建新事务
//            mCurTransaction = mFragmentManager.beginTransaction();
//        }
//        mCurTransaction.detach((Fragment) object);
//    }

    @Override
    public int getCount() {
        return baseFragments.size();
    }
}
