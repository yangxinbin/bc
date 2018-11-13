package com.mango.bc.homepage.bean;

/**
 * Created by Administrator on 2018/11/13 0013.
 */

public class BannerBean {

    /**
     * id : 5bea325476937c28c85edec3
     * image : {"id":"5beaa01339505f0b40398052","alias":"123.png","fileName":"1542103059397651711.png","contentType":"image/png","size":136034,"createdOn":1542103059397}
     * url :
     * status : online
     */

    private String id;
    private ImageBean image;
    private String url;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ImageBean {
        /**
         * id : 5beaa01339505f0b40398052
         * alias : 123.png
         * fileName : 1542103059397651711.png
         * contentType : image/png
         * size : 136034
         * createdOn : 1542103059397
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
