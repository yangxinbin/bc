package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.activity.OpenUpVipActivity;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
        initView(MineJsonUtils.readStatsBean(spUtils.getString("stats", "")));
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
/*        if (auth.getBilling() != null)
            tvVipTime.setText("至" + DateUtil.getDateToString(auth.getBilling().getEndOn(), "yyyy-MM-dd"));

        tvBookReadDay.setText("BC大陆已经陪你读书" + auth.getStats().getAgeDays()*//*((System.currentTimeMillis() - auth.getCreatedOn()) / (1000 * 60 * 60 * 24))*//* + "天");
        if (auth.getStats() != null) {
            tvVipContent1.setText("VIP特权免费读了" + auth.getStats().getVipGetMemberBooks() + "本书，共节省" + auth.getStats().getBuyMemberMoneySaved() + "币");
            tvVipContent2.setText("购买大咖课程" + auth.getStats().getPaidBooks() + "节，共节省" + auth.getStats().getBuyPaidBookMoneySaved() + "币");
        }*/
        //以下是达人节点UI
        if (auth.getAgencyInfo() == null)
            return;
        if (auth.getAgencyInfo().getStatus() == 2) {
            imgAgency.setVisibility(View.VISIBLE);
        }
    }

    private void initView(StatsBean statsBean) {
        if (statsBean == null)
            return;
        tvBookReadDay.setText("BC大陆已经陪你读书" + statsBean.getAgeDays()/*((System.currentTimeMillis() - auth.getCreatedOn()) / (1000 * 60 * 60 * 24))*/ + "天");
        tvVipContent1.setText("VIP特权免费读了" + statsBean.getVipBooks() + "本书，共节省" + statsBean.getMemberBookSaving() + "币");
        tvVipContent2.setText("购买大咖课程" + statsBean.getPaidBooks() + "节，共节省" + statsBean.getPaidBookSaving() + "币");
    }

    @OnClick({R.id.imageView_back, R.id.buy_vip, R.id.l_auto})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_auto:
                intent = new Intent(this, VipAutoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buy_vip:
                intent = new Intent(this, OpenUpVipActivity.class);
                intent.putExtra("center", true);
                startActivity(intent);
                finish();
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
