package com.mango.bc.mine.bean;

import java.util.List;

/**
 * Created by admin on 2018/10/12.
 */

public class MemberBean {

    /**
     * total : 13.36
     * size : 1
     * users : [{"userId":"5b991c74c6987a31a42779f1","type":"general","agencyInfo":{"realName":"","company":"","position":"","phone":"","recommendedBy":"","level":0,"status":0},"alias":"KK","walletAddress":"13.36","avator":{"id":"5bc00b762557b905d888975a","alias":"avator","fileName":"15393125021706388.avator","contentType":"image/jpeg","size":1024,"createdOn":1539312502170}}]
     */

    private double total;
    private int size;
    private List<UsersBean> users;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        /**
         * userId : 5b991c74c6987a31a42779f1
         * type : general
         * agencyInfo : {"realName":"","company":"","position":"","phone":"","recommendedBy":"","level":0,"status":0}
         * alias : KK
         * walletAddress : 13.36
         * avator : {"id":"5bc00b762557b905d888975a","alias":"avator","fileName":"15393125021706388.avator","contentType":"image/jpeg","size":1024,"createdOn":1539312502170}
         */

        private String userId;
        private String type;
        private AgencyInfoBean agencyInfo;
        private String alias;
        private String walletAddress;
        private AvatorBean avator;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

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

        public String getWalletAddress() {
            return walletAddress;
        }

        public void setWalletAddress(String walletAddress) {
            this.walletAddress = walletAddress;
        }

        public AvatorBean getAvator() {
            return avator;
        }

        public void setAvator(AvatorBean avator) {
            this.avator = avator;
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

        public static class AvatorBean {
            /**
             * id : 5bc00b762557b905d888975a
             * alias : avator
             * fileName : 15393125021706388.avator
             * contentType : image/jpeg
             * size : 1024
             * createdOn : 1539312502170
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
    }
}
