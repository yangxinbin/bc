package com.mango.bc.mine.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseServiceActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceServiceActivity extends BaseServiceActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        finish();
    }
}
