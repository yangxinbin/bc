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
    void onSuccessAllBook(List<MyBookBean> bookBeanList);

    void onCompetitiveBookSuccessMes(String msg);
    void onExpertBookSuccessMes(String msg);
    void onFreeBookSuccessMes(String msg);
    void onAllBookSuccessMes(String msg);

    void onCompetitiveFailMes(String msg, Exception e);
    void onExpertFailMes(String msg, Exception e);
    void onFreeBookFailMes(String msg, Exception e);
    void onAllBookFailMes(String msg, Exception e);

}
