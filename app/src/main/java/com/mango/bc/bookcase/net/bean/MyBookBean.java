package com.mango.bc.bookcase.net.bean;

import java.util.List;

/**
 * Created by admin on 2018/9/10.
 */

public class MyBookBean {

    /**
     * id : 5b99d13e2557b90fe0c6abb7
     * userId : 5b8a3d4b04440c0a48a33a05
     * book : {"id":"5b86879a04440c0e6cfabaa6","title":"李林：币圈扑克牌大佬的幕后\u201c大东家\u201d","subtitle":"火币交易平台创始人，不是说好不收手续费吗？","recommendation":"","descriptionImages":[{"id":"5b8687f704440c0e6cfabaa7","alias":"李林详情.jpg","fileName":"15355432877415548.jpg","contentType":"image/jpeg","size":214913,"createdOn":1535543287741}],"cover":{"id":"5b86879a04440c0e6cfabaa4","alias":"李林.jpg","fileName":"15355431948186421.jpg","contentType":"image/jpeg","size":90295,"createdOn":1535543194818},"banner":null,"category":"Other","author":{"id":"5b839f2b04440c1224e72ba7","name":"BC大陆","introduction":"no","photo":{"id":"5b8501b204440c128c65a11e","alias":"作者-BC大陆.jpg","fileName":"15354433784241925.jpg","contentType":"image/jpeg","size":39399,"createdOn":1535443378424}},"chapters":[{"id":"1535543301760","title":"李林：币圈扑克牌大佬的幕后\u201c大东家\u201d","duration":440,"audio":{"id":"5b86884f04440c0e6cfabaae","alias":"李林：币圈扑克牌大佬的幕后\u201c大东家\u201d.mp3","fileName":"15355433756045834.mp3","contentType":"audio/mp3","size":10571110,"createdOn":1535543375604},"contentImages":[{"id":"5b868a6204440c0e6cfabb02","alias":"李林内容.jpg","fileName":"15355439065987954.jpg","contentType":"image/jpeg","size":815747,"createdOn":1535543906598}],"free":true,"index":1,"updatedOn":1535543301760}],"tags":[],"price":0,"vipPrice":0,"groupBuy2Price":0,"groupBuy3Price":0,"type":"free","likes":11,"likesPlus":0,"sold":52,"soldPlus":0,"recommended":0,"updatedOn":1536739740990,"updatedBy":"5b84ad3904440c128c32992b","status":"online"}
     * type : free
     * createdOn : 1536807230174
     * percentComplete : 0
     */

    private String id;
    private String userId;
    private BookBean book;
    private String type;
    private long createdOn;
    private int percentComplete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public int getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(int percentComplete) {
        this.percentComplete = percentComplete;
    }

    public static class BookBean {
        /**
         * id : 5b86879a04440c0e6cfabaa6
         * title : 李林：币圈扑克牌大佬的幕后“大东家”
         * subtitle : 火币交易平台创始人，不是说好不收手续费吗？
         * recommendation :
         * descriptionImages : [{"id":"5b8687f704440c0e6cfabaa7","alias":"李林详情.jpg","fileName":"15355432877415548.jpg","contentType":"image/jpeg","size":214913,"createdOn":1535543287741}]
         * cover : {"id":"5b86879a04440c0e6cfabaa4","alias":"李林.jpg","fileName":"15355431948186421.jpg","contentType":"image/jpeg","size":90295,"createdOn":1535543194818}
         * banner : null
         * category : Other
         * author : {"id":"5b839f2b04440c1224e72ba7","name":"BC大陆","introduction":"no","photo":{"id":"5b8501b204440c128c65a11e","alias":"作者-BC大陆.jpg","fileName":"15354433784241925.jpg","contentType":"image/jpeg","size":39399,"createdOn":1535443378424}}
         * chapters : [{"id":"1535543301760","title":"李林：币圈扑克牌大佬的幕后\u201c大东家\u201d","duration":440,"audio":{"id":"5b86884f04440c0e6cfabaae","alias":"李林：币圈扑克牌大佬的幕后\u201c大东家\u201d.mp3","fileName":"15355433756045834.mp3","contentType":"audio/mp3","size":10571110,"createdOn":1535543375604},"contentImages":[{"id":"5b868a6204440c0e6cfabb02","alias":"李林内容.jpg","fileName":"15355439065987954.jpg","contentType":"image/jpeg","size":815747,"createdOn":1535543906598}],"free":true,"index":1,"updatedOn":1535543301760}]
         * tags : []
         * price : 0.0
         * vipPrice : 0.0
         * groupBuy2Price : 0.0
         * groupBuy3Price : 0.0
         * type : free
         * likes : 11
         * likesPlus : 0
         * sold : 52
         * soldPlus : 0
         * recommended : 0
         * updatedOn : 1536739740990
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
             * id : 5b86879a04440c0e6cfabaa4
             * alias : 李林.jpg
             * fileName : 15355431948186421.jpg
             * contentType : image/jpeg
             * size : 90295
             * createdOn : 1535543194818
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
             * id : 5b8687f704440c0e6cfabaa7
             * alias : 李林详情.jpg
             * fileName : 15355432877415548.jpg
             * contentType : image/jpeg
             * size : 214913
             * createdOn : 1535543287741
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
             * id : 1535543301760
             * title : 李林：币圈扑克牌大佬的幕后“大东家”
             * duration : 440
             * audio : {"id":"5b86884f04440c0e6cfabaae","alias":"李林：币圈扑克牌大佬的幕后\u201c大东家\u201d.mp3","fileName":"15355433756045834.mp3","contentType":"audio/mp3","size":10571110,"createdOn":1535543375604}
             * contentImages : [{"id":"5b868a6204440c0e6cfabb02","alias":"李林内容.jpg","fileName":"15355439065987954.jpg","contentType":"image/jpeg","size":815747,"createdOn":1535543906598}]
             * free : true
             * index : 1
             * updatedOn : 1535543301760
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
                 * id : 5b86884f04440c0e6cfabaae
                 * alias : 李林：币圈扑克牌大佬的幕后“大东家”.mp3
                 * fileName : 15355433756045834.mp3
                 * contentType : audio/mp3
                 * size : 10571110
                 * createdOn : 1535543375604
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
                 * id : 5b868a6204440c0e6cfabb02
                 * alias : 李林内容.jpg
                 * fileName : 15355439065987954.jpg
                 * contentType : image/jpeg
                 * size : 815747
                 * createdOn : 1535543906598
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
}
