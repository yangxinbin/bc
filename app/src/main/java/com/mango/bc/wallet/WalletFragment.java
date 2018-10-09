package com.mango.bc.wallet;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.DensityUtil;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.activity.CurrencyActivity;
import com.mango.bc.wallet.activity.RechargeActivity;
import com.mango.bc.wallet.activity.TransactionActivity;
import com.mango.bc.wallet.activity.TransferActivity;
import com.mango.bc.wallet.bean.CheckBean;
import com.mango.bc.wallet.bean.CheckInBean;
import com.mango.bc.wallet.bean.RefreshTaskBean;
import com.mango.bc.wallet.fragment.AlreadyObtainedFragment;
import com.mango.bc.wallet.fragment.DailyTasksFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.victoralbertos.breadcumbs_view.BreadcrumbsView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/3.
 */

public class WalletFragment extends Fragment {
    @Bind(R.id.tv_transaction_record)
    TextView tvTransactionRecord;
    @Bind(R.id.tv_all_pp)
    TextView tvAllPp;
    @Bind(R.id.tv_copy)
    TextView tvCopy;
    @Bind(R.id.l_recharge)
    LinearLayout lRecharge;
    @Bind(R.id.l_transfer)
    LinearLayout lTransfer;
    @Bind(R.id.l_currency)
    LinearLayout lCurrency;
    @Bind(R.id.tv_sign_day)
    TextView tvSignDay;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.tabLayout_wallet)
    TabLayout tabLayout;
    @Bind(R.id.viewPager_wallet)
    ViewPager viewPager;
    @Bind(R.id.tv_walletadress)
    TextView tvWalletadress;
    @Bind(R.id.tv_1)
    TextView tv1;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.tv_3)
    TextView tv3;
    @Bind(R.id.tv_4)
    TextView tv4;
    @Bind(R.id.tv_5)
    TextView tv5;
    @Bind(R.id.tv_6)
    TextView tv6;
    @Bind(R.id.tv_7)
    TextView tv7;
    @Bind(R.id.breadcrumbs)
    BreadcrumbsView breadcrumbs;
    @Bind(R.id.tv_d1)
    TextView tvD1;
    @Bind(R.id.tv_d2)
    TextView tvD2;
    @Bind(R.id.tv_d3)
    TextView tvD3;
    @Bind(R.id.tv_d4)
    TextView tvD4;
    @Bind(R.id.tv_d5)
    TextView tvD5;
    @Bind(R.id.tv_d6)
    TextView tvD6;
    @Bind(R.id.tv_d7)
    TextView tvD7;
    @Bind(R.id.breadcrumbs_fork)
    BreadcrumbsView breadcrumbsFork;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();
    private SPUtils spUtils;
    private boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wallet, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initDatas();
        init();
        initAuth(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));
        initChechIf(JsonUtil.readCheckInBean(spUtils.getString("checkIf", "")));
        return view;
    }

/*    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void CheckEventBus(CheckInBean checkInBean) {
        if (checkInBean == null)
            return;
        initChechIf(checkInBean*//*JsonUtil.readCheckInBean(spUtils.getString("checkIf", ""))*//*);
        //EventBus.getDefault().removeStickyEvent(CheckInBean.class);
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void UserBeanEventBus(UserBean userBean) {
        if (userBean == null)
            return;
        initAuth(userBean);
    }

    private void initAuth(UserBean userBean) {
        if (userBean == null)
            return;
        Log.v("cccccccccc", "-----R--1--" + spUtils.getString("auth", ""));
        if (userBean.getWallet() != null) {
            tvAllPp.setText(userBean.getWallet().getPpCoins() + "");
            tvWalletadress.setText(userBean.getWallet().getWalletAddress());
        }
    }

    private void initDatas() {
        //  mDatas = new ArrayList<String>(Arrays.asList("       我的事件       ", "       全部事件       "));
        mDatas = new ArrayList<String>(Arrays.asList("每日任务", "已获得"));

    }

    @SuppressLint("NewApi")
    private void init() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getFragmentManager(), mfragments, mDatas);
        tabLayout.setupWithViewPager(viewPager);
        mfragments.add(new DailyTasksFragment());
        mfragments.add(new AlreadyObtainedFragment());
        viewPager.setAdapter(vp);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
                switch (position) {
                    case 0:
                        linearParams.height = DensityUtil.dip2px(getActivity(), 810);// 当控件的高强制设成50象素
                        break;
                    case 1:
                        linearParams.height = DensityUtil.dip2px(getActivity(), 730);// 当控件的高强制设成50象素
                        break;
                }
                viewPager.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        reflex(tabLayout);
    }

    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp20 = DensityUtil.dip2px(tabLayout.getContext(), 20);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp20;
                        params.rightMargin = dp20;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        //EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_transaction_record, R.id.tv_copy, R.id.l_recharge, R.id.l_transfer, R.id.l_currency, R.id.tv_sign,/* R.id.l_to_bc, R.id.l_1, R.id.l_2, R.id.l_3, R.id.l_4, R.id.l_5, R.id.l_service*/})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_transaction_record:
                intent = new Intent(getActivity(), TransactionActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_copy:
                //添加到剪切板
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                /**之前的应用过期的方法，clipboardManager.setText(copy);*/
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, tvWalletadress.getText().toString()));
                if (clipboardManager.hasPrimaryClip()) {
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();
                }
                AppUtils.showToast(getActivity(), "复制成功");
                break;
            case R.id.l_recharge:
                intent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.l_transfer:
                intent = new Intent(getActivity(), TransferActivity.class);
                startActivity(intent);
                break;
            case R.id.l_currency:
                intent = new Intent(getActivity(), CurrencyActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_sign:
                checkIn();
                break;
        }
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
                        mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            CheckBean checkBean = JsonUtil.readCheckBean(string);
                            //spUtils.put("checkIf", string);
                            Message msg = mHandler.obtainMessage();
                            msg.obj = checkBean;
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

    private MyHandler mHandler = new MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://签到失败
                    AppUtils.showToast(getActivity(), "签到失败");
                    break;
                case 1://签到成功
                    CheckBean checkBean = (CheckBean) msg.obj;
                    //EventBus.getDefault().postSticky(checkInBean);//刷新
                    //initChechIf(checkBean);
                    try {
                        EventBus.getDefault().postSticky(new RefreshTaskBean(true));//刷新任务列表
                        AppUtils.showToast(getActivity(), "签到成功");
                        loadUser();
                        initChechfromWallet(checkBean);
                        //ifCheckIn();
                        //tvSign.setEnabled(false);
                    } catch (IndexOutOfBoundsException e) {
                        //签到7天完成
                        //tvSignDay.setText("已签到" + 7 + "天");
                    }
                    break;
                case 2://
