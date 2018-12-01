package com.mango.bc.mine.activity.point.presenter;

import android.content.Context;


/**
 * Created by admin on 2018/5/21.
 */

public interface PointPresenter {
    void visitPoints(Context context, int status, int page, Boolean ifCache);//刷新动作加载书
}
