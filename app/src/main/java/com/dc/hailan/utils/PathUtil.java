package com.dc.hailan.utils;

import com.dc.hailan.utils.data.StatedPerference;
import com.dc.hailan.utils.datastorage.StorageUtil;
import com.dc.hailan.utils.logger.L;

import java.io.File;

/**
 * Created by dc on 2017/2/17.
 * 路径工具类
 */

public class PathUtil {

    public static final String PATH_CACHE = "cache";
    public static String PATH_WEQIA = "Qia";

    public static final String PATH_CONFIG = "config";
    public static final String PATH_PICTURE = "picture";
    public static final String PATH_FILE = "file";
    public static final String PATH_DATA = "data";
    public static final String PATH_AVATAR = "avatar";
    public static final String PATH_EXCEPTION = "exception";
    public static final String PATH_VOICE = "voice";
    public static final String PATH_CRASH = "crash";
    private static String defaultDiskPath = null;

    private static String RootPath = "HaiLan";

    public PathUtil() {
    }

    /**
     *
     * 在  程序 一开始就会调用  BaseApplication
     * @param rootPath
     */
    public static void initUtil(String rootPath) {

        if(StrUtil.notEmptyOrNull(rootPath)) {

            RootPath = rootPath;

            if(rootPath.equalsIgnoreCase("ssstai")) {
                PATH_WEQIA = "shangshang";
            }
        }
        //根据 储存大小获取   默认储存的根路径
        defaultDiskPath = StorageUtil.getSaveDiskPath(52428800L);
        L.e("默认 路径地址为 ："+defaultDiskPath);
        if(defaultDiskPath == null) {
            if(L.D) {
                L.e("error no enough space to use");
            }
        } else {
            StatedPerference.getInstance().put("pr_default_disk_path", defaultDiskPath);
        }
    }



    /**
     *返回默认 的 sd卡 路径
     *
     * todo  用法
     *
     *  String filePath = PathUtil.getFilePath() + File.separator + data.getName();
     *
     * @return
     */
    public static String getDefaultdiskpath() {
        return defaultDiskPath;
    }

    public static String getCachePath() {
        String path = getPathFolder("cache");
        return path == null?null:path;
    }

    public static String getWQPath() {
        return getDownPathFolder(PATH_WEQIA);
    }
    public static String getFilePath() {
        return getDownPathFolder("file");
    }

    public static String getConfigPath() {
        return getPathFolder("config");
    }

    public static String getPicturePath() {
        return getPathFolder("picture");
    }

    public static String getDataPath() {
        return getPathFolder("data");
    }

    public static String getCrashPath() {
        return getPathFolder("crash");
    }

    public static String getAvatarPath() {
        return getPathFolder("avatar");
    }

    public static String getExceptionPath() {
        String exceptionPath = getDataPath() + File.separator + "exception";
        return FileUtil.getFolder(exceptionPath);
    }

    public static String getVoicePath() {
        return getPathFolder("voice");
    }


    /**
     * 文件目录
     * @param path
     * @return
     */
    private static String getPathFolder(String path) {
        if(StrUtil.notEmptyOrNull(defaultDiskPath)) {
            return FileUtil.getFolder(defaultDiskPath + File.separator + RootPath + File.separator + path + File.separator);
        } else {
            String tempPath = getTemporaryDiskPath(52428800L);
            return StrUtil.notEmptyOrNull(tempPath)?FileUtil.getFolder(tempPath + File.separator + RootPath + File.separator + path + File.separator):null;
        }
    }


    /** 上面用
     * @param fileSize
     * @return
     */
    public static String getTemporaryDiskPath(long fileSize) {

        String temporayDiskPath = (String)StatedPerference.getInstance().get("pr_temporary_disk_path", String.class, "");

        //内部空间 大于0
        if(temporayDiskPath != null && StorageUtil.getTotalSpace(temporayDiskPath) > 0L) {
            return temporayDiskPath;
        } else {
            String diskPath = StorageUtil.getSaveDiskPath(fileSize);

            if(StrUtil.isEmptyOrNull(diskPath)) {
                StatedPerference.getInstance().put("pr_temporary_disk_path", diskPath);
            }
            return diskPath;
        }
    }

    private static String getDownPathFolder(String path) {
        return StrUtil.notEmptyOrNull(StorageUtil.getSDcardPath(52428800L))?FileUtil.getFolder(defaultDiskPath + File.separator + RootPath + File.separator + path + File.separator):null;
    }



    /**
     * @param path
     * @return
     */
    public static boolean isPathInDisk(String path) {
        if(StrUtil.isEmptyOrNull(path)) {
            return false;
        } else if(path.startsWith("/")) {
            return true;
        } else if(path.contains("storage")) {
            return true;
        } else if(path.contains("emulated")) {
            return true;
        } else if(path.contains("sdcard")) {
            return true;
        } else if(path.contains("/mnt")) {
            return true;
        } else if(path.startsWith("content:")) {
            return true;
        } else {

            String path1 = StorageUtil.getExternalStorageDirectory();
            if(StrUtil.isEmptyOrNull(path1)) {
                path1 = "------------------------------++";
            }

            String path2 = StorageUtil.getSdcard2StorageDirectory();
            if(StrUtil.isEmptyOrNull(path2)) {
                path2 = "---------------------++00";
            }

            String path3 = StorageUtil.getEmmcStorageDirectory();
            if(StrUtil.isEmptyOrNull(path3)) {
                path3 = "--0-0-0-0-0-0-0-0-0-0-";
            }

            String path4 = StorageUtil.getOtherExternalStorageDirectory();
            if(StrUtil.isEmptyOrNull(path4)) {
                path4 = "909-90909-9090-909";
            }

            String path5 = StorageUtil.getInternalStorageDirectory();
            if(StrUtil.isEmptyOrNull(path5)) {
                path5 = "678-9098-dfafdfasdf-------------";
            }

            return path.contains(path1) || path.contains(path2) || path.contains(path3) || path.contains(path4) || path.contains(path5);
        }
    }
}
