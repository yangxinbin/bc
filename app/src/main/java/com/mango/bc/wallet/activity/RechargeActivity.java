package com.mango.bc.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.bookdetail.play.PlayActivity;
import com.mango.bc.wallet.adapter.SingleRechargeAdapter;
import com.mango.bc.wallet.bean.RechargeType;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.buy_vip)
    Button buyVip;
    private ArrayList<RechargeType> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        intList();
        initData();
        intView();
    }
    private void intView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        final SingleRechargeAdapter adapter = new SingleRechargeAdapter(datas);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new SingleRechargeAdapter.OnItemClickLitener() {
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
        datas.add(new RechargeType("6 积分","6元"));
        datas.add(new RechargeType("38 积分","38元"));
        datas.add(new RechargeType("68 积分","68元"));
        datas.add(new RechargeType("108 积分","108元"));
        datas.add(new RechargeType("208 积分","208元"));
        datas.add(new RechargeType("388 积分","388元"));
    }

    private void intList() {

    }
    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.buy_vip:
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
