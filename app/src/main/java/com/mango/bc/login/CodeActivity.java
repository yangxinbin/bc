package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.activity.VipDetailActivity;
import com.mango.bc.mine.activity.VipCenterActivity;
import com.tuo.customview.VerificationCodeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeActivity extends BaseActivity {

    @Bind(R.id.textView_to)
    TextView textViewTo;
    @Bind(R.id.icv)
    VerificationCodeView icv;
    @Bind(R.id.tv_reset)
    TextView tvReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
        textViewTo.setText("已发送6位数验证码至  "+getIntent().getStringExtra("num"));
    }

    @OnClick({R.id.imageView_back, R.id.button_next, R.id.tv_reset})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_next:
                intent = new Intent(this, PasswordActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_reset:
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
