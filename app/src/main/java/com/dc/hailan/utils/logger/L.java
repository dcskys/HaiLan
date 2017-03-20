package com.dc.hailan.utils.logger;

import com.dc.hailan.base.BaseInit;
import com.dc.hailan.utils.LHelper;

/**
 * Created by dc on 2017/2/17.
 *
 * 日志输出
 *
 */

public class L {

    //控制 日志输出
    public static boolean D = true;


    public L() {
    }

    public static void i(String msg) {

        (new LogContent(LogContent.Type.Information, msg)).flush();
    }


    public static void i(String msg, Throwable tr) {
        (new LogContent(LogContent.Type.Information, msg, tr)).flush();
    }

    public static void e(String msg) {
        (new LogContent(LogContent.Type.Error, msg)).flush();
    }

    public static void e(String msg, Throwable tr) {
        (new LogContent(LogContent.Type.Error, msg, tr)).flush();
    }

    public static void d(String msg) {
        (new LogContent(LogContent.Type.Debug, msg)).flush();
    }

    public static void d(String msg, Throwable tr) {
        (new LogContent(LogContent.Type.Debug, msg, tr)).flush();
    }

    public static void v(String msg) {
        (new LogContent(LogContent.Type.Verbose, msg)).flush();
    }

    public static void v(String msg, Throwable tr) {
        (new LogContent(LogContent.Type.Verbose, msg, tr)).flush();
    }

    public static void w(String msg) {
        (new LogContent(LogContent.Type.Warning, msg)).flush();
    }

    public static void w(String msg, Throwable tr) {
        (new LogContent(LogContent.Type.Warning, msg, tr)).flush();
    }


    /**
     * @param msg
     *  BaseInit.ctx   Application
     *  0  short
     *
     */
    public static void toastShort(String msg) {

        TispToastFactory.getToast(BaseInit.ctx, msg, 0).show();
    }

    public static void toastShort(int msgRes) {
        TispToastFactory.getToast(BaseInit.ctx, BaseInit.ctx.getString(msgRes), 0).show();
    }

    public static void toastLong(String msg) {
        // 1  Long
        TispToastFactory.getToast(BaseInit.ctx, msg, 1).show();
    }

    public static void toastLong(int msgRes) {
        TispToastFactory.getToast(BaseInit.ctx, BaseInit.ctx.getString(msgRes), 1).show();
    }


    /**
     * 帮助打印  json
     * @param message
     */
    public static void json(Object message) {
        (new LHelper()).json(String.valueOf(message));
    }




}
