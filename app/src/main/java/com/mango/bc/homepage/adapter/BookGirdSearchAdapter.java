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

public class BookGirdSearchAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BookBean> datas = new ArrayList<>();
    private SPUtils spUtils;

    public BookGirdSearchAdapter(Context context) {
        spUtils = SPUtils.getInstance("bc", context);
        this.context = context;
    }

    public BookGirdSearchAdapter(List<BookBean> datas) {
        this.datas = datas;
    }

    private BookGirdSearchAdapter.OnItemClickLitener mOnItemClickLitener;

    public BookGirdSearchAdapter() {
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

    public void setOnItemClickLitener(BookGirdSearchAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {

        void onItemPlayClick(View view, int position);

        void onItemExpertPlayClick(View view, int position);

        void onItemFreeGetClick(View view, int position);

        void onItemBuyGetClick(View view, int position);

        void onItemExpertGetClick(View view, int position);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_search_item, parent, false);
        return new BookGirdSearchAdapter.BookGirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookGirdSearchAdapter.BookGirdViewHolder) {
            final BookGirdSearchAdapter.BookGirdViewHolder viewHolder = (BookGirdSearchAdapter.BookGirdViewHolder) holder;
            viewHolder.tv_search_title.setText(datas.get(position).getTitle());
            if (datas.get(position).getCover() != null)
                Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName()).into(((BookGirdSearchAdapter.BookGirdViewHolder) holder).img_search_book);
            if (chechState(datas.get(position).getId())) {//拿书id遍历判断
                if (datas.get(position).getType().equals("paid")){
                    viewHolder.book_search_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemExpertPlayClick(viewHolder.book_search_item, position);
                        }
                    });
                }else {
                    viewHolder.book_search_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemPlayClick(viewHolder.book_search_item, position);
                        }
                    });
                }
            } else {
                if (datas.get(position).getType().equals("free")) {
                    viewHolder.book_search_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemFreeGetClick(viewHolder.book_search_item, position);//免费领
                        }
                    });
                } else if (datas.get(position).getType().equals("member")) {
                    viewHolder.book_search_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemBuyGetClick(viewHolder.book_search_item, position);//上新+精品购买  需要加vip判断
                        }
                    });
                } else if (datas.get(position).getType().equals("paid")) {
                    viewHolder.book_search_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemExpertGetClick(viewHolder.book_search_item, position);//大咖购买
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

    class BookGirdViewHolder extends RecyclerView.ViewHolder {
        TextView tv_search_title;
        RoundImageView img_search_book;
        LinearLayout book_search_item;

        public BookGirdViewHolder(final View itemView) {
            super(itemView);
            img_search_book = (RoundImageView) itemView.findViewById(R.id.img_search_book);
            tv_search_title = (TextView) itemView.findViewById(R.id.tv_search_title);
            book_search_item = (LinearLayout) itemView.findViewById(R.id.book_search_item);
        }

    }

}
