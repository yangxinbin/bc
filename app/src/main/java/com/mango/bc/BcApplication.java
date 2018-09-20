package com.mango.bc;

import android.app.Application;

import com.mob.MobSDK;


public class BcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        MobSDK.init(getApplicationContext(), "27cc03aae2030", "7b1cb97ec4df744db5601517c3b58a0b");
    }
}