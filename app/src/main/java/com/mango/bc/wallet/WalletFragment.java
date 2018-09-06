package com.mango.bc.wallet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.bookcase.fragment.MyExpertFragment;
import com.mango.bc.bookcase.fragment.MyFreeFragment;
import com.mango.bc.bookcase.fragment.MyQualityFragment;
import com.mango.bc.homepage.activity.VipDetailActivity;
import com.mango.bc.util.DensityUtil;
import com.mango.bc.view.ViewPagerForScrollView;
import com.mango.bc.wallet.activity.ExpertActivity;
import com.mango.bc.wallet.activity.ProblemActivity;
import com.mango.bc.wallet.fragment.AlreadyObtainedFragment;
import com.mango.bc.wallet.fragment.DailyTasksFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2018/9/3.
 */

public class WalletFragment extends Fragment {
    @Bind(R.id.tv_transaction_record)
    TextView tvTransactionRecord;
    @Bind(R.id.tv_all_pp)
    TextView tvAllPp;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.tv_cannot_put)
    TextView tvCannotPut;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.tv_can_put)
    TextView tvCanPut;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.tv_copy)
    TextView tvCopy;
    @Bind(R.id.l_recharge)
    LinearLayout lRecharge;
    @Bind(R.id.l_transfer)
    LinearLayout lTransfer;
    @Bind(R.id.l_currency)
    LinearLayout lCurrency;
    @Bind(R.id.tv_sign_day)
    TextView tvSignDay;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.l_to_bc)
    LinearLayout lToBc;
    @Bind(R.id.tabLayout_wallet)
    TabLayout tabLayout;
    @Bind(R.id.viewPager_wallet)
    ViewPager viewPager;
    @Bind(R.id.l_1)
    LinearLayout l1;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.l_3)
    LinearLayout l3;
    @Bind(R.id.l_4)
    LinearLayout l4;
    @Bind(R.id.l_5)
    LinearLayout l5;
    @Bind(R.id.l_service)
    LinearLayout lService;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wallet, container, false);
        ButterKnife.bind(this, view);
        initDatas();
        init();
        return view;
    }
    private void initDatas() {
        //  mDatas = new ArrayList<String>(Arrays.asList("       我的事件       ", "       全部事件       "));
        mDatas = new ArrayList<String>(Arrays.asList("每日任务","已获得"));

    }

    @SuppressLint("NewApi")
    private void init() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getFragmentManager(), mfragments, mDatas);
        tabLayout.setupWithViewPager(viewPager);
        mfragments.add(new DailyTasksFragment());
        mfragments.add(new AlreadyObtainedFragment());
        viewPager.setAdapter(vp);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
                switch (position){
                    case 0:
                        linearParams.height = DensityUtil.dip2px(getActivity(),810);// 当控件的高强制设成50象素
                        break;
                    case 1:
                        linearParams.height = DensityUtil.dip2px(getActivity(),650);// 当控件的高强制设成50象素
                        break;
                }
                viewPager.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        reflex(tabLayout);
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

    @OnClick({R.id.tv_transaction_record, R.id.tv_copy, R.id.l_recharge, R.id.l_transfer, R.id.l_currency,R.id.tv_sign, R.id.l_to_bc, R.id.l_1, R.id.l_2, R.id.l_3, R.id.l_4, R.id.l_5, R.id.l_service})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_transaction_record:
                break;
            case R.id.tv_copy:
                break;
            case R.id.l_recharge:
                break;
            case R.id.l_transfer:
                break;
            case R.id.l_currency:
                break;
            case R.id.tv_sign:
                break;
            case R.id.l_to_bc:
                intent = new Intent(getActivity(), ExpertActivity.class);
                startActivity(intent);
                break;
            case R.id.l_1:
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("k1",getActivity().getResources().getString(R.string.what_pp));
                intent.putExtra("k2",getActivity().getResources().getString(R.string.what_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_2:
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("k1",getActivity().getResources().getString(R.string.how_pp));
                intent.putExtra("k2",getActivity().getResources().getString(R.string.how_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_3:
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("k1",getActivity().getResources().getString(R.string.does_pp));
                intent.putExtra("k2",getActivity().getResources().getString(R.string.does_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_4:
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("k1",getActivity().getResources().getString(R.string.how_to_pp));
                intent.putExtra("k2",getActivity().getResources().getString(R.string.how_to_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_5:
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("k1",getActivity().getResources().getString(R.string.cancel_pp));
                intent.putExtra("k2",getActivity().getResources().getString(R.string.cancel_pp_detail));
                startActivity(intent);
                break;
            case R.id.l_service:
                break;
        }
    }
}
