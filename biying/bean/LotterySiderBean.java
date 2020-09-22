package com.lottery.biying.bean;

import android.app.Activity;

import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.RepluginMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */
public class LotterySiderBean {


    /**
     * datas : {"eduMinPay":1,"fundsList":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/cz_big.png","isFast":1,"menuCode":"deposit","menuModuleCode":"funds","menuName":"充值","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/cz_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tx_big.png","isFast":1,"menuCode":"withdraw","menuModuleCode":"funds","menuName":"提现","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tx_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/zxkf_big.png","isFast":1,"menuCode":"service","menuModuleCode":"funds","menuName":"在线客服","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/zxkf_small.png"}],"information":{"register":"注册信息提示","bdsj":"绑定手机号","withdraw":"111111111111","rxff":"日薪发放","forgetpwd":"使用手机号码注册的用户,用户名请填写手机号码!","login":"登录信息提示","compay":"公司入款提示","applyAgent":"申请代理提示","bank":"银行卡绑定","zcxj":"注册下级","xjrx":"下级日薪","fastpay":"快捷支付提示","wszl":"完善资料"},"menuList":[{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/ctjl_big.png","isFast":1,"menuCode":"czjl","menuModuleCode":"bank","menuName":"充提记录","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/ctjl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/cpzb_big.png","isFast":1,"menuCode":"cpzb","menuModuleCode":"bank","menuName":"彩票账变","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/cpzb_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/gryk_big.png","isFast":1,"menuCode":"gryk","menuModuleCode":"bank","menuName":"个人盈亏","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/gryk_small.png"}],"menuModuleCode":"bank","menuModuleName":"资金账变"},{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tzjl_big.png","isFast":1,"menuCode":"tzjl","menuModuleCode":"history","menuName":"投注记录","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tzjl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/zhjl_big.png","isFast":1,"menuCode":"zhjl","menuModuleCode":"history","menuName":"追号记录","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/zhjl_small.png"}],"menuModuleCode":"history","menuModuleName":"游戏记录"},{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/grzl_big.png","isFast":1,"menuCode":"grzx","menuModuleCode":"account","menuName":"个人资料","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/grzl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/mmgl_big.png","isFast":1,"menuCode":"mmgl","menuModuleCode":"account","menuName":"密码管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/mmgl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/yhkgl_big.png","isFast":1,"menuCode":"yhkxx","menuModuleCode":"account","menuName":"银行卡管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/yhkgl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/czxx_big.png","isFast":1,"menuCode":"czxx","menuModuleCode":"account","menuName":"彩种信息","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/czxx_small.png"}],"menuModuleCode":"account","menuModuleName":"账户管理"},{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tdgl_big.png","isFast":1,"menuCode":"tdgl","menuModuleCode":"agent","menuName":"团队管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tdgl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tdyk_big.png","isFast":1,"menuCode":"tdyk","menuModuleCode":"agent","menuName":"团队盈亏","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tdyk_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/zcxj_big.png","isFast":1,"menuCode":"zcxj","menuModuleCode":"agent","menuName":"注册下级","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/zcxj_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tglj_big.png","isFast":1,"menuCode":"tglj","menuModuleCode":"agent","menuName":"推广链接","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tglj_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/rxgl_big.png","isFast":1,"menuCode":"rxgl","menuModuleCode":"agent","menuName":"日薪管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/rxgl_small.png"}],"menuModuleCode":"agent","menuModuleName":"代理中心"}],"refreshTime":"60","userDetail":{"teamMoney":"10001.0000","typeLevel":"代理会员分组","typeName":"代理分组","typePicName":"http://res.6820168.com/m/site/e0/member/1497666045601.png","userMoney":"39818.5600"}}
     * errorCode : 000000
     * msg : 操作成功
     */

