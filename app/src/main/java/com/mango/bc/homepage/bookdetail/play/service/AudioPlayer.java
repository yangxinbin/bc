package com.mango.bc.homepage.bookdetail.play.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.bean.BookDetailBean;
import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.homepage.bookdetail.bean.PlayPauseBean;
import com.mango.bc.homepage.bookdetail.jsonutil.JsonBookDetailUtils;
import com.mango.bc.homepage.bookdetail.play.enums.PlayModeEnum;
import com.mango.bc.homepage.bookdetail.play.global.Notifier;
import com.mango.bc.homepage.bookdetail.play.preference.Preferences;
import com.mango.bc.homepage.bookdetail.play.receiver.NoisyAudioStreamReceiver;
import com.mango.bc.login.UserDetailActivity;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.RefreshTaskBean;
import com.mango.bc.wallet.bean.RefreshWalletBean;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by hzwangchenyan on 2018/1/26.
 */
public class AudioPlayer {
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_PAUSE = 3;

    private static final long TIME_UPDATE = 300L;

    private Context context;
    private AudioFocusManager audioFocusManager;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private NoisyAudioStreamReceiver noisyReceiver;
    private IntentFilter noisyFilter;
    private List<BookMusicDetailBean> musicList = new ArrayList<>();
    private final List<OnPlayerEventListener> listeners = new ArrayList<>();
    private int state = STATE_IDLE;
    private SPUtils spUtils;

