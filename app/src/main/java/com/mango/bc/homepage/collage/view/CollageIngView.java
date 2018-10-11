package com.mango.bc.homepage.collage.view;

import com.mango.bc.homepage.collage.bean.CollageBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface CollageIngView {
    void addCollageIng(List<CollageBean> collageBeans);

    void addSuccessCollageIng(String s);

    void addFailCollageIng(String f);
}
