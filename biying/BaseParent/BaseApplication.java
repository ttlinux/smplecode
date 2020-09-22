package com.lottery.biying.BaseParent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryPlaytypeBean;
import com.lottery.biying.bean.LotterySiderBean;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Crasherr;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.NetBroadcastReceiver;
import com.lottery.biying.util.NetUtil;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/3/28.
 */
public class BaseApplication extends Application implements NetBroadcastReceiver.NetEvevt {

    public ImageLoader imageloader = null;
    public DisplayImageOptions options = null;
    public ImageLoaderConfiguration config = null;
    public String Username="";//用户名 不要修改 主程序反射修改此标量
    public static boolean NoticehasClick = false;
    public static NetBroadcastReceiver.NetEvevt evevt;
    public static String packagename="";//com.a8android888.bocforandroid com.boc.bocforandroid
    public Activity activity;
    public boolean isAppBackstage;
    public static Drawable qidong,logo;
    public HashMap<String,MenuBean> MenuBeans;
    public String isgent="";//是不是代理
    public String web_flag="";
    public LotterySiderBean lotterySiderBean;
    SharedPreferences NOT_CountinueMode, CountinueMode, ssopenstate;

    public LotterySiderBean getLotterySiderBean() {
        return lotterySiderBean;
    }

    public void setLotterySiderBean(LotterySiderBean lotterySiderBean) {
        this.lotterySiderBean = lotterySiderBean;
    }

    public String getWeb_flag() {
        return web_flag;
    }

    public void setWeb_flag(String web_flag) {
        this.web_flag = web_flag;
    }

    public String isgent() {
        return isgent;
    }

    public void setisgent(String isgent) {
        this.isgent = isgent;
    }

    public HashMap<String, MenuBean> getMenuBeans() {
        if(MenuBeans==null)
        {
            MenuBeans=new HashMap<>();
        }
        return MenuBeans;
    }

    public void setMenuBeans(HashMap<String,MenuBean> menuBeans) {
        MenuBeans = menuBeans;
    }

    public boolean getIsAppBackstage() {
        return isAppBackstage;
    }

    public Activity getActivity() {
        return activity;
    }

    public boolean isNoticehasClick() {
        return NoticehasClick;
    }

    public void setNoticehasClick(boolean noticehasClick) {
        NoticehasClick = noticehasClick;
    }
    SharedPreferences sharedPreferences;

    ActivityLifecycleCallbacks callbacks;

    /**
     * 网络类型
     */
    public static int netMobile;
    public static boolean isMNC() {
        return Build.VERSION.SDK_INT >= 23;
    }


    public String getBaseapplicationUsername() {
        return Username;
    }
    public void setUsername(String username) {
        LogTools.e("usernameusername",username+"");
        this.Username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Httputils.BaseUrl= ZipUtils.unzip(getString(R.string.baseurl));
//        PackageManager pm =getPackageManager();

        if(!LogTools.isShow)
        {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            mRefWatcher = LeakCanary.install(this);
        }
        LogTools.e("插件packagename", getPackageName());

//        getLocalIPAddress();
//        GetNetIp();
        evevt = this;
//        inspectNet();
        //全局异常捕获初始化
        if (LogTools.isShow) {
            Crasherr crashHandler = Crasherr.getInstance();
            crashHandler.init(getApplicationContext());
        }

        NOT_CountinueMode = RepluginMethod.getHostApplication(this).getSharedPreferences(BundleTag.NOT_CountinueMode, Activity.MODE_PRIVATE);
        ssopenstate = RepluginMethod.getHostApplication(this).getSharedPreferences(BundleTag.isopenstate, Activity.MODE_PRIVATE);
        CountinueMode = RepluginMethod.getHostApplication(this).getSharedPreferences(BundleTag.CountinueMode, Activity.MODE_PRIVATE);
        SharedPreferences shared = RepluginMethod.getHostApplication(this).getSharedPreferences(BundleTag.Lottery, Context.MODE_PRIVATE);
        Httputils.androidkey=shared.getString(BundleTag.AndroidKey, "b13f17404e84b4c0ecee26daba4c09ccafd5c60835f5a43739d472b739f9b9f0");
        Httputils.androidsecret=shared.getString(BundleTag.AndroidSecret, "d29d5926683e28dd5cbeae736f217d00");
        Httputils.BaseUrl=shared.getString(BundleTag.BaseUrl,"http://tc.ybapi.net:88/");
        //测试 http://192.168.0.239:8391/ 6524ee4deb5e785798f39f73afedfb7bafd5c60835f5a43739d472b739f9b9f0
        //正式 http://tc.ybapi.net/ b13f17404e84b4c0ecee26daba4c09ccafd5c60835f5a43739d472b739f9b9f0
        //线上测试环境 103.240.143.141:8083
//        JPushInterface.setLatestNotificationNumber(this, 3);
        qidong=getResources().getDrawable(R.drawable.a4android888qidong);
        logo=getResources().getDrawable(R.drawable.a8android888applogo);
        if(packagename.length()==0)
        packagename=shared.getString(BundleTag.PackageName,"com.boc.lottery");
        setconfig(this);
    }


