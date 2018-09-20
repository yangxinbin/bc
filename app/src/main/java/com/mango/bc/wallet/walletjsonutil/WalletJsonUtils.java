package com.mango.bc.wallet.walletjsonutil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.wallet.bean.TaskAndRewardBean;

import java.util.List;

/**
 * Description :
 * Date   : 15/12/19
 */
public class WalletJsonUtils {

    private final static String TAG = "NewsJsonUtils";

    public static List<TaskAndRewardBean> readTaskAndRewardBean(String res) {
        Gson gson = new Gson();
        List<TaskAndRewardBean> taskAndRewardBeans = gson.fromJson(res, new TypeToken<List<TaskAndRewardBean>>() {
        }.getType());
        return taskAndRewardBeans;
    }
}
