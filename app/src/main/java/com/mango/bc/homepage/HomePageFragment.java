package com.mango.bc.homepage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenter;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.adapter.HomePageAdapter;
import com.mango.bc.homepage.net.bean.LoadStageBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/3.
 */

public class HomePageFragment extends Fragment implements MyAllBookView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    private boolean isFirstEnter = true;
    private HomePageAdapter homePageAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 3;
    private int page = 0;
    private MyBookPresenter myBookPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.homepage, container, false);
        myBookPresenter = new MyBookPresenterImpl(this);
        if (NetUtil.isNetConnect(getActivity())){
            myBookPresenter.visitBooks(getActivity(), TYPE, 0, false);//获取书架的所有书
        }else {
            myBookPresenter.visitBooks(getActivity(), TYPE, 0, true);//获取书架的所有书
        }
        ButterKnife.bind(this, view);
        initView();
        refreshAndLoadMore();
        return view;
    }

    private void initView() {
        homePageAdapter = new HomePageAdapter(getActivity());
        recycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycle.setAdapter(homePageAdapter);
    }

    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        if (NetUtil.isNetConnect(getActivity())) {
                            RefreshStageBean refreshStageBean = new RefreshStageBean(true, true, true, true, true);
                            Log.v("yyyyyyy", "=====all--" + refreshStageBean.toString());
                            EventBus.getDefault().postSticky(refreshStageBean);
                        } else {
                            AppUtils.showToast(getActivity(), getString(R.string.check_net));
                            RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, false, false, false);
                            EventBus.getDefault().postSticky(refreshStageBean);
                        }
                        refreshLayout.finishRefresh();
                    }
                }, 200);
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadStageBean loadStageBean = new LoadStageBean(++page);
                        EventBus.getDefault().postSticky(loadStageBean);
                        refreshLayout.finishLoadMore();
                    }
                }, 200);
            }
        });
        refresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        refresh.setHeaderHeight(50);

        //触发自动刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            refresh.autoRefresh();//进来自动刷新
        } else {
            //mAdapter.refresh(initData());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void addAllBook(final List<MyBookBean> bookBeanList) {

    }

    @Override
    public void addSuccess(String s) {

    }

    @Override
    public void addFail(String f) {

    }
}
