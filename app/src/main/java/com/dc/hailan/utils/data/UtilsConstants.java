package com.dc.hailan.utils.data;

/**
 * Created by dc on 2017/2/20.
 *
 * 常用常量
 */

public class UtilsConstants {

    public static final int LOG_CLEAR_TIME = 604800000;

    public static final int NETWORK_TIME_OUT = 90000;

    public static final int IO_BUFFER_SIZE = 8192;
    public static final String HTTP_CHARSET = "utf-8";
    public static final int DEFAULT_DISK_CACHE_SIZE = 52428800;
    public static final int ORIGINAL_DISK_CACHE_SIZE = 52428800;
    public static final int SD_DOWNLOAD_SIZE = 52428800;
    public static final int DEFAULT_IMAGE_HEIGHT = 1280;
    public static final int DEFAULT_IMAGE_WIDTH = 960;
    public static final String DATA_PRIMARY_KEY = "table_id";
    public static final String KEY_GALLERY_POSITION = "gallery_position";
    public static final String KEY_GALLERY_ORI = "gallery_ori";
    public static final String KEY_GALLERY_TYPE = "gallery_type";
    public static final String KEY_GALLERY_DELETE = "gallery_delete";
    public static final String KEY_GALLERY_DELETE_PICTURE = "gallery_delete_picture";

    public static final int REQUESTCODE_PHOTO = 10111;
    public static final int REQUESTCODE_PICKER = 10112;
    public static final int REQUESTCODE_DELETE_PICTURE = 10114;

    public static final String FILE_START = "file://";

    public static boolean DEBUG_DB = false;

    public static final String MUTIL_KEY = "mutil:";
    public static final String MASK_KEY = "mask:";
    public static final String DB_KEY = "weqia_sql";
    public static final String pr_log_clear_time = "pr_log_clear_time";
    public static final String pr_default_disk_path = "pr_default_disk_path";
    public static final String pr_temporary_disk_path = "pr_temporary_disk_path";
    public static final String NETWORK_MSG = "network_msg";

    public UtilsConstants() {
    }



}
