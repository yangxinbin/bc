package com.mango.bc.mine.activity.point.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.Urls;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/10/11.
 */

public class PointAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MemberBean.UsersBean> datas = new ArrayList<>();

    public PointAdapter(Context context) {
        this.context = context;
    }

    public PointAdapter(List<MemberBean.UsersBean> datas) {
        this.datas = datas;
    }

    private PointAdapter.OnItemClickLitener mOnItemClickLitener;

    public PointAdapter() {
    }

    public void setmDate(List<MemberBean.UsersBean> data) {
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<MemberBean.UsersBean> m = new ArrayList<MemberBean.UsersBean>();
        this.datas = m;
        this.notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        datas.remove(position);
        this.notifyItemRemoved(position);
        this.notifyDataSetChanged();
    }
    /**
     * 添加列表项     * @param item
     */
    public void addItem(MemberBean.UsersBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
        this.notifyDataSetChanged();
    }

    public MemberBean.UsersBean getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    public void setOnItemClickLitener(PointAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onAcceptClick(View view, int position);

        void onRejectClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item, parent, false);
        return new PointAdapter.PoinrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PointAdapter.PoinrViewHolder) {
            final PointAdapter.PoinrViewHolder viewHolder = (PointAdapter.PoinrViewHolder) holder;
            if (datas.get(position) == null)
                return;
            if (datas.get(position).getAgencyInfo() != null) {
                viewHolder.tv_name.setText(datas.get(position).getAgencyInfo().getRealName());
                viewHolder.tv_phone.setText(datas.get(position).getAgencyInfo().getPhone());
                viewHolder.tv_company.setText(datas.get(position).getAgencyInfo().getCompany());
                viewHolder.tv_position.setText(datas.get(position).getAgencyInfo().getPosition());
                if (datas.get(position).getAgencyInfo().getStatus() == 1) {
                    viewHolder.end.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.end.setVisibility(View.GONE);
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

    class PoinrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name, tv_company, tv_phone, tv_position;
        Button re_bu, ac_bu;
        LinearLayout end;

        public PoinrViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_company = (TextView) itemView.findViewById(R.id.tv_company);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_position = (TextView) itemView.findViewById(R.id.tv_position);
            end = (LinearLayout) itemView.findViewById(R.id.end);

            re_bu = (Button) itemView.findViewById(R.id.re_bu);
            ac_bu = (Button) itemView.findViewById(R.id.ac_bu);
            re_bu.setOnClickListener(this);
            ac_bu.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ac_bu:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onAcceptClick(ac_bu, getAdapterPosition());
                    }
                    break;
                case R.id.re_bu:
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onRejectClick(re_bu, getAdapterPosition());
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
