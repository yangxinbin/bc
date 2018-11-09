package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.adapter.GirdDownAdapter;
import com.mango.bc.login.adapter.NoScrollGridView;
import com.mango.bc.mine.activity.setting.UserChangeActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;

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
    @Bind(R.id.tv_jump)
    TextView tvJump;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.button_next)
    Button buttonNext;
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
        if (getIntent().getBooleanExtra("update", false)) {
            textView2.setText("修改信息");
            buttonNext.setText("确认修改");
            tvJump.setVisibility(View.GONE);
            etName.setText(getIntent().getStringExtra("name"));
            etOld.setText(getIntent().getStringExtra("year"));
            etZoom.setText(getIntent().getStringExtra("company"));
            etPosition.setText(getIntent().getStringExtra("position"));
        }
    }

    private void initGird() {
        adapter = new GirdDownAdapter(this, list);
        gv.setAdapter(adapter);
        if (getIntent().getBooleanExtra("update", false)) {
            if ("先生".equals(getIntent().getStringExtra("sex"))) {
                currentPosition = 0;
            } else {
                currentPosition = 1;
            }
        }
        adapter.setCheckItem(currentPosition);
        gv.setOnItemClickListener(this);
    }

    @OnClick({R.id.imageView_back, R.id.tv_jump, R.id.button_next})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (getIntent().getBooleanExtra("update", false)) {

                } else {
                    intent = new Intent(this, LikeActivity.class);
                    startActivity(intent);
                }
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
                if (!/*(TextUtils.isEmpty(identity) || TextUtils.isEmpty(hobbies) || */(TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etOld.getText()) || TextUtils.isEmpty(etZoom.getText()) || TextUtils.isEmpty(etPosition.getText()))) {
                    if (getIntent().getBooleanExtra("update", false)) {
                        updateProfile();
                    } else {
                        addProfile();
                    }
                } else {
                    AppUtils.showToast(UserDetailActivity.this, getString(R.string.finish_information));
                }
                break;
        }
    }

    private void updateProfile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("identity", spUtils.getString("identity", ""));
                mapParams.put("hobbies", spUtils.getString("hobbies", ""));
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
                                        loadUser();
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
                                    if (getIntent().getBooleanExtra("perfect", false)) {
                                        Log.v("iiiiiiiiii","=====");
                                        UserBean userBean = MineJsonUtils.readUserBean(string);
                                        if (userBean != null) {
                                            EventBus.getDefault().postSticky(userBean);//刷新
                                        }
                                        Intent intent = new Intent(UserDetailActivity.this, UserChangeActivity.class);
                                        startActivity(intent);
                                    }else {
                                        setResult(10);
                                    }
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
                                        Intent intent;
                                        if (getIntent().getBooleanExtra("perfect", false)) {
                                            loadUser();
                                        } else {
                                            intent = new Intent(UserDetailActivity.this, BcActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

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
            if (getIntent().getBooleanExtra("update", false)) {

            } else {
                intent = new Intent(this, LikeActivity.class);
                startActivity(intent);
            }
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
