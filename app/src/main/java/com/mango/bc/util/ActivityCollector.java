package com.mango.bc.util;

import android.app.Activity;

import com.mango.bc.homepage.bookdetail.play.global.Notifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                Notifier.get().cancelAll();
                activity.finish();
            }
        }
    }
}
