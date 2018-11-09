package com.mango.bc.wallet.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2018/7/3.
 */

public class WechatPayBean {

    /**
     * id : 5be54ba47f701105b8150f2b
     * uuid : ce4c2cbbbe0e42cb9105bf42
     * userId : 5b8a3d4b04440c0a48a33a05
     * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
     * ppCoin : 10
     * dollars : 10
     * type : topup
     * status : 0
     * payMap : {"appid":"wxb93480bda524daa0","partnerid":"1515082901","prepayid":"wx0916560470333479455ec23b3865438202","package":"Sign=WXPay","noncestr":"bc02d3afdd1246c59be0f40c8617fb8d","timestamp":"1541753764","sign":"DEC71D4BE0BD49E44AA78FA677CBA7C1BB69034718E2CCFEC0A9EECD23A3F0F1","out_trade_no":"ce4c2cbbbe0e42cb9105bf42"}
     */

    private String id;
    private String uuid;
    private String userId;
    private String walletAddress;
    private int ppCoin;
    private int dollars;
    private String type;
    private int status;
    private PayMapBean payMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public int getPpCoin() {
        return ppCoin;
    }

    public void setPpCoin(int ppCoin) {
        this.ppCoin = ppCoin;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PayMapBean getPayMap() {
        return payMap;
    }

    public void setPayMap(PayMapBean payMap) {
        this.payMap = payMap;
    }

    public static class PayMapBean {
        /**
         * appid : wxb93480bda524daa0
         * partnerid : 1515082901
         * prepayid : wx0916560470333479455ec23b3865438202
         * package : Sign=WXPay
         * noncestr : bc02d3afdd1246c59be0f40c8617fb8d
         * timestamp : 1541753764
         * sign : DEC71D4BE0BD49E44AA78FA677CBA7C1BB69034718E2CCFEC0A9EECD23A3F0F1
         * out_trade_no : ce4c2cbbbe0e42cb9105bf42
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;
        private String out_trade_no;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        @Override
        public String toString() {
            return "PayMapBean{" +
                    "appid='" + appid + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", packageX='" + packageX + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", sign='" + sign + '\'' +
                    ", out_trade_no='" + out_trade_no + '\'' +
                    '}';
        }
    }
}
