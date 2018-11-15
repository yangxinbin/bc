package com.mango.bc.homepage.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mango.bc.R;
import com.mango.bc.homepage.activity.CollageActivity;
import com.mango.bc.homepage.activity.SearchActivity;
import com.mango.bc.homepage.activity.VipDetailActivity;
import com.mango.bc.homepage.bean.BannerBean;
import com.mango.bc.homepage.bookdetail.ExpertBookDetailActivity;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.GlideImageLoader;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.Urls;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

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

public class HeadFragment extends Fragment {
    @Bind(R.id.et_search)
    EditText etSearch;
    /*   @Bind(R.id.imageView_collage)
       ImageView imageViewCollage;
       @Bind(R.id.imageView_novip)
       ImageView imageViewNovip;
       @Bind(R.id.imageVie_author)
       CircleImageView imageVieAuthor;
       @Bind(R.id.tv_vipName)
       TextView tvVipName;
       @Bind(R.id.img_vip)
       ImageView imgVip;
       @Bind(R.id.img_agency)
       ImageView imgAgency;
       @Bind(R.id.tv_vipTime)
       TextView tvVipTime;
       @Bind(R.id.buy_vip)
       TextView buyVip;
       @Bind(R.id.tv_bookReadDay)
       TextView tvBookReadDay;
       @Bind(R.id.tv_vipTip)
       TextView tvVipTip;
       @Bind(R.id.tv_vipContent1)
       TextView tvVipContent1;
       @Bind(R.id.tv_vipContent2)
       TextView tvVipContent2;
       @Bind(R.id.l_vip)
       LinearLayout lVip;
       private SPUtils spUtils;*/
    @Bind(R.id.imageView)
    Banner imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        //spUtils = SPUtils.getInstance("bc", getActivity());
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        etSearch.setFocusable(false);
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        //AppUtils.hideInput(getActivity());
        //init();
        //initBanner();
        //initView(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));
        return view;
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.doGet(Urls.HOST_BANNER, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        final String string;
                        try {
                            if (getActivity() != null) {
                                string = response.body().string();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<BannerBean> bannerBeanList = JsonUtil.readBannerBean(string);
                                        initBanner(bannerBeanList);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshStageBeanEventBus(RefreshStageBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getBanner()) {
            init();
            bean.setBanner(false);//刷新完修改状态
            EventBus.getDefault().postSticky(bean);
        } else {
            //bookPresenter.visitBooks(getActivity(), TYPE, "", page, true);//缓存。
        }
    }

    /*    private void initView(UserBean auth) {
            if (auth == null)
                return;
            if (auth.isVip()) {
                lVip.setVisibility(View.VISIBLE);
                imgVip.setVisibility(View.VISIBLE);
                imageViewNovip.setVisibility(View.GONE);
            } else {
                lVip.setVisibility(View.GONE);
                imgVip.setVisibility(View.GONE);
                imageViewNovip.setVisibility(View.VISIBLE);
            }
            tvVipName.setText(auth.getAlias());
            if (auth.getAvator() != null)
                Glide.with(getActivity()).load(Urls.HOST_GETFILE + "?name=" + auth.getAvator().getFileName()).into(imageVieAuthor);
            if (auth.getBilling() != null)
                tvVipTime.setText("至" + DateUtil.getDateToString(auth.getBilling().getEndOn(), "yyyy-MM-dd"));

        }

        @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
        public void UserBeanEventBus(UserBean userBean) {//用于实时更新
            if (userBean == null)
                return;
            initView(userBean);
        }*/
    private void initBanner(final List<BannerBean> bannerBeanList) {
        if (bannerBeanList == null)
            return;
        List<String> pathsImage = new ArrayList<>();
        List<String> pathsTitle = new ArrayList<>();
        for (int i = 0; i < bannerBeanList.size(); i++) {
            if (bannerBeanList.get(i).getImage() != null) {
                pathsImage.add(Urls.HOST_GETFILE + "?name=" + bannerBeanList.get(i).getImage().getFileName());
                pathsTitle.add("");
            }
        }
/*        pathsImage.add(getResourcesUri(R.drawable.banner));
        pathsImage.add(getResourcesUri(R.drawable.banner));
        pathsTitle.add("");
        pathsTitle.add("");*/
        imageView.setImageLoader(new GlideImageLoader())
                .setBannerTitles(pathsTitle)
                .setImages(pathsImage)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setDelayTime(5000)
                // .setOnBannerClickListener(this)
                .start()
                .setBannerAnimation(Transformer.Stack);
        imageView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = null;
                if ("vip".equals(bannerBeanList.get(position - 1).getUrl())) {
                    intent = new Intent(getContext(), VipDetailActivity.class);
                } /*else if ("agency".equals(bannerBeanList.get(position-1).getUrl())) {
                    intent = new Intent(getContext(), VipDetailActivity.class);
                }*/ else {
                    intent = new Intent(getActivity(), ExpertBookDetailActivity.class);
                    intent.putExtra("bannerBookId", bannerBeanList.get(position - 1).getUrl());
                }
                startActivity(intent);
            }
        });
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({/*R.id.imageView_novip, R.id.buy_vip, R.id.l_vip,*/ R.id.imageView_collage})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
/*            case R.id.imageView_novip:
                intent = new Intent(getContext(), VipDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.buy_vip:
                break;
            case R.id.l_vip:
                //intent = new Intent(getContext(), VipAutoActivity.class);//管理自动续费
                intent = new Intent(getContext(), VipDetailActivity.class);//暂时
                startActivity(intent);
                break;*/
            case R.id.imageView_collage:
                intent = new Intent(getContext(), CollageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
