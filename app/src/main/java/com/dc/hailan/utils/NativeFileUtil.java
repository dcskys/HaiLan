package com.dc.hailan.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by dc on 2017/3/17.
 */

public class NativeFileUtil {

    public NativeFileUtil() {
    }



    public static File createFile(String path) throws IOException {
        if(!StrUtil.isEmptyOrNull(path)) {
            File file = new File(path);
            if(file.exists()) {
                file.createNewFile();
                return file;
            }
            int lastIndex = path.lastIndexOf(File.separator);
            String dir = path.substring(0, lastIndex);
            if(createFolder(dir) != null) {
                file.createNewFile();
                return file;
            }
        }

        return null;
    }




    /**
     * 创建文件
     * @param path
     * @return
     */
    public static File createFolder(String path) {
        if(StrUtil.notEmptyOrNull(path)) {
            File dir = new File(path);
            if(dir.exists() && dir.isDirectory()) {
                return dir;
            } else {
                dir.mkdirs();
                return dir;
            }
        } else {
            return null;
        }
    }





}
