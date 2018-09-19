package com.mango.bc.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.activity.freebook.FreeBookActivity;
import com.mango.bc.homepage.adapter.BookGirdFreeAdapter;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookFreeView;
import com.mango.bc.util.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FreeFragment extends Fragment implements BookFreeView {
    @Bind(R.id.see_more)
    TextView seeMore;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookGirdFreeAdapter bookGirdFreeAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 3;//免费课
    private int page = 0;
    private ArrayList<BookBean> mData = new ArrayList<BookBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.free, container, false);
        bookPresenter = new BookPresenterImpl(this);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getFreeBook()) {
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);//刷新从网络。
            bean.setFreeBook(false);//刷新完修改状态
            Log.v("yyyyyyy", "=====3--" + bean.toString());
            EventBus.getDefault().postSticky(bean);
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }

    private void initView() {
        bookGirdFreeAdapter = new BookGirdFreeAdapter(getActivity());
        recycle.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        recycle.setAdapter(bookGirdFreeAdapter);
        bookGirdFreeAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private BookGirdFreeAdapter.OnItemClickLitener mOnClickListenner = new BookGirdFreeAdapter.OnItemClickLitener() {
        @Override
        public void onItemPlayClick(View view, int position) {
            Log.v("wwwwwww","======pi");
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            intent.putExtra("foot_play",true);
            startActivity(intent);
        }

        @Override
        public void onItemGetClick(View view, int position) {
            Log.v("wwwwwww","======gi");
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            intent.putExtra("foot_free_get",true);
            startActivity(intent);
        }

        @Override
        public void onPlayClick(View view, int position) {//播放
            Log.v("wwwwwww","======p");
        }

        @Override
        public void onGetClick(View view, int position) {//领取
            getFreeBook();
        }
    };

    private void getFreeBook() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.see_more)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), FreeBookActivity.class);
        startActivity(intent);
    }

    @Override
    public void addFreeBook(final List<BookBean> bookBeanList) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("yyyyyyyyyyy", "=====3===" + bookBeanList.size());
                    if (bookBeanList == null || bookBeanList.size() == 0) {
                        AppUtils.showToast(getActivity(), getString(R.string.date_over));
                        return;
                    }
                    if (mData != null) {
                        mData.clear();
                    }
                    if (page == 0) {
                        for (int i = 0; i < 3; i++) {//
                            if (i > bookBeanList.size() - 1)
                                continue;
                            mData.add(bookBeanList.get(i)); //一次显示page= ? 20条数据
                        }
                        bookGirdFreeAdapter.setmDate(mData);
                    }
                }
            });
    }

    @Override
    public void addSuccessFreeBook(String s) {

    }

    @Override
    public void addFailFreeBook(String f) {
        if (getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getActivity(), "免费课程请求失败");
            }
        });
    }
}
