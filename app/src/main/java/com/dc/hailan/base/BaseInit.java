package com.dc.hailan.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dc.hailan.utils.PathUtil;
import com.dc.hailan.utils.data.UtilsConstants;
import com.dc.hailan.utils.logger.L;

/**
 * Created by dc on 2017/2/17.
 */

public class BaseInit {

    public static Application ctx;

    private static BaseInit instance;

    //单例
    public static BaseInit getInstance() {
        if(instance == null) {
            instance = new BaseInit();
        }
        return instance;
    }

    private BaseInit() {
    }

    /**
     * @param ctx     Application  全局 this
     * @param rootPath
     * @param debug
     */
    public void init(Application ctx, String rootPath, boolean debug) {

        this.ctx = ctx;
        L.D = debug;
        PathUtil.initUtil(rootPath);
    }

   /* public void showSql() {
        UtilsConstants.DEBUG_DB = true;
    }
*/

    /**
     *
     * 调用Context对象的getSharedPreferences()方法获得的SharedPreferences对象可以被同一应用程序下的其他组件共享.
     调用Activity对象的getPreferences()方法获得的SharedPreferences对象只能在该Activity中使用.

     * @param fileString
     * @return
     *
     *
     *   mode        MODE_PRIVATE     0
     */
    public static SharedPreferences getPreferences(String fileString) {
        return ctx.getSharedPreferences(fileString, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getPreferences(String fileString, int mod) {
        return ctx.getSharedPreferences(fileString, mod);
    }


}
