package com.lottery.biying.Activity.User.Proxy.SalaryManage;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.Proxy.TeamManage.TeamManageDetailActivity;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.Salarybean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/4. 下级日薪详情
 */
public class SubordinateSalaryDetailActivity extends BaseActivity implements View.OnClickListener{

    TextView confirm,username;
    LinearLayout infolist;
    String titles[];
    Salarybean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subordinate_salary_detail);
        Initeview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==BundleTag.ResultCode)
        {
            RefreshData();
        }
    }

    private void Initeview()
    {
        titles=getResources().getStringArray(R.array.team_manage_detail_titles);
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.dailysalarydetail));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        confirm=FindView(R.id.confirm);
        username=FindView(R.id.username);
        infolist=FindView(R.id.infolist);
        confirm.setOnClickListener(this);

        try {
             bean=Salarybean.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            fillinData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void fillinData()
    {
        if(bean==null)return;
        infolist.removeAllViews();
        String values[]={bean.getSalaryMoney(),bean.getStartMoney(),bean.getPrivodeFangshi(),bean.getPrivodezhouqi(),bean.getPersonCount(),bean.getMoneyCount()
                ,bean.getLossCount(),bean.getCreateTime()};
            /*list*/
        for (int i = 0; i <titles.length ; i++) {
            RelativeLayout relativelayout=new RelativeLayout(this);
            LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.topMargin= ScreenUtils.getDIP2PX(this, 10);
            relativelayout.setLayoutParams(ll);

            TextView title=new TextView(this);
            title.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            title.setTextColor(getResources().getColor(R.color.gray20));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            title.setText(titles[i]);
            relativelayout.addView(title);

            TextView value=new TextView(this);
            RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            value.setLayoutParams(rl);
            if(i>1)
                value.setTextColor(getResources().getColor(R.color.gray25));
            else
                value.setTextColor(getResources().getColor(R.color.loess4));
            value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            value.setText(values[i]);
            relativelayout.addView(value);

            infolist.addView(relativelayout);
        }

        username.setText(bean.getUserName());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.confirm:
                Intent intent=new Intent();
                intent.setClass(this, RuleofPayoffActivity.class);
                if(bean==null)
                {
                    ToastUtil.showMessage(SubordinateSalaryDetailActivity.this, "日薪数据缺失");
                }
                else
                {
                    intent.putExtra(BundleTag.DailySalary, bean.getSalaryMoney());
                    intent.putExtra(BundleTag.StartSalary,bean.getStartMoney());
                    intent.putExtra(BundleTag.BetAmount,bean.getPersonCountValue());
                    intent.putExtra(BundleTag.Limitedfund,bean.getMoneyCountValue());
                    intent.putExtra(BundleTag.id, bean.getId());
                    intent.putExtra(BundleTag.Username,bean.getUserName());
                }
                startActivityForResult(intent, BundleTag.RequestCode);
                break;
        }
    }

    private void RefreshData()
    {
        RequestParams request=new RequestParams();
        request.put("flag","0");
        request.put("account",bean.getUserName());
        Httputils.PostWithBaseUrl(Httputils.PersonalSalaryDetail,request,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(SubordinateSalaryDetailActivity.this, jsonObject.optString("msg", ""));
                    return;
                }

                JSONArray resultList=jsonObject.optJSONObject("datas").optJSONArray("resultList");
                if(resultList!=null && resultList.length()!=1)return;
                 bean=Salarybean.Analysis(resultList.optJSONObject(0));
                fillinData();
                setResult(BundleTag.ResultCode);
            }
        });
    }
}
