package com.lottery.biying.LotteryMethod;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.biying.LotteryMethod.K3.K3;
import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.view.DiceView;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.view.LotteryButton;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/9.
 */
public class AnalysisType {

    public static final String QSAN_ZX_HZ = "q3_zx_hz"; // 前三直选和值
    public static final String QSAN_ZX_KD = "q3_zx_kd"; // 前三直选跨度
    public static final String QSAN_ZUX_HZ = "q3_zux_hz"; // 前三组选和值

    public static final String ZSAN_ZX_HZ = "z3_zx_hz"; // 中三直选和值
    public static final String ZSAN_ZX_KD = "z3_zx_kd"; // 中三直选跨度
    public static final String ZSAN_ZUX_HZ = "z3_zux_hz"; // 中三组选和值

    public static final String HSAN_ZX_HZ = "h3_zx_hz"; // 后三直选和值
    public static final String HSAN_ZX_KD = "h3_zx_kd"; // 后三直选跨度
    public static final String HSAN_ZUX_HZ = "h3_zux_hz"; // 后三组选和值

    // ssc玩法 - 前二
    public static final String QE_ZX_FS = "q2_zx_fs"; // 前二直选复式
    public static final String QE_ZX_HZ = "q2_zx_hz"; // 前二直选和值
    public static final String QE_ZX_KD = "q2_zx_kd";// 前二直选跨度
    public static final String QE_ZUX_FS = "q2_zux_fs"; // 前二组选复式
    public static final String QE_ZUX_HZ = "q2_zux_hz"; // 前二组选和值
    public static final String QE_ZUX_BD = "q2_zux_bd"; // 前二组选包胆

    // ssc玩法 - 后二
    public static final String HE_ZX_FS = "h2_zx_fs"; // 后二直选复式
    public static final String HE_ZX_HZ = "h2_zx_hz"; // 后二直选和值
    public static final String HE_ZX_KD = "h2_zx_kd";// 后二直选跨度
    public static final String HE_ZUX_FS = "h2_zux_fs"; // 后二组选复式
    public static final String HE_ZUX_HZ = "h2_zux_hz"; // 后二组选和值
    public static final String HE_ZUX_BD = "h2_zux_bd"; // 后二组选包胆

    public static final String WXWF_TM = "wxwf_tm_tm"; //微信玩法特码

    public static final int Hk6NumColor_Numbers[][]=new int[][]{
            {1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45},
            {3,4,9,10,14,15,20,25,26,31,36,37,41,42,47,48},
            {5,6,11,16,17,21,22,27,28,32,33,38,39,43,44,49}};//红 蓝 绿

    public static final int Hk6NumColors[]={0xffee1f3b,0xff1f8aee,0xff56ee1f};

    public static String CurrentLottery = "";//最外层
    public static String CurrentLotteryMainCode = "";//titlecode 小类
    private static int LottertCategory;
    private static SparseArray<ArrayList<LotteryButton>> SelectNumbers;
    private static ArrayList<ArrayList<LotteryButton>> m_numbers;//记录当前号码 做随机用
    private static String NoLeftBtnTags[];

    public static int getLottertCategory() {
        return LottertCategory;
    }

    public static void setLottertCategory(int lottertCategory) {
        LottertCategory = lottertCategory;
    }

    public static void setCurrentLotteryMainCode(String currentLotteryMainCode) {
        getSelectNumbers().clear();
        CurrentLotteryMainCode = currentLotteryMainCode;
    }

    public static SparseArray<ArrayList<LotteryButton>> getSelectNumbers() {
        if (SelectNumbers == null) SelectNumbers = new SparseArray<ArrayList<LotteryButton>>();
        return SelectNumbers;
    }

    public static void setSelectNumbers(SparseArray<ArrayList<LotteryButton>> selectNumbers) {
        SelectNumbers = selectNumbers;
    }

    public static ArrayList<ArrayList<LotteryButton>> getCurrentbuttons() {
        return m_numbers;
    }

    public static void setCurrentbuttons(ArrayList<ArrayList<LotteryButton>> btns) {
        m_numbers = btns;
    }

    public static void setCurrentLottery(String lotterycode) {
        getSelectNumbers().clear();
        CurrentLottery = lotterycode;
    }