    private DatasBean datas;
    private String errorCode;
    private String msg;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DatasBean {
        /**
         * eduMinPay : 1
         * fundsList : [{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/cz_big.png","isFast":1,"menuCode":"deposit","menuModuleCode":"funds","menuName":"充值","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/cz_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tx_big.png","isFast":1,"menuCode":"withdraw","menuModuleCode":"funds","menuName":"提现","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tx_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/zxkf_big.png","isFast":1,"menuCode":"service","menuModuleCode":"funds","menuName":"在线客服","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/zxkf_small.png"}]
         * information : {"register":"注册信息提示","bdsj":"绑定手机号","withdraw":"111111111111","rxff":"日薪发放","forgetpwd":"使用手机号码注册的用户,用户名请填写手机号码!","login":"登录信息提示","compay":"公司入款提示","applyAgent":"申请代理提示","bank":"银行卡绑定","zcxj":"注册下级","xjrx":"下级日薪","fastpay":"快捷支付提示","wszl":"完善资料"}
         * menuList : [{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/ctjl_big.png","isFast":1,"menuCode":"czjl","menuModuleCode":"bank","menuName":"充提记录","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/ctjl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/cpzb_big.png","isFast":1,"menuCode":"cpzb","menuModuleCode":"bank","menuName":"彩票账变","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/cpzb_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/gryk_big.png","isFast":1,"menuCode":"gryk","menuModuleCode":"bank","menuName":"个人盈亏","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/gryk_small.png"}],"menuModuleCode":"bank","menuModuleName":"资金账变"},{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tzjl_big.png","isFast":1,"menuCode":"tzjl","menuModuleCode":"history","menuName":"投注记录","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tzjl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/zhjl_big.png","isFast":1,"menuCode":"zhjl","menuModuleCode":"history","menuName":"追号记录","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/zhjl_small.png"}],"menuModuleCode":"history","menuModuleName":"游戏记录"},{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/grzl_big.png","isFast":1,"menuCode":"grzx","menuModuleCode":"account","menuName":"个人资料","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/grzl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/mmgl_big.png","isFast":1,"menuCode":"mmgl","menuModuleCode":"account","menuName":"密码管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/mmgl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/yhkgl_big.png","isFast":1,"menuCode":"yhkxx","menuModuleCode":"account","menuName":"银行卡管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/yhkgl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/czxx_big.png","isFast":1,"menuCode":"czxx","menuModuleCode":"account","menuName":"彩种信息","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/czxx_small.png"}],"menuModuleCode":"account","menuModuleName":"账户管理"},{"list":[{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tdgl_big.png","isFast":1,"menuCode":"tdgl","menuModuleCode":"agent","menuName":"团队管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tdgl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tdyk_big.png","isFast":1,"menuCode":"tdyk","menuModuleCode":"agent","menuName":"团队盈亏","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tdyk_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/zcxj_big.png","isFast":1,"menuCode":"zcxj","menuModuleCode":"agent","menuName":"注册下级","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/zcxj_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/tglj_big.png","isFast":1,"menuCode":"tglj","menuModuleCode":"agent","menuName":"推广链接","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/tglj_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/rxgl_big.png","isFast":1,"menuCode":"rxgl","menuModuleCode":"agent","menuName":"日薪管理","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/rxgl_small.png"}],"menuModuleCode":"agent","menuModuleName":"代理中心"}]
         * refreshTime : 60
         * userDetail : {"teamMoney":"10001.0000","typeLevel":"代理会员分组","typeName":"代理分组","typePicName":"http://res.6820168.com/m/site/e0/member/1497666045601.png","userMoney":"39818.5600"}
         */

        private int eduMinPay;
        private InformationBean information;
        private String refreshTime;
        private UserDetailBean userDetail;
        private List<FundsListBean> fundsList;
        private List<MenuListBean> menuList;

        public int getEduMinPay() {
            return eduMinPay;
        }

        public void setEduMinPay(int eduMinPay) {
            this.eduMinPay = eduMinPay;
        }

        public InformationBean getInformation() {
            return information;
        }

        public void setInformation(InformationBean information) {
            this.information = information;
        }

        public String getRefreshTime() {
            return refreshTime;
        }

        public void setRefreshTime(String refreshTime) {
            this.refreshTime = refreshTime;
        }

        public UserDetailBean getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetailBean userDetail) {
            this.userDetail = userDetail;
        }

        public List<FundsListBean> getFundsList() {
            return fundsList;
        }

        public void setFundsList(List<FundsListBean> fundsList) {
            this.fundsList = fundsList;
        }

        public List<MenuListBean> getMenuList() {
            return menuList;
        }

