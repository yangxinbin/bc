package com.mango.bc.homepage.bookdetail.play.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.util.HttpUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 专辑封面图片加载器
 * Created by wcy on 2015/11/27.
 */
public class CoverLoader {
    public static final int THUMBNAIL_MAX_LENGTH = 500;
    private static final String KEY_NULL = "null";
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    private Context context;
    //private Map<Type, LruCache<String, Bitmap>> cacheMap;
    private int roundLength = ScreenUtils.getScreenWidth() / 2;
    private Bitmap bitmap;

    private enum Type {
        THUMB,
        ROUND,
        BLUR
    }

    public static CoverLoader get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static CoverLoader instance = new CoverLoader();
    }

    private CoverLoader() {
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();

        // 获取当前进程的可用内存（单位KB）
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 缓存大小为当前进程可用内存的1/8
        int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> thumbCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return bitmap.getAllocationByteCount() / 1024;
                } else {
                    return bitmap.getByteCount() / 1024;
                }
            }
        };
        LruCache<String, Bitmap> roundCache = new LruCache<>(10);
        LruCache<String, Bitmap> blurCache = new LruCache<>(10);

        /*cacheMap = new HashMap<>(3);
        cacheMap.put(Type.THUMB, thumbCache);
        cacheMap.put(Type.ROUND, roundCache);
        cacheMap.put(Type.BLUR, blurCache);*/
    }

    public void setRoundLength(int roundLength) {
        if (this.roundLength != roundLength) {
            this.roundLength = roundLength;
            //cacheMap.get(Type.ROUND).evictAll();
        }
    }

    public Bitmap loadThumb(BookMusicDetailBean music) {
        return loadCover(music, Type.THUMB);
    }

    public Bitmap loadRound(BookMusicDetailBean music) {
        return loadCover(music, Type.ROUND);
    }

    public Bitmap loadBlur(BookMusicDetailBean music) {
        return loadCover(music, Type.BLUR);
    }

    private Bitmap loadCover(BookMusicDetailBean music, Type type) {
        Log.v("pppppp", "=loadCover=");

        Bitmap bitmap = null;
        String key = getKey(music);
        //LruCache<String, Bitmap> cache = cacheMap.get(type);
        if (TextUtils.isEmpty(key)) {
            //bitmap = cache.get(KEY_NULL);
            if (bitmap != null) {
                return bitmap;
            }

            bitmap = getDefaultCover(type);
            //cache.put(KEY_NULL, bitmap);
            return bitmap;
        }

        //bitmap = cache.get(key);
        if (bitmap != null) {
            return bitmap;
        }

        bitmap = loadCoverByType(music, type);
        if (bitmap != null) {
            //cache.put(key, bitmap);
            return bitmap;
        }

        return loadCover(null, type);
    }

    private String getKey(BookMusicDetailBean music) {
        if (music == null) {
            return null;
        }
        if (!TextUtils.isEmpty(music.getCoverPath())) {
            Log.v("ccccccc", "-----" + music.getCoverPath());
            return music.getCoverPath();
        } else {
            return null;
        }
    }

    private Bitmap getDefaultCover(Type type) {
        switch (type) {
            case ROUND:
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.play_page_default_cover);
                bitmap = ImageUtils.resizeImage(bitmap, roundLength, roundLength);
                return bitmap;
            case BLUR:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.play_page_default_bg);
            default:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_cover);
        }
    }

    private Bitmap loadCoverByType(BookMusicDetailBean music, Type type) {
        Bitmap bitmap;
        bitmap = loadCoverFromFile(music.getCoverPath());
        switch (type) {
            case ROUND:
                bitmap = ImageUtils.resizeImage(bitmap, roundLength, roundLength);
                return ImageUtils.createCircleImage(bitmap);
            case BLUR:
                return ImageUtils.blur(bitmap);
            default:
                return bitmap;
        }
    }

    /**
     * 从媒体库加载封面<br>
     * 本地音乐
     */
    /*private Bitmap loadCoverFromMediaStore(long albumId) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MusicUtils.getMediaStoreAlbumCoverUri(albumId);
        InputStream is;
        try {
            is = resolver.openInputStream(uri);
        } catch (FileNotFoundException ignored) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeStream(is, null, options);
    }*/

    /**
     * 从下载的图片加载封面<br>
     * 网络音乐
     */
    public Bitmap loadCoverFromFile(final String path) {
        Log.v("pppppp", "==" + path);
        HttpUtils.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream input = response.body().byteStream();
                //使用BitmapFactory工厂，把字节数组转化为bitmap
                //java.lang.OutOfMemoryError: Failed to allocate a 14445012 byte allocation with 3456152 free bytes and 3MB until OOM
                //待优化
                //final Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                //通过imageview，设置图片
                final Bitmap bitmap1 = streamToBitmap(input);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        bitmap = bitmap1;
                    }
                });
            }
        });
        return bitmap;
    }

    public static Bitmap streamToBitmap(InputStream input) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = getinSampleSize(options);
        //options.inPreferredConfig = Bitmap.Config.RGB_565;//颜色要求不高
        //options.inPurgeable = true;
        //options.inInputShareable = true;
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    //获取option采样率inSampleSize
    private static int getinSampleSize(BitmapFactory.Options options) {
        int inSampleSize = 1;
        int imageWidth = options.outWidth;//取出bitmap的原始高宽
        int imageHeight = options.outHeight;
        //个人认为intent，bundle传递图片的时候，当图片内存大于1024KB的时候，会发生内存溢出，
        // 所以为解决内存溢出问题，此处选择通过计算图片大小来查找缩放比例系数小于1024KB时，找到inSampleSize
        while (getImageMemory(imageWidth, imageHeight, inSampleSize) > 1024) {
            inSampleSize *= 2;
        }
        Log.v("sssssss", "--1--" + inSampleSize);
        return inSampleSize;
    }

    private static int getImageMemory(int imagewidth, int imageheight, int inSampleSize) {
        Log.v("sssssss", "--2--" + (imagewidth / inSampleSize) * (imageheight / inSampleSize) * 3 / 1024);
        return (imagewidth / inSampleSize) * (imageheight / inSampleSize) * 3 / 1024;
    }
}
