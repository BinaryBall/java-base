package com.jamal;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工具类
 * LRU
 */
public class LRUCache<K, V> {

    private class Node{
        Node prev;
        Node next;
        Object key;
        Object value;

        public Node(Object key, Object value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }


    private int capacity;

    private ConcurrentHashMap<Object, Node> concurrentHashMap;

    private Node head = new Node(-1, -1);
    private Node tail = new Node(-1, -1);

    public LRUCache(int capacity) {
        // write your code here
        this.capacity = capacity;
        this.concurrentHashMap = new ConcurrentHashMap<>(capacity);
        tail.prev = head;
        head.next = tail;
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
        Node current = concurrentHashMap.get(key);
        // 将当前链表移出
        current.prev.next = current.next;
        current.next.prev = current.prev;

        move_to_tail(current);

        return current.value;
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        checkNotNull(key);
        checkNotNull(value);
        // 当缓存存在时，更新缓存
        if (concurrentHashMap.containsKey(key)){
            Node current = concurrentHashMap.get(key);
            // 将当前链表移出
            current.prev.next = current.next;
            current.next.prev = current.prev;

            move_to_tail(current);
            return;
        }
        // 已经达到最大缓存
        if (isFull()) {
            concurrentHashMap.remove(head.next.key);
            head.next.next.prev = head;
            head.next = head.next.next;
        }
        Node node = new Node(key,value);
        concurrentHashMap.put(key,node);
        move_to_tail(node);
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
        return concurrentHashMap.size() == capacity;
    }

    private void move_to_tail(Node current) {
        // 将当前链表添加到尾部
        tail.prev.next = current;
        current.prev = tail.prev;
        tail.prev = current;
        current.next = tail;
    }

}


