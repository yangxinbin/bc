package com.mango.bc.bookcase.bean;

/**
 * Created by Administrator on 2018/9/29 0029.
 */

public class RefreshBookCaseBean {
    private boolean refreshPaid;
    private boolean refreshCompetitive;
    private boolean refreshFree;

    public RefreshBookCaseBean(boolean refreshPaid, boolean refreshCompetitive, boolean refreshFree) {
        this.refreshPaid = refreshPaid;
        this.refreshCompetitive = refreshCompetitive;
        this.refreshFree = refreshFree;
    }

    public boolean isRefreshPaid() {
        return refreshPaid;
    }

    public void setRefreshPaid(boolean refreshPaid) {
        this.refreshPaid = refreshPaid;
    }

    public boolean isRefreshCompetitive() {
        return refreshCompetitive;
    }

    public void setRefreshCompetitive(boolean refreshCompetitive) {
        this.refreshCompetitive = refreshCompetitive;
    }

    public boolean isRefreshFree() {
        return refreshFree;
    }

    public void setRefreshFree(boolean refreshFree) {
        this.refreshFree = refreshFree;
    }
}
