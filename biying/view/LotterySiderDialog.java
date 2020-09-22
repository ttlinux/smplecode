package com.lottery.biying.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.User.AccountManage.BankCardManage.BankListActivity;
import com.lottery.biying.Activity.User.AccountManage.LotteryInstrution.LotteryInstrutionDetailActivity;
import com.lottery.biying.Activity.User.AccountManage.Password.ModifyPassword;
import com.lottery.biying.Activity.User.AccountManage.PersonalData.PersonalDataActivity;
import com.lottery.biying.Activity.User.CustomerActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.LotteryAccTransfer.LotteryAccountTracsferListActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.PersonalIncomeActivity;
import com.lottery.biying.Activity.User.LotteryAccountTransfer.WithdrawAndDepositRecord.WADActivity;
import com.lottery.biying.Activity.User.Proxy.GeneralizeLink.GeneralizeLinkActivity;
import com.lottery.biying.Activity.User.Proxy.RegistSubordinate;
import com.lottery.biying.Activity.User.Proxy.SalaryManage.SalaryManageActivity;
import com.lottery.biying.Activity.User.Proxy.TeamIncomeActivity;
import com.lottery.biying.Activity.User.Proxy.TeamManage.TeamManageActivity;
import com.lottery.biying.Activity.User.Record.OrderHistoryActivity;
import com.lottery.biying.Activity.User.Record.OtherGame.OtherGameRecordActivity;
import com.lottery.biying.Activity.User.Record.OtherGame.OtherGameRecordListActivity;
import com.lottery.biying.Activity.User.Record.TraceHistoryActivity;
import com.lottery.biying.Activity.User.TopUp.PayActivitty;
import com.lottery.biying.Activity.User.WithDraw.WithDrawActivity;
import com.lottery.biying.R;
import com.lottery.biying.bean.LotterySiderBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/6/25.
 */
public class LotterySiderDialog extends RelativeLayout implements OnClickListener{

    WindowManager windowManager;
    Activity context;
    String LotteryCode,LotteryName;
    View view;
    ImageView personal_icon;
    TextView username,usermoney;
    ImageLoader imageLoader;
    LinearLayout funclayout;
    int densityDpi;
    boolean status=false;

    public LotterySiderDialog(Activity context, String LotteryCode,String LotteryName) {
        super(context);
        Initview(context, LotteryCode, LotteryName);
    }

