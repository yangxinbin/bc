package com.mango.bc.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.bean.RefreshBookCaseBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.bean.BuySuccessBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.AuthJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.activity.RechargeActivity;

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
    private SPUtils spUtils;
    private MyBookPresenterImpl myBookPresenter;
    private String bookId;
    private Double ppCoins, prices;
    private boolean isVip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);
        spUtils = SPUtils.getInstance("bc", this);
        myBookPresenter = new MyBookPresenterImpl(this);
        ButterKnife.bind(this);
        initAuth(AuthJsonUtils.readUserBean(spUtils.getString("auth", "")));
        Log.v("ppppppp", ppCoins + "===" + prices);
        if (ppCoins < prices) {
            tvBuy.setText(getResources().getString(R.string.pp_recharge));
        }
    }

    private void initAuth(UserBean userBean) {
        if (userBean == null)
            return;
        isVip = userBean.isVip();
        EventBus.getDefault().register(this);
        if (userBean.getWallet() != null) {
            ppCoins = userBean.getWallet().getPpCoins();
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
        prices = bookDetailBean.getPrice();
        if (isVip) {
            tvPpgNeed.setText(bookDetailBean.getVipPrice() + "积分");
            tvNeedPpg.setText("实付款：" + bookDetailBean.getVipPrice() + "积分");
        } else {
            tvPpgNeed.setText(bookDetailBean.getPrice() + "积分");
            tvNeedPpg.setText("实付款：" + bookDetailBean.getPrice() + "积分");
        }

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
                if (tvBuy.getText().equals(getResources().getString(R.string.pp_recharge))) {
                    Intent intent = new Intent(this, RechargeActivity.class);
                    startActivity(intent);
                } else {
                    getBuyBook(bookId);
                }
                break;
        }
    }

    private void getBuyBook(final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
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
                            loadStats();
                            //loadUser();
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
                                    EventBus.getDefault().postSticky(new RefreshBookCaseBean(true, true, false));//两种情况

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

    private void loadStats() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //final HashMap<String, String> mapParams = new HashMap<String, String>();
                //mapParams.clear();
                //mapParams.put("authToken", spUtils.getString("authToken", ""));
                HttpUtils.doGet(Urls.HOST_STATS + "?authToken=" + spUtils.getString("authToken", ""), /*mapParams,*/ new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        try {
                            final String string1 = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadUser();//更新用户信息（钱）
                                    StatsBean statsBean = AuthJsonUtils.readStatsBean(string1);
                                    EventBus.getDefault().postSticky(statsBean);//刷新钱包
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }

    private void loadUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", spUtils.getString("openId", ""));
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        try {
                            final String string2 = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spUtils.put("auth", string2);//刷新用户信息
                                    Log.v("cccccccccc", "-----auth----");
                                    UserBean userBean = AuthJsonUtils.readUserBean(string2);
                                    EventBus.getDefault().postSticky(userBean);//刷新钱包
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
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
