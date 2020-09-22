package com.lottery.biying.Activity.User.LotteryAccountTransfer.WithdrawAndDepositRecord;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.PaymentBean;
import com.lottery.biying.bean.PaymentBean2;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.ScreenUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/12.
 */
public class WithdrawOrDepositDeatilActivity extends BaseActivity {

    TextView changeinfo, changemoney;
    LinearLayout infolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_or_withdraw_detail);
        Initview();
    }

    private void Initview() {
        TextView backtitle = FindView(R.id.backtitle);
        backtitle.setText(getIntent().getStringExtra(BundleTag.Title));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        changeinfo = FindView(R.id.changeinfo);
        changemoney = FindView(R.id.changemoney);
        infolist = FindView(R.id.infolist);
        changemoney.setTextColor(getResources().getColor(R.color.loess4));
        changeinfo.setTextColor(getResources().getColor(R.color.black2));


        int type = getIntent().getIntExtra(BundleTag.Type, -1);
        if (type == -1) return;
        if (type == 0) {
            /*充值*/
            try {
                PaymentBean bean = PaymentBean.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
                changeinfo.setText("充值金额");
                changemoney.setText(bean.getHkMoney());
                String titles[] = {"充值类型","订单时间","状态", "备注", "订单号"};

                String values[] = {bean.getHkType(),bean.getCreateTime(), bean.getStatusDes(), bean.getRemark(), bean.getHkOrder()};
                for (int i = 0; i < titles.length; i++) {
                    LinearLayout linearLayout = new LinearLayout(this);
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ll.topMargin = ScreenUtils.getDIP2PX(this, 10);
                    linearLayout.setLayoutParams(ll);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    TextView title = new TextView(this);
                    title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    title.setTextColor(getResources().getColor(R.color.gray25));
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    title.setText(titles[i]);
                    linearLayout.addView(title);

                    TextView value = new TextView(this);
                    LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    rl.leftMargin=ScreenUtils.getDIP2PX(this,10);
                    value.setGravity(Gravity.RIGHT);
                    value.setLayoutParams(rl);
                    value.setTextColor(getResources().getColor(R.color.black2));
                    value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    value.setText(values[i]);
                    linearLayout.addView(value);

                    infolist.addView(linearLayout);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            /*取现*/
            try {
                PaymentBean2 bean = PaymentBean2.Analysis_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
                changeinfo.setText("提现金额");
                changemoney.setText(bean.getUserWithdrawMoney() + "");
                String titles[] = {"提现类型","审核时间", "状态", "备注", "订单号"};

                String values[] = {bean.getWithdrawTypeDes(),bean.getCheckTime(), bean.getCheckStatusDes()+"("+bean.getStatusDes()+")", bean.getRemark(), bean.getUserOrder()};
                for (int i = 0; i < titles.length; i++) {
                    RelativeLayout relativelayout = new RelativeLayout(this);
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ll.topMargin = ScreenUtils.getDIP2PX(this, 10);
                    relativelayout.setLayoutParams(ll);

                    TextView title = new TextView(this);
                    title.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    title.setTextColor(getResources().getColor(R.color.gray25));
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    title.setText(titles[i]);
                    relativelayout.addView(title);

                    TextView value = new TextView(this);
                    RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    value.setLayoutParams(rl);
                    value.setTextColor(getResources().getColor(R.color.black2));
                    value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    value.setText(values[i]);
                    relativelayout.addView(value);

                    infolist.addView(relativelayout);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
