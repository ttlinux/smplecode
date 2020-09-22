package com.lottery.biying.Activity.User.Proxy.GeneralizeLink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.Adapter.AccTransferAdapter;
import com.lottery.biying.Adapter.GeneralizeLinkAdapter;
import com.lottery.biying.BaseParent.BaseFragment;
import com.lottery.biying.R;
import com.lottery.biying.bean.GeneralizeLinkBean;
import com.lottery.biying.util.Httputils;
import com.lottery.biying.util.LogTools;
import com.lottery.biying.util.MyJsonHttpResponseHandler;
import com.lottery.biying.util.ToastUtil;
import com.lottery.biying.view.ListviewWithBackTitle;
import com.lottery.biying.view.SwipeHeader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/4.
 */
public class ExistLinkFragment extends BaseFragment {

    ListviewWithBackTitle listview;
    private int currentPage=1;
    private final int RefreshCount=20;
    int mtotalItemCount,mfirstVisibleItem;
    boolean hasdata=true;
    ArrayList<GeneralizeLinkBean> arrayList=new ArrayList<>();
    GeneralizeLinkAdapter generalizeLinkAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_exist_link, null);
    }

    public void Initview() {
        listview=FindView(R.id.listview);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;

                GetData(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                GetData(false);
            }
        });
        GetData(true);
    }


    public void GetData(final boolean isinit) {

        if(isinit)
        {
            currentPage=1;
            hasdata=true;
            arrayList.clear();
            SetAdapter();
        }
        RequestParams re = new RequestParams();
        re.put("currentPage",currentPage+"");
        re.put("pageLimit",RefreshCount+"");
        Httputils.PostWithBaseUrl(Httputils.LinkList, re, new MyJsonHttpResponseHandler(getActivity(), true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listview.onRefreshComplete();
                hasdata=false;
                if(arrayList.size()==0)
                {
                    listview.ShowTextview();
                }

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("EXXXX",jsonObject.toString());
                listview.onRefreshComplete();
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg"));
                    return;
                }
                if(isinit)
                {
                    hasdata=true;
                    arrayList.clear();
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                }

                JSONObject datas=jsonObject.optJSONObject("datas");
                JSONArray resultList=datas.optJSONArray("resultList");
                for (int i = 0; i <resultList.length() ; i++) {
                    arrayList.add(GeneralizeLinkBean.AnaLysis(resultList.optJSONObject(i)));
                }
                if(resultList.length()<RefreshCount)
                    hasdata=false;
                if(  resultList.length()<RefreshCount)
                {
                    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if(arrayList.size()>=RefreshCount)
                    ToastUtil.showMessage(getActivity(), getString(R.string.completetoast));
                }
                SetAdapter();
                if(arrayList.size()==0)
                {
                    listview.ShowTextview();
                }
                else
                {
                    listview.ShowListview();
                }
            }
        });
    }

    private void SetAdapter()
    {
        if(generalizeLinkAdapter==null)
        {
            generalizeLinkAdapter=new GeneralizeLinkAdapter(arrayList,getActivity());
            listview.setAdapter(generalizeLinkAdapter);
        }
        else
        {
            generalizeLinkAdapter.Notidfy(arrayList);
        }
    }
}
