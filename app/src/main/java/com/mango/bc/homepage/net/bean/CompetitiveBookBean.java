package com.mango.bc.homepage.net.bean;

import java.util.List;

/**
 * Created by admin on 2018/9/10.
 */

public class CompetitiveBookBean {

    /**
     * author : {"id":"string","introduction":"string","name":"string","photo":{"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}}
     * banner : {"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}
     * category : string
     * chapters : [{"audio":{"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0},"contentImages":[{"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}],"duration":0,"free":true,"id":"string","index":0,"title":"string","updatedOn":0}]
     * cover : {"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}
     * descriptionImages : [{"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}]
     * groupBuy2Price : 0
     * groupBuy3Price : 0
     * id : string
     * likes : 0
     * likesPlus : 0
     * price : 0
     * recommendation : string
     * recommended : 0
     * sold : 0
     * soldPlus : 0
     * status : online
     * subtitle : string
     * tags : ["string"]
     * title : string
     * type : paid
     * updatedBy : string
     * updatedOn : 0
     * vipPrice : 0
     */

    private AuthorBean author;
    private BannerBean banner;
    private String category;
    private CoverBean cover;
    private int groupBuy2Price;
    private int groupBuy3Price;
    private String id;
    private int likes;
    private int likesPlus;
    private int price;
    private String recommendation;
    private int recommended;
    private int sold;
    private int soldPlus;
    private String status;
    private String subtitle;
    private String title;
    private String type;
    private String updatedBy;
    private int updatedOn;
    private int vipPrice;
    private List<ChaptersBean> chapters;
    private List<DescriptionImagesBean> descriptionImages;
    private List<String> tags;

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CoverBean getCover() {
        return cover;
    }

    public void setCover(CoverBean cover) {
        this.cover = cover;
    }

    public int getGroupBuy2Price() {
        return groupBuy2Price;
    }

    public void setGroupBuy2Price(int groupBuy2Price) {
        this.groupBuy2Price = groupBuy2Price;
    }

    public int getGroupBuy3Price() {
        return groupBuy3Price;
    }

    public void setGroupBuy3Price(int groupBuy3Price) {
        this.groupBuy3Price = groupBuy3Price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public int getRecommended() {
        return recommended;
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(int updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public List<ChaptersBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChaptersBean> chapters) {
        this.chapters = chapters;
    }

    public List<DescriptionImagesBean> getDescriptionImages() {
        return descriptionImages;
    }

    public void setDescriptionImages(List<DescriptionImagesBean> descriptionImages) {
        this.descriptionImages = descriptionImages;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public static class AuthorBean {
        /**
         * id : string
         * introduction : string
         * name : string
         * photo : {"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}
         */

        private String id;
        private String introduction;
        private String name;
        private PhotoBean photo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PhotoBean getPhoto() {
            return photo;
        }

        public void setPhoto(PhotoBean photo) {
            this.photo = photo;
        }

        public static class PhotoBean {
            /**
             * alias : string
             * contentType : string
             * createdOn : 0
             * fileName : string
             * id : string
             * size : 0
             */

            private String alias;
            private String contentType;
            private int createdOn;
            private String fileName;
            private String id;
            private int size;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }

            public int getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(int createdOn) {
                this.createdOn = createdOn;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }

    public static class BannerBean {
        /**
         * alias : string
         * contentType : string
         * createdOn : 0
         * fileName : string
         * id : string
         * size : 0
         */

        private String alias;
        private String contentType;
        private int createdOn;
        private String fileName;
        private String id;
        private int size;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public int getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(int createdOn) {
            this.createdOn = createdOn;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class CoverBean {
        /**
         * alias : string
         * contentType : string
         * createdOn : 0
         * fileName : string
         * id : string
         * size : 0
         */

        private String alias;
        private String contentType;
        private int createdOn;
        private String fileName;
        private String id;
        private int size;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public int getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(int createdOn) {
            this.createdOn = createdOn;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class ChaptersBean {
        /**
         * audio : {"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}
         * contentImages : [{"alias":"string","contentType":"string","createdOn":0,"fileName":"string","id":"string","size":0}]
         * duration : 0
         * free : true
         * id : string
         * index : 0
         * title : string
         * updatedOn : 0
         */

        private AudioBean audio;
        private int duration;
        private boolean free;
        private String id;
        private int index;
        private String title;
        private int updatedOn;
        private List<ContentImagesBean> contentImages;

        public AudioBean getAudio() {
            return audio;
        }

        public void setAudio(AudioBean audio) {
            this.audio = audio;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(int updatedOn) {
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
             * alias : string
             * contentType : string
             * createdOn : 0
             * fileName : string
             * id : string
             * size : 0
             */

            private String alias;
            private String contentType;
            private int createdOn;
            private String fileName;
            private String id;
            private int size;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }

            public int getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(int createdOn) {
                this.createdOn = createdOn;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }

        public static class ContentImagesBean {
            /**
             * alias : string
             * contentType : string
             * createdOn : 0
             * fileName : string
             * id : string
             * size : 0
             */

            private String alias;
            private String contentType;
            private int createdOn;
            private String fileName;
            private String id;
            private int size;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }

            public int getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(int createdOn) {
                this.createdOn = createdOn;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }

    public static class DescriptionImagesBean {
        /**
         * alias : string
         * contentType : string
         * createdOn : 0
         * fileName : string
         * id : string
         * size : 0
         */

        private String alias;
        private String contentType;
        private int createdOn;
        private String fileName;
        private String id;
        private int size;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public int getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(int createdOn) {
            this.createdOn = createdOn;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
