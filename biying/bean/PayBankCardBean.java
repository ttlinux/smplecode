package com.lottery.biying.bean;

/**
 * Created by Administrator on 2017/5/3.
 */
public class PayBankCardBean {

    public static class PayBankCardBean1
    {
        //银行卡
        /**
         * id : 15
         * payNo : b15
         * isFastPay : false
         * hkCompanyBank : 建设银行-王刚
         * bankType : 建设银行
         * minEdu : 100
         * bankUser : 王刚
         * bankAddress : 福建福州鼓楼
         * bankCode : 95533
         * bankCard : 787878786766878
         */

        private int id;
        private String payNo;
        private boolean isFastPay;
        private String hkCompanyBank;
        private String bankType;
        private int minEdu;
        private String bankUser;
        private String bankAddress;
        private String bankCode;
        private String bankCard;
        private String maxPay;
        private String bigPic;
        private String payLink;
        private String payType;
        private String smallPic;
        private String minMaxDes;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getMaxPay() {
            return maxPay;
        }

        public void setMaxPay(String maxPay) {
            this.maxPay = maxPay;
        }

        public String getMinMaxDes() {
            return minMaxDes;
        }

        public void setMinMaxDes(String minMaxDes) {
            this.minMaxDes = minMaxDes;
        }

        public String getSmallPic() {
            return smallPic;
        }

        public void setSmallPic(String smallPic) {
            this.smallPic = smallPic;
        }

        public String getBigPic() {
            return bigPic;
        }

        public void setBigPic(String bigPic) {
            this.bigPic = bigPic;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayLink() {
            return payLink;
        }

        public void setPayLink(String payLink) {
            this.payLink = payLink;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPayNo() {
            return payNo;
        }

        public void setPayNo(String payNo) {
            this.payNo = payNo;
        }

        public boolean isIsFastPay() {
            return isFastPay;
        }

        public void setIsFastPay(boolean isFastPay) {
            this.isFastPay = isFastPay;
        }

        public String getHkCompanyBank() {
            return hkCompanyBank;
        }

        public void setHkCompanyBank(String hkCompanyBank) {
            this.hkCompanyBank = hkCompanyBank;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public int getMinEdu() {
            return minEdu;
        }

        public void setMinEdu(int minEdu) {
            this.minEdu = minEdu;
        }

        public String getBankUser() {
            return bankUser;
        }

        public void setBankUser(String bankUser) {
            this.bankUser = bankUser;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }
    }

    public static class PayBankCardBean2
    {
        //快捷支付
        /**
         * maxEdu : 110
         * picUrl : s
         * payNo : k2
         * payNname : 诚信赢天下   支付宝
         * isFastPay : true
         * payRnameDes : 支付宝账号: 6677766@qq.com
         * payTypeDes : ALIPAY
         * payType : 2
         * minEdu : 1
         * payNnameDes : 支付宝昵称: 诚信赢天下   支付宝
         * payRname : 6677766@qq.com
         */

        private int maxEdu;
        private String picUrl;
        private String payNo;
        private String payNname;
        private boolean isFastPay;
        private String payRnameDes;
        private String payTypeDes;
        private int payType;
        private int minEdu;
        private String payNnameDes;
        private String payRname;

        public int getMaxEdu() {
            return maxEdu;
        }

        public void setMaxEdu(int maxEdu) {
            this.maxEdu = maxEdu;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPayNo() {
            return payNo;
        }

        public void setPayNo(String payNo) {
            this.payNo = payNo;
        }

        public String getPayNname() {
            return payNname;
        }

        public void setPayNname(String payNname) {
            this.payNname = payNname;
        }

        public boolean isIsFastPay() {
            return isFastPay;
        }

        public void setIsFastPay(boolean isFastPay) {
            this.isFastPay = isFastPay;
        }

        public String getPayRnameDes() {
            return payRnameDes;
        }

        public void setPayRnameDes(String payRnameDes) {
            this.payRnameDes = payRnameDes;
        }

        public String getPayTypeDes() {
            return payTypeDes;
        }

        public void setPayTypeDes(String payTypeDes) {
            this.payTypeDes = payTypeDes;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getMinEdu() {
            return minEdu;
        }

        public void setMinEdu(int minEdu) {
            this.minEdu = minEdu;
        }

        public String getPayNnameDes() {
            return payNnameDes;
        }

        public void setPayNnameDes(String payNnameDes) {
            this.payNnameDes = payNnameDes;
        }

        public String getPayRname() {
            return payRname;
        }

        public void setPayRname(String payRname) {
            this.payRname = payRname;
        }


    }
}
