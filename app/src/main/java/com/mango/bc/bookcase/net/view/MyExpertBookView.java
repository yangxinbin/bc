package com.mango.bc.bookcase.net.view;

import com.mango.bc.bookcase.net.bean.MyBookBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface MyExpertBookView {
    void addExpertBook(List<MyBookBean> bookBeanList);
    void addSuccess(String s);
    void addFail(String f);
}
