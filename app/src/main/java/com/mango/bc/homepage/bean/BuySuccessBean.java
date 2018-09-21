package com.mango.bc.homepage.bean;

/**
 * Created by admin on 2018/9/21.
 */

public class BuySuccessBean {
    private boolean IsBuySuccess;

    public BuySuccessBean(boolean isBuySuccess) {
        IsBuySuccess = isBuySuccess;
    }

    public boolean getBuySuccess() {
        return IsBuySuccess;
    }

    public void setBuySuccess(boolean buySuccess) {
        IsBuySuccess = buySuccess;
    }
}
