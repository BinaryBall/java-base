package com.jamal;

/**
 * @author xiaoxiang
 * @title: HeadSort
 * @projectName heap
 * @description: TODO
 * @date 2019/9/517:39
 */
public class HeadSort {


//    private void buildHeap(int[] a, int n) {
//        for (int i = n/2; i >= 1; --i) {
//            heapify(a, n, i);
//        }
//    }
//
//    private void heapify(int[] a, int n, int i) {
//        while (true) {
//            int maxPos = i;
//            if (i*2 <= n && a[i] < a[i*2]) maxPos = i*2;
//            if (i*2+1 <= n && a[maxPos] < a[i*2+1]) maxPos = i*2+1;
//            if (maxPos == i) break;
//            swap(a, i, maxPos);
//            i = maxPos;
//        }
//    }
//    // n 表示数据的个数，数组 a 中的数据从下标 1 到 n 的位置。
//    public void sort(int[] a, int n) {
//        buildHeap(a, n);
//        int k = n;
//        while (k > 1) {
//            swap(a, 1, k);
//            --k;
//            heapify(a, k, 1);
//        }
//    }
private void buildHeap(int[] a, int n) {
    for (int i = n/2; i >= 0; i--) {
        heapify(a, n, i);
    }
}

    private void heapify(int[] a, int n, int i) {
        while (true) {
            int maxPos = i;
            if (i*2 <= n && a[i] < a[i*2]) maxPos = i*2;
            if (i*2+1 <= n && a[maxPos] < a[i*2+1]) maxPos = i*2+1;
            if (maxPos == i) break;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }
    // n 表示数据的个数，数组 a 中的数据从下标 1 到 n 的位置。
    public void sort(int[] a, int n) {
        buildHeap(a, n-1);
        int k = n-1;
        while (k > 0) {
            swap(a, 0, k);
            --k;
            heapify(a, k, 0);
        }
    }
    private void swap(int[] a, int i, int i1) {
        int temp = a[i];
        a[i]=a[i1];
        a[i1] = temp;
    }

}
