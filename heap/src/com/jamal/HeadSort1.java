package com.jamal;

/**
 * @author xiaoxiang
 * @title: HeadSort
 * @projectName heap
 * @description: TODO
 * @date 2019/9/517:39
 */
public class HeadSort1 {



    // n 表示数据的个数，数组 a 中的数据从下标 1 到 n 的位置。
    public void sort(int[] a, int n) {
        Heap heap = new Heap(n+1);
        // 先将数组添加到堆中
        for (int i =0;i<n;i++){
            heap.insert(a[i]);
        }
        for (int i =0;i<n;i++){
            a[i] = heap.pop();
        }
    }
    private void swap(int[] a, int i, int i1) {
        int temp = a[i];
        a[i]=a[i1];
        a[i1] = temp;
    }

}
