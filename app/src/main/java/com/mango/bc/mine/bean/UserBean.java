package com.mango.bc.mine.bean;

import java.util.List;

/**
 * Created by admin on 2018/9/17.
 */

public class UserBean {

    /**
     * id : 5b8a3d4b04440c0a48a33a05
     * username : oXhi94jQkXPovBsqEs0B8QKsbM0A
     * unionId : oetTo5r15v1y6B2DNwRuiS22JaFo
     * phone : 18318836309
     * openId : oXhi94jQkXPovBsqEs0B8QKsbM0A
     * alias : 杨鑫斌
     * type : agency
     * avator : {"id":"5bf5437439505f0458fb48e5","alias":"avator","fileName":"1542800244800243831.avator","contentType":"image/jpeg","size":1024,"createdOn":1542800244800}
     * authToken : eyJhbGciOiJIUzUxMiJ9.eyJhdWRpZW5jZSI6Im1vYmlsZSIsImNyZWF0ZWQiOjE1NDMwNzg1OTI1MzksImFsaWFzIjoi5p2o6ZGr5paMIiwiaWQiOiI1YjhhM2Q0YjA0NDQwYzBhNDhhMzNhMDUiLCJ0eXBlIjoiYWdlbmN5Iiwid2FsbGV0QWRkcmVzcyI6IjB4ZTcyZTM4N2FmMTJlMDg2YWVlY2M4ZWYxOWM3NzFjYzgiLCJleHAiOjQxMzUwNzg1OTIsInVzZXJuYW1lIjoib1hoaTk0alFrWFBvdkJzcUVzMEI4UUtzYk0wQSJ9.MJ556m-dLhlqdGywmw8p84wDPD9MINmfiHNUNcL4sxsyP50UGy1ZhbIogZtDJenh7_2NikmzTiBwnIihiG1cYQ
     * agencyInfo : {"agencyCode":"A33A05A","realName":"kk","company":"芒果","position":"IT","phone":"18318836309","status":2,"node":false,"accepted":false}
     * recommender :
     * userProfile : {"identity":["从业者","创业者"],"hobbies":["小白学堂","数字钱包"],"realName":"杨","age":"18","sex":"先生","company":"安公","duty":"it"}
     * banner : null
     * wallet : {"id":"5b8a3d4b04440c0a48a33a05","blockChainWalletAddress":null,"walletAddress":"0xe72e387af12e086aeecc8ef19c771cc8","ppCoins":79478.38}
     * billing : {"startOn":1538135914379,"endOn":1605873514381,"billingAmount":39.9,"billingType":"monthly","auto":false}
     * vip : true
     * status :
     * createdOn : 1538015317854
     */

    private String id;
    private String username;
    private String unionId;
    private String phone;
    private String openId;
    private String alias;
    private String type;
    private AvatorBean avator;
    private String authToken;
    private AgencyInfoBean agencyInfo;
    private String recommender;
    private UserProfileBean userProfile;
    private Object banner;
    private WalletBean wallet;
    private BillingBean billing;
    private boolean vip;
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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public UserProfileBean getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileBean userProfile) {
        this.userProfile = userProfile;
    }

    public Object getBanner() {
        return banner;
    }

    public void setBanner(Object banner) {
        this.banner = banner;
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
         * id : 5bf5437439505f0458fb48e5
         * alias : avator
         * fileName : 1542800244800243831.avator
         * contentType : image/jpeg
         * size : 1024
         * createdOn : 1542800244800
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
         * agencyCode : A33A05A
         * realName : kk
         * company : 芒果
         * position : IT
         * phone : 18318836309
         * status : 2
         * node : false
         * accepted : false
         */

        private String agencyCode;
        private String realName;
        private String company;
        private String position;
        private String phone;
        private int status;
        private boolean node;
        private boolean accepted;

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isNode() {
            return node;
        }

        public void setNode(boolean node) {
            this.node = node;
        }

        public boolean isAccepted() {
            return accepted;
        }

        public void setAccepted(boolean accepted) {
            this.accepted = accepted;
        }
    }

    public static class UserProfileBean {
        /**
         * identity : ["从业者","创业者"]
         * hobbies : ["小白学堂","数字钱包"]
         * realName : 杨
         * age : 18
         * sex : 先生
         * company : 安公
         * duty : it
         */

        private String realName;
        private String age;
        private String sex;
        private String company;
        private String duty;
        private List<String> identity;
        private List<String> hobbies;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        public List<String> getIdentity() {
            return identity;
        }

        public void setIdentity(List<String> identity) {
            this.identity = identity;
        }

        public List<String> getHobbies() {
            return hobbies;
        }

        public void setHobbies(List<String> hobbies) {
            this.hobbies = hobbies;
        }
    }

    public static class WalletBean {
        /**
         * id : 5b8a3d4b04440c0a48a33a05
         * blockChainWalletAddress : null
         * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
         * ppCoins : 79478.38
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
         * endOn : 1605873514381
         * billingAmount : 39.9
         * billingType : monthly
         * auto : false
         */

        private long startOn;
        private long endOn;
        private double billingAmount;
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

        public double getBillingAmount() {
            return billingAmount;
        }

        public void setBillingAmount(double billingAmount) {
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
}
