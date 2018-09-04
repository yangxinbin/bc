package com.mango.bc.homepage.bean;

/**
 * Created by admin on 2018/9/4.
 */

public class VipType {
    private String title;
    private String Flagtitle;
    private String detail;
    private String unbc;
    private String bc;

    public VipType(String title, String flagtitle, String detail, String unbc, String bc) {
        this.title = title;
        Flagtitle = flagtitle;
        this.detail = detail;
        this.unbc = unbc;
        this.bc = bc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlagtitle() {
        return Flagtitle;
    }

    public void setFlagtitle(String flagtitle) {
        Flagtitle = flagtitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUnbc() {
        return unbc;
    }

    public void setUnbc(String unbc) {
        this.unbc = unbc;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }
}
