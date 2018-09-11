package com.mango.bc.homepage.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.homepage.activity.CollageActivity;
import com.mango.bc.homepage.activity.SearchActivity;
import com.mango.bc.homepage.activity.VipDetailActivity;
import com.mango.bc.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeadFragment extends Fragment {
    /*    @Bind(R.id.imageView_vip)
        ImageView imageViewVip;*/
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.imageView_collage)
    ImageView imageViewCollage;
    @Bind(R.id.imageView)
    Banner imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        ButterKnife.bind(this, view);
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        initBanner();
        return view;
    }

    private void initBanner() {
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({/*R.id.imageView_vip,*/ R.id.imageView_collage})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
/*            case R.id.imageView_vip:
                intent = new Intent(getContext(), VipDetailActivity.class);
                startActivity(intent);
                break;*/
            case R.id.imageView_collage:
                intent = new Intent(getContext(), CollageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
