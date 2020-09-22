package com.lottery.biying.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.MainGridAdapter;
import com.lottery.biying.Adapter.MainbannerPagerAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.BannerBean;
import com.lottery.biying.bean.LotteryPlaytypeBean;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.FileMethod;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AutoVerticalScrollTextView;
import com.lottery.biying.view.MyViewPager;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Administrator on 2017/11/25.
 */
public class MainFragment extends BaseFragment {

    private ImageHandler handler = new ImageHandler(new WeakReference<MainFragment>(this));
    AutoVerticalScrollTextView textdd;
    ArrayList<ImageView> imageviews = new ArrayList<ImageView>();
    ArrayList<BannerBean> Banners = new ArrayList<BannerBean>();
    MyViewPager viewpager;
    GridView gridview;
    int recordindex = 0;
    SwipeHeader swipe;
    ArrayList<MenuBean> Arrmenus = new ArrayList<MenuBean>();
    MainGridAdapter mainGridAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_main, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (textdd != null)
            textdd.PauseScroll();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if (!state && viewpager != null && viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > 1 && getActivity() != null) {
            handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
            textdd.StartScroll();
            LogTools.e("huilai", "回来1");
        }
        if (!state && getActivity() != null) {
            //下载提示信息
            textdd.StartScroll();
        }
    }

    private void InitView() {
        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                GetBannerdata(true);
            }
        });
        swipe.setOnPullListener(new SwipeHeader.OnPullListener() {
            @Override
            public void OnRefresh() {

                GetBannerdata(false);
            }
        });
        textdd = (AutoVerticalScrollTextView) getView().findViewById(R.id.textdd);
        viewpager = (MyViewPager) FindView(R.id.viewpager);
        viewpager.setOffscreenPageLimit(2);
        gridview = FindView(R.id.gridview);


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //配合Adapter的currentItem字段进行设置。
            @Override
            public void onPageSelected(int arg0) {
                if(imageviews.size()==0)return;
                imageviews.get(recordindex).setImageDrawable(getResources().getDrawable(R.drawable.yindaocheck));
                recordindex = arg0;
                imageviews.get(recordindex).setImageDrawable(getResources().getDrawable(R.drawable.yindaochedked));
                handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            //覆写该方法实现轮播效果的暂停和恢复
            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });


        GetBannerdata(true);
    }
    private void GetBannerdata(boolean isinit) {

        if(isinit)
        {
            Arrmenus.clear();
            if(mainGridAdapter==null)
            {
                mainGridAdapter=new MainGridAdapter(getActivity(), Arrmenus);
                gridview.setAdapter(mainGridAdapter);
            }
            else
            {
                mainGridAdapter.notifyDataSetInvalidated();
            }
        }
        Httputils.PostWithBaseUrl(Httputils.mainmethod, new RequestParams(), new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("onSuccessOfMe", jsonObject.toString());
                swipe.setRefreshing(false);
                swipe.setLoadMore(false);
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(getActivity(),jsonObject.optString("msg"));
                    return;
                }
                JSONObject datas = jsonObject.optJSONObject("datas");
                //Banner 图
                JSONArray jsonArray = datas.optJSONArray("banner");

                //内容

                JSONArray menus = datas.optJSONArray("menu");

                for (int i = 0; i < menus.length(); i++) {
                    MenuBean mbean=MenuBean.Handlder_Json(menus.optJSONObject(i));
                    mbean.setIndex(i);
                    Arrmenus.add(mbean);
                    RepluginMethod.getApplication(context).getMenuBeans().put(mbean.getMenuCode(),mbean);
                }

                if(mainGridAdapter==null)
                {
                    mainGridAdapter=new MainGridAdapter(getActivity(), Arrmenus);
                    gridview.setAdapter(mainGridAdapter);
                }
                else
                {
                    mainGridAdapter.notifyDataSetInvalidated();
                }

                //内容
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
                swipe.setLoadMore(false);
            }
        });
    }

    private void AddButton(RelativeLayout relayout, int size) {
        imageviews.clear();
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        linearLayout.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(getActivity(), 10));
        linearLayout.setLayoutParams(params);

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 10;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.yindaocheck));
            imageviews.add(imageView);

            linearLayout.addView(imageView);
        }

        relayout.addView(linearLayout);
    }

    public class ImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<MainFragment> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<MainFragment> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogTools.d("handleMessage", "receive message " + msg.what);
            MainFragment activity = weakReference.get();
            if (MainFragment.this.getActivity() == null) {
                //Activity已经回收，无需再处理UI了
                return;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                activity.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    if (currentItem == activity.viewpager.getAdapter().getCount() - 1) {
                        currentItem = 0;
                    } else
                        currentItem++;
                    activity.viewpager.setCurrentItem(currentItem);
                    //准备下次播放
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }


}
