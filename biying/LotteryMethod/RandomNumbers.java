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
public class RandomNumbers {

    public static void randomnumbers(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode, String CurrentLottery) {
        if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SSC.getIndex()) {
            CQSSC(numbers, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.SYXW.getIndex()) {
            GDSYXW(numbers, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.Saiche.getIndex()) {
            BJSC_FT(numbers, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.PL3.getIndex()) {
            FC3D_PL3(numbers, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.XY28.getIndex()) {
            XY28(numbers, gamecode, CurrentLotteryMainCode);
        } else if (AnalysisType.getLottertCategory() == AnalysisType.MainLotteryCode.HK.getIndex()) {
            LHC(numbers, gamecode, CurrentLotteryMainCode);
        }
    }

    private static void XY28(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode) {
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.TM.name)) {
            TEMA um = TEMA.getInstance();
            um.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(um.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.DWD.name)||CurrentLotteryMainCode.contains(AnalysisType.XY28.BDW.name)) {
            DWDAndBDW ddd = DWDAndBDW.getInstance();
            ddd.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(ddd.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.SX.name)) {
            SanXing sanXing = SanXing.getInstance();
            sanXing.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(sanXing.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.XY28.EX.name)) {
            ErXing erXing = ErXing.getInstance();
            erXing.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(erXing.getSelectNumbers());
        }
    }

    private static void LHC(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode) {
            LHC lhc = LHC.getInstance();
            lhc.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(lhc.getSelectNumbers());
    }

    private static void CQSSC(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode) {
        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.WuXing.name)) {
            Wuxing wuxing = Wuxing.getInstance();
            wuxing.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(wuxing.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian4.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou4.name)) {
            QiansiAndHousi qiansiAndHousi = QiansiAndHousi.getInstance();
            qiansiAndHousi.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(qiansiAndHousi.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian3.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Zhong3.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou3.name)) {
            Qian_Zhong_Hou_San qianZhongHouSan = Qian_Zhong_Hou_San.getInstance();
            qianZhongHouSan.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(qianZhongHouSan.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Qian2.name) || CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Hou2.name)) {
            Qian_Hou_Er qian_hou_er = Qian_Hou_Er.getInstance();
            qian_hou_er.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(qian_hou_er.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.DingWeiDan.name)) {
            DingweiDan dingweiDan = DingweiDan.getInstance();
            dingweiDan.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(dingweiDan.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.RenXuan.name)) {
            RenXuan renXuan = RenXuan.getInstance();
            renXuan.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(renXuan.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.Longhu.name)) {
            DragonTiger dragonTiger = DragonTiger.getInstance();
            dragonTiger.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(dragonTiger.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.CQSSC.WeChat.name)) {
            Wechat wechat = Wechat.getInstance();
            wechat.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(wechat.getSelectNumbers());
        }
    }

    private static void GDSYXW(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode) {
        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X1.name)) {
            X1 x1 = X1.getInstance();
            x1.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(x1.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X2.name)) {
            X2 x2 = X2.getInstance();
            x2.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(x2.getSelectNumbers());
        }

        if (CurrentLotteryMainCode.contains(AnalysisType.GDSYXW.X3.name)) {
            X3 x3 = X3.getInstance();
            x3.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(x3.getSelectNumbers());
        }
        if (contains(CurrentLotteryMainCode,
                AnalysisType.GDSYXW.X4.name, AnalysisType.GDSYXW.X5.name,
                AnalysisType.GDSYXW.X6.name, AnalysisType.GDSYXW.X7.name, AnalysisType.GDSYXW.X8.name)) {
            X45678 xx = X45678.getInstance();
            xx.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(xx.getSelectNumbers());
        }
    }

    private static void BJSC_FT(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode) {
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.DWD.name)) {
            DWD dwd = DWD.getInstance();
            dwd.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(dwd.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.LMP.name)) {
            LMP lmp = LMP.getInstance();
            lmp.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(lmp.getSelectNumbers());
        }
        if (CurrentLotteryMainCode.contains(AnalysisType.BJSC.Q2.name)) {
            Q2 q2 = Q2.getInstance();
            q2.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(q2.getSelectNumbers());
        }
        if (contains(CurrentLotteryMainCode, AnalysisType.BJSC.Q3.name, AnalysisType.BJSC.Q4.name, AnalysisType.BJSC.Q5.name)) {
            Q345 qx = Q345.getInstance();
            qx.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(qx.getSelectNumbers());
        }
    }

    private static void FC3D_PL3(ArrayList<ArrayList<LotteryButton>> numbers, String gamecode, String CurrentLotteryMainCode) {
        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.DWD.name)) {
            FC3D_DWD dwd = FC3D_DWD.getInstance();
            dwd.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(dwd.getSelectNumbers());
        }

        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.Q2.name, AnalysisType.FC3D.H2.name)) {
            FC3D_Qian_Hou_2 fqh2 = FC3D_Qian_Hou_2.getInstance();
            fqh2.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(fqh2.getSelectNumbers());
        }

        if (contains(CurrentLotteryMainCode, AnalysisType.FC3D.SX.name)) {
            FC3D_Sanxing fsx = FC3D_Sanxing.getInstance();
            fsx.RandomNumbers(numbers, gamecode);
            AnalysisType.setSelectNumbers(fsx.getSelectNumbers());
        }
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
