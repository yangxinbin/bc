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
    private String type;

    public VipType(String title, String flagtitle, String detail, String unbc, String bc, String type) {
        this.title = title;
        Flagtitle = flagtitle;
        this.detail = detail;
        this.unbc = unbc;
        this.bc = bc;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
