package com.lottery.biying.LotteryMethod;

import android.util.SparseArray;

import com.lottery.biying.view.LotteryButton;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/9.
 */
public abstract class BaseAnalysisClass {

    public SparseArray<ArrayList<LotteryButton>> SelectNumbers=new SparseArray<>();
    public abstract int CaculateOrderAmount(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode);
    public abstract boolean NumberSelectRule(ArrayList<ArrayList<LotteryButton>> Numbers,LotteryButton LotteryButton,String GameCode);
    public abstract void RandomNumbers(ArrayList<ArrayList<LotteryButton>> Numbers,String GameCode);
    public abstract JSONObject Formatnumber(SparseArray<ArrayList<LotteryButton>> SelectNumbers,String GameCode);

    public  SparseArray<ArrayList<LotteryButton>> getSelectNumbers() {
        return SelectNumbers;
    }

    public  void setSelectNumbers(SparseArray<ArrayList<LotteryButton>> selectNumbers) {
        SelectNumbers = selectNumbers;
    }
}
