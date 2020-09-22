package com.lottery.biying.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.Activity.LotteryActivity;
import com.lottery.biying.Activity.LotteryActivity_For_K3;
import com.lottery.biying.Activity.User.LoginActivity;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.BuildConfig;
import com.lottery.biying.R;
import com.lottery.biying.bean.LotteryPlaytypeBean;
import com.lottery.biying.bean.MenuBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.RepluginMethod;
import com.lottery.biying.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/25.
 */
public class MainGridAdapter extends BaseAdapter {

    Activity context;
    ArrayList<MenuBean> Arrmenus;

    public MainGridAdapter(Context context, ArrayList<MenuBean> Arrmenus) {
        this.context = (Activity) context;
        this.Arrmenus = Arrmenus;
    }

    @Override
    public int getCount() {
        return Arrmenus.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(context, R.layout.item_main_fragment, null);

        LinearLayout contentview = (LinearLayout) convertView.findViewById(R.id.contentview);

        TextView title = (TextView) contentview.getChildAt(0);
        TextView content = (TextView) contentview.getChildAt(1);
        title.setText(Arrmenus.get(position).getMenuName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPlayMethod(Arrmenus.get(position));
            }
        });
        return convertView;
    }

    private void GetPlayMethod(final MenuBean bean) {
        long time=System.currentTimeMillis();
        final MenuBean beans= RepluginMethod.getApplication(context).getMenuBeans().get(bean.getMenuCode());
//        if(RepluginMethod.getApplication(context).getBaseapplicationUsername()==null)
//        {
//            context.startActivity(new Intent(context,LoginActivity.class));
//            return;
//        }
        if (beans != null ) {
            Class cls = null;
            if (bean.getMenuCode().contains("k3")) {
                cls = LotteryActivity_For_K3.class;
            } else
            {
                cls = LotteryActivity.class;
            }
            Intent intent = new Intent(context, cls);
            intent.putExtra(BundleTag.id, bean.getMenuCode());
            intent.putExtra(BundleTag.LotteryName, bean.getMenuName());
            context.startActivity(intent);
        }
//        RequestParams requestparams = new RequestParams();
//        requestparams.put("code", bean.getMenuCode());
//        Httputils.PostWithBaseUrl(Httputils.playmethod, requestparams, new MyJsonHttpResponseHandler(context, true) {
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
//                    ToastUtil.showMessage(context, jsonObject.optString("msg"));
//                    return;
//                }
//                JSONObject datas = jsonObject.optJSONObject("datas");
//                JSONArray list = datas.optJSONArray("list");
//                SparseArray beanlist1 = new SparseArray();
//                for (int i = 0; i < list.length(); i++) {
//                    JSONArray list2 = list.optJSONObject(i).optJSONArray("list");
//                    ArrayList<LotteryPlaytypeBean> beans = new ArrayList<LotteryPlaytypeBean>();
//                    for (int j = 0; j < list2.length(); j++) {
//                        beans.add(LotteryPlaytypeBean.HandlerJson(list2.optJSONObject(j)));
//                    }
//                    beanlist1.put(i, beans);
//                }
//                ((BaseApplication) (context.getApplication())).getLotteryPlaytypeBeans().put(bean.getMenuCode(), beanlist1);
//                Intent intent = new Intent(context, LotteryActivity.class);
//                intent.putExtra(BundleTag.id, bean.getMenuCode());
//                intent.putExtra(BundleTag.LotteryName, bean.getMenuName());
//                context.startActivity(intent);
//            }
//
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//
//            }
//        });
    }
}
