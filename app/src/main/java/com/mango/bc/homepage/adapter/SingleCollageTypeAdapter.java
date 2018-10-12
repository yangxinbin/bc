package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bean.CollageTypeBean;
import com.mango.bc.homepage.net.bean.BookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class SingleCollageTypeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private int selected = -1;
    private List<CollageTypeBean> list = new ArrayList<>();

    private OnItemClickLitener mOnItemClickLitener;

    public SingleCollageTypeAdapter(Context context, List<CollageTypeBean> list) {
        this.mContext = context;
        this.list = list;
    }

    public CollageTypeBean getItem(int position) {
        return list == null ? null : list.get(position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collage_pay_layout, parent, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SingleViewHolder) {
            final SingleViewHolder viewHolder = (SingleViewHolder) holder;
            viewHolder.tv_pay_num.setText(list.get(position).getNum());
            viewHolder.tv_pay_ppg.setText(list.get(position).getPpg());
            if (selected == position) {
                viewHolder.itemView.setSelected(true);
            } else {
                viewHolder.itemView.setSelected(false);
            }

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
        return 2;
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pay_num, tv_pay_ppg;

        public SingleViewHolder(View itemView) {
            super(itemView);
            tv_pay_num = itemView.findViewById(R.id.tv_pay_num);
            tv_pay_ppg = itemView.findViewById(R.id.tv_pay_ppg);
        }
    }

}
