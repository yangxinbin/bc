package com.mango.bc.homepage.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.SingleAdapter;
import com.mango.bc.homepage.bean.VipPackageBean;
import com.mango.bc.homepage.bean.VipType;
import com.mango.bc.mine.activity.VipCenterActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.ACache;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.activity.RechargeActivity;
import com.mango.bc.wallet.activity.TransferActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OpenUpVipActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_buy_detail)
    TextView tvBuyDetail;
    @Bind(R.id.buy_vip)
    Button buyVip;
    private ArrayList<VipType> datas;
    private Calendar calendar;
    private Date da;
    private SingleAdapter adapter;
    private String sVipPackageId;
    private String sAutoBilling;
    private SPUtils spUtils;
    private Double ppg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_up_vip);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        if (NetUtil.isNetConnect(this)) {
            intVipPackage(false);
        } else {
            intVipPackage(true);
        }
    }

    private void intVipPackage(final Boolean ifCache) {
        final ACache mCache = ACache.get(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("vipPackage");
                    Log.v("yyyyyy", "---cache---");
                    if (newString != null) {
                        List<VipPackageBean> vipPackageBeans = JsonUtil.readVipPackageBean(newString);
                        sAutoBilling = "true";
                        if (vipPackageBeans.get(0) != null)
                            sVipPackageId = vipPackageBeans.get(0).getId();
                        initData(vipPackageBeans);
                        return;
                    }
                } else {
                    mCache.remove("vipPackage");//刷新之后缓存也更新过来
                }
                HttpUtils.doGet(Urls.HOST_VIPPACKAGE, /*mapParams,*/ new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        try {
                            final String string1 = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCache.put("vipPackage", string1);
                                    List<VipPackageBean> vipPackageBeans = JsonUtil.readVipPackageBean(string1);
                                    sAutoBilling = "true";
                                    if (vipPackageBeans.get(0) != null)
                                        sVipPackageId = vipPackageBeans.get(0).getId();
                                    initData(vipPackageBeans);
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

    private void intView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SingleAdapter(datas);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new SingleAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                initDetail(adapter.getItem(position).getType());
                sVipPackageId = adapter.getItem(position).getVipPackageId();
                sAutoBilling = adapter.getItem(position).getAutoBilling();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initDetail(String s) {
        calendar = Calendar.getInstance();
        UserBean userBean = AuthJsonUtils.readUserBean(spUtils.getString("auth", ""));
        Log.v("vvvv", "====" + userBean.getBilling().getEndOn());
        if (userBean != null) {
            if (userBean.getBilling() != null) {
                if (userBean.getBilling().getEndOn() == 0) {
                    //获取当前时间
                    da = new Date();
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String d = format.format(userBean.getBilling().getEndOn());
                    try {
                        da = format.parse(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        calendar.setTime(da);//把当前时间赋给日历
        long timePast = 0;
        if (s.equals("monthly")) {//月
            calendar.add(Calendar.MONTH, 1);
            timePast = calendar.getTimeInMillis();
            tvBuyDetail.setText("购买后，VIP到期时间将延迟至" + DateUtil.getDateToString(timePast, "yyyy年MM月dd日"));
        } else {//年
            calendar.add(Calendar.YEAR, 1);
            timePast = calendar.getTimeInMillis();
            tvBuyDetail.setText("购买后，VIP到期时间将延迟至" + DateUtil.getDateToString(timePast, "yyyy年MM月dd日"));
        }
    }

    private void initData(List<VipPackageBean> vipPackageBeans) {
        if (vipPackageBeans == null)
            return;
        datas = new ArrayList<>();
        if (vipPackageBeans.get(0) != null) {
            String s11 = vipPackageBeans.get(0).getAutoBillingFee() + "";
            String new11 = s11;
            if (s11.endsWith(".0")) {
                new11 = s11.substring(0, s11.length() - 2);
            }
            String s12 = vipPackageBeans.get(0).getFirstTimeFee() + "";
            String new12 = s12;
            if (s12.endsWith(".0")) {
                new12 = s12.substring(0, s12.length() - 2);
            }
            datas.add(new VipType("连续包月VIP", "首月特价", "每月仅需" + new11 + "积分，自动续费可随时取消", "", new12 + "积分", vipPackageBeans.get(0).getBillingType(), vipPackageBeans.get(0).getId(), "true"));
        }
        if (vipPackageBeans.get(1) != null) {
            String s21 = vipPackageBeans.get(1).getAutoBillingFee() + "";
            String new21 = s21;
            if (s21.endsWith(".0")) {
                new21 = s21.substring(0, s21.length() - 2);
            }
            String s22 = vipPackageBeans.get(1).getFirstTimeFee() + "";
            String new22 = s22;
            if (s22.endsWith(".0")) {
                new22 = s22.substring(0, s22.length() - 2);
            }
            datas.add(new VipType("连续包年VIP", "八折优惠", "每年仅需" + new21 + "积分，自动续费可随时取消", "", new22 + "积分", vipPackageBeans.get(1).getBillingType(), vipPackageBeans.get(1).getId(), "true"));
        }
        if (vipPackageBeans.get(0) != null) {
            String s13 = vipPackageBeans.get(0).getManualBillingFee() + "";
            String new13 = s13;
            if (s13.endsWith(".0")) {
                new13 = s13.substring(0, s13.length() - 2);
            }
            datas.add(new VipType("包月VIP", "", "", "", new13 + "积分", vipPackageBeans.get(0).getBillingType(), vipPackageBeans.get(0).getId(), "false"));

        }
        if (vipPackageBeans.get(1) != null) {
            String s23 = vipPackageBeans.get(1).getManualBillingFee() + "";
            String new23 = s23;
            if (s23.endsWith(".0")) {
                new23 = s23.substring(0, s23.length() - 2);
            }
            datas.add(new VipType("包年VIP", "", "", "", new23 + "积分", vipPackageBeans.get(1).getBillingType(), vipPackageBeans.get(1).getId(), "false"));
        }
        intView();
        adapter.setSelection(0);
        initDetail("monthly");
    }


    @OnClick({R.id.imageView_back, R.id.buy_vip})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (getIntent().getBooleanExtra("center", false)) {
                    intent = new Intent(this, VipCenterActivity.class);
                } else {
                    intent = new Intent(this, VipDetailActivity.class);
                }
                startActivity(intent);
                finish();
                break;
            case R.id.buy_vip:
                showDailog("是否确认支付", "");
                break;
        }
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
                        becomeVip();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void showDailogOpen(String s1, final String s2) {
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
                        Intent intent = new Intent(OpenUpVipActivity.this, RechargeActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void becomeVip() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("autoBilling", sAutoBilling);
                mapParams.put("vipPackageId", sVipPackageId);
                HttpUtils.doPost(Urls.HOST_BUYVIP, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(OpenUpVipActivity.this, "购买失败");
                                //showDailogOpen("请检查网络连接", "");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        final String s;
                        try {
                            s = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (s.equals("LOW_BALANCE")) {
                                        showDailogOpen(getString(R.string.less_ppg), "");
                                    } else {
                                        AppUtils.showToast(OpenUpVipActivity.this, "购买成功");
                                        loadUser();
                                        finish();
                                    }

                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(OpenUpVipActivity.this, "购买失败");
                                }
                            });

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
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
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
                                    UserBean userBean = AuthJsonUtils.readUserBean(string);
                                    Log.v("lllllllll", "=aaaa==" + userBean.isVip());
                                    EventBus.getDefault().postSticky(userBean);//刷新钱包，我的。
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (getIntent().getBooleanExtra("center", false)) {
                intent = new Intent(this, VipCenterActivity.class);
            } else {
                intent = new Intent(this, VipDetailActivity.class);
            }
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
