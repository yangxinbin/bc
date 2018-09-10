package com.mango.bc.homepage.activity.competitivebook.fragment;

import android.content.SharedPreferences;
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
import com.mango.bc.homepage.adapter.BookComprtitiveAdapter;
import com.mango.bc.homepage.net.bean.CompetitiveBookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.ExpertBookBean;
import com.mango.bc.homepage.net.bean.FreeBookBean;
import com.mango.bc.homepage.net.bean.NewestBookBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookView;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/21.
 */

public class CompetitivesRecyclerviewFragment extends Fragment implements BookView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    private BookPresenter bookPresenter;
    private BookComprtitiveAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private String nowProvince, nowCity, nowDistrict;
    private boolean isFirstEnter;
    private String mType = "";
    private final int TYPE = 1;
    private int page = 0;

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
        mType = getArguments().getString("type");
        sharedPreferences = getActivity().getSharedPreferences("CIFIT", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Log.v("yyyyy", "====mType======" + mType);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.competitive_items, null);
        ButterKnife.bind(this, view);
        initView();
        refreshAndLoadMore();
        return view;
    }

    private void initView() {
        recycle.setHasFixedSize(true);//固定宽高
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycle.setLayoutManager(mLayoutManager);
        recycle.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        adapter = new BookComprtitiveAdapter(getActivity());
        adapter.setOnItemClickLitener(mOnItemClickListener);
        recycle.removeAllViews();
        recycle.setAdapter(adapter);
        bookPresenter.visitBooks(getActivity(), TYPE, mType, page, true);
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
                            Log.v("zzzzzzzzz", "-------onRefresh-------" + page);
                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, false);
                        } else {
                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, true);
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
                        if (NetUtil.isNetConnect(getActivity())) {
                            Log.v("zzzzzzzzz", "-------onRefresh-------" + page);
                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, false);
                        } else {
                            bookPresenter.visitBooks(getActivity(), TYPE, mType, page, true);
                        }

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

    private BookComprtitiveAdapter.OnItemClickLitener mOnItemClickListener = new BookComprtitiveAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            //Intent intent = new Intent(getActivity(), BusinessPlanActivity.class);
            //intent.putExtra("type", adapter.getItem(position).getResponseObject().getContent().get(position).getStage());
            //startActivity(intent);
            //getActivity().finish();
        }

        @Override
        public void onStageClick(View view, int position) {

        }

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList) {

    }

    @Override
    public void addCompetitiveBook(List<CompetitiveBookBean> competitiveBookBeanList) {

    }

    @Override
    public void addExpertBook(List<ExpertBookBean> expertBookBeanList) {

    }

    @Override
    public void addFreeBook(List<FreeBookBean> freeBookBeanList) {

    }

    @Override
    public void addNewestBook(List<NewestBookBean> newestBookBeanList) {

    }

    @Override
    public void addSuccess(String s) {

    }

    @Override
    public void addFail(String f) {

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
