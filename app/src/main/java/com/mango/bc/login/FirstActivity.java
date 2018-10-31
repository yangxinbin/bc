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
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.SPUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class FirstActivity extends BaseActivity {

    @Bind(R.id.tv_see)
    TextView tvSee;
    private SPUtils spUtils;
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
                wechatLogin();
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
                Log.v("uuuuuuuuu","----"+platform.getDb().get("unionid"));
                Intent intent;
                if (true){
                    intent = new Intent(FirstActivity.this, BcActivity.class);
                    intent.putExtra("wechat", true);
                }else {
                    intent = new Intent(FirstActivity.this, BunblePhoneActivity.class);
                }
                startActivity(intent);
                finish();
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
}
