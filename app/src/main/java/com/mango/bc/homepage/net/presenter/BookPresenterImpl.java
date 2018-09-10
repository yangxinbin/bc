package com.mango.bc.homepage.net.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mango.bc.homepage.net.bean.CompetitiveBookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.ExpertBookBean;
import com.mango.bc.homepage.net.bean.FreeBookBean;
import com.mango.bc.homepage.net.bean.NewestBookBean;
import com.mango.bc.homepage.net.listener.OnBookListener;
import com.mango.bc.homepage.net.model.BookModel;
import com.mango.bc.homepage.net.model.BookModelImpl;
import com.mango.bc.homepage.net.view.BookView;
import com.mango.bc.util.Urls;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/21.
 */

public class BookPresenterImpl implements BookPresenter, OnBookListener {
    private BookView bookView;
    private BookModel bookModel;
    private SharedPreferences sharedPreferences;

    public BookPresenterImpl(BookView u) {
        this.bookView = u;
        this.bookModel = new BookModelImpl();
    }

    @Override
    public void visitBooks(Context context, int type,String tabString, int page) {
        sharedPreferences = context.getSharedPreferences("DCOM", MODE_PRIVATE);
        String url = null;
        if (type == 0) {
            url = getUrl(type, context);
        } else if (type == 1) {
            url = getUrl(type, context) + "?token=" + sharedPreferences.getString("token", "") + "&pageNum=" + page;
        } else if (type == 2) {
            url = getUrl(type, context);
        }
        Log.v("pppppppppppp", "" + url);
        bookModel.visitBooks(context, type, url,tabString,page, this);
    }
    private String getUrl(int type, Context context) {
        StringBuffer sburl = new StringBuffer();
        switch (type) {
            case 0:
                sburl.append(Urls.HOST_BOOKCATEGORIES);
                break;
            case 1:
                sburl.append(Urls.HOST_BOOKLISTCATEGORIES);
                break;
            case 2:
                sburl.append(Urls.HOST_BOOKTYPE);
                break;
        }
        return sburl.toString();
    }

    @Override
    public void onSuccessCompetitiveBook(List<CompetitiveBookBean> competitiveBookBeanList) {

    }

    @Override
    public void onSuccessCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList) {

    }

    @Override
    public void onSuccessExpertBook(List<ExpertBookBean> expertBookBeanList) {

    }

    @Override
    public void onSuccessFreeBook(List<FreeBookBean> freeBookBeanList) {

    }

    @Override
    public void onSuccessNewestBook(List<NewestBookBean> newestBookBeanList) {

    }

    @Override
    public void onSuccessMes(String msg) {

    }

    @Override
    public void onFailMes(String msg, Exception e) {

    }
}
