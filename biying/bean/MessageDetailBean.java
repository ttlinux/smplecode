package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/9.
 */
public class MessageDetailBean {


    /**
     * groupKey : 1234
     * message : 222
     * messageTime : 05-02
     * messageTimeLong : 2018-05-02 15:07:55
     * sender : nihaoma
     * senderSimple : 我
     */

    private String groupKey;
    private String message;
    private String messageTime;
    private String messageTimeLong;
    private String sender;
    private String senderSimple;

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTimeLong() {
        return messageTimeLong;
    }

    public void setMessageTimeLong(String messageTimeLong) {
        this.messageTimeLong = messageTimeLong;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderSimple() {
        return senderSimple;
    }

    public void setSenderSimple(String senderSimple) {
        this.senderSimple = senderSimple;
    }

    public static MessageDetailBean Analysis(JSONObject jsonObject)
    {
        MessageDetailBean bean=new MessageDetailBean();
        bean.setGroupKey(jsonObject.optString("groupKey", ""));
        bean.setMessage(jsonObject.optString("message", ""));
        bean.setMessageTime(jsonObject.optString("messageTime", ""));
        bean.setMessageTimeLong(jsonObject.optString("messageTimeLong", ""));
        bean.setSender(jsonObject.optString("sender", ""));
        bean.setSenderSimple(jsonObject.optString("senderSimple",""));
        return bean;
    }
}
