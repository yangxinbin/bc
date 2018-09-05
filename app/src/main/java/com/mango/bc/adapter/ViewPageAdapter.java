package com.mango.bc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mfragments;
    private List<String> list_Title;
    public ViewPageAdapter(FragmentManager fm, List<Fragment> fragmentlists, List<String> list_Title) {
        super(fm);
        this.mfragments=fragmentlists;
        this.list_Title=list_Title;
    }
    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position % list_Title.size());
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

}