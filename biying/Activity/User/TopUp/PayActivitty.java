package com.lottery.biying.Activity.User.TopUp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.Pay_Adapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.OnlinefastPayBean;
import com.lottery.biying.bean.PayBankCardBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Getlistheight;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.view.MyListView;
import com.lottery.biying.view.SwipeHeader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1. 充值
 */
public class PayActivitty extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageView back;
    RadioButton rb1, rb2;
    LinearLayout quick_paylayout;
    RelativeLayout into_paylayout;
    MyListView listView;
    Pay_Adapter adapter;
    ArrayList<PayBankCardBean.PayBankCardBean1> beans = new ArrayList<PayBankCardBean.PayBankCardBean1>();
    ImageLoader imageloader;
    TextView textView2;
    ImageView moveline;
    RelativeLayout ScrollerParentView;
    int Screenwidth, mCurrentCheckedRadioLeft=0;
    LinearLayout linearLayout1,linearLayout0,linearLayout2;
    SwipeHeader swipe;
//    ArrayList<JSONObject> incBankPayList=new ArrayList<JSONObject>();
//    ArrayList<JSONObject> thirdPayKjList=new ArrayList<JSONObject>();
//    ArrayList<String> bankTransferTypeList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        initview();
//        GetPayType();//在线二维码支付
//        GetPayType2();//传统二维码支付
//        GetincData();//银行卡
        //        GetincData(true);//快捷支付
        GetAllPayType();//所有支付方式

    }


    private void setMoveline(int index) {
        if (moveline == null) {
            moveline = new ImageView(this);
            RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(Screenwidth / 4, ScreenUtils.getDIP2PX(this, 5));
            moveline.setLayoutParams(rl);
            moveline.setBackgroundColor(getResources().getColor(R.color.loess4));
            ScrollerParentView.addView(moveline);
        }

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;
        translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, Screenwidth / 8 + Screenwidth / 2 * index, 0f, 0f);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(100);
        moveline.startAnimation(animationSet);//开始上面红色横条图片的动画切换
        mCurrentCheckedRadioLeft=Screenwidth / 8 + Screenwidth / 2 * index;
    }

    //快捷支付的
    private void initview() {
        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                GetAllPayType();
            }
        });
        linearLayout2= FindView(R.id.lineview2);
         linearLayout1 = FindView(R.id.lineview1);
        linearLayout0 = FindView(R.id.lineview0);
        listView = (MyListView) FindView(R.id.listview);
        into_paylayout = (RelativeLayout) FindView(R.id.into_paylayout);
        imageloader = RepluginMethod.getApplication(this).getImageLoader();
        back = (ImageView) FindView(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        Screenwidth = ScreenUtils.getScreenWH(this)[0];
        ScrollerParentView = FindView(R.id.ScrollerParentView);

        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.userrecharge):title);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        rb1 = (RadioButton) FindView(R.id.rb1);
        rb1.setText("快捷支付");
        rb1.setOnCheckedChangeListener(this);
        rb2 = (RadioButton) FindView(R.id.rb2);
        rb2.setText("公司入款");
        rb2.setOnCheckedChangeListener(this);

        quick_paylayout =  FindView(R.id.quick_paylayout);
        textView2 = FindView(R.id.textView2);

        HttpforNoticeinbottom.GetMainPageData(textView2, HttpforNoticeinbottom.Tag.fastpay.name, this);
        rb1.setChecked(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb1:
                if (isChecked) {
                    quick_paylayout.setVisibility(View.VISIBLE);
                    into_paylayout.setVisibility(View.GONE);
                    setMoveline(0);
                    HttpforNoticeinbottom.GetMainPageData(textView2, HttpforNoticeinbottom.Tag.fastpay.name, this);
                }
                break;
            case R.id.rb2:
                if (isChecked) {
                    quick_paylayout.setVisibility(View.GONE);
                    into_paylayout.setVisibility(View.VISIBLE);
                    setMoveline(1);
                    HttpforNoticeinbottom.GetMainPageData(textView2, HttpforNoticeinbottom.Tag.compay.name, this);
                }
                break;
        }
    }


    private void analysis0(JSONArray scanCodePayList,String strs)//在线支付
    {
        ArrayList<OnlinefastPayBean> beans = new ArrayList<OnlinefastPayBean>();
        for (int i = 0; i < scanCodePayList.length(); i++) {
            JSONObject jsobj = scanCodePayList.optJSONObject(i);
            OnlinefastPayBean bean = new OnlinefastPayBean();
            bean.setBigPicUrl(jsobj.optString("bigPicUrl", ""));
            bean.setPayName(jsobj.optString("payName", ""));
//                        bean.setPayNo(jsobj.optString("payNo", ""));
//                        bean.setPayRname(jsobj.optString("payRname", ""));
//                        bean.setPicUrl(jsobj.optString("picUrl", ""));
            bean.setSmallPicUrl(jsobj.optString("smallPicUrl", ""));
            bean.setmaxPay(jsobj.optInt("payMaxEdu", 1000));
            bean.setMinEdu(jsobj.optInt("payMinEdu", 1));
            bean.setPayType(jsobj.optInt("payType", 1));
            bean.setMinMaxDes(jsobj.optString("minMaxDes", ""));
            bean.setModule(jsobj.optString("module", ""));
            JSONArray json_banklist=jsobj.optJSONArray("bank");
            if(json_banklist!=null && json_banklist.length()>0)
            {
               ArrayList<OnlinefastPayBean.BankInfo> bankinfos=new ArrayList<>();
                for (int j = 0; j < json_banklist.length(); j++) {
                    JSONObject jsonobj=json_banklist.optJSONObject(j);
                    OnlinefastPayBean.BankInfo bankinfo=new OnlinefastPayBean.BankInfo();
                    bankinfo.setBankCode(jsonobj.optString("bankCode"));
                    bankinfo.setBankImages(jsonobj.optString("bankImages"));
                    bankinfo.setBankName(jsonobj.optString("bankName"));
                    bankinfo.setClientType(jsonobj.optString("clientType"));
                    bankinfo.setThirdpayCode(jsonobj.optString("thirdpayCode"));
                    bankinfos.add(bankinfo);
                }
                bean.setBanklist(bankinfos);
            }

            File cacheDir = StorageUtils.getOwnCacheDirectory(
                    getApplicationContext(), "/BiYing/Cache");
//                        String imagedata=jsobj.optString("payPic", "");
//                        String image16path=cacheDir.getAbsolutePath()+"/"+imagedata.hashCode()+".biying";
//                        stringtoBitmap(imagedata, image16path);
//                        bean.setPayPic(image16path);
            String remark = jsobj.optString("remark", "");
            bean.setRemark(jsobj.optString("remark", ""));
            beans.add(bean);
        }
        if (beans.size() > 0)
            InitView0(beans, strs);
    }

    private void analysis1(JSONArray scanCodePayList,String strs)//在线扫码支付
    {
        ArrayList<OnlinefastPayBean> beans = new ArrayList<OnlinefastPayBean>();
        for (int i = 0; i < scanCodePayList.length(); i++) {
            JSONObject jsobj = scanCodePayList.optJSONObject(i);
            OnlinefastPayBean bean = new OnlinefastPayBean();
            bean.setBigPicUrl(jsobj.optString("bigPicUrl", ""));
            bean.setPayName(jsobj.optString("payName", ""));
//                        bean.setPayNo(jsobj.optString("payNo", ""));
//                        bean.setPayRname(jsobj.optString("payRname", ""));
//                        bean.setPicUrl(jsobj.optString("picUrl", ""));
            bean.setSmallPicUrl(jsobj.optString("smallPicUrl", ""));
            bean.setmaxPay(jsobj.optInt("payMaxEdu", 1000));
            bean.setMinEdu(jsobj.optInt("payMinEdu", 1));
            bean.setPayType(jsobj.optInt("payType", 1));
            bean.setMinMaxDes(jsobj.optString("minMaxDes", ""));
            bean.setModule(jsobj.optString("module", ""));
            JSONArray json_banklist=jsobj.optJSONArray("bank");
            if(json_banklist!=null && json_banklist.length()>0)
            {
                ArrayList<OnlinefastPayBean.BankInfo> bankinfos=new ArrayList<>();
                for (int j = 0; j < json_banklist.length(); j++) {
                    JSONObject jsonobj=json_banklist.optJSONObject(j);
                    OnlinefastPayBean.BankInfo bankinfo=new OnlinefastPayBean.BankInfo();
                    bankinfo.setBankCode(jsonobj.optString("bankCode"));
                    bankinfo.setBankImages(jsonobj.optString("bankImages"));
                    bankinfo.setBankName(jsonobj.optString("bankName"));
                    bankinfo.setClientType(jsonobj.optString("clientType"));
                    bankinfo.setThirdpayCode(jsonobj.optString("thirdpayCode"));
                    bankinfos.add(bankinfo);
                }
                bean.setBanklist(bankinfos);
            }

            File cacheDir = StorageUtils.getOwnCacheDirectory(
                    getApplicationContext(), "/BiYing/Cache");
//                        String imagedata=jsobj.optString("payPic", "");
//                        String image16path=cacheDir.getAbsolutePath()+"/"+imagedata.hashCode()+".biying";
//                        stringtoBitmap(imagedata, image16path);
//                        bean.setPayPic(image16path);
            String remark = jsobj.optString("remark", "");
            bean.setRemark(jsobj.optString("remark", ""));
            beans.add(bean);
        }
        if (beans.size() > 0)
            InitView1(beans, strs);
    }

    private void analysis2(JSONArray scanCodePayList,String strs)//传统扫码支付
    {
        ArrayList<OnlinefastPayBean> beans = new ArrayList<OnlinefastPayBean>();
        for (int i = 0; i < scanCodePayList.length(); i++) {
            JSONObject jsobj = scanCodePayList.optJSONObject(i);
            OnlinefastPayBean bean = new OnlinefastPayBean();
            bean.setBigPicUrl(jsobj.optString("bigPicUrl", ""));
            bean.setPayNname(jsobj.optString("payNname", ""));
            bean.setPayNo(jsobj.optString("payNo", ""));
            bean.setPayRname(jsobj.optString("payRname", ""));
            bean.setPicUrl(jsobj.optString("picUrl", ""));
            bean.setPayName(jsobj.optString("payName", ""));
            bean.setSmallPicUrl(jsobj.optString("smallPicUrl", ""));
            bean.setmaxPay(jsobj.optInt("maxPay", 100));
            bean.setMinEdu(jsobj.optInt("minPay", 1));
            bean.setPayType(jsobj.optInt("payType", 1));
            bean.setMinMaxDes(jsobj.optString("minMaxDes", ""));
            bean.setModule(jsobj.optString("module", ""));
            String remark = jsobj.optString("remark", "");
            bean.setRemark(remark);
            JSONArray json_banklist=jsobj.optJSONArray("bank");
            if(json_banklist!=null && json_banklist.length()>0)
            {
                ArrayList<OnlinefastPayBean.BankInfo> bankinfos=new ArrayList<>();
                for (int j = 0; j < json_banklist.length(); j++) {
                    JSONObject jsonobj=json_banklist.optJSONObject(j);
                    OnlinefastPayBean.BankInfo bankinfo=new OnlinefastPayBean.BankInfo();
                    bankinfo.setBankCode(jsonobj.optString("bankCode"));
                    bankinfo.setBankImages(jsonobj.optString("bankImages"));
                    bankinfo.setBankName(jsonobj.optString("bankName"));
                    bankinfo.setClientType(jsonobj.optString("clientType"));
                    bankinfo.setThirdpayCode(jsonobj.optString("thirdpayCode"));
                    bankinfos.add(bankinfo);
                }
                bean.setBanklist(bankinfos);
            }
//                        File cacheDir = StorageUtils.getOwnCacheDirectory(
//                                getApplicationContext(), "/BiYing/Cache");
//                        String imagedata=jsobj.optString("payPic", "");
//                        String image16path=cacheDir.getAbsolutePath()+"/"+imagedata.hashCode()+".biying";
//                        stringtoBitmap(imagedata, image16path);
//                        bean.setPayPic(image16path);
            beans.add(bean);
        }
        if (beans.size() > 0)
            Initview2(beans,strs);
    }

    private void analysis3(JSONArray jsonArray)//公司
    {
        if (jsonArray != null && jsonArray.length() > 0) {
            beans.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsob = jsonArray.getJSONObject(i);
                    PayBankCardBean.PayBankCardBean1 bean = new PayBankCardBean.PayBankCardBean1();
                    bean.setBankAddress(jsob.optString("bankAddress", ""));
                    bean.setBankCard(jsob.optString("bankCard", ""));
                    bean.setBankCode(jsob.optString("bankCode", ""));
                    bean.setBankType(jsob.optString("bankType", ""));
                    bean.setBankUser(jsob.optString("bankUser", ""));
                    bean.setBigPic(jsob.optString("bigPic", ""));
                    bean.setMinEdu(jsob.optInt("minEdu", 100));
                    bean.setMaxPay(jsob.optInt("maxPay", 100)+"");
                    bean.setPayLink(jsob.optString("payLink", ""));
                    bean.setPayNo(jsob.optString("payNo", ""));
                    bean.setPayType(jsob.optString("payType", ""));
                    bean.setSmallPic(jsob.optString("smallPic", ""));
                    bean.setMinMaxDes(jsob.optString("minMaxDes", ""));
                    String remark = jsob.optString("remark", "");
                    bean.setRemark(jsob.optString("remark", ""));

                    beans.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            setAdapter(beans);
        }
    }

    private void GetAllPayType()
    {
        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.AllPayType,params,new MyJsonHttpResponseHandler(this,false)
        {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                swipe.setRefreshing(false);
                LogTools.ee("GetAllPayType", jsonObject.toString());
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))return;
                JSONObject datas=jsonObject.optJSONObject("datas");

                linearLayout1.removeAllViews();
                linearLayout0.removeAllViews();
                linearLayout2.removeAllViews();

                JSONObject online=datas.optJSONObject("online");
                analysis0(online.optJSONArray("list"),online.optString("title"));

                JSONObject onlineSaoma=datas.optJSONObject("onlineSaoma");
                analysis1(onlineSaoma.optJSONArray("list"),onlineSaoma.optString("title"));

                JSONObject chuantongSaoma=datas.optJSONObject("chuantongSaoma");
                analysis2(chuantongSaoma.optJSONArray("list"),chuantongSaoma.optString("title"));

                JSONObject bank=datas.optJSONObject("bank");
                analysis3(bank.optJSONArray("list"));
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
            }
        });
    }

    private void setAdapter(ArrayList<PayBankCardBean.PayBankCardBean1> bankTransferTypeList) {
        if (adapter == null) {
            adapter = new Pay_Adapter(this, bankTransferTypeList);
            listView.setAdapter(adapter);
            Getlistheight.setListViewHeightBasedOnChildren(listView);
        } else {
            adapter.NotifyAdapter(bankTransferTypeList);
            Getlistheight.setListViewHeightBasedOnChildren(listView);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void InitView0(final ArrayList<OnlinefastPayBean> list2,String title)/////在线支付
    {
        LogTools.e("InitView0", "InitView0");
        TextView title1 = FindView(R.id.title0);
        title1.setVisibility(View.VISIBLE);
        title1.setText(title);

        for (int i = 0; i < list2.size(); i++) {
            final RelativeLayout view = (RelativeLayout) View.inflate(this, R.layout.item_temple_relation, null);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    switch (list2.get(tag).getPayType()) {
                        case 1:
                            HttpforNoticeinbottom.hashMap2.put("onlineWx", list2.get(tag).getRemark());
                            break;
                        case 2:
                            HttpforNoticeinbottom.hashMap2.put("onlineAlipay", list2.get(tag).getRemark());
                            break;
                        default:
                            HttpforNoticeinbottom.hashMap2.put("onlineTenpay", list2.get(tag).getRemark());
                            break;
                    }
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(BundleTag.Platform, list2.get(tag).getPayType() + "");
                    intent.putExtra(BundleTag.Data, new Gson().toJson(list2.get(tag)));
                    intent.putExtra(BundleTag.Jumpurl, true);
                    intent.setClass(PayActivitty.this, Pay_OnlineActivity.class);
                    startActivity(intent);
                }
            });
            int tag = (int) view.getTag();
            TextView radioButton = (TextView) view.findViewById(R.id.name);
            radioButton.setText(list2.get(tag).getPayName());

            ImageView icon= (ImageView) view.findViewById(R.id.icon);
            imageloader.displayImage(list2.get(i).getSmallPicUrl(), icon);

            linearLayout0.addView(view);
        }
    }

    private void InitView1(final ArrayList<OnlinefastPayBean> list2,String title)/////在线二维码支付
    {
        TextView title1 = FindView(R.id.title1);
        title1.setVisibility(View.VISIBLE);
        title1.setText(title);
        LinearLayout linearLayout1 = FindView(R.id.lineview1);
        for (int i = 0; i < list2.size(); i++) {
            final RelativeLayout view = (RelativeLayout) View.inflate(this, R.layout.item_temple_relation, null);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    switch (list2.get(tag).getPayType()) {
                        case 1:
                            HttpforNoticeinbottom.hashMap2.put("onlineWx", list2.get(tag).getRemark());
                            break;
                        case 2:
                            HttpforNoticeinbottom.hashMap2.put("onlineAlipay", list2.get(tag).getRemark());
                            break;
                        default:
                            HttpforNoticeinbottom.hashMap2.put("onlineTenpay", list2.get(tag).getRemark());
                            break;
                    }
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(BundleTag.Platform, list2.get(tag).getPayType() + "");
                    intent.putExtra(BundleTag.Data, new Gson().toJson(list2.get(tag)));
                    intent.putExtra(BundleTag.Jumpurl, true);
                    intent.setClass(PayActivitty.this, Pay_OnlineActivity.class);
                    startActivity(intent);
                }
            });
            int tag = (int) view.getTag();
            TextView radioButton = (TextView) view.findViewById(R.id.name);
            radioButton.setText(list2.get(tag).getPayName());

            ImageView icon= (ImageView) view.findViewById(R.id.icon);
            imageloader.displayImage(list2.get(i).getSmallPicUrl(), icon);
            linearLayout1.addView(view);
        }
    }

    private void Initview2(ArrayList<OnlinefastPayBean> list2,String title)//传统二维码支付
    {
        TextView title2 = FindView(R.id.title2);
        title2.setVisibility(View.VISIBLE);
        title2.setText(title);
        for (int i = 0; i < list2.size(); i++) {
            final OnlinefastPayBean bean = list2.get(i);
            final RelativeLayout view2 = (RelativeLayout) View.inflate(this, R.layout.item_temple_relation, null);
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (bean.getPayType()) {
                        case 1:
                            HttpforNoticeinbottom.hashMap2.put("traditWx", bean.getRemark());
                            break;
                        case 2:
                            HttpforNoticeinbottom.hashMap2.put("traditAlipay", bean.getRemark());
                            break;
                        default:
                            HttpforNoticeinbottom.hashMap2.put("traditTenpay", bean.getRemark());
                            break;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(BundleTag.Data, new Gson().toJson(bean));
                    intent.setClass(PayActivitty.this, PayPersonalfastpay_QRCoder.class);
                    startActivity(intent);
                }
            });
            TextView radioButton = (TextView) view2.findViewById(R.id.name);
            radioButton.setText(bean.getPayName());

            ImageView icon=(ImageView) view2.findViewById(R.id.icon);
            imageloader.displayImage(bean.getSmallPicUrl(),icon);
            linearLayout2.addView(view2);
        }
    }

    public void stringtoBitmap(String string, String output) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        byte bitmapArray[] = null;

        try {
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null) return;
        try {
            FileOutputStream out = new FileOutputStream(new File(output));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
