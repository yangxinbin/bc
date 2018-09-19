package com.mango.bc.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.homepage.net.bean.BookBean;
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
}
