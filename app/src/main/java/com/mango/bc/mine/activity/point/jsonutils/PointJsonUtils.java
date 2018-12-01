package com.mango.bc.mine.activity.point.jsonutils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.mine.bean.MemberBean;

import java.util.List;

/**
 * Description :
 * Date   : 15/12/19
 */
public class PointJsonUtils {
    public static List<MemberBean> readPointBean(String res) {
        Gson gson = new Gson();
        List<MemberBean> memberBeans = gson.fromJson(res, new TypeToken<List<MemberBean>>(){}.getType());
        return memberBeans;
    }
}
