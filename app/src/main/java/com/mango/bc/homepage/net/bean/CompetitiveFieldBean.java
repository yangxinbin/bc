package com.mango.bc.homepage.net.bean;

/**
 * Created by admin on 2018/9/10.
 */

public class CompetitiveFieldBean {

    /**
     * id : 5ba45bde2557b90bfc3cdf13
     * name : 实操指南
     * icon : {"id":"5bda58775976861338b91025","alias":"WechatIMG19.png","fileName":"1541036151376249276.png","contentType":"image/png","size":6920,"createdOn":1541036151376}
     * count : 9
     */

    private String id;
    private String name;
    private IconBean icon;
    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IconBean getIcon() {
        return icon;
    }

    public void setIcon(IconBean icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class IconBean {
        /**
         * id : 5bda58775976861338b91025
         * alias : WechatIMG19.png
         * fileName : 1541036151376249276.png
         * contentType : image/png
         * size : 6920
         * createdOn : 1541036151376
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
