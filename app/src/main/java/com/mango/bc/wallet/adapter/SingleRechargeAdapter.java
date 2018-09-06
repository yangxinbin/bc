package com.mango.bc.wallet.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.wallet.bean.RechargeType;

import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class SingleRechargeAdapter extends RecyclerView.Adapter {

    private List<RechargeType> datas;

    private int selected = -1;

    public SingleRechargeAdapter(List<RechargeType> datas) {
        this.datas = datas;
    }

    private OnItemClickLitener mOnItemClickLitener;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_layout, parent, false);
        return new SingleRechargeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SingleRechargeViewHolder) {
            final SingleRechargeViewHolder viewHolder = (SingleRechargeViewHolder) holder;
            viewHolder.tv_bc.setText(datas.get(position).getBc());
            viewHolder.tv_money.setText(datas.get(position).getMoney());

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

    class SingleRechargeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bc,tv_money;
        public SingleRechargeViewHolder(View itemView) {
            super(itemView);
            tv_bc = (TextView) itemView.findViewById(R.id.tv_bc);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);

        }
    }

}
