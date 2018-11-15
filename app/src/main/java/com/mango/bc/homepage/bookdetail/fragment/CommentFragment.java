package com.mango.bc.homepage.bookdetail.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.bookdetail.adapter.BookCommentAdapter;
import com.mango.bc.homepage.bookdetail.bean.CommentBean;
import com.mango.bc.homepage.bookdetail.jsonutil.JsonBookDetailUtils;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.util.ACache;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.Urls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/12.
 */

public class CommentFragment extends Fragment {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    private LinearLayoutManager mLayoutManager;
    private boolean isFirstEnter = true;
    private int page = 0;
    private BookCommentAdapter adapter;
    private String bookId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_book, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        if (getActivity().getIntent().getStringExtra("bannerBookId") != null) {
            bookId = getActivity().getIntent().getStringExtra("bannerBookId");
        }
        initView();
        if (NetUtil.isNetConnect(getActivity())){
            vivistNet(false,page);
        }else {
            vivistNet(true,page);
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        bookId = bookBean.getId();
        Log.v("uuuuuuuuuuuu", "--3--");
        //EventBus.getDefault().removeStickyEvent(MyBookBean.class);//展示完删除
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MyBookBeanEventBus(MyBookBean bookBean) {  //书架进来  不需要判断 直接可以播放
        if (bookBean == null) {
            return;
        }
        if (bookBean.getBook() != null) {
            bookId = bookBean.getBook().getId();
        }
    }

    private void initView() {
        recycle.setHasFixedSize(true);//固定宽高
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycle.setLayoutManager(mLayoutManager);
        recycle.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        adapter = new BookCommentAdapter(getActivity());
        //adapter.setOnEventnewsClickListener(mOnItemClickListener);
        recycle.removeAllViews();
        recycle.setAdapter(adapter);
        refreshAndLoadMore();
    }

    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        Log.v("zzzzzzzzz", "-------onRefresh-------" + page);
                        if (NetUtil.isNetConnect(getActivity())){
                            vivistNet(false,page);
                        }else {
                            vivistNet(true,page);
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
                        Log.v("zzzzzzzzz", "-------onLoadMore-------" + page);
                        if (NetUtil.isNetConnect(getActivity())){
                            vivistNet(false,page);
                        }else {
                            vivistNet(true,page);
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

    private void vivistNet(final Boolean ifCache, final int page) {
        final ACache mCache = ACache.get(getActivity());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("comment"+bookId+ page);
                    if (newString != null) {
                        List<CommentBean> beanList = JsonBookDetailUtils.readCommentBean(newString);//data是json字段获得data的值即对象数组
                        Message msg = mHandler.obtainMessage();
                        msg.obj = beanList;
                        msg.what = 1;
                        msg.sendToTarget();
                        return;
                    }
                } else {
                    mCache.remove("comment"+bookId+ page);//刷新之后缓存也更新过来
                }
                HttpUtils.doGet(Urls.HOST_COMMENT + "?bookId=" + bookId + "&page=" + page, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mHandler.sendEmptyMessage(0);
                        Log.v("zzzzzzzzz", Urls.HOST_COMMENT + "?bookId=" + bookId + "&page=" + page + "-------onFailure-------" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            mCache.put("comment"+bookId+ page, s);
                            List<CommentBean> beanList = JsonBookDetailUtils.readCommentBean(s);//data是json字段获得data的值即对象数组
                            Message msg = mHandler.obtainMessage();
                            msg.obj = beanList;
                            msg.what = 1;
                            msg.sendToTarget();
                        } catch (Exception e) {
                            Log.v("zzzzzzzzz", Urls.HOST_COMMENT + "?bookId=" + bookId + "&page=" + page + "-------Exception-------" + e);
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private CommentFragment.MyHandler mHandler = new CommentFragment.MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    AppUtils.showToast(getActivity(), getString(R.string.fail_comment));
                    break;
                case 1:
                    List<CommentBean> beanList = (List<CommentBean>) msg.obj;
                    addCommentsView(beanList);
                    break;
                default:
                    break;
            }
        }
    }

    private void addCommentsView(List<CommentBean> beanList) {
        if (beanList == null || beanList.size() == 0) {
            if (page == 0) {
                refresh.setVisibility(View.GONE);
                return;
            }
            AppUtils.showToast(getActivity(), getString(R.string.no_comment));
            return;
        } else {
            refresh.setVisibility(View.VISIBLE);
        }
        if (page == 0) {
            adapter.reMove();
            adapter.setmDate(beanList);
        } else {
            //加载更多
            for (int i = 0; i < beanList.size(); i++) {
                adapter.addItem(beanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
            }
        }
    }
}