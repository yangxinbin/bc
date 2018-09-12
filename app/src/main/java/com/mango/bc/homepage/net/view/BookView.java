package com.mango.bc.homepage.net.view;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.BookBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface BookView {
    void addCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList);
    void addCompetitiveBook(List<BookBean> bookBeanList);
    void addExpertBook(List<BookBean> bookBeanList);
    void addFreeBook(List<BookBean> bookBeanList);
    void addNewestBook(List<BookBean> bookBeanList);
    void addSearchBook(List<BookBean> bookBeanList);

    void addSuccess(String s);
    void addFail(String f);
}
