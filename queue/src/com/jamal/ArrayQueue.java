package com.jamal;

/**
 * 基于数组的队列
 */
public class ArrayQueue {

    // 存放数据的数组
    private String[] items;
    // 容器的大小
    private int size = 0;
    // 第一个节点
    private int head = 0;
    // 最后一个节点
    private int tail = 0;

    // 构造函数
    public ArrayQueue(int size){
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
         * 判断队列满了的条件，tail = size,head = 0,
         */
        if (tail == size && head == 0) return -1;

        /**
         * 如果tail = size，但是head != 0,说明前有数据删除，队列未满，需要数据迁移
         */
        if (tail == size){
            // head 后面的数据都需要往前迁移 head 位
            for (int i= head;i< size;i++){
                items[i-head] = items[i];
            }
            // 将最后一个元素迁移 head 位
            tail -=head;
            // 第一个元素指向 0
            head = 0;
        }
        // 向队列中添加元素
        items[tail] = data;

        tail++;

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
        // 第一个元素后移一次，这样做的好处是在出队时不需要数据迁移
        head ++ ;

        return result;
    }
}
