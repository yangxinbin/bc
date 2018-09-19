package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/9/18.
 */

public class CheckInBean {


    /**
     * id : 5b8a3d4b04440c0a48a33a05
     * count : 1
     * date : 20180918
     * canCheckIn : true
     */

    private String id;
    private int count;
    private int date;
    private boolean canCheckIn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isCanCheckIn() {
        return canCheckIn;
    }

    public void setCanCheckIn(boolean canCheckIn) {
        this.canCheckIn = canCheckIn;
    }
}
