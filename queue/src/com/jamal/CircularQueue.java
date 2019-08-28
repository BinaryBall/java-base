package com.jamal;

/**
 * 环形队列,不需要数据迁移，提高性能
 */
public class CircularQueue {

    // 存放数据的数组
    private String[] items;
    // 容器的大小
    private int size = 0;
    // 第一个节点
    private int head = 0;
    // 最后一个节点
    private int tail = 0;

    // 构造函数
    public CircularQueue(int size){
        this.size = size;
        items = new String[size];
    }

    /**
     * 入队操作
     * @param data
     * @return
     */
    public int enqueue(String data){
        // 如果最后一个节点等于容器大小，说明队列满了
        /**
         * 判断环形队列满了的条件，(tail+1)求余等于head
         */
        if ((tail+1)%size == head) return -1;

        // 向队列中添加元素
        items[tail] = data;
        // 因为是环形队列，所以下边是数组长度的余数
        tail= (tail+1)%size;

        return 1;
    }

    /**
     * 出队操作
     * @return
     */
    public String dequeue(){
        // 第一个元素和最后一个元素相等时，队列为空
        if (head == tail) return null;

        String result = items[head];
        // 因为是环形队列，所以下边是数组长度的余数
        head = (head+1)% size ;

        return result;
    }
}
