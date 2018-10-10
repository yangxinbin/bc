package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.activity.user.UserChangeActivity;
import com.mango.bc.util.PublicWay;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.tv_wenxin_state)
    TextView tvWenxinState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.l_1, R.id.l_2, R.id.l_3, R.id.l_4, R.id.l_5, R.id.l_6, R.id.exit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_1:
                intent = new Intent(this, UserChangeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.l_2:
                break;
            case R.id.l_3:
                break;
            case R.id.l_4:
                break;
            case R.id.l_5:
                break;
            case R.id.l_6:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.exit:
                //editor.putString("isOk", "no")
                //        .commit();
                for (int i = 0; i < PublicWay.activityList.size(); i++) {
                    if (null != PublicWay.activityList.get(i)) {
                        // 关闭存放在activityList集合里面的所有activity
                        PublicWay.activityList.get(i).finish();
                    }
                }
                System.exit(0);
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
