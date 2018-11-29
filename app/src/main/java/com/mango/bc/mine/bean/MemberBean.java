package com.mango.bc.mine.bean;

import java.util.List;

/**
 * Created by admin on 2018/10/12.
 */

public class MemberBean {

    /**
     * total : 68.53
     * size : 1
     * users : [{"userId":"5ba745112557b911bc8a1eb9","type":"general","agencyInfo":{"agencyCode":"aa","realName":"123","company":"123","position":"123","phone":"123456789","status":4,"node":false,"accepted":false},"alias":"KK","walletAddress":"0xff8b88a2a45c6deb7ad3e077f437b1cf","commission":"0","avator":{"id":"5be3b01f1fd8420b242b5334","alias":"avator","fileName":"1541648415825162286.avator","contentType":"image/jpeg","size":1024,"createdOn":1541648415825}}]
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
         * userId : 5ba745112557b911bc8a1eb9
         * type : general
         * agencyInfo : {"agencyCode":"aa","realName":"123","company":"123","position":"123","phone":"123456789","status":4,"node":false,"accepted":false}
         * alias : KK
         * walletAddress : 0xff8b88a2a45c6deb7ad3e077f437b1cf
         * commission : 0
         * avator : {"id":"5be3b01f1fd8420b242b5334","alias":"avator","fileName":"1541648415825162286.avator","contentType":"image/jpeg","size":1024,"createdOn":1541648415825}
         */

        private String userId;
        private String type;
        private AgencyInfoBean agencyInfo;
        private String alias;
        private String walletAddress;
        private String commission;
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

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public AvatorBean getAvator() {
            return avator;
        }

        public void setAvator(AvatorBean avator) {
            this.avator = avator;
        }

        public static class AgencyInfoBean {
            /**
             * agencyCode : aa
             * realName : 123
             * company : 123
             * position : 123
             * phone : 123456789
             * status : 4
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

        public static class AvatorBean {
            /**
             * id : 5be3b01f1fd8420b242b5334
             * alias : avator
             * fileName : 1541648415825162286.avator
             * contentType : image/jpeg
             * size : 1024
             * createdOn : 1541648415825
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
