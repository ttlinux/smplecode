package com.lottery.biying.util;

import android.os.Environment;

import com.lottery.biying.BaseParent.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 */
public class LoginRecord {

    private  static final int key=Integer.MAX_VALUE/73+'2'^6;

    public static void SaveReord(String username,String Password)
    {
        try {
            File temp=new File(getRootpath()+"/Lottery/"+ BaseApplication.packagename+"/Record.cc");
            if(temp!=null &&temp.exists())
            {
                temp.delete();
            }
            RandomAccessFile file=new RandomAccessFile(getRootpath()+"/Lottery/"+ BaseApplication.packagename+"/Record.cc","rw");
            byte[] pbyte=en(RepluginMethod.ENCRYPT(Password));
            byte[] ubyte=en(username.getBytes());
            file.write((byte)(username.length()^key));
            file.write(ubyte);
            file.write(pbyte);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteFile()
    {
        File file=new File(getRootpath()+"/lottery/Record.cc");
        if(file!=null && file.exists())
        {
            file.delete();
        }
    }

    public static String[] getReocrd()
    {
        String record[]=new String[2];
        try {
            RandomAccessFile file=new RandomAccessFile(getRootpath()+"/Lottery/"+ BaseApplication.packagename+"/Record.cc","rw");
            if(file.length()==0)
                return null;
            byte[] bytes=new byte[(int)file.length()];
            file.read(bytes);
            en(bytes);
            int usernamelength=bytes[0];
            byte[] ubytes=new byte[usernamelength];
            for (int i = 1; i < usernamelength+1; i++) {
                ubytes[i-1]=bytes[i];
            }
            record[0]=new String(ubytes);

            byte[] pbytes=new byte[bytes.length-usernamelength-1];
            for (int i =usernamelength+1 ; i < bytes.length; i++) {
                pbytes[i-usernamelength-1]=bytes[i];
            }
            record[1]=RepluginMethod.DECRYPT(pbytes);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return record;
    }

    private static byte[] en(byte[] source)
    {
        for (int i = 0; i < source.length; i++) {
            source[i]=(byte)(source[i]^key);
        }
        return source;
    }

    public static String getRootpath()
    {
        String root_path= Environment.getExternalStorageDirectory().getPath();
        if (root_path == null || root_path.equalsIgnoreCase("")) {
            List<String> lResult = new ArrayList<String>();
            try {
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec("mount");
                InputStream is = proc.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("extSdCard")) {
                        String[] arr = line.split(" ");
                        String path = arr[1];
                        File file = new File(path);
                        if (file.isDirectory()) {
                            lResult.add(path);
                        }
                    }
                }
                isr.close();
            } catch (Exception e) {
            }

            if (lResult.size() > 0)
                root_path = lResult.get(0);
            else {
                LogTools.e("错误","没手机卡");
                return null;
            }
        }
        return root_path;
    }
}
