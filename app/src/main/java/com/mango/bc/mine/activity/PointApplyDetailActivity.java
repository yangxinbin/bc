package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.wallet.activity.RechargeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointApplyDetailActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tv_copy)
    TextView tvCopy;
    @Bind(R.id.p1)
    LinearLayout p1;
    @Bind(R.id.p2)
    LinearLayout p2;
    @Bind(R.id.l_1)
    LinearLayout l1;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.buy_ppg)
    Button buyPpg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_apply_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.tv_copy, R.id.p1, R.id.p2, R.id.l_1, R.id.l_2, R.id.buy_ppg})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.tv_copy:
                break;
            case R.id.p1:
                break;
            case R.id.p2:
                break;
            case R.id.l_1:
                break;
            case R.id.l_2:
                intent = new Intent(this, PointDetailActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.buy_ppg:
                intent = new Intent(this, RechargeActivity.class);
                startActivity(intent);
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
