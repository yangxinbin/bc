package com.mango.bc.mine.activity.point.view;

import com.mango.bc.mine.bean.MemberBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface PointSuccessView {
    void addPointSuccess(List<MemberBean> memberBeans);

    void addSuccessPointSuccess(String s);

    void addFailPointSuccess(String f);
}
