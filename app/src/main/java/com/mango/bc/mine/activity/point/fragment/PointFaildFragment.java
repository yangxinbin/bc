package com.mango.bc.mine.activity.point.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.homepage.collage.CollageDetailActivity;
import com.mango.bc.homepage.collage.adapter.CollageAdapter;
import com.mango.bc.mine.activity.point.adapter.PointAdapter;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.mine.bean.NodeBean;
import com.mango.bc.mine.bean.RefreshMemberBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/12/1.
 */

public class PointFaildFragment extends Fragment {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_nocollage)
    ImageView imgNocollage;
    private PointAdapter pointAdapter;
    private SPUtils spUtils;
    private List<MemberBean.UsersBean> usersBeans = new ArrayList<>();
    private boolean isFirstEnter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.point_faild, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        refreshAndLoadMore();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshNodeBeanEventBus(NodeBean bean) {
        if (bean == null)
            return;
        if (bean.isIfRefesh()) {
            //initViewFrom();
            //Log.v("ppppppppppppppp", "===1");
            refresh.autoRefresh();
            bean.setIfRefesh(false);
            EventBus.getDefault().postSticky(bean);
        }
    }

    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initMember();
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

    private void initMember() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.doGet(Urls.HOST_MEMBER + "?parentId=" + spUtils.getString("parentId", ""), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            spUtils.put("member", string);
                            if (getActivity() != null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initViewFrom();
                                    }
                                });
                        } catch (Exception e) {
                        }
                    }
                });
            }
        }).start();
    }

    private void initView() {
        pointAdapter = new PointAdapter(getActivity());
        pointAdapter.reMove();
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(pointAdapter);
        MemberBean memberBean = MineJsonUtils.readMemberBean(spUtils.getString("member", ""));
        if (memberBean.getUsers() == null) {
            //refresh.setVisibility(View.GONE);
            //imgNocollage.setVisibility(View.VISIBLE);
        } else {
            //refresh.setVisibility(View.VISIBLE);
            //imgNocollage.setVisibility(View.GONE);
            for (int i = 0; i < memberBean.getUsers().size(); i++) {
                if (memberBean.getUsers().get(i).getAgencyInfo().getStatus() == 3 || memberBean.getUsers().get(i).getAgencyInfo().getStatus() == 4) {
                    usersBeans.add(memberBean.getUsers().get(i));
                }
            }
            if (usersBeans.size() != 0) {
                pointAdapter.setmDate(usersBeans);
            } else {
                //refresh.setVisibility(View.GONE);
                //imgNocollage.setVisibility(View.VISIBLE);
            }
        }
        //pointAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private void initViewFrom() {
        pointAdapter.reMove();
        if (usersBeans != null)
            usersBeans.clear();
        MemberBean memberBean = MineJsonUtils.readMemberBean(spUtils.getString("member", ""));
        if (memberBean.getUsers() == null) {
            //refresh.setVisibility(View.GONE);
            //imgNocollage.setVisibility(View.VISIBLE);
        } else {
            //refresh.setVisibility(View.VISIBLE);
            //imgNocollage.setVisibility(View.GONE);
            for (int i = 0; i < memberBean.getUsers().size(); i++) {
                if (memberBean.getUsers().get(i).getAgencyInfo().getStatus() == 3 || memberBean.getUsers().get(i).getAgencyInfo().getStatus() == 4) {
                    usersBeans.add(memberBean.getUsers().get(i));
                }
            }
            if (usersBeans.size() != 0) {
                pointAdapter.setmDate(usersBeans);
            } else {
                //refresh.setVisibility(View.GONE);
                //imgNocollage.setVisibility(View.VISIBLE);
            }
        }
        //pointAdapter.setOnItemClickLitener(mOnClickListenner);
    }
/*    private PointAdapter.OnItemClickLitener mOnClickListenner = new PointAdapter.OnItemClickLitener() {

        @Override
        public void onAcceptClick(View view, int position) {

        }

        @Override
        public void onRejectClick(View view, int position) {
        }
    };*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
