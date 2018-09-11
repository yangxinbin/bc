package com.mango.bc.homepage.net.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.mango.bc.homepage.net.bean.CompetitiveBookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.homepage.net.listener.OnBookListener;
import com.mango.bc.util.ACache;
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
    public void visitBooks(final Context context, final int type, final String url, String tabString, final int page, final Boolean ifCache, final OnBookListener listener) {
        sharedPreferences = context.getSharedPreferences("BC", MODE_PRIVATE);
        final ACache mCache = ACache.get(context);
        if (type == 0) {//精品tab字段
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type);
                        Log.v("yyyyyy", "---cache---");
                        if (newString != null) {
                            List<CompetitiveFieldBean> beanList = JsonUtils.readCompetitiveFieldBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessCompetitiveField(beanList);
                            listener.onSuccessMes("SUCCESS");
                            Log.v("yyyyyy", "---cache---" + type);
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.v("yyyyyyyyy", e + "*****onFailure*****" + url);
                            listener.onFailMes("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string*****" + string);
                                mCache.put("cache" + type, string);
                                List<CompetitiveFieldBean> beanList = JsonUtils.readCompetitiveFieldBean(string);//data是json字段获得data的值即对象数组
                                listener.onSuccessCompetitiveField(beanList);
                                listener.onSuccessMes("SUCCESS");
                            } catch (Exception e) {
                                Log.v("yyyyyyyyy", "*****e*****" + response.code());
                                listener.onFailMes("FAILURE", e);
                            }
                        }
                    });
                }
            }).start();
        }
        if (type == 1) {//精品课程各种
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type+page);
                        Log.v("yyyyyy", "---cache---");
                        if (newString != null) {
//                            List<CompetitiveFieldBean> beanList = JsonUtils.readCompetitiveFieldBean(newString);//data是json字段获得data的值即对象数组
                            //listener.onSuccessCompetitiveField(beanList);
                            listener.onSuccessMes("SUCCESS");
                            Log.v("yyyyyy", "---cache---" + type);
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type+page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMes("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string*****" + string);
                                mCache.put("cache" + type+page, string);
                                //List<CompetitiveFieldBean> beanList = JsonUtils.readCompetitiveFieldBean(string);//data是json字段获得data的值即对象数组
                                //listener.onSuccessCompetitiveField(beanList);
                                listener.onSuccessMes("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMes("FAILURE", e);
                            }
                        }
                    });
                }
            }).start();

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
