package com.mango.bc.mine.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.SingleAdapter;
import com.mango.bc.homepage.bean.VipType;
import com.mango.bc.mine.adapter.SinglePayAdapter;
import com.mango.bc.mine.adapter.SinglePointAdapter;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.PayMes;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PointApplyActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.recyclerView_what)
    RecyclerView recyclerViewWhat;
    @Bind(R.id.tv_after)
    TextView tvAfter;
    @Bind(R.id.tv_before)
    TextView tvBefore;
    @Bind(R.id.pay_ok)
    TextView payOk;
    private ArrayList<VipType> datas;
    private SinglePointAdapter adapter;
    private SinglePayAdapter adapterWhat;
    private double num = 10000;
    private SPUtils spUtils;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = SPUtils.getInstance("bc", this);
        setContentView(R.layout.activity_point_apply);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initDate();
        initView();
        initViewPay();
    }

    private void initDate() {
        datas = new ArrayList<>();
        datas.add(new VipType("10000.00 PPG", "", "1PPG=1元", "", "享9折", "", "", "true"));
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
                .setTitle("恭喜您成为节点！")//设置对话框的标题
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

    private void initView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SinglePointAdapter(datas);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new SinglePointAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                //initDetail(adapter.getItem(position).getType());
                //sVipPackageId = adapter.getItem(position).getVipPackageId();
                //sAutoBilling = adapter.getItem(position).getAutoBilling();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adapter.setSelection(0);
    }

    private void initViewPay() {
        recyclerViewWhat.setHasFixedSize(true);
        recyclerViewWhat.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        adapterWhat = new SinglePayAdapter(this);
        recyclerViewWhat.setAdapter(adapterWhat);

        adapterWhat.setOnItemClickLitener(new SinglePayAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapterWhat.setSelection(position);
                //initDetail(adapter.getItem(position).getType());
                //sVipPackageId = adapter.getItem(position).getVipPackageId();
                //sAutoBilling = adapter.getItem(position).getAutoBilling();
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adapterWhat.setSelection(0);
    }

    @OnClick({R.id.imageView_back, R.id.pay_ok})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (getIntent().getBooleanExtra("expert_fail", false)) {
                    intent = new Intent(this, ExpertApplyActivity.class);
                    intent.putExtra("expert", 2);//跳到申请失败页面
                } else if (getIntent().getBooleanExtra("expert_detail", false)) {
                    intent = new Intent(this, ExpertApplyDetailActivity.class);
                } else {
                    intent = new Intent(this, ApplyActivity.class);
                }
                startActivity(intent);
                finish();
                break;
            case R.id.pay_ok:
                //intent = new Intent(this, PointDetailActivity.class);
                //startActivity(intent);
                //finish();
                //showDailog("稍后更新", "");
                registrationWechatPay(num);
                break;
        }
    }

    private void registrationWechatPay(double dollar) {
        final HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.clear();
        mapParams.put("authToken", spUtils.getString("authToken", ""));
        mapParams.put("dollar", dollar + "");
        mapParams.put("ttype", "become_node");
        mapParams.put("mobileType", "android");
        HttpUtils.doPost(Urls.HOST_WEIXINPAY, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.showToast(getBaseContext(), "购买失败");
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
                                AppUtils.showToast(getBaseContext(), "购买失败");
                            }
                        });
                    }
                } catch (final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("mmmmmmmmmmmm", "=====" + e);
                            AppUtils.showToast(getBaseContext(), "购买失败");
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

    private void showDailog(String s1, final String s2) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(s1)//设置对话框的标题
                //.setMessage(s2)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (getIntent().getBooleanExtra("expert_fail", false)) {
                intent = new Intent(this, ExpertApplyActivity.class);
                intent.putExtra("expert", 2);//跳到申请失败页面
            } else if (getIntent().getBooleanExtra("expert_detail", false)) {
                intent = new Intent(this, ExpertApplyDetailActivity.class);
            } else {
                intent = new Intent(this, ApplyActivity.class);
            }
            startActivity(intent);
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
