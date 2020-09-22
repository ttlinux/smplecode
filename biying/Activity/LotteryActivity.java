package com.lottery.biying.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.RunChart.RunChartActivity;
import com.lottery.biying.Activity.User.AccountManage.LotteryInstrution.LotteryInstrutionDetailActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer.LotteryAccountTracsferListActivity;
import com.lottery.biying.Activity.User.Record.OrderHistoryActivity;
import com.lottery.biying.Activity.User.Record.TraceHistoryActivity;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.LotteryMethod.CQSSC.RenXuan;
import com.lottery.biying.R;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.LotteryTimebean;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.bean.MoneyTypeBean;
import com.lottery.biying.bean.RewardRecordBean;
import com.lottery.biying.util.BitmapHandler;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HandleLotteryOrder;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.LotteryButtonOnCheckListener;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnMultiClickListener;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeMethod;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AppAlertDialog;
import com.lottery.biying.view.LotteryButton;
import com.lottery.biying.view.ContentScrollView;
import com.lottery.biying.view.Keyboard;
import com.lottery.biying.view.LotteryButton;
import com.lottery.biying.view.LotteryPopWindows;
import com.lottery.biying.view.LotterySiderDialog;
import com.lottery.biying.view.Lottery_RandomNumber_Dialog;
import com.lottery.biying.view.Lottery_more_Dialog;
import com.lottery.biying.view.ScrollLayout;
import com.lottery.biying.view.TipsDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/23.
 */
public class LotteryActivity extends BaseActivity implements View.OnClickListener, SensorEventListener {

    RelativeLayout lottery_view;
    public View temp2;
    public TextView playtips;
    ScrollLayout historylist;
    ContentScrollView contentview;
    TextView backtitle, title;
    ImageView assistant_icon, assistant_icon2;
    EditText multiple;
    PopupWindow popupWindow;
    LotteryPopWindows lotteryPopWindows;
    MenuBean.GameListBean.ListBean Selectbean;
    Drawable normal, upsidedown;
    LinearLayout contentmainview;
    SensorManager mSensorManager;
    Sensor mSensor;
    RadioGroup money_btns;
    JSONObject yl;
    int RollBackDistance;//底部回弹距离

