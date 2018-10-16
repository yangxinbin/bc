package com.mango.bc.mine.bean;

/**
 * Created by admin on 2018/9/28.
 */

public class StatsBean {

    /**
     * createDate : 1538015317854
     * vipGetMemberBooks : 0
     * buyMemberMoneySaved : 0.0
     * buyPaidBookMoneySaved : 0.0
     * paidBooks : 1
     * vipGetBooks : 17
     * totalDuration : 0.0
     * ppCoinEarned : 16.02
     * lastCalculateDate : 1
     */

    private long createDate;
    private int vipGetMemberBooks;
    private double buyMemberMoneySaved;
    private double buyPaidBookMoneySaved;
    private int paidBooks;
    private int vipGetBooks;
    private double totalDuration;
    private double paidBookSaving;
    private double memberBookSaving;
    private double ppCoinEarned;
    private int lastCalculateDate;
    private int ageDays;
    private int updatedOn;

    public double getPaidBookSaving() {
        return paidBookSaving;
    }

    public void setPaidBookSaving(double paidBookSaving) {
        this.paidBookSaving = paidBookSaving;
    }

    public double getMemberBookSaving() {
        return memberBookSaving;
    }

    public void setMemberBookSaving(double memberBookSaving) {
        this.memberBookSaving = memberBookSaving;
    }

    public int getAgeDays() {
        return ageDays;
    }

    public void setAgeDays(int ageDays) {
        this.ageDays = ageDays;
    }

    public int getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(int updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getVipGetMemberBooks() {
        return vipGetMemberBooks;
    }

    public void setVipGetMemberBooks(int vipGetMemberBooks) {
        this.vipGetMemberBooks = vipGetMemberBooks;
    }

    public double getBuyMemberMoneySaved() {
        return buyMemberMoneySaved;
    }

    public void setBuyMemberMoneySaved(double buyMemberMoneySaved) {
        this.buyMemberMoneySaved = buyMemberMoneySaved;
    }

    public double getBuyPaidBookMoneySaved() {
        return buyPaidBookMoneySaved;
    }

    public void setBuyPaidBookMoneySaved(double buyPaidBookMoneySaved) {
        this.buyPaidBookMoneySaved = buyPaidBookMoneySaved;
    }

    public int getPaidBooks() {
        return paidBooks;
    }

    public void setPaidBooks(int paidBooks) {
        this.paidBooks = paidBooks;
    }

    public int getVipGetBooks() {
        return vipGetBooks;
    }

    public void setVipGetBooks(int vipGetBooks) {
        this.vipGetBooks = vipGetBooks;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public double getPpCoinEarned() {
        return ppCoinEarned;
    }

    public void setPpCoinEarned(double ppCoinEarned) {
        this.ppCoinEarned = ppCoinEarned;
    }

    public int getLastCalculateDate() {
        return lastCalculateDate;
    }

    public void setLastCalculateDate(int lastCalculateDate) {
        this.lastCalculateDate = lastCalculateDate;
    }
}