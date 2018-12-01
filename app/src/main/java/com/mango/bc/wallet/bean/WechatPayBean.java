package com.mango.bc.wallet.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2018/7/3.
 */

public class WechatPayBean {

    /**
     * id : 5c02478e5976860d88748188
     * uuid : feab44a1e6954540b9ab2e47
     * userId : 5b8a3d4b04440c0a48a33a05
     * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
     * ppCoin : 0.01
     * dollars : 0.01
     * type : become_node
     * status : 0
     * payMap : {"appid":"wxb93480bda524daa0","partnerid":"1515082901","prepayid":"wx011634227768834a8ac4fba30206120284","package":"Sign=WXPay","noncestr":"50e84462983f4e24956ecc13128c4999","timestamp":"1543653262","sign":"B40E119F00ED98862CE5705B42266A659ED303B1F6876CDF356EF00E5929EEB6","out_trade_no":"feab44a1e6954540b9ab2e47"}
     */

    private String id;
    private String uuid;
    private String userId;
    private String walletAddress;
    private double ppCoin;
    private double dollars;
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

    public double getPpCoin() {
        return ppCoin;
    }

    public void setPpCoin(double ppCoin) {
        this.ppCoin = ppCoin;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
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

        /**
         * appid : wxb93480bda524daa0
         * partnerid : 1515082901
         * prepayid : wx011634227768834a8ac4fba30206120284
         * package : Sign=WXPay
         * noncestr : 50e84462983f4e24956ecc13128c4999
         * timestamp : 1543653262
         * sign : B40E119F00ED98862CE5705B42266A659ED303B1F6876CDF356EF00E5929EEB6
         * out_trade_no : feab44a1e6954540b9ab2e47
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
    }
}
