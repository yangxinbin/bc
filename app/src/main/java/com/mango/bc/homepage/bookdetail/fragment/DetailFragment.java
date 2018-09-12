package com.mango.bc.homepage.bookdetail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/12.
 */

public class DetailFragment extends Fragment {

    @Bind(R.id.l_img)
    LinearLayout lImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_book, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        if (bookBean.getDescriptionImages() != null) {
            for (int i = 0;i<bookBean.getDescriptionImages().size();i++){
                Log.v("oooooooooooo","====");
                ImageView imageView = new ImageView(getContext());
                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;;
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                imageView.setLayoutParams(lp);
                Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookBean.getDescriptionImages().get(i).getFileName()).into(imageView);
                lImg.addView(imageView,i);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
