package com.jamal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @author xiaoxiang
 * @title: app
 * @projectName localcache
 * @description: TODO
 * @date 2019/9/1011:09
 */
public class app {
    public static void main(String[] args) throws Exception {
////        LFUCache localCache = new LFUCache();
////        localCache.put("key", "66666");
////        System.out.println(localCache.get("key"));
////        TimeUnit.SECONDS.sleep(6);
////        System.out.println("清除缓存后："+localCache.get("key"));
//
//        FileWriter writer = new FileWriter("txt.txt");
//        BufferedWriter bufferedWriter = new BufferedWriter(writer);
////        for(int i = 0;i<5;i++){
////            bufferedWriter.write("");
//        bufferedWriter.write("哈哈哈哈哈\n哈哈哈哈哈\n哈哈哈哈哈\n哈哈哈哈哈\n哈哈哈哈哈\n");
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//
//
////        }
//        LFUCache localCache = new LFUCache(3);
//        for (int i = 0; i < 3; i++) {
//            localCache.put("01"+i, "张三"+i,2*60);
//        }
//        localCache.get("010");
//        localCache.get("011");
//        localCache.get("010");
//        localCache.put("013","李四",2*60);
//
//        for (int i = 0; i < 4; i++) {
//            System.out.println(localCache.get("01"+i));
//        }

        LRUCache lruCache = new LRUCache(5);
        for (int i = 0; i < 5; i++) {
            lruCache.put("lru"+i, "张三"+i);
        }
        lruCache.put("lru5","李四");

        for (int i = 0; i < 6; i++) {
            System.out.println(lruCache.get("lru"+i));
        }
    }
}
