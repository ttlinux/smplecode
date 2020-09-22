package com.lottery.biying.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;

/**
 * Created by Administrator on 2018/5/28.
 */
public class Special_Dialog extends Dialog {

    boolean Cancancel=true;
    boolean noStyle=false;
    public  Runnable runable=new Runnable() {
        @Override
        public void run() {
            Special_Dialog.this.dismiss();
        }
    };

    public Special_Dialog(Context show_context,Context re_context) {
        super(show_context);
        InitView(show_context,re_context);
    }
    public Special_Dialog(Context context, boolean Cancancel) {
        super(context);
        this.Cancancel=Cancancel;
    }

    public Special_Dialog(Context context, boolean Cancancel, boolean noStyle) {
        super(context);
        this.Cancancel=Cancancel;
        this.noStyle=noStyle;
    }

    public Special_Dialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected Special_Dialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void InitView(Context show_context,Context re_context)
    {
        getContext().setTheme(R.style.loading_dialog);
        View view=null;
//         view = RePlugin.fetchViewByLayoutName(BundleTag.PluginName, "dialog_loading", null);// 得到加载view
        if(noStyle)
        {
            View progressBar=view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_loading_view);// 加载布局.
        setCancelable(Cancancel); // 是否可以按“返回键”消失
        setCanceledOnTouchOutside(Cancancel); // 点击加载框以外的区域
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);


    }
}
