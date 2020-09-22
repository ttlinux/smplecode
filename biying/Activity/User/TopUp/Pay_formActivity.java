package com.lottery.biying.Activity.User.TopUp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MD5Util;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.util.verifyEditext;
import com.lottery.biying.view.CustomDatePicker;
import com.lottery.biying.view.DatetimeDialog;
import com.lottery.biying.view.WheelDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2017/4/3. 公司入款
 */
public class Pay_formActivity extends BaseActivity implements View.OnClickListener {
    TextView bankname, banknumber, bankusername;
    ImageView back;
    Button comit;
    TextView choosedate;
    EditText hkfs;
    DatetimeDialog datetimeDialog;
    int year, month, day, hour, min;
    LinearLayout list;
    ArrayList<EditText> arrayList = new ArrayList<EditText>();
    ArrayList<PayTypeBean> PayTypeBeans = new ArrayList<PayTypeBean>();
    String paytype[];//银行支付列表方式
    int patypeSelectIndex = -1;//银行支付列表方式index
    String index;
    JSONObject data = null;
    TextView textView2;
    TextView jine;
    LinearLayout noticelayout;
    CustomDatePicker customDatePicker;

//    1:微信 2:支付宝 3:财付通 4:银行卡 5:网页支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getStringExtra(BundleTag.Platform);
        try {
            data = new JSONObject(getIntent().getStringExtra(BundleTag.Data));
            LogTools.e("data", data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!index.equalsIgnoreCase("0") && !index.equalsIgnoreCase("1") && !index.equalsIgnoreCase("2") &&
                !index.equalsIgnoreCase("3") && !index.equalsIgnoreCase("4")
                && !index.equalsIgnoreCase("5") && !index.equalsIgnoreCase("11")
                && !index.equalsIgnoreCase("12") && !index.equalsIgnoreCase("13")) {
            ToastUtil.showMessage(this, "未知支付类型");
            return;
        }
        switch (index) {
            case "4":
                setContentView(R.layout.pay_formlayout_bank);
                getBankPayType(false);
                break;
            case "0":
            case "1":
            case "2":
            case "3":
                setContentView(R.layout.pay_formlayout_fastpay_normal);//财付通
                break;
            case "5":
                setContentView(R.layout.pay_formlayout_web_normal);

        }
        View toplayout = FindView(R.id.toplayout);
        noticelayout = FindView(R.id.noticelayout);
        if (Integer.valueOf(index) != 5)
            initview();
        else
            initviewForWebpay();
        SetNotice(index);
    }

    private void SetNotice(String index) {

        String remark = data.optString("remark", "");
        if (remark == null || remark.equalsIgnoreCase("")) {
            noticelayout.setVisibility(View.GONE);
        } else {
            noticelayout.setVisibility(View.VISIBLE);
            textView2.setText(remark);
        }
        if (index.equalsIgnoreCase("4")) duplicate();
    }

    private void duplicate() {
        TextView duplicate_kaihuming = FindView(R.id.duplicate_kaihuming);
        if (duplicate_kaihuming != null)
            duplicate_kaihuming.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                    TextView bankusername = FindView(R.id.bankusername);
                    ClipData mClipData = ClipData.newPlainText("Label", bankusername.getText().toString());
// 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showMessage(Pay_formActivity.this, "用户名已经复制到剪贴板");
                }
            });
        TextView duplicate_bankcode = FindView(R.id.duplicate_bankcode);
        if (duplicate_bankcode != null)
            duplicate_bankcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                    TextView banknumber = FindView(R.id.banknumber);
                    ClipData mClipData = ClipData.newPlainText("Label", banknumber.getText().toString());
// 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showMessage(Pay_formActivity.this, "银行卡号已经复制到剪贴板");
                }
            });
    }

    private void initview() //这里面有 快捷支付 第一个项 公司入款全部项 isinc false 是公司
    {
        textView2 = FindView(R.id.textView2);
        String str = getIntent().getStringExtra(BundleTag.Data);

        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        ImageView img = FindView(R.id.img);
        ImageLoader imageLoader = RepluginMethod.getApplication(this).getImageLoader();
        imageLoader.displayImage(data.optString("bigPic", "") + "?xxx=1", img);
        try {
            data = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String minMaxDes = data.optString("minMaxDes", "");//输入金额的hint
        jine = FindView(R.id.jine);
        jine.setHint(minMaxDes);
//        LogTools.e("data", data.toString());
        comit = (Button) FindView(R.id.comit);
        comit.setOnClickListener(this);
        list = FindView(R.id.list);
        //////////////////////////////////////////////////公司入款
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0 && list.getChildAt(i) instanceof LinearLayout) {
                LinearLayout lin = (LinearLayout) list.getChildAt(i);
                if (lin.getChildAt(2) instanceof EditText) {
                    arrayList.add((EditText) lin.getChildAt(2));
                }
            }
        }
        if (arrayList.size() > 0 && data.optString("minEdu") != null && data.optString("minEdu").length() > 0)
