package com.mango.bc.mine.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.activity.CollageActivity;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExpertApplyDetailActivity extends BaseActivity {

    @Bind(R.id.imageVie_pic)
    CircleImageView imageViePic;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_member_state)
    TextView tvMemberState;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_apply_detail);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
        initMember(MineJsonUtils.readMemberBean(spUtils.getString("member", "")));
    }

    private void initMember(MemberBean member) {
        if (member == null)
            return;
        tvMemberState.setText(member.getSize() + "人收益" + member.getTotal() + "积分");
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
    }

    @OnClick({R.id.imageView_back, R.id.tv_teach, R.id.tv_copy, R.id.tv_code, R.id.l_member, R.id.open_point})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.tv_teach:
                intent = new Intent(this, TeachActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_copy:
                //添加到剪切板
                ClipboardManager clipboardManager =
                        (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                /**之前的应用过期的方法，clipboardManager.setText(copy);*/
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, tvNumber.getText().toString()));
                if (clipboardManager.hasPrimaryClip()) {
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();
                }
                AppUtils.showToast(this, "复制成功");
                break;
            case R.id.tv_code:
                break;
            case R.id.l_member:
                intent = new Intent(this, MemberActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.open_point:
                intent = new Intent(this, PointApplyActivity.class);
                intent.putExtra("expert_detail",true);
                startActivity(intent);
                finish();
                break;
        }
    }
}
