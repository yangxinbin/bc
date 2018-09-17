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

public class BookNewestAdapter extends RecyclerView.Adapter {
    private Context context;
    private BookNewestAdapter.OnItemClickLitener mOnItemClickLitener;

    private List<BookBean> datas = new ArrayList<>();

    public BookNewestAdapter(List<BookBean> datas) {
        this.datas = datas;
    }


    public BookNewestAdapter(Context context) {
        this.context = context;
    }

    public BookNewestAdapter() {
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

    public void setOnItemClickLitener(BookNewestAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemPlayClick(View view, int position);

        void onItemGetClick(View view, int position);

        void onPlayClick(View view, int position);

        void onGetClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recycler_item, parent, false);
        return new BookNewestAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookNewestAdapter.BookViewHolder) {
            if (((BookNewestAdapter.BookViewHolder) holder) != null && datas.get(position) != null) {
                ((BookNewestAdapter.BookViewHolder) holder).tv_title.setText(datas.get(position).getTitle());
                ((BookNewestAdapter.BookViewHolder) holder).tv_detail.setText(datas.get(position).getSubtitle());
                ((BookNewestAdapter.BookViewHolder) holder).tv_time.setText("共" + datas.get(position).getChapters().size() + "节课");
                ((BookNewestAdapter.BookViewHolder) holder).tv_buy.setText("已购买" + datas.get(position).getSold());
                if (datas.get(position).getCover() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName()).into(((BookNewestAdapter.BookViewHolder) holder).img_book);

                if (false) {//拿书id遍历判断
                    ((BookViewHolder) holder).tv_stage.setText("播放");//是领取
                    ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onPlayClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((BookNewestAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemPlayClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                } else {
                    ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setText(datas.get(position).getPrice() + "积分");//否领取
                    ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onGetClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((BookNewestAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemGetClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
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

        }
    }

}
