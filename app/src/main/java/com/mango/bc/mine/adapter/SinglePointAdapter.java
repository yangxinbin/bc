package com.mango.bc.mine.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bean.VipType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class SinglePointAdapter extends RecyclerView.Adapter {

    private List<VipType> datas = new ArrayList<>();

    private int selected = -1;

    public SinglePointAdapter(List<VipType> datas) {
        this.datas = datas;
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public VipType getItem(int position) {
        return datas.get(position);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point_layout, parent, false);

        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SingleViewHolder) {
            final SingleViewHolder viewHolder = (SingleViewHolder) holder;
            viewHolder.tv_title.setText(datas.get(position).getTitle());
            if (datas.get(position).getFlagtitle().equals("")) {
                viewHolder.Flagtitle.setVisibility(View.GONE);
            } else {
                viewHolder.Flagtitle.setText(datas.get(position).getFlagtitle());
            }
            if (datas.get(position).getDetail().equals("")) {
                viewHolder.detail.setVisibility(View.GONE);
            } else {
                viewHolder.detail.setText(datas.get(position).getDetail());
            }
            viewHolder.tv_unbc.setText(datas.get(position).getUnbc());
            viewHolder.tv_unbc.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            viewHolder.tv_bc.setText(datas.get(position).getBc());

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
        return datas.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, Flagtitle, detail, tv_unbc, tv_bc;

        public SingleViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            Flagtitle = (TextView) itemView.findViewById(R.id.Flagtitle);
            detail = (TextView) itemView.findViewById(R.id.detail);
            tv_unbc = (TextView) itemView.findViewById(R.id.tv_unbc);
            tv_bc = (TextView) itemView.findViewById(R.id.tv_bc);

        }
    }

}
