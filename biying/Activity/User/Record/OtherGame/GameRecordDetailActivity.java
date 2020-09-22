package com.lottery.biying.Activity.User.Record.OtherGame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.ORecordListBean;
import com.lottery.biying.bean.QueryBean;
import com.lottery.biying.util.BundleTag;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/12.
 */
public class GameRecordDetailActivity extends BaseActivity {

    TextView winmoney,betmoney,flat,orderid,bettime,refundmoney,realbetmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_record_detail);

        realbetmoney=FindView(R.id.realbetmoney);
        refundmoney=FindView(R.id.refundmoney);
        winmoney=FindView(R.id.winmoney);
        betmoney=FindView(R.id.betmoney);
        flat=FindView(R.id.flat);
        orderid=FindView(R.id.orderid);
        bettime=FindView(R.id.bettime);
        QueryBean bean= null;
        try {
            bean = QueryBean.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            TextView backtitle=FindView(R.id.backtitle);
            backtitle.setText(bean.getBetGameContent() + "游戏记录");
            backtitle.setVisibility(View.VISIBLE);
            backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            winmoney.setText(bean.getBetUsrWin()+"");
            try {
                double money=Double.valueOf(bean.getBetUsrWin());
                if(money>-1)
                {
                    winmoney.setTextColor(getResources().getColor(R.color.green));
                }
                else
                {
                    winmoney.setTextColor(getResources().getColor(R.color.red2));
                }
            }
            catch (NumberFormatException ex)
            {
                ex.printStackTrace();
            }
            realbetmoney.setText(bean.getBetIncome());
            betmoney.setText(bean.getBetIn()+"");
            refundmoney.setText(bean.getBackWaterMoney());
            flat.setText(bean.getBetGameContent());
            orderid.setText("订单号:"+bean.getBetWagersId());
            bettime.setText(bean.getBetTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
