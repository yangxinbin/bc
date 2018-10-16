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
     * type : agency
     * avator : {"id":"5bc3f8ba2557b905d8889a58","alias":"avator","fileName":"15395698502861556.avator","contentType":"image/jpeg","size":1024,"createdOn":1539569850286}
     * authToken : eyJhbGciOiJIUzUxMiJ9.eyJhdWRpZW5jZSI6Im1vYmlsZSIsImNyZWF0ZWQiOjE1Mzk1NzcyNTQ4MzcsImFsaWFzIjoi5p2o6ZGr5paMIiwiaWQiOiI1YjhhM2Q0YjA0NDQwYzBhNDhhMzNhMDUiLCJ0eXBlIjoiYWdlbmN5Iiwid2FsbGV0QWRkcmVzcyI6IjB4ZTcyZTM4N2FmMTJlMDg2YWVlY2M4ZWYxOWM3NzFjYzgiLCJleHAiOjQxMzE1NzcyNTQsInVzZXJuYW1lIjoib1hoaTk0alFrWFBvdkJzcUVzMEI4UUtzYk0wQSJ9.eUhOHgJTswqYu2ZZoUxEZpA2hHpqWWU3ksGLk0lvgMxQ6t0MNjDpxr1hs2Bzs919ep3LQAk_d5CFtcXqg5_Cvg
     * agencyInfo : {"realName":"kk","company":"mango","position":"ceo ","phone":"18318836309","recommendedBy":"","level":0,"status":2}
     * profile : {"role":"","interest":null,"industry":null,"name":"","age":0,"gender":"","companyName":"","position":""}
     * wallet : {"id":"5b8a3d4b04440c0a48a33a05","blockChainWalletAddress":null,"walletAddress":"0xe72e387af12e086aeecc8ef19c771cc8","ppCoins":7571.09}
     * billing : {"startOn":1538135914379,"endOn":1553774314381,"billingAmount":28,"billingType":"monthly","auto":true}
     * vip : true
     * stats : {"createDate":1538015317854,"vipGetMemberBooks":0,"buyMemberMoneySaved":0,"buyPaidBookMoneySaved":0,"paidBooks":1,"vipBooks":0,"totalDuration":0,"ppCoinEarned":28.42,"paidBookSaving":0,"memberBookSaving":0,"ageDays":0,"updatedOn":1538015317854,"lastCalculateDate":null}
     * status :
     * createdOn : 1538015317854
     */

    private String id;
    private String username;
    private String openId;
    private String alias;
    private String type;
    private AvatorBean avator;
    private String authToken;
    private AgencyInfoBean agencyInfo;
    private ProfileBean profile;
    private WalletBean wallet;
    private BillingBean billing;
    private boolean vip;
    private StatsBean stats;
    private String status;
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

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
         * id : 5bc3f8ba2557b905d8889a58
         * alias : avator
         * fileName : 15395698502861556.avator
         * contentType : image/jpeg
         * size : 1024
         * createdOn : 1539569850286
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
         * realName : kk
         * company : mango
         * position : ceo
         * phone : 18318836309
         * recommendedBy :
         * level : 0
         * status : 2
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

    public static class ProfileBean {
        /**
         * role :
         * interest : null
         * industry : null
         * name :
         * age : 0
         * gender :
         * companyName :
         * position :
         */

        private String role;
        private Object interest;
        private Object industry;
        private String name;
        private int age;
        private String gender;
        private String companyName;
        private String position;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Object getInterest() {
            return interest;
        }

        public void setInterest(Object interest) {
            this.interest = interest;
        }

        public Object getIndustry() {
            return industry;
        }

        public void setIndustry(Object industry) {
            this.industry = industry;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }

    public static class WalletBean {
        /**
         * id : 5b8a3d4b04440c0a48a33a05
         * blockChainWalletAddress : null
         * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
         * ppCoins : 7571.09
         */

        private String id;
        private Object blockChainWalletAddress;
        private String walletAddress;
        private double ppCoins;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getBlockChainWalletAddress() {
            return blockChainWalletAddress;
        }

        public void setBlockChainWalletAddress(Object blockChainWalletAddress) {
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
         * endOn : 1553774314381
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
         * buyMemberMoneySaved : 0.0
         * buyPaidBookMoneySaved : 0.0
         * paidBooks : 1
         * vipBooks : 0
         * totalDuration : 0.0
         * ppCoinEarned : 28.42
         * paidBookSaving : 0.0
         * memberBookSaving : 0.0
         * ageDays : 0
         * updatedOn : 1538015317854
         * lastCalculateDate : null
         */

        private long createDate;
        private int vipGetMemberBooks;
        private double buyMemberMoneySaved;
        private double buyPaidBookMoneySaved;
        private int paidBooks;
        private int vipGetBooks;
        private double totalDuration;
        private double ppCoinEarned;
        private double paidBookSaving;
        private double memberBookSaving;
        private int ageDays;
        private long updatedOn;
        private Object lastCalculateDate;

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

        public void setVipGetBooks(int vipBooks) {
            this.vipGetBooks = vipBooks;
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

        public long getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(long updatedOn) {
            this.updatedOn = updatedOn;
        }

        public Object getLastCalculateDate() {
            return lastCalculateDate;
        }

        public void setLastCalculateDate(Object lastCalculateDate) {
            this.lastCalculateDate = lastCalculateDate;
        }
    }
}
