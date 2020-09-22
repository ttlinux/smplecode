package com.lottery.biying.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.MainActivity;
import com.qihoo360.replugin.RePlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/3.
 */
public class RepluginMethod {


    private static final String Classname="shell.lottery.com.lotteryshell.util.AuthCodeMethod";
    public static BaseApplication getApplication(Context context)
    {
        if(context.getApplicationContext() instanceof BaseApplication)
        {
            return (BaseApplication)context.getApplicationContext();
        }
        else
        {
            return (BaseApplication) RePlugin.getPluginContext().getApplicationContext();
//            return null;
        }
    }


    public static Application getHostApplication(Context context)
    {
        Application application=(Application) RePlugin.getHostContext().getApplicationContext();

        if(application==null)
        {
            return (BaseApplication) RePlugin.getPluginContext().getApplicationContext();
        }
        else
        {
            return application;
        }
//        return (BaseApplication)context.getApplicationContext();
    }

    public static Intent HostMainclass(Context context)
    {
        String PackageName=RepluginMethod.getApplication(context).getPackageName();
        Intent intent = new Intent();
        intent.setClassName(PackageName, "shell.lottery.com.lotteryshell.MainActivity");
        return intent;
    }

    public static Intent Hostclass(Context context,String name)
    {
        String PackageName=RepluginMethod.getApplication(context).getPackageName();
        Intent intent = new Intent();
        intent.setClassName(PackageName, "shell.lottery.com.lotteryshell"+"."+name);
        return intent;
    }

    public static SharedPreferences getHosttSharedPreferences(Context context)
    {
        return  getHostApplication(context).getSharedPreferences(BundleTag.Lottery, Activity.MODE_PRIVATE);
    }

    public  static byte[] ENCRYPT(String content)
    {
        Object obj=getMethod(Classname, "ENCRYPT",content, String.class);
        if(obj==null)
        {
            return content.getBytes();
        }
        return (byte[])obj;
    };
    public  static String DECRYPT(byte[] content)
    {
        Object obj=getMethod(Classname, "DECRYPT",content, String.class);
        if(obj==null)
        {
            return new String(content);
        }
        return (String)obj;
    };

    public static Object getMethod(String classname,String Methodname,Object arg,Class<?>...classargs)
    {
        try {
            Class tempclass=Class.forName(classname);
            Method method=tempclass.getMethod(Methodname, classargs);
            return method.invoke(tempclass,arg);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
