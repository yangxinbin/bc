package com.mango.bc.bookcase.net.listener;

import com.mango.bc.bookcase.net.bean.MyBookBean;

import java.util.List;

/**
 * Created by admin on 2018/5/21.
 */

public interface OnMyBookListener {
    void onSuccessCompetitiveBook(List<MyBookBean> bookBeanList);
    void onSuccessExpertBook(List<MyBookBean> bookBeanList);
    void onSuccessFreeBook(List<MyBookBean> bookBeanList);

    void onSuccessMes(String msg);
    void onFailMes(String msg, Exception e);
}
