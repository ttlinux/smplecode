package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lottery.biying.Activity.JumpActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.bean.BannerBean;
import com.lottery.biying.bean.ModuleBean;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/28.
 */
public class MainbannerPagerAdapter extends PagerAdapter {

    Context co;
    ArrayList<BannerBean> BannerBeanList;
    ArrayList<RelativeLayout> relativeLayouts = new ArrayList<RelativeLayout>();
    public SparseArray<View> mListViews = new SparseArray<View>();
    private ImageLoader mImageDownLoader;

    public MainbannerPagerAdapter(Context co,ArrayList<BannerBean> BannerBeanListd) {
        this.co = co;
        this.BannerBeanList = BannerBeanListd;
        mImageDownLoader = RepluginMethod.getApplication((Activity)co)
                .getImageLoader();
    }

    @Override
    public int getCount() {
        return BannerBeanList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mListViews.get(position));
        mListViews.remove(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (mListViews.get(position) == null) {
            RelativeLayout relativeLayout = new RelativeLayout(co);
            relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ImageView imageView = new ImageView(co);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int tag = Integer.valueOf(v.getTag() + "");
                    modeluonclick2(tag);
                }
            });
            mImageDownLoader.displayImage(BannerBeanList.get(position).getImageUrl(), imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (bitmap == null) return;
                    int width = ScreenUtils.getScreenWH((Activity) co)[0] + 2;
                    double height = 0.001 * width / bitmap.getWidth() * 1000.00 * bitmap.getHeight();
                    LogTools.e("bitmap22", bitmap.getWidth() + " " + bitmap.getHeight() + " " + height);
                    ImageView image = (ImageView) view;
                    image.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, (int) height));
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
            relativeLayout.addView(imageView);
            container.addView(relativeLayout);
            mListViews.put(position, relativeLayout);
        }


        return mListViews.get(position);
    }

    public void modeluonclick2(int tag) {
//        JumpActivity.modeluonclick(co, BannerBeanList.get(tag).getLinkType(),
//                BannerBeanList.get(tag).getOpenLinkType(), BannerBeanList.get(tag).getTypeCode(),
//                BannerBeanList.get(tag).getLevel(), BannerBeanList.get(tag).getCateCode(),
//                BannerBeanList.get(tag).getGameCode(), BannerBeanList.get(tag).getBannerName(),
//                BannerBeanList.get(tag).getBannerName(), BannerBeanList.get(tag).getLinkUrl()
//                , BannerBeanList.get(tag).getArticleType(), BannerBeanList.get(tag).getArticleId(), "nopull", "1", BannerBeanList.get(tag).getLinkGroupId());
    }
}
