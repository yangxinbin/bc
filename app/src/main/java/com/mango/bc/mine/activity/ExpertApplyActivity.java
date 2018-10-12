package com.mango.bc.mine.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    /*    @Bind(R.id.et_email)
        EditText etEmail;*/
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.l_et_all)
    LinearLayout lEtAll;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.l_3)
    LinearLayout l3;
/*    @Bind(R.id.l_4)
    LinearLayout l4;*/
    @Bind(R.id.l_5)
    LinearLayout l5;
    @Bind(R.id.l_tv_all)
    LinearLayout lTvAll;
    @Bind(R.id.sure_apply)
    Button sureApply;
    @Bind(R.id.re_apply)
    Button reApply;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_position)
    TextView tvPosition;
    private int expert;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_apply);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        expert = getIntent().getIntExtra("expert", 0);
        initView(expert);
        initViewPlay(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
    }

    private void initViewPlay(UserBean auth) {
        if (auth == null)
            return;
        if (auth.getAgencyInfo() != null) {
            tvName.setText(auth.getAgencyInfo().getRealName());
            tvCompany.setText(auth.getAgencyInfo().getCompany());
            tvPhone.setText(auth.getAgencyInfo().getPhone());
            tvPosition.setText(auth.getAgencyInfo().getPosition());
        }

    }

    private void initView(int i) {//三个页面
        switch (i) {
            case 0://申请
                tvPoint.setVisibility(View.GONE);
                applying.setVisibility(View.GONE);
                applyFail.setVisibility(View.GONE);
                lTvAll.setVisibility(View.GONE);
                reApply.setVisibility(View.GONE);
                break;
            case 1://申请中
                tvPoint.setVisibility(View.GONE);
                applyFail.setVisibility(View.GONE);
                lEtAll.setVisibility(View.GONE);
                reApply.setVisibility(View.GONE);
                sureApply.setVisibility(View.GONE);
                break;
            case 2://申请失败
                lEtAll.setVisibility(View.GONE);
                applying.setVisibility(View.GONE);
                sureApply.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick({R.id.imageView_back, R.id.tv_point, R.id.sure_apply, R.id.re_apply})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (expert == 0) {
                    intent = new Intent(this, ApplyActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.tv_point:
                intent = new Intent(this, PointApplyActivity.class);
                intent.putExtra("expert_fail", true);
                startActivity(intent);
                finish();
                break;
            case R.id.sure_apply:
                if (!(TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etCompany.getText()) || TextUtils.isEmpty(etPhone.getText()) || TextUtils.isEmpty(etPosition.getText()))) {
                    applyAgency();
                }else {
                    AppUtils.showToast(ExpertApplyActivity.this, getString(R.string.finish_information));
                }
                break;
            case R.id.re_apply:
                intent = new Intent(this, ExpertApplyActivity.class);
                intent.putExtra("expert", 0);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void showDailog(String s1, final String s2) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(s1)//设置对话框的标题
                //.setMessage(s2)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void applyAgency() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("realName", etName.getText().toString());
                mapParams.put("company", etCompany.getText().toString());
                mapParams.put("phone", etPhone.getText().toString());
                mapParams.put("position", etPosition.getText().toString());
                HttpUtils.doPost(Urls.HOST_APPLYAGENCY, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(ExpertApplyActivity.this, "申请失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(ExpertApplyActivity.this, "申请成功");
                                    loadUser();
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(ExpertApplyActivity.this, "申请失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    private void loadUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", spUtils.getString("openId", ""));
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        final String string;
                        try {
                            string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spUtils.put("auth", string);
                                    UserBean userBean = MineJsonUtils.readUserBean(string);
                                    EventBus.getDefault().postSticky(userBean);//刷新钱包，我的。
                                    showDailog("审核中，请您耐心等待。", "");
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (expert == 0) {
                intent = new Intent(this, ApplyActivity.class);
                startActivity(intent);
            }
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
