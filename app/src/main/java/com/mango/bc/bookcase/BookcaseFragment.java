package com.mango.bc.bookcase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.bookcase.bean.RefreshBookCaseBean;
import com.mango.bc.bookcase.fragment.MyCompetitiveFragment;
import com.mango.bc.bookcase.fragment.MyExpertFragment;
import com.mango.bc.bookcase.fragment.MyFreeFragment;
import com.mango.bc.homepage.bookdetail.bean.PlayPauseBean;
import com.mango.bc.homepage.bookdetail.play.BaseServiceFragment;
import com.mango.bc.homepage.bookdetail.play.executor.ControlPanel;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.util.DensityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/3.
 */

public class BookcaseFragment extends BaseServiceFragment {
    /*    @Bind(R.id.imageVie_pic)
        CircleImageView imageVieP;
        @Bind(R.id.tv_class)
        TextView tvClass;
        @Bind(R.id.tv_get)
        TextView tvGet;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.toolbar)
    Toolbar toolbar;*/
    @Bind(R.id.tabLayout_bookcase)
    TabLayout tabLayout;
    @Bind(R.id.viewpager_bookcase)
    ViewPager viewPager;
    @Bind(R.id.fl_play_bar)
    FrameLayout flPlayBar;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();//
    private ControlPanel controlPanel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bookcase, container, false);
        ButterKnife.bind(this, view);
        initDatas();
        init();
        return view;
    }
    @Override
    protected void onServiceBound() {
        controlPanel = new ControlPanel(flPlayBar);
        AudioPlayer.get().addOnPlayEventListener(controlPanel);
        //parseIntent();
    }
    private void initDatas() {
        //  mDatas = new ArrayList<String>(Arrays.asList("       我的事件       ", "       全部事件       "));
        mDatas = new ArrayList<String>(Arrays.asList("大咖课程", "精品课程", "免费课程"));

    }

    private void init() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getFragmentManager(), mfragments, mDatas);
        tabLayout.setupWithViewPager(viewPager);
        mfragments.add(new MyExpertFragment());
        mfragments.add(new MyCompetitiveFragment());
        mfragments.add(new MyFreeFragment());
        viewPager.setAdapter(vp);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        reflex(tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setHasOptionsMenu(true);
    }

    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp20 = DensityUtil.dip2px(tabLayout.getContext(), 20);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp20;
                        params.rightMargin = dp20;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}