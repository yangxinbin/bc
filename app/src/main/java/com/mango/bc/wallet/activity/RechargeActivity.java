package com.mango.bc.wallet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.bookdetail.play.PlayActivity;
import com.mango.bc.login.UserDetailActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.adapter.SingleRechargeAdapter;
import com.mango.bc.wallet.bean.PayMes;
import com.mango.bc.wallet.bean.RechargeType;
import com.mango.bc.wallet.bean.TransactionBean;
import com.mango.bc.wallet.bean.WechatPayBean;
import com.mango.bc.wallet.walletjsonutil.WalletJsonUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RechargeActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.buy_vip)
    Button buyVip;
    private ArrayList<RechargeType> datas;
    private SPUtils spUtils;
    private IWXAPI api;
    private int num = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = SPUtils.getInstance("bc", this);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        intList();
        initData();
        intView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void PayMesEventBus(PayMes payMes) {
        if (payMes == null)
            return;
        if (payMes.isPaySuccess()) {
            loadUser();
            payMes.setPaySuccess(false);
        }
        EventBus.getDefault().removeStickyEvent(PayMes.class);
    }

    private void loadUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", spUtils.getString("openId", ""));
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        try {
                            final String string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spUtils.put("auth", string);//刷新用户信息
                                    UserBean userBean = MineJsonUtils.readUserBean(string);
                                    EventBus.getDefault().postSticky(userBean);//刷新钱包
                                    showDialog();
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

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("充值成功")//设置对话框的标题
                //.setMessage("")//设置对话框的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void intView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        final SingleRechargeAdapter adapter = new SingleRechargeAdapter(datas);
        mRecyclerView.setAdapter(adapter);
        adapter.setSelection(0);
        adapter.setOnItemClickLitener(new SingleRechargeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                switch (position) {
                    case 0:
                        num = 6;
                        break;
                    case 1:
                        num = 38;
                        break;
                    case 2:
                        num = 68;
                        break;
                    case 3:
                        num = 108;
                        break;
                    case 4:
                        num = 208;
                        break;
                    case 5:
                        num = 388;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initData() {
        datas = new ArrayList<>();
        datas.add(new RechargeType("6 PPG", "6元"));
        datas.add(new RechargeType("38 PPG", "38元"));
        datas.add(new RechargeType("68 PPG", "68元"));
        datas.add(new RechargeType("108 PPG", "108元"));
        datas.add(new RechargeType("208 PPG", "208元"));
        datas.add(new RechargeType("388 PPG", "388元"));
    }

    private void intList() {

    }

    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.buy_vip:
                registrationWechatPay(num);
                break;
        }
    }

    private void registrationWechatPay(int dollar) {
        final HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.clear();
        mapParams.put("authToken", spUtils.getString("authToken", ""));
        mapParams.put("dollar", dollar + "");
        mapParams.put("ttype", "topup");
        mapParams.put("mobileType", "android");
        HttpUtils.doPost(Urls.HOST_WEIXINPAY, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.showToast(getBaseContext(), "充值失败");
                        Log.v("mmmmmmmmmmmm", "=====" + e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                final String string;
                try {
                    string = response.body().string();
                    if (String.valueOf(response.code()).startsWith("2")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("mmmmmmmmmmmm", "===s==");
                                WechatPayBean wechatPayBean = WalletJsonUtils.readWechatPayBean(string);
                                wechatPay(wechatPayBean);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getBaseContext(), "充值失败");
                            }
                        });
                    }
                } catch (final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("mmmmmmmmmmmm", "=====" + e);
                            AppUtils.showToast(getBaseContext(), "充值失败");
                        }
                    });
                }
            }
        });
    }

    private void wechatPay(WechatPayBean wechatPayBean) {
        api = WXAPIFactory.createWXAPI(this, "wxb93480bda524daa0");
        if (wechatPayBean == null || wechatPayBean.getPayMap() == null) {
            AppUtils.showToast(this, "服务器请求错误");
            return;
        }
        PayReq req = new PayReq();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        Log.v("mmmmmmmmmmmm", "==appId===" + wechatPayBean.getPayMap().toString());
        req.appId = wechatPayBean.getPayMap().getAppid();//json.getString("appid");
        req.partnerId = wechatPayBean.getPayMap().getPartnerid();//json.getString("partnerid");
        req.prepayId = wechatPayBean.getPayMap().getPrepayid();//json.getString("prepayid");
        req.nonceStr = wechatPayBean.getPayMap().getNoncestr();//json.getString("noncestr");
        req.timeStamp = wechatPayBean.getPayMap().getTimestamp();//json.getString("timestamp");
        req.packageValue = wechatPayBean.getPayMap().getPackageX();//json.getString("package");
        req.sign = wechatPayBean.getPayMap().getSign();//json.getString("sign");
        req.extData = "app data"; // optional
        AppUtils.showToast(this, "正在支付中...");
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
