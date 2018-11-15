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
import com.mango.bc.R;
import com.mango.bc.util.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class FieldAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> datas = new ArrayList<>();

    public FieldAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    private FieldAdapter.OnItemClickLitener mOnItemClickLitener;

    public FieldAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnItemClickLitener(FieldAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        //void onStageClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.field_item, parent, false);
        return new FieldAdapter.FinefieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FieldAdapter.FinefieldViewHolder) {
            final FieldAdapter.FinefieldViewHolder viewHolder = (FieldAdapter.FinefieldViewHolder) holder;
            if (datas != null) {
                Glide.with(mContext).load(Urls.HOST_GETFILE + "?name=" + datas.get(position)).into(viewHolder.img);
            } else {
                viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.school));
            }
/*            switch (position) {
                case 0:
                    viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.school));
                    break;
                case 1:
                    viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.develop_guide));
                    break;
                case 2:
                    viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.invest));
                    break;
                case 3:
                    viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.figure));
                    break;
                case 4:
                    viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.guide));
                    break;
                case 5:
                    viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.number_wallet));
                    break;
                case 6:
                    viewHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.property));
                    break;
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class FinefieldViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        LinearLayout book_field_item;

        public FinefieldViewHolder(final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            book_field_item = (LinearLayout) itemView.findViewById(R.id.book_field_item);
            book_field_item.setOnClickListener(this);
            //tv_field.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.book_field_item:
                    Log.v("yyyyyyy", "===book_field_item--");

                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(book_field_item, getAdapterPosition());
                    }
                    break;
/*                case R.id.tv_field:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onStageClick(tv_field, getAdapterPosition());
                    }
                    break;*/
            }
        }
    }

}
