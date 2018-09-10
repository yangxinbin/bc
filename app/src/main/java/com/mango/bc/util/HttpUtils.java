package com.mango.bc.util;


import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Description : OkHttp网络连接封装工具类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/17
 */
public class HttpUtils {
    private static OkHttpClient client = null;

    private HttpUtils() {
    }

    private static final int TIME_OUT = 30; //超时参数

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (HttpUtils.class) {
                if (client == null) {
                    final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
                    //创建我们Client对象的构建者
                    OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                            .cookieJar(new CookieJar() {
                                @Override
                                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                                    cookieStore.put(httpUrl.host(), list);
                                }

                                @Override
                                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                                    return cookies != null ? cookies : new ArrayList<Cookie>();
                                }
                            })
                            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(TIME_OUT, TimeUnit.SECONDS)

                            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                            //允许重定向
                            .followRedirects(true)
                            //添加https支持
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String s, SSLSession sslSession) {
                                    return false;
                                }
                            });
                    client = okHttpBuilder.build();
                }
            }
        }
        return client;
    }

    /**
     * Get请求
     *
     * @param url
     * @param callback
     */
    public static void doGet(String url, Callback callback) {
        Request request = new Request.Builder()
                //.header("Content-Type", "application/x-www-form-urlencoded")
                //.header("api-key", "49ku8919456qwe4534578451kiutrvfg")
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Get请求
     *
     * @param url
     * @param callback
     */
    public static void doDelete(String url, Map<String, String> mapParams, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        StringBuffer formatUrl = new StringBuffer();
        formatUrl.append(url);
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
/*            if (key.equals("projectId")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位//
                break;
            }
            if (key.equals("username")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位//
                break;
            }*/
            if (key.equals("token")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位
                break;
            }
        }
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
            if (/*!key.equals("projectId") &&*/ !key.equals("token") /*&& !key.equals("username")*/) {
                try {
                    formatUrl.append("&" + key + "=").append(URLEncoderURI.encode(mapParams.get(key), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        Log.e("eeeee", " = " + formatUrl);
        Request request = new Request.Builder()
                .url(formatUrl.toString())
                .delete(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Post请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    public static void doPost(String url, Map<String, String> mapParams, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Log.v("doPost", "---" + url);
        Request request = new Request.Builder()
                .url(url)
                //.header("Content-Type", "application/x-www-form-urlencoded")
                //.header("api-key", "49ku8919456qwe4534578451kiutrvfg")
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Post请求发送键值对数据可以带参数上传文件
     *
     * @param url
     * @param paramsMap
     * @param callback
     */
    public static void doPostAll(String url, HashMap<String, String> paramsMap, File f, Callback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        for (String key : paramsMap.keySet()) {
            Object object = paramsMap.get(key);
            if (!(object instanceof File) && object != null) {
                builder.addFormDataPart(key, object.toString());
                Log.v("doPostAll", key + "^^^^^doPostAll^^paramsMap^^^" + object.toString());
            }
        }
        if (f != null) {
            Log.v("doPostAll", "^^^^^f^^");
            builder.addFormDataPart("file", f.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), f));
        }
        //创建RequestBody
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                //.header("Content-Type", "application/x-www-form-urlencoded")
                //.header("api-key", "49ku8919456qwe4534578451kiutrvfg")
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    public static void doPostTwoPicture(String url, HashMap<String, String> paramsMap, File f1, File f2, Callback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        for (String key : paramsMap.keySet()) {
            Object object = paramsMap.get(key);
            if (!(object instanceof File) && object != null) {
                builder.addFormDataPart(key, object.toString());
                Log.v("doPostAll", key + "^^^^^doPostAll^^paramsMap^^^" + object.toString());
            }
        }
        if (f1 != null) {
            Log.v("doPostAll", "^^^^^f1^^");
            builder.addFormDataPart("file", f1.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), f1));
        }
        if (f2 != null) {
            Log.v("doPostAll", "^^^^^f2^^");
            builder.addFormDataPart("solutionAttachment", f2.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), f2));
        }
        //创建RequestBody
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                //.header("Content-Type", "application/x-www-form-urlencoded")
                //.header("api-key", "49ku8919456qwe4534578451kiutrvfg")
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Put请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    public static void doPut(String url, Map<String, String> mapParams, Callback callback) {//
        FormBody.Builder builder = new FormBody.Builder();
        StringBuffer formatUrl = new StringBuffer();
        formatUrl.append(url);
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
/*            if (key.equals("projectId")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位//
                break;
            }
            if (key.equals("username")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位//
                break;
            }*/
            if (key.equals("token")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位
                break;
            }
        }
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
            if (/*!key.equals("projectId") && */!key.equals("token")/* && !key.equals("username")*/) {
                try {
                    formatUrl.append("&" + key + "=").append(URLEncoderURI.encode(mapParams.get(key), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        Request request = new Request.Builder()
                .url(formatUrl.toString())
                .put(builder.build())
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
        Log.v("doPutWithJson", "******request*****" + formatUrl);
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    public static void doPutRePhone(String url, Map<String, String> mapParams, Callback callback) {//
        FormBody.Builder builder = new FormBody.Builder();
        StringBuffer formatUrl = new StringBuffer();
        formatUrl.append(url);
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
/*            if (key.equals("projectId")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位//
                break;
            }*/
            if (key.equals("username")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位//
                break;
            }
            if (key.equals("token")) {//保证第一个
                formatUrl.append("?" + key + "=").append(mapParams.get(key));//id 必须为第一位
                break;
            }
        }
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
            if (/*!key.equals("projectId") && */!key.equals("token")/* && !key.equals("username")*/) {
                try {
                    formatUrl.append("&" + key + "=").append(URLEncoderURI.encode(mapParams.get(key), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        Request request = new Request.Builder()
                .url(formatUrl.toString())
                .put(builder.build())
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
        Log.v("doPutWithJson", "******request*****" + formatUrl);
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    public static String formatEncode(String originUrl) {
        if (TextUtils.isEmpty(originUrl)) {
            return "";
        }
        try {
            URL url = new URL(originUrl);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            return uri.toURL().toString();
        } catch (MalformedURLException e) {
        } catch (URISyntaxException e) {
        }
        return originUrl;
    }

    /**
     * Post请求发送JSON数据
     *
     * @param url
     * @param jsonParams
     * @param callback
     */
    public static void doPost(String url, String jsonParams, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param pathName
     * @param fileName
     * @param callback
     */
    public static void doFile(String url, String pathName, String fileName, Callback callback) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName,
                        RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 下载文件
     *
     * @param url
     * @param fileDir
     * @param fileName
     */
    public static void downFile(String url, final String fileDir, final String fileName) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(fileDir, fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
            /*
//下载文件增加进度
@Override
public void onResponse(Call call, Response response) throws IOException {
    InputStream is = null;
    byte[] buf = new byte[2048];
    int len = 0;
    FileOutputStream fos = null;
    try {
        is = response.body().byteStream();
        File file = new File(fileDir, fileName);
        fos = new FileOutputStream(file);
        //---增加的代码---
        //计算进度
        long totalSize = response.body().contentLength();
        long sum = 0;
        while ((len = is.read(buf)) != -1) {
            sum += len;
            //progress就是进度值
            int progress = (int) (sum * 1.0f/totalSize * 100);
            //---增加的代码---
            fos.write(buf, 0, len);
        }
        fos.flush();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (is != null) is.close();
        if (fos != null) fos.close();
    }
}
            */
        });
    }

}
