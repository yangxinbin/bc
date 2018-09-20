package com.mango.bc.bookcase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.bookcase.adapter.MyBookGirdAdapter;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenter;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyFreeBookView;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/5.
 */

public class MyFreeFragment extends Fragment implements MyFreeBookView{
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_nobook)
    ImageView imgNobook;
    private MyBookGirdAdapter myBookGirdAdapter;
    private boolean isFirstEnter = true;
    private MyBookPresenter myBookPresenter;
    private final int TYPE = 2;//免费
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_free, container, false);
        myBookPresenter = new MyBookPresenterImpl(this);
        ButterKnife.bind(this, view);
        initView();
        if (NetUtil.isNetConnect(getActivity())){
            myBookPresenter.visitBooks(getActivity(), TYPE, page, false);
        }else {
            myBookPresenter.visitBooks(getActivity(), TYPE, page, true);
        }
        refreshAndLoadMore();
        return view;
    }

    private void initView() {
        myBookGirdAdapter = new MyBookGirdAdapter(getActivity());
        recycle.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        recycle.setAdapter(myBookGirdAdapter);
        myBookGirdAdapter.setOnItemClickLitener(mOnClickListenner);
    }
    private MyBookGirdAdapter.OnItemClickLitener mOnClickListenner = new MyBookGirdAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().removeStickyEvent(BookBean.class);
            EventBus.getDefault().postSticky(myBookGirdAdapter.getItem(position));
            startActivity(intent);
        }

    };
    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        if (NetUtil.isNetConnect(getActivity())) {
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, false);
                        } else {
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, true);
                        }
                        refreshLayout.finishRefresh();
                    }
                }, 500);
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        if (NetUtil.isNetConnect(getActivity())) {
                            Log.v("yyyyyy", "-------isNetConnect-------");
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, false);
                        } else {
                            Log.v("yyyyyy", "-------isnoNetConnect-------");
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, true);
                        }
                        refreshLayout.finishLoadMore();
                    }
                }, 500);
            }
        });
        refresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        refresh.setHeaderHeight(50);

        //触发自动刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            //refresh.autoRefresh();
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
    public void addFreeBook(final List<MyBookBean> bookBeanList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v("doPostAll", page +"==="+bookBeanList.size());
                if (bookBeanList == null || bookBeanList.size() == 0) {
                    if (page == 0) {
                        refresh.setVisibility(View.GONE);
                        imgNobook.setVisibility(View.VISIBLE);
                        return;
                    }
                    AppUtils.showToast(getActivity(), getString(R.string.date_over));
                    return;
                } else {
                    refresh.setVisibility(View.VISIBLE);
                    imgNobook.setVisibility(View.GONE);
                }
                if (page == 0) {
                    myBookGirdAdapter.reMove();
                    myBookGirdAdapter.setmDate(bookBeanList);
                } else {
                    //加载更多
                    for (int i = 0; i < bookBeanList.size(); i++) {
                        myBookGirdAdapter.addItem(bookBeanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
                    }
                }
            }
        });
    }

    @Override
    public void addSuccess(String s) {

    }

    @Override
    public void addFail(String f) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getActivity(), "免费课程书架请求失败");
            }
        });
    }
}