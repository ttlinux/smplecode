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
import com.lottery.biying.util.LogTools;
import com.lottery.biying.view.LotteryButton;
import com.lottery.biying.view.LotteryButton;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */
public class AnalysisBets {

    public static int Caculatebets(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, String CurrentLottery) {
        int bets = 0;
        if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SSC.getIndex()) {
            bets = CQSSC(numbers, gamecode, CurrentLotteryMainCode, bets);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SYXW.getIndex()) {
            bets = GDSYXW(numbers, gamecode, CurrentLotteryMainCode, bets);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.Saiche.getIndex()) {
            bets = BJSC_FT(numbers, gamecode, CurrentLotteryMainCode, bets);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.PL3.getIndex()) {
            return FC3D_PL3(numbers, gamecode, CurrentLotteryMainCode, bets);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.XY28.getIndex()) {
            return XY28(numbers, gamecode, CurrentLotteryMainCode, bets);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.HK.getIndex()) {
            return LHC(numbers, gamecode, CurrentLotteryMainCode, bets);
        }
        return bets;
    }

    private static int XY28(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, int bets) {
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.TM.name)) {
            TEMA um = TEMA.getInstance();
            bets = um.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(um.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.DWD.name)|| CurrentLotteryMainCode.contains(AnalysisType.XY28.BDW.name)) {
            DWDAndBDW DDD = DWDAndBDW.getInstance();
            bets = DDD.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(DDD.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.SX.name)) {
            SanXing sx = SanXing.getInstance();
            bets = sx.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(sx.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.EX.name)) {
            ErXing erXing = ErXing.getInstance();
            bets = erXing.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(erXing.getSelectNumbers());
        }
        return bets;
    }

    private static int LHC(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, int bets) {
            LHC lhc = LHC.getInstance();
            bets = lhc.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(lhc.getSelectNumbers());
        return bets;
    }

    private static int CQSSC(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, int bets) {
        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.WuXing.name)) {
            Wuxing wuxing = Wuxing.getInstance();
            bets = wuxing.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(wuxing.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian4.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou4.name)) {
            QiansiAndHousi qiansiAndHousi = QiansiAndHousi.getInstance();
            bets = qiansiAndHousi.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(qiansiAndHousi.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian3.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Zhong3.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou3.name)) {
            LogTools.e("CurrentLotteryMainCode", CurrentLotteryMainCode);
            Qian_Zhong_Hou_San qianZhongHouSan = Qian_Zhong_Hou_San.getInstance();
            bets = qianZhongHouSan.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(qianZhongHouSan.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian2.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou2.name)) {
            Qian_Hou_Er qian_hou_er = Qian_Hou_Er.getInstance();
            bets = qian_hou_er.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(qian_hou_er.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.DingWeiDan.name)) {
            DingweiDan dingweiDan = DingweiDan.getInstance();
            bets = dingweiDan.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(dingweiDan.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.RenXuan.name)) {
            RenXuan renXuan = RenXuan.getInstance();
            bets = renXuan.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(renXuan.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Longhu.name)) {
            DragonTiger dragonTiger = DragonTiger.getInstance();
            bets = dragonTiger.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(dragonTiger.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.WeChat.name)) {
            Wechat wechat = Wechat.getInstance();
            bets = wechat.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(wechat.getSelectNumbers());
        }

        return bets;
    }

    private static int GDSYXW(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, int bets) {
        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X1.name)) {
            X1 x1 = X1.getInstance();
            bets = x1.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(x1.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X2.name)) {
            X2 x2 = X2.getInstance();
            bets = x2.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(x2.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X3.name)) {
            X3 x3 = X3.getInstance();
            bets = x3.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(x3.getSelectNumbers());
        }
        if (contains(CurrentLotteryMainCode,
                AnalysisType.GDSYXW.X4.name, AnalysisType.GDSYXW.X5.name,
                AnalysisType.GDSYXW.X6.name, AnalysisType.GDSYXW.X7.name, AnalysisType.GDSYXW.X8.name)) {
            X45678 xx = X45678.getInstance();
            bets = xx.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(xx.getSelectNumbers());
        }
        return bets;
    }

    private static int BJSC_FT(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, int bets) {
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.DWD.name)) {
            DWD dwd = DWD.getInstance();
            bets = dwd.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(dwd.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.LMP.name)) {
            LMP lmp = LMP.getInstance();
            bets = lmp.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(lmp.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.Q2.name)) {
            Q2 q2 = Q2.getInstance();
            bets = q2.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(q2.getSelectNumbers());
        }
        if (contains(CurrentLotteryMainCode, AnalysisType.BJSC.Q3.name, AnalysisType.BJSC.Q4.name, AnalysisType.BJSC.Q5.name)) {
            Q345 qx = Q345.getInstance();
            bets = qx.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(qx.getSelectNumbers());
        }
        return bets;
    }

    private static int FC3D_PL3(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, int bets) {
        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.DWD.name)) {
            FC3D_DWD dwd = FC3D_DWD.getInstance();
            bets = dwd.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(dwd.getSelectNumbers());
        }

        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.Q2.name, AnalysisType.FC3D.H2.name)) {
            FC3D_Qian_Hou_2 fqh2 = FC3D_Qian_Hou_2.getInstance();
            bets = fqh2.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(fqh2.getSelectNumbers());
        }

        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.SX.name)) {
            FC3D_Sanxing fsx = FC3D_Sanxing.getInstance();
            bets = fsx.CaculateOrderAmount(numbers, gamecode);
            AnalysisType.setSelectNumbers(fsx.getSelectNumbers());
        }

        return bets;
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
}
