package com.mango.bc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.BookcaseFragment;
import com.mango.bc.homepage.HomePageFragment;
import com.mango.bc.mine.MineFragment;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.view.BottomBar;
import com.mango.bc.wallet.WalletFragment;

import java.io.IOException;
import java.util.HashMap;

public class BcActivity extends BaseActivity {

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.bottom_bar)
    BottomBar bottomBar;
    private SPUtils spUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc);
        ButterKnife.bind(this);
        loadUser(false); //个人信息从网络拿数据
        spUtils = SPUtils.getInstance("authToken", this);
        spUtils.put("authToken", "eyJhbGciOiJIUzUxMiJ9.eyJhdWRpZW5jZSI6Im1vYmlsZSIsImNyZWF0ZWQiOjE1MzY5MDk3MjI5MzksImFsaWFzIjoi5p2o6ZGr5paMIiwiaWQiOiI1YjhhM2Q0YjA0NDQwYzBhNDhhMzNhMDUiLCJ0eXBlIjoiZ2VuZXJhbCIsIndhbGxldEFkZHJlc3MiOiIweGU3MmUzODdhZjEyZTA4NmFlZWNjOGVmMTljNzcxY2M4IiwiZXhwIjo0MTI4OTA5NzIyLCJ1c2VybmFtZSI6Im9YaGk5NGpRa1hQb3ZCc3FFczBCOFFLc2JNMEEifQ.m6rVYWnsxxogOAVmOLQ1HEC5bv0YzAwPhqGOlQ0tOP1CVec8XBRytgEFo_0rMlgSW42u2F199y3WAOr8XE2yYA");
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
    private void loadUser(final Boolean ifCache) {
        //final ACache mCache = ACache.get(getActivity().getApplicationContext());
        spUtils = SPUtils.getInstance("auth", this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", "oXhi94jQkXPovBsqEs0B8QKsbM0A");
                if (ifCache) {//读取缓存数据
                    //String newString = spUtils.put("auth", "eyJhbGciOiJIUzUxMiJ9.eyJhdWRpZW5jZSI6Im1vYmlsZSIsImNyZWF0ZWQiOjE1MzY5MDk3MjI5MzksImFsaWFzIjoi5p2o6ZGr5paMIiwiaWQiOiI1YjhhM2Q0YjA0NDQwYzBhNDhhMzNhMDUiLCJ0eXBlIjoiZ2VuZXJhbCIsIndhbGxldEFkZHJlc3MiOiIweGU3MmUzODdhZjEyZTA4NmFlZWNjOGVmMTljNzcxY2M4IiwiZXhwIjo0MTI4OTA5NzIyLCJ1c2VybmFtZSI6Im9YaGk5NGpRa1hQb3ZCc3FFczBCOFFLc2JNMEEifQ.m6rVYWnsxxogOAVmOLQ1HEC5bv0YzAwPhqGOlQ0tOP1CVec8XBRytgEFo_0rMlgSW42u2F199y3WAOr8XE2yYA");
                    //if (newString != null) {

                    //}
                } else {
                    //mCache.remove("cache_auth");//刷新之后缓存也更新过来
                }
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override

                    public void onFailure(Call call, IOException e) {
                       // mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                           /* mCache.put("cache_auth");
                            UserBean userBean = AuthJsonUtils.readUserBean(string);
                            Message msg = mHandler.obtainMessage();
                            msg.obj = userBean;
                            msg.what = 1;
                            msg.sendToTarget();*/
                        } catch (Exception e) {
                            /*Log.v("doPostAll", "^^^^^Exception^^^^^" + e);
                            mHandler.sendEmptyMessage(0);*/
                        }
                    }
                });
            }
        }).start();
    }
}
