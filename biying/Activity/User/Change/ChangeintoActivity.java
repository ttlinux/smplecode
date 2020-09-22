package com.lottery.biying.Activity.User.Change;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.Change_Adapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MD5Util;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/1.
 */
public class ChangeintoActivity extends BaseActivity implements View.OnClickListener {
    TextView backtitle, changeintoout, zhuan, zhuan2;
    ImageView imageView;
    Button comit;
    Intent intent;
    String httpurl;
    EditText introduce;

    //    public PublicDialog publicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.changeinto);
        initview();
    }

    private void initview() {
        intent = getIntent();
        imageView = (ImageView) FindView(R.id.imageView);
        backtitle = FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.moneytransfer));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        changeintoout = (TextView) FindView(R.id.changeintoout);
        introduce = (EditText) FindView(R.id.introduce);
        zhuan = (TextView) FindView(R.id.zhuan);
        zhuan2 = (TextView) FindView(R.id.zhuan2);
        if (intent.getStringExtra("type").equalsIgnoreCase("1")) {
            zhuan.setText("转入金额");
            HttpforNoticeinbottom.Gettext("最低转入金额需≥%s元", zhuan2, this);
            changeintoout.setText("由总账户转入到" + intent.getStringExtra("title") + "平台");
            httpurl = Httputils.deposit;
        }
        if (intent.getStringExtra("type").equalsIgnoreCase("2")) {
            httpurl = Httputils.withdraw;
            zhuan.setText("转出金额");
            HttpforNoticeinbottom.Gettext("最低转出金额需≥%s元", zhuan2, this);
//            HttpforNoticeinbottom.GetMainPageData(zhuan2, "edu", this);
            changeintoout.setText("由" + intent.getStringExtra("title") + "平台" + "转出到总账户");
        }
        comit = (Button) FindView(R.id.comit);
        comit.setOnClickListener(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.KEYCODE_BACK) {
            setResult(888);
            finish();
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                setResult(888);
                finish();
                break;
            case R.id.comit:
                String minpay=HttpforNoticeinbottom.geteduMinPay(this);
                double min=0;
                if(minpay!=null)
                {
                    try
                    {
                        min=Double.valueOf(minpay);
                    }
                    catch (NumberFormatException ex)
                    {
                        ex.printStackTrace();
                    }

                }
                if (introduce.getText().toString().trim().length() < 1) {
                    ToastUtil.showMessage(this, "请填写金额");
                    return;
                } else if (min>0 && Integer.valueOf(introduce.getText().toString().trim()) < min) {
                    ToastUtil.showMessage(this, "最低金额需≥" +min + "元");
                    return;
                } else {
                    GetPlatform();
                }
                break;
        }
    }

    private void GetPlatform() {
//        if(publicDialog==null)
//        {
//            publicDialog=new PublicDialog(this);
//        }
//        publicDialog.show();

        Animation circle_anim = AnimationUtils.loadAnimation(this, R.anim.anim_round_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);
        if (circle_anim != null) {
            imageView.startAnimation(circle_anim);  //开始动画
        }
        comit.setEnabled(false);
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        requestParams.put("flat", intent.getStringExtra("flat"));
        requestParams.put("money", Integer.valueOf(introduce.getText().toString())+"");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RepluginMethod.getApplication(this).getBaseapplicationUsername());
        stringBuilder.append("|");
        stringBuilder.append(intent.getStringExtra("flat"));
        stringBuilder.append("|");
        stringBuilder.append(Integer.valueOf(introduce.getText().toString())+"");
        requestParams.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));

        Httputils.PostWithBaseUrl(httpurl, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if (!isDestroyed()) {
                    imageView.clearAnimation();
                    comit.setEnabled(true);
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!isDestroyed()) {
                    imageView.clearAnimation();
                    comit.setEnabled(true);
                }
                LogTools.e("ddddsfafadsfas", jsonObject.toString());
                ToastUtil.showMessage(ChangeintoActivity.this, jsonObject.optString("msg"));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    String index = intent.getStringExtra("index");
                    Change_Adapter.platformBeans.get(Integer.valueOf(index)).setState(true);
                    setResult(888);
                    finish();
                    imageView.clearAnimation();
                }

            }
        });
    }

}
