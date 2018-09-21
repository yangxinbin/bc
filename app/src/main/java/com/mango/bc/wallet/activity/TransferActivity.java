package com.mango.bc.wallet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.RefreshPpgBean;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TransferActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.et_purse_adress)
    EditText etPurseAdress;
    @Bind(R.id.tv_all_pp)
    TextView tvAllPp;
    @Bind(R.id.et_purse_to)
    EditText etPurseTo;
    @Bind(R.id.bu_sure)
    Button buSure;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        if (!NetUtil.isNetConnect(this))
            AppUtils.showToast(this, getResources().getString(R.string.check_net));
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initAuth(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));

    }

    private void initAuth(UserBean userBean) {
        if (userBean == null)
            return;
        if (userBean.getWallet() != null) {
            tvAllPp.setText("（当前余额：" + userBean.getWallet().getPpCoins() + "积分）");
        }
    }

    @OnClick({R.id.imageView_back, R.id.bu_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.bu_sure:
                transfer();
                break;
        }
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
                                    spUtils.put("auth", string);
                                    initAuth(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));//刷新
                                    EventBus.getDefault().postSticky(new RefreshPpgBean(true));//刷新
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

    private void transfer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("toWalletAddress", etPurseAdress.getText().toString());
                mapParams.put("ppCoin", etPurseTo.getText().toString());
                HttpUtils.doPost(Urls.HOST_TRANSFER, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getBaseContext(), "转账失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getBaseContext(), "转账成功");
                                    loadUser();
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getBaseContext(), "转账失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }
}
