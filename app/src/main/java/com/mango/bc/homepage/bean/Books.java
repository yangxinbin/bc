package com.mango.bc.homepage.bean;

/**
 * Created by admin on 2018/9/7.
 */

public class Books {
    private String bookImg;
    private String bookTitle;
    private String bookDetail;
    private String bookTime;
    private String bookBuy;
    private String bookStage;

    public Books(String bookImg, String bookTitle, String bookDetail, String bookTime, String bookBuy, String bookStage) {
        this.bookImg = bookImg;
        this.bookTitle = bookTitle;
        this.bookDetail = bookDetail;
        this.bookTime = bookTime;
        this.bookBuy = bookBuy;
        this.bookStage = bookStage;
    }

    public Books() {
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getBookBuy() {
        return bookBuy;
    }

    public void setBookBuy(String bookBuy) {
        this.bookBuy = bookBuy;
    }

    public String getBookStage() {
        return bookStage;
    }

    public void setBookStage(String bookStage) {
        this.bookStage = bookStage;
    }
}
