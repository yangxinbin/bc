package com.mango.bc.homepage.net.view;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.ExpertBookBean;
import com.mango.bc.homepage.net.bean.FreeBookBean;
import com.mango.bc.homepage.net.bean.NewestBookBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface BookView {
    void addCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList);
    void addCompetitiveBook(List<BookBean> bookBeanList);
    void addExpertBook(List<ExpertBookBean> expertBookBeanList);
    void addFreeBook(List<BookBean> bookBeanList);
    void addNewestBook(List<NewestBookBean> newestBookBeanList);

    void addSuccess(String s);
    void addFail(String f);
}
