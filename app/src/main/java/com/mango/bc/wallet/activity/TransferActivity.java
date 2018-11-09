package com.mango.bc.wallet.activity;

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
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
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
    private double ppg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        if (!NetUtil.isNetConnect(this))
            AppUtils.showToast(this, getResources().getString(R.string.check_net));
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initAuth(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));

    }

    private void initAuth(UserBean userBean) {
        if (userBean == null)
            return;
        if (userBean.getWallet() != null) {
            ppg = userBean.getWallet().getPpCoins();
            tvAllPp.setText("（当前余额：" + ppg + "PPG）");
        }
    }

    @OnClick({R.id.imageView_back, R.id.bu_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                AppUtils.hideInput(TransferActivity.this);
                finish();
                break;
            case R.id.bu_sure:
                //Log.v("tttttttt",ppg+"---"+Double.parseDouble(etPurseTo.getText().toString()));
                if (!(TextUtils.isEmpty(etPurseAdress.getText()) || TextUtils.isEmpty(etPurseTo.getText()))) {
                    if (ppg >= Double.parseDouble(etPurseTo.getText().toString())) {
                        transfer();
                    } else {
                        showDailogOpen(getString(R.string.less_ppg), "");
                    }
                } else {
                    AppUtils.showToast(TransferActivity.this, getString(R.string.finish_information));
                }
                break;
        }
    }

    private void showDailogOpen(String s1, final String s2) {
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
                        Intent intent = new Intent(TransferActivity.this, RechargeActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
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
                                    initAuth(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
                                    UserBean userBean = MineJsonUtils.readUserBean(string);
                                    EventBus.getDefault().postSticky(userBean);//刷新钱包
                                    showDailog("转账成功", "");
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
                            if ("ok".equals(response.body().string())) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //AppUtils.showToast(getBaseContext(), "转账成功");
                                        loadUser();
                                    }
                                });
                            } else {
                                AppUtils.showToast(getBaseContext(), "转账失败");
                            }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AppUtils.hideInput(TransferActivity.this);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