        public void setMenuList(List<MenuListBean> menuList) {
            this.menuList = menuList;
        }

        public static class InformationBean {
            /**
             * register : 注册信息提示
             * bdsj : 绑定手机号
             * withdraw : 111111111111
             * rxff : 日薪发放
             * forgetpwd : 使用手机号码注册的用户,用户名请填写手机号码!
             * login : 登录信息提示
             * compay : 公司入款提示
             * applyAgent : 申请代理提示
             * bank : 银行卡绑定
             * zcxj : 注册下级
             * xjrx : 下级日薪
             * fastpay : 快捷支付提示
             * wszl : 完善资料
             */

            private String register;
            private String bdsj;
            private String withdraw;
            private String rxff;
            private String forgetpwd;
            private String login;
            private String compay;
            private String applyAgent;
            private String bank;
            private String zcxj;
            private String xjrx;
            private String fastpay;
            private String wszl;

            public String getRegister() {
                return register;
            }

            public void setRegister(String register) {
                this.register = register;
            }

            public String getBdsj() {
                return bdsj;
            }

            public void setBdsj(String bdsj) {
                this.bdsj = bdsj;
            }

            public String getWithdraw() {
                return withdraw;
            }

            public void setWithdraw(String withdraw) {
                this.withdraw = withdraw;
            }

            public String getRxff() {
                return rxff;
            }

            public void setRxff(String rxff) {
                this.rxff = rxff;
            }

            public String getForgetpwd() {
                return forgetpwd;
            }

            public void setForgetpwd(String forgetpwd) {
                this.forgetpwd = forgetpwd;
            }

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public String getCompay() {
                return compay;
            }

            public void setCompay(String compay) {
                this.compay = compay;
            }

            public String getApplyAgent() {
                return applyAgent;
            }

            public void setApplyAgent(String applyAgent) {
                this.applyAgent = applyAgent;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getZcxj() {
                return zcxj;
            }

            public void setZcxj(String zcxj) {
                this.zcxj = zcxj;
            }

            public String getXjrx() {
                return xjrx;
            }

            public void setXjrx(String xjrx) {
                this.xjrx = xjrx;
            }

            public String getFastpay() {
                return fastpay;
            }

            public void setFastpay(String fastpay) {
                this.fastpay = fastpay;
            }

            public String getWszl() {
                return wszl;
            }

            public void setWszl(String wszl) {
                this.wszl = wszl;
            }
        }

        public static class UserDetailBean {
            /**
             * teamMoney : 10001.0000
             * typeLevel : 代理会员分组
             * typeName : 代理分组
             * typePicName : http://res.6820168.com/m/site/e0/member/1497666045601.png
             * userMoney : 39818.5600
             */

            private String teamMoney;
            private String typeLevel;
            private String typeName;
            private String typePicName;
            private String userMoney;
            private String userName;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getTeamMoney() {
                return teamMoney;
            }

            public void setTeamMoney(String teamMoney) {
                this.teamMoney = teamMoney;
            }

            public String getTypeLevel() {
                return typeLevel;
            }

            public void setTypeLevel(String typeLevel) {
                this.typeLevel = typeLevel;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getTypePicName() {
                return typePicName;
            }

            public void setTypePicName(String typePicName) {
                this.typePicName = typePicName;
            }

            public String getUserMoney() {
                return userMoney;
            }

            public void setUserMoney(String userMoney) {
                this.userMoney = userMoney;
            }
        }

        public static class FundsListBean {
            /**
             * bigBackgroundPic :
             * bigPic : http://res.6820168.com/share/icon/h5/member/cz_big.png
             * isFast : 1
             * menuCode : deposit
             * menuModuleCode : funds
             * menuName : 充值
             * smallBackgroundPic :
             * smallPic : http://res.6820168.com/share/icon/h5/member/cz_small.png
             */

            private String bigBackgroundPic;
            private String bigPic;
            private int isFast;
            private String menuCode;
            private String menuModuleCode;
            private String menuName;
            private String smallBackgroundPic;
            private String smallPic;

            public String getBigBackgroundPic() {
                return bigBackgroundPic;
            }

            public void setBigBackgroundPic(String bigBackgroundPic) {
                this.bigBackgroundPic = bigBackgroundPic;
            }

