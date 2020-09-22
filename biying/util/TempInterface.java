package com.lottery.biying.util;

/**
 * Created by Administrator on 2018/6/9.
 */
public class TempInterface {

    public static  TempInterface tempInterface;
    MessageListener messageListener;

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public static TempInterface getInstance()
    {
        if(tempInterface==null)
            tempInterface=new TempInterface();
        return tempInterface;
    }

    public interface MessageListener
    {
        public void OnNotify();
    }
}
