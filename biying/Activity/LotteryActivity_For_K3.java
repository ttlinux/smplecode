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
import android.view.Gravity;
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

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.RunChart.RunChartActivity;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.LotteryMethod.AnalysisType;
import com.lottery.biying.LotteryMethod.CQSSC.RenXuan;
import com.lottery.biying.LotteryMethod.K3.K3;
import com.lottery.biying.R;
import com.lottery.biying.bean.IndexRecordBean;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.bean.LotteryTimebean;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.bean.MoneyTypeBean;
import com.lottery.biying.util.BitmapHandler;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.view.DiceView;
import com.lottery.biying.util.HandleLotteryOrder;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.TimeMethod;
import com.lottery.biying.util.TimeUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AppAlertDialog;
import com.lottery.biying.view.ContentScrollView;
import com.lottery.biying.view.LotteryPopWindows;
import com.lottery.biying.view.LotterySiderDialog;
import com.lottery.biying.view.Lottery_RandomNumber_Dialog;
import com.lottery.biying.view.ScrollLayout;
import com.lottery.biying.view.TipsDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */
public class LotteryActivity_For_K3 extends BaseActivity implements View.OnClickListener, SensorEventListener {

    final String HZDXDS = "dxds_dxds_hzdxds";
    final String HWDXDS = "dxds_dxds_hwdxds";
    final String HeZhi = "hz_hz_hz";
    final String DTYS = "ds_ds_dtys";
    final String EBT = "es_es_ebt";
    final String ETH = "es_es_eth";
    final String SLH = "ss_ss_slh";
    final String SBT = "ss_ss_sbt";
    final String STH = "ss_ss_sth";
    RelativeLayout lottery_view;
    public View temp2;
    public TextView playtips;
    ScrollLayout historylist;
    ContentScrollView contentview;
    TextView backtitle,title;
    EditText multiple;
    PopupWindow popupWindow;
    LotteryPopWindows lotteryPopWindows;
    MenuBean.GameListBean.ListBean Selectbean;
    Drawable normal, upsidedown;
    LinearLayout contentmainview;
    SensorManager mSensorManager;
    Sensor mSensor;
    RadioGroup money_btns;

