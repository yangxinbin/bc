package com.mango.bc.wallet.bean;

/**
 * Created by Administrator on 2018/9/28 0028.
 */

public class CheckBean {

    /**
     * id : 5b8a3d4b04440c0a48a33a05
     * count : 2
     * date : 20180927
     * todayCheckedIn : null
     */

    private String id;
    private int count;
    private int date;
    private Object todayCheckedIn;

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

    public Object getTodayCheckedIn() {
        return todayCheckedIn;
    }

    public void setTodayCheckedIn(Object todayCheckedIn) {
        this.todayCheckedIn = todayCheckedIn;
    }
}
