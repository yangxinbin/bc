package com.mango.bc.homepage.net.listener;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.ExpertBookBean;
import com.mango.bc.homepage.net.bean.FreeBookBean;
import com.mango.bc.homepage.net.bean.NewestBookBean;

import java.util.List;

/**
 * Created by admin on 2018/5/21.
 */

public interface OnBookListener {
    void onSuccessCompetitiveBook(List<BookBean> bookBeanList);
    void onSuccessCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList);
    void onSuccessExpertBook(List<ExpertBookBean> expertBookBeanList);
    void onSuccessFreeBook(List<BookBean> bookBeanList);
    void onSuccessNewestBook(List<NewestBookBean> newestBookBeanList);

    void onSuccessMes(String msg);
    void onFailMes(String msg, Exception e);
}
