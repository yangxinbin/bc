package com.mango.bc.homepage.activity.competitivebook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.homepage.activity.competitivebook.fragment.CompetitivesRecyclerviewFragment;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.util.ACache;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompetitiveBookActivity extends AppCompatActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private List<CompetitiveFieldBean> beanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitive_book);
        ButterKnife.bind(this);
        viewpager.setOffscreenPageLimit(0);
        initDatas();
        setupViewPager(viewpager);
        tabLayout.setupWithViewPager(viewpager);
    }

    private void initDatas() {
        final ACache mCache = ACache.get(this);
        String newString = mCache.getAsString("cache" + 0);
        Log.v("yyyyyyyyy", "*****newString*****" + newString);

        //data是json字段获得data的值即对象数组
        if (newString != null) {
            beanList = JsonUtils.readCompetitiveFieldBean(newString);
            for (int i = 0; i < beanList.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(beanList.get(i).getName()));
            }
        }
    }

    private void setupViewPager(ViewPager viewpager) {
        ProjectsPagerAdapter adapter = new ProjectsPagerAdapter(getSupportFragmentManager());
        for (int j = 0; j < beanList.size(); j++) {
            adapter.addFragment(CompetitivesRecyclerviewFragment.newInstance(beanList.get(j).getName()), beanList.get(j).getName());
        }
        viewpager.setAdapter(adapter);
    }

    @OnClick(R.id.imageView_back)
    public void onViewClicked() {
        finish();
    }

    private class ProjectsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public ProjectsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String string) {
            mFragments.add(fragment);
            mFragmentTitles.add(string);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
