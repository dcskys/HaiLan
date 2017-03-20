package com.dc.hailan.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.dc.hailan.R;

/**
 * Created by dc on 2017/3/17.*
 * api 版本的适配问题
 * setStatusBarColor  状态栏颜色    沉浸式状态栏
 */

public class StatusBarCompat {


    private static final int INVALID_VAL = -1;

    //屏蔽使用新的方法中的API时报错 警告问题。  一共有3种  参考 http://blog.csdn.net/pony_maggie/article/details/52115884
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (statusColor != INVALID_VAL) {
                //设置状态栏的颜色
                activity.getWindow().setStatusBarColor(statusColor);
            }

            return;
        }

        //版本在19和21之间时
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            //获取状态栏的颜色
            int color = activity.getResources().getColor(R.color.primaryDark);

            //获取当前activity 的根视图
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);

            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            //获取第一个视图
            View statusBarView = contentView.getChildAt(0);

            //改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(activity)) {
                statusBarView.setBackgroundColor(color);
                return;
            }

            statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp); //导航栏
        }
    }

    public static void compat(Activity activity) {
        compat(activity, INVALID_VAL);
    }

    /**
     * @param context
     * @return     获取actionBar的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}



