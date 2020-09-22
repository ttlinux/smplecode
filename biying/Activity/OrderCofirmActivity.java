package com.lottery.biying.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.SmartChooseNumber.MainActivity;
import com.lottery.biying.Adapter.OrderConfirmAdapter;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.LotteryMethod.K3.K3;
import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryTimebean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.CommitOrder;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeMethod;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AppAlertDialog;
import com.lottery.biying.view.LotteryButton;
import com.lottery.biying.view.Keyboard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/11.
 * //        ImageView imageView=new ImageView(this);
 * //        ListView.LayoutParams ll=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
 * //        imageView.setLayoutParams(ll);
 * //        imageView.setImageDrawable(getResources().getDrawable(R.drawable.bottom_pic));
 * //        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
 * //        listview.addFooterView(imageView);
 */
public class OrderCofirmActivity extends BaseActivity implements View.OnClickListener {

    ListView listview;
    OrderConfirmAdapter oca;
    LinearLayout lottrytimelayout;
    public TextView timeview1, timeview2;
    private static ArrayList<OrderConfirmAdapter.Itemvalue> arrayList = new ArrayList<>();
    private static String MainLottery_id;
    TextView bottomview1, bottomview2, bottomview3, buttons1, buttons2, buttons3, buttons4;
//    EditText follow_times, multiply;
    LinearLayout bottowview, buttons;
    String itemtitle,UnpGanmecode,Real_Gamecode,QS,Titlecode;//UnpGanmecode 未处理的gamecode
    String Weishu;
    int  Randomtime;
    double unit;
    int BounsType,LotteryCategory;
    String CurrentQS="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==BundleTag.TraceSuccessOrder)
        {
            arrayList.clear();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_order_confirm);
        InitView();
    }

    private void InitView() {
        lottrytimelayout = FindView(R.id.lottrytimelayout);
        timeview1 = (TextView) lottrytimelayout.getChildAt(0);
        timeview2 = (TextView) lottrytimelayout.getChildAt(1);
        listview = FindView(R.id.listview);
        buttons = FindView(R.id.buttons);
        buttons1 = (TextView) buttons.getChildAt(0);
        buttons2 = (TextView) buttons.getChildAt(1);
        buttons3 = (TextView) buttons.getChildAt(2);
        buttons4 = (TextView) buttons.getChildAt(3);
        buttons1.setOnClickListener(this);
        buttons2.setOnClickListener(this);
        buttons3.setOnClickListener(this);
        buttons4.setOnClickListener(this);
        TextView title = FindView(R.id.title);
        title.setText(getIntent().getStringExtra(BundleTag.LotteryName)+"投注");
        bottowview = FindView(R.id.bottowview);
        bottomview1 = (TextView) bottowview.getChildAt(0);
        bottomview2 = (TextView) bottowview.getChildAt(1);
        bottomview3 = (TextView) bottowview.getChildAt(2);
        bottomview1.setOnClickListener(this);
        bottomview3.setOnClickListener(this);

        if(!getIntent().getStringExtra(BundleTag.id).equalsIgnoreCase(MainLottery_id))
        {
            //不同彩种
            arrayList.clear();
        }
        MainLottery_id = getIntent().getStringExtra(BundleTag.id);
        itemtitle = getIntent().getStringExtra(BundleTag.title);
        Titlecode=getIntent().getStringExtra(BundleTag.TitleCode);
        Randomtime = getIntent().getIntExtra(BundleTag.Randomtime, 0);
        BounsType=getIntent().getIntExtra(BundleTag.BounsType, 0);
        LotteryCategory=getIntent().getIntExtra(BundleTag.LotteryCategory, 0);
        QS=getIntent().getStringExtra(BundleTag.QS);
        UnpGanmecode=getIntent().getStringExtra(BundleTag.GameCode);
        unit=getIntent().getDoubleExtra(BundleTag.Unit,1);



        if(UnpGanmecode.contains("|"))
        {
            String datas[]=UnpGanmecode.split("\\|");
            Weishu=datas[0];
            Real_Gamecode=datas[1];
        }
        else
        {
            Real_Gamecode=UnpGanmecode;
        }
        if(Randomtime<0)
        {
            //点购物车进来看看而已
            SetAdapter();
            setShowValue();
            return;
        }

        if (Randomtime == 0)
            Handlerdata();
        else if(Randomtime>0)
            Random(Randomtime);
        setBackButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog();
            }
        });

        AnalysisType.ClearSelected(LotteryCategory);//清掉选中号码数据
        setShowValue();
        int trace=getIntent().getIntExtra(BundleTag.HasTrace,0);
        if(trace<1)
        IfHasNoTrace();
    }

    public void Handlerdata() {
        JSONObject jsonObject=AnalysisType.FormatNumbers(AnalysisType.getSelectNumbers(), UnpGanmecode);
        LogTools.e("FormatNumber",jsonObject.toString());
        int bettimes=getIntent().getIntExtra(BundleTag.BetTimes, 1);
        int ordertimes=getIntent().getIntExtra(BundleTag.Times, 1);
        String BounsName=getIntent().getStringExtra(BundleTag.BounsName);
        BounsName=BounsName==null?"":BounsName;
        String money=String.format("%.2f",bettimes * ordertimes*unit);
        OrderConfirmAdapter.Itemvalue iv = new OrderConfirmAdapter.Itemvalue();
        iv.setItemvalueV1(jsonObject.optString(BundleTag.FormatNumber, ""));
        iv.setItemvalueO1(itemtitle);
        iv.setItemvalueO2(bettimes + "注 " + ordertimes + "倍 " + money + "元 " + BounsName);
        iv.setTimes(ordertimes);
        iv.setBettimes(bettimes);
        iv.setUnit(unit);
        iv.setLotterytype(MainLottery_id);
        iv.setPlaytype_str(Real_Gamecode);
        iv.setInfo(jsonObject);
        iv.setTitlecode(Titlecode);
        iv.setBounsType(BounsType);

        arrayList.add(iv);
        SetAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smartfollow:
                if(arrayList.size()==0)
                {
                    ToastUtil.showMessage(this,getString(R.string.atleatselectone));
                    return;
                }
                if (arrayList.size() > 1) {
                    AlertDialog2();
                } else {
                    //太多注数调用这个    AlertDialog4();
                    int times=getIntent().getIntExtra(BundleTag.Times, 1);
                    int bettimes=getIntent().getIntExtra(BundleTag.BetTimes, 1);
//                    double award=getIntent().getDoubleExtra(BundleTag.AWARD,1d);
//                    if(times*bettimes*unit>=award)
//                    {
//                        AlertDialog4();
//                        return;
//                    }
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(BundleTag.id, MainLottery_id);
                    intent.putExtra(BundleTag.QS,getIntent().getStringExtra(BundleTag.QS));
                    intent.putExtra(BundleTag.GameCode, Real_Gamecode);
                    intent.putExtra(BundleTag.LotteryName,getIntent().getStringExtra(BundleTag.LotteryName));
                    intent.putExtra(BundleTag.Times,times);
                    intent.putExtra(BundleTag.BetTimes,bettimes);
                    intent.putExtra(BundleTag.TitleCode,getIntent().getStringExtra(BundleTag.TitleCode));
                    intent.putExtra(BundleTag.Unit,unit);
                    intent.putExtra(BundleTag.BounsType,BounsType);
                    intent.putExtra(BundleTag.AppendQsMax,getIntent().getStringExtra(BundleTag.AppendQsMax));
                    intent.putExtra(BundleTag.MultipleMax,getIntent().getStringExtra(BundleTag.MultipleMax));
                    intent.putExtra(BundleTag.UnitType,getIntent().getIntExtra(BundleTag.UnitType,1));
                    intent.putExtra(BundleTag.AWARD,getIntent().getDoubleExtra(BundleTag.AWARD,1d));
                    intent.putExtra(BundleTag.Data, new Gson().toJson(arrayList.get(0)));

                    startActivityForResult(intent, BundleTag.RequestCode);
                }
                break;
            case R.id.buttons1:
                finish();
                break;
            case R.id.buttons2:
                Random(1);
                setShowValue();
                break;
            case R.id.buttons3:
                Random(5);
                setShowValue();
                break;
            case R.id.buttons4:
                AlertDialog3();
                break;
            case R.id.multiply:
//                Keyboard kb = new Keyboard(this, 1);
//                String text = multiply.getText().toString();
//                String follow_text = follow_times.getText().toString();
//                int multiply_num;
//                if (text.length() > 0) {
//                    multiply_num = Integer.valueOf(text);
//                } else {
//                    multiply_num = 1;
//                }
//                kb.setTitle(arrayList.size() + "注%s倍" + (follow_text.length() > 0 ? follow_text : "1") + "期 " + "共%s元", multiply_num, multiply_num * 2);
//                kb.setOndismiss(new Keyboard.OnDismissListener() {
//                    @Override
//                    public void Ondismiss(int value,double unit) {
//                        multiply.setText(value + "");
//                        setShowValue();
//                    }
//                });
//                kb.show();
                break;
            case R.id.bottomview3:
                CommitOrder();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog();
    }

    private void AlertDialog() {
        if(oca!=null && oca.getIvs().size()>0)
        {
            AppAlertDialog dialog = new AppAlertDialog(this);
            dialog.setTitle(getResources().getString(R.string.warmtips));
            dialog.setConetnt(getResources().getString(R.string.tips1));
            dialog.setCancelstr("否");
            dialog.setConfirmstr("是");
            dialog.show();
            dialog.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
                @Override
                public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                    dialog.hide();
                    if (!confirm) {
                        arrayList.clear();
                    }
                    finish();
                }
            });
        }
        else
        {
            arrayList.clear();
            finish();
        }

    }

    private void Random(int count) {
        if(Real_Gamecode==null)return;
        String BounsName=getIntent().getStringExtra(BundleTag.BounsName);
        BounsName=BounsName==null?"":BounsName;
        int bettimes=0;
        for (int i = 0; i < count; i++) {
            ArrayList<ArrayList<LotteryButton>> buttons=AnalysisType.getCurrentbuttons();
            JSONObject jsonObject=null;
            if(Real_Gamecode.contains("k3"))
            {
                //快三 独立插件
                K3 k3=K3.getInstance();
                k3.getInstance().RandomNumbers(k3.getCorrent_Numbers(),Real_Gamecode);
                bettimes=k3.CaculateOrderAmount(k3.getCorrent_Numbers(), Real_Gamecode);
                jsonObject=k3.Formatnumber();
            }
            else
            {
                AnalysisType.RandomNumbers(buttons,Real_Gamecode);
                bettimes=AnalysisType.Caculatebets(buttons, UnpGanmecode);
                jsonObject=AnalysisType.FormatNumbers(AnalysisType.getSelectNumbers(), UnpGanmecode);
            }
            AnalysisType.ClearSelected(LotteryCategory);
            int ordertimes=getIntent().getIntExtra(BundleTag.Times, 1);
            String money=String.format("%.2f",bettimes * ordertimes*unit);
            OrderConfirmAdapter.Itemvalue iv = new OrderConfirmAdapter.Itemvalue();
            iv.setItemvalueV1(jsonObject.optString(BundleTag.FormatNumber, ""));
            iv.setItemvalueO1(itemtitle);
            iv.setItemvalueO2(bettimes + "注 " + ordertimes + "倍 " + money + "元 "+BounsName);
            iv.setTimes(ordertimes);
            iv.setBettimes(bettimes);
            iv.setUnit(unit);
            iv.setLotterytype(MainLottery_id);
            iv.setPlaytype_str(Real_Gamecode);
            iv.setInfo(jsonObject);
            iv.setTitlecode(Titlecode);
            iv.setBounsType(BounsType);

                arrayList.add(iv);
        }
        SetAdapter();
    }

    private void AlertDialog2() {
        AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.setTitle(getResources().getString(R.string.tips3));
        dialog.setConetnt(getResources().getString(R.string.tips4));
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

    private void AlertDialog3() {
        AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.setTitle(getResources().getString(R.string.warmtips));
        dialog.setConetnt(getResources().getString(R.string.tips5));
        dialog.setCancelstr("否");
        dialog.setConfirmstr("是");
        dialog.show();
        dialog.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
            @Override
            public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                dialog.hide();
                if (confirm) {
                    arrayList.clear();
                    oca.Notify(arrayList);
                    setShowValue();
                }
            }
        });
    }

    private void AlertDialog4() {
        AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.setTitle(getResources().getString(R.string.notearnmoney));
        dialog.setConetnt(getResources().getString(R.string.tips10));
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

    private void AlertDialog2(String Content) {
        AppAlertDialog dialog = new AppAlertDialog(this);
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

    private void setShowValue() {
        int qi=1;
        int zhu = 0;
        double money=0;
        for (int i = 0; i <oca.getIvs().size() ; i++) {
            zhu=zhu+oca.getIvs().get(i).getBettimes();
            money=money+oca.getIvs().get(i).getBettimes()*oca.getIvs().get(i).getTimes()*oca.getIvs().get(i).getUnit();
        }


        String template="%s元\n%s注";
        String str=String.format(template,String.format("%.2f",money),zhu);
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.loess4)), 0, str.indexOf("元") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bottomview2.setText(style);
    }

    private void IfHasNoTrace()//如果没有智能追号
    {
            bottomview1.setVisibility(View.GONE);
            int width= ScreenUtils.getScreenWH(this)[0];

            ViewGroup.LayoutParams layoutParams= bottomview2.getLayoutParams();
            layoutParams.width=width/3*2;
            bottomview2.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams2= bottomview3.getLayoutParams();
            layoutParams2.width=width/3;
            bottomview3.setLayoutParams(layoutParams2);

    }

    private void CommitOrder()
    {
        if(CurrentQS.length()<1)
        {
            ToastUtil.showMessage(OrderCofirmActivity.this,"正在获取当前期数，请稍后再试");
            return;
        }
        if(oca.getIvs().size()==0)
        {
            ToastUtil.showMessage(OrderCofirmActivity.this,"请下注");

            return;
        }
        bottomview3.setEnabled(false);
        JSONArray orders= CommitOrder.orderMaker_Usually(oca.getIvs());
        JSONArray traceOrders=CommitOrder.traceOrderMaker_Usually(oca.getIvs(), CurrentQS);
        double amount=0;
        int counts=0;
        for (int i = 0; i < oca.getIvs().size(); i++) {
            OrderConfirmAdapter.Itemvalue iv= oca.getIvs().get(i);
            amount=amount+iv.getTimes()*iv.getUnit()*iv.getBettimes();
            counts=iv.getBettimes()+counts;
        }

        CommitOrder.commit(new CommitOrder.OnFinishListener() {
            @Override
            public void OnDone(boolean succesful) {
                if (succesful) {
                    arrayList.clear();
                    SetAdapter();
                    setShowValue();
                    finish();
                }
                bottomview3.setEnabled(true);
            }
        }, false, false, OrderCofirmActivity.this, AnalysisType.CurrentLottery, String.format("%.2f", amount), counts + "", orders.toString(), traceOrders.toString());
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
        requestParams.put("lotteryCode", MainLottery_id);
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
                if(jsonobj.optString("isClose", "").equalsIgnoreCase("1"))
                {
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
                        if (CloseLimitime == 0  && isInFront()) {
                            AlertDialog2("第" + qsstr + "期已截止");
                        }
                        if (CloseLimitime == -1) {
                            timeview1.setText(getString(R.string.requestingqihao));
                            timeview2.setText("");
                            GetTime();
                        }
                    }
                }, OrderCofirmActivity.this, ltbean.getQsFormat());
                TimeMethod.Start(Integer.valueOf(ltbean.getTimes()));
                 /*倒计时*/
            }
        });
    }

    private void SetAdapter()
    {
        if(oca==null)
        {
            oca = new OrderConfirmAdapter(this, arrayList);
            oca.setOnDeleteListener(new OrderConfirmAdapter.OnDeleteListener() {
                @Override
                public void Ondelete(int index) {
                    arrayList.remove(index);
                    oca.Notify(arrayList);
                    setShowValue();
                }
            });
            listview.setAdapter(oca);
        }
        else
        {
            oca.Notify(arrayList);
        }

    }
}