//            arrayList.get(0).setHint("最低金额" + data.optString("minEdu"));
            choosedate = FindView(R.id.choosedate);
        choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        min = c.get(Calendar.MINUTE);

        hkfs = FindView(R.id.hkfs);
        if (index.equalsIgnoreCase("4"))
            backtitle.setText("银行卡充值");//titles[Integer.valueOf(index) - 1]
        else
            backtitle.setText(data.optString("bankType", "") + "充值");//titles[Integer.valueOf(index) - 1]
        TextView bankname = FindView(R.id.bankname);
        TextView bankaccount = FindView(R.id.account);
        TextView banknumber = FindView(R.id.banknumber);
        TextView username = FindView(R.id.username);
        TextView bankusername = FindView(R.id.bankusername);
        switch (index) {
            case "0":
            case "1":
            case "2":
            case "3":
                hkfs.setFocusable(true);
                hkfs.setEnabled(true);
                username.setText(data.optString("bankUser", ""));
                bankname.setText(data.optString("bankType", ""));
                bankaccount.setText("账号:" + data.optString("bankCard", "") + " \n" + data.optString("bankUserDes", ""));
                break;
            case "4":
                hkfs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getBankPayType(true);
                    }
                });
                bankname.setText(data.optString("bankType", ""));
                bankusername.setText(data.optString("bankUser", ""));
                banknumber.setText(data.optString("bankCard", ""));
                bankaccount.setText("开户行：" + data.optString("bankAddress", ""));
                break;
        }
    }

    private void initviewForWebpay() {
        textView2 = FindView(R.id.textView2);
        String titles[] = getResources().getStringArray(R.array.titles_list);
        String str = getIntent().getStringExtra(BundleTag.Data);
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        backtitle.setText("网页充值");
//        title.setText("公司入款");
        try {
            data = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ImageView img = FindView(R.id.img);
        String minMaxDes = data.optString("minMaxDes", "");//输入金额的hint
        jine = FindView(R.id.jine);
        jine.setHint(minMaxDes);
        if (data.optString("minEdu") != null && data.optString("minEdu").length() > 0)
//            jine.setHint("最低金额"+data.optString("minEdu"));
            comit = (Button) FindView(R.id.comit);
        comit.setOnClickListener(this);
        TextView account = FindView(R.id.account);
        TextView clickbutton = FindView(R.id.clickbutton);
        clickbutton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        clickbutton.getPaint().setAntiAlias(true);//抗锯齿
        clickbutton.setOnClickListener(this);
        account.setText("支付网址:" + data.optString("payLink", ""));
        ImageLoader imageLoader = RepluginMethod.getApplication(this).getImageLoader();
        imageLoader.displayImage(data.optString("bigPic", "") + "?xxx=1", img);
    }


    @Override
    public void onClick(View v) {

        String text2 = "";//名字
        String text1 = "";//type
        String text3 = "";//备注

        LogTools.e("arrayList", text2 + "  " + text1);
        switch (v.getId()) {
            case R.id.comit:
                if (index.equalsIgnoreCase("5")) {
                    text1 = "网页支付";
                    text2 = "网页支付";
                } else {
                    text2 = arrayList.get(arrayList.size() - 2).getText().toString().trim();//名字
                    text1 = arrayList.get(arrayList.size() - 3).getText().toString().trim();//type
                    text3 = arrayList.get(arrayList.size() - 1).getText().toString().trim();//备注
                }
                switch (index) {
                    case "0":
                    case "1":
                    case "2":
                    case "3":
                        SaveFast(text1, text2, true, text3);
                        break;
                    case "4":
                        TextView hkfs = FindView(R.id.hkfs);
                        TextView code = FindView(R.id.code);
                        SaveFast(hkfs.getText().toString(), code.getText().toString(), true, null);
                        break;
                    case "5":
                        SaveFast("网页支付", "网页支付", true, null);//现在又说要成功后退出页面
                        break;
                }
                break;
            case R.id.clickbutton:
//                Intent intent = new Intent(this, webActivity.class);
//                intent.putExtra(BundleTag.title, "网页充值");
//                intent.putExtra(BundleTag.IntentTag, true);
//                intent.putExtra(BundleTag.URL, data.optString("payLink", ""));
//                startActivity(intent);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(data.optString("payLink", ""));
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
    }

    public void ShowWheel() {
        if (hkfs.getText().toString().trim().equalsIgnoreCase("")) {
            hkfs.setText(PayTypeBeans.get(0).getCodeShowName());
        }
        WheelDialog dialog = new WheelDialog(Pay_formActivity.this);
        dialog.setListener(new WheelDialog.OnChangeListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogTools.e("oldValue", oldValue + " " + newValue);
                patypeSelectIndex = newValue;
                hkfs.setText(PayTypeBeans.get(newValue).getCodeShowName());
            }
        });
        if (patypeSelectIndex < 0) {
            patypeSelectIndex = 0;
        }
        dialog.setStrs(paytype);
        dialog.show();
    }

    private void ShowDialog() {
//        if (datetimeDialog == null) {
//            datetimeDialog = new DatetimeDialog(this, new DatetimeDialog.OnSelectDialogListener2() {
//                @Override
//                public void OnselectDate(int year, int mouth, int date, int hour, int min) {
//                    choosedate.setText(year + "-" + (mouth + 1) + "-" + date + " " + hour + ":" + min + ":" + "00");
//                }
//            }, true);
//        }
//
//
//        datetimeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                if (!choosedate.getText().toString().trim().contains("-")) {
//                    choosedate.setText(year + "-" + (month + 1) + "-" + day + " " + hour + ":" + min + ":" + "00");
//                }
//            }
//        });
//
//        datetimeDialog.setArg(choosedate.getText().toString().trim());
//        datetimeDialog.show();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
               LogTools.e("tttt",time);
                choosedate.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
        customDatePicker.show(now);
    }

    //暂时没用
