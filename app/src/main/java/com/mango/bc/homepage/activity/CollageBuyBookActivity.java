package com.mango.bc.homepage.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.SingleCollageTypeAdapter;
import com.mango.bc.homepage.bean.CollageTypeBean;
import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.mine.adapter.SinglePayAdapter;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollageBuyBookActivity extends BaseActivity {

    @Bind(R.id.img_book)
    RoundImageView imgBook;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_ppg_before)
    TextView tvPpgBefore;
    @Bind(R.id.tv_ppg_after)
    TextView tvPpgAfter;
    @Bind(R.id.tv_need_ppg)
    TextView tvNeedPpg;
    @Bind(R.id.tv_unneed_ppg)
    TextView tvUnneedPpg;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.recyclerView_what)
    RecyclerView recyclerViewWhat;
    private List<CollageTypeBean> list;
    private SingleCollageTypeAdapter adapterWhat;
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_buy_book);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookDetailBean) { //首页进来 需要判断 是否可以播放 是否要钱购买
        if (bookDetailBean == null) {
            return;
        }
        if (bookDetailBean.getAuthor() != null) {
            if (bookDetailBean.getAuthor().getPhoto() != null)
                Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookDetailBean.getAuthor().getPhoto().getFileName()).into(imgBook);
        }
        bookId = bookDetailBean.getId();
        tvTitle.setText(bookDetailBean.getTitle());
        tvPpgBefore.setText(bookDetailBean.getPrice() + "积分");
        tvUnneedPpg.setText("原价:"+bookDetailBean.getPrice() + "积分");
        tvPpgBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvUnneedPpg.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvPpgAfter.setText(bookDetailBean.getGroupBuy2Price()+"积分");
        tvNeedPpg.setText("实付款:"+bookDetailBean.getGroupBuy2Price()+"积分  ");
        initDate(bookDetailBean);
    }

    private void initDate(BookBean bookDetailBean) {
        list = new ArrayList<>();
        if (bookDetailBean == null)
            return;
        list.add(new CollageTypeBean("2人拼团", bookDetailBean.getGroupBuy2Price() + ""));
        list.add(new CollageTypeBean("3人拼团", bookDetailBean.getGroupBuy3Price() + ""));
    }

    private void initView() {
        recyclerViewWhat.setHasFixedSize(true);
        recyclerViewWhat.setLayoutManager(new LinearLayoutManager(this));
        adapterWhat = new SingleCollageTypeAdapter(this, list);
        recyclerViewWhat.setAdapter(adapterWhat);

        adapterWhat.setOnItemClickLitener(new SingleCollageTypeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapterWhat.setSelection(position);
                tvPpgAfter.setText(adapterWhat.getItem(position).getPpg()+"积分");
                tvNeedPpg.setText("实付款:"+adapterWhat.getItem(position).getPpg()+"积分  ");
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adapterWhat.setSelection(0);
    }

    @OnClick({R.id.imageView_back, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.tv_buy:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
