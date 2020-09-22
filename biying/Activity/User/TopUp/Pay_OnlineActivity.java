package com.lottery.biying.Activity.User.TopUp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.OnlinefastPayBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MD5Util;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.WheelDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2017/10/12. 在线支付 在线扫码支付
 */
public class Pay_OnlineActivity extends BaseActivity implements View.OnClickListener {

    String index;
    JSONObject data;
    Button comit;
    TextView choosedate;
    LinearLayout list;
    EditText jine;
    int patypeSelectIndex = -1;//银行支付列表方式index
    ArrayList<OnlinefastPayBean.BankInfo> bankInfos;
    String titles[];
    LinearLayout noticelayout;
    boolean jumpurl = false;
    boolean init=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_formlayout_fastpay3_normal);

        index = getIntent().getStringExtra(BundleTag.Platform);
        jumpurl = getIntent().getBooleanExtra(BundleTag.Jumpurl, false);
        try {
            data = new JSONObject(getIntent().getStringExtra(BundleTag.Data));
            LogTools.e("Pay_OnlineActivity", data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        if (!index.equalsIgnoreCase("0") && !index.equalsIgnoreCase("1") && !index.equalsIgnoreCase("2") &&
//                !index.equalsIgnoreCase("3") && !index.equalsIgnoreCase("4")
//                && !index.equalsIgnoreCase("5") && !index.equalsIgnoreCase("11")
//                && !index.equalsIgnoreCase("12") && !index.equalsIgnoreCase("13")) {
//            ToastUtil.showMessage(this, "未知支付类型");
//            return;
//        }
        initview();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(init)
        {
            finish();
        }
        else
        {
            init=true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void initview() {
        TextView textView2 = FindView(R.id.textView2);
        noticelayout = FindView(R.id.noticelayout);
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(data.optString("payName", ""));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        comit = (Button) FindView(R.id.comit);
        comit.setText("确定充值");
        comit.setOnClickListener(this);
        String minMaxDes = data.optString("minMaxDes", "");//输入金额的hint
        jine = FindView(R.id.jine);
        jine.setHint(minMaxDes);
        ImageView img = FindView(R.id.img);
        list = FindView(R.id.list);
        ImageLoader imageLoader = RepluginMethod.getApplication(this).getImageLoader();
        imageLoader.displayImage(data.optString("bigPic", "") + "?xxx=1", img);
        String remark = data.optString("remark", "");
        if (remark == null || remark.equalsIgnoreCase("")) {
            noticelayout.setVisibility(View.GONE);
        } else {
            noticelayout.setVisibility(View.VISIBLE);
            textView2.setText(remark);
        }

        View view = FindView(R.id.topview);
        view.setVisibility(View.GONE);
        JSONArray banklist = data.optJSONArray("banklist");
        if (banklist != null && banklist.length() > 0) {
            for (int i = 0; i < list.getChildCount(); i++) {
                if (i < 4 || i > list.getChildCount() - 3) continue;
                list.getChildAt(i).setVisibility(View.GONE);
            }
            choosedate = FindView(R.id.choosedate);
            choosedate.setOnClickListener(this);
            bankInfos = new ArrayList<>();
            titles = new String[banklist.length()];
            for (int i = 0; i < banklist.length(); i++) {
                JSONObject jsonobj = banklist.optJSONObject(i);
                OnlinefastPayBean.BankInfo bankinfo = new OnlinefastPayBean.BankInfo();
                bankinfo.setBankCode(jsonobj.optString("bankCode"));
                bankinfo.setBankImages(jsonobj.optString("bankImages"));
                bankinfo.setBankName(jsonobj.optString("bankName"));
                bankinfo.setClientType(jsonobj.optString("clientType"));
                bankinfo.setThirdpayCode(jsonobj.optString("thirdpayCode"));
                titles[i] = bankinfo.getBankName();
                bankInfos.add(bankinfo);
            }
            choosedate.setText(titles[0]);
            choosedate.setTag(bankInfos.get(0).getBankCode());
        } else {
            for (int i = 0; i < list.getChildCount(); i++) {
                if (i < 2 || i > list.getChildCount() - 3) continue;
                list.getChildAt(i).setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comit:
                fastPay();
                break;
            case R.id.choosedate:
                choosedate.setEnabled(false);
                ShowWheel();
                choosedate.setEnabled(true);
                break;
        }
    }

    public void ShowWheel() {

        if (bankInfos == null || titles == null) {
            LogTools.e("ShowWheel", titles == null ? "YYY" : "NNN");
            LogTools.e("ShowWheel111", bankInfos == null ? "YYY" : "NNN");
            return;
        } else
            LogTools.e("ShowWheel22", "ShowWheel22");
        WheelDialog dialog = new WheelDialog(Pay_OnlineActivity.this);
        dialog.setListener(new WheelDialog.OnChangeListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogTools.e("oldValue", oldValue + " " + newValue);
                patypeSelectIndex = newValue;
                choosedate.setText(bankInfos.get(newValue).getBankName());
                choosedate.setTag(bankInfos.get(newValue).getBankCode());
            }
        });
        if (patypeSelectIndex < 0) {
            patypeSelectIndex = 0;
        }
        dialog.setStrs(titles);
        dialog.show();
    }


    private void fastPay()//个人快捷支付
    {

        LinearLayout temp = (LinearLayout) list.getChildAt(0);
        EditText edittext = (EditText) temp.getChildAt(2);
        double money;
        try {
            money = Double.valueOf(edittext.getText().toString());
            int defaultvalue = data.optInt("minEdu", 100);
            int max = data.optInt("maxPay", 100);
            if (max > 0 && money > max) {
                ToastUtil.showMessage(this, jine.getHint().toString());
                return;
            }
            if (money < defaultvalue) {
                ToastUtil.showMessage(this, jine.getHint().toString());
                return;
            }

        } catch (Exception ex) {
            ToastUtil.showMessage(this, "请输入充值金额");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        params.put("money", Httputils.Limit3(money * 1.0000d));//Httputils.Limit2(money * 1.00d)
//        params.put("payId", data.optString("thirdPayId", ""));
        params.put("client", "1");
        params.put("payType", data.optString("payType", ""));
        params.put("module", data.optString("module", ""));
        if (choosedate != null && choosedate.getTag() != null)
            params.put("bankCode", choosedate.getTag().toString());
//        params.put("pdFrpId","");
//        params.put("pa7CardAmt","");
//        params.put("pa8CardNo","");
//        params.put("pa9CardPwd","");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RepluginMethod.getApplication(this).getBaseapplicationUsername());
        stringBuilder.append("|");
        stringBuilder.append(Httputils.Limit3(money * 1.0000d));
        stringBuilder.append("|");
        stringBuilder.append("1");
        stringBuilder.append("|");
        stringBuilder.append(data.optString("payType", ""));
        params.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));

        comit.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.onlineFastPay, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                ToastUtil.showMessage(Pay_OnlineActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONObject datas = jsonObject.optJSONObject("datas");
                    String url = "http://" + datas.optString("pay_url", "") + "?jsonParams=" + datas.optString("sendParams", "");
                    if (jumpurl) {
                        Intent intent = new Intent();
//                        LogTools.e("mmmm", "http://" + datas.optString("pay_url", "") + "?jsonParams=" + datas.optString("sendParams", ""));
                        intent.setAction("android.intent.action.VIEW");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        startActivity(intent);


//                        new MyWebViewforPay(Pay_OnlineActivity.this,url);
//                        finish();
//                        Intent intent = new Intent(Pay_OnlineActivity.this, webActivity.class);
//                        intent.putExtra(BundleTag.title, "充值");
//                        intent.putExtra(BundleTag.IntentTag, true);
//                        intent.putExtra(BundleTag.URL, url);
//                        intent.putExtra(BundleTag.Method, true);
//                        startActivity(intent);
                    } else {
//                        Intent intent = new Intent(Pay_OnlineActivity.this, webActivity.class);
//                        intent.putExtra(BundleTag.title, "充值");
//                        intent.putExtra(BundleTag.IntentTag, true);
//                        intent.putExtra(BundleTag.URL, url);
//                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
            }
        });
    }
}