//    private void SaveBank(boolean iswebpay) {
//        double money = 0;
//        if (!iswebpay) {
//            try {
//                money = Double.valueOf(arrayList.get(0).getText().toString().trim());
//                if (money == 0) {
//                    ToastUtil.showMessage(this, "请输入金额数大于0");
//                    return;
//                }
//
//            } catch (Exception ex) {
//                ToastUtil.showMessage(this, "请输入数字");
//                return;
//            }
//            if (arrayList.get(arrayList.size() - 1).getText().toString().trim().equalsIgnoreCase("")) {
//                ToastUtil.showMessage(this, "请输入姓名");
//                return;
//            }
//        } else {
//            try {
//                money = Double.valueOf(jine.getText().toString().trim());
//            } catch (Exception ex) {
//                ToastUtil.showMessage(this, "请输入数字");
//                return;
//            }
//
//        }
//
//        RequestParams params = new RequestParams();
//        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
//
//        params.put("payNo", data.optString("payNo", ""));
////        params.put("id", data.optString("id", ""));
//        if (!iswebpay) {
//            params.put("money", money + "");
//            params.put("companyBank", data.optString("hkCompanyBank", ""));
//            params.put("hkType", hkfs.getText().toString().trim());
//            params.put("time", choosedate.getText().toString().trim());//yyyy-MM-dd HH:mm:ss
//            params.put("payType", data.optString("payType", ""));
//            params.put("hkUserName", arrayList.get(arrayList.size() - 1).getText().toString().trim());
//        } else {
//            params.put("money", money + "");
//            params.put("hkType", "");
//            params.put("hkUserName", "");
//            Calendar calendar1 = Calendar.getInstance();
//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            params.put("time", sdf1.format(calendar1.getTime()));//yyyy-MM-dd HH:mm:ss
//            params.put("payType", data.optString("payType", ""));
//        }
//
//        Httputils.PostWithBaseUrl(Httputils.saveIncBankPay, params, new MyJsonHttpResponseHandler(this, true) {
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//            }
//
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                LogTools.e("jsonObject", jsonObject.toString());
//                ToastUtil.showMessage(Pay_formActivity.this, jsonObject.optString("msg", ""));
//                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
//                    finish();
//                }
//            }
//        });
//    }

    private void SaveFast(String hkType, String hkName, final boolean needfinish, String remark) {

        int indexnum = Integer.valueOf(index);
        if (indexnum > -1 && indexnum < 4) {
            if (!verifyEditext.getInstance().Companypay_Type(this, jine.getText().toString().trim(), choosedate.getText().toString().trim(), hkType, hkName)) {
                return;
            }
        } else if (indexnum == 4) {
            //银行
            if (!verifyEditext.getInstance().Companypay_Bank(this, jine.getText().toString().trim(), choosedate.getText().toString().trim(), hkType, hkName)) {
                return;
            }
            if (patypeSelectIndex > -1) {
                hkType = PayTypeBeans.get(patypeSelectIndex).getCodeValue();
            }
        } else {
            String jinestr = jine.getText().toString();
            if (TextUtils.isEmpty(jinestr)) {
                ToastUtil.showMessage(this, "请输入充值金额");
                return;
            }
        }

        double money = 0;
        try {
            if (arrayList.size() == 0) {
                money = Double.valueOf(jine.getText().toString().trim());
            } else {
                money = Double.valueOf(arrayList.get(0).getText().toString().trim());
            }
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
            LogTools.e("Exception", ex.getMessage().toString());
            ToastUtil.showMessage(this, "请正确输入");
            return;
        }
//        if (hkName.equalsIgnoreCase("")) {
//            ToastUtil.showMessage(this, "请输入姓名");
//            return;
//        }
//        if (hkType.equalsIgnoreCase("")) {
//            ToastUtil.showMessage(this, "请选择支付方式");
//            return;
//        }

        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        params.put("money", Httputils.Limit3(money * 1.0000d));
        params.put("payNo", data.optString("payNo", ""));
        if (choosedate != null)
            params.put("time", choosedate.getText().toString().trim());//yyyy-MM-dd HH:mm:ss
        params.put("hkType", hkType.trim());
//        params.put("payType", data.optString("payType", ""));
        params.put("hkName", hkName.trim());
        params.put("client", "1");
        if (remark == null)
            params.put("userRemark", "");
        else
            params.put("userRemark", remark.trim());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RepluginMethod.getApplication(this).getBaseapplicationUsername());
        stringBuilder.append("|");
        stringBuilder.append(Httputils.Limit3(money * 1.0000d));
        stringBuilder.append("|");
