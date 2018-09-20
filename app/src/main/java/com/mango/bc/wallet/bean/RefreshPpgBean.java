package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/9/20.
 */

public class RefreshPpgBean {
    private Boolean ifRefreshPpg;

    public RefreshPpgBean(Boolean ifRefreshPpg) {
        this.ifRefreshPpg = ifRefreshPpg;
    }

    public Boolean getIfRefreshPpg() {
        return ifRefreshPpg;
    }

    public void setIfRefreshPpg(Boolean ifRefreshPpg) {
        this.ifRefreshPpg = ifRefreshPpg;
    }
}
