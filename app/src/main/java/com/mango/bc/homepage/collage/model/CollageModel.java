package com.mango.bc.homepage.collage.model;

import android.content.Context;

import com.mango.bc.homepage.collage.listenter.OnCollageListener;
import com.mango.bc.homepage.net.listener.OnBookListener;

/**
 * Created by admin on 2018/5/21.
 */

public interface CollageModel {
    void visitCollages(Context context, int status, String url, int page, Boolean ifCache, OnCollageListener listener);//刷新动作加载书
}
