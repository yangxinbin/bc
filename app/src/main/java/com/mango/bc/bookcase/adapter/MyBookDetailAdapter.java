package com.mango.bc.bookcase.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
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

public class MyBookDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MyBookBean.BookBean.DescriptionImagesBean> datas = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public MyBookDetailAdapter(List<MyBookBean.BookBean.DescriptionImagesBean> datas, Context context) {
        AppUtils.createLoadDailog(context);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_item, parent, false);
        return new MyBookDetailAdapter.MyBookDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyBookDetailAdapter.MyBookDetailViewHolder) {
            final MyBookDetailAdapter.MyBookDetailViewHolder viewHolder = (MyBookDetailAdapter.MyBookDetailViewHolder) holder;
            if (datas.get(position).getFileName() != null) {
                //Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName()).into(viewHolder.img_book_detail);
                Log.v("uuuuuuuuuuuu", "----" + Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName());
                setIamge(Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName(), viewHolder.img_book_detail);
            }
        }
    }

    private void setIamge(String url, final ImageView imageView) {
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] Picture = response.body().bytes();
                //使用BitmapFactory工厂，把字节数组转化为bitmap
                final Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                //通过imageview，设置图片
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.dissmissLoadDailog(context);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyBookDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView img_book_detail;

        public MyBookDetailViewHolder(final View itemView) {
            super(itemView);
            img_book_detail = (ImageView) itemView.findViewById(R.id.img_book_detail);

        }

    }

}
