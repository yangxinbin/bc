package com.mango.bc.homepage.collage.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.Urls;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/10/11.
 */

public class CollageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CollageBean> datas = new ArrayList<>();

    public CollageAdapter(Context context) {
        this.context = context;
    }

    public CollageAdapter(List<CollageBean> datas) {
        this.datas = datas;
    }

    private CollageAdapter.OnItemClickLitener mOnItemClickLitener;

    public CollageAdapter() {
    }

    public void setmDate(List<CollageBean> data) {
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<CollageBean> m = new ArrayList<CollageBean>();
        this.datas = m;
        this.notifyDataSetChanged();
    }

    /**
     * 添加列表项     * @param item
     */
    public void addItem(CollageBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
        this.notifyDataSetChanged();
    }

    public CollageBean getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    public void setOnItemClickLitener(CollageAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collage_item, parent, false);
        return new CollageAdapter.CollageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CollageAdapter.CollageViewHolder) {
            final CollageAdapter.CollageViewHolder viewHolder = (CollageAdapter.CollageViewHolder) holder;
            if (datas.get(position) == null)
                return;
            viewHolder.tv_collage_name.setText(datas.get(position).getBookTitle());
            if (datas.get(position).getStatus().equals("started")) {
                viewHolder.tv_collage_state.setText("拼团中");
            } else if (datas.get(position).getStatus().equals("finished")) {
                viewHolder.tv_collage_state.setText("完成拼团");
            } else if (datas.get(position).getStatus().equals("expired")) {
                viewHolder.tv_collage_state.setText("拼团失败");
            }
            viewHolder.tv_collage_price_after.setText(datas.get(position).getPrice() + "积分");
            viewHolder.tv_collage_price_before.setText(datas.get(position).getBookPrice() + "积分");
            viewHolder.tv_collage_price_before.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            Log.v("ddddddddddddd", (datas.get(position).getTimestamp() + (1000 * 60 * 60 * 24) + "==" + System.currentTimeMillis()) + "==" + ((datas.get(position).getTimestamp() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis()));
            viewHolder.tv_collage_time.setText("剩余" + DateUtil.getMToHM(((datas.get(position).getTimestamp() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis())));
            if (datas.get(position).getType().equals("three")) {
                viewHolder.tv_collage_num.setText("3人拼团");
            } else if (datas.get(position).getType().equals("two")) {
                viewHolder.tv_collage_num.setText("2人拼团");
            }
            if (datas.get(position).getBookCover() != null)
                Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getBookCover().getFileName()).into(((CollageAdapter.CollageViewHolder) holder).img_collage_book);
            if (datas.get(position).getMembers() != null) {
                viewHolder.l_member.removeAllViews();
                for (int i = 0; i < datas.get(position).getMembers().size(); i++) {
                    CircleImageView circleImageView = new CircleImageView(context);
                    if (datas.get(position).getMembers().get(i).getAvator() != null) {
                        Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getMembers().get(i).getAvator().getFileName()).into(circleImageView);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(context, 40), dip2px(context, 40));//两个400分别为添加图片的大小
                        params.setMargins(0,0,dip2px(context,5),0);
                        circleImageView.setLayoutParams(params);
                    }
                    viewHolder.l_member.addView(circleImageView, i);
                }
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CollageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_collage_name, tv_collage_state, tv_collage_price_after, tv_collage_price_before, tv_collage_time, tv_collage_num;
        ImageView img_collage_book;
        LinearLayout l_item, l_member;

        public CollageViewHolder(final View itemView) {
            super(itemView);
            tv_collage_name = (TextView) itemView.findViewById(R.id.tv_collage_name);
            tv_collage_state = (TextView) itemView.findViewById(R.id.tv_collage_state);
            tv_collage_price_after = (TextView) itemView.findViewById(R.id.tv_collage_price_after);
            tv_collage_price_before = (TextView) itemView.findViewById(R.id.tv_collage_price_before);
            tv_collage_time = (TextView) itemView.findViewById(R.id.tv_collage_time);
            tv_collage_num = (TextView) itemView.findViewById(R.id.tv_collage_num);

            img_collage_book = (ImageView) itemView.findViewById(R.id.img_collage_book);
            l_member = (LinearLayout) itemView.findViewById(R.id.l_member);
            l_item = (LinearLayout) itemView.findViewById(R.id.l_item);
            l_item.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.l_item:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(l_item, getAdapterPosition());
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
