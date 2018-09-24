package com.mango.bc.homepage.bookdetail.play.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.mango.bc.homepage.bookdetail.bean.BookMusicDetailBean;
import com.mango.bc.homepage.bookdetail.play.preference.Preferences;
import com.mango.bc.homepage.bookdetail.play.utils.CoverLoader;
import com.mango.bc.homepage.bookdetail.play.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by hzwangchenyan on 2016/11/23.
 */
public class AppCache {
    private Context mContext;
    private final List<BookMusicDetailBean> mLocalMusicList = new ArrayList<>();
    private final List<Activity> mActivityStack = new ArrayList<>();

    private AppCache() {
    }

    private static class SingletonHolder {
        private static AppCache instance = new AppCache();
    }

    public static AppCache get() {
        return SingletonHolder.instance;
    }

    public void init(Application application) {
        mContext = application.getApplicationContext();
        Preferences.init(mContext);
        ScreenUtils.init(mContext);
        CrashHandler.getInstance().init();
        CoverLoader.get().init(mContext);
        application.registerActivityLifecycleCallbacks(new ActivityLifecycle());
    }

    public Context getContext() {
        return mContext;
    }

    public List<BookMusicDetailBean> getLocalMusicList() {
        return mLocalMusicList;
    }


    public void clearStack() {
        List<Activity> activityStack = mActivityStack;
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }


    private class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
        private static final String TAG = "Activity";

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(TAG, "onCreate: " + activity.getClass().getSimpleName());
            mActivityStack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onDestroy: " + activity.getClass().getSimpleName());
            mActivityStack.remove(activity);
        }
    }
}
