package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.activity.PointApplyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BunblePhoneActivity extends BaseActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.get_code)
    TextView getCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunble_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.get_code, R.id.button_password_ok})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(BunblePhoneActivity.this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.get_code:
                break;
            case R.id.button_password_ok:
                intent = new Intent(BunblePhoneActivity.this, PositionActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(BunblePhoneActivity.this, FirstActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
