package com.lottery.biying.bean;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/25.
 */
public class OnlinefastPayBean {

    String bigPicUrl;
    String payNname;
    String payNo;
    String payRname;
    String picUrl;
    String smallPicUrl;
    int maxPay,minEdu,payType;
    String payPicpath;
    String payName;
    String minMaxDes;
    String remark;
    String module;
    ArrayList<BankInfo> banklist;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public ArrayList<BankInfo> getBanklist() {
        return banklist;
    }

    public void setBanklist(ArrayList<BankInfo> banklist) {
        this.banklist = banklist;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMinMaxDes() {
        return minMaxDes;
    }

    public void setMinMaxDes(String minMaxDes) {
        this.minMaxDes = minMaxDes;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getSmallPicUrl() {
        return smallPicUrl;
    }

    public void setSmallPicUrl(String smallPicUrl) {
        this.smallPicUrl = smallPicUrl;
    }

    public String getBigPicUrl() {
        return bigPicUrl;
    }

    public void setBigPicUrl(String bigPicUrl) {
        this.bigPicUrl = bigPicUrl;
    }

    public int getmaxPay() {
        return maxPay;
    }

    public void setmaxPay(int maxEdu) {
        this.maxPay = maxEdu;
    }

    public int getMinEdu() {
        return minEdu;
    }

    public void setMinEdu(int minEdu) {
        this.minEdu = minEdu;
    }

    public String getPayNname() {
        return payNname;
    }

    public void setPayNname(String payNname) {
        this.payNname = payNname;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayPic() {
        return payPicpath;
    }

    public void setPayPic(String payPicpath) {
        this.payPicpath = payPicpath;
    }

    public String getPayRname() {
        return payRname;
    }

    public void setPayRname(String payRname) {
        this.payRname = payRname;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public static class BankInfo
    {
        String bankCode;
        String bankImages;
        String bankName;
        String clientType;
        String thirdpayCode;

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBankImages() {
            return bankImages;
        }

        public void setBankImages(String bankImages) {
            this.bankImages = bankImages;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }

        public String getThirdpayCode() {
            return thirdpayCode;
        }

        public void setThirdpayCode(String thirdpayCode) {
            this.thirdpayCode = thirdpayCode;
        }
    }
}
