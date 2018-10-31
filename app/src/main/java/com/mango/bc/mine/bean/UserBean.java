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
     * phone :
     * openId : oXhi94jQkXPovBsqEs0B8QKsbM0A
     * alias : 杨鑫斌
     * type : agency
     * avator : {"id":"5bd8710e1fd8420cd4b068da","alias":"avator","fileName":"15409113748273474.avator","contentType":"image/jpeg","size":1024,"createdOn":1540911374827}
     * authToken : eyJhbGciOiJIUzUxMiJ9.eyJhdWRpZW5jZSI6Im1vYmlsZSIsImNyZWF0ZWQiOjE1NDA5Njc0MDk1OTQsImFsaWFzIjoi5p2o6ZGr5paMIiwiaWQiOiI1YjhhM2Q0YjA0NDQwYzBhNDhhMzNhMDUiLCJ0eXBlIjoiYWdlbmN5Iiwid2FsbGV0QWRkcmVzcyI6IjB4ZTcyZTM4N2FmMTJlMDg2YWVlY2M4ZWYxOWM3NzFjYzgiLCJleHAiOjQxMzI5Njc0MDksInVzZXJuYW1lIjoib1hoaTk0alFrWFBvdkJzcUVzMEI4UUtzYk0wQSJ9.XVG6l4g7D9XAcGjuaxZyTQiANjcbeH9LOCHTRvq7oo6fgaC9tuCVFiBmbpf04OCvQ9H1yIS4EEJkz-J-qb2glA
     * agencyInfo : {"realName":"kk","company":"mango","position":"ceo ","phone":"18318836309","recommendedBy":null,"level":0,"status":2}
     * userProfile : {"identity":["创业者","从业者"],"hobbies":["小白学堂","技术开发"],"realName":"leo","age":"19","sex":"男","company":"mango","duty":"software"}
     * wallet : {"id":"5b8a3d4b04440c0a48a33a05","blockChainWalletAddress":null,"walletAddress":"0xe72e387af12e086aeecc8ef19c771cc8","ppCoins":79396.1}
     * billing : {"startOn":1538135914379,"endOn":1559044714381,"billingAmount":28,"billingType":"monthly","auto":true}
     * vip : true
     * status : null
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
    private UserProfileBean userProfile;
    private WalletBean wallet;
    private BillingBean billing;
    private boolean vip;
    private Object status;
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

    public UserProfileBean getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileBean userProfile) {
        this.userProfile = userProfile;
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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
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
         * id : 5bd8710e1fd8420cd4b068da
         * alias : avator
         * fileName : 15409113748273474.avator
         * contentType : image/jpeg
         * size : 1024
         * createdOn : 1540911374827
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
         * recommendedBy : null
         * level : 0
         * status : 2
         */

        private String realName;
        private String company;
        private String position;
        private String phone;
        private Object recommendedBy;
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

        public Object getRecommendedBy() {
            return recommendedBy;
        }

        public void setRecommendedBy(Object recommendedBy) {
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

    public static class UserProfileBean {
        /**
         * identity : ["创业者","从业者"]
         * hobbies : ["小白学堂","技术开发"]
         * realName : leo
         * age : 19
         * sex : 男
         * company : mango
         * duty : software
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
         * ppCoins : 79396.1
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
         * endOn : 1559044714381
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
}
