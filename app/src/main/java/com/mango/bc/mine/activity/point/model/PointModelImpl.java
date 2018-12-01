package com.mango.bc.mine.activity.point.model;

import android.content.Context;
import android.util.Log;

import com.mango.bc.mine.activity.point.jsonutils.PointJsonUtils;
import com.mango.bc.mine.activity.point.listenter.OnPointListener;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.util.ACache;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.SPUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/5/21.
 */

public class PointModelImpl implements PointModel {

    private SPUtils spUtils;

    @Override
    public void visitPoints(Context context, final int status, final String url, final int page, final Boolean ifCache, final OnPointListener listener) {
        final ACache mCache = ACache.get(context.getApplicationContext());
        final HashMap<String, String> mapParams = new HashMap<String, String>();
        spUtils = SPUtils.getInstance("bc", context);
        mapParams.clear();
        if (status == 0) {//正在
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("point" + status + page);
                        Log.v("gggg", "---group1---" + newString);
                        if (newString != null) {
                            List<MemberBean> beanList = PointJsonUtils.readPointBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessPointIng(beanList);
                            listener.onSuccessMesPointIng("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("point" + status + page);//刷新之后缓存也更新过来
                    }
                    HttpUtils.doPost(url, mapParams, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesPointIng("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****c1*****" + string);
                                mCache.put("point" + status + page, string);
                                List<MemberBean> beanList = PointJsonUtils.readPointBean(string);
                                listener.onSuccessPointIng(beanList);
                                listener.onSuccessMesPointSuccess("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMesPointSuccess("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (status == 1) {//完成
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("point" + status + page);
                        Log.v("gggg", "---group2---" + newString);
                        if (newString != null) {
                            List<MemberBean> beanList = PointJsonUtils.readPointBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessPointSuccess(beanList);
                            listener.onSuccessMesPointSuccess("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("point" + status + page);//刷新之后缓存也更新过来
                    }
                    mapParams.put("authToken", spUtils.getString("authToken", ""));
                    mapParams.put("status", "finished");
                    mapParams.put("page", page + "");
                    HttpUtils.doPost(url, mapParams, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesPointSuccess("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****c2*****" + string);
                                mCache.put("point" + status + page, string);
                                List<MemberBean> beanList = PointJsonUtils.readPointBean(string);
                                listener.onSuccessPointSuccess(beanList);
                                listener.onSuccessMesPointSuccess("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMesPointSuccess("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
                            }
                        }
                    });
                }
            }).start();
        } else if (status == 2) {//失败
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ifCache) {//读取缓存数据
                        String newString = mCache.getAsString("point" + status + page);
                        Log.v("gggg", "---group3---" + newString);
                        if (newString != null) {
                            List<MemberBean> beanList = PointJsonUtils.readPointBean(newString);//data是json字段获得data的值即对象数组
                            listener.onSuccessPointFail(beanList);
                            listener.onSuccessMesPointFail("SUCCESS");
                            return;
                        }
                    } else {
                        mCache.remove("point" + status + page);//刷新之后缓存也更新过来
                    }
                    mapParams.put("authToken", spUtils.getString("authToken", ""));
                    mapParams.put("status", "expired");
                    mapParams.put("page", page + "");
                    HttpUtils.doPost(url, mapParams, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            listener.onFailMesPointFail("FAILURE", e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String string = response.body().string();
                                Log.v("yyyyyyyyy", "*****c3*****" + string);
                                mCache.put("point" + status + page, string);
                                List<MemberBean> beanList = PointJsonUtils.readPointBean(string);
                                listener.onSuccessPointFail(beanList);
                                listener.onSuccessMesPointFail("SUCCESS");
                            } catch (Exception e) {
                                listener.onFailMesPointFail("请求失败", e);//java.lang.IllegalStateException: Not a JSON Object: null
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
