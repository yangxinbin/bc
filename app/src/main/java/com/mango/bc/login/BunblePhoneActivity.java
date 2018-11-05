package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.activity.PointApplyActivity;
import com.mango.bc.mine.activity.setting.UserChangeActivity;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BunblePhoneActivity extends BaseActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.get_code)
    TextView getCode;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = SPUtils.getInstance("bc", this);
        setContentView(R.layout.activity_bunble_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.get_code, R.id.button_password_ok})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (getIntent().getBooleanExtra("phone", false)) {
                } else {
                    intent = new Intent(BunblePhoneActivity.this, FirstActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.get_code:
                if (etPhone.getText().toString().trim().length() == 11) {
                    timer.start();
                    getPhoneCode();
                } else {
                    AppUtils.showToast(getBaseContext(), "请输入正确的手机号码");
                }
                break;
            case R.id.button_password_ok:
                if (etPhone.getText().toString().trim().length() == 11) {
                    if (etCode.getText().toString().trim().length() != 0) {
                        bunblePhone();
                    } else {
                        AppUtils.showToast(getBaseContext(), "请输入验证码");
                    }
                } else {
                    AppUtils.showToast(getBaseContext(), "请输入正确的手机号码");
                }
/*                intent = new Intent(BunblePhoneActivity.this, PositionActivity.class);
                startActivity(intent);
                finish();*/
                break;
        }
    }

    private void bunblePhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("phone", etPhone.getText().toString());
                mapParams.put("code", etCode.getText().toString());
                HttpUtils.doPost(Urls.HOST_VERIFY, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(BunblePhoneActivity.this, getString(R.string.bunble_fail));
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
                                        if (getIntent().getBooleanExtra("phone", false)) {
                                            loadUser();
                                        } else {
                                            Intent intent = new Intent(BunblePhoneActivity.this, PositionActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtils.showToast(BunblePhoneActivity.this, getString(R.string.bunble_fail));
                                    }
                                });
                            }
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(BunblePhoneActivity.this, getString(R.string.bunble_fail));
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
                                    setResult(10);
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

    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setEnabled(false);
            getCode.setText("   " + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            getCode.setEnabled(true);
            getCode.setText("发送验证码");
        }
    };

    private void getPhoneCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("phone", etPhone.getText().toString());
                HttpUtils.doPost(Urls.HOST_SEND, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(BunblePhoneActivity.this, getString(R.string.code_fail));
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
                                        AppUtils.showToast(BunblePhoneActivity.this, "验证码发送成功");
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtils.showToast(BunblePhoneActivity.this, getString(R.string.code_fail));
                                    }
                                });
                            }
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(BunblePhoneActivity.this, getString(R.string.code_fail));
                                }
                            });
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
            if (getIntent().getBooleanExtra("phone", false)) {
            } else {
                intent = new Intent(BunblePhoneActivity.this, FirstActivity.class);
                startActivity(intent);
            }
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (timer != null) {
            timer.cancel();
        }
    }
}
