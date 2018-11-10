package com.mango.bc.wallet.bean;

/**
 * Created by Administrator on 2018/11/10 0010.
 */

public class RefreshWalletBean {
    private Boolean ifWalletRefresh;

    public RefreshWalletBean(Boolean ifWalletRefresh) {
        this.ifWalletRefresh = ifWalletRefresh;
    }

    public Boolean getIfWalletRefresh() {
        return ifWalletRefresh;
    }

    public void setIfWalletRefresh(Boolean ifWalletRefresh) {
        this.ifWalletRefresh = ifWalletRefresh;
    }
}
