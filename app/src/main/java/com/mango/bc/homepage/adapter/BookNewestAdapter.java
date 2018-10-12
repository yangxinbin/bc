package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.R;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/9/4.
 */

public class BookNewestAdapter extends RecyclerView.Adapter {
    private boolean isVip = false;
    private SPUtils spUtils;
    private Context context;
    private BookNewestAdapter.OnItemClickLitener mOnItemClickLitener;

    private List<BookBean> datas = new ArrayList<>();

    public BookNewestAdapter(List<BookBean> datas) {
        this.datas = datas;
    }


    public BookNewestAdapter(Context context) {
        spUtils = SPUtils.getInstance("bc", context);
        this.context = context;
    }

    public BookNewestAdapter() {
    }

    public void setmDate(List<BookBean> data) {
        UserBean userBean = MineJsonUtils.readUserBean(spUtils.getString("auth", ""));
        Log.v("lllllllll", "===userBean.isVip()=" + userBean.isVip());
        if (userBean != null)
            isVip = userBean.isVip();
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<BookBean> m = new ArrayList<BookBean>();
        this.datas = m;
        this.notifyDataSetChanged();
    }

    public BookBean getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    /**
     * 添加列表项     * @param item
     */
    public void addItem(BookBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
        this.notifyDataSetChanged();
    }

    public void setOnItemClickLitener(BookNewestAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemPlayClick(View view, int position);

        void onItemGetClick(View view, int position);

        void onPlayClick(View view, int position);

        void onGetClick(View view, int position);

        void onItemVipGetClick(View view, int position);

        void onVipGetClick(View view, int position);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recycler_item, parent, false);
        return new BookNewestAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BookNewestAdapter.BookViewHolder) {
            if (((BookNewestAdapter.BookViewHolder) holder) != null && datas.get(position) != null) {
                ((BookNewestAdapter.BookViewHolder) holder).tv_title.setText(datas.get(position).getTitle());
                ((BookNewestAdapter.BookViewHolder) holder).tv_detail.setText(datas.get(position).getSubtitle());
                ((BookNewestAdapter.BookViewHolder) holder).tv_time.setText("时长：" + secToTime(datas.get(position).getChapters().get(0).getDuration()));
                ((BookNewestAdapter.BookViewHolder) holder).tv_buy.setText("已售：" + datas.get(position).getSold());
                if (datas.get(position) == null)
                    return;
                if (datas.get(position).getCover() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + datas.get(position).getCover().getFileName()).into(((BookNewestAdapter.BookViewHolder) holder).img_book);

                if (chechState(datas.get(position).getId())) {//拿书id遍历判断
                    ((BookViewHolder) holder).tv_stage.setText("播放");//是领取
                    ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onPlayClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((BookNewestAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemPlayClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                        }
                    });
                } else {
                    if (isVip) {
                        ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setText("免费领取");//vip领取
                        ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onVipGetClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                            }
                        });
                        ((BookNewestAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onItemVipGetClick(((BookNewestAdapter.BookViewHolder) holder).book_item, position);
                            }
                        });
                    } else {
                        ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setText(datas.get(position).getPrice() + "积分");//否领取
                        ((BookNewestAdapter.BookViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onGetClick(((BookNewestAdapter.BookViewHolder) holder).tv_stage, position);
                            }
                        });
                        ((BookNewestAdapter.BookViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onItemGetClick(((BookNewestAdapter.BookViewHolder) holder).book_item, position);
                            }
                        });
                    }

                }
            }
        }
    }

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

    private boolean chechState(String bookId) {
        if (spUtils != null) {
            String data = spUtils.getString("allMyBook", "");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> list = gson.fromJson(data, listType);
            if (list == null)
                return false;
            if (list.contains(bookId)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_detail, tv_time, tv_buy, tv_stage;
        RoundImageView img_book;
        LinearLayout book_item;

        public BookViewHolder(final View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_buy = (TextView) itemView.findViewById(R.id.tv_buy);
            tv_stage = (TextView) itemView.findViewById(R.id.tv_stage);
            img_book = (RoundImageView) itemView.findViewById(R.id.img_book);
            book_item = (LinearLayout) itemView.findViewById(R.id.book_item);

        }
    }

}
