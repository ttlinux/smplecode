package com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.AccChangeBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.ScreenUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/2.
 */
public class LotteryAccTransferDetailActivity extends BaseActivity{

    TextView backtitle;
    LinearLayout infolist;
    TextView changeinfo,changemoney;
    String accchange_titles[];
    int type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_acc_transfer_detail);
        InitView();
    }

    private void InitView()
    {
        type=getIntent().getIntExtra(BundleTag.Type,0);
        LogTools.e("TYPE",type+" ");
        if(type>0)
        {
            accchange_titles=getResources().getStringArray(R.array.accchange_titles2);
        }
        else
        {
            accchange_titles=getResources().getStringArray(R.array.accchange_titles);
        }

        backtitle=FindView(R.id.backtitle);
        infolist=FindView(R.id.infolist);
        changeinfo=FindView(R.id.changeinfo);
        changemoney=FindView(R.id.changemoney);
        backtitle.setText(getString(R.string.lotterytransferdetail));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        try {
            AccChangeBean bean=AccChangeBean.AnalusisData_local(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
            changeinfo.setText(bean.getChangeType());
            changemoney.setText(bean.getChangeMoney());
            try {
                double money=Double.valueOf(bean.getChangeMoney());
                if(money>-1)
                {
                    changemoney.setTextColor(getResources().getColor(R.color.green));
                }
                else
                {
                    changemoney.setTextColor(getResources().getColor(R.color.red2));
                }
            }
            catch (NumberFormatException ex)
            {

            }

            String values[]=null;
            if(type==0)
            {
                String temp[]= {bean.getUserName(),bean.getLotteryName(),bean.getGameName(),bean.getQihao(),bean.getChangeTime(),
                        bean.getAppendOrder(),bean.getFanganOrder(),
                        bean.getUserBalance()
                        ,bean.getRemark()};
                values=temp;
            }
            else
            {
                String temp[]= {bean.getUserName(),bean.getLotteryName(),bean.getGameName(),bean.getChangeTime(),
                        bean.getAppendOrder(),
                        bean.getUserBalance()
                        ,bean.getRemark()};
                values=temp;
            }

            /*list*/
            for (int i = 0; i <accchange_titles.length ; i++) {
                RelativeLayout relativelayout=new RelativeLayout(this);
                LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.topMargin= ScreenUtils.getDIP2PX(this,10);
                relativelayout.setLayoutParams(ll);

                TextView title=new TextView(this);
                title.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                title.setTextColor(getResources().getColor(R.color.gray20));
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                title.setText(accchange_titles[i]);
                relativelayout.addView(title);

                TextView value=new TextView(this);
                RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                value.setLayoutParams(rl);
                value.setTextColor(getResources().getColor(R.color.black2));
                if(type==0)
                {
                    if(i==7 )
                        value.setTextColor(getResources().getColor(R.color.loess4));
                }
                else
                {
                    if(i==5 )
                        value.setTextColor(getResources().getColor(R.color.loess4));
                }

                value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                value.setText(values[i]);
                relativelayout.addView(value);

                infolist.addView(relativelayout);
            }

            /*list*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
