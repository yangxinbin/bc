package com.mango.bc.wallet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProblemActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tv_problem_title)
    TextView tvProblemTitle;
    @Bind(R.id.tv_problem_detail)
    TextView tvProblemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        ButterKnife.bind(this);
        tvProblemTitle.setText(getIntent().getStringExtra("k1"));
        tvProblemDetail.setText(getIntent().getStringExtra("k2"));
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        finish();
    }
}
