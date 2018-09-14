package com.mango.bc.bookcase.net.presenter;

import android.content.Context;


/**
 * Created by admin on 2018/5/21.
 */

public interface MyBookPresenter {
    void visitBooks(Context context, int type, int page, Boolean ifCache);//刷新动作加载书
}
