package com.mango.bc.mine;

import android.content.Intent;
import android.os.Bundle;
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
import com.mango.bc.R;
import com.mango.bc.homepage.activity.CollageActivity;
import com.mango.bc.homepage.activity.OpenUpVipActivity;
import com.mango.bc.mine.activity.ApplyActivity;
import com.mango.bc.mine.activity.FaqActivity;
import com.mango.bc.mine.activity.ServiceActivity;
import com.mango.bc.mine.activity.VipCenterActivity;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private SPUtils spUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.mine, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        //loadUser(false); //从网络拿数据
        initView(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));
        return view;
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
                            UserBean userBean = AuthJsonUtils.readUserBean(newString);
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
                                UserBean userBean = AuthJsonUtils.readUserBean(string);
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
        if (userBean.getAvator() != null){
            Glide.with(getActivity()).load(Urls.HOST_GETFILE + "?name=" + userBean.getAvator().getFileName()).into(imageViePic);
        }else {
            imageViePic.setImageDrawable(getResources().getDrawable(R.drawable.head_pic2));
        }
        tvClass.setText(userBean.getStats().getPaidBooks() + "本");
        tvGet.setText(userBean.getStats().getVipGetBooks() + "本");
        tvTime.setText(userBean.getStats().getTotalDuration() + "小时");
        tvCode.setText(userBean.getStats().getPpCoinEarned() + "积分");
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
        //EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.center_vip, R.id.imageView_to_vip, R.id.l_collage, R.id.imageVie_pic, R.id.l_class, R.id.l_get, R.id.l_time, R.id.l_code,/* R.id.l_to_vip,*/ R.id.l_to_agent, /*R.id.l_to_talent,*/ R.id.l_faq, R.id.l_service, R.id.l_setting,R.id.l_bc})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageVie_pic:
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
                break;
            case R.id.l_service:
                intent = new Intent(getActivity(), ServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.l_setting:
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
        }
    }

}
