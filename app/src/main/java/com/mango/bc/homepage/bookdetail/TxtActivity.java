package com.mango.bc.homepage.bookdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.adapter.MyBookDetailAdapter;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.bookdetail.adapter.BookDetailAdapter;
import com.mango.bc.homepage.bookdetail.adapter.MyTxtDetailAdapter;
import com.mango.bc.homepage.bookdetail.adapter.TxtDetailAdapter;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TxtActivity extends BaseActivity {


    @Bind(R.id.recycle)
    RecyclerView recycle;
    private TxtDetailAdapter txtDetailAdapter;
    private MyTxtDetailAdapter myTxtDetailAdapter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt);
        ButterKnife.bind(this);
        position = getIntent().getIntExtra("position", -1);//-1 代表除大咖课其它课跳过来的
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        if (bookBean.getChapters() != null) {
            if (position == -1) {
                txtDetailAdapter = new TxtDetailAdapter(bookBean.getChapters().get(0).getContentImages(), this);
            }else {
                txtDetailAdapter = new TxtDetailAdapter(bookBean.getChapters().get(position).getContentImages(), this);
            }
            recycle.setLayoutManager(new LinearLayoutManager(this));
            recycle.setAdapter(txtDetailAdapter);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MyBookBeanEventBus(MyBookBean bookBean) {  //书架进来  不需要判断 直接可以播放
        if (bookBean == null) {
            return;
        }
        if (bookBean.getBook().getDescriptionImages() != null) {
            if (position == -1) {
                myTxtDetailAdapter = new MyTxtDetailAdapter(bookBean.getBook().getChapters().get(0).getContentImages(), this);
            }else {
                myTxtDetailAdapter = new MyTxtDetailAdapter(bookBean.getBook().getChapters().get(position).getContentImages(), this);
            }
            recycle.setLayoutManager(new LinearLayoutManager(this));
            recycle.setAdapter(myTxtDetailAdapter);
        }
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
}
