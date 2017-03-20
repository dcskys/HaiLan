package com.dc.hailan.utils.picture;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dc.hailan.R;

import java.io.File;

/**
 * Created by dc on 2017/3/16.
 * Glide  图片框架  封装
 */
public class GlideUtil {

    /**
     * 常用加载
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImage(Context context, String url,ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder( R.drawable.cover_default).error( R.drawable.cover_default).into(iv);
    }

    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
    }

    public static void loadGifImage(Context context, String url, ImageView iv) {
        //设置 缓存策略 原始数据    asGif   如果不是 gif 加载 错误界面
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.cover_default).error(R.drawable.cover_default).into(iv);
    }


    /**
     * 加载圆形图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.drawable.ic_avatar_default).error(R.drawable.ic_avatar_default).transform(new GlideCircleTransform(context)).into(iv);
    }

    /**
     * 加载 圆角 图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.drawable.cover_default).error(R.drawable.cover_default).transform(new GlideRoundTransform(context,10)).into(iv);
    }


    /**
     * 加载 本地 文件    只能加载本地视屏，  在线不行
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);
    }


    /**
     * 加载本地资源
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }


}
