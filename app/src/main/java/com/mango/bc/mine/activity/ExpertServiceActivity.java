package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseServiceActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpertServiceActivity extends BaseServiceActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tv_apply)
    Button tvApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.tv_apply})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.tv_apply:
                intent = new Intent(this, ExpertApplyServiceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
