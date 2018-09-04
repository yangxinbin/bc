package com.mango.bc.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenUpVipActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.list_item)
    ListView listItem;
    @Bind(R.id.tv_buy_detail)
    TextView tvBuyDetail;
    @Bind(R.id.buy_vip)
    Button buyVip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_up_vip);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                Intent intent = new Intent(this,VipDetailActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buy_vip:
                break;
        }
    }
}
