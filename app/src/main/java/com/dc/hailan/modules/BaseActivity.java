package com.dc.hailan.modules;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.dc.hailan.R;
import com.dc.hailan.utils.StatusBarCompat;
import com.dc.hailan.view.loadding.CustomDialog;

import butterknife.ButterKnife;

public  abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mCommonToolbar;
    protected Context mContext;

    protected int statusBarColor = 0;

    private boolean mNowMode; //换肤（夜间模式 ）

    //加载对话框
    private CustomDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());


        //沉浸式状态栏的颜色
        if (statusBarColor > 0) {
            StatusBarCompat.compat(this, statusBarColor);
        } else if (statusBarColor == 0) {
            StatusBarCompat.compat(this);
        }

        /*api  19 -23 时*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        mContext = this;
        ButterKnife.bind(this);

        //toolBar
        mCommonToolbar = ButterKnife.findById(this, R.id.common_toolbar);

        if (mCommonToolbar != null) {
            initToolBar();  //抽象给子类
            setSupportActionBar(mCommonToolbar);
        }

        configViews();//抽象给子类
        initDatas();//抽象给子类

    }

    public abstract int getLayoutId();
    public abstract void initToolBar();
    public abstract void initDatas();
    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();


    /**
     * 公用方法
     *
     * @param views
     */
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }


    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }


    /**
     * 呈现 加载动画框
     */
    public void showDialog() {
        getDialog().show();
    }

    /**
     * 取消加载动画框
     */
    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        dismissDialog();
    }


    //菜单栏点击   返回键  退出
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
