package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.homepage.net.bean.CompetitiveBookBean;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/11.
 */

public class BookComprtitiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;//自注册的接口给调用者用于点击逻辑
    private List<CompetitiveBookBean> mData = new ArrayList<>();
    public static final int TYPE_ITEM = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public void setmDate(List<CompetitiveBookBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<CompetitiveBookBean> m = new ArrayList<CompetitiveBookBean>();
        this.mData = m;
        this.notifyDataSetChanged();
    }

    /**
     * 添加列表项     * @param item
     */
    public void addItem(CompetitiveBookBean bean) {
        if (mData != null) {
            mData.add(bean);
        }
        this.notifyDataSetChanged();
    }


    public BookComprtitiveAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_recycler_item, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;

    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (holder instanceof ItemViewHolder) {
            if (((ItemViewHolder) holder) != null && mData.get(pos) != null) {
                Log.v("yyyyy", "====pos======" + pos % 20);//
                ((ItemViewHolder) holder).tv_title.setText(mData.get(position).getTitle());
                ((ItemViewHolder) holder).tv_detail.setText(mData.get(position).getSubtitle());
                ((ItemViewHolder) holder).tv_time.setText("共" + mData.get(position).getChapters().size() + "节课");
                ((ItemViewHolder) holder).tv_buy.setText("已购买" + mData.get(position).getSold());
                if (mData.get(position).getCover() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + mData.get(position).getCover().getFileName()).into(((ItemViewHolder) holder).img_book);
                    /*Picasso.with(context)
                            .load(Urls.HOST_GETFILE + "?name=" + mData.get(position).getCover().getFileName())
                            .into(((ItemViewHolder) holder).img_book);*/
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickLitener(BookComprtitiveAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onStageClick(View view, int position);
    }


    public CompetitiveBookBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_title, tv_detail, tv_time, tv_buy, tv_stage;
        RoundImageView img_book;
        LinearLayout book_item;

        public ItemViewHolder(View v) {
            super(v);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_buy = (TextView) itemView.findViewById(R.id.tv_buy);
            tv_stage = (TextView) itemView.findViewById(R.id.tv_stage);

            img_book = (RoundImageView) itemView.findViewById(R.id.img_book);
            book_item = (LinearLayout) itemView.findViewById(R.id.book_item);
            book_item.setOnClickListener(this);
            tv_stage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
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
