package com.mango.bc.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.activity.VipDetailActivity;
import com.mango.bc.homepage.activity.freebook.FreeBookActivity;
import com.mango.bc.homepage.adapter.BookGirdAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FreeFragment extends Fragment {
    @Bind(R.id.see_more)
    TextView seeMore;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookGirdAdapter bookGirdAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.free, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    private void initView() {
        bookGirdAdapter = new BookGirdAdapter();
        recycle.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),3));
        recycle.setAdapter(bookGirdAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.see_more)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), FreeBookActivity.class);
        startActivity(intent);
    }
}
