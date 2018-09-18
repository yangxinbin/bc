package com.mango.bc;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.BookcaseFragment;
import com.mango.bc.homepage.HomePageFragment;
import com.mango.bc.mine.MineFragment;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.view.BottomBar;
import com.mango.bc.wallet.WalletFragment;

import java.io.IOException;
import java.util.HashMap;

public class BcActivity extends BaseActivity{

    private static Dialog dialog;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.bottom_bar)
    BottomBar bottomBar;
    private SPUtils spUtilsAuthToken;
    private SPUtils spUtilsAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc);
        ButterKnife.bind(this);
        ifCheckIn(this);
        loadUser(); //个人信息从网络拿数据
        spUtilsAuthToken = SPUtils.getInstance("authToken", this);
        spUtilsAuthToken.put("authToken", "eyJhbGciOiJIUzUxMiJ9.eyJhdWRpZW5jZSI6Im1vYmlsZSIsImNyZWF0ZWQiOjE1MzY5MDk3MjI5MzksImFsaWFzIjoi5p2o6ZGr5paMIiwiaWQiOiI1YjhhM2Q0YjA0NDQwYzBhNDhhMzNhMDUiLCJ0eXBlIjoiZ2VuZXJhbCIsIndhbGxldEFkZHJlc3MiOiIweGU3MmUzODdhZjEyZTA4NmFlZWNjOGVmMTljNzcxY2M4IiwiZXhwIjo0MTI4OTA5NzIyLCJ1c2VybmFtZSI6Im9YaGk5NGpRa1hQb3ZCc3FFczBCOFFLc2JNMEEifQ.m6rVYWnsxxogOAVmOLQ1HEC5bv0YzAwPhqGOlQ0tOP1CVec8XBRytgEFo_0rMlgSW42u2F199y3WAOr8XE2yYA");
        bottomBar.setContainer(R.id.container)
                .setTitleBeforeAndAfterColor("#333333", "#ffac00")
                .addItem(HomePageFragment.class,
                        "主页",
                        R.drawable.home,
                        R.drawable.home_select)
                .addItem(BookcaseFragment.class,
                        "书架",
                        R.drawable.bookcase,
                        R.drawable.bookcase_select)
                .addItem(WalletFragment.class,
                        "钱包",
                        R.drawable.wallet,
                        R.drawable.wallet_select)
                .addItem(MineFragment.class,
                        "我的",
                        R.drawable.my,
                        R.drawable.my_select)
                .build();
    }

    private void ifCheckIn(BcActivity bcActivity) {
        showCheckInWindow(bcActivity);
    }

    private void loadUser() {
        spUtilsAuth = SPUtils.getInstance("auth", this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", "oXhi94jQkXPovBsqEs0B8QKsbM0A");
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        AppUtils.showToast(getBaseContext(), "信息获取失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            spUtilsAuth.put("auth", response.body().string());
                        } catch (Exception e) {
                            AppUtils.showToast(getBaseContext(), "信息获取失败");

                        }
                    }
                });
            }
        }).start();
    }

    private void showCheckInWindow(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.check_in, null);
        //此处可按需求为各控件设置属性
        ImageView close_check_in = view.findViewById(R.id.close_check_in);
        TextView tv_check = view.findViewById(R.id.tv_check);
        close_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置显示位置
        window.setGravity(Gravity.CENTER);
        //设置动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
