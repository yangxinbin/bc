package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends BaseActivity {

    @Bind(R.id.tv_see)
    TextView tvSee;
    @Bind(R.id.button_begin)
    Button buttonBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_see, R.id.button_begin})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_see:
                intent = new Intent(this, BcActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_begin:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
