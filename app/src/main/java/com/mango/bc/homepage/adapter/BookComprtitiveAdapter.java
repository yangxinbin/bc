package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by admin on 2018/5/11.
 */

public class BookComprtitiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isVip;
    private SPUtils spUtils;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;//自注册的接口给调用者用于点击逻辑
    private List<BookBean> mData = new ArrayList<>();
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_ITEM = 1;
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public void setmDate(List<BookBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    public void reMove() {
        List<BookBean> m = new ArrayList<BookBean>();
        this.mData = m;
        this.notifyDataSetChanged();
    }

    /**
     * 添加列表项     * @param item
     */
    public void addItem(BookBean bean) {
        if (mData != null) {
            mData.add(bean);
        }
        this.notifyDataSetChanged();
    }


    public BookComprtitiveAdapter(Context context) {
        spUtils = SPUtils.getInstance("bc", context);
        UserBean userBean = MineJsonUtils.readUserBean(spUtils.getString("auth", ""));
        if (userBean != null)
            isVip = userBean.isVip();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return new BookComprtitiveAdapter.HeadViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_head, parent, false));
            case TYPE_ITEM:
                return new BookComprtitiveAdapter.ItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.course_recycler_item, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //Toast.makeText(context, "onAttachedToRecyclerView", Toast.LENGTH_SHORT).show();
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            Toast.makeText(context, "manager instanceof GridLayoutManager", Toast.LENGTH_SHORT).show();
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_HEAD:
                        case TYPE_ITEM:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeadViewHolder) {
            if (((HeadViewHolder) holder) != null && mData.get(position) != null) {
                ((HeadViewHolder) holder).tv_head_title.setText(mData.get(position).getTitle());
                ((HeadViewHolder) holder).tv_head_detail.setText(mData.get(position).getSubtitle());
                ((HeadViewHolder) holder).tv_head_bc.setText(mData.get(position).getPrice() + "PPG");
                if (mData.get(position).getCover() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + mData.get(position).getCover().getFileName()).into(((HeadViewHolder) holder).img_head_book);
                if (chechState(mData.get(position).getId())) {//拿书id遍历判断
                    ((HeadViewHolder) holder).tv_head_stage.setText("播放");//是领取
                    ((HeadViewHolder) holder).tv_head_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onPlayClick(((HeadViewHolder) holder).tv_head_stage, position);
                        }
                    });
                    ((HeadViewHolder) holder).book_head_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemPlayClick(((HeadViewHolder) holder).book_head_item, position);
                        }
                    });
                } else {
                    if (isVip) {
                        ((HeadViewHolder) holder).tv_head_stage.setText("免费领取");
                        ((HeadViewHolder) holder).tv_head_stage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onHeadVipGetClick(((HeadViewHolder) holder).tv_head_stage, position);
                            }
                        });
                        ((HeadViewHolder) holder).book_head_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onItemVipGetClick(((HeadViewHolder) holder).book_head_item, position);
                            }
                        });
                    } else {
                        ((HeadViewHolder) holder).tv_head_stage.setText("购买");
                        ((HeadViewHolder) holder).tv_head_stage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onHeadGetClick(((HeadViewHolder) holder).tv_head_stage, position);
                            }
                        });
                        ((HeadViewHolder) holder).book_head_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onItemGetClick(((HeadViewHolder) holder).book_head_item, position);
                            }
                        });
                    }

                }
            }
        } else if (holder instanceof ItemViewHolder) {
            if (((ItemViewHolder) holder) != null && mData.get(position) != null) {
                ((ItemViewHolder) holder).tv_title.setText(mData.get(position).getTitle());
                ((ItemViewHolder) holder).tv_detail.setText(mData.get(position).getSubtitle());
                ((ItemViewHolder) holder).tv_time.setText("时长：" + secToTime(mData.get(position).getChapters().get(0).getDuration()));
                ((ItemViewHolder) holder).tv_buy.setText("已售：" + (mData.get(position).getSold()+mData.get(position).getSoldPlus()));
                if (mData.get(position).getCover() != null)
                    Glide.with(context).load(Urls.HOST_GETFILE + "?name=" + mData.get(position).getCover().getFileName()).into(((ItemViewHolder) holder).img_book);
                if (chechState(mData.get(position).getId())) {//拿书id遍历判断
                    ((ItemViewHolder) holder).tv_stage.setText("播放");//是领取
                    ((ItemViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onPlayClick(((ItemViewHolder) holder).tv_stage, position);
                        }
                    });
                    ((ItemViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemPlayClick(((ItemViewHolder) holder).tv_stage, position);
                        }
                    });
                } else {
                    if (isVip) {
                        ((ItemViewHolder) holder).tv_stage.setText("免费领取");//否领取
                        ((ItemViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onVipGetClick(((ItemViewHolder) holder).tv_stage, position);
                            }
                        });
                        ((ItemViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onItemVipGetClick(((ItemViewHolder) holder).tv_stage, position);
                            }
                        });
                    } else {
                        ((ItemViewHolder) holder).tv_stage.setText(mData.get(position).getPrice() + "PPG");//否领取
                        ((ItemViewHolder) holder).tv_stage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onGetClick(((ItemViewHolder) holder).tv_stage, position);
                            }
                        });
                        ((ItemViewHolder) holder).book_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickLitener.onItemGetClick(((ItemViewHolder) holder).tv_stage, position);
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
        return mData.size();
    }

    public void setOnItemClickLitener(BookComprtitiveAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemPlayClick(View view, int position);

        void onItemGetClick(View view, int position);

        void onPlayClick(View view, int position);

        void onGetClick(View view, int position);

        void onHeadGetClick(View view, int position);

        void onItemVipGetClick(View view, int position);

        void onVipGetClick(View view, int position);

        void onHeadVipGetClick(View view, int position);

    }


    public BookBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    /*
    * 头部的viewholder
    * */
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView tv_head_title, tv_head_detail, tv_head_bc, tv_head_stage;
        RoundImageView img_head_book;
        LinearLayout book_head_item;

        public HeadViewHolder(View itemView) {
            super(itemView);
            tv_head_title = (TextView) itemView.findViewById(R.id.tv_head_title);
            tv_head_detail = (TextView) itemView.findViewById(R.id.tv_head_detail);
            tv_head_bc = (TextView) itemView.findViewById(R.id.tv_head_bc);
            tv_head_stage = (TextView) itemView.findViewById(R.id.tv_head_stage);
            img_head_book = (RoundImageView) itemView.findViewById(R.id.img_head_book);
            book_head_item = (LinearLayout) itemView.findViewById(R.id.book_head_item);
        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_detail, tv_time, tv_buy, tv_stage;
        RoundImageView img_book;
        LinearLayout book_item;

        public ItemViewHolder(View v) {
            super(v);
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
