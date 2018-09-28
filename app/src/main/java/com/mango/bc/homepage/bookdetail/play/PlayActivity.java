package com.mango.bc.homepage.bookdetail.play;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.TxtActivity;
import com.mango.bc.homepage.bookdetail.bean.BookDetailBean;
import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.homepage.bookdetail.jsonutil.JsonBookDetailUtils;
import com.mango.bc.homepage.bookdetail.play.adapter.BookCourseListAdapter;
import com.mango.bc.homepage.bookdetail.play.adapter.PlayPagerAdapter;
import com.mango.bc.homepage.bookdetail.play.constants.Actions;
import com.mango.bc.homepage.bookdetail.play.preference.Preferences;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.bookdetail.play.service.OnPlayerEventListener;
import com.mango.bc.homepage.bookdetail.play.utils.CoverLoader;
import com.mango.bc.homepage.bookdetail.play.utils.ScreenUtils;
import com.mango.bc.homepage.bookdetail.play.utils.SystemUtils;
import com.mango.bc.homepage.bookdetail.play.widget.AlbumCoverView;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.CheckInBean;
import com.mango.bc.wallet.bean.RefreshTaskBean;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 正在播放界面
 * Created by wcy on 2015/11/27.
 */
public class PlayActivity extends BasePlayActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, SeekBar.OnSeekBarChangeListener, OnPlayerEventListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    @Bind(R.id.vp_play_page)
    ViewPager vpPlayPage;
    @Bind(R.id.tv_current_time)
    TextView tvCurrentTime;
    @Bind(R.id.sb_progress)
    SeekBar sbProgress;
    @Bind(R.id.tv_total_time)
    TextView tvTotalTime;
    /*    @Bind(R.id.iv_mode)
        ImageView ivMode;*/
    @Bind(R.id.iv_prev)
    ImageView ivPrev;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.iv_txt)
    ImageView ivTxt;
    @Bind(R.id.iv_list)
    ImageView ivList;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    private AlbumCoverView mAlbumCoverView;
    //private LrcView mLrcViewSingle;
    //private LrcView mLrcViewFull;
    //private SeekBar sbVolume;

    private AudioManager mAudioManager;
    private List<View> mViewPagerContent;
    private int mLastProgress;
    private boolean isDraggingProgress;
    private BookMusicDetailBean showMusic;
    private Dialog dialog;
    private BookCourseListAdapter adapter;
    private List<BookMusicDetailBean> bookMusicDetailBeanList = new ArrayList<>();
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_play);
        ButterKnife.bind(this);
        spUtils = SPUtils.getInstance("bc", this);
        //initSystemBar();
        initViewPager();
        //ilIndicator.create(mViewPagerContent.size());
        //initPlayMode();
        onChangeImpl(AudioPlayer.get().getPlayMusic());
        AudioPlayer.get().addOnPlayEventListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Actions.VOLUME_CHANGED_ACTION);
        //getContext().registerReceiver(mVolumeReceiver, filter);
    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(this);
        //ivMode.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        sbProgress.setOnSeekBarChangeListener(this);
        //sbVolume.setOnSeekBarChangeListener(this);
        vpPlayPage.addOnPageChangeListener(this);
    }

    /**
     * 沉浸式状态栏
     */
    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int top = ScreenUtils.getStatusBarHeight();
            llContent.setPadding(0, top, 0, 0);
        }
    }

    private void initViewPager() {
        View coverView = LayoutInflater.from(this).inflate(R.layout.fragment_play_page_cover, null);
        //View lrcView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_play_page_lrc, null);
        mAlbumCoverView = (AlbumCoverView) coverView.findViewById(R.id.album_cover_view);
        //mLrcViewSingle = (LrcView) coverView.findViewById(R.id.lrc_view_single);
        //mLrcViewFull = (LrcView) lrcView.findViewById(R.id.lrc_view_full);
        //sbVolume = (SeekBar) lrcView.findViewById(R.id.sb_volume);
        mAlbumCoverView.initNeedle(AudioPlayer.get().isPlaying());
        //mLrcViewFull.setOnPlayClickListener(this);
        initVolume();

        mViewPagerContent = new ArrayList<>(1);
        mViewPagerContent.add(coverView);
        //mViewPagerContent.add(lrcView);
        vpPlayPage.setAdapter(new PlayPagerAdapter(mViewPagerContent));
    }

    private void initVolume() {
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        //sbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        //sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    private void initPlayMode() {
        int mode = Preferences.getPlayMode();
        //ivMode.setImageLevel(mode);
    }

    @Override
    public void onChange(BookMusicDetailBean music) {
        onChangeImpl(music);
    }

    @Override
    public void onPlayerStart() {
        ivPlay.setSelected(true);
        mAlbumCoverView.start();
    }

    @Override
    public void onPlayerPause() {
        ivPlay.setSelected(false);
        mAlbumCoverView.pause();
    }

    /**
     * 更新播放进度
     */
    @Override
    public void onPublish(int progress) {
        if (!isDraggingProgress) {
            //Log.v("ddddddddd", "---onPublish--" + progress);
            sbProgress.setProgress(progress);
        }

/*        if (mLrcViewSingle.hasLrc()) {
            mLrcViewSingle.updateTime(progress);
            mLrcViewFull.updateTime(progress);
        }*/
    }

    @Override
    public void onBufferingUpdate(int percent) {
        if (percent != 0) {
            //Log.v("ddddddddd", "---onBufferingUpdate--"+sbProgress.getMax() * 100 / percent);
            sbProgress.setSecondaryProgress(sbProgress.getMax() * 100 / percent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
/*            case R.id.iv_mode:
                switchPlayMode();
                break;*/
            case R.id.iv_play:
                play();
                break;
            case R.id.iv_next:
                next();
                break;
            case R.id.iv_prev:
                prev();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //ilIndicator.setCurrent(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private String formatTime(long time) {
        return SystemUtils.formatTime("mm:ss", time);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == sbProgress) {
            if (Math.abs(progress - mLastProgress) >= DateUtils.SECOND_IN_MILLIS) {
                tvCurrentTime.setText(formatTime(progress));
                mLastProgress = progress;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar == sbProgress) {
            isDraggingProgress = true;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == sbProgress) {
            isDraggingProgress = false;
            if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing()) {
                int progress = seekBar.getProgress();
                AudioPlayer.get().seekTo(progress);

/*                if (mLrcViewSingle.hasLrc()) {
                    mLrcViewSingle.updateTime(progress);
                    mLrcViewFull.updateTime(progress);
                }*/
            } else {
                seekBar.setProgress(0);
            }
        } /*else if (seekBar == sbVolume) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(),
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }*/
    }

    private void onChangeImpl(BookMusicDetailBean music) {
        if (music == null) {
            return;
        }
        this.showMusic = music;
        tvTitle.setText(music.getTitle());
        tvArtist.setText(music.getName() + " | " + music.getMp3Name());
        sbProgress.setProgress((int) AudioPlayer.get().getAudioPosition());
        sbProgress.setSecondaryProgress(0);
        sbProgress.setMax((music.getDuration()) * 1000);//需要时间戳
        mLastProgress = 0;
        tvCurrentTime.setText(R.string.play_time_start);
        //Log.v("ddddddddd", "--onChangeImpl---" + music.getDuration());
        tvTotalTime.setText(formatTime(music.getDuration() * 1000));//秒转为标准格式
        setCoverAndBg(music);
        //setLrc(music);
        if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPreparing()) {
            ivPlay.setSelected(true);
            mAlbumCoverView.start();
        } else {
            ivPlay.setSelected(false);
            mAlbumCoverView.pause();
        }
    }

    private void play() {
        AudioPlayer.get().playPause();
    }

    private void next() {
        AudioPlayer.get().next();
    }

    private void prev() {
        AudioPlayer.get().prev();
    }

