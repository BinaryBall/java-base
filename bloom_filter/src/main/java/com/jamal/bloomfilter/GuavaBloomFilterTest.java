package com.jamal.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * bloom_filter
 * 2019/10/16 17:11
 *
 * @author 曾小辉
 **/
public class GuavaBloomFilterTest {
    // bit 数组大小
    private static int size = 10000;
    // 布隆过滤器
    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, 0.03);

    public static void main(String[] args) {
        // 先向布隆过滤器中添加 10000 个url
        for (int i = 0; i < size; i++) {
            String url = "https://voice.hupu.com/nba/" + i;
            bloomFilter.put(url);
        }
        // 前10000个url不会出现误判
        for (int i = 0; i < size; i++) {
            String url = "https://voice.hupu.com/nba/" + i;
            if (!bloomFilter.mightContain(url)) {
                System.out.println("该 url 被采集过了");
            }
        }

        List<String> list = new ArrayList<String>(1000);
        // 再向布隆过滤器中添加 2000 个 url ，在这2000 个中就会出现误判了
        // 误判的个数为 2000 * fpp
        for (int i = size; i < size + 2000; i++) {
            String url = "https://voice.hupu.com/nba/" + i;
            if (bloomFilter.mightContain(url)) {
                list.add(url);
            }
        }
        System.out.println("误判数量：" + list.size());
    }
}
