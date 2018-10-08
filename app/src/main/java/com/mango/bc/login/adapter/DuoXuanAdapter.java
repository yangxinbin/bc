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
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mango.bc.R;

import java.util.List;
import java.util.Map;


public class DuoXuanAdapter extends BaseAdapter {

    private List<String> beans;
    private Context mContext;
    public int now = 0;//上一次选中的pos
    private boolean isFlag = true;
    Map<Integer, Boolean> gvChooseMap;

    public DuoXuanAdapter(Context mContext, List<String> beans) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return beans.size();
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
        ViewHolder viewHolder;
        if (containView != null) {
            viewHolder = (ViewHolder) containView.getTag();
        } else {
            containView = LayoutInflater.from(mContext).inflate(R.layout.itemduoxuan_default_down, null);
            viewHolder = new ViewHolder();
            viewHolder.tv = containView.findViewById(R.id.tv);
            viewHolder.co = containView.findViewById(R.id.con);
            containView.setTag(viewHolder);
        }
        viewHolder.co.setBackgroundResource(R.color.common_gray);
        viewHolder.tv.setTextColor(mContext.getResources().getColor(R.color.black));
        fillValue(position, viewHolder,containView);
        return containView;
    }

    private void fillValue(int position, ViewHolder viewHolder,View view) {
        viewHolder.tv.setText(beans.get(position));
        for (Map.Entry<Integer, Boolean> entry : gvChooseMap.entrySet()){
            if (position == entry.getKey() && entry.getValue()){
                Log.v("ooooo",entry.getKey()+"**********"+gvChooseMap.size());
                view.setActivated(true);
                viewHolder.co.setBackgroundResource(R.color.common_yello);
                viewHolder.tv.setTextColor(mContext.getResources().getColor(R.color.yello));
            }
        }
        }
    }

class ViewHolder {
    TextView tv;
    LinearLayout co;
}
