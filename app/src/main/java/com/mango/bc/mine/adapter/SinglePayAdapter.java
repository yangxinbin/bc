package com.mango.bc.mine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.homepage.bean.VipType;
import com.mango.bc.mine.activity.PointApplyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class SinglePayAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private int selected = -1;


    private OnItemClickLitener mOnItemClickLitener;

    public SinglePayAdapter(Context context) {
        this.mContext = context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point_pay_layout, parent, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SingleViewHolder) {
            final SingleViewHolder viewHolder = (SingleViewHolder) holder;
/*            switch (position) {
                case 0:
                    viewHolder.img_pay_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.alipay));
                    viewHolder.tv_pay_name.setText(R.string.aliPay);
                    break;
                case 1:*/
            viewHolder.img_pay_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.weixin));
            viewHolder.tv_pay_name.setText(R.string.weixin);
/*                    break;
            }
            if (selected == position) {
                viewHolder.itemView.setSelected(true);
            } else {
                viewHolder.itemView.setSelected(false);
            }*/

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
        return 1;
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {
        ImageView img_pay_icon;
        TextView tv_pay_name;

        public SingleViewHolder(View itemView) {
            super(itemView);
            img_pay_icon = itemView.findViewById(R.id.img_pay_icon);
            tv_pay_name = itemView.findViewById(R.id.tv_pay_name);
        }
    }

}
