package com.jamal;

/**
 * 优先队列
 */
public class PriorityQueue {

    // 存放数据的数组
    private Integer[] items;
    // 容器的大小
    private int size = 0;
    // 第一个节点
    private int head = 0;

    // 构造函数
    public PriorityQueue(int size){
        this.size = size;
        items = new Integer[size];
    }

    /**
     * 入队操作
     * @param data
     * @return
     */
    public int enqueue(Integer data){
        int j;
        if (head == 0){
            items[head++] = data;
        }
        else {
            for (j=head-1;j>=0;j--){
                // 将小的数往后排
                if (data > items[j]){
                    items[j+1] = items[j];
                }else {
                    break;
                }
            }
            items[j+1] = data;
            head++;
        }
        return 1;
    }

    public Integer dequeue(){
        return items[--head];
    }

    public Integer[] getItems() {
        return items;
    }
}
