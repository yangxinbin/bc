package com.mango.bc.wallet.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.util.DateUtil;
import com.mango.bc.wallet.bean.TransactionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class TransactionAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TransactionBean> datas = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public TransactionAdapter(Context context) {
        this.context = context;
    }

    public void setmDate(List<TransactionBean> data) {
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<TransactionBean> m = new ArrayList<TransactionBean>();
        this.datas = m;
        this.notifyDataSetChanged();
    }

    public TransactionBean getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    /**
     * 添加列表项     * @param item
     */
    public void addItem(TransactionBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionAdapter.Transaction(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TransactionAdapter.Transaction) {
            final TransactionAdapter.Transaction viewHolder = (TransactionAdapter.Transaction) holder;
            if (datas.get(position) != null) {
                if (datas.get(position).getType().equals("mission_reward")){
                    viewHolder.img_transaction.setImageResource(R.drawable.record_reward);
                    viewHolder.tv_title.setText("平台奖励");
                    viewHolder.tv_time.setText(DateUtil.getDateToString(datas.get(position).getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
                    viewHolder.tv_pp.setText("+"+datas.get(position).getPpCoin()+"PPG");
                    viewHolder.tv_pp.setTextColor(context.getResources().getColor(R.color.red));
                }else if (datas.get(position).getType().equals("shopping")){
                    viewHolder.img_transaction.setImageResource(R.drawable.record_buy);
                    viewHolder.tv_title.setText(datas.get(position).getMeta());
                    viewHolder.tv_time.setText(DateUtil.getDateToString(datas.get(position).getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
                    viewHolder.tv_pp.setText(datas.get(position).getPpCoin()+"PPG");
                    viewHolder.tv_pp.setTextColor(context.getResources().getColor(R.color.gray_3));
                }else if (datas.get(position).getType().equals("receive")){
                    viewHolder.img_transaction.setImageResource(R.drawable.record_transfer);
                    viewHolder.tv_title.setText("从"+datas.get(position).getMeta()+"获得");
                    viewHolder.tv_time.setText(DateUtil.getDateToString(datas.get(position).getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
                    viewHolder.tv_pp.setText("+"+datas.get(position).getPpCoin()+"PPG");
                    viewHolder.tv_pp.setTextColor(context.getResources().getColor(R.color.red));
                }else if (datas.get(position).getType().equals("transfer")){
                    viewHolder.img_transaction.setImageResource(R.drawable.record_transfer);
                    viewHolder.tv_title.setText("转账给"+datas.get(position).getMeta());
                    viewHolder.tv_time.setText(DateUtil.getDateToString(datas.get(position).getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
                    viewHolder.tv_pp.setText(datas.get(position).getPpCoin()+"PPG");
                    viewHolder.tv_pp.setTextColor(context.getResources().getColor(R.color.gray_3));
                }else if (datas.get(position).getType().equals("commission")){
                    viewHolder.img_transaction.setImageResource(R.drawable.record_money);
                    viewHolder.tv_title.setText("佣金所得");
                    viewHolder.tv_time.setText(DateUtil.getDateToString(datas.get(position).getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
                    viewHolder.tv_pp.setText(datas.get(position).getPpCoin()+"PPG");
                    viewHolder.tv_pp.setTextColor(context.getResources().getColor(R.color.red));
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Transaction extends RecyclerView.ViewHolder {
        ImageView img_transaction;
        TextView tv_title, tv_time, tv_pp;

        public Transaction(final View itemView) {
            super(itemView);
            img_transaction = (ImageView) itemView.findViewById(R.id.img_transaction);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_pp = (TextView) itemView.findViewById(R.id.tv_pp);
        }

    }

}
