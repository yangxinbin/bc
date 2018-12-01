package com.mango.bc.mine.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RefereeActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.et_bc_ag)
    EditText etBcRe;
    @Bind(R.id.tc_referee_title)
    TextView tcRefereeTitle;
    @Bind(R.id.tv_referee)
    TextView tvReferee;
    @Bind(R.id.bt_sure)
    Button btSure;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = SPUtils.getInstance("bc", this);
        setContentView(R.layout.activity_referee);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (getIntent().getBooleanExtra("hasparent", false)) {
            etBcRe.setVisibility(View.GONE);
            btSure.setVisibility(View.GONE);
            tcRefereeTitle.setVisibility(View.VISIBLE);
            tvReferee.setVisibility(View.VISIBLE);
            Log.v("ccccccccccccccc","========="+getIntent().getStringExtra("parentId"));
            whatParent(getIntent().getStringExtra("parentId"));
        } else {
            etBcRe.setVisibility(View.VISIBLE);
            btSure.setVisibility(View.VISIBLE);
            tcRefereeTitle.setVisibility(View.GONE);
            tvReferee.setVisibility(View.GONE);
        }
    }

    private void whatParent(final String parentId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.doGet(Urls.HOST + "/v2/user/" + parentId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        final String string;
                        try {
                            string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UserBean userBean = MineJsonUtils.readUserBean(string);
                                    if (userBean != null) {
                                        tvReferee.setText("你的推荐人："+userBean.getAlias());
                                    }
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

    @OnClick({R.id.imageView_back, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                AppUtils.HideKeyboard(etBcRe);
                finish();
                break;
            case R.id.bt_sure:
                if (!TextUtils.isEmpty(etBcRe.getText())) {
                    bcSure();
                } else {
                    AppUtils.showToast(RefereeActivity.this, getString(R.string.finish_information));
                }
                break;
        }
    }

    private void bcSure() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("agencyCode", etBcRe.getText().toString());
                HttpUtils.doPost(Urls.HOST_BECAME, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(RefereeActivity.this, "推荐失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            loadUser();
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(RefereeActivity.this, "推荐失败");
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
                Log.v("qqqqqqqqqqq1111", spUtils.getString("openId", ""));
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //mHandler.sendEmptyMessage(2);
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
                                    if (userBean != null) {
                                        //init();
                                        finish();
                                        AppUtils.showToast(RefereeActivity.this, "推荐成功");
                                        spUtils.put("authToken", userBean.getAuthToken());
                                        Log.v("llll1lllll", "=aaaa==" + userBean.isVip());
                                        EventBus.getDefault().postSticky(userBean);//刷新
                                    }
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
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
