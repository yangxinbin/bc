package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.homepage.activity.OpenUpVipServiceActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class VipCenterServiceActivity extends BaseServiceActivity {

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

        tvBookReadDay.setText("BC大陆已经陪你读书" + ((System.currentTimeMillis() - auth.getCreatedOn()) / (1000 * 60 * 60 * 24)) + "天");
        if (auth.getStats() != null) {
            tvVipContent1.setText("VIP特权免费读了" + auth.getStats().getVipGetMemberBooks() + "本书，共节省" + auth.getStats().getBuyMemberMoneySaved() + "币");
            tvVipContent2.setText("购买大咖课程" + auth.getStats().getPaidBooks() + "节，共节省" + auth.getStats().getBuyPaidBookMoneySaved() + "币");
        }
    }

    @OnClick({R.id.imageView_back, R.id.buy_vip,R.id.l_auto})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_auto:
                intent = new Intent(this, VipAutoServiceActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buy_vip:
                intent = new Intent(this, OpenUpVipServiceActivity.class);
                intent.putExtra("center",true);
                startActivity(intent);
                finish();
                break;
        }
    }
}
