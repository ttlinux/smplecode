package com.lottery.biying.Activity.User.Proxy.TeamManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.CompletePerSonalData;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.RuleofPayoffActivity;
import com.lottery.biying.Activity.User.Record.OrderHistoryActivity;
import com.lottery.biying.Activity.User.Record.TraceHistoryActivity;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.ManageRecordBean;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.TempInterface;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ModifyRemarkDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/5.
 */
public class TeamManageDetailActivity extends BaseActivity implements View.OnClickListener{


    TextView backtitle,type,name;
    ImageLoader imageLoader;
    ImageView personal_icon;
    LinearLayout detailll;
    TextView betrecord,tracereocred,setpoint,setsalary,sitemessage,proxytransfer,modifyremark;
    public  TextView remark;
    ManageRecordBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manage_detail);
        Initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==BundleTag.ResultCode)
        {
            RefreshData();
        }
    }

    private void Initview()
    {
        imageLoader= RepluginMethod.getApplication(this).getImageLoader();
        backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.teammanage));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        personal_icon=FindView(R.id.personal_icon);
        remark=FindView(R.id.remark);
        type=FindView(R.id.type);
        name=FindView(R.id.name);
        detailll=FindView(R.id.detailll);

        betrecord=FindView(R.id.betrecord);
        betrecord.setOnClickListener(this);
        tracereocred=FindView(R.id.tracereocred);
        tracereocred.setOnClickListener(this);
        setpoint=FindView(R.id.setpoint);
        setpoint.setOnClickListener(this);
        setsalary=FindView(R.id.setsalary);
        setsalary.setOnClickListener(this);
        sitemessage=FindView(R.id.sitemessage);
        sitemessage.setOnClickListener(this);
        proxytransfer=FindView(R.id.proxytransfer);
        proxytransfer.setOnClickListener(this);
        modifyremark=FindView(R.id.modifyremark);
        modifyremark.setOnClickListener(this);

        try {
             bean= ManageRecordBean.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            FillInData();
//            remark.setText("备注 : "+bean.getRemark());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void FillInData()
    {
        if(bean.getOperateFlag().equalsIgnoreCase("0"))
        {
            setpoint.setVisibility(View.INVISIBLE);
            setsalary.setVisibility(View.INVISIBLE);
            sitemessage.setVisibility(View.INVISIBLE);
            proxytransfer.setVisibility(View.INVISIBLE);
            modifyremark.setVisibility(View.INVISIBLE);
        }
        name.setText(bean.getUserName());
        type.setText(bean.getUserType());
        String valuse[]={bean.getBetBack()+"%",bean.getTeamCount(),bean.getUserMoney(),
                bean.getTeamMoney(),bean.getCreateTime(),bean.getLastLoginTime(),bean.getSalaryInfo(),bean.getRemark()};
        for (int i = 0; i < detailll.getChildCount(); i++) {
            RelativeLayout rl=(RelativeLayout)detailll.getChildAt(i);
            TextView textview=(TextView)rl.getChildAt(1);
            textview.setText(valuse[i]);
        }
    }

    @Override
    public void onClick(View v) {
        if(bean==null)
        {
            ToastUtil.showMessage(this,"数据异常");
            return;
        }
        Intent intent=new Intent();
        switch (v.getId())
        {
            case R.id.betrecord:
                intent.setClass(this, OrderHistoryActivity.class);
                intent.putExtra(BundleTag.Username, name.getText().toString());
                intent.putExtra(BundleTag.Type,bean.getUserTypeValue());//用户类型值（1代理，0会员）
                startActivity(intent);
                break;
            case R.id.tracereocred:
                intent.setClass(this, TraceHistoryActivity.class);
                intent.putExtra(BundleTag.Username, name.getText().toString());
                intent.putExtra(BundleTag.Type,bean.getUserTypeValue());
                startActivity(intent);
                break;
            case R.id.setpoint:
                intent.setClass(this,SetpointActivity.class);
                intent.putExtra(BundleTag.Data, new Gson().toJson(bean) );
                startActivityForResult(intent, BundleTag.RequestCode);
                break;
            case R.id.setsalary:
                getDailyRule(intent);
                break;
            case R.id.sitemessage:
                intent.setClass(this,SiteMessageActivity.class);
                intent.putExtra(BundleTag.Username, name.getText().toString());
                intent.putExtra(BundleTag.Type, "1");
                startActivity(intent);
                break;
            case R.id.proxytransfer:
                NeedCompleteData(ProxyTransferActivity.class.getName());
                break;
            case R.id.modifyremark:
                ModifyRemarkDialog dialog=new ModifyRemarkDialog(this);
                dialog.setOnEditListener(new ModifyRemarkDialog.OnEditListener() {
                    @Override
                    public void OnEdit(String name) {
                        if(name!=null && name.length()>0)
                        ModifyRemark(name);
                    }
                });
                dialog.show();
                break;
        }

    }

    private void getDailyRule(final Intent intent)
    {
        RequestParams request=new RequestParams();
        request.put("flag","0");
        request.put("account",name.getText().toString());
        Httputils.PostWithBaseUrl(Httputils.PersonalSalaryDetail,request,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(TeamManageDetailActivity.this,jsonObject.optString("msg"));
                    return;
                }
                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                intent.setClass(TeamManageDetailActivity.this, RuleofPayoffActivity.class);
                if( resultList.length()==1)
                {
                    JSONObject result=resultList.optJSONObject(0);
                    intent.putExtra(BundleTag.DailySalary, result.optString("salaryMoney", ""));
                    intent.putExtra(BundleTag.StartSalary,result.optString("startMoney",""));
                    intent.putExtra(BundleTag.BetAmount,result.optString("personCount",""));
                    intent.putExtra(BundleTag.Limitedfund,result.optString("moneyCount",""));
                    intent.putExtra(BundleTag.id,result.optString("id",""));
                }
                else
                {

                }
                intent.putExtra(BundleTag.Username, name.getText().toString());
                startActivityForResult(intent, BundleTag.RequestCode);

            }
        });
    }

    private void ModifyRemark(final String mremark)
    {
        RequestParams request=new RequestParams();
        request.put("id",bean.getId());
        request.put("account",bean.getUserName());
        request.put("remark",mremark);
        Httputils.PostWithBaseUrl(Httputils.ModifyRemark,request,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                ToastUtil.showMessage(TeamManageDetailActivity.this, jsonObject.optString("msg"));
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    return;
                }
                remark.setText(mremark);
                setResult(BundleTag.ResultCode);
            }
        });
    }

    private void NeedCompleteData(String Classname)
    {
        try {
            JSONObject UserInfo=new JSONObject(RepluginMethod.getHosttSharedPreferences(this).getString(BundleTag.UserInfo, ""));
            UserInfoBean bean=UserInfoBean.Analysis_local(UserInfo);
            if(bean!=null)
            {
                TempInterface.getInstance().setMessageListener(new TempInterface.MessageListener() {
                    @Override
                    public void OnNotify() {
                        TempInterface.getInstance().setMessageListener(null);
                        RefreshData();
                    }
                });
                if(bean.isHasRealName() && bean.isHasWithdrawPwd())
                {
                    Intent intent=new Intent();
                    intent.setClass(this,ProxyTransferActivity.class);
                    intent.putExtra(BundleTag.Username, name.getText().toString());
                    startActivityForResult(intent, BundleTag.RequestCode);
                }
                else
                {
                    Intent intent=new Intent();
                    intent.setClass(this, CompletePerSonalData.class);
                    intent.putExtra(BundleTag.Classname,Classname);
                    intent.putExtra(BundleTag.Username, name.getText().toString());
                    startActivityForResult(intent, BundleTag.RequestCode);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private  void RefreshData()
    {
        RequestParams requestParams=new RequestParams();

            requestParams.put("account",bean.getUserName());
        requestParams.put("flag", "1");
        Httputils.PostWithBaseUrl(Httputils.TeamManageList,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("TeamManageList",jsonObject.toString());
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(TeamManageDetailActivity.this, jsonObject.optString("msg", ""));
                    return;
                }
                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                if(resultList.length()>0)
                bean=ManageRecordBean.Analysis(resultList.optJSONObject(0));
                FillInData();
            }
        });
    }
}
