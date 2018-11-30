package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.adapter.NoScrollGridView;
import com.mango.bc.mine.adapter.MemberAdapter;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.SPUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberActivity extends BaseActivity {

    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_ppg)
    TextView tvPpg;
    @Bind(R.id.gv)
    NoScrollGridView gv;
    private SPUtils spUtils;
    private MemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initMember(MineJsonUtils.readMemberBean(spUtils.getString("member", "")));
    }

    private void initMember(MemberBean member) {
        if (member == null)
            return;
        tvNum.setText("已有成员" + member.getSize() + "人");
        tvPpg.setText("累计收益" + member.getTotal() + "PPG");
        initView(member.getUsers());
    }

    private void initView(List<MemberBean.UsersBean> list) {
        adapter = new MemberAdapter(this, list);
        gv.setAdapter(adapter);
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        Intent intent;
        if (getIntent().getBooleanExtra("node", false)) {
            intent = new Intent(this, PointApplyDetailActivity.class);
        } else {
            intent = new Intent(this, ExpertApplyDetailActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent;
            if (getIntent().getBooleanExtra("node", false)) {
                intent = new Intent(this, PointApplyDetailActivity.class);
            } else {
                intent = new Intent(this, ExpertApplyDetailActivity.class);
            }
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
