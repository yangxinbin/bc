package com.mango.bc.homepage.activity.freebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.bookcase.bean.RefreshBookCaseBean;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.adapter.BookGirdFreeAdapter;
import com.mango.bc.homepage.bookdetail.OtherBookDetailServiceActivity;
import com.mango.bc.homepage.bookdetail.play.executor.ControlPanel;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookFreeView;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.ACache;
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
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FreeBookServiceActivity extends BaseServiceActivity implements BookFreeView, MyAllBookView {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_no_book)
    ImageView imgNoBook;
    @Bind(R.id.fl_play_bar)
    FrameLayout flPlayBar;
    private BookGirdFreeAdapter bookGirdFreeAdapter;
    private boolean isFirstEnter = true;
    private BookPresenter bookPresenter;
    private final int TYPE = 7;//免费
    private int page = 0;
    private MyBookPresenterImpl myBookPresenter;
    private SPUtils spUtils;
    private TextView tv_free_stage;
    private ACache mCache;
    private ControlPanel controlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_book);
        bookPresenter = new BookPresenterImpl(this);
        myBookPresenter = new MyBookPresenterImpl(this);
        spUtils = SPUtils.getInstance("bc", this);
        mCache = ACache.get(this);
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

    @Override
    protected void onServiceBound() {
        controlPanel = new ControlPanel(flPlayBar);
        AudioPlayer.get().addOnPlayEventListener(controlPanel);
        //parseIntent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 5)
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
            Log.v("wwwwwww", "======pi");
            Intent intent = new Intent(getBaseContext(), OtherBookDetailServiceActivity.class);
            EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onItemGetClick(View view, int position) {
            Log.v("wwwwwww", "======gi");
            Intent intent = new Intent(getBaseContext(), OtherBookDetailServiceActivity.class);
            EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onPlayClick(View view, int position) {//播放
            EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
            if (chechState(bookGirdFreeAdapter.getItem(position).getId())) {
                spUtils.put("isFree", true);
            } else {
                spUtils.put("isFree", false);
            }
            if (AudioPlayer.get().isPausing() /*&& mData.get(position).getId().equals(spUtils.getString("isSameBook", ""))*/) {
                AudioPlayer.get().startPlayer();
                //tv_free_stage.setText("播放中");
                return;
            }
            if (NetUtil.isNetConnect(FreeBookServiceActivity.this)) {
                loadBookDetail(false, bookGirdFreeAdapter.getItem(position).getId());
            } else {
                loadBookDetail(true, bookGirdFreeAdapter.getItem(position).getId());
            }
        }

        @Override
        public void onGetClick(View view, int position) {//领取
            tv_free_stage = view.findViewById(R.id.tv_free_stage);
            if (tv_free_stage.getText().equals("播放")) {
                EventBus.getDefault().postSticky(bookGirdFreeAdapter.getItem(position));
                if (chechState(bookGirdFreeAdapter.getItem(position).getId())) {
                    spUtils.put("isFree", true);
                } else {
                    spUtils.put("isFree", false);
                }
                if (AudioPlayer.get().isPausing() /*&& mData.get(position).getId().equals(spUtils.getString("isSameBook", ""))*/) {
                    AudioPlayer.get().startPlayer();
                    //tv_free_stage.setText("播放中");
                    return;
                }
                if (NetUtil.isNetConnect(FreeBookServiceActivity.this)) {
                    loadBookDetail(false, bookGirdFreeAdapter.getItem(position).getId());
                } else {
                    loadBookDetail(true, bookGirdFreeAdapter.getItem(position).getId());
                }
            } else {
                getFreeBook(bookGirdFreeAdapter.getItem(position).getId());
            }
        }

    };

    private boolean chechState(String bookId) {
        if (spUtils != null) {
            String data = spUtils.getString("allMyBook", "");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> list = gson.fromJson(data, listType);
            if (list == null)
                return false;
            if (list.contains(bookId)) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    private void loadBookDetail(final Boolean ifCache, final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("bookDetail" + bookId);
                    Log.v("yyyyyy", "---cache5---" + newString);
                    if (newString != null) {
                        spUtils.put("bookDetail", newString);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AudioPlayer.get().init(FreeBookServiceActivity.this);
                                AudioPlayer.get().play(0);//第一个开始播放

                            }
                        });
                        return;
                    }
                } else {
                    mCache.remove("bookDetail" + bookId);//刷新之后缓存也更新过来
                }
                HttpUtils.doGet(Urls.HOST_BOOKDETAIL + "/" + bookId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(FreeBookServiceActivity.this, "播放失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            Log.v("fffffffff", "---f--");
                            String string = response.body().string();
                            mCache.put("bookDetail" + bookId, string);
                            spUtils.put("bookDetail", string);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AudioPlayer.get().init(FreeBookServiceActivity.this);
                                    AudioPlayer.get().play(0);//第一个开始播放
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(FreeBookServiceActivity.this, "播放失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

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
                                    loadStats();
                                    if (NetUtil.isNetConnect(getBaseContext())) {
                                        myBookPresenter.visitBooks(getBaseContext(), 3, 0, false);//获取书架的所有书(加入刷新)
                                    } else {
                                        myBookPresenter.visitBooks(getBaseContext(), 3, 0, true);//获取书架的所有书(加入刷新)
                                    }  //要不点击详情页还是显现领取，详情书的状态在adapter里面控制
/*                                    RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, false, true, false);
                                    EventBus.getDefault().postSticky(refreshStageBean);*/
                                    EventBus.getDefault().postSticky(new RefreshBookCaseBean(false, false, true));
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

    private void loadStats() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //final HashMap<String, String> mapParams = new HashMap<String, String>();
                //mapParams.clear();
                //mapParams.put("authToken", spUtils.getString("authToken", ""));
                HttpUtils.doGet(Urls.HOST_STATS + "?authToken=" + spUtils.getString("authToken", ""), /*mapParams,*/ new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        try {
                            final String string1 = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //loadUser();//更新用户信息（钱）
                                    StatsBean statsBean = AuthJsonUtils.readStatsBean(string1);
                                    EventBus.getDefault().postSticky(statsBean);//刷新钱包
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
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
