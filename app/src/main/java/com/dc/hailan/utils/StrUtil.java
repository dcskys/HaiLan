package com.dc.hailan.utils;

/**
 * Created by dc on 2017/2/17.
 *字符串 工具类

 */

public class StrUtil {

    public StrUtil() {
    }


    /**
     * @param className 截取第二 部分名
     * @return
     */
    public static String getShortClassName(String className) {
        if(className == null) {
            return null;
        } else {
            int index = className.lastIndexOf(".");
            return className.substring(index + 1);
        }
    }


    /**
     * @param string
     * @param length  重复 次数
     * @return
     */
    public static String makeLongRepeatString(String string, int length) {
        if(string == null) {
            return "";
        } else {
            StringBuffer buffer = new StringBuffer();

            for(int i = 0; i < length; ++i) {
                buffer.append(string);
            }
            return buffer.toString();
        }
    }


    public static boolean isEmptyOrNull(String string) {
        return string == null || string.trim().length() == 0 || string.equalsIgnoreCase("null");
    }


    public static boolean notEmptyOrNull(String string) {
        return string != null && !string.equalsIgnoreCase("null") && !string.equalsIgnoreCase("[]") && string.trim().length() > 0;
    }


    /**
     * 截取 部分路径  用于创建 文件
     * @param path
     * @return
     */
    public static String getFileNameByPath(String path) {
        if(path == null) {
            return null;
        } else {
            int index = path.lastIndexOf("/");
            return path.substring(index + 1);
        }
    }



}
