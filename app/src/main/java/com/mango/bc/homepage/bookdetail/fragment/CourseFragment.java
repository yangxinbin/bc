package com.mango.bc.homepage.bookdetail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.bookdetail.TxtActivity;
import com.mango.bc.homepage.bookdetail.adapter.BookCourseAdapter;
import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.homepage.bookdetail.bean.PlayBarBean;
import com.mango.bc.homepage.bookdetail.play.BaseServiceFragment;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.bookdetail.play.service.OnPlayerEventListener;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/12.
 */

public class CourseFragment extends BaseServiceFragment implements OnPlayerEventListener {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookCourseAdapter bookCourseAdapter;
    private SPUtils spUtils;
    //private BookCourseAdapter myBookCourseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_book, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
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
        List<BookMusicDetailBean> bookMusicDetailBeanList = new ArrayList<>();
        bookMusicDetailBeanList.clear();
        if (bookBean.getChapters() != null) {
            for (int i = 0; i < bookBean.getChapters().size(); i++) {
                BookMusicDetailBean bookMusicDetailBean = new BookMusicDetailBean();
                bookMusicDetailBean.setName(bookBean.getAuthor().getName());
                bookMusicDetailBean.setTitle(bookBean.getTitle());
                bookMusicDetailBean.setBookId(bookBean.getId());
                bookMusicDetailBean.setIsFree(bookBean.getChapters().get(i).isFree());
                bookMusicDetailBean.setMp3Name(bookBean.getChapters().get(i).getTitle());
                if (bookBean.getChapters().get(i).getAudio() != null)
                    bookMusicDetailBean.setMp3Path(bookBean.getChapters().get(i).getAudio().getFileName());
                bookMusicDetailBean.setDuration(bookBean.getChapters().get(i).getDuration());
                bookMusicDetailBeanList.add(bookMusicDetailBean);
            }
            bookCourseAdapter = new BookCourseAdapter(bookMusicDetailBeanList, getActivity());
            if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing())
                bookCourseAdapter.setIsPlaylist(true);
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
        List<BookMusicDetailBean> bookMusicDetailBeanList = new ArrayList<>();
        bookMusicDetailBeanList.clear();
        if (bookBean.getBook() != null) {
            for (int i = 0; i < bookBean.getBook().getChapters().size(); i++) {
                BookMusicDetailBean bookMusicDetailBean = new BookMusicDetailBean();
                bookMusicDetailBean.setName(bookBean.getBook().getAuthor().getName());
                bookMusicDetailBean.setBookId(bookBean.getId());
                bookMusicDetailBean.setTitle(bookBean.getBook().getTitle());
                bookMusicDetailBean.setIsFree(bookBean.getBook().getChapters().get(i).isFree());
                bookMusicDetailBean.setMp3Name(bookBean.getBook().getChapters().get(i).getTitle());
                if (bookBean.getBook().getChapters().get(i).getAudio() != null)
                    bookMusicDetailBean.setMp3Path(bookBean.getBook().getChapters().get(i).getAudio().getFileName());
                bookMusicDetailBean.setDuration(bookBean.getBook().getChapters().get(i).getDuration());
                bookMusicDetailBeanList.add(bookMusicDetailBean);
            }
            bookCourseAdapter = new BookCourseAdapter(bookMusicDetailBeanList, getActivity());
            if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing())
                bookCourseAdapter.setIsPlaylist(true);
            recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycle.setAdapter(bookCourseAdapter);
            bookCourseAdapter.setOnItemClickLitener(mOnClickListenner);
        }
    }

    private BookCourseAdapter.OnItemClickLitener mOnClickListenner = new BookCourseAdapter.OnItemClickLitener() {
        Intent intent;

        @Override
        public void onReadClick(View view, int position) {
            AudioPlayer.get().init(getActivity());
            AudioPlayer.get().play(position);
        }

        @Override
        public void onTxtClick(View view, int position) {
            //AppUtils.showToast(getContext(), "阅读");
            intent = new Intent(getActivity(), TxtActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    };

    @Override
    protected void onServiceBound() {
        bookCourseAdapter.setIsPlaylist(true);
        Log.v("ssssssssss", "----" + AudioPlayer.get().getPlayPosition());
        AudioPlayer.get().addOnPlayEventListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        AudioPlayer.get().removeOnPlayEventListener(this);

    }


    @Override
    public void onChange(BookMusicDetailBean music) {
        bookCourseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayerStart() {

    }

    @Override
    public void onPlayerPause() {

    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }
}
