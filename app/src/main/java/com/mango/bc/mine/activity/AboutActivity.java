package com.mango.bc.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        tvVersion.setText("版本号V"+getAppVersionName(this));
    }
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName=null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            //Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        finish();
    }
}
