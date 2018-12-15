package com.mango.bc.wallet.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.activity.competitivebook.CompetitiveBookActivity;
import com.mango.bc.homepage.activity.expertbook.ExpertBookActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.ACache;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.RefreshTaskBean;
import com.mango.bc.wallet.bean.TaskAndRewardBean;
import com.mango.bc.wallet.walletjsonutil.WalletJsonUtils;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
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
    @Bind(R.id.tv_get_like)
    TextView tvGetLike;
    @Bind(R.id.tv_get_share)
    TextView tvGetShare;
    @Bind(R.id.tv_get_comment)
    TextView tvGetComment;
    @Bind(R.id.tv_get_online)
    TextView tvGetOnline;
    @Bind(R.id.tv_get_group)
    TextView tvGetGroup;
    @Bind(R.id.tv_get_paid)
    TextView tvGetPaid;
    @Bind(R.id.tv_get_member)
    TextView tvGetMember;
    @Bind(R.id.tv_get_vip)
    TextView tvGetVip;
    private SPUtils spUtils;
    private ACache mCache;
    private String userId;
    private String alias;
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_tasks, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        spUtils = SPUtils.getInstance("bc", getActivity());
        mCache = ACache.get(getActivity());
        if (MineJsonUtils.readUserBean(spUtils.getString("auth", "")) != null) {
            userId = MineJsonUtils.readUserBean(spUtils.getString("auth", "")).getId();
            alias = MineJsonUtils.readUserBean(spUtils.getString("auth", "")).getAlias();
            type = MineJsonUtils.readUserBean(spUtils.getString("auth", "")).getType();
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void UserBeanEventBus(UserBean userBean) {
        if (userBean == null)
            return;
        type = userBean.getType();
    }

    private void loadReward(final Boolean ifCache) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                if (spUtils != null && spUtils.getString("authToken", "") != null)
                    mapParams.put("authToken", spUtils.getString("authToken", ""));
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("task");
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
                    if (mCache != null)
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
        tvLikeRate.setText("进度" + taskAndRewardBeans.get(0).getCount() + "/10");

        tvLikeNum.setText("已获得" + taskAndRewardBeans.get(0).getEarning() + "PPG");

        tvShareRate.setText("进度" + taskAndRewardBeans.get(1).getCount() + "/10");
        tvShareNum.setText("已获得" + taskAndRewardBeans.get(1).getEarning() + "PPG");

        tvCommentRate.setText("进度" + taskAndRewardBeans.get(2).getCount() + "/10");
        tvCommentNum.setText("已获得" + taskAndRewardBeans.get(2).getEarning() + "PPG");

        tvOnlineRate.setText("已阅读" + taskAndRewardBeans.get(3).getCount() + "分钟");
        tvOnlineNum.setText("已获得" + taskAndRewardBeans.get(3).getEarning() + "PPG");

        tvGroupNum.setText("已获得" + taskAndRewardBeans.get(4).getEarning() + "PPG");

        tvPaidNum.setText("已获得" + taskAndRewardBeans.get(5).getEarning() + "PPG");

        tvMemberNum.setText("已获得" + taskAndRewardBeans.get(6).getEarning() + "PPG");

        tvVipNum.setText("已获得" + taskAndRewardBeans.get(7).getEarning() + "PPG");
        if (!("general".equals(type))) {
            tvGetLike.setText("限10次，0.3PPG/次");
            tvGetShare.setText("限10次，0.3PPG/次");
            tvGetComment.setText("限10次，0.3PPG/次");
            tvGetOnline.setText("10分钟0.6PPG，限50分钟");
            tvGetGroup.setText("20%收益");
            tvGetPaid.setText("15%收益");
            tvGetMember.setText("20%收益");
            tvGetVip.setText("10%收益");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_group, R.id.tv_paid, R.id.tv_member, R.id.tv_vip})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_group:
                intent = new Intent(getActivity(), ExpertBookActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_paid:
                intent = new Intent(getActivity(), ExpertBookActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_member:
                intent = new Intent(getActivity(), CompetitiveBookActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_vip:
/*                intent = new Intent(getActivity(), OpenUpVipActivity.class);
                startActivity(intent);*/
                //邀请
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        oks.setTitle(alias + "邀请你加入BC大陆VIP");
        oks.setText("BC大陆");
        oks.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        oks.setUrl("http://www.mob.com");
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (platform.getName().equals("Wechat")) {
                    paramsToShare.setShareType(Platform.SHARE_WXMINIPROGRAM);
                    paramsToShare.setWxMiniProgramType(WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE);
                    paramsToShare.setWxUserName("gh_482031325125");
                    paramsToShare.setWxPath("pages/becomeVip/becomeVip?model=" + "\"" + userId + "\"");
                }
            }
        });
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });
        oks.show(getActivity());
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }
}