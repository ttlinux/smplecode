package com.lottery.biying.LotteryMethod;

import com.lottery.biying.LotteryMethod.BJSC.DWD;
import com.lottery.biying.LotteryMethod.BJSC.LMP;
import com.lottery.biying.LotteryMethod.BJSC.Q2;
import com.lottery.biying.LotteryMethod.BJSC.Q345;
import com.lottery.biying.LotteryMethod.CQSSC.DingweiDan;
import com.lottery.biying.LotteryMethod.CQSSC.DragonTiger;
import com.lottery.biying.LotteryMethod.CQSSC.Qian_Hou_Er;
import com.lottery.biying.LotteryMethod.CQSSC.Qian_Zhong_Hou_San;
import com.lottery.biying.LotteryMethod.CQSSC.QiansiAndHousi;
import com.lottery.biying.LotteryMethod.CQSSC.RenXuan;
import com.lottery.biying.LotteryMethod.CQSSC.Wechat;
import com.lottery.biying.LotteryMethod.CQSSC.Wuxing;
import com.lottery.biying.LotteryMethod.FC3D.FC3D_DWD;
import com.lottery.biying.LotteryMethod.FC3D.FC3D_Qian_Hou_2;
import com.lottery.biying.LotteryMethod.FC3D.FC3D_Sanxing;
import com.lottery.biying.LotteryMethod.GDSYXW.X1;
import com.lottery.biying.LotteryMethod.GDSYXW.X2;
import com.lottery.biying.LotteryMethod.GDSYXW.X3;
import com.lottery.biying.LotteryMethod.GDSYXW.X45678;
import com.lottery.biying.LotteryMethod.LHC.LHC;
import com.lottery.biying.LotteryMethod.XY28.DWDAndBDW;
import com.lottery.biying.LotteryMethod.XY28.ErXing;
import com.lottery.biying.LotteryMethod.XY28.SanXing;
import com.lottery.biying.LotteryMethod.XY28.TEMA;
import com.lottery.biying.view.LotteryButton;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */
public class AnalusisBtnStatus {

    public static boolean CaculateStatus(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode, String CurrentLotteryMainCode, String CurrentLottery) {
        if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SSC.getIndex()) {
            return CQSSC(numbers, buttonNumber, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SYXW.getIndex()) {
            return GDSYXW(numbers, buttonNumber, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.Saiche.getIndex()) {
            return BJSC_FT(numbers, buttonNumber, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.PL3.getIndex()) {
            return FC3D_PL3(numbers, buttonNumber, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.XY28.getIndex()) {
            return XY28(numbers, buttonNumber, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.HK.getIndex()) {
            return LHC(numbers, buttonNumber, gamecode, CurrentLotteryMainCode);
        }
        return true;
    }

    private static boolean eq(String gamecode, String... strs) {
        boolean temp = false;
        for (int i = 0; i < strs.length; i++) {
            temp = gamecode.equalsIgnoreCase(strs[i]);
            if (temp) return temp;
        }
        return temp;
    }

    private static boolean contains(String gamecode, String... strs) {
        boolean temp = false;
        for (int i = 0; i < strs.length; i++) {
            temp = gamecode.contains(strs[i]);
            if (temp) return temp;
        }
        return temp;
    }

    private static boolean CQSSC(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode, String CurrentLotteryMainCode) {
        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.WuXing.name)) {
            Wuxing wuxing = Wuxing.getInstance();
            return wuxing.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian4.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou4.name)) {
            QiansiAndHousi qiansiAndHousi = QiansiAndHousi.getInstance();
            return qiansiAndHousi.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian3.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Zhong3.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou3.name)) {
            Qian_Zhong_Hou_San qianZhongHouSan = Qian_Zhong_Hou_San.getInstance();
            return qianZhongHouSan.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian2.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou2.name)) {
            Qian_Hou_Er qian_hou_er = Qian_Hou_Er.getInstance();
            return qian_hou_er.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.DingWeiDan.name)) {
            DingweiDan dingweiDan = DingweiDan.getInstance();
            return dingweiDan.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.RenXuan.name)) {
            RenXuan renXuan = RenXuan.getInstance();
            return renXuan.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Longhu.name)) {
            DragonTiger dragonTiger = DragonTiger.getInstance();
            return dragonTiger.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.WeChat.name)) {
            Wechat wechat = Wechat.getInstance();
            return wechat.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        return true;
    }

    private static boolean GDSYXW(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode, String CurrentLotteryMainCode) {
        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X1.name)) {
            X1 x1 = X1.getInstance();
            return x1.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X2.name)) {
            X2 x2 = X2.getInstance();
            return x2.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X3.name)) {
            X3 x3 = X3.getInstance();
            return x3.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        if (contains(CurrentLotteryMainCode,
                AnalysisType.GDSYXW.X4.name, AnalysisType.GDSYXW.X5.name,
                AnalysisType.GDSYXW.X6.name, AnalysisType.GDSYXW.X7.name, AnalysisType.GDSYXW.X8.name)) {
            X45678 xx = X45678.getInstance();
            return xx.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        return true;
    }

    private static boolean BJSC_FT(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode, String CurrentLotteryMainCode) {
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.DWD.name)) {
            DWD dwd = DWD.getInstance();
            return dwd.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.LMP.name)) {
            LMP lmp = LMP.getInstance();
            return lmp.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.Q2.name)) {
            Q2 q2 = Q2.getInstance();
            return q2.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        if (contains(CurrentLotteryMainCode, AnalysisType.BJSC.Q3.name, AnalysisType.BJSC.Q4.name, AnalysisType.BJSC.Q5.name)) {
            Q345 qx = Q345.getInstance();
            return qx.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        return true;
    }

    private static boolean FC3D_PL3(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode, String CurrentLotteryMainCode) {
        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.DWD.name)) {
            FC3D_DWD dwd = FC3D_DWD.getInstance();
            return dwd.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.Q2.name, AnalysisType.FC3D.H2.name)) {
            FC3D_Qian_Hou_2 fqh2 = FC3D_Qian_Hou_2.getInstance();
            return fqh2.NumberSelectRule(numbers, buttonNumber, gamecode);
        }

        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.SX.name)) {
            FC3D_Sanxing fsx = FC3D_Sanxing.getInstance();
            return fsx.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        return true;
    }

    private static boolean XY28(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode, String CurrentLotteryMainCode) {
        if (contains(CurrentLotteryMainCode, AnalysisType.XY28.TM.name)) {
            TEMA um = TEMA.getInstance();
            return um.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        if (contains(CurrentLotteryMainCode, AnalysisType.XY28.DWD.name,CurrentLotteryMainCode, AnalysisType.XY28.BDW.name)) {
            DWDAndBDW ddd = DWDAndBDW.getInstance();
            return ddd.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        if (contains(CurrentLotteryMainCode, AnalysisType.XY28.EX.name)) {
            ErXing erXing = ErXing.getInstance();
            return erXing.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        if (contains(CurrentLotteryMainCode, AnalysisType.XY28.SX.name)) {
            SanXing sanXing = SanXing.getInstance();
            return sanXing.NumberSelectRule(numbers, buttonNumber, gamecode);
        }
        return true;
    }

    private static boolean LHC(ArrayList<ArrayList<LotteryButton>> numbers, LotteryButton buttonNumber, String gamecode, String CurrentLotteryMainCode) {
            LHC lhc = LHC.getInstance();
            return lhc.NumberSelectRule(numbers, buttonNumber, gamecode);
    }
}
