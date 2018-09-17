package com.mango.bc.mine.bean;

/**
 * Created by admin on 2018/9/17.
 */

public class UserBean {

    /**
     * agencyInfo : {"company":"string","level":0,"phone":"string","position":"string","realName":"string","recommendedBy":"string","status":0}
     * alias : string
     * authToken : string
     * avator : {"alias":"string","contentType":"string","createdOn":1537177161382,"fileName":"string","id":"string","size":0}
     * billing : {"auto":true,"billingAmount":0,"billingType":"monthly","endOn":0,"startOn":0}
     * createdOn : 1537177161382
     * id : string
     * openId : string
     * stats : {"buyMemberMoneySaved":0,"buyPaidBookMoneySaved":0,"createDate":1536753983099,"lastCalculateDate":1536753983099,"paidBooks":0,"ppCoinEarned":0,"totalDuration":0,"vipGetBooks":0,"vipGetMemberBooks":0}
     * status : string
     * type : general
     * username : string
     * vip : true
     * wallet : {"blockChainWalletAddress":"string","id":"string","ppCoins":0,"walletAddress":"string"}
     */

    private AgencyInfoBean agencyInfo;
    private String alias;
    private String authToken;
    private AvatorBean avator;
    private BillingBean billing;
    private long createdOn;
    private String id;
    private String openId;
    private StatsBean stats;
    private String status;
    private String type;
    private String username;
    private boolean vip;
    private WalletBean wallet;

    public AgencyInfoBean getAgencyInfo() {
        return agencyInfo;
    }

    public void setAgencyInfo(AgencyInfoBean agencyInfo) {
        this.agencyInfo = agencyInfo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public AvatorBean getAvator() {
        return avator;
    }

    public void setAvator(AvatorBean avator) {
        this.avator = avator;
    }

    public BillingBean getBilling() {
        return billing;
    }

    public void setBilling(BillingBean billing) {
        this.billing = billing;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public StatsBean getStats() {
        return stats;
    }

    public void setStats(StatsBean stats) {
        this.stats = stats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public WalletBean getWallet() {
        return wallet;
    }

    public void setWallet(WalletBean wallet) {
        this.wallet = wallet;
    }

    public static class AgencyInfoBean {
        /**
         * company : string
         * level : 0
         * phone : string
         * position : string
         * realName : string
         * recommendedBy : string
         * status : 0
         */

        private String company;
        private int level;
        private String phone;
        private String position;
        private String realName;
        private String recommendedBy;
        private int status;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRecommendedBy() {
            return recommendedBy;
        }

        public void setRecommendedBy(String recommendedBy) {
            this.recommendedBy = recommendedBy;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class AvatorBean {
        /**
         * alias : string
         * contentType : string
         * createdOn : 1537177161382
         * fileName : string
         * id : string
         * size : 0
         */

        private String alias;
        private String contentType;
        private long createdOn;
        private String fileName;
        private String id;
        private int size;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public long getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(long createdOn) {
            this.createdOn = createdOn;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class BillingBean {
        /**
         * auto : true
         * billingAmount : 0
         * billingType : monthly
         * endOn : 0
         * startOn : 0
         */

        private boolean auto;
        private int billingAmount;
        private String billingType;
        private int endOn;
        private int startOn;

        public boolean isAuto() {
            return auto;
        }

        public void setAuto(boolean auto) {
            this.auto = auto;
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

        public int getEndOn() {
            return endOn;
        }

        public void setEndOn(int endOn) {
            this.endOn = endOn;
        }

        public int getStartOn() {
            return startOn;
        }

        public void setStartOn(int startOn) {
            this.startOn = startOn;
        }
    }

    public static class StatsBean {
        /**
         * buyMemberMoneySaved : 0
         * buyPaidBookMoneySaved : 0
         * createDate : 1536753983099
         * lastCalculateDate : 1536753983099
         * paidBooks : 0
         * ppCoinEarned : 0.0
         * totalDuration : 0.0
         * vipGetBooks : 0
         * vipGetMemberBooks : 0
         */

        private int buyMemberMoneySaved;
        private int buyPaidBookMoneySaved;
        private long createDate;
        private long lastCalculateDate;
        private int paidBooks;
        private double ppCoinEarned;
        private double totalDuration;
        private int vipGetBooks;
        private int vipGetMemberBooks;

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

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public long getLastCalculateDate() {
            return lastCalculateDate;
        }

        public void setLastCalculateDate(long lastCalculateDate) {
            this.lastCalculateDate = lastCalculateDate;
        }

        public int getPaidBooks() {
            return paidBooks;
        }

        public void setPaidBooks(int paidBooks) {
            this.paidBooks = paidBooks;
        }

        public double getPpCoinEarned() {
            return ppCoinEarned;
        }

        public void setPpCoinEarned(double ppCoinEarned) {
            this.ppCoinEarned = ppCoinEarned;
        }

        public double getTotalDuration() {
            return totalDuration;
        }

        public void setTotalDuration(double totalDuration) {
            this.totalDuration = totalDuration;
        }

        public int getVipGetBooks() {
            return vipGetBooks;
        }

        public void setVipGetBooks(int vipGetBooks) {
            this.vipGetBooks = vipGetBooks;
        }

        public int getVipGetMemberBooks() {
            return vipGetMemberBooks;
        }

        public void setVipGetMemberBooks(int vipGetMemberBooks) {
            this.vipGetMemberBooks = vipGetMemberBooks;
        }
    }

    public static class WalletBean {
        /**
         * blockChainWalletAddress : string
         * id : string
         * ppCoins : 0.0
         * walletAddress : string
         */

        private String blockChainWalletAddress;
        private String id;
        private double ppCoins;
        private String walletAddress;

        public String getBlockChainWalletAddress() {
            return blockChainWalletAddress;
        }

        public void setBlockChainWalletAddress(String blockChainWalletAddress) {
            this.blockChainWalletAddress = blockChainWalletAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getPpCoins() {
            return ppCoins;
        }

        public void setPpCoins(double ppCoins) {
            this.ppCoins = ppCoins;
        }

        public String getWalletAddress() {
            return walletAddress;
        }

        public void setWalletAddress(String walletAddress) {
            this.walletAddress = walletAddress;
        }
    }
}
