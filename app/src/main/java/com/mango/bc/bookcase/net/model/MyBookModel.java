package com.mango.bc.bookcase.net.model;

import android.content.Context;

import com.mango.bc.bookcase.net.listener.OnMyBookListener;

/**
 * Created by admin on 2018/5/21.
 */

public interface MyBookModel {
    void visitBooks(Context context, int type, String url, int page, Boolean ifCache, OnMyBookListener listener);//刷新动作加载书
}
