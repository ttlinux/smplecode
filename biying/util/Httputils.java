package com.lottery.biying.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/29.
 */
public class Httputils {
    public static final String client_server = "1";//		客户端类型(1:PC端 2:移动端)

    public static String ImgLOGOUrl = "http://192.168.0.239:8391/";//logo图前缀地址
    //  public static  String BaseUrl="http://192.168.0.124:8080/";//http://192.168.0.239:8391/";//http://api.6820168.com/    http://192.168.0.113:8080/
    public static String BaseUrl = "";//43.249.30.40:8083 api.6820168.com 192.168.0.239:8391 103.240.143.141:8083
    private static MyAsyncHttpClient client;
    //    public static String playmethod="lottery/playmethod";
    public static String mainmethod = "lottery/menu";//彩种列表
    public static String numberlist = "lottery/number";
    public static String lotteryresult = "lottery/result";//开奖结果
    public static String CommitOrder = "lottery/order";//
    public static String TodayYingkui = "lotterymember/lottery/account/yingkui/today";
    public static String PreviewYingkui="lotterymember/lottery/user/yingkui/report";
    public static String TeamYingkui="lotterymember/lottery/team/yingkui/report";

    public static String SmartFollow = "lottery/num/append";
    public static String Trend = "lottery/trend";
    public static String Trend_detail = "lottery/trend/detail";
    public static String detail = "lottery/mobi/gamedetail/v2";
    public static String Timer = "lottery/index/gameendtime";
    public static String trace = "lotterymember/order/trace";
    public static String DTime = "lottery/nextqs";
    public static String BetOrder = "lotterymember/order/bet";
    public static String AndroidApkPath = "";
    public static String Mainpage = "";
    public static String modulelist = "";
    public static String login = "lotterylogon/login";
    public static String authorize = "lotteryauth/authorize";
    public static String register = "lotterymember/member/register";
    public static String OrderHistory="lotterymember/order";//投注列表
    public static String Tracehistory="lotterymember/order/append";//追号列表
    public static final String OrderDetail="lotterymember/order/detail";//注单列表
    public static final String UserFragment_Model="lotterymember/resource";
    public static final String Trace_Order_detail="lotterymember/order/append/detail";//追号记录详情
    public static final String StopTrace="lotterymember/order/append/stop";
    public static final String AccountChangeList="lotterymember/lottery/account/change";
    public static final String AccountChangeListv2="lotterymember/lottery/account/change/v2";
    public static final String TeamIncome="lotterymember/team/account/yingkui";
    public static final String TeamIncome_other="lotterymember/team/account/flat/yingkui";
    public static final String PersonalIncome="lotterymember/lottery/account/yingkui";
    public static final String PersonalIncome_other="lotterymember/flat/account/yingkui";
    public static final String PersonalSalaryDetail="lotterymember/user/salary";
    public static final String PersonalSalaryList="lotterymember/user/salary/detail";
    public static final String NewLink="lotterymember/user/promotion/link/add";
    public static final String LinkList="lotterymember/user/promotion/link";
    public static final String NewSubordinateSalary="lotterymember/user/salary/add";
    public static final String saveScanCodePay="lotterypay/chuantong/saomaPay";//1.29	快捷支付-传统扫码支付
    public static final String selectPayList="member/pay/online/saoma/list";//获取个人快捷支付列表
    public static final String selectPayList_traditional="member/pay/chuantong/saoma/list";//获取传统扫码支付列表
    public static final String selectIncFastPayList="member/pay/chuantong/bank/list";//获取公司快捷支付列表
    public static final String AllPayType="lotterypay/allList";//所有支付方式
    public static final String onlineFastPay="lotterypay/thirdPay/pay";//
    public static final String saveIncBankPay="lotterypay/chuantong/bankPay";//公司入款银行卡支付提交
    public static final String huikuanType="lotterypay/huikuan/list";//汇款方式列表
    public static final String TeamManageList="lotterymember/team/account/manage";
    public static final String Pointupdate="lotterymember/team/back/update";
    public static final String Salaryupdate="lotterymember/user/salary/update";
    public static final String SendSitemEssage="lotterymember/add/message/group";
    public static final String Transfertoagent="lotterymember/transfer/agent";
    public static final String ModifyRemark="lotterymember/team/remark/add";
    public  static final String UserInfo="lotterymember/info";//会员信息
    public static final String ChangeWithdrawPassword="lotterymember/info/withdrawPwdEdit";//修改取款密码
    public static final String ChangePassword="lotterymember/info/loginPwdEdit";//修改账户密码
    public static final String logout="user/logout";//退出
    public static final String ModifyNickname="lotterymember/info/edit";//修改昵称
    public static final String SelectBankList="lotterymember/info/userBankList";//用户点击[提现]
    public static final String Flatwithdraw="flat/withdraw/";//额度转换平台提款
    public static final String Withdraw="lotteryWithdraw/submit";//提现  提款到银行卡
    public static final String BindBank="lotterymember/info/bindBank";//绑定银行卡
    public static final String selectUserBankCodeInfo="lotterymember/info/bankList";//加载银行列表信息
    public static final String MessageList="lotterymember/message/list";//信息列表
    public static final String MessageContent="lotterymember/message/content";//聊天记录
    public static final String SendMessage="lotterymember/update/message/group";//发送消息
    public static final String RegistSubordinate="lotterymember/agent/register";//注册下级
    public static final String LotteryDetail="lotterymember/lotteryDetail";//彩种信息
    public static final String Payrecord="lotterymember/record/deposit";//充值记录
    public static final String WithdrawHistory="lotterymember/record/withdraw";//提现记录
    public static final String DeleteMessage="lotterymember/delete/message/group";//删除信息
    public static final String withdraw="flat/withdraw/";//额度转换平台提款
    public static final String deposit="flat/deposit/";//额度转换平台充值
    public static final String balancemList="lotteryflat/balance/";//获取游戏平台余额
    public static final String Platform="electronic/flat";//注单查询平台和额度转换平台列表
    public static final String FlatRecordlist="lotterymember/flat/game";//注单查询平台和额度转换平台列表
    public static final String EduHistory="lotterymember/record/edu";//额度转换记录
    public static final String bankremove="lotterymember/info/removeBank";//银行卡解绑
    public static final String bankreupdate="lotterymember/info/updateBank";//银行卡修改
    public static final String Record="flat/record";//平台注单查询
    public static final String TrendChart="lottery/trendChart";//走势图
    public static final String bindmobile="lotterymember/info/mobileBind";//绑定手机号
    public static final String Sendsms="lotterycommons/sms/send";//发送验证码
    public static final String ResetPassword="lotterypassword/login/reset";//忘记密码 重置新密码
    public static final String VerifyPassword="lotterypassword/verify";//忘记密码 重置新密码
    public static final String UserBalance="lotterymember/info/balance";//用户余额
    public static final String MessageCount="lotterymember/message/count";//未读消息条数
    public static final String CompleteData="lotterymember/info/withdrawPwdBind";//完善资料
    public static final String RemoveOrder="lotterymember/order/stop";//撤单
    public static final String OnlineService="main/getSiteInfo";//在线客服
    public static final String panel="lottery/panel";//开户协议
    public static final String DeleteLink="lotterymember/user/promotion/link/delete";
    public static final String PayOff="lotterymember/pay/agent";
    public static final String FastResourse="lotterymember/resource/fast";


