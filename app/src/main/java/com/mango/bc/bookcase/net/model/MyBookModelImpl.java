package com.mango.bc.bookcase.net.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.jsonutils.MyBookJsonUtils;
import com.mango.bc.bookcase.net.listener.OnMyBookListener;
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

public class MyBookModelImpl implements MyBookModel {
    private SharedPreferences sharedPreferences;

    @Override
    public void visitBooks(final Context context, final int type, final String url, final int page, final Boolean ifCache, final OnMyBookListener listener) {
        sharedPreferences = context.getSharedPreferences("BC", MODE_PRIVATE);
        final ACache mCache = ACache.get(context.getApplicationContext());
        if (type == 0) {//大咖课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final HashMap<String, String> mapParams = new HashMap<String, String>();
                    mapParams.clear();
                    mapParams.put("authToken", sharedPreferences.getString("authToken", ""));
                    mapParams.put("type", "paid");
                    mapParams.put("page", ""+page);
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache_my" + type + page);
                        if (newString != null) {
                            List<MyBookBean> beanList = MyBookJsonUtils.readMyBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessExpertBook(beanList);
                            listener.onSuccessMes("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache_my" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doPost(url, mapParams,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.v("doPostAll", "^^^^^onFailure^^^^^"+e);
                            listener.onFailMes("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                mCache.put("cache_my" + type + page, string);
                                List<MyBookBean> beanList = MyBookJsonUtils.readMyBookBean(string);
                                listener.onSuccessExpertBook(beanList);
                                listener.onSuccessMes("请求成功");
                            } catch (Exception e) {
                                Log.v("doPostAll", "^^^^^Exception^^^^^"+e);
                                listener.onSuccessMes("请求失败");//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (type == 1) {//精品课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final HashMap<String, String> mapParams = new HashMap<String, String>();
                    mapParams.clear();
                    mapParams.put("authToken", sharedPreferences.getString("authToken", ""));
                    mapParams.put("type", "member");
                    mapParams.put("page", ""+page);
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache_my" + type + page);
                        if (newString != null) {
                            List<MyBookBean> beanList = MyBookJsonUtils.readMyBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessCompetitiveBook(beanList);
                            listener.onSuccessMes("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache_my" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doPost(url, mapParams,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.v("doPostAll", "^^^^^onFailure^^^^^"+e);
                            listener.onFailMes("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                mCache.put("cache_my" + type + page, string);
                                List<MyBookBean> beanList = MyBookJsonUtils.readMyBookBean(string);
                                listener.onSuccessCompetitiveBook(beanList);
                                listener.onSuccessMes("请求成功");
                            } catch (Exception e) {
                                Log.v("doPostAll", "^^^^^Exception^^^^^"+e);
                                listener.onSuccessMes("请求失败");//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        }else if (type == 2) {//免费课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final HashMap<String, String> mapParams = new HashMap<String, String>();
                    mapParams.clear();
                    mapParams.put("authToken", sharedPreferences.getString("authToken", ""));
                    mapParams.put("type", "free");
                    mapParams.put("page", ""+page);
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache_my" + type + page);
                        if (newString != null) {
                            List<MyBookBean> beanList = MyBookJsonUtils.readMyBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessFreeBook(beanList);
                            listener.onSuccessMes("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache_my" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doPost(url, mapParams,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.v("doPostAll", "^^^^^onFailure^^^^^"+e);
                            listener.onFailMes("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                mCache.put("cache_my" + type + page, string);
                                List<MyBookBean> beanList = MyBookJsonUtils.readMyBookBean(string);
                                listener.onSuccessFreeBook(beanList);
                                listener.onSuccessMes("请求成功");
                            } catch (Exception e) {
                                Log.v("doPostAll", "^^^^^Exception^^^^^"+e);
                                listener.onSuccessMes("请求失败");//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
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