/*                    CheckInBean checkInBean = (CheckInBean) msg.obj;
                    if (checkInBean != null)
                        initChechfromWallet(checkInBean);//更新签到表*/
                    break;
                default:
                    break;
            }
        }
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
                            getActivity().runOnUiThread(new Runnable() {
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
/*    private void ifCheckIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                HttpUtils.doPost(Urls.HOST_IFCHECK, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            CheckInBean checkInBean = JsonUtil.readCheckInBean(string);
                            spUtils.put("checkIf", string);
                            Message msg = mHandler.obtainMessage();
                            msg.obj = checkInBean;
                            msg.what = 2;
                            msg.sendToTarget();
                        } catch (Exception e) {
                            //mHandler.sendEmptyMessage(0);
                        }
                    }
                });
            }
        }).start();
    }*/

    private void initChechfromWallet(CheckBean checkBean) {
        if (checkBean.getCount() == 1) {
            breadcrumbsFork.setVisibility(View.VISIBLE);
            breadcrumbs.setVisibility(View.GONE);
        } else {
            breadcrumbsFork.setVisibility(View.GONE);
            breadcrumbs.setVisibility(View.VISIBLE);
            try {
                breadcrumbs.nextStep();
            } catch (IndexOutOfBoundsException e) {
            }
        }

        switch (checkBean.getCount()) {
            case 0:
                break;
            case 1:
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 2:
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 3:
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 4:
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 5:
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;

            case 6:
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 7:
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv7.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD7.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
        }
        tvSignDay.setText("已签到" + checkBean.getCount() + "天");
/*        if (checkBean.isTodayCheckedIn()) {
            tvSign.setText("立即签到");
            tvSign.setEnabled(true);
        } else {*/
        tvSign.setText("已签到");
        tvSign.setEnabled(false);
        //}
    }

    private void initChechIf(CheckInBean checkInBean) {
        if (checkInBean == null)
            return;
        tvSignDay.setText("已签到" + checkInBean.getCount() + "天");
        if (checkInBean.isTodayCheckedIn()) {
            tvSign.setText("立即签到");
            tvSign.setEnabled(true);
        } else {
            tvSign.setText("已签到");
            tvSign.setEnabled(false);
        }
        if (checkInBean.getCount() != 0)
            breadcrumbsFork.setVisibility(View.GONE);

        switch (checkInBean.getCount()) {
            case 0:
                breadcrumbs.setCurrentStep(-1);
                break;
            case 1:
                breadcrumbs.setCurrentStep(0);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 2:
                breadcrumbs.setCurrentStep(1);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 3:
                breadcrumbs.setCurrentStep(2);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 4:
                breadcrumbs.setCurrentStep(3);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 5:
                breadcrumbs.setCurrentStep(4);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;

            case 6:
                breadcrumbs.setCurrentStep(5);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
            case 7:
                breadcrumbs.setCurrentStep(6);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD1.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD2.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD3.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD4.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD5.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD6.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tv7.setTextColor(getActivity().getResources().getColor(R.color.yello));
                tvD7.setTextColor(getActivity().getResources().getColor(R.color.yello));
                break;
        }
    }
}
