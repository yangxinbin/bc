package com.mango.bc.mine.adapter;

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

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.mine.activity.MemberActivity;
import com.mango.bc.mine.bean.MemberBean;
import com.mango.bc.util.Urls;

import java.util.List;
import java.util.Map;


public class MemberAdapter extends BaseAdapter {

    private List<MemberBean.UsersBean> beans;
    private Context mContext;
    private boolean isFlag = true;

    public MemberAdapter(MemberActivity mContext, List<MemberBean.UsersBean> list) {
        this.mContext = mContext;
        this.beans = list;
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


    @Override
    public View getView(final int position, View containView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (containView != null) {
            viewHolder = (ViewHolder) containView.getTag();
        } else {
            containView = LayoutInflater.from(mContext).inflate(R.layout.member_default_down, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = containView.findViewById(R.id.tv_name);
            viewHolder.tv_get = containView.findViewById(R.id.tv_get);
            viewHolder.imageVie_pic = containView.findViewById(R.id.imageVie_pic);
            viewHolder.co = containView.findViewById(R.id.con);
            containView.setTag(viewHolder);
        }
        if (beans.get(position).getAvator() != null) {
            Glide.with(mContext).load(Urls.HOST_GETFILE + "?name=" + beans.get(position).getAvator().getFileName()).into(viewHolder.imageVie_pic);
        } else {
            viewHolder.imageVie_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.head_pic2));
        }
        viewHolder.tv_name.setText(beans.get(position).getAlias());
        viewHolder.tv_get.setText(beans.get(position).getCommission());
        return containView;
    }
}

class ViewHolder {
    TextView tv_name, tv_get;
    LinearLayout co;
    ImageView imageVie_pic;
}

