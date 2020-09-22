package com.lottery.biying.Activity.User.Proxy.TeamManage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.ManageRecordBean;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.util.VerrifyPointForTextview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/7. 返点调配
 */
public class SetpointActivity extends BaseActivity implements View.OnClickListener{


    TextView confirm,username;
    ManageRecordBean bean;
    TextView backtitle;
    String OriginalFandian="";
    SharedPreferences sharedPreferences;
    JSONObject jsonObject;
    EditText lottery,live,electric,sport,fish,card;
    UserInfoBean ubean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_point);
        Initview();
    }

    private void Initview()
    {
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.setpoint));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        confirm = FindView(R.id.confirm);
        username = FindView(R.id.username);
        confirm.setOnClickListener(this);

        try {
             bean= ManageRecordBean.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            username.setText(bean.getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sharedPreferences = RepluginMethod.getHostApplication(SetpointActivity.this).getSharedPreferences(BundleTag.Lottery, MODE_PRIVATE);
        try {
            jsonObject=new JSONObject(sharedPreferences.getString(BundleTag.UserInfo,""));
            ubean=UserInfoBean.Analysis_local(jsonObject);
            if(ubean==null){
                getuserinfo();
                return;
            }
            setVerify(ubean);
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
        LinearLayout fishpoint=FindView(R.id.fishpoint);
        LinearLayout cardpoint=FindView(R.id.cardpoint);
        lottery=(EditText)lotterypoint.getChildAt(2);
        live=(EditText)livepoint.getChildAt(2);
        electric=(EditText)electricpoint.getChildAt(2);
        sport=(EditText)sportpoint.getChildAt(2);
        fish=(EditText)fishpoint.getChildAt(2);
        card=(EditText)cardpoint.getChildAt(2);

        lottery.setText(bean.getBackWater().getLottery());
        live.setText(bean.getBackWater().getLive());
        electric.setText(bean.getBackWater().getElectronic());
        sport.setText(bean.getBackWater().getSport());
        fish.setText(bean.getBackWater().getFish());
        card.setText(bean.getBackWater().getCard());


        VerrifyPointForTextview.AddVerifyView(lottery,
                (TextView) lotterypoint.getChildAt(3), (TextView) lotterypoint.getChildAt(1), bean.getBackWater().getLottery(), this);

        VerrifyPointForTextview.AddVerifyView(live,
                (TextView) livepoint.getChildAt(3), (TextView) livepoint.getChildAt(1), bean.getBackWater().getLive(), this);

        VerrifyPointForTextview.AddVerifyView(electric,
                (TextView) electricpoint.getChildAt(3), (TextView) electricpoint.getChildAt(1), bean.getBackWater().getElectronic(), this);

        VerrifyPointForTextview.AddVerifyView(sport,
                (TextView)sportpoint.getChildAt(3),(TextView)sportpoint.getChildAt(1),bean.getBackWater().getSport(),this);

        VerrifyPointForTextview.AddVerifyView(fish,
                (TextView)fishpoint.getChildAt(3),(TextView)fishpoint.getChildAt(1),bean.getBackWater().getFish(),this);

        VerrifyPointForTextview.AddVerifyView(card,
                (TextView)cardpoint.getChildAt(3),(TextView)cardpoint.getChildAt(1),bean.getBackWater().getCard(),this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.confirm:
                ArrayList<EditText> editTexts=new ArrayList<>();
                editTexts.add(lottery);
                editTexts.add(live);
                editTexts.add(electric);
                editTexts.add(sport);
                editTexts.add(fish);
                editTexts.add(card);
                if(VerrifyPointForTextview.VerifyValue(editTexts,ubean,this))
                    Commit();
                break;
        }
    }

    private void Commit()
    {
        if(bean==null)
        {
            ToastUtil.showMessage(this,"数据异常");
            return;
        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("id",bean.getId());
        requestParams.put("account",bean.getUserName());
        requestParams.put("back",lottery.getText().toString());
        requestParams.put("liveBack",live.getText().toString());
        requestParams.put("electronicBack",electric.getText().toString());
        requestParams.put("fishBack", fish.getText().toString());
        requestParams.put("sportBack",sport.getText().toString());
        requestParams.put("cardBack",card.getText().toString());

        confirm.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.Pointupdate,requestParams,new MyJsonHttpResponseHandler(this,true)
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
                ToastUtil.showMessage(SetpointActivity.this, jsonObject.optString("msg", ""));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(SetpointActivity.this,jsonObject.optString("msg",""));
                    return;
                }
                else
                {
                    Intent intent=new Intent();
                    setResult(BundleTag.ResultCode, intent);
                    finish();
                }

            }
        });
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
                    ubean=UserInfoBean.Analysis_local(datas);
                    setVerify(ubean);
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