    //修改每个包的极光推送key
    private void EditJPUSH_APPKEY(String key ) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String msg = appInfo.metaData.getString("JPUSH_APPKEY");
        LogTools.e("JPUSH_APPKEY", "before: " + msg);

        appInfo.metaData.putString("JPUSH_APPKEY", key);

        msg = appInfo.metaData.getString("JPUSH_APPKEY");
        LogTools.e("JPUSH_APPKEY", "after: " + msg);
    }
    public SharedPreferences getSharedPreferences() {
        if(sharedPreferences==null)
        {
            sharedPreferences=getSharedPreferences(BundleTag.APP,MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public ImageLoader getImageLoader() {
        if (imageloader == null)
            imageloader = ImageLoader.getInstance();

        if (imageloader.isInited()) {
            return imageloader;
        }
        return imageloader;
    }

    @SuppressWarnings("deprecation")
    public void setconfig(Context co) {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.defaultimage) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.defaultimage)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.defaultimage) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 是否緩存都內存中
                .cacheOnDisc(true)// 是否緩存到sd卡上
                        // .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .decodingOptions(options)//设置图片的解码配置
                        // .delayBeforeLoading(int delayInMillis)//int
                        // delayInMillis为你设置的下载前的延迟时间
                        // 设置图片加入缓存前，对bitmap进行设置
                        // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
//                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "/Lottery/"+packagename);
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(1440, 2560) // max width, max
                        // height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
                        // .threadPriority(Thread.NORM_PRIORITY - 2)
                        // .denyCacheImageMultipleSizesInMemory()
                        // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You
                        // can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                        // .memoryCacheSize(2 * 1024 * 1024)
                        // .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        // .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                        // // connectTimeout (5 s), readTimeout (30 s)超时时间
                        // .writeDebugLogs() // Remove for release app
                        // .build();//开始构建
                .memoryCache(new WeakMemoryCache())
                .defaultDisplayImageOptions(options)
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
                .discCacheFileCount(60)// 缓存文件的最大个数
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(getApplicationContext());
        return isNetConnect();
    }

    public String getLocalIPAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
//        setDip(intToIp(info.getIpAddress()));
        LogTools.v("macip", intToIp(info.getIpAddress()));
        return intToIp(info.getIpAddress());
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtil.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_NONE) {
//            ToastUtil.showLongTimeMessage(getApplicationContext(), "没有网络", 1000);
            return false;

        }
        return false;
    }

    public static String GetNetIp() {
        String IP = "";
        try {
            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(false);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

// 将流转化为字符串
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));

                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    IP = data.getString("ip") + "(" + data.getString("country")
                            + data.getString("area") + "区"
                            + data.getString("region") + data.getString("city")
                            + data.getString("isp") + ")";

                    Log.e("提示", "您的IP地址是：" + IP);
                } else {
                    IP = "";
                    Log.e("提示", "IP接口异常，无法获取IP地址！");
                }
            } else {
                IP = "";
                Log.e("提示", "网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            IP = "";
            Log.e("提示", "获取IP地址时出现异常，异常信息是：" + e.toString());
        }
        LogTools.e("ipip", IP);
        return IP;
    }

    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public void ClearTiYuCache()//清体育下单记录
    {
        NOT_CountinueMode.edit().clear().commit();
        CountinueMode.edit().clear().commit();
        ssopenstate.edit().clear().commit();
    }

}
