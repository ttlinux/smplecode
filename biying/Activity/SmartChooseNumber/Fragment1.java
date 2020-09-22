package com.lottery.biying.Activity.SmartChooseNumber;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.SmartbetAdapter;
import com.lottery.biying.Adapter.SmartbetRecycleAdapter;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/8.
 */
public class Fragment1 extends BaseFragment implements View.OnClickListener,OnClickSmartMainListener {

    RecyclerView listview;
    TextView createplan;
    EditText qs, bs;
    Bundle bundle;
    SmartbetRecycleAdapter smartbetAdapter;
    ArrayList<SmartTraceBean> stbs = new ArrayList<>();
    double Canwinmoney = 200, unit = 1,AwardUnit=1;
    int bettimes,UnitType,times,BounsType;//bettimes 注数 times 倍数
    int AppendQsMax,MultipleMax;//最大期数限制，最大倍数限制
    String Content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_smart_layout1, null);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(getView()!=null && !state)
        {
            MainActivity act=(MainActivity)getActivity();
            act.setMoney("1", "0", "0", "0");
            if(smartbetAdapter!=null)
            {
                stbs.clear();
                smartbetAdapter.clearData(stbs);
            }
        }



    }

    public void Initview() {
        createplan = FindView(R.id.createplan);
        createplan.setOnClickListener(this);
        qs = FindView(R.id.qs);
        bs = FindView(R.id.bs);
        bundle = getArguments();
        AppendQsMax=Integer.valueOf(bundle.getString(BundleTag.AppendQsMax, "110"));
        MultipleMax=Integer.valueOf(bundle.getString(BundleTag.MultipleMax, "110"));
        unit = bundle.getDouble(BundleTag.Unit, 1);
        bettimes=bundle.getInt(BundleTag.BetTimes, 1);
        Canwinmoney = bundle.getDouble(BundleTag.AWARD, 1);
        UnitType=bundle.getInt(BundleTag.UnitType, 1);
        times=bundle.getInt(BundleTag.Times,1);
        BounsType=bundle.getInt(BundleTag.BounsType,0);
        bs.setHint(times + "");

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
        Content=bundle.getString(BundleTag.Content, "");
        listview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listview.setLayoutManager(mLayoutManager);
        LinearLayout mainview = (LinearLayout)getView();
        mainview.addView(View.inflate(getActivity(), R.layout.item_smart_bet, null), 2);


        MainActivity act=(MainActivity)getActivity();
        act.setMoney(bettimes+"", "0", times + "", "0");

    }


    private void SetAdapter() {
        setShareObj(stbs);
        if (smartbetAdapter == null) {
            smartbetAdapter = new SmartbetRecycleAdapter(getActivity(), stbs);
            smartbetAdapter.setOnchangelistener(new SmartbetRecycleAdapter.OnChangeListener() {
                @Override
                public ArrayList<SmartTraceBean> onChange(int position) {
                    ArrayList<SmartTraceBean> m_stbs = smartbetAdapter.getCasebeans();
                    double betsmoney = 0;
                    int beishu=0;
                    int qishu=0;
                    for (int i = 0; i < m_stbs.size(); i++) {
                        SmartTraceBean stb = m_stbs.get(i);
                        if (stb.isSelect()) {
                            betsmoney = betsmoney + stb.getTimes() * unit*bettimes;
                            beishu=beishu+stb.getTimes();
                            qishu++;
                            double winmoney =  stb.getTimes()*Canwinmoney*AwardUnit - betsmoney;
                            m_stbs.get(i).setWinmoney(String.format("%.2f", winmoney));
                            m_stbs.get(i).setBetsmoney(String.format("%.2f", betsmoney));
                            m_stbs.get(i).setPercentofwinmoney(String.format("%.0f", winmoney / betsmoney*100) + "%");
                        }
                    }
                    stbs = m_stbs;
                    setShareObj(stbs);
                    MainActivity act=(MainActivity)getActivity();
                    int sbeishu = bs.getText().toString().length() < 1 ? 1 : Integer.valueOf(bs.getText().toString());
                    act.setMoney("1", String.format("%.2f", betsmoney), sbeishu + "", ""+qishu);
                    return stbs;
                }
            });
            listview.setAdapter(smartbetAdapter);
        } else {
            LogTools.e("shuaxin111",stbs.size()+" ");
            smartbetAdapter.NotifysetChange(stbs,0);
        }
    }


    private void RequestData(final int tracecount, final int beishu) {
        stbs.clear();
        if(smartbetAdapter!=null)
        smartbetAdapter.clearData(stbs);
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", tracecount + "");
        requestParams.put("lotteryCode", bundle.getString(BundleTag.id));
        requestParams.put("gameCode", bundle.getString(BundleTag.GameCode));
        requestParams.put("multiple",beishu+"");

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
                for (int i = 0; i < resultList.length(); i++) {
                    JSONObject temp = resultList.optJSONObject(i);
                    SmartTraceBean stb = new SmartTraceBean();
                    stb.setGameCode(bundle.getString(BundleTag.GameCode));
                    stb.setUnit(unit);
                    stb.setIndex(i + 1);
                    stb.setQihao(temp.optString("qsFormat", ""));
                    stb.setQs(temp.optString("qs", ""));
                    stb.setTimes(beishu);
                    stb.setBettimes(bettimes);
                    stb.setBounsType(BounsType);
                    stb.setContent(Content);
                    double betsmoney = unit * (i + 1) * bettimes * beishu;
                    double winmoney = beishu * Canwinmoney * AwardUnit - betsmoney;
                    stb.setBetsmoney(String.format("%.2f", betsmoney));
                    stb.setWinmoney(String.format("%.2f", winmoney));
                    stb.setPercentofwinmoney(String.format("%.0f", winmoney / betsmoney * 100) + "%");
                    stb.setSelect(true);
                    stbs.add(stb);
                }
                SetAdapter();
                MainActivity act = (MainActivity) getActivity();
                act.setMoney("1", stbs.get(stbs.size() - 1).getBetsmoney(), beishu + "", tracecount + "");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.createplan:

                if(qs.getText().toString().length()>0 && Integer.valueOf(qs.getText().toString())==0)
                {
                    qs.setText("");
                }

                if(bs.getText().toString().length()>0 && Integer.valueOf(bs.getText().toString())==0)
                {
                    bs.setText("");
                }
                int qishu = qs.getText().toString().length() < 1 ? Integer.valueOf(qs.getHint().toString()) : Integer.valueOf(qs.getText().toString());
                int beishu = bs.getText().toString().length() < 1 ? Integer.valueOf(bs.getHint().toString()) : Integer.valueOf(bs.getText().toString());
                if(qishu>AppendQsMax)
                {
                    AlertDialog2("当前最大期数限制为"+AppendQsMax+"期");
                    return;
                }
                if(beishu>MultipleMax)
                {
                    AlertDialog2("当前最大倍数限制为"+MultipleMax+"倍");
                    return;
                }
                RequestData(qishu,beishu);
                break;
        }
    }

    @Override
    public void OnClick(int index, int btnindex) {
        if(index==0) {
            if(btnindex==0)
            {
                stbs.clear();
                if(smartbetAdapter!=null)
                smartbetAdapter.clearData(stbs);
                MainActivity act=(MainActivity)getActivity();
                int beishu = bs.getText().toString().length() < 1 ? 1 : Integer.valueOf(bs.getText().toString());
                act.setMoney("1", "0", beishu+"", "0");
            }
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
