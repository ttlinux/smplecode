package com.lottery.biying.Activity.User;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONObject;

/*
*客服中心
*
 */

public class CustomerActivity extends BaseActivity implements View.OnClickListener{
    private TextView contact;
    private SharedPreferences sharedPreferences;
    SwipeHeader swipe;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerlayout);
        initview();
    }
    private void initview(){

        swipe=FindView(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                Getdata();
            }
        });

        setBackTitleClickFinish();
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.customer));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        contact=FindView(R.id.contact);
        contact.setOnClickListener(this);
        sharedPreferences=RepluginMethod.getHosttSharedPreferences(this);
         radioGroup=FindView(R.id.radiogroup);

        String siteQq=sharedPreferences.getString(BundleTag.QQ,"error");
        String siteWeixin=sharedPreferences.getString(BundleTag.Weixin,"error");
        String siteTel=sharedPreferences.getString(BundleTag.Tel,"error");
        String siteMobile=sharedPreferences.getString(BundleTag.Phonenum,"error");
        String siteMail=sharedPreferences.getString(BundleTag.Email,"error");
        String URL=sharedPreferences.getString(BundleTag.URL,"error");

        if(siteQq.equalsIgnoreCase("error") || siteWeixin.equalsIgnoreCase("error")
                ||siteTel.equalsIgnoreCase("error") || siteMobile.equalsIgnoreCase("error")
                || siteMail.equalsIgnoreCase("error")||URL.equalsIgnoreCase("error"))
        {
            Getdata();
            return;
        }
        contact.setTag(URL);
        RadioButton radioButton=(RadioButton)radioGroup.getChildAt(0);
        radioButton.setText(getString(R.string.qq)+" "+siteQq);

        RadioButton radioButton2=(RadioButton)radioGroup.getChildAt(2);
        radioButton2.setText(getString(R.string.weixin) + " " + siteWeixin);

        RadioButton radioButton4=(RadioButton)radioGroup.getChildAt(4);
        radioButton4.setText(getString(R.string.phone)+" "+siteTel+" "+siteMobile);

        RadioButton radioButton6=(RadioButton)radioGroup.getChildAt(6);
        radioButton6.setText(getString(R.string.email) + siteMail);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.contact:
                if(contact.getTag()==null)
                {
                    ToastUtil.showMessage(this,"数据缺失,请刷新界面");
                    return;
                }
                Intent intent4 =RepluginMethod.Hostclass(this,"webActivity");
                intent4.putExtra(BundleTag.URL,(String)contact.getTag());
                intent4.putExtra(BundleTag.IntentTag,true);
                intent4.putExtra(BundleTag.title, this.getResources().getText(R.string.contactoffice));
                v.getContext().startActivity(intent4);
                break;
        }
    }

    private void Getdata()
    {

        Httputils.PostWithBaseUrl(Httputils.OnlineService,new RequestParams(),new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                swipe.setRefreshing(false);
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(CustomerActivity.this,jsonObject.optString("msg"));
                    return;
                }
                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONObject siteInfo=datas.optJSONObject("siteInfo");

//                sharedPreferences.edit().putString(BundleTag.SiteInfo,siteInfo.toString()).commit();

                RadioButton radioButton=(RadioButton)radioGroup.getChildAt(0);
                radioButton.setText(getString(R.string.qq)+" "+siteInfo.optString("siteQq"));

                RadioButton radioButton2=(RadioButton)radioGroup.getChildAt(2);
                radioButton2.setText(getString(R.string.weixin)+" " +siteInfo.optString("siteWeixin"));

                RadioButton radioButton4=(RadioButton)radioGroup.getChildAt(4);
                radioButton4.setText(getString(R.string.phone)+" "+siteInfo.optString("siteTel")+" "+siteInfo.optString("siteMobile"));

                RadioButton radioButton6=(RadioButton)radioGroup.getChildAt(6);
                radioButton6.setText(getString(R.string.email) +siteInfo.optString("siteMail"));

                contact.setTag(datas.optString("messengerLink"));
            }
        });
    }
}
