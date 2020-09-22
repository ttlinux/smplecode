package com.lottery.biying.bean;

/**
 * Created by Administrator on 2017/12/15.
 */
public class IndexRecordBean {

    int index1,index2;
    String title;
    MenuBean.GameListBean.ListBean.OddsBean oddsBean;

    public MenuBean.GameListBean.ListBean.OddsBean getOddsBean() {
        return oddsBean;
    }

    public void setOddsBean(MenuBean.GameListBean.ListBean.OddsBean oddsBean) {
        this.oddsBean = oddsBean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex1() {
        return index1;
    }

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public int getIndex2() {
        return index2;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public IndexRecordBean()
    {

    }
    public IndexRecordBean(int index1,int index2)
    {
        setIndex1(index1);
        setIndex2(index2);
    }
}
