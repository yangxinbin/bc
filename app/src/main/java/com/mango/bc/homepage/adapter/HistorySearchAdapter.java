package com.mango.bc.homepage.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bean.VipType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class HistorySearchAdapter extends RecyclerView.Adapter {

    private List<String> datas;

    public HistorySearchAdapter(List<String> datas) {
        this.datas = datas;
    }

    private HistorySearchAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(HistorySearchAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void reMove() {
        List<String> m = new ArrayList<String>();
        this.datas = m;
        this.notifyDataSetChanged();
    }

    public List<String> reMoveItem(int position) {
        if (datas != null) {
            datas.remove(position);
            for (int i = 0; i < datas.size(); i++) {
                Log.v("ooooooo", "---addItem--" + datas.get(i));
            }
        }
        this.notifyDataSetChanged();
        return datas;
    }

    public interface OnItemClickLitener {

        void onItemClick(View view, int position);

        void onDeleteClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_layout, parent, false);
        return new HistorySearchAdapter.SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HistorySearchAdapter.SingleViewHolder) {
            final HistorySearchAdapter.SingleViewHolder viewHolder = (HistorySearchAdapter.SingleViewHolder) holder;
            viewHolder.tv_search.setText(datas.get(position));

            if (mOnItemClickLitener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {
        TextView tv_search;
        ImageView img_delete;

        public SingleViewHolder(final View itemView) {
            super(itemView);
            tv_search = (TextView) itemView.findViewById(R.id.tv_search);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }

}
