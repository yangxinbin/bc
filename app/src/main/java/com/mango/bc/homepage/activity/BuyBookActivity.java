package com.mango.bc.homepage.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.bean.BuySuccessBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BuyBookActivity extends BaseActivity implements MyAllBookView {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.img_book)
    RoundImageView imgBook;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_ppg_need)
    TextView tvPpgNeed;
    @Bind(R.id.book_item)
    LinearLayout bookItem;
    @Bind(R.id.tv_all_ppg)
    TextView tvAllPpg;
    @Bind(R.id.tv_need_ppg)
    TextView tvNeedPpg;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    private SPUtils spUtilsAuth;
    private SPUtils spUtilsAuthToken;
    private MyBookPresenterImpl myBookPresenter;
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);
        spUtilsAuth = SPUtils.getInstance("auth", this);
        spUtilsAuthToken = SPUtils.getInstance("authToken", this);
        myBookPresenter = new MyBookPresenterImpl(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initAuth(AuthJsonUtils.readUserBean(spUtilsAuth.getString("auth", "")));
    }
    private void initAuth(UserBean userBean) {
        if (userBean == null)
            return;
        if (userBean.getWallet() != null) {
            tvAllPpg.setText(userBean.getWallet().getPpCoins() + "积分");
        }
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
        tvPpgNeed.setText(bookDetailBean.getPrice()+"积分");
        tvNeedPpg.setText("实付款："+bookDetailBean.getPrice()+"积分");

    }
    @OnClick({R.id.imageView_back, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.tv_buy:
/*              BuySuccessBean buySuccessBean = new BuySuccessBean(true);
                EventBus.getDefault().postSticky(buySuccessBean);
                setResult(1);
                finish();*/
                getFreeBook(bookId);
                break;
        }
    }
    private void getFreeBook(final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtilsAuthToken.getString("authToken", ""));
                mapParams.put("bookId", bookId);
                HttpUtils.doPost(Urls.HOST_BUYBOOK, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getBaseContext(), "领取失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    BuySuccessBean buySuccessBean = new BuySuccessBean(true);
                                    EventBus.getDefault().postSticky(buySuccessBean);
                                    if (NetUtil.isNetConnect(getBaseContext())) {
                                        myBookPresenter.visitBooks(getBaseContext(), 3, 0, false);//获取书架的所有书(加入刷新  加载条目会notify)
                                    } else {
                                        myBookPresenter.visitBooks(getBaseContext(), 3, 0, true);//获取书架的所有书(加入刷新)
                                    }  //要不点击详情页还是显现领取，详情书的状态在adapter里面控制
                                    setResult(1);
                                    finish();
/*                                    RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, false, true, false);
                                    EventBus.getDefault().postSticky(refreshStageBean);*/
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getBaseContext(), "领取失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }
}
