package com.mango.bc.homepage.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.collage.fragment.CollageAllFragment;
import com.mango.bc.homepage.collage.fragment.CollageFailFragment;
import com.mango.bc.homepage.collage.fragment.CollageIngFragment;
import com.mango.bc.homepage.collage.fragment.CollageSuccessFragment;
import com.mango.bc.util.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollageActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tabLayout_collage)
    TabLayout tabLayoutCollage;
    @Bind(R.id.viewPager_collage)
    ViewPager viewPagerCollage;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        ButterKnife.bind(this);
        initDatas();
        init();
    }

    private void initDatas() {
        //  mDatas = new ArrayList<String>(Arrays.asList("       我的事件       ", "       全部事件       "));
        mDatas = new ArrayList<String>(Arrays.asList("全部", "拼团中", "已完成", "已失败"));
    }

    @SuppressLint("NewApi")
    private void init() {
        tabLayoutCollage.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getSupportFragmentManager(), mfragments, mDatas);
        tabLayoutCollage.setupWithViewPager(viewPagerCollage);
        mfragments.add(new CollageAllFragment());
        mfragments.add(new CollageIngFragment());
        mfragments.add(new CollageSuccessFragment());
        mfragments.add(new CollageFailFragment());
        viewPagerCollage.setAdapter(vp);
        viewPagerCollage.setCurrentItem(0);
        viewPagerCollage.setOffscreenPageLimit(0);
/*        viewPagerCollage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPagerCollage.getLayoutParams();
                switch (position) {
                    case 0:
                        linearParams.height = DensityUtil.dip2px(CollageActivity.this, 810);// 当控件的高强制设成50象素
                        break;
                    case 1:
                        linearParams.height = DensityUtil.dip2px(CollageActivity.this, 650);// 当控件的高强制设成50象素
                        break;
                }
                viewPagerCollage.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
        reflex(tabLayoutCollage);
    }

    public void reflex(final TabLayout tabLayoutCollage) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayoutCollage.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayoutCollage.getChildAt(0);

                    int dp20 = DensityUtil.dip2px(tabLayoutCollage.getContext(), 20);

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

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        finish();
    }
}
