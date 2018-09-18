package com.mango.bc.homepage.bookdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.adapter.MyBookDetailAdapter;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.bookdetail.adapter.BookDetailAdapter;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.Urls;
import com.mango.bc.view.likeview.PraiseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherBookDetailActivity extends BaseActivity {


    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_cover)
    ImageView imgCover;
    @Bind(R.id.tv_buyer)
    TextView tvBuyer;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.l_like_get)
    PraiseView lLikeGet;
    @Bind(R.id.l_share_get)
    LinearLayout lShareGet;
    @Bind(R.id.l_txt_get)
    LinearLayout lTxtGet;
    @Bind(R.id.book_stage_play)
    TextView bookStagePlay;
    @Bind(R.id.l_get)
    LinearLayout lGet;
    @Bind(R.id.l_like_free)
    PraiseView lLikeFree;
    @Bind(R.id.l_share_free)
    LinearLayout lShareFree;
    @Bind(R.id.book_stage_free)
    TextView bookStageFree;
    @Bind(R.id.l_free)
    LinearLayout lFree;
    @Bind(R.id.l_like_needbuy)
    PraiseView lLikeNeedbuy;
    @Bind(R.id.book_stage_needbuy_vip)
    TextView bookStageNeedbuyVip;
    @Bind(R.id.book_stage_needbuy_money)
    TextView bookStageNeedbuyMoney;
    @Bind(R.id.l_needbuy)
    LinearLayout lNeedbuy;
    private BookDetailAdapter bookDetailAdapter;
    private MyBookDetailAdapter myBookDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {   //精品+上新+免费 详情共用  判断：是否领取   是否购买  三种 精品上新一样
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_other_detail);
        ButterKnife.bind(this);
        recycle.setNestedScrollingEnabled(false);
        initState();
        EventBus.getDefault().register(this);
    }

    private void initState() {
        if (getIntent().getBooleanExtra("foot_play", false)) {
            lGet.setVisibility(View.VISIBLE);//进去播放界面
            lFree.setVisibility(View.GONE);//进去免费领取界面
            lNeedbuy.setVisibility(View.GONE);//进去购买领取界面
        } else if (getIntent().getBooleanExtra("foot_free_get", false)) {
            lGet.setVisibility(View.GONE);
            lFree.setVisibility(View.VISIBLE);
            lNeedbuy.setVisibility(View.GONE);
        } else if (getIntent().getBooleanExtra("foot_buy_get", false)) {
            lGet.setVisibility(View.GONE);
            lFree.setVisibility(View.GONE);
            lNeedbuy.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) { //首页进来 需要判断 是否可以播放 是否要钱购买
        if (bookBean == null) {
            return;
        }
        if (bookBean.getAuthor() != null) {
            if (bookBean.getAuthor().getPhoto() != null)
                Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookBean.getAuthor().getPhoto().getFileName()).into(imgCover);
        }
        tvTitle.setText(bookBean.getTitle());
        tvBuyer.setText(bookBean.getSold() + "人已购买");
        if (bookBean.getDescriptionImages() != null) {
            bookDetailAdapter = new BookDetailAdapter(bookBean.getDescriptionImages(), this);
            recycle.setLayoutManager(new LinearLayoutManager(this));
            recycle.setAdapter(bookDetailAdapter);
            Log.v("uuuuuuuuuuuu", "--?--");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MyBookBeanEventBus(MyBookBean bookBean) {  //书架进来  不需要判断 直接可以播放
        if (bookBean == null) {
            return;
        }
        if (bookBean.getBook() != null) {
            if (bookBean.getBook().getCover() != null)
                Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookBean.getBook().getCover().getFileName()).into(imgCover);
            tvTitle.setText(bookBean.getBook().getTitle());
            tvBuyer.setText(bookBean.getBook().getSold() + "人已购买");
        }
        if (bookBean.getBook().getDescriptionImages() != null) {
            myBookDetailAdapter = new MyBookDetailAdapter(bookBean.getBook().getDescriptionImages(), this);
            recycle.setLayoutManager(new LinearLayoutManager(this));
            recycle.setAdapter(myBookDetailAdapter);
            Log.v("uuuuuuuuuuuu", "--?--");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(BookBean.class);//展示完删除
        EventBus.getDefault().removeStickyEvent(MyBookBean.class);

    }

    @OnClick({R.id.imageView_back, R.id.l_like_get, R.id.l_share_get, R.id.l_txt_get, R.id.book_stage_play, R.id.l_get, R.id.l_like_free, R.id.l_share_free, R.id.book_stage_free, R.id.l_free, R.id.l_like_needbuy, R.id.book_stage_needbuy_vip, R.id.book_stage_needbuy_money, R.id.l_needbuy})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_like_get:
                break;
            case R.id.l_share_get:
                break;
            case R.id.l_txt_get:
                intent = new Intent(this, TxtActivity.class);
                startActivity(intent);
                break;
            case R.id.book_stage_play:
                break;
            case R.id.l_get:
                break;//以上领取可以播放状态
            case R.id.l_like_free:
                break;
            case R.id.l_share_free:
                break;
            case R.id.book_stage_free:
                break;
            case R.id.l_free:
                break;//以上免费需要播放
            case R.id.l_like_needbuy:
                break;
            case R.id.book_stage_needbuy_vip:
                break;
            case R.id.book_stage_needbuy_money:
                break;
            case R.id.l_needbuy:
                break;//以上需要购买播放
        }
    }
}
