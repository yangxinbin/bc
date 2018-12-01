package com.mango.bc.mine.activity.point.listenter;

import com.mango.bc.mine.bean.MemberBean;

import java.util.List;

/**
 * Created by admin on 2018/5/21.
 */

public interface OnPointListener {
    void onSuccessPointIng(List<MemberBean> memberBeans);
    void onSuccessPointSuccess(List<MemberBean> memberBeans);
    void onSuccessPointFail(List<MemberBean> memberBeans);

    void onSuccessMesPointIng(String msg);
    void onSuccessMesPointSuccess(String msg);
    void onSuccessMesPointFail(String msg);

    void onFailMesPointIng(String msg, Exception e);
    void onFailMesPointSuccess(String msg, Exception e);
    void onFailMesPointFail(String msg, Exception e);

}
