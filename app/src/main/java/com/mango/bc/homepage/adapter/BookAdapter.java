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
import com.mango.bc.homepage.bean.Books;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class BookAdapter extends RecyclerView.Adapter {

    private List<Books> datas = new ArrayList<>();

    public BookAdapter(List<Books> datas) {
        this.datas = datas;
    }

    private BookAdapter.OnItemClickLitener mOnItemClickLitener;

    public BookAdapter() {
    }

    public void setOnItemClickLitener(BookAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onStageClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recycler_item, parent, false);
        return new BookAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookAdapter.BookViewHolder) {
            final BookAdapter.BookViewHolder viewHolder = (BookAdapter.BookViewHolder) holder;
            //viewHolder.tv_title.setText(datas.get(position).getBookTitle());
            //viewHolder.tv_title.setText(datas.get(position).getBookTitle());
        }
    }

    @Override
    public int getItemCount() {
        return 3;//datas.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title,tv_detail,tv_time,tv_buy,tv_stage;
        ImageView img_book;
        LinearLayout book_item;

        public BookViewHolder(final View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_buy = (TextView) itemView.findViewById(R.id.tv_buy);
            tv_stage = (TextView) itemView.findViewById(R.id.tv_stage);

            img_book = (ImageView) itemView.findViewById(R.id.img_book);
            book_item = (LinearLayout) itemView.findViewById(R.id.book_item);
            book_item.setOnClickListener(this);
            tv_stage.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.book_item:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(book_item,getAdapterPosition());
                    }
                    break;
                case R.id.tv_stage:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onStageClick(tv_stage,getAdapterPosition());
                    }
                    break;
            }
        }
    }

}
