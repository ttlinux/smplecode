package com.lottery.biying.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lottery.biying.R;
import com.lottery.biying.bean.UserInfoBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/10/16.
 */
public class VerrifyPointForTextview {

    public static boolean VerifyValue(final ArrayList<EditText> editTexts,final UserInfoBean bean,final Context context)
    {
        if(bean==null)
        {
            ToastUtil.showMessage(context,"等待数据下载中，请稍等");
            return false;
        }
        String maxpoint[]={bean.getBackWater().getLottery(),bean.getBackWater().getLive(),
                bean.getBackWater().getElectronic(),bean.getBackWater().getSport(),bean.getBackWater().getFish(),bean.getBackWater().getCard()};
        String tips[]=context.getResources().getStringArray(R.array.lottery_point_titles);
        for (int i = 0; i < editTexts.size(); i++) {
            double index = Verify2(editTexts.get(i).getText().toString(), maxpoint[i], context);
            if(index==-1)
            {
                ToastUtil.showMessage(context,"请输入正确的"+tips[i]+"(n >= 0，n <= "+maxpoint[i]+")");
                return false;
            }
        }

        return true;
    }
    public static void AddVerifyView(final EditText editText,TextView Plus,TextView minus,final String Maxpoint,final Context context)
    {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Verify(s.toString(),Maxpoint,context);
            }
        });
        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double index=Verify(editText.getText().toString(),Maxpoint,context);
                if(index>-1 && Maxpoint.length()>0 && index<Double.valueOf(Maxpoint))
                {
                    index=index+0.1d;
                    editText.setText(String.format("%.1f",index));
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double index=Verify(editText.getText().toString(),Maxpoint,context);
                if(index>0.1)
                {
                    index=index-0.1d;
                    editText.setText(String.format("%.1f",index));
                }
            }
        });
    }

    public static double Verify(String value,String Maxpoint,Context context)
    {
        double index = 0;
        try {
            index=Double.valueOf(value);
        }
        catch (NumberFormatException ex)
        {
            ex.printStackTrace();
            ToastUtil.showMessage(context,"请输入正确的返点(n >= 0，n <= "+Maxpoint+")");
            return -1;
        }
        if( index<0 || (Maxpoint.length()>0 && index>Double.valueOf(Maxpoint) ))
        {
            ToastUtil.showMessage(context,"请输入正确的返点(n >= 0，n <= "+Maxpoint+")");
            return -1;
        }
        return index;
    }

    public static double Verify2(String value,String Maxpoint,Context context)
    {
        double index = 0;
        try {
            index=Double.valueOf(value);
        }
        catch (NumberFormatException ex)
        {
            ex.printStackTrace();
            return -1;
        }
        if( index<0 || (Maxpoint.length()>0 && index>Double.valueOf(Maxpoint) ))
        {
            return -1;
        }
        return index;
    }
}
