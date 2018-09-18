package com.mango.bc.homepage.net.view;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface BookFreeView {
    void addFreeBook(List<BookBean> bookBeanList);
    void addSuccessFreeBook(String s);
    void addFailFreeBook(String f);
}
