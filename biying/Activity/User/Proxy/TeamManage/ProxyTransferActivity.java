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
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.TempInterface;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/7. 代理转账
 */
public class ProxyTransferActivity extends BaseActivity {

    TextView confirm;
    EditText money,fundpassword,remark;
    TextView username;
    String Username_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_transfer);
        Initview();
    }

    private void Initview()
    {
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.proxytransfer));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        Username_str=getIntent().getStringExtra(BundleTag.Username);
        confirm=FindView(R.id.confirm);
        username=FindView(R.id.username);
        username.setText(Username_str);
        money=FindView(R.id.money);
        fundpassword=FindView(R.id.fundpassword);
        remark=FindView(R.id.remark);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commit();
            }
        });
    }

    private void Commit()
    {

        if(money.getText().toString().length()<1)
        {
            ToastUtil.showMessage(this,"请输入正确的金额");
            return;
        }

            try
            {
                double value=Double.valueOf(money.getText().toString());
                if(value==0)
                {
                    ToastUtil.showMessage(this,"转账金额需大于0");
                    return;
                }

            }
            catch (NumberFormatException ex)
            {
                ToastUtil.showMessage(this,"请输入正确的金额");
                return;
            }

        if(fundpassword.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this,"请输入正确的资金密码");
            return;
        }

        RequestParams requestParams=new RequestParams();
        if(Username_str!=null && Username_str.length()>0)
        requestParams.put("account",Username_str);
        requestParams.put("zjPassword",fundpassword.getText().toString());
        requestParams.put("money",money.getText().toString());
        requestParams.put("remark",remark.getText().toString());

        confirm.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.Transfertoagent,requestParams,new MyJsonHttpResponseHandler(this,true)
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
                ToastUtil.showMessage(ProxyTransferActivity.this, jsonObject.optString("msg", ""));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    return;
                }
                else
                {
                    //setResult(BundleTag.ResultCode); 存在多级回传失败
                    TempInterface.getInstance().getMessageListener().OnNotify();
                    finish();
                }
            }
        });
    }
}
