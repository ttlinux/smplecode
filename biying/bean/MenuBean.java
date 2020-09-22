package com.lottery.biying.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */
public class MenuBean {


    /**
     * bigBackgroudPicUrl :
     * bigPicUrl : http://192.168.0.118:8081/lottery/ssc_big.png
     * gameList : [{"titleCode":"","title":"","list":[{"gameCode":"yxzx","gameName":"一星直选"},{"gameCode":"exzx","gameName":"二星直选"},{"gameCode":"exzux","gameName":"二星组选"},{"gameCode":"sxzx","gameName":"三星直选"},{"gameCode":"sxzsds","gameName":"三星组三单式"},{"gameCode":"sxzlds","gameName":"三星组六单式"},{"gameCode":"wxzx","gameName":"五星直选"},{"gameCode":"wxtx","gameName":"五星通选"},{"gameCode":"dxds","gameName":"大小单双"}]}]
     * menuCode : ssc
     * menuName : ssc
     * smallBackgroudPicUrl :
     * smallPicUrl : http://192.168.0.118:8081/lottery/ssc_small.png
     */

    private int index;
    private String bigBackgroudPicUrl;
    private String bigPicUrl;
    private String menuCode;
    private String menuName;
    private String smallBackgroudPicUrl;
    private String smallPicUrl;
    private List<GameListBean> gameList;
    private int type;
    private int trace;

    public int getTrace() {
        return trace;
    }

