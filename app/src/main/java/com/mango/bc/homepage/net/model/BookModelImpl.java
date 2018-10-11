package com.mango.bc.homepage.net.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.homepage.net.listener.OnBookListener;
import com.mango.bc.util.ACache;
import com.mango.bc.util.HttpUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/21.
 */

public class BookModelImpl implements BookModel {

    @Override
    public void visitBooks(final Context context, final int type, final String url, final String keyWordString, final int page, final Boolean ifCache, final OnBookListener listener) {
        final ACache mCache = ACache.get(context.getApplicationContext());
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
                            listener.onSuccessMesCompetitiveField("SUCCESS");
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
                            listener.onFailMesCompetitiveField("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string0*****" + string);
                                mCache.put("cache" + type, string);
                                List<CompetitiveFieldBean> beanList = JsonUtils.readCompetitiveFieldBean(string);//data是json字段获得data的值即对象数组
                                listener.onSuccessCompetitiveField(beanList);
                                listener.onSuccessMesCompetitiveField("SUCCESS");
                            } catch (Exception e) {
                                Log.v("yyyyyyyyy", "*****e*****" + response.code());
                                listener.onFailMesCompetitiveField("FAILURE", e);
                            }
                        }
                    });
                }
            }).start();
        } else if (type == 1) {//精品课程各种
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        Log.v("yyyyyy", url + "---cache1---" + ifCache);

                        String newString = mCache.getAsString("cache" + keyWordString + page);
                        if (newString != null) {
                            List<BookBean> beanList = JsonUtils.readBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessCompetitiveBook(beanList);
                            listener.onSuccessMesCompetitiveBook("SUCCESS");
                            Log.v("yyyyyy", "---cache---" + type);
                            return;
                        }
                    } else {
                        mCache.remove("cache" + keyWordString + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesCompetitiveBook("FAILURE", e);
                            Log.v("yyyyyyyyy", url + "*****1*****" + e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                mCache.put("cache" + keyWordString + page, string);
                                Log.v("yyyyyyyyy", url + "*****1*****" + string);
                                List<BookBean> beanList = JsonUtils.readBookBean(string);//data是json字段获得data的值即对象数组
                                listener.onSuccessCompetitiveBook(beanList);
                                listener.onSuccessMesCompetitiveBook("SUCCESS");
                            } catch (Exception e) {
                                Log.v("yyyyyyyyy", "*****Exception*****" + e);
                                listener.onFailMesCompetitiveBook("FAILURE", e);
                            }
                        }
                    });
                }
            }).start();

        } else if (type == 2) {//大咖课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type + page);
                        Log.v("yyyyyy", "---cache2---" + newString);
                        if (newString != null) {
                            List<BookBean> beanList = JsonUtils.readBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessExpertBook(beanList);
                            listener.onSuccessMesExpertBook("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesExpertBook("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string2*****" + string);
                                mCache.put("cache" + type + page, string);
                                List<BookBean> beanList = JsonUtils.readBookBean(string);
                                listener.onSuccessExpertBook(beanList);
                                listener.onSuccessMesExpertBook("请求成功");
                            } catch (Exception e) {
                                listener.onFailMesExpertBook("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (type == 3) {//免费课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type + page);
                        Log.v("yyyyyy", "---cache3---" + newString);
                        if (newString != null) {
                            List<BookBean> beanList = JsonUtils.readBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessFreeBook(beanList);
                            listener.onSuccessMesFreeBook("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesFreeBook("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string3*****" + string);
                                mCache.put("cache" + type + page, string);
                                List<BookBean> beanList = JsonUtils.readBookBean(string);
                                listener.onSuccessFreeBook(beanList);
                                listener.onSuccessMesFreeBook("请求成功");
                            } catch (Exception e) {
                                listener.onFailMesFreeBook("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();

        } else if (type == 4) { //最新课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type + page);
                        Log.v("yyyyyy", "---cache4---" + newString);
                        if (newString != null) {
                            List<BookBean> beanList = JsonUtils.readBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessNewestBook(beanList);
                            listener.onSuccessMesNewestBook("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesNewestBook("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string4*****" + string);
                                mCache.put("cache" + type + page, string);
                                List<BookBean> beanList = JsonUtils.readBookBean(string);
                                listener.onSuccessNewestBook(beanList);
                                listener.onSuccessMesNewestBook("请求成功");
                            } catch (Exception e) {
                                listener.onFailMesNewestBook("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (type == 5) { //search
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type + page);
                        Log.v("yyyyyy", "---cache5---" + newString);
                        if (newString != null) {
                            List<BookBean> beanList = JsonUtils.readBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessSearchBook(beanList);
                            listener.onSuccessMesSearchBook("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesSearchBook("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string5*****" + string);
                                mCache.put("cache" + type + page, string);
                                List<BookBean> beanList = JsonUtils.readBookBean(string);
                                listener.onSuccessSearchBook(beanList);
                                listener.onSuccessMesSearchBook("请求成功");
                            } catch (Exception e) {
                                listener.onFailMesSearchBook("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (type == 6) {//大咖课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type + page);
                        Log.v("yyyyyy", "---cache6---" + newString);
                        if (newString != null) {
                            List<BookBean> beanList = JsonUtils.readBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessExpertBook(beanList);
                            listener.onSuccessMesExpertBook("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesExpertBook("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string6*****" + string);
                                mCache.put("cache" + type + page, string);
                                List<BookBean> beanList = JsonUtils.readBookBean(string);
                                listener.onSuccessExpertBook(beanList);
                                listener.onSuccessMesExpertBook("请求成功");
                            } catch (Exception e) {
                                listener.onFailMesExpertBook("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (type == 7) {//免费课
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("cache" + type + page);
                        Log.v("yyyyyy", "---cache7---" + newString);
                        if (newString != null) {
                            List<BookBean> beanList = JsonUtils.readBookBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessFreeBook(beanList);
                            listener.onSuccessMesFreeBook("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("cache" + type + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doGet(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesFreeBook("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****string7*****" + string);
                                mCache.put("cache" + type + page, string);
                                List<BookBean> beanList = JsonUtils.readBookBean(string);
                                listener.onSuccessFreeBook(beanList);
                                listener.onSuccessMesFreeBook("请求成功");
                            } catch (Exception e) {
                                listener.onFailMesFreeBook("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
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