    public static ArrayList<ArrayList<LotteryButton>> buttonnumbers = new ArrayList<>();
    private HashMap<String, ArrayList<TextView>> yilou_Btns = new HashMap<>();
    ArrayList<View> yilou = new ArrayList<>();
    //速度阀值
    private int mSpeed = 3000;
    //时间间隔
    private int mInterval = 50;
    //上一次摇晃的时间
    private long LastTime;
    //上一次的x、y、z坐标
    private float LastX, LastY, LastZ;
    private TextView yaoyiyao;
    LinearLayout lottrytimelayout;
    public LinearLayout lottery_list;
    public TextView timeview1, timeview2;
    int temp2sheight;
    TextView bottomview1, bottomview2, bottomview3, rule1;
    LinearLayout bottowview;
    SharedPreferences sharep;
    boolean isshowyilou = true, isOpenYaoyiyao = true;
    String tipsstr, example, lotterycode;
    //    HashMap<String, LotteryButton> Selectednumbers = new HashMap<>();//记录选中了的号码
    String functionbtns[], moneytype[];
    MenuBean mbean;
    ArrayList<View> fastbtn_layout = new ArrayList<>();
    RadioGroup Selectorswitch;
    LinearLayout weishu_layout;
    MoneyTypeBean current_mt_bean;
    double Award = 0;
    LotteryTimebean timebean;
    float Scrolly;
    CheckBox checkBox_yilou;
    public JSONObject lotteryresultJson;
    ArrayList<MenuBean.GameListBean.ListBean.OddsBean> oddsbeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_lottery);
        Initview();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AnalysisType.setLottertCategory(0);
        buttonnumbers.clear();
        TimeMethod.End();
    }

    private void Initview() {
        RollBackDistance = ScreenUtils.getDIP2PX(LotteryActivity.this, 30);
        lotterycode = getIntent().getStringExtra(BundleTag.id);
        AnalysisType.setCurrentLottery(getIntent().getStringExtra(BundleTag.id));
        functionbtns = getResources().getStringArray(R.array.functionbtns);
        moneytype = getResources().getStringArray(R.array.moneytype);
        sharep = RepluginMethod.getHosttSharedPreferences(this);
        isOpenYaoyiyao = sharep.getBoolean(BundleTag.isOpenYaoyiyao, true);//放弃记录摇一摇 这功能
//        Showmoretitles[3] = !isshowyilou ? "显示遗漏" : "隐藏遗漏";
//        Showmoretitles[4] = !isOpenYaoyiyao ? "开启摇一摇" : "关闭摇一摇";

        normal = getResources().getDrawable(R.drawable.triangle);
        upsidedown = new BitmapDrawable(BitmapHandler.upSideDown(this, R.drawable.triangle));
        Selectorswitch = FindView(R.id.Selectorswitch);
        multiple = FindView(R.id.multiple);
        multiple.setHint("1");
        multiple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1 && s.toString().equalsIgnoreCase("0"))
                    multiple.setText("");
                if (bottomview2.getTag() != null)
                    bottomview2.setText(HandleLotteryOrder.GetStr((int) bottomview2.getTag(), CaculateBetsMoney()));
            }
        });
        weishu_layout = FindView(R.id.weishu_layout);
        for (int i = 0; i < weishu_layout.getChildCount(); i++) {
            CheckBox checkbox = (CheckBox) weishu_layout.getChildAt(i);
            checkbox.setTag(i);
            checkbox.setGravity(Gravity.CENTER);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                        int pad = ScreenUtils.getDIP2PX(LotteryActivity.this, 10);
                        buttonView.setPadding(pad, 0, pad, 0);
                        buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                    } else {
                        buttonView.setGravity(Gravity.CENTER);
                        buttonView.setCompoundDrawablePadding(0);
                        buttonView.setPadding(0, 0, 0, 0);
                        buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    }
                    Caculatebets();
                }
            });
        }
        checkBox_yilou = FindView(R.id.checkBox_yilou);
        checkBox_yilou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShoworHideYiLou(isChecked);
            }
        });
        checkBox_yilou.setVisibility(View.GONE);
        yaoyiyao = FindView(R.id.yaoyiyao);
        money_btns = FindView(R.id.money_btns);
        playtips = FindView(R.id.playtips);
        playtips.setOnClickListener(this);
        yaoyiyao.setOnClickListener(this);
        backtitle = FindView(R.id.backtitle);
        rule1 = FindView(R.id.rule1);
        backtitle.setVisibility(View.VISIBLE);
        lottrytimelayout = FindView(R.id.lottrytimelayout);
        timeview1 = (TextView) lottrytimelayout.getChildAt(0);
        timeview2 = (TextView) lottrytimelayout.getChildAt(1);
        bottowview = FindView(R.id.bottowview);
        bottomview1 = (TextView) bottowview.getChildAt(0);
        bottomview2 = (TextView) bottowview.getChildAt(1);
        bottomview3 = (TextView) bottowview.getChildAt(2);
        bottomview1.setOnClickListener(this);
        bottomview3.setOnClickListener(this);
        bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
        assistant_icon = FindView(R.id.assistant_icon);
        assistant_icon2 = FindView(R.id.assistant_icon2);
        assistant_icon2.setOnClickListener(this);
        assistant_icon.setOnClickListener(this);
        temp2 = FindView(R.id.temp2);
        temp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogTools.e("touch", "touch " + v.getHeight());
            }
        });

        lottery_view = FindView(R.id.lottery_view);
        historylist = FindView(R.id.historylist);
        lottery_list = FindView(R.id.lottery_list);
        contentview = FindView(R.id.contentview);
        contentmainview = FindView(R.id.contentmainview);
        contentmainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        InitScrollview();
        mbean = new MenuBean();
        mbean.setMenuName(getIntent().getStringExtra(BundleTag.LotteryName));
        mbean.setMenuCode(getIntent().getStringExtra(BundleTag.id));
        title = FindView(R.id.title);
        title.setText(mbean.getMenuName());
        getDetailData(mbean);
        LotterySiderDialog.GetSiderData(this);
    }

    public void InitScrollview() {
        if (temp2sheight == 0) {
            temp2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (temp2sheight != 0) return;
                    int max = 0, med = 0;
                    temp2sheight = temp2.getHeight();
                    LogTools.e("temp2sheight", temp2sheight + "");
                    max = temp2sheight * 11;
                    med = temp2sheight * 6;
                    historylist.setMinOffset(0);
                    historylist.setExitOffset(ScreenUtils.getDIP2PX(LotteryActivity.this, 10));
                    historylist.setIsSupportExit(false);
                    historylist.setAllowHorizontalScroll(false);
                    historylist.setMaxOffset(max + RollBackDistance);
                    historylist.setMedOffset(med);
                    historylist.Open();
                }
            });

        }

    }

    private void InitTitleAndSelectWindows(MenuBean bean) {
        if (bean != null) {
            backtitle.setText(bean.getGameList().get(0).getList().get(0).getCurrentGameName());
            backtitle.setOnClickListener(this);
        }
        if (popupWindow == null && bean != null) {
            lotteryPopWindows = new LotteryPopWindows(bean, this, backtitle, new LotteryPopWindows.OnSelectListener() {
                @Override
                public void OnSelect(MenuBean.GameListBean.ListBean bean) {
                    Selectbean = bean;
                }
            });
            popupWindow = lotteryPopWindows.getPopupWindow();
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    bottomview2.setTag(null);//去掉了会出问题
                    bottomview1.setText(getResources().getString(R.string.randomchoose));
                    AnalysisType.setCurrentLotteryMainCode(Selectbean.getTitleCode());
                    requestdataNum();
                    HandleDetail_Bouns();
                    if (lotteryresultJson != null)
                        RefreshResultInterFace();
                }
            });
            AnalysisType.setCurrentLotteryMainCode(Selectbean.getTitleCode());
            if (lotteryresultJson != null)
                RefreshResultInterFace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtitle:
                if (popupWindow != null) {
                    if (!popupWindow.isShowing()) {
                        upsidedown.setBounds(0, 0, upsidedown.getMinimumWidth(), upsidedown.getMinimumHeight());
                        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, upsidedown, null);
                        lotteryPopWindows.setItemSelected(Selectbean);
                        View view = FindView(R.id.toplayout);
                        if (Build.VERSION.SDK_INT < 24) {
                            popupWindow.showAsDropDown(view);
                        } else {
                            Rect visibleFrame = new Rect();
                            view.getGlobalVisibleRect(visibleFrame);
                            int height = view.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                            popupWindow.setHeight(height);
                            popupWindow.showAsDropDown(view);
                        }
                    } else {
                        if(!isDestroyed() && !isFinishing())
                        popupWindow.dismiss();
                        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                    }
                }
                break;
            case R.id.yaoyiyao:
                Radomnumbers();
                break;
            case R.id.assistant_icon:
                LotterySiderDialog ldialog = new LotterySiderDialog(this, mbean.getMenuCode(), mbean.getMenuName());
                ldialog.show();
                break;
            case R.id.assistant_icon2:
                StartActivityOrderCommit(-1);
                break;
            case R.id.confirm:
                int resultcode = authOrder();
                if (resultcode == 0) {
//                        new Test(this);
                    Radomnumbers();
                }
                if (resultcode == 2) {

                    if (StartActivityOrderCommit(0)) {
                        RenXuan.isNeedSpecialView(Selectbean.getGameCode(), weishu_layout);
                        ClearSelectnumber(true);
                    }
                }

                break;
            case R.id.jixuan:
                if (AnalysisType.getSelectNumbers().size() > 0) {
                    //清除
                    ClearSelectnumber(false);
                    bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
                } else {
                    //机选 弹出框
                    ShowRandomDialog();
                }
                break;
            case R.id.playtips:
                TipsDialog dialog = new TipsDialog(this);
                dialog.setConetent(tipsstr, example);
                dialog.show();
                break;
            case R.id.multiple:
