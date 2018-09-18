package com.mango.bc.bookcase.net.presenter;

import android.content.Context;
import android.util.Log;

import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.listener.OnMyBookListener;
import com.mango.bc.bookcase.net.model.MyBookModel;
import com.mango.bc.bookcase.net.model.MyBookModelImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.bookcase.net.view.MyCompetitiveBookView;
import com.mango.bc.bookcase.net.view.MyExpertBookView;
import com.mango.bc.bookcase.net.view.MyFreeBookView;
import com.mango.bc.util.Urls;

import java.util.List;

/**
 * Created by admin on 2018/5/21.
 */

public class MyBookPresenterImpl implements MyBookPresenter, OnMyBookListener {
    private MyCompetitiveBookView myCompetitiveBookView;
    private MyExpertBookView myExpertBookView;
    private MyFreeBookView myFreeBookView;
    private MyAllBookView myAllBookView;
    private MyBookModel bookModel;

    public MyBookPresenterImpl(MyCompetitiveBookView u) {
        this.myCompetitiveBookView = u;
        this.bookModel = new MyBookModelImpl();
    }

    public MyBookPresenterImpl(MyExpertBookView u) {
        this.myExpertBookView = u;
        this.bookModel = new MyBookModelImpl();
    }

    public MyBookPresenterImpl(MyFreeBookView u) {
        this.myFreeBookView = u;
        this.bookModel = new MyBookModelImpl();
    }

    public MyBookPresenterImpl(MyAllBookView u) {
        this.myAllBookView = u;
        this.bookModel = new MyBookModelImpl();
    }

    @Override
    public void visitBooks(Context context, int type, int page, Boolean ifCache) {
        String url = null;
        if (type == 0) {
            url = getUrl(type, context);
        } else if (type == 1) {
            url = getUrl(type, context);
        } else if (type == 2) {
            url = getUrl(type, context);
        } else if (type == 3) {//所有
            url = getUrl(type, context);
        }
        Log.v("pppppppppppp", "" + url);
        bookModel.visitBooks(context, type, url, page, ifCache, this);
    }

    private String getUrl(int type, Context context) {
        StringBuffer sburl = new StringBuffer();
        switch (type) {
            case 0:
                sburl.append(Urls.HOST_LIBRARY);
                break;
            case 1:
                sburl.append(Urls.HOST_LIBRARY);
                break;
            case 2:
                sburl.append(Urls.HOST_LIBRARY);
                break;
            case 3:
                sburl.append(Urls.HOST_LIBRARY);
                break;
        }
        return sburl.toString();
    }

    @Override
    public void onSuccessCompetitiveBook(List<MyBookBean> bookBeanList) {
        myCompetitiveBookView.addCompetitiveBook(bookBeanList);
    }


    @Override
    public void onSuccessExpertBook(List<MyBookBean> bookBeanList) {
        myExpertBookView.addExpertBook(bookBeanList);
    }

    @Override
    public void onSuccessFreeBook(List<MyBookBean> bookBeanList) {
        myFreeBookView.addFreeBook(bookBeanList);
    }

    @Override
    public void onSuccessAllBook(List<MyBookBean> bookBeanList) {
        myAllBookView.addAllBook(bookBeanList);
    }

    @Override
    public void onCompetitiveBookSuccessMes(String msg) {
        myCompetitiveBookView.addSuccess(msg);
    }

    @Override
    public void onExpertBookSuccessMes(String msg) {
        myExpertBookView.addSuccess(msg);
    }

    @Override
    public void onFreeBookSuccessMes(String msg) {
        myFreeBookView.addSuccess(msg);
    }

    @Override
    public void onAllBookSuccessMes(String msg) {
        myAllBookView.addSuccess(msg);
    }

    @Override
    public void onCompetitiveFailMes(String msg, Exception e) {
        myCompetitiveBookView.addFail(msg);
    }

    @Override
    public void onExpertFailMes(String msg, Exception e) {
        myExpertBookView.addFail(msg);
    }

    @Override
    public void onFreeBookFailMes(String msg, Exception e) {
        myFreeBookView.addFail(msg);
    }

    @Override
    public void onAllBookFailMes(String msg, Exception e) {
        myAllBookView.addFail(msg);
    }

}
