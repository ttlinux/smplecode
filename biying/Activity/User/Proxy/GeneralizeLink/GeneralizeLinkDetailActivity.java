package com.lottery.biying.Activity.User.Proxy.GeneralizeLink;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.GeneralizeLinkBean;
import com.lottery.biying.util.BitmapUtils;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/4.
 */
public class GeneralizeLinkDetailActivity extends BaseActivity implements View.OnClickListener{

    LinearLayout mainll,qrlayout;
    TextView linkaddress, wechatRegAddress,backtitle,copylink1,copylink2;
    TextView commit;
    GeneralizeLinkBean bean;
    RelativeLayout weixinlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalize_link_detail);
        Initview();
    }

    private void Initview() {
        weixinlayout=FindView(R.id.weixinlayout);
        qrlayout=FindView(R.id.qrlayout);
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.generalizelink));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        copylink1=FindView(R.id.copylink1);
        copylink2=FindView(R.id.copylink2);
        commit=FindView(R.id.commit);
        commit.setOnClickListener(this);
        copylink2.setOnClickListener(this);
        copylink1.setOnClickListener(this);
        mainll = FindView(R.id.mainll);
        linkaddress = FindView(R.id.linkaddress);
        wechatRegAddress = FindView(R.id.wechatRegAddress);
        try {
             bean = GeneralizeLinkBean.AnaLysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            String values[] = {bean.getRebateRatio(),bean.getLiveRatio(),bean.getElectronicRatio(),bean.getSportRatio(),bean.getFishRatio(), bean.getUserType(), bean.getCreateTime(),
                    bean.getValidTime(), bean.getRegistNum(),bean.getExtAddress(), bean.getQq(),
            bean.getWx(),bean.getSkype()};
            for (int i = 0; i < 13; i++) {
                RelativeLayout relayout = (RelativeLayout) mainll.getChildAt(i);
                TextView textview = (TextView) relayout.getChildAt(1);
                textview.setText(values[i]);
            }
            linkaddress.setText(bean.getRegistAddress());
            wechatRegAddress.setText(bean.getWxAddress());
            ImageView imageview = FindView(R.id.imageView1);
            ImageView imageview2 = FindView(R.id.imageView2);
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            createBitmap(bean.getRegistAddress(), imageview);
            if(bean.getWxFlag().equalsIgnoreCase("1"))
            createBitmap(bean.getWxAddress(), imageview2);
            else
            {
                weixinlayout.setVisibility(View.GONE);
                qrlayout.getChildAt(1).setVisibility(View.GONE);
                wechatRegAddress.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  void createBitmap(String str, ImageView image) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapUtils.create2DCode(str);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        image.setTag(bitmap);
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveImageToGallery((Bitmap)v.getTag());
                return false;
            }
        });
        if (bitmap != null)
            image.setImageBitmap(bitmap);
    }

    public void onClickCopy(TextView v) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(v.getText());
        Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.copylink1:
                onClickCopy(linkaddress);
                break;
            case R.id.copylink2:
                onClickCopy(wechatRegAddress);
                break;
            case R.id.commit:
                Delete();
                break;
        }
    }

    public  void saveImageToGallery( Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "/Lottery");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appDir.getAbsolutePath())));
        ToastUtil.showMessage(this,"图片已经保存");
    }

    private void Delete()
    {
        if(bean==null)
        {
            ToastUtil.showMessage(this,"数据缺失");
            return;
        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("id",bean.getId());
        Httputils.PostWithBaseUrl(Httputils.DeleteLink,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                ToastUtil.showMessage(GeneralizeLinkDetailActivity.this,jsonObject.optString("msg",""));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                    return;
                setResult(BundleTag.ResultCode);
                finish();
            }
        });
    }
}
