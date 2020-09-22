package com.lottery.biying.Activity.SmartChooseNumber;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class Fragment2 extends BaseFragment  implements OnClickSmartMainListener,View.OnClickListener {

    RecyclerView listview;
    TextView createplan;
    EditText qs, lowestwin;
    Bundle bundle;
    SmartbetRecycleAdapter smartbetAdapter;
    ArrayList<SmartTraceBean> stbs = new ArrayList<>();
    double Canwinmoney = 200, unit = 1,AwardUnit=1;
    int bettimes,UnitType,times,BounsType;//bettimes 注数 times 倍数
    String Content;
    int AppendQsMax,MultipleMax;//最大期数限制，最大倍数限制

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_smart_layout2,null);

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
            act.setMoney(bettimes + "", "0", "0", "0");
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
        lowestwin = FindView(R.id.lowestwin);
        bundle = getArguments();
        AppendQsMax=Integer.valueOf(bundle.getString(BundleTag.AppendQsMax, "110"));
        MultipleMax=Integer.valueOf(bundle.getString(BundleTag.MultipleMax, "110"));
        unit = bundle.getDouble(BundleTag.Unit, 1);
        bettimes=bundle.getInt(BundleTag.BetTimes, 1);
        Canwinmoney = bundle.getDouble(BundleTag.AWARD, 1);
        UnitType=bundle.getInt(BundleTag.UnitType, 1);
        BounsType=bundle.getInt(BundleTag.BounsType,0);
        Content=bundle.getString(BundleTag.Content, "");
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
        mainview.addView(View.inflate(getActivity(), R.layout.item_smart_bet, null), 2);


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
                    act.setMoney("1", String.format("%.2f", betsmoney), beishu + "", ""+qishu);
                    return stbs;
                }
            });
            listview.setAdapter(smartbetAdapter);
        } else {
            LogTools.e("shuaxin22",stbs.size()+" ");
            smartbetAdapter.NotifysetChange(stbs,0);
        }
    }


    private void RequestData(final int tracecount, final int beishu,final double limited) {
        stbs.clear();
        if(smartbetAdapter!=null)
        smartbetAdapter.clearData(stbs);
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", tracecount + "");
        requestParams.put("lotteryCode", bundle.getString(BundleTag.id));
        requestParams.put("gameCode", bundle.getString(BundleTag.GameCode));
//        requestParams.put("multiple");

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
                int sumtimes = 0;
                int lasttimes = 1;

                for (int i = 0; i < resultList.length(); i++) {
                    JSONObject temp = resultList.optJSONObject(i);
                    SmartTraceBean bean = CaculateData(lasttimes, temp.optString("qsFormat", ""), i, sumtimes, limited);
                    if (bean.getTimes() > MultipleMax) {
                        AlertDialog2("当前最大期数限制为" + AppendQsMax + "期");
                        return;
                    }
                    bean.setQs(temp.optString("qs", ""));
                    bean.setBettimes(bettimes);
                    bean.setBounsType(BounsType);
                    bean.setContent(Content);
                    bean.setUnit(unit);
                    bean.setGameCode(bundle.getString(BundleTag.GameCode));
                    sumtimes = sumtimes + bean.getTimes();
                    stbs.add(bean);
                }
                SetAdapter();
                MainActivity act = (MainActivity) getActivity();
                act.setMoney("1", stbs.get(stbs.size() - 1).getBetsmoney(), beishu + "", tracecount + "");
            }
        });
    }

    private SmartTraceBean CaculateData(int from,String qs,int index,int sumtimes,double limit)
    {
        int beishu=-1;
        for (int i = from; i <MultipleMax ; i++) {
            if((from*Canwinmoney*AwardUnit-from*unit*bettimes-sumtimes*unit*bettimes)/from*unit*bettimes>=limit)
            {
                beishu=i;
                break;
            }
        }
        if(beishu<0)beishu=MultipleMax+1;
        SmartTraceBean stb = new SmartTraceBean();
        stb.setIndex(index + 1);
        stb.setQihao(qs);
        stb.setTimes(beishu);
        double betsmoney = unit * beishu*bettimes+sumtimes*unit*bettimes;
        double winmoney =beishu*Canwinmoney*AwardUnit-beishu*unit*bettimes-sumtimes*unit*bettimes;
        stb.setBetsmoney(String.format("%.2f", betsmoney));
        stb.setWinmoney(String.format("%.2f", winmoney));
        stb.setPercentofwinmoney(String.format("%.0f", winmoney / betsmoney * 100) + "%");
        stb.setSelect(true);
        return stb;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.createplan:

                if(Integer.valueOf(qs.getHint().toString())==0)
                {
                    qs.setText("");
                }

                if(Integer.valueOf(lowestwin.getHint().toString())==0)
                {
                    lowestwin.setText("");
                }
                int beishu = bundle.getInt(BundleTag.Times, 1);
                int qishu = qs.getText().toString().length() < 1 ? Integer.valueOf(qs.getHint().toString()) : Integer.valueOf(qs.getText().toString());
                double limited=lowestwin.getText().toString().length()<1?Integer.valueOf(lowestwin.getHint().toString())/100.00d:Integer.valueOf(lowestwin.getText().toString())/100.00d;
                if(qishu>AppendQsMax)
                {
                    AlertDialog2("当前最大期数限制为"+AppendQsMax+"期");
                    return;
                }

                int sumtimes = 0;
                int lasttimes = 1;
                for (int i = 0; i < qishu; i++) {
                    SmartTraceBean bean = CaculateData(lasttimes, "", i, sumtimes, limited);
                    if (bean.getTimes() > MultipleMax) {
                        AlertDialog2("当前最大倍数限制为" + MultipleMax + "倍");
                        return;
                    }
                    sumtimes = sumtimes + bean.getTimes();
                }

                RequestData(qishu, beishu,limited);


                break;
        }
    }

    @Override
    public void OnClick(int index, int btnindex) {
        if(index==1)
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
