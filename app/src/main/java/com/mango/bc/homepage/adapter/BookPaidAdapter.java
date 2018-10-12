package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class BookPaidAdapter extends RecyclerView.Adapter {
    private Context context;
    private BookPaidAdapter.OnItemClickLitener mOnItemClickLitener;
    private SPUtils spUtils;
    private List<BookBean> datas = new ArrayList<>();
    private boolean isVip = false;

    public BookPaidAdapter(List<BookBean> datas) {
        this.datas = datas;
    }


    public BookPaidAdapter(Context context) {
        spUtils = SPUtils.getInstance("bc", context);
        this.context = context;
    }

    public BookPaidAdapter() {
    }

    public void setmDate(List<BookBean> data) {
        this.datas = data;
        UserBean userBean = MineJsonUtils.readUserBean(spUtils.getString("auth", ""));
        Log.v("lllllllll", "===userBean.isVip()=" + userBean.isVip());
        if (userBean != null)
            isVip = userBean.isVip();
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

    public void setOnItemClickLitener(BookPaidAdapter.OnItemClickLitener mOnItemClickLitener) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recycler_paid_items, parent, false);
        return new BookPaidAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookPaidAdapter.BookViewHolder) {
            if (((BookPaidAdapter.BookViewHolder) holder) != null && datas.get(position) != null) {
                ((BookPaidAdapter.BookViewHolder) holder).tv_title.setText(datas.get(position).getTitle());
                ((BookPaidAdapter.BookViewHolder) holder).tv_detail.setText(datas.get(position).getSubtitle());
                if (datas.get(position).getBanner() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getBanner().getFileName()).into(((BookPaidAdapter.BookViewHolder) holder).img_book);
                //((BookExpertAdapter.BookViewHolder) holder).tv_stage.setText(datas.get(position).getPrice() + "积分");
                if (chechState(datas.get(position).getId())) {//拿书id遍历判断
                    Log.v("rrrrrrr", "==y==");
/*                    if (AudioPlayer.get().isPlaying() && datas.get(position).getId().equals(spUtils.getString("isSameBook", ""))) {
                        ((BookExpertAdapter.BookViewHolder) holder).tv_stage.setText("播放中");
                    } else {*/
                        ((BookPaidAdapter.BookViewHolder) holder).tv_stage.setText("播放");
                    //}
                    //((BookExpertAdapter.BookViewHolder) holder).tv_stage.setText("播放");//是领取
                    ((BookPaidAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onPlayClick(((BookPaidAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((BookPaidAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemPlayClick(((BookPaidAdapter.BookViewHolder) holder).book_item, position);
                        }
                    });
                } else {
                    Log.v("rrrrrrr", "==n=="+isVip);
                    if (isVip){
                        ((BookPaidAdapter.BookViewHolder) holder).tv_stage.setText(datas.get(position).getVipPrice() + "积分");//否领取
                    }else {
                        ((BookPaidAdapter.BookViewHolder) holder).tv_stage.setText(datas.get(position).getPrice() + "积分");//否领取
                    }
                    ((BookPaidAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onGetClick(((BookPaidAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((BookPaidAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemGetClick(((BookPaidAdapter.BookViewHolder) holder).book_item, position);
                        }
                    });
                }
            }
        }
    }

    private boolean chechState(String bookId) {
        if (spUtils != null) {
            String data = spUtils.getString("allMyBook", "");
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
        }else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_detail, tv_stage;
        RoundImageView img_book;
        LinearLayout book_item;

        public BookViewHolder(final View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
            tv_stage = (TextView) itemView.findViewById(R.id.tv_stage);

            img_book = (RoundImageView) itemView.findViewById(R.id.img_book);
            book_item = (LinearLayout) itemView.findViewById(R.id.book_item);

        }

    }

}
