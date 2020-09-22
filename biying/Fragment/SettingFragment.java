package com.lottery.biying.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.AccountManage.LotteryInstrution.LotteryInstrutionActivity;
import com.lottery.biying.Activity.User.AccountManage.Password.ModifyPassword;
import com.lottery.biying.Activity.User.AccountManage.PersonalData.PersonalDataActivity;
import com.lottery.biying.Activity.User.Change.ChangeActivity;
import com.lottery.biying.Activity.User.CompletePerSonalData;
import com.lottery.biying.Activity.User.CustomerActivity;
import com.lottery.biying.Activity.User.ExternalModule.TodayIncomeDetailActivity;
import com.lottery.biying.Activity.User.LoginActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.ChangeRecordActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer.LotteryAccountTracsferListActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.PerSonalIncomePreViewActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.PersonalIncomeActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.WithdrawAndDepositRecord.WADActivity;
import com.lottery.biying.Activity.User.Message.MessageListActivity;
import com.lottery.biying.Activity.User.Proxy.GeneralizeLink.GeneralizeLinkActivity;
import com.lottery.biying.Activity.User.Proxy.RegistSubordinate;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.SalaryManageActivity;
import com.lottery.biying.Activity.User.Proxy.TeamChartActivity;
import com.lottery.biying.Activity.User.Proxy.TeamIncomeActivity;
import com.lottery.biying.Activity.User.Proxy.TeamManage.TeamManageActivity;
import com.lottery.biying.Activity.User.AccountManage.BankCardManage.BankListActivity;
import com.lottery.biying.Activity.User.Record.OrderHistoryActivity;
import com.lottery.biying.Activity.User.Record.OtherGame.OtherGameRecordListActivity;
import com.lottery.biying.Activity.User.Record.TraceHistoryActivity;
import com.lottery.biying.Activity.User.RegActivity;
import com.lottery.biying.Activity.User.TopUp.PayActivitty;
import com.lottery.biying.Activity.User.WithDraw.WithDrawActivity;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.HttpforNoticeinbottom;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Administrator on 2017/11/25.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout mainlayout;
    int densityDpi;
    ImageLoader imageLoader;
    LinearLayout toplayout2;
    TextView username,usertype,balance,nickname,grouptype;
    ImageView personal_icon,setting;
    RelativeLayout messages;
    TextView login,register;
    LinearLayout nologinlayout,loginlayout;
    SharedPreferences UserInfoshare;
    boolean ClickLogin=false;

    int Refreshtime=60;//固定刷新时间 服务器发过来的
    Handler handler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what)
            {
                case 0:
                    String username=RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername();
                    if(username!=null && username.length()>0)
                    {
                        RefreshBalanceAndMessage();
                        sendEmptyMessageDelayed(0,Refreshtime*1000);
                    }
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        return View.inflate(getActivity(), R.layout.fragment_user, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        LogTools.e("OnViewShowOrHide", state + "");
        if(getActivity()!=null && !state)
        {
            String username=RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername();

            if(username!=null && username.length()>0)
            {
                nologinlayout.setVisibility(View.GONE);
                loginlayout.setVisibility(View.VISIBLE);
                messages.setVisibility(View.VISIBLE);
                HandleLocalData();
                if(ClickLogin)
                {
                    GetModelData();
                    ClickLogin=false;
                }
                getuserinfo(username);
                handler.sendEmptyMessageDelayed(0,1000);
            }
            else
            {
                UserInfoshare.edit().clear().commit();
                nologinlayout.setVisibility(View.VISIBLE);
                loginlayout.setVisibility(View.GONE);
                messages.setVisibility(View.GONE);
            }
        }
        if(getActivity()!=null && state)
        {
            handler.removeMessages(0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }

    public void initview() {
        UserInfoshare = RepluginMethod.getHostApplication(getActivity()).getSharedPreferences(BundleTag.Lottery, Activity.MODE_PRIVATE);
        nologinlayout=FindView(R.id.nologinlayout);
        loginlayout=FindView(R.id.loginlayout);
        login=FindView(R.id.login);
        register=FindView(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        messages=FindView(R.id.messages);
        messages.setOnClickListener(this);
        setting=FindView(R.id.setting);
        setting.setOnClickListener(this);
        toplayout2 = FindView(R.id.toplayout2);
        personal_icon=FindView(R.id.personal_icon);
        username = FindView(R.id.username);
        usertype = FindView(R.id.usertype);
        balance = FindView(R.id.balance);
        nickname = FindView(R.id.nickname);
        grouptype = FindView(R.id.grouptype);
        toplayout2.setOnClickListener(this);
        mainlayout = FindView(R.id.mainlayout);
        imageLoader = RepluginMethod.getApplication(getActivity()).getImageLoader();
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        GetModelData();
    }

    public void GetModelData() {

        RequestParams requestParams = new RequestParams();
        Httputils.PostWithBaseUrl(Httputils.UserFragment_Model, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("GetModelData",jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                JSONObject datas = jsonObject.optJSONObject("datas");

                Refreshtime=datas.optInt("refreshTime",60);
                while (mainlayout.getChildCount()>1)
                {
                    mainlayout.removeViewAt(1);
                }
                JSONArray fundsList = datas.optJSONArray("fundsList");
                Model1(fundsList);

                JSONArray menuList = datas.optJSONArray("menuList");
                for (int i = 0; i < menuList.length(); i++) {
                    Model_Normal(menuList.optJSONObject(i));
                }

                JSONObject information = datas.optJSONObject("information");
                Iterator<String> keys = information.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = information.optString(key, "");
                    HttpforNoticeinbottom.hashMap2.put(key, value);
                }

                try {
                    information.put("eduMinPay",datas.optString("eduMinPay",""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                UserInfoshare.edit().putString(BundleTag.Information,information.toString()).commit();
//                JSONObject userInfo = datas.optJSONObject("userInfo");
//                if (userInfo != null) {
//                    HandlerPersonalData(userInfo);
//                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    /**
     * 获取会员信息
     */
    private void getuserinfo(final String name) {
        RequestParams params = new RequestParams();
        params.put("userName", name);
        Httputils.PostWithBaseUrl(Httputils.UserInfo, params, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONObject datas = jsonObject.optJSONObject("datas");
                    HandlerPersonalData(datas.optJSONObject("userInfo"));
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });

    }
    private void HandleLocalData()
    {
        try {
            JSONObject UserInfo=new JSONObject(UserInfoshare.getString(BundleTag.UserInfo,""));
            UserInfoBean bean=UserInfoBean.Analysis_local(UserInfo);
            if(bean!=null)
            {
                imageLoader.displayImage(bean.getTypeDetail().getTypePicName(), personal_icon);
                grouptype.setText(bean.getTypeDetail().getTypeLevel());
                usertype.setText(bean.getTypeDetail().getTypeName());
                nickname.setText(bean.getNickName());
                username.setText(bean.getUserName());

                RepluginMethod.getApplication(getActivity()).setUsername(bean.getUserName());
                RepluginMethod.getApplication(getActivity()).setisgent(bean.getIsAgent()+"");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void HandlerPersonalData(JSONObject datas) {
        UserInfoshare.edit()
                .putString(BundleTag.UserInfo, datas.toString())
                .commit();
        UserInfoBean bean=UserInfoBean.Analysis_local(datas);
        if(bean!=null)
        {
            imageLoader.displayImage(bean.getTypeDetail().getTypePicName(), personal_icon);
            grouptype.setText(bean.getTypeDetail().getTypeLevel());
            usertype.setText(bean.getTypeDetail().getTypeName());
            nickname.setText(bean.getNickName());
            username.setText(bean.getUserName());
            balance.setText(String.format("%.2f", Double.valueOf(bean.getUserMoney())));
            RepluginMethod.getApplication(getActivity()).setUsername(bean.getUserName());
            RepluginMethod.getApplication(getActivity()).setisgent(bean.getIsAgent() + "");
        }
    }
    private void Model1(JSONArray fundsList) {

        int padding = ScreenUtils.getDIP2PX(getActivity(), 10);
        int padding2 = ScreenUtils.getDIP2PX(getActivity(), 5);
        LinearLayout hor = new LinearLayout(getActivity());
        LinearLayout.LayoutParams mll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mll.leftMargin = padding;
        mll.rightMargin = padding;
        mll.topMargin = padding;
        hor.setLayoutParams(mll);
        hor.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < fundsList.length(); i++) {
            JSONObject function = fundsList.optJSONObject(i);
            View item = View.inflate(getActivity(), R.layout.user_function_item1, null);
            ImageView image = (ImageView) item.findViewById(R.id.image);
            TextView textview = (TextView) item.findViewById(R.id.text);
            if (densityDpi <= 480) {
                imageLoader.displayImage(function.optString("smallPic", ""), image);
            } else {
                imageLoader.displayImage(function.optString("bigPic", ""), image);
            }
            textview.setText(function.optString("menuName", ""));
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.weight = 1;
            if (i > 0)
                ll.leftMargin = padding2;
            item.setLayoutParams(ll);
            hor.addView(item);
            item.setTag(function);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Username=RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername();
                    if(Username.length()==0 && !((String) v.getTag()).equalsIgnoreCase("service"))
                    {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        ClickLogin=true;
                        return;
                    }
                    JSONObject record=(JSONObject)v.getTag();
                    String menuCode=record.optString("menuCode", "");
                    String menuName=record.optString("menuName", "");
                    Intent intent=null;
                    switch (menuCode) {
                        case "deposit":
                            NeedCompleteData(PayActivitty.class.getName(),menuName);
                            break;
                        case "withdraw":
                            NeedCompleteData(WithDrawActivity.class.getName(),menuName);
                            break;
                        case "service":
                            intent=new Intent(getActivity(), CustomerActivity.class);
                            intent.putExtra("title",menuName);
                            startActivity(intent);
                            break;
                        case "edu":
                            intent=new Intent(getActivity(), ChangeActivity.class);
                            intent.putExtra("title",menuName);
                            startActivity(intent);
                            break;
                    }

                }
            });
        }
        mainlayout.addView(hor);
    }

    private void Model_Normal(JSONObject jsobj) {
        JSONArray jsonarry = jsobj.optJSONArray("list");
        int padding = ScreenUtils.getDIP2PX(getActivity(), 10);
        int horcount = jsonarry.length() % 4 == 0 ? (jsonarry.length() / 4) : (jsonarry.length() / 4 + 1);
        LinearLayout ver = new LinearLayout(getActivity());
        LinearLayout.LayoutParams vll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vll.topMargin = padding;
        ver.setLayoutParams(vll);
        ver.setOrientation(LinearLayout.VERTICAL);
        ver.setBackgroundColor(0xFFFFFFFF);

        ImageView imageview = new ImageView(getActivity());
        imageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        imageview.setBackgroundColor(getResources().getColor(R.color.line));

        TextView textview = new TextView(getActivity());
        LinearLayout.LayoutParams tll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tll.leftMargin = ScreenUtils.getDIP2PX(getActivity(), 15);
        textview.setLayoutParams(tll);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textview.setTextColor(getResources().getColor(R.color.black2));
        textview.setPadding(0, padding, 0, padding);
        textview.setText(jsobj.optString("menuModuleName", ""));

        ImageView imageview2 = new ImageView(getActivity());
        imageview2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        imageview2.setBackgroundColor(getResources().getColor(R.color.line));
        imageview2.setPadding(10, 0, 10, 0);

        ver.addView(imageview);
        ver.addView(textview);
        ver.addView(imageview2);

        for (int i = 0; i < horcount; i++) {
            LinearLayout hor = new LinearLayout(getActivity());
            LinearLayout.LayoutParams mll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            hor.setLayoutParams(mll);
            hor.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 4; j++) {
                View item = View.inflate(getActivity(), R.layout.user_function_item2, null);

                LinearLayout.LayoutParams itemll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                itemll.weight = 1;
                item.setLayoutParams(itemll);
                hor.addView(item);

                if (i * 4 + j < jsonarry.length()) {
                    JSONObject menu = jsonarry.optJSONObject(i * 4 + j);
                    ImageView image = (ImageView) item.findViewById(R.id.image);
                    TextView text = (TextView) item.findViewById(R.id.text);
                    if (densityDpi <= 480) {
                        imageLoader.displayImage(menu.optString("smallPic", ""), image);
                    } else {
                        imageLoader.displayImage(menu.optString("bigPic", ""), image);
                    }
                    text.setText(menu.optString("menuName", ""));
                    item.setTag(menu);
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            JSONObject jsonobj=(JSONObject)v.getTag();
                            JumpActivity(jsonobj.optString("menuCode",""),jsonobj.optString("menuModuleCode",""),jsonobj.optString("menuName",""));
                        }
                    });
                }


            }
            ver.addView(hor);
        }


        ImageView endline = new ImageView(getActivity());
        endline.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        endline.setBackgroundColor(getResources().getColor(R.color.line));
        ver.addView(endline);

        mainlayout.addView(ver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.login:
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    ClickLogin=true;
                    break;
                case R.id.register:
                    Intent intent2 = new Intent(getActivity(), RegActivity.class);
                    startActivity(intent2);
                    break;
            case R.id.setting:

                break;
            case R.id.messages:
                startActivity(new Intent(getActivity(), MessageListActivity.class));
                break;
        }
    }

    public void JumpActivity(String tag,String Modelcode,String title) {
        if(RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername().length()==0)
        {
            startActivity(new Intent(getActivity(),LoginActivity.class));
            ClickLogin=true;
            return;
        }
        if(tag==null)return;
        if (tag.equalsIgnoreCase("tzjl")) {
            Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if (tag.equalsIgnoreCase("zhjl") && Modelcode.equalsIgnoreCase("history")) {
            Intent intent = new Intent(getActivity(), TraceHistoryActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if (tag.equalsIgnoreCase("cpzb")) {
            Intent intent = new Intent(getActivity(), LotteryAccountTracsferListActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if (tag.equalsIgnoreCase("tdyk")) {
            Intent intent = new Intent(getActivity(), TeamIncomeActivity.class);
             intent.putExtra("title",title);
            startActivity(intent);
        }

        if(tag.equalsIgnoreCase("gryk"))
        {
            Intent intent = new Intent(getActivity(), PerSonalIncomePreViewActivity.class);
            intent.putExtra("title",title);
//            Intent intent = new Intent(getActivity(), PersonalIncomeActivity.class);
            startActivity(intent);
        }

        if(tag.equalsIgnoreCase("rxgl"))
        {
            NeedCompleteData(SalaryManageActivity.class.getName(),title);
        }
        if(tag.equalsIgnoreCase("tglj"))
        {
            Intent intent = new Intent(getActivity(), GeneralizeLinkActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if(tag.equalsIgnoreCase("tdgl"))
        {
            Intent intent = new Intent(getActivity(), TeamManageActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }
        if(tag.equalsIgnoreCase("mmgl"))
        {
            NeedCompleteData(ModifyPassword.class.getName(),title);
        }

        if(tag.equalsIgnoreCase("grzx"))
        {
            Intent intent = new Intent(getActivity(), PersonalDataActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if(tag.equalsIgnoreCase("zcxj"))
        {
            Intent intent = new Intent(getActivity(), RegistSubordinate.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if(tag.equalsIgnoreCase("czxx"))
        {
            Intent intent = new Intent(getActivity(), LotteryInstrutionActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if(tag.equalsIgnoreCase("czjl"))
        {
            Intent intent = new Intent(getActivity(), WADActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }

        if(tag.equalsIgnoreCase("qtyx"))
        {
            Intent intent = new Intent(getActivity(), OtherGameRecordListActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);
        }
        if(tag.equalsIgnoreCase("zhjl") && Modelcode.equalsIgnoreCase("bank"))
        {
            Intent intent = new Intent(getActivity(), ChangeRecordActivity.class);
            intent.putExtra("title",title);
            startActivity(intent);

        }
        if(tag.equalsIgnoreCase("yhkxx") && Modelcode.equalsIgnoreCase("account"))
        {
            NeedCompleteData(BankListActivity.class.getName(),title);
        }

        if(tag.equalsIgnoreCase("dlbb") )
        {
            Intent intent = new Intent(getActivity(), TeamChartActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("title",title);
            startActivity(intent);
        }



    }

    private void RefreshBalanceAndMessage()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("user-name",RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.UserBalance, requestParams, new MyJsonHttpResponseHandler(getActivity(), false) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    balance.setText(String.format("%.2f",Double.valueOf(jsonObject.optJSONObject("datas")
                            .optJSONObject("memberBalance").optString("balance",""))));
                }
            }
        });


        RequestParams requestParams2=new RequestParams();
        Httputils.PostWithBaseUrl(Httputils.MessageCount,requestParams2,new MyJsonHttpResponseHandler(getActivity(),false)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    int count=jsonObject.optJSONObject("datas").optInt("messageCount",0);
                    if(count>0)
                    {
                        messages.getChildAt(1).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        messages.getChildAt(1).setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void NeedCompleteData(String Classname,String title)
    {
        try {
            JSONObject UserInfo=new JSONObject(UserInfoshare.getString(BundleTag.UserInfo,""));
            UserInfoBean bean=UserInfoBean.Analysis_local(UserInfo);
            if(bean!=null)
            {
                if(bean.isHasRealName() && bean.isHasWithdrawPwd())
                {
                    try {
                        Intent intent=new Intent(getActivity(), Class.forName(Classname));
                        intent.putExtra("title",title);
                        startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Intent intent=new Intent();
                    intent.putExtra(BundleTag.Classname,Classname);
                    startActivity(new Intent(getActivity(),CompletePerSonalData.class));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