    public LotterySiderDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LotterySiderDialog(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void Initview(final Activity context, String LotteryCode,String LotteryName) {
        this.LotteryName=LotteryName;
        this.LotteryCode = LotteryCode;
        this.context = context;
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        view = View.inflate(context, R.layout.dialog_right_sider, null);
        personal_icon=(ImageView)view.findViewById(R.id.personal_icon);
        personal_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PersonalDataActivity.class));
            }
        });
        username=(TextView)view.findViewById(R.id.username);
        usermoney=(TextView)view.findViewById(R.id.usermoney);
        imageLoader = RepluginMethod.getApplication(context).getImageLoader();
        funclayout=(LinearLayout)view.findViewById(R.id.funclayout);
        LotterySiderBean bean=RepluginMethod.getApplication(context).getLotterySiderBean();
        RelativeLayout relaview=(RelativeLayout)view.findViewById(R.id.topview);
        relaview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止点击消失
            }
        });
        if(bean!=null)
        {
            imageLoader.displayImage(bean.getDatas().getUserDetail().getTypePicName(), personal_icon);
            username.setText(bean.getDatas().getUserDetail().getUserName());
            try {
                usermoney.setText("￥ "+String.format("%.2f", Double.valueOf(bean.getDatas().getUserDetail().getUserMoney())));
            }
            catch (NumberFormatException ex)
            {
                ex.printStackTrace();
            }

            GetBalance();
            AddView(bean);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void show() {

        if (getChildCount() < 1) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
//        params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
            addView(view, params);

            TranslateAnimation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(animation);
            animationSet.setFillBefore(true);
            animationSet.setFillAfter(true);
            animationSet.setDuration(200);
            getChildAt(0).startAnimation(animationSet);
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        windowManager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明
        windowManager.addView(this, lp);
    }

    public void hide() {
        if(status)return;
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(200);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                status=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                status=false;
                windowManager.removeView(LotterySiderDialog.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        getChildAt(0).startAnimation(animationSet);

    }


    public static void GetSiderData(final Activity activity) {

        Httputils.PostWithBaseUrl(Httputils.FastResourse, new RequestParams(), new MyJsonHttpResponseHandler(activity, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                RepluginMethod.getApplication(activity).setLotterySiderBean(LotterySiderBean.AnalysisData(jsonObject, activity));

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }


    private void AddView(LotterySiderBean bean)
    {

        LinearLayout firstitem=new LinearLayout(context);
        firstitem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        firstitem.setOrientation(LinearLayout.HORIZONTAL);
        firstitem.setBackgroundColor(0xFFFFFFFF);
        funclayout.addView(firstitem);

        for (int i = 0; i < 2; i++) {

            RelativeLayout relativeLayout=new RelativeLayout(context);
            LinearLayout.LayoutParams rlayoutparams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlayoutparams.weight=1;
            relativeLayout.setLayoutParams(rlayoutparams);

            LinearLayout item=new LinearLayout(context);
            item.setOrientation(LinearLayout.HORIZONTAL);
            RelativeLayout.LayoutParams itemll= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemll.topMargin=ScreenUtils.getDIP2PX(context,10);
            itemll.bottomMargin=ScreenUtils.getDIP2PX(context,10);
            itemll.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            item.setLayoutParams(itemll);
            item.setGravity(Gravity.CENTER_VERTICAL);

            ImageView imageView=new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            item.addView(imageView);

            TextView textView=new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding(ScreenUtils.getDIP2PX(context, 10), 0, 0, 0);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(context.getResources().getColor(R.color.gray19));
            item.addView(textView);
            relativeLayout.addView(item);

            ImageView line=new ImageView(context);
            line.setLayoutParams(new ViewGroup.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT));
            line.setBackgroundColor(context.getResources().getColor(R.color.line));
            firstitem.addView(relativeLayout);
            firstitem.addView(line);
        }

        boolean hasdeposit=false;
        boolean haswithdraw=false;
        for (int i = 0; i < bean.getDatas().getFundsList().size(); i++) {
            if(bean.getDatas().getFundsList().get(i).getMenuCode().equalsIgnoreCase("deposit"))
            {
                hasdeposit=true;
                Handlefirstitem(firstitem,0,bean.getDatas().getFundsList().get(i));
            }
            else if(bean.getDatas().getFundsList().get(i).getMenuCode().equalsIgnoreCase("withdraw"))
            {
                haswithdraw=true;
                Handlefirstitem(firstitem,1,bean.getDatas().getFundsList().get(i));
            }
            else if(bean.getDatas().getFundsList().get(i).getMenuCode().equalsIgnoreCase("service")) {
                LotterySiderBean.DatasBean.FundsListBean fbean=bean.getDatas().getFundsList().get(i);
                NormalItem(ScreenUtils.getDIP2PX(context,10),fbean.getBigPic(),
                        fbean.getSmallPic(),fbean.getMenuName(),fbean.getMenuModuleCode(),fbean.getMenuCode());
            }
        }
        if(!hasdeposit)
        {
            RelativeLayout relativeLayout=(RelativeLayout)firstitem.getChildAt(0);
            relativeLayout.setVisibility(INVISIBLE);
        }
        if(!haswithdraw)
        {
            RelativeLayout relativeLayout=(RelativeLayout)firstitem.getChildAt(1);
            relativeLayout.setVisibility(INVISIBLE);
        }
        if(!hasdeposit && !haswithdraw)
        {
            firstitem.setVisibility(GONE);
        }


        for (int i = 0; i <bean.getDatas().getMenuList().size() ; i++) {
            LotterySiderBean.DatasBean.MenuListBean  mbean=bean.getDatas().getMenuList().get(i);
            int margin=10;
            for (int j = 0; j <mbean.getList().size() ; j++) {
                LotterySiderBean.DatasBean.MenuListBean.ListBean lbean=mbean.getList().get(j);
                NormalItem(ScreenUtils.getDIP2PX(context,margin),lbean.getBigPic(),lbean.getSmallPic(),
                        lbean.getMenuName(),lbean.getMenuModuleCode(),lbean.getMenuCode());
                margin=1;
            }
        }

        AddLogOut();
    }

    @Override
    public void onClick(View v) {
        if(v.getTag()==null)return;
        Tagclass tag=(Tagclass)v.getTag();
        switch (tag.getMenuCode())
        {
            case "deposit":
                context.startActivity(new Intent(context, PayActivitty.class));
                break;
            case "withdraw":
                context.startActivity(new Intent(context, WithDrawActivity.class));
                break;
            case "service":
                context.startActivity(new Intent(context, CustomerActivity.class));
                break;
            case "czjl":
                //充提记录
                context.startActivity(new Intent(context, WADActivity.class));
                break;
            case "cpzb":
                //彩票账变
                context.startActivity(new Intent(context, LotteryAccountTracsferListActivity.class));
                break;
            case "gryk":
                //个人盈亏
                context.startActivity(new Intent(context, PersonalIncomeActivity.class));
                break;
            case "tzjl":
                //投注记录
                Intent tzintent=new Intent(context, OrderHistoryActivity.class);
                tzintent.putExtra(BundleTag.LotteryCode,LotteryCode);
                tzintent.putExtra(BundleTag.LotteryName,LotteryName);
                context.startActivity(tzintent);
                break;
            case "zhjl":
                //追号记录
                context.startActivity(new Intent(context, TraceHistoryActivity.class));
                break;
            case "grzx":
                //个人资料
                context.startActivity(new Intent(context, PersonalDataActivity.class));
                break;
            case "mmgl":
                //密码管理
                context.startActivity(new Intent(context, ModifyPassword.class));
                break;
            case "yhkxx":
                //银行卡管理
                context.startActivity(new Intent(context, BankListActivity.class));
                break;
            case "czxx":
                //彩种信息
                Intent czxxintent=new Intent(context, LotteryInstrutionDetailActivity.class);
                czxxintent.putExtra(BundleTag.LotteryCode,LotteryCode);
                czxxintent.putExtra(BundleTag.LotteryName,LotteryName);
                context.startActivity(czxxintent);
                break;
            case "tdgl":
                //团队管理
                context.startActivity(new Intent(context, TeamManageActivity.class));
                break;
            case "tdyk":
                //团队盈亏
                context.startActivity(new Intent(context, TeamIncomeActivity.class));
                break;
            case "zcxj":
                //注册下级
                context.startActivity(new Intent(context, RegistSubordinate.class));
                break;
            case "tglj":
                //推广链接
                context.startActivity(new Intent(context, GeneralizeLinkActivity.class));
                break;
            case "rxgl":
                //日薪管理
                context.startActivity(new Intent(context, SalaryManageActivity.class));
                break;
            case"qtyx":
                //其他游戏
                context.startActivity(new Intent(context, OtherGameRecordListActivity.class));
                break;
        }
    }



    private void Handlefirstitem(LinearLayout firstitem,int index,LotterySiderBean.DatasBean.FundsListBean bean)
    {
        RelativeLayout relativeLayout=(RelativeLayout)firstitem.getChildAt(index*2);
        LinearLayout linearLayout=(LinearLayout)relativeLayout.getChildAt(0);
        ImageView imageView=(ImageView)linearLayout.getChildAt(0);
        TextView textView=(TextView)linearLayout.getChildAt(1);

        if (densityDpi <= 320) {
            imageLoader.displayImage(bean.getSmallPic(), imageView);
        } else {
            imageLoader.displayImage(bean.getBigPic(), imageView);
        }

        textView.setText(bean.getMenuName());
        relativeLayout.setTag(new Tagclass(bean.getMenuModuleCode(),bean.getMenuCode()));
        relativeLayout.setOnClickListener(this);
    }

    private void NormalItem(int margintop,String burl,String surl,String titlestr,String menuModuleCode,String menuCode)
    {
        View view=View.inflate(context,R.layout.item_lottery_sider,null);
        ImageView icon=(ImageView)view.findViewById(R.id.icon);
        TextView title=(TextView)view.findViewById(R.id.title);

        if (densityDpi <= 320) {
            imageLoader.displayImage(surl, icon);
        } else {
            imageLoader.displayImage(burl, icon);
        }

        title.setText(titlestr);
        view.setTag(new Tagclass(menuModuleCode, menuCode));
        view.setOnClickListener(this);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.topMargin=margintop;
        funclayout.addView(view, ll);
    }

    private void AddLogOut()
    {
        View view=View.inflate(context,R.layout.item_lottery_sider,null);
        ImageView icon=(ImageView)view.findViewById(R.id.icon);
        TextView title=(TextView)view.findViewById(R.id.title);
        icon.setPadding(ScreenUtils.getDIP2PX(context,3),0,0,0);
        icon.setImageDrawable(context.getResources().getDrawable(R.drawable.logout));
        title.setText("退出登录");
        view.setTag(new Tagclass("logout", ""));
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.topMargin=ScreenUtils.getDIP2PX(context,10);
        funclayout.addView(view,ll);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }


    private void Logout() {

        RequestParams params = new RequestParams();
        params.put("userName", RepluginMethod.getApplication(context).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.logout, params, new MyJsonHttpResponseHandler(context, true) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;

                //清空主程序Username
                Application app=RepluginMethod.getHostApplication(context);
                String MainHostUsername="";
                Field f = null;
                try {
                    f = app.getClass().getDeclaredField("Username");
                    f.setAccessible(true);
                    MainHostUsername=(String)f.get(app);
                    f.set(app, "");
                    LogTools.e("主程序Username","修改成功");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                RepluginMethod.getApplication(context).ClearTiYuCache();
                RepluginMethod.getApplication(context).setUsername("");
                SharedPreferences sharedPreferences = RepluginMethod.getHosttSharedPreferences(context);
                sharedPreferences.edit().putString(BundleTag.Access_token, "").commit();

                if(MainHostUsername!=null && MainHostUsername.length()>0)
                {
                    if(!context.isDestroyed() && !context.isFinishing())
                    {
                        ToastUtil.showMessage(context, jsonObject.optString("msg", ""));
                    }
                    Intent intent = RepluginMethod.HostMainclass(context);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(BundleTag.Username, "");
                    intent.putExtra(BundleTag.IntentTag, 0);
                    context.startActivity(intent);
                }
                context.finish();

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    public class Tagclass
    {
        String menuModuleCode;
        String menuCode;

        public Tagclass(String menuModuleCode,String menuCode)
        {
            setMenuCode(menuCode);
            setMenuModuleCode(menuModuleCode);
        }
        public String getMenuCode() {
            return menuCode;
        }

        public void setMenuCode(String menuCode) {
            this.menuCode = menuCode;
        }

        public String getMenuModuleCode() {
            return menuModuleCode;
        }

        public void setMenuModuleCode(String menuModuleCode) {
            this.menuModuleCode = menuModuleCode;
        }
    }

    private void GetBalance()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("user-name", RepluginMethod.getApplication(context).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.UserBalance, requestParams, new MyJsonHttpResponseHandler(context, false) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("UserBalance", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    String balance=String.format("%.2f", Double.valueOf(jsonObject.optJSONObject("datas")
                                    .optJSONObject("memberBalance").optString("balance", "")));
                           usermoney.setText("￥ "+balance);
                    RepluginMethod.getApplication(context).getLotterySiderBean().getDatas().getUserDetail().setUserMoney(balance);
                }
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            hide();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
