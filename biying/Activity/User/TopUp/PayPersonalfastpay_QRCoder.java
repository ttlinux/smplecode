package com.lottery.biying.Activity.User.TopUp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MD5Util;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.util.verifyEditext;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/19. 传统扫码支付
 */
public class PayPersonalfastpay_QRCoder extends BaseActivity implements View.OnClickListener
{
    RelativeLayout toplayout;
    ImageView qrcode;
    Button comit;
    TextView name;
    EditText account,jine,remarkvalue;
    JSONObject jsdata=null;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String data=getIntent().getStringExtra(BundleTag.Data);
        LogTools.e("data", data);

        int type=1;
        try {
            jsdata=new JSONObject(data);
            type=jsdata.optInt("payType",1 );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.pay_formlayout_fastpay_qrcode);//统一一个
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        backtitle.setText(jsdata.optString("payName", ""));
        final TextView textView2=FindView(R.id.textView2);
        radioButton=FindView(R.id.radioButton);
        String remark = jsdata.optString("remark", "");
        LinearLayout noticelayout=FindView(R.id.noticelayout);
        if (remark == null || remark.equalsIgnoreCase("")) {
            noticelayout.setVisibility(View.GONE);
        } else {
            noticelayout.setVisibility(View.VISIBLE);
            textView2.setText(remark);
        }


        toplayout=FindView(R.id.toplayout);
        qrcode=FindView(R.id.qrcode);
        remarkvalue=FindView(R.id.remarkvalue);
        int width= ScreenUtils.getScreenWH(this)[0]/2;
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,width);
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.topMargin=ScreenUtils.getDIP2PX(this,15);
        qrcode.setLayoutParams(layoutParams);
        qrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(qrcode.getDrawable() instanceof BitmapDrawable){
                    Toast.makeText(getApplicationContext(), "图片已保存至相册", Toast.LENGTH_SHORT).show();
//                    File fileDir=new File(getApplication().getExternalCacheDir(),"images");
//                    File file=new File(fileDir.getAbsolutePath()+"/"+System.currentTimeMillis()+".png");
//                    if(file!=null&&file.length()>0){
//                        CameraRollManager rollManager=new CameraRollManager(PayPersonalfastpay_QRCoder.this, Uri.parse(file.getAbsolutePath()));
//                        rollManager.execute();
//                    }
                    MediaStore.Images.Media.insertImage(getContentResolver(), ((BitmapDrawable) qrcode.getDrawable()).getBitmap(), "title", "description");
                }
                return false;
            }
        });
        comit=FindView(R.id.comit);
        comit.setOnClickListener(this);
        jine=FindView(R.id.jine);
        jine.setHint(jsdata.optString("minMaxDes", ""));
        account=FindView(R.id.account);
        name=FindView(R.id.name);
        name.setText(jsdata.optString("payRname", ""));
        ImageLoader imageloader=RepluginMethod.getApplication(this).getImageLoader();
        try {
            jsdata.put("picUrl",jsdata.optString("picUrl", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageloader.displayImage(jsdata.optString("picUrl", "")+"?xxx=1", qrcode, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if(bitmap==null)return;
                String picpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/";
                picpath = picpath + jsdata.optString("picUrl", "").hashCode() + ".jpg";
                saveBitmap(picpath, bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
//        qrcode.setImageBitmap(BitmapFactory.decodeFile(jsdata.optString("payPicpath", "")));


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.comit:
                CommitData();
                break;
        }
    }

    private void CommitData()
    {
        if(!verifyEditext.getInstance().QRCode_pay(this, jine.getText().toString().trim(), account.getText().toString()))
        {
            return;
        }

        String jinestr=jine.getText().toString().trim();
        double money=0;
        if(!jinestr.equalsIgnoreCase(""))
        {
            try
            {
                money=Double.valueOf(jinestr);
                int defaultvalue = jsdata.optInt("minEdu", 100);
                int max = jsdata.optInt("maxPay", 100);
                if (max>0 && money > max ) {
                    ToastUtil.showMessage(this, jine.getHint().toString());
                    return;
                }
                if (money < defaultvalue ) {
                    ToastUtil.showMessage(this, jine.getHint().toString());
                    return;
                }
            }
            catch (Exception ex)
            {
                ToastUtil.showMessage(this, "请正确填写金额");
                return;
            }
        }
        else
        {
            ToastUtil.showMessage(this,"请正确填写金额");
            return;
        }

        RequestParams requestParams=new RequestParams();
        requestParams.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        requestParams.put("money",Httputils.Limit3(money * 1.0000d));
        requestParams.put("payType",jsdata.optString("payType", "1"));
        requestParams.put("account", account.getText().toString().trim());
        requestParams.put("client", "1");//1移动端
        requestParams.put("payNo", jsdata.optString("payNo", ""));
        requestParams.put("module", jsdata.optString("module", ""));
        requestParams.put("userRemark", remarkvalue.getText().toString().trim());

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(RepluginMethod.getApplication(this).getBaseapplicationUsername());
        stringBuilder.append("|");
        stringBuilder.append(Httputils.Limit3(money * 1.0000d));
        stringBuilder.append("|");
        stringBuilder.append(jsdata.optString("payType", "1"));
        stringBuilder.append("|");
        stringBuilder.append(account.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append("1");
        stringBuilder.append("|");
        stringBuilder.append(jsdata.optString("payNo", ""));
        stringBuilder.append("|");
        stringBuilder.append(remarkvalue.getText().toString().trim());
        requestParams.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));

        comit.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.saveScanCodePay,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.e("saveScanCodePay", jsonObject.toString());
                ToastUtil.showMessage(PayPersonalfastpay_QRCoder.this, jsonObject.optString("msg", ""));
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    finish();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
            }
        });
    }

    /** 保存方法 */
    public void saveBitmap(String filename,Bitmap bm) {
        File f = new File(filename);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
