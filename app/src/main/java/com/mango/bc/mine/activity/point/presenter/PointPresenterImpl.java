package com.mango.bc.mine.activity.point.presenter;

import android.content.Context;
import android.util.Log;

import com.mango.bc.mine.activity.point.listenter.OnPointListener;
import com.mango.bc.mine.activity.point.model.PointModel;
import com.mango.bc.mine.activity.point.model.PointModelImpl;
import com.mango.bc.mine.activity.point.view.PointFailView;
import com.mango.bc.mine.activity.point.view.PointIngView;
import com.mango.bc.mine.activity.point.view.PointSuccessView;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.util.Urls;

import java.util.List;

/**
 * Created by admin on 2018/5/21.
 */

public class PointPresenterImpl implements PointPresenter, OnPointListener {
    private PointIngView pointIngView;
    private PointSuccessView pointSuccessView;
    private PointFailView pointFailView;

    private PointModel pointModel;


    public PointPresenterImpl(PointIngView u) {
        this.pointIngView = u;
        this.pointModel = new PointModelImpl();
    }

    public PointPresenterImpl(PointSuccessView u) {
        this.pointSuccessView = u;
        this.pointModel = new PointModelImpl();
    }

    public PointPresenterImpl(PointFailView u) {
        this.pointFailView = u;
        this.pointModel = new PointModelImpl();
    }

    private String getUrl(int status, Context context) {
        StringBuffer sburl = new StringBuffer();
        switch (status) {
            case 0:
                sburl.append(Urls.HOST_MEMBER);
                break;
            case 1:
                sburl.append(Urls.HOST_MEMBER);
                break;
            case 2:
                sburl.append(Urls.HOST_MEMBER);
                break;
        }
        return sburl.toString();
    }

    @Override
    public void visitPoints(Context context, int status, int page, Boolean ifCache) {
        String url = null;
        if (status == 0) {
            url = getUrl(status, context)/* + "?status=" + "&page=" + page*/;
        } else if (status == 1) {
            url = getUrl(status, context)/* + "?status=started" + "&page=" + page*/;
        } else if (status == 2) {
            url = getUrl(status, context)/* + "?status=finished" + "&page=" + page*/;
        } else if (status == 3) {
            url = getUrl(status, context)/* + "?status=expired" + "&page=" + page*/;
        }
        Log.v("ppppppppppppp", "" + url);
        pointModel.visitPoints(context, status, url, page, ifCache, this);
    }

    @Override
    public void onSuccessPointIng(List<MemberBean> MemberBeans) {
        pointIngView.addPointIng(MemberBeans);
    }

    @Override
    public void onSuccessPointSuccess(List<MemberBean> MemberBeans) {
        pointSuccessView.addPointSuccess(MemberBeans);
    }

    @Override
    public void onSuccessPointFail(List<MemberBean> MemberBeans) {
        pointFailView.addPointFail(MemberBeans);
    }

    @Override
    public void onSuccessMesPointIng(String msg) {
        pointIngView.addPointCollageIng(msg);
    }

    @Override
    public void onSuccessMesPointSuccess(String msg) {
        pointSuccessView.addSuccessPointSuccess(msg);
    }

    @Override
    public void onSuccessMesPointFail(String msg) {
        pointFailView.addSuccessPointFail(msg);
    }

    @Override
    public void onFailMesPointIng(String msg, Exception e) {
        pointIngView.addFailPointIng(msg);
    }

    @Override
    public void onFailMesPointSuccess(String msg, Exception e) {
        pointSuccessView.addFailPointSuccess(msg);
    }

    @Override
    public void onFailMesPointFail(String msg, Exception e) {
        pointFailView.addFailPointFail(msg);
    }
}
