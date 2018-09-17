package com.mango.bc.view.likeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by admin on 2018/9/17.
 */

@SuppressLint("AppCompatCustomView")
public class CheckedImageView extends ImageView implements Checkable { //通用点击变图

    protected boolean isChecked;//选中状态

    protected OnCheckedChangeListener onCheckedChangeListener;//状态改变事件监听


    public static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};


    public CheckedImageView(Context context) {
        super(context);
        initialize();
    }


    public CheckedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }


    private void initialize() {
        isChecked = false;
    }


    @Override


    public boolean isChecked() {
        return isChecked;
    }


    @Override


    public void setChecked(boolean isChecked) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked;
            refreshDrawableState();
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, isChecked);
            }
        }
    }

    @Override


    public void toggle() {//改变状态
        setChecked(!isChecked);
    }

    //初始DrawableState时候为它添加一个CHECKED_STATE，ImageView本身是没有这个状态的

    @Override

    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECKED_STATE_SET);
        }
        return states;
    }

    //当view的选中状态被改变的时候通知ImageView改变背景或内容，这个view会自动在drawable状态集中选择与当前状态匹配的图片

    @Override


    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int[] myDrawableState = getDrawableState();
            drawable.setState(myDrawableState);
            invalidate();
        }
    }

    //设置状态改变监听事件


    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    //当选中状态改变时监听接口触发该事件


    public static interface OnCheckedChangeListener {


        public void onCheckedChanged(CheckedImageView checkedImgeView, boolean isChecked);

    }
}