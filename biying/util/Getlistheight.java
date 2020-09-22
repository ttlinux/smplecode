package com.lottery.biying.util;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;


public class Getlistheight {
	public static int setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
	     ListAdapter listAdapter = listView.getAdapter();
	        if (listAdapter == null) {
	            return 0;
	        }
		    int itemheight=0;
	        int totalHeight = 0;
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, listView);
	            listItem.measure(0, 0);
				itemheight=listItem.getMeasuredHeight();
	            totalHeight += itemheight;
	        }
	 
	        ViewGroup.LayoutParams params = listView.getLayoutParams();
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + listView.getPaddingTop() + listView.getPaddingBottom();
	        listView.setLayoutParams(params);
		return  itemheight;
	}

	public static void setListViewHeightBasedOnChildren(GridView listView) {
		// 获取GridView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int a=listAdapter.getCount()%2;
		Log.v("listAdapter>0", ""+listAdapter.getCount());
		int b = 0;

		if(a>0){
			b=listAdapter.getCount()/2+1;
			Log.v("aaa>0", ""+a+b);
		}
		else if(a==0){
			b=listAdapter.getCount()/2;
			Log.v("aaa=0", ""+a+b);
		}
		int index=0;
		int index2=0;
		for (int i = 0; i <(b); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			if(i==0){
				index=listItem.getMeasuredHeight();
			}
			if(i!=0){
				index2=listItem.getMeasuredHeight();
			}
			totalHeight += listItem.getMeasuredHeight();
			LogTools.e("totalHeight"+i,listItem.getMeasuredHeight()+"index"+index+"index2."+index2);
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + listView.getPaddingTop() +listView.getPaddingBottom()-(index-index2);

		listView.setLayoutParams(params);
	}

//	public static void setViewPagerHeightBasedOnChildren(ViewPager viewPager) {
//		// 获取ListView对应的Adapter
//		PagerAdapter listAdapter = viewPager.getAdapter();
//		if (listAdapter == null) {
//			return;
//		}
//		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
//			// listAdapter.getCount()返回数据项的数目
//			 View listItem = ((Adapter) listAdapter) .getView(i, null,
//			 viewPager);
//			 // 计算子项View 的宽高
//			 listItem.measure(0, 0);
//			 // 统计所有子项的总高度
//			 totalHeight += listItem.getMeasuredHeight();
//			View view = viewPager.getChildAt(i);
//			int height = view.getMeasuredHeight();
//			ViewGroup.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewPager
//					.getLayoutParams();
//			layoutParams.height = height;
//			viewPager.setLayoutParams(layoutParams);
//		}
//
//		 ViewGroup.LayoutParams params = viewPager.getLayoutParams();
//		 params.height = totalHeight+ (viewPager.getHeight() * (listAdapter
//		 .getCount() - 1));
//		 // listView.getDividerHeight()获取子项间分隔符占用的高度
//		 // params.height最后得到整个ListView完整显示需要的高度
//		 viewPager.setLayoutParams(params);
//	}
}
