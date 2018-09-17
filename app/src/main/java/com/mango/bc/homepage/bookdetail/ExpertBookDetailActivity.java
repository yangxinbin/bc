package com.mango.bc.homepage.bookdetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.bookdetail.fragment.CommentFragment;
import com.mango.bc.homepage.bookdetail.fragment.CourseFragment;
import com.mango.bc.homepage.bookdetail.fragment.DetailFragment;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.DensityUtil;
import com.mango.bc.util.Urls;
import com.mango.bc.view.likeview.PraiseView;

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

public class ExpertBookDetailActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.img_cover)
    ImageView imgCover;
    @Bind(R.id.tv_buyer)
    TextView tvBuyer;
    @Bind(R.id.tv_course)
    TextView tvCourse;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.l_01)
    LinearLayout l01;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.l_like)
    PraiseView lLike;
    @Bind(R.id.l_try)
    LinearLayout lTry;
    @Bind(R.id.l_buy)
    TextView lBuy;
    @Bind(R.id.l_collage)
    TextView lCollage;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initDatas();
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        Log.v("uuuuuuuuuuuu", "--0-");
        if (bookBean.getAuthor() != null) {
            if (bookBean.getAuthor().getPhoto() != null)
                Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookBean.getAuthor().getPhoto().getFileName()).into(imgCover);
        }
        tvBuyer.setText(bookBean.getSold() + "");
        tvCourse.setText(bookBean.getChapters().size() + "");
        lBuy.setText(bookBean.getPrice()+"币购买\n"+"会员"+bookBean.getVipPrice()+"币");
        lCollage.setText(bookBean.getGroupBuy2Price()+"-"+bookBean.getGroupBuy3Price()+"币\n拼团购买");
    }

    private void initDatas() {
        //  mDatas = new ArrayList<String>(Arrays.asList("       我的事件       ", "       全部事件       "));
        mDatas = new ArrayList<String>(Arrays.asList("详情", "课程", "评论"));

    }

    private void init() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getSupportFragmentManager(), mfragments, mDatas);
        tabLayout.setupWithViewPager(viewPager);
        mfragments.add(new DetailFragment());
        mfragments.add(new CourseFragment());
        mfragments.add(new CommentFragment());
        viewPager.setAdapter(vp);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        reflex(tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2) {
                    l2.setVisibility(View.VISIBLE);
                    l01.setVisibility(View.GONE);
                } else {
                    l2.setVisibility(View.GONE);
                    l01.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp40 = DensityUtil.dip2px(tabLayout.getContext(), 40);

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
                        params.width = width;
                        params.leftMargin = dp40;
                        params.rightMargin = dp40;
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

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.l_like, R.id.l_try, R.id.l_buy, R.id.l_collage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.l_like:
                break;
            case R.id.l_try:
                break;
            case R.id.l_buy:
                break;
            case R.id.l_collage:
                break;
        }
    }
}