    public void setTrace(int trace) {
        this.trace = trace;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBigBackgroudPicUrl() {
        return bigBackgroudPicUrl;
    }

    public void setBigBackgroudPicUrl(String bigBackgroudPicUrl) {
        this.bigBackgroudPicUrl = bigBackgroudPicUrl;
    }

    public String getBigPicUrl() {
        return bigPicUrl;
    }

    public void setBigPicUrl(String bigPicUrl) {
        this.bigPicUrl = bigPicUrl;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSmallBackgroudPicUrl() {
        return smallBackgroudPicUrl;
    }

    public void setSmallBackgroudPicUrl(String smallBackgroudPicUrl) {
        this.smallBackgroudPicUrl = smallBackgroudPicUrl;
    }

    public String getSmallPicUrl() {
        return smallPicUrl;
    }

    public void setSmallPicUrl(String smallPicUrl) {
        this.smallPicUrl = smallPicUrl;
    }

    public List<GameListBean> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameListBean> gameList) {
        this.gameList = gameList;
    }

    public static class GameListBean {
        /**
         * titleCode :
         * title :
         * list : [{"gameCode":"yxzx","gameName":"一星直选"},{"gameCode":"exzx","gameName":"二星直选"},{"gameCode":"exzux","gameName":"二星组选"},{"gameCode":"sxzx","gameName":"三星直选"},{"gameCode":"sxzsds","gameName":"三星组三单式"},{"gameCode":"sxzlds","gameName":"三星组六单式"},{"gameCode":"wxzx","gameName":"五星直选"},{"gameCode":"wxtx","gameName":"五星通选"},{"gameCode":"dxds","gameName":"大小单双"}]
         */

        private String titleCode;
        private String title;
        private List<ListBean> list;

        public String getTitleCode() {
            return titleCode;
        }

        public void setTitleCode(String titleCode) {
            this.titleCode = titleCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * gameCode : yxzx
             * gameName : 一星直选
             */
            public static class Ballbean {
                private JSONObject ballnumber[];
                private String ws;
                private String code;
                private String fastButton;

                public String getFastButton() {
                    return fastButton;
                }

                public void setFastButton(String fastButton) {
                    this.fastButton = fastButton;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public JSONObject[] getBallnumber() {
                    return ballnumber;
                }

                public void setBallnumber(JSONObject[] ballnumber) {
                    this.ballnumber = ballnumber;
                }

                public String getWs() {
                    return ws;
                }

                public void setWs(String ws) {
                    this.ws = ws;
                }
            }

            public static class Helper {
                private String code;
                private String url;
                private String value;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class Detail {
                private int gjjMoney;
                private String xhsl;
                private String gfd;
                private int gfdMoney;
                private String controlYl;
                private String wfsm;
                private String gjj;

                public String getControlYl() {
                    return controlYl;
                }

                public void setControlYl(String controlYl) {
                    this.controlYl = controlYl;
                }

                public int getGjjMoney() {
                    return gjjMoney;
                }

                public void setGjjMoney(int gjjMoney) {
                    this.gjjMoney = gjjMoney;
                }

                public String getXhsl() {
                    return xhsl;
                }

                public void setXhsl(String xhsl) {
                    this.xhsl = xhsl;
                }

                public String getGfd() {
                    return gfd;
                }

                public void setGfd(String gfd) {
                    this.gfd = gfd;
                }


                public int getGfdMoney() {
                    return gfdMoney;
                }

                public void setGfdMoney(int gfdMoney) {
                    this.gfdMoney = gfdMoney;
                }


                public String getWfsm() {
                    return wfsm;
                }

                public void setWfsm(String wfsm) {
                    this.wfsm = wfsm;
                }

                public String getGjj() {
                    return gjj;
                }

                public void setGjj(String gjj) {
                    this.gjj = gjj;
                }
            }

            public static class Bonus {
                private String bonusName;
                private int bonusType;
                private double money;
                private String select;
                private String typeBtn;

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

                public double getMoney() {
                    return money;
                }

                public void setMoney(double money) {
                    this.money = money;
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

            public static class OddsBean
            {

                /**
                 * gfd : 180000.0
                 * hm : cqssc_5x_zx_fs
                 * gjj : 190000.0
                 * odds : 180000.0000
                 * name : 五星复式
                 */

                private String gfd;
                private String hm;
                private String gjj;
                private String odds;
                private String name;

                public String getGfd() {
                    return gfd;
                }

                public void setGfd(String gfd) {
                    this.gfd = gfd;
                }

                public String getHm() {
                    return hm;
                }

                public void setHm(String hm) {
                    this.hm = hm;
                }

                public String getGjj() {
                    return gjj;
                }

                public void setGjj(String gjj) {
                    this.gjj = gjj;
                }

                public String getOdds() {
                    return odds;
                }

                public void setOdds(String odds) {
                    this.odds = odds;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            private String multipleMax;
            private String appendQsMax;
            private String titleCode;
            private String gameCode;
            private String gameName;
            private String currentGameName;
            private int index;
            private int gameListIndex;
            private ArrayList<Ballbean> ballbeans = new ArrayList<>();
            private ArrayList<Helper> helpers = new ArrayList<>();
            private ArrayList<Bonus> bonuses = new ArrayList<>();
            private Detail detail;
            private String sfyl;
            private int oddsType;
            private ArrayList<OddsBean> oddsBeans = new ArrayList<>();

            public ArrayList<OddsBean> getOddsBeans() {
                return oddsBeans;
            }

            public void setOddsBeans(ArrayList<OddsBean> oddsBeans) {
                this.oddsBeans = oddsBeans;
            }

            public int getOddsType() {
                return oddsType;
            }

            public void setOddsType(int oddsType) {
                this.oddsType = oddsType;
            }

            public String getSfyl() {
                return sfyl;
            }

            public void setSfyl(String sfyl) {
                this.sfyl = sfyl;
            }

            public String getAppendQsMax() {
                return appendQsMax;
            }

            public void setAppendQsMax(String appendQsMax) {
                this.appendQsMax = appendQsMax;
            }

            public String getMultipleMax() {
                return multipleMax;
            }

            public void setMultipleMax(String multipleMax) {
                this.multipleMax = multipleMax;
            }

            public ArrayList<Bonus> getBonuses() {
                return bonuses;
            }

            public void setBonuses(ArrayList<Bonus> bonuses) {
                this.bonuses = bonuses;
            }

            public Detail getDetail() {
                return detail;
            }

            public void setDetail(Detail detail) {
                this.detail = detail;
            }

            public ArrayList<Helper> getHelpers() {
                return helpers;
            }

            public void setHelpers(ArrayList<Helper> helpers) {
                this.helpers = helpers;
            }

            public int getGameListIndex() {
                return gameListIndex;
            }

            public void setGameListIndex(int gameListIndex) {
                this.gameListIndex = gameListIndex;
            }

            public String getTitleCode() {
                return titleCode;
            }

            public void setTitleCode(String titleCode) {
                this.titleCode = titleCode;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }


            public ArrayList<Ballbean> getBallbeans() {
                return ballbeans;
            }

            public void setBallbeans(ArrayList<Ballbean> ballbeans) {
                this.ballbeans = ballbeans;
            }

            public String getCurrentGameName() {
                return currentGameName;
            }

            public void setCurrentGameName(String currentGameName) {
                this.currentGameName = currentGameName;
            }

            public String getGameCode() {
                return gameCode;
            }

            public void setGameCode(String gameCode) {
                this.gameCode = gameCode;
            }

            public String getGameName() {
                return gameName;
            }

            public void setGameName(String gameName) {
                this.gameName = gameName;
            }
        }
    }

    public static MenuBean Handlder_Json(JSONObject jsonObject) {

        MenuBean bean = new MenuBean();
//        bean.setSmallBackgroudPicUrl(jsonObject.optString("smallBackgroudPicUrl", ""));
//        bean.setSmallPicUrl(jsonObject.optString("smallPicUrl", ""));
        bean.setMenuName(jsonObject.optString("menuName", ""));
        bean.setMenuCode(jsonObject.optString("menuCode", ""));
//        bean.setBigBackgroudPicUrl(jsonObject.optString("bigBackgroudPicUrl", ""));
        bean.setBigPicUrl(jsonObject.optString("bigPicUrl", ""));

//        JSONArray gameList=jsonObject.optJSONArray("gameList");
//        List<GameListBean> glbbeans=new ArrayList<>();
//        for (int i = 0; i <gameList.length() ; i++) {
//            JSONObject temp=gameList.optJSONObject(i);
//            GameListBean glbean=new GameListBean();
//            glbean.setTitle(temp.optString("title", ""));
//            glbean.setTitleCode(temp.optString("titleCode", ""));
//            JSONArray list=temp.optJSONArray("list");
//            List<GameListBean.ListBean> listBeans=new ArrayList<>();
//            for (int j = 0; j <list.length() ; j++) {
//                GameListBean.ListBean lbean=new GameListBean.ListBean();
//                JSONObject jsonobj=list.optJSONObject(j);
//                lbean.setGameCode(jsonobj.optString("gameCode", ""));
//                lbean.setGameName(jsonobj.optString("gameName", ""));
//                lbean.setNumSelectDes(jsonobj.optString("numSelectDes", ""));
//                lbean.setShowWinMoney(jsonobj.optString("showWinMoney", ""));
//                lbean.setSingleBetMoney(jsonobj.optString("singleBetMoney", ""));
//                lbean.setWinMoneyDes(jsonobj.optString("winMoneyDes", ""));
//                lbean.setWinMoney1(jsonobj.optString("winMoney1", ""));
//                lbean.setWinMoney2(jsonobj.optString("winMoney2", ""));
//                lbean.setWinMoney3(jsonobj.optString("winMoney3", ""));
//                lbean.setCurrentGameName(jsonobj.optString("currentGameName", ""));
//                lbean.setIndex(j);
//                lbean.setTitleCode(glbean.getTitleCode());
//                lbean.setGameListIndex(i);
//                JSONArray balls=jsonobj.optJSONArray("balls");
//                ArrayList<GameListBean.ListBean.Ballbean> ballbeans=new ArrayList<>();
//                for (int k = 0; k < balls.length(); k++) {
//                    JSONObject balljsonobj=balls.optJSONObject(k);
//                    GameListBean.ListBean.Ballbean ballbean=new GameListBean.ListBean.Ballbean();
//                    ballbean.setWs(balljsonobj.optString("ws",""));
//                    ballbean.setCode(balljsonobj.optString("code",""));
//                    JSONArray ballarray=balljsonobj.optJSONArray("ball");
//                    if(ballarray.length()>0)
//                    {
//                        String balls_arr[]=new String[ballarray.length()];
//                        for (int l = 0; l <ballarray.length() ; l++) {
//                            balls_arr[l]=ballarray.optString(l);
//                        }
//                        ballbean.setBallnumber(balls_arr);
//                    }
//                    ballbeans.add(ballbean);
//                }
//                lbean.setBallbeans(ballbeans);
//                listBeans.add(lbean);
//            }
//            glbean.setList(listBeans);
//            glbbeans.add(glbean);
//        }
//        bean.setGameList(glbbeans);
        return bean;
    }

    public static ArrayList<GameListBean.ListBean.Helper> Handlder_Json_Helper(JSONArray jsonArray) {
        if(jsonArray==null)return null;
        ArrayList<GameListBean.ListBean.Helper> helpers = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.optJSONObject(i);
            GameListBean.ListBean.Helper helper = new GameListBean.ListBean.Helper();
            helper.setCode(temp.optString("code", ""));
            helper.setUrl(temp.optString("url", ""));
            helper.setValue(temp.optString("value", ""));
            helpers.add(helper);
        }
        return helpers;
    }

    public static GameListBean.ListBean.Detail Handlder_Json_Detail(JSONObject jsonobj) {
        GameListBean.ListBean.Detail detail = new GameListBean.ListBean.Detail();
        detail.setGjjMoney(jsonobj.optInt("gjjMoney", 1));
        detail.setXhsl(jsonobj.optString("xhsl", ""));
        detail.setGfd(jsonobj.optString("gfd", ""));
        detail.setGfdMoney(jsonobj.optInt("gfdMoney", 1));
        detail.setWfsm(jsonobj.optString("wfsm", ""));
        detail.setGjj(jsonobj.optString("gjj", ""));
        detail.setControlYl(jsonobj.optString("controlYl",""));
        return detail;
    }

    public static ArrayList<GameListBean.ListBean.Ballbean> Handlder_Json_Ballbean(JSONArray jsonarray) {
        ArrayList<GameListBean.ListBean.Ballbean> ballbeans = new ArrayList<>();
        for (int k = 0; k < jsonarray.length(); k++) {
            JSONObject balljsonobj = jsonarray.optJSONObject(k);
            GameListBean.ListBean.Ballbean ballbean = new GameListBean.ListBean.Ballbean();
            ballbean.setWs(balljsonobj.optString("ws", ""));
            ballbean.setCode(balljsonobj.optString("code", ""));
            ballbean.setFastButton(balljsonobj.optString("fastButton", ""));
            JSONArray ballarray = balljsonobj.optJSONArray("ball");
            if (ballarray.length() > 0) {
                JSONObject balls_arr[] = new JSONObject[ballarray.length()];
                for (int l = 0; l < ballarray.length(); l++) {
                    balls_arr[l] = ballarray.optJSONObject(l);
                }
                ballbean.setBallnumber(balls_arr);
            }
            ballbeans.add(ballbean);
        }
        return ballbeans;
    }

    public static ArrayList<GameListBean.ListBean.Bonus> Handlder_Json_Bonus(JSONArray jsonarray) {
        ArrayList<GameListBean.ListBean.Bonus> bounss = new ArrayList<>();
        for (int i = 0; i <jsonarray.length() ; i++) {
            JSONObject jsonobj=jsonarray.optJSONObject(i);
            GameListBean.ListBean.Bonus bouns=new GameListBean.ListBean.Bonus();
            bouns.setBonusName(jsonobj.optString("bonusName"));
            bouns.setBonusType(jsonobj.optInt("bonusType", 0));
            bouns.setMoney(jsonobj.optDouble("money", 0));
            bouns.setSelect(jsonobj.optString("select"));
            bouns.setTypeBtn(jsonobj.optString("typeBtn"));
            bounss.add(bouns);
        }
        return bounss;
    }

    public static ArrayList<GameListBean.ListBean.OddsBean> Handlder_Json_Odds(JSONArray jsonArray)
    {
        ArrayList<GameListBean.ListBean.OddsBean> oddsBeans = new ArrayList<>();
        for (int i = 0; i <jsonArray.length() ; i++) {
            JSONObject jsonobj=jsonArray.optJSONObject(i);
            GameListBean.ListBean.OddsBean bouns=new GameListBean.ListBean.OddsBean();
            bouns.setGfd(jsonobj.optString("gfd",""));
            bouns.setHm(jsonobj.optString("hm", ""));
            bouns.setGjj(jsonobj.optString("gjj",""));
            bouns.setOdds(jsonobj.optString("odds",""));
            bouns.setName(jsonobj.optString("name",""));
            oddsBeans.add(bouns);
        }
        return oddsBeans;
    }

}
