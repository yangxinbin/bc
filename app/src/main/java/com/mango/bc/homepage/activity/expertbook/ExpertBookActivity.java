package com.mango.bc.homepage.activity.expertbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.activity.BuyBookActivity;
import com.mango.bc.homepage.adapter.BookExpertAdapter;
import com.mango.bc.homepage.bookdetail.ExpertBookDetailActivity;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookExpertView;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpertBookActivity extends BaseActivity implements BookExpertView {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_no_book)
    ImageView imgNoBook;
    private BookExpertAdapter bookExpertAdapter;
    private boolean isFirstEnter;
    private BookPresenter bookPresenter;
    private final int TYPE = 2;//大咖课
    private int page = 0;
    public TextView tv_stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_book);
        bookPresenter = new BookPresenterImpl(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        if (NetUtil.isNetConnect(this)) {
            bookPresenter.visitBooks(this, TYPE, "", page, false);
        } else {
            bookPresenter.visitBooks(this, TYPE, "", page, true);
        }
        refreshAndLoadMore();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 5)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getExpertBook()) {
            EventBus.getDefault().removeStickyEvent(bean);
            refresh.autoRefresh();
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }

    private void initView() {
        bookExpertAdapter = new BookExpertAdapter(this);
        recycle.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycle.setAdapter(bookExpertAdapter);
        bookExpertAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private BookExpertAdapter.OnItemClickLitener mOnClickListenner = new BookExpertAdapter.OnItemClickLitener() {
        @Override
        public void onItemPlayClick(View view, int position) {
            Intent intent = new Intent(getBaseContext(), ExpertBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookExpertAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onItemGetClick(View view, int position) {
            Intent intent = new Intent(getBaseContext(), ExpertBookDetailActivity.class);
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
            if (tv_stage.getText().equals("播放")) {//用户没有刷新没有加载时临时调用（刷新与加载会重新与书架匹配）
                Log.v("bbbbbbbb", "---tv_stage--" + tv_stage.getText());
            } else {
                Intent intent = new Intent(ExpertBookActivity.this, BuyBookActivity.class);
                EventBus.getDefault().postSticky(bookExpertAdapter.getItem(position));
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivityForResult(intent,1);
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1){
            tv_stage.setText("播放");
        }
    }
    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        if (NetUtil.isNetConnect(getBaseContext())) {
                            Log.v("zzzzzzzzz", "-------onRefresh y-------" + page);
                            bookPresenter.visitBooks(getBaseContext(), TYPE, "", page, false);
                        } else {
                            Log.v("zzzzzzzzz", "-------onRefresh n-------" + page);
                            bookPresenter.visitBooks(getBaseContext(), TYPE, "", page, true);
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
                        if (NetUtil.isNetConnect(getBaseContext())) {
                            Log.v("yyyyyy", "-------isNetConnect-------");
                            bookPresenter.visitBooks(getBaseContext(), TYPE, "", page, false);
                        } else {
                            Log.v("yyyyyy", "-------isnoNetConnect-------");

                            bookPresenter.visitBooks(getBaseContext(), TYPE, "", page, true);
                        }
                        refreshLayout.finishLoadMore();
                    }
                }, 500);
            }
        });
        refresh.setRefreshHeader(new ClassicsHeader(this));
        refresh.setHeaderHeight(50);

        //触发自动刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            //refresh.autoRefresh();
        } else {
            //mAdapter.refresh(initData());
        }
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void addExpertBook(final List<BookBean> bookBeanList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bookBeanList == null || bookBeanList.size() == 0) {
                    if (page == 0) {
                        refresh.setVisibility(View.GONE);
                        imgNoBook.setVisibility(View.VISIBLE);
                        return;
                    }
                    AppUtils.showToast(getBaseContext(), getString(R.string.date_over));
                    return;
                } else {
                    refresh.setVisibility(View.VISIBLE);
                    imgNoBook.setVisibility(View.GONE);
                }
                if (page == 0) {
                    bookExpertAdapter.reMove();
                    bookExpertAdapter.setmDate(bookBeanList);
                } else {
                    //加载更多
                    for (int i = 0; i < bookBeanList.size(); i++) {
                        bookExpertAdapter.addItem(bookBeanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
                    }
                }

            }
        });

    }

    @Override
    public void addSuccessExpertBook(String s) {

    }

    @Override
    public void addFailExpertBook(String f) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getBaseContext(), "大咖课程请求失败");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
