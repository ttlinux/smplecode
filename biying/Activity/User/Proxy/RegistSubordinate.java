package com.lottery.biying.Activity.User.Proxy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.util.VerrifyPointForTextview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12. 注册下级
 */
public class RegistSubordinate extends BaseActivity implements View.OnClickListener{

    RadioGroup radiogroup1;
    String member_type=null;
    TextView confirm,username,password,notice;
    SharedPreferences sharedPreferences;
    JSONObject jsonObject;
    EditText lottery,live,electric,sport,fish,card;
    UserInfoBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_subordinate);
        InitView();
    }

    private void InitView()
    {
        setBackTitleClickFinish();
        String title=getIntent().getStringExtra("title");
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.registsubordinate):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        username = FindView(R.id.username);
        password = FindView(R.id.password);
        notice=FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice, HttpforNoticeinbottom.Tag.zcxj.name, this);
        radiogroup1 = FindView(R.id.radiogroup1);
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.member:
                        member_type = "0";
                        break;
                    case R.id.proxy:
                        member_type = "1";
                        break;
                }
            }
        });
        ((RadioButton)radiogroup1.getChildAt(2)).setChecked(true);

        confirm = FindView(R.id.confirm);
        confirm.setOnClickListener(this);

        sharedPreferences = RepluginMethod.getHostApplication(RegistSubordinate.this).getSharedPreferences(BundleTag.Lottery, MODE_PRIVATE);

        try {
            jsonObject=new JSONObject(sharedPreferences.getString(BundleTag.UserInfo,""));
             bean=UserInfoBean.Analysis_local(jsonObject);
            if(bean==null){
                getuserinfo();
                return;
            }
            setVerify(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setVerify(UserInfoBean bean)
    {
        LinearLayout lotterypoint=FindView(R.id.lotterypoint);
        LinearLayout livepoint=FindView(R.id.livepoint);
        LinearLayout electricpoint=FindView(R.id.electricpoint);
        LinearLayout sportpoint=FindView(R.id.sportpoint);
        LinearLayout finspoint=FindView(R.id.finspoint);
        LinearLayout cardspoint=FindView(R.id.cardspoint);
        lottery=(EditText)lotterypoint.getChildAt(3);
        live=(EditText)livepoint.getChildAt(3);
        electric=(EditText)electricpoint.getChildAt(3);
        sport=(EditText)sportpoint.getChildAt(3);
        fish=(EditText)finspoint.getChildAt(3);
        card=(EditText)cardspoint.getChildAt(3);


        lottery.setText(bean.getBackWater().getLottery());
        live.setText(bean.getBackWater().getLive());
        electric.setText(bean.getBackWater().getElectronic());
        sport.setText(bean.getBackWater().getSport());
        fish.setText(bean.getBackWater().getFish());
        card.setText(bean.getBackWater().getCard());

        VerrifyPointForTextview.AddVerifyView(lottery,
                (TextView) lotterypoint.getChildAt(4), (TextView) lotterypoint.getChildAt(2), bean.getBackWater().getLottery(), this);

        VerrifyPointForTextview.AddVerifyView(live,
                (TextView)livepoint.getChildAt(4),(TextView)livepoint.getChildAt(2),bean.getBackWater().getLive(),this);

        VerrifyPointForTextview.AddVerifyView(electric,
                (TextView)electricpoint.getChildAt(4),(TextView)electricpoint.getChildAt(2),bean.getBackWater().getElectronic(),this);

        VerrifyPointForTextview.AddVerifyView(sport,
                (TextView)sportpoint.getChildAt(4),(TextView)sportpoint.getChildAt(2),bean.getBackWater().getSport(),this);

        VerrifyPointForTextview.AddVerifyView(fish,
                (TextView)finspoint.getChildAt(4),(TextView)finspoint.getChildAt(2),bean.getBackWater().getFish(),this);

        VerrifyPointForTextview.AddVerifyView(card,
                (TextView)cardspoint.getChildAt(4),(TextView)cardspoint.getChildAt(2),bean.getBackWater().getCard(),this);
    }

    private void Regist()
    {
        if(member_type==null)
        {
            ToastUtil.showMessage(this,"请选择用户类型");
            return;
        }
        if(username.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this,"请输入4-16位数字/字母或组合作为帐号");
            return;
        }
        if(password.getText().toString().length()<6)
        {
            ToastUtil.showMessage(this,"请输入6-16位数字/字母或组合作为密码");
            return;
        }
        confirm.setEnabled(false);
        RequestParams requestParams=new RequestParams();
        requestParams.put("userType", member_type);
        requestParams.put("liveBack", live.getText().toString());
        requestParams.put("electronicBack", electric.getText().toString());
        requestParams.put("sportBack", sport.getText().toString());
        requestParams.put("fishBack", fish.getText().toString());
        requestParams.put("cardBack", card.getText().toString());
        requestParams.put("back", lottery.getText().toString());
        requestParams.put("account",username.getText().toString());
        requestParams.put("password", password.getText().toString());
        Httputils.PostWithBaseUrl(Httputils.RegistSubordinate,requestParams,new MyJsonHttpResponseHandler(this,true)
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
                ToastUtil.showMessage(RegistSubordinate.this, jsonObject.optString("msg"));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {


        switch (v.getId())
        {

            case R.id.confirm:
                ArrayList<EditText> editTexts=new ArrayList<>();
                editTexts.add(lottery);
                editTexts.add(live);
                editTexts.add(electric);
                editTexts.add(sport);
                editTexts.add(fish);
                if(VerrifyPointForTextview.VerifyValue(editTexts,bean,this))
                Regist();
                break;
        }
    }

    /**
     * 获取会员信息
     */
    private void getuserinfo() {
        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.UserInfo, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONObject datas = jsonObject.optJSONObject("datas").optJSONObject("userInfo");
                    sharedPreferences.edit()
                            .putString(BundleTag.UserInfo, datas.toString())
                            .commit();
                     bean=UserInfoBean.Analysis_local(datas);
                    setVerify(bean);
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });

    }
}
