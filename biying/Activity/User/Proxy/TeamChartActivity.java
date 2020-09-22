package com.lottery.biying.Activity.User.Proxy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.PerSonalIncomePreViewActivity;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnMultiClickListener;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.view.AppAlertDialog;
import com.lottery.biying.view.OrderHistoryDialog;

import org.json.JSONObject;

import java.util.ArrayList;

public class TeamChartActivity extends BaseActivity {

    LinearLayout circle_layout;
    int ScreenWidth;
    String timestr[],assitant_time[];
    int times[]={1,7,30};
    TextView assistant,teamcount;
    int index=0;
    ArrayList<TextView> circleValues=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_chart);
        InitView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitView()
    {
        String title=getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title))
        {
            setActivityTitle(title);
        }
        teamcount=FindView(R.id.teamcount);
        circle_layout=FindView(R.id.circle_layout);
        final TextView wordsexplain=FindView(R.id.wordsexplain);
        wordsexplain.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                AppAlertDialog dialog=new AppAlertDialog(TeamChartActivity.this);
                dialog.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
                    @Override
                    public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                        dialog.hide();
                    }
                });
                dialog.setTitleWithLine(wordsexplain.getText().toString());
                dialog.setConetnt(getString(R.string.wordsexplainstr));
                dialog.setConetntTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                dialog.setConetntgravity(Gravity.LEFT);
                dialog.setStyle();
                dialog.show();
            }
        });
        ScreenWidth= ScreenUtils.getScreenWH(this)[0];
        float scale[]={0.8f,0.9f,0.8f};
        for (int i = 0; i < circle_layout.getChildCount(); i++) {
            RelativeLayout relativeLayout=(RelativeLayout)circle_layout.getChildAt(i);
            ImageView imageView=(ImageView)relativeLayout.getChildAt(0);
            imageView.getLayoutParams().width=(int) (ScreenWidth/3*scale[i]);
            imageView.getLayoutParams().height=(int) (ScreenWidth/3*scale[i]);

            LinearLayout linearLayout=(LinearLayout)relativeLayout.getChildAt(1);
            TextView vaule=(TextView) linearLayout.getChildAt(1);
            circleValues.add(vaule);
        }

        assistant=FindView(R.id.assistant);
        assitant_time=getResources().getStringArray(R.array.assitant_time);
        assistant.setText(assitant_time[index]);
        timestr = TimeUtils.GetTimestr(times[index]);
        assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OrderHistoryDialog dialog = new OrderHistoryDialog(TeamChartActivity.this, getResources().getStringArray(R.array.assitant_time));
                dialog.setOnDiaClickitemListener(new OrderHistoryDialog.OnDiaClickitemListener() {
                    @Override
                    public void Onclickitem(int index) {
                        TeamChartActivity.this.index=index;
                        timestr = TimeUtils.GetTimestr(times[index]);
                        dialog.hide();
                        assistant.setText(assitant_time[index]);
                        RequestData();
                    }
                });
                dialog.showDropdown((View) FindView(R.id.toplayout));
            }
        });
        RequestData();
    }

    private void RequestData()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("startTime",timestr[0]);
        requestParams.put("finishTime",timestr[1]);
        requestParams.put("userName",((BaseApplication) RepluginMethod.getApplication(this)).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.TeamYingkui,requestParams,new MyJsonHttpResponseHandler(this,true){
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jssss",jsonObject.toString());
                    if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                    {
//                        {
//                            "datas": {
//                            "betBack": "500.0000", //返点总额
//                                    "betCount": "8", //投注人数
//                                    "betMoeny": "500.0000", //投注总额
//                                    "betPayout": "600.0000", //中奖总额
//                                    "depositAmount": "921.0000", //充值总额
//                                    "depositCount": "1", //首次充值人数
//                                    "flag": "1", //盈亏标识 0:未亏损1:亏损
//                                    "houDongAmount": "0.0000", //活动总额
//                                    "lowerCount": "5", //直属下级人数
//                                    "registerCount": "12", //注册人数
//                                    "teamCount": "12", //团队人数
//                                    "totalProfit": "-600.0000", //盈亏总金额
//                                    "withdrawAmount": "500.0000" //提现总额
//
//                            "teamAmount":"10494.2661" //团队余额
//                        },
//                            "errorCode": "000000",
//                                "msg": "操作成功"
//                        }



                        JSONObject datas=jsonObject.optJSONObject("datas");
                        String values[]=new String[3];
                        values[0]=datas.optString("teamAmount","");
                        values[1]=datas.optString("totalProfit","");
                        values[2]=datas.optString("betBack","");

                        String values1[]=new String[3];
                        String values2[]=new String[3];
                        String values3[]=new String[3];

                        values1[0]=datas.optString("betMoeny","");
                        values1[1]=datas.optString("betPayout","");
                        values1[2]=datas.optString("houDongAmount","");

                        values2[0]=datas.optString("depositAmount","");
                        values2[1]=datas.optString("withdrawAmount","");
                        values2[2]=datas.optString("teamAmount","");

                        values3[0]=datas.optString("depositCount","");
                        values3[1]=datas.optString("registerCount","");
                        values3[2]=datas.optString("betCount","");

                        for (int i = 0; i < circleValues.size(); i++) {
                            circleValues.get(i).setText(values[i]);
                        }

                        int index=0;

                        LinearLayout layout1=FindView(R.id.layout1);
                        for (int i = 0; i < layout1.getChildCount(); i++) {
                            View view=layout1.getChildAt(i);
                            if(view instanceof LinearLayout)
                            {
                                LinearLayout linearLayout=(LinearLayout)view;
                                TextView textView=(TextView) linearLayout.getChildAt(1);
                                textView.setText(values1[index++]);
                            }
                        }

                        index=0;
                        LinearLayout layout2=FindView(R.id.layout2);
                        for (int i = 0; i < layout2.getChildCount(); i++) {
                            View view=layout2.getChildAt(i);
                            if(view instanceof LinearLayout)
                            {
                                LinearLayout linearLayout=(LinearLayout)view;
                                TextView textView=(TextView) linearLayout.getChildAt(1);
                                textView.setText(values2[index++]);
                            }
                        }

                        index=0;
                        LinearLayout layout3=FindView(R.id.layout3);
                        for (int i = 0; i < layout3.getChildCount(); i++) {
                            View view=layout3.getChildAt(i);
                            if(view instanceof LinearLayout)
                            {
                                LinearLayout linearLayout=(LinearLayout)view;
                                TextView textView=(TextView) linearLayout.getChildAt(1);
                                textView.setText(values3[index++]);
                            }
                        }

                        String temple="下级人数%s人";
                        teamcount.setText(String.format(temple,datas.optString("lowerCount","")));
                    }

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);

            }
        });
    }
}
