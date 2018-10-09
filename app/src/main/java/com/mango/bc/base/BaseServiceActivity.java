package com.mango.bc.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.play.service.PlayService;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.PublicWay;
import com.mango.bc.util.StatusBarUtil;


/**
 * Created by Administrator on 2018/5/15 0015.
 */

public abstract class BaseServiceActivity extends AppCompatActivity {
    private PlayService playService;
    private PlayServiceConnection serviceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityState(this);
        PublicWay.activityList.add(this); // 把这个界面添加到activityList集合里面
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            StatusBarUtil.setStatusBarColor(this, R.color.white);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark);
        }
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        serviceConnection = new PlayServiceConnection();
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playService = ((PlayService.PlayBinder) service).getService();
            onServiceBound();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getClass().getSimpleName(), "service disconnected");
        }
    }

    protected void onServiceBound() {
    }

    /**
     * 设置屏幕只能竖屏
     *
     * @param activity activity
     */
    public void setActivityState(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 检测网络是否连接
     */
    public boolean isNetConnect() {
        return NetUtil.isNetConnect(this); // NetUtil 是我自己封装的类
    }
}
