package com.lottery.biying.LotteryMethod;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;

import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryResultList;
import com.lottery.biying.util.LogTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 2018/9/1.
 */
public class ExtraItemHelper {

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
    public static final String QE_ZX_KD = "q2_zx_kd";// 前二直选跨度
    // ssc玩法 - 后二
    public static final String HE_ZX_KD = "h2_zx_kd";// 后二直选跨度
    //龙虎
    public static final String LongHu = "lh_lhh";// 龙虎
    //微信玩法
    public static final String WXWF = "wxwf";// 微信玩法
    public static class Itemclass {
        SpannableString content;
        SpannableString xingtai;

        public SpannableString getContent() {
            return content;
        }

        public void setContent(SpannableString content) {
            this.content = content;
        }

        public SpannableString getXingtai() {
            return xingtai;
        }

        public void setXingtai(SpannableString xingtai) {
            this.xingtai = xingtai;
        }
    }

    public static Itemclass for_SSC(Context context, LotteryResultList lrl) {
        String nums[] = lrl.getOpenResult();
        if (nums == null) return null;
        Itemclass itemclass = new Itemclass();

        LogTools.e("lrl.getGameCode()", lrl.getGameCode());
        if (lrl.getGameCode().contains(AnalysisType.CQSSC.WuXing.name)) {
            itemclass = WuXing(context, itemclass, nums);
        } else if (lrl.getGameCode().contains(AnalysisType.CQSSC.Qian4.name)) {
            itemclass = Four_numbers(context, itemclass, nums, true);
        } else if (lrl.getGameCode().contains(AnalysisType.CQSSC.Hou4.name)) {
            itemclass = Four_numbers(context, itemclass, nums, false);
        } else if (lrl.getGameCode().contains(AnalysisType.CQSSC.Qian3.name)) {
            if (eq(lrl.getGameCode(), QSAN_ZX_KD)) {
                //跨度
                itemclass = Three_numbers(context, itemclass, nums, 0, 1);
            } else if (eq(lrl.getGameCode(), QSAN_ZX_HZ, QSAN_ZUX_HZ)) {
                //和值
                itemclass = Three_numbers(context, itemclass, nums, 0, 2);
            } else {
                itemclass = Three_numbers(context, itemclass, nums, 0, 0);
            }
        } else if (lrl.getGameCode().contains(AnalysisType.CQSSC.Zhong3.name)) {
            if (eq(lrl.getGameCode(), ZSAN_ZX_KD)) {
                //跨度
                itemclass = Three_numbers(context, itemclass, nums, 1, 1);
            } else if (eq(lrl.getGameCode(), ZSAN_ZX_HZ, ZSAN_ZUX_HZ)) {
                //和值
                itemclass = Three_numbers(context, itemclass, nums, 1, 2);
            } else {
                itemclass = Three_numbers(context, itemclass, nums, 1, 0);
            }
        } else if (lrl.getGameCode().contains(AnalysisType.CQSSC.Hou3.name)) {
            if (eq(lrl.getGameCode(), HSAN_ZX_KD)) {
                itemclass = Three_numbers(context, itemclass, nums, 2, 1);
                //跨度
            } else if (eq(lrl.getGameCode(), HSAN_ZX_HZ, HSAN_ZUX_HZ)) {
                //和值
                itemclass = Three_numbers(context, itemclass, nums, 2, 2);
            } else {
                itemclass = Three_numbers(context, itemclass, nums, 2, 0);
            }
        } else if (lrl.getGameCode().contains(AnalysisType.CQSSC.Qian2.name)) {
            if (lrl.getGameCode().contains(QE_ZX_KD))
                itemclass = Two_numbers(context, itemclass, nums, 0, 0);
            else
                itemclass = Two_numbers(context, itemclass, nums, 0, 1);

        } else if (lrl.getGameCode().contains(AnalysisType.CQSSC.Hou2.name)) {
            if (lrl.getGameCode().contains(HE_ZX_KD))
                itemclass = Two_numbers(context, itemclass, nums, 1, 0);
            else
                itemclass = Two_numbers(context, itemclass, nums, 1, 1);
        } else if (lrl.getGameCode().contains(LongHu)) {
            String titles[] = context.getResources().getStringArray(R.array.longhu_titles);
            String values[] = context.getResources().getStringArray(R.array.longhu_titles_values);
            for (int i = 0; i < titles.length; i++) {
                if (lrl.getGameCode().contains(titles[i])) {
                    String value = values[i];
                    int start = value.charAt(0)- '0';
                    int end = value.charAt(1)- '0';
                    itemclass = Longhu(context, itemclass, nums, start, end);
                    break;
                }
            }
        }
        else if(lrl.getGameCode().contains(WXWF))
        {
            String titles[]=context.getResources().getStringArray(R.array.weixin_titles);
            for (int i = 0; i < titles.length; i++) {
                if(lrl.getGameCode().contains(titles[i]))
                {
                    itemclass = weixin(context, itemclass, nums, i);
                    break;
                }
            }
        }
        return itemclass;
    }

