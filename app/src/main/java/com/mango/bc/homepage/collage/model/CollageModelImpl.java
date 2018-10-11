package com.mango.bc.homepage.collage.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.homepage.collage.jsonutils.GroupJsonUtils;
import com.mango.bc.homepage.collage.listenter.OnCollageListener;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.jsonutils.JsonUtils;
import com.mango.bc.homepage.net.listener.OnBookListener;
import com.mango.bc.homepage.net.model.BookModel;
import com.mango.bc.util.ACache;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;

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

public class CollageModelImpl implements CollageModel {

    private SPUtils spUtils;

    @Override
    public void visitCollages(Context context, final int status, final String url, final int page, final Boolean ifCache, final OnCollageListener listener) {
        final ACache mCache = ACache.get(context.getApplicationContext());
        final HashMap<String, String> mapParams = new HashMap<String, String>();
        spUtils = SPUtils.getInstance("bc", context);
        mapParams.clear();
        if (status == 0) {//所有
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("collage" + status + page);
                        Log.v("gggg", "---group0---" + newString);
                        if (newString != null) {
                            List<CollageBean> beanList = GroupJsonUtils.readCollageBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessCollageAll(beanList);
                            listener.onSuccessMesCollageAll("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("collage" + status + page);//刷新之后缓存也更新过来
                    }
                    mapParams.put("authToken", spUtils.getString("authToken", ""));
                    mapParams.put("status", "");
                    mapParams.put("page", page+"");
                    HttpUtils.doPost(url, mapParams,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesCollageAll("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****c0*****" + string);
                                mCache.put("collage" + status + page, string);
                                List<CollageBean> beanList = GroupJsonUtils.readCollageBean(string);
                                listener.onSuccessCollageAll(beanList);
                                listener.onSuccessMesCollageAll("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMesCollageAll("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (status == 1) {//正在
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("collage" + status + page);
                        Log.v("gggg", "---group1---" + newString);
                        if (newString != null) {
                            List<CollageBean> beanList = GroupJsonUtils.readCollageBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessCollageIng(beanList);
                            listener.onSuccessMesCollageIng("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("collage" + status + page);//刷新之后缓存也更新过来
                    }
                    mapParams.put("authToken", spUtils.getString("authToken", ""));
                    mapParams.put("status", "started");
                    mapParams.put("page", page+"");
                    HttpUtils.doPost(url, mapParams,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesCollageIng("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****c1*****" + string);
                                mCache.put("collage" + status + page, string);
                                List<CollageBean> beanList = GroupJsonUtils.readCollageBean(string);
                                listener.onSuccessCollageIng(beanList);
                                listener.onSuccessMesCollageIng("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMesCollageIng("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (status == 2) {//完成
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("collage" + status + page);
                        Log.v("gggg", "---group2---" + newString);
                        if (newString != null) {
                            List<CollageBean> beanList = GroupJsonUtils.readCollageBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessCollageSuccess(beanList);
                            listener.onSuccessMesCollageSuccess("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("collage" + status + page);//刷新之后缓存也更新过来
                    }
                    mapParams.put("authToken", spUtils.getString("authToken", ""));
                    mapParams.put("status", "finished");
                    mapParams.put("page", page+"");
                    HttpUtils.doPost(url, mapParams,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesCollageSuccess("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****c2*****" + string);
                                mCache.put("collage" + status + page, string);
                                List<CollageBean> beanList = GroupJsonUtils.readCollageBean(string);
                                listener.onSuccessCollageSuccess(beanList);
                                listener.onSuccessMesCollageSuccess("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMesCollageSuccess("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (status == 3) {//失败
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("collage" + status + page);
                        Log.v("gggg", "---group3---" + newString);
                        if (newString != null) {
                            List<CollageBean> beanList = GroupJsonUtils.readCollageBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessCollageFail(beanList);
                            listener.onSuccessMesCollageFail("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("collage" + status + page);//刷新之后缓存也更新过来
                    }
                    mapParams.put("authToken", spUtils.getString("authToken", ""));
                    mapParams.put("status", "expired");
                    mapParams.put("page", page+"");
                    HttpUtils.doPost(url, mapParams,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesCollageFail("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****c3*****" + string);
                                mCache.put("collage" + status + page, string);
                                List<CollageBean> beanList = GroupJsonUtils.readCollageBean(string);
                                listener.onSuccessCollageFail(beanList);
                                listener.onSuccessMesCollageFail("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMesCollageFail("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
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
