package com.mango.bc.mine.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.image2)
    ImageView image2;
    private Dialog dialog1, dialog2;
    private int width;
    private int heigh;
    /*    ImageView image1;
    ImageView image2;
    private Dialog dialog1,dialog2;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        //init();
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT<17){
            display.getSize(point);
        }else {
            display.getRealSize(point);
        }
        width = point.x;
        heigh = point.x;
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


    @OnClick({R.id.imageView_back, R.id.image1, R.id.image2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.image1:
                smallImgClick1(image1);
                break;
            case R.id.image2:
                smallImgClick2(image2);
                break;
            default:
                break;
        }
    }

    public void smallImgClick1(View v) {
        //有背景图
/*        final AlertDialog dialog1 = new AlertDialog.Builder(this).create();
        ImageView imgView = getView1();
        dialog1.setView(imgView);
        dialog1.show();*/


        // 全屏显示的方法
        final Dialog dialog1 = new Dialog(this, R.style.dialog);
        ImageView imgView1 = getView1();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,heigh);
        dialog1.setContentView(imgView1,layoutParams);
        dialog1.show();

        // 点击图片消失
        imgView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog1.dismiss();
            }
        });
    }

    public void smallImgClick2(View v) {
        //有背景图
/*        final AlertDialog dialog2 = new AlertDialog.Builder(this).create();
        ImageView imgView = getView2();
        dialog2.setView(imgView);
        dialog2.show();*/

        // 全屏显示的方法
        final Dialog dialog2 = new Dialog(this, R.style.dialog);
        ImageView imgView2 = getView2();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,heigh);
        dialog2.setContentView(imgView2,layoutParams);
        dialog2.show();

        // 点击图片消失
        imgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog2.dismiss();
            }
        });
    }

    private ImageView getView1() {
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.qr_code11);
        Drawable drawable = BitmapDrawable.createFromStream(is, null);
        imgView.setImageDrawable(drawable);

        return imgView;
    }

    private ImageView getView2() {
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.qr_code22);
        Drawable drawable = BitmapDrawable.createFromStream(is, null);
        imgView.setImageDrawable(drawable);

        return imgView;
    }
}
