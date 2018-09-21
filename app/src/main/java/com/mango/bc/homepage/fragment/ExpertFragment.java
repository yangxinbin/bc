package com.mango.bc.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.activity.BuyBookActivity;
import com.mango.bc.homepage.activity.expertbook.ExpertBookActivity;
import com.mango.bc.homepage.adapter.BookExpertAdapter;
import com.mango.bc.homepage.bean.BuySuccessBean;
import com.mango.bc.homepage.bookdetail.ExpertBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookExpertView;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpertFragment extends Fragment implements BookExpertView {
    @Bind(R.id.see_more)
    TextView seeMore;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookExpertAdapter bookExpertAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 2;//大咖课
    private int page = 0;
    private ArrayList<BookBean> mData = new ArrayList<BookBean>();
    public TextView tv_stage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expert, container, false);
        bookPresenter = new BookPresenterImpl(this);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        if (NetUtil.isNetConnect(getActivity())) {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);
        } else {
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);
        }
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BuySuccessBeanEventBus(BuySuccessBean bean) {
        if (bean == null) {
            return;
        }
        Log.v("bbbbb","---1----"+bean.getBuySuccess());

        if (bean.getBuySuccess()){
            Log.v("bbbbb","----2---");
            tv_stage.setText("播放");
        }
        EventBus.getDefault().removeStickyEvent(BuySuccessBean.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getExpertBook()) {
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);//刷新从网络。
            bean.setExpertBook(false);//刷新完修改状态
            Log.v("yyyyyyy", "=====3--" + bean.toString());
            EventBus.getDefault().postSticky(bean);
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }

    private void initView() {
        bookExpertAdapter = new BookExpertAdapter(getActivity());
        recycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycle.setAdapter(bookExpertAdapter);
        bookExpertAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private BookExpertAdapter.OnItemClickLitener mOnClickListenner = new BookExpertAdapter.OnItemClickLitener() {

        @Override
        public void onItemPlayClick(View view, int position) {
            Intent intent = new Intent(getActivity(), ExpertBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookExpertAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onItemGetClick(View view, int position) {
            Intent intent = new Intent(getActivity(), ExpertBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookExpertAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onPlayClick(View view, int position) {

        }

        @Override
        public void onGetClick(View view, int position) {
            tv_stage = view.findViewById(R.id.tv_stage);
            if (tv_stage.getText().equals("播放")){
                Log.v("bbbbbbbb","-----"+tv_stage.getText());
            }else {
                Intent intent = new Intent(getActivity(), BuyBookActivity.class);
                EventBus.getDefault().postSticky(bookExpertAdapter.getItem(position));
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.see_more)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), ExpertBookActivity.class);
        startActivity(intent);
    }

    @Override
    public void addExpertBook(final List<BookBean> bookBeanList) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("yyyyyyyyyyy", "=====2===" + bookBeanList.size());
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
                        bookExpertAdapter.setmDate(mData);
                    }
                }
            });
    }

    @Override
    public void addSuccessExpertBook(String s) {

    }

    @Override
    public void addFailExpertBook(String f) {
        if (getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getActivity(), "大咖课程请求失败");
            }
        });
    }

}
