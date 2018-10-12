package com.mango.bc.mine.bean;

/**
 * Created by admin on 2018/10/12.
 */

public class RefreshMemberBean {
    private boolean refresh;

    public RefreshMemberBean(boolean refresh) {
        this.refresh = refresh;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }
}
