package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordActivity extends BaseActivity {

    @Bind(R.id.editText_password1)
    EditText editTextPassword1;
    @Bind(R.id.editText_password2)
    EditText editTextPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.button_password_ok})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, CodeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_password_ok:
                intent = new Intent(this, PositionActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
