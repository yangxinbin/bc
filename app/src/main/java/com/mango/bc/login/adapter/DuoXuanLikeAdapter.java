package com.mango.bc.login.adapter;

/**
 * Created by admin on 2018/5/29.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.login.LikeActivity;

import java.util.List;
import java.util.Map;


public class DuoXuanLikeAdapter extends BaseAdapter {

    private List<String> beans;
    private Context mContext;
    public int now = 0;//上一次选中的pos
    private boolean isFlag = true;
    Map<Integer, Boolean> gvChooseMap;

    public DuoXuanLikeAdapter(Context mContext, List<String> beans) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.beans = beans;
    }

    public DuoXuanLikeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 11/*beans.size()*/;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void setCheckItem(Map<Integer, Boolean> chooseMap) {
        this.gvChooseMap = chooseMap;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View containView, final ViewGroup parent) {
        ViewLikeHolder viewHolder;
        if (containView != null) {
            viewHolder = (ViewLikeHolder) containView.getTag();
        } else {
            containView = LayoutInflater.from(mContext).inflate(R.layout.itemduoxuan_default_like_down, null);
            viewHolder = new ViewLikeHolder();
            //viewHolder.tv = containView.findViewById(R.id.tv);
            viewHolder.co = containView.findViewById(R.id.con);
            containView.setTag(viewHolder);
        }
        switch (position) {
            case 0:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_1));
                break;
            case 1:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_2));
                break;
            case 2:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_3));
                break;
            case 3:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_4));
                break;
            case 4:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_5));
                break;
            case 5:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_6));
                break;
            case 6:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_7));
                break;
            case 7:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_8));
                break;
            case 8:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_9));
                break;
            case 9:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_10));
                break;
            case 10:
                viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_11));
                break;
        }
        //viewHolder.tv.setTextColor(mContext.getResources().getColor(R.drawable.black));
        fillValue(position, viewHolder, containView);
        return containView;
    }

    private void fillValue(int position, ViewLikeHolder viewHolder, View view) {
        //viewHolder.tv.setText(beans.get(position));
        for (Map.Entry<Integer, Boolean> entry : gvChooseMap.entrySet()) {
            if (position == entry.getKey() && entry.getValue()) {
                Log.v("ooooo", entry.getKey() + "**********" + gvChooseMap.size());
                view.setActivated(true);
                switch (position) {
                    case 0:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_1));
                        break;
                    case 1:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_2));
                        break;
                    case 2:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_3));
                        break;
                    case 3:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_4));
                        break;
                    case 4:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_5));
                        break;
                    case 5:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_6));
                        break;
                    case 6:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_7));
                        break;
                    case 7:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_8));
                        break;
                    case 8:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_9));
                        break;
                    case 9:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_10));
                        break;
                    case 10:
                        viewHolder.co.setImageDrawable(mContext.getResources().getDrawable(R.drawable.liked_11));
                        break;
                }
                //viewHolder.tv.setTextColor(mContext.getResources().getColor(R.drawable.yello));
            }
        }
    }
}

class ViewLikeHolder {
    //TextView tv;
    ImageView co;
}
