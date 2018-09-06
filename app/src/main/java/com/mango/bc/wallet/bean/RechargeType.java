package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/9/4.
 */

public class RechargeType {
    private String bc;
    private String money;

    public RechargeType(String bc, String money) {
        this.bc = bc;
        this.money = money;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