    public static AudioPlayer get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static AudioPlayer instance = new AudioPlayer();
    }

    private AudioPlayer() {
        spUtils = SPUtils.getInstance("bc", context);
    }

    public void init(Context context) {
        if (isPlaying() || isPausing()) {
        } else {
            spUtils.put("start", 0L);
        }
        stopPlayer();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        this.context = context.getApplicationContext();
        musicList.clear();
        BookDetailBean bookBean = JsonBookDetailUtils.readBookDetailBean(spUtils.getString("bookDetail", ""));
        if (bookBean != null) {
            if (bookBean.getChapters() != null)
                for (int i = 0; i < bookBean.getChapters().size(); i++) {
                    if (bookBean.getChapters().get(i).isFree() || spUtils.getBoolean("isFree", false)) {
                        BookMusicDetailBean bookMusicDetailBean = new BookMusicDetailBean();
                        bookMusicDetailBean.setBookId(bookBean.getId());
                        bookMusicDetailBean.setType(bookBean.getType());
                        spUtils.put("isSameBook", bookBean.getId());
                        //bookMusicDetailBean.setContentImages(bookBean.getChapters().get(i).getContentImages().get(i).getFileName());
                        if (bookBean.getAuthor() != null)
                            bookMusicDetailBean.setName(bookBean.getAuthor().getName());
                        bookMusicDetailBean.setTitle(bookBean.getTitle());
                        bookMusicDetailBean.setIsFree(bookBean.getChapters().get(i).isFree());
                        bookMusicDetailBean.setMp3Name(bookBean.getChapters().get(i).getTitle());
                        if (bookBean.getCover() != null)
                            bookMusicDetailBean.setCoverPath(Urls.HOST_GETFILE + "?name=" + bookBean.getCover().getFileName());
                        if (bookBean.getChapters().get(i).getAudio() != null)
                            bookMusicDetailBean.setMp3Path(Urls.HOST_GETFILE + "?name=" + bookBean.getChapters().get(i).getAudio().getFileName());
                        bookMusicDetailBean.setDuration(bookBean.getChapters().get(i).getDuration());
                        musicList.add(bookMusicDetailBean);
                    } else {
                        continue;
                    }

                }
        }

        audioFocusManager = new AudioFocusManager(context);
        mediaPlayer = new MediaPlayer();
        handler = new Handler(Looper.getMainLooper());
        noisyReceiver = new NoisyAudioStreamReceiver();
        noisyFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (isPreparing()) {
                    startPlayer();
                }
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                for (OnPlayerEventListener listener : listeners) {
                    listener.onBufferingUpdate(percent);
                }
            }
        });
    }

    public void addOnPlayEventListener(OnPlayerEventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeOnPlayEventListener(OnPlayerEventListener listener) {
        listeners.remove(listener);
    }

    public void addAndPlay(BookMusicDetailBean music) {
        int position = musicList.indexOf(music);
        if (position < 0) {
            musicList.add(music);
            //DBManager.get().getMusicDao().insert(music);
            position = musicList.size() - 1;
        }
        play(position);
    }

    public void play(int position) {
        EventBus.getDefault().postSticky(new PlayPauseBean(false));
        if (musicList.isEmpty()) {
            return;
        }

        if (position < 0) {
            position = musicList.size() - 1;
        } else if (position >= musicList.size()) {
            position = 0;
        }
        setPlayPosition(position);
        BookMusicDetailBean music = getPlayMusic();
        spUtils.put("start", System.currentTimeMillis());
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getMp3Path());
            mediaPlayer.prepareAsync();
            state = STATE_PREPARING;
            for (OnPlayerEventListener listener : listeners) {
                listener.onChange(music);
            }
            Notifier.get().showPlay(music);
            MediaSessionManager.get().updateMetaData(music);
            MediaSessionManager.get().updatePlaybackState();
        } catch (IOException e) {
            e.printStackTrace();
            AppUtils.showToast(context, "当前课程无法播放");
        }
    }

    public void delete(int position) {
        int playPosition = getPlayPosition();
        BookMusicDetailBean music = musicList.remove(position);
        //DBManager.get().getMusicDao().delete(music);
        if (playPosition > position) {
            setPlayPosition(playPosition - 1);
        } else if (playPosition == position) {
            if (isPlaying() || isPreparing()) {
                setPlayPosition(playPosition - 1);
                next();
            } else {
                stopPlayer();
                for (OnPlayerEventListener listener : listeners) {
                    listener.onChange(getPlayMusic());
                }
            }
        }
    }

    public void playPause() {
        PlayPauseBean playPauseBean = new PlayPauseBean();
        if (isPreparing()) {
            playPauseBean.setPause(true);
            stopPlayer();
        } else if (isPlaying()) {
            playPauseBean.setPause(true);
            learnTime(System.currentTimeMillis());
            //spUtils.put("start", 0L);
            pausePlayer();
        } else if (isPausing()) {
            playPauseBean.setPause(false);
            startPlayer();
        } else {
            playPauseBean.setPause(false);
            play(getPlayPosition());
        }
        EventBus.getDefault().postSticky(playPauseBean);
    }

    public void startPlayer() {
        if (!isPreparing() && !isPausing()) {
            return;
        }
        spUtils.put("start", System.currentTimeMillis());
        if (audioFocusManager.requestAudioFocus()) {
            mediaPlayer.start();
            state = STATE_PLAYING;
            handler.post(mPublishRunnable);
            Notifier.get().showPlay(getPlayMusic());
            MediaSessionManager.get().updatePlaybackState();
            context.registerReceiver(noisyReceiver, noisyFilter);

            for (OnPlayerEventListener listener : listeners) {
                listener.onPlayerStart();
            }
        }
    }

    public void pausePlayer() {
        pausePlayer(true);
    }

    public void pausePlayer(boolean abandonAudioFocus) {
        if (!isPlaying()) {
            return;
        }
        mediaPlayer.pause();
        state = STATE_PAUSE;
        handler.removeCallbacks(mPublishRunnable);
        Notifier.get().showPause(getPlayMusic());
        MediaSessionManager.get().updatePlaybackState();
//        context.unregisterReceiver(noisyReceiver);
        if (abandonAudioFocus) {
            audioFocusManager.abandonAudioFocus();
        }

        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerPause();
        }
    }

    public void pausePlayer2(boolean abandonAudioFocus) {
        if (!isPlaying()) {
            return;
        }

        mediaPlayer.pause();
        state = STATE_PAUSE;
        handler.removeCallbacks(mPublishRunnable);
        Notifier.get().showPause(getPlayMusic());
        MediaSessionManager.get().updatePlaybackState();
        if (abandonAudioFocus) {
            audioFocusManager.abandonAudioFocus();
        }

        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerPause();
        }
    }

    public void stopPlayer() {
        EventBus.getDefault().postSticky(new PlayPauseBean(true));
        if (spUtils.getLong("start", 0L) != 0L && !isPausing() && isPlaying()) {
            learnTime(System.currentTimeMillis());
        }
        if (isIdle()) {
            return;
        }
        pausePlayer();
        mediaPlayer.reset();
        state = STATE_IDLE;
    }

    private void learnTime(final long over) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("seconds", (over - spUtils.getLong("start", over)) / 1000 + "");
                Log.v("oooooooooo","-------------"+(over - spUtils.getLong("start", over)) / 1000);
                HttpUtils.doPost(Urls.HOST_USAGE, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            if ("ok".equals(response.body().string())) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadStats();
                                    }
                                });
                            }
                        } catch (Exception e) {

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
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                HttpUtils.doPost(Urls.HOST_STATS/* + "?authToken=" + spUtils.getString("authToken", "")*/, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        try {
                            final String string1 = response.body().string();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //loadUser();//更新用户信息（钱）
                                    StatsBean statsBean = MineJsonUtils.readStatsBean(string1);
                                    spUtils.put("stats", string1);
                                    EventBus.getDefault().postSticky(statsBean);//刷新状态
                                    EventBus.getDefault().postSticky(new RefreshTaskBean(true));//刷新任务列表
                                    EventBus.getDefault().postSticky(new RefreshWalletBean(true));//刷新钱包
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

/*    private void loadUser() {
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
                        final String string;
                        try {
                            string = response.body().string();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    spUtils.put("auth", string);
                                    UserBean userBean = MineJsonUtils.readUserBean(string);
                                    EventBus.getDefault().postSticky(userBean);//刷新钱包，我的。
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }*/

    public void next() {
        if (musicList.isEmpty()) {
            return;
        }

        PlayModeEnum mode = PlayModeEnum.valueOf(Preferences.getPlayMode());
        switch (mode) {
            case SHUFFLE:
                play(new Random().nextInt(musicList.size()));
                break;
            case SINGLE:
                play(getPlayPosition());
                break;
            case LOOP:
            default:
                play(getPlayPosition() + 1);
                break;
        }
    }

    public void prev() {
        if (musicList.isEmpty()) {
            return;
        }

        PlayModeEnum mode = PlayModeEnum.valueOf(Preferences.getPlayMode());
        switch (mode) {
            case SHUFFLE:
                play(new Random().nextInt(musicList.size()));
                break;
            case SINGLE:
                play(getPlayPosition());
                break;
            case LOOP:
            default:
                play(getPlayPosition() - 1);
                break;
        }
    }

    /**
     * 跳转到指定的时间位置
     *
     * @param msec 时间
     */
    public void seekTo(int msec) {
        if (isPlaying() || isPausing()) {
            mediaPlayer.seekTo(msec);
            MediaSessionManager.get().updatePlaybackState();
            for (OnPlayerEventListener listener : listeners) {
                listener.onPublish(msec);
            }
        }
    }

    private Runnable mPublishRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying()) {
                for (OnPlayerEventListener listener : listeners) {
                    listener.onPublish(mediaPlayer.getCurrentPosition());
                }
            }
            handler.postDelayed(this, TIME_UPDATE);
        }
    };

    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    public long getAudioPosition() {
        if (isPlaying() || isPausing()) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public BookMusicDetailBean getPlayMusic() {
        if (musicList.isEmpty()) {
            return null;
        }
        return musicList.get(getPlayPosition());
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public List<BookMusicDetailBean> getMusicList() {
        return musicList;
    }

    public boolean isPlaying() {
        return state == STATE_PLAYING;
    }

    public boolean isPausing() {
        return state == STATE_PAUSE;
    }

    public boolean isPreparing() {
        return state == STATE_PREPARING;
    }

    public boolean isIdle() {
        return state == STATE_IDLE;
    }

    public int getPlayPosition() {
        int position = Preferences.getPlayPosition();
        if (position < 0 || position >= musicList.size()) {
            position = 0;
            Preferences.savePlayPosition(position);
        }
        return position;
    }

    private void setPlayPosition(int position) {
        Preferences.savePlayPosition(position);
    }
}
