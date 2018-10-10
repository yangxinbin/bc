package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpertApplyActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.applying)
    ImageView applying;
    @Bind(R.id.apply_fail)
    ImageView applyFail;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_company)
    EditText etCompany;
    @Bind(R.id.et_position)
    EditText etPosition;
    /*    @Bind(R.id.et_email)
        EditText etEmail;*/
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.l_et_all)
    LinearLayout lEtAll;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.l_3)
    LinearLayout l3;
    @Bind(R.id.l_4)
    LinearLayout l4;
    @Bind(R.id.l_5)
    LinearLayout l5;
    @Bind(R.id.l_tv_all)
    LinearLayout lTvAll;
    @Bind(R.id.sure_apply)
    Button sureApply;
    @Bind(R.id.re_apply)
    Button reApply;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    private int expert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_apply);
        ButterKnife.bind(this);
        expert = getIntent().getIntExtra("expert", 0);
        initView(expert);
    }

    private void initView(int i) {//三个页面
        switch (i) {
            case 0://申请
                tvPoint.setVisibility(View.GONE);
                applying.setVisibility(View.GONE);
                applyFail.setVisibility(View.GONE);
                lTvAll.setVisibility(View.GONE);
                reApply.setVisibility(View.GONE);
                break;
            case 1://申请中
                tvPoint.setVisibility(View.GONE);
                applyFail.setVisibility(View.GONE);
                lEtAll.setVisibility(View.GONE);
                reApply.setVisibility(View.GONE);
                sureApply.setVisibility(View.GONE);
                break;
            case 2://申请失败
                lEtAll.setVisibility(View.GONE);
                applying.setVisibility(View.GONE);
                sureApply.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick({R.id.imageView_back, R.id.tv_point})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (expert == 0) {
                    intent = new Intent(this, ApplyActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.tv_point:
                intent = new Intent(this, PointApplyActivity.class);
                intent.putExtra("expert_fail", true);
                startActivity(intent);
                finish();
                break;
        }
    }
}
