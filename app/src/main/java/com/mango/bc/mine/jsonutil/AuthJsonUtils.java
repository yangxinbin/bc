package com.mango.bc.mine.jsonutil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.mine.bean.UserBean;

import java.util.List;

/**
 * Description :
 * Date   : 15/12/19
 */
public class AuthJsonUtils {

    private final static String TAG = "NewsJsonUtils";

    public static UserBean readUserBean(String res) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(res, new TypeToken<UserBean>() {
        }.getType());
        return userBean;
    }
}
