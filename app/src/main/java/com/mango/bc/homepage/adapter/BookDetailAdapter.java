package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class BookDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BookBean.DescriptionImagesBean> datas = new ArrayList<>();

    public BookDetailAdapter(List<BookBean.DescriptionImagesBean> datas, Context context) {
        this.context = context;
        this.datas = datas;
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
                Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName()).into(viewHolder.img_book_detail);
                Log.v("uuuuuuuuuuuu", "----" + Urls.HOST_GETFILE + "?name=" + datas.get(position).getFileName());
            }
        }
    }//

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
