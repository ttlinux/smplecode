package com.lottery.biying.util;

import org.json.JSONArray;

/**
 * Created by Administrator on 2018/2/27.
 */
public class HandleOrderCommit {

    public static JSONArray handle(String lotterytype,String gamecode,String args[])
    {
        if(lotterytype.equalsIgnoreCase(LotteryTypeName.SSC))
        {
            return SSC_Method.HandleOrderCommit(gamecode,args);
        }

        return null;
    }
}
