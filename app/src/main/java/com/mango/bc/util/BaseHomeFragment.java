package com.mango.bc.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.homepage.bookdetail.play.service.PlayService;
import com.mango.bc.homepage.bookdetail.play.utils.PermissionReq;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class BaseHomeFragment extends Fragment implements BcActivity.FragmentBackListener {
    protected Disposable disposable;
    protected final String TAG = this.getClass().getSimpleName();
    //返回键点击间隔时间计算
    private long exitTime = 0;
    //捕捉返回键点击动作
    protected Handler handler;
    protected PlayService playService;
    private ServiceConnection serviceConnection;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        handler = new Handler(Looper.getMainLooper());
        bindService();
    }

    @Override
    public void onStart() {
        super.onStart();
        setListener();
    }

    protected void setListener() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackForward() {
        //和上次点击返回键的时间间隔
        long intervalTime = System.currentTimeMillis() - exitTime;
        if (intervalTime > 2000) {
            Toast.makeText(getActivity(), getString(R.string.exit_sure), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            ActivityCollector.finishAll();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //注册监听
        ((BcActivity) getActivity()).setBackListener(this);
        ((BcActivity) getActivity()).setInterception(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //取消监听
        ((BcActivity) getActivity()).setBackListener(null);
        ((BcActivity) getActivity()).setInterception(false);
    }

    @Override
    public void onDestroyView() {
        RxBus.get().unregister(this);
        super.onDestroyView();
        unsubscribe();
    }


    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlayService.class);
        serviceConnection = new BaseHomeFragment.PlayServiceConnection();
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playService = ((PlayService.PlayBinder) service).getService();
            onServiceBound();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getClass().getSimpleName(), "service disconnected");
        }
    }

    protected void onServiceBound() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void unsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
