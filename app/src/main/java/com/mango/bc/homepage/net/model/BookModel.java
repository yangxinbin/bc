package com.mango.bc.homepage.net.model;

import android.content.Context;

import com.mango.bc.homepage.net.listener.OnBookListener;

/**
 * Created by admin on 2018/5/21.
 */

public interface BookModel {
    void visitBooks(Context context, int type, String url, String tabString,int page, OnBookListener listener);//刷新动作加载书
}
