package com.mango.bc.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mango.bc.R;
import com.mango.bc.bookcase.net.presenter.MyBookPresenter;
import com.mango.bc.bookcase.net.presenter.MyBookPresenterImpl;
import com.mango.bc.bookcase.net.view.MyAllBookView;
import com.mango.bc.homepage.adapter.HomePageAdapter;
import com.mango.bc.homepage.bean.JumpToPlayDetailBean;
import com.mango.bc.homepage.bookdetail.bean.PlayBarBean;
import com.mango.bc.homepage.bookdetail.play.PlayActivity;
import com.mango.bc.homepage.bookdetail.play.executor.ControlPanel;
import com.mango.bc.homepage.bookdetail.play.service.AudioPlayer;
import com.mango.bc.homepage.net.bean.LoadStageBean;
import com.mango.bc.homepage.net.bean.RefreshStageBean;
import com.mango.bc.mine.bean.RefreshMemberBean;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.BaseHomeFragment;
import com.mango.bc.util.HttpUtils;
import com.mango.bc.util.NetUtil;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;
import com.mango.bc.wallet.bean.RefreshTaskBean;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/9/3.
 */

public class HomePageFragment extends BaseHomeFragment implements MyAllBookView {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.fl_play_bar)
    FrameLayout flPlayBar;
    private boolean isFirstEnter = true;
    private HomePageAdapter homePageAdapter;
    private int page = 0;
    private MyBookPresenter myBookPresenter;
    private ControlPanel controlPanel;
    private SPUtils spUtils;
    private boolean isFirstInit = true;
    private boolean isFirstRefresh = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.homepage, container, false);
        spUtils = SPUtils.getInstance("bc", getActivity());
        myBookPresenter = new MyBookPresenterImpl(this);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        //initView();
        refreshAndLoadMore();
        return view;
    }

    @Override
    protected void onServiceBound() {
        controlPanel = new ControlPanel(flPlayBar);
        AudioPlayer.get().addOnPlayEventListener(controlPanel);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void PlayBarBeanEventBus(PlayBarBean playBarBean) {
        if (playBarBean == null) {
            return;
        }
        Log.v("iiiiiiiiiiiiii", "---iiiihhhhiiiii---");
        if (!playBarBean.isShowBar()) {
            flPlayBar.setVisibility(View.GONE);//播放控件
            Log.v("iiiiiiiiiiiiii", "----h---");
        }
        EventBus.getDefault().removeStickyEvent(PlayBarBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void JumpToPlayDetailBeanEventBus(JumpToPlayDetailBean jumpToPlayDetailBean) {
        if (jumpToPlayDetailBean == null) {
            return;
        }
        Log.v("jjjjjjjjjjjjj", "---jjjjjjjjjjj---");
        if (jumpToPlayDetailBean.isIdJump()) {
            Intent intentDetail = new Intent(getActivity(), PlayActivity.class);
            intentDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentDetail.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intentDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intentDetail);
            Log.v("jjjjjjjjjjjjj", "----h---");
        }
        EventBus.getDefault().removeStickyEvent(JumpToPlayDetailBean.class);
    }

    /*private void parseIntent() {
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(Extras.EXTRA_NOTIFICATION)) {
            showPlayingFragment();
            getActivity().setIntent(new Intent());
        }
    }

    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = new PlayActivity();
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }

    private void hidePlayingFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }*/
    private void initView() {
        if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing())
            flPlayBar.setVisibility(View.VISIBLE);//播放控件
        homePageAdapter = new HomePageAdapter(getActivity());
        recycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycle.setAdapter(homePageAdapter);
    }

    private void refreshAndLoadMore() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                if (NetUtil.isNetConnect(getActivity())) {
                    myBookPresenter.visitBooks(getActivity(), 3, 0, false);//刷新书架的所有书(比其它先访问)
                } else {
                    myBookPresenter.visitBooks(getActivity(), 3, 0, true);//获取书架的所有书
                }
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadUser();
                        page = 0;
                        if (NetUtil.isNetConnect(getActivity())) {
                            if (isFirstRefresh) {
                                isFirstRefresh = false;
                                RefreshStageBean refreshStageBean = new RefreshStageBean(true, true, true, true, true, true);
                                Log.v("llllllll", "=====all--" + refreshStageBean.toString());
                                EventBus.getDefault().postSticky(refreshStageBean);
                            }
                            EventBus.getDefault().postSticky(new RefreshTaskBean(true));//刷新任务列表
                        } else {
                            AppUtils.showToast(getActivity(), getString(R.string.check_net));
                            RefreshStageBean refreshStageBean = new RefreshStageBean(false, false, false, false, false, false);
                            EventBus.getDefault().postSticky(refreshStageBean);
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
                        LoadStageBean loadStageBean = new LoadStageBean(++page);
                        EventBus.getDefault().postSticky(loadStageBean);
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
            refresh.autoRefresh();//进来自动刷新
        } else {
            //mAdapter.refresh(initData());
        }
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
                        try {
                            final String string2 = response.body().string();
                            if (getActivity() != null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        spUtils.put("auth", string2);//刷新用户信息
                                        Log.v("cccccccccc", "-----auth----");
                                        UserBean userBean = MineJsonUtils.readUserBean(string2);
                                        spUtils.put("parentId", userBean.getId());
                                        if (userBean != null) {
                                            Log.v("lllllllll", "=rrrr==" + userBean.isVip());
                                            EventBus.getDefault().postSticky(userBean);//刷新钱包 我的
                                        }
                                        EventBus.getDefault().postSticky(new RefreshMemberBean(true));//刷新达人
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void addSuccess(String s) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isFirstInit) {
                        Log.v("llllllll", "========================");
                        isFirstInit = false;
                        initView();
                    } else {
                        RefreshStageBean refreshStageBean = new RefreshStageBean(true, true, true, true, true, true);
                        Log.v("yyyyyyy", "=====all--" + refreshStageBean.toString());
                        EventBus.getDefault().postSticky(refreshStageBean);
                    }
                }
            });
    }

/*    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    // 处理fragment的返回事件
                    Log.v("iiiiiiiiiii", "-----------1");
                    exitDialog(true);
                    return true;
                }
                return false;
            }
        });
    }

    private void exitDialog(boolean b) {
        // 创建退出对话框
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.exit);
        // 设置对话框标题
        builder.setTitle("确定要退出吗?");
        // 设置对话框消息
        //builder.setMessage("确定要退出吗?");
        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        // 显示对话框
        builder.show();
    }*/

}
