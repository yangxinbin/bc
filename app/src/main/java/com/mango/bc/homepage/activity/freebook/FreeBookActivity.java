package com.mango.bc.homepage.activity.freebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.adapter.BookGirdFreeAdapter;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookFreeView;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
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
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FreeBookActivity extends BaseActivity implements BookFreeView,MyAllBookView {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_no_book)
    ImageView imgNoBook;
    private BookGirdFreeAdapter bookGirdFreeAdapter;
    private boolean isFirstEnter = true;
    private BookPresenter bookPresenter;
    private final int TYPE = 3;//免费
    private int page = 0;
    private MyBookPresenterImpl myBookPresenter;
    private SPUtils spUtils;
    private TextView tv_free_stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_book);
        bookPresenter = new BookPresenterImpl(this);
        myBookPresenter = new MyBookPresenterImpl(this);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        if (NetUtil.isNetConnect(this)){
            bookPresenter.visitBooks(this, TYPE, "", page, false);
        }else {
            bookPresenter.visitBooks(this, TYPE, "", page, true);
        }
        refreshAndLoadMore();
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true,priority = 5)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getFreeBook()) {
            EventBus.getDefault().removeStickyEvent(bean);
            refresh.autoRefresh();
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }
    private void initView() {
        bookGirdFreeAdapter = new BookGirdFreeAdapter(this);
        recycle.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 3));
        recycle.setAdapter(bookGirdFreeAdapter);
        bookGirdFreeAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private BookGirdFreeAdapter.OnItemClickLitener mOnClickListenner = new BookGirdFreeAdapter.OnItemClickLitener() {

        @Override
        public void onItemPlayClick(View view, int position) {
            Log.v("wwwwwww","======pi");
            Intent intent = new Intent(getBaseContext(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onItemGetClick(View view, int position) {
            Log.v("wwwwwww","======gi");
            Intent intent = new Intent(getBaseContext(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onPlayClick(View view, int position) {//播放
            Log.v("wwwwwww","======p");
        }

        @Override
        public void onGetClick(View view, int position) {//领取
            tv_free_stage = view.findViewById(R.id.tv_free_stage);
            getFreeBook(bookGirdFreeAdapter.getItem(position).getId());
        }

    };

    private void getFreeBook(final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("bookId", bookId);
                HttpUtils.doPost(Urls.HOST_BUYBOOK, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getBaseContext(), "领取失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_free_stage.setText("播放");//领取成功
                                    if (NetUtil.isNetConnect(getBaseContext())) {
                                        myBookPresenter.visitBooks(getBaseContext(), 3, 0, false);//获取书架的所有书(加入刷新)
                                    } else {
                                        myBookPresenter.visitBooks(getBaseContext(), 3, 0, true);//获取书架的所有书(加入刷新)
                                    }  //要不点击详情页还是显现领取，详情书的状态在adapter里面控制
/*                                    RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, false, true, false);
                                    EventBus.getDefault().postSticky(refreshStageBean);*/
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getBaseContext(), "领取失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
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
                            myBookPresenter.visitBooks(getBaseContext(), 3, 0, false);//获取书架的所有书(加入刷新)
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
    public void addFreeBook(final List<BookBean> bookBeanList) {
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
                    bookGirdFreeAdapter.reMove();
                    bookGirdFreeAdapter.setmDate(bookBeanList);
                } else {
                    //加载更多
                    for (int i = 0; i < bookBeanList.size(); i++) {
                        bookGirdFreeAdapter.addItem(bookBeanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
                    }
                }

            }
        });

    }

    @Override
    public void addSuccessFreeBook(String s) {

    }

    @Override
    public void addFailFreeBook(String f) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getBaseContext(), "免费课程请求失败");
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
