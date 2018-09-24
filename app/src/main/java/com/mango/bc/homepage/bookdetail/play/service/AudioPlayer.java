package com.mango.bc.homepage.bookdetail.play.service;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.homepage.bookdetail.play.enums.PlayModeEnum;
import com.mango.bc.homepage.bookdetail.play.global.Notifier;
import com.mango.bc.homepage.bookdetail.play.preference.Preferences;
import com.mango.bc.homepage.bookdetail.play.receiver.NoisyAudioStreamReceiver;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    private List<BookMusicDetailBean> musicList;
    private final List<OnPlayerEventListener> listeners = new ArrayList<>();
    private int state = STATE_IDLE;

    public static AudioPlayer get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static AudioPlayer instance = new AudioPlayer();
    }

    private AudioPlayer() {
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
        //musicList = DBManager.get().getMusicDao().queryBuilder().build().list();
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
            AppUtils.showToast(context,"当前歌曲无法播放");
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
        if (isPreparing()) {
            stopPlayer();
        } else if (isPlaying()) {
            pausePlayer();
        } else if (isPausing()) {
            startPlayer();
        } else {
            play(getPlayPosition());
        }
    }

    public void startPlayer() {
        if (!isPreparing() && !isPausing()) {
            return;
        }

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
        context.unregisterReceiver(noisyReceiver);
        if (abandonAudioFocus) {
            audioFocusManager.abandonAudioFocus();
        }

        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerPause();
        }
    }

    public void stopPlayer() {
        if (isIdle()) {
            return;
        }

        pausePlayer();
        mediaPlayer.reset();
        state = STATE_IDLE;
    }

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
