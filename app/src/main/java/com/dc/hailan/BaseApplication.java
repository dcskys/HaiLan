package com.dc.hailan;

import android.app.Application;
import android.content.Context;

import com.dc.hailan.base.BaseInit;
import com.dc.hailan.data.WPfCommon;
import com.dc.hailan.data.global.Hks;
import com.dc.hailan.data.net.loginreg.LoginUserData;
import com.dc.hailan.utils.greendao.GreenDaoManager;
import com.dc.hailan.utils.greendao.demo.DaoMaster;
import com.dc.hailan.utils.greendao.demo.DaoSession;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.logging.Level;


/**
 * 基础配置
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    private LoginUserData loginUser;//用户类

    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
        BaseInit.getInstance().init(this, getString(R.string.co_root_path), true);

        init();

    }

    private void init() {

        OkGo.init(this);//网络框架
        OkGo.getInstance()
                .debug("okGo", Level.INFO,true)  // 最后的true表示是否打印okgo的内部异常
                 .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(3); //可以全局统一设置超时重连次数
               // .addCommonParams(params);   //设置全局公共参数

        GreenDaoManager.getInstance(); //数据库管理类

    }


    public static BaseApplication getInstance() {
        return instance;
    }


    /**
     * 查询本地储存的登录类
     * @return
     */
    public LoginUserData getLoginUser() {
        if (loginUser == null) {
            loginUser = WPfCommon.getInstance().get(Hks.user_info, LoginUserData.class);
        }
        return loginUser;
    }

    public void setLoginUser(LoginUserData loginUser) {
        this.loginUser = loginUser;
        if (this.loginUser == null) {
            WPfCommon.getInstance().remove(Hks.user_info);
        } else {
            WPfCommon.getInstance().put(Hks.user_info, loginUser, true);
        }
    }





}
