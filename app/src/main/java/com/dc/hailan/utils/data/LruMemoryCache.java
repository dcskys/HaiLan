package com.dc.hailan.utils.data;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.dc.hailan.utils.logger.L;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dc on 2017/2/20.
 *
 */

public class LruMemoryCache<K, V> {


    /**
     * LinkedHashMap 是HashMap的一个子类，保存了记录的插入顺序，
     * 在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的.也可以在构造时用带参数，按照应用次数排序。在遍历的时候会比HashMap慢，不过有种情况例外，当HashMap容量很大，实际数据较少时，遍历起来可能会比 LinkedHashMap慢，
     * 因为LinkedHashMap的遍历速度只和实际数据有关，和容量无关，而HashMap的遍历速度和他的容量有关
     * 如果需要输出的顺序和输入的相同,那么用LinkedHashMap 可以实现,它还可以按读取顺序来排列.
     */
    private final LinkedHashMap<K, V> map;

    /**
     * HashMap 是一个最常用的Map,
     * 具有很快的访问速度，遍历时，取得数据的顺序是完全随机的
     * HashMap不支持线程的同步，即任一时刻可以有多个线程同时写HashMap;可能会导致数据的不一致。
     * 如果需要同步，可以用 Collections的synchronizedMap方法使HashMap具有同步的能力，或者使用ConcurrentHashMap。
     *
     */
    private HashMap<K, V> tmpMap;


    private int size;
    private int maxSize;
    private int putCount;
    private int createCount;
    private int evictionCount;

    private int hitCount;

    private int missCount;


    public LruMemoryCache(int maxSize) {
        if(maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else {
            this.maxSize = maxSize;
            //true表示按照访问顺序迭代，false时表示按照插入顺序
            this.map = new LinkedHashMap(0, 0.75F, true);

            this.tmpMap = new HashMap();
        }
    }


    public LinkedHashMap<K, V> getMap() {
        return this.map;
    }



    public final V get(K key) {

        if(key == null) {
            return null;
        } else {

            Object mapValue;

            synchronized(this) {

                label69: {
                    Object var10000;
                    try {
                        mapValue = this.map.get(key);
                        if(mapValue != null) {
                            ++this.hitCount;
                            var10000 = mapValue;
                        } else {
                            ++this.missCount;
                            break label69;
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                        break label69;
                    }

                    return (V) var10000;
                }
            }

             //返回 null
            Object createdValue = this.create(key);

            if(createdValue == null) {
                return null;
            } else {

                synchronized(this) {

                    ++this.createCount;

                    mapValue = this.map.put(key, (V) createdValue);
                    if(mapValue != null) {
                        this.map.put(key, (V) mapValue);
                    } else {
                        //返回1
                        this.size += this.safeSizeOf(key, (V) createdValue);
                    }
                }

                if(mapValue != null) {
                   // this.entryRemoved(false, key, createdValue, mapValue);

                    return (V) mapValue;
                } else {
                    if(this.size >= this.maxSize) {
                        this.trimToSize(this.maxSize);
                    }

                    return (V) createdValue;
                }
            }
        }
    }


    public final V put(K key, V value) {
        if(key != null && value != null) {
            Object previous;
            synchronized(this) {
                ++this.putCount;
                this.size += this.safeSizeOf(key, value);
                previous = this.map.put(key, value);
                if(previous != null) {
                    this.size -= this.safeSizeOf(key, (V) previous);
                }
            }

            if(previous != null) {
                this.entryRemoved(false, key, (V) previous, value);
            }

            if(this.size >= this.maxSize) {
                this.trimToSize(this.maxSize);
            }

            return (V) previous;
        } else {
            return null;
        }
    }





    private void trimToSize(int maxSize) {
        if(L.D) {
            L.e("trimToSize trim trim, size = " + this.size + "  : maxSzie = " + maxSize);
        }

        while(true) {
            Iterator iterator;
            label73: {
                iterator = null;
                Object value;
                Object iterator1;
                synchronized(this) {
                    label86: {
                        if(this.size >= 0 && (!this.map.isEmpty() || this.size == 0)) {
                            if(this.size > maxSize && !this.map.isEmpty()) {
                                //MAP  遍历
                                Map.Entry value2 = (Map.Entry)this.map.entrySet().iterator().next();
                                iterator1 = value2.getKey();
                                value = value2.getValue();
                                if(!(iterator1 instanceof String) || !(value instanceof Bitmap)) {
                                    break label86;
                                }

                                String tmpKey = (String)iterator1;
                                if(tmpKey.startsWith("310") || tmpKey.startsWith("mutil:")) {

                                    this.tmpMap.put((K)iterator1, (V) value);
                                }
                                break label86;
                            }
                            break label73;
                        }

                        throw new IllegalStateException(this.getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                    }



                    this.map.remove(iterator1);
                    this.size -= this.safeSizeOf((K)iterator1, (V) value);
                    ++this.evictionCount;
                }

                //this.entryRemoved(true, iterator1, value, (Object)null);
                continue;
            }

            if(this.tmpMap.size() != 0) {
                iterator = this.tmpMap.keySet().iterator();

                while(iterator.hasNext()) {
                    synchronized(this) {
                        Object key = iterator.next();
                        Object value1 = this.tmpMap.get(key);
                        this.put((K)key,(V) value1);
                    }
                }

                this.tmpMap.clear();
            }

            return;
        }
    }





    public final V remove(K key) {
        if(key == null) {
            return null;
        } else {
            if(L.D) {
                L.w("Remove data from memory, key = " + key);
            }

            Object previous;
            synchronized(this) {
                previous = this.map.remove(key);
                if(previous != null) {
                    this.size -= this.safeSizeOf(key, (V) previous);
                }
            }

            if(previous != null) {
                //this.entryRemoved(false, key, previous, (Object)null);
            }

            return (V) previous;
        }
    }





    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
    }



    protected V create(K key) {
        return null;
    }



    private int safeSizeOf(K key, V value) {
        int result = this.sizeOf(key, value);
        if(result < 0) {
            throw new IllegalStateException("Negative size: " + key + "=" + value);
        } else {
            return result;
        }
    }


    protected int sizeOf(K key, V value) {
        return 1;
    }



    public final void evictAll() {
        this.trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }



    @SuppressLint({"DefaultLocale"})
    public final synchronized String toString() {
        int accesses = this.hitCount + this.missCount;
        int hitPercent = accesses != 0?100 * this.hitCount / accesses:0;
        return String.format("LruMemoryCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent)});
    }



}