    public static Itemclass weixin(Context context, Itemclass itemclass, String nums[], int index)
    {
        if (nums.length != 5) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            sb.append("  ");
        }

        int number=Integer.valueOf(nums[index]);
        String titles[]={"大","小","单","双","和"};
        StringBuilder titlesb=new StringBuilder();
        if(number>4)
        {
            titlesb.append(titles[0]);
            titlesb.append(" ");
        }
        if(number<5)
        {
            titlesb.append(titles[1]);
            titlesb.append(" ");
        }
        if(number%2==0)
        {
            titlesb.append(titles[3]);
            titlesb.append(" ");
        }
        else
        {
            titlesb.append(titles[2]);
            titlesb.append(" ");
        }
        if(number==0 || number==5)
        {
            titlesb.append(titles[4]);
            titlesb.append(" ");
        }

        SpannableString spstr = new SpannableString(titlesb.toString());
        spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray19)), 0, titlesb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemclass.setXingtai(spstr);

        SpannableString content = new SpannableString(sb.toString());
        content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), 0,
                sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), index*3,
                index*3+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemclass.setContent(content);

        return itemclass;
    }

    public static Itemclass Longhu(Context context, Itemclass itemclass, String nums[], int start, int end) {
        if (nums.length != 5) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            sb.append("  ");
        }
        int startnumber = Integer.valueOf(nums[start]);
        int endnumber = Integer.valueOf(nums[end]);


        String lhh[] = {"龙", "虎", "和"};
        String xingtai = null;
        if (startnumber == endnumber) {
            xingtai = lhh[2];
        } else {
            if (startnumber > endnumber)
                xingtai = lhh[0];
            else
                xingtai = lhh[1];
        }
        SpannableString spstr = new SpannableString(xingtai);
        spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray19)), 0, xingtai.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemclass.setXingtai(spstr);

        SpannableString content = new SpannableString(sb.toString());
        content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), 0,
                sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), start*3,
                start*3+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), end*3,
                end*3+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemclass.setContent(content);
        return itemclass;
    }

    public static Itemclass WuXing(Context context, Itemclass itemclass, String nums[]) {
        String strs[] = context.getResources().getStringArray(R.array.game_name_wuxing);
        if (nums.length != 5) return null;
        ArrayList<Integer> numbers = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            sb.append("  ");
            numbers.add(Integer.valueOf(nums[i]));
        }
        Collections.sort(numbers, new comparer());//小到大
        SparseArray<Integer> result = sort(numbers);

        //1 1 1 1 1 组选120
        //2 1 1 1  组选60
        //2 2 1  组选30
        //3 1 1 组选20
        //3 2 组选10
        //4 1 组选5
        //5
        if (result.size() > 0) {
            int multiple = 1;
            for (int i = 0; i < result.size(); i++) {
                multiple = multiple * result.valueAt(i);
            }
            String xingtai = "";
            switch (result.size()) {
                case 1:
                    //5个一样 --
                    xingtai = strs[0];
                    break;
                case 2:
                    if (multiple == 4) {
                        // 组选5
                        xingtai = strs[1];
                    } else if (multiple == 6) {
                        //组选10
                        xingtai = strs[2];
                    }
                    break;
                case 3:
                    if (multiple == 4) {
                        // 组选30
                        xingtai = strs[4];
                    } else if (multiple == 3) {
                        //组选20
                        xingtai = strs[3];
                    }
                    break;
                case 4:
                    //组选60
                    xingtai = strs[5];
                    break;
                case 5:
                    //组选120
                    xingtai = strs[6];
                    break;
            }
            SpannableString spstr = new SpannableString(xingtai);
            spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray19)), 0, xingtai.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemclass.setXingtai(spstr);

            SpannableString content = new SpannableString(sb.toString());
            content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 0, sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemclass.setContent(content);
        }
        return itemclass;
    }

    public static Itemclass Four_numbers(Context context, Itemclass itemclass, String nums[], boolean isQ) {
        //isq true 前4 false 后4

        String strs[] = context.getResources().getStringArray(R.array.game_name_four);
        if (nums.length != 5) return null;
        ArrayList<Integer> numbers = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            sb.append("  ");
            if (isQ && i < nums.length - 1) {
                numbers.add(Integer.valueOf(nums[i]));
            }
            if (!isQ && i > 0) {
                numbers.add(Integer.valueOf(nums[i]));
            }

        }
        Collections.sort(numbers, new comparer());//小到大 4个数
        SparseArray<Integer> result = sort(numbers);
        //3 1 组选4
        //2 2 组选6
        //2 1 1 组选12
        //1 1 1 1 组选24
        if (result.size() > 0) {
            int multiple = 1;
            for (int i = 0; i < result.size(); i++) {
                multiple = multiple * result.valueAt(i);
            }
            String xingtai = "";
            switch (result.size()) {
                case 1:
                    //4个一样 --
                    xingtai = strs[0];
                    break;
                case 2:
                    if (multiple == 3) {
                        // 组选4
                        xingtai = strs[1];
                    } else if (multiple == 4) {
                        //组选6
                        xingtai = strs[2];
                    }
                    break;
                case 3:
                    // 组选12
                    xingtai = strs[3];
                    break;
                case 4:
                    //组选24
                    xingtai = strs[4];
                    break;
            }
            SpannableString spstr = new SpannableString(xingtai);
            spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray19)), 0, xingtai.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemclass.setXingtai(spstr);

            SpannableString content = new SpannableString(sb.toString());
            if (isQ) {
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 0,
                        sb.toString().length() - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), sb.toString().length() - 3,
                        sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), 0,
                        3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 3,
                        sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            itemclass.setContent(content);
        } else {
            return null;
        }
        return itemclass;

    }

    public static Itemclass Three_numbers(Context context, Itemclass itemclass, String nums[], int type, int handletype) {
        //type 0 前 1 中 2 后
        //handletype 0 形态 1跨度 2和值
        String strs[] = context.getResources().getStringArray(R.array.game_name_three);
        if (nums.length != 5) return null;
        ArrayList<Integer> numbers = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            sb.append("  ");
            switch (type) {
                case 0:
                    if (i < 3) {
                        numbers.add(Integer.valueOf(nums[i]));
                    }
                    break;
                case 1:
                    if (i > 0 && i < 4) {
                        numbers.add(Integer.valueOf(nums[i]));
                    }
                    break;
                case 2:
                    if (i > 1) {
                        numbers.add(Integer.valueOf(nums[i]));
                    }
                    break;
            }
        }
        Collections.sort(numbers, new comparer());//小到大
        SparseArray<Integer> result = sort(numbers);

        //1 1 1 组六
        //2 1 组三
        //3 --
        String xingtai = "";
        switch (handletype) {
            case 0:
                switch (result.size()) {
                    case 1:
                        xingtai = strs[0];
                        break;
                    case 2:
                        xingtai = strs[1];
                        break;
                    case 3:
                        xingtai = strs[2];
                        break;
                }
                break;
            case 1:
                xingtai = String.valueOf(numbers.get(numbers.size() - 1) - numbers.get(0));
                break;
            case 2:
                int value = 0;
                for (int i = 0; i < numbers.size(); i++) {
                    value = value + numbers.get(i);
                }
                xingtai = String.valueOf(value);
                break;
        }


        SpannableString spstr = new SpannableString(xingtai);
        spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray19)), 0, xingtai.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemclass.setXingtai(spstr);

        SpannableString content = new SpannableString(sb.toString());
        switch (type) {
            case 0:
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 0,
                        sb.toString().length() - 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), sb.toString().length() - 6,
                        sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 1:
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 3,
                        sb.toString().length() - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), 0,
                        3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), sb.toString().length() - 3,
                        sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 2:
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), 0,
                        6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 6,
                        sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }
        itemclass.setContent(content);
        return itemclass;
    }

    public static Itemclass Two_numbers(Context context, Itemclass itemclass, String nums[], int type, int handletype) {
        //type 0 前2  1 后2
        //handletype 0 跨度 1 普通和值
        if (nums.length != 5) return null;
        ArrayList<Integer> numbers = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            sb.append("  ");
            switch (type) {
                case 0:
                    if (i < 2) {
                        numbers.add(Integer.valueOf(nums[i]));
                    }
                    break;
                case 1:
                    if (i > 2) {
                        numbers.add(Integer.valueOf(nums[i]));
                    }
                    break;
            }
        }

        SpannableString content = new SpannableString(sb.toString());
        if (type < 1) {
            content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 0,
                    6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), 6,
                    sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue8)), 0,
                    9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red3)), 9,
                    sb.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        itemclass.setContent(content);


        if (handletype == 0) {
            //跨度
            String value = String.valueOf(Math.abs(numbers.get(0) - numbers.get(1)));
            SpannableString spstr = new SpannableString(value);
            spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray19)), 0, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemclass.setXingtai(spstr);
        } else {
            //和值
            String value = String.valueOf(numbers.get(0) + numbers.get(1));
            SpannableString spstr = new SpannableString(value);
            spstr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray19)), 0, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemclass.setXingtai(spstr);
        }

        return itemclass;
    }

    public static SparseArray<Integer> sort(ArrayList<Integer> numbers) {
        int compareindex = 1;
        int count = 0;
        SparseArray<Integer> result = new SparseArray<>();
        result.put(0, compareindex);
        for (int i = 0; i < numbers.size(); i++) {
            if (i < numbers.size() - 1) {
                if (numbers.get(i) == numbers.get(i + 1)) {
                    compareindex++;
                } else {
                    compareindex = 1;
                    count++;
                }
                result.put(count, compareindex);
            }
        }
        return result;
    }

    public static class comparer implements Comparator<Integer> {

        @Override
        public int compare(Integer i, Integer ii) {
            return i > ii ? 1 : -1;
        }
    }

    private static boolean eq(String gamecode, String... strs) {
        boolean temp = false;
        for (int i = 0; i < strs.length; i++) {
            temp = gamecode.contains(strs[i]);
            if (temp) return temp;
        }
        return temp;
    }
}
