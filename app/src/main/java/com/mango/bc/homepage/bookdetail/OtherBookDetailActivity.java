package com.mango.bc.homepage.bookdetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.homepage.bookdetail.fragment.CommentFragment;
import com.mango.bc.homepage.bookdetail.fragment.CourseFragment;
import com.mango.bc.homepage.bookdetail.fragment.DetailFragment;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.DensityUtil;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherBookDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_other_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        if (bookBean.getAuthor() != null) {
            if (bookBean.getAuthor().getPhoto() != null){}
                //Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookBean.getAuthor().getPhoto().getFileName()).into(imgCover);
        }
        //tvBuyer.setText(bookBean.getSold()+"");
        //tvCourse.setText(bookBean.getChapters().size()+"");
       // EventBus.getDefault().removeStickyEvent(BookBean.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
