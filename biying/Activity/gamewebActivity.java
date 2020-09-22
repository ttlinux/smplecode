package com.lottery.biying.Activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.List;

/**
 * @author limingfang
 * @category
 */
public class gamewebActivity extends Activity  {//implements OnClickListener
//
//    private WebView mWebView;
//    private TextView title;
//    private ImageView back;
//    Intent intent;
//    private ProgressBar progress;
//    private String urld = "";
//    RelativeLayout toplayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.gameweb);
//
//        if (getPhoneAndroidSDK() >= 14) {
//            getWindow().setFlags(0x1000000, 0x1000000);
//        }
//        findView();
////        WebAPPChooserDialog webAPPChooserDialog=new WebAPPChooserDialog(this,"http://www.baidu.com");
////        webAPPChooserDialog.show();
//    }
//
//    private void findView() {
//        title = (TextView) findViewById(R.id.title);
//        intent = getIntent();
//        title.setText(intent.getStringExtra("title"));
//        toplayout = (RelativeLayout) findViewById(R.id.toplayout);
//        title.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //获取剪贴板管理器：
//                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//// 创建普通字符型ClipData
//                ClipData mClipData = ClipData.newPlainText("Label", urld);
//// 将ClipData内容放到系统剪贴板里。
//                cm.setPrimaryClip(mClipData);
//            }
//        });
//        back = (ImageView) findViewById(R.id.back);
//        back.setVisibility(View.VISIBLE);
//        back.setOnClickListener(this);
//        mWebView = (WebView) findViewById(R.id.web);
//
//        mWebView.getSettings().setBlockNetworkImage(false);
//        mWebView.getSettings().setBlockNetworkLoads(false);
//        progress = (ProgressBar) findViewById(R.id.progressBar);
//        progress.setMax(100);
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
//        String appCacheDir = this.getApplicationContext()
//                .getDir("cache", Context.MODE_PRIVATE).getPath();
//        webSettings.setAppCachePath(appCacheDir);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAppCacheMaxSize(1024 * 1024 * 10);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setDatabaseEnabled(true);
//        String databaseDir = this.getApplicationContext()
//                .getDir("database", Context.MODE_PRIVATE).getPath();
//        webSettings.setDatabasePath(databaseDir);
//
//        webSettings.setGeolocationEnabled(true);
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setGeolocationDatabasePath(databaseDir);
//        webSettings.setRenderPriority(RenderPriority.HIGH);
//        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
//        mWebView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
//        mWebView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
//        mWebView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
//        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
//        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
//        mWebView.getSettings().setAppCacheEnabled(true);//是否使用缓存
//        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
////        mWebView.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
////        mWebView.getSettings().setUserAgentString("User-Agent:Mozilla/5.0 (Linux; Android 6.0;MZ-MX6 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/45.0.2454.94 Mobile Safari/537.36");//设置用户代理，一般不用
//
//        mWebView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//
//            }
//        });
//        mWebView.setWebViewClient(mWebViewClient);
//        mWebView.setWebChromeClient(mChromeClient);
//        LogTools.e("kkkxzcxzsad3333", mWebView.getSettings().getUserAgentString());
//        if (intent.getStringExtra("flat").equalsIgnoreCase("sa")) {
//            String[] all = intent.getStringExtra("url").split("\\^");
//            LogTools.e("ddafdfadfAA" + all, "dddd");
//            RequestParams requestParams = new RequestParams();
//            requestParams.put("username", all[1]);
//            requestParams.put("token", all[2]);
//            requestParams.put("lobby", all[3]);
//            requestParams.put("lang", all[4]);
//            requestParams.put("returnurl", Httputils.BaseUrl);
//            requestParams.put("mobile", "true");
//            String postDate = "username=" + all[1] + "&token=" + all[2] + "&lobby=" + all[3] + "&lang=" + all[4] + "&returnurl=" + Httputils.BaseUrl + "&mobile=" + "true";
////                            mWebView.loadUrl(all[0] + "?" + requestParams);
//            mWebView.postUrl(all[0] + "?", EncodingUtils.getBytes(postDate, "utf-8"));
////            mWebView.loadUrl(all[0] + "?"+postDate);
//            LogTools.ee("ddafdfadfSS" + EncodingUtils.getBytes(postDate, "utf-8"), all[0] + "?" + requestParams + "");
//        }else {
//        mWebView.loadUrl(intent.getStringExtra("url"));
//        }
////        if(intent.getBooleanExtra(BundleTag.IntentTag,false))//不用请求直接跳转
////        {
////            mWebView.loadUrl(intent.getStringExtra(BundleTag.URL));
////        }
////        else
////        GetData(intent.getStringExtra("flat"));
//    }
//
//
//    private void GetData(String url) {
//        RequestParams requestParams = new RequestParams();
////        if(intent.getStringExtra("flat").equalsIgnoreCase("bbin")){
////            requestParams.put("gameCode",intent.getStringExtra("gamecode"));
////        }
////        if(intent.getStringExtra("flat").equalsIgnoreCase("ag")){
////            requestParams.put("gameCode",intent.getStringExtra("gamecode"));
////        }
//        requestParams.put("gameCode", intent.getStringExtra("gamecode"));
//        requestParams.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
//        requestParams.put("flat", url);
//        Httputils.PostWithBaseUrl(Httputils.gameloginlist, requestParams, new MyJsonHttpResponseHandler(this, true) {
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//            }
//
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                LogTools.e("需要打开的网页", jsonObject.toString());
////                ToastUtil.showMessage(gamewebActivity.this, jsonObject.optString("msg"));
//                if (jsonObject.optString("datas")==null||jsonObject.optString("datas").equalsIgnoreCase("null")||jsonObject.optString("datas").equalsIgnoreCase("")||jsonObject.optString("datas").length()<5)
//                {
//                    ToastUtil.showMessage(gamewebActivity.this,jsonObject.optString("msg",""));
//                    return;
//                }
//
//                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000")|| isFinishing() || isDestroyed())
//                    return;
//
//                String datas = jsonObject.optString("datas", "");
//
////                StringBuilder sb = new StringBuilder();
////                sb.append("<html><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />");
////                sb.append(datas);
////                sb.append("</html>");
//                if (intent.getStringExtra("flat").equalsIgnoreCase("sa")) {
//                    String[] all = datas.split("\\^");
//                    LogTools.e("ddafdfadfAA" + all, "dddd");
//                    RequestParams requestParams = new RequestParams();
//                    requestParams.put("username", all[1]);
//                    requestParams.put("token", all[2]);
//                    requestParams.put("lobby", all[3]);
//                    requestParams.put("lang", all[4]);
//                    requestParams.put("returnurl", Httputils.BaseUrl);
//                    requestParams.put("mobile", "true");
//                    String postDate = "username=" + all[1] + "&token=" + all[2] + "&lobby=" + all[3] + "&lang=" + all[4] + "&returnurl=" + Httputils.BaseUrl + "&mobile=" + "true";
////                            mWebView.loadUrl(all[0] + "?" + requestParams);
//                    mWebView.postUrl(all[0] + "?", EncodingUtils.getBytes(postDate, "utf-8"));
//                    LogTools.ee("ddafdfadfSS" + EncodingUtils.getBytes(postDate, "utf-8"), all[0] + "?" + requestParams + "");
//                } else {
////                            mWebView.loadUrl(datas);
//                    Openurl(datas);
//                    LogTools.e("kkkxzcxzsad", datas);
////                            final Uri uri = Uri.parse(datas);
////                            final Intent it = new Intent();
////                            it.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");
////                            it.setAction(Intent.ACTION_VIEW);
////                            it.addCategory(Intent.CATEGORY_DEFAULT);
////                            it.setData(uri);
////                            startActivity(it);
//
//                }
////
//
////                Intent intent = new Intent();
////                intent.setAction("android.intent.action.VIEW");
////                Uri content_url = Uri.parse(datas);
////                intent.setData(content_url);
////                startActivity(intent);
//            }
//        });
//    }
//
//    private WebViewClient mWebViewClient = new WebViewClient() {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//            if (url.indexOf("http") != -1) {
//                view.loadUrl(url);
//            } else {
//                view.loadUrl("http://" + url);
//            }
//            urld = url;
//            LogTools.e("ddafdfadf", url + "");
//            return false;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//
//        }
//    };
//
//
//    private WebChromeClient mChromeClient = new WebChromeClient() {
//
//        private View myView = null;
//        private CustomViewCallback myCallback = null;
//
//        @Override
//        public void onGeolocationPermissionsShowPrompt(String origin,
//                                                       GeolocationPermissions.Callback callback) {
//            callback.invoke(origin, true, false);
//            super.onGeolocationPermissionsShowPrompt(origin, callback);
//        }
//
//        @Override
//        public void onExceededDatabaseQuota(String url,
//                                            String databaseIdentifier, long currentQuota,
//                                            long estimatedSize, long totalUsedQuota,
//                                            WebStorage.QuotaUpdater quotaUpdater) {
//
//            quotaUpdater.updateQuota(estimatedSize * 2);
//        }
//
//        @Override
//        public void onReachedMaxAppCacheSize(long spaceNeeded,
//                                             long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
//
//            quotaUpdater.updateQuota(spaceNeeded * 2);
//        }
//
//        @Override
//        public void onShowCustomView(View view, CustomViewCallback callback) {
//            if (myCallback != null) {
//                myCallback.onCustomViewHidden();
//                myCallback = null;
//                return;
//            }
//
//            ViewGroup parent = (ViewGroup) mWebView.getParent();
//            parent.removeView(mWebView);
//            parent.addView(view);
//            myView = view;
//            myCallback = callback;
//            mChromeClient = this;
//        }
//
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            result.cancel();
//            return true;
//        }
//
//        @Override
//        public void onHideCustomView() {
//            if (myView != null) {
//                if (myCallback != null) {
//                    myCallback.onCustomViewHidden();
//                    myCallback = null;
//                }
//
//                ViewGroup parent = (ViewGroup) myView.getParent();
//                parent.removeView(myView);
//                parent.addView(mWebView);
//                myView = null;
//            }
//        }
//
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            // TODO 自动生成的方法存根
//
//            if (newProgress == 100) {
//                progress.setVisibility(View.GONE);//加载完网页进度条消失
//            } else {
//                progress.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
//                progress.setProgress(newProgress);//设置进度值
//            }
//
//        }
//    };
//
//
//    @SuppressWarnings({"deprecation", "static-access"})
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.back:
////                    Intent intent = new Intent(this,
////                            StartFragmentActivity.class);
//////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    startActivity(intent);
//                finish();
//                break;
//            default:
//                break;
//        }
//    }
//
//    public static int getPhoneAndroidSDK() {
//        // TODO Auto-generated method stub
//        int version = 0;
//        try {
//            version = Integer.valueOf(Build.VERSION.SDK);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return version;
//
//    }
//
//    class MyWebViewClient extends WebViewClient {
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//            view.loadUrl(url);
//            return super.shouldOverrideUrlLoading(view, url);
//        }
//
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//            mWebView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    public void onPause() {
//        super.onPause();
////        mWebView.onPause();
//    }
//
//    ;
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        mWebView.onResume();
//
////        if(intent.getStringExtra("flat").equalsIgnoreCase("hg")) {
////            if (getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
////                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
////            }
////        }
//
//
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
////        LogTools.e("ddafdfadf的", mWebView.getHeight() + "");
////        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
////            mWebView.setMinimumHeight(mWebView.getHeight() - toplayout.getHeight());
////            LogTools.e("ddafdfadf啊", mWebView.getHeight() + "");
////        }
////        LogTools.e("ddafdfadf", mWebView.getHeight() + "");
//    }
//
//    private boolean check() {
//        PackageManager pm = getPackageManager();
//        List<PackageInfo> infoList = pm
//                .getInstalledPackages(PackageManager.GET_SERVICES);
//        for (PackageInfo info : infoList) {
//            if ("com.adobe.flashplayer".equals(info.packageName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void Openurl(final String Url) {
//        SharedPreferences sharedPreferences = ((BaseApplication) this.getApplication()).getSharedPreferences();
//                        String title = sharedPreferences.getString(BundleTag.chooseBrowser, "");
//     final   WebAPPChooserDialog webAPPChooserDialog=new WebAPPChooserDialog(this,Url);
//        webAPPChooserDialog.show();
//        webAPPChooserDialog.setOnSelectDefalutWebListener(new WebAPPChooserDialog.OnSelectDefalutWebListener() {
//            @Override
//            public void OnDefalutselect() {
//                mWebView.loadUrl(Url);
//                webAPPChooserDialog.dismiss();
////                ToastUtil.showMessage(gamewebActivity.this,"自己");
//            }
//        });
//        webAPPChooserDialog.setTitle(title);
////        Uri uri = Uri.parse(Url);
////        intent = new Intent(Intent.ACTION_VIEW, uri);
////        intent.addCategory(Intent.CATEGORY_DEFAULT);
////        intent.putExtra("url", Url);
////        PackageManager pm = getPackageManager();
////        List<ResolveInfo> resolveList = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
////        ComponentName cn = new ComponentName("com.android.chrome",
////                "org.chromium.chrome.browser.document.ChromeLauncherActivity");
////        //org.chromium.chrome.browser.preferences.website.ManageSpaceActivity
////        intent.setComponent(cn);
////        startActivity(intent);
////        for (ResolveInfo reso:
////        resolveList) {
////            LogTools.e("reso",new Gson().toJson(reso).toString());
////        }
////        if(resolveList.size() > 0) {
//////            String title = "请选择打开方式";
//////            Intent intentChooser = Intent.createChooser(intent, title);
//////            startActivity(intentChooser);
////        }else {
//////            Intent intent = new Intent();
//////            intent.setAction("android.intent.action.VIEW");
//////            Uri content_url = Uri.parse(Url);
//////            intent.setData(content_url);
//////            startActivity(intent);
////        }
////        Uri uri = Uri.parse(Url);
////        intent = new Intent(Intent.ACTION_VIEW, uri);
////        intent.addCategory(Intent.CATEGORY_DEFAULT);
////        intent.putExtra("url", Url);
////        PackageManager pm = getPackageManager();
////        List<ResolveInfo> resolveList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
////
////        for (ResolveInfo reso :
////                resolveList) {
////            String chromestr=reso.activityInfo.packageName;
////            if (chromestr.equalsIgnoreCase("com.android.chrome") || chromestr.equalsIgnoreCase("com.chrome.beta")) {
//////                Intent intent = new Intent();
////                intent.setClassName(reso.activityInfo.packageName, reso.activityInfo.name);
//////                intent.setAction(Intent.ACTION_VIEW);
//////                intent.addCategory(Intent.CATEGORY_DEFAULT);
////                intent.setData(Uri.parse(Url));
////                startActivity(intent);
////                finish();
////                return;
////            }
////        }
////        Intent intent = new Intent();
////        intent.setAction("android.intent.action.VIEW");
////        Uri content_url = Uri.parse(Url);
////        intent.setData(content_url);
////        startActivity(intent);
////        finish();
//    }
}
