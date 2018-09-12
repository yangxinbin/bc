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
    void onSuccessCompetitiveBook(List<BookBean> bookBeanList);
    void onSuccessCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList);
    void onSuccessExpertBook(List<BookBean> bookBeanList);
    void onSuccessFreeBook(List<BookBean> bookBeanList);
    void onSuccessNewestBook(List<BookBean> bookBeanList);
    void onSuccessSearchBook(List<BookBean> bookBeanList);

    void onSuccessMes(String msg);
    void onFailMes(String msg, Exception e);
}
