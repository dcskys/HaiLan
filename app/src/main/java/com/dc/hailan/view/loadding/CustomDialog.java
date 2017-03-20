package com.dc.hailan.view.loadding;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.dc.hailan.R;


/**
 *  自定义  加载对话框
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static CustomDialog instance(Activity activity) {

         //加载对话框圆圈视图 view
        LoadingView v = (LoadingView) View.inflate(activity, R.layout.common_progress_view, null);

        v.setColor(ContextCompat.getColor(activity, R.color.reader_menu_bg_color));

         //设置对话框的样式(主题样式)
        CustomDialog dialog = new CustomDialog(activity, R.style.loading_dialog);
        //对话框加载的布局
        dialog.setContentView(v,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );

        return dialog;
    }
}
