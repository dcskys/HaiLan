package com.dc.hailan.utils.datastorage;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.dc.hailan.base.BaseInit;
import com.dc.hailan.utils.logger.L;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dc on 2017/2/18.
 *
 * 储存工具类    获取  储存的根目录
 *
 * Environment.getExternalStorageDirectory()
 *
 * 设备厂商所认为的“外部存储”，有可能返回外置的SD卡目录（Micro SD Card），也可能返回内置的存储目（eMMC）。
 *
 *
 */

public class StorageUtil {

    private static String otherExternalStorageDirectory = null;

    private static int kOtherExternalStorageStateUnknow = -1;

    private static int kOtherExternalStorageStateUnable = 0;

    private static int kOtherExternalStorageStateIdle = 1;

    private static int otherExternalStorageState;

    static {
        //默认
        otherExternalStorageState = kOtherExternalStorageStateUnknow;
    }


    public StorageUtil() {
    }

    /**挂载目录是：/mnt/sdcard2
     * @return
     */
    public static final String getSdcard2StorageDirectory() {
        return "/mnt/sdcard2";
    }


    /**Emmc  手机内置存储
     * @return
     */
    public static final String getEmmcStorageDirectory() {
        return "/mnt/emmc";
    }


    /**
     * 获取  除了sd卡  额外的储存空间
     * @return
     */
    public static final String getOtherExternalStorageDirectory() {

        if(otherExternalStorageState == kOtherExternalStorageStateUnable) {
            return null;
        } else {
            //默认值
            if(otherExternalStorageState == kOtherExternalStorageStateUnknow) {

                StorageUtil.FstabReader fsReader = new StorageUtil.FstabReader();

                //storages 也就是 储存空间不可用
                if(fsReader.size() <= 0) {
                    otherExternalStorageState = kOtherExternalStorageStateUnable;
                    return null;
                }
                 //获取储存空间
                List storages = fsReader.getStorages();

                long availableSpace = 104857600L;

                String path = null;

                for(int i = 0; i < storages.size(); ++i) {
                    //每一个 存储信息
                    StorageUtil.StorageInfo info = (StorageUtil.StorageInfo)storages.get(i);
                    //储存可用空间较多
                    if(info != null && info.getAvailableSpace() > availableSpace) {
                        //获取储存空间 和 储存的根目录
                        availableSpace = info.getAvailableSpace();
                        path = info.getPath();
                    }

                    //路径为 Emmc 或 sd卡
                    if(path != null && (path.equalsIgnoreCase(getEmmcStorageDirectory()) || path.equalsIgnoreCase(getExternalStorageDirectory()) || path.equalsIgnoreCase(getSdcard2StorageDirectory()))) {
                        path = null;
                    }
                }
                otherExternalStorageDirectory = path;

                if(otherExternalStorageDirectory != null) {
                    otherExternalStorageState = kOtherExternalStorageStateIdle;
                } else {
                    otherExternalStorageState = kOtherExternalStorageStateUnable;
                }
            }

            return otherExternalStorageDirectory;
        }
    }


    /**需要往sdcard中保存特定类型的内容，，该函数可以返回特定类型的目录
     * @param type
     *
     * DIRECTORY_ALARMS //警报的铃声
    DIRECTORY_DCIM //相机拍摄的图片和视频保存的位置
    DIRECTORY_DOWNLOADS //下载文件保存的位置
    DIRECTORY_MOVIES //电影保存的位置， 比如 通过google play下载的电影
    DIRECTORY_MUSIC //音乐保存的位置
    DIRECTORY_NOTIFICATIONS //通知音保存的位置
    DIRECTORY_PICTURES //下载的图片保存的位置
    DIRECTORY_PODCASTS //用于保存podcast(博客)的音频文件
    DIRECTORY_RINGTONES //保存铃声的位置
     * @return
     */
    public static final String getExternalStoragePublicDirectory(String type) {
        File file = Environment.getExternalStoragePublicDirectory(type);
        return file != null?file.getAbsolutePath():null;
    }


    /**
     * @return 判断sd卡的状态
     */
    public static boolean hasExternalStorage() {
        //表示状态 是可挂载的
        return Environment.getExternalStorageState().equals("mounted");
    }


    /**
     * 让你的应用被卸载后，与该应用相关的数据也清除掉，该怎么办呢？
     *
     * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据

     如果使用上面的方法，当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息。

     而且上面二个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项

     如果要保存下载的内容，就不要放在以上目录下
     * @param ctx
     * @return
     */
    public static final String getExternalPrivateFilesDirectory(Context ctx) {
        String externalStoragePrivateDirectory = null;

        File file = ctx.getExternalFilesDir((String)null);

        if(file != null) {
            externalStoragePrivateDirectory = file.getAbsolutePath();
        }

        return externalStoragePrivateDirectory;
    }


