package com.lottery.biying.Activity.User.AccountManage.PersonalData;

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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/16.
 */
public class ModifyNickNameActivity extends BaseActivity {

    TextView confirm,nicknametag;
    EditText newnickname;
    TextView oldname;
    int type=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);

        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        nicknametag=FindView(R.id.nicknametag);
        confirm = FindView(R.id.confirm);
        newnickname = FindView(R.id.newnickname);
        oldname = FindView(R.id.oldname);

        type=getIntent().getIntExtra(BundleTag.Type,-1);
        if(type==0)
        {
            nicknametag.setText("昵称");
            newnickname.setHint("请输入昵称");
            backtitle.setText(getIntent().getStringExtra(BundleTag.Title));
        }
        else if(type==1)
        {
            nicknametag.setText("QQ");
            newnickname.setHint("请输入QQ");
            backtitle.setText(getIntent().getStringExtra(BundleTag.Title));
        }

        oldname.setText(getIntent().getStringExtra(BundleTag.Username));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commit();
            }
        });
    }

    private void Commit() {
        int lenght=newnickname.getText().toString().length();
        if(lenght==0 )
        {
            ToastUtil.showMessage(this,"请输入正确的昵称(10个字符内)");
            return;
        }
        confirm.setEnabled(false);
        RequestParams request = new RequestParams();
        String key=type==1?"qq":"nickName";
        request.put(key, newnickname.getText().toString());

        Httputils.PostWithBaseUrl(Httputils.ModifyNickname, request, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirm.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirm.setEnabled(true);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    setResult(type==1?BundleTag.ResultCode_Change_QQ:BundleTag.ResultCode_Change_Nickname);
                    finish();
                }
                ToastUtil.showMessage(ModifyNickNameActivity.this, jsonObject.optString("msg"));
            }
        });
    }
}
