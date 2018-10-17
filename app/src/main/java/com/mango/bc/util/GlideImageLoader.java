package com.mango.bc.util;

/**
 * Created by admin on 2018/5/11.
 */

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        if(path != null && !path.toString().isEmpty() && path.toString() != null) {
            Glide.with(context)
                    .load(path.toString())
                    //.placeholder(R.drawable.beginimg)//图片加载出来前，显示的图片
                    //.error(R.drawable.imgerror)//图片加载失败后，显示的图片
                    .into(imageView);
        }
    }

    @Override
    public ImageView createImageView(Context context) {
        //圆角
        return new RoundImageView(context);
    }
}