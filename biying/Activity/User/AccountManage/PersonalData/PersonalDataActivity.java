package com.lottery.biying.Activity.User.AccountManage.PersonalData;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.CompletePerSonalData;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/9. 个人资料
 */
public class PersonalDataActivity extends BaseActivity implements View.OnClickListener{

    TextView nickname,modifynickname,phone,usertype,QQ,userrealname;
    LinearLayout mainll;
    TextView confirm;
    SharedPreferences sharedPreferences;
    JSONObject jsonObject;
    SwipeHeader swipe;
    UserInfoBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        String title=getIntent().getStringExtra("title");
        setBackTitleClickFinish();
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(TextUtils.isEmpty(title)?getString(R.string.personaldata):title);
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                getuserinfo();
            }
        });
        phone=FindView(R.id.phone);
        modifynickname=FindView(R.id.modifynickname);

        userrealname=FindView(R.id.userrealname);
        nickname=FindView(R.id.nickname);
        usertype=FindView(R.id.usertype);
        mainll=FindView(R.id.mainll);
        QQ=FindView(R.id.QQ);
//        confirm=FindView(R.id.confirm);
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Commit();
//            }
//        });
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case BundleTag.ResultCode_Change_Nickname:
                modifynickname.setText("");
                getuserinfo();
                break;
            case BundleTag.ResultCode_Change_QQ:
                QQ.setText("");
                getuserinfo();
                break;
            case BundleTag.BandPhone:
                phone.setText("");
                getuserinfo();
                break;
            case BundleTag.CompleteData:
                userrealname.setText("");
                getuserinfo();
                break;

        }
    }

    private void getData()
    {
        sharedPreferences = RepluginMethod.getHostApplication(PersonalDataActivity.this).getSharedPreferences(BundleTag.Lottery, MODE_PRIVATE);
        try {
             jsonObject=new JSONObject(sharedPreferences.getString(BundleTag.UserInfo,""));
             bean=UserInfoBean.Analysis_local(jsonObject);
            if(bean==null){
                getuserinfo();
                return;
            }
            if(bean.getNickName()==null || bean.getNickName().length()<1)
            {
                modifynickname.setText("设置>");
                modifynickname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

                modifynickname.setOnClickListener(this);
            }
            else
            {
                modifynickname.setText("");
            }

            if(bean.getQq()==null || bean.getQq().length()<1)
            {
                QQ.setText("设置>");
                QQ.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                QQ.setOnClickListener(this);
            }
            else
            {
                QQ.setText("");
            }
            if(bean.getUserRealName()==null || bean.getUserRealName().length()<1)
            {
                userrealname.setText("完善信息>");
                userrealname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                userrealname.setOnClickListener(this);
            }
            else
            {
                userrealname.setText("");
            }

            if(bean.getMobile() ==null || bean.getMobile().length()<1)
            {
                phone.setTextColor(getResources().getColor(R.color.loess4));
                phone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                phone.setText("设置>");
                phone.setOnClickListener(this);
            }
            else
            {
                phone.setText("");
            }

            String valuse[]={bean.getUserName(),
//                    bean.getAgentDesc(),
                    bean.getUserRealName(),
                    bean.getUserMoney(),
                    bean.getNickName(),
                    bean.getQq(),
                    bean.getMobile(),
                    bean.getBackWater().getLottery(),
                    bean.getBackWater().getElectronic(),
                    bean.getBackWater().getLive(),
                    bean.getBackWater().getSport(),
                    bean.getBackWater().getFish(),
                    bean.getBackWater().getCard(),
//                    bean.getTeamCount(),
                    bean.getLastLoginTime(),
                    bean.getLastLoginIp()};
            int index=0;
            for (int i = 0; i <mainll.getChildCount() ; i++) {
                if(mainll.getChildAt(i) instanceof RelativeLayout)
                {
                    RelativeLayout relativeLayout=((RelativeLayout)mainll.getChildAt(i));
                    int iindex=relativeLayout.getChildCount()>2?2:1;
                    TextView text=(TextView)((RelativeLayout)mainll.getChildAt(i)).getChildAt(iindex);
                    text.setText(valuse[index++]);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId())
        {
            case R.id.phone:
                intent.setClass(this,BandPhoneActivity.class);
                startActivityForResult(intent, BundleTag.RequestCode);
                break;
            case R.id.QQ:
                intent.setClass(this, ModifyNickNameActivity.class);
                intent.putExtra(BundleTag.Title, "设置QQ");
                intent.putExtra(BundleTag.Type,1);
                startActivityForResult(intent, BundleTag.RequestCode);
                break;
            case R.id.modifynickname:
                intent.setClass(this, ModifyNickNameActivity.class);
                intent.putExtra(BundleTag.Title, "设置昵称");
                intent.putExtra(BundleTag.Type,0);
                startActivityForResult(intent, BundleTag.RequestCode);
                break;
            case R.id.userrealname:
                startActivityForResult(new Intent(this, CompletePerSonalData.class), BundleTag.RequestCode);
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
                swipe.setRefreshing(false);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONObject datas = jsonObject.optJSONObject("datas").optJSONObject("userInfo");
                    sharedPreferences.edit()
                            .putString(BundleTag.UserInfo, datas.toString())
                            .commit();
                    getData();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
                swipe.setRefreshing(false);
            }
        });

    }
}
