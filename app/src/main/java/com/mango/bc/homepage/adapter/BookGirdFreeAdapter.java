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
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
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

public class BookGirdFreeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BookBean> datas = new ArrayList<>();
    private SPUtils spUtils;

    public BookGirdFreeAdapter(Context context) {
        spUtils = SPUtils.getInstance("bc", context);
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

        void onItemPlayClick(View view, int position);

        void onItemGetClick(View view, int position);

        void onPlayClick(View view, int position);

        void onGetClick(View view, int position);
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
            if (datas.get(position) == null)
                return;
            viewHolder.tv_free_title.setText(datas.get(position).getTitle());
            if (chechState(datas.get(position).getId())) {//拿书id遍历判断
/*                if (AudioPlayer.get().isPlaying() && datas.get(position).getId().equals(spUtils.getString("isSameBook", ""))) {
                    viewHolder.tv_free_stage.setText("播放中");
                }else {*/
                    viewHolder.tv_free_stage.setText("播放");//是领取
                //}
                viewHolder.tv_free_stage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onPlayClick(viewHolder.tv_free_stage, position);
                    }
                });
                viewHolder.book_free_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemPlayClick(viewHolder.tv_free_stage, position);
                    }
                });
            } else {
                viewHolder.tv_free_stage.setText("免费领取");//否领取
                viewHolder.tv_free_stage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onGetClick(viewHolder.tv_free_stage, position);
                    }
                });
                viewHolder.book_free_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemGetClick(viewHolder.tv_free_stage, position);
                    }
                });
            }
            if (datas.get(position).getCover() != null)
                Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName()).into(((BookGirdFreeAdapter.BookGirdViewHolder) holder).img_free_book);
        }
    }

    private boolean chechState(String bookId) {
        String data = spUtils.getString("allMyBook", "");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> list = gson.fromJson(data, listType);
        if (list == null)
            return false;
        if (list.contains(bookId)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookGirdViewHolder extends RecyclerView.ViewHolder {
        TextView tv_free_title, tv_free_stage;
        RoundImageView img_free_book;
        LinearLayout book_free_item;

        public BookGirdViewHolder(final View itemView) {
            super(itemView);
            img_free_book = (RoundImageView) itemView.findViewById(R.id.img_free_book);
            tv_free_title = (TextView) itemView.findViewById(R.id.tv_free_title);
            tv_free_stage = (TextView) itemView.findViewById(R.id.tv_free_stage);
            book_free_item = (LinearLayout) itemView.findViewById(R.id.book_free_item);
        }
    }

}
