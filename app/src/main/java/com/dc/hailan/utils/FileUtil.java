package com.dc.hailan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.dc.hailan.utils.logger.L;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Created by dc on 2017/3/17.
 *
 * 文件工具类
 */
public class FileUtil {

    public FileUtil() {
    }

    /**
     *把图片  储存到 路径
     * @param filePath
     * @param bitmap
     * @param quality
     * @throws IOException
     */
    public static void saveImageToSD(String filePath, Bitmap bitmap, int quality) throws IOException {
        if(bitmap != null) {
            File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if(!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        }

    }

    public static String getFormatFilePath(String path) {
        return TextUtils.isEmpty(path)?"":"file://" + path;
    }


    /**
     * 根据 文件名 创建  文件
     * @param folderName
     * @return
     */
    public static String getFolder(String folderName) {
        if(folderName == null) {
            return null;
        } else {
            //创建文件
            File dir = NativeFileUtil.createFolder(folderName);
            return dir != null?dir.getAbsolutePath():null;
        }
    }


    /**
     * 把 内容写入 到  指定路径中
     * @param filePath
     * @param content
     */
    public static void writeStringToFile(String filePath, String content) {
        if(filePath == null) {
            if(L.D) {
                L.e("file path is null or empty");
            }

        } else {
            BufferedWriter writer = null;
            try {
                File e = createOrReadFile(filePath);
                if(e == null) {
                    return;
                }
                writer = new BufferedWriter(new FileWriter(e), 8192);
                writer.write(content);
                writer.flush();
                writer.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            } finally {
                if(writer != null) {
                    try {
                        writer.close();
                    } catch (Exception var12) {
                        var12.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     *
     * 获取文件中的内容
     * @param filePath
     * @return
     */
    public static String getStringFromFile(String filePath) {
        BufferedReader reader = null;

        try {
            File e = createOrReadFile(filePath);
            if(e == null) {
                return null;
            }

            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new FileReader(e), 8192);
            String tempString = null;

            while((tempString = reader.readLine()) != null) {
                buffer.append(tempString);
            }

            String var6 = buffer.toString();
            return var6;
        } catch (IOException var15) {
            L.w("--", var15);
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException var14) {
                    L.w("--", var14);
                }
            }

        }

        return null;
    }


    /**
     * 本地图片地址   获取 Bitmap
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream e = new FileInputStream(url);
            return BitmapFactory.decodeStream(e);
        } catch (FileNotFoundException var2) {
            L.w("--", var2);
            return null;
        }
    }


    /**
     *输入流  获取  String
     * @param is
     * @return
     * @throws IOException
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while((line = in.readLine()) != null) {
            buffer.append(line);
        }

        return buffer.toString();
    }


    /**
     *获取  文件  或 文件夹的大小
     * @param filePath
     * @return
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0L;

        try {
            if(file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception var5) {
            L.w("--", var5);
        }

        return formetFileSize(blockSize);
    }

























































    /**
     * 获取文件夹的大小
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0L;
        File[] flist = f.listFiles();

        for(int i = 0; i < flist.length; ++i) {
            if(flist[i].isDirectory()) {
                size += getFileSizes(flist[i]);
            } else {
                size += getFileSize(flist[i]);
            }
        }

        return size;
    }


    /**
     *
     * 文件大小
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0L;
        FileInputStream fis = null;
        if(file.exists()) {
            try {
                fis = new FileInputStream(file);
                size = (long)fis.available();
                fis.close();
            } catch (Exception var13) {
                ;
            } finally {
                try {
                    fis.close();
                } catch (Exception var12) {
                    ;
                }

            }
        }

        return size;
    }


    /**
     *
     * 根据长度   转化成对应类型
     * @param fileS
     * @return
     */
    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if(fileS == 0L) {
            return wrongSize;
        } else {
            if(fileS < 1024L) {
                fileSizeString = df.format((double)fileS) + "B";
            } else if(fileS < 1048576L) {
                fileSizeString = df.format((double)fileS / 1024.0D) + "KB";
            } else if(fileS < 1073741824L) {
                fileSizeString = df.format((double)fileS / 1048576.0D) + "MB";
            } else {
                fileSizeString = df.format((double)fileS / 1.073741824E9D) + "GB";
            }

            return fileSizeString;
        }
    }

    /**
     *
     * 根据长度   转化成对应类型
     * @param fileS
     * @param sizeType
     * @return
     */
    public static double formetFileSize(long fileS, int sizeType) {

        DecimalFormat df = new DecimalFormat("#.00");

        double fileSizeLong = 0.0D;
        switch(sizeType) {
            case 1:
                fileSizeLong = Double.valueOf(df.format((double)fileS)).doubleValue();
                break;
            case 2:
                fileSizeLong = Double.valueOf(df.format((double)fileS / 1024.0D)).doubleValue();
                break;
            case 3:
                fileSizeLong = Double.valueOf(df.format((double)fileS / 1048576.0D)).doubleValue();
                break;
            case 4:
                fileSizeLong = Double.valueOf(df.format((double)fileS / 1.073741824E9D)).doubleValue();
        }

        return fileSizeLong;
    }


    public static File createOrReadFile(String filePath) throws IOException {

        if(filePath == null) {
            if(L.D) {
                L.e("file path is null or empty");
            }
            return null;
        } else {

            File file = new File(filePath);

            if(!file.exists()) {
                //截取部分作为  文件名
                String fileName = StrUtil.getFileNameByPath(filePath);
                if(fileName == null) {
                    if(L.D) {
                        L.e("write path error");
                    }
                    return null;
                }
                //创建文件
                String path = getFile(file.getParent(), fileName);

                if(path == null || !path.equals(filePath)) {
                    if(L.D) {
                        L.e("create file error");
                    }
                    return null;
                }
            }
            return file;
        }
    }


    public static String getFile(String folderString, String fileName) throws IOException {
        if(folderString == null | fileName == null) {
            return null;
        } else {
            File folder = new File(folderString);
            File file;
            if(folder.exists()) {
                file = NativeFileUtil.createFile(folderString + File.separator + fileName);
                return file != null?file.getAbsolutePath():null;
            } else {
                folder = NativeFileUtil.createFolder(folderString);
                if(folder != null) {
                    file = NativeFileUtil.createFile(folder.getAbsolutePath() + File.separator + fileName);
                    return file != null?file.getAbsolutePath():null;
                } else {
                    return null;
                }
            }
        }
    }




}
