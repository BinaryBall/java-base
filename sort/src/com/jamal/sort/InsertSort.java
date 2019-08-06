package com.jamal.sort;

public class InsertSort {

    public static void main(String[] args) {
        int[] nums = new int[]{40, 2, 11, 5, 15, 6, 90, 10, 1, 3, 60, 17, 8};
        insertionSort(nums, nums.length);
        print(nums);
    }
    // 插入排序，a 表示数组，n 表示数组大小
    public static void insertionSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j];  // 数据移动
                } else {
                    break;
                }
            }
            a[j + 1] = value; // 插入数据
        }
    }

    public static void print(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
