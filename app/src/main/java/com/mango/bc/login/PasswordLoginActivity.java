package com.mango.bc.login;

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

public class PasswordLoginActivity extends BaseActivity {

    @Bind(R.id.editText_phone)
    EditText editTextPhone;
    @Bind(R.id.editText_password)
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.imageView_delete_phone, R.id.button_password_ok, R.id.forget_password})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imageView_delete_phone:
                break;
            case R.id.button_password_ok:
                break;
            case R.id.forget_password:
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
