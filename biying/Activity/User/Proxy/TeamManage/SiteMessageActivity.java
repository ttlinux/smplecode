package com.lottery.biying.Activity.User.Proxy.TeamManage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/7.
 */
public class SiteMessageActivity extends BaseActivity{

    EditText mtitle,content;
    TextView confirm;
    EditText receiver;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitemessage);
        Initview();
    }

    private void Initview()
    {
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.sitemessage));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        receiver=FindView(R.id.receiver);
        type=getIntent().getStringExtra(BundleTag.Type);

        content=FindView(R.id.content);
        mtitle=FindView(R.id.mtitle);
        confirm=FindView(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commit();
            }
        });
        String name=getIntent().getStringExtra(BundleTag.Username);
        if(name==null || name.length()<1)
        {
            receiver.setText("");
            receiver.setHint("请填写接收人");
        }
        else
        {
            receiver.setFocusable(false);
            receiver.setFocusableInTouchMode(false);
            receiver.setText(name);
        }

    }

    private void Commit()
    {
        if(mtitle.getText().toString().length()<1)
        {
            ToastUtil.showMessage(this,"请输入标题");
        }
        if(content.getText().toString().length()<1)
        {
            ToastUtil.showMessage(this,"请输入内容");
        }
        confirm.setEnabled(false);
        RequestParams requestParams=new RequestParams();
        requestParams.put("type",type);
        requestParams.put("title",mtitle.getText().toString());
        requestParams.put("content",content.getText().toString());
        if(receiver.getText().toString().length()>0)
        requestParams.put("receiver",receiver.getText().toString());

        Httputils.PostWithBaseUrl(Httputils.SendSitemEssage,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirm.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirm.setEnabled(true);
                setResult(BundleTag.ResultCode);
                ToastUtil.showMessage(SiteMessageActivity.this, jsonObject.optString("msg"));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                finish();
            }
        });
    }
}
