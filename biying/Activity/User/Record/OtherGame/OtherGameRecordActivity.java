package com.lottery.biying.Activity.User.Record.OtherGame;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.OrderGameRecordAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PlatformBean;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12. 此页面已废弃
 */
public class OtherGameRecordActivity extends BaseActivity {

//    ListView listview;
//    ArrayList<PlatformBean> platformBeans=new ArrayList<>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_other_game_record);
//        Initview();
//    }
//
//    private void Initview()
//    {
//        listview=FindView(R.id.listview);
//        TextView backtitle=FindView(R.id.backtitle);
//        backtitle.setText(getString(R.string.othergamerecord));
//        backtitle.setVisibility(View.VISIBLE);
//        backtitle.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
//
//        GetData();
//    }
//
//    private void GetData()
//    {
//        RequestParams requestParams=new RequestParams();
//        requestParams.put("userName", RepluginMethod.getApplication(this).getBaseapplicationUsername());
//        Httputils.PostWithBaseUrl(Httputils.Platform, requestParams, new MyJsonHttpResponseHandler(this, true) {
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//            }
//
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
//                    return;
//                LogTools.e("oooooo", jsonObject.toString());
//                JSONArray jsonArray = jsonObject.optJSONArray("datas");
//                if (jsonArray != null && jsonArray.length() > 0) {
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsobject = jsonArray.optJSONObject(i);
//                        platformBeans.add(PlatformBean.Analysis(jsobject));
//                    }
//                    OrderGameRecordAdapter orderGameRecordAdapter=new OrderGameRecordAdapter(platformBeans,OtherGameRecordActivity.this);
//                    listview.setAdapter(orderGameRecordAdapter);
//                }
//            }
//        });
//    }
}