            public String getBigPic() {
                return bigPic;
            }

            public void setBigPic(String bigPic) {
                this.bigPic = bigPic;
            }

            public int getIsFast() {
                return isFast;
            }

            public void setIsFast(int isFast) {
                this.isFast = isFast;
            }

            public String getMenuCode() {
                return menuCode;
            }

            public void setMenuCode(String menuCode) {
                this.menuCode = menuCode;
            }

            public String getMenuModuleCode() {
                return menuModuleCode;
            }

            public void setMenuModuleCode(String menuModuleCode) {
                this.menuModuleCode = menuModuleCode;
            }

            public String getMenuName() {
                return menuName;
            }

            public void setMenuName(String menuName) {
                this.menuName = menuName;
            }

            public String getSmallBackgroundPic() {
                return smallBackgroundPic;
            }

            public void setSmallBackgroundPic(String smallBackgroundPic) {
                this.smallBackgroundPic = smallBackgroundPic;
            }

            public String getSmallPic() {
                return smallPic;
            }

            public void setSmallPic(String smallPic) {
                this.smallPic = smallPic;
            }
        }

        public static class MenuListBean {
            /**
             * list : [{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/ctjl_big.png","isFast":1,"menuCode":"czjl","menuModuleCode":"bank","menuName":"充提记录","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/ctjl_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/cpzb_big.png","isFast":1,"menuCode":"cpzb","menuModuleCode":"bank","menuName":"彩票账变","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/cpzb_small.png"},{"bigBackgroundPic":"","bigPic":"http://res.6820168.com/share/icon/h5/member/gryk_big.png","isFast":1,"menuCode":"gryk","menuModuleCode":"bank","menuName":"个人盈亏","smallBackgroundPic":"","smallPic":"http://res.6820168.com/share/icon/h5/member/gryk_small.png"}]
             * menuModuleCode : bank
             * menuModuleName : 资金账变
             */

            private String menuModuleCode;
            private String menuModuleName;
            private List<ListBean> list;

            public String getMenuModuleCode() {
                return menuModuleCode;
            }

            public void setMenuModuleCode(String menuModuleCode) {
                this.menuModuleCode = menuModuleCode;
            }

            public String getMenuModuleName() {
                return menuModuleName;
            }

            public void setMenuModuleName(String menuModuleName) {
                this.menuModuleName = menuModuleName;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * bigBackgroundPic :
                 * bigPic : http://res.6820168.com/share/icon/h5/member/ctjl_big.png
                 * isFast : 1
                 * menuCode : czjl
                 * menuModuleCode : bank
                 * menuName : 充提记录
                 * smallBackgroundPic :
                 * smallPic : http://res.6820168.com/share/icon/h5/member/ctjl_small.png
                 */

                private String bigBackgroundPic;
                private String bigPic;
                private int isFast;
                private String menuCode;
                private String menuModuleCode;
                private String menuName;
                private String smallBackgroundPic;
                private String smallPic;

                public String getBigBackgroundPic() {
                    return bigBackgroundPic;
                }

                public void setBigBackgroundPic(String bigBackgroundPic) {
                    this.bigBackgroundPic = bigBackgroundPic;
                }

                public String getBigPic() {
                    return bigPic;
                }

                public void setBigPic(String bigPic) {
                    this.bigPic = bigPic;
                }

                public int getIsFast() {
                    return isFast;
                }

                public void setIsFast(int isFast) {
                    this.isFast = isFast;
                }

                public String getMenuCode() {
                    return menuCode;
                }

                public void setMenuCode(String menuCode) {
                    this.menuCode = menuCode;
                }

                public String getMenuModuleCode() {
                    return menuModuleCode;
                }

                public void setMenuModuleCode(String menuModuleCode) {
                    this.menuModuleCode = menuModuleCode;
                }

                public String getMenuName() {
                    return menuName;
                }

                public void setMenuName(String menuName) {
                    this.menuName = menuName;
                }

                public String getSmallBackgroundPic() {
                    return smallBackgroundPic;
                }

                public void setSmallBackgroundPic(String smallBackgroundPic) {
                    this.smallBackgroundPic = smallBackgroundPic;
                }

                public String getSmallPic() {
                    return smallPic;
                }

                public void setSmallPic(String smallPic) {
                    this.smallPic = smallPic;
                }
            }
        }
    }

