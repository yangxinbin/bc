package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.adapter.GirdDownAdapter;
import com.mango.bc.login.adapter.NoScrollGridView;
import com.mango.bc.mine.activity.ExpertApplyActivity;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_old)
    EditText etOld;
    @Bind(R.id.gv)
    NoScrollGridView gv;
    @Bind(R.id.et_zoom)
    EditText etZoom;
    @Bind(R.id.et_position)
    EditText etPosition;
    private List<String> list;
    private GirdDownAdapter adapter;
    private int currentPosition = 0;
    private String sex;
    private SPUtils spUtils;
    private String identity;
    private String hobbies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initDate();
        initGird();
    }

    private void initDate() {
        list = new ArrayList<>();
        list.add("先生");
        list.add("女士");
        identity = getIntent().getStringExtra("identity");
        hobbies = getIntent().getStringExtra("hobbies");
    }

    private void initGird() {
        adapter = new GirdDownAdapter(this, list);
        gv.setAdapter(adapter);
        adapter.setCheckItem(currentPosition);
        gv.setOnItemClickListener(this);
    }

    @OnClick({R.id.imageView_back, R.id.tv_jump, R.id.button_next})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, LikeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_jump:
                spUtils.put("skip", "true");
                intent = new Intent(this, BcActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_next:
                sex = list.get(currentPosition);
                if (!(TextUtils.isEmpty(identity) || TextUtils.isEmpty(hobbies) || TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etOld.getText()) || TextUtils.isEmpty(etZoom.getText()) || TextUtils.isEmpty(etPosition.getText()))) {
                    addProfile();
                } else {
                    AppUtils.showToast(UserDetailActivity.this, getString(R.string.finish_information));
                }
                break;
        }
    }

    private void addProfile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("identity", identity);
                mapParams.put("hobbies", hobbies);
                mapParams.put("realName", etName.getText().toString());
                mapParams.put("age", etOld.getText().toString());
                mapParams.put("sex", sex);
                mapParams.put("company", etZoom.getText().toString());
                mapParams.put("duty", etPosition.getText().toString());
                HttpUtils.doPost(Urls.HOST_PROFILE, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(UserDetailActivity.this, getString(R.string.send_fail));
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            if ("ok".equals(response.body().string())) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(UserDetailActivity.this, BcActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtils.showToast(UserDetailActivity.this, getString(R.string.send_fail));
                                    }
                                });
                            }
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(UserDetailActivity.this, getString(R.string.send_fail));
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentPosition = position;
        adapter.setCheckItem(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(this, LikeActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
