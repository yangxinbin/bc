package com.mango.bc.homepage.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.SingleCollageTypeAdapter;
import com.mango.bc.homepage.bean.CollageTypeBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.RoundImageView;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.activity.RechargeActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CollageBuyBookActivity extends BaseActivity {

    @Bind(R.id.img_book)
    RoundImageView imgBook;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_ppg_before)
    TextView tvPpgBefore;
    @Bind(R.id.tv_ppg_after)
    TextView tvPpgAfter;
    @Bind(R.id.tv_need_ppg)
    TextView tvNeedPpg;
    @Bind(R.id.tv_unneed_ppg)
    TextView tvUnneedPpg;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.recyclerView_what)
    RecyclerView recyclerViewWhat;
    private List<CollageTypeBean> list;
    private SingleCollageTypeAdapter adapterWhat;
    private String bookId;
    private SPUtils spUtils;
    private String typeCollage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_buy_book);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void BookBeanEventBus(BookBean bookDetailBean) { //首页进来 需要判断 是否可以播放 是否要钱购买
        if (bookDetailBean == null) {
            return;
        }
        if (bookDetailBean.getAuthor() != null) {
            if (bookDetailBean.getAuthor().getPhoto() != null)
                Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + bookDetailBean.getAuthor().getPhoto().getFileName()).into(imgBook);
        }
        bookId = bookDetailBean.getId();
        tvTitle.setText(bookDetailBean.getTitle());
        tvPpgBefore.setText(bookDetailBean.getPrice() + "PPG");
        tvUnneedPpg.setText("原价:" + bookDetailBean.getPrice() + "PPG");
        tvPpgBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvUnneedPpg.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvPpgAfter.setText(bookDetailBean.getGroupBuy2Price() + "PPG");
        tvNeedPpg.setText("实付款:" + bookDetailBean.getGroupBuy2Price() + "PPG  ");
        typeCollage = "two";
        initDate(bookDetailBean);
    }

    private void initDate(BookBean bookDetailBean) {
        list = new ArrayList<>();
        if (bookDetailBean == null)
            return;
        list.add(new CollageTypeBean("2人拼团", bookDetailBean.getGroupBuy2Price() + ""));
        list.add(new CollageTypeBean("3人拼团", bookDetailBean.getGroupBuy3Price() + ""));
    }

    private void initView() {
        recyclerViewWhat.setHasFixedSize(true);
        recyclerViewWhat.setLayoutManager(new LinearLayoutManager(this));
        adapterWhat = new SingleCollageTypeAdapter(this, list);
        recyclerViewWhat.setAdapter(adapterWhat);

        adapterWhat.setOnItemClickLitener(new SingleCollageTypeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                adapterWhat.setSelection(position);
                tvPpgAfter.setText(adapterWhat.getItem(position).getPpg() + "PPG");
                tvNeedPpg.setText("实付款:" + adapterWhat.getItem(position).getPpg() + "PPG  ");
                if (adapterWhat.getItem(position).getNum().startsWith("2")) {
                    typeCollage = "two";
                } else {
                    typeCollage = "three";
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adapterWhat.setSelection(0);
    }

    @OnClick({R.id.imageView_back, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.tv_buy:
                showDailog("确认拼团购买", "");
                break;
        }
    }

    private void createGroup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("bookId", bookId);
                mapParams.put("type", typeCollage);
                HttpUtils.doPost(Urls.HOST_CREATEGROUP, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtils.showToast(CollageBuyBookActivity.this, "购买失败");
                                //showDailogOpen("请检查网络连接", "");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        final String s;
                        try {
                            s = response.body().string();
                            Log.v("ssssssssss", typeCollage + "===" + s);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (s.equals("LOW_BALANCE")) {
                                        showDailogOpen(getString(R.string.less_ppg), "");
                                    } else if (s.equals("ALREADY_BOUND_EXCEPTION")) {
                                        showDailogHaved("您正在拼团中", "");
                                    } else {
                                        AppUtils.showToast(CollageBuyBookActivity.this, "购买成功");
                                        loadUser();
                                    }

                                }
                            });
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(CollageBuyBookActivity.this, "购买失败");
                                }
                            });

                        }

                    }
                });
            }
        }).start();
    }

    private void loadUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("openId", spUtils.getString("openId", ""));
                HttpUtils.doPost(Urls.HOST_AUTH, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) {
                        final String string;
                        try {
                            string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spUtils.put("auth", string);
                                    UserBean userBean = MineJsonUtils.readUserBean(string);
                                    Log.v("lllllllll", "=aaaa==" + userBean.isVip());
                                    EventBus.getDefault().postSticky(userBean);//刷新钱包，我的。
                                    Intent intent = new Intent(CollageBuyBookActivity.this, CollageActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }

    private void showDailog(String s1, final String s2) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(s1)//设置对话框的标题
                //.setMessage(s2)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createGroup();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void showDailogOpen(String s1, final String s2) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(s1)//设置对话框的标题
                //.setMessage(s2)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CollageBuyBookActivity.this, RechargeActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void showDailogHaved(String s1, final String s2) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle(s1)//设置对话框的标题
                //.setMessage(s2)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CollageBuyBookActivity.this, CollageActivity.class);
                        startActivity(intent);
                        //finish();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
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
}