    public static enum MainLotteryCode {
        //彩票类型(1:时时彩 2:赛车|飞艇 3:快3 4:11选5 5:排列3|福彩 6:幸运28 7:香港)
        SSC(1),
        Saiche(2), K3(3),
        SYXW(4), PL3(5),
        XY28(6), HK(7);
        public int index;

        // 构造方法
        private MainLotteryCode(int index) {
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.index + "";
        }

        public int getIndex() {
            return this.index;
        }
    }

    public static enum CQSSC {
        WuXing("5x", 0), Qian4("q4", 1),
        Hou4("h4", 2), Qian3("q3", 3),
        Zhong3("z3", 4), Hou3("h3", 5),
        Qian2("q2", 6), Hou2("h2", 7), DingWeiDan("dwd", 8),
        RenXuan("rx", 9), Longhu("lh", 10), WeChat("wxwf", 11);

        public String name;
        public int index;

        // 构造方法
        private CQSSC(String name, int index) {
            this.name = name;
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }

    }

    public static enum BJSC {
        LMP("lmp", 0), DWD("dwd", 1),
        Q2("q2", 2), Q3("q3", 3),
        Q4("q4", 4), Q5("q5", 5);
        public String name;
        public int index;

        // 构造方法
        private BJSC(String name, int index) {
            this.name = name;
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }

    }

    public static enum FC3D {
        SX("3x", 0), H2("h2", 1),
        Q2("q2", 2), DWD("wd", 3);
        public String name;
        public int index;

        // 构造方法
        private FC3D(String name, int index) {
            this.name = name;
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }

    }

    public static enum XY28 {
        TM("tm", 0),DWD("dwd", 0),BDW("bdw", 0),SX("sx", 0),EX("ex", 0);
        public String name;
        public int index;

        // 构造方法
        private XY28(String name, int index) {
            this.name = name;
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }

    }

    public static enum LHC {
        TM("tm", 0),ZM("zm", 1),ZTM("ztm", 2),SB("sb", 3),
        LM("lm", 4),TX("tx", 5),TWS("tws", 6),ZM1T6("zm1t6",7),PT("pt",8),ZHENGX("zhengx",9),ZONGX("zongx",10);
        public String name;
        public int index;

        // 构造方法
        private LHC(String name, int index) {
            this.name = name;
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }

    }

    public static enum GDSYXW {
        X1("x1", 0), X2("x2", 1),
        X3("x3", 2), X4("x4", 3),
        X5("x5", 4), X6("x6", 5),
        X7("x7", 6), X8("x8", 7);
        public String name;
        public int index;

        // 构造方法
        private GDSYXW(String name, int index) {
            this.name = name;
            this.index = index;
        }

        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }

