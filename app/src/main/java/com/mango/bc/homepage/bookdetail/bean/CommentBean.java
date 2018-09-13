package com.mango.bc.homepage.bookdetail.bean;

/**
 * Created by admin on 2018/9/13.
 */

public class CommentBean {

    /**
     * id : 5b8d3e6704440c15e09df606
     * comment : 廉华老师真棒！
     * commentBy : {"userId":null,"type":null,"agencyInfo":null,"alias":"沉默","walletAddress":"0x58a33738a2d7327f0225c615db378ef8","avator":{"id":"5b8d3bc404440c15e09df5f3","alias":"avator","fileName":"15359825326815564.avator","contentType":"image/jpeg","size":1024,"createdOn":1535982532681}}
     * bookId : 5b8cb5ce04440c13249a643e
     * createOn : 1535983207514
     * approved : true
     */

    private String id;
    private String comment;
    private CommentByBean commentBy;
    private String bookId;
    private long createOn;
    private boolean approved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentByBean getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(CommentByBean commentBy) {
        this.commentBy = commentBy;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public long getCreateOn() {
        return createOn;
    }

    public void setCreateOn(long createOn) {
        this.createOn = createOn;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public static class CommentByBean {
        /**
         * userId : null
         * type : null
         * agencyInfo : null
         * alias : 沉默
         * walletAddress : 0x58a33738a2d7327f0225c615db378ef8
         * avator : {"id":"5b8d3bc404440c15e09df5f3","alias":"avator","fileName":"15359825326815564.avator","contentType":"image/jpeg","size":1024,"createdOn":1535982532681}
         */

        private Object userId;
        private Object type;
        private Object agencyInfo;
        private String alias;
        private String walletAddress;
        private AvatorBean avator;

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getAgencyInfo() {
            return agencyInfo;
        }

        public void setAgencyInfo(Object agencyInfo) {
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

        public static class AvatorBean {
            /**
             * id : 5b8d3bc404440c15e09df5f3
             * alias : avator
             * fileName : 15359825326815564.avator
             * contentType : image/jpeg
             * size : 1024
             * createdOn : 1535982532681
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
