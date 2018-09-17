package com.mango.bc.view.likeview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mango.bc.R;

/**
 * Created by admin on 2018/9/17.
 */

public class PraiseView extends FrameLayout implements Checkable {//同样继承Checkable
    protected OnPraisCheckedListener praiseCheckedListener;
    protected CheckedImageView imageView; //点赞图片
    protected TextView textView; //+1
    protected int padding;

    public PraiseView(Context context) {
        super(context);
        initalize();
    }

    public PraiseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initalize();
    }

    protected void initalize() {
        setClickable(true);
        imageView = new CheckedImageView(getContext());
        imageView.setImageResource(R.drawable.praise_selector);
        FrameLayout.LayoutParams flp = new LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(imageView, flp);

        textView = new TextView(getContext());
        textView.setTextSize(10);
        textView.setText("+1");
        textView.setTextColor(Color.parseColor("#A24040"));
        textView.setGravity(Gravity.CENTER);
        addView(textView, flp);
        textView.setVisibility(View.GONE);
    }

    @Override
    public boolean performClick() {
        checkChange();
        return super.performClick();
    }

    @Override
    public void toggle() {
        checkChange();
    }

    public void setChecked(boolean isCheacked) {
        imageView.setChecked(isCheacked);
    }

    public void checkChange() {//点击切换状态
        if (imageView.isChecked) {
            imageView.setChecked(false);
        } else {
            imageView.setChecked(true);
            textView.setVisibility(View.VISIBLE);
            //放大动画
            AnimHelper.with(new PulseAnimator()).duration(1000).playOn(imageView);
            //飘 “+1”动画
            AnimHelper.with(new SlideOutUpAnimator()).duration(1000).playOn(textView);
        }
        //调用点赞事件
        if (praiseCheckedListener != null) {
            praiseCheckedListener.onPraisChecked(imageView.isChecked);
        }
    }

    public boolean isChecked() {
        return imageView.isChecked;
    }

    public void setOnPraisCheckedListener(OnPraisCheckedListener praiseCheckedListener) {
        this.praiseCheckedListener = praiseCheckedListener;
    }

    public interface OnPraisCheckedListener {
        void onPraisChecked(boolean isChecked);
    }
    /**
     *上划消失（飘+1）
     */
    public class SlideOutUpAnimator extends BaseViewAnimator {
        @Override
        public void prepare(View target) {
            ViewGroup parent = (ViewGroup)target.getParent();
            getAnimatorAgent().playTogether(
                    ObjectAnimator.ofFloat(target, "alpha", 1, 0),
                    ObjectAnimator.ofFloat(target,"translationY",0,-parent.getHeight()/2)
            );
        }
    }

    /**
     *放大效果
     */
    public class PulseAnimator extends BaseViewAnimator {
        @Override
        public void prepare(View target) {
            getAnimatorAgent().playTogether(
                    ObjectAnimator.ofFloat(target, "scaleY", 1, 1.2f, 1),
                    ObjectAnimator.ofFloat(target, "scaleX", 1, 1.2f, 1)
            );
        }
    }
}
