package com.mango.bc.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mango.bc.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/5/24.
 */

public class GirdDownAdapter extends BaseAdapter {
    private Context context;
    private List<String> stringList;
    private int checkItemPosition = -1;
    private HashMap<String, String> map;
    private HashMap<Integer, Boolean> mapposition;

    public GirdDownAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }


    public GirdDownAdapter(Context context, HashMap<String, String> hashMap) {
        this.context = context;
        this.map = hashMap;
    }


    public void setCheckItem(int position) {
        checkItemPosition = position;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View containView, ViewGroup viewGroup) {//
        ViewHolder viewHolder;
        if (containView != null) {
            viewHolder = (ViewHolder) containView.getTag();
        } else {
            containView = LayoutInflater.from(context).inflate(R.layout.item_default_down, null);
            viewHolder = new ViewHolder();
            viewHolder.tv = containView.findViewById(R.id.tv);
            viewHolder.co = containView.findViewById(R.id.con);
            containView.setTag(viewHolder);
        }
        viewHolder.co.setBackgroundResource(R.drawable.gray_circle);
        viewHolder.tv.setTextColor(context.getResources().getColor(R.color.gray_3));
        fillValue(position, viewHolder);
        return containView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.tv.setText(stringList.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.co.setBackgroundResource(R.drawable.blue_circle);
                viewHolder.tv.setTextColor(context.getResources().getColor(R.color.blue));
            } else {
                viewHolder.co.setBackgroundResource(R.drawable.gray_circle);
                viewHolder.tv.setTextColor(context.getResources().getColor(R.color.gray_3));
            }
        }
    }

    class ViewHolder {
        TextView tv;
        LinearLayout co;
    }
}
