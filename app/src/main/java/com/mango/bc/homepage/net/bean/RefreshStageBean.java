package com.mango.bc.homepage.net.bean;

/**
 * Created by admin on 2018/9/10.
 */

public class RefreshStageBean {
    private Boolean competitiveField;
    private Boolean competitiveBook;
    private Boolean expertBook;
    private Boolean freeBook;
    private Boolean newestBook;

    public RefreshStageBean(Boolean competitiveField, Boolean competitiveBook, Boolean expertBook, Boolean freeBook, Boolean newestBook) {
        this.competitiveField = competitiveField;
        this.competitiveBook = competitiveBook;
        this.expertBook = expertBook;
        this.freeBook = freeBook;
        this.newestBook = newestBook;
    }

    public Boolean getCompetitiveField() {
        return competitiveField;
    }

    public void setCompetitiveField(Boolean competitiveField) {
        this.competitiveField = competitiveField;
    }

    public Boolean getCompetitiveBook() {
        return competitiveBook;
    }

    public void setCompetitiveBook(Boolean competitiveBook) {
        this.competitiveBook = competitiveBook;
    }

    public Boolean getExpertBook() {
        return expertBook;
    }

    public void setExpertBook(Boolean expertBook) {
        this.expertBook = expertBook;
    }

    public Boolean getFreeBook() {
        return freeBook;
    }

    public void setFreeBook(Boolean freeBook) {
        this.freeBook = freeBook;
    }

    public Boolean getNewestBook() {
        return newestBook;
    }

    public void setNewestBook(Boolean newestBook) {
        this.newestBook = newestBook;
    }

    @Override
    public String toString() {
        return "RefreshStageBean{" +
                "competitiveField=" + competitiveField +
                ", competitiveBook=" + competitiveBook +
                ", expertBook=" + expertBook +
                ", freeBook=" + freeBook +
                ", newestBook=" + newestBook +
                '}';
    }
}
