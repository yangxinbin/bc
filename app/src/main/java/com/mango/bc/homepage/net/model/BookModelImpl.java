package com.mango.bc.homepage.net.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.mango.bc.homepage.net.bean.CompetitiveBookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.homepage.net.listener.OnBookListener;
import com.mango.bc.util.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/21.
 */

public class BookModelImpl implements BookModel {
    private SharedPreferences sharedPreferences;

    @Override
    public void visitBooks(final Context context, final int type, String url, String tabString,int page, final OnBookListener listener) {
        sharedPreferences = context.getSharedPreferences("DCOM", MODE_PRIVATE);
        if (type == 0) {//精品tab字段
            HttpUtils.doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFailMes("FAILURE", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        List<CompetitiveFieldBean> beanList = JsonUtils.readCompetitiveFieldBean(response.body().string());//data是json字段获得data的值即对象数组
                        listener.onSuccessCompetitiveField(beanList);
                        listener.onSuccessMes("请求成功");
                    } catch (Exception e) {
                        Log.v("yyyyyyyyy", "*****e*****" + response.code());
                        listener.onSuccessMes("请求失败");
                    }
                }
            });
        }
        if (type == 1) {//精品课程各种
            Log.v("yyyyyyyyy", "*****onResponse******" + 0);

            HttpUtils.doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFailMes("FAILURE", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //response.body().string() 只能用一次  java.lang.IllegalStateException异常, 该异常表示，当前对客户端的响应已经结束，不能在响应已经结束（或说消亡）后再向客户端（实际上是缓冲区）输出任何内容。
                        // List<ListEventBean> beanList = EventJsonUtils.readJsonEventBeans(response.body().string(), "list");//data是json字段获得data的值即对象数组
                        // listener.onSuccess(beanList);
                    } catch (Exception e) {
                        listener.onSuccessMes("请求失败");
                    }
                }
            });
        }
        if (type == 2) {//大咖课
            Log.v("yyyyyyyyy", "*****onResponse******" + 1);

            HttpUtils.doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFailMes("FAILURE", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //response.body().string() 只能用一次  java.lang.IllegalStateException异常, 该异常表示，当前对客户端的响应已经结束，不能在响应已经结束（或说消亡）后再向客户端（实际上是缓冲区）输出任何内容。
                        //List<ListEventBean> beanList = EventJsonUtils.readJsonEventBeans(response.body().string(), "list");//data是json字段获得data的值即对象数组
                        //listener.onSuccess(beanList);
                        //listener.onSuccessMes("请求成功");
                    } catch (Exception e) {
                        //listener.onSuccessMes("请求失败");//java.lang.IllegalStateException: Not a JSON Object: null
                    }
                }
            });
        }
        if (type == 3) {//免费课
            Log.v("yyyyyyyyy", "*****onResponse******" + 1);

            HttpUtils.doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFailMes("FAILURE", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //response.body().string() 只能用一次  java.lang.IllegalStateException异常, 该异常表示，当前对客户端的响应已经结束，不能在响应已经结束（或说消亡）后再向客户端（实际上是缓冲区）输出任何内容。
                        //List<ListEventBean> beanList = EventJsonUtils.readJsonEventBeans(response.body().string(), "list");//data是json字段获得data的值即对象数组
                        //listener.onSuccess(beanList);
                        //listener.onSuccessMes("请求成功");
                    } catch (Exception e) {
                        //listener.onSuccessMes("请求失败");//java.lang.IllegalStateException: Not a JSON Object: null
                    }
                }
            });
        }
        if (type == 4) { //最新课
            Log.v("yyyyyyyyy", "*****onResponse******" + 1);

            HttpUtils.doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFailMes("FAILURE", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //response.body().string() 只能用一次  java.lang.IllegalStateException异常, 该异常表示，当前对客户端的响应已经结束，不能在响应已经结束（或说消亡）后再向客户端（实际上是缓冲区）输出任何内容。
                        //List<ListEventBean> beanList = EventJsonUtils.readJsonEventBeans(response.body().string(), "list");//data是json字段获得data的值即对象数组
                        //listener.onSuccess(beanList);
                        //listener.onSuccessMes("请求成功");
                    } catch (Exception e) {
                        //listener.onSuccessMes("请求失败");//java.lang.IllegalStateException: Not a JSON Object: null
                    }
                }
            });
        }
    }

    private String listToString(List<String> stringList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == stringList.size() - 1) {
                stringBuffer.append(stringList.get(i));
                break;
            }
            stringBuffer.append(stringList.get(i) + ",");
        }
        return stringBuffer.toString();
    }

}
