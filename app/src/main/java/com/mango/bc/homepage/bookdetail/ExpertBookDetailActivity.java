package com.mango.bc.homepage.bookdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.adapter.ViewPageAdapter;
import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.activity.BuyBookActivity;
import com.mango.bc.homepage.activity.CollageBuyBookActivity;
import com.mango.bc.homepage.activity.VipDetailActivity;
import com.mango.bc.homepage.bookdetail.bean.BookDetailBean;
import com.mango.bc.homepage.bookdetail.bean.PlayBarBean;
import com.mango.bc.homepage.bookdetail.bean.PlayPauseBean;
import com.mango.bc.homepage.bookdetail.fragment.CommentFragment;
import com.mango.bc.homepage.bookdetail.fragment.CourseFragment;
import com.mango.bc.homepage.bookdetail.fragment.DetailFragment;
import com.mango.bc.homepage.bookdetail.jsonutil.JsonBookDetailUtils;
import com.mango.bc.homepage.bookdetail.play.executor.ControlPanel;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.mine.activity.VipCenterActivity;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.ACache;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.DensityUtil;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.view.likeview.PraiseView;
import com.mango.bc.wallet.bean.RefreshTaskBean;
import com.mango.bc.wallet.bean.RefreshWalletBean;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ExpertBookDetailActivity extends BaseServiceActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.img_cover)
    ImageView imgCover;
    @Bind(R.id.tv_buyer)
    TextView tvBuyer;
    @Bind(R.id.tv_course)
    TextView tvCourse;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.l_get)
    LinearLayout lGet;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.l_2)
    LinearLayout l2;
    @Bind(R.id.l_like_get)
    PraiseView l_like_get;
    @Bind(R.id.l_try)
    LinearLayout lTry;
    @Bind(R.id.l_buy)
    TextView lBuy;
    @Bind(R.id.l_collage)
    TextView lCollage;
    @Bind(R.id.l_like_play)
    PraiseView l_like_play;
    @Bind(R.id.l_share_play_expert)
    LinearLayout lSharePlayExpert;
    @Bind(R.id.book_stage_expert_play)
    TextView bookStageExpertPlay;
    @Bind(R.id.l_play_expert)
    LinearLayout lPlayExpert;
    @Bind(R.id.tv_like_get)
    TextView tvLikeGet;
    @Bind(R.id.tv_like_play)
    TextView tvLikePlay;
    @Bind(R.id.fl_play_bar)
    FrameLayout flPlayBar;
    private ArrayList<String> mDatas;
    List<Fragment> mfragments = new ArrayList<Fragment>();
    private SPUtils spUtils;
    private String bookId;
    private int likeNum;
    private ACache mCache;
    private String type;
    private BookDetailBean mBookDetailBean;
    private BookBean mBookBean;
    private ControlPanel controlPanel;
    private boolean isPlayFragmentShow;
    private String title;
    private String cover;
    //private PlayActivity mPlayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_expert_detail);
        spUtils = SPUtils.getInstance("bc", this);
        mCache = ACache.get(this.getApplicationContext());
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("bannerBookId") != null) {
            bannerEventBus(getIntent().getStringExtra("bannerBookId"));
        }
        EventBus.getDefault().register(this);
        etComment.setFocusable(false);
        initDatas();
        init();
        if (getIntent().getBooleanExtra("bookCourse", false)) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    protected void onServiceBound() {
        controlPanel = new ControlPanel(flPlayBar);
        AudioPlayer.get().addOnPlayEventListener(controlPanel);
        //parseIntent();
    }
