package com.lottery.biying.bean;

/**
 * Created by Administrator on 2018/4/16.
 */
public class BounsBean {


    /**
     * bonusName : 高返
     * bonusType : 1
     * select : 0
     * typeBtn :
     */

    private String bonusName;
    private int bonusType;
    private String select;
    private String typeBtn;
    double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getBonusName() {
        return bonusName;
    }

    public void setBonusName(String bonusName) {
        this.bonusName = bonusName;
    }

    public int getBonusType() {
        return bonusType;
    }

    public void setBonusType(int bonusType) {
        this.bonusType = bonusType;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getTypeBtn() {
        return typeBtn;
    }

    public void setTypeBtn(String typeBtn) {
        this.typeBtn = typeBtn;
    }


}
