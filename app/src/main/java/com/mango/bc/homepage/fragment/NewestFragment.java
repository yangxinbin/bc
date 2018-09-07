package com.mango.bc.homepage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.bc.R;
import com.mango.bc.homepage.adapter.BookAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewestFragment extends Fragment {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    private BookAdapter bookAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newest, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    private void initView() {
        bookAdapter = new BookAdapter();
        recycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycle.setAdapter(bookAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
