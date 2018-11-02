package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mango.bc.BcActivity;
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
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FirstActivity extends BaseActivity {

    @Bind(R.id.tv_see)
    TextView tvSee;
    private SPUtils spUtils;
    private boolean isFirstEnter = true;
/*    @Bind(R.id.button_begin)
    Button buttonBegin;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.l_wechat, R.id.tv_see/*, R.id.button_begin*/})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.l_wechat:
                if (NetUtil.isNetConnect(this)) {
                    if (isFirstEnter) {
                        isFirstEnter = false;
                        wechatLogin();//只能点一次
                    }
                } else {
                    AppUtils.showToast(this, getResources().getString(R.string.check_net));
                }
                break;
            case R.id.tv_see:
                //intent = new Intent(this, BcActivity.class);
                intent = new Intent(FirstActivity.this, BunblePhoneActivity.class);
                startActivity(intent);
                finish();
                break;
/*            case R.id.button_begin:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;*/
        }
    }

    private void wechatLogin() {//要数据不要功能
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //wechat.SSOSetting(false);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                final String userInfo = StrUtils.format("", hashMap);
                spUtils.put("openId", platform.getDb().get("unionid"));
                Log.v("uuuuuuuuu", "-----" + platform.getDb().get("unionid"));
                loadUser(platform.getDb().get("unionid"));
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.showToast(FirstActivity.this, "微信登录失败");
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        if (wechat.isClientValid()) {
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
        }
        //判断指定平台是否已经完成授权
/*        if(wechat.isAuthValid()) {
            String userId = wechat.getDb().getUserId();
            if (userId != null) {
                //UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                //login(wechat.getName(), userId, null);
                return;
            }
        }*/
        //wechat.authorize();
        wechat.showUser(null);

    }

    private void loadUser(final String unionid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", unionid);
                Log.v("qqqqqqqqqqq1111", spUtils.getString("openId", ""));
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(FirstActivity.this,"登录失败");
                            }
                        });
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
                                        spUtils.put("authToken", userBean.getAuthToken());
                                        EventBus.getDefault().postSticky(userBean);//刷新
                                        Intent intent = null;
                                        if (userBean.getUserProfile() != null ) {
                                            if (userBean.getUserProfile().getRealName() != null || "true".equals(spUtils.getString("skip", ""))) {
                                                intent = new Intent(FirstActivity.this, BcActivity.class);
                                                intent.putExtra("wechat", true);
                                            } else {
                                                intent = new Intent(FirstActivity.this, BunblePhoneActivity.class);
                                            }
                                        }
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(FirstActivity.this,"登录失败");
                                }
                            });
                        }

                    }
                });
            }
        }).start();
    }
}
