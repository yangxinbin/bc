package com.mango.bc.homepage.net.view;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface BookSearchView {
    void addSearchBook(List<BookBean> bookBeanList);
    void addSuccessSearchBook(String s);
    void addFailSearchBook(String f);
}
