package com.mango.bc.mine.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.activity.SettingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePhoneActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.et_new_phone)
    EditText etNewPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.get_code)
    TextView getCode;
    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.get_code, R.id.button})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.get_code:
                break;
            case R.id.button:
                break;
        }
    }
}
