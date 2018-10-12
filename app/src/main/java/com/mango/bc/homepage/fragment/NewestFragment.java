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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.bookcase.bean.RefreshBookCaseBean;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.activity.BuyBookActivity;
import com.mango.bc.homepage.adapter.BookNewestAdapter;
import com.mango.bc.homepage.bean.BuySuccessBean;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.LoadStageBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookNewestView;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.ACache;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

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

public class NewestFragment extends Fragment implements BookNewestView, MyAllBookView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookNewestAdapter bookNewestAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 4;//最新课
    private int page = 0;
    private TextView tv_stage;
    private MyBookPresenterImpl myBookPresenter;
    private ACache mCache;
    private SPUtils spUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newest, container, false);
        bookPresenter = new BookPresenterImpl(this);
        myBookPresenter = new MyBookPresenterImpl(this);
        spUtils = SPUtils.getInstance("bc", getActivity());
        mCache = ACache.get(this.getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        if (NetUtil.isNetConnect(getActivity())) {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);
        } else {
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
            EventBus.getDefault().postSticky(bookNewestAdapter.getItem(position));
            if (chechState(bookNewestAdapter.getItem(position).getId())) {
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
                loadBookDetail(false, bookNewestAdapter.getItem(position).getId());
            } else {
                loadBookDetail(true, bookNewestAdapter.getItem(position).getId());
            }
        }

        @Override
        public void onGetClick(View view, int position) {
            tv_stage = view.findViewById(R.id.tv_stage);
            if (tv_stage.getText().equals("播放")) {
                Log.v("bbbbbbbb", "-----" + tv_stage.getText());
                EventBus.getDefault().postSticky(bookNewestAdapter.getItem(position));
                if (chechState(bookNewestAdapter.getItem(position).getId())) {
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
                    loadBookDetail(false, bookNewestAdapter.getItem(position).getId());
                } else {
                    loadBookDetail(true, bookNewestAdapter.getItem(position).getId());
                }
            } else {
                Intent intent = new Intent(getActivity(), BuyBookActivity.class);
                EventBus.getDefault().postSticky(bookNewestAdapter.getItem(position));
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivity(intent);
            }

        }

        @Override
        public void onItemVipGetClick(View view, int position) {
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            intent.putExtra("vipFree", true);
            EventBus.getDefault().postSticky(bookNewestAdapter.getItem(position));
            EventBus.getDefault().removeStickyEvent(MyBookBean.class);
            startActivity(intent);
        }

        @Override
        public void onVipGetClick(View view, int position) {
            tv_stage = view.findViewById(R.id.tv_stage);
            getVipFreeBook(bookNewestAdapter.getItem(position).getId());
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
                                    tv_stage.setText("播放");//领取成功
                                    loadStats();
                                    if (NetUtil.isNetConnect(getActivity())) {
                                        myBookPresenter.visitBooks(getActivity(), 3, 0, false);//获取书架的所有书(加入刷新)
                                    } else {
                                        myBookPresenter.visitBooks(getActivity(), 3, 0, true);//获取书架的所有书(加入刷新)
                                    }  //要不点击详情页还是显现领取，详情书的状态在adapter里面控制
/*                                    RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, false, true, false);
                                    EventBus.getDefault().postSticky(refreshStageBean);*/
                                    EventBus.getDefault().postSticky(new RefreshBookCaseBean(false, true, true));

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
                        Log.v("yyyyyyy", "==?like===4--");
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
