package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/9/20.
 */

public class RefreshTaskBean {
    private Boolean ifTaskRefresh;

    public RefreshTaskBean(Boolean ifTaskRefresh) {
        this.ifTaskRefresh = ifTaskRefresh;
    }

    public Boolean getIfTaskRefresh() {
        return ifTaskRefresh;
    }

    public void setIfTaskRefresh(Boolean ifTaskRefresh) {
        this.ifTaskRefresh = ifTaskRefresh;
    }
}
