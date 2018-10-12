package com.mango.bc.homepage.bean;

/**
 * Created by admin on 2018/10/12.
 */

public class CollageTypeBean {
    private String num;
    private String ppg;

    public CollageTypeBean(String num, String ppg) {
        this.num = num;
        this.ppg = ppg;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPpg() {
        return ppg;
    }

    public void setPpg(String ppg) {
        this.ppg = ppg;
    }
}
