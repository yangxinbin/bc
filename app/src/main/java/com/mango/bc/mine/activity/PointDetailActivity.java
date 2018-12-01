package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
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

public class PointDetailActivity extends BaseActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_position)
    EditText etPosition;
    @Bind(R.id.et_company)
    EditText etCompany;
    private SPUtils spUtils;
/*    @Bind(R.id.l_zoom)
    LinearLayout lZoom;
    @Bind(R.id.l_adress)
    LinearLayout lAdress;
    @Bind(R.id.l_et_all)
    LinearLayout lEtAll;
    @Bind(R.id.item_content1)
    EditText itemContent1;
    @Bind(R.id.item_content2)
    EditText itemContent2;
    @Bind(R.id.sure_apply)
    Button sureApply;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = SPUtils.getInstance("bc", this);
        setContentView(R.layout.activity_point_detail);
        ButterKnife.bind(this);
        initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
    }

    private void initView(UserBean auth) {
        if (auth == null)
            return;
        if (auth.getAgencyInfo().getRealName() != null)
            etName.setText(auth.getAgencyInfo().getRealName());
        if (auth.getAgencyInfo().getPhone() != null)
            etPhone.setText(auth.getAgencyInfo().getPhone());
        if (auth.getAgencyInfo().getCompany() != null)
            etCompany.setText(auth.getAgencyInfo().getCompany());
        if (auth.getAgencyInfo().getPosition() != null)
            etPosition.setText(auth.getAgencyInfo().getPosition());
    }

    @OnClick({R.id.imageView_back, R.id.tv_jump,/* R.id.l_zoom, R.id.l_adress,*/ R.id.sure_apply})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                //intent = new Intent(this, PointApplyDetailActivity.class);
                //startActivity(intent);
                finish();
                break;
            case R.id.tv_jump:
                finish();
                break;
/*            case R.id.l_zoom:
                intent = new Intent(this, PointZoomActivity.class);
                startActivityForResult(intent, 0);
                finish();
                break;
            case R.id.l_adress:
                break;*/
            case R.id.sure_apply:
                updateInfo();
                break;
        }
    }

    private void updateInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("realName", etName.getText().toString());
                mapParams.put("phone", etPhone.getText().toString());
                mapParams.put("company", etCompany.getText().toString());
                mapParams.put("position", etPosition.getText().toString());
                HttpUtils.doPost(Urls.HOST_INFO, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getBaseContext(), "信息更新失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        if (String.valueOf(response.code()).startsWith("2")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadUser();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getBaseContext(), "信息更新失败");
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
                        try {
                            final String string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spUtils.put("auth", string);//刷新用户信息
                                    finish();
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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {

        }
    }*/

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
