package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/9.
 */
public class MessageBean {


    /**
     * groupKey : 004f278796427f126f5facbc56668eec
     * id : 64
     * messageTime : 2018-05-09 16:30:57
     * newMessage : 好的
     * receiver : nihaoma
     * receiverSimple : 我
     * sender : okmokm
     * senderSimple : 下级
     * status : 0
     * title : 发一个消息
     * type : 0
     */

    private String groupKey;
    private String id;
    private String messageTime;
    private String newMessage;
    private String receiver;
    private String receiverSimple;
    private String sender;
    private String senderSimple;
    private String status;
    private String title;
    private String type;
    private boolean ischeck=false;

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverSimple() {
        return receiverSimple;
    }

    public void setReceiverSimple(String receiverSimple) {
        this.receiverSimple = receiverSimple;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static MessageBean AnalySis(JSONObject jsonObject)
    {
        MessageBean bean=new MessageBean();
        bean.setGroupKey(jsonObject.optString("groupKey",""));
        bean.setId(jsonObject.optString("id",""));
        bean.setMessageTime(jsonObject.optString("messageTime",""));
        bean.setNewMessage(jsonObject.optString("newMessage",""));
        bean.setReceiver(jsonObject.optString("receiver",""));
        bean.setReceiverSimple(jsonObject.optString("receiverSimple",""));
        bean.setSender(jsonObject.optString("sender",""));
        bean.setSenderSimple(jsonObject.optString("senderSimple",""));
        bean.setStatus(jsonObject.optString("status",""));
        bean.setTitle(jsonObject.optString("title",""));
        bean.setType(jsonObject.optString("type",""));
        return bean;

    }
}
