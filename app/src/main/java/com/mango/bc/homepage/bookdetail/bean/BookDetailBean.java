package com.mango.bc.homepage.bookdetail.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/19 0019.
 */

public class BookDetailBean {

    /**
     * id : 5b850b5d04440c128c65a189
     * title : IPFS—分布式存储挖矿
     * subtitle : IPFS是否能成为区块链4.0时代…？
     * recommendation : bbb
     * descriptionImages : [{"id":"5b850b9704440c128c65a18c","alias":"精品课-刘锋-课程详情.jpg","fileName":"15354459113759169.jpg","contentType":"image/jpeg","size":593726,"createdOn":1535445911375}]
     * cover : {"id":"5b850b5d04440c128c65a187","alias":"刘锋400x300.jpg","fileName":"15354458533908661.jpg","contentType":"image/jpeg","size":66907,"createdOn":1535445853390}
     * banner : {"id":"5b85100a04440c128c65a28e","alias":"精品课-刘锋-banner2.jpg","fileName":"15354470506599764.jpg","contentType":"image/jpeg","size":76757,"createdOn":1535447050659}
     * category : Other
     * author : {"id":"5b85056a04440c128c65a135","name":"刘锋","introduction":"","photo":{"id":"5b85056904440c128c65a133","alias":"精品课-刘锋-封面.jpg","fileName":"15354443293105681.jpg","contentType":"image/jpeg","size":41770,"createdOn":1535444329310}}
     * chapters : [{"id":"1536057201329","title":"发刊词","duration":243,"audio":{"id":"5bab4a2e2557b90cc4e6a49b","alias":"发刊词.mp3","fileName":"15379523025745651.mp3","contentType":"audio/mp3","size":5977373,"createdOn":1537952302574},"contentImages":[{"id":"5b8e602604440c12508dd828","alias":"发刊词.jpg","fileName":"15360573825003719.jpg","contentType":"image/jpeg","size":802675,"createdOn":1536057382500}],"free":true,"index":1,"updatedOn":1536057201329},{"id":"1537352833463","title":"IPFS的诞生","duration":543,"audio":{"id":"5ba225342557b90430ebc568","alias":"IPFS的诞生.mp3","fileName":"15373530129561273.mp3","contentType":"audio/mp3","size":13346901,"createdOn":1537353012956},"contentImages":[{"id":"5ba225592557b90430ebc59d","alias":"IPFS的诞生.jpg","fileName":"15373530494708241.jpg","contentType":"image/jpeg","size":845454,"createdOn":1537353049470},{"id":"5ba225642557b90430ebc5a2","alias":"IPFS的诞生2.jpg","fileName":"15373530601628961.jpg","contentType":"image/jpeg","size":1024582,"createdOn":1537353060162}],"free":false,"index":2,"updatedOn":1537352833463},{"id":"1537952469829","title":"什么是IPFS？","duration":733,"audio":{"id":"5bab4b1d2557b90cc4e6a4b3","alias":"什么是IPFS？_.mp3","fileName":"15379525413423484.mp3","contentType":"audio/mp3","size":18035923,"createdOn":1537952541342},"contentImages":[{"id":"5bab4fa82557b90cc4e6a4ff","alias":"什么是IPFS？.jpg","fileName":"15379537044447483.jpg","contentType":"image/jpeg","size":876920,"createdOn":1537953704444},{"id":"5bab506b2557b90cc4e6a504","alias":"什么是IPFS？2.jpg","fileName":"15379538992843318.jpg","contentType":"image/jpeg","size":963752,"createdOn":1537953899284}],"free":false,"index":3,"updatedOn":1537952469829},{"id":"1537954387401","title":"什么是filecoin","duration":583,"audio":{"id":"5bab52822557b90cc4e6a509","alias":"什么是filecoin？.mp3","fileName":"15379544347305664.mp3","contentType":"audio/mp3","size":14338781,"createdOn":1537954434730},"contentImages":[{"id":"5bab52932557b90cc4e6a541","alias":"什么是filecoin？.jpg","fileName":"15379544513073192.jpg","contentType":"image/jpeg","size":798390,"createdOn":1537954451307},{"id":"5bab52b02557b90cc4e6a546","alias":"什么是filecoin？2.jpg","fileName":"15379544801095967.jpg","contentType":"image/jpeg","size":1003236,"createdOn":1537954480109}],"free":false,"index":4,"updatedOn":1537954387401},{"id":"1537954545094","title":"IPFS挖矿机制","duration":675,"audio":{"id":"5bab53652557b90cc4e6a54b","alias":"IPFS挖矿机制.mp3","fileName":"15379546612843224.mp3","contentType":"audio/mp3","size":16595071,"createdOn":1537954661284},"contentImages":[{"id":"5bab537a2557b90cc4e6a58c","alias":"IPFS挖矿机制.jpg","fileName":"15379546824894283.jpg","contentType":"image/jpeg","size":840938,"createdOn":1537954682489},{"id":"5bab548e2557b90cc4e6a591","alias":"IPFS挖矿机制2.jpg","fileName":"15379549581982192.jpg","contentType":"image/jpeg","size":871052,"createdOn":1537954958198}],"free":false,"index":5,"updatedOn":1537954545094},{"id":"1537955009953","title":"\u201cIPFS+\u201d无限的应用拓展","duration":581,"audio":{"id":"5bab56c62557b90cc4e6a5ce","alias":"\u201cIPFS \u201d无限的应用拓展.mp3","fileName":"15379555260934664.mp3","contentType":"audio/mp3","size":14279751,"createdOn":1537955526093},"contentImages":[{"id":"5bab571a2557b90cc4e6a606","alias":"\u201cIPFS \u201d无限的应用拓展.jpg","fileName":"15379556108487493.jpg","contentType":"image/jpeg","size":953830,"createdOn":1537955610848}],"free":false,"index":6,"updatedOn":1537955009953}]
     * tags : []
     * price : 99.0
     * vipPrice : 89.1
     * groupBuy2Price : 79.2
     * groupBuy3Price : 69.3
     * type : paid
     * likes : 70
     * likesPlus : 0
     * sold : 45
     * soldPlus : 213
     * recommended : 300
     * updatedOn : 1538012029047
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
         * id : 5b850b5d04440c128c65a187
         * alias : 刘锋400x300.jpg
         * fileName : 15354458533908661.jpg
         * contentType : image/jpeg
         * size : 66907
         * createdOn : 1535445853390
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
         * id : 5b85100a04440c128c65a28e
         * alias : 精品课-刘锋-banner2.jpg
         * fileName : 15354470506599764.jpg
         * contentType : image/jpeg
         * size : 76757
         * createdOn : 1535447050659
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
         * id : 5b85056a04440c128c65a135
         * name : 刘锋
         * introduction :
         * photo : {"id":"5b85056904440c128c65a133","alias":"精品课-刘锋-封面.jpg","fileName":"15354443293105681.jpg","contentType":"image/jpeg","size":41770,"createdOn":1535444329310}
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
             * id : 5b85056904440c128c65a133
             * alias : 精品课-刘锋-封面.jpg
             * fileName : 15354443293105681.jpg
             * contentType : image/jpeg
             * size : 41770
             * createdOn : 1535444329310
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
         * id : 5b850b9704440c128c65a18c
         * alias : 精品课-刘锋-课程详情.jpg
         * fileName : 15354459113759169.jpg
         * contentType : image/jpeg
         * size : 593726
         * createdOn : 1535445911375
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
         * id : 1536057201329
         * title : 发刊词
         * duration : 243
         * audio : {"id":"5bab4a2e2557b90cc4e6a49b","alias":"发刊词.mp3","fileName":"15379523025745651.mp3","contentType":"audio/mp3","size":5977373,"createdOn":1537952302574}
         * contentImages : [{"id":"5b8e602604440c12508dd828","alias":"发刊词.jpg","fileName":"15360573825003719.jpg","contentType":"image/jpeg","size":802675,"createdOn":1536057382500}]
         * free : true
         * index : 1
         * updatedOn : 1536057201329
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
             * id : 5bab4a2e2557b90cc4e6a49b
             * alias : 发刊词.mp3
             * fileName : 15379523025745651.mp3
             * contentType : audio/mp3
             * size : 5977373
             * createdOn : 1537952302574
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
             * id : 5b8e602604440c12508dd828
             * alias : 发刊词.jpg
             * fileName : 15360573825003719.jpg
             * contentType : image/jpeg
             * size : 802675
             * createdOn : 1536057382500
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
