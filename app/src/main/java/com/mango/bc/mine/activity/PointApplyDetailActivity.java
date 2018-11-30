package com.mango.bc.mine.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.activity.RechargeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PointApplyDetailActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tv_copy)
    TextView tvCopy;
    @Bind(R.id.p1)
    LinearLayout p1;
    @Bind(R.id.p2)
    LinearLayout p2;
    @Bind(R.id.l_1)
    LinearLayout l1;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.buy_ppg)
    Button buyPpg;
    @Bind(R.id.imageVie_pic)
    CircleImageView imageViePic;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_ppg)
    TextView tvPpg;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.num1)
    TextView num1;
    @Bind(R.id.tv_member_state)
    TextView tvMemberState;
    @Bind(R.id.num2)
    TextView num2;
    @Bind(R.id.tv_member_point)
    TextView tvMemberPoint;
    @Bind(R.id.finish_info)
    TextView finishInfo;
    private SPUtils spUtils;
    private String stringCode;
    private int num_1 = 0;
    private int num_2 = 0;
    private double c_1 = 0;
    private double c_2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = SPUtils.getInstance("bc", this);
        setContentView(R.layout.activity_point_apply_detail);
        ButterKnife.bind(this);
        initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
        initMember(MineJsonUtils.readMemberBean(spUtils.getString("member", "")));
    }

    private void initMember(MemberBean member) {
        if (member == null)
            return;
        if (member.getUsers() != null) {
            for (int i = 0; i < member.getUsers().size(); i++) {
                if ("agency".equals(member.getUsers().get(i).getType())) {
                    num_1 = num_1 + 1;
                    c_1 = c_1 + Double.parseDouble(member.getUsers().get(i).getCommission());
                } else {
                    num_2 = num_2 + 1;
                    c_2 = c_2 + Double.parseDouble(member.getUsers().get(i).getCommission());
                }
            }
            num1.setText("已有达人" + num_1 + "人");
            num2.setText("已有成员" + num_2 + "人");
            tvMemberState.setText("收益" + c_1 + "PPG");
            tvMemberPoint.setText("收益" + c_2 + "PPG");
        }
    }

    private void initView(UserBean auth) {
        if (auth == null)
            return;
        tvName.setText(auth.getAlias());
        if (auth.getAvator() != null) {
            Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + auth.getAvator().getFileName()).into(imageViePic);
        } else {
            imageViePic.setImageDrawable(getResources().getDrawable(R.drawable.head_pic2));
        }
        if (auth.getAgencyInfo() != null) {
            stringCode = auth.getAgencyInfo().getAgencyCode();
            tvNumber.setText("邀请码：" + stringCode);
        }
        if (auth.getAgencyInfo().getRealName() != null) {
            finishInfo.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.imageView_back, R.id.tv_copy, R.id.p1, R.id.p2, R.id.l_1, R.id.l_2, R.id.buy_ppg})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.tv_copy:
                //添加到剪切板
                ClipboardManager clipboardManager =
                        (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                /**之前的应用过期的方法，clipboardManager.setText(copy);*/
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, stringCode));
                if (clipboardManager.hasPrimaryClip()) {
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();
                }
                AppUtils.showToast(this, "复制成功");
                break;
            case R.id.p1:
                break;
            case R.id.p2:
                intent = new Intent(this, MemberActivity.class);
                intent.putExtra("node",true);
                startActivity(intent);
                finish();
                break;
            case R.id.l_1:
                break;
            case R.id.l_2:
                intent = new Intent(this, PointDetailActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.buy_ppg:
                intent = new Intent(this, RechargeActivity.class);
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
