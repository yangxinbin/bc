package com.mango.bc.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.homepage.activity.CollageActivity;
import com.mango.bc.homepage.activity.SearchActivity;
import com.mango.bc.homepage.activity.VipAutoActivity;
import com.mango.bc.homepage.activity.VipDetailActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.AppUtils;
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

public class HeadFragment extends Fragment {
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.imageView_collage)
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
    private SPUtils spUtils;
/*    @Bind(R.id.imageView)
    Banner imageView;*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
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
        AppUtils.hideInput(getActivity());
        //initBanner();
        initView(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));
        return view;
    }

    private void initView(UserBean auth) {
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
    }
/*    private void initBanner() {
        List<String> pathsImage = new ArrayList<>();
        List<String> pathsTitle = new ArrayList<>();
        pathsImage.add(getResourcesUri(R.drawable.banner));
        pathsImage.add(getResourcesUri(R.drawable.banner));
        pathsTitle.add("");
        pathsTitle.add("");
        imageView.setImages(pathsImage)
                .setBannerTitles(pathsTitle)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                // .setOnBannerClickListener(this)
                .start();
        imageView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getContext(), VipDetailActivity.class);
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
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.imageView_novip, R.id.buy_vip, R.id.l_vip, R.id.imageView_collage})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_novip:
                intent = new Intent(getContext(), VipDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.buy_vip:
                break;
            case R.id.l_vip:
                //intent = new Intent(getContext(), VipAutoActivity.class);//管理自动续费
                intent = new Intent(getContext(), VipDetailActivity.class);//暂时
                startActivity(intent);
                break;
            case R.id.imageView_collage:
                intent = new Intent(getContext(), CollageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
