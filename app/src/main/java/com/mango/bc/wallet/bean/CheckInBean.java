package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/9/18.
 */

public class CheckInBean {


    /**
     * id : 5b8a3d4b04440c0a48a33a05
     * count : 1
     * date : 1
     * todayCheckedIn : true
     */

    private String id;
    private int count;
    private int date;
    private boolean todayCheckedIn;

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

    public boolean isTodayCheckedIn() {
        return todayCheckedIn;
    }

    public void setTodayCheckedIn(boolean todayCheckedIn) {
        this.todayCheckedIn = todayCheckedIn;
    }
}
