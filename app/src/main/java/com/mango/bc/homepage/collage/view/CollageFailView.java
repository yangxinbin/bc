package com.mango.bc.homepage.collage.view;

import com.mango.bc.homepage.collage.bean.CollageBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface CollageFailView {
    void addCollageFail(List<CollageBean> collageBeans);

    void addSuccessCollageFail(String s);

    void addFailCollageFail(String f);
}
