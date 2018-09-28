package com.mango.bc.mine.bean;

/**
 * Created by admin on 2018/9/17.
 */

public class UserBean {

    /**
     * id : 5b8a3d4b04440c0a48a33a05
     * username : oXhi94jQkXPovBsqEs0B8QKsbM0A
     * openId : oXhi94jQkXPovBsqEs0B8QKsbM0A
     * alias : 杨鑫斌
     * type : general
     * avator : {"id":"5bae413c2557b905d88888e5","alias":"avator","fileName":"15381466204473764.avator","contentType":"image/jpeg","size":1024,"createdOn":1538146620447}
     * authToken : eyJhbGciOiJIUzUxMiJ9.eyJhdWRpZW5jZSI6Im1vYmlsZSIsImNyZWF0ZWQiOjE1MzgxNDg2NDgyMTIsImFsaWFzIjoi5p2o6ZGr5paMIiwiaWQiOiI1YjhhM2Q0YjA0NDQwYzBhNDhhMzNhMDUiLCJ0eXBlIjoiZ2VuZXJhbCIsIndhbGxldEFkZHJlc3MiOiIweGU3MmUzODdhZjEyZTA4NmFlZWNjOGVmMTljNzcxY2M4IiwiZXhwIjo0MTMwMTQ4NjQ4LCJ1c2VybmFtZSI6Im9YaGk5NGpRa1hQb3ZCc3FFczBCOFFLc2JNMEEifQ.BVoaTg6aVbfh8OdJWwA4gLw8df8cmcH3Vcs7wjXsyvCT2LeOLx3YaySO0K6-qAuDYte_KaXeuJMud3fbE_8D4w
     * agencyInfo : {"realName":"","company":"","position":"","phone":"","recommendedBy":"","level":0,"status":0}
     * wallet : {"id":"5b8a3d4b04440c0a48a33a05","blockChainWalletAddress":"","walletAddress":"0xe72e387af12e086aeecc8ef19c771cc8","ppCoins":9542.37}
     * billing : {"startOn":1538135914379,"endOn":1543406314381,"billingAmount":28,"billingType":"monthly","auto":true}
     * vip : true
     * stats : {"createDate":1538015317854,"vipGetMemberBooks":0,"buyMemberMoneySaved":0,"buyPaidBookMoneySaved":0,"paidBooks":1,"vipGetBooks":29,"totalDuration":0,"ppCoinEarned":20.85,"lastCalculateDate":1538015317854}
     * status : 0
     * createdOn : 1535786315137
     */

    private String id;
    private String username;
    private String openId;
    private String alias;
    private String type;
    private AvatorBean avator;
    private String authToken;
    private AgencyInfoBean agencyInfo;
    private WalletBean wallet;
    private BillingBean billing;
    private boolean vip;
    private StatsBean stats;
    private int status;
    private long createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AvatorBean getAvator() {
        return avator;
    }

    public void setAvator(AvatorBean avator) {
        this.avator = avator;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public AgencyInfoBean getAgencyInfo() {
        return agencyInfo;
    }

    public void setAgencyInfo(AgencyInfoBean agencyInfo) {
        this.agencyInfo = agencyInfo;
    }

    public WalletBean getWallet() {
        return wallet;
    }

    public void setWallet(WalletBean wallet) {
        this.wallet = wallet;
    }

    public BillingBean getBilling() {
        return billing;
    }

    public void setBilling(BillingBean billing) {
        this.billing = billing;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public StatsBean getStats() {
        return stats;
    }

    public void setStats(StatsBean stats) {
        this.stats = stats;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public static class AvatorBean {
        /**
         * id : 5bae413c2557b905d88888e5
         * alias : avator
         * fileName : 15381466204473764.avator
         * contentType : image/jpeg
         * size : 1024
         * createdOn : 1538146620447
         */

        private String id;
        private String alias;
        private String fileName;
        private String contentType;
        private int size;
        private long createdOn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(long createdOn) {
            this.createdOn = createdOn;
        }
    }

    public static class AgencyInfoBean {
        /**
         * realName :
         * company :
         * position :
         * phone :
         * recommendedBy :
         * level : 0
         * status : 0
         */

        private String realName;
        private String company;
        private String position;
        private String phone;
        private String recommendedBy;
        private int level;
        private int status;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRecommendedBy() {
            return recommendedBy;
        }

        public void setRecommendedBy(String recommendedBy) {
            this.recommendedBy = recommendedBy;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class WalletBean {
        /**
         * id : 5b8a3d4b04440c0a48a33a05
         * blockChainWalletAddress :
         * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
         * ppCoins : 9542.37
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

    public static class BillingBean {
        /**
         * startOn : 1538135914379
         * endOn : 1543406314381
         * billingAmount : 28
         * billingType : monthly
         * auto : true
         */

        private long startOn;
        private long endOn;
        private int billingAmount;
        private String billingType;
        private boolean auto;

        public long getStartOn() {
            return startOn;
        }

        public void setStartOn(long startOn) {
            this.startOn = startOn;
        }

        public long getEndOn() {
            return endOn;
        }

        public void setEndOn(long endOn) {
            this.endOn = endOn;
        }

        public int getBillingAmount() {
            return billingAmount;
        }

        public void setBillingAmount(int billingAmount) {
            this.billingAmount = billingAmount;
        }

        public String getBillingType() {
            return billingType;
        }

        public void setBillingType(String billingType) {
            this.billingType = billingType;
        }

        public boolean isAuto() {
            return auto;
        }

        public void setAuto(boolean auto) {
            this.auto = auto;
        }
    }

    public static class StatsBean {
        /**
         * createDate : 1538015317854
         * vipGetMemberBooks : 0
         * buyMemberMoneySaved : 0
         * buyPaidBookMoneySaved : 0
         * paidBooks : 1
         * vipGetBooks : 29
         * totalDuration : 0
         * ppCoinEarned : 20.85
         * lastCalculateDate : 1538015317854
         */

        private long createDate;
        private int vipGetMemberBooks;
        private int buyMemberMoneySaved;
        private int buyPaidBookMoneySaved;
        private int paidBooks;
        private int vipGetBooks;
        private int totalDuration;
        private double ppCoinEarned;
        private long lastCalculateDate;

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

        public int getBuyMemberMoneySaved() {
            return buyMemberMoneySaved;
        }

        public void setBuyMemberMoneySaved(int buyMemberMoneySaved) {
            this.buyMemberMoneySaved = buyMemberMoneySaved;
        }

        public int getBuyPaidBookMoneySaved() {
            return buyPaidBookMoneySaved;
        }

        public void setBuyPaidBookMoneySaved(int buyPaidBookMoneySaved) {
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

        public int getTotalDuration() {
            return totalDuration;
        }

        public void setTotalDuration(int totalDuration) {
            this.totalDuration = totalDuration;
        }

        public double getPpCoinEarned() {
            return ppCoinEarned;
        }

        public void setPpCoinEarned(double ppCoinEarned) {
            this.ppCoinEarned = ppCoinEarned;
        }

        public long getLastCalculateDate() {
            return lastCalculateDate;
        }

        public void setLastCalculateDate(long lastCalculateDate) {
            this.lastCalculateDate = lastCalculateDate;
        }
    }
}
