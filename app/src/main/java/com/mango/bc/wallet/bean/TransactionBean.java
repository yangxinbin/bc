package com.mango.bc.wallet.bean;

/**
 * Created by admin on 2018/9/20.
 */

public class TransactionBean {

    /**
     * id : 5ba309482557b90430e990af
     * type : mission_reward
     * ppCoin : 0.1
     * meta : LIKE
     * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
     * timestamp : 1537411400670
     * deleted : null
     */

    private String id;
    private String type;
    private double ppCoin;
    private String meta;
    private String walletAddress;
    private long timestamp;
    private Object deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPpCoin() {
        return ppCoin;
    }

    public void setPpCoin(double ppCoin) {
        this.ppCoin = ppCoin;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getDeleted() {
        return deleted;
    }

    public void setDeleted(Object deleted) {
        this.deleted = deleted;
    }
}
