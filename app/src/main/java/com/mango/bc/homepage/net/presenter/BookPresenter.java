package com.mango.bc.homepage.net.presenter;

import android.content.Context;


/**
 * Created by admin on 2018/5/21.
 */

public interface BookPresenter {
    void visitBooks(Context context, int type,String keyWordString, int page,Boolean ifCache);//刷新动作加载书
}
