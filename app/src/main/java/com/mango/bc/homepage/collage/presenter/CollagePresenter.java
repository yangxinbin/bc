package com.mango.bc.homepage.collage.presenter;

import android.content.Context;


/**
 * Created by admin on 2018/5/21.
 */

public interface CollagePresenter {
    void visitCollages(Context context, int status,int page, Boolean ifCache);//刷新动作加载书
}
