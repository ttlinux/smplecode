package com.lottery.biying.Activity.User.WithDraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.WithDrawBankListAdapter;
import com.lottery.biying.Adapter.withdrawListAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.view.MyListView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/9. 提现到银行卡之前的选择银行卡列表
 */
public class WithDrawBankListActivty extends BaseActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_banklist);

        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getString(R.string.bankcardinfo));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        listview=FindView(R.id.listview);
        GetData();
    }

    private void GetData() {
        /*{
		"bankCnName": "京东",
		"bankCode": "jdpay",
		"bigPicUrl": "http:\/\/res.6820168.com\/share\/bankicon\/1521830572751.jpg",
		"isBank": 1,
		"smallPicUrl": "http:\/\/res.6820168.com\/share\/bankicon\/1521830572751.jpg"
	}*/
        Httputils.PostWithBaseUrl(Httputils.selectUserBankCodeInfo, new RequestParams(), new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }

                JSONArray jsonArray=jsonObject.optJSONArray("datas");
                WithDrawBankListAdapter adapter=new WithDrawBankListAdapter(jsonArray,WithDrawBankListActivty.this);
                listview.setAdapter(adapter);

            }
        });
    }
}
