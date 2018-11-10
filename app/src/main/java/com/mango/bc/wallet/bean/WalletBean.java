package com.mango.bc.wallet.bean;

/**
 * Created by Administrator on 2018/11/10 0010.
 */

public class WalletBean {

    /**
     * id : 5b8a3d4b04440c0a48a33a05
     * blockChainWalletAddress :
     * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
     * ppCoins : 79734.1
     */

    private String id;
    private String blockChainWalletAddress;
    private String walletAddress;
    private double ppCoins;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlockChainWalletAddress() {
        return blockChainWalletAddress;
    }

    public void setBlockChainWalletAddress(String blockChainWalletAddress) {
        this.blockChainWalletAddress = blockChainWalletAddress;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public double getPpCoins() {
        return ppCoins;
    }

    public void setPpCoins(double ppCoins) {
        this.ppCoins = ppCoins;
    }
}
