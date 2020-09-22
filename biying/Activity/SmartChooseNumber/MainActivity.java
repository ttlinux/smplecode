package com.lottery.biying.Activity.SmartChooseNumber;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.OrderConfirmAdapter;
import com.lottery.biying.Adapter.RunChartFragmentAdapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.BaseParent.BaseFragmentActivity;
import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.R;
import com.lottery.biying.bean.CalculateBet;
import com.lottery.biying.bean.LotteryTimebean;
import com.lottery.biying.bean.SmartTraceBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.CalculateBetMoney;
import com.lottery.biying.util.CommitOrder;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.LotteryTypeName;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnClickSmartMainListener;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeMethod;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AppAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/8.
 */
public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener, OnClickListener {

    public static int Maxpay=500000;
    RelativeLayout selectview,alertview;
    LinearLayout lottrytimelayout;
    public TextView timeview1, timeview2;
    int pagerindex = 0;
    ImageView line;
    RadioButton radiobutton1;
    RadioGroup radiogroup;
    ViewPager viewpager;
    FragmentManager fragmentManager;
    ArrayList<BaseFragment> bases = new ArrayList<BaseFragment>();
    int min, max;
    LinearLayout bottowview, bottowview2;
    TextView show_bet2, confirm2, cancel2,title,mynumber;
    int mode = 0;//
    public int condition1=30,condition2=-1, start_multiply=1;
    public int follow_times,Allmoney;//期数 总共多少钱
    public double award;
    String CurrentQS="";
    LinearLayout winstop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_smart_choose_main);
        InitView();
    }

    private void InitView() {
        lottrytimelayout = FindView(R.id.lottrytimelayout);
        timeview1 = (TextView) lottrytimelayout.getChildAt(0);
        timeview2 = (TextView) lottrytimelayout.getChildAt(1);
        fragmentManager = getSupportFragmentManager();
        bases.add(new Fragment1());
        bases.add(new Fragment2());
        bases.add(new Fragment3());
        OrderConfirmAdapter.Itemvalue iv=null;
        try {
            iv= OrderConfirmAdapter.Itemvalue.Itemvalue_analysis(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bases.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString(BundleTag.id, getIntent().getStringExtra(BundleTag.id));
            bundle.putString(BundleTag.GameCode, getIntent().getStringExtra(BundleTag.GameCode));
            bundle.putInt(BundleTag.BetTimes, getIntent().getIntExtra(BundleTag.BetTimes, 1));
            bundle.putInt(BundleTag.Times, getIntent().getIntExtra(BundleTag.Times, 1));
            bundle.putString(BundleTag.LotteryName, getIntent().getStringExtra(BundleTag.LotteryName));
            bundle.putString(BundleTag.TitleCode, getIntent().getStringExtra(BundleTag.TitleCode));
            bundle.putDouble(BundleTag.Unit, getIntent().getDoubleExtra(BundleTag.Unit, 1));
            bundle.putDouble(BundleTag.AWARD, getIntent().getDoubleExtra(BundleTag.AWARD, 1d));
            bundle.putInt(BundleTag.UnitType, getIntent().getIntExtra(BundleTag.UnitType, 1));
            bundle.putInt(BundleTag.BounsType, getIntent().getIntExtra(BundleTag.BounsType, 0));
            bundle.putString(BundleTag.Content, iv.getItemvalueV1());
            bundle.putString(BundleTag.AppendQsMax, getIntent().getStringExtra(BundleTag.AppendQsMax));
            bundle.putString(BundleTag.MultipleMax, getIntent().getStringExtra(BundleTag.MultipleMax));
            bases.get(i).setArguments(bundle);
        }
        winstop=FindView(R.id.winstop);
        winstop.setTag(true);
        winstop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout item=(LinearLayout)v;
                boolean state=(boolean)item.getTag();
                state=!state;
                v.setTag(state);
                ImageView imageView=(ImageView)item.getChildAt(0);
                imageView.setImageDrawable(state?getResources().getDrawable(R.drawable.gou_selected):getResources().getDrawable(R.drawable.gou));
            }
        });
        selectview = FindView(R.id.selectview);
        alertview=FindView(R.id.alertview);
        mynumber=FindView(R.id.mynumber);
        bottowview = FindView(R.id.bottowview);
        bottowview2 = (LinearLayout) bottowview.getChildAt(0);
        cancel2 = (TextView) bottowview2.getChildAt(0);
        show_bet2 = (TextView) bottowview2.getChildAt(1);
        confirm2 = (TextView) bottowview2.getChildAt(2);
        title=FindView(R.id.title);

        cancel2.setOnClickListener(this);
        confirm2.setOnClickListener(this);
        mynumber.setOnClickListener(this);
        radiobutton1 = FindView(R.id.radiobutton1);
        radiobutton1.post(new Runnable() {
            @Override
            public void run() {
                SetIndex(0);
            }
        });
        radiogroup = FindView(R.id.radiogroup);
        title.setText("智能追号");
        radiogroup.setOnCheckedChangeListener(this);
        viewpager = FindView(R.id.viewpager);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(new RunChartFragmentAdapter(fragmentManager, bases));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SetIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setMoney(String zhu,String money,String bei,String qi)
    {
        String template="共%s元\n追%s期";
        String str=String.format(template,money,zhu,bei,qi);
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.loess4)), 0, str.indexOf("元") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        show_bet2.setText(style);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radiobutton1) {
            viewpager.setCurrentItem(0);
        }
        if (checkedId == R.id.radiobutton2) {
            viewpager.setCurrentItem(1);
        }
        if (checkedId == R.id.radiobutton3) {
            viewpager.setCurrentItem(2);
        }
    }

    public void SetIndex(int index) {
        if (selectview.getChildCount() == 1) {
            line = new ImageView(MainActivity.this);
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(radiobutton1.getWidth() - 30, ScreenUtils.getDIP2PX(this, 2));

            int[] location = new int[2];
            radiobutton1.getLocationInWindow(location);
            radiobutton1.setTextColor(getResources().getColor(R.color.loess4));
            rl.leftMargin = location[0] * (index + 1) + 15;
            rl.addRule(RelativeLayout.ALIGN_BOTTOM, radiogroup.getId());
            line.setLayoutParams(rl);
            line.setBackgroundColor(getResources().getColor(R.color.loess4));
            selectview.addView(line);
            pagerindex = 0;
        } else {
            RadioButton recordbutton = (RadioButton) radiogroup.getChildAt(pagerindex * 2);
            recordbutton.setTextColor(getResources().getColor(R.color.gray19));
            RadioButton button = (RadioButton) radiogroup.getChildAt(index * 2);
            int[] location = new int[2];
            button.getLocationInWindow(location);
            button.setTextColor(getResources().getColor(R.color.loess4));
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) line.getLayoutParams();
            rl.leftMargin = location[0] + 15;
            rl.addRule(RelativeLayout.ALIGN_BOTTOM, radiogroup.getId());
            line.setLayoutParams(rl);
            pagerindex = index;
        }

    }



    @Override
    public void onClick(View v) {
        OnClickSmartMainListener listener=null;
        switch (pagerindex)
        {
            case 0:
                listener=(Fragment1)bases.get(0);
                break;
            case 1:
                listener=(Fragment2)bases.get(1);
                break;
            case 2:
                listener=(Fragment3)bases.get(2);
                break;
        }
        switch (v.getId()) {
            case R.id.cancel2:
                listener.OnClick(pagerindex,0);
                break;
            case R.id.confirm2:
//                listener.OnClick(pagerindex,1);
                CommitOrder();
                break;
            case R.id.mynumber:
                try {
                    AlertDialog2();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void AlertDialog2() throws JSONException {
        AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.setTitle(getResources().getString(R.string.mynumber));
        OrderConfirmAdapter.Itemvalue iv= OrderConfirmAdapter.Itemvalue.Itemvalue_analysis(new JSONObject(getIntent().getStringExtra(BundleTag.Data)));
        dialog.setConetnt(iv.getItemvalueV1()+"\n"+iv.getItemvalueO2()+" "+iv.getItemvalueO1());
        dialog.setStyle();
        dialog.setConfirmstr("确定");
        dialog.show();
        dialog.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
            @Override
            public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                dialog.hide();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetTime();
    }


    private void GetTime() {
        timeview1.setText(getString(R.string.requestingqihao));
        timeview2.setText("");
        TimeMethod.End();
        RequestParams requestParams = new RequestParams();
        requestParams.put("lotteryCode", getIntent().getStringExtra(BundleTag.id));
        Httputils.PostWithBaseUrl(Httputils.DTime, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("timeeee", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONObject jsonobj = datas.optJSONObject("paiqiResult");
                JSONObject yl = datas.optJSONObject("yl");
                if (jsonobj.optString("isClose", "").equalsIgnoreCase("1")) {
                    timeview1.setText(getString(R.string.RoundEnd));
                    timeview2.setText("");
                    TimeMethod.End();
                    return;
                }
                LotteryTimebean ltbean = new LotteryTimebean();
                ltbean.setTimes(jsonobj.optInt("closeTime", 0));
                ltbean.setQs(jsonobj.optString("qs", ""));
                ltbean.setQsFormat(jsonobj.optString("qsFormat", ""));
                CurrentQS = ltbean.getQs();

                /*倒计时*/
                TimeMethod.setOnTimeChangeListener(new TimeMethod.OnTimeChangeListener() {
                    @Override
                    public void OnChange(int CloseLimitime, int LotteryStatus, String qsstr) {

                        timeview1.setText("距离" + qsstr + "期截止: ");
                        timeview2.setText(TimeUtils.ConverTime(CloseLimitime));
                        if (CloseLimitime == 0 && isInFront()) {
                            AlertDialog2("第" + qsstr + "期已截止");
                        }
                        if (CloseLimitime == -1) {
                            timeview1.setText(getString(R.string.requestingqihao));
                            timeview2.setText("");
                            GetTime();
                        }
                    }
                }, MainActivity.this, ltbean.getQsFormat());
                TimeMethod.Start(Integer.valueOf(ltbean.getTimes()));
                 /*倒计时*/
            }
        });
    }

    private void CommitOrder()
    {

        if(CurrentQS.length()<1)
        {
            ToastUtil.showMessage(MainActivity.this, "正在获取当前期数，请稍后再试");
            return;
        }
        ArrayList<SmartTraceBean> smartTraceBeans=(ArrayList<SmartTraceBean>)bases.get(pagerindex).getShareObj();

        if(smartTraceBeans==null || smartTraceBeans.size()==0)
        {
            ToastUtil.showMessage(MainActivity.this, "请生成追号计划");
            return;
        }
        else
        {
            boolean hasselected=false;
            for (int i = 0; i <smartTraceBeans.size() ; i++) {
                if(smartTraceBeans.get(i).isSelect())
                {
                    hasselected=true;
                    break;
                }
            }
            if(!hasselected)
            {
                ToastUtil.showMessage(MainActivity.this, "请至少选择一注");
                return;
            }
        }
        confirm2.setEnabled(false);
        JSONArray orders= CommitOrder.orderMaker_Trace(smartTraceBeans);
        JSONArray traceOrders=CommitOrder.traceOrderMaker_Trace(smartTraceBeans, CurrentQS);
        double amount=0;
        int counts=0;
        for (int i = 0; i < smartTraceBeans.size(); i++) {
            SmartTraceBean smartTraceBean= smartTraceBeans.get(i);
            if(smartTraceBean.isSelect())
            {
                amount=amount+smartTraceBean.getTimes()*smartTraceBean.getUnit()*smartTraceBean.getBettimes();
                counts=smartTraceBean.getBettimes()+counts;
            }
        }


        CommitOrder.commit(new CommitOrder.OnFinishListener() {
                               @Override
                               public void OnDone(boolean succesful) {
                                   if (succesful) {
                                       OnClickSmartMainListener listener = null;
                                       switch (pagerindex) {
                                           case 0:
                                               listener = (Fragment1) bases.get(0);
                                               break;
                                           case 1:
                                               listener = (Fragment2) bases.get(1);
                                               break;
                                           case 2:
                                               listener = (Fragment3) bases.get(2);
                                               break;
                                       }
                                       listener.OnClick(pagerindex, 0);
                                       setResult(BundleTag.TraceSuccessOrder);
                                       finish();
                                   }
                                   confirm2.setEnabled(true);
                               }
                           }, true, (boolean) winstop.getTag(), MainActivity.this,
                AnalysisType.CurrentLottery, String.format("%.2f", amount), counts + "", orders.toString(), traceOrders.toString());
    }

    private void AlertDialog2(String Content) {
        final AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.setTitle(getResources().getString(R.string.warmtips));
        dialog.setConetnt(Content);
        dialog.setStyle();
        dialog.setConfirmstr("确定");
        dialog.show();
        dialog.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
            @Override
            public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                dialog.hide();
            }
        });
        dialog.setAutoClose();
    }
}
