package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class BookGirdFreeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BookBean> datas = new ArrayList<>();

    public BookGirdFreeAdapter(Context context) {
        this.context = context;
    }

    public BookGirdFreeAdapter(List<BookBean> datas) {
        this.datas = datas;
    }

    private BookGirdFreeAdapter.OnItemClickLitener mOnItemClickLitener;

    public BookGirdFreeAdapter() {
    }

    public void setmDate(List<BookBean> data) {
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<BookBean> m = new ArrayList<BookBean>();
        this.datas = m;
        this.notifyDataSetChanged();
    }

    /**
     * 添加列表项     * @param item
     */
    public void addItem(BookBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
        this.notifyDataSetChanged();
    }

    public BookBean getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    public void setOnItemClickLitener(BookGirdFreeAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {

        void onItemClick(View view, int position);

        void onStageClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_free_item, parent, false);
        return new BookGirdFreeAdapter.BookGirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookGirdFreeAdapter.BookGirdViewHolder) {
            final BookGirdFreeAdapter.BookGirdViewHolder viewHolder = (BookGirdFreeAdapter.BookGirdViewHolder) holder;
            viewHolder.tv_free_title.setText(datas.get(position).getTitle());
            viewHolder.tv_free_stage.setText("免费领取");
            if (datas.get(position).getCover() != null)
                Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName()).into(((BookGirdFreeAdapter.BookGirdViewHolder) holder).img_free_book);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookGirdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_free_title, tv_free_stage;
        ImageView img_free_book;
        LinearLayout book_free_item;

        public BookGirdViewHolder(final View itemView) {
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
