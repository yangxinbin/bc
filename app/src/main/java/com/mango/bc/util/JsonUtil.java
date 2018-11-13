package com.mango.bc.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.homepage.bean.BannerBean;
import com.mango.bc.homepage.bean.VipPackageBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.wallet.bean.CheckBean;
import com.mango.bc.wallet.bean.CheckInBean;

import java.util.List;

/**
 * Created by admin on 2018/9/19.
 */

public class JsonUtil {
    public static CheckInBean readCheckInBean(String res) {
        Gson gson = new Gson();
        CheckInBean checkInBean = gson.fromJson(res, new TypeToken<CheckInBean>(){}.getType());
        return checkInBean;
    }
    public static CheckBean readCheckBean(String res) {
        Gson gson = new Gson();
        CheckBean checkBean = gson.fromJson(res, new TypeToken<CheckBean>(){}.getType());
        return checkBean;
    }
    public static List<VipPackageBean> readVipPackageBean(String res) {
        Gson gson = new Gson();
        List<VipPackageBean> vipPackageBeans = gson.fromJson(res, new TypeToken<List<VipPackageBean>>(){}.getType());
        return vipPackageBeans;
    }
    public static List<BannerBean> readBannerBean(String res) {
        Gson gson = new Gson();
        List<BannerBean> bannerBeanList = gson.fromJson(res, new TypeToken<List<BannerBean>>(){}.getType());
        return bannerBeanList;
    }
}
