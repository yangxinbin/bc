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
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.activity.BuyBookActivity;
import com.mango.bc.homepage.adapter.BookNewestAdapter;
import com.mango.bc.homepage.bean.BuySuccessBean;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.LoadStageBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookNewestView;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewestFragment extends Fragment implements BookNewestView,MyAllBookView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookNewestAdapter bookNewestAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 4;//最新课
    private int page = 0;
    private TextView tv_stage;
    private MyBookPresenterImpl myBookPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newest, container, false);
        bookPresenter = new BookPresenterImpl(this);
        myBookPresenter = new MyBookPresenterImpl(this);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        if (NetUtil.isNetConnect(getActivity())){
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);
        }else {
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        EventBus.getDefault().removeStickyEvent(LoadStageBean.class);//移除加载
        if (bean.getNewestBook()) {
            page = 0;
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);//刷新从网络。
            bean.setNewestBook(false);//刷新完修改状态
            Log.v("yyyyyyy", "==?===4--" + bean.toString());
            EventBus.getDefault().postSticky(bean);
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void LoadStageBeanEventBus(LoadStageBean bean) {
        if (bean == null) {
            return;
        }
        page = bean.getNewestBookPage();
        Log.v("yyyyyyy", "=====4--" + page);
        bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);//刷新从网络。
    }

    private void initView() {
        bookNewestAdapter = new BookNewestAdapter(getActivity());
        recycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycle.setAdapter(bookNewestAdapter);
        bookNewestAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private BookNewestAdapter.OnItemClickLitener mOnClickListenner = new BookNewestAdapter.OnItemClickLitener() {
        @Override
        public void onItemPlayClick(View view, int position) {
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookNewestAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);

        }

        @Override
        public void onItemGetClick(View view, int position) {
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookNewestAdapter.getItem(position));
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
                EventBus.getDefault().postSticky(bookNewestAdapter.getItem(position));
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivity(intent);
            }

        }

    };

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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void addNewestBook(final List<BookBean> bookBeanList) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bookBeanList == null || bookBeanList.size() == 0) {
                        AppUtils.showToast(getActivity(), getString(R.string.date_over));
                        return;
                    }
                    if (page == 0) {
                        Log.v("yyyyyyy", "==?like===4--"+bookBeanList.get(4).getLikes());
                        bookNewestAdapter.reMove();
                        bookNewestAdapter.setmDate(bookBeanList);
                    } else {
                        //加载更多
                        for (int i = 0; i < bookBeanList.size(); i++) {
                            bookNewestAdapter.addItem(bookBeanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
                        }
                    }

                }
            });
    }

    @Override
    public void addSuccessNewestBook(String s) {

    }

    @Override
    public void addFailNewestBook(String f) {
        if (getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getActivity(), "最新上新课程请求失败");
            }
        });
    }
}
