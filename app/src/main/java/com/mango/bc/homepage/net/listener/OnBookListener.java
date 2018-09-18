package com.mango.bc.homepage.net.listener;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.BookBean;

import java.util.List;

/**
 * Created by admin on 2018/5/21.
 */

public interface OnBookListener {
    void onSuccessCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList);
    void onSuccessCompetitiveBook(List<BookBean> bookBeanList);
    void onSuccessExpertBook(List<BookBean> bookBeanList);
    void onSuccessFreeBook(List<BookBean> bookBeanList);
    void onSuccessNewestBook(List<BookBean> bookBeanList);
    void onSuccessSearchBook(List<BookBean> bookBeanList);

    void onSuccessMesCompetitiveField(String msg);
    void onSuccessMesCompetitiveBook(String msg);
    void onSuccessMesExpertBook(String msg);
    void onSuccessMesFreeBook(String msg);
    void onSuccessMesNewestBook(String msg);
    void onSuccessMesSearchBook(String msg);

    void onFailMesCompetitiveField(String msg, Exception e);
    void onFailMesCompetitiveBook(String msg, Exception e);
    void onFailMesExpertBook(String msg, Exception e);
    void onFailMesFreeBook(String msg, Exception e);
    void onFailMesNewestBook(String msg, Exception e);
    void onFailMesSearchBook(String msg, Exception e);

}
