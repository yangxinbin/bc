package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.util.SPUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.editText_phone)
    EditText editTextPhone;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.imageView_delete_phone, R.id.button_code, R.id.tv_password, R.id.l_wechat})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, FirstActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imageView_delete_phone:
                break;
            case R.id.button_code:
                intent = new Intent(this, CodeActivity.class);
                intent.putExtra("num", editTextPhone.getText().toString());
                startActivity(intent);
                finish();
                break;
            case R.id.tv_password:
                intent = new Intent(this, PasswordLoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.l_wechat:
                wechatLogin();
                break;
        }
    }

    private void wechatLogin() {//要数据不要功能
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //wechat.SSOSetting(false);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                final String userInfo = StrUtils.format("", hashMap);
                Log.v("ooooooooo", platform.getDb().getUserIcon()+"==" + userInfo);
                spUtils.put("openId", platform.getDb().get("unionid"));
                Log.v("qqqqqqqqqqq", spUtils.getString("openId", ""));
                Intent intent = new Intent(LoginActivity.this, WechatLoginActivity.class);
                intent.putExtra("img",platform.getDb().getUserIcon());
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

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

    /* // 授权登录
     private void authorize(Platform plat, Boolean isSSO) {
         // 判断指定平台是否已经完成授权
         if (plat.isAuthValid()) {
             // 已经完成授权，直接读取本地授权信息，执行相关逻辑操作（如登录操作）
             String userId = plat.getDb().getUserId();
             if (!TextUtils.isEmpty(userId)) {
                 UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                 login(plat.getName(), userId, null);
                 return;
             }
         }
         plat.setPlatformActionListener(this);
         // 是否使用SSO授权：true不使用，false使用
         plat.SSOSetting(isSSO);
         // 获取用户资料
         plat.showUser(null);
     }

     // 取消授权
     private void cancleAuth() {
         Platform wxPlatform = ShareSDK.getPlatform(Wechat.NAME);
         wxPlatform.removeAccount(true);
         Toast.makeText(this, "取消授权成功!", Toast.LENGTH_SHORT).show();
     }

     // 回调：授权成功
     public void onComplete(final Platform platform, int action, HashMap<String, Object> res) {
         String userInfo = StrUtils.format("", res);
         textView.setText(userInfo.toString());
         if (action == Platform.ACTION_USER_INFOR) {
             UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
             // 业务逻辑处理：比如登录操作
             String userName = platform.getDb().getUserName(); // 用户昵称
             String userId = platform.getDb().getUserId();   // 用户Id
             String platName = platform.getName();     // 平台名称
             login(platName, userName, res);
         }
     }

     // 回调：授权失败
     public void onError(Platform platform, int action, Throwable t) {
         if (action == Platform.ACTION_USER_INFOR) {
             UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
         }
         t.printStackTrace();
     }

     // 回调：授权取消
     public void onCancel(Platform platform, int action) {
         if (action == Platform.ACTION_USER_INFOR) {
             UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
         }
     }

     // 业务逻辑：登录处理
     private void login(String plat, String userId, HashMap<String, Object> userInfo) {
         Toast.makeText(this, "用户ID:" + userId, Toast.LENGTH_SHORT).show();
         Message msg = new Message();
         msg.what = MSG_LOGIN;
         msg.obj = plat;
         UIHandler.sendMessage(msg, this);
     }

     // 统一消息处理
     private static final int MSG_USERID_FOUND = 1; // 用户信息已存在
     private static final int MSG_LOGIN = 2; // 登录操作
     private static final int MSG_AUTH_CANCEL = 3; // 授权取消
     private static final int MSG_AUTH_ERROR = 4; // 授权错误
     private static final int MSG_AUTH_COMPLETE = 5; // 授权完成

     public boolean handleMessage(Message msg) {
         switch (msg.what) {

             case MSG_USERID_FOUND:
                 Toast.makeText(this, "用户信息已存在，正在跳转登录操作......", Toast.LENGTH_SHORT).show();
                 break;
             case MSG_LOGIN:
                 Toast.makeText(this, "使用微信帐号登录中...", Toast.LENGTH_SHORT).show();
                 break;
             case MSG_AUTH_CANCEL:
                 Toast.makeText(this, "授权操作已取消", Toast.LENGTH_SHORT).show();
                 break;
             case MSG_AUTH_ERROR:
                 Toast.makeText(this, "授权操作遇到错误，请阅读Logcat输出", Toast.LENGTH_SHORT).show();
                 break;
             case MSG_AUTH_COMPLETE:
                 Toast.makeText(this, "授权成功，正在跳转登录操作…", Toast.LENGTH_SHORT).show();
                 // 执行相关业务逻辑操作，比如登录操作
                 String userName = new Wechat().getDb().getUserName(); // 用户昵称
                 String userId = new Wechat().getDb().getUserId();   // 用户Id
                 String platName = new Wechat().getName();      // 平台名称
                 login(platName, userId, null);
                 break;
         }
         return false;
     }

     @Override
     public void onPointerCaptureChanged(boolean hasCapture) {

     }*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
