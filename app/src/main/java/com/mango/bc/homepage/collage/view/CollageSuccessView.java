package com.mango.bc.homepage.collage.view;

import com.mango.bc.homepage.collage.bean.CollageBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface CollageSuccessView {
    void addCollageSuccess(List<CollageBean> collageBeans);

    void addSuccessCollageSuccess(String s);

    void addFailCollageSuccess(String f);
}
