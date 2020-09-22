package com.lottery.biying.Activity.SmartChooseNumber;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.SmartbetAdapter;
import com.lottery.biying.Adapter.SmartbetAdapter3;
import com.lottery.biying.Adapter.SmartbetRecycleAdapter3;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.CaseBean;
import com.lottery.biying.bean.SmartTraceBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.CalculateBetMoney;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.OnBottomChangeListener;
import com.lottery.biying.util.OnClickSmartMainListener;
import com.lottery.biying.util.SmartFollowUntils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.AppAlertDialog;
import com.lottery.biying.view.Keyboard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/8.
 */
public class Fragment3 extends BaseFragment implements OnClickSmartMainListener, View.OnClickListener {


    private EditText fromtimes, periodtimes, multipletimes, follow_times;
    RecyclerView listview;
    TextView createplan;
    SmartbetRecycleAdapter3 smartbetAdapter;
    Bundle bundle;
    double Canwinmoney = 200, unit = 1,AwardUnit=1;
    int bettimes,UnitType,times,BounsType;//bettimes 注数 times 倍数
    ArrayList<SmartTraceBean> stbs = new ArrayList<>();
    String Content;
    int AppendQsMax,MultipleMax;//最大期数限制，最大倍数限制

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_smart_layout3, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitView();
    }

    private void InitView() {
        fromtimes = FindView(R.id.fromtimes);
        periodtimes = FindView(R.id.periodtimes);
        multipletimes = FindView(R.id.multipletimes);
        follow_times = FindView(R.id.follow_times);
        createplan = FindView(R.id.createplan);
        createplan.setOnClickListener(this);


        bundle = getArguments();
        AppendQsMax=Integer.valueOf(bundle.getString(BundleTag.AppendQsMax, "110"));
        MultipleMax=Integer.valueOf(bundle.getString(BundleTag.MultipleMax, "110"));
        BounsType=bundle.getInt(BundleTag.BounsType,0);
        Content=bundle.getString(BundleTag.Content, "");
        unit = bundle.getDouble(BundleTag.Unit, 1);
        Canwinmoney = bundle.getDouble(BundleTag.AWARD, 1);
        bettimes=bundle.getInt(BundleTag.BetTimes, 1);
        UnitType=bundle.getInt(BundleTag.UnitType, 1);
        times=bundle.getInt(BundleTag.Times,1);
        fromtimes.setHint(times + "");
        switch (UnitType)
        {
            case 1:
                AwardUnit=1;
                break;
            case 2:
                AwardUnit=0.1;
                break;
            case 3:
                AwardUnit=0.01;
            case 4:
                AwardUnit=0.001;
                break;
        }
        listview = FindView(R.id.listview);
        listview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listview.setLayoutManager(mLayoutManager);
        LinearLayout mainview=(LinearLayout)getView();
        mainview.addView(View.inflate(getActivity(), R.layout.item_smart_bet3, null), 2);
    }


    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if (getView() != null && !state) {
            MainActivity act = (MainActivity) getActivity();
            act.setMoney(bettimes + "", "0", "0", "0");
            if(smartbetAdapter!=null)
            {
                stbs.clear();
                smartbetAdapter.clearData(stbs);
            }
        }

    }

    private void RequestData(final int tracecount, final int start_beishu, final int period, final int multiple) {
        stbs.clear();
        if(smartbetAdapter!=null)
                    smartbetAdapter.clearData(stbs);
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", tracecount + "");
        requestParams.put("lotteryCode", bundle.getString(BundleTag.id));
        requestParams.put("gameCode", bundle.getString(BundleTag.GameCode));
        int m=1;
        for (int i = start_beishu; i <tracecount/ period; i++) {
            m=m*start_beishu*multiple;
        }
        requestParams.put("multiple",m+"");

        Httputils.PostWithBaseUrl(Httputils.trace, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                    return;
                }
                JSONArray resultList = jsonObject.optJSONObject("datas").optJSONArray("resultList");
                if (resultList.length() < tracecount) return;
                int count_period = 0;
                int indextimes = start_beishu;
                int sum_times = 0;
                double sum_betmoney = 0;
                for (int i = 0; i < resultList.length(); i++) {
                    JSONObject temp = resultList.optJSONObject(i);
                    SmartTraceBean stb = new SmartTraceBean();
                    stb.setIndex(i + 1);
                    stb.setQs(temp.optString("qs", ""));
                    stb.setQihao(temp.optString("qsFormat", ""));
                    stb.setBettimes(bettimes);
                    stb.setBounsType(BounsType);
                    stb.setContent(Content);
                    stb.setUnit(unit);
                    stb.setGameCode(bundle.getString(BundleTag.GameCode));
                    if (count_period == period) {
                        count_period = 0;
                        indextimes = indextimes * multiple;
                        sum_times = sum_times + indextimes;
                        stb.setTimes(indextimes);
                        double betsmoney = unit * indextimes * bettimes;
                        sum_betmoney = sum_betmoney + betsmoney;
                        double winmoney = stb.getTimes() * Canwinmoney * AwardUnit - sum_betmoney;
                        stb.setBetsmoney(String.format("%.2f", sum_betmoney));
                        stb.setWinmoney(String.format("%.2f", winmoney));
                        stb.setPercentofwinmoney(String.format("%.0f", winmoney / sum_betmoney * 100) + "%");
                        stb.setSelect(true);
                        stbs.add(stb);
                    } else {
                        sum_times = sum_times + indextimes;
                        stb.setTimes(indextimes);
                        double betsmoney = unit * indextimes * bettimes;
                        sum_betmoney = sum_betmoney + betsmoney;
                        double winmoney = stb.getTimes() * Canwinmoney * AwardUnit - sum_betmoney;
                        stb.setBetsmoney(String.format("%.2f", sum_betmoney));
                        stb.setWinmoney(String.format("%.2f", winmoney));
                        stb.setPercentofwinmoney(String.format("%.0f", winmoney / sum_betmoney * 100) + "%");
                        stb.setSelect(true);
                        stbs.add(stb);
                    }
                    count_period++;


                }
                SetAdapter();
                MainActivity act = (MainActivity) getActivity();
                act.setMoney("1", stbs.get(stbs.size() - 1).getBetsmoney(), sum_times + "", tracecount + "");
            }
        });
    }

    private void SetAdapter() {
        setShareObj(stbs);
        if (smartbetAdapter == null) {
            smartbetAdapter = new SmartbetRecycleAdapter3(getActivity(), stbs);
            smartbetAdapter.setOnchangelistener(new SmartbetRecycleAdapter3.OnChangeListener() {
                @Override
                public ArrayList<SmartTraceBean> onChange(int position) {
                    ArrayList<SmartTraceBean> m_stbs = smartbetAdapter.getCasebeans();
                    double betsmoney = 0;
                    int beishu = 0;
                    int qishu = 0;
                    for (int i = 0; i < m_stbs.size(); i++) {
                        SmartTraceBean stb = m_stbs.get(i);
                        if (stb.isSelect()) {
                            betsmoney = betsmoney + stb.getTimes() * unit*bettimes;
                            beishu = beishu + stb.getTimes();
                            qishu++;
                            double winmoney = stb.getTimes() * Canwinmoney*AwardUnit - betsmoney;
                            m_stbs.get(i).setWinmoney(String.format("%.2f", winmoney));
                            m_stbs.get(i).setBetsmoney(String.format("%.2f", betsmoney));
                            m_stbs.get(i).setPercentofwinmoney(String.format("%.0f", winmoney / betsmoney*100) + "%");
                        }
                    }
                    stbs = m_stbs;
                    setShareObj(stbs);
                    MainActivity act = (MainActivity) getActivity();
                    act.setMoney("1", String.format("%.2f", betsmoney), beishu + "", "" + qishu);
                    return stbs;
                }
            });
            listview.setAdapter(smartbetAdapter);
        } else {
            LogTools.e("shuaxin333",stbs.size()+" ");
            smartbetAdapter.NotifysetChange(stbs,0);
        }
    }

    private void DismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void OnClick(int index, int btnindex) {

        if(index==2)
        {
            if(btnindex==0)
            {
                stbs.clear();
                if(smartbetAdapter!=null)
                smartbetAdapter.clearData(stbs);
                MainActivity act=(MainActivity)getActivity();
                act.setMoney(bettimes + "", "0", "0", "0");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createplan:
                if(follow_times.getText().toString().length()>0 && Integer.valueOf(follow_times.getText().toString())==0)
                {
                    follow_times.setText("");
                }
                if(fromtimes.getText().toString().length()>0 && Integer.valueOf(fromtimes.getText().toString())==0)
                {
                    fromtimes.setText("");
                }
                if(periodtimes.getText().toString().length()>0 && Integer.valueOf(periodtimes.getText().toString())==0)
                {
                    periodtimes.setText("");
                }
                if(multipletimes.getText().toString().length()>0 && Integer.valueOf(multipletimes.getText().toString())==0)
                {
                    multipletimes.setText("");
                }
                int qishu = follow_times.getText().toString().length() < 1 ? Integer.valueOf(follow_times.getHint().toString()) :
                        Integer.valueOf(follow_times.getText().toString());
                int start_beishu = fromtimes.getText().toString().length() < 1 ? Integer.valueOf(fromtimes.getHint().toString()) :
                        Integer.valueOf(fromtimes.getText().toString());
                int period=periodtimes.getText().toString().length() < 1 ? Integer.valueOf(periodtimes.getHint().toString()) :
                        Integer.valueOf(periodtimes.getText().toString());
                int multiple=multipletimes.getText().toString().length() < 1 ? Integer.valueOf(multipletimes.getHint().toString()) :
                        Integer.valueOf(multipletimes.getText().toString());

                if(qishu>AppendQsMax)
                {
                    AlertDialog2("当前最大期数限制为"+AppendQsMax+"期");
                    return;
                }
                int m=1;
                for (int i = start_beishu; i <qishu/ period; i++) {
                    m=m*start_beishu*multiple;
                }
                LogTools.e("mmm",m+"");
                if(m<0 || m>MultipleMax)//数据太大 超出int 会变成负数
                {
                    AlertDialog2("当前最大倍数限制为"+MultipleMax+"倍");
                    return;
                }
                RequestData(qishu, start_beishu,period,multiple);
                break;
        }
    }

    private void AlertDialog2(String Content) {
        AppAlertDialog dialog = new AppAlertDialog(getActivity());
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
    }
}
