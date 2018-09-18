package com.mango.bc.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;


import com.mango.bc.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mzaiy on 2017/6/2.
 */

public class AppUtils {


    private static Dialog dialog_load;

    /**
     * 隐藏输入法
     *
     * @param context 上下文
     */
    @SuppressLint("NewApi")
    public static void hideInput(Context context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 格式化时间
     *
     * @param value int 值
     * @return 01
     */
    public static String format(int value) {
        String s = String.valueOf(value);
        if (s.length() == 1)
            s = "0" + s;
        return s;
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return 屏幕宽高[宽, 高]
     */
    @SuppressLint("NewApi")
    public static int[] screenWH(Context context) {
        int[] screenWH = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWH[0] = size.x;
        screenWH[1] = size.y;
        return screenWH;
    }

    /**
     * 抖动动画
     *
     * @param CycleTimes 动画重复的次数
     * @return 动画
     */
    public static Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 6, 0, 6);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    /**
     * 显示SnackBar
     *
     * @param view   视图
     * @param string 文本
     */
    public static void showSnackar(View view, String string) {
        //Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context,string,Toast.LENGTH_LONG).show();
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return 是否正确
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证手机号
     *
     * @param mobiles 手机号
     * @return true, false
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("[1][3578]\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 随机生成字符串(nonce)
     *
     * @return 随机字符串
     */
    public static String randomStr(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   //生成字符串从此序列中取
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {//32位
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取时间
     *
     * @return yyyy-MM-dd HH-mm-ss
     */
    public static String getTime() {
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dateFormat.format(timeMillis);
    }
    /**
     * 图片转换成base64
     * @param file
     * @return
     */
    @SuppressLint("NewApi")
    public static String GetImageStr(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.v("rrrrrrrrrr",""+Base64.encodeToString(bytes, Base64.DEFAULT));
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    public static List<Activity> activityList = new LinkedList<Activity>();
    /**
     * 添加到Activity容器中
     */
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 便利所有Activigty并finish
     */
    public static void finishActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }
    /**
     * 加载中的动画
     *
     * @param context 上下文
     * @return 将对话框对象直接返回
     */
    public static void createLoadDailog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_loading, null);
        dialog_load = new Dialog(context, R.style.dialog);
        dialog_load.setContentView(view);
        Window window = dialog_load.getWindow();
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置显示位置
        window.setGravity(Gravity.CENTER);
        //设置动画效果
        //window.setWindowAnimations(R.style.AnimBottom);
        dialog_load.setCanceledOnTouchOutside(true);
        dialog_load.show();
    }
    public static void dissmissLoadDailog(final Context context) {
        dialog_load.dismiss();
    }

}
