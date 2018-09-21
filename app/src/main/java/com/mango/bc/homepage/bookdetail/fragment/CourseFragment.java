package com.mango.bc.homepage.bookdetail.fragment;

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

import com.mango.bc.R;
import com.mango.bc.bookcase.adapter.MyBookCourseAdapter;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.bookdetail.TxtActivity;
import com.mango.bc.homepage.bookdetail.adapter.BookCourseAdapter;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/12.
 */

public class CourseFragment extends Fragment {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookCourseAdapter bookCourseAdapter;
    private MyBookCourseAdapter myBookCourseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_book, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        Log.v("uuuuuuuuuuuu", "--2--");
        if (bookBean.getDescriptionImages() != null) {
            bookCourseAdapter = new BookCourseAdapter(bookBean.getChapters(), getActivity());
            recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycle.setAdapter(bookCourseAdapter);
            bookCourseAdapter.setOnItemClickLitener(mOnClickListenner);
        }
        //EventBus.getDefault().removeStickyEvent(MyBookBean.class);//展示完删除
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MyBookBeanEventBus(MyBookBean bookBean) {  //书架进来  不需要判断 直接可以播放
        if (bookBean == null) {
            return;
        }
        if (bookBean.getBook() != null) {
            myBookCourseAdapter = new MyBookCourseAdapter(bookBean.getBook().getChapters(), getActivity());
            recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycle.setAdapter(myBookCourseAdapter);
            bookCourseAdapter.setOnItemClickLitener(mOnClickListenner);
        }
    }

    private BookCourseAdapter.OnItemClickLitener mOnClickListenner = new BookCourseAdapter.OnItemClickLitener() {
        Intent intent;

        @Override
        public void onReadClick(View view, int position) {
            //AppUtils.showToast(getContext(), "播放");
        }

        @Override
        public void onTxtClick(View view, int position) {
            //AppUtils.showToast(getContext(), "阅读");
            intent = new Intent(getActivity(), TxtActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
