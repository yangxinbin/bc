package com.mango.bc.bookcase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class MyBookGirdAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MyBookBean> datas = new ArrayList<>();

    public MyBookGirdAdapter(Context context) {
        this.context = context;
    }

    public MyBookGirdAdapter(List<MyBookBean> datas) {
        this.datas = datas;
    }

    private MyBookGirdAdapter.OnItemClickLitener mOnItemClickLitener;

    public MyBookGirdAdapter() {
    }

    public void setmDate(List<MyBookBean> data) {
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<MyBookBean> m = new ArrayList<MyBookBean>();
        this.datas = m;
        this.notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        datas.remove(position);
        this.notifyItemRemoved(position);
        this.notifyDataSetChanged();
    }
    /**
     * 添加列表项     * @param item
     */
    public void addItem(MyBookBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
        this.notifyDataSetChanged();
    }

    public MyBookBean getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    public void setOnItemClickLitener(MyBookGirdAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onDeleteClick(View view, int position);

        void onButGiftClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_mybook_item, parent, false);
        return new MyBookGirdAdapter.BookGirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyBookGirdAdapter.BookGirdViewHolder) {
            final MyBookGirdAdapter.BookGirdViewHolder viewHolder = (MyBookGirdAdapter.BookGirdViewHolder) holder;
            if (datas.get(position).getBook() == null)
                return;
            viewHolder.tv_free_title.setText(datas.get(position).getBook().getTitle());
            if (datas.get(position).getBook().getCover() != null)
                Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getBook().getCover().getFileName()).into(((MyBookGirdAdapter.BookGirdViewHolder) holder).img_free_book);
            if (datas.get(position).getType().equals("gift")) {
                viewHolder.textView_gift_state.setVisibility(View.VISIBLE);
                viewHolder.textView_gift_state.setText(DateUtil.getDateToString(datas.get(position).getCreatedOn()+(1000*60*60*24*7),"yyyy-MM-dd")+"到期");
                if (System.currentTimeMillis() > (datas.get(position).getCreatedOn()+(1000*60*60*24*7))){
                    Log.v("gggggggggg",System.currentTimeMillis()+"==?=="+System.currentTimeMillis());
                    viewHolder.textView_gift_state.setText("已过期");
                    viewHolder.textView_delete.setVisibility(View.VISIBLE);
                    viewHolder.f_buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickLitener != null) {
                                mOnItemClickLitener.onButGiftClick(viewHolder.f_buy, position);
                            }
                        }
                    });
                }else {
                    viewHolder.textView_delete.setVisibility(View.GONE);
                }
            }else {
                viewHolder.textView_delete.setVisibility(View.GONE);
                viewHolder.textView_gift_state.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookGirdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_free_title, textView_gift_state, textView_delete;
        ImageView img_free_book;
        LinearLayout book_free_item;
        FrameLayout f_buy;

        public BookGirdViewHolder(final View itemView) {
            super(itemView);
            img_free_book = (ImageView) itemView.findViewById(R.id.img_free_book);
            tv_free_title = (TextView) itemView.findViewById(R.id.tv_free_title);
            textView_gift_state = (TextView) itemView.findViewById(R.id.textView_gift_state);
            textView_delete = (TextView) itemView.findViewById(R.id.textView_delete);
            f_buy = (FrameLayout) itemView.findViewById(R.id.f_buy);
            book_free_item = (LinearLayout) itemView.findViewById(R.id.book_free_item);
            book_free_item.setOnClickListener(this);
            textView_delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.book_free_item:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(book_free_item, getAdapterPosition());
                    }
                    break;
                case R.id.textView_delete:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onDeleteClick(textView_delete, getAdapterPosition());
                    }
                    break;
            }
        }
    }

}
