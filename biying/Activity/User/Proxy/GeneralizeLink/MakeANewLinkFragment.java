package com.lottery.biying.Activity.User.Proxy.GeneralizeLink;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.UserInfoBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.util.VerrifyPointForTextview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/4.
 */
public class MakeANewLinkFragment extends BaseFragment implements View.OnClickListener {
    RadioGroup radiogroup1;
    LinearLayout radiogroup2, radiogroup3;
    String member_type=null, vaild_date=null;
    TextView confirm;
    EditText  channel, qq,wechat,skype;
    TextView recordcheckbox;
    SharedPreferences sharedPreferences;
    JSONObject jsonObject;
    String OriginalFandian;
    EditText lottery,live,electric,sport,fish,card;
    UserInfoBean bean;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitView();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_make_new_link, null);
    }

    private void InitView() {
        channel = FindView(R.id.channel);
        wechat=FindView(R.id.wechat);
        skype=FindView(R.id.skype);
        qq = FindView(R.id.qq);
        confirm = FindView(R.id.confirm);
        confirm.setOnClickListener(this);
        radiogroup1 = FindView(R.id.radiogroup1);
        radiogroup2 = FindView(R.id.radiogroup2);
        radiogroup3 = FindView(R.id.radiogroup3);
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.member:
                        member_type = "0";
                        break;
                    case R.id.proxy:
                        member_type = "1";
                        break;
                }
            }
        });
        ((RadioButton) radiogroup1.getChildAt(1)).setChecked(true);

        for (int i = 1; i < radiogroup2.getChildCount(); i++) {
            TextView textView = (TextView) radiogroup2.getChildAt(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recordcheckbox != null) {
                        recordcheckbox.setTextColor(getResources().getColor(R.color.gray19));
                        recordcheckbox.setBackground(getResources().getDrawable(R.drawable.item_title));
                    }
                    v.setBackground(getResources().getDrawable(R.drawable.item_title_selected));
                    recordcheckbox = (TextView) v;
                    recordcheckbox.setTextColor(getResources().getColor(R.color.loess4));
                    switch (v.getId()) {
                        case R.id.oneday:
                            vaild_date = "1";
                            break;
                        case R.id.threeday:
                            vaild_date = "3";
                            break;
                        case R.id.sevenday:
                            vaild_date = "7";
                            break;
                    }
                }
            });
        }

        for (int i = 1; i < radiogroup3.getChildCount(); i++) {
            TextView textView = (TextView) radiogroup3.getChildAt(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recordcheckbox != null) {
                        recordcheckbox.setTextColor(getResources().getColor(R.color.gray19));
                        recordcheckbox.setBackground(getResources().getDrawable(R.drawable.item_title));
                    }
                    v.setBackground(getResources().getDrawable(R.drawable.item_title_selected));
                    recordcheckbox = (TextView) v;
                    recordcheckbox.setTextColor(getResources().getColor(R.color.loess4));
                    switch (v.getId()) {
                        case R.id.fifteenday:
                            vaild_date = "15";
                            break;
                        case R.id.thirtyday:
                            vaild_date = "30";
                            break;
                        case R.id.forever:
                            vaild_date = "0";
                            break;
                    }
                }
            });
        }
        ((TextView)radiogroup2.getChildAt(1)).performClick();
        sharedPreferences = RepluginMethod.getHostApplication(getActivity()).getSharedPreferences(BundleTag.Lottery, Activity.MODE_PRIVATE);

        try {
            jsonObject=new JSONObject(sharedPreferences.getString(BundleTag.UserInfo,""));
             bean=UserInfoBean.Analysis_local(jsonObject);
            if(bean==null){
                getuserinfo();
                return;
            }
            setVerify(bean);
            OriginalFandian=bean.getBetBackWater();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setVerify(UserInfoBean bean)
    {
        LinearLayout lotterypoint=FindView(R.id.lotterypoint);
        LinearLayout livepoint=FindView(R.id.livepoint);
        LinearLayout electricpoint=FindView(R.id.electricpoint);
        LinearLayout sportpoint=FindView(R.id.sportpoint);
        LinearLayout fishpoint=FindView(R.id.fishpoint);
        LinearLayout cardpoint=FindView(R.id.cardpoint);

        lottery=(EditText)lotterypoint.getChildAt(2);
        live=(EditText)livepoint.getChildAt(2);
        electric=(EditText)electricpoint.getChildAt(2);
        sport=(EditText)sportpoint.getChildAt(2);
        fish=(EditText)fishpoint.getChildAt(2);
        card=(EditText)cardpoint.getChildAt(2);

        lottery.setText(bean.getBackWater().getLottery());
        live.setText(bean.getBackWater().getLive());
        electric.setText(bean.getBackWater().getElectronic());
        sport.setText(bean.getBackWater().getSport());
        fish.setText(bean.getBackWater().getFish());
        card.setText(bean.getBackWater().getCard());

        VerrifyPointForTextview.AddVerifyView(lottery,
                (TextView) lotterypoint.getChildAt(3), (TextView) lotterypoint.getChildAt(1), bean.getBackWater().getLottery(), getActivity());

        VerrifyPointForTextview.AddVerifyView(live,
                (TextView) livepoint.getChildAt(3), (TextView) livepoint.getChildAt(1), bean.getBackWater().getLive(), getActivity());

        VerrifyPointForTextview.AddVerifyView(electric,
                (TextView) electricpoint.getChildAt(3), (TextView) electricpoint.getChildAt(1), bean.getBackWater().getElectronic(), getActivity());

        VerrifyPointForTextview.AddVerifyView(sport,
                (TextView)sportpoint.getChildAt(3),(TextView)sportpoint.getChildAt(1),bean.getBackWater().getSport(),getActivity());

        VerrifyPointForTextview.AddVerifyView(fish,
                (TextView)fishpoint.getChildAt(3),(TextView)fishpoint.getChildAt(1),bean.getBackWater().getFish(),getActivity());

        VerrifyPointForTextview.AddVerifyView(card,
                (TextView)cardpoint.getChildAt(3),(TextView)cardpoint.getChildAt(1),bean.getBackWater().getCard(),getActivity());
    }


    private void Commit() {
        if(member_type==null)
        {
            ToastUtil.showMessage(getActivity(), "请选择用户类型");
            return;
        }

        if(vaild_date==null)
        {
            ToastUtil.showMessage(getActivity(), "请选择有效期");
            return;
        }
        if (channel.getText().toString().length() < 1) {
            ToastUtil.showMessage(getActivity(), "请输入推广渠道");
            return;
        }

        if(qq.getText().toString().length()<1 && skype.getText().toString().length()<1 && wechat.getText().toString().length()<1)
        {
            ToastUtil.showMessage(getActivity(),"请至少填写一种联系方式");
            return;
        }
//        if (qq.getText().toString().length()>0 && qq.getText().toString().length() < 4) {
//            ToastUtil.showMessage(getActivity(), "请输入正确的QQ号码      4-12位");
//            return;
//        }

        confirm.setEnabled(false);
        RequestParams requestParams = new RequestParams();
        requestParams.put("userType", member_type);
        requestParams.put("valadateTime", vaild_date);
        requestParams.put("back", lottery.getText().toString());
        requestParams.put("liveBack", live.getText().toString());
        requestParams.put("electronicBack", electric.getText().toString());
        requestParams.put("sportBack", sport.getText().toString());
        requestParams.put("fishBack", fish.getText().toString());
        requestParams.put("cardBack", card.getText().toString());
        requestParams.put("qudao", channel.getText().toString());
        requestParams.put("qq", qq.getText().toString());
        requestParams.put("skype",skype.getText().toString());
        requestParams.put("wx", wechat.getText().toString());

        Httputils.PostWithBaseUrl(Httputils.NewLink, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirm.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("NewLink",jsonObject.toString());
                confirm.setEnabled(true);
                ToastUtil.showMessage(getActivity(), jsonObject.optString("msg"));
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                ResetView();
                ((GeneralizeLinkActivity)getActivity()).SetFragment();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.confirm:
                ArrayList<EditText> editTexts=new ArrayList<>();
                editTexts.add(lottery);
                editTexts.add(live);
                editTexts.add(electric);
                editTexts.add(sport);
                editTexts.add(fish);
                if(VerrifyPointForTextview.VerifyValue(editTexts,bean,getActivity()))
                Commit();
                break;
        }
    }

    /**
     * 获取会员信息
     */
    private void getuserinfo() {
        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(getActivity()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.UserInfo, params, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONObject datas = jsonObject.optJSONObject("datas").optJSONObject("userInfo");
                    sharedPreferences.edit()
                            .putString(BundleTag.UserInfo, datas.toString())
                            .commit();
                     bean=UserInfoBean.Analysis_local(datas);
                    setVerify(bean);
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });

    }

    private void ResetView()
    {
        member_type = "0";
        vaild_date = "1";
        channel.setText("");
        qq.setText("");
        skype.setText("");
        wechat.setText("");
        try {
            jsonObject=new JSONObject(sharedPreferences.getString(BundleTag.UserInfo,""));
             bean=UserInfoBean.Analysis_local(jsonObject);
            if(bean==null){
                getuserinfo();
                return;
            }
            setVerify(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recordcheckbox=null;
        for (int i = 1; i < radiogroup2.getChildCount(); i++) {
            TextView textview=(TextView)radiogroup2.getChildAt(i);
            textview.setBackground(getResources().getDrawable(R.drawable.btn_item_title_selector));
            textview.setTextColor(getResources().getColor(R.color.gray19));
        }
        for (int i = 1; i < radiogroup3.getChildCount(); i++) {
            TextView textview=(TextView)radiogroup3.getChildAt(i);
            textview.setBackground(getResources().getDrawable(R.drawable.btn_item_title_selector));
            textview.setTextColor(getResources().getColor(R.color.gray19));
        }
        ((RadioButton) radiogroup1.getChildAt(1)).setChecked(true);
        ((TextView)radiogroup2.getChildAt(1)).performClick();
    }
}
