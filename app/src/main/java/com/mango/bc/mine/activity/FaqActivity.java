package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.wallet.activity.ProblemActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FaqActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.l_1)
    LinearLayout l1;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.l_3)
    LinearLayout l3;
    @Bind(R.id.l_4)
    LinearLayout l4;
    @Bind(R.id.l_5)
    LinearLayout l5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.l_1, R.id.l_2, R.id.l_3, R.id.l_4, R.id.l_5})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_1:
                intent = new Intent(this, ProblemActivity.class);
                intent.putExtra("k1",this.getResources().getString(R.string.what_pp));
                intent.putExtra("k2",this.getResources().getString(R.string.what_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_2:
                intent = new Intent(this, ProblemActivity.class);
                intent.putExtra("k1",this.getResources().getString(R.string.how_pp));
                intent.putExtra("k2",this.getResources().getString(R.string.how_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_3:
                intent = new Intent(this, ProblemActivity.class);
                intent.putExtra("k1",this.getResources().getString(R.string.does_pp));
                intent.putExtra("k2",this.getResources().getString(R.string.does_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_4:
                intent = new Intent(this, ProblemActivity.class);
                intent.putExtra("k1",this.getResources().getString(R.string.how_to_pp));
                intent.putExtra("k2",this.getResources().getString(R.string.how_to_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_5:
                intent = new Intent(this, ProblemActivity.class);
                intent.putExtra("k1",this.getResources().getString(R.string.cancel_pp));
                intent.putExtra("k2",this.getResources().getString(R.string.cancel_pp_detail));
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
