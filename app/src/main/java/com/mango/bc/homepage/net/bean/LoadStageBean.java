package com.mango.bc.homepage.net.bean;

/**
 * Created by admin on 2018/9/10.
 */

public class LoadStageBean {
    private int newestBookPage;

    public LoadStageBean(int newestBookPage) {
        this.newestBookPage = newestBookPage;
    }

    public int getNewestBookPage() {
        return newestBookPage;
    }

    public void setNewestBookPage(int newestBookPage) {
        this.newestBookPage = newestBookPage;
    }
}
