package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.activity.OpenUpVipActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class VipCenterActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.imageVie_author)
    CircleImageView imageVieAuthor;
    @Bind(R.id.tv_vipName)
    TextView tvVipName;
    @Bind(R.id.img_vip)
    ImageView imgVip;
    @Bind(R.id.img_agency)
    ImageView imgAgency;
    @Bind(R.id.tv_vipTime)
    TextView tvVipTime;
    @Bind(R.id.tv_bookReadDay)
    TextView tvBookReadDay;
    @Bind(R.id.tv_vipTip)
    TextView tvVipTip;
    @Bind(R.id.tv_vipContent1)
    TextView tvVipContent1;
    @Bind(R.id.tv_vipContent2)
    TextView tvVipContent2;
    @Bind(R.id.l_vip)
    LinearLayout lVip;
    @Bind(R.id.buy_vip)
    TextView buyVip;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_center);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initView(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));
    }
    private void initView(UserBean auth) {
        if (auth == null)
            return;
        if (auth.isVip()) {
            imgVip.setVisibility(View.VISIBLE);
        } else {
            imgVip.setVisibility(View.GONE);
        }
        tvVipName.setText(auth.getAlias());
        if (auth.getAvator() != null)
            Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + auth.getAvator().getFileName()).into(imageVieAuthor);
        if (auth.getBilling() != null)
            tvVipTime.setText("至" + DateUtil.getDateToString(auth.getBilling().getEndOn(), "yyyy-MM-dd"));

    }
    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.buy_vip:
                Intent intent = new Intent(this,OpenUpVipActivity.class);
                startActivity(intent);
                break;
        }
    }
}
