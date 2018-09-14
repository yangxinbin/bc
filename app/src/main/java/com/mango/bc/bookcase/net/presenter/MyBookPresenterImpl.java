package com.mango.bc.bookcase.net.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.listener.OnMyBookListener;
import com.mango.bc.bookcase.net.model.MyBookModel;
import com.mango.bc.bookcase.net.model.MyBookModelImpl;
import com.mango.bc.bookcase.net.view.MyBookView;
import com.mango.bc.util.Urls;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/21.
 */

public class MyBookPresenterImpl implements MyBookPresenter, OnMyBookListener {
    private MyBookView bookView;
    private MyBookModel bookModel;
    private SharedPreferences sharedPreferences;

    public MyBookPresenterImpl(MyBookView u) {
        this.bookView = u;
        this.bookModel = new MyBookModelImpl();
    }

    @Override
    public void visitBooks(Context context, int type, int page, Boolean ifCache) {
        sharedPreferences = context.getSharedPreferences("BC", MODE_PRIVATE);
        String url = null;
        if (type == 0) {
            url = getUrl(type, context);
        } else if (type == 1) {
            url = getUrl(type, context);
        } else if (type == 2) {
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
        }
        return sburl.toString();
    }

    @Override
    public void onSuccessCompetitiveBook(List<MyBookBean> bookBeanList) {
        bookView.addCompetitiveBook(bookBeanList);
    }


    @Override
    public void onSuccessExpertBook(List<MyBookBean> bookBeanList) {
        bookView.addExpertBook(bookBeanList);
    }

    @Override
    public void onSuccessFreeBook(List<MyBookBean> bookBeanList) {
        bookView.addFreeBook(bookBeanList);
    }


    @Override
    public void onSuccessMes(String msg) {
        bookView.addSuccess(msg);
    }

    @Override
    public void onFailMes(String msg, Exception e) {
        bookView.addFail(msg);
    }
}
