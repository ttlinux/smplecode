package com.lottery.biying.util;

import com.lottery.biying.view.LotteryButton;
import com.lottery.biying.view.LotteryButton;

public interface LotteryButtonOnCheckListener {
    public boolean OnChecking(LotteryButton buttonNumber, boolean status);//需要改变状态的话返回true
    public void OnChecked(LotteryButton buttonNumber, boolean status);
}
