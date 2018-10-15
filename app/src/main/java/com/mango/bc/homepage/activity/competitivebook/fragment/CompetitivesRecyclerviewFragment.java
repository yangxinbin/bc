package com.mango.bc.homepage.activity.competitivebook.fragment;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.bookcase.bean.RefreshBookCaseBean;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.activity.BuyBookActivity;
import com.mango.bc.homepage.adapter.BookComprtitiveAdapter;
import com.mango.bc.homepage.bean.BuySuccessBean;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookCompetitiveView;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/5/21.
 */

public class CompetitivesRecyclerviewFragment extends Fragment implements BookCompetitiveView, MyAllBookView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_no_book)
    ImageView imgNoBook;
    private BookPresenter bookPresenter;
    private BookComprtitiveAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isFirstEnter;
    private String mType = "";
    private final int TYPE = 1;
    private int page = 0;
    public TextView tv_stage;
    private TextView tv_head_stage;
    private SPUtils spUtils;
    private ACache mCache;
    private MyBookPresenterImpl myBookPresenter;


    public static CompetitivesRecyclerviewFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        CompetitivesRecyclerviewFragment fragment = new CompetitivesRecyclerviewFragment();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookPresenter = new BookPresenterImpl(this);
        myBookPresenter = new MyBookPresenterImpl(this);
        mType = getArguments().getString("type");
        Log.v("yyyyy", "====mType======" + mType);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.competitive_items, null);
        spUtils = SPUtils.getInstance("bc", getActivity());
        mCache = ACache.get(getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        refreshAndLoadMore();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 5)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getCompetitiveBook()) {
            EventBus.getDefault().removeStickyEvent(bean);
            refresh.autoRefresh();
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }

    private void initView() {
        recycle.setHasFixedSize(true);//固定宽高
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycle.setLayoutManager(mLayoutManager);
        recycle.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        adapter = new BookComprtitiveAdapter(getActivity());
        recycle.removeAllViews();
        recycle.setAdapter(adapter);
        if (NetUtil.isNetConnect(getActivity())) {
            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, false);
        } else {
            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, true);
        }
        adapter.setOnItemClickLitener(mOnClickListenner);
    }

    private BookComprtitiveAdapter.OnItemClickLitener mOnClickListenner = new BookComprtitiveAdapter.OnItemClickLitener() {

        @Override
        public void onItemPlayClick(View view, int position) {//播放
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(adapter.getItem(position));
            startActivity(intent);
        }

        @Override
        public void onItemGetClick(View view, int position) {//购买界面
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().postSticky(adapter.getItem(position));
            startActivity(intent);
        }

        @Override
        public void onPlayClick(View view, int position) {//播放按钮
            EventBus.getDefault().postSticky(adapter.getItem(position));
            if (chechState(adapter.getItem(position).getId())) {
                spUtils.put("isFree", true);
            } else {
                spUtils.put("isFree", false);
            }
            if (AudioPlayer.get().isPausing() /*&& mData.get(position).getId().equals(spUtils.getString("isSameBook", ""))*/) {
                AudioPlayer.get().startPlayer();
                //tv_free_stage.setText("播放中");
                return;
            }
            if (NetUtil.isNetConnect(getActivity())) {
                loadBookDetail(false, adapter.getItem(position).getId());
            } else {
                loadBookDetail(true, adapter.getItem(position).getId());
            }
        }

        @Override
        public void onGetClick(View view, int position) {//购买按钮
            tv_stage = view.findViewById(R.id.tv_stage);
            if (tv_stage.getText().equals("播放")) {//用户没有刷新没有加载时临时调用（刷新与加载会重新与书架匹配）
                Log.v("bbbbbbbb", "---tv_stage--" + tv_stage.getText());
            } else {
                Intent intent = new Intent(getActivity(), BuyBookActivity.class);
                EventBus.getDefault().postSticky(adapter.getItem(position));
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivity(intent);
            }
        }

        @Override
        public void onHeadGetClick(View view, int position) {
            tv_head_stage = view.findViewById(R.id.tv_head_stage);
            if (tv_head_stage.getText().equals("播放")) {
                Log.v("bbbbbbbb", "---tv_head_stage--" + tv_head_stage.getText());
            } else {
                Intent intent = new Intent(getActivity(), BuyBookActivity.class);
                EventBus.getDefault().postSticky(adapter.getItem(position));
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivity(intent);
            }
        }

        @Override
        public void onItemVipGetClick(View view, int position) {
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            intent.putExtra("vipFree", true);
            EventBus.getDefault().postSticky(adapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onVipGetClick(View view, int position) {
            tv_stage = view.findViewById(R.id.tv_stage);
            if (tv_stage.getText().equals("播放")) {//用户没有刷新没有加载时临时调用（刷新与加载会重新与书架匹配）
                Log.v("bbbbbbbb", "---tv_stage--" + tv_stage.getText());
                EventBus.getDefault().postSticky(adapter.getItem(position));
                if (chechState(adapter.getItem(position).getId())) {
                    spUtils.put("isFree", true);
                } else {
                    spUtils.put("isFree", false);
                }
                if (AudioPlayer.get().isPausing() /*&& mData.get(position).getId().equals(spUtils.getString("isSameBook", ""))*/) {
                    AudioPlayer.get().startPlayer();
                    //tv_free_stage.setText("播放中");
                    return;
                }
                if (NetUtil.isNetConnect(getActivity())) {
                    loadBookDetail(false, adapter.getItem(position).getId());
                } else {
                    loadBookDetail(true, adapter.getItem(position).getId());
                }
            } else {
                getVipFreeBook(adapter.getItem(position).getId());
            }
        }

        @Override
        public void onHeadVipGetClick(View view, int position) {
            tv_head_stage = view.findViewById(R.id.tv_head_stage);
            if (tv_head_stage.getText().equals("播放")) {
                Log.v("bbbbbbbb", "---tv_head_stage--" + tv_head_stage.getText());
                EventBus.getDefault().postSticky(adapter.getItem(position));
                if (chechState(adapter.getItem(position).getId())) {
                    spUtils.put("isFree", true);
                } else {
                    spUtils.put("isFree", false);
                }
                if (AudioPlayer.get().isPausing() /*&& mData.get(position).getId().equals(spUtils.getString("isSameBook", ""))*/) {
                    AudioPlayer.get().startPlayer();
                    //tv_free_stage.setText("播放中");
                    return;
                }
                if (NetUtil.isNetConnect(getActivity())) {
                    loadBookDetail(false, adapter.getItem(position).getId());
                } else {
                    loadBookDetail(true, adapter.getItem(position).getId());
                }
            } else {
                getVipFreeBook(adapter.getItem(position).getId());
            }
        }

    };

    private void getVipFreeBook(final String bookId) {
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getActivity(), "领取失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (tv_stage != null)
                                        tv_stage.setText("播放");//领取成功
                                    if (tv_head_stage != null)
                                        tv_head_stage.setText("播放");//领取成功
                                    loadStats();
                                    if (NetUtil.isNetConnect(getActivity())) {
                                        myBookPresenter.visitBooks(getActivity(), 3, 0, false);//获取书架的所有书(加入刷新)
                                    } else {
                                        myBookPresenter.visitBooks(getActivity(), 3, 0, true);//获取书架的所有书(加入刷新)
                                    }  //要不点击详情页还是显现领取，详情书的状态在adapter里面控制
/*                                    RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, false, true, false);
                                    EventBus.getDefault().postSticky(refreshStageBean);*/
                                    EventBus.getDefault().postSticky(new RefreshBookCaseBean(false, true, false));

                                }
                            });
                        } catch (Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getActivity(), "领取失败");
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //loadUser();//更新用户信息（钱）
                                    StatsBean statsBean = MineJsonUtils.readStatsBean(string1);
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AudioPlayer.get().init(getActivity());
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getActivity(), "播放失败");
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AudioPlayer.get().init(getActivity());
                                    AudioPlayer.get().play(0);//第一个开始播放
                                }
                            });
                        } catch (Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getActivity(), "播放失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BuySuccessBeanEventBus(BuySuccessBean bean) {
        if (bean == null) {
            return;
        }
        Log.v("bbbbb", "---1----" + bean.getBuySuccess());

        if (bean.getBuySuccess()) {
            Log.v("bbbbb", "----2---");
            if (tv_stage != null)
                tv_stage.setText("播放");
            if (tv_head_stage != null)
                tv_head_stage.setText("播放");

        }
        EventBus.getDefault().removeStickyEvent(BuySuccessBean.class);
    }

    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        if (NetUtil.isNetConnect(getActivity())) {
                            Log.v("zzzzzzzzz", "-------onRefresh y-------" + page);
                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, false);
                        } else {
                            Log.v("zzzzzzzzz", "-------onRefresh n-------" + page);
                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, true);
                        }
                        refreshLayout.finishRefresh();
                    }
                }, 200);
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        Log.v("yyyyyy", "-------isNetConnect-------" + NetUtil.isNetConnect(getActivity()));
                        if (NetUtil.isNetConnect(getActivity())) {
                            Log.v("yyyyyy", "-------isNetConnect-------");
                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, false);
                        } else {
                            Log.v("yyyyyy", "-------isnoNetConnect-------");

                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, true);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addCompetitiveBook(final List<BookBean> bookBeanList) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("yyyyyyyyyyy", "========" + bookBeanList.size());
                    if (bookBeanList == null || bookBeanList.size() == 0) {
                        if (page == 0) {
                            refresh.setVisibility(View.GONE);
                            imgNoBook.setVisibility(View.VISIBLE);
                            return;
                        }
                        AppUtils.showToast(getActivity(), getString(R.string.date_over));
                        return;
                    } else {
                        refresh.setVisibility(View.VISIBLE);
                        imgNoBook.setVisibility(View.GONE);
                    }
                    if (page == 0) {
                        adapter.reMove();
                        adapter.setmDate(bookBeanList);
                    } else {
                        //加载更多
                        for (int i = 0; i < bookBeanList.size(); i++) {
                            adapter.addItem(bookBeanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
                        }
                    }

                }
            });
    }

    @Override
    public void addSuccessCompetitiveBook(String s) {

    }

    @Override
    public void addFailCompetitiveBook(String f) {
        if (getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getActivity(), "精品课程请求失败");
            }
        });
    }

    @Override
    public void addSuccess(String s) {

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }
}
