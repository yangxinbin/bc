package com.mango.bc.homepage.bean;

/**
 * Created by admin on 2018/9/28.
 */

public class VipPackageBean {

    /**
     * id : 5b9665e1c6987a229ca08c8f
     * name : 月套餐
     * firstTimeFee : 9.9
     * manualBillingFee : 39.9
     * autoBillingFee : 28.0
     * billingType : monthly
     */

    private String id;
    private String name;
    private double firstTimeFee;
    private double manualBillingFee;
    private double autoBillingFee;
    private String billingType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFirstTimeFee() {
        return firstTimeFee;
    }

    public void setFirstTimeFee(double firstTimeFee) {
        this.firstTimeFee = firstTimeFee;
    }

    public double getManualBillingFee() {
        return manualBillingFee;
    }

    public void setManualBillingFee(double manualBillingFee) {
        this.manualBillingFee = manualBillingFee;
    }

    public double getAutoBillingFee() {
        return autoBillingFee;
    }

    public void setAutoBillingFee(double autoBillingFee) {
        this.autoBillingFee = autoBillingFee;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }
}
