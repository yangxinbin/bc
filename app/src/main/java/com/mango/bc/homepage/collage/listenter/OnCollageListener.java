package com.mango.bc.homepage.collage.listenter;

import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;

import java.util.List;

/**
 * Created by admin on 2018/5/21.
 */

public interface OnCollageListener {
    void onSuccessCollageAll(List<CollageBean> collageBeans);
    void onSuccessCollageIng(List<CollageBean> collageBeans);
    void onSuccessCollageSuccess(List<CollageBean> collageBeans);
    void onSuccessCollageFail(List<CollageBean> collageBeans);

    void onSuccessMesCollageAll(String msg);
    void onSuccessMesCollageIng(String msg);
    void onSuccessMesCollageSuccess(String msg);
    void onSuccessMesCollageFail(String msg);

    void onFailMesCollageAll(String msg, Exception e);
    void onFailMesCollageIng(String msg, Exception e);
    void onFailMesCollageSuccess(String msg, Exception e);
    void onFailMesCollageFail(String msg, Exception e);

}
