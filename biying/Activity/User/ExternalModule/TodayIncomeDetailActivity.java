package com.lottery.biying.Activity.User.ExternalModule;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnMultiClickListener;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.view.TodayIncomeDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodayIncomeDetailActivity extends BaseActivity {

    TextView income_value;
    LinearLayout layout1,layout2;
    ArrayList<TextView> arrayList1=new ArrayList<>();
    ArrayList<TextView> arrayList2=new ArrayList<>();
    ArrayList<ArrayList<String>> dataslist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayincome_detail);
        String title=getIntent().getStringExtra(BundleTag.title);
        TextView titleview=FindView(R.id.title);

        TextView m_titleview=FindView(R.id.m_titleview);
        if(!TextUtils.isEmpty(title))
        {
            m_titleview.append(title);
            titleview.setText(title);
        }
        m_titleview.append("今日盈亏(元)");
        income_value=FindView(R.id.income_value);
        layout1=FindView(R.id.layout1);

        layout2=FindView(R.id.layout2);
        for (int i = 0; i <layout1.getChildCount() ; i++) {
            if(layout1.getChildAt(i) instanceof LinearLayout)
            {
                LinearLayout temp=(LinearLayout)layout1.getChildAt(i);
                arrayList1.add((TextView)temp.getChildAt(1));
            }
        }
        for (int i = 0; i <layout2.getChildCount() ; i++) {
            if(layout2.getChildAt(i) instanceof LinearLayout)
            {
                LinearLayout temp=(LinearLayout)layout2.getChildAt(i);
                arrayList2.add((TextView)temp.getChildAt(1));
            }
        }
        RequestData();
    }

    private void RequestData()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("userName", ((BaseApplication)RepluginMethod.getApplication(this)).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.TodayYingkui,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("RequestData",jsonObject.toString());
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    JSONObject datas=jsonObject.optJSONObject("datas");
                    income_value.setText(datas.optString("totalProfitAll",""));

                    String str1[]=new String[arrayList1.size()];
                    String str2[]=new String[arrayList2.size()];
                    str1[0]=datas.optString("betMoenyAll","");
                    str1[1]=datas.optString("betPayoutAll","");
                    str1[2]=datas.optString("betBackAll","");

                    str2[0]=datas.optString("depositAmountAll","");
                    str2[1]=datas.optString("withdrawAmountAll","");
                    str2[2]=datas.optString("houDongAmountAll","");
                    for (int i = 0; i <arrayList1.size() ; i++) {
                        arrayList1.get(i).setText(str1[i]);
                    }
                    for (int i = 0; i <arrayList2.size() ; i++) {
                        arrayList2.get(i).setText(str2[i]);
                    }
                    JSONArray resultList=datas.optJSONArray("resultList");
                    for (int i = 0; i < resultList.length(); i++) {
                        JSONObject temp=resultList.optJSONObject(i);
                        ArrayList<String> tempstrs=new ArrayList<>();
                        tempstrs.add(temp.optString("flatTypeName",""));
                        tempstrs.add(temp.optString("betAmount",""));
                        tempstrs.add(temp.optString("betPayout",""));
                        tempstrs.add(temp.optString("totalProfit",""));
                        tempstrs.add(temp.optString("betBack",""));
                        dataslist.add(tempstrs);
                    }
                    layout1.setOnClickListener(new OnMultiClickListener() {
                        @Override
                        public void onMultiClick(View v) {
                            TodayIncomeDialog todayIncomeDialog=new TodayIncomeDialog(TodayIncomeDetailActivity.this);
                            todayIncomeDialog.fillUpData(dataslist);
                            todayIncomeDialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }
}
