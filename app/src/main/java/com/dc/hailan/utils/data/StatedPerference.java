package com.dc.hailan.utils.data;

import android.content.SharedPreferences;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.dc.hailan.data.UtilData;
import com.dc.hailan.utils.StrUtil;
import com.dc.hailan.utils.logger.L;

import java.util.List;

/**
 * Created by dc on 2017/2/20.
 *
 * StatedPerference  app
 */

public class StatedPerference extends SharedPerferenceBase {

    private SharedPreferences sharedPreferences;

    private static LruMemoryCache<String, Object> cachedObjs;

    //控制 日志输出
    protected static boolean debug_per = false;


    private static StatedPerference instance;

    public StatedPerference() {
    }



    public static StatedPerference getInstance() {
        if(instance == null) {
            instance = new StatedPerference();
        }
        return instance;
    }


    /**
     * 创建 SharedPreference 的文件名
     *
     * SharedPreferences  对象  私有的
     * @return
     */
    public SharedPreferences getSharedPreferences() {

        if(StrUtil.isEmptyOrNull(this.getPerName())) {
            //为空 时返回  这个 SharedPerference
            return getPreferences("no_vale_pp");
        } else {
            if(this.sharedPreferences == null) {
                //
                this.sharedPreferences = getPreferences(this.getPerName());
            }
            return this.sharedPreferences;
        }
    }


    /**
     *
     * 获取 对应类型
     * @param cls
     * @param <T>
     * @return
     */
    public <T> Object getDefault(Class<T> cls) {

        Object dvalue = null;

        if(cls == Integer.class) {
            dvalue = Integer.valueOf(0);
        } else if(cls != Float.class && cls != Double.class) {
            if(cls == Boolean.class) {
                dvalue = Boolean.valueOf(true);
            } else if(cls == Long.class) {
                return Long.valueOf(0L);
            }
        } else {
            dvalue = Float.valueOf(0.0F);
        }
        return dvalue;
    }


    public <T> void put(String key, T value) {

        this.put(key, value, Boolean.valueOf(false));
    }


    /**
     *
     * 泛型  任何类型 转化为json String    Base64转码 存入
     * @param key
     * @param value
     * @param bCache
     * @param <T>
     */
    public <T> void put(String key, T value, Boolean bCache) {
        if(debug_per) {
            L.e("存入 key = " + key + ", 到文件" + this.getPerName());
        }

        if(value != null) {

            if(this.getCachedObjs().get(key) != null) {
                //HashMap 中移除
                this.getCachedObjs().remove(key);
            }

            //booleanValue  返回原始的布尔值
            if(bCache != null && bCache.booleanValue()) {
                this.getCachedObjs().put(key, value);
            }

            String realStr = "";

            if(value instanceof UtilData) {  //都是变成 JSON字符串
                //JSON字符串
                realStr = value.toString();
            } else {
                realStr = JSON.toJSONString(value);
            }
            //Base64转码
            byte[] tmpBytes = Base64.encode(realStr.getBytes(), 0);

            if(this.getSharedPreferences() != null) {
                //转化为String 在储存
                putString(this.getSharedPreferences(), key, new String(tmpBytes));
            } else if(debug_per) {
                L.e("没有配置文件信息，不能写入");
            }
        }
    }


    public <T> T get(String key, Class<T> cls) {
        return this.get(key, cls, (T)null);
    }


    /**
     *
     * 解码成对应类型
     * @param key
     * @param cls
     * @param dvalue
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> cls, T dvalue) {

        if(debug_per) {
            L.e("获取单个 key = " + key + "从文件" + this.getPerName());
        }

        Object obj = this.getCachedObjs().get(key);

        if(obj != null) {
            return (T) obj;
        } else {

            String stoStr = getString(this.getSharedPreferences(), key, "");

            if(StrUtil.isEmptyOrNull(stoStr)) {
                //获取对应类型
                return dvalue == null? (T) this.getDefault(cls) :(T)dvalue;
            } else {

                try {
                    byte[] e = Base64.decode(stoStr, 0); //解码

                    String realStr = new String(e);
                    //把json 解析成类
                    Object data = JSON.parseObject(realStr, cls);
                    return (T)data;
                } catch (Exception var9) {
                    var9.printStackTrace();
                    return dvalue == null?(T)this.getDefault(cls):(T)dvalue;
                }
            }
        }
    }


    public <T> List<T> getList(String key, Class<T> cls) {
        if(debug_per) {
            L.e("获取列表 key = " + key + "从文件" + this.getPerName());
        }

        Object obj = this.getCachedObjs().get(key);
        if(obj != null) {
            return (List)obj;
        } else {
            String mList = this.getSharedPreferences().getString(key, "");
            if(StrUtil.isEmptyOrNull(mList)) {
                return null;
            } else {
                try {
                    byte[] e = Base64.decode(mList, 0);
                    String realStr = new String(e);
                    List list = JSON.parseArray(realStr, cls);
                    return list;
                } catch (Exception var8) {
                    var8.printStackTrace();
                    return null;
                }
            }
        }
    }

    public void remove(String key) {
        this.getCachedObjs().remove(key);
        if(this.getSharedPreferences() != null) {
            removeKey(this.getSharedPreferences(), key);
        } else if(debug_per) {
            L.e("没有配置文件信息，不能写入");
        }

    }

    /**
     * perference  的文件名
     * @return
     */
    public String getPerName() {
        if(debug_per) {
            L.e("从共同配置里面获取");
        }
        return "4.0_new_perf";
    }

    public LruMemoryCache<String, Object> getCachedObjs() {
        if(cachedObjs == null) {
            cachedObjs = new LruMemoryCache(100);
        }
        return cachedObjs;
    }


}
