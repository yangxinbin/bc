package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/11/9.
 */

public class PayMes {
    private boolean PaySuccess;

    public PayMes(boolean paySuccess) {
        PaySuccess = paySuccess;
    }

    public boolean isPaySuccess() {
        return PaySuccess;
    }

    public void setPaySuccess(boolean paySuccess) {
        PaySuccess = paySuccess;
    }
}
