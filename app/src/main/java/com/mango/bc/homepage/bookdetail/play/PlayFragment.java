package com.mango.bc.homepage.bookdetail.play;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.homepage.bookdetail.play.adapter.PlayPagerAdapter;
import com.mango.bc.homepage.bookdetail.play.constants.Actions;
import com.mango.bc.homepage.bookdetail.play.enums.PlayModeEnum;
import com.mango.bc.homepage.bookdetail.play.preference.Preferences;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.bookdetail.play.service.OnPlayerEventListener;
import com.mango.bc.homepage.bookdetail.play.utils.Bind;
import com.mango.bc.homepage.bookdetail.play.utils.CoverLoader;
import com.mango.bc.homepage.bookdetail.play.utils.ScreenUtils;
import com.mango.bc.homepage.bookdetail.play.utils.SystemUtils;
import com.mango.bc.homepage.bookdetail.play.widget.AlbumCoverView;
import com.mango.bc.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * 正在播放界面
 * Created by wcy on 2015/11/27.
 */
public class PlayFragment extends BaseFragment implements View.OnClickListener,
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
    private AlbumCoverView mAlbumCoverView;
    //private LrcView mLrcViewSingle;
    //private LrcView mLrcViewFull;
    //private SeekBar sbVolume;

    private AudioManager mAudioManager;
    private List<View> mViewPagerContent;
    private int mLastProgress;
    private boolean isDraggingProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*        View view = inflater.inflate(R.layout.fragment_play, container, false);
        ButterKnife.bind(this, view);
        return view;*/
        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        View coverView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_play_page_cover, null);
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
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        //sbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        //sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    private void initPlayMode() {
        int mode = Preferences.getPlayMode();
        //ivMode.setImageLevel(mode);
    }

    @Override
    public void onChange(BookMusicDetailBean music) {
        Log.v("ddddddddd", "---onChange--");
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
        if (percent != 0){
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

    private void switchPlayMode() {
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
    }

    private void onBackPressed() {
        getActivity().onBackPressed();
        ivBack.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBack.setEnabled(true);
            }
        }, 300);
    }

    private void setCoverAndBg(BookMusicDetailBean music) {
        mAlbumCoverView.setCoverBitmap(CoverLoader.get().loadThumb(music));
        vpPlayPage.setBackground(new BitmapDrawable(getActivity().getResources(), CoverLoader.get().loadBlur(music)));
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
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
