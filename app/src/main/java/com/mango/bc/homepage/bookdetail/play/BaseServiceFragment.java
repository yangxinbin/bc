package com.mango.bc.homepage.bookdetail.play;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hwangjr.rxbus.RxBus;
import com.mango.bc.homepage.bookdetail.play.preference.Preferences;
import com.mango.bc.homepage.bookdetail.play.service.PlayService;
import com.mango.bc.homepage.bookdetail.play.utils.PermissionReq;
import com.mango.bc.homepage.bookdetail.play.utils.ViewBinder;


/**
 * 基类<br>
 * Created by wcy on 2015/11/26.
 */
public abstract class BaseServiceFragment extends Fragment {
    protected Handler handler;
    protected PlayService playService;
    private ServiceConnection serviceConnection;
    private ProgressDialog progressDialog;

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
    public void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }


    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlayService.class);
        serviceConnection = new PlayServiceConnection();
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


    public void cancelProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}
