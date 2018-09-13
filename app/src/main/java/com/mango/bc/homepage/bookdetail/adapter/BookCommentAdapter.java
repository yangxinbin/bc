package com.mango.bc.homepage.bookdetail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.bean.CommentBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.Urls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/4.
 */

public class BookCommentAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CommentBean> datas = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public BookCommentAdapter(Context context) {
        this.context = context;
    }
    public void setmDate(List<CommentBean> data) {
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<CommentBean> m = new ArrayList<CommentBean>();
        this.datas = m;
        this.notifyDataSetChanged();
    }
    public CommentBean getItem(int position) {
        return datas == null ? null : datas.get(position);
    }
    /**
     * 添加列表项     * @param item
     */
    public void addItem(CommentBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_comment_item, parent, false);
        return new BookCommentAdapter.BookCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookCommentAdapter.BookCommentViewHolder) {
            final BookCommentAdapter.BookCommentViewHolder viewHolder = (BookCommentAdapter.BookCommentViewHolder) holder;
            if (datas.get(position) != null) {
                Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCommentBy().getAvator().getFileName()).into(viewHolder.img_author);
                viewHolder.tv_author.setText(datas.get(position).getCommentBy().getAlias());
                viewHolder.tv_comment.setText(datas.get(position).getComment());
                viewHolder.tv_time.setText(DateUtil.getDateToString(datas.get(position).getCreateOn(),"yyyy-MM-dd HH:mm:ss"));
                Log.v("uuuuuuuuuuuu", "----" + Urls.HOST_GETFILE + "?name=" + datas.get(position));
            }
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookCommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_author;
        TextView tv_author,tv_comment,tv_time;

        public BookCommentViewHolder(final View itemView) {
            super(itemView);
            img_author = (CircleImageView) itemView.findViewById(R.id.img_author);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }

    }

}
