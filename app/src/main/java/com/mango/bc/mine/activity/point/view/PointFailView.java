package com.mango.bc.mine.activity.point.view;

import com.mango.bc.mine.bean.MemberBean;

import java.util.List;

/**
 * Created by admin on 2018/7/10.
 */

public interface PointFailView {
    void addPointFail(List<MemberBean> memberBeans);

    void addSuccessPointFail(String s);

    void addFailPointFail(String f);
}
