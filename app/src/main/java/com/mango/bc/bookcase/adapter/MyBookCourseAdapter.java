package com.mango.bc.bookcase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class MyBookCourseAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MyBookBean.BookBean.ChaptersBean> datas = new ArrayList<>();
    private MyBookCourseAdapter.OnItemClickLitener mOnItemClickLitener;


    public MyBookCourseAdapter(List<MyBookBean.BookBean.ChaptersBean> datas, Context context) {
        this.context = context;
        this.datas = datas;
    }

    public void setOnItemClickLitener(MyBookCourseAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onReadClick(View view, int position);

        void onTxtClick(View view, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_course_item, parent, false);
        return new MyBookCourseAdapter.BookCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyBookCourseAdapter.BookCourseViewHolder) {
            final MyBookCourseAdapter.BookCourseViewHolder viewHolder = (MyBookCourseAdapter.BookCourseViewHolder) holder;
            if (datas.get(position) != null) {
                viewHolder.tv_title.setText(datas.get(position).getTitle());
                viewHolder.tv_time.setText("时长："+secToTime(datas.get(position).getDuration()));
                if (!datas.get(position).isFree()){
                    viewHolder.img_txt.setImageResource(R.drawable.lock);
                }
                viewHolder.img_read.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (datas.get(position).isFree()){
                            mOnItemClickLitener.onReadClick(viewHolder.img_read, position);
                        }else {
                            AppUtils.showToast(context,"请购买");
                        }
                    }
                });
                viewHolder.img_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (datas.get(position).isFree()){
                            mOnItemClickLitener.onTxtClick(viewHolder.img_txt, position);
                        }else {
                            AppUtils.showToast(context,"请购买");
                        }
                    }
                });
            }
        }
    }
    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookCourseViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/ {
        ImageView img_read, img_txt;
        TextView tv_title, tv_time;

        public BookCourseViewHolder(final View itemView) {
            super(itemView);
            img_read = (ImageView) itemView.findViewById(R.id.img_read);
            img_txt = (ImageView) itemView.findViewById(R.id.img_txt);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            //img_read.setOnClickListener(this);
            //img_txt.setOnClickListener(this);

        }
/*
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_read:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onReadClick(img_read, getAdapterPosition());
                    }
                    break;
                case R.id.img_txt:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onTxtClick(img_txt, getAdapterPosition());
                    }
                    break;
            }
        }*/
    }

}
