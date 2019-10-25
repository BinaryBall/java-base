package com.jamal.bloomfilter;

import java.util.BitSet;

/**
 * bloom_filter
 * 2019/10/16 17:33
 *
 * @author 曾小辉
 **/
public class SimpleBloomFilterTest {

    // bit 数组的大小
    private static final int DEFAULT_SIZE = 1000;
    // 用来生产三个不同的哈希函数的
    private static final int[] seeds = new int[]{7, 31, 61,};
    // bit 数组
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    // 存放哈希函数的数组
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public static void main(String[] args) {
        SimpleBloomFilterTest filter = new SimpleBloomFilterTest();
        filter.add("https://voice.hupu.com/nba/2492440.html");
        filter.add("https://voice.hupu.com/nba/2492437.html");
        filter.add("https://voice.hupu.com/nba/2492439.html");
        System.out.println(filter.contains("https://voice.hupu.com/nba/2492440.html"));
        System.out.println(filter.contains("https://voice.hupu.com/nba/249244.html"));
    }
    public SimpleBloomFilterTest() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }
    /**
     * 向布隆过滤器添加元素
     * @param value
     */
    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }
    /**
     * 判断某元素是否存在布隆过滤器
     * @param value
     * @return
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    /**
     * 哈希函数
     */
    public static class SimpleHash {
        private int cap;
        private int seed;
        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }
        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }
}