        //覆盖方法
        public int getIndex() {
            return this.index;
        }
    }

    //计算下注
    public static int Caculatebets(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode) {
        LogTools.e("code", gamecode + " " + CurrentLotteryMainCode + " " + CurrentLottery);
        return AnalysisBets.Caculatebets(numbers, gamecode, CurrentLotteryMainCode, CurrentLottery);
    }

    //按钮点击是否需要处理
    public static boolean CaculateStatus(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode) {
        LogTools.e("code", gamecode + " " + CurrentLotteryMainCode + " " + CurrentLottery);
        return AnalusisBtnStatus.CaculateStatus(numbers, buttonNumber, gamecode, CurrentLotteryMainCode, CurrentLottery);
    }

    //随机号码
    public static void RandomNumbers(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode) {
        LogTools.e("code", gamecode + " " + CurrentLotteryMainCode + " " + CurrentLottery);
        RandomNumbers.randomnumbers(numbers, gamecode, CurrentLotteryMainCode, CurrentLottery);
    }

    //格式化下注号码
    public static JSONObject FormatNumbers(SparseArray<ArrayList<LotteryButton>> numbers, String gamecode) {
        LogTools.e("code", gamecode + " " + CurrentLotteryMainCode + " " + CurrentLottery);
        if (LottertCategory==MainLotteryCode.K3.getIndex()) {
            return K3.getInstance().Formatnumber();
        } else {
            return FormatNumbers.FormatNumbers(numbers, gamecode, CurrentLotteryMainCode, CurrentLottery);
        }
    }

    //判断六合彩哪个没有左边的按钮
    public static boolean CheckIfHasLeftBtn(Activity context,String tag)
    {
        if (LottertCategory==MainLotteryCode.HK.getIndex())
        {
            if(NoLeftBtnTags==null && !context.isFinishing() && !context.isDestroyed())
            {
                NoLeftBtnTags=context.getResources().getStringArray(R.array.no_leftbutton_tags);
            }
            for (int i = 0; i < NoLeftBtnTags.length; i++) {
                if( NoLeftBtnTags[i].equalsIgnoreCase(tag))
                {
                    return false;
                }
            }
        }

        return true;
    }

    //获取一行你能放几个按钮
    public static int getCountOfLotteryBtns(Activity context, String tag)
    {
        if (LottertCategory==MainLotteryCode.HK.getIndex())
        {
            if(NoLeftBtnTags==null && !context.isFinishing() && !context.isDestroyed())
            {
                NoLeftBtnTags=context.getResources().getStringArray(R.array.no_leftbutton_tags);
            }
            for (int i = 0; i < NoLeftBtnTags.length; i++) {
                if( NoLeftBtnTags[i].equalsIgnoreCase(tag))
                {
                    return 4;
                }
            }
            if(tag.contains("hk6_sb_sb"))
            {
                return 3;
            }
        }

        return 6;
    }

    //获取六合彩圆圈字体颜色
    public static int getHK6CircleColor(int value)
    {

        for (int i = 0; i < Hk6NumColor_Numbers.length; i++) {
            int[] temp=Hk6NumColor_Numbers[i];
            for (int j = 0; j <temp.length ; j++) {
                if(value==temp[j])
                {
                    return Hk6NumColors[i];
                }
            }
        }
        return Hk6NumColors[0];
    }

    public static void ClearSelected(int LotteryCategory) {
        if (LotteryCategory==MainLotteryCode.K3.getIndex()) {
            K3 k3 = K3.getInstance();
            for (int i = 0; i < k3.getSelectNumbers().size(); i++) {
                ArrayList<DiceView> buttons = k3.getSelectNumbers().valueAt(i);
                for (int j = 0; j < buttons.size(); j++) {
                    buttons.get(j).setCheck(false);
                }
            }
            k3.getSelectNumbers().clear();
        } else {
            for (int i = 0; i < getSelectNumbers().size(); i++) {
                ArrayList<LotteryButton> buttons = getSelectNumbers().valueAt(i);
                for (int j = 0; j < buttons.size(); j++) {
                    buttons.get(j).setChecked(false);
                }
            }
            getSelectNumbers().clear();
        }
    }


    private static boolean contains(String gamecode, String... strs) {
        boolean temp = false;
        for (int i = 0; i < strs.length; i++) {
            temp = gamecode.contains(strs[i]);
            if (temp) return temp;
        }
        return temp;
    }

    public static void ModifyTitleItem(RelativeLayout relativeLayout, int type,String GameCode) {
        //彩票类型(1:时时彩 2:赛车|飞艇 3:快3 4:11选5 5:排列3|福彩 6:幸运28 7:香港)
        LinearLayout mainview = (LinearLayout) relativeLayout.findViewById(R.id.mainview);
        if (type == MainLotteryCode.K3.getIndex() || type == MainLotteryCode.XY28.getIndex()) {
            mainview.getChildAt(mainview.getChildCount() - 1).setVisibility(View.GONE);
            mainview.getChildAt(mainview.getChildCount() - 2).setVisibility(View.GONE);
            TextView textView = (TextView) mainview.getChildAt(mainview.getChildCount() - 3);
            textView.setText("和值");
        } else if (type==MainLotteryCode.HK.getIndex()) {
            mainview.getChildAt(mainview.getChildCount() - 1).setVisibility(View.GONE);
            mainview.getChildAt(mainview.getChildCount() - 2).setVisibility(View.GONE);
            TextView textView = (TextView) mainview.getChildAt(mainview.getChildCount() - 3);
            textView.setText("特码");
        }
        else if(type==MainLotteryCode.SSC.getIndex())
        {
            mainview.getChildAt(mainview.getChildCount() - 1).setVisibility(View.GONE);
            mainview.getChildAt(mainview.getChildCount() - 2).setVisibility(View.GONE);
            TextView textView=(TextView)mainview.getChildAt(mainview.getChildCount() - 3);
            textView.setVisibility(View.VISIBLE);
            if(GameCode!=null)
            {
                if(contains(GameCode,HSAN_ZX_KD,ZSAN_ZX_KD,QSAN_ZX_KD))
                {
                    textView.setText("跨度");
                }
                else if(contains(GameCode,QSAN_ZX_HZ,ZSAN_ZX_HZ,HSAN_ZX_HZ))
                {
                    textView.setText("直选和值");
                }
                else if(contains(GameCode,QSAN_ZUX_HZ,ZSAN_ZUX_HZ,HSAN_ZUX_HZ))
                {
                    textView.setText("组选和值");
                }
                else if(contains(GameCode, CQSSC.Longhu.name))
                {
                    textView.setText("龙虎");
                }
                else if(contains(GameCode, CQSSC.Qian2.name, CQSSC.Hou2.name))
                {
                    if(contains(GameCode, QE_ZX_KD,HE_ZX_KD))
                    {
                        textView.setText("直选跨度");
                    }
                    else
                    {
                        if(contains(GameCode, QE_ZX_FS,QE_ZX_HZ,HE_ZX_FS,HE_ZX_HZ))
                        {
                            textView.setText("直选和值");
                        }
                        else if(contains(GameCode, QE_ZUX_FS,QE_ZUX_HZ,QE_ZUX_BD,HE_ZUX_FS,HE_ZUX_HZ,HE_ZUX_BD))
                        {
                            textView.setText("组选和值");
                        }
                    }
                }
                else if(contains(GameCode, WXWF_TM,CQSSC.DingWeiDan.name,CQSSC.RenXuan.name))
                {
                    mainview.getChildAt(mainview.getChildCount() - 1).setVisibility(View.GONE);
                    mainview.getChildAt(mainview.getChildCount() - 2).setVisibility(View.GONE);
                    mainview.getChildAt(mainview.getChildCount() - 3).setVisibility(View.GONE);
                }
                else
                {
                    textView.setText("形态");
                }
            }
            else
            {
                textView.setText("形态");
            }
        }
        else {
            mainview.getChildAt(mainview.getChildCount() - 1).setVisibility(View.GONE);
            mainview.getChildAt(mainview.getChildCount() - 2).setVisibility(View.GONE);
            mainview.getChildAt(mainview.getChildCount() - 3).setVisibility(View.GONE);
        }
    }

    public static RelativeLayout Inititem(LotteryResultList lrl, int color, final Context context, View view) {
        //彩票类型(1:时时彩 2:赛车|飞艇 3:快3 4:11选5 5:排列3|福彩 6:幸运28 7:香港)
        RelativeLayout rl = null;
        if (view == null)
            rl = (RelativeLayout) View.inflate(context, R.layout.item_lottery_history, null);
        else
            rl = (RelativeLayout) view;
        rl.setBackgroundColor(color);

        LinearLayout mainview = (LinearLayout) rl.findViewById(R.id.mainview);
        TextView textView1 = (TextView) mainview.getChildAt(0);
        TextView textView2 = (TextView) mainview.getChildAt(2);
        TextView textView3 = (TextView) mainview.getChildAt(4);
        TextView textView4 = (TextView) mainview.getChildAt(6);
        textView1.setText(lrl.getQsFormat());
        textView2.setText("");
        textView3.setText("");
        textView4.setText("");

        if (lrl.getOpenResult() == null || lrl.getQsFormat().length() == 0) {
            textView1.setText("");
            textView2.setText("");
            textView4.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            return rl;
        }
        if (lrl.getIsOpen() < 1) {
            textView2.setTextColor(context.getResources().getColor(R.color.red3));
            textView2.setText(context.getString(R.string.waiteforOpen));
            if (lrl.getType() == MainLotteryCode.K3.getIndex())//3:快3
            {
                textView3.setText("");
                textView3.setVisibility(View.INVISIBLE);
                textView4.setVisibility(View.GONE);
            } else if (lrl.getType() == MainLotteryCode.XY28.getIndex())//6:幸运28
            {
                textView3.setText("");
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.GONE);
            } else if (lrl.getType() == MainLotteryCode.HK.getIndex())//7:香港
            {
                textView3.setText("");
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.GONE);
            }
            else if(lrl.getType() == MainLotteryCode.SSC.getIndex())//时时彩
            {
                if(lrl.getGameCode()!=null && lrl.getGameCode().contains(WXWF_TM))
                {
                    textView3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                }
                else if(lrl.getGameCode().contains(CQSSC.DingWeiDan.name) || lrl.getGameCode().contains(CQSSC.RenXuan.name))
                {
                    textView3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                }
                else
                {
                    textView3.setText("");
                    textView3.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.GONE);
                }
            }
            else {
                textView3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
            }
            return rl;
        }

        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for (int i = 0; i < lrl.getOpenResult().length; i++) {
            sb.append(lrl.getOpenResult()[i]);
            sb.append("  ");
            sum = sum + Integer.valueOf(lrl.getOpenResult()[i]);
        }
        if (sb.length() > 2)
            sb.deleteCharAt(sb.length() - 2);

        if(lrl.getType()==MainLotteryCode.K3.getIndex())//3:快3
        {
            StringBuilder htmlsb = new StringBuilder();
            int ResoureId[] = {R.drawable.history_num1, R.drawable.history_num2, R.drawable.history_num3,
                    R.drawable.history_num4, R.drawable.history_num5, R.drawable.history_num6};
            for (int i = 0; i < lrl.getOpenResult().length; i++) {
                int index = Integer.valueOf(lrl.getOpenResult()[i]);
                if (index > 0 && index < 7) {
                    htmlsb.append("<img src='" + ResoureId[index - 1] + "'>&nbsp;");
                }
            }
            textView2.setText(Html.fromHtml(htmlsb.toString(), new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    int id = Integer.parseInt(source);
                    Drawable drawable = context.getResources().getDrawable(id);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                    return drawable;
                }
            }, null));
            SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder();
