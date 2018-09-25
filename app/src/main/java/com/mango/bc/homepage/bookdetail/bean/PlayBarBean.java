package com.mango.bc.homepage.bookdetail.bean;

/**
 * Created by Administrator on 2018/9/26 0026.
 */

public class PlayBarBean {
    private boolean showBar;

    public PlayBarBean(boolean showBar) {
        this.showBar = showBar;
    }

    public boolean isShowBar() {
        return showBar;
    }

    public void setShowBar(boolean showBar) {
        this.showBar = showBar;
    }
}
