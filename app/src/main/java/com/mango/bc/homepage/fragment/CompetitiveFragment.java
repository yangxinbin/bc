package com.mango.bc.homepage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.adapter.BookAdapter;
import com.mango.bc.homepage.adapter.FinefieldAdapter;
import com.mango.bc.homepage.bean.Finefield;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompetitiveFragment extends Fragment {
    @Bind(R.id.see_more)
    TextView seeMore;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private List<Finefield> listS;
    private FinefieldAdapter finefieldAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.competitive, container, false);
        ButterKnife.bind(this, view);
        initDate();
        initView();
        return view;
    }

    private void initDate() {
        listS = new ArrayList<>();
        listS.add(new Finefield("技术"));
        listS.add(new Finefield("金融"));
        listS.add(new Finefield("产业"));
        listS.add(new Finefield("货币"));
        listS.add(new Finefield("白皮书"));
        listS.add(new Finefield("其它"));

    }

    private void initView() {
        finefieldAdapter = new FinefieldAdapter(listS);
        recycle.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),4));
        recycle.setAdapter(finefieldAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.see_more)
    public void onViewClicked() {
    }
}
