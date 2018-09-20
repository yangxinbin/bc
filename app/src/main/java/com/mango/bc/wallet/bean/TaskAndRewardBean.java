package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/9/20.
 */

public class TaskAndRewardBean {

    /**
     * count : 64
     * type : LIKE
     * earning : 2.1
     */

    private int count;
    private String type;
    private double earning;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }
}
