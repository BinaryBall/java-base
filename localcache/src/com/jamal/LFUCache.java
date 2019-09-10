package com.jamal;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工具类
 */
public class LFUCache<K, V> {

    private ConcurrentHashMap<Object, Cache> concurrentHashMap;

    final int size;


    public LFUCache(int capacity) {
        this.size = capacity;
        this.concurrentHashMap = new ConcurrentHashMap<>(capacity);
        new Thread(new TimeoutTimerThread()).start();
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public Object get(K key) {
        checkNotNull(key);
        if (concurrentHashMap.isEmpty()) return null;
        if (!concurrentHashMap.containsKey(key)) return null;
        Cache cache = concurrentHashMap.get(key);
        if (cache == null) return null;
        cache.setHitCount(cache.getHitCount()+1);
        cache.setAccessTime(System.currentTimeMillis());
        return cache.getValue();
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     */
    public void put(K key, V value,long expire) {
        checkNotNull(key);
        checkNotNull(value);
        // 当缓存存在时，更新缓存
        if (concurrentHashMap.containsKey(key)){
            Cache cache = concurrentHashMap.get(key);
            cache.setHitCount(cache.getHitCount()+1);
            cache.setWriteTime(System.currentTimeMillis());
            cache.setAccessTime(System.currentTimeMillis());
            cache.setExpireTime(expire);
            cache.setValue(value);
            return;
        }
        // 已经达到最大缓存
        if (isFull()) {
            Object kickedKey = getKickedKey();
            if (kickedKey !=null){
                // 移除最少使用的缓存
                concurrentHashMap.remove(kickedKey);
            }else {
                return;
            }
        }
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setValue(value);
        cache.setWriteTime(System.currentTimeMillis());
        cache.setAccessTime(System.currentTimeMillis());
        cache.setHitCount(1);
        cache.setExpireTime(expire);
        concurrentHashMap.put(key, cache);
    }

    /**
     * 检测字段是否合法
     *
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * 判断是否达到最大缓存
     *
     * @return
     */
    private boolean isFull() {
        return concurrentHashMap.size() == size;
    }

    /**
     * 获取最少使用的缓存
     * @return
     */
    private Object getKickedKey() {
        Cache min = Collections.min(concurrentHashMap.values());
        return min.getKey();
    }




    /**
     * 处理过期缓存
     */
    class TimeoutTimerThread implements Runnable {
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(60);
                    expireCache();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 创建多久后，缓存失效
         *
         * @throws Exception
         */
        private void expireCache() throws Exception {
            System.out.println("检测缓存是否过期缓存");
            for (Object key : concurrentHashMap.keySet()) {
                Cache cache = concurrentHashMap.get(key);
                long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()
                        - cache.getWriteTime());
                if (cache.getExpireTime() > timoutTime) {
                    continue;
                }
                System.out.println(" 清除过期缓存 ： " + key);
                //清除过期缓存
                concurrentHashMap.remove(key);
            }
        }
    }

}


