package com.mango.bc.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.activity.competitivebook.CompetitiveBookActivity;
import com.mango.bc.homepage.adapter.CompetitiveFieldAdapter;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.presenter.BookPresenterImpl;
import com.mango.bc.homepage.net.view.BookCompetitiveFieldView;
import com.mango.bc.homepage.net.view.BookCompetitiveView;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompetitiveFieldFragment extends Fragment implements BookCompetitiveFieldView {
    @Bind(R.id.see_more)
    TextView seeMore;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private List<String> listS = new ArrayList<>();
    private CompetitiveFieldAdapter competitiveFieldAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 0;
    private int page = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.competitive, container, false);
        bookPresenter = new BookPresenterImpl(this);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        if (NetUtil.isNetConnect(getActivity())){
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);//true从缓存读数据，false从网络读数据。
        }else {
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//true从缓存读数据，false从网络读数据。
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getCompetitiveField()) {
            bookPresenter.visitBooks(getActivity(), TYPE, "", page, false);//刷新从网络。
            bean.setCompetitiveField(false);//刷新完修改状态
            Log.v("yyyyyyy", "=====1--" + bean.toString());
            EventBus.getDefault().postSticky(bean);
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }

    private void initView() {
        competitiveFieldAdapter = new CompetitiveFieldAdapter(listS);
        recycle.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 4));
        recycle.setAdapter(competitiveFieldAdapter);
        competitiveFieldAdapter.setOnItemClickLitener(mOnClickListenner);
    }
    private CompetitiveFieldAdapter.OnItemClickLitener mOnClickListenner = new CompetitiveFieldAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(getActivity(), CompetitiveBookActivity.class);
            intent.putExtra("which",position);
            startActivity(intent);
        }

/*        @Override
        public void onStageClick(View view, int position) {
        }*/
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.see_more)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), CompetitiveBookActivity.class);
        intent.putExtra("which",0);
        startActivity(intent);
    }

    @Override
    public void addCompetitiveField(final List<CompetitiveFieldBean> competitiveFieldBeanList) {
        if (competitiveFieldBeanList == null)
            return;
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("yyyyyy", "----------" + competitiveFieldBeanList.size());
                    listS.clear();
                    for (int i = 0; i < competitiveFieldBeanList.size(); i++) {
                        listS.add(competitiveFieldBeanList.get(i).getName());
                    }
                }
            });
    }

    @Override
    public void addSuccessCompetitiveField(String s) {
        if (getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
    }

    @Override
    public void addFailCompetitiveField(String f) {
        if (getActivity() != null) getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppUtils.showToast(getActivity(), "精品类型请求失败");
            }
        });
    }
}
