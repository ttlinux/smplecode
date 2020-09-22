package com.lottery.biying.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
public class WebAPPChooserDialog {

    WindowManager windowManager;
    boolean iscloseing = false;
    ArrayList<AppDeatilBean> beans;
    ArrayList<GridView> linearLayouts;
    String Url;
    View mainlayout;
    ArrayList<ImageView> imageviews = new ArrayList<ImageView>();
    LinearLayout linearLayout, bottomview;
    int recordindex = 0;
    OnSelectDefalutWebListener onSelectDefalutWebListener;
    TextView title;

    public WebAPPChooserDialog(Context context, String Url,OnSelectDefalutWebListener onSelectDefalutWebListener) {
        Initview2(context, Url);
        this.onSelectDefalutWebListener=onSelectDefalutWebListener;
    }
    public WebAPPChooserDialog(Activity context, String Url) {
        Initview(context, Url);
    }


    private void Initview2(Context context, String url) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        SearchWebAPP(context, url);//搜索app
        CreatepagerView(context);//数据处理 列表
        CreateMainView(context);//页面处理 viewpager
    }
    private void Initview(Activity context, String url) {
        if(windowManager==null) {
            windowManager = context.getWindowManager();
            SearchWebAPP(context, url);//搜索app
            CreatepagerView(context);//数据处理 列表
            CreateMainView(context);//页面处理 viewpager
        }
    }
    public void setTitle(String titlestr)
    {
        title.setText(titlestr);
    }

    public void SetUrl(String Url){this.Url=Url;}

    public void show() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明

        windowManager.addView(mainlayout, lp);
        mainlayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    dismiss();
                    return true;
                }
                return false;
            }
        });
        mainlayout.setFocusableInTouchMode(true);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(400);
        bottomview.startAnimation(animationSet);
    }

    public void dismiss() {

        if (!iscloseing)
            iscloseing = true;
        else
            return;
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iscloseing = false;
                mainlayout.setOnClickListener(null);
                windowManager.removeView(mainlayout);
                windowManager=null;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        bottomview.startAnimation(animationSet);
    }

    private void CreateMainView(final Context activity) {
        mainlayout = View.inflate(activity, R.layout.dialog_appchooser, null);
        TextView cancel=(TextView)mainlayout.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bottomview = (LinearLayout) mainlayout.findViewById(R.id.bottomview);
        title=(TextView)mainlayout.findViewById(R.id.title);
        LinearLayout indicator = (LinearLayout) mainlayout.findViewById(R.id.indicator);
//        RelativeLayout relayout=(RelativeLayout)mainlayout.findViewById(R.id.relayout);
        mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        AddButton(indicator, linearLayouts.size(), activity);
//        indicator.addView(linearLayouts.get(0));
        ViewPager viewPager = (ViewPager) mainlayout.findViewById(R.id.viewpager);
//        viewPager.setBackgroundColor(Color.RED);
//        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new PagerAdapter() {

            public SparseArray<View> mListViews = new SparseArray<View>();

            @Override
            public int getCount() {
                return linearLayouts.size();
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
                    container.addView(linearLayouts.get(position));
                    mListViews.put(position, linearLayouts.get(position));
                }
                return mListViews.get(position);

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageviews.get(recordindex).setImageDrawable(activity.getResources().getDrawable(R.drawable.yindaocheck));
                recordindex = position;
                imageviews.get(recordindex).setImageDrawable(activity.getResources().getDrawable(R.drawable.yindaochedked));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void AddButton(LinearLayout relayout, int size, Context activity) {
        imageviews.clear();
        linearLayout = new LinearLayout(activity);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        params.bottomMargin=ScreenUtils.getDIP2PX(getActivity(),15);
//        params.setMargins(0,0,0,ScreenUtils.getDIP2PX(getActivity(),15));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(activity, 10));
        linearLayout.setLayoutParams(params);

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(activity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 10;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.yindaocheck));
            imageviews.add(imageView);

            linearLayout.addView(imageView);
        }
        relayout.addView(linearLayout);
        if (imageviews.size() > 0)
            imageviews.get(0).setImageDrawable(activity.getResources().getDrawable(R.drawable.yindaochedked));
    }

    private void SearchWebAPP(Context activity, String Url)//查询web浏览器
    {
        this.Url = Url;
        if (beans != null) return;
        Uri uri = Uri.parse(Url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("url", Url);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> resolveList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        ComponentName cn = new ComponentName("com.android.chrome",
//                "org.chromium.chrome.browser.document.ChromeLauncherActivity");
        //org.chromium.chrome.browser.preferences.website.ManageSpaceActivity
//        intent.setComponent(cn);
        beans = new ArrayList<>();
        String packagename[] = activity.getResources().getStringArray(R.array.app_info);
        AppDeatilBean defaultweb = new AppDeatilBean();
        defaultweb.setTitle(activity.getResources().getString(R.string.app_name));
        defaultweb.setDrawableWeakReference(activity.getResources().getDrawable(R.drawable.defaultimage));
        defaultweb.setIsDefaultWebClient(true);
        beans.add(defaultweb);
        for (ResolveInfo reso :
                resolveList) {
            String apptitle = reso.loadLabel(pm).toString();
            if (Isexist(packagename, reso.activityInfo.packageName))//包名相同 录入
            {
                AppDeatilBean bean = new AppDeatilBean();
                bean.setActvityName(reso.activityInfo.name);
                bean.setDrawableWeakReference(reso.loadIcon(pm));
                bean.setPackageName(reso.activityInfo.packageName);
                bean.setTitle(apptitle);
                beans.add(bean);
            } else {
                if (apptitle.contains("browser") || apptitle.contains("浏览器")) {
                    AppDeatilBean bean = new AppDeatilBean();
                    bean.setActvityName(reso.activityInfo.name);
                    bean.setDrawableWeakReference(reso.loadIcon(pm));
                    bean.setPackageName(reso.activityInfo.packageName);
                    bean.setTitle(apptitle);
                    beans.add(bean);
                }
            }
            LogTools.e("reso", reso.activityInfo.packageName + " " + reso.activityInfo.name + " " + apptitle);
        }
    }

    private void CreatepagerView(Context activity) {
        if (linearLayouts != null) return;
        linearLayouts = new ArrayList<>();
        int pagecount = beans.size() % 4 == 0 ? beans.size() / 4 : beans.size() / 4 + 1;
        for (int i = 0; i < pagecount; i++) {
            GridView mainll = new GridView(activity);
            mainll.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT));
            mainll.setNumColumns(4);
            mainll.setHorizontalSpacing(5);
            mainll.setVerticalSpacing(5);
//            mainll.setBackgroundColor(0x77b8b8b8);
            ArrayList<AppDeatilBean> beanstemp = new ArrayList<>();
            for (int j = 0 + i * 4; j < (4 * (i + 1) > beans.size() ? beans.size() : 4 * (i + 1)); j++) {
                if (j < beans.size())
                    beanstemp.add(beans.get(j));
            }
            LogTools.e("beansbeans", beanstemp.size() + "");
            mainll.setAdapter(new AppAdapter(activity, beanstemp));
            ScreenUtils.setGridHeightBasedOnChildren(mainll);
            linearLayouts.add(mainll);
        }
    }

    private boolean Isexist(String[] strs, String name) {
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].contains(name)) {
                return true;
            }
        }
        return false;
    }

    private class AppDeatilBean {
        String title;
        Drawable drawableWeakReference;
        String packageName;
        boolean isDefaultWebClient = false;

        public boolean isDefaultWebClient() {
            return isDefaultWebClient;
        }

        public void setIsDefaultWebClient(boolean isDefaultWebClient) {
            this.isDefaultWebClient = isDefaultWebClient;
        }

        public String getActvityName() {
            return ActvityName;
        }

        public void setActvityName(String actvityName) {
            ActvityName = actvityName;
        }

        public Drawable getDrawableWeakReference() {
            return drawableWeakReference;
        }

        public void setDrawableWeakReference(Drawable drawableWeakReference) {
            this.drawableWeakReference = drawableWeakReference;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        String ActvityName;

    }

    private class AppAdapter extends BaseAdapter {
        Context activity;
        ArrayList<AppDeatilBean> apps;

        public AppAdapter(Context activity, ArrayList<AppDeatilBean> apps) {
            this.activity = activity;
            this.apps = apps;
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(activity, R.layout.real_video_item, null);
            }
            convertView.setBackgroundColor(0xFFECEBEB);
            TextView textview = (TextView) convertView.findViewById(R.id.videoname);
            ImageView imageview = (ImageView) convertView.findViewById(R.id.videoimg);
            imageview.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(activity,50),ScreenUtils.getDIP2PX(activity,50)));
            textview.setText(apps.get(position).getTitle());
            imageview.setImageDrawable(apps.get(position).getDrawableWeakReference());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (apps.get(position).isDefaultWebClient()) {
                        if (onSelectDefalutWebListener != null) {
                            onSelectDefalutWebListener.OnDefalutselect();
                        }
                        dismiss();
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClassName(apps.get(position).getPackageName(), apps.get(position).getActvityName());
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
//                    intent
                    intent.setData(Uri.parse(Url));
                    activity.startActivity(intent);
                    dismiss();
                }
            });
            return convertView;
        }
    }



    public interface OnSelectDefalutWebListener
    {
        public void OnDefalutselect();
    }
    public OnSelectDefalutWebListener getOnSelectDefalutWebListener() {
        return onSelectDefalutWebListener;
    }

    public void setOnSelectDefalutWebListener(OnSelectDefalutWebListener onSelectDefalutWebListener) {
        this.onSelectDefalutWebListener = onSelectDefalutWebListener;
    }
}
