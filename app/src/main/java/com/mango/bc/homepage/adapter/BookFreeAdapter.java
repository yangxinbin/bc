package com.mango.bc.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bean.BookFree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class BookFreeAdapter extends RecyclerView.Adapter {

    private List<BookFree> datas = new ArrayList<>();

    public BookFreeAdapter(List<BookFree> datas) {
        this.datas = datas;
    }

    private BookFreeAdapter.OnItemClickLitener mOnItemClickLitener;

    public BookFreeAdapter() {
    }

    public void setOnItemClickLitener(BookFreeAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {

        void onItemClick(View view, int position);

        void onStageClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_free_item, parent, false);
        return new BookFreeAdapter.BookFreeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookFreeAdapter.BookFreeViewHolder) {
            final BookFreeAdapter.BookFreeViewHolder viewHolder = (BookFreeAdapter.BookFreeViewHolder) holder;
            //viewHolder.tv_free_title.setText(datas.get(position).getBookFreeTitle());
            //viewHolder.tv_free_title.setText(datas.get(position).getBookFreeTitle());
        }
    }

    @Override
    public int getItemCount() {
        return 3;//datas.size();
    }

    class BookFreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_free_title, tv_free_stage;
        ImageView img_free_book;
        LinearLayout book_free_item;

        public BookFreeViewHolder(final View itemView) {
            super(itemView);
            img_free_book = (ImageView) itemView.findViewById(R.id.img_free_book);
            tv_free_title = (TextView) itemView.findViewById(R.id.tv_free_title);
            tv_free_stage = (TextView) itemView.findViewById(R.id.tv_free_stage);
            book_free_item = (LinearLayout) itemView.findViewById(R.id.book_free_item);
            book_free_item.setOnClickListener(this);
            tv_free_stage.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.book_free_item:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(book_free_item, getAdapterPosition());
                    }
                    break;
                case R.id.tv_free_stage:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onStageClick(tv_free_stage, getAdapterPosition());
                    }
                    break;
            }
        }
    }

}
