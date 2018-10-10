package com.mango.bc.mine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.mine.fragment.ExpertApplyFragment;
import com.mango.bc.mine.fragment.PointApplyFragment;
import com.mango.bc.util.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyActivity extends BaseServiceActivity {

    @Bind(R.id.tabLayout_apply)
    TabLayout tabLayoutApply;
    @Bind(R.id.viewPager_apply)
    ViewPager viewPagerApply;
    @Bind(R.id.expert_apply)
    Button expertApply;
    @Bind(R.id.point_apply)
    Button pointApply;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        initDatas();
        init();
    }
    private void initDatas() {
        //  mDatas = new ArrayList<String>(Arrays.asList("       我的事件       ", "       全部事件       "));
        mDatas = new ArrayList<String>(Arrays.asList("达人申请", "开通节点"));
    }

    @SuppressLint("NewApi")
    private void init() {
        tabLayoutApply.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getSupportFragmentManager(), mfragments, mDatas);
        tabLayoutApply.setupWithViewPager(viewPagerApply);
        mfragments.add(new ExpertApplyFragment());
        mfragments.add(new PointApplyFragment());
        viewPagerApply.setAdapter(vp);
        viewPagerApply.setCurrentItem(0);
        viewPagerApply.setOffscreenPageLimit(0);
        viewPagerApply.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        expertApply.setVisibility(View.VISIBLE);
                        pointApply.setVisibility(View.GONE);
                        break;
                    case 1:
                        expertApply.setVisibility(View.GONE);
                        pointApply.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        reflex(tabLayoutApply);
    }

    public void reflex(final TabLayout tabLayoutApply) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayoutApply.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayoutApply.getChildAt(0);

                    int dp20 = DensityUtil.dip2px(tabLayoutApply.getContext(), 20);

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
    @OnClick({R.id.imageView_back, R.id.expert_apply, R.id.point_apply})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.expert_apply:
                intent = new Intent(this, ExpertApplyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.point_apply:
                intent = new Intent(this, PointApplyActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