/*    private void switchPlayMode() {
        PlayModeEnum mode = PlayModeEnum.valueOf(Preferences.getPlayMode());
        switch (mode) {
            case LOOP:
                mode = PlayModeEnum.SHUFFLE;
                AppUtils.showToast(getActivity(), getActivity().getResources().getString(R.string.mode_shuffle));
                break;
            case SHUFFLE:
                mode = PlayModeEnum.SINGLE;
                AppUtils.showToast(getActivity(), getActivity().getResources().getString(R.string.mode_one));
                break;
            case SINGLE:
                mode = PlayModeEnum.LOOP;
                AppUtils.showToast(getActivity(), getActivity().getResources().getString(R.string.mode_loop));
                break;
        }
        Preferences.savePlayMode(mode.value());
        initPlayMode();
    }*/

    public void onBackPressed() {
        ivBack.setEnabled(false);
        finish();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBack.setEnabled(true);
            }
        }, 300);
    }

    private void setCoverAndBg(BookMusicDetailBean music) {
        mAlbumCoverView.setCoverBitmap(CoverLoader.get().loadThumb(music));
        vpPlayPage.setBackground(new BitmapDrawable(getResources(), CoverLoader.get().loadBlur(music)));
    }

    /*public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }*/
/*    private BroadcastReceiver mVolumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    };*/

    @Override
    public void onDestroy() {
        //getContext().unregisterReceiver(mVolumeReceiver);
        AudioPlayer.get().removeOnPlayEventListener(this);
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.iv_txt, R.id.iv_list, R.id.iv_share})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_txt:
                intent = new Intent(this, TxtActivity.class);
                intent.putExtra("position", AudioPlayer.get().getPlayPosition());
                intent.putExtra("playActivity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_list:
                //intent = new Intent(this, ExpertBookDetailActivity.class);
                //EventBus.getDefault().removeStickyEvent(BookBean.class);
                //EventBus.getDefault().removeStickyEvent(MyBookBean.class);
                //intent.putExtra("bookCourse",true);
                //startActivity(intent);
                BookDetailBean bookBean = JsonBookDetailUtils.readBookDetailBean(spUtils.getString("bookDetail", ""));
                Log.v("xxxxxxxxx", "-----" + spUtils.getString("bookDetail", ""));
                bookMusicDetailBeanList.clear();//防止叠加
                if (bookBean.getChapters() != null)
                    for (int i = 0; i < bookBean.getChapters().size(); i++) {
                        Log.v("xxxxxxxxxxx", bookBean.getChapters().get(i).isFree() + "-----" + spUtils.getBoolean("isFree", false));
                        if (bookBean.getChapters().get(i).isFree() || spUtils.getBoolean("isFree", false)) {
                            BookMusicDetailBean bookMusicDetailBean = new BookMusicDetailBean();
                            bookMusicDetailBean.setBookId(bookBean.getId());
                            Log.v("xxxxxxxxx", "---isSameBook--" + bookBean.getId());
                            spUtils.put("isSameBook", bookBean.getId());
                            //bookMusicDetailBean.setContentImages(bookBean.getChapters().get(i).getContentImages().get(i).getFileName());
                            bookMusicDetailBean.setName(bookBean.getAuthor().getName());
                            bookMusicDetailBean.setTitle(bookBean.getTitle());
                            bookMusicDetailBean.setIsFree(bookBean.getChapters().get(i).isFree());
                            bookMusicDetailBean.setMp3Name(bookBean.getChapters().get(i).getTitle());
                            if (bookBean.getCover() != null)
                                bookMusicDetailBean.setCoverPath(Urls.HOST_GETFILE + "?name=" + bookBean.getCover().getFileName());
                            if (bookBean.getChapters().get(i).getAudio() != null)
                                bookMusicDetailBean.setMp3Path(Urls.HOST_GETFILE + "?name=" + bookBean.getChapters().get(i).getAudio().getFileName());
                            bookMusicDetailBean.setDuration(bookBean.getChapters().get(i).getDuration());
                            bookMusicDetailBeanList.add(bookMusicDetailBean);
                        } else {
                            continue;
                        }

                    }
                showPopupWindow(this, bookMusicDetailBeanList);
                break;
            case R.id.iv_share:
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(showMusic.getTitle());
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(showMusic.getName() + " | " + showMusic.getMp3Name());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        Log.v("sssssssssss", Urls.HOST_GETFILE + "?name=" + showMusic.getCoverPath() + "---onChange--" + Urls.HOST_GETFILE + "?name=" + showMusic.getMp3Path());
        oks.setImageUrl(showMusic.getCoverPath());//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(showMusic.getMp3Path());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.v("nnnnn", "----1");
                shareNum();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.v("nnnn", "----2");

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.v("nnnn", "----3");

            }
        });
        // 启动分享GUI
        oks.show(getApplicationContext());

    }

    private void shareNum() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                HttpUtils.doPost(Urls.HOST_TASKSHARE, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            EventBus.getDefault().postSticky(new RefreshTaskBean(true));//刷新任务列表
                        } catch (Exception e) {
                            //mHandler.sendEmptyMessage(0);
                        }
                    }
                });
            }
        }).start();
    }

    private void showPopupWindow(Context context, List<BookMusicDetailBean> bookMusicDetailBeanList) {
        Log.v("xxxxxxxxx", "-bookMusicDetailBeanList----" + bookMusicDetailBeanList.size());
        //设置要显示的view
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_text_down, null);
        //此处可按需求为各控件设置属性
        RecyclerView recyclerView = view.findViewById(R.id.recycle_course);
        ImageView imageView_close = view.findViewById(R.id.img_close);
        imageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        adapter = new BookCourseListAdapter(bookMusicDetailBeanList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(mOnClickListenner);
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置显示位置
        window.setGravity(Gravity.CENTER);
        //设置动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private BookCourseListAdapter.OnItemClickLitener mOnClickListenner = new BookCourseListAdapter.OnItemClickLitener() {
        @Override
        public void onReadClick(View view, int position) {
            AudioPlayer.get().init(PlayActivity.this);
            AudioPlayer.get().play(position);
            dialog.dismiss();
            //adapter.notifyDataSetChanged();
        }
    };


/*    @Override
    protected void onServiceBound() {
        if (adapter != null)
            adapter.setIsPlaylist(true);
        Log.v("xxxx", "----" + AudioPlayer.get().getPlayPosition());
        AudioPlayer.get().addOnPlayEventListener(this);
    }*/


}
