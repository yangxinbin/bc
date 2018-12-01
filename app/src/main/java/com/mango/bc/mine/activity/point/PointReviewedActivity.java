package com.mango.bc.mine.activity.point;

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
import com.mango.bc.mine.activity.point.fragment.PointFaildFragment;
import com.mango.bc.mine.activity.point.fragment.PointSuccessFragment;
import com.mango.bc.mine.activity.point.fragment.PointingFragment;
import com.mango.bc.util.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointReviewedActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tabLayout_point)
    TabLayout tabLayoutPoint;
    @Bind(R.id.viewPager_point)
    ViewPager viewPagerPoint;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_reviewed);
        ButterKnife.bind(this);
        initDatas();
        init();
    }

    private void initDatas() {
        //  mDatas = new ArrayList<String>(Arrays.asList("       我的事件       ", "       全部事件       "));
        mDatas = new ArrayList<String>(Arrays.asList("待审核", "审核成功", "审核失败"));
    }

    @SuppressLint("NewApi")
    private void init() {
        tabLayoutPoint.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getSupportFragmentManager(), mfragments, mDatas);
        tabLayoutPoint.setupWithViewPager(viewPagerPoint);
        mfragments.add(new PointingFragment());
        mfragments.add(new PointSuccessFragment());
        mfragments.add(new PointFaildFragment());
        viewPagerPoint.setAdapter(vp);
        viewPagerPoint.setCurrentItem(0);
        viewPagerPoint.setOffscreenPageLimit(0);
/*        viewPagerPoint.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPagerPoint.getLayoutParams();
                switch (position) {
                    case 0:
                        linearParams.height = DensityUtil.dip2px(CollageActivity.this, 810);// 当控件的高强制设成50象素
                        break;
                    case 1:
                        linearParams.height = DensityUtil.dip2px(CollageActivity.this, 650);// 当控件的高强制设成50象素
                        break;
                }
                viewPagerPoint.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
        reflex(tabLayoutPoint);
    }

    public void reflex(final TabLayout tabLayoutPoint) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayoutPoint.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayoutPoint.getChildAt(0);

                    int dp20 = DensityUtil.dip2px(tabLayoutPoint.getContext(), 20);

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
