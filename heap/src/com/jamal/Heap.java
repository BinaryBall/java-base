package com.jamal;

/**
 * @author xiaoxiang  https://www.cnblogs.com/hapjin/p/4622681.html
 * @title: Heap
 * @projectName heap
 * @description: TODO
 * @date 2019/9/514:46
 */
public class Heap {

    private int[] a; // 数组，从下标 1 开始存储数据
    private int n;  // 堆可以存储的最大数据个数
    private int count; // 堆中已经存储的数据个数

    public Heap(int capacity) {
        a = new int[capacity + 1];
        n = capacity;
        count = 0;
    }

    /**
     * 堆的插入，采用从下往上的堆化,需要浪费第一个存储空间，即数组下标1为堆顶
     * @param data
     */
    public void insert(int data) {
        if (count >= n) return; // 堆满了
        ++count;
        a[count] = data;
        int i = count;
        //
        while (i/2 > 0 && a[i] > a[i/2]) {
            swap(a, i, i/2); // swap() 函数作用：交换下标为 i 和 i/2 的两个元素
            i = i/2;
        }
    }

    /**
     * 删除堆顶元素
     */
    public void removeMax() {
        if (count == 0) return ; // 堆中没有数据
        a[1] = a[count];
        a[count] = 0;
        --count;
        heapify(a, count, 1);
    }

    public int pop(){
        if (count == 0) return -1; // 堆中没有数据
        int temp = a[1];
        a[1] = a[count];
        a[count] = 0;
        --count;
        heapify(a, count, 1);
        return temp;
    }

    /**
     * 堆化，将最大的元素放到堆顶
     * @param a
     * @param n
     * @param i
     */
    private void heapify(int[] a, int n, int i) { // 自上往下堆化
        while (true) {
            int maxPos = i;
            if (i*2 <= n && a[i] < a[i*2]) maxPos = i*2;
            if (i*2+1 <= n && a[maxPos] < a[i*2+1]) maxPos = i*2+1;
            if (maxPos == i) break;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }


    private void swap(int[] a, int i, int i1) {
        int temp = a[i];
        a[i]=a[i1];
        a[i1] = temp;
    }

    public void displayer(){
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
}
