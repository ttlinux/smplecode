package com.lottery.biying.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/6.
 */
public class ZoushiBean {

    String qishu;
    ArrayList<Integer> indexs;
    ArrayList<Integer> snAmount;
    int Status;
    String openMessage;

    public String getOpenMessage() {
        return openMessage;
    }

    public void setOpenMessage(String openMessage) {
        this.openMessage = openMessage;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public ArrayList<Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs(ArrayList<Integer> indexs) {
        this.indexs = indexs;
    }

    public ArrayList<Integer> getSnAmount() {
        return snAmount;
    }

    public void setSnAmount(ArrayList<Integer> snAmount) {
        this.snAmount = snAmount;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }


}
