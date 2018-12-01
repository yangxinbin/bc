package com.mango.bc.mine.activity.point.model;

import android.content.Context;

import com.mango.bc.mine.activity.point.listenter.OnPointListener;

/**
 * Created by admin on 2018/5/21.
 */

public interface PointModel {
    void visitPoints(Context context, int status, String url, int page, Boolean ifCache, OnPointListener listener);//刷新动作加载书
}