    public static final String getInternalStorageDirectory() {
        String internalStorageDirectory = null;

        //于获取/data/data//files目录
        File file = BaseInit.ctx.getFilesDir();

        if(file != null) {
            internalStorageDirectory = file.getAbsolutePath();
            if(!file.exists()) {
                file.mkdirs();
            }
        }

        return internalStorageDirectory;
    }




    /**
     * 获取  外部挂载 目录  sd卡
     * 获得外部存储器的第一层的文件对象
     * @return
     */
    public static final String getExternalStorageDirectory() {
        File file = Environment.getExternalStorageDirectory();
        return file != null?file.getAbsolutePath():null;
    }


    /*** 获取手机内部可用空间大小
     * @param path
     * @return
     */
    public static long getAvailableSpace(String path) {
        if(path == null) {
            return -1L;
        } else {
            File file = new File(path);
            if(!file.exists()) {
                return -1L;
            } else {
                StatFs statfs = new StatFs(path);

                long blockSize = (long)statfs.getBlockSize();
                long availableBlocks = (long)statfs.getAvailableBlocks();
                return blockSize * availableBlocks;

            }
        }
    }

    /**获取手机内部空间大小
     * @param path
     * @return
     */
    public static long getTotalSpace(String path) {
        if(path == null) {
            return -1L;
        } else {
            File file = new File(path);
            if(!file.exists()) {
                return -1L;
            } else {
                StatFs statfs = new StatFs(path);
                //每个block 占字节数
                long blockSize = (long)statfs.getBlockSize();
                //block 数量
                long totalBlocks = (long)statfs.getBlockSize();
                return blockSize * totalBlocks;
            }
        }
    }


    /**
     * /data/data//files目录  可用空间大小
     * @return
     */
    public static long getInternalStorageAvailableSpace() {
        String path = getInternalStorageDirectory();
        return getAvailableSpace(path);
    }


    /**
     * 用户数据目录的大小
     * @return
     */
    public static long getInternalStorageTotalSpace() {
        //用户数据目录。
        File path = Environment.getDataDirectory();
        return path != null?getTotalSpace(path.getPath()):0L;
    }


    /**
     * 获取外部 挂载 sd卡 目录的大小
     * @return
     */
    public static long getExternaltStorageAvailableSpace() {

        if(!Environment.getExternalStorageState().equals("mounted")) {
            return 0L;
        } else {
            String path = getExternalStorageDirectory();
            return getAvailableSpace(path);
        }
    }


    /**‘
     *sdcard2  第二个  sd卡 目录的可用空间
     * @return
     */
    public static long getSdcard2StorageAvailableSpace() {
        if(!Environment.getExternalStorageState().equals("mounted")) {
            return 0L;
        } else {
            String path = getSdcard2StorageDirectory();
            return getAvailableSpace(path);
        }
    }


    /**
     *sdcard2  第二个  sd卡 目录的总空间
     *
     * @return
     */
    public static long getSdcard2StorageTotalSpace() {
        String path = getSdcard2StorageDirectory();
        return getTotalSpace(path);
    }


    /**
     * Emmc  路径 可用空间
     * @return
     */
    public static long getEmmcStorageAvailableSpace() {
        String path = getEmmcStorageDirectory();
        return getAvailableSpace(path);
    }

    /**
     * Emmc  路径 总空间
     * @return
     */
    public static long getEmmcStorageTotalSpace() {
        String path = getEmmcStorageDirectory();
        return getTotalSpace(path);
    }


    /**
     * 额外储存空间的 可用大小
     * @return
     */
    public static long getOtherExternaltStorageAvailableSpace() {
        if(!Environment.getExternalStorageState().equals("mounted")) {
            return -1L;
        } else if(otherExternalStorageState == kOtherExternalStorageStateUnable) {
            return -1L;
        } else {
            //外置sdk 不可用时 获取 额外 储存的空间
            if(otherExternalStorageDirectory == null) {
                getOtherExternalStorageDirectory();
            }

            return otherExternalStorageDirectory == null?-1L:getAvailableSpace(otherExternalStorageDirectory);
        }
    }


    /**
     * 额外 额外储存空间的  总量
     * @return
     */
    public static long getOtherExternaltStorageTotalSpace() {
        if(!Environment.getExternalStorageState().equals("mounted")) {
            return -1L;
        } else if(otherExternalStorageState == kOtherExternalStorageStateUnable) {
            return -1L;
        } else {
            if(otherExternalStorageDirectory == null) {
                getOtherExternalStorageDirectory();
            }

            return otherExternalStorageDirectory == null?-1L:getTotalSpace(otherExternalStorageDirectory);
        }
    }


/*
    public static void changeAccessForInternalStorage(Context ctx) {
        String shellScript = "chmod 705 " + getInternalStorageDirectory();

        try {
            runShellScriptForWait(shellScript);
        } catch (SecurityException var3) {
            L.w("--", var3);
        }

    }*/



