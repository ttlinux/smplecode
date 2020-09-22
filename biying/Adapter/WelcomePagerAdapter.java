package com.lottery.biying.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lottery.biying.R;


/**
 * 引导页面Pager Adapter
 * 
 * @author Administrator
 * 
 */
public class WelcomePagerAdapter extends PagerAdapter {
	public int[] img = new int[]{R.drawable.yindao, R.drawable.yindao1,
			R.drawable.yindao2};
	Context co;
	public WelcomePagerAdapter(Context co) {
		this.co=co;
	}

	@Override
	public int getCount() {
		return img.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView	imgView = new ImageView(co);
			imgView.setBackgroundResource(img[position]);
		imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		((ViewPager) container).addView(imgView);
		return imgView;
	}

}
