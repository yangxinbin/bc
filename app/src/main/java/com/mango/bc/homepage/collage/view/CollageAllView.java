package com.mango.bc.homepage.collage.view;

import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.homepage.net.bean.BookBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface CollageAllView {
    void addCollageAll(List<CollageBean> collageBeans);

    void addSuccessCollageAll(String s);

    void addFailCollageAll(String f);
}
