package com.mango.bc.homepage.net.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.listener.OnBookListener;
import com.mango.bc.homepage.net.model.BookModel;
import com.mango.bc.homepage.net.model.BookModelImpl;
import com.mango.bc.homepage.net.view.BookCompetitiveFieldView;
import com.mango.bc.homepage.net.view.BookCompetitiveView;
import com.mango.bc.homepage.net.view.BookExpertView;
import com.mango.bc.homepage.net.view.BookFreeView;
import com.mango.bc.homepage.net.view.BookNewestView;
import com.mango.bc.homepage.net.view.BookSearchView;
import com.mango.bc.util.URLEncoderURI;
import com.mango.bc.util.Urls;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/21.
 */

public class BookPresenterImpl implements BookPresenter, OnBookListener {
    private BookCompetitiveFieldView bookCompetitiveFieldView;
    private BookCompetitiveView bookCompetitiveView;
    private BookExpertView bookExpertView;
    private BookFreeView bookFreeView;
    private BookNewestView bookNewestView;
    private BookSearchView bookSearchView;

    private BookModel bookModel;
    private SharedPreferences sharedPreferences;

    public BookPresenterImpl(BookCompetitiveFieldView u) {
        this.bookCompetitiveFieldView = u;
        this.bookModel = new BookModelImpl();
    }
    public BookPresenterImpl(BookCompetitiveView u) {
        this.bookCompetitiveView = u;
        this.bookModel = new BookModelImpl();
    }
    public BookPresenterImpl(BookExpertView u) {
        this.bookExpertView = u;
        this.bookModel = new BookModelImpl();
    }
    public BookPresenterImpl(BookFreeView u) {
        this.bookFreeView = u;
        this.bookModel = new BookModelImpl();
    }
    public BookPresenterImpl(BookNewestView u) {
        this.bookNewestView = u;
        this.bookModel = new BookModelImpl();
    }
    public BookPresenterImpl(BookSearchView u) {
        this.bookSearchView = u;
        this.bookModel = new BookModelImpl();
    }
    @Override
    public void visitBooks(Context context, int type, String keyWordString, int page, Boolean ifCache) {
        sharedPreferences = context.getSharedPreferences("BC", MODE_PRIVATE);
        String url = null;
        if (type == 0) {
            url = getUrl(type, context);
        } else if (type == 1) {
            try {
                url = getUrl(type, context) + "?category=" + URLEncoderURI.encode(keyWordString, "UTF-8") + "&page=" + page;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (type == 2) {
            url = getUrl(type, context) + "?type=paid" + "&page=" + page;
        } else if (type == 3) {
            url = getUrl(type, context) + "?type=free" + "&page=" + page;
        } else if (type == 4) {
            url = getUrl(type, context) + "?page=" + page;
        } else if (type == 5) {
            url = getUrl(type, context) + "?keyword=" + keyWordString + "&page=" + page;
        }
        Log.v("pppppppppppp", "" + url);
        bookModel.visitBooks(context, type, url, keyWordString, page, ifCache, this);
    }

    private String getUrl(int type, Context context) {
        StringBuffer sburl = new StringBuffer();
        switch (type) {
            case 0:
                sburl.append(Urls.HOST_BOOKCATEGORIES);//精品字段
                break;
            case 1:
                sburl.append(Urls.HOST_BOOKLISTCATEGORIES);//精品课列表
                break;
            case 2:
                sburl.append(Urls.HOST_BOOKTYPE);//大咖
                break;
            case 3:
                sburl.append(Urls.HOST_BOOKTYPE);//免费
                break;
            case 4:
                sburl.append(Urls.HOST_RECENT);//最新
                break;
            case 5:
                sburl.append(Urls.HOST_SEARCH);//search
                break;
        }
        return sburl.toString();
    }

    @Override
    public void onSuccessCompetitiveBook(List<BookBean> bookBeanList) {
        bookCompetitiveView.addCompetitiveBook(bookBeanList);
    }

    @Override
    public void onSuccessCompetitiveField(List<CompetitiveFieldBean> competitiveFieldBeanList) {
        bookCompetitiveFieldView.addCompetitiveField(competitiveFieldBeanList);
    }

    @Override
    public void onSuccessExpertBook(List<BookBean> bookBeanList) {
        bookExpertView.addExpertBook(bookBeanList);
    }

    @Override
    public void onSuccessFreeBook(List<BookBean> bookBeanList) {
        bookFreeView.addFreeBook(bookBeanList);
    }

    @Override
    public void onSuccessNewestBook(List<BookBean> bookBeanList) {
        bookNewestView.addNewestBook(bookBeanList);
    }

    @Override
    public void onSuccessSearchBook(List<BookBean> bookBeanList) {
        bookSearchView.addSearchBook(bookBeanList);
    }

    @Override
    public void onSuccessMesCompetitiveField(String msg) {
        bookCompetitiveFieldView.addSuccessCompetitiveField(msg);
    }

    @Override
    public void onSuccessMesCompetitiveBook(String msg) {
        bookCompetitiveView.addSuccessCompetitiveBook(msg);
    }

    @Override
    public void onSuccessMesExpertBook(String msg) {
        bookExpertView.addSuccessExpertBook(msg);
    }

    @Override
    public void onSuccessMesFreeBook(String msg) {
        bookFreeView.addSuccessFreeBook(msg);

    }

    @Override
    public void onSuccessMesNewestBook(String msg) {
        bookNewestView.addSuccessNewestBook(msg);

    }

    @Override
    public void onSuccessMesSearchBook(String msg) {
        bookSearchView.addSuccessSearchBook(msg);

    }

    @Override
    public void onFailMesCompetitiveField(String msg, Exception e) {
        bookCompetitiveFieldView.addFailCompetitiveField(msg);

    }

    @Override
    public void onFailMesCompetitiveBook(String msg, Exception e) {
        bookCompetitiveView.addFailCompetitiveBook(msg);

    }

    @Override
    public void onFailMesExpertBook(String msg, Exception e) {
        bookExpertView.addFailExpertBook(msg);

    }

    @Override
    public void onFailMesFreeBook(String msg, Exception e) {
        bookFreeView.addFailFreeBook(msg);

    }

    @Override
    public void onFailMesNewestBook(String msg, Exception e) {
        bookNewestView.addFailNewestBook(msg);

    }

    @Override
    public void onFailMesSearchBook(String msg, Exception e) {
        bookSearchView.addFailSearchBook(msg);

    }
}
