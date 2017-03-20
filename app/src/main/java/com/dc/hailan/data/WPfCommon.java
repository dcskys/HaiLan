package com.dc.hailan.data;

import com.dc.hailan.utils.data.StatedPerference;

/**
 * Created by dc on 2017/2/20.
 *
 * 储存账号的信息
 * 不用登陆信息的文件存储，存储在一个固定文件里面
 */

public class WPfCommon extends StatedPerference {

    private static WPfCommon instance;

    public static WPfCommon getInstance() {
        if (instance == null) {
            instance = new WPfCommon();
        }
        return instance;
    }
}
