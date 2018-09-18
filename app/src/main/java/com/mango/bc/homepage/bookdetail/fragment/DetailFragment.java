package com.mango.bc.homepage.bookdetail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.bookcase.adapter.MyBookDetailAdapter;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.bookdetail.adapter.BookDetailAdapter;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/12.
 */

public class DetailFragment extends Fragment {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookDetailAdapter bookDetailAdapter;
    private MyBookDetailAdapter myBookDetailAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_book, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        Log.v("uuuuuuuuuuuu", "--1--");
        if (bookBean.getDescriptionImages() != null) {
            bookDetailAdapter = new BookDetailAdapter(bookBean.getDescriptionImages(), getActivity());
            recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycle.setAdapter(bookDetailAdapter);
        }
        //EventBus.getDefault().removeStickyEvent(MyBookBean.class);//展示完删除
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MyBookBeanEventBus(MyBookBean bookBean) {  //书架进来  不需要判断 直接可以播放
        if (bookBean == null) {
            return;
        }
        if (bookBean.getBook() != null) {
            myBookDetailAdapter = new MyBookDetailAdapter(bookBean.getBook().getDescriptionImages(), getActivity());
            recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycle.setAdapter(myBookDetailAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
