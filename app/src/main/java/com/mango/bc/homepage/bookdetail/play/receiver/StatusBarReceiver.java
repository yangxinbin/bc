package com.mango.bc.homepage.bookdetail.play.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.mango.bc.homepage.bookdetail.play.global.Notifier;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;


/**
 * Created by wcy on 2017/4/18.
 */
public class StatusBarReceiver extends BroadcastReceiver {
    public static final String ACTION_STATUS_BAR = "me.bc.music.STATUS_BAR_ACTIONS";
    public static final String EXTRA = "extra";
    public static final String EXTRA_NEXT = "next";
    public static final String EXTRA_PLAY_PAUSE = "play_pause";
    public static final String EXTRA_PREV = "prev";
    public static final String EXTRA_STOP = "stop";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }

        String extra = intent.getStringExtra(EXTRA);
        if (TextUtils.equals(extra, EXTRA_NEXT)) {
            Log.v("ppppppppppp","----n---");
            AudioPlayer.get().next();
        } else if (TextUtils.equals(extra, EXTRA_PLAY_PAUSE)) {
            Log.v("ppppppppppp","----playPause---");
            AudioPlayer.get().playPause();
        } else if (TextUtils.equals(extra, EXTRA_PREV)) {
            Log.v("ppppppppppp","----p---");
            AudioPlayer.get().prev();
        } else if (TextUtils.equals(extra, EXTRA_STOP)) {
            Log.v("ppppppppppp","----s---");
            AudioPlayer.get().playPause();
            Notifier.get().cancelAll();
            //AudioPlayer.get().stopPlayer();
        }
    }
}
