package com.dc.hailan.data;

import com.alibaba.fastjson.JSON;
import com.dc.hailan.utils.StrUtil;
import com.dc.hailan.utils.annotation.sqlite.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dc on 2017/2/20.
 *
 * 最 基础类
 * 结合  fastJson  解析 json
 *
 */

public class UtilData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer table_id;

    public UtilData() {
    }

    public Integer getTable_id() {
        return this.table_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }


    /**
     *
     * fastJson   用法   把将对象转换为JSON字符串
     * @return
     */
    public String toString() {
        return JSON.toJSONString(this);
    }


    /**
     * 反序列化  把  string 变成 类
     *
     * @param cls
     * @param jsonString
     * @param <T>
     * @return
     */
    public static <T extends UtilData> T fromString(Class<? extends UtilData> cls, String jsonString) {
        UtilData t = null;

        if(StrUtil.isEmptyOrNull(jsonString)) {
            return null;
        } else {

            try {
                t = (UtilData)JSON.parseObject(jsonString, cls);
            } catch (Exception var4) {
                t = null;
            }

            return (T) t;
        }
    }


    /**
     *
     * 反序列化  把  string 变成  列表
     *
     * @param cls
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends UtilData> List<T> fromList(Class<T> cls, String list) {
        if(list == null) {
            return null;
        } else {
            List arrayList = JSON.parseArray(list, cls);
            return arrayList;
        }
    }



}
