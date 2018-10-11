package com.mango.bc.homepage.collage.bean;

import java.util.List;

/**
 * Created by admin on 2018/10/11.
 */

public class CollageBean {

    /**
     * id : 5bbdd9be2557b905d8889539
     * bookTitle : 一起拆解区块链
     * bookId : 5b8cb5ce04440c13249a643e
     * bookPrice : 99
     * bookCover : {"id":"5b8cb5ce04440c13249a643c","alias":"廉华400x300.jpg","fileName":"15359482386316416.jpg","contentType":"image/jpeg","size":69249,"createdOn":1535948238631}
     * price : 69.3
     * timestamp : 1539168702769
     * createdBy : {"userId":"5b8a3d4b04440c0a48a33a05","type":"general","agencyInfo":{"realName":"kk","company":"mango","position":"ceo ","phone":"18318836309","recommendedBy":null,"level":0,"status":1},"alias":"杨鑫斌","walletAddress":"0xe72e387af12e086aeecc8ef19c771cc8","avator":{"id":"5bbdd9a62557b905d8889536","alias":"avator","fileName":"15391686784237832.avator","contentType":"image/jpeg","size":1024,"createdOn":1539168678423}}
     * members : [{"userId":"5b8a3d4b04440c0a48a33a05","type":"general","agencyInfo":{"realName":"kk","company":"mango","position":"ceo ","phone":"18318836309","recommendedBy":"","level":0,"status":1},"alias":"杨鑫斌","walletAddress":"0xe72e387af12e086aeecc8ef19c771cc8","avator":{"id":"5bbdd9a62557b905d8889536","alias":"avator","fileName":"15391686784237832.avator","contentType":"image/jpeg","size":1024,"createdOn":1539168678423}}]
     * type : three
     * status : started
     */

    private String id;
    private String bookTitle;
    private String bookId;
    private int bookPrice;
    private BookCoverBean bookCover;
    private double price;
    private long timestamp;
    private CreatedByBean createdBy;
    private String type;
    private String status;
    private List<MembersBean> members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public BookCoverBean getBookCover() {
        return bookCover;
    }

    public void setBookCover(BookCoverBean bookCover) {
        this.bookCover = bookCover;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public CreatedByBean getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedByBean createdBy) {
        this.createdBy = createdBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<MembersBean> members) {
        this.members = members;
    }

    public static class BookCoverBean {
        /**
         * id : 5b8cb5ce04440c13249a643c
         * alias : 廉华400x300.jpg
         * fileName : 15359482386316416.jpg
         * contentType : image/jpeg
         * size : 69249
         * createdOn : 1535948238631
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

    public static class CreatedByBean {
        /**
         * userId : 5b8a3d4b04440c0a48a33a05
         * type : general
         * agencyInfo : {"realName":"kk","company":"mango","position":"ceo ","phone":"18318836309","recommendedBy":null,"level":0,"status":1}
         * alias : 杨鑫斌
         * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
         * avator : {"id":"5bbdd9a62557b905d8889536","alias":"avator","fileName":"15391686784237832.avator","contentType":"image/jpeg","size":1024,"createdOn":1539168678423}
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
             * realName : kk
             * company : mango
             * position : ceo
             * phone : 18318836309
             * recommendedBy : null
             * level : 0
             * status : 1
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

        public static class AvatorBean {
            /**
             * id : 5bbdd9a62557b905d8889536
             * alias : avator
             * fileName : 15391686784237832.avator
             * contentType : image/jpeg
             * size : 1024
             * createdOn : 1539168678423
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

    public static class MembersBean {
        /**
         * userId : 5b8a3d4b04440c0a48a33a05
         * type : general
         * agencyInfo : {"realName":"kk","company":"mango","position":"ceo ","phone":"18318836309","recommendedBy":"","level":0,"status":1}
         * alias : 杨鑫斌
         * walletAddress : 0xe72e387af12e086aeecc8ef19c771cc8
         * avator : {"id":"5bbdd9a62557b905d8889536","alias":"avator","fileName":"15391686784237832.avator","contentType":"image/jpeg","size":1024,"createdOn":1539168678423}
         */

        private String userId;
        private String type;
        private AgencyInfoBeanX agencyInfo;
        private String alias;
        private String walletAddress;
        private AvatorBeanX avator;

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

        public AgencyInfoBeanX getAgencyInfo() {
            return agencyInfo;
        }

        public void setAgencyInfo(AgencyInfoBeanX agencyInfo) {
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

        public AvatorBeanX getAvator() {
            return avator;
        }

        public void setAvator(AvatorBeanX avator) {
            this.avator = avator;
        }

        public static class AgencyInfoBeanX {
            /**
             * realName : kk
             * company : mango
             * position : ceo
             * phone : 18318836309
             * recommendedBy :
             * level : 0
             * status : 1
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

        public static class AvatorBeanX {
            /**
             * id : 5bbdd9a62557b905d8889536
             * alias : avator
             * fileName : 15391686784237832.avator
             * contentType : image/jpeg
             * size : 1024
             * createdOn : 1539168678423
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
