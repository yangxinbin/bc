package com.mango.bc.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.bc.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/10.
 */

public class MineFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
