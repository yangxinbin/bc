package com.mango.bc.homepage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.bc.R;
import com.mango.bc.homepage.adapter.BookAdapter;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.LoadStageBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookView;
import com.mango.bc.util.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewestFragment extends Fragment implements BookView{
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookAdapter bookAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 4;//最新课
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newest, container, false);
        bookPresenter = new BookPresenterImpl(this);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getNewestBook()) {
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);//刷新从网络。
            bean.setNewestBook(false);//刷新完修改状态
            Log.v("yyyyyyy", "=====4--" + bean.toString());
            EventBus.getDefault().postSticky(bean);
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }
/*    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void LoadStageBeanEventBus(LoadStageBean bean) {
        if (bean == null) {
            return;
        }
    }*/
    private void initView() {
        bookAdapter = new BookAdapter(getActivity());
        recycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycle.setAdapter(bookAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void addCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList) {

    }

    @Override
    public void addCompetitiveBook(List<BookBean> bookBeanList) {

    }

    @Override
    public void addExpertBook(List<BookBean> bookBeanList) {

    }

    @Override
    public void addFreeBook(List<BookBean> bookBeanList) {

    }

    @Override
    public void addNewestBook(final List<BookBean> bookBeanList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bookBeanList == null || bookBeanList.size() == 0) {
                    AppUtils.showToast(getActivity(), getString(R.string.date_over));
                    return;
                }
                if (page == 0) {
                    bookAdapter.reMove();
                    bookAdapter.setmDate(bookBeanList);
                } else {
                    //加载更多
                    for (int i = 0; i < bookBeanList.size(); i++) {
                        bookAdapter.addItem(bookBeanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
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
                AppUtils.showToast(getActivity(), "免费课程请求失败");
            }
        });
    }
}
