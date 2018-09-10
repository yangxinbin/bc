package com.mango.bc.homepage.activity.expertbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.homepage.adapter.BookAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpertBookActivity extends AppCompatActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    private BookAdapter bookAdapter;
    private boolean isFirstEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_book);
        ButterKnife.bind(this);
        initView();
        refreshAndLoadMore();
    }

    private void initView() {
        bookAdapter = new BookAdapter();
        recycle.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycle.setAdapter(bookAdapter);
    }

    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
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
