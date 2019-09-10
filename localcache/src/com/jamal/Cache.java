package com.jamal;

/**
 * 缓存类
 *
 */
public class Cache implements Comparable<Cache>{

    // 键
    private Object key;
    // 缓存值
    private Object value;
    // 最后一次访问时间
    private long accessTime;
    // 创建时间
    private long writeTime;
    // 存活时间
    private long expireTime;
    // 命中次数
    private Integer hitCount;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    public long getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(long writeTime) {
        this.writeTime = writeTime;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public int compareTo(Cache o) {
        return hitCount.compareTo(o.hitCount);
    }
}
