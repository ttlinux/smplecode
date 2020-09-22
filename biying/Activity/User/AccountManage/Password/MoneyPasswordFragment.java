package com.lottery.biying.Activity.User.AccountManage.Password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ReplacementTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MD5Util;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.PublicDialog;

import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/3. 修改资金密码
 */
public class MoneyPasswordFragment extends BaseFragment {

    private EditText confirmpassword,newpassword,oldpassword;
    private TextView confirmmodify;
    private PublicDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_moneypassword,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldpassword=FindView(R.id.oldpassword);
        newpassword=FindView(R.id.newpassword);
        confirmpassword=FindView(R.id.confirmpassword);
        confirmmodify=FindView(R.id.confirmmodify);

        confirmmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPassword();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ModifyPassword()
    {
        if(oldpassword.getText().toString().trim().length()<4){
            ToastUtil.showMessage(getActivity(), "请输入正确的4位旧密码 ");
            return;
        }
        if(newpassword.getText().toString().trim().length()<4){
            ToastUtil.showMessage(getActivity(), "请输入正确的4位新密码 ");
            return;
        }
        if(oldpassword.getText().toString().equalsIgnoreCase(newpassword.getText().toString()))
        {
            ToastUtil.showMessage(getActivity(), "旧密码与新密码不能一致");
            return;
        }
        if(confirmpassword.getText().toString().trim().length()<4){
            ToastUtil.showMessage(getActivity(), "请输入正确的4位确认新密码 ");
            return;
        }
        if(!confirmpassword.getText().toString().equalsIgnoreCase(newpassword.getText().toString()))
        {
            ToastUtil.showMessage(getActivity(), "新密码与确认新密码不一致");
            return;
        }

        if(dialog==null)
        {
            dialog=new PublicDialog(getActivity());
        }
        dialog.show();

        String name= RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername();
        if(name==null || name.equalsIgnoreCase(""))return;
        RequestParams params=new RequestParams();
        params.put("userName",name);
        params.put("newPwd",newpassword.getText().toString().trim());
        params.put("oldPwd",oldpassword.getText().toString().trim());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("|");
        stringBuilder.append(newpassword.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(oldpassword.getText().toString().trim());
        params.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));
        confirmmodify.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.ChangeWithdrawPassword, params, new MyJsonHttpResponseHandler(getActivity(), true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirmmodify.setEnabled(true);
                if (getView() != null && dialog != null) {
                    dialog.dismiss();
                }
                ToastUtil.showMessage(getActivity(), getString(R.string.modifyfail));
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirmmodify.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                if (getView() != null && dialog != null) {
                    dialog.dismiss();
                }
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(getActivity(), getString(R.string.modifysuccess));
                    getActivity().finish();
                } else {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                }
            }
        });
    }

}
