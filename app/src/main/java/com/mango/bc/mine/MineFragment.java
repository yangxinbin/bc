package com.mango.bc.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.homepage.activity.CollageActivity;
import com.mango.bc.homepage.activity.OpenUpVipActivity;
import com.mango.bc.mine.activity.ApplyActivity;
import com.mango.bc.mine.activity.BcCardActivity;
import com.mango.bc.mine.activity.ExpertApplyActivity;
import com.mango.bc.mine.activity.ExpertApplyDetailActivity;
import com.mango.bc.mine.activity.FaqActivity;
import com.mango.bc.mine.activity.RefereeActivity;
import com.mango.bc.mine.activity.ServiceActivity;
import com.mango.bc.mine.activity.SettingActivity;
import com.mango.bc.mine.activity.VipCenterActivity;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.mine.bean.RefreshMemberBean;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.CheckInBean;
import com.mango.bc.wallet.bean.RefreshTaskBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/10.
 */

public class MineFragment extends Fragment {
    @Bind(R.id.imageVie_pic)
    CircleImageView imageViePic;
    @Bind(R.id.tv_class)
    TextView tvClass;
    @Bind(R.id.l_class)
    LinearLayout lClass;
    @Bind(R.id.tv_get)
    TextView tvGet;
    @Bind(R.id.l_get)
    LinearLayout lGet;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.l_time)
    LinearLayout lTime;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.l_code)
    LinearLayout lCode;
    /*    @Bind(R.id.l_to_vip)
        LinearLayout lToVip;
        @Bind(R.id.l_to_talent)
        LinearLayout lToTalent;*/
    @Bind(R.id.l_service)
    LinearLayout lService;
    @Bind(R.id.l_setting)
    LinearLayout lSetting;
    @Bind(R.id.imageView_to_vip)
    ImageView imageViewToVip;
    @Bind(R.id.l_collage)
    LinearLayout lCollage;
    @Bind(R.id.tv_authNName)
    TextView tvAuthNName;
    @Bind(R.id.l_to_agent)
    LinearLayout lToAgent;
    @Bind(R.id.l_faq)
    LinearLayout lFaq;
    @Bind(R.id.img_vip)
    ImageView imgVip;
    @Bind(R.id.img_agency)
    ImageView imgAgency;
    @Bind(R.id.vip_time)
    TextView vipTime;
    @Bind(R.id.vip_hasTime)
    TextView vipHasTime;
    @Bind(R.id.center_vip)
    LinearLayout centerVip;
    @Bind(R.id.tv_expert_state)
    TextView tvExpertState;
    @Bind(R.id.l_expert)
    LinearLayout lExpert;
    @Bind(R.id.tv_point_get)
    TextView tvPointGet;
    @Bind(R.id.l_point)
    LinearLayout lPoint;
    @Bind(R.id.tv_finish)
    TextView tvFinish;
    @Bind(R.id.bt_finish)
    TextView btFinish;
    private SPUtils spUtils;
    private int agencyInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.mine, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        //loadUser(false); //从网络拿数据
        initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshMemberBeanEventBus(RefreshMemberBean bean) {
        if (bean == null)
            return;
        if (bean.isRefresh()) {
            if (NetUtil.isNetConnect(getActivity())) {
                Log.v("ttttttttt", "-----ttt");
                initMember();
            } else {
            }
        } else {
            bean.setRefresh(false);
            EventBus.getDefault().postSticky(bean);
        }
        EventBus.getDefault().removeStickyEvent(RefreshMemberBean.class);
    }

    private void initMember() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.doGet(Urls.HOST_MEMBER + "?authToken=" + spUtils.getString("authToken", ""), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            MemberBean memberBean = MineJsonUtils.readMemberBean(string);
                            spUtils.put("member", string);
                            Message msg = mHandler.obtainMessage();
                            msg.obj = memberBean;
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

    private MineFragment.MyHandler mHandler = new MineFragment.MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://达人成员失败
                    AppUtils.showToast(getActivity(), "获取成员失败");
                    break;
                case 1://达人成员成功
                    MemberBean memberBean = (MemberBean) msg.obj;
                    if (memberBean == null)
                        return;
                    if (tvExpertState != null)
                        tvExpertState.setText("已获取贡献值" + memberBean.getTotal() + "积分");
                    break;
            }
        }
    }
    /*
        private void loadUser(final Boolean ifCache) {
            final ACache mCache = ACache.get(getActivity().getApplicationContext());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final HashMap<String, String> mapParams = new HashMap<String, String>();
                    mapParams.clear();
                    mapParams.put("openId", "oXhi94jQkXPovBsqEs0B8QKsbM0A");
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache_auth");
                        if (newString != null) {
                            UserBean userBean = MineJsonUtils.readUserBean(newString);
                            Message msg = mHandler.obtainMessage();
                            msg.obj = userBean;
                            msg.what = 1;
                            msg.sendToTarget();
                            return;
                        }
                    } else {
                        mCache.remove("cache_auth");//刷新之后缓存也更新过来
                    }
                    HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                        @Override

                        public void onFailure(Call call, IOException e) {
                            mHandler.sendEmptyMessage(0);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                mCache.put("cache_auth");
                                UserBean userBean = MineJsonUtils.readUserBean(string);
                                Message msg = mHandler.obtainMessage();
                                msg.obj = userBean;
                                msg.what = 1;
                                msg.sendToTarget();
                            } catch (Exception e) {
                                Log.v("doPostAll", "^^^^^Exception^^^^^" + e);
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
                    case 0:
                        AppUtils.showToast(getActivity(), "获取失败");
                        break;
                    case 1:
                        UserBean userBean = (UserBean) msg.obj;
                        initView(userBean);
                        break;
                    default:
                        break;
                }
            }
        }
    */

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StatsBeanEventBus(StatsBean statsBean) {
        if (statsBean == null)
            return;
        initView(statsBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void UserBeanEventBus(UserBean userBean) {
        if (userBean == null)
            return;
        initView(userBean);
    }

    private void initView(UserBean userBean) {
        if (userBean == null)
            return;
        Log.v("cccccccccc", "-----R--2--" + spUtils.getString("auth", ""));
        if (userBean.isVip()) {
            imgVip.setVisibility(View.VISIBLE);
            centerVip.setVisibility(View.VISIBLE);
            imageViewToVip.setVisibility(View.GONE);
            vipTime.setText("至" + DateUtil.getDateToString(userBean.getBilling().getEndOn(), "yyyy-MM-dd"));
        } else {
            imgVip.setVisibility(View.GONE);
            centerVip.setVisibility(View.GONE);
            imageViewToVip.setVisibility(View.VISIBLE);
        }
        tvAuthNName.setText(userBean.getAlias());
        if (userBean.getAvator() != null) {
            Glide.with(getActivity()).load(Urls.HOST_GETFILE + "?name=" + userBean.getAvator().getFileName()).into(imageViePic);
        } else {
            imageViePic.setImageDrawable(getResources().getDrawable(R.drawable.head_pic2));
        }
        tvClass.setText(userBean.getStats().getPaidBooks() + "本");
        tvGet.setText(userBean.getStats().getVipGetBooks() + "本");
        tvTime.setText(userBean.getStats().getTotalDuration() + "小时");
        tvCode.setText(userBean.getStats().getPpCoinEarned() + "积分");
        //以下是达人节点UI
        if (userBean.getAgencyInfo() == null)
            return;
        agencyInfo = userBean.getAgencyInfo().getStatus();
        switch (agencyInfo) {
            case 0:
                lToAgent.setVisibility(View.VISIBLE);
                lExpert.setVisibility(View.GONE);
                lPoint.setVisibility(View.GONE);
                break;
            case 1://申请中
                lToAgent.setVisibility(View.GONE);
                lExpert.setVisibility(View.VISIBLE);
                lPoint.setVisibility(View.GONE);
                tvExpertState.setText("审核中");
                break;
            case 2://达人申请成功
                lToAgent.setVisibility(View.GONE);
                lExpert.setVisibility(View.VISIBLE);
                lPoint.setVisibility(View.GONE);
                tvExpertState.setText("已获取贡献值900积分");
                imgAgency.setVisibility(View.VISIBLE);
                break;
            case 3://达人申请失败
                lToAgent.setVisibility(View.GONE);
                lExpert.setVisibility(View.VISIBLE);
                lPoint.setVisibility(View.GONE);
                tvExpertState.setText("审核失败");
                break;
        }
    }

    private void initView(StatsBean statsBean) {
        if (statsBean == null)
            return;
        tvClass.setText(statsBean.getPaidBooks() + "本");
        tvGet.setText(statsBean.getVipGetBooks() + "本");
        tvTime.setText(statsBean.getTotalDuration() + "小时");
        tvCode.setText(statsBean.getPpCoinEarned() + "积分");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_referee,R.id.bt_finish, R.id.l_expert, R.id.l_point, R.id.center_vip, R.id.imageView_to_vip, R.id.l_collage, R.id.imageVie_pic, R.id.l_class, R.id.l_get, R.id.l_time, R.id.l_code,/* R.id.l_to_vip,*/ R.id.l_to_agent, /*R.id.l_to_talent,*/ R.id.l_faq, R.id.l_service, R.id.l_setting, R.id.l_bc})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imageVie_pic:
                break;
            case R.id.tv_referee:
                intent = new Intent(getActivity(), RefereeActivity.class);
                startActivity(intent);
                break;
            case R.id.l_class:
                break;
            case R.id.l_get:
                break;
            case R.id.l_time:
                break;
            case R.id.l_code:
                break;
/*            case R.id.l_to_vip:
                break;*/
            case R.id.l_to_agent:
                intent = new Intent(getActivity(), ApplyActivity.class);
                startActivity(intent);
                break;
/*            case R.id.l_to_talent:
                break;*/
            case R.id.l_faq:
                intent = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent);
                break;
            case R.id.l_bc:
                intent = new Intent(getActivity(), BcCardActivity.class);
                startActivity(intent);
                break;
            case R.id.l_service:
                intent = new Intent(getActivity(), ServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.l_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView_to_vip:
                intent = new Intent(getActivity(), OpenUpVipActivity.class);
                startActivity(intent);
                break;
            case R.id.center_vip:
                intent = new Intent(getActivity(), VipCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.l_collage:
                intent = new Intent(getContext(), CollageActivity.class);
                startActivity(intent);
                break;
            case R.id.l_expert:
                if (agencyInfo == 1) {
                    intent = new Intent(getContext(), ExpertApplyActivity.class);
                    intent.putExtra("expert", 1);
                    startActivity(intent);
                } else if (agencyInfo == 2) {
                    //详情
                    intent = new Intent(getContext(), ExpertApplyDetailActivity.class);
                    intent.putExtra("expert", 1);
                    startActivity(intent);
                } else if (agencyInfo == 3) {
                    intent = new Intent(getContext(), ExpertApplyActivity.class);
                    intent.putExtra("expert", 2);
                    startActivity(intent);
                }
                break;
            case R.id.l_point:
                //详情
/*                intent = new Intent(getContext(), CollageActivity.class);
                startActivity(intent);*/
                break;
            case R.id.bt_finish:
                break;
        }
    }

}
