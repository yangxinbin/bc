package com.mango.bc.homepage.collage.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.homepage.collage.listenter.OnCollageListener;
import com.mango.bc.homepage.collage.model.CollageModel;
import com.mango.bc.homepage.collage.model.CollageModelImpl;
import com.mango.bc.homepage.collage.view.CollageAllView;
import com.mango.bc.homepage.collage.view.CollageFailView;
import com.mango.bc.homepage.collage.view.CollageIngView;
import com.mango.bc.homepage.collage.view.CollageSuccessView;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.listener.OnBookListener;
import com.mango.bc.homepage.net.model.BookModel;
import com.mango.bc.homepage.net.model.BookModelImpl;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.homepage.net.view.BookCompetitiveFieldView;
import com.mango.bc.homepage.net.view.BookCompetitiveView;
import com.mango.bc.homepage.net.view.BookExpertView;
import com.mango.bc.homepage.net.view.BookFreeView;
import com.mango.bc.homepage.net.view.BookNewestView;
import com.mango.bc.homepage.net.view.BookSearchView;
import com.mango.bc.util.URLEncoderURI;
import com.mango.bc.util.Urls;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/21.
 */

public class CollagePresenterImpl implements CollagePresenter, OnCollageListener {
    private CollageAllView collageAllView;
    private CollageIngView collageIngView;
    private CollageSuccessView collageSuccessView;
    private CollageFailView collageFailView;

    private CollageModel collageModel;

    public CollagePresenterImpl(CollageAllView u) {
        this.collageAllView = u;
        this.collageModel = new CollageModelImpl();
    }

    public CollagePresenterImpl(CollageIngView u) {
        this.collageIngView = u;
        this.collageModel = new CollageModelImpl();
    }

    public CollagePresenterImpl(CollageSuccessView u) {
        this.collageSuccessView = u;
        this.collageModel = new CollageModelImpl();
    }

    public CollagePresenterImpl(CollageFailView u) {
        this.collageFailView = u;
        this.collageModel = new CollageModelImpl();
    }

    private String getUrl(int status, Context context) {
        StringBuffer sburl = new StringBuffer();
        switch (status) {
            case 0:
                sburl.append(Urls.HOST_GROUP);
                break;
            case 1:
                sburl.append(Urls.HOST_GROUP);
                break;
            case 2:
                sburl.append(Urls.HOST_GROUP);
                break;
            case 3:
                sburl.append(Urls.HOST_GROUP);
                break;
        }
        return sburl.toString();
    }

    @Override
    public void visitCollages(Context context, int status, int page, Boolean ifCache) {
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
        collageModel.visitCollages(context, status, url, page, ifCache, this);
    }

    @Override
    public void onSuccessCollageAll(List<CollageBean> collageBeans) {
        collageAllView.addCollageAll(collageBeans);
    }

    @Override
    public void onSuccessCollageIng(List<CollageBean> collageBeans) {
        collageIngView.addCollageIng(collageBeans);
    }

    @Override
    public void onSuccessCollageSuccess(List<CollageBean> collageBeans) {
        collageSuccessView.addCollageSuccess(collageBeans);
    }

    @Override
    public void onSuccessCollageFail(List<CollageBean> collageBeans) {
        collageFailView.addCollageFail(collageBeans);
    }

    @Override
    public void onSuccessMesCollageAll(String msg) {
        collageAllView.addSuccessCollageAll(msg);
    }

    @Override
    public void onSuccessMesCollageIng(String msg) {
        collageIngView.addSuccessCollageIng(msg);
    }

    @Override
    public void onSuccessMesCollageSuccess(String msg) {
        collageSuccessView.addSuccessCollageSuccess(msg);
    }

    @Override
    public void onSuccessMesCollageFail(String msg) {
        collageFailView.addSuccessCollageFail(msg);
    }

    @Override
    public void onFailMesCollageAll(String msg, Exception e) {
        collageAllView.addFailCollageAll(msg);
    }

    @Override
    public void onFailMesCollageIng(String msg, Exception e) {
        collageIngView.addFailCollageIng(msg);
    }

    @Override
    public void onFailMesCollageSuccess(String msg, Exception e) {
        collageSuccessView.addFailCollageSuccess(msg);
    }

    @Override
    public void onFailMesCollageFail(String msg, Exception e) {
        collageFailView.addFailCollageFail(msg);
    }
}