//        if(choosedate!=null) {
//            stringBuilder.append(choosedate.getText().toString().trim());
//            stringBuilder.append("|");
//        }
//        else {
//            stringBuilder.append("");
//            stringBuilder.append("|");
//        }
        stringBuilder.append(data.optString("payNo", ""));
        stringBuilder.append("|");
        stringBuilder.append(hkName.trim());
        stringBuilder.append("|");
        stringBuilder.append(hkType.trim());
        stringBuilder.append("|");
        stringBuilder.append("1");
//        stringBuilder.append("|");
//        if(remark==null)
//        {
//            stringBuilder.append("");
//        }
//        else
//            stringBuilder.append(remark.trim());
        LogTools.e("stringBuilder", stringBuilder.toString());
        params.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));

        comit.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.saveIncBankPay, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                ToastUtil.showMessage(Pay_formActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000") && needfinish) {
                    finish();
                }


            }
        });
    }

    private void getBankPayType(final boolean show) {
        if (PayTypeBeans.size() > 0 || paytype != null) {
            if (show)
                ShowWheel();
            return;
        }
        RequestParams requestParams = new RequestParams();
        Httputils.PostWithBaseUrl(Httputils.huikuanType, requestParams, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("pppppp", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;

                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                if (jsonArray == null || jsonArray.length() == 0) return;
                paytype = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsob = jsonArray.optJSONObject(i);
                    PayTypeBean bean = new PayTypeBean();
                    bean.setCodeShowName(jsob.optString("codeShowName", ""));
                    paytype[i] = bean.getCodeShowName();
                    bean.setCodeValue(jsob.optString("codeValue", ""));
                    PayTypeBeans.add(bean);
                }
                if (show)
                    ShowWheel();
            }
        });
    }

    class PayTypeBean {
        public String getCodeShowName() {
            return codeShowName;
        }

        public void setCodeShowName(String codeShowName) {
            this.codeShowName = codeShowName;
        }

        public String getCodeValue() {
            return codeValue;
        }

        public void setCodeValue(String codeValue) {
            this.codeValue = codeValue;
        }

        private String codeShowName;
        private String codeValue;
    }
}