    public static  String androidkey = "";//体育下单详情detail
    public static  String androidsecret = "";//体育下单详情detail
    private static int timeout = 10000;//上线时记得改成10秒

    public interface BalanceListener {
        public void OnRecevicedata(String balance);

        public void Onfail(String str);
    }

    public static String[] convertStrToArray(String str) {
        String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[ \\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\= \\+ ] ";
//        LogTools.e("Stringcode",str);
        String aaa = str.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\\"", "").replaceAll("\\+", ",").replaceAll("=", ",");
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(aaa);
        String aa = m.replaceAll(",").trim();
//        LogTools.e("Stringcode",aa);
        String[] strArray = null;
        strArray = aa.split(",");
//        LogTools.e("Stringcodeee", strArray.toString());
        //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }


    public static boolean useLoop(int[] arr, int targetValue) {
        for (int s : arr) {
            if (s == targetValue) {
                return true;
            }
        }
        return false;
    }

    public static String httpurlAndroidUpdate() {
        return "";
    }


    public static void GetBaseInfo(Context context) {
        if (BaseUrl.length() < 1) {
            SharedPreferences sharedPreferences = RepluginMethod.getHostApplication(context).getSharedPreferences(BundleTag.Lottery, Context.MODE_PRIVATE);
            BaseUrl=sharedPreferences.getString(BundleTag.BaseUrl,"http://test.tc.ybapi.net/");//192.168.0.239:8391 103.240.143.136:8083
            androidkey=sharedPreferences.getString(BundleTag.AndroidKey,"b13f17404e84b4c0ecee26daba4c09ccafd5c60835f5a43739d472b739f9b9f0");
            androidsecret=sharedPreferences.getString(BundleTag.AndroidSecret,"d29d5926683e28dd5cbeae736f217d00");
        }
    }

