package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointZoomActivity extends BaseActivity {

    @Bind(R.id.item_content1)
    EditText itemContent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_zoom);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.sure_apply})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, PointDetailActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sure_apply:
                intent = new Intent(this, PointDetailActivity.class);
                setResult(1);
                startActivity(intent);
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(this, PointDetailActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
