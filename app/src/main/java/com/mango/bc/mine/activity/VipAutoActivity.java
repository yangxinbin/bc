package com.mango.bc.mine.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.activity.OpenUpVipActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.DateUtil;
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

public class VipAutoActivity extends BaseActivity {

    @Bind(R.id.buy_vip)
    TextView buyVip;
    @Bind(R.id.tv_time_to)
    TextView tvTimeTo;
    @Bind(R.id.tv_time_next)
    TextView tvTimeNext;
    @Bind(R.id.tv_vip_time)
    LinearLayout tvVipTime;
    @Bind(R.id.auto_title)
    TextView autoTitle;
    private SPUtils spUtils;
    private boolean isAuto;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_auto);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
    }

    private void initView(UserBean auth) {
        if (auth == null)
            return;
        authToken = auth.getAuthToken();
        if (auth.getBilling() != null) {
            isAuto = auth.getBilling().isAuto();
            if (isAuto) {
                autoTitle.setText(getResources().getString(R.string.haved_auto_vip));
                buyVip.setText(getResources().getString(R.string.no_auto_bt_vip));
                tvTimeTo.setText("当前会员有效期：" + DateUtil.getDateToString(auth.getBilling().getStartOn(), "yyyy.MM.dd") + " - " + DateUtil.getDateToString(auth.getBilling().getEndOn(), "yyyy.MM.dd"));
                tvTimeNext.setText("下次扣费时间：" + DateUtil.getDateToString(auth.getBilling().getEndOn(), "yyyy.MM.dd"));
            } else {
                autoTitle.setText(getResources().getString(R.string.no_haved_auto_vip));
                buyVip.setText(getResources().getString(R.string.auto_bt_vip));
                tvVipTime.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, VipCenterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buy_vip:
                if (isAuto) {
                    showDailog("确定取消续费吗 ？", "");
                } else {
                    intent = new Intent(this, OpenUpVipActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    private void cancelAuto() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", authToken);
                HttpUtils.doPut(Urls.HOST_AUTOBILL, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(VipAutoActivity.this, "取消失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(VipAutoActivity.this, "取消成功");
                                    loadUser();
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(VipAutoActivity.this, "取消失败");
                                }
                            });
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
                        cancelAuto();
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
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
                        final String string;
                        try {
                            string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spUtils.put("auth", string);
                                    UserBean userBean = MineJsonUtils.readUserBean(string);
                                    initView(userBean);
                                    Log.v("lllllllll", "=aaaa==" + userBean.isVip());
                                    EventBus.getDefault().postSticky(userBean);//我的。
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
            intent = new Intent(this, VipCenterActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
