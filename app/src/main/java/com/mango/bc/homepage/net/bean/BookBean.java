package com.mango.bc.homepage.net.bean;

import java.util.List;

/**
 * Created by admin on 2018/9/10.
 */

public class BookBean {

    /**
     * id : 5b84b31c04440c128c659ff2
     * title : 贝尔福特：华尔街之狼
     * subtitle : 出狱后比入狱前更赚钱的传奇人生。
     * recommendation : 出狱后比入狱前更赚钱的传奇人生。
     * descriptionImages : [{"id":"5b84b35304440c128c659ff3","alias":"郭宏才详情.jpg","fileName":"15354233153218227.jpg","contentType":"image/jpeg","size":49510,"createdOn":1535423315321}]
     * cover : {"id":"5b87560e04440c002066b616","alias":"贝尔福特.jpg","fileName":"15355960465391611.jpg","contentType":"image/jpeg","size":97859,"createdOn":1535596046539}
     * banner : null
     * category : 人物系列
     * author : {"id":"5b839f2b04440c1224e72ba7","name":"BC大陆","introduction":"no","photo":{"id":"5b8501b204440c128c65a11e","alias":"作者-BC大陆.jpg","fileName":"15354433784241925.jpg","contentType":"image/jpeg","size":39399,"createdOn":1535443378424}}
     * chapters : [{"id":"1535423361229","title":"贝尔福特：华尔街之狼","duration":653,"audio":{"id":"5b84b3c004440c128c659ffa","alias":"贝尔福特完整版.mp3","fileName":"15354234248283889.mp3","contentType":"audio/mp3","size":15685975,"createdOn":1535423424828},"contentImages":[{"id":"5b84b39104440c128c659ff7","alias":"贝尔.jpg","fileName":"15354233774864373.jpg","contentType":"image/jpeg","size":512384,"createdOn":1535423377486}],"free":true,"index":1,"updatedOn":1535423361229}]
     * tags : ["人物系列"]
     * price : 0
     * vipPrice : 0
     * groupBuy2Price : 0
     * groupBuy3Price : 0
     * type : free
     * likes : 13
     * likesPlus : 0
     * sold : 70
     * soldPlus : 0
     * recommended : 102
     * updatedOn : 1536139478337
     * updatedBy : 5b84ad3904440c128c32992b
     * status : online
     */

    private String id;
    private String title;
    private String subtitle;
    private String recommendation;
    private CoverBean cover;
    private Object banner;
    private String category;
    private AuthorBean author;
    private Double price;
    private Double vipPrice;
    private Double groupBuy2Price;
    private Double groupBuy3Price;
    private String type;
    private int likes;
    private int likesPlus;
    private int sold;
    private int soldPlus;
    private int recommended;
    private long updatedOn;
    private String updatedBy;
    private String status;
    private List<DescriptionImagesBean> descriptionImages;
    private List<ChaptersBean> chapters;
    private List<String> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public CoverBean getCover() {
        return cover;
    }

    public void setCover(CoverBean cover) {
        this.cover = cover;
    }

    public Object getBanner() {
        return banner;
    }

    public void setBanner(Object banner) {
        this.banner = banner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Double getGroupBuy2Price() {
        return groupBuy2Price;
    }

    public void setGroupBuy2Price(Double groupBuy2Price) {
        this.groupBuy2Price = groupBuy2Price;
    }

    public Double getGroupBuy3Price() {
        return groupBuy3Price;
    }

    public void setGroupBuy3Price(Double groupBuy3Price) {
        this.groupBuy3Price = groupBuy3Price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikesPlus() {
        return likesPlus;
    }

    public void setLikesPlus(int likesPlus) {
        this.likesPlus = likesPlus;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getSoldPlus() {
        return soldPlus;
    }

    public void setSoldPlus(int soldPlus) {
        this.soldPlus = soldPlus;
    }

    public int getRecommended() {
        return recommended;
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DescriptionImagesBean> getDescriptionImages() {
        return descriptionImages;
    }

    public void setDescriptionImages(List<DescriptionImagesBean> descriptionImages) {
        this.descriptionImages = descriptionImages;
    }

    public List<ChaptersBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChaptersBean> chapters) {
        this.chapters = chapters;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public static class CoverBean {
        /**
         * id : 5b87560e04440c002066b616
         * alias : 贝尔福特.jpg
         * fileName : 15355960465391611.jpg
         * contentType : image/jpeg
         * size : 97859
         * createdOn : 1535596046539
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

    public static class AuthorBean {
        /**
         * id : 5b839f2b04440c1224e72ba7
         * name : BC大陆
         * introduction : no
         * photo : {"id":"5b8501b204440c128c65a11e","alias":"作者-BC大陆.jpg","fileName":"15354433784241925.jpg","contentType":"image/jpeg","size":39399,"createdOn":1535443378424}
         */

        private String id;
        private String name;
        private String introduction;
        private PhotoBean photo;

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

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public PhotoBean getPhoto() {
            return photo;
        }

        public void setPhoto(PhotoBean photo) {
            this.photo = photo;
        }

        public static class PhotoBean {
            /**
             * id : 5b8501b204440c128c65a11e
             * alias : 作者-BC大陆.jpg
             * fileName : 15354433784241925.jpg
             * contentType : image/jpeg
             * size : 39399
             * createdOn : 1535443378424
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

    public static class DescriptionImagesBean {
        /**
         * id : 5b84b35304440c128c659ff3
         * alias : 郭宏才详情.jpg
         * fileName : 15354233153218227.jpg
         * contentType : image/jpeg
         * size : 49510
         * createdOn : 1535423315321
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

    public static class ChaptersBean {
        /**
         * id : 1535423361229
         * title : 贝尔福特：华尔街之狼
         * duration : 653
         * audio : {"id":"5b84b3c004440c128c659ffa","alias":"贝尔福特完整版.mp3","fileName":"15354234248283889.mp3","contentType":"audio/mp3","size":15685975,"createdOn":1535423424828}
         * contentImages : [{"id":"5b84b39104440c128c659ff7","alias":"贝尔.jpg","fileName":"15354233774864373.jpg","contentType":"image/jpeg","size":512384,"createdOn":1535423377486}]
         * free : true
         * index : 1
         * updatedOn : 1535423361229
         */

        private String id;
        private String title;
        private int duration;
        private AudioBean audio;
        private boolean free;
        private int index;
        private long updatedOn;
        private List<ContentImagesBean> contentImages;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public AudioBean getAudio() {
            return audio;
        }

        public void setAudio(AudioBean audio) {
            this.audio = audio;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(long updatedOn) {
            this.updatedOn = updatedOn;
        }

        public List<ContentImagesBean> getContentImages() {
            return contentImages;
        }

        public void setContentImages(List<ContentImagesBean> contentImages) {
            this.contentImages = contentImages;
        }

        public static class AudioBean {
            /**
             * id : 5b84b3c004440c128c659ffa
             * alias : 贝尔福特完整版.mp3
             * fileName : 15354234248283889.mp3
             * contentType : audio/mp3
             * size : 15685975
             * createdOn : 1535423424828
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

        public static class ContentImagesBean {
            /**
             * id : 5b84b39104440c128c659ff7
             * alias : 贝尔.jpg
             * fileName : 15354233774864373.jpg
             * contentType : image/jpeg
             * size : 512384
             * createdOn : 1535423377486
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