    /*public static boolean runShellScriptForWait(String cmd) throws SecurityException {

        ShellUtil thread = new ShellUtil(cmd);
        thread.setDaemon(true);
        thread.start();
        int k = 0;

        while(!thread.isReturn() && k++ < 20) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException var4) {
                L.w("--", var4);
            }
        }

        if(k >= 20) {
            thread.interrupt();
        }

        return thread.isSuccess();
    }*/





    /**
     * //data/data//files   是否在这个目录下
     * @param path
     * @return
     */
    public static boolean isInternalStoragePath(String path) {
        ///data/data//files目录
        String rootPath = getInternalStorageDirectory();

        return path != null && path.startsWith(rootPath);
    }


    /**

     * @param saveSize    需要使用的空间大小
     *
       哪个剩余空间大    储存在 哪个空间   返回路径

     * @return   返回 储存空间的根目录
     */
    public static String getSaveDiskPath(long saveSize) {

        String savePath = null;

        if(getExternaltStorageAvailableSpace() > saveSize) {

            savePath = getExternalStorageDirectory();
        } else if(getSdcard2StorageAvailableSpace() > saveSize) {
            savePath = getSdcard2StorageDirectory();
        } else if(getEmmcStorageAvailableSpace() > saveSize) {
            savePath = getEmmcStorageDirectory();
        } else if(getOtherExternaltStorageAvailableSpace() > saveSize) {
            savePath = getOtherExternalStorageDirectory();
        } else if(getInternalStorageAvailableSpace() > saveSize) {
            savePath = getInternalStorageDirectory();
        }

        return savePath;
    }

    /**
     * 获取  sd卡的路径    根据大小
     * @param saveSize
     * @return
     */
    public static String getSDcardPath(long saveSize) {
        String savePath = null;
        if(getExternaltStorageAvailableSpace() > saveSize) {
            savePath = getExternalStorageDirectory();
        } else if(getSdcard2StorageAvailableSpace() > saveSize) {
            savePath = getSdcard2StorageDirectory();
        } else if(getEmmcStorageAvailableSpace() > saveSize) {
            savePath = getEmmcStorageDirectory();
        }

        return savePath;
    }



    /**
     * * 获取扩展SD卡存储目录
     *
     * 返回内置SD卡目录  空间大小
     *
     */
    public static class FstabReader {

        final List<StorageUtil.StorageInfo> storages = new ArrayList();

        public FstabReader() {
            this.init();
        }

        //list 数量
        public int size() {
            return this.storages == null?0:this.storages.size();
        }

        public List<StorageUtil.StorageInfo> getStorages() {
            return this.storages;
        }

        public void init() {
           //"system/etc/vold.fstab” 只是一个简单的配置文件，它描述了Android的挂载点信息。
            File file = new File("/system/etc/vold.fstab");

            if(file.exists()) {
                FileReader fr = null;
                BufferedReader br = null;
                try {
                    fr = new FileReader(file);
                    if(fr != null) {
                        br = new BufferedReader(fr);

                        for(String e = br.readLine(); e != null; e = br.readLine()) {
                            if(e.startsWith("dev_mount")) {
                                String[] tokens = e.split("\\s");
                                //挂载的sd卡路径
                                String path = tokens[2];
                                 //获取可用空间大小
                                long availableSpace = StorageUtil.getAvailableSpace(path);

                                if(availableSpace > 0L) {
                                    //路径 可用空间 和 总空间
                                    StorageUtil.StorageInfo storage = new StorageUtil.StorageInfo(path, availableSpace, StorageUtil.getTotalSpace(path));
                                    this.storages.add(storage);
                                }
                            }
                        }
                    }
                } catch (Exception var22) {
                    L.e("--", var22);
                } finally {
                    if(fr != null) {
                        try {
                            fr.close();
                        } catch (IOException var21) {
                            L.e("--", var21);
                        }
                    }

                    if(br != null) {
                        try {
                            br.close();
                        } catch (IOException var20) {
                            L.e("--", var20);
                        }
                    }

                }
            }

        }
    }








    /**
     * 此接口强行对实现它的每个类的对象进行整体排序。
     */
    static class StorageInfo  implements Comparable<StorageUtil.StorageInfo>{

        private String path;
        private long availableSpace;
        private long totalSpace;

        StorageInfo(String path, long availableSpace, long totalSpace) {
            this.path = path;
            this.availableSpace = availableSpace;
            this.totalSpace = totalSpace;
        }

        //比较此对象与指定对象的顺序。如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。
        //比较总空间的大小
        @Override
        public int compareTo(StorageInfo another) {
            return another == null?1:(this.totalSpace - another.totalSpace > 0L?1:-1);
        }

        long getAvailableSpace() {
            return this.availableSpace;
        }

        long getTotalSpace() {
            return this.totalSpace;
        }

        String getPath() {
            return this.path;
        }

    }


}
