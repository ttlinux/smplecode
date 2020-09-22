package com.lottery.biying.Activity.User.Proxy.SalaryManage;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.DailySalaryitemBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/3. 日薪方法详情
 */
public class DailySalaryManageActivity extends BaseActivity {

    DailySalaryitemBean bean;
    EditText fundpassword;
    TextView confirm;
    LinearLayout infolist;
    String payoff_titles[];
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_salary_manage);
        Initview();
    }

    private void Initview()
    {

        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.salarypayoff));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        payoff_titles=getResources().getStringArray(R.array.payoff_titles);
        fundpassword=FindView(R.id.fundpassword);
        confirm=FindView(R.id.confirm);
        infolist=FindView(R.id.infolist);
        username=FindView(R.id.username);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payoff();
            }
        });
        try {
             bean=DailySalaryitemBean.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            username.setText(bean.getUserName());
            String values[]={bean.getDate(),bean.getPersonCount(),bean.getLossCount(),bean.getBetMoney(),bean.getSalaryMoney(),
                    bean.getSalaryAmount()};
            /*list*/
            for (int i = 0; i <payoff_titles.length ; i++) {
                RelativeLayout relativelayout=new RelativeLayout(this);
                LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.topMargin= ScreenUtils.getDIP2PX(this, 10);
                relativelayout.setLayoutParams(ll);

                TextView title=new TextView(this);
                title.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                title.setTextColor(getResources().getColor(R.color.gray20));
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                title.setText(payoff_titles[i]);
                relativelayout.addView(title);

                TextView value=new TextView(this);
                RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
                value.setLayoutParams(rl);
                if(i!=7)
                    value.setTextColor(getResources().getColor(R.color.black2));
                else
                    value.setTextColor(getResources().getColor(R.color.loess4));
                value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                value.setText(values[i]);
                relativelayout.addView(value);

                infolist.addView(relativelayout);
            }

            if(bean.getStatusValue().equalsIgnoreCase("1"))
            {
                View view=FindView(R.id.fundlayout);
                view.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                ImageView line=FindView(R.id.line);
                line.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Payoff()
    {
        if(bean==null)
        {
            ToastUtil.showMessage(this,"数据缺失");
            return;
        }
        if(fundpassword.getText().toString().length()<4)
        {
            ToastUtil.showMessage(this,"请输入正确的资金密码");
            return;
        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("id",bean.getId());
        requestParams.put("zjPassword",fundpassword.getText().toString());

        confirm.setEnabled(false);

        Httputils.PostWithBaseUrl(Httputils.PayOff,requestParams,new MyJsonHttpResponseHandler(this,true)
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
                ToastUtil.showMessage(DailySalaryManageActivity.this,jsonObject.optString("msg",""));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    return;
                }
                setResult(BundleTag.ResultCode);
                finish();
            }
        });
    }
}
