package com.mango.bc.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.mine.activity.VipCenterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VipDetailActivity extends BaseServiceActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.open_up)
    Button openUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.open_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.open_up:
                Intent intent = new Intent(this, OpenUpVipActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
