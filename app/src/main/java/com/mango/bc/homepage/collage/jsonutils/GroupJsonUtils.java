package com.mango.bc.homepage.collage.jsonutils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;

import java.util.List;

/**
 * Description :
 * Date   : 15/12/19
 */
public class GroupJsonUtils {
    public static List<CollageBean> readCollageBean(String res) {
        Gson gson = new Gson();
        List<CollageBean> collageBeans = gson.fromJson(res, new TypeToken<List<CollageBean>>(){}.getType());
        return collageBeans;
    }
}
