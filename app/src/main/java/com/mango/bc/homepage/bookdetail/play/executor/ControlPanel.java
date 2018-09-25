package com.mango.bc.homepage.bookdetail.play.executor;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.homepage.bookdetail.play.global.Notifier;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.bookdetail.play.service.OnPlayerEventListener;
import com.mango.bc.homepage.bookdetail.play.utils.Bind;
import com.mango.bc.homepage.bookdetail.play.utils.ViewBinder;


/**
 * Created by hzwangchenyan on 2018/1/26.
 */
public class ControlPanel implements View.OnClickListener, OnPlayerEventListener {
    @Bind(R.id.tv_play_bar_title)
    private TextView tvPlayBarTitle;
    @Bind(R.id.tv_play_bar_artist)
    private TextView tvPlayBarArtist;
    @Bind(R.id.iv_play_bar_play)
    private ImageView ivPlayBarPlay;
    @Bind(R.id.iv_close_bar_play)
    private ImageView ivPlayBarClose;
    @Bind(R.id.fl_play_bar)
    FrameLayout flPlayBar;
/*    @Bind(R.id.pb_play_bar)
    private ProgressBar mProgressBar;*/

    public ControlPanel(View view) {
        ViewBinder.bind(this, view);
        ivPlayBarPlay.setOnClickListener(this);
        ivPlayBarClose.setOnClickListener(this);
        onChange(AudioPlayer.get().getPlayMusic());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play_bar_play:
                AudioPlayer.get().playPause();
                break;
            case R.id.iv_close_bar_play:
                flPlayBar.setVisibility(View.GONE);
                AudioPlayer.get().stopPlayer();
                Notifier.get().cancelAll();
                break;
        }
    }

    @Override
    public void onChange(BookMusicDetailBean music) {
        if (music == null) {
            return;
        }
        tvPlayBarTitle.setText(music.getTitle());
        tvPlayBarArtist.setText(music.getName()+" | "+music.getMp3Name());
        ivPlayBarPlay.setSelected(AudioPlayer.get().isPlaying() || AudioPlayer.get().isPreparing());
        if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPreparing())
            flPlayBar.setVisibility(View.VISIBLE);
/*        mProgressBar.setMax((int) music.getDuration());
        mProgressBar.setProgress((int) AudioPlayer.get().getAudioPosition());*/
    }

    @Override
    public void onPlayerStart() {
        ivPlayBarPlay.setSelected(true);
    }

    @Override
    public void onPlayerPause() {
        ivPlayBarPlay.setSelected(false);
    }

    @Override
    public void onPublish(int progress) {
        //mProgressBar.setProgress(progress);
    }

    @Override
    public void onBufferingUpdate(int percent) {
    }
}
