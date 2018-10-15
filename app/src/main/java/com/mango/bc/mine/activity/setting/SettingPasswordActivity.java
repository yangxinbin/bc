package com.mango.bc.mine.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingPasswordActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.et_set)
    EditText etSet;
    @Bind(R.id.et_ok)
    EditText etOk;
    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.button})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button:
                break;
        }
    }
}