//                ShowKeyboard();
                break;
        }
    }

    private void ShowKeyboard() {
        if (current_mt_bean == null) return;
        Keyboard kb = new Keyboard(this, 1);
        String text = multiple.getText().toString();
        int multiply_num;
        if (text.length() > 0) {
            multiply_num = Integer.valueOf(text);
        } else {
            multiply_num = 1;
        }
//        moneytype
        int zhu = 0;
        if (bottomview2.getTag() != null) {
            zhu = (int) bottomview2.getTag();
        }
        kb.setzhu(zhu);
        kb.setTitle(zhu + "注%s倍" + "1期 " + "共%s元", current_mt_bean.getUnit(), multiply_num);
        kb.setOndismiss(new Keyboard.OnDismissListener() {
            @Override
            public void Ondismiss(int value, double unit) {
                multiple.setText(value + "");
                if (bottomview2.getTag() != null)
                    bottomview2.setText(HandleLotteryOrder.GetStr((int) bottomview2.getTag(), CaculateBetsMoney()));
            }
        });
        kb.show();
    }

    public void ClearSelectnumber(boolean isClearstatusonly) {

        for (int i = 0; i < buttonnumbers.size(); i++) {
            ArrayList<LotteryButton> btns = AnalysisType.getSelectNumbers().get(i);
            if (btns != null) {
                LogTools.e("ccc", btns.size() + "");
                for (int j = 0; j < btns.size(); j++) {
                    btns.get(j).setChecked(false);
                }
            }
        }
        if (!isClearstatusonly)
            AnalysisType.getSelectNumbers().clear();

        bottomview1.setText(getResources().getString(R.string.randomchoose));
        bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
        bottomview2.setTag(null);
        oddsbeans.clear();
        setRewardOnClickbtn(null, false);
    }

    public void requestdataNum() {
        if (Selectbean == null) return;


        //初始化weishu_layout
//        LogTools.e("tttt",RenXuan.isNeedSpecialView(Selectbean.getGameCode())?"YYY":"NNN");
        RenXuan.isNeedSpecialView(Selectbean.getGameCode(), weishu_layout);
        bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
        bottomview1.setText(getResources().getString(R.string.randomchoose));
        buttonnumbers.clear();
        yilou_Btns.clear();

//        String showWinMoney = currentBean.getShowWinMoney();
//        String winMoneyDes = currentBean.getWinMoneyDes();
//        SpannableStringBuilder style = new SpannableStringBuilder(winMoneyDes + showWinMoney);
//        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.loess4)), winMoneyDes.length(), style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        rule1.setText(currentBean.getNumSelectDes());
//        rule2.setText(style);
        while (contentmainview.getChildCount() > 3) {
            contentmainview.removeViewAt(3);
        }
        int dip10=ScreenUtils.getDIP2PX(this,10);
        int rowcount = AnalysisType.getCountOfLotteryBtns(this,Selectbean.getGameCode());//暂时定时
        int oddstype = Selectbean.getOddsType();
        Boolean hasLeftBtns=AnalysisType.CheckIfHasLeftBtn(this,Selectbean.getGameCode());
        for (int i = 0; i < Selectbean.getBallbeans().size(); i++) {
            MenuBean.GameListBean.ListBean.Ballbean ballbean = Selectbean.getBallbeans().get(i);
            String ws = ballbean.getWs();
            JSONObject nums[] = ballbean.getBallnumber();
            LinearLayout itemlayout = null;
            ArrayList<LotteryButton> bs = new ArrayList<LotteryButton>();
            ArrayList<TextView> yls = new ArrayList<>();
            for (int k = 0; k < nums.length; k++) {
                if ((k + 1) % rowcount == 1) {
                    itemlayout = new LinearLayout(LotteryActivity.this);
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (k == 0)
                        ll.topMargin = ScreenUtils.getDIP2PX(LotteryActivity.this, 10);
                    else
                        ll.topMargin = ScreenUtils.getDIP2PX(LotteryActivity.this, 5);
                    itemlayout.setLayoutParams(ll);
                    itemlayout.setOrientation(LinearLayout.HORIZONTAL);
                    contentmainview.addView(itemlayout);

                    if(hasLeftBtns)
                    {
                        View leftview = View.inflate(LotteryActivity.this, R.layout.item_numberlist_left, null);
                        TextView tag1 = (TextView) leftview.findViewById(R.id.tag1);
                        TextView tag2 = (TextView) leftview.findViewById(R.id.tag2);
                        tag1.setText(ws);
                        tag2.setText(getString(R.string.yl));
//                    if (!isshowyilou) ((View) tag2.getParent()).setVisibility(View.GONE);
                        yilou.add((View) tag2.getParent());
                        itemlayout.addView(leftview);
                        if (k > 0) {
                            leftview.setVisibility(View.INVISIBLE);
                        }
                    }

                }

                final LinearLayout numberlist = LotteryButton.makeView(this,Selectbean.getGameCode(),k%rowcount==0?dip10:(dip10/2));
                LotteryButton bn = (LotteryButton) numberlist.getChildAt(0);
                IndexRecordBean irb = new IndexRecordBean(i, k);
                irb.setTitle(ws);
                if (oddstype == 1) {
                    //点了会变的赔率情况
                    String hm = nums[k].optString("hm", "");
                    for (int t = 0; t < Selectbean.getOddsBeans().size(); t++) {
                        if (hm.equalsIgnoreCase(Selectbean.getOddsBeans().get(t).getHm())) {
                            irb.setOddsBean(Selectbean.getOddsBeans().get(t));
                            break;
                        }
                    }
                }
                bn.setTag(irb);
                bn.setOnCheckListener(new LotteryButtonOnCheckListener() {

                    @Override
                    public boolean OnChecking(LotteryButton buttonNumber, boolean status) {
                        boolean needhandle = AnalysisType.CaculateStatus(buttonnumbers, buttonNumber, Selectbean.getGameCode());
                        if (needhandle) {
                            if (AnalysisType.getSelectNumbers().size() > 0)
                                bottomview1.setText(getResources().getString(R.string.clearnumber));
                            else
                                bottomview1.setText(getResources().getString(R.string.randomchoose));
                        }
                        return needhandle;
                    }

                    @Override
                    public void OnChecked(LotteryButton buttonNumber, boolean status) {
                        if (buttonNumber.getTag() != null && Selectbean.getOddsType() == 1) {
                            IndexRecordBean irb = (IndexRecordBean) buttonNumber.getTag();
                            setRewardOnClickbtn(irb, status);
                        }
                        Caculatebets();

                    }
                });
                TextView yltext = (TextView) numberlist.getChildAt(1);
                bn.setText(nums[k].optString("hm", ""));
                String AliceName=irb.getOddsBean()==null?"":irb.getOddsBean().getName();
                bn.fillUpData(nums[k].optString("hm", ""),"",AliceName);
                yltext.setText("-");
//                if (!isshowyilou) yltext.setVisibility(View.GONE);
                yilou.add(yltext);
                bs.add(bn);
                yls.add(yltext);
                itemlayout.addView(numberlist);
            }
            buttonnumbers.add(bs);
            yilou_Btns.put(ballbean.getCode(), yls);
            if (ballbean.getFastButton().equalsIgnoreCase("1"))
                addBtns(i);//加入大小单双等按钮
            if (i + 1 < Selectbean.getBallbeans().size()) {

                ImageView line = new ImageView(LotteryActivity.this);
                line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
                line.setBackgroundColor(0x33666666);
                contentmainview.addView(line);
            }
        }

        SetYiLou();
        AnalysisType.setCurrentbuttons(buttonnumbers);
    }

    private void SetYiLou()//设置遗漏
    {
                /*设置遗漏*/
        if (Selectbean != null && yl != null && yilou_Btns.size() > 0) {
            for (int i = 0; i < Selectbean.getBallbeans().size(); i++) {
                String ws = Selectbean.getBallbeans().get(i).getCode();
                JSONArray ws_arr = yl.optJSONArray(ws);
                if (yilou_Btns.get(ws) != null && ws_arr != null) {
                    for (int j = 0; j < yilou_Btns.get(ws).size(); j++) {
                        yilou_Btns.get(ws).get(j).setText(ws_arr.optString(j, ""));
                    }
                }
            }

        }
                    /*设置遗漏*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogTools.e("Loooooo", "start");
        Getlotteryresult();
        GetTime();
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mSensor != null) {
                mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        TimeMethod.End();
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!isOpenYaoyiyao) return;
        long NowTime = System.currentTimeMillis();
        if ((NowTime - LastTime) < mInterval)
            return;
        //将NowTime赋给LastTime
        LastTime = NowTime;
        //获取x,y,z
        float NowX = event.values[0];
        float NowY = event.values[1];
        float NowZ = event.values[2];
        //计算x,y,z变化量
        float DeltaX = NowX - LastX;
        float DeltaY = NowY - LastY;
        float DeltaZ = NowZ - LastZ;
        //赋值
        LastX = NowX;
        LastY = NowY;
        LastZ = NowZ;
        //计算
        double NowSpeed = Math.sqrt(DeltaX * DeltaX + DeltaY * DeltaY + DeltaZ * DeltaZ) / mInterval * 10000;
        //判断
        if (NowSpeed >= mSpeed) {
            Radomnumbers();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private boolean Radomnumbers() {
        if (buttonnumbers.size() == 0) {
            LogTools.e("buttonnumbers is 0", "buttonnumbers is 0");
            return false;
        }
        ClearSelectnumber(false);

        bottomview1.setText(getResources().getString(R.string.clearnumber));
        AnalysisType.RandomNumbers(buttonnumbers, Selectbean.getGameCode());
        if (Selectbean.getOddsType() == 1) {
            oddsbeans.clear();
            for (int i = 0; i < buttonnumbers.size(); i++) {
                for (int k = 0; k < buttonnumbers.get(i).size(); k++) {
                    LotteryButton bn = buttonnumbers.get(i).get(k);
                    if (bn.getChecked()) {
                        IndexRecordBean irb = (IndexRecordBean) bn.getTag();
                        if (irb.getOddsBean() != null)
                            oddsbeans.add(irb.getOddsBean());
                    }
                }
            }
            setRewardOnClickbtn(null, false);
        }
        Caculatebets();

        return true;
    }


    private void ShowRandomDialog() {
        final int num[] = {1, 5, 10};
        String numstrs[] = new String[num.length];
        for (int i = 0; i < num.length; i++) {
            numstrs[i] = num[i] + "注";
        }
        final Lottery_RandomNumber_Dialog dialog = new Lottery_RandomNumber_Dialog(this, numstrs);
        dialog.showDropUp(bottomview1);
        dialog.setOnDiaClickitemListener(new Lottery_RandomNumber_Dialog.OnDiaClickitemListener() {
            @Override
            public void Onclickitem(int index) {
                dialog.hide();
                if (current_mt_bean == null) return;
                if (timebean == null) {
                    ToastUtil.showMessage(LotteryActivity.this, "未获取到期数");
                    return;
                }
                if (multiple.getText().toString().length() > 1 && Selectbean.getMultipleMax().length() > 0) {
                    int beishu = Integer.valueOf(multiple.getText().toString());
                    if (beishu > Integer.valueOf(Selectbean.getMultipleMax())) {
                        AlertDialog2("当前最大倍数限制为" + Selectbean.getMultipleMax() + "倍");
                        return;
                    }
                }
                int indexlist[] = {1, 5, 10};
                if (StartActivityOrderCommit(indexlist[index])) {
                    RenXuan.isNeedSpecialView(Selectbean.getGameCode(), weishu_layout);
                    ClearSelectnumber(true);
                }
            }
        });

    }

    private void Getlotteryresult() {
//        ClearSelectnumber(false);
        RequestParams requestparams = new RequestParams();
        requestparams.put("lotteryCode", getIntent().getStringExtra(BundleTag.id));
        requestparams.put("count", 10 + "");
        Httputils.PostWithBaseUrl(Httputils.lotteryresult, requestparams, new MyJsonHttpResponseHandler(this, false) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                //彩票类型(1:时时彩 2:赛车|飞艇 3:快3 4:11选5 5:排列3|福彩 6:幸运28 7:香港)
                LogTools.e("Getlotteryresult", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(LotteryActivity.this, jsonObject.optString("msg"));
                    return;
                }
                lotteryresultJson = jsonObject;
                if(Selectbean!=null && Selectbean.getGameCode()!=null)
                RefreshResultInterFace();
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    private void RefreshResultInterFace() {

        JSONObject datas = lotteryresultJson.optJSONObject("datas");
        JSONArray resultList = datas.optJSONArray("openResult");
        //定死10条不够就不显示
        LotteryResultList lbean = null;
        for (int i = 0; i < 10; i++) {
            JSONObject jsonobj = null;
            if (resultList.length() > i)
                jsonobj = resultList.optJSONObject(i);
            else
                jsonobj = new JSONObject();
            //不够数据补个空的数据
            LotteryResultList bean = new LotteryResultList();
            bean.setQs(jsonobj.optString("qs", ""));
            bean.setQsFormat(jsonobj.optString("qsFormat", ""));
            bean.setIsOpen(jsonobj.optInt("isOpen", 0));
            bean.setType(jsonobj.optInt("type", 0));
            if (Selectbean != null && Selectbean.getGameName() != null)
                bean.setGameCode(Selectbean.getGameCode());

            JSONArray openResult = jsonobj.optJSONArray("openResult");
            if (openResult != null && openResult.length() > 0) {
                String results[] = new String[openResult.length()];
                for (int j = 0; j < openResult.length(); j++) {
                    results[j] = openResult.optString(j);
                }
                bean.setOpenResult(results);
            }

            int color = i % 2 == 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.gray18);
            View view = null;
            if (lottery_list.getTag() != null) {
                view = lottery_list.getChildAt(i + 1);
                AnalysisType.Inititem(bean, color, LotteryActivity.this, view);
            } else {
                view = AnalysisType.Inititem(bean, color, LotteryActivity.this, view);
                lottery_list.addView(view);
            }
            if (view != null)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LotteryActivity.this, RunChartActivity.class);
                        intent.putExtra(BundleTag.Index, 1);
                        intent.putExtra(BundleTag.id, getIntent().getStringExtra(BundleTag.id));
                        startActivity(intent);
                    }
                });
            lbean = bean;
        }
        if (lbean != null) {
            if (lbean.getType() != 0)
                AnalysisType.setLottertCategory(lbean.getType());
            AnalysisType.ModifyTitleItem((RelativeLayout) temp2, lbean.getType(), lbean.getGameCode());
        }

        if (lottery_list.getTag() == null) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getDIP2PX(this, 30)));
            textView.setText(getString(R.string.toasts));
            textView.setGravity(Gravity.CENTER);
            lottery_list.addView(textView);
        }
        lottery_list.setTag("DONE");

    }


    private void ShoworHideYiLou(boolean arg) {
        for (int i = 0; i < yilou.size(); i++) {
            yilou.get(i).setVisibility(arg ? View.VISIBLE : View.GONE);
        }
    }

    public int authOrder() {
        String atleasechooseone = getResources().getString(R.string.atleasechooseone);
        String plzcnumber = getResources().getString(R.string.plzcnumber);
        int showdialog = 2;
        //0 一个号码都没选 1 没选齐号码 2 直接跳转 3 选号注数大于10000
        if (AnalysisType.getSelectNumbers().size() == 0) {
            showdialog = 0;
        } else {
            if (bottomview2.getTag() == null) {
                showdialog = 1;
                AppAlertDialog aad = new AppAlertDialog(this);
                aad.setConetnt(atleasechooseone);
                aad.setTitle(plzcnumber);
                aad.setOnChooseListener(new AppAlertDialog.OnChooseListener() {
                    @Override
                    public void Onchoose(boolean confirm, AppAlertDialog dialog) {
                        dialog.hide();

                    }
                });
                aad.show();
            }
//            else if ((int) bottomview2.getTag() > 10000) {
//                showdialog = 3;
//                AlertDialog2(getString(R.string.tips9));
//            }
        }
        return showdialog;
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

    private void addBtns(final int hor_index) {
        Drawable jiantou = getResources().getDrawable(R.drawable.jiantou);
        int marginleft = jiantou.getMinimumWidth() + ScreenUtils.getDIP2PX(this, 7);
        RadioGroup linearLayout = new RadioGroup(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(marginleft, ScreenUtils.getDIP2PX(this, 7), 0, ScreenUtils.getDIP2PX(this, 10));

        int hw = ScreenUtils.getDIP2PX(this, 30);
        for (int i = 0; i < 6; i++) {
            RadioButton radiobtn = new RadioButton(this);
            RadioGroup.LayoutParams ll = new RadioGroup.LayoutParams(hw, hw);
            ll.leftMargin = ScreenUtils.getDIP2PX(this, 10);
            radiobtn.setLayoutParams(ll);
            radiobtn.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            radiobtn.setText(functionbtns[i]);
            radiobtn.setTag(i);
            radiobtn.setGravity(Gravity.CENTER);
            radiobtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            radiobtn.setTextColor(0xff666666);
            radiobtn.setBackground(getResources().getDrawable(R.drawable.btn_conner_radiu_white));
            radiobtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        int tag = (int) buttonView.getTag();
//                        if (tag == 5) {
//                            buttonView.setChecked(false);
//                        } else {
////                            buttonView.setTextColor(0xFFFFFFFF);
////                            buttonView.setBackground(getResources().getDrawable(R.drawable.btn_conner_radiu_red));
//                        }
                        buttonView.setChecked(false);
                        LogTools.e("hor_index", hor_index + "");
                        ArrayList<LotteryButton> btnnums = buttonnumbers.get(hor_index);
                        for (int i = 0; i < btnnums.size(); i++) {
                            btnnums.get(i).setChecked(false);
                        }
                        switch (tag) {
                            case 0:
                                for (int i = 0; i < btnnums.size(); i++) {
                                    btnnums.get(i).setChecked(true);
                                }
                                break;
                            case 1:
                                int dcount = btnnums.size() > 4 ? btnnums.size() / 2 : btnnums.size();
                                for (int i = dcount; i < btnnums.size(); i++) {
                                    btnnums.get(i).setChecked(true);
                                }
                                break;
                            case 2:
                                int xcount = btnnums.size() > 4 ? btnnums.size() / 2 : btnnums.size();
                                for (int i = 0; i < xcount; i++) {
                                    btnnums.get(i).setChecked(true);
                                }
                                break;
                            case 3:
                                for (int i = 0; i < btnnums.size(); i++) {
                                    if (Integer.valueOf(btnnums.get(i).getText()) % 2 == 1)
                                        btnnums.get(i).setChecked(true);
                                }
                                break;
                            case 4:
                                for (int i = 0; i < btnnums.size(); i++) {
                                    if (Integer.valueOf(btnnums.get(i).getText()) % 2 == 0)
                                        btnnums.get(i).setChecked(true);
                                }
                                break;
                            case 5:
                                for (int i = 0; i < btnnums.size(); i++) {
                                    btnnums.get(i).setChecked(false);
                                }
                                break;
                        }
                        //点完计算
                        Caculatebets();
                    } else {
                        buttonView.setTextColor(0xff666666);
                        buttonView.setBackground(getResources().getDrawable(R.drawable.btn_conner_radiu_white));
                    }

                }
            });
            linearLayout.addView(radiobtn);
        }
        contentmainview.addView(linearLayout);
//        linearLayout.setVisibility(View.GONE);
        fastbtn_layout.add(linearLayout);
    }

    private void getDetailData(final MenuBean menuBean) {

        RequestParams requestparams = new RequestParams();
        requestparams.put("lotteryCode", lotterycode);
//        requestparams.put("gameCode", Selectbean.getGameCode());
        Httputils.PostWithBaseUrl(Httputils.detail, requestparams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("detailjsonObject", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                JSONObject datas = jsonObject.optJSONObject("datas");
                menuBean.setTrace(datas.optInt("trace", 0));
                menuBean.setType(datas.optInt("type", 0));
                AnalysisType.setLottertCategory(menuBean.getType());

                JSONArray gameList = datas.optJSONArray("gameList");
                List<MenuBean.GameListBean> glbbeans = new ArrayList<>();
                for (int i = 0; i < gameList.length(); i++) {
                    JSONObject temp = gameList.optJSONObject(i);
                    MenuBean.GameListBean glbean = new MenuBean.GameListBean();
                    glbean.setTitle(temp.optString("title", ""));
                    glbean.setTitleCode(temp.optString("titleCode", ""));
                    JSONArray list = temp.optJSONArray("list");
                    List<MenuBean.GameListBean.ListBean> listBeans = new ArrayList<>();
                    for (int j = 0; j < list.length(); j++) {
                        MenuBean.GameListBean.ListBean lbean = new MenuBean.GameListBean.ListBean();
                        JSONObject jsonobj = list.optJSONObject(j);

                        lbean.setBallbeans(MenuBean.Handlder_Json_Ballbean(jsonobj.optJSONArray("balls")));
                        lbean.setHelpers(MenuBean.Handlder_Json_Helper(jsonobj.optJSONArray("zhushouList")));
                        lbean.setDetail(MenuBean.Handlder_Json_Detail(jsonobj.optJSONObject("detail")));
                        lbean.setBonuses(MenuBean.Handlder_Json_Bonus(jsonobj.optJSONArray("bonus")));

                        lbean.setSfyl(jsonobj.optString("sfyl", ""));
                        lbean.setGameCode(jsonobj.optString("gameCode", ""));
                        lbean.setGameName(jsonobj.optString("gameName", ""));
                        lbean.setCurrentGameName(jsonobj.optString("currentGameName", ""));
                        lbean.setIndex(j);
                        lbean.setOddsBeans(MenuBean.Handlder_Json_Odds(jsonobj.optJSONArray("oddsArr")));
                        lbean.setOddsType(jsonobj.optInt("oddsType", 0));
                        lbean.setTitleCode(glbean.getTitleCode());
                        lbean.setGameListIndex(i);
                        lbean.setAppendQsMax(jsonobj.optString("appendQsMax", ""));
                        lbean.setMultipleMax(jsonobj.optString("multipleMax", ""));
                        listBeans.add(lbean);
                    }
                    glbean.setList(listBeans);
                    glbbeans.add(glbean);
                }
                menuBean.setGameList(glbbeans);

                /*圆角分*/
                JSONArray unit = datas.optJSONArray("unit");
                for (int i = 0; i < money_btns.getChildCount(); i++) {
                    RadioButton view = (RadioButton) money_btns.getChildAt(i);
                    view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked && buttonView.getTag() != null) {

                                current_mt_bean = (MoneyTypeBean) buttonView.getTag();
                                setRewardOnClickbtn(null, false);
                                if (bottomview2.getTag() != null)
                                    bottomview2.setText(HandleLotteryOrder.GetStr((int) bottomview2.getTag(), CaculateBetsMoney()));
                            }
                        }
                    });
                    view.setVisibility(View.GONE);
                }
                for (int i = 0; i < unit.length(); i++) {
                    JSONObject un = unit.optJSONObject(i);
                    int tag = un.optInt("type", 1);
                    if (tag > 0 && tag < 5) {
                        RadioButton view = (RadioButton) money_btns.getChildAt(tag - 1);
                        view.setVisibility(View.VISIBLE);
                        MoneyTypeBean myb = new MoneyTypeBean();
                        myb.setIndex(tag - 1);
                        myb.setType(tag);
                        myb.setName(view.getText().toString());
                        myb.setUnit(un.optDouble("value", 0));
                        view.setTag(myb);
                    }
                }
                RadioButton rb = (RadioButton) money_btns.getChildAt(0);
                rb.setChecked(true);
                 /*圆角分*/

                InitTitleAndSelectWindows(menuBean);
                requestdataNum();
                HandleDetail_Bouns();
            }
        });
    }

    private void Caculatebets() {
        int zhu = AnalysisType.Caculatebets(buttonnumbers, getGameCode());
        if (zhu > 0) {
            bottomview2.setTag(zhu);
            bottomview2.setText(HandleLotteryOrder.GetStr(zhu, CaculateBetsMoney()));
        } else {
            bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
            bottomview2.setTag(null);
        }
        if (AnalysisType.getSelectNumbers().size() == 0) {
            bottomview1.setText(getResources().getString(R.string.randomchoose));
        } else {
            bottomview1.setText(getResources().getString(R.string.clearnumber));
        }
    }

    private double CaculateBetsMoney() {
        int bets = 0;
        int times = 0;
        if (bottomview2.getTag() != null) {
            bets = (int) bottomview2.getTag();
        }
        if (multiple.getText().toString().length() < 1) {
            times = 1;
        } else
            times = Integer.valueOf(multiple.getText().toString());
        if (current_mt_bean == null) return 0;
        return current_mt_bean.getUnit() * times * bets;
    }


    private String getGameCode() {
        String gamecode = Selectbean.getGameCode();
        if (weishu_layout.getVisibility() == View.VISIBLE) {
            //任选投注有特殊模式 把位数加在gamecode
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < weishu_layout.getChildCount(); i++) {
                CheckBox cb = (CheckBox) weishu_layout.getChildAt(i);
                if (cb.isChecked()) {
                    sb.append(i);
                }
            }
            sb.append("|");
            gamecode = sb.toString() + gamecode;
        }
        return gamecode;
    }

    private void GetTime() {
        timebean = null;
        timeview1.setText(getString(R.string.requestingqihao));
        timeview2.setText("");
        TimeMethod.End();
        RequestParams requestParams = new RequestParams();
        requestParams.put("lotteryCode", lotterycode);
        Httputils.PostWithBaseUrl(Httputils.DTime, requestParams, new MyJsonHttpResponseHandler(this, false) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                timeview1.setText(getString(R.string.requestingqihaofail));
                timeview1.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onMultiClick(View v) {
                        GetTime();
                    }
                });
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("timeeee", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                timeview1.setOnClickListener(null);
                JSONObject datas = jsonObject.optJSONObject("datas");
                JSONObject jsonobj = datas.optJSONObject("paiqiResult");
                yl = datas.optJSONObject("yl");
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
                timebean = ltbean;

                SetYiLou();

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
                            Getlotteryresult();
                            GetTime();
                        }
                    }
                }, LotteryActivity.this, ltbean.getQsFormat());
                TimeMethod.Start(Integer.valueOf(ltbean.getTimes()));
                 /*倒计时*/
            }
        });
    }

    private void HandleDetail_Bouns() {

        if (Selectbean == null) return;

        //玩法说明 和快速下注按钮
        tipsstr = Selectbean.getDetail().getWfsm();
        example = Selectbean.getDetail().getXhsl();

        ShoworHideYiLou(false);
        int ishow = Selectbean.getSfyl().equalsIgnoreCase("1") ? View.VISIBLE : View.GONE;
        checkBox_yilou.setVisibility(ishow);
        checkBox_yilou.setChecked(Selectbean.getDetail().getControlYl().equalsIgnoreCase("1"));


        /*高奖高返*/
        if (Selectbean.getBonuses().size() == 1) {
            Selectorswitch.getLayoutParams().width = Selectorswitch.getMeasuredWidth();
            Selectorswitch.getChildAt(1).setVisibility(View.GONE);
            RadioButton radiobtn = (RadioButton) Selectorswitch.getChildAt(0);
            radiobtn.setBackground(getResources().getDrawable(R.drawable.radio_lottery_selector));
        }
        Selectorswitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setRewardOnClickbtn(null, false);
            }
        });
        for (int bo = 0; bo < Selectbean.getBonuses().size(); bo++) {
            RadioButton radiobtn = (RadioButton) Selectorswitch.getChildAt(Selectbean.getBonuses().get(bo).getBonusType());
            radiobtn.setText(Selectbean.getBonuses().get(bo).getBonusName());
            radiobtn.setVisibility(View.VISIBLE);


            radiobtn.setTag(Selectbean.getBonuses().get(bo));
            if (Selectbean.getBonuses().get(bo).getSelect().equalsIgnoreCase("1") && Award == 0) {
                radiobtn.setChecked(true);
            }
        }
        oddsbeans.clear();
        setRewardOnClickbtn(null, false);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_SCROLL:
