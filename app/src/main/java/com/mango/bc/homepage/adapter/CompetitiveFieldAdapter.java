package com.mango.bc.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class CompetitiveFieldAdapter extends RecyclerView.Adapter {

    private List<String> datas = new ArrayList<>();

    public CompetitiveFieldAdapter(List<String> datas) {
        this.datas = datas;
    }

    private CompetitiveFieldAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(CompetitiveFieldAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        //void onStageClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_field_item, parent, false);
        return new CompetitiveFieldAdapter.FinefieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CompetitiveFieldAdapter.FinefieldViewHolder) {
            final CompetitiveFieldAdapter.FinefieldViewHolder viewHolder = (CompetitiveFieldAdapter.FinefieldViewHolder) holder;
            viewHolder.tv_field.setText(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class FinefieldViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_field;
        LinearLayout book_field_item;

        public FinefieldViewHolder(final View itemView) {
            super(itemView);
            tv_field = (TextView) itemView.findViewById(R.id.tv_field);
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
