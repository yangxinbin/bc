package com.mango.bc.wallet.activity;

import android.os.Bundle;
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
    @Bind(R.id.et_email)
    EditText etEmail;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_apply);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        finish();
    }
}
