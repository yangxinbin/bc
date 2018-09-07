package com.mango.bc.homepage.bean;

/**
 * Created by admin on 2018/9/7.
 */

public class BookFree {
    private String bookFreeImg;
    private String bookFreeTitle;
    private String bookFreeStage;

    public BookFree(String bookFreeImg, String bookFreeTitle, String bookFreeStage) {
        this.bookFreeImg = bookFreeImg;
        this.bookFreeTitle = bookFreeTitle;
        this.bookFreeStage = bookFreeStage;
    }

    public String getBookFreeImg() {
        return bookFreeImg;
    }

    public void setBookFreeImg(String bookFreeImg) {
        this.bookFreeImg = bookFreeImg;
    }

    public String getBookFreeTitle() {
        return bookFreeTitle;
    }

    public void setBookFreeTitle(String bookFreeTitle) {
        this.bookFreeTitle = bookFreeTitle;
    }

    public String getBookFreeStage() {
        return bookFreeStage;
    }

    public void setBookFreeStage(String bookFreeStage) {
        this.bookFreeStage = bookFreeStage;
    }
}
