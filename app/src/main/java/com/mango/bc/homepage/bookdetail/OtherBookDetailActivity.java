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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.activity.BuyBookActivity;
import com.mango.bc.homepage.bookdetail.adapter.BookDetailAdapter;
import com.mango.bc.homepage.bookdetail.bean.BookDetailBean;
import com.mango.bc.homepage.bookdetail.jsonutil.JsonBookDetailUtils;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.util.ACache;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.view.likeview.PraiseView;
import com.mango.bc.wallet.bean.RefreshTaskBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    @Bind(R.id.l_like_play)
    PraiseView l_like_play;
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
    @Bind(R.id.tv_like_play)
    TextView tvLikePlay;
    @Bind(R.id.tv_like_free)
    TextView tvLikeFree;
    @Bind(R.id.tv_like_needbuy)
    TextView tvLikeNeedbuy;
    private BookDetailAdapter bookDetailAdapter;
    private SPUtils spUtils;
    private String bookId;
    private int likeNum;
    private ACache mCache;
    private String type;
    private BookDetailBean mBookDetailBean;
    private BookBean mBookBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {   //精品+上新+免费 详情共用  判断：是否领取   是否购买  三种 精品上新一样
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_other_detail);
        spUtils = SPUtils.getInstance("bc", this);
        mCache = ACache.get(this.getApplicationContext());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    private boolean chechState(String bookId) {
        String data = spUtils.getString("allMyBook", "");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> list = gson.fromJson(data, listType);
        if (list == null)
            return false;
        if (list.contains(bookId)) {
            return true;
        } else {
            return false;
        }
    }

    private void loadBookDetail(final Boolean ifCache, final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ifCache) {//读取缓存数据
                    String newString = mCache.getAsString("bookDetail" + bookId);
                    Log.v("yyyyyy", "---cache5---" + newString);
                    if (newString != null) {
                        final BookDetailBean bookDetailBean = JsonBookDetailUtils.readBookDetailBean(newString);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initBookDetailView(bookDetailBean);
                            }
                        });

                        return;
                    }
                } else {
                    mCache.remove("bookDetail" + bookId);//刷新之后缓存也更新过来
                }
                HttpUtils.doGet(Urls.HOST_BOOKDETAIL + "/" + bookId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getBaseContext(), "课程详情加载失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            mCache.put("bookDetail" + bookId, string);
                            final BookDetailBean bookDetailBean = JsonBookDetailUtils.readBookDetailBean(string);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initBookDetailView(bookDetailBean);
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getBaseContext(), "课程详情加载失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();

    }

    private void checkLike(final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("bookId", bookId);
                HttpUtils.doPost(Urls.HOST_IFLIKE, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //mHandler.sendEmptyMessage(4);
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        final String string;
                        try {
                            string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("tttttttttt", "------" + string);
                                    if (string.equals("true")) {
                                        l_like_play.setChecked(true);
                                        lLikeFree.setChecked(true);
                                        lLikeNeedbuy.setChecked(true);
                                        l_like_play.setClickable(false);
                                        lLikeFree.setClickable(false);
                                        lLikeNeedbuy.setClickable(false);
                                    }/*else {
                                    l_like_play.setChecked(false);
                                    lLikeFree.setChecked(false);
                                    lLikeNeedbuy.setChecked(false);
                                }*/
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

    private void like(final String bookId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //final RefreshStageBean refreshStageBean = new RefreshStageBean(true, true, true, true, true);
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("bookId", bookId);
                HttpUtils.doPost(Urls.HOST_LIKE, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //mHandler.sendEmptyMessage(4);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(getBaseContext(), getResources().getString(R.string.check_net));
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        final String string;
                        try {
                            string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("tttttttttt", "------" + string);
                                    if (string.equals("ok")) {
                                        l_like_play.setChecked(true);
                                        lLikeFree.setChecked(true);
                                        lLikeNeedbuy.setChecked(true);
                                        l_like_play.setClickable(false);
                                        lLikeFree.setClickable(false);
                                        lLikeNeedbuy.setClickable(false);
                                        tvLikePlay.setText(likeNum + 1 + "");
                                        tvLikeFree.setText(likeNum + 1 + "");
                                        tvLikeNeedbuy.setText(likeNum + 1 + "");
                                        EventBus.getDefault().postSticky(new RefreshTaskBean(true));//刷新任务列表
                                    }/*else {
                                    l_like_play.setChecked(false);
                                    lLikeFree.setChecked(false);
                                    lLikeNeedbuy.setChecked(false);
                                }*/
                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getBaseContext(), getResources().getString(R.string.check_net));
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    private void initState(String bookId, String type) {
        if (chechState(bookId)) {
            lGet.setVisibility(View.VISIBLE);//进去播放界面
            lFree.setVisibility(View.GONE);//进去免费领取界面
            lNeedbuy.setVisibility(View.GONE);//进去购买领取界面
        } else {
            if (type.equals("free")) {
                lGet.setVisibility(View.GONE);
                lFree.setVisibility(View.VISIBLE);
                lNeedbuy.setVisibility(View.GONE);
            } else if (type.equals("member")) {
                lGet.setVisibility(View.GONE);
                lFree.setVisibility(View.GONE);
                lNeedbuy.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initBookDetailView(BookDetailBean bookDetailBean) {
        if (bookDetailBean == null)
            return;
        this.mBookDetailBean = bookDetailBean;
        if (bookDetailBean.getAuthor() != null) {
            if (bookDetailBean.getAuthor().getPhoto() != null)
                Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookDetailBean.getAuthor().getPhoto().getFileName()).into(imgCover);
        }
        tvTitle.setText(bookDetailBean.getTitle());
        tvBuyer.setText(bookDetailBean.getSold() + "人已购买");
        likeNum = bookDetailBean.getLikes();
        tvLikePlay.setText(bookDetailBean.getLikes() + "");
        tvLikeFree.setText(bookDetailBean.getLikes() + "");
        tvLikeNeedbuy.setText(bookDetailBean.getLikes() + "");
        bookStageNeedbuyMoney.setText(bookDetailBean.getPrice() + "积分免费读");//还要判断是否是VIP
        if (bookDetailBean.getDescriptionImages() != null) {
            bookDetailAdapter = new BookDetailAdapter(bookDetailBean.getDescriptionImages(), this);
            recycle.setLayoutManager(new LinearLayoutManager(this));
            recycle.setAdapter(bookDetailAdapter);
            Log.v("uuuuuuuuuuuu", "--?--");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookBean) { //首页进来 需要判断 是否可以播放 是否要钱购买
        if (bookBean == null) {
            return;
        }
        this.mBookBean = bookBean;
        bookId = bookBean.getId();
        type = bookBean.getType();
        initState(bookId,type);
        checkLike(bookId);
        if (NetUtil.isNetConnect(this)) {
            loadBookDetail(false, bookId);
        } else {
            loadBookDetail(true, bookId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MyBookBeanEventBus(MyBookBean bookBean) {  //书架进来  不需要判断 直接可以播放
        if (bookBean == null) {
            return;
        }
        if (bookBean.getBook() != null) {
            bookId = bookBean.getBook().getId();
            type = bookBean.getBook().getType();
            initState(bookId,type);
            checkLike(bookId);
            if (NetUtil.isNetConnect(this)) {
                loadBookDetail(false, bookId);
            } else {
                loadBookDetail(true, bookId);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("dddddddddddd","-----ddd1---");
        bookDetailAdapter.recycleBitmap();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(BookBean.class);//展示完删除
        EventBus.getDefault().removeStickyEvent(MyBookBean.class);

    }

    @OnClick({R.id.imageView_back, R.id.l_like_play, R.id.l_share_get, R.id.l_txt_get, R.id.book_stage_play, R.id.l_get, R.id.l_like_free, R.id.l_share_free, R.id.book_stage_free, R.id.l_free, R.id.l_like_needbuy, R.id.book_stage_needbuy_vip, R.id.book_stage_needbuy_money, R.id.l_needbuy})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_like_play://播放点赞
                like(bookId);
                break;
            case R.id.l_share_get:
                showShare();
                break;
            case R.id.l_txt_get:
                intent = new Intent(this, TxtActivity.class);
                intent.putExtra("position", -1);
                startActivity(intent);
                break;
            case R.id.book_stage_play:
                break;
            case R.id.l_get:
                break;//以上领取可以播放状态
            case R.id.l_like_free://免费领取点赞
                like(bookId);
                break;
            case R.id.l_share_free:
                showShare();
                break;
            case R.id.book_stage_free:
                break;
            case R.id.l_free:
                break;//以上免费需要领取
            case R.id.l_like_needbuy://购买领取点赞
                like(bookId);
                Log.v("yyyyyyy", "==?===4--");
                break;
            case R.id.book_stage_needbuy_vip:
                break;
            case R.id.book_stage_needbuy_money:
                intent = new Intent(this, BuyBookActivity.class);
                EventBus.getDefault().postSticky(mBookBean);
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivityForResult(intent,0);
                break;
            case R.id.l_needbuy:
                break;//以上需要购买播放
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1){
            lGet.setVisibility(View.VISIBLE);//进去播放界面
            lFree.setVisibility(View.GONE);//进去免费领取界面
            lNeedbuy.setVisibility(View.GONE);//进去购买领取界面
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(mBookDetailBean.getTitle());
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mBookDetailBean.getSubtitle());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(Urls.HOST_GETFILE + "?name=" + mBookDetailBean.getCover().getFileName());//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(Urls.HOST_GETFILE + "?name=" + mBookDetailBean.getDescriptionImages().get(0).getFileName());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(getApplicationContext());
    }
}
