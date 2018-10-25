package com.mango.bc.bookcase.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.bookcase.adapter.MyBookGirdAdapter;
import com.mango.bc.bookcase.bean.RefreshBookCaseBean;
import com.mango.bc.bookcase.net.bean.MyBookBean;
import com.mango.bc.bookcase.net.presenter.MyBookPresenter;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyFreeBookView;
import com.mango.bc.homepage.bookdetail.OtherBookDetailActivity;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.JsonUtil;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.CheckInBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/5.
 */

public class MyFreeFragment extends Fragment implements MyFreeBookView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.img_nobook)
    ImageView imgNobook;
    private MyBookGirdAdapter myBookGirdAdapter;
    private boolean isFirstEnter = true;
    private MyBookPresenter myBookPresenter;
    private final int TYPE = 2;//免费
    private int page = 0;
    private SPUtils spUtils;
    private String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_free, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
        myBookPresenter = new MyBookPresenterImpl(this);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        if (NetUtil.isNetConnect(getActivity())) {
            myBookPresenter.visitBooks(getActivity(), TYPE, page, false);
        } else {
            myBookPresenter.visitBooks(getActivity(), TYPE, page, true);
        }
        refreshAndLoadMore();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RefreshBookCaseBeanEventBus(RefreshBookCaseBean refreshBookCaseBean) {
        if (refreshBookCaseBean == null) {
            return;
        }
        if (refreshBookCaseBean.isRefreshFree()) {
            refresh.autoRefresh();
            Log.v("111111111111", "111111111111");
            refreshBookCaseBean.setRefreshFree(false);
            //EventBus.getDefault().removeStickyEvent(RefreshBookCaseBean.class);
        }
    }

    private void initView() {
        myBookGirdAdapter = new MyBookGirdAdapter(getActivity());
        recycle.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        recycle.setAdapter(myBookGirdAdapter);
        myBookGirdAdapter.setOnItemClickLitener(mOnClickListenner);
    }

    private MyBookGirdAdapter.OnItemClickLitener mOnClickListenner = new MyBookGirdAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            spUtils.put("isFree", true);
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().removeStickyEvent(BookBean.class);
            EventBus.getDefault().postSticky(myBookGirdAdapter.getItem(position));
            startActivity(intent);
        }

        @Override
        public void onDeleteClick(View view, int position) {
            //myBookGirdAdapter.deleteItem(position);
            showDailog("确定删除吗 ？", "",position);
        }

        @Override
        public void onButGiftClick(View view, int position) {
            Intent intent = new Intent(getActivity(), OtherBookDetailActivity.class);
            EventBus.getDefault().removeStickyEvent(BookBean.class);
            EventBus.getDefault().postSticky(myBookGirdAdapter.getItem(position));
            intent.putExtra("gift", true);
            startActivity(intent);
        }

    };
    private void showDailog(String s1, final String s2, final int position) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
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
                        deleteGift(position);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
    private void deleteGift(final int position) {
        if (myBookGirdAdapter.getItem(position).getBook() != null) {
            id = myBookGirdAdapter.getItem(position).getBook().getId();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HashMap<String, String> mapParams = new HashMap<String, String>();
                mapParams.clear();
                mapParams.put("authToken", spUtils.getString("authToken", ""));
                mapParams.put("bookId", id);
                HttpUtils.doPost(Urls.HOST_DELETEGIFT, mapParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.showToast(getActivity(), "删除失败");
                                }
                            });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            if (getActivity() != null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtils.showToast(getActivity(), "删除成功");
                                        myBookGirdAdapter.deleteItem(position);
                                    }
                                });
                        } catch (Exception e) {
                            if (getActivity() != null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtils.showToast(getActivity(), "删除失败");
                                    }
                                });
                        }
                    }
                });
            }
        }).start();
    }

    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        if (NetUtil.isNetConnect(getActivity())) {
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, false);
                        } else {
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, true);
                        }
                        refreshLayout.finishRefresh();
                    }
                }, 500);
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        if (NetUtil.isNetConnect(getActivity())) {
                            Log.v("yyyyyy", "-------isNetConnect-------");
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, false);
                        } else {
                            Log.v("yyyyyy", "-------isnoNetConnect-------");
                            myBookPresenter.visitBooks(getActivity(), TYPE, page, true);
                        }
                        refreshLayout.finishLoadMore();
                    }
                }, 500);
            }
        });
        refresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        refresh.setHeaderHeight(50);

        //触发自动刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            //refresh.autoRefresh();
        } else {
            //mAdapter.refresh(initData());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void addFreeBook(final List<MyBookBean> bookBeanList) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("doPostAll", page + "===" + bookBeanList.size());
                    if (bookBeanList == null || bookBeanList.size() == 0) {
                        if (page == 0) {
                            refresh.setVisibility(View.GONE);
                            imgNobook.setVisibility(View.VISIBLE);
                            return;
                        }
                        AppUtils.showToast(getActivity(), getString(R.string.date_over));
                        return;
                    } else {
                        refresh.setVisibility(View.VISIBLE);
                        imgNobook.setVisibility(View.GONE);
                    }
                    if (page == 0) {
                        myBookGirdAdapter.reMove();
                        myBookGirdAdapter.setmDate(bookBeanList);
                    } else {
                        //加载更多
                        for (int i = 0; i < bookBeanList.size(); i++) {
                            myBookGirdAdapter.addItem(bookBeanList.get(i));//addItem里面记得要notifyDataSetChanged 否则第一次加载不会显示数据
                        }
                    }
                }
            });
    }

    @Override
    public void addSuccess(String s) {

    }

    @Override
    public void addFail(String f) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppUtils.showToast(getActivity(), "免费课程书架请求失败");
                }
            });
    }
}