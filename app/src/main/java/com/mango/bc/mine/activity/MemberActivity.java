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

import java.util.ArrayList;
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
    private List<MemberBean.UsersBean> usersBeans = new ArrayList<>();
    private double c_1 = 0;
    private double c_2 = 0;

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
        int type = getIntent().getIntExtra("node", -1);
        if (type == 1) {
            for (int i = 0; i < member.getUsers().size(); i++) {
                if (member.getUsers().get(i).getAgencyInfo().getStatus() == 2 ) {
                    usersBeans.add(member.getUsers().get(i));
                    c_1 = c_1 + Double.parseDouble(member.getUsers().get(i).getCommission());
                }
            }
            tvNum.setText("已有达人" + usersBeans.size() + "人");
            tvPpg.setText("累计收益" + c_1 + "PPG");
        } else if (type == 2) {
            for (int i = 0; i < member.getUsers().size(); i++) {
                if (member.getUsers().get(i).getAgencyInfo().getStatus() != 2 ) {
                    usersBeans.add(member.getUsers().get(i));
                    c_2 = c_2 + Double.parseDouble(member.getUsers().get(i).getCommission());
                }
            }
            tvNum.setText("已有成员" + usersBeans.size() + "人");
            tvPpg.setText("累计收益" + c_2 + "PPG");
        }


        initView(usersBeans);
    }

    private void initView(List<MemberBean.UsersBean> list) {
        adapter = new MemberAdapter(this, list);
        gv.setAdapter(adapter);
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        Intent intent;
        if (getIntent().getIntExtra("node", 0) != 0) {
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
            if (getIntent().getIntExtra("node", 0) != 0) {
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
