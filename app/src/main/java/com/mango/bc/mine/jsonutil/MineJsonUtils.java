package com.mango.bc.mine.jsonutil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.mine.bean.StatsBean;
import com.mango.bc.mine.bean.UserBean;

/**
 * Description :
 * Date   : 15/12/19
 */
public class MineJsonUtils {

    private final static String TAG = "NewsJsonUtils";

    public static UserBean readUserBean(String res) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(res, new TypeToken<UserBean>() {
        }.getType());
        return userBean;
    }

    public static StatsBean readStatsBean(String res) {
        Gson gson = new Gson();
        StatsBean statsBean = gson.fromJson(res, new TypeToken<StatsBean>() {
        }.getType());
        return statsBean;
    }

    public static MemberBean readMemberBean(String res) {
        Gson gson = new Gson();
        MemberBean memberBean = gson.fromJson(res, new TypeToken<MemberBean>() {
        }.getType());
        return memberBean;
    }
}
