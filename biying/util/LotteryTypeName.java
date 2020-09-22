package com.lottery.biying.util;

/**
 * Created by Administrator on 2018/1/26.
 */
public class LotteryTypeName {
    public static final String SSC="ssc";
    public static final String dxds="dxds";
    public static final String PL3="pl3";
    public static final String PL5="pl5";
    public static final String FuCai3D="fcsd";
    public static final String QiXingCai="qxc";

    public static enum LotteryMainType
    {
        CQSSC("cqssc",0),TJSSC("tjssc",1),
        XJSSC("xjssc",2),TXFFC("txffc",3);
        private String name;
        private int index;
        // 构造方法
        private LotteryMainType(String name,int index) {
            this.name = name;
            this.index=index;
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

    public static enum Wuxing
    {
        FuShi(""),Danshi("");
        private String name;
        // 构造方法
        private Wuxing(String name) {
            this.name = name;
        }
        //覆盖方法
        @Override
        public String toString() {
            return this.name;
        }
    }
}
