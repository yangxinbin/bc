package com.mango.bc.wallet.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.util.ACache;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.adapter.TransactionAdapter;
import com.mango.bc.wallet.bean.TransactionBean;
import com.mango.bc.wallet.walletjsonutil.WalletJsonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TransactionServiceActivity extends BaseServiceActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.if_has)
    ImageView ifHas;
    private int page = 0;
    private boolean isFirstEnter = true;
    private SPUtils spUtils;
    private ACache mCache;
    private LinearLayoutManager mLayoutManager;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        spUtils = SPUtils.getInstance("bc", this);
        mCache = ACache.get(this);
        ButterKnife.bind(this);
        if (NetUtil.isNetConnect(this)) {
            loadTransaction(false, page);
        } else {
            loadTransaction(true, page);
        }
        initView();
        refreshAndLoadMore();
    }

    private void initView() {
        recycle.setHasFixedSize(true);//固定宽高
        mLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(mLayoutManager);
        recycle.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        adapter = new TransactionAdapter(this);
        recycle.removeAllViews();
        recycle.setAdapter(adapter);
        refreshAndLoadMore();
    }

    private void loadTransaction(final Boolean ifCache, final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("page", page + "");
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("transaction" + 0);
                    if (newString != null) {
                        final List<TransactionBean> transactionBeans = WalletJsonUtils.readTransactionBean(newString);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initTransactionView(transactionBeans);
                            }
                        });
                        return;
                    }
                } else {
                    mCache.remove("reward");//刷新之后缓存也更新过来
                }
                HttpUtils.doPost(Urls.HOST_TRANSACTIONS, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            final String string = response.body().string();
                            if (page == 0)
                                mCache.put("transaction" + page);
                            final List<TransactionBean> transactionBeans = WalletJsonUtils.readTransactionBean(string);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("ppppppppp", page + "==" + string);
                                    initTransactionView(transactionBeans);
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
            }
        }).start();
    }

    private void initTransactionView(List<TransactionBean> transactionBeans) {
        if (transactionBeans == null || transactionBeans.size() == 0) {
            AppUtils.showToast(this, getString(R.string.transaction));
            if (page == 0) {
                refresh.setVisibility(View.GONE);
                ifHas.setVisibility(View.VISIBLE);
            }
            return;
        } else {
            refresh.setVisibility(View.VISIBLE);
            ifHas.setVisibility(View.GONE);
        }
        if (page == 0) {
            adapter.reMove();
            adapter.setmDate(transactionBeans);
        } else {
            //加载更多
            for (int i = 0; i < transactionBeans.size(); i++) {
                adapter.addItem(transactionBeans.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
            }
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
                            loadTransaction(false, page);
                        } else {
                            loadTransaction(true, page);
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
                            loadTransaction(false, page);
                        } else {
                            loadTransaction(true, page);
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
}
