package com.mango.bc.homepage.net.bean;

import java.util.List;

/**
 * Created by admin on 2018/9/10.
 */

public class BookBean {

    /**
     * id : 5b8cb5ce04440c13249a643e
     * title : 一起拆解区块链
     * subtitle : 步入区块链世界的必修课，丰富人脉圈谈资的百宝箱。
     * recommendation : 区块链被炒得这么火，到底什么才是区块链？！廉华老师带您揭开区块链的神秘面纱，一起感受新兴科技的魅力！
     * descriptionImages : [{"id":"5b961c9804440c02c4555296","alias":"微信图片_20180910145903.jpg","fileName":"15365643760001476.jpg","contentType":"image/jpeg","size":914442,"createdOn":1536564376000}]
     * cover : {"id":"5b8cb5ce04440c13249a643c","alias":"廉华400x300.jpg","fileName":"15359482386316416.jpg","contentType":"image/jpeg","size":69249,"createdOn":1535948238631}
     * banner : {"id":"5b8cb5d404440c13249a643f","alias":"导师介绍-廉华-banner.jpg","fileName":"15359482441393248.jpg","contentType":"image/jpeg","size":37857,"createdOn":1535948244139}
     * category : Other
     * author : {"id":"5b85035804440c128c65a12f","name":"廉华","introduction":"","photo":{"id":"5b85035704440c128c65a12d","alias":"导师介绍-廉华-封面.jpg","fileName":"15354437990167324.jpg","contentType":"image/jpeg","size":34927,"createdOn":1535443799016}}
     * chapters : [{"id":"1535955171006","title":"发刊词","duration":247,"audio":{"id":"5b921c5604440c02c4554cc9","alias":"发刊词.mp3","fileName":"15363021667298718.mp3","contentType":"audio/mp3","size":6080608,"createdOn":1536302166729},"contentImages":[{"id":"5b8cd0e804440c13249a645f","alias":"发刊词1.jpg","fileName":"15359551763619645.jpg","contentType":"image/jpeg","size":947843,"createdOn":1535955176361}],"free":true,"index":1,"updatedOn":1535955171006},{"id":"1535955297322","title":"区块链\u2014站在巨人肩膀的宠儿","duration":598,"audio":{"id":"5b8cd19104440c13249a648e","alias":"区块链\u2014站在巨人肩膀的宠儿.mp3","fileName":"15359553450021426.mp3","contentType":"audio/mpeg","size":14383627,"createdOn":1535955345002},"contentImages":[{"id":"5b8e542204440c12508dd7f0","alias":"廉华第一篇文章.jpg","fileName":"15360543065099194.jpg","contentType":"image/jpeg","size":817002,"createdOn":1536054306509}],"free":false,"index":2,"updatedOn":1535955297322},{"id":"1536300295001","title":"世界范围内的区块链发展现","duration":519,"audio":{"id":"5b92156204440c02c4554c88","alias":"世界范围内的区块链发展现状.mp3","fileName":"15363003869837312.mp3","contentType":"audio/mpeg","size":12767949,"createdOn":1536300386983},"contentImages":[{"id":"5b92156904440c02c4554cba","alias":"第2节：世界范围内的区块链发展现状.jpg","fileName":"15363003936907178.jpg","contentType":"image/jpeg","size":992837,"createdOn":1536300393690}],"free":false,"index":3,"updatedOn":1536300295001},{"id":"1536306856803","title":"国内外大企业区块链战略","duration":607,"audio":{"id":"5b922ec204440c02c4554d4c","alias":"国内外大企业区块链战略.mp3","fileName":"15363068823144351.mp3","contentType":"audio/mpeg","size":14933174,"createdOn":1536306882314},"contentImages":[{"id":"5b922eb104440c02c4554d47","alias":"第3节：国内外大企业区块链战略.jpg","fileName":"15363068655772693.jpg","contentType":"image/jpeg","size":906817,"createdOn":1536306865577}],"free":false,"index":4,"updatedOn":1536306856803}]
     * tags : []
     * price : 99.1
     * vipPrice : 89.1
     * groupBuy2Price : 79.2
     * groupBuy3Price : 69.3
     * type : paid
     * likes : 12
     * likesPlus : 0
     * sold : 8
     * soldPlus : 0
     * recommended : 300
     * updatedOn : 1536564386695
     * updatedBy : 5b84ad3904440c128c32992b
     * status : online
     */

    private String id;
    private String title;
    private String subtitle;
    private String recommendation;
    private CoverBean cover;
    private BannerBean banner;
    private String category;
    private AuthorBean author;
    private double price;
    private double vipPrice;
    private double groupBuy2Price;
    private double groupBuy3Price;
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
    private List<?> tags;

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

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public double getGroupBuy2Price() {
        return groupBuy2Price;
    }

    public void setGroupBuy2Price(double groupBuy2Price) {
        this.groupBuy2Price = groupBuy2Price;
    }

    public double getGroupBuy3Price() {
        return groupBuy3Price;
    }

    public void setGroupBuy3Price(double groupBuy3Price) {
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

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public static class CoverBean {
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

    public static class BannerBean {
        /**
         * id : 5b8cb5d404440c13249a643f
         * alias : 导师介绍-廉华-banner.jpg
         * fileName : 15359482441393248.jpg
         * contentType : image/jpeg
         * size : 37857
         * createdOn : 1535948244139
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
         * id : 5b85035804440c128c65a12f
         * name : 廉华
         * introduction :
         * photo : {"id":"5b85035704440c128c65a12d","alias":"导师介绍-廉华-封面.jpg","fileName":"15354437990167324.jpg","contentType":"image/jpeg","size":34927,"createdOn":1535443799016}
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
             * id : 5b85035704440c128c65a12d
             * alias : 导师介绍-廉华-封面.jpg
             * fileName : 15354437990167324.jpg
             * contentType : image/jpeg
             * size : 34927
             * createdOn : 1535443799016
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
         * id : 5b961c9804440c02c4555296
         * alias : 微信图片_20180910145903.jpg
         * fileName : 15365643760001476.jpg
         * contentType : image/jpeg
         * size : 914442
         * createdOn : 1536564376000
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
         * id : 1535955171006
         * title : 发刊词
         * duration : 247
         * audio : {"id":"5b921c5604440c02c4554cc9","alias":"发刊词.mp3","fileName":"15363021667298718.mp3","contentType":"audio/mp3","size":6080608,"createdOn":1536302166729}
         * contentImages : [{"id":"5b8cd0e804440c13249a645f","alias":"发刊词1.jpg","fileName":"15359551763619645.jpg","contentType":"image/jpeg","size":947843,"createdOn":1535955176361}]
         * free : true
         * index : 1
         * updatedOn : 1535955171006
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
             * id : 5b921c5604440c02c4554cc9
             * alias : 发刊词.mp3
             * fileName : 15363021667298718.mp3
             * contentType : audio/mp3
             * size : 6080608
             * createdOn : 1536302166729
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
             * id : 5b8cd0e804440c13249a645f
             * alias : 发刊词1.jpg
             * fileName : 15359551763619645.jpg
             * contentType : image/jpeg
             * size : 947843
             * createdOn : 1535955176361
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