    public static void PostWithBaseUrl(String url, RequestParams requestParams, MyJsonHttpResponseHandler jsonHttpResponseHandler) {
        GetBaseInfo(jsonHttpResponseHandler.getContext());
        jsonHttpResponseHandler.setUrl(url);
        client = new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        LogTools.e("URL", BaseUrl + url + new Gson().toJson(requestParams));
        client.post(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }

    public static void PostWithBaseUrl(String url, RequestParams requestParams, MyJsonHttpResponseHandler2 jsonHttpResponseHandler) {
        GetBaseInfo(jsonHttpResponseHandler.getContext());
        LogTools.e("URL", BaseUrl + url + new Gson().toJson(requestParams));
        client = new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.post(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }


    //    public static void PostWithBaseUrld(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler) {
//        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
//        client.setTimeout(timeout);
//        LogTools.e("getURL", "http://192.168.0.238:8083/Inter/" + url + new Gson().toJson(requestParams));
//        client.post("http://192.168.0.238:8083/Inter/" + url, requestParams, jsonHttpResponseHandler);
//    }
//    public static void GetWithBaseUrld(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler)
//    {
//        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
//        client.setTimeout(timeout);
//        LogTools.e("getURL", "http://192.168.0.238:8083/Inter/" + url);
//        client.get("http://192.168.0.238:8083/Inter/" + url, requestParams, jsonHttpResponseHandler);
//    }
    public static void GETWithoutBASEURL(String url, RequestParams params,
                                         MyJsonHttpResponseHandler responseHandler) {
        AsyncHttpClient client1 = new AsyncHttpClient();
        client1.setTimeout(3000);
        client1.get(url, params, responseHandler);
        LogTools.e("getAbsoluteUrl(url)", "" + url + params);
//		saveFile(""+BASE+url+params);
    }

    public static void getWithoutBASEurl(String url, RequestParams params,
                                         MyJsonHttpResponseHandler responseHandler) {
        AsyncHttpClient client1 = new AsyncHttpClient();
        client1.setTimeout(3000);
        client1.get(url, params, responseHandler);
        LogTools.e("getAbsoluteUrl(url)", "" + url + params);
//		saveFile(""+BASE+url+params);
    }


    public static void GetWithBaseUrl(String url, RequestParams requestParams, MyJsonHttpResponseHandler jsonHttpResponseHandler) {
        client = new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.get(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }

    public static void PutWithBaseUrl(String url, RequestParams requestParams, MyJsonHttpResponseHandler jsonHttpResponseHandler) {
        client = new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.put(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }

    public static void DeleteWithBaseUrl(Context context, String url, MyJsonHttpResponseHandler jsonHttpResponseHandler) {
        client = new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.delete(context, url, jsonHttpResponseHandler);
//        client.delete(BaseUrl + url, jsonHttpResponseHandler);
    }


//    /**
//     * Delete
//     * @param url 发送请求的URL
//     * @return 服务器响应字符串
//     * @throws Exception
//     */
//    public static String deleteRequest(final String url,final int id)
//            throws Exception
//    {
//
//        FutureTask<String> task = new FutureTask<String>(
//                new Callable<String>()
//                {
//                    @Override
//                    public String call() throws Exception
//                    {
//                        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
//// json 处理
//                        httpDelete.setHeader("Content-Type", "application/x-www-form-urlencoded;");//or addHeader();
//                        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//
//                        NameValuePair pair1 = new BasicNameValuePair("userName", "duanaifei");
//                        NameValuePair pair2 = new BasicNameValuePair("id", id+"");
//
//                        pairs.add(pair1);
//                        pairs.add(pair2);
//                        httpDelete.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
//
//                        httpDelete.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
//
//                        httpDelete.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
//                        // 发送GET请求
//                        httpClient= new DefaultHttpClient();
//                        HttpResponse httpResponse = httpClient.execute(httpDelete);
//                        // 如果服务器成功地返回响应
//                        LogTools.e("codeccccc", httpResponse.getStatusLine()
//                                .getStatusCode()+ " " + httpResponse.getEntity().getContentType() + " " + httpResponse.getStatusLine().getReasonPhrase());
//
//                        LogTools.e("success", convertStreamToString(httpResponse.getEntity().getContent()));
//
//                        if (httpResponse.getStatusLine()
//                                .getStatusCode() == 204)
//                        {
//                            // 获取服务器响应字符串
//                            LogTools.e("success","success");
//                            return "success";
//                        }
//                        return null;
//                    }
//                });
//        new Thread(task).start();
//        return task.get();
//    }

//    public static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
//
//        public static final String METHOD_NAME = "DELETE";
//        public String getMethod() { return METHOD_NAME; }
//        public HttpDeleteWithBody(final String uri) {
//            super();
//            setURI(URI.create(uri));
//        }
//        public HttpDeleteWithBody(final URI uri) {
//            super();
//            setURI(uri);
//        }
//        public HttpDeleteWithBody() { super(); }
//
//    }


    public static String Limit2(double data) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(data);
    }
    public static String Limit3(double data) {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return df.format(data);
    }

    public static String convertStreamToString(InputStream is) {
        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /***
     * 年月日 时分秒
     ***/
    public static String getStringtime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        long diff = 0;
        String d1 = df.format(date);
        try {
            diff = df.parse(d1).getTime();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d1;

    }

    /***
     * 年月日时分秒
     ***/
    @SuppressLint("SimpleDateFormat")
    public static String getStringToDate(long diff) {
        String day = null;
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String diffDaystring = "天";
        String diffHourstirng = "小时";
        String diffMinutestring = "分";
        String diffSecondstring = "秒";

        String diffdays = String.valueOf(diffDays);
        String diffhours = String.valueOf(diffHours);
        String diffminutes = String.valueOf(diffMinutes);
        String diffseconds = String.valueOf(diffSeconds);
        if (diffDays < 1) {
            diffDaystring = "";
            diffdays = "";
        }
        if (diffHours < 1) {
            if (diffDays < 1) {
                diffHourstirng = "";
                diffhours = "";
            }
        }
        if (diffMinutes < 1) {
            if (diffDays < 1 && diffHours < 1) {
                diffMinutestring = "";
                diffminutes = "";
            }
        }
        day = diffdays + diffDaystring + diffhours + diffHourstirng + diffminutes + diffMinutestring + diffseconds + diffSecondstring;
        return day;
    }

    /***
     * 年月日
     ***/
    public static String getStringtimeday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        long diff = 0;
        String d1 = df.format(date);
        try {
            diff = df.parse(d1).getTime();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d1;

    }

    /***
     * 年月日
     ***/
    public static String getStringtimeymd() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        long diff = 0;
        String d1 = df.format(date);
        try {
            diff = df.parse(d1).getTime();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d1;

    }

    /***
     * 年月日时分秒
     ***/
    @SuppressLint("SimpleDateFormat")
    public static String getStringToDate(long endtime, long startime, int reshtime) {
        SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timed = dateformat3.format(new Date(Long.valueOf(endtime + "000")));
        String stime = dateformat3.format(new Date(Long.valueOf(startime + "000")));
        return getStringtime(stime, timed, reshtime);
    }

    @SuppressLint("SimpleDateFormat")
    /***时间差***/
    public static String getStringtime(String stime, String endtime, int reshtime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day = null;
        Date d1 = null;
        Date d2 = null;
        Date d3 = null;
        try {
            d1 = format.parse(stime);
            d2 = format.parse(endtime);
//            Calendar nowTime = Calendar.getInstance();
//            nowTime.add(Calendar.SECOND, reshtime);
//
//            String three_days_ago = format.format(nowTime.getTime());
//            d3=format.parse(three_days_ago);
            //毫秒ms
            long diff = d2.getTime() - d1.getTime()/*- d3.getTime()*/;

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            day = diffDays + "天" + diffHours + "时" + diffMinutes + "分" + diffSeconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;

    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        long diff = 0;
        String d1 = df.format(date);
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }

    /**
     * 获取某日期往前多少天的日期
     *
     * @param nowDate
     * @param beforeNum
     * @return
     * @CreateTime
     * @Author PSY
     */
    public static String getBeforeDate(Integer beforeNum) {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar1.add(Calendar.DATE, -beforeNum);
        String three_days_ago = sdf1.format(calendar1.getTime());
        return (three_days_ago);//得到前beforeNum天的时间
    }

    /**
     * 获取某日期往前多少小时的时间
     *
     * @param nowDate
     * @param beforeNum
     * @return
     * @CreateTime
     * @Author PSY
     */
    public static Date getBeforeHour(Date nowDate, Integer beforeNum) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(nowDate);//把当前时间赋给日历
        calendar.add(Calendar.HOUR_OF_DAY, -beforeNum);  //设置为前beforeNum小时
        return calendar.getTime();   //得到前beforeNum小时的时间
    }

    /**
     * 判断是否是Integer类型
     *
     * @param str
     * @return
     * @author daichangfu
     */
    public static boolean isNumber(String str) {
        if (str != null && !"".equals(str.trim())) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            Long number = 0l;
            if (isNum.matches()) {
                number = Long.parseLong(str);
            } else {
                return false;
            }
            if (number > 2147483647) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /*/保留2位小数***/
    public static String setjiage(String data) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(new BigDecimal(Double.valueOf(data.toString())));
    }

    public static String set_four(String data) {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return df.format(new BigDecimal(Double.valueOf(data.toString())));
    }
    public static String set_four(float data) {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return df.format(new BigDecimal(data));
    }

    public static String set_four(double data) {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return df.format(new BigDecimal(data));
    }
}
