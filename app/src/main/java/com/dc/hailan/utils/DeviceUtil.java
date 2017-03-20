package com.dc.hailan.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by dcv on 2017/2/20.
 *
 * 设备工具类
 */

public class DeviceUtil {

    public DeviceUtil() {
    }


    /**
     * 获取当前 app版本号
     * @param ctx
     * @return
     */
    public static Integer getVersionCode(Activity ctx) {

        PackageManager packageManager = ctx.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException var4) {
            var4.printStackTrace();
        }

        Integer versionCode = Integer.valueOf(packInfo.versionCode);

        return versionCode;
    }



}
