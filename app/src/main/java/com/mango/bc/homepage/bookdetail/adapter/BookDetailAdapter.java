package com.mango.bc.homepage.bookdetail.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.bean.BookDetailBean;
import com.mango.bc.homepage.bookdetail.bean.CommentBean;
import com.mango.bc.homepage.bookdetail.jsonutil.JsonBookDetailUtils;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.ACache;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.Urls;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/4.
 */

public class BookDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BookDetailBean.DescriptionImagesBean> datas = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler
    private Dialog dialog_load;
    private Bitmap bitmap;
    private String id;
    private final ACache mCache;

    public BookDetailAdapter(List<BookDetailBean.DescriptionImagesBean> datas, Context context, String id) {
        createLoadDailog(context);
        mCache = ACache.get(context);
        this.context = context;
        this.datas = datas;
        this.id = id;
    }

    public void createLoadDailog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_loading, null);
        dialog_load = new Dialog(context, R.style.dialog);
        dialog_load.setContentView(view);
        Window window = dialog_load.getWindow();
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置显示位置
        window.setGravity(Gravity.CENTER);
        //设置动画效果
        //window.setWindowAnimations(R.style.AnimBottom);
        dialog_load.setCanceledOnTouchOutside(true);
        dialog_load.show();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_item, parent, false);
        return new BookDetailAdapter.BookDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookDetailAdapter.BookDetailViewHolder) {
            final BookDetailAdapter.BookDetailViewHolder viewHolder = (BookDetailAdapter.BookDetailViewHolder) holder;
            if (datas.get(position).getFileName() != null) {
                //Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName()).into(viewHolder.img_book_detail);
                Log.v("uuuuuuuuuuuu", "----" + Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName());
                if (NetUtil.isNetConnect(context)) {
                    Log.v("yyyyyyy", id + "---1--!!----" + datas.get(position).getFileName());
                    final Bitmap bitmap = mCache.getAsBitmap(id + datas.get(position).getFileName());
                    if (bitmap != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog_load.dismiss();
                                viewHolder.img_book_detail.setImageBitmap(bitmap);
                            }
                        });
                    } else {
                        setIamge(false, datas.get(position).getFileName(), viewHolder.img_book_detail);
                    }
                } else {
                    Log.v("yyyyyyy", id + "--2---!!----" + datas.get(position).getFileName());

                    setIamge(true, datas.get(position).getFileName(), viewHolder.img_book_detail);
                }
            }
        }
    }

    private void setIamge(final Boolean ifCache, final String fileName, final ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ifCache) {//读取缓存数据
                    final Bitmap bitmap = mCache.getAsBitmap(id + fileName);
                    if (bitmap != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("yyyyyyy", "-----!!----" + fileName);
                                dialog_load.dismiss();
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                        return;
                    }
                } else {
                    mCache.remove(id + fileName);//刷新之后缓存也更新过来
                }
                HttpUtils.doGet(Urls.HOST_GETFILE + "?name=" + fileName, new Callback() {
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
                        bitmap = streamToBitmap(input);
                        mCache.put(id + fileName, bitmap, 60 * 60 * 24 * 2);//有效期2天
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog_load.dismiss();
                                imageView.setImageBitmap(bitmap);
/*                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bitmap.recycle();  //一秒之后回收
                                        System.gc();//提醒系统即时回收
                                    }
                                },1000);*/
                            }
                        });
                    }
                });
            }
        }).start();
    }

    public void recycleBitmap() {
        if (bitmap != null) {
            bitmap.recycle();  //一秒之后回收
            System.gc();//提醒系统即时回收
        }
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

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView img_book_detail;

        public BookDetailViewHolder(final View itemView) {
            super(itemView);
            img_book_detail = (ImageView) itemView.findViewById(R.id.img_book_detail);
        }

    }

}
