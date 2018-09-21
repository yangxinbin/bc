package com.mango.bc.homepage.bookdetail.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
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
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.Urls;

import java.io.IOException;
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

    public BookDetailAdapter(List<BookDetailBean.DescriptionImagesBean> datas, Context context) {
        createLoadDailog(context);
        this.context = context;
        this.datas = datas;
    }

    public void createLoadDailog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_loading, null);
        dialog_load = new Dialog(context, R.style.dialog);
        dialog_load.setContentView(view);
        Window window = dialog_load.getWindow();
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookDetailAdapter.BookDetailViewHolder) {
            final BookDetailAdapter.BookDetailViewHolder viewHolder = (BookDetailAdapter.BookDetailViewHolder) holder;
            if (datas.get(position).getFileName() != null) {
                //Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName()).into(viewHolder.img_book_detail);
                Log.v("uuuuuuuuuuuu", "----" + Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName());
                setIamge(Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName(), viewHolder.img_book_detail);
            }
        }
    }

    private void setIamge(final String url, final ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.doGet(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        byte[] Picture = response.body().bytes();
                        //使用BitmapFactory工厂，把字节数组转化为bitmap
                        //java.lang.OutOfMemoryError: Failed to allocate a 14445012 byte allocation with 3456152 free bytes and 3MB until OOM
                        //待优化
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                        //通过imageview，设置图片
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog_load.dismiss();
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                });
            }
        }).start();

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