    public static ArrayList<ArrayList<DiceView>> DiceViews = new ArrayList<>();
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
    boolean isshowyilou, isOpenYaoyiyao;
    String tipsstr, example, lotterycode;
    //    HashMap<String, DiceView> Selectednumbers = new HashMap<>();//记录选中了的号码
    String functionbtns[], moneytype[];
    RadioGroup Selectorswitch;
    LinearLayout weishu_layout;
    MoneyTypeBean current_mt_bean;
    double Award = 0;
    LotteryTimebean timebean;
    int DiceMode = -1;
    ImageView assistant_icon, assistant_icon2;
    CheckBox checkBox_yilou;
    MenuBean mbean;
    int RollBackDistance;
    ArrayList<MenuBean.GameListBean.ListBean.OddsBean> oddsbeans=new ArrayList<>();

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
        DiceViews.clear();
        K3.getInstance().getSelectNumbers().clear();
        TimeMethod.End();
    }

    private void Initview() {
        RollBackDistance=ScreenUtils.getDIP2PX(LotteryActivity_For_K3.this, 30);
        lotterycode = getIntent().getStringExtra(BundleTag.id);
        AnalysisType.setCurrentLottery(getIntent().getStringExtra(BundleTag.id));
        functionbtns = getResources().getStringArray(R.array.functionbtns);
        moneytype = getResources().getStringArray(R.array.moneytype);
        sharep = getSharedPreferences(BundleTag.APP, Activity.MODE_PRIVATE);
        isshowyilou = sharep.getBoolean(BundleTag.IsShowYilou, true);
//        Showmoretitles[3] = !isshowyilou ? "显示遗漏" : "隐藏遗漏";
//        Showmoretitles[4] = !isOpenYaoyiyao ? "开启摇一摇" : "关闭摇一摇";
        isOpenYaoyiyao = sharep.getBoolean(BundleTag.isOpenYaoyiyao, true);

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
                        int pad = ScreenUtils.getDIP2PX(LotteryActivity_For_K3.this, 10);
                        buttonView.setPadding(pad, 0, pad, 0);
                        buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                        Caculatebets();
                    } else {
                        buttonView.setGravity(Gravity.CENTER);
                        buttonView.setCompoundDrawablePadding(0);
                        buttonView.setPadding(0, 0, 0, 0);
                        buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    }
                }
            });
        }
        checkBox_yilou=FindView(R.id.checkBox_yilou);
        checkBox_yilou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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
        title=FindView(R.id.title);
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
                    historylist.setExitOffset(ScreenUtils.getDIP2PX(LotteryActivity_For_K3.this, 10));
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

                }
            });
            AnalysisType.setCurrentLotteryMainCode(Selectbean.getTitleCode());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtitle:
                if (popupWindow != null) {
                    if (!popupWindow.isShowing()) {
                        upsidedown.setBounds(0, 0, normal.getMinimumWidth(), normal.getMinimumHeight());
                        backtitle.setCompoundDrawables(null, null, upsidedown, null);
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
                        popupWindow.dismiss();
                        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, normal, null);
                    }
                }
                break;
            case R.id.yaoyiyao:
                Radomnumbers();
                break;
            case R.id.assistant_icon:
                LotterySiderDialog ldialog=new LotterySiderDialog(this,mbean.getMenuCode(),mbean.getMenuName());
                ldialog.show();
                break;
            case R.id.assistant_icon2:
                StartActivityOrderCommit(-1);
                break;
            case R.id.confirm:
                int resultcode = authOrder();
                if (resultcode == 0) {
                    Radomnumbers();
                }
                if (resultcode == 2) {
                    if(StartActivityOrderCommit(0))
                    ClearSelectnumber(true);
                }

                break;
            case R.id.jixuan:
                if (K3.getInstance().getSelectNumbers().size() > 0) {
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

    public void ClearSelectnumber(boolean isClearstatusonly) {
        for (int i = 0; i < DiceViews.size(); i++) {
            ArrayList<DiceView> btns = K3.getInstance().getSelectNumbers().get(i);
            if (btns != null) {
                LogTools.e("ccc", btns.size() + "");
                for (int j = 0; j < btns.size(); j++) {
                    btns.get(j).setCheck(false);
                }
            }
        }
        if (!isClearstatusonly)
            K3.getInstance().getSelectNumbers().clear();
        bottomview1.setText(getResources().getString(R.string.randomchoose));
        bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
        bottomview2.setTag(null);
        oddsbeans.clear();
        setRewardOnClickbtn(null, false);
    }

    public void requestdataNum() {
        if (Selectbean == null) return;
        RenXuan.isNeedSpecialView(Selectbean.getGameCode(), weishu_layout);
        bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
        bottomview1.setText(getResources().getString(R.string.randomchoose));
        DiceViews.clear();

        while (contentmainview.getChildCount() > 3) {
            contentmainview.removeViewAt(3);
        }
        int linecount = GetlineCount(Selectbean.getGameCode());
        int oddstype=Selectbean.getOddsType();
        for (int i = 0; i < Selectbean.getBallbeans().size(); i++) {
            MenuBean.GameListBean.ListBean.Ballbean ballbean = Selectbean.getBallbeans().get(i);
            JSONObject nums[] = ballbean.getBallnumber();
            int MesureWidth = 0;
            int cal = 0;
            ArrayList<DiceView> bs = null;
            LinearLayout linearLayout = null;
            for (int j = 0; j < nums.length; j++) {
                if (cal % linecount == 0) {
                    bs = new ArrayList<DiceView>();
                    linearLayout = new LinearLayout(this);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                }
                int margin = ScreenUtils.getDIP2PX(this, 10);
                DiceView diceView = new DiceView(this);
                IndexRecordBean irb = new IndexRecordBean(j / linecount, j % linecount);
                irb.setTitle(ballbean.getCode());
                if(oddstype==1)
                {
                    //点了会变的赔率情况
                    String hm=   nums[j].optString("hm");
                    for (int t = 0; t <Selectbean.getOddsBeans().size() ; t++) {
                        if(hm.equalsIgnoreCase(Selectbean.getOddsBeans().get(t).getHm()))
                        {
                            irb.setOddsBean(Selectbean.getOddsBeans().get(t));
                            break;
                        }
                    }
                }
                diceView.setTag(irb);
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.leftMargin = margin;
                ll.rightMargin = margin;
                ll.topMargin = margin;
                diceView.setLayoutParams(ll);
                diceView.setModeAndArgs(DiceMode, K3.getArgs(Selectbean.getGameCode(), nums[j].optString("hm")));
                diceView.setContent(nums[j].optString("sm", ""));
                diceView.setValue(nums[j].optString("hm"));
                diceView.setOnCheckedListener(new DiceView.OnCheckedListener() {
                    @Override
                    public void onChecked(boolean checked, DiceView view) {
                        if (view.getTag() != null && Selectbean.getOddsType()==1) {
                            IndexRecordBean irb = (IndexRecordBean) view.getTag();
                            setRewardOnClickbtn(irb,checked);
                        }
                        Caculatebets();
                    }
                });
                linearLayout.addView(diceView);
                bs.add(diceView);
                cal++;
                if ((cal % linecount == 0 || cal == nums.length) && bs != null) {
                    DiceViews.add(bs);
                    if (MesureWidth == 0) {
                        //补丁方案 左对齐
                        linearLayout.measure(0, 0);
                        MesureWidth = linearLayout.getMeasuredWidth();
                    }
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    layoutParams.leftMargin = (ScreenUtils.getScreenWH(this)[0] - MesureWidth) / 2;
                    linearLayout.setLayoutParams(layoutParams);
                    contentmainview.addView(linearLayout);
                }
            }


        }

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
        if (DiceViews.size() == 0) {
            LogTools.e("DiceViews is 0", "DiceViews is 0");
            return false;
        }
        ClearSelectnumber(false);

        bottomview1.setText(getResources().getString(R.string.clearnumber));
        K3.getInstance().RandomNumbers(DiceViews, Selectbean.getGameCode());
        Caculatebets();

        if ( Selectbean.getOddsType()==1) {
            oddsbeans.clear();
            for (int i = 0; i <DiceViews.size() ; i++) {
                for (int k = 0; k <DiceViews.get(i).size() ; k++) {
                    DiceView bn=DiceViews.get(i).get(k);
                    if(bn.getCheck())
                    {
                        IndexRecordBean irb = (IndexRecordBean) bn.getTag();
                        if(irb.getOddsBean()!=null)
                            oddsbeans.add(irb.getOddsBean());
                    }
                }
            }
            setRewardOnClickbtn(null, false);
        }
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
                int indexlist[] = {1, 5, 10};
                if (StartActivityOrderCommit(indexlist[index])) {
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
                LogTools.e("Getlotteryresult", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(LotteryActivity_For_K3.this, jsonObject.optString("msg"));
                    return;
                }
                RefreshResultInterFace(jsonObject);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    private void RefreshResultInterFace(JSONObject jsonObject) {
        JSONObject datas = jsonObject.optJSONObject("datas");

//        List<List<LotteryResultList.YiLou>> yiLous_main = new ArrayList<>();
//        JSONObject yl = datas.optJSONObject("yl");
//        String tags[] = {"ww", "qw", "bw", "sw", "gw"};
//        for (int i = 0; i < tags.length; i++) {
//            JSONArray tag_arr = yl.optJSONArray(tags[i]);
//            List<LotteryResultList.YiLou> yiLous = new ArrayList<>();
//            for (int j = 0; j < tag_arr.length(); j++) {
//                LotteryResultList.YiLou yilou = LotteryResultList.YiLou.AnalysisData(tag_arr.optJSONObject(j));
//                yiLous.add(yilou);
//            }
//            yiLous_main.add(yiLous);
//        }
//        //更新号码遗漏
//        UpdateYilou(yiLous_main);
//        //更新号码遗漏

        JSONArray resultList = datas.optJSONArray("openResult");
        //定死10条不够就不显示
        LotteryResultList lbean=null;
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
            if(Selectbean!=null && Selectbean.getGameCode()!=null)
                bean.setGameCode(Selectbean.getGameCode());

            JSONArray openResult = jsonobj.optJSONArray("openResult");
            if(openResult==null)
            {
                //不知道什么情况 字段没了
                return;
            }
            if (openResult.length() > 0) {
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
                AnalysisType.Inititem(bean, color, LotteryActivity_For_K3.this, view);
            } else {
                view = AnalysisType.Inititem(bean, color, LotteryActivity_For_K3.this, view);
                lottery_list.addView(view);
            }
            if (view != null)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LotteryActivity_For_K3.this, RunChartActivity.class);
                        intent.putExtra(BundleTag.Index, 1);
                        intent.putExtra(BundleTag.id, getIntent().getStringExtra(BundleTag.id));
                        startActivity(intent);
                    }
                });

            lbean=bean;
        }
        if(lottery_list.getTag()==null)
        {
            TextView textView=new TextView(this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtils.getDIP2PX(this,30)));
            textView.setText(getString(R.string.toasts));
            textView.setGravity(Gravity.CENTER);
            lottery_list.addView(textView);
        }
        if(lbean!=null)
        {
            if(lbean.getType()!=0)
            AnalysisType.setLottertCategory(lbean.getType());
            AnalysisType.ModifyTitleItem((RelativeLayout) temp2, lbean.getType(),lbean.getGameCode());
        }

        lottery_list.setTag("DONE");

    }

    public int authOrder() {
        String atleasechooseone = getResources().getString(R.string.atleasechooseone);
        String plzcnumber = getResources().getString(R.string.plzcnumber);
        int showdialog = 2;
        //0 一个号码都没选 1 没选齐号码 2 直接跳转 3 选号注数大于10000
        if (K3.getInstance().getSelectNumbers().size() == 0) {
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
                LogTools.ee("detail", jsonObject.toString());
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
                        lbean.setTitleCode(glbean.getTitleCode());
                        lbean.setOddsBeans(MenuBean.Handlder_Json_Odds(jsonobj.optJSONArray("oddsArr")));
                        lbean.setOddsType(jsonobj.optInt("oddsType", 0));
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
                    view.setOnCheckedChangeListener(
                            new CompoundButton.OnCheckedChangeListener() {
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
        int zhu = K3.getInstance().CaculateOrderAmount(DiceViews, Selectbean.getGameCode());
        if (zhu > 0) {
            bottomview2.setTag(zhu);
            bottomview2.setText(HandleLotteryOrder.GetStr(zhu, CaculateBetsMoney()));
        } else {
            bottomview2.setText(HandleLotteryOrder.GetStr("共0注 0元"));
            bottomview2.setTag(null);
        }
        if (K3.getInstance().getSelectNumbers().size() == 0) {
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

    private void GetTime() {
        timebean = null;
        TimeMethod.End();
        RequestParams requestParams = new RequestParams();
        requestParams.put("lotteryCode", lotterycode);
        Httputils.PostWithBaseUrl(Httputils.DTime, requestParams, new MyJsonHttpResponseHandler(this, false) {
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
                timebean = ltbean;

                /*倒计时*/


                TimeMethod.setOnTimeChangeListener(new TimeMethod.OnTimeChangeListener() {
                    @Override
                    public void OnChange(int CloseLimitime, int LotteryStatus, String qsstr) {

                        timeview1.setText("距离" + qsstr + "期截止: ");
                        timeview2.setText(TimeUtils.ConverTime(CloseLimitime));
                        if (CloseLimitime == 0) {
                            AlertDialog2("第" + qsstr + "期已截止");
                        }
                        if (CloseLimitime == -1) {
                            timeview1.setText(getString(R.string.requestingqihao));
                            timeview2.setText("");
                            Getlotteryresult();
                            GetTime();
                        }
                    }
                }, LotteryActivity_For_K3.this, ltbean.getQsFormat());
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

        /*高奖高返*/
        if (Selectbean.getBonuses().size() == 1) {
            Selectorswitch.getLayoutParams().width = Selectorswitch.getMeasuredWidth();
            Selectorswitch.getChildAt(1).setVisibility(View.GONE);
            RadioButton radiobtn = (RadioButton) Selectorswitch.getChildAt(0);
            radiobtn.setBackground(getResources().getDrawable(R.drawable.radio_lottery_selector));
        }
        for (int bo = 0; bo < Selectbean.getBonuses().size(); bo++) {
            RadioButton radiobtn = (RadioButton) Selectorswitch.getChildAt(Selectbean.getBonuses().get(bo).getBonusType());
            radiobtn.setText(Selectbean.getBonuses().get(bo).getBonusName());
            radiobtn.setVisibility(View.VISIBLE);

            radiobtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setRewardOnClickbtn(null, false);
                }
            });
            radiobtn.setTag(Selectbean.getBonuses().get(bo));
            if (Selectbean.getBonuses().get(bo).getSelect().equalsIgnoreCase("1")&& Award==0) {
                radiobtn.setChecked(true);
            }
        }
        oddsbeans.clear();
        setRewardOnClickbtn(null, false);
    }


    private int GetlineCount(String gamecode) {
        //shk3_dxds_dxds_hzdxds  shk3_dxds_dxds_hwdxds

        if (gamecode.contains(HZDXDS) || gamecode.contains(HWDXDS)) {
            DiceMode = DiceView.Char;
            return 2;
        }
        if (gamecode.contains(HeZhi)) {
            DiceMode = DiceView.Char;
            return 4;
        }
        if (gamecode.contains(DTYS)) {
            DiceMode = DiceView.Dice;
            return 4;
        }
        if (gamecode.contains(EBT)) {
            DiceMode = DiceView.Dice;
            return 3;
        }
        if (gamecode.contains(ETH)) {
            DiceMode = DiceView.Dice;
            return 2;
        }
        if (gamecode.contains(SLH) || gamecode.contains(STH) || gamecode.contains(SBT)) {
            DiceMode = DiceView.Dice;
            return 2;
        }
        return 0;
    }

    private boolean StartActivityOrderCommit(int type) {
        // type 0确认进来的 -1进入购物车 >0 随机投注
        if (current_mt_bean == null) return false;
        if (timebean == null) {
            ToastUtil.showMessage(this, "未获取到期数");
            return false;
        }
        if (multiple.getText().toString().length() > 1 && Selectbean.getMultipleMax().length()>0) {
            int beishu=Integer.valueOf(multiple.getText().toString());
            if(beishu>Integer.valueOf(Selectbean.getMultipleMax()))
            {
                AlertDialog2("当前最大倍数限制为"+Selectbean.getMultipleMax()+"倍");
                return false;
            }
        }
        Intent intent = new Intent();
        intent.setClass(LotteryActivity_For_K3.this, OrderCofirmActivity.class);
        intent.putExtra(BundleTag.id, getIntent().getStringExtra(BundleTag.id));
        intent.putExtra(BundleTag.TitleCode, Selectbean.getTitleCode());
        intent.putExtra(BundleTag.QS, timebean.getQs());
        intent.putExtra(BundleTag.title, backtitle.getText().toString());
        if (bottomview2.getTag() != null)
            intent.putExtra(BundleTag.BetTimes, (int) bottomview2.getTag());
        intent.putExtra(BundleTag.GameCode, Selectbean.getGameCode());
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
