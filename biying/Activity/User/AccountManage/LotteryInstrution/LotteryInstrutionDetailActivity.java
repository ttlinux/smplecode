package com.lottery.biying.Activity.User.AccountManage.LotteryInstrution;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseActivity;
import com.lottery.biying.R;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ScreenUtils;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/12.
 */
public class LotteryInstrutionDetailActivity extends BaseActivity {

    ScrollView scrollView;
    LinearLayout mainll;
    int ScreenWidth;
    ArrayList<Integer> widths=new ArrayList<>();
    String strs[];
    SwipeHeader swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotteryinstrution_detail);

        scrollView=FindView(R.id.scrollView);
        swipe=FindView(R.id.swipe);
        swipe.enablePullUp(this,false);
        swipe.setOnRefreshListener(new SwipeHeader.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                mainll.removeAllViews();
                GetData();
            }
        });
        mainll=FindView(R.id.mainll);
        strs=getResources().getStringArray(R.array.lotter_info_titles);
        ScreenWidth=ScreenUtils.getScreenWH(this)[0];
        widths.add(ScreenWidth*65/390);
        widths.add(ScreenWidth*85/390);
        widths.add(ScreenWidth*85/390);
        widths.add(ScreenWidth*155/390);
        TextView backtitle=FindView(R.id.backtitle);
        backtitle.setText(getIntent().getStringExtra(BundleTag.LotteryName));
        backtitle.setVisibility(View.VISIBLE);
        backtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        GetData();
    }

    private void GetData()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("lotteryCode", getIntent().getStringExtra(BundleTag.LotteryCode));
        requestParams.put("client", "2");
        Httputils.PostWithBaseUrl(Httputils.LotteryDetail, requestParams, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipe.setRefreshing(false);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                swipe.setRefreshing(false);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(LotteryInstrutionDetailActivity.this, jsonObject.optString("msg"));
                    return;
                }
                try {
                    JSONArray gameGroup=jsonObject.optJSONObject("datas").getJSONArray("gameGroup");
                    for (int i = 0; i < gameGroup.length(); i++) {
                        CreateItem(gameGroup.optJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /* {
                       "code": "cqssc_5x_zx",
                       "games": [
                           {
                               "code": "cqssc_5x_zx_fs",
                               "gf": "170000.0+13.0%",
                               "gj": "196000.0",
                               "name": "复式",
                               "saleDesc": "销售",
                               "saleStatus": 1
                           }
                       ],
                       "name": "直选"
                   }*/
    private void CreateItem(JSONObject jsonObject)
    {
        int pad6= ScreenUtils.getDIP2PX(this,6);
        LinearLayout ItemLL=new LinearLayout(this);
        LinearLayout.LayoutParams mll= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mll.topMargin=ScreenUtils.getDIP2PX(this,10);
        ItemLL.setLayoutParams(mll);
        ItemLL.setOrientation(LinearLayout.VERTICAL);

        TextView textView=new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        textView.setBackgroundColor(getResources().getColor(R.color.gray20));
        textView.setTextColor(0xFFFFFFFF);
        textView.setPadding(pad6, pad6, pad6, pad6);
        textView.setText(jsonObject.optString("name", ""));
        textView.setGravity(Gravity.CENTER);
        ItemLL.addView(textView);

        LinearLayout titles=new LinearLayout(this);
        titles.setOrientation(LinearLayout.HORIZONTAL);
        titles.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        titles.setBackgroundColor(getResources().getColor(R.color.gray6));

        for (int i = 0; i <strs.length ; i++) {
            TextView title=new TextView(this);
            title.setLayoutParams(new LinearLayout.LayoutParams(widths.get(i), ViewGroup.LayoutParams.WRAP_CONTENT));
            title.setPadding(pad6, pad6, pad6, pad6);
            title.setTextColor(getResources().getColor(R.color.gray19));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            title.setGravity(Gravity.CENTER);
            title.setText(strs[i]);

            ImageView line=new ImageView(this);
            line.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            line.setBackgroundColor(getResources().getColor(R.color.line));
            titles.addView(title);
            titles.addView(line);
        }
        ItemLL.addView(titles);


        JSONArray groupDetails=null;

        try {
            groupDetails=jsonObject.getJSONArray("groupDetails");
            if(groupDetails==null || groupDetails.length()<1)return;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i <groupDetails.length() ; i++) {
            JSONArray games=null;
            try {
                games=groupDetails.optJSONObject(i).getJSONArray("games");
                if(games==null || games.length()<1)continue;
            } catch (JSONException e) {
                e.printStackTrace();
                break;
            }
            ItemLL.addView(MakeHorLine());

            LinearLayout vll=new LinearLayout(this);
            vll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            vll.setOrientation(LinearLayout.HORIZONTAL);

            TextView MethodName=new TextView(this);
            MethodName.setLayoutParams(new LinearLayout.LayoutParams(widths.get(0), ViewGroup.LayoutParams.MATCH_PARENT));
            MethodName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            MethodName.setTextColor(getResources().getColor(R.color.gray19));
            MethodName.setText(groupDetails.optJSONObject(i).optString("name", ""));
            MethodName.setGravity(Gravity.CENTER);
            MethodName.setBackgroundColor(0xFFFFFFFF);
            vll.addView(MethodName);

            LinearLayout verll=new LinearLayout(this);
            verll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            verll.setOrientation(LinearLayout.VERTICAL);

            for (int j = 0; j <games.length() ; j++) {
                JSONObject jsobj=games.optJSONObject(j);

                int backColor=j%2!=0?getResources().getColor(R.color.gray6):0xFFFFFFFF;
                LinearLayout smallitem=new LinearLayout(this);
                smallitem.setOrientation(LinearLayout.HORIZONTAL);
                smallitem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                smallitem.setBackgroundColor(backColor);

                smallitem.addView(MakeVerLine());
                smallitem.addView(MakeTextview(jsobj.optString("name"), widths.get(1), pad6));
                smallitem.addView(MakeVerLine());
                smallitem.addView(MakeTextview(jsobj.optString("gj"), widths.get(2), pad6));
                smallitem.addView(MakeVerLine());
                smallitem.addView(MakeTextview(jsobj.optString("gf"), widths.get(3), pad6));

                verll.addView(smallitem);
            }
            vll.addView(verll);
            ItemLL.addView(vll);
        }

        ItemLL.addView(MakeHorLine());
        mainll.addView(ItemLL);
    }

    private ImageView MakeVerLine()
    {
        ImageView smallline=new ImageView(this);
        smallline.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        smallline.setBackgroundColor(getResources().getColor(R.color.line));
        return smallline;
    }

    private ImageView MakeHorLine()
    {
        ImageView smallline=new ImageView(this);
        smallline.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        smallline.setBackgroundColor(getResources().getColor(R.color.line));
        return smallline;
    }

    private TextView MakeTextview(String text,int width,int pad)
    {
        TextView imethod=new TextView(this);
        imethod.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
        imethod.setTextColor(getResources().getColor(R.color.gray19));
        imethod.setPadding(pad, pad, pad, pad);
        imethod.setText(text);
        imethod.setGravity(Gravity.CENTER);
        imethod.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        return imethod;
    }
}
