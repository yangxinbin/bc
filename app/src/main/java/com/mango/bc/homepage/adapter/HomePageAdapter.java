package com.mango.bc.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mango.bc.R;


public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    //type
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_EXPERT = 1;
    public static final int TYPE_FREE = 2;
    public static final int TYPE_COMPETITIVE = 3;
    public static final int TYPE_NEWEST = 4;

    public HomePageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                return new HeadViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.index_recycler_head, parent, false));
            case TYPE_EXPERT:
                return new ExpertViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.index_recycler_expert, parent, false));
            case TYPE_FREE:
                return new FreeViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.index_recycler_free, parent, false));
            case TYPE_COMPETITIVE:
                return new CompetitiveViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.index_recycler_competitive, parent, false));
            case TYPE_NEWEST:
                return new NewestViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.index_recycler_newest, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {

        } else if (holder instanceof ExpertViewHolder) {

        } else if (holder instanceof FreeViewHolder) {

        } else if (holder instanceof CompetitiveViewHolder) {

        } else if (holder instanceof NewestViewHolder) {

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
                        case TYPE_EXPERT:
                        case TYPE_FREE:
                        case TYPE_COMPETITIVE:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 1) {
            return TYPE_EXPERT;
        } else if (position == 2) {
            return TYPE_FREE;
        } else if (position == 3) {
            return TYPE_COMPETITIVE;
        } else {
            return TYPE_NEWEST;
        }
    }

    /*
    * 头部的viewholder
    * */
    public class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    /*
    * 大咖课程的viewholder
    * */
    public class ExpertViewHolder extends RecyclerView.ViewHolder {
        //public PercentLinearLayout index_circler_show;
        //public CardView cardView;

        public ExpertViewHolder(View itemView) {
            super(itemView);
            //index_circler_show = (PercentLinearLayout) itemView.findViewById(R.id.index_recycler_circler_show);
            //cardView = (CardView) index_circler_show.findViewById(R.id.index_circleshow_cardview);
        }
    }


    /*
    * 免费试听的viewholder
    * */
    public class FreeViewHolder extends RecyclerView.ViewHolder {

        //ImageView imageview;

        public FreeViewHolder(View itemView) {
            super(itemView);
            //imageview = (ImageView) itemView.findViewById(R.id.recommend_second_image);
        }
    }

    /*
    * 精品课程的viewholder
    * */
    public class CompetitiveViewHolder extends RecyclerView.ViewHolder {

        //ImageView imageview;

        public CompetitiveViewHolder(View itemView) {
            super(itemView);
            //imageview = (ImageView) itemView.findViewById(R.id.recommend_second_image);
        }
    }
    /*
    * 最新上新的viewholder
    * */

    public class NewestViewHolder extends RecyclerView.ViewHolder {
        //LinearLayout list_item;
        //ImageView pic_1;
        //TextView txt_name, tx_money, txt_what, txt_place, tx_far;

        public NewestViewHolder(View itemView) {
            super(itemView);
/*            list_item = (LinearLayout) itemView.findViewById(R.id.list_item);
            pic_1 = (ImageView) itemView.findViewById(R.id.pic_1);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            tx_money = (TextView) itemView.findViewById(R.id.tx_money);
            txt_what = (TextView) itemView.findViewById(R.id.txt_what);
            txt_place = (TextView) itemView.findViewById(R.id.txt_place);
            tx_far = (TextView) itemView.findViewById(R.id.tx_far);*/

        }
    }
}
