package com.mango.bc.homepage.bookdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    @Bind(R.id.l_like)
    PraiseView lLike;
    @Bind(R.id.l_share)
    LinearLayout lShare;
    @Bind(R.id.l_txt)
    LinearLayout lTxt;
    @Bind(R.id.book_stage)
    TextView bookStage;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.l_foot)
    LinearLayout lFoot;
    private BookDetailAdapter bookDetailAdapter;
    private MyBookDetailAdapter myBookDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_other_detail);
        ButterKnife.bind(this);
        recycle.setNestedScrollingEnabled(false);
        if (getIntent().getBooleanExtra("foot", false)) {
            lFoot.setVisibility(View.GONE);
        } else {
            lFoot.setVisibility(View.VISIBLE);//免费才有底部
        }
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) { //首页进来
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

        EventBus.getDefault().removeStickyEvent(BookBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MyBookBeanEventBus(MyBookBean bookBean) {  //书架进来
        if (bookBean == null) {
            return;
        }
        if (bookBean.getBook() != null) {
            if (bookBean.getBook().getCover() != null)
            Glide.with(this).load(Urls.HOST_GETFILE + "?name=" +bookBean.getBook().getCover().getFileName()).into(imgCover);
            tvTitle.setText(bookBean.getBook().getTitle());
            tvBuyer.setText(bookBean.getBook().getSold() + "人已购买");
        }
        if (bookBean.getBook().getDescriptionImages() != null) {
            myBookDetailAdapter = new MyBookDetailAdapter(bookBean.getBook().getDescriptionImages(), this);
            recycle.setLayoutManager(new LinearLayoutManager(this));
            recycle.setAdapter(myBookDetailAdapter);
            Log.v("uuuuuuuuuuuu", "--?--");
        }

        EventBus.getDefault().removeStickyEvent(MyBookBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.imageView_back, R.id.l_like, R.id.l_share, R.id.l_txt, R.id.book_stage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_like:
                break;
            case R.id.l_share:
                break;
            case R.id.l_txt:
                break;
            case R.id.book_stage:
                break;
        }
    }
}
