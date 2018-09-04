package com.mango.bc.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.SingleAdapter;
import com.mango.bc.homepage.bean.VipType;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenUpVipActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_buy_detail)
    TextView tvBuyDetail;
    @Bind(R.id.buy_vip)
    Button buyVip;
    private ArrayList<VipType> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_up_vip);
        ButterKnife.bind(this);
        intList();
        initData();
        intView();
    }

    private void intView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final SingleAdapter adapter = new SingleAdapter(datas);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new SingleAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initData() {
        datas = new ArrayList<>();
        datas.add(new VipType("连续包月VIP","首月特价","每月仅需19币，自动续费可随时取消","29币","9.9币"));
        datas.add(new VipType("连续包年VIP","八折优惠","每年仅需292币，自动续费可随时取消","365币","292币"));
        datas.add(new VipType("包月VIP","","","","9.9币"));
        datas.add(new VipType("包年VIP","","","29币","9.9币"));

    }

    private void intList() {

    }

    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                Intent intent = new Intent(this, VipDetailActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buy_vip:
                break;
        }
    }
}
