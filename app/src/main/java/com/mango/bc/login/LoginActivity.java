package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements PlatformActionListener {

    @Bind(R.id.imageView_wechatLogin)
    ImageView imageViewWechatLogin;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.button)
    Button button;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    private void wechatLogin() {
        textView.setText(++i + "");
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(this);
        wechat.authorize();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        textView.setText(platform.getDb().exportData());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @OnClick({R.id.imageView_wechatLogin, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_wechatLogin:
                wechatLogin();
                break;
            case R.id.button:
                Intent intent = new Intent(this, BcActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