    public static LotterySiderBean AnalysisData(JSONObject jsonObject,Activity act)
    {
        LotterySiderBean bean=new LotterySiderBean();
        if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
        return null;
        JSONObject datas=jsonObject.optJSONObject("datas");
        bean.setErrorCode(jsonObject.optString("errorCode", ""));
        bean.setMsg(jsonObject.optString("msg", ""));
        DatasBean datasbean=new DatasBean();

        List<DatasBean.FundsListBean> fundsarray=new ArrayList<>();
        JSONArray fundsList=datas.optJSONArray("fundsList");
        for (int i = 0; i <fundsList.length() ; i++) {
            JSONObject temp=fundsList.optJSONObject(i);
            DatasBean.FundsListBean fundsbean=new DatasBean.FundsListBean();
            fundsbean.setBigBackgroundPic(temp.optString("bigBackgroundPic",""));
            fundsbean.setBigPic(temp.optString("bigPic", ""));
            fundsbean.setIsFast(temp.optInt("isFast", 0));
            fundsbean.setMenuCode(temp.optString("menuCode", ""));
            fundsbean.setMenuModuleCode(temp.optString("menuModuleCode", ""));
            fundsbean.setMenuName(temp.optString("menuName", ""));
            fundsbean.setSmallBackgroundPic(temp.optString("smallBackgroundPic", ""));
            fundsbean.setSmallPic(temp.optString("smallPic", ""));
            fundsarray.add(fundsbean);
        }
        datasbean.setFundsList(fundsarray);

        //information 直接保留到SharedPreferences
        RepluginMethod.getHosttSharedPreferences(act).edit()
                .putString(BundleTag.Information,datas.optJSONObject("information").toString()).commit();



        List<DatasBean.MenuListBean> mbeanlist=new ArrayList<>();
        JSONArray menuList=datas.optJSONArray("menuList");
        for (int k = 0; k < menuList.length(); k++) {
            JSONObject tempmain=menuList.optJSONObject(k);
            DatasBean.MenuListBean mbean=new DatasBean.MenuListBean();
            mbean.setMenuModuleCode(tempmain.optString("menuModuleCode", ""));
            mbean.setMenuModuleName(tempmain.optString("menuModuleName", ""));
            JSONArray list=tempmain.optJSONArray("list");

            List<DatasBean.MenuListBean.ListBean> menusbean=new ArrayList<>();
            for (int i = 0; i <list.length(); i++) {
                JSONObject temp=list.optJSONObject(i);
                DatasBean.MenuListBean.ListBean menuListBean=new DatasBean.MenuListBean.ListBean();
                menuListBean.setBigBackgroundPic(temp.optString("bigBackgroundPic",""));
                menuListBean.setBigPic(temp.optString("bigPic", ""));
                menuListBean.setIsFast(temp.optInt("isFast", 0));
                menuListBean.setMenuCode(temp.optString("menuCode", ""));
                menuListBean.setMenuModuleCode(temp.optString("menuModuleCode", ""));
                menuListBean.setMenuName(temp.optString("menuName", ""));
                menuListBean.setSmallBackgroundPic(temp.optString("smallBackgroundPic", ""));
                menuListBean.setSmallPic(temp.optString("smallPic", ""));
                menusbean.add(menuListBean);
            }
            mbean.setList(menusbean);
            mbeanlist.add(mbean);
        }
        datasbean.setMenuList(mbeanlist);

        DatasBean.UserDetailBean ubean=new DatasBean.UserDetailBean();
        JSONObject userDetail=datas.optJSONObject("userDetail");
        ubean.setTeamMoney(userDetail.optString("teamMoney", ""));
        ubean.setTypeLevel(userDetail.optString("typeLevel", ""));
        ubean.setTypeName(userDetail.optString("typeName", ""));
        ubean.setTypePicName(userDetail.optString("typePicName", ""));
        ubean.setUserMoney(userDetail.optString("userMoney", ""));
        ubean.setUserName(userDetail.optString("userName", ""));
        datasbean.setUserDetail(ubean);


        bean.setDatas(datasbean);
        return  bean;

    }
}


