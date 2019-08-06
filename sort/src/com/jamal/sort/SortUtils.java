package com.jamal.sort;

/**
 * @author xiaoxiang
 * @title: SortUtils
 * @projectName sort
 * @description: TODO
 * @date 2019/8/515:07
 */
public class SortUtils {

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

//    public static void mergeSort(int[] nums) {
//        // 排序数组
//        int[] sortNums = new int[nums.length];
//        // 求出中间值
//        int mid = nums.length >> 1;
//
//        // 前半部分数组起始下标
//        int i = 0;
//
//        // 后半部分起始下标
//        int j = mid + 1;
//
//        // 排序数组的起始下标
//        int k = 0;
//
//        while (i <= mid && j < nums.length) {
//            if (nums[i] <= nums[j]) {
//                sortNums[k++] = nums[i++];
//            } else {
//                sortNums[k++] = nums[j++];
//            }
//        }
//
//        while (i <= mid) sortNums[k++] = nums[i++];
//        while (j < nums.length) sortNums[k++] = nums[j++];
//        print(sortNums);
//    }

    public static void merge_sort(int[] nums, int leftPtr, int rightPtr) {
        if (leftPtr >= rightPtr) return;
        // 将数组分成两半
        int mid = leftPtr + (rightPtr - leftPtr) / 2;

        // 左半边排序
        merge_sort(nums, leftPtr, mid);

        // 右半边排序
        merge_sort(nums, mid + 1, rightPtr);

        merge(nums, leftPtr, mid + 1, rightPtr);
    }

    public static void merge(int[] nums, int leftPtr, int rightPtr, int rightBound) {
        // 排序数组
        int[] sortNums = new int[rightBound-leftPtr+1];
        // 求出中间值
        int mid = rightPtr - 1;

        // 前半部分数组起始下标
        int i = leftPtr;

        // 后半部分起始下标
        int j = rightPtr;

        // 排序数组的起始下标
        int k = 0;

        while (i <= mid && j <= rightBound) {
            sortNums[k++] = nums[i] <= nums[j]?nums[i++]:nums[j++];
        }

        while (i <= mid) sortNums[k++] = nums[i++];
        while (j <= rightBound) sortNums[k++] = nums[j++];

        // 将数组拷贝回nums
        for (int m = 0; m < sortNums.length; m++) nums[leftPtr + m] = sortNums[m];
        print(nums);
        System.out.println("    ");
    }

    public static void print(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }

}
