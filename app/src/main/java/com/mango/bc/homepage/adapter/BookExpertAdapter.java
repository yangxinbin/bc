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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class BookExpertAdapter extends RecyclerView.Adapter {
    private Context context;
    private BookExpertAdapter.OnItemClickLitener mOnItemClickLitener;
    private SPUtils spUtilsAllMyBook;
    private List<BookBean> datas = new ArrayList<>();

    public BookExpertAdapter(List<BookBean> datas) {
        this.datas = datas;
    }


    public BookExpertAdapter(Context context) {
        spUtilsAllMyBook = SPUtils.getInstance("allMyBook", context);
        this.context = context;
    }

    public BookExpertAdapter() {
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

    public void setOnItemClickLitener(BookExpertAdapter.OnItemClickLitener mOnItemClickLitener) {
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
        return new BookExpertAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookExpertAdapter.BookViewHolder) {
            if (((BookExpertAdapter.BookViewHolder) holder) != null && datas.get(position) != null) {
                ((BookExpertAdapter.BookViewHolder) holder).tv_title.setText(datas.get(position).getTitle());
                ((BookExpertAdapter.BookViewHolder) holder).tv_detail.setText(datas.get(position).getSubtitle());
                ((BookExpertAdapter.BookViewHolder) holder).tv_time.setText("共" + datas.get(position).getChapters().size() + "节课");
                ((BookExpertAdapter.BookViewHolder) holder).tv_buy.setText("已购买" + datas.get(position).getSold());
                if (datas.get(position).getCover() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName()).into(((BookExpertAdapter.BookViewHolder) holder).img_book);
                ((BookExpertAdapter.BookViewHolder) holder).tv_stage.setText(datas.get(position).getPrice() + "积分");
                if (chechState(datas.get(position).getId())) {//拿书id遍历判断
                    ((BookExpertAdapter.BookViewHolder) holder).tv_stage.setText("播放");//是领取
                    ((BookExpertAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onPlayClick(((BookExpertAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((BookExpertAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemPlayClick(((BookExpertAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                } else {
                    ((BookExpertAdapter.BookViewHolder) holder).tv_stage.setText(datas.get(position).getPrice() + "积分");//否领取
                    ((BookExpertAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onGetClick(((BookExpertAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((BookExpertAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemGetClick(((BookExpertAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                }
            }
        }
    }

    private boolean chechState(String bookId) {
        String data = spUtilsAllMyBook.getString("allMyBook", "");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> list = gson.fromJson(data, listType);

        if (list == null)
            return false;
        if (list.contains(bookId)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_detail, tv_time, tv_buy, tv_stage;
        RoundImageView img_book;
        LinearLayout book_item;

        public BookViewHolder(final View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_buy = (TextView) itemView.findViewById(R.id.tv_buy);
            tv_stage = (TextView) itemView.findViewById(R.id.tv_stage);

            img_book = (RoundImageView) itemView.findViewById(R.id.img_book);
            book_item = (LinearLayout) itemView.findViewById(R.id.book_item);

        }

    }

}
