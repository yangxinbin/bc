package com.mango.bc.bookcase.net.jsonutils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.bookcase.net.bean.MyBookBean;

import java.util.List;

/**
 * Description :
 * Date   : 15/12/19
 */
public class MyBookJsonUtils {

    private final static String TAG = "NewsJsonUtils";

    public static List<MyBookBean> readMyBookBean(String res) {
        Gson gson = new Gson();
        List<MyBookBean> myBookBeans = gson.fromJson(res, new TypeToken<List<MyBookBean>>(){}.getType());
        return myBookBeans;
    }
}
