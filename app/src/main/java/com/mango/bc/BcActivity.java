package com.mango.bc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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

import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.bookcase.BookcaseFragment;
import com.mango.bc.bookcase.net.presenter.MyBookPresenter;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.HomePageFragment;
import com.mango.bc.mine.MineFragment;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.view.BottomBar;
import com.mango.bc.wallet.WalletFragment;
import com.mango.bc.wallet.bean.CheckInBean;
import com.mango.bc.wallet.bean.RefreshTaskBean;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

public class BcActivity extends BaseServiceActivity implements MyAllBookView {

    private static Dialog dialog;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.bottom_bar)
    BottomBar bottomBar;
    private SPUtils spUtils;
    private MyBookPresenter myBookPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc);
        myBookPresenter = new MyBookPresenterImpl(this);
        if (NetUtil.isNetConnect(this)) {
            //myBookPresenter.visitBooks(this,3,0,false);//获取书架的所有书
        } else {
            AppUtils.showToast(this, getResources().getString(R.string.check_net));
            //myBookPresenter.visitBooks(this, 3, 0, true);//获取书架的所有书
        }
        spUtils = SPUtils.getInstance("bc", this);
        if (getIntent().getBooleanExtra("wechat",false)){
        }else {
            spUtils.put("openId", "oXhi94jQkXPovBsqEs0B8QKsbM0A");
        }
        //假 普通
        //spUtils.put("openId", "oetTo5r15v1y6B2DNwRuiS22JaFo");
        //spUtils.put("openId", "ttttXhi94jQkXPovBsqEs0B8QKsbM0A");
        ButterKnife.bind(this);
        //进来刷新可以屏蔽
        //loadUser(); //个人信息从网络拿数据
        ifCheckIn();
        initPage();
    }

    private void initPage() {
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

    private void ifCheckIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                HttpUtils.doPost(Urls.HOST_IFCHECK, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            CheckInBean checkInBean = JsonUtil.readCheckInBean(string);
                            spUtils.put("checkIf", string);
                            Message msg = mHandler.obtainMessage();
                            msg.obj = checkInBean;
                            msg.what = 1;
                            msg.sendToTarget();
                        } catch (Exception e) {
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                });
            }
        }).start();
    }

    private void checkIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                HttpUtils.doPost(Urls.HOST_CHECK, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mHandler.sendEmptyMessage(4);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            mHandler.sendEmptyMessage(5);
                        } catch (Exception e) {
                            mHandler.sendEmptyMessage(4);
                        }
                    }
                });
            }
        }).start();
    }

    private void loadUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", spUtils.getString("openId", ""));
                Log.v("qqqqqqqqqqq1111", spUtils.getString("openId", ""));
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mHandler.sendEmptyMessage(2);
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
                                    if (userBean != null){
                                        spUtils.put("authToken", userBean.getAuthToken());
                                        ifCheckIn();
                                        Log.v("llll1lllll", "=aaaa==" + userBean.isVip());
                                        EventBus.getDefault().postSticky(userBean);//刷新
                                    }
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

    private void showCheckInWindow(Context context, CheckInBean checkInBean) {
        View view = LayoutInflater.from(context).inflate(R.layout.check_in, null);
        //此处可按需求为各控件设置属性
        ImageView close_check_in = view.findViewById(R.id.close_check_in);
        TextView tv_check = view.findViewById(R.id.tv_check);
        TextView tv_check_day = view.findViewById(R.id.tv_check_day);
        TextView tv_check_num = view.findViewById(R.id.tv_check_num);
        close_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIn();
                dialog.dismiss();
            }
        });
        if (checkInBean == null)
            return;
        if (checkInBean.getCount() == 7) {
            tv_check.setText("签到+5币");
        } else {
            tv_check.setText("签到+1币");
        }
        tv_check_num.setText(checkInBean.getCount() + "");
        tv_check_day.setText("已签到" + checkInBean.getCount() + "天");

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


    private BcActivity.MyHandler mHandler = new BcActivity.MyHandler();

    @Override
    public void addSuccess(String s) {

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://检查签到失败
                    break;
                case 1://检查签到成功
                    CheckInBean checkInBean = (CheckInBean) msg.obj;
                    if (checkInBean == null)
                        return;
                    if (checkInBean.isTodayCheckedIn()) {
                        showCheckInWindow(BcActivity.this, checkInBean);
                    }/* else {
                        EventBus.getDefault().postSticky(checkInBean);
                    }*/
                    break;
                case 2://获取信息失败

                    break;
                case 3://获取信息成功
                    break;
                case 4://签到失败
                    AppUtils.showToast(getBaseContext(), "签到失败");
                    break;
                case 5://签到成功
                    AppUtils.showToast(getBaseContext(), "签到成功");
                    EventBus.getDefault().postSticky(new RefreshTaskBean(true));//刷新任务列表
                    ifCheckIn();
                    loadUser();
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false; //返回交给fragment
        }
        return super.onKeyDown(keyCode, event);
    }
}