/*
    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(Extras.EXTRA_NOTIFICATION)) {
            showPlayingFragment();
            setIntent(new Intent());
        }
    }

    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = new PlayActivity();
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }

    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }*/

    private void initState(boolean state, String type) {
        if (state) {
            spUtils.put("isFree", true);
            lGet.setVisibility(View.GONE);
            lPlayExpert.setVisibility(View.VISIBLE);//进去播放界面
        } else {
            spUtils.put("isFree", false);
            lGet.setVisibility(View.VISIBLE);//购买状态
            lPlayExpert.setVisibility(View.GONE);

        }
    }

    private boolean chechState(String bookId) {
        if (spUtils != null) {
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
                        spUtils.put("bookDetail", newString);
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
                            spUtils.put("bookDetail", string);
                            final BookDetailBean bookDetailBean = JsonBookDetailUtils.readBookDetailBean(string);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //EventBus.getDefault().postSticky(bookDetailBean);
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
                                        if (l_like_get != null) {
                                            l_like_get.setChecked(true);
                                            l_like_get.setClickable(false);
                                        }
                                        if (l_like_play != null) {
                                            l_like_play.setChecked(true);
                                            l_like_play.setClickable(false);
                                        }
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
                //final RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, true, false, false);
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
                                        l_like_get.setChecked(true);
                                        l_like_play.setChecked(true);
                                        l_like_play.setClickable(false);
                                        l_like_get.setClickable(false);
                                        tvLikePlay.setText(likeNum + 1 + "");
                                        tvLikeGet.setText(likeNum + 1 + "");
                                        EventBus.getDefault().postSticky(new RefreshTaskBean(true));//刷新任务列表
                                        EventBus.getDefault().postSticky(new RefreshWalletBean(true));//刷新钱包

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

    private void initBookDetailView(BookDetailBean bookDetailBean) {
        if (bookDetailBean == null)
            return;
        //AudioPlayer.get().init(this);
        title = bookDetailBean.getTitle();
        if (bookDetailBean.getCover() != null) {
            cover = bookDetailBean.getCover().getFileName();
        }
        this.mBookDetailBean = bookDetailBean;
        EventBus.getDefault().postSticky(bookDetailBean);
        if (bookDetailBean.getBanner() != null)
            Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookDetailBean.getBanner().getFileName()).into(imgCover);

        if (AudioPlayer.get().isPlaying() && mBookDetailBean.getId().equals(spUtils.getString("isSameBook", ""))) {
            bookStageExpertPlay.setText("播放中");
        } else if (AudioPlayer.get().isPausing() && mBookDetailBean.getId().equals(spUtils.getString("isSameBook", ""))) {
            bookStageExpertPlay.setText(getResources().getString(R.string.play));
        }
        tvBuyer.setText(bookDetailBean.getSold() + "");
        tvCourse.setText(bookDetailBean.getChapters().size() + "");
        likeNum = bookDetailBean.getLikes();
        tvLikeGet.setText(bookDetailBean.getLikes() + "");
        tvLikePlay.setText(bookDetailBean.getLikes() + "");
        lBuy.setText(bookDetailBean.getPrice() + "币购买\n" + "会员" + bookDetailBean.getVipPrice() + "币");
        lCollage.setText(bookDetailBean.getGroupBuy2Price() + "-" + bookDetailBean.getGroupBuy3Price() + "币\n拼团购买");
    }

    public void bannerEventBus(String bookId) {
        initState(chechState(bookId), type);
        checkLike(bookId);
        if (NetUtil.isNetConnect(this)) {
            loadBookDetail(false, bookId);
        } else {
            loadBookDetail(true, bookId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void BookBeanEventBus(BookBean bookBean) {
        if (bookBean == null) {
            return;
        }
        Log.v("bbbbbbbbbbb", "-----f");
        this.mBookBean = bookBean;
        bookId = bookBean.getId();
        type = bookBean.getType();
        initState(chechState(bookId), type);
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
            if (getIntent().getBooleanExtra("gift", false)) {
                initState(false, "");
            } else {
                initState(chechState(bookId), type);
            }
            checkLike(bookId);
            if (NetUtil.isNetConnect(this)) {
                loadBookDetail(false, bookId);
            } else {
                loadBookDetail(true, bookId);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void PlayBarBeanEventBus(PlayBarBean playBarBean) {
        if (playBarBean == null) {
            return;
        }
        Log.v("iiiiiiiiiiiiii", "---iiiieeeeiiiii---");
        if (!playBarBean.isShowBar()) {
            flPlayBar.setVisibility(View.GONE);//播放控件
            Log.v("iiiiiiiiiiiiii", "----h---");
        }
        EventBus.getDefault().removeStickyEvent(PlayBarBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void PlayPauseBeanEventBus(PlayPauseBean playPauseBean) {
        if (playPauseBean == null) {
            return;
        }
        if (playPauseBean.isPause()) {
            bookStageExpertPlay.setText(getResources().getString(R.string.play));
        } else {
            bookStageExpertPlay.setText("播放中");
        }
        EventBus.getDefault().removeStickyEvent(PlayPauseBean.class);
    }

    private void initDatas() {
        mDatas = new ArrayList<String>(Arrays.asList("详情", "课程", "评论"));
    }

    private void init() {
        if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing())
            flPlayBar.setVisibility(View.VISIBLE);//播放控件
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ViewPageAdapter vp = new ViewPageAdapter(getSupportFragmentManager(), mfragments, mDatas);
        tabLayout.setupWithViewPager(viewPager);
        mfragments.add(new DetailFragment());
        mfragments.add(new CourseFragment());
        mfragments.add(new CommentFragment());
        viewPager.setAdapter(vp);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        reflex(tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2) {
                    l2.setVisibility(View.VISIBLE);
                    lGet.setVisibility(View.GONE);
                    lPlayExpert.setVisibility(View.GONE);
                } else {
                    l2.setVisibility(View.GONE);
                    initState(chechState(bookId), type);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp40 = DensityUtil.dip2px(tabLayout.getContext(), 40);

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
                        params.width = width;
                        params.leftMargin = dp40;
                        params.rightMargin = dp40;
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

    @OnClick({R.id.et_comment, R.id.imageView_back, R.id.l_like_play, R.id.l_share_play_expert, R.id.book_stage_expert_play, R.id.l_like_get, R.id.l_try, R.id.l_buy, R.id.l_collage})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_like_get:
                like(bookId);
                break;
            case R.id.l_try:
                if (AudioPlayer.get().isPlaying() && mBookDetailBean.getId().equals(spUtils.getString("isSameBook", ""))) {
                    return;
                } else if (AudioPlayer.get().isPausing() && mBookDetailBean.getId().equals(spUtils.getString("isSameBook", ""))) {
                    AudioPlayer.get().startPlayer();
                    bookStageExpertPlay.setText("播放中");
                } else {
                    AudioPlayer.get().init(this);
                    AudioPlayer.get().play(0);//第一个开始播放
                    bookStageExpertPlay.setText("播放中");
                }
                break;
            case R.id.l_buy:
                intent = new Intent(this, BuyBookActivity.class);
                EventBus.getDefault().postSticky(mBookBean);
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.l_collage:
                intent = new Intent(this, CollageBuyBookActivity.class);
                EventBus.getDefault().postSticky(mBookBean);
                EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                startActivity(intent);
                //startActivityForResult(intent, 3);
                break;//以上是购买foot
            case R.id.l_like_play:
                like(bookId);
                break;
            case R.id.l_share_play_expert:
                showShare();
                break;
            case R.id.book_stage_expert_play:
                if (AudioPlayer.get().isPlaying() && mBookDetailBean.getId().equals(spUtils.getString("isSameBook", ""))) {
                    return;
                } else if (AudioPlayer.get().isPausing() && mBookDetailBean.getId().equals(spUtils.getString("isSameBook", ""))) {
                    AudioPlayer.get().startPlayer();
                    bookStageExpertPlay.setText("播放中");
                } else {
                    AudioPlayer.get().init(this);
                    AudioPlayer.get().play(0);//第一个开始播放
                    bookStageExpertPlay.setText("播放中");
                }
                break;//以上是播放foot
            case R.id.et_comment:
                etComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ExpertBookDetailActivity.this, CommentActivity.class);
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 1) {
            spUtils.put("isFree", true);
            lGet.setVisibility(View.GONE);
            lPlayExpert.setVisibility(View.VISIBLE);//进去播放界面
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        oks.setTitle(title);
        oks.setText("BC大陆");
        oks.setImageUrl(Urls.HOST_GETFILE + "?name=" + cover);
        oks.setUrl("http://www.mob.com");
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (platform.getName().equals("Wechat")) {
                    paramsToShare.setShareType(Platform.SHARE_WXMINIPROGRAM);
                    paramsToShare.setWxMiniProgramType(WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE);
                    paramsToShare.setWxUserName("gh_482031325125");
                    paramsToShare.setWxPath("/pages/giftBook/giftBook?model=" + "\"" + bookId + "\"");
                }
            }
        });
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });
        oks.show(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
/*        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }*/
        super.onBackPressed();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(BookBean.class);//展示完删除
        EventBus.getDefault().removeStickyEvent(MyBookBean.class);
    }
}
