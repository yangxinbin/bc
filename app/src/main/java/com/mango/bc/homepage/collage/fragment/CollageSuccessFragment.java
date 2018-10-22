package com.mango.bc.homepage.collage.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.homepage.collage.adapter.CollageAdapter;
import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.homepage.collage.presenter.CollagePresenter;
import com.mango.bc.homepage.collage.presenter.CollagePresenterImpl;
import com.mango.bc.homepage.collage.view.CollageAllView;
import com.mango.bc.homepage.collage.view.CollageSuccessView;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/6.
 */

public class CollageSuccessFragment extends Fragment implements CollageSuccessView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_nocollage)
    ImageView imgNocollage;
    private CollageAdapter collageAdapter;
    private boolean isFirstEnter = true;
    private CollagePresenter collagePresenter;
    private final int STAYUA = 2;//完成
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collage_success, container, false);
        collagePresenter = new CollagePresenterImpl(this);
        ButterKnife.bind(this, view);
        initView();
        if (NetUtil.isNetConnect(getActivity())) {
            collagePresenter.visitCollages(getActivity(), STAYUA, page, false);
        } else {
            collagePresenter.visitCollages(getActivity(), STAYUA, page, true);
        }
        refreshAndLoadMore();
        return view;
    }

    private void initView() {
        collageAdapter = new CollageAdapter(getActivity());
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(collageAdapter);
        collageAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private CollageAdapter.OnItemClickLitener mOnClickListenner = new CollageAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            //Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            //startActivity(intent);
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
                        if (NetUtil.isNetConnect(getActivity())) {
                            collagePresenter.visitCollages(getActivity(), STAYUA, page, false);
                        } else {
                            collagePresenter.visitCollages(getActivity(), STAYUA, page, true);
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
                            collagePresenter.visitCollages(getActivity(), STAYUA, page, false);
                        } else {
                            collagePresenter.visitCollages(getActivity(), STAYUA, page, true);
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
    }


    @Override
    public void addCollageSuccess(final List<CollageBean> collageBeans) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("doPostAll", page + "===" + collageBeans.size());
                    if (collageBeans == null || collageBeans.size() == 0) {
                        if (page == 0) {
                            refresh.setVisibility(View.GONE);
                            imgNocollage.setVisibility(View.VISIBLE);
                            return;
                        }
                        AppUtils.showToast(getActivity(), getString(R.string.date_over));
                        return;
                    } else {
                        refresh.setVisibility(View.VISIBLE);
                        imgNocollage.setVisibility(View.GONE);
                    }
                    if (page == 0) {
                        collageAdapter.reMove();
                        collageAdapter.setmDate(collageBeans);
                    } else {
                        //加载更多
                        for (int i = 0; i < collageBeans.size(); i++) {
                            collageAdapter.addItem(collageBeans.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
                        }
                    }
                }
            });
    }

    @Override
    public void addSuccessCollageSuccess(String s) {

    }

    @Override
    public void addFailCollageSuccess(String f) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppUtils.showToast(getActivity(), "拼团列表请求失败");
                }
            });
    }
}