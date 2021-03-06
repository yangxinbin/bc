package com.mango.bc.homepage.bookdetail.play;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hwangjr.rxbus.RxBus;
import com.mango.bc.base.BaseServiceActivity;
import com.mango.bc.homepage.bookdetail.play.utils.PermissionReq;


/**
 * 基类<br>
 * Created by wcy on 2015/11/26.
 */
public abstract class BasePlayActivity extends BaseServiceActivity {
    protected Handler handler;
    //private PlayServiceConnection serviceConnection;
    //private PlayService playService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        //ViewBinder.bind(this, getView());
        RxBus.get().register(this);
        //bindService();
    }
/*    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        serviceConnection = new PlayServiceConnection();
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
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
    }*/
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
    public void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