//            大red5
//             小loess5
//            单green3
//             双blue9
            int textcolor=context.getResources().getColor(R.color.blue10);
            String values[]={"大","小","单","双"};
            String temp=String.valueOf(sum);
            spannableStringBuilder.append(temp);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(textcolor), 0, temp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(" ");

            if(sum>10)
            {
                temp=values[0];
                textcolor=context.getResources().getColor(R.color.red5);
            }
            else
            {
                temp=values[1];
                textcolor=context.getResources().getColor(R.color.loess5);
            }
            spannableStringBuilder.append(temp);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(textcolor), spannableStringBuilder.length() - temp.length(), spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(" ");

            if(sum%2==0)
            {
                temp=values[3];
                textcolor=context.getResources().getColor(R.color.blue9);
            }
            else
            {
                temp=values[2];
                textcolor=context.getResources().getColor(R.color.green3);
            }
            spannableStringBuilder.append(temp);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(textcolor), spannableStringBuilder.length() - temp.length(), spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView3.setText(spannableStringBuilder);
            textView4.setVisibility(View.GONE);
        } else if (lrl.getType() == MainLotteryCode.XY28.getIndex())//6:幸运28
        {
            textView2.setText(sb.toString());
            textView2.setTextColor(context.getResources().getColor(R.color.red3));
            textView3.setText(sum + "");
            textView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.GONE);
        } else if (lrl.getType() == MainLotteryCode.HK.getIndex())//7:香港
        {
            sb.delete(sb.length() - 4, sb.length());
            textView2.setText(sb.toString());
            textView2.setTextColor(context.getResources().getColor(R.color.red3));
            textView3.setText(lrl.getOpenResult()[lrl.getOpenResult().length - 1]);
            textView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.GONE);
        }
        else if(lrl.getType() == MainLotteryCode.SSC.getIndex())//时时彩
        {
            if(lrl.getGameCode()!=null)
            {
                if(lrl.getGameCode().contains(WXWF_TM))
                {
                    textView2.setText(sb.toString());
                    textView2.setTextColor(context.getResources().getColor(R.color.red3));
                    textView3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                }
                else if(lrl.getGameCode().contains(CQSSC.DingWeiDan.name) || lrl.getGameCode().contains(CQSSC.RenXuan.name))
                {
                    textView2.setText(sb.toString());
                    textView2.setTextColor(context.getResources().getColor(R.color.blue8));
                    textView3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                }
                else
                {
                    ExtraItemHelper.Itemclass itemclass= ExtraItemHelper.for_SSC(context,lrl);
                    if(itemclass!=null)
                    {
                        textView2.setText(itemclass.getContent());
                        textView2.setVisibility(View.VISIBLE);
                        textView3.setText(itemclass.getXingtai());
                        textView3.setVisibility(View.VISIBLE);
                        textView4.setVisibility(View.GONE);
                    }
                }

            }
        }
        else {
            textView2.setText(sb.toString());
            textView2.setTextColor(context.getResources().getColor(R.color.red3));
            textView3.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
        }
        return rl;
    }
}
