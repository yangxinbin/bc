package com.mango.bc.homepage.activity.expertbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.BookAdapter;
import com.mango.bc.homepage.bookdetail.CompetitiveBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookView;
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
import butterknife.OnClick;

public class ExpertBookActivity extends BaseActivity implements BookView {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_no_book)
    ImageView imgNoBook;
    private BookAdapter bookAdapter;
    private boolean isFirstEnter;
    private BookPresenter bookPresenter;
    private final int TYPE = 2;//大咖课
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_book);
        bookPresenter = new BookPresenterImpl(this);
        ButterKnife.bind(this);
        initView();
        bookPresenter.visitBooks(this, TYPE, "", page, true);
        refreshAndLoadMore();
    }

    private void initView() {
        bookAdapter = new BookAdapter(this);
        recycle.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycle.setAdapter(bookAdapter);
        bookAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private BookAdapter.OnItemClickLitener mOnClickListenner = new BookAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(getBaseContext(), CompetitiveBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookAdapter.getItem(position));
            startActivity(intent);
        }

        @Override
        public void onStageClick(View view, int position) {

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
    public void addCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList) {

    }

    @Override
    public void addCompetitiveBook(List<BookBean> bookBeanList) {

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
    public void addFreeBook(List<BookBean> bookBeanList) {

    }

    @Override
    public void addNewestBook(List<BookBean> bookBeanList) {

    }

    @Override
    public void addSearchBook(List<BookBean> bookBeanList) {

    }

    @Override
    public void addSuccess(String s) {

    }

    @Override
    public void addFail(String f) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getBaseContext(), "大咖课程请求失败");
            }
        });
    }
}
