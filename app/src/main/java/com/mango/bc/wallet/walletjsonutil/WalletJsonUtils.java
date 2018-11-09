package com.mango.bc.wallet.walletjsonutil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.wallet.bean.TaskAndRewardBean;
import com.mango.bc.wallet.bean.TransactionBean;
import com.mango.bc.wallet.bean.WechatPayBean;

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
    public static List<TransactionBean> readTransactionBean(String res) {
        Gson gson = new Gson();
        List<TransactionBean> transactionBeans = gson.fromJson(res, new TypeToken<List<TransactionBean>>() {
        }.getType());
        return transactionBeans;
    }

    public static WechatPayBean readWechatPayBean(String res) {
        Gson gson = new Gson();
        WechatPayBean wechatPayBean = gson.fromJson(res, new TypeToken<WechatPayBean>() {
        }.getType());
        return wechatPayBean;
    }
}
