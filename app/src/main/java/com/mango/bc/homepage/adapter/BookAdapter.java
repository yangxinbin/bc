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

public class BookAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<BookBean> datas = new ArrayList<>();

    public BookAdapter(List<BookBean> datas) {
        this.datas = datas;
    }

    private BookAdapter.OnItemClickLitener mOnItemClickLitener;

    public BookAdapter(Context context) {
        this.context = context;
    }

    public BookAdapter() {
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
    public BookBean getItem(int position) {
        return datas == null ? null : datas.get(position);
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
            if (((BookAdapter.BookViewHolder) holder) != null && datas.get(position) != null) {
                ((BookAdapter.BookViewHolder) holder).tv_title.setText(datas.get(position).getTitle());
                ((BookAdapter.BookViewHolder) holder).tv_detail.setText(datas.get(position).getSubtitle());
                ((BookAdapter.BookViewHolder) holder).tv_time.setText("共" + datas.get(position).getChapters().size() + "节课");
                ((BookAdapter.BookViewHolder) holder).tv_buy.setText("已购买" + datas.get(position).getSold());
                ((BookAdapter.BookViewHolder) holder).tv_stage.setText("免费领取");

                if (datas.get(position).getCover() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName()).into(((BookAdapter.BookViewHolder) holder).img_book);
                    /*Picasso.with(context)
                            .load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName())
                            .into(((BookViewHolder) holder).img_book);*/
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title, tv_detail, tv_time, tv_buy, tv_stage;
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
                        mOnItemClickLitener.onItemClick(book_item, getAdapterPosition());
                    }
                    break;
                case R.id.tv_stage:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onStageClick(tv_stage, getAdapterPosition());
                    }
                    break;
            }
        }
    }

}
