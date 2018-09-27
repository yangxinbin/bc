package com.mango.bc.homepage.bookdetail.bean;

/**
 * Created by admin on 2018/9/27.
 */

public class PlayPauseBean {
    private boolean isPause;

    public PlayPauseBean(boolean isPause) {
        this.isPause = isPause;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}
