package com.mango.bc.homepage.net.view;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface BookExpertView {
    void addExpertBook(List<BookBean> bookBeanList);
    void addSuccessExpertBook(String s);
    void addFailExpertBook(String f);
}
