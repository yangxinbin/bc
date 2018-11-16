package com.mango.bc.mine.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.SingleAdapter;
import com.mango.bc.homepage.bean.VipType;
import com.mango.bc.mine.adapter.SinglePayAdapter;
import com.mango.bc.mine.adapter.SinglePointAdapter;
import com.mango.bc.mine.bean.UserBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointApplyActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.recyclerView_what)
    RecyclerView recyclerViewWhat;
    @Bind(R.id.tv_after)
    TextView tvAfter;
    @Bind(R.id.tv_before)
    TextView tvBefore;
    @Bind(R.id.pay_ok)
    TextView payOk;
    private ArrayList<VipType> datas;
    private SinglePointAdapter adapter;
    private SinglePayAdapter adapterWhat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_apply);
        ButterKnife.bind(this);
        initDate();
        initView();
        initViewPay();

    }

    private void initDate() {
        datas = new ArrayList<>();
        datas.add(new VipType("10000.00 PPG", "", "1PPG=1元", "", "享9折", "", "", "true"));
    }

    private void initView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SinglePointAdapter(datas);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new SinglePointAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                //initDetail(adapter.getItem(position).getType());
                //sVipPackageId = adapter.getItem(position).getVipPackageId();
                //sAutoBilling = adapter.getItem(position).getAutoBilling();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adapter.setSelection(0);
    }

    private void initViewPay() {
        recyclerViewWhat.setHasFixedSize(true);
        recyclerViewWhat.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        adapterWhat = new SinglePayAdapter(this);
        recyclerViewWhat.setAdapter(adapterWhat);

        adapterWhat.setOnItemClickLitener(new SinglePayAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapterWhat.setSelection(position);
                //initDetail(adapter.getItem(position).getType());
                //sVipPackageId = adapter.getItem(position).getVipPackageId();
                //sAutoBilling = adapter.getItem(position).getAutoBilling();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adapterWhat.setSelection(0);
    }

    @OnClick({R.id.imageView_back, R.id.pay_ok})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (getIntent().getBooleanExtra("expert_fail", false)) {
                    intent = new Intent(this, ExpertApplyActivity.class);
                    intent.putExtra("expert", 2);//跳到申请失败页面
                } else if (getIntent().getBooleanExtra("expert_detail", false)) {
                    intent = new Intent(this, ExpertApplyDetailActivity.class);
                } else {
                    intent = new Intent(this, ApplyActivity.class);
                }
                startActivity(intent);
                finish();
                break;
            case R.id.pay_ok:
                //intent = new Intent(this, PointDetailActivity.class);
                //startActivity(intent);
                //finish();
                showDailog("稍后更新", "");
                break;
        }
    }

    private void showDailog(String s1, final String s2) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(s1)//设置对话框的标题
                //.setMessage(s2)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (getIntent().getBooleanExtra("expert_fail", false)) {
                intent = new Intent(this, ExpertApplyActivity.class);
                intent.putExtra("expert", 2);//跳到申请失败页面
            } else if (getIntent().getBooleanExtra("expert_detail", false)) {
                intent = new Intent(this, ExpertApplyDetailActivity.class);
            } else {
                intent = new Intent(this, ApplyActivity.class);
            }
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
