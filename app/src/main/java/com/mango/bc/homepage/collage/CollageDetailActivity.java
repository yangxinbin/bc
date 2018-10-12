package com.mango.bc.homepage.collage;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.collage.bean.CollageBean;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CollageDetailActivity extends BaseActivity {

    @Bind(R.id.img_collage_book)
    RoundImageView imgCollageBook;
    @Bind(R.id.tv_collage_name)
    TextView tvCollageName;
    @Bind(R.id.tv_collage_num)
    TextView tvCollageNum;
    @Bind(R.id.l_member)
    LinearLayout lMember;
    @Bind(R.id.tv_collage_price_after)
    TextView tvCollagePriceAfter;
    @Bind(R.id.tv_collage_price_before)
    TextView tvCollagePriceBefore;
    @Bind(R.id.tv_collage_time)
    Button tvCollageTime;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.l_mes)
    LinearLayout lMes;
    @Bind(R.id.tv_collage_mes)
    TextView tvCollageMes;
    @Bind(R.id.tv_collage_delete)
    Button tvCollageDelete;
    private Long leftTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void CollageBeanEventBus(CollageBean collageBean) { //首页进来 需要判断 是否可以播放 是否要钱购买
        if (collageBean == null)
            return;
        if (collageBean.getBookCover() != null)
            Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + collageBean.getBookCover().getFileName()).into(imgCollageBook);
        tvCollageName.setText(collageBean.getBookTitle());
        if (collageBean.getType().equals("three")) {
            tvCollageNum.setText("3人拼团");
        } else if (collageBean.getType().equals("two")) {
            tvCollageNum.setText("2人拼团");
        }
        tvCollagePriceAfter.setText(collageBean.getPrice() + "积分");
        tvCollagePriceBefore.setText(collageBean.getBookPrice() + "积分");
        tvCollagePriceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        leftTime = ((collageBean.getTimestamp() + (1000 * 60 * 60 * 23)) - System.currentTimeMillis()) / 1000;
        switch (getIntent().getIntExtra("status", 0)) {
            case 0:
                handler.post(update_thread);
                tvStatus.setVisibility(View.GONE);
                break;
            case 1:
                tvCollageTime.setVisibility(View.GONE);
                lMes.setVisibility(View.GONE);
                tvCollageDelete.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText("拼团成功");
                tvCollageMes.setText("拼团成功，已放入书架");
                break;
            case 2:
                tvCollageTime.setVisibility(View.GONE);
                lMes.setVisibility(View.GONE);
                tvCollageDelete.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText("拼团失败");
                tvCollageMes.setText("拼团失败后，我们将立即退款到您的钱包中");
                break;
        }
        //tvCollageTime.setText("邀请好友参团 " + DateUtil.getMToHMS(((collageBean.getTimestamp() + (1000 * 60 * 60 * 24)) - System.currentTimeMillis())) + " 后结束");
        if (collageBean.getMembers() != null) {
            lMember.removeAllViews();
            for (int i = 0; i < collageBean.getMembers().size(); i++) {
                CircleImageView circleImageView = new CircleImageView(this);
                if (collageBean.getMembers().get(i).getAvator() != null) {
                    Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + collageBean.getMembers().get(i).getAvator().getFileName()).into(circleImageView);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(this, 40), dip2px(this, 40));//两个400分别为添加图片的大小
                    params.setMargins(0, 0, dip2px(this, 5), 0);
                    circleImageView.setLayoutParams(params);
                }
                lMember.addView(circleImageView, i);
            }
        }
        tvName.setText(collageBean.getBookTitle());
        tvTime.setText(DateUtil.getDateToString(collageBean.getTimestamp(), "yyyy-MM-dd HH:mm:ss"));
        EventBus.getDefault().removeStickyEvent(CollageBean.class);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    Handler handler = new Handler();
    Runnable update_thread = new Runnable() {
        @Override
        public void run() {
            leftTime--;
            if (leftTime > 0) {
                //倒计时效果展示
                String formatLongToTimeStr = formatLongToTimeStr(leftTime);
                tvCollageTime.setText(formatLongToTimeStr);
                //每一秒执行一次
                handler.postDelayed(this, 1000);
            } else {//倒计时结束
                //处理业务流程

                //发送消息，结束倒计时
               /* Message message = new Message();
                message.what = 1;
                handlerStop.sendMessage(message);*/
            }
        }
    };

    public String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue();
        if (second > 60) {
            minute = second / 60;   //取整
            second = second % 60;   //取余
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = "";
        if (hour <= 9) {
            if (minute <= 9) {
                strtime = "邀请好友参团 0" + hour + ":0" + minute + ":" + second + " 后结束";
            } else {
                strtime = "邀请好友参团 0" + hour + ":" + minute + ":" + second + " 后结束";
            }
        } else {
            if (minute <= 9) {
                strtime = "邀请好友参团 " + hour + ":0" + minute + ":" + second + " 后结束";
            } else {
                strtime = "邀请好友参团 " + hour + ":" + minute + ":" + second + " 后结束";

            }
        }
        return strtime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(update_thread);
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @OnClick({R.id.imageView_back, R.id.tv_collage_time, R.id.tv_collage_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
/*            case R.id.tv_collage_buy:
                break;*/
            case R.id.tv_collage_time:
                break;
            case R.id.tv_collage_delete:
                break;
        }
    }
}
