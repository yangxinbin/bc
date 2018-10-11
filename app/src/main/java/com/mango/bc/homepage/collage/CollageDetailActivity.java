package com.mango.bc.homepage.collage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.util.RoundImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollageDetailActivity extends BaseActivity {

    @Bind(R.id.img_collage_book)
    RoundImageView imgCollageBook;
    @Bind(R.id.tv_collage_name)
    TextView tvCollageName;
    @Bind(R.id.tv_collage_num)
    TextView tvCollageNum;
    @Bind(R.id.l_member)
    LinearLayout lMember;
    @Bind(R.id.tv_collage_price_after)
    TextView tvCollagePriceAfter;
    @Bind(R.id.tv_collage_price_before)
    TextView tvCollagePriceBefore;
    @Bind(R.id.tv_collage_time)
    Button tvCollageTime;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_time)
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.tv_collage_buy, R.id.tv_collage_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                break;
            case R.id.tv_collage_buy:
                break;
            case R.id.tv_collage_time:
                break;
        }
    }
}
