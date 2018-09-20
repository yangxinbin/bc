package com.mango.bc.wallet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.util.ACache;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.RefreshTaskBean;
import com.mango.bc.wallet.bean.TaskAndRewardBean;
import com.mango.bc.wallet.walletjsonutil.WalletJsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/5.
 */

public class DailyTasksFragment extends Fragment {
    @Bind(R.id.tv_like_num)
    TextView tvLikeNum;
    @Bind(R.id.tv_like_rate)
    TextView tvLikeRate;
    @Bind(R.id.tv_share_num)
    TextView tvShareNum;
    @Bind(R.id.tv_share_rate)
    TextView tvShareRate;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.tv_comment_rate)
    TextView tvCommentRate;
    @Bind(R.id.tv_online_num)
    TextView tvOnlineNum;
    @Bind(R.id.tv_online_rate)
    TextView tvOnlineRate;
    @Bind(R.id.tv_group_num)
    TextView tvGroupNum;
    @Bind(R.id.tv_group)
    TextView tvGroup;
    @Bind(R.id.tv_paid_num)
    TextView tvPaidNum;
    @Bind(R.id.tv_paid)
    TextView tvPaid;
    @Bind(R.id.tv_member_num)
    TextView tvMemberNum;
    @Bind(R.id.tv_member)
    TextView tvMember;
    @Bind(R.id.tv_vip_num)
    TextView tvVipNum;
    @Bind(R.id.tv_vip)
    TextView tvVip;
    private SPUtils spUtilsAuthToken;
    private ACache mCache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_tasks, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        spUtilsAuthToken = SPUtils.getInstance("authToken", getActivity());
        mCache = ACache.get(getActivity());
        if (NetUtil.isNetConnect(getActivity())) {
            loadReward(false);
        } else {
            loadReward(true);
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshTaskEventBus(RefreshTaskBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getIfTaskRefresh()) {
            if (NetUtil.isNetConnect(getActivity())) {
                Log.v("ttttttttt", "-----ttt");
                loadReward(false);
            } else {
                loadReward(true);
            }
        } else {
            loadReward(true);
        }
    }

    private void loadReward(final Boolean ifCache) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtilsAuthToken.getString("authToken", ""));
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("task");
                    if (newString != null) {
                        final List<TaskAndRewardBean> taskAndRewardBeans = WalletJsonUtils.readTaskAndRewardBean(newString);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRewardView(taskAndRewardBeans);
                            }
                        });
                        return;
                    }
                } else {
                    mCache.remove("task");//刷新之后缓存也更新过来
                }
                HttpUtils.doPost(Urls.HOST_TASKS, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            mCache.put("task");
                            final List<TaskAndRewardBean> taskAndRewardBeans = WalletJsonUtils.readTaskAndRewardBean(string);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initRewardView(taskAndRewardBeans);
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
            }
        }).start();
    }

    private void initRewardView(List<TaskAndRewardBean> taskAndRewardBeans) {
        if (taskAndRewardBeans == null)
            return;
        tvLikeRate.setText("进度" + taskAndRewardBeans.get(0).getCount() + "/10");
        tvLikeNum.setText("已获得" + taskAndRewardBeans.get(0).getEarning() + "积分");

        tvShareRate.setText("进度" + taskAndRewardBeans.get(1).getCount() + "/10");
        tvShareNum.setText("已获得" + taskAndRewardBeans.get(1).getEarning() + "积分");

        tvCommentRate.setText("进度" + taskAndRewardBeans.get(2).getCount() + "/10");
        tvCommentNum.setText("已获得" + taskAndRewardBeans.get(2).getEarning() + "积分");

        tvOnlineRate.setText("已阅读" + taskAndRewardBeans.get(3).getCount() + "分钟");
        tvOnlineNum.setText("已获得" + taskAndRewardBeans.get(3).getEarning() + "积分");

        tvGroupNum.setText("已获得" + taskAndRewardBeans.get(4).getEarning() + "积分");

        tvPaidNum.setText("已获得" + taskAndRewardBeans.get(5).getEarning() + "积分");

        tvMemberNum.setText("已获得" + taskAndRewardBeans.get(6).getEarning() + "积分");

        tvVipNum.setText("已获得" + taskAndRewardBeans.get(7).getEarning() + "积分");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_group, R.id.tv_paid, R.id.tv_member, R.id.tv_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_group:
                break;
            case R.id.tv_paid:
                break;
            case R.id.tv_member:
                break;
            case R.id.tv_vip:
                break;
        }
    }
}