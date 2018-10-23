package com.mango.bc.wallet.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.bean.BookDetailBean;
import com.mango.bc.homepage.bookdetail.jsonutil.JsonBookDetailUtils;
import com.mango.bc.util.ACache;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.CheckInBean;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/5.
 */

public class AlreadyObtainedFragment extends Fragment {
    @Bind(R.id.tv_like)
    TextView tvLike;
    @Bind(R.id.tv_like_num)
    TextView tvLikeNum;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.tv_share_num)
    TextView tvShareNum;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.tv_online)
    TextView tvOnline;
    @Bind(R.id.tv_online_num)
    TextView tvOnlineNum;
    @Bind(R.id.tv_group)
    TextView tvGroup;
    @Bind(R.id.tv_group_num)
    TextView tvGroupNum;
    @Bind(R.id.tv_menber)
    TextView tvMenber;
    @Bind(R.id.tv_member_num)
    TextView tvMemberNum;
    @Bind(R.id.tv_paid)
    TextView tvPaid;
    @Bind(R.id.tv_paid_num)
    TextView tvPaidNum;
    @Bind(R.id.tv_sign_num)
    TextView tvSignNum;
    @Bind(R.id.tv_vip_num)
    TextView tvVipNum;
    private SPUtils spUtils;
    private ACache mCache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.already_obtained, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
        mCache = ACache.get(getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
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
                Log.v("ttttttttt", "-----rr");
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
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("reward");
                    if (newString != null) {
                        final List<TaskAndRewardBean> taskAndRewardBeans = WalletJsonUtils.readTaskAndRewardBean(newString);
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initRewardView(taskAndRewardBeans);
                                }
                            });
                        return;
                    }
                } else {
                    mCache.remove("reward");//刷新之后缓存也更新过来
                }
                HttpUtils.doPost(Urls.HOST_REWARDS, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            mCache.put("reward");
                            final List<TaskAndRewardBean> taskAndRewardBeans = WalletJsonUtils.readTaskAndRewardBean(string);
                            if (getActivity() != null)
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
        tvLike.setText("已点赞" + taskAndRewardBeans.get(0).getCount() + "次");
        tvLikeNum.setText(taskAndRewardBeans.get(0).getEarning() + "PPG");

        tvShare.setText("已分享" + taskAndRewardBeans.get(1).getCount() + "次");
        tvShareNum.setText(taskAndRewardBeans.get(1).getEarning() + "PPG");

        tvComment.setText("已评论" + taskAndRewardBeans.get(2).getCount() + "次");
        tvCommentNum.setText(taskAndRewardBeans.get(2).getEarning() + "PPG");

        tvOnline.setText("总共阅读" + taskAndRewardBeans.get(3).getCount() + "分钟");
        tvOnlineNum.setText(taskAndRewardBeans.get(3).getEarning() + "PPG");

        tvGroup.setText("已完成拼团" + taskAndRewardBeans.get(4).getCount() + "次");
        tvGroupNum.setText(taskAndRewardBeans.get(4).getEarning() + "PPG");

        //tvPaid.setText("推荐大咖课");
        tvPaidNum.setText(taskAndRewardBeans.get(5).getEarning() + "PPG");

        tvMemberNum.setText(taskAndRewardBeans.get(6).getEarning() + "PPG");

        tvSignNum.setText(taskAndRewardBeans.get(7).getEarning() + "PPG");

        tvVipNum.setText(taskAndRewardBeans.get(8).getEarning() + "PPG");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}