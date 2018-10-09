package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.activity.OpenUpVipActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VipAutoActivity extends BaseActivity {

    @Bind(R.id.buy_vip)
    TextView buyVip;
    @Bind(R.id.tv_time_to)
    TextView tvTimeTo;
    @Bind(R.id.tv_time_next)
    TextView tvTimeNext;
    @Bind(R.id.tv_vip_time)
    LinearLayout tvVipTime;
    @Bind(R.id.auto_title)
    TextView autoTitle;
    private SPUtils spUtils;
    private boolean isAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_auto);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initView(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));
    }

    private void initView(UserBean auth) {
        if (auth == null)
            return;
        if (auth.getBilling() != null) {
            isAuto = auth.getBilling().isAuto();
            if (isAuto) {
                autoTitle.setText(getResources().getString(R.string.haved_auto_vip));
                buyVip.setText(getResources().getString(R.string.no_auto_bt_vip));
                tvTimeTo.setText("当前会员有效期：" + DateUtil.getDateToString(auth.getBilling().getStartOn(), "yyyy-MM-dd") + "-" + DateUtil.getDateToString(auth.getBilling().getEndOn(), "yyyy-MM-dd"));
                tvTimeNext.setText("下次扣费时间：" + DateUtil.getDateToString(auth.getBilling().getEndOn(), "yyyy-MM-dd"));
            } else {
                autoTitle.setText(getResources().getString(R.string.no_haved_auto_vip));
                buyVip.setText(getResources().getString(R.string.auto_bt_vip));
                tvVipTime.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, VipCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.buy_vip:
                if (isAuto) {

                } else {
                    intent = new Intent(this, OpenUpVipActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