//                isFingerOnScreen=true;
//                break;
//            case MotionEvent.ACTION_UP:
//                isFingerOnScreen=false;
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                isFingerOnScreen=false;
//                break;
//        }
//        if (Scrolly > historylist.getMaxOffset() - RollBackDistance && !isFingerOnScreen && historylist.getScrollStatus())
//        {
//            historylist.scrollTo(0,-(historylist.getMaxOffset() - RollBackDistance));
//        }
//        return super.onTouchEvent(event);
//    }

    private boolean StartActivityOrderCommit(int type) {
        // type 0确认进来的 -1进入购物车 >0 随机投注
        if (current_mt_bean == null) return false;
        if (timebean == null) {
            ToastUtil.showMessage(this, "未获取到期数");
            return false;
        }
        if (multiple.getText().toString().length() > 1 && Selectbean.getMultipleMax().length() > 0) {
            int beishu = Integer.valueOf(multiple.getText().toString());
            if (beishu > Integer.valueOf(Selectbean.getMultipleMax())) {
                AlertDialog2("当前最大倍数限制为" + Selectbean.getMultipleMax() + "倍");
                return false;
            }
        }
        Intent intent = new Intent();
        intent.setClass(LotteryActivity.this, OrderCofirmActivity.class);
        intent.putExtra(BundleTag.id, getIntent().getStringExtra(BundleTag.id));
        intent.putExtra(BundleTag.TitleCode, Selectbean.getTitleCode());
        intent.putExtra(BundleTag.QS, timebean.getQs());
        intent.putExtra(BundleTag.title, backtitle.getText().toString());
        if (bottomview2.getTag() != null)
            intent.putExtra(BundleTag.BetTimes, (int) bottomview2.getTag());
        intent.putExtra(BundleTag.GameCode, getGameCode());
        intent.putExtra(BundleTag.Randomtime, 0);
        intent.putExtra(BundleTag.LotteryName, getIntent().getStringExtra(BundleTag.LotteryName));
        intent.putExtra(BundleTag.Unit, current_mt_bean.getUnit());
        intent.putExtra(BundleTag.UnitType, current_mt_bean.getType());
        intent.putExtra(BundleTag.AWARD, Award);

        int selected = 0;
        String typename = "";
        for (int i = 0; i < Selectorswitch.getChildCount(); i++) {
            RadioButton radiobtn = (RadioButton) Selectorswitch.getChildAt(i);
            if (radiobtn.isChecked()) {
                MenuBean.GameListBean.ListBean.Bonus bonus = ((MenuBean.GameListBean.ListBean.Bonus) radiobtn.getTag());
                selected = bonus.getBonusType();
                typename = bonus.getBonusName();
                break;
            }
        }
        intent.putExtra(BundleTag.BounsType, selected);
        intent.putExtra(BundleTag.BounsName, typename);
        intent.putExtra(BundleTag.AppendQsMax, Selectbean.getAppendQsMax());
        intent.putExtra(BundleTag.MultipleMax, Selectbean.getMultipleMax());
        if (multiple.getText().toString().length() < 1) {
            intent.putExtra(BundleTag.Times, 1);
        } else {
            intent.putExtra(BundleTag.Times, Integer.valueOf(multiple.getText().toString()));
        }
        intent.putExtra(BundleTag.Randomtime, type);
        intent.putExtra(BundleTag.HasTrace, mbean.getTrace());
        intent.putExtra(BundleTag.LotteryCategory, mbean.getType());
        startActivityForResult(intent, BundleTag.RequestCode);

        oddsbeans.clear();
        setRewardOnClickbtn(null, false);

        return true;
    }


    public void setRewardOnClickbtn(IndexRecordBean bean, boolean status) {
        float unit = 1;
        switch (current_mt_bean.getType()) {
            case 1:
                unit = 1;
                break;
            case 2:
                unit = 0.1f;
                break;
            case 3:
                unit = 0.01f;
                break;
        }
        LogTools.e("mmmm", current_mt_bean.getType() + " ");
        int selected = 0;
        for (int i = 0; i < Selectorswitch.getChildCount(); i++) {
            RadioButton radiobtn = (RadioButton) Selectorswitch.getChildAt(i);
            if (radiobtn.isChecked()) {
                MenuBean.GameListBean.ListBean.Bonus bonus = ((MenuBean.GameListBean.ListBean.Bonus) radiobtn.getTag());
                if(bonus==null)
                    return;
                selected = bonus.getBonusType();
                break;
            }
        }

        if (bean != null) {
            //切换高奖高返 或者别的操作 没有点击数字按钮的时候不操作oddsbeans
            if (status) {
                oddsbeans.add(bean.getOddsBean());
            } else {
                oddsbeans.remove(bean.getOddsBean());
            }
        }

        //改变显示的赔率 目前只有六合彩有这个
        for (int i = 0; i < buttonnumbers.size(); i++) {
            for (int j = 0; j <buttonnumbers.get(i).size() ; j++) {
                LotteryButton lotteryButton=buttonnumbers.get(i).get(j);
                if(lotteryButton.getData()==null || lotteryButton.getType()!= LotteryButton.ButtonTypes.Rectangle_HK6 || lotteryButton.getTag()==null)
                {
                    continue;
                }
                else
                {
                    //六合彩的长方形框需要改变赔率
                    Object[] objects=lotteryButton.getData();

                    if(lotteryButton.getTag()==null)
                    {
                        continue;
                    }
                    IndexRecordBean indexRecordBean=(IndexRecordBean)lotteryButton.getTag();
                    if(indexRecordBean.getOddsBean()!=null)
                    {
                        switch (selected)
                        {
                            case 0:
                                //高奖金
                                objects[1]="赔率: "+indexRecordBean.getOddsBean().getGjj();
                                break;
                            case 1:
                                //高返点
                                objects[1]="赔率: "+indexRecordBean.getOddsBean().getGfd();
                                break;
                        }
                        lotteryButton.fillUpData(objects);
                    }


                }
            }
        }

        rule1.setText("");
        rule1.append("奖金: ");
        switch (selected) {
            case 0:
                //高奖金
                if (oddsbeans.size() > 1) {
                    MenuBean.GameListBean.ListBean.OddsBean min = oddsbeans.get(0);
                    MenuBean.GameListBean.ListBean.OddsBean max = oddsbeans.get(0);
                    for (int i = 0; i < oddsbeans.size(); i++) {
                        float temp = Float.valueOf(oddsbeans.get(i).getGjj());
                        if (Float.valueOf(min.getGjj()) > temp) {
                            min = oddsbeans.get(i);
                        }
                        if (Float.valueOf(max.getGjj()) < temp) {
                            max = oddsbeans.get(i);
                        }
                    }
                    float minvalue = Float.valueOf(min.getGjj()) * unit;
                    float maxvalue = Float.valueOf(max.getGjj()) * unit;

                    if (!min.getGjj().equalsIgnoreCase(max.getGjj())) {
                        rule1.append(Httputils.set_four(minvalue) + " - " + Httputils.set_four(maxvalue));
                    } else {
                        rule1.append(Httputils.set_four(minvalue) + "");
                    }
                    Award = Float.valueOf(min.getGjj()) * unit;
                } else {
                    if (oddsbeans.size() == 0) {
                        MenuBean.GameListBean.ListBean.OddsBean min = Selectbean.getOddsBeans().get(0);
                        MenuBean.GameListBean.ListBean.OddsBean max = Selectbean.getOddsBeans().get(0);
                        for (int i = 0; i < Selectbean.getOddsBeans().size(); i++) {
                            float temp = Float.valueOf(Selectbean.getOddsBeans().get(i).getGjj());
                            if (Float.valueOf(min.getGjj()) > temp) {
                                min = Selectbean.getOddsBeans().get(i);
                            }
                            if (Float.valueOf(max.getGjj()) < temp) {
                                max = Selectbean.getOddsBeans().get(i);
                            }
                        }
                        float minvalue = Float.valueOf(min.getGjj()) * unit;
                        float maxvalue = Float.valueOf(max.getGjj()) * unit;
                        if (!min.getGjj().equalsIgnoreCase(max.getGjj())) {
                            rule1.append(Httputils.set_four(minvalue) + " - " +Httputils.set_four( maxvalue));
                        } else {
                            rule1.append(Httputils.set_four(minvalue) + "");
                        }
                        Award = Float.valueOf(min.getGjj()) * unit;
                    } else {
                        Award = Float.valueOf(oddsbeans.get(0).getGjj()) * unit;
                        rule1.append(Httputils.set_four(Award) + "");
                    }

                }
                break;
            case 1:
                //高返点
                if (oddsbeans.size() > 1) {
                    MenuBean.GameListBean.ListBean.OddsBean min = oddsbeans.get(0);
                    MenuBean.GameListBean.ListBean.OddsBean max = oddsbeans.get(0);
                    for (int i = 0; i < oddsbeans.size(); i++) {
                        float temp = Float.valueOf(oddsbeans.get(i).getGfd());
                        if (Float.valueOf(min.getGfd()) > temp) {
                            min = oddsbeans.get(i);
                        }
                        if (Float.valueOf(max.getGfd()) < temp) {
                            max = oddsbeans.get(i);
                        }
                    }
                    float minvalue = Float.valueOf(min.getGfd()) * unit;
                    float maxvalue = Float.valueOf(max.getGfd()) * unit;
                    if (!min.getGfd().equalsIgnoreCase(max.getGfd())) {
                        rule1.append(Httputils.set_four(minvalue) + " - " + Httputils.set_four(maxvalue));
                    } else {
                        rule1.append(Httputils.set_four(minvalue) + "");
                    }
                    Award = Float.valueOf(min.getGfd()) * unit;
                } else {
                    if (oddsbeans.size() == 0) {
                        MenuBean.GameListBean.ListBean.OddsBean min = Selectbean.getOddsBeans().get(0);
                        MenuBean.GameListBean.ListBean.OddsBean max = Selectbean.getOddsBeans().get(0);
                        for (int i = 0; i < Selectbean.getOddsBeans().size(); i++) {
                            float temp = Float.valueOf(Selectbean.getOddsBeans().get(i).getGfd());
                            if (Float.valueOf(min.getGfd()) > temp) {
                                min = Selectbean.getOddsBeans().get(i);
                            }
                            if (Float.valueOf(max.getGfd()) < temp) {
                                max = Selectbean.getOddsBeans().get(i);
                            }
                        }
                        float minvalue = Float.valueOf(min.getGfd()) * unit;
                        float maxvalue = Float.valueOf(max.getGfd()) * unit;
                        if (!min.getGfd().equalsIgnoreCase(max.getGfd())) {
                            rule1.append(Httputils.set_four(minvalue) + " - " + Httputils.set_four(maxvalue));
                        } else {
                            rule1.append(Httputils.set_four(minvalue) + "");
                        }
                        Award = Float.valueOf(min.getGfd()) * unit;
                    } else {
                        Award = Float.valueOf(oddsbeans.get(0).getGfd()) * unit;
                        rule1.append(Httputils.set_four(Award) + "");

                    }
                }
                break;
        }


    }
}